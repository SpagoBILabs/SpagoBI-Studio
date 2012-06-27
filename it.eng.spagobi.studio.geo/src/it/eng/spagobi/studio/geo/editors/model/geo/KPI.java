/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.geo;

import java.io.Serializable;

public class KPI  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4936266761341753956L;
	private String columnId;
	private String description;
	private String aggFunct;
	private String color;
	private Tresholds tresholds;
	private Colours colours;
	
	public Colours getColours() {
		return colours;
	}
	public void setColours(Colours colours) {
		this.colours = colours;
	}
	public Tresholds getTresholds() {
		return tresholds;
	}
	public void setTresholds(Tresholds tresholds) {
		this.tresholds = tresholds;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAggFunct() {
		return aggFunct;
	}
	public void setAggFunct(String aggFunct) {
		this.aggFunct = aggFunct;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
