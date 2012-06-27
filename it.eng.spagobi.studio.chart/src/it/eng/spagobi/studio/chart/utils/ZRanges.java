/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
