package it.eng.spagobi.studio.core.services.datamartTemplate;

import it.eng.spagobi.meta.model.Model;
import it.eng.spagobi.meta.model.business.BusinessModel;
import it.eng.spagobi.meta.model.serializer.EmfXmiSerializer;
import it.eng.spagobi.meta.querybuilder.ui.editor.SpagoBIDataSetEditor;
import it.eng.spagobi.studio.core.util.ComboSelectionDialog;
import it.eng.spagobi.studio.utils.bo.DataSource;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.internal.theme.ComboDrawData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UploadDatamartTemplateService {

	ISelection selection; 
	private static Logger logger = LoggerFactory.getLogger(UploadDatamartTemplateService.class);
	String projectname = null;
	String modelname = null;

	DataSource[] dataSources = null;
	String userDataSource = null;
	String messageStatusDocument = "";
	boolean documentAlreadyPresent = false;

	public static final String DATAMART_JAR = "datamart.jar";
	public static final String CALCULATED_FIELD = "cfields_meta.xml";

	public UploadDatamartTemplateService(ISelection _selection) {
		selection = _selection;	
	}



	public boolean datamartUpload(){
		logger.debug("IN");

		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if is selected a document
		Object objSel = sel.toList().get(0);
		File fileSel=(File)objSel;
		projectname = fileSel.getProject().getName();
		modelname = fileSel.getName();

		logger.debug("get datamart.jar of model file name "+fileSel.getName());

		// refresh metadata_model folder
		refreshModelFolder(fileSel);

		EmfXmiSerializer emfXmiSerializer = new EmfXmiSerializer();

		Model root = null;
		BusinessModel businessModel= null;
		try{
			root = emfXmiSerializer.deserialize(fileSel.getContents(true));
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


		// generate the jar


		//Create temp dir
		long ll =System.currentTimeMillis();
		String UUID =Long.valueOf(ll).toString();

		String tempDirPath = System.getProperty("java.io.tmpdir");
		logger.debug("Temp dir is: "+tempDirPath + " check if ends with "+java.io.File.pathSeparator);
		if(!tempDirPath.endsWith(java.io.File.separator)){
			tempDirPath += java.io.File.separator;
		}

		String idFolderPath = businessModel.getName()+"_"+UUID;
		String tempDirPathId = tempDirPath + idFolderPath;
		logger.debug("create model in temporary folder "+tempDirPathId);

		try{
			new SpagoBIDataSetEditor().generateMapping(businessModel, tempDirPathId, null);
		}
		catch (Exception e) {
			logger.error("Error in generating the datamart for model "+businessModel.getName(),e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in genertig datamart.ajr for model "+businessModel.getName());
			return false;
		}
		logger.debug("model datamart.jar created in "+tempDirPathId);


		// search for file datamart.jar, is rooted in the folder created
		String pathToSearch = tempDirPathId + java.io.File.separator + businessModel.getName() + java.io.File.separator + "dist"+java.io.File.separator +DATAMART_JAR;
		logger.debug("try reatrieving datamart.jar file "+pathToSearch);
		Path tmppath = new Path(pathToSearch);
		java.io.File datamart = tmppath.toFile();
		if(datamart == null){
			logger.error("could not retrieve file "+pathToSearch);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
					"could not retrieve file "+pathToSearch);	
			return false;
		}
		else{
			logger.debug("found file "+businessModel.getName()+"/dist/datamart.jar");
		}

		// search for non mandatory file calculatedFields, rooted in the folder created too
		String pathToSearchXml = tempDirPathId + java.io.File.separator + businessModel.getName() + java.io.File.separator + "dist"+java.io.File.separator +CALCULATED_FIELD;
		logger.debug("try reatrieving calculatedFields xml file "+pathToSearch);
		Path tmppathXml = new Path(pathToSearchXml);
		java.io.File xmlFile = tmppathXml.toFile();
		if(xmlFile == null || !xmlFile.exists()){
			logger.warn("Xml file for calculate dields was not found in "+pathToSearchXml);
			xmlFile = null;
		}
		else{
			logger.debug("found file for calculate dfields in "+pathToSearchXml+"/dist/datamart.jar");
		}		


		SpagoBIServerObjectsFactory spagoBIServerObjects = null;
		try{
			spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectname);
		}
		catch (NoActiveServerException e) {
			logger.error("No server is defined active");			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No server is defined active");
		}

//		final BusinessModel finalBusinessModel = businessModel;
//		final File finalBusinessModelFile = fileSel ; 
//		final java.io.File finalDatamartFile = datamart;
//		final java.io.File finalXmlFile = xmlFile;
		final NoActiveServerException noActiveServerException=new NoActiveServerException();
		final SpagoBIServerObjectsFactory spagoBIServerObjectsFinal = spagoBIServerObjects; 

		IRunnableWithProgress monitorCheckExistance = getMonitorCheckExistance(businessModel, spagoBIServerObjects);
		IRunnableWithProgress monitorForDatasources = getMonitorForDatasources(businessModel, spagoBIServerObjects);
		IRunnableWithProgress monitorForUpload = getMonitorForUpload(businessModel, spagoBIServerObjects, datamart, xmlFile, fileSel);


		// Start monitor for upload operation
		ProgressMonitorDialog dialog=new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		

		try {

			// the dataSource should be asked only if there is not already a documetn present with datamartnamein system

			// check if document is already present
			dialog.run(true, true, monitorCheckExistance);

			if(!documentAlreadyPresent){
				// get datasources
				dialog.run(true, true, monitorForDatasources);

				// ask datasource to user			
				if(dataSources != null){
					logger.debug("found "+dataSources.length+ " datasources: make user choose one");
					Map<String, DataSource> mapLabelToDatasource = new HashMap<String, DataSource> ();
					int size = dataSources.length;
					String[] optionsArray = new String[size];
					for (int i = 0; i < dataSources.length; i++) {
						DataSource ds = dataSources[i];
						mapLabelToDatasource.put(ds.getLabel(), ds);
						optionsArray[i] = ds.getLabel();
					}

					final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					ComboSelectionDialog csd = new ComboSelectionDialog(shell);	
					csd.setMessage("Select data source for QBE document (optional)");
					csd.setText("Data source Selection");
					csd.setOptions(optionsArray);
					logger.debug("Open datasource selection dialog");
					userDataSource = csd.open();
					logger.debug("user selected dataSource "+userDataSource);

				}
			}

			// do the uploads
			dialog.run(true, true, monitorForUpload);
		} 
		catch (InvocationTargetException e1) {
			logger.error("error in uploading datamart",e1);
			String detailMessage = e1.getTargetException() != null ? "\n\nDetail: "+e1.getTargetException().getMessage() : "";
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "error",
					"Error in uploading datamart: check server definition is right, check server is avalaible and model file is not in use on server."+detailMessage);	
			dialog.close();
			return false;
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
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Upload succesfull", "Succesfully uploaded to resources\n\n"+messageStatusDocument);		
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



	// *******  Runnable block for checking if dopcumetnAlready exists ***********		
	IRunnableWithProgress getMonitorCheckExistance(final BusinessModel businessModel, final SpagoBIServerObjectsFactory spagoBIServerObjects ){
		IRunnableWithProgress opCheck = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("check if there is already document with name "+businessModel.getName()+" ", IProgressMonitor.UNKNOWN);
				String modelname = businessModel.getName();


				logger.debug("is there documetn with llabel "+modelname);	
				try{

					Document doc  = spagoBIServerObjects.getServerDocuments().getDocumentByLabel(modelname);

					if(doc != null){
						documentAlreadyPresent = true;
						messageStatusDocument = "Detail: QBEDocument with label "+modelname+" was not added because already present in server";
					}
					else{
						documentAlreadyPresent = false;
					}
				}
				catch (RemoteException e2) {
					logger.error("error in uploading datamart",e2);
					throw new InvocationTargetException(e2);
				}
			}
		};
		return opCheck;
	}

	// *******  Runnable block for retrieving datasources ***********	
	IRunnableWithProgress getMonitorForDatasources(final BusinessModel businessModel, final SpagoBIServerObjectsFactory spagoBIServerObjects){
		IRunnableWithProgress opDs = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Retrieving data sources for selection)", IProgressMonitor.UNKNOWN);

				logger.debug("get Datasources defined");
				try{
					UploadDatamartTemplateService.this.dataSources = spagoBIServerObjects.getServerDataSources().getDataSourceList();
				}
				catch (RemoteException e2) {
					logger.error("error in uploading datamart",e2);
					throw new InvocationTargetException(e2);
				}
			}
		};
		return opDs;
	}


	// *******  Runnable block for upload     ***********	
	IRunnableWithProgress getMonitorForUpload(
			final BusinessModel businessModel
			, final SpagoBIServerObjectsFactory spagoBIServerObjects
			, final java.io.File datamartFile
			, final java.io.File xmlFile
			, final File businessModelFile)
	{
		IRunnableWithProgress op = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Deploying model files (datamart.jar and xmlFile for calculated fields " + modelname +")", IProgressMonitor.UNKNOWN);

				Template datamartTemplate = new Template();
				Template modelTemplate = new Template();
				Template xmlCalcFieldsTemplate = new Template();

				//defines properties for datamart file
				datamartTemplate.setFileName(datamartFile.getName());
				datamartTemplate.setFolderName(businessModel.getName());
				// create templates content
				FileDataSource fileDataSource=new FileDataSource(datamartFile);
				DataHandler dataHandler=new DataHandler(fileDataSource);			
				datamartTemplate.setContent(dataHandler);
				logger.debug("built Datamart template with content data handler");

				//defines properties for sbimodel file 
				modelTemplate.setFileName(modelname);
				modelTemplate.setFolderName(businessModel.getName());


				// defines properties for xml Calculated fields file
				if(xmlFile != null){
					xmlCalcFieldsTemplate.setFileName(xmlFile.getName());
					xmlCalcFieldsTemplate.setFolderName(businessModel.getName());
					// create templates content
					FileDataSource xmlDataSource=new FileDataSource(xmlFile);
					DataHandler xmlDataHandler=new DataHandler(xmlDataSource);

					xmlCalcFieldsTemplate.setContent(xmlDataHandler);
					logger.debug("built xml calculated fields with content data handler");
				}

				// create templates content
				try{
					ByteArrayDataSource byteDataSource = new ByteArrayDataSource(businessModelFile.getContents(),"application/octet-stream");	
					dataHandler = new DataHandler(byteDataSource);
					modelTemplate.setContent(dataHandler);
					logger.debug("built Model template with content data handler");
				}catch(Exception e){
					logger.error("error in getting model template",e);
					throw new InvocationTargetException(e);
				}


				try {
					spagoBIServerObjects.getServerDocuments().uploadDatamartTemplate(datamartTemplate, xmlCalcFieldsTemplate, userDataSource);
				}

				catch (RemoteException e2) {
					logger.error("error in uploading datamart",e2);
					throw new InvocationTargetException(e2);
				}
				try {
					spagoBIServerObjects.getServerDocuments().uploadDatamartModel(modelTemplate);
				} catch (RemoteException e3) {
					logger.error("error in uploading model file",e3);
					throw new InvocationTargetException(e3);
				}

				if(documentAlreadyPresent == false){
					messageStatusDocument = "Detail: QBE Document with label "+modelname+ " added to server";
				}
				monitor.done();
				if (monitor.isCanceled())
					logger.error("Operation not ended",new InterruptedException("The long running operation was cancelled"));
			}
		};
		return op;

	}







	void refreshModelFolder(File file){
		logger.debug("IN");
		// search for "Metadata_Model" folder to refresh
		final String METADATA_MODEL_FOLDER = "Metadata_Model";

		IContainer folder = file.getParent();

		while(folder != null && !folder.getName().equals(METADATA_MODEL_FOLDER)){
			folder = folder.getParent();	
		}

		if(folder != null){
			try {
				folder.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				logger.error("Error in automatically refreshing model server, please do manual refresh on Metada_Model");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in automatically refreshing model server, please do manual refresh on Metada_Model");
			}

		}
		logger.debug("OUT");

	}




	public static byte[] getByteArrayFromInputStream(InputStream is) {
		logger.debug("IN");
		try {
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(baos);

			int c = 0;
			byte[] b = new byte[1024];
			while ((c = is.read(b)) != -1) {
				if (c == 1024)
					bos.write(b);
				else
					bos.write(b, 0, c);
			}
			bos.flush();
			byte[] ret = baos.toByteArray();
			bos.close();
			return ret;
		} catch (IOException ioe) {
			logger.error("IOException", ioe);
			return null;
		} finally {
			logger.debug("OUT");
		}

	}

}
