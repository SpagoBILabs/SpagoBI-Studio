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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ModelPropertyPageSettings implements IPropertyPageSettings {

	IFile fileSel = null;

	public String getDescription() {
		return "Model properties";
	}

	public ModelPropertyPageSettings(IFile filSel) {
		super();
		this.fileSel = filSel;
	}

	public Control createContents(Composite contents) {
		// TODO Auto-generated method stub
		return null;
	}

	public String fillValues() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}


}
