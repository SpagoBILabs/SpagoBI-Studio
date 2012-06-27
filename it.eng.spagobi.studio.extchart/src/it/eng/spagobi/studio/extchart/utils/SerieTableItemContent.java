/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.model.bo.Series;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;

public class SerieTableItemContent {


	Button  customButton;
	Button deleteButton;
	CCombo positionCombo;
	
	Series serie;

	public Button getCustomButton() {
		return customButton;
	}

	public void setCustomButton(Button customButton) {
		this.customButton = customButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}

	public CCombo getPositionCombo() {
		return positionCombo;
	}

	public void setPositionCombo(CCombo positionCombo) {
		this.positionCombo = positionCombo;
	}

	public Series getSerie() {
		return serie;
	}

	public void setSerie(Series serie) {
		this.serie = serie;
	}
	
	
	
}
