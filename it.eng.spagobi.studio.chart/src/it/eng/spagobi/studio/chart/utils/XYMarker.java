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
import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.swt.graphics.RGB;

public class XYMarker {

	String label;
	Integer valueStartInt;
	Integer valueEndInt;
	Double valueMarker;
	Integer colorInt;
	RGB color;

	public XYMarker(String label, Integer valueStartInt, Integer valueEndInt,
			Double valueMarker, Integer colorInt, RGB color) {
		super();
		this.label = label;
		this.valueStartInt = valueStartInt;
		this.valueEndInt = valueEndInt;
		this.valueMarker = valueMarker;
		this.colorInt = colorInt;
		this.color = color;
	}


	public void fillMarkerConfigurations(Node node){
		if(node!=null){
			String xLabel=node.valueOf("@label"); 
			if(xLabel!=null){
				label=xLabel;
			}

			String valueStartIntS=node.valueOf("@value_start_int"); 
			if(valueStartIntS!=null){
				Integer valI=null;
				try{
					valI=Integer.valueOf(valueStartIntS);
				}
				catch (Exception e) {
				valI=0;
				}

				valueStartInt=valI;
			}

			String valueEndIntS=node.valueOf("@value_end_int"); 
			if(valueEndIntS!=null){
				Integer valI=null;
				try{
					valI=Integer.valueOf(valueEndIntS);
				}
				catch (Exception e) {
				valI=0;
				}

				valueEndInt=valI;
			}

			String valueMarkerS=node.valueOf("@value_marker"); 
			if(valueMarkerS!=null){
				Double valD=null;
				try{
					valD=Double.valueOf(valueMarkerS);
				}
				catch (Exception e) {
				valD=0.0;
				}

				valueMarker=valD;
			}

			String colorIntS=node.valueOf("@color_int"); 
			if(colorIntS!=null){
				Integer valI=null;
				try{
					valI=Integer.valueOf(colorIntS);
				}
				catch (Exception e) {
				valI=0;
				}

				colorInt=valI;
			}

			String colorS=node.valueOf("@color"); 
			if(colorIntS!=null){
				RGB val=null;
				try{
					val=ChartEditor.convertHexadecimalToRGB(colorS);
				}
				catch (Exception e) {
				}

				color=val;
			}
		}
		
	}



		public String toXML(String name){
			String toReturn="";	
			if(label!=null || valueStartInt!=null || valueEndInt!=null || colorInt!=null || color!=null || valueMarker!=null){
				toReturn+="<PARAMETER name=\""+name+"\" ";
				if(label!=null){
					toReturn+="label=\""+label+"\" ";
				}
				if(valueStartInt!=null){
					toReturn+="value_start_int=\""+valueStartInt.toString()+"\" ";
				}
				if(valueEndInt!=null){
					toReturn+="value_end_int=\""+valueEndInt.toString()+"\" ";
				}
				if(colorInt!=null){
					toReturn+="color_int=\""+colorInt.toString()+"\" ";
				}
				if(color!=null){
					toReturn+="color=\""+ChartEditor.convertRGBToHexadecimal(color)+"\" ";
				}
				if(valueMarker!=null){
					toReturn+="value_marker=\""+valueMarker.toString()+"\" ";
				}
				toReturn+=" />\n";
			}

			return toReturn;
		}



	




	public Integer getColorInt() {
		return colorInt;
	}
	public void setColorInt(Integer colorInt) {
		this.colorInt = colorInt;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getValueStartInt() {
		return valueStartInt;
	}
	public void setValueStartInt(Integer valueStartInt) {
		this.valueStartInt = valueStartInt;
	}
	public Integer getValueEndInt() {
		return valueEndInt;
	}
	public void setValueEndInt(Integer valueEndInt) {
		this.valueEndInt = valueEndInt;
	}
	public Double getValueMarker() {
		return valueMarker;
	}
	public void setValueMarker(Double valueMarker) {
		this.valueMarker = valueMarker;
	}
	public RGB getColor() {
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}
	public XYMarker() {
		super();
	}




}
