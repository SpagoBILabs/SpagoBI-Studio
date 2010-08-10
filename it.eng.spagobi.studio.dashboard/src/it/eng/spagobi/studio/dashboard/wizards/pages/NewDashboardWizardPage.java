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
