/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.actions;

import it.eng.spagobi.studio.core.wizards.serverWizard.NewServerWizard;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewServerAction  implements IObjectActionDelegate {

	ISelection selection;
	private static Logger logger = LoggerFactory.getLogger(NewServerAction.class);

	public NewServerAction() {
	}


	public void run(IAction action) {
		logger.debug("IN");
		NewServerWizard sbindw = new NewServerWizard();
		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if you selected a folder
		Object objSel = sel.toList().get(0);
		Folder folderSel = null;		
		folderSel=(Folder)objSel;


		// init wizard
		sbindw.init(PlatformUI.getWorkbench(), sel);
		// Create the wizard dialog
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
		// Open the wizard dialog
		dialog.open();	
		logger.debug("OUT");


	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;		
	}

	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub

	}

}