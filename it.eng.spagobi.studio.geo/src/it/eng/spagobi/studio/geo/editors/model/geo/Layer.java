/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
