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
