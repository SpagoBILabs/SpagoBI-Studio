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
package it.eng.spagobi.studio.documentcomposition.wizards;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.properties.PropertyPage;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;
import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.NewDocumentCompositionWizardPage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;




public class SpagoBIDocumentCompositionWizard extends Wizard implements INewWizard {

	// dashboard creation page
	private NewDocumentCompositionWizardPage newDocumentCompositionWizardPage;
	// workbench selection when the wizard was started
	protected IStructuredSelection selection;
	// the workbench instance
	protected IWorkbench workbench;

	public static final String DOCUMENT_COMPOSITION_INFO_FILE = "it/eng/spagobi/studio/documentcomposition/resources/new_template.doccomp";


	public boolean performFinish() {
		// get the name of the dashboard from the form
		String docCompFileName = newDocumentCompositionWizardPage.getDocCompNameText().getText();
		if (docCompFileName == null || docCompFileName.trim().equals("")) {
			//SpagoBILogger.errorLog("JasperNameEmpty", null);
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Error", "Document Composition name empty");
			return false;
		}

		// get the folder selected:  
		Object objSel = selection.toList().get(0);
		// FolderSel is the folder in wich to insert the new template
		Folder folderSel=(Folder)objSel;

		// get the project
		String projectName = folderSel.getProject().getName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// get the folder where to insert the dashboard document
		IProject project = root.getProject(projectName);

		// generate the byte array input stream used to fill the file
		ByteArrayInputStream bais = null;
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(Activator.PLUGIN_ID);
		String dashboardTemplatePath = null;

		URL res = b.getResource(DOCUMENT_COMPOSITION_INFO_FILE);;
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
				SpagoBILogger.errorLog("Error while closing stream", e);
			}
		}
		// generate the file	       
		IPath pathFolder = folderSel.getProjectRelativePath();
		IPath pathNewFile = pathFolder.append(docCompFileName + ".sbidoccomp");
		IFile newFile = project.getFile(pathNewFile);
		try {
			newFile.create(bais, true, null);
		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error while creating file", e);
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

		try {
			newFile.setPersistentProperty(SpagoBIStudioConstants.MADE_WITH_STUDIO, (new Date()).toString());
		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error while setting made with studio meatdata", e);
		}
		
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New Jasper template creation");
		this.workbench = workbench;
		this.selection = selection;
	}


	public void addPages() {
		super.addPages();
		newDocumentCompositionWizardPage = new NewDocumentCompositionWizardPage("New Document Composed");
		addPage(newDocumentCompositionWizardPage);
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
