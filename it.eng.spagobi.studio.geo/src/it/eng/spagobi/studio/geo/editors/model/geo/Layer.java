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

public class Layer  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3332143022971484137L;
	private String name;
	private String description;
	private String selected;
	private String defaultFillColour;
	
	private boolean choosenForTemplate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getDefaultFillColour() {
		return defaultFillColour;
	}
	public void setDefaultFillColour(String defaultFillColour) {
		this.defaultFillColour = defaultFillColour;
	}
	public boolean isChoosenForTemplate() {
		return choosenForTemplate;
	}
	public void setChoosenForTemplate(boolean choosenForTemplate) {
		this.choosenForTemplate = choosenForTemplate;
	}


}
