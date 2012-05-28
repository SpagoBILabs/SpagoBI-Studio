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
package it.eng.spagobi.studio.extchart.wizard;

import it.eng.spagobi.studio.extchart.Activator;
import it.eng.spagobi.studio.extchart.model.XmlTemplateGenerator;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.wizard.pages.NewExtChartWizardPage;
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

public class SpagoBINewExtChartWizard extends AbstractSpagoBIDocumentWizard {

	// chart creation page
	private NewExtChartWizardPage newExtChartWizardPage;
	private WorkbenchProjectTreePage workbenchProjectTreePage;
	// workbench selection when the wizard was started
	private static Logger logger = LoggerFactory.getLogger(SpagoBINewExtChartWizard.class);


	private boolean calledFromMenu = false;

	public boolean performFinish() {
		// get the name of the dashboard from the form
		logger.debug("IN");
		String chartFileName = newExtChartWizardPage.getChartNameText().getText();

		// Get the selected Type
		String typeSelected=newExtChartWizardPage.getSelectedType();		

		logger.debug("Type selected is "+typeSelected);
		
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
		IPath pathNewFile = pathFolder.append(chartFileName + "." + SpagoBIStudioConstants.EXT_ENGINE_EXTENSION);
		IFile newFile = project.getFile(pathNewFile);
		try {
			ExtChart extChart = new ExtChart();
			extChart.setType(typeSelected);
			String toWrite = XmlTemplateGenerator.transformToXml(extChart);			
			byte[] bytes=toWrite.getBytes();
			InputStream inputStream=new ByteArrayInputStream(bytes);
			newFile.create(inputStream, true, null);
		} catch (CoreException e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(newExtChartWizardPage.getShell(), 
					"Error", "File Name already present in Workspace");
			return false;			
		}
		catch (Exception e1) {
			logger.error("Error while creating file", e1);
			MessageDialog.openError(newExtChartWizardPage.getShell(), 
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
		newExtChartWizardPage = new NewExtChartWizardPage(workbench, "New Ext Chart");
		addPage(newExtChartWizardPage);

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
