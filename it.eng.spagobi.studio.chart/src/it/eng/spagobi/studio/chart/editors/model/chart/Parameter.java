/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors.model.chart;

import java.util.ArrayList;

public class Parameter {

	public static final int STRING_TYPE = 0;
	public static final int INTEGER_TYPE = 1;
	public static final int COLOR_TYPE = 2;
	public static final int BOOLEAN_TYPE = 3;
	public static final int COMBO_TYPE = 4;
	public static final int FLOAT_TYPE = 5;

	private String name;
	private Object value;
	private String description;
	private int type;
	private ArrayList<String> predefinedValues;
	private String tooltip;

	public Parameter () {}
	public Parameter (String name, String value, String description, int type) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
	}



	public Parameter(String name, String value, String description, int type,
			ArrayList<String> predefinedValues) {
		super();
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
		this.predefinedValues = predefinedValues;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
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
	public ArrayList<String> getPredefinedValues() {
		return predefinedValues;
	}
	public void setPredefinedValues(ArrayList<String> predefinedValues) {
		this.predefinedValues = predefinedValues;
	}

	public String toXML(){
		// Choose if design second to type
		String toReturn="";
		if(type==STRING_TYPE || type==COMBO_TYPE || type==COLOR_TYPE){
			if(value!=null){
				toReturn="<PARAMETER ";
				toReturn+="name=\""+name+"\" ";
				toReturn+="value=\""+value+"\" ";
				toReturn+="/>\n";
			}
		}
		else if(type==INTEGER_TYPE || type==FLOAT_TYPE){ // if number cannot be empty
			if(value!=null && !value.toString().equals("")){
				toReturn="<PARAMETER ";
				toReturn+="name=\""+name+"\" ";
				toReturn+="value=\""+value+"\" ";
				toReturn+="/>\n";
			}
			if(value.toString().equals("")){
				toReturn="<PARAMETER ";
				toReturn+="name=\""+name+"\" ";
				toReturn+="value=\"0.0\" ";
				toReturn+="/>\n";
				
			}
		}
		else if(type==BOOLEAN_TYPE){ // if boolean and is empty cannot be empty
			if(value==null) value="false";
			if(value.equals("")){
				value="false";
			}
			toReturn="<PARAMETER ";
			toReturn+="name=\""+name+"\" ";
			toReturn+="value=\""+value+"\" ";
			toReturn+="/>\n";
		}

		return toReturn;
	}
	
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


}
