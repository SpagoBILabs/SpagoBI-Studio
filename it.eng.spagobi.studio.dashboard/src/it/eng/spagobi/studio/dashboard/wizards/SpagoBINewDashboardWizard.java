/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.wizards;

import it.eng.spagobi.studio.dashboard.Activator;
import it.eng.spagobi.studio.dashboard.editors.model.dashboard.DashboardModel;
import it.eng.spagobi.studio.dashboard.utils.GeneralUtils;
import it.eng.spagobi.studio.dashboard.wizards.pages.NewDashboardWizardPage;
import it.eng.spagobi.studio.utils.util.IOUtilities;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;
import it.eng.spagobi.studio.utils.wizard.wizardPage.WorkbenchProjectTreePage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

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
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBINewDashboardWizard extends AbstractSpagoBIDocumentWizard {

	// dashboard creation page
	private NewDashboardWizardPage newDashboardWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;
	// workbench selection when the wizard was started

	private boolean calledFromMenu = false;

	
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewDashboardWizard.class);

	
	
	public boolean performFinish() {
		// get the name of the dashboard from the form
		logger.debug("IN");
		String dashboardFileName = newDashboardWizardPage.getDashboardNameText().getText();
		if (dashboardFileName == null || dashboardFileName.trim().equals("")) {
			logger.error("DashboardNameEmpty");
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Dashboard name empty");
			return false;
		}
		String dashboardType = newDashboardWizardPage.getDashboardTypeCombo().getText();
		if (dashboardType == null || dashboardType.trim().equals("")) {
			logger.error("Dashboard Type Not Set" );
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Dashboard type not set");
			return false;
		}

		// get the folder selected:  
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
			// FolderSel is the folder in wich to insert the new template
			folderSel=(Folder)objSel;

		}
		
		logger.debug("Save in "+folderSel.getName());
		
		// get the project
		String projectName = folderSel.getProject().getName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// get the folder where to insert the dashboard document
		IProject project = root.getProject(projectName);
	
		// generate the byte array input stream used to fill the file
		ByteArrayInputStream bais = null;
		Bundle b = Platform.getBundle(Activator.PLUGIN_ID);
		String dashboardTemplatePath = null;
		try {
			dashboardTemplatePath = DashboardModel.getDashboardTemplatePath(dashboardType);
		} catch (Exception e) {
			logger.error("Error", e);			
			MessageDialog.openInformation(getShell(), "Error", e.getMessage());
			return true;
		}
		if (dashboardTemplatePath == null || dashboardTemplatePath.trim().equals("")) {
			logger.error("Missing template path for dashboard " + dashboardType);
			MessageDialog.openInformation(getShell(), 
					"Error", "Missing template path for dashboard " + dashboardType);
			return true;
		}
		URL res = b.getResource(dashboardTemplatePath);;
		InputStream is = null;
		try {
			is = res.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.flushFromInputStreamToOutputStream(is, baos, true);
			byte[] resbytes = baos.toByteArray();
			bais = new ByteArrayInputStream(resbytes);
		} catch (Exception e) {
			logger.error("Error while creating file", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while creating file");
		} finally {
			try {
				if(is!=null) is.close();
			} catch (Exception e) {
				logger.error("Error while closing stream", e);
				logger.error("Error while creating file", e);
			}
		}
		// generate the file	       
		IPath pathFolder = folderSel.getProjectRelativePath();
		IPath pathNewFile = pathFolder.append(dashboardFileName + ".sbidash");
		IFile newFile = project.getFile(pathNewFile);
		try {
			newFile.create(bais, true, null);
		} catch (CoreException e) {
			logger.error("Error while creating file", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while creating file; name alreay present");
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
		}
		logger.debug("Open the dashboard wizard");
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New Dashboard template creation");
		this.workbench = workbench;
		this.selection = selection;
	}

	public void addPages() {
		logger.debug("IN");
		super.addPages();
		newDashboardWizardPage = new NewDashboardWizardPage("New Dashboard");
		addPage(newDashboardWizardPage);
		
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
