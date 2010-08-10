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
