/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.editors.model.dashboard;

public class Parameter {
	
	public static final int STRING_TYPE = 0;
	public static final int NUMBER_TYPE = 1;
	public static final int COLOR_TYPE = 2;
	public static final int BOOLEAN_TYPE = 3;
	
	private String name;
	private String value;
	private String description;
	private int type;
	public Parameter () {}
	public Parameter (String name, String value, String description, int type) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
