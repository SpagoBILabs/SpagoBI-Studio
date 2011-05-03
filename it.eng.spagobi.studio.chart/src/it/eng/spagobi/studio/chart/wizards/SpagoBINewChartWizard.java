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
package it.eng.spagobi.studio.chart.wizards;

import it.eng.spagobi.studio.chart.Activator;
import it.eng.spagobi.studio.chart.wizards.pages.NewChartWizardPage;
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

public class SpagoBINewChartWizard extends Wizard implements INewWizard {

	// chart creation page
	private NewChartWizardPage newChartWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;
	// workbench selection when the wizard was started
	protected IStructuredSelection selection;
	// the workbench instance
	protected IWorkbench workbench;
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewChartWizard.class);

	private boolean calledFromMenu = false;

	public boolean performFinish() {
		// get the name of the dashboard from the form
		logger.debug("IN");
		String chartFileName = newChartWizardPage.getChartNameText().getText();
		if (chartFileName == null || chartFileName.trim().equals("")) {
			logger.error("ChartNameEmpty");
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Chart name empty");
			return false;
		}
		// Get the selected Type
		String typeSelected=newChartWizardPage.getSelectedType();		

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
		// get the folder where to insert the chart document
		IProject project = root.getProject(projectName);

		// generate the byte array input stream used to fill the file
		ByteArrayInputStream bais = null;
		Bundle b = Platform.getBundle(Activator.PLUGIN_ID);



		// generate the file	       
		IPath pathFolder = folderSel.getProjectRelativePath();
		IPath pathNewFile = pathFolder.append(chartFileName + ".sbichart");
		IFile newFile = project.getFile(pathNewFile);
		try {
			String toWrite="<"+typeSelected.toUpperCase()+" name='"+chartFileName+"' />";
			byte[] bytes=toWrite.getBytes();
			InputStream inputStream=new ByteArrayInputStream(bytes);
			newFile.create(inputStream, true, null);
		} catch (CoreException e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "File Name already present in Workspace");
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
		newChartWizardPage = new NewChartWizardPage(workbench, "New Dashboard");
		addPage(newChartWizardPage);

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
