/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.wizard;

import it.eng.spagobi.studio.highchart.Activator;
import it.eng.spagobi.studio.highchart.model.XmlTemplateGenerator;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.wizard.pages.NewHighChartWizardPage;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;
import it.eng.spagobi.studio.utils.wizard.wizardPage.WorkbenchProjectTreePage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBINewHighChartWizard extends AbstractSpagoBIDocumentWizard {

	// chart creation page
	private NewHighChartWizardPage newHighChartWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;
	// workbench selection when the wizard was started
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewHighChartWizard.class);


	public static final String DOCUMENT_COMPOSITION_INFO_FILE = "it/eng/spagobi/studio/highchart/configuration/highchart.sbihchart";


	private boolean calledFromMenu = false;

	public boolean performFinish() {
		// get the name of the dashboard from the form
		logger.debug("IN");
		String chartFileName = newHighChartWizardPage.getChartNameText().getText();

		// Get the selected Type
		String typeSelected=newHighChartWizardPage.getSelectedType();		

		// get the folder selected, if from context menu is from navigator tree, else is from project tree
		Folder folderSel = null;

		if(calledFromMenu){
			Tree tree =workbenchProjectTreePage.getTree();
			TreeItem[] item = tree.getSelection();
			TreeItem selected = item[0];
			IFolder folder= workbenchProjectTreePage.getItemFolderMap().get(selected.getText());
			folderSel = (Folder)folder;
		}
		else {
			// get the folder selected:  
			Object objSel = selection.toList().get(0);
			folderSel=(Folder)objSel;

		}
		logger.debug("Save in "+folderSel.getName());

		// get the project
		String projectName = folderSel.getProject().getName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// get the folder where to insert the chart document
		IProject project = root.getProject(projectName);

		// generate the byte array input stream used to fill the file
		ByteArrayInputStream bais = null;
		Bundle b = Platform.getBundle(Activator.PLUGIN_ID);

		// generate the file	       
		IPath pathFolder = folderSel.getProjectRelativePath();
		IPath pathNewFile = pathFolder.append(chartFileName + "." + SpagoBIStudioConstants.HIGHCHART_ENGINE_EXTENSION);
		IFile newFile = project.getFile(pathNewFile);
		try {
			HighChart highChart = new HighChart();
			highChart.getChart().setDefaultSeriesType(typeSelected);
			String toWrite = XmlTemplateGenerator.transformToXml(highChart);			
			byte[] bytes=toWrite.getBytes();
			InputStream inputStream=new ByteArrayInputStream(bytes);
			newFile.create(inputStream, true, null);
		} catch (CoreException e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(newHighChartWizardPage.getShell(), 
					"Error", "File Name already present in Workspace");
			return false;			
		}
		catch (Exception e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(newHighChartWizardPage.getShell(), 
					"Error", "Error in reading the template");
			return false;			
		}


		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		IEditorRegistry er = wb.getEditorRegistry();
		IEditorDescriptor editordesc =  er.getDefaultEditor(newFile.getName());

		try {
			page.openEditor(new FileEditorInput(newFile), editordesc.getId());
		} catch (PartInitException e) {
			logger.error("Error while opening editor", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while opening editor");
			return false;
		}
		logger.debug("OUT");
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New Chart template creation");
		this.workbench = workbench;
		this.selection = selection;
	}

	public void addPages() {
		logger.debug("IN");
		super.addPages();
		newHighChartWizardPage = new NewHighChartWizardPage(workbench, "New High Chart");
		addPage(newHighChartWizardPage);

		if(calledFromMenu == true){
			logger.debug("wizard has been called by workbench menu, page for folder selection must be added");
			workbenchProjectTreePage = new WorkbenchProjectTreePage("Page Name", selection);
			addPage(workbenchProjectTreePage);
		}

		logger.debug("OUT");

	}

	public boolean isCalledFromMenu() {
		return calledFromMenu;
	}

	public void setCalledFromMenu(boolean calledFromMenu) {
		this.calledFromMenu = calledFromMenu;
	}




}
