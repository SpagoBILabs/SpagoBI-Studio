/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.model.bo;



public class Title {

private String text;
private String align;
private Boolean floating;
private Integer margin;
private String style;
private String verticalAlign;
private Integer x;
private Integer y;

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public String getAlign() {
	return align;
}

public void setAlign(String align) {
	this.align = align;
}

public Boolean isFloating() {
	return floating;
}

public void setFloating(Boolean floating) {
	this.floating = floating;
}

public Integer getMargin() {
	return margin;
}

public void setMargin(Integer margin) {
	this.margin = margin;
}

public String getStyle() {
	return style;
}

public void setStyle(String style) {
	this.style = style;
}

public String getVerticalAlign() {
	return verticalAlign;
}

public void setVerticalAlign(String verticalAlign) {
	this.verticalAlign = verticalAlign;
}

public Integer getX() {
	return x;
}

public void setX(Integer x) {
	this.x = x;
}

public Integer getY() {
	return y;
}

public void setY(Integer y) {
	this.y = y;
}

}
