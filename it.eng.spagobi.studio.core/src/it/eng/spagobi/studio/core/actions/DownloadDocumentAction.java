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
package it.eng.spagobi.studio.core.actions;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.wizards.downloadWizard.SpagoBIDownloadWizard;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

/** DownloadDocumentAction calls the SpagoBI Download Wizard
 * 
 */

public class DownloadDocumentAction implements IViewActionDelegate {

	private IViewPart view = null;

	public DownloadDocumentAction() {
	}

	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {
		SpagoBIDownloadWizard sbindw = new SpagoBIDownloadWizard();
		CommonViewer commViewer=((CommonNavigator) view).getCommonViewer();
		IStructuredSelection sel=(IStructuredSelection)commViewer.getSelection();

		// go on only if you selected a folder
		Object objSel = sel.toList().get(0);
		Folder folderSel = null;		
		try{
			folderSel=(Folder)objSel;
		}
		catch (Exception e) {
			SpagoBILogger.warningLog("No folder selected");					
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Not a folder", "You must select a folder in wich to download the template");		
			return;
		}

		// init wizard
		sbindw.init(PlatformUI.getWorkbench(), sel);
		// Create the wizard dialog
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
		// Open the wizard dialog
		dialog.open();	


	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
