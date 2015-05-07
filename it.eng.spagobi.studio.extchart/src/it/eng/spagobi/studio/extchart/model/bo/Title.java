/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.model.bo;

public class Title {

	public static final String TEXT = "text";
	public static final String STYLE = "style";
	
	private String text;
	private StyleTitle style;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public StyleTitle getStyle() {
		if(style == null) style = new StyleTitle();
		return style;
	}
	public void setStyle(StyleTitle style) {
		this.style = style;
	}
	
	
	
}
