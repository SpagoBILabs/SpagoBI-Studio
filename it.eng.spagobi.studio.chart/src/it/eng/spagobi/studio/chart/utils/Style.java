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
import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.Parameter;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class Style {
	protected boolean hasFont=true;
	protected boolean hasSize=true;
	protected boolean hasColor=true;
	protected boolean hasOrientation=true;


	protected String name;	
	protected String font;
	protected Integer size;
	protected RGB color;
	protected String orientation;
	protected String tooltip;
	protected String description;

	protected static final String FONT="font";
	protected static final String COLOR="color";
	protected static final String ORIENTATION="orientation";
	protected static final String SIZE="size";

	public static final String HORIZONTAL="HORIZONTAL";
	public static final String VERTICAL="VERTICAL";
	public static final String STYLE_SUBTITLE="STYLE_SUBTITLE";
	
	
	//parameters collector, automatically reloaded by set methods
	//protected ArrayList<Parameter> parameters=new ArrayList<Parameter>();

	public String getFont() {
		return font;

	}
	public void setFont(String font) {
		this.font = font;
		//		reloadParameters(FONT, font);
	}

	//	public void reloadParameters(String parToReload, Object newValue){
	//		for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
	//			Parameter par = (Parameter) iterator.next();
	//			String parName=par.getName();
	//			if(parName.equalsIgnoreCase(parToReload)){
	//				par.setValue(newValue);
	//			}
	//		}
	//		return;
	//	}

	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		//		reloadParameters(SIZE, size);
		this.size = size;
	}
	public RGB getColor() {
		return color;
	}
	public void setColor(RGB color) {
		//		reloadParameters(COLOR, color);
		this.color = color;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		//	reloadParameters(ORIENTATION, orientation);
		this.orientation = orientation;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public Style() {
		super();
	}
	public Style(String font, Integer size, RGB color,
			String orientation) {
		super();
		this.font = font;
		this.size = size;
		this.color = color;
		this.orientation = orientation;
	}

	//	public ArrayList<Parameter> getParameters() {
	//		return parameters;
	//	}
	//	public void setParameters(ArrayList<Parameter> parameters) {
	//		this.parameters = parameters;
	//	}


	public String toXML(ChartModel model) {
		String toReturn="<"+this.name.toUpperCase()+" ";
		if(hasFont==true){
			String fontDef=font!=null?font:"Arial";
			toReturn+="font=\""+fontDef+"\" ";
		}
		if(hasSize==true){
			Integer sizeDef=size!=null ? size: 10;
			toReturn+="size=\""+sizeDef+"\" ";
		}
		if(hasColor==true){
			String colorDef=color!=null? ChartEditor.convertRGBToHexadecimal(color) :"#000000";
			toReturn+="color=\""+colorDef+"\" ";
		}
		if(hasOrientation==true){		
			String orientationDef=orientation!=null? orientation :"vertical";
			toReturn+="orientation=\""+orientationDef+"\" ";
		}
		
		// Only for subtitle add the tag name
		if(this.name.equalsIgnoreCase(STYLE_SUBTITLE)){
			String subTitle=model.getSubTitle();
			if(subTitle==null)subTitle="";
			toReturn+="name=\""+subTitle+"\" ";			
		}
		
		toReturn+="/>\n";
		return toReturn;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public boolean isHasFont() {
		return hasFont;
	}
	public void setHasFont(boolean hasFont) {
		this.hasFont = hasFont;
	}
	public boolean isHasSize() {
		return hasSize;
	}
	public void setHasSize(boolean hasSize) {
		this.hasSize = hasSize;
	}
	public boolean isHasColor() {
		return hasColor;
	}
	public void setHasColor(boolean hasColor) {
		this.hasColor = hasColor;
	}
	public boolean isHasOrientation() {
		return hasOrientation;
	}
	public void setHasOrientation(boolean hasOrientation) {
		this.hasOrientation = hasOrientation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



}
