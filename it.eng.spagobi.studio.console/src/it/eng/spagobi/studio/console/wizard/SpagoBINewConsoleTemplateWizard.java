/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.console.wizard;


import it.eng.spagobi.studio.console.Activator;
import it.eng.spagobi.studio.console.model.bo.Chart;
import it.eng.spagobi.studio.console.model.bo.ConsoleTemplateModel;
import it.eng.spagobi.studio.console.model.bo.DatasetElement;
import it.eng.spagobi.studio.console.model.bo.JsonTemplateGenerator;
import it.eng.spagobi.studio.console.model.bo.LayoutManagerConfig;
import it.eng.spagobi.studio.console.model.bo.SummaryPanel;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElement;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementLiveLine;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementSemaphore;
import it.eng.spagobi.studio.console.wizard.pages.NewConsoleTemplateWizardPage;
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

public class SpagoBINewConsoleTemplateWizard extends AbstractSpagoBIDocumentWizard {

	// chart creation page
	private NewConsoleTemplateWizardPage newConsoleTemplateWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;
	// workbench selection when the wizard was started
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewConsoleTemplateWizard.class);


	private boolean calledFromMenu = false;

	public boolean performFinish() {
		// get the name of the dashboard from the form
		logger.debug("IN");
		String chartFileName = newConsoleTemplateWizardPage.getTemplateNameText().getText();


		
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
		IPath pathNewFile = pathFolder.append(chartFileName + "." + SpagoBIStudioConstants.CONSOLE_TEMPLATE_EXTENSION);
		IFile newFile = project.getFile(pathNewFile);
		//Serialize template on file
		try {
			ConsoleTemplateModel consoleTemplateModel = new ConsoleTemplateModel();
			String toWrite = JsonTemplateGenerator.transformToJson(consoleTemplateModel);
			byte[] bytes=toWrite.getBytes();
			InputStream inputStream=new ByteArrayInputStream(bytes);
			newFile.create(inputStream, true, null);
		} catch (CoreException e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(newConsoleTemplateWizardPage.getShell(), 
					"Error", "File Name already present in Workspace");
			return false;			
		}
		catch (Exception e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(newConsoleTemplateWizardPage.getShell(), 
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
		setWindowTitle("New Console template creation");
		this.workbench = workbench;
		this.selection = selection;
	}

	public void addPages() {
		logger.debug("IN");
		super.addPages();
		newConsoleTemplateWizardPage = new NewConsoleTemplateWizardPage(workbench, "New Console Template");
		addPage(newConsoleTemplateWizardPage);

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
