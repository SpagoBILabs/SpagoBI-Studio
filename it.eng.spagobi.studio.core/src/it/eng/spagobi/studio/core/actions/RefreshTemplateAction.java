/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.core.actions;

import it.eng.spagobi.studio.core.wizards.deployWizard.SpagoBIDeployWizard;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.bo.xmlMapping.XmlParametersMapping;
import it.eng.spagobi.studio.utils.exceptions.AlreadyPresentException;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.BiObjectUtilities;
import it.eng.spagobi.studio.utils.util.FileFinder;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.rmi.RemoteException;

import javax.activation.DataHandler;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshTemplateAction implements IObjectActionDelegate {

	private final Template template= new Template();

	Document document=null;
	ISelection selection;
	String projectName = null;

	// fields to retrieve only once
	String[] roles=null;
	DocumentParameter[] parameters=null;
	Engine engine=null;
	// if not null means that template has changed name (used for user advertisement)
	String newTemplateName=null;
	AlreadyPresentException alreadyPresentException=new AlreadyPresentException();
	private static Logger logger = LoggerFactory.getLogger(RefreshTemplateAction.class);


	public RefreshTemplateAction() {
	}


	public void run(IAction action) {
		SpagoBIDeployWizard sbindw = new SpagoBIDeployWizard();
		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if you selected a document (a file)
		Object objSel = sel.toList().get(0);
		org.eclipse.core.internal.resources.File fileSel = null;		
		try{
			fileSel=(org.eclipse.core.internal.resources.File)objSel;
			projectName = fileSel.getProject().getName();
		}
		catch (Exception e) {
			logger.error("No file selected",e);		

			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Not a file", "You must select a file to refresh");		
			return;
		}

		//if file has document metadata associated upload it, else call wizard
		String document_idString=null;
		String document_label=null;
		try {
			document_idString=fileSel.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);			
			document_label=fileSel.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL);
		} catch (CoreException e) {
			logger.error("Error in retrieving document Label",e);		
		}
		//final Integer documentId=Integer.valueOf(document_idString);

		// IF File selected has already an id of document associated start the templater refresh, else throw an error
		if(document_idString!=null){
			ProgressMonitorPart monitor;
			monitor=new ProgressMonitorPart(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), null);
			logger.debug("Metadata found: do the template refresh, document with id "+document_idString);
			final Integer idInteger=Integer.valueOf(document_idString);
			final String label2=document_label;
			final org.eclipse.core.internal.resources.File fileSel2=fileSel;
			final NoDocumentException documentException=new NoDocumentException();
			final NoActiveServerException noActiveServerException=new NoActiveServerException();

			IRunnableWithProgress op = new IRunnableWithProgress() {			
				public void run(IProgressMonitor monitor) throws InvocationTargetException {

					monitor.beginTask("Template Refresh for document "+label2, IProgressMonitor.UNKNOWN);

					// document associated, upload the template
					SpagoBIServerObjectsFactory spagoBIServerObjects = null;
					try{
						spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectName);

						// check document still exists
						document=spagoBIServerObjects.getServerDocuments().getDocumentByLabel(label2);
						if(document==null){
							documentException.setNoDocument(true);
							return;
						}
						else{
							documentException.setNoDocument(false);
							Template mytemplate  = spagoBIServerObjects.getServerDocuments().downloadTemplate(idInteger);
							template.setContent(mytemplate.getContent());
							template.setFileName(mytemplate.getFileName());
							// get documents metadata
							String fileExtension=recoverFileExtension(document,idInteger);						
							overwriteTemplate(template, fileSel2, fileExtension, spagoBIServerObjects);
						}
					}

//					catch (NotAllowedOperationException e) {
//						logger.error("Not Allowed Operation",e);		
//						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
//								"Error upload", "Error while uploading the template: not allowed operation");	
//						return;
//					} 

					catch (NoActiveServerException e1) {
						noActiveServerException.setNoServer(true);
						return;
					}
					catch (RemoteException e) {
						if(e.getClass().toString().equalsIgnoreCase("class it.eng.spagobi.sdk.exceptions.NotAllowedOperationException")){	
							logger.error("Current User has no permission to deploy dataset", e);
							MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Current user has no permission to deploy dataset");	
						}
						else{
						logger.error("Error comunicating with server", e);			
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							"Error comunicating with server", "Error while uploading the template: missing comunication with server");	
						}
						return;
					}
					catch (CoreException e) {
						logger.error("Error in fie creation",e);		
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error in file creation", "Error in file creation");	
						return;
					}


					monitor.done();
					if (monitor.isCanceled())
						logger.error("The long running operation was cancelled");		
				}
			};


			ProgressMonitorDialog dialog=new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		
			try {
				dialog.run(true, true, op);
			} catch (InvocationTargetException e1) {
				logger.error("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return;
			} catch (InterruptedException e1) {
				logger.error("Error comunicating with server");		
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return;
			} 
			if(noActiveServerException.isNoServer()){
				logger.error("No server is defined active");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "No server is defined active");	
				return;
			}				
			// check if document has been found (could have been deleted) or if the template was already present somewhere else
			if(documentException.isNoDocument() || template.getContent()==null){
				logger.warn("Document no more present on server "+document_label);					
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error upload", "Document is no more present on server. Make a new Deploy.");	
				return;
			}		
			if(alreadyPresentException!=null && alreadyPresentException.isAlreadyPresent()){
				logger.warn("Template ealready present in project workspace: "+newTemplateName);					
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "File "+alreadyPresentException.getFilePath()+" already exists in your project: to download it againg you must first delete the existing one");
				return;
			}

			dialog.close();

			String succesfullMessage="Succesfully replaced with the last template version of the associated document "+document_label;
			if(newTemplateName!=null){
				succesfullMessage+=": template file has changed its name; new one is "+newTemplateName;
			}
			logger.debug(succesfullMessage);					
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Refresh succesfull", succesfullMessage);		
		}
		else{
			logger.warn("No document associated ");
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"No document warning", "The file selected has no document associated, to the deploy first");	
		}
	}



	public void overwriteTemplate(Template template, 
			org.eclipse.core.internal.resources.File fileSel, 
			String extension,
			SpagoBIServerObjectsFactory proxyServerObjects
	) throws CoreException{
		// get template URL to overwrite
		try {
			URI uri=fileSel.getLocationURI();
			// get the directory
			Folder folder=(Folder)fileSel.getParent();
			String projectName=folder.getProject().getName();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = root.getProject(projectName);
			IPath pathFolder = folder.getProjectRelativePath(); 
			String templateFileName=template.getFileName();
			String completeFileName="";

			// if is defined a new extension use it, else keep its name
			if(extension!=null){
				int indexPoint=templateFileName.indexOf('.');
				if(indexPoint!=-1){
					templateFileName=templateFileName.substring(0, indexPoint);
					completeFileName=templateFileName+extension;
				}
			}
			else{
				completeFileName=templateFileName;
			}

			IPath pathNewFile = pathFolder.append(completeFileName); 
			IFile newFile = project.getFile(pathNewFile);
			DataHandler dh=template.getContent(); 
			InputStream is=null;
			is=dh.getInputStream();

			IPath projectFolder=project.getLocation();

			// if it has the same name as the one before do not have to check if name is already present
			if(completeFileName.equals(fileSel.getName())){

			}
			else{
				// Check there is not another existing file with the same name inside project directory workspace!!!
				boolean alreadyFound=FileFinder.fileExistsInSubtree(completeFileName, projectFolder.toString());
				if(alreadyFound){
					alreadyPresentException.setAlreadyPresent(true);
					alreadyPresentException.setFilePath(completeFileName);
					return;
				}
				newTemplateName=completeFileName;
			}

			// I must remove previous file and add new one because it could have changed its name
			// I have also to report metadata
			fileSel.delete(true, null);


			// create new File
			newFile.create(is, true, null);


			//Set File Metadata	
			try{
				newFile=BiObjectUtilities.setFileMetaData(newFile,document, true,proxyServerObjects);
				//newFile=BiObjectUtilities.setFileMetaData(newFile,document);

				//Set ParametersFile Metadata	
				if(parameters.length>0){
					newFile=XmlParametersMapping.setFileParametersMetaData(newFile,parameters);
				}
				newFile=BiObjectUtilities.setFileLastRefreshDateMetaData(newFile);

			}
			catch (Exception e) {
				logger.error("Error while setting meta data", e);		
				return;
			}			


		} catch (IOException e1) {

			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in writing the file");				
			logger.error("Error in writing the file", e1);		

			return ;
		}

	}


	/** Retrieve document type and engine to set right file extension (if doessn't find leave the actual)
	 *  It saves paramters and engine informations in fields so they can be re used for metadata assignment
	 * @param document
	 * @param documentId
	 * @return
	 */

	public String recoverFileExtension(Document document, Integer documentId){
		//Get the parameters
		SDKProxyFactory proxyFactory = null;
		SpagoBIServerObjectsFactory proxyServerObjects = null;
		try{
			proxyServerObjects = new SpagoBIServerObjectsFactory(projectName);
		}
		catch (NoActiveServerException e1) {
			logger.error("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return null;
		}		

		try{
			roles=proxyServerObjects.getServerDocuments().getCorrectRolesForExecution(documentId);
		}
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return null;
		}		
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve roles for execution", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Could not retrieve roles for execution");	
			return null;
		}			
		if(roles==null || roles.length==0){
			logger.error("Could not retrieve roles for execution");		
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "No roles for execution found", "No roles for execution found");	
			return null;			
		}


		try{
			parameters=proxyServerObjects.getServerDocuments().getDocumentParameters(documentId, roles[0]);
		}
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return null;
		}		
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not get engine", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Could not retrieve document parameters for execution", "Could not retrieve roles for execution");	
			return null;
		}			


		// get the extension
		Integer engineId=document.getEngineId();

		Engine engine=null;
		try{
			engine=proxyServerObjects.getServerEngines().getEngine(engineId);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not get engine", e);		
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Could not get engine the template from server");	
			return null;
		}		

		String type=document.getType();
		String engineName=engine!=null?engine.getLabel(): null;
		String extension=BiObjectUtilities.getFileExtension(type, engineName);
		return extension;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;		
	}

	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub

	}






}