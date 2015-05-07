/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.model.bo;



public class PlotOptions {

	private Area area;
	private AreaSpline areaSpline;
	private Pie pie;
	private Bar bar;
	private Spline spline;
	private Scatter scatter;
	private Line line;
	private Series series;
	private Column column;
	
	public Area getArea() {
		if(series == null ) area = new Area();
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Series getSeries() {
		if(series == null ) series = new Series();
		return series;
	}
	public void setSeries(Series series) {
		this.series = series;
	}
	public AreaSpline getAreaSpline() {
		if(areaSpline== null) areaSpline = new AreaSpline();
		return areaSpline;
	}
	public void setAreaSpline(AreaSpline areaSpline) {
		this.areaSpline = areaSpline;
	}
	public Pie getPie() {
		if(pie == null) pie = new Pie();
		return pie;
	}
	public void setPie(Pie pie) {
		this.pie = pie;
	}
	public Bar getBar() {
		if(bar== null) bar = new Bar();
		return bar;
	}
	public void setBar(Bar bar) {
		this.bar = bar;
	}
	public Spline getSpline() {
		if(spline == null) spline = new Spline();
		return spline;
	}
	public void setSpline(Spline spline) {
		this.spline = spline;
	}
	public Scatter getScatter() {
		if(scatter== null) scatter = new Scatter();
		return scatter;
	}
	public void setScatter(Scatter scatter) {
		this.scatter = scatter;
	}
	public Line getLine() {
		if(line == null) line = new Line();
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public Column getColumn() {
		if(column== null) column = new Column();
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}

	

	
}
