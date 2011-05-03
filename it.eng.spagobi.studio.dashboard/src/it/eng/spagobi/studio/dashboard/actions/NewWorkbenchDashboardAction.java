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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewWorkbenchDashboardAction implements IWorkbenchWindowActionDelegate {

	private IViewPart view = null;

	ISelection selection;

	private static Logger logger = LoggerFactory.getLogger(NewWorkbenchDashboardAction.class);

	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {
		logger.debug("IN");
		SpagoBINewDashboardWizard sbindw = new SpagoBINewDashboardWizard();
		sbindw.setCalledFromMenu(true);
		
		IStructuredSelection sel=(IStructuredSelection)selection;

		// from menu has no selection
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
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}



}
