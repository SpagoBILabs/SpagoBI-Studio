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
package it.eng.spagobi.studio.dashboard.wizards;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.dashboard.Activator;
import it.eng.spagobi.studio.dashboard.editors.model.dashboard.DashboardModel;
import it.eng.spagobi.studio.dashboard.utils.GeneralUtils;
import it.eng.spagobi.studio.dashboard.wizards.pages.NewDashboardWizardPage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
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

public class SpagoBINewDashboardWizard extends Wizard implements INewWizard {

	// dashboard creation page
	private NewDashboardWizardPage newDashboardWizardPage;
	// workbench selection when the wizard was started
	protected IStructuredSelection selection;
	// the workbench instance
	protected IWorkbench workbench;

	
	
	public boolean performFinish() {
		// get the name of the dashboard from the form
		SpagoBILogger.infoLog("Starting dashboard wizard");
		String dashboardFileName = newDashboardWizardPage.getDashboardNameText().getText();
		if (dashboardFileName == null || dashboardFileName.trim().equals("")) {
			SpagoBILogger.errorLog("DashboardNameEmpty", null);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Dashboard name empty");
			return false;
		}
		String dashboardType = newDashboardWizardPage.getDashboardTypeCombo().getText();
		if (dashboardType == null || dashboardType.trim().equals("")) {
			SpagoBILogger.errorLog("Dashboard Type Not Set", null);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Dashboard type not set");
			return false;
		}

		// get the folder selected:  
		Object objSel = selection.toList().get(0);
		Folder folderSel = null;		
		try{
			// FolderSel is the folder in wich to insert the new template
			folderSel=(Folder)objSel;
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("no selected folder", e);			
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "You must select a folder in wich to insert the dashboard");		
		}
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
			SpagoBILogger.errorLog("Error", e);			
			MessageDialog.openInformation(getShell(), "Error", e.getMessage());
			return true;
		}
		if (dashboardTemplatePath == null || dashboardTemplatePath.trim().equals("")) {
			SpagoBILogger.errorLog("Missing template path for dashboard " + dashboardType, null);
			MessageDialog.openInformation(getShell(), 
					"Error", "Missing template path for dashboard " + dashboardType);
			return true;
		}
		URL res = b.getResource(dashboardTemplatePath);;
		InputStream is = null;
		try {
			is = res.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GeneralUtils.flushFromInputStreamToOutputStream(is, baos, true);
			byte[] resbytes = baos.toByteArray();
			bais = new ByteArrayInputStream(resbytes);
		} catch (Exception e) {
			SpagoBILogger.errorLog("Error while creating file", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while creating file");
		} finally {
			try {
				if(is!=null) is.close();
			} catch (Exception e) {
				SpagoBILogger.errorLog("Error while closing stream", e);
				SpagoBILogger.errorLog("Error while creating file", e);
			}
		}
		// generate the file	       
		IPath pathFolder = folderSel.getProjectRelativePath();
		IPath pathNewFile = pathFolder.append(dashboardFileName + ".sbidash");
		IFile newFile = project.getFile(pathNewFile);
		try {
			newFile.create(bais, true, null);
		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error while creating file", e);
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
			SpagoBILogger.errorLog("Error while opening editor", e);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Error while opening editor");
		}
		SpagoBILogger.infoLog("Open the dashboard wizard");
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New Dashboard template creation");
		this.workbench = workbench;
		this.selection = selection;
	}

	public void addPages() {
		super.addPages();
		newDashboardWizardPage = new NewDashboardWizardPage("New Dashboard");
		addPage(newDashboardWizardPage);
	}

}
