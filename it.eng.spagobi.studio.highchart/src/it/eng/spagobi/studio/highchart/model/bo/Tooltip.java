/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.model.bo;



public class Tooltip {

private Boolean enabled;
private Style style;
private String backgroundColor;
private Boolean crosshairs;
private Boolean shadow;

public Boolean getEnabled() {
	return enabled;
}

public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
}

public Style getStyle() {
	return style;
}

public void setStyle(Style style) {
	this.style = style;
}

public String getBackgroundColor() {
	return backgroundColor;
}

public void setBackgroundColor(String backgroundColor) {
	this.backgroundColor = backgroundColor;
}

public Boolean getCrosshairs() {
	return crosshairs;
}

public void setCrosshairs(Boolean crosshairs) {
	this.crosshairs = crosshairs;
}

public Boolean getShadow() {
	return shadow;
}

public void setShadow(Boolean shadow) {
	this.shadow = shadow;
}



	

}
