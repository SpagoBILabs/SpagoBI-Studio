/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.wizards.deployOlapWizard;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.BiObjectUtilities;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;

import java.io.File;
import java.net.URI;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBIDeployOlapTemplateWizard extends AbstractSpagoBIDocumentWizard  {
	private SpagoBIDeployOlapTemplateWizardFormPage formPage;

	String projectName = null;

	private static Logger logger = LoggerFactory.getLogger(SpagoBIDeployOlapTemplateWizard.class);

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
	public SpagoBIDeployOlapTemplateWizard() {
		super();
		setNeedsProgressMonitor(true);

	}


	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		formPage = new SpagoBIDeployOlapTemplateWizardFormPage(selection);
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
		name=formPage.getNameText().getText();
		description=formPage.getDescriptionText().getText();
		type=formPage.getTypeLabel();



		int selectedDataSourceIndex=formPage.getDataSourceCombo().getSelectionIndex();
		if(selectedDataSourceIndex!=-1){
			labelDataSource=formPage.getDataSourceCombo().getItem(selectedDataSourceIndex);
		}



		doFinish(); 

		

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

		SpagoBIServerObjectsFactory proxyServerObjects = null;
		try{
			proxyServerObjects = new SpagoBIServerObjectsFactory(projectName);
		}
		catch (NoActiveServerException e1) {
			SpagoBILogger.errorLog("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return;
		}

		// ********** CREATE THE NEW DOCUMENT ************
		Document newDocument = new Document();
		newDocument.setName(name);
		newDocument.setLabel(name);
		newDocument.setDescription(description);

		if(labelDataSource!=null){
			Integer dataSourceId=formPage.getDataSourceLabelIdMap().get(labelDataSource);
			if(dataSourceId!=null){
				newDocument.setDataSourceId(dataSourceId);		
			}
		}

		File fileJava=new File(uri.getPath()); 
		FileDataSource fileDataSource=new FileDataSource(fileJava);
		DataHandler dataHandler=new DataHandler(fileDataSource);

		Template template=new Template();
		template.setFileName(fileSel.getName());
		template.setContent(dataHandler);

		try {
			boolean goOn = true;

			// check if document is already present
			
			Document existingDoc = proxyServerObjects.getServerDocuments().getDocumentByLabel(newDocument.getLabel());

			if(existingDoc != null){
				logger.debug("Found existing document with label "+newDocument.getLabel()+": ask for update ");
				goOn = MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Overwrite?", "A document with label "+newDocument.getLabel()+" is already existing; do you want to overwrite it?");
			}
			else{
				logger.debug("Not Found existing document with label "+newDocument.getLabel()+": go on with insert.");				
			}
			 
			if(goOn){
				proxyServerObjects.getServerDocuments().uploadMondrianSchema(newDocument, template, labelDataSource);
				SpagoBILogger.infoLog("Document "+label+" has been deployed");	
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Success", "Olap Schema correctly uploaded");
			}
			else{
				logger.debug("User choose not to insert. ");
				return;
			}
		}  

		catch (Exception e) {
			if(e.getClass().toString().equalsIgnoreCase("class it.eng.spagobi.sdk.exceptions.NotAllowedOperationException")){	
				logger.error("Current User has no permission to deploy documents", e);
				MessageDialog.openError(getShell(), "", "Current user has no permission to deploy document");	
			}
			else{
				SpagoBILogger.errorLog("Cannot deploy document on server", e);		
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Cannot deploy document on server", "Cannot deploy document on server: "+e.getClass().toString());		
			}
			return;
			

		}
		/*
		try{
			// it is important to delete previous metadata!
			fileSel=(org.eclipse.core.internal.resources.File)BiObjectUtilities.setFileMetaData(fileSel,newDocument, newDeployFromOld, proxyServerObjects);
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
		 */



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


