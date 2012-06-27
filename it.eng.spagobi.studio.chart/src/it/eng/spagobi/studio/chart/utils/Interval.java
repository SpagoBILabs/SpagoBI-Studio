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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Interval {

	RGB color=null;
	Double min=null;
	Double max=null;
	String label=null;
	private static Logger logger = LoggerFactory.getLogger(Interval.class);


	public RGB getColor() {
		return color;
	}




	public void setColor(RGB color) {
		this.color = color;
	}




	public Double getMin() {
		return min;
	}




	public void setMin(Double min) {
		this.min = min;
	}




	public Double getMax() {
		return max;
	}




	public void setMax(Double max) {
		this.max = max;
	}




	public String getLabel() {
		return label;
	}




	public void setLabel(String label) {
		this.label = label;
	}

	public String toXML(){
		String toReturn="";
		toReturn+="<INTERVAL ";
		if(label!=null){
			toReturn+="label=\""+label+"\" ";	
		}
		if(min!=null){
			toReturn+="min=\""+min.toString()+"\" ";	
		}
		if(max!=null){
			toReturn+="max=\""+max.toString()+"\" ";	
		}
		if(color!=null){
			toReturn+="color=\""+ChartEditor.convertRGBToHexadecimal(color)+"\" ";	
		}
		toReturn+=" />";
		return toReturn;
	}


}
