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
package it.eng.spagobi.studio.core.services.modelTemplate;

import it.eng.spagobi.meta.model.Model;
import it.eng.spagobi.meta.model.business.BusinessModel;
import it.eng.spagobi.meta.model.serializer.EmfXmiSerializer;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.AlreadyPresentException;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.activation.DataHandler;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  Called by action of model refresh
 * @author Antonella Giachino (antonella.giachino@eng.it)
 *
 */

public class RestoreModelService {

	private final Template template = new Template();

	Document document=null;
	ISelection selection;
	String projectName = null;
	String modelName = null;

	// fields to retrieve only once
	String[] roles=null;
	DocumentParameter[] parameters=null;
	Engine engine=null;
	
	AlreadyPresentException alreadyPresentException = new AlreadyPresentException();
	private static Logger logger = LoggerFactory.getLogger(RestoreModelService.class);


	public RestoreModelService(ISelection _selection) {
		selection = _selection;	
	}



	public void RestoreModelTemplate() {
		
		IStructuredSelection sel = (IStructuredSelection)selection;

		// go on only if you selected a document (a file)
		Object objSel = sel.toList().get(0);
		File fileSel = null;
		try{
			fileSel = (File)objSel;
			projectName = fileSel.getProject().getName();
			modelName = fileSel.getName().substring(0, fileSel.getName().indexOf(SpagoBIStudioConstants.BACKUP_EXTENSION)-1);
		}
		catch (Exception e) {
			logger.error("No file selected",e);		

			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Not a file", "You must select a file to refresh");		
			return;
		}

		boolean restore = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Restore model template", "Confirm restore template for model " + modelName + " ?");
		if(restore){
			logger.debug("Do the model template restore model with name  " + modelName);
			
			try {
				// get the directory
				Folder folder = (Folder)fileSel.getParent().getParent();
				String projectName = folder.getProject().getName();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = root.getProject(projectName);
				IPath pathFolder = folder.getProjectRelativePath(); 
				
				IPath pathNewFile = pathFolder.append(modelName); 
				IFile newFile = project.getFile(pathNewFile);	
				if (newFile.exists()){
					newFile.delete(true, null);
				}
				newFile.create(fileSel.getContents(), true, null);
				fileSel.delete(true, null);

			}catch (Exception e1) {

				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in restore the file");				
				logger.error("Error in restore the file", e1);		

				return ;
			}

			String succesfullMessage="Succesfully restore for the template " + modelName ;
			
			logger.debug(succesfullMessage);					
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Restore succesfull", succesfullMessage);		

		}
	}


	public void overwriteTemplate(Template template, File fileSel) 
			throws CoreException{
		try {
			// get the directory
			Folder folder = (Folder)fileSel.getParent();
			String projectName = folder.getProject().getName();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = root.getProject(projectName);
			IPath pathFolder = folder.getProjectRelativePath(); 
			String templateFileName = template.getFileName();
			
			DataHandler dh = template.getContent(); 
			InputStream is = dh.getInputStream();
			
			IPath pathNewFile = pathFolder.append(templateFileName); 
			IFile newFile = project.getFile(pathNewFile);			
			
			//Rename the actual in backup file into a dedicated bck folder
			IFolder bckFolder = project.getFolder(pathFolder.append(SpagoBIStudioConstants.FOLDER_METADATA_MODEL_BCK));
			if(bckFolder == null || !bckFolder.exists()){
				bckFolder.create(true, true, null);
			}
			IPath pathOldFile = pathFolder.append(SpagoBIStudioConstants.FOLDER_METADATA_MODEL_BCK);
			pathOldFile = pathOldFile.append(templateFileName + "." + SpagoBIStudioConstants.BACKUP_EXTENSION);
			IFile oldFile = project.getFile(pathOldFile);
			if (oldFile.exists()){
				oldFile.delete(true, null);
			}
			oldFile.create(fileSel.getContents(), true, null);

			// Delete the local old file 
			if (fileSel.exists()){
				fileSel.delete(true, null);
			}
			// create new File
			newFile.create(is, true, null);

		} catch (IOException e1) {

			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in writing the file");				
			logger.error("Error in writing the file", e1);		

			return ;
		}

	}
	
}