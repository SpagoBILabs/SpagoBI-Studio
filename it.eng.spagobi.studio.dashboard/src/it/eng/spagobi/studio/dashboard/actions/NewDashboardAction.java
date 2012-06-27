/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.actions;

import it.eng.spagobi.studio.dashboard.wizards.SpagoBINewDashboardWizard;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

public class NewDashboardAction implements IObjectActionDelegate {

	ISelection selection;
	
	public NewDashboardAction() {
		// TODO Auto-generated constructor stub
	}


	public void run(IAction action) {
		SpagoBINewDashboardWizard sbindw = new SpagoBINewDashboardWizard();
		IStructuredSelection sel=(IStructuredSelection)selection;
   	 	sbindw.init(PlatformUI.getWorkbench(), sel);
   	 	// Create the wizard dialog
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
        // Open the wizard dialog
        dialog.open();
     
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;		
	}

	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}
