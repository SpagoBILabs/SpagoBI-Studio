package it.eng.spagobi.studio.core.wizards.downloadWizard;

import it.eng.spagobi.commons.constants.SpagoBIConstants;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.util.BiObjectUtilities;
import it.eng.spagobi.studio.core.util.FileFinder;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.bo.xmlMapping.XmlParametersMapping;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.ServerObjectsComparator;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjects;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.activation.DataHandler;

import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBIDownloadWizard extends Wizard implements INewWizard {
	private SpagoBIDownloadWizardPage page;
	private IStructuredSelection selection;
	protected IWorkbench workbench;
	String projectName = null;

	private static Logger logger = LoggerFactory.getLogger(SpagoBIDownloadWizard.class);

	/**
	 *  vector that stores user messages to show at the end of download
	 */
	Vector<String> messages = new Vector<String>();

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SpagoBIDownloadWizard() {
		super();
		setNeedsProgressMonitor(true);
	}


	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new SpagoBIDownloadWizardPage(selection);
		addPage(page);
	}

	/**
	 *  Download document, if it is a document composition ask if want to download all
	 * @param document
	 */

	public boolean downloadDocument(Document document){
		logger.debug("IN");
		// if it is a document composed download also contained documents
		if(document.getType().equalsIgnoreCase(SpagoBIConstants.DOCUMENT_COMPOSITE_TYPE) ){
			// ask user if wants to download related template
			boolean downloadContained=MessageDialog.openQuestion(getShell(), "Download contained Documents?", "You have selected a document composition, do you want to download contained documents? You will be notified if they already esists in your workspace");	
			if(downloadContained==true){
				downloadContainedTemplate(document);
			}
		}
		logger.debug("OUT");
		return downloadTemplate(document);		
	}

	/** Download contained documents and also nto subfolders
	 * 
	 * @param functionality
	 * @return
	 */

	public boolean downloadDocumentsFromFunctionality(Functionality functionality){
		logger.debug("IN");
		Document[] documents = functionality.getContainedDocuments();
		// Download contained Documents
		for (int i = 0; i < documents.length; i++) {
			Document document = documents[i];	
			downloadDocument(document);
		}
		Functionality[] funcitonalities = functionality.getContainedFunctionalities();
		// Download contained subfolders
		for (int i = 0; i < funcitonalities.length; i++) {
			Functionality funct = funcitonalities[i];	
			downloadDocumentsFromFunctionality(funct);
		}		
		logger.debug("OUT");
		return true;
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		logger.debug("IN");
		TreeItem[] selectedItems=page.getTree().getSelection();
		if(selectedItems==null){
			logger.warn("Error; no item selected");
		}
		else{	
			// cycle on selected items
			for (int i = 0; i < selectedItems.length; i++) {
				TreeItem selectedItem=selectedItems[i];
				Object docObject=selectedItem.getData();	
				// check if it is a folder or a document
				if(ServerObjectsComparator.isObjectSDKDocument(docObject)){
					Document document= ServerObjectsComparator.getDocument(docObject);
					downloadDocument(document);
				}
				else if(ServerObjectsComparator.isObjectSDKFunctionality(docObject)){
					// cycle on all document contained (also subfolders?)
					Functionality functionality = ServerObjectsComparator.getFunctionality(docObject);
					downloadDocumentsFromFunctionality(functionality);
				}

			}

			// print messages on file that could not be written
			if(messages.size()>0){
				String message = "Following files could not be added because already exist in project with the same name. You must delete firstly the existing ones: ";
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


	public boolean downloadContainedTemplate(Document document){
		logger.debug("IN");
		boolean toReturn=true;
		Integer id=document.getId();
		Template template=null;
		SpagoBIServerObjects proxyServerObjects = null;
		try{
			proxyServerObjects  = new SpagoBIServerObjects(projectName);
		}
		catch (NoActiveServerException e1) {
			SpagoBILogger.errorLog("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return false;
		}

		int numDocs=0;
		try{
			template=proxyServerObjects.downloadTemplate(id);
		}	
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page", e);
			MessageDialog.openError(getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return false;
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve template",e);
			MessageDialog.openError(getShell(), "Error", "Could not get the template from server for document with id "+id);	
			return false;
		}	

		template.getContent();

		String xmlString=null;
		DataHandler dh=template.getContent(); 
		InputStream is=null;		
		try {
			is=dh.getInputStream();
			xmlString=readInputStreamAsString(is);
			xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+xmlString;

			is=new ByteArrayInputStream(xmlString.getBytes("UTF-8"));

			// put all the labels in an array, so if there is a problem in parsing does not download anything
			boolean correctParsing=true;
			SAXReader reader = new SAXReader();
			org.dom4j.Document thisDocument = null;
			List<String> labels=new ArrayList<String>();
			try{
				thisDocument=reader.read(is);
				List docs= thisDocument.selectNodes("//DOCUMENTS_COMPOSITION/DOCUMENTS_CONFIGURATION/DOCUMENT");

				for (int i = 0; i < docs.size(); i++) {
					Node doc = (Node) docs.get(i);
					String label = doc.valueOf("@sbi_obj_label");
					if (label!=null) {
						labels.add(label);
					}
					else{
						correctParsing=false;
					}
				}
			}
			catch (Exception e) {
				correctParsing=false;
			}
			if(correctParsing==false){
				logger.error("error in reading the file searching for document labels ");				
				MessageDialog.openWarning(getShell(), "Warning", "error in reading template searching for document labels: will not download contained documents but only composed one");	
				return false;
			}


			for (int i = 0; i < labels.size(); i++) {
				Document docToDownload=proxyServerObjects.getDocumentByLabel(labels.get(i));
				if(docToDownload!=null){
					toReturn = downloadTemplate(docToDownload);
					if(toReturn==true){
						numDocs++;
						logger.debug("Download document with label "+docToDownload.getName());
					}
				}
			}


		} catch (Exception e1) {
			logger.error("Error in writing the file", e1);
			MessageDialog.openWarning(getShell(), "Warning", "Error in downloading contained documents; will not download contained documents but only composed one");	

			return false;
		}


		logger.debug("Downloaded # document "+numDocs);
		logger.debug("OUT");
		return toReturn;
	}

	public boolean downloadTemplate(it.eng.spagobi.studio.utils.bo.Document document){
		logger.debug("IN");
		InputStream is=null;
		//try{
		Integer id=document.getId();
		Template template=null;
		SDKProxyFactory proxyFactory = null;
		SpagoBIServerObjects spagoBIServerObjects = null;
		try{
			spagoBIServerObjects = new SpagoBIServerObjects(projectName);
			template = spagoBIServerObjects.downloadTemplate(id);
		}
		catch (NoActiveServerException e1) {
			SpagoBILogger.errorLog("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return false;
		}
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page",e);
			MessageDialog.openError(getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return false;
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve template",e);
			MessageDialog.openError(getShell(), "Error", "Could not get the template from server");	
			return false;
		}	

		if(template == null){
			logger.error("Template download is null for documentId "+id+" and label "+document.getLabel());
			return false;
		}

		// Recover information field like dataSource, dataSet, engine names!


		//Get the parameters
		String[] roles;
		try{
			roles=spagoBIServerObjects.getCorrectRolesForExecution(id);
		}
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page",e);
			MessageDialog.openError(getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return false;
		}		
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve roles for execution",e);
			MessageDialog.openError(getShell(), "Could not retrieve roles for execution", "Could not retrieve roles for execution");	
			return false;
		}			
		if(roles==null || roles.length==0){
			logger.error("No roles for execution found");
			MessageDialog.openError(getShell(), "No roles for execution found", "No roles for execution found");	
			return false;			
		}

		//SDKDocumentParameter[] parameters=null;

		DocumentParameter[] parameters=null;
		try{
			parameters=spagoBIServerObjects.getDocumentParameters(id, roles[0]);
		}
		catch (NullPointerException e) {
			logger.error("No comunication with server, check SpagoBi Server definition in preferences page",e);
			MessageDialog.openError(getShell(), "Error", "No comunication with server, check SpagoBi Server definition in preferences page");	
			return false;
		}		
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve document parameters",e);
			MessageDialog.openError(getShell(), "Could not retrieve document parameters for execution", "Could not retrieve roles for execution");	
			return false;
		}			




		// get the extension
		Integer engineId=document.getEngineId();

		Engine sdkEngine=null;
		try{
			sdkEngine=spagoBIServerObjects.getEngine(engineId);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not get engine", e);
			MessageDialog.openError(getShell(), "", "Could not get engine the template from server");	
			return false;
		}		

		String type=document.getType();
		String engineName=sdkEngine!=null?sdkEngine.getLabel(): null;
		String extension=BiObjectUtilities.getFileExtension(type, engineName);

		// create the file in the selected directory
		// get the folder selected 
		Object objSel = selection.toList().get(0);
		Folder folderSel = null;
		folderSel=(Folder)objSel;  
		String projectName = folderSel.getProject().getName();

		//Take workspace
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// get the folder where to insert the template document
		IProject project = root.getProject(projectName);
		IPath pathFolder = folderSel.getProjectRelativePath(); 

		String templateFileName=template.getFileName();

		// remove previous extensions only if a new Extension was found
		String fileName="";
		if(extension!=null){
			int indexPoint=templateFileName.indexOf('.');
			if(indexPoint!=-1){
				templateFileName=templateFileName.substring(0, indexPoint);
				fileName=templateFileName+extension;
			}
		}
		else{
			fileName=templateFileName;
		}

		IPath pathNewFile = pathFolder.append(fileName); 
		IFile newFile = project.getFile(pathNewFile);
		logger.debug("file path to download "+pathNewFile.toString());
		DataHandler dh=template.getContent(); 
		try {
			is=dh.getInputStream();
		} catch (IOException e1) {
			logger.error("Error in writing the file", e1);
			return false;
		}

		IPath projectFolder=project.getLocation();
		// Check there is not another existing file with the same name inside project directory workspace!!!
		boolean alreadyFound=FileFinder.fileExistsInSubtree(fileName, projectFolder.toString());

		if(alreadyFound){

			messages.add(fileName);
			logger.warn("File "+fileName+" already exists in your project: to download it againg you must first delete the existing one");
			return false;
			//write=MessageDialog.openQuestion(workbench.getActiveWorkbenchWindow().getShell(), "File exists: Overwrite?", "File "+newFile.getName()+" already exists, overwrite?"); 
		}

		if(true){

			try{
				newFile.create(is, true, null);
			}
			catch (CoreException e) {
				logger.error("error while creating new file", e);	
				return false;
			}


			//Set File Metadata	
			try{
				newFile=BiObjectUtilities.setFileMetaData(newFile,document, false);
			}
			catch (CoreException e) {
				logger.error("Error while setting meta data", e);	
				return false;
			}
			catch (NoActiveServerException e1) {
				SpagoBILogger.errorLog("No active server found", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "No active server found");	
				return false;
			}

			//Set ParametersFile Metadata	
			if(parameters.length>0){
				try{
					newFile=XmlParametersMapping.setFileParametersMetaData(newFile,parameters);

				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("Error while setting meta data", e);	
					return false;
				}			
			}

			try{			
				newFile=BiObjectUtilities.setFileLastRefreshDateMetaData(newFile);
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("Error while setting last refresh date", e);	
				return false;
			}			
		}
		else // choose not to overwrite the file
		{
			logger.debug("Choose to not overwrite file "+newFile.getName());	
		}
		logger.debug("OUT");
		return true;

	}


	/** The worker method. Download the template and creates the file
	 * 
	 * @param document: the SdkDocument refderencing the BiObject
	 * @throws CoreException 
	 * 
	 */

	private void doFinish() {
		logger.debug("Documents downloaded");
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


}


