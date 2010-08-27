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
