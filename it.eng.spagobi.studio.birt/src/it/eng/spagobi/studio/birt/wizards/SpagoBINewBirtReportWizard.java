/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.birt.wizards;

import it.eng.spagobi.studio.birt.Activator;
import it.eng.spagobi.studio.birt.wizards.pages.NewBirtReportWizardPage;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;
import it.eng.spagobi.studio.utils.wizard.wizardPage.WorkbenchProjectTreePage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class SpagoBINewBirtReportWizard extends AbstractSpagoBIDocumentWizard {

	// dashboard creation page
	private NewBirtReportWizardPage newBirtWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;

	private boolean calledFromMenu = false;

	public static final String BIRT_INFO_FILE = "it/eng/spagobi/studio/birt/resources/new_template.rptdesign";
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewBirtReportWizard.class);


	public boolean performFinish() {
		// get the name of the dashboard from the form
		String birtFileName = newBirtWizardPage.getBirtNameText().getText();
		if (birtFileName == null || birtFileName.trim().equals("")) {
			//SpagoBILogger.errorLog("BirtNameEmpty", null);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Birt name empty");
			return false;
		}

		// FolderSel is the folder in wich to insert the new template
		Folder folderSel= null;

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

		URL res = b.getResource(BIRT_INFO_FILE);;
		InputStream is = null;
		try {
			is = res.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			flushFromInputStreamToOutputStream(is, baos, true);
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
		IPath pathNewFile = pathFolder.append(birtFileName + ".rptdesign");
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
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New Birt template creation");
		this.workbench = workbench;
		this.selection = selection;
	}


	public void addPages() {
		logger.debug("IN");
		super.addPages();
		newBirtWizardPage = new NewBirtReportWizardPage("New Birt Report");
		addPage(newBirtWizardPage);

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

	public static void flushFromInputStreamToOutputStream(InputStream is, OutputStream os, 
			boolean closeStreams) throws Exception  {
		try{	
			int c = 0;
			byte[] b = new byte[1024];
			while ((c = is.read(b)) != -1) {
				if (c == 1024)
					os.write(b);
				else
					os.write(b, 0, c);
			}
			os.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (closeStreams) {
				try {
					if (os != null) os.close();
					if (is != null) is.close();
				} catch (IOException e) {
					throw e;
				}

			}
		}
	}

}
