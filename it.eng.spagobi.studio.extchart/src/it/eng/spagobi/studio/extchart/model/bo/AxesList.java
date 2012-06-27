/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.model.bo;

import java.util.Vector;

public class AxesList {

	public static final String AXES = "axes";
	
	private Vector<Axes> axes;

	public Vector<Axes> getAxes() {
		if(axes == null) axes = new Vector<Axes>();
		return axes;
	}

	public void setAxes(Vector<Axes> axes) {
		this.axes = axes;
	}
	
	
	
}
