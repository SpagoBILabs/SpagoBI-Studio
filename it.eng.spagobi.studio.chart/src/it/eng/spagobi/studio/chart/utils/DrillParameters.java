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
package it.eng.spagobi.studio.chart.utils;

public class DrillParameters {

	String name;
	String value;
	String type=RELATIVE;

	public static final String RELATIVE="RELATIVE";
	public static final String ABSOLUTE="ABSOLUTE";


	public DrillParameters(String name, String value, String type) {
		super();
		this.name = name;
		this.value = value;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String toXml(){
		String toReturn="<PARAM ";
		toReturn+="name=\""+name+"\" ";
		toReturn+="type=\""+type+"\" ";
		if(value!=null){
			toReturn+="value=\""+value+"\" ";
		}
		toReturn+="/>\n";
		return toReturn;
	}


}
