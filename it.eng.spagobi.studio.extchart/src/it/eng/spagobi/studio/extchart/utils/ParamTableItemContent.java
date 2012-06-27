/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.model.bo.Param;
import it.eng.spagobi.studio.extchart.model.bo.Series;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;

public class ParamTableItemContent {


	Button deleteButton;
	Param param;



	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}

	/**
	 * @return the param
	 */
	public Param getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(Param param) {
		this.param = param;
	}



	
	
	
}
