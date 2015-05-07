/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.model.bo;

public class StyleSeries {

	public static final String OPACITY = "opacity";
	
	private Float opacity = new Float(1);

	public Float getOpacity() {
		return opacity;
	}

	public void setOpacity(Float opacity) {
		this.opacity = opacity;
	}
	
	
}
