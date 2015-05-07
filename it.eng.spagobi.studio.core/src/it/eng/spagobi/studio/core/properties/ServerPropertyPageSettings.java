/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerPropertyPageSettings implements IPropertyPageSettings {

	private static Logger logger = LoggerFactory.getLogger(ServerPropertyPageSettings.class);
	Composite container = null;
	IFile fileSel = null;
	Group serverGroup = null;

	public String getDescription() {
		return "Server properties";
	}

	public ServerPropertyPageSettings(IFile filSel) {
		super();
		this.fileSel = filSel;
	}


	public Control createContents(Composite _container) {
		container = _container;

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;

		serverGroup = new Group(container, SWT.NULL);
		serverGroup.setText("Server information:");
		serverGroup.setLayout(new FillLayout());
		Composite docContainer = new Composite(serverGroup, SWT.NULL);
		docContainer.setLayout(layout);

		return container;
	}

	public String fillValues() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}



}
