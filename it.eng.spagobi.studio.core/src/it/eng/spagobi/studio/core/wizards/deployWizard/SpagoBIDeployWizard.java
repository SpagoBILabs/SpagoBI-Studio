package it.eng.spagobi.studio.core.wizards.deployWizard;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.util.BiObjectUtilities;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.ServerObjectsComparator;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjects;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;

import java.io.File;
import java.net.URI;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.eclipse.core.runtime.CoreException;
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

public class SpagoBIDeployWizard extends AbstractSpagoBIDocumentWizard  {
	private SpagoBIDeployWizardFormPage formPage;

	String projectName = null;

	private static Logger logger = LoggerFactory.getLogger(SpagoBIDeployWizard.class);

	String label;
	String name;
	String description;
	String type;
	String labelEngine;
	String labelDataSet;
	String labelDataSource;	
	String labelState;
	boolean criptable;
	boolean visible;
	int refreshSeconds;
	Functionality functionality;

	// boolean tells if file was already previously deployed (delete old metadata).
	boolean newDeployFromOld = false;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SpagoBIDeployWizard() {
		super();
		setNeedsProgressMonitor(true);

	}


	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		formPage = new SpagoBIDeployWizardFormPage(selection);
		addPage(formPage);
		//		treePage = new SpagoBIDeployWizardTreePage(selection);
		//		addPage(treePage);

	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		// Get all selection
		label=formPage.getLabelText().getText();
		name=formPage.getNameText().getText();
		description=formPage.getDescriptionText().getText();
		type=formPage.getTypeLabel();

		int selectedEngineIndex=formPage.getEngineCombo().getSelectionIndex();
		if(selectedEngineIndex!=-1){
			labelEngine=formPage.getEngineCombo().getItem(selectedEngineIndex);
		}

		int selectedDataSetIndex=formPage.getDataSetCombo().getSelectionIndex();
		if(selectedDataSetIndex!=-1){
			labelDataSet=formPage.getDataSetCombo().getItem(selectedDataSetIndex);
		}

		int selectedDataSourceIndex=formPage.getDataSourceCombo().getSelectionIndex();
		if(selectedDataSourceIndex!=-1){
			labelDataSource=formPage.getDataSourceCombo().getItem(selectedDataSourceIndex);
		}

		int selectedStateIndex=formPage.getStateCombo().getSelectionIndex();
		if(selectedStateIndex!=-1){
			labelState=formPage.getStateCombo().getItem(selectedStateIndex);
		}

		refreshSeconds=formPage.getRefreshSecondsSpinner().getSelection();

		TreeItem[] selectedItems=formPage.getTree().getSelection();

		if(selectedItems==null || selectedItems.length!=1){
			SpagoBILogger.errorLog("Error; no item or multiple items selected",null);		
		}
		else{	
			TreeItem selectedItem=selectedItems[0];
			Object docObject=selectedItem.getData();
			functionality = (Functionality)docObject;
			doFinish(); 

		}

		return true;
	}

	/** The worker method. Download the template and creates the file
	 * 
	 * @param document: the SdkDocument refderencing the BiObject
	 * @throws CoreException 
	 * 
	 */

	private void doFinish() {


		// ************ BUILD THE TEMPLATE ************

		// go on only if you selected a document
		Object objSel = selection.toList().get(0);
		org.eclipse.core.internal.resources.File fileSel = null;		
		try{
			fileSel=(org.eclipse.core.internal.resources.File)objSel;
		}
		catch (Exception e) {
			SpagoBILogger.warningLog("No file selected",e);
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Not a file", "You must select a file to deploy");		
			return;
		}

		URI uri=fileSel.getLocationURI();
		projectName = fileSel.getProject().getName();

		SpagoBIServerObjects proxyServerObjects = null;
		try{
			proxyServerObjects = new SpagoBIServerObjects(projectName);
		}
		catch (NoActiveServerException e1) {
			SpagoBILogger.errorLog("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return;
		}

		// ********** CREATE THE NEW DOCUMENT ************
		Document newDocument = new Document();
		newDocument.setLabel(label);
		newDocument.setName(name);
		newDocument.setDescription(description);

		if(labelEngine!=null){
			Integer engineId=formPage.getEngineLabelIdMap().get(labelEngine);
			if(engineId!=null){
				newDocument.setEngineId(engineId);		
			}
		}

		if(labelDataSet!=null){
			Integer dataSetId=formPage.getDataSetLabelIdMap().get(labelDataSet);
			if(dataSetId!=null){
				newDocument.setDataSetId(dataSetId);		
			}
		}

		if(labelDataSource!=null){
			Integer dataSourceId=formPage.getDataSourceLabelIdMap().get(labelDataSource);
			if(dataSourceId!=null){
				newDocument.setDataSourceId(dataSourceId);		
			}
		}


		newDocument.setState(labelState);
		newDocument.setType(type);

		Integer functionalityId=functionality.getId();



		File fileJava=new File(uri.getPath()); 
		FileDataSource fileDataSource=new FileDataSource(fileJava);
		DataHandler dataHandler=new DataHandler(fileDataSource);

		Template template=new Template();
		template.setFileName(fileSel.getName());
		template.setContent(dataHandler);

		try {
			Integer returnCode=proxyServerObjects.saveNewDocument(newDocument, template, functionalityId);
			if(returnCode==null){
				SpagoBILogger.errorLog("Error during document deploy: Check that label is not already present", null);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Error during file deploy: label already present");		
				return;
			}
			//			System.out.println(returnCode);
			newDocument.setId(returnCode);
		}  catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with server, cannot deploy document on server", e);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"No comunciation with server", "No comunication with server, cannot deploy document on server");		
			return;

		}

		try{
			// it is important to delete previous metadata!
			fileSel=(org.eclipse.core.internal.resources.File)BiObjectUtilities.setFileMetaData(fileSel,newDocument, newDeployFromOld);
		}
		catch (CoreException e) {
			SpagoBILogger.errorLog("Error while setting meta data",e);		
			return;
		}
		catch (NoActiveServerException e1) {
			SpagoBILogger.errorLog("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return;
		}

		try{			
			fileSel=(org.eclipse.core.internal.resources.File)BiObjectUtilities.setFileLastRefreshDateMetaData(fileSel);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error while setting last refresh date",e);		
			return;
		}			



		SpagoBILogger.infoLog("Document "+label+" has been deployed");		
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Deploy succesfull", "Document has been deployed");		

	}




	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench _workbench, IStructuredSelection _selection) {
		this.selection = _selection;
		this.workbench=_workbench;

	}


	public boolean isNewDeployFromOld() {
		return newDeployFromOld;
	}


	public void setNewDeployFromOld(boolean newDeployFromOld) {
		this.newDeployFromOld = newDeployFromOld;
	}



}


