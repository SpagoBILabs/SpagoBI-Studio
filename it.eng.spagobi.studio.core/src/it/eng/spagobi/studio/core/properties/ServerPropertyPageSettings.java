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
