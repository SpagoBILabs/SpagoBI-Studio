package it.eng.spagobi.studio.core.services.datamartTemplate;

import it.eng.spagobi.meta.model.Model;
import it.eng.spagobi.meta.model.business.BusinessModel;
import it.eng.spagobi.meta.model.serializer.EmfXmiSerializer;
import it.eng.spagobi.studio.core.services.dataset.DeployDatasetService;
import it.eng.spagobi.studio.core.util.JSONReader;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.xerces.util.URI;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.json.JSONObject;
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
			logger.error("error in retrieving business model ",e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning",
			"error in retrieving business model ");				
			return false;
		}
		final BusinessModel finalBusinessModel = businessModel;

		// search for file datamart.jar, is rooted in the same folder of the sbiModel selected
		IFile datamartFile = null;
		String errorMessage = null;
		Folder container = (Folder)fileSel.getParent();
		if(container.getFolder(businessModel.getName()).exists()){
			IFolder modelNameFolder = container.getFolder(businessModel.getName());
			if(modelNameFolder.getFolder("dist").exists()){
				logger.debug("Entered in "+businessModel.getName()+"/dist");
				IFolder fold = modelNameFolder.getFolder("dist");
				// search for datamart.jar
				if(fold.getFile("datamart.jar").exists()){
					datamartFile = fold.getFile("datamart.jar");
				}
				else{
					errorMessage = "Could not find datamart.jar in "+businessModel.getName()+"/dist/";
				}
			}
			else{
				errorMessage = "Could not find dist folder on "+businessModel.getName()+"/: you must create at list one query asssociated to the model";	
			}
		}
		else{
			errorMessage = "Could not find folder "+businessModel.getName();				
		}

		if(datamartFile == null){
			logger.warn(errorMessage);
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning",
					errorMessage);	
			return false;
		}
		else{
			logger.debug("found file "+businessModel.getName()+"/dist/datamart.jar");
		}

		// got the template datamart.jar


		final IFile finalDatamartFile = datamartFile;
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
				java.net.URI uri = finalDatamartFile.getLocationURI();
				java.io.File fileJar = new java.io.File(uri.getPath());
				FileDataSource fileDataSource=new FileDataSource(fileJar);
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

		logger.debug("OUT");
		return true;

	}

















}
