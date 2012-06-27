/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.wizards.downloadModelWizard;

import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.activation.DataHandler;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBIDownloadModelWizard extends AbstractSpagoBIDocumentWizard  {
	private SpagoBIDownloadModelWizardPage page;
	String projectName = null;
	Folder folderSelected = null;

	private static Logger logger = LoggerFactory.getLogger(SpagoBIDownloadModelWizard.class);

	/**
	 *  vector that stores user messages to show at the end of download
	 */
	Vector<String> messages = new Vector<String>();

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SpagoBIDownloadModelWizard() {
		super();
		setNeedsProgressMonitor(true);
	}


	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new SpagoBIDownloadModelWizardPage(selection);
		addPage(page);
	}


	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		logger.debug("IN");
		TreeItem[] selectedItems = page.getTree().getSelection();
		if(selectedItems == null){
			logger.warn("Error; no models selected");
		}
		else{	
			// cycle on selected items
			for (int i = 0; i < selectedItems.length; i++) {
				TreeItem selectedItem = selectedItems[i];	
				TreeItem folderItem = selectedItem;
				//	gets the folder and file name selected
				if (selectedItem.getData().toString().endsWith(".sbimodel")){
					folderItem = selectedItem.getParentItem();
					if(folderItem == null){
						logger.warn("Error; no models selected");
						MessageDialog.openError(getShell(), "Warning", "Choose a folder and a model file to continue.");	
						return false;
					}
					String folderName = folderItem.getText();
					String modelName = selectedItem.getText();
					if (folderName != null && !folderName.equals("") &&
						modelName != null && !modelName.equals("") && modelName.endsWith(".sbimodel")){
						downloadModel(folderName, modelName);
					}
					else{
						logger.warn("Could not download model,not a right element was selected!");
						MessageDialog.openError(getShell(), "Warning", "Choose a folder and a model file to continue.");	
						return false;
					}
				}
			}

			// print messages on file that could not be written
			if(messages.size()>0){
				String message = "Following models could not be added because already exist in project with the same name. You must delete firstly the existing ones: ";
				for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
					String msg = (String) iterator.next();
					message += msg;
					if(iterator.hasNext()){
						message += ", ";
					}
				}
				MessageDialog.openWarning(page.getShell(), "Warning", message);
				messages= new Vector<String>();

			}

			doFinish();
		}
		logger.debug("OUT");
		return true;
	}


	public boolean downloadModel(String folderName, String modelName){
		logger.debug("IN");
		boolean toReturn=true;
		
		SpagoBIServerObjectsFactory proxyServerObjects = null;

		try{
			proxyServerObjects = new SpagoBIServerObjectsFactory(projectName);
			
			Template template  = proxyServerObjects.getServerDocuments().downloadDatamartFile(folderName, modelName);
			
			if (template == null){
				logger.error("The download operation has returned a null object!");			
				return false;
			}		
			overwriteTemplate(template);		
		}	
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page", e);
			MessageDialog.openError(getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return false;
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve template",e);
			MessageDialog.openError(getShell(), "Error", "Could not get the template from server for document");	
			return false;
		}	

		logger.debug("OUT");
		return toReturn;
	}

	/** The worker method. Download the template and creates the file
	 * 
	 * @param document: the SdkDocument refderencing the BiObject
	 * @throws CoreException 
	 * 
	 */

	private void doFinish() {
		logger.debug("Models downloaded");
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench _workbench, IStructuredSelection _selection) {
		this.selection = _selection;
		this.workbench=_workbench;

		Object objSel = selection.toList().get(0);
		Folder fileSelected=(Folder)objSel;
		projectName = fileSelected.getProject().getName();
		folderSelected = fileSelected;
	}


	public static String readInputStreamAsString(InputStream in) 
	throws IOException {
		logger.debug("IN");
		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while(result != -1) {
			byte b = (byte)result;
			buf.write(b);
			result = bis.read();
		}        
		logger.debug("OUT");
		return buf.toString();
	}
	
	public void overwriteTemplate(Template template) 
			throws CoreException{
		try {
			// get the directory
			String projectName = folderSelected.getProject().getName();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = root.getProject(projectName);
			IPath pathFolder = folderSelected.getProjectRelativePath(); 
			String templateFileName = template.getFileName();
			
			DataHandler dh = template.getContent(); 
			InputStream is = dh.getInputStream();
			
			IPath pathNewFile = pathFolder.append(templateFileName); 
			IFile newFile = project.getFile(pathNewFile);
			
			// create new File
			if (newFile.exists()){
				newFile.delete(true, null);
			}
			newFile.create(is, true, null);
			//set the dirty property to true 
			newFile.setPersistentProperty(SpagoBIStudioConstants.DIRTY_MODEL, "true");
		
		} catch (IOException e1) {
		
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in writing the file");				
			logger.error("Error in writing the file", e1);		
		
			return ;
		}

	}


}


