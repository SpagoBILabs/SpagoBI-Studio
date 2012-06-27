/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.wizards.pages;

import it.eng.spagobi.studio.dashboard.editors.model.dashboard.DashboardModel;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewDashboardWizardPage extends WizardPage {

	Text dashboardNameText;
	Combo dashboardTypeCombo;
	
	public NewDashboardWizardPage(String pageName) {
		super(pageName);
		setTitle("New Dashboard ...");
	}

	public void createControl(Composite parent) {
		
		Shell shell = parent.getShell();
		
		Composite composite =  new Composite(parent, SWT.BORDER);
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);
		new Label(composite, SWT.NONE).setText("Name:");				
		dashboardNameText = new Text(composite, SWT.BORDER);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol - 1;
		dashboardNameText.setLayoutData(gd);
		
		new Label(composite, SWT.NONE).setText("Type:");
		dashboardTypeCombo = new Combo(composite, SWT.NONE | SWT.READ_ONLY);
		List dashboardTypes = null;
		try {
			dashboardTypes = DashboardModel.getConfiguredDashboardTypes();
		} catch (Exception e) {
			MessageDialog.openInformation(shell, "Error", e.getMessage());
		}
		if (dashboardTypes == null || dashboardTypes.size() == 0) {
			MessageDialog.openInformation(shell, "Error", "No dashboard configured");
		}
		if (dashboardTypes != null) {
			for (int i = 0; i < dashboardTypes.size(); i++) {
				String aDashboardType = (String) dashboardTypes.get(i);
				dashboardTypeCombo.add(aDashboardType);
			}
		}
		setControl(composite);

	}
	
	public Text getDashboardNameText() {
		return dashboardNameText;
	}

	public Combo getDashboardTypeCombo() {
		return dashboardTypeCombo;
	}
	
}
