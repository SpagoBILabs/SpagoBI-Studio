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

import it.eng.spagobi.studio.chart.editors.ChartEditor;

import org.eclipse.swt.graphics.RGB;

public class ZRanges {

	String label;
	Double valueLow;
	Double valueHigh;
	RGB	color;
	
	
	
	public ZRanges(String label, Double valueLow, Double valueHigh, RGB color) {
		super();
		this.label = label;
		this.valueLow = valueLow;
		this.valueHigh = valueHigh;
		this.color = color;
	}
	
	
	
	public ZRanges() {
		super();
	}


	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getValueLow() {
		return valueLow;
	}
	public void setValueLow(Double valueLow) {
		this.valueLow = valueLow;
	}
	public Double getValueHigh() {
		return valueHigh;
	}
	public void setValueHigh(Double valueHigh) {
		this.valueHigh = valueHigh;
	}
	public RGB getColor() {
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}
	
	
	public String toXML(){
		String toReturn="";
		toReturn+="<RANGE ";
		if(label!=null){
			toReturn+="label=\""+label+"\" ";	
		}
		if(valueLow!=null){
			toReturn+="value_low=\""+valueLow.toString()+"\" ";	
		}
		if(valueHigh!=null){
			toReturn+="value_high=\""+valueHigh.toString()+"\" ";	
		}
		if(color!=null){
			toReturn+="colour=\""+ChartEditor.convertRGBToHexadecimal(color)+"\" ";	
		}
		toReturn+=" />\n";
		return toReturn;
	}


	
}
