/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.pages;

import org.eclipse.swt.widgets.Composite;
import org.slf4j.LoggerFactory;

public abstract class AbstractPage extends Composite {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractPage.class);
	
	public AbstractPage(Composite parent, int style) {
		super(parent, style);
	}


	
	
}
