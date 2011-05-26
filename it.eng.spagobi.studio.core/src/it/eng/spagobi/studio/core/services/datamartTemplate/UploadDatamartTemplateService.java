package it.eng.spagobi.studio.core.services.datamartTemplate;

import it.eng.spagobi.meta.model.Model;
import it.eng.spagobi.meta.model.business.BusinessModel;
import it.eng.spagobi.meta.model.serializer.EmfXmiSerializer;
import it.eng.spagobi.meta.querybuilder.ui.editor.SpagoBIDataSetEditor;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UploadDatamartTemplateService {

	ISelection selection; 
	private static Logger logger = LoggerFactory.getLogger(UploadDatamartTemplateService.class);
	String projectname = null;

	public UploadDatamartTemplateService(ISelection _selection) {
		selection = _selection;	
	}

	public boolean datamartUpload(){
		logger.debug("IN");

		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if ysou selected a document
		Object objSel = sel.toList().get(0);
		File fileSel=(File)objSel;
		projectname = fileSel.getProject().getName();



		logger.debug("get datamart.jar of model file name "+fileSel.getName());

		EmfXmiSerializer emfXmiSerializer = new EmfXmiSerializer();

		Model root = null;
		BusinessModel businessModel= null;
		try{
			root = emfXmiSerializer.deserialize(fileSel.getContents());
			logger.debug("Model root is [{}] ",root );
			businessModel = root.getBusinessModels().get(0);
			logger.debug("model "+businessModel.getName());	
		}
		catch (Exception e) {
			logger.error("error in retrieving business model; try refreshing model folder ",e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning",
			"error in retrieving business model: try refreshing model folder");				
			return false;
		}
		final BusinessModel finalBusinessModel = businessModel;

		// generate the jar


		//Creta temp dir
		long ll =System.currentTimeMillis();
		String UUID =Long.valueOf(ll).toString();

		String tempDirPath = System.getProperty("java.io.tmpdir");
		String idFolderPath = businessModel.getName()+"_"+UUID;
		String tempDirPathId = tempDirPath + idFolderPath;
		logger.debug("create model in temporary folder "+tempDirPathId);

		try{
			new SpagoBIDataSetEditor().generateMapping(finalBusinessModel, tempDirPathId, null);
		}
		catch (Exception e) {
			logger.error("Error in generating the datamart for model "+businessModel.getName(),e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in genertig datamart.ajr for model "+businessModel.getName());
			return false;
		}
		logger.debug("model datamart.jar created in "+tempDirPathId);


		// search for file datamart.jar, is rooted in the folder created

		String pathToSearch = tempDirPathId + java.io.File.separator + businessModel.getName() + java.io.File.separator + "dist"+java.io.File.separator +"datamart.jar";
		logger.debug("try reatrieving file "+pathToSearch);
		Path tmppath = new Path(pathToSearch);

		java.io.File datamart = tmppath.toFile();

		//		if(datamart.exists()){
		//			if(folder.getFolder(businessModel.getName()).exists()){
		//				IFolder modelNameFolder = folder.getFolder(businessModel.getName());
		//				if(modelNameFolder.getFolder("dist").exists()){
		//					logger.debug("Entered in "+modelNameFolder.getFullPath().toOSString());
		//					IFolder fold = modelNameFolder.getFolder("dist");
		//					// search for datamart.jar
		//					if(fold.getFile("datamart.jar").exists()){
		//						datamartFile = fold.getFile("datamart.jar");
		//					}
		//					else
		//						errorMessage = "Could not find datamart.jar in "+modelNameFolder.getFullPath().toOSString()+"/dist/";
		//				}
		//				else
		//					errorMessage = "Could not find dist folder on "+folder.getFullPath().toOSString()+java.io.File.separator+businessModel.getName()+"/dist/ ";	
		//			}
		//			else
		//				errorMessage = "Could not find dist folder on "+folder.getFullPath().toOSString()+java.io.File.separator+businessModel.getName()+"/ ";							
		//		}
		//		else{
		//			errorMessage = "Could not find folder "+folder.getFullPath().toOSString();				
		//		}

		if(datamart == null){
			logger.error("could not retrieve file "+pathToSearch);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
					"could not retrieve file "+pathToSearch);	
			return false;
		}
		else{
			logger.debug("found file "+businessModel.getName()+"/dist/datamart.jar");
		}

		// got the template datamart.jar


		final java.io.File finalDatamartFile = datamart;
		final NoActiveServerException noActiveServerException=new NoActiveServerException();

		IRunnableWithProgress op = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Deploying datamart.jar", IProgressMonitor.UNKNOWN);

				SpagoBIServerObjectsFactory spagoBIServerObjects = null;
				try{
					spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectname);
				}
				catch (NoActiveServerException e) {
					logger.error("no active server found");
					noActiveServerException.setNoServer(true);
					return;
				}

				Template template = new Template();
				template.setFileName(finalDatamartFile.getName());
				template.setFolderName(finalBusinessModel.getName());

				// create template content
				FileDataSource fileDataSource=new FileDataSource(finalDatamartFile);
				DataHandler dataHandler=new DataHandler(fileDataSource);			
				template.setContent(dataHandler);
				logger.debug("built Template with content data handler");

				try {
					spagoBIServerObjects.getServerDocuments().uploadDatamartTemplate(template);
				} catch (RemoteException e2) {
					logger.error("error in uploading datamart",e2);
					throw new InvocationTargetException(e2);
				}
				monitor.done();
				if (monitor.isCanceled())
					logger.error("Operation not ended",new InterruptedException("The long running operation was cancelled"));
			}
		};



		// Start monitor for upload operation
		ProgressMonitorDialog dialog=new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		
		try {
			dialog.run(true, true, op);
		} 
		catch (Exception e1) {
			logger.error("error in uploading datamart",e1);		
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "error",
			"error in uploading datamart: check server definition is right and server is avalaible");				
			dialog.close();
			return false;
		} 
		dialog.close();

		if(noActiveServerException.isNoServer()){
			logger.error("No server is defined active");			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No server is defined active");	
			return false;
		}

		// if here success
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Upload succesfull", "Uploaded to resources");		
		logger.debug("Uploaded to resources in "+businessModel.getName());		

		// delete the temporary file

		try{
			Path pathToDelete = new Path(tempDirPathId);
			java.io.File toDelete = pathToDelete.toFile();
			boolean deleted = toDelete.delete();	
			if(deleted){
				logger.warn("deleted folder "+tempDirPathId);
			}
		}
		catch (Exception e) {
			logger.warn("could not delete folder "+tempDirPathId);
		}
		logger.debug("OUT");
		return true;

	}

















}
