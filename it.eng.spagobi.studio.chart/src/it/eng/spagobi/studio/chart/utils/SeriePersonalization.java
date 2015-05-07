/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class SeriePersonalization {

	String name;
	String label;
	String draw; // bar o line o line_no_shapes
	RGB color;
	int scale=1;
	
	public static String BAR="bar";
	public static String LINE="line";
	public static String LINE_NO_SHAPES="line_no_shapes";
	
	
	public SeriePersonalization() {
		super();
	}
	
	
	public SeriePersonalization(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public RGB getColor() {
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}


	public int getScale() {
		return scale;
	}


	public void setScale(int scale) {
		this.scale = scale;
	}
	
	
	
	
}
