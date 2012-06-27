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

public class Label implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6686644116440080130L;
	private String position;
	private String className;
	private Format format;
	private String text;
	private Vector<GuiParam> params;
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Vector<GuiParam> getParams() {
		return params;
	}
	public void setParams(Vector<GuiParam> params) {
		this.params = params;
	}

}
