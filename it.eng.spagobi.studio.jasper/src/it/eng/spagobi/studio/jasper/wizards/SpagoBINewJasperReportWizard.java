/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.jasper.wizards;



import it.eng.spagobi.studio.jasper.Activator;
import it.eng.spagobi.studio.jasper.wizards.pages.NewJasperReportWizardPage;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class SpagoBINewJasperReportWizard extends AbstractSpagoBIDocumentWizard  {

	// dashboard creation page
	private NewJasperReportWizardPage newJasperWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;
	// workbench selection when the wizard was started
	// the workbench instance
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewJasperReportWizard.class);
	private boolean calledFromMenu = false;
	public static final String JASPER_INFO_FILE = "it/eng/spagobi/studio/jasper/resources/new_template.jrxml";


	public boolean performFinish() {
		logger.debug("IN");
		// get the name of the dashboard from the form
		String jasperFileName = newJasperWizardPage.getJasperNameText().getText();
		if (jasperFileName == null || jasperFileName.trim().equals("")) {
			//SpagoBILogger.errorLog("JasperNameEmpty", null);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Report name empty");
			return false;
		}

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
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(Activator.PLUGIN_ID);
		String dashboardTemplatePath = null;

		URL res = b.getResource(JASPER_INFO_FILE);;
		InputStream is = null;
		try {
			is = res.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.flushFromInputStreamToOutputStream(is, baos, true);
			byte[] resbytes = baos.toByteArray();
			bais = new ByteArrayInputStream(resbytes);
		} catch (Exception e) {
			//SpagoBILogger.errorLog("Error while creating file", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while creating file");
		} finally {
			try {
				if(is!=null) is.close();
			} catch (Exception e) {
//				SpagoBILogger.errorLog("Error while closing stream", e);
//				SpagoBILogger.errorLog("Error while creating file", e);
			}
		}
		// generate the file	       
		IPath pathFolder = folderSel.getProjectRelativePath();
		IPath pathNewFile = pathFolder.append(jasperFileName + ".jrxml");
		IFile newFile = project.getFile(pathNewFile);
		try {
			newFile.create(bais, true, null);
		} catch (CoreException e) {
//			SpagoBILogger.errorLog("Error while creating file", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while creating file; name alreay present");
		}

		//		IWorkbench wb = PlatformUI.getWorkbench();
		//		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		//		IWorkbenchPage page = win.getActivePage();
		//		IEditorRegistry er = wb.getEditorRegistry();
		//		IEditorDescriptor editordesc =  er.getDefaultEditor(newFile.getName());
		//
		//		try {
		//			page.openEditor(new FileEditorInput(newFile), editordesc.getId());
		//		} catch (PartInitException e) {
		//			SpagoBILogger.errorLog("Error while opening editor", e);
		//			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
		//					"Error", "Error while opening editor");
		//		}
		logger.debug("OUT");
		return true;
	}




	public void addPages() {
		logger.debug("IN");
		super.addPages();
		newJasperWizardPage = new NewJasperReportWizardPage("New Jasper Report");
		addPage(newJasperWizardPage);
		

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
