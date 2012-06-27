/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PropertiesView extends org.eclipse.ui.part.ViewPart {
	private Label label;
	
	public void setFocus() {
		label.setFocus();
	}
	
	public void createPartControl(Composite parent) {
		label = new Label(parent, 0);
		label.setText("Hello World");
	}

	
	
}
