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
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployDocumentAction implements IObjectActionDelegate {

	private IViewPart view = null;
	ISelection selection; 
	String projectname = null;

	private static Logger logger = LoggerFactory.getLogger(DeployDocumentAction.class);


	public DeployDocumentAction() {
	}


	public void init(IViewPart view) {
		this.view = view;
		IFile file = (IFile)selection;	
		projectname = file.getProject().getName();

	}

	public void run(IAction action) {

		SpagoBIDeployWizard sbindw = new SpagoBIDeployWizard();

		//		CommonViewer commViewer=((CommonNavigator) view).getCommonViewer();
		//		IStructuredSelection sel=(IStructuredSelection)commViewer.getSelection();
		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if you selected a document
		Object objSel = sel.toList().get(0);
		org.eclipse.core.internal.resources.File fileSel = null;		
		try{
			fileSel=(org.eclipse.core.internal.resources.File)objSel;
		}
		catch (Exception e) {
			logger.warn("No file selected to deploy");					
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Not a file", "You must select a file to deploy");		
			return;
		}

		//if file has document metadata associated upload it, else call wizard

		String document_idString=null;
		String document_label=null;
		try {
			document_idString=fileSel.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);			
			document_label=fileSel.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL);
		} catch (CoreException e) {
			logger.error("Error in retrieving document Label", e);		
			//logger.error("Error in retrieving document Label", e);
		}

		// IF File selected has already and id of document associated do the upload wiyhout asking further informations
		boolean newDeploy = false;
		if(document_idString!=null){
			logger.debug("Template already associated to document "+document_idString);	
			final Integer idInteger=Integer.valueOf(document_idString);
			final String label2=document_label;
			final org.eclipse.core.internal.resources.File fileSel2=fileSel;
			final NoDocumentException documentException=new NoDocumentException();
			final NoActiveServerException noActiveServerException=new NoActiveServerException();

			IRunnableWithProgress op = new IRunnableWithProgress() {			
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					monitor.beginTask("Deploying to document "+label2, IProgressMonitor.UNKNOWN);

					if(projectname == null){
						projectname = fileSel2.getProject().getName();
					}

					try{
						// document associated, upload the template

						SpagoBIServerObjectsFactory spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectname);


						URI uri=fileSel2.getLocationURI();

						File fileJava=new File(uri.getPath()); 
						FileDataSource fileDataSource=new FileDataSource(fileJava);
						DataHandler dataHandler=new DataHandler(fileDataSource);			
						Template template=new Template();
						template.setFileName(fileSel2.getName());
						template.setContent(dataHandler);

						// check document still exists
						Document doc=spagoBIServerObjects.getServerDocuments().getDocumentById(idInteger);
						if(doc==null){
							documentException.setNoDocument(true);
							logger.warn("Document no more present on server: with id "+idInteger);					
							return;
						}
						else{
							documentException.setNoDocument(false);
							spagoBIServerObjects.getServerDocuments().uploadTemplate(idInteger, template);
						}
					}

					catch (NoActiveServerException e1) {
						// no active server found 
						noActiveServerException.setNoServer(true);
						return;
					}
					catch (RemoteException e) {
						if(e.getClass().toString().equalsIgnoreCase("class it.eng.spagobi.sdk.exceptions.NotAllowedOperationException")){	
							logger.error("Current User has no permission to complete the operation", e);
							MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Current User has no permission to complete the operation");	
						}
						else{
						
						logger.error("Error comunicating with server", e);		
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error comunicating with server", "Error while uploading the template: missing comunication with server");	
						}
						return;
					}

					monitor.done();
					if (monitor.isCanceled())
						logger.error("Operation not ended",new InterruptedException("The long running operation was cancelled"));
				}
			};


			ProgressMonitorDialog dialog=new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		
			try {
				dialog.run(true, true, op);
			} 
			catch (InvocationTargetException e1) {
				logger.error("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return;
			} catch (InterruptedException e1) {
				logger.error("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return;
			} 
			if(documentException.isNoDocument()){
				logger.error("Document no more present");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error upload", "Document is no more present on server; you can do a new deploy");	
				newDeploy = true;
				sbindw.setNewDeployFromOld(true);
			}
			if(noActiveServerException.isNoServer()){
				logger.error("No server is defined active");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "No server is defined active");	
				return;
			}

			dialog.close();
			if(!newDeploy){
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Deploy succesfull", "Deployed to the associated document "+document_label+" succesfull");		
				logger.debug("Deployed to the associated document "+document_label+" succesfull");		
			}		
		}
		else{
			newDeploy = true;
		}

		if(newDeploy)
		{
			logger.debug("deploy a new Document: start wizard");		
			// init wizard
			sbindw.init(PlatformUI.getWorkbench(), sel);
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);

			//			{
			//				@Override
			//				protected void configureShell(Shell newShell) {
			//					super.configureShell(newShell);
			//					newShell.setSize(1300, 600);
			//				}
			//			};

			// Open the wizard dialog
			dialog.open();	
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;		
	}


	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub

	}

}