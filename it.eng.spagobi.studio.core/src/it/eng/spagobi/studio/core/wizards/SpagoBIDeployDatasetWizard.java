/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.wizards;


import it.eng.spagobi.studio.core.services.dataset.DeployDatasetService;
import it.eng.spagobi.studio.core.wizards.deployDatasetWizard.SpagoBIDeployDatasetWizardFormPage;
import it.eng.spagobi.studio.core.wizards.deployDatasetWizard.SpagoBIDeployDatasetWizardPivotPage;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.BiObjectUtilities;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBIDeployDatasetWizard extends AbstractSpagoBIDocumentWizard  {
	private SpagoBIDeployDatasetWizardFormPage formPage;
	private SpagoBIDeployDatasetWizardPivotPage pivotPage;

	String projectName = null;
	private static Logger logger = LoggerFactory.getLogger(SpagoBIDeployDatasetWizard.class);

	// boolean tells if file was already previously deployed (delete old metadata).
	boolean newDeployFromOld = false;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SpagoBIDeployDatasetWizard() {
		super();
		setNeedsProgressMonitor(true);

	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		logger.debug("IN");
		formPage = new SpagoBIDeployDatasetWizardFormPage(selection);
		addPage(formPage);
		pivotPage = new SpagoBIDeployDatasetWizardPivotPage(selection);
		addPage(pivotPage);
		logger.debug("OUT");
	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		boolean toReturn = super.canFinish();
// check
		boolean isComplete=true;
		String labelTextS=formPage.getLabelText().getText();
		String nameTextS=formPage.getNameText().getText();
		boolean transform = formPage.getTransformerCheck().getSelection();
		if(labelTextS==null || nameTextS==null || labelTextS.equalsIgnoreCase("") || nameTextS.equalsIgnoreCase("")){
			return false;
		}
		if(transform == true) {
			String rowPivot = pivotPage.getRowPivotText().getText();
			String columnPivot = pivotPage.getColumnPivotText().getText();
			String valuePivot = pivotPage.getValuePivotText().getText();
			if(valuePivot == null || rowPivot==null || nameTextS==null || rowPivot.equalsIgnoreCase("") || columnPivot.equalsIgnoreCase("") || valuePivot.equalsIgnoreCase("")){
				return false;
			}	
			
		}

		return isComplete;

		
	}



	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		logger.debug("IN");


		// ************ BUILD THE TEMPLATE ************

		// go on only if you selected a document
		Object objSel = selection.toList().get(0);
		IFile fileSel=(IFile)objSel;		

		URI uri=fileSel.getLocationURI();
		projectName = fileSel.getProject().getName();

		SpagoBIServerObjectsFactory proxyServerObjects = null;
		try{
			proxyServerObjects = new SpagoBIServerObjectsFactory(projectName);
		}
		catch (NoActiveServerException e1) {
			logger.error("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return false;
		}

		Dataset newDataset = createSelectedDataset();


		try {
			Integer returnCode=proxyServerObjects.getServerDatasets().saveDataSet(newDataset);
			if(returnCode==null){
				logger.error("Error during Data Set deploy: Check that label is not already present, otherwise check server log");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Error during Data Set deploy: check that label is not already present, otherwise check server log for details");		
				return false;
			}
			//			System.out.println(returnCode);
			newDataset.setId(returnCode);
		}  

		catch (Exception e) {
			if(e.getClass().toString().equalsIgnoreCase("class it.eng.spagobi.sdk.exceptions.NotAllowedOperationException")){	
				logger.error("Current User has no permission to save the dataset", e);
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Current user has no permission to save the dataset");	
			}
			else{
				logger.error("No comunication with server, cannot deploy document on server", e);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"No comunciation with server", "No comunication with server, cannot deploy document on server");		
			}
			return false;

		}

		try{
			// it is important to delete previous metadata!
			//fileSel=(org.eclipse.core.internal.resources.File)BiObjectUtilities.setDataSetMetaData(fileSel,newDataset);
			fileSel=(org.eclipse.core.internal.resources.File)BiObjectUtilities.setFileDataSetMetaData(fileSel,newDataset);

		}
		catch (CoreException e) {
			logger.error("Error while setting meta data",e);		
			return false;
		}



		logger.info("Dataset has been deployed");		
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Deploy succesfull", "Dataset has been deployed");		

		logger.debug("OUT");



		return true;
	}


	/**
	 * 
	 */

	public Dataset createSelectedDataset(){
		logger.debug("IN");
		// ********** CREATE THE NEW DATASET ************

		Dataset toReturn = new Dataset();
		// Get all selection
		String label=formPage.getLabelText().getText();
		String name=formPage.getNameText().getText();
		String description=formPage.getDescriptionText().getText();
		String type=formPage.getTypeLabel().getText();
		String datamart = formPage.getDatamartLabel().getText();
		Integer dataSourceId = null;

		String labelDataSource = null;
		int selectedDataSourceIndex=formPage.getDataSourceCombo().getSelectionIndex();
		if(selectedDataSourceIndex!=-1){
			labelDataSource=formPage.getDataSourceCombo().getItem(selectedDataSourceIndex);
		}
		if(labelDataSource!=null){
			dataSourceId=formPage.getDataSourceLabelIdMap().get(labelDataSource);
		}

		boolean transformer = formPage.getTransformerCheck().getSelection();
		String columnPivot = pivotPage.getColumnPivotText().getText();
		String rowPivot = pivotPage.getRowPivotText().getText();
		String valuePivot = pivotPage.getValuePivotText().getText();
		boolean numberedColumnsPivot = pivotPage.getNumberedColumnsPivotCheck().getSelection();

		//		String query = formPage.getQueryText().getText();
		// adapt query to list
		String queryAdapted = DeployDatasetService.adaptQueryToList(formPage.getQuery());


		toReturn = new Dataset();
		toReturn.setLabel(label);
		toReturn.setName(name);
		toReturn.setDescription(description);
		toReturn.setType(type);
		toReturn.setJdbcDataSourceId(dataSourceId);
		if(transformer){
			toReturn.setTransformer("PIVOT_TRANSFOMER");
		}
		toReturn.setPivotColumnName(columnPivot);
		toReturn.setPivotColumnValue(valuePivot);
		toReturn.setPivotRowName(rowPivot);
		toReturn.setNumberingRows(numberedColumnsPivot);
		toReturn.setJsonQuery(queryAdapted);
		toReturn.setDatamarts(datamart);
		logger.debug("OUT");
		return toReturn;
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


