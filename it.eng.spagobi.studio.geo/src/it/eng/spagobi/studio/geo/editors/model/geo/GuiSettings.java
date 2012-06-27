/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.geo;

import java.io.Serializable;
import java.util.Vector;

public class GuiSettings   implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6664124887478223504L;
	private Vector<GuiParam> params;
	private Windows windows;
	private Labels labels;
	
	public Vector<GuiParam> getParams() {
		return params;
	}
	public void setParams(Vector<GuiParam> params) {
		this.params = params;
	}
	public Windows getWindows() {
		return windows;
	}
	public void setWindows(Windows windows) {
		this.windows = windows;
	}
	public Labels getLabels() {
		return labels;
	}
	public void setLabels(Labels labels) {
		this.labels = labels;
	}

}
