/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.services;

import it.eng.spagobi.studio.geo.wizards.SpagoBIGEOWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizardLauncher {

	private static Logger logger = LoggerFactory.getLogger(WizardLauncher.class);

	
	static public void wizardLaunch(){
		logger.debug("IN");
		SpagoBIGEOWizard sbindw = new SpagoBIGEOWizard();
		sbindw.setCalledFromMenu(true);

		// from menu has no selection
		sbindw.init(PlatformUI.getWorkbench(), null);
		// Create the wizard dialog
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
		// Open the wizard dialog
		dialog.open();

		logger.debug("OUT");

	}
	
}
