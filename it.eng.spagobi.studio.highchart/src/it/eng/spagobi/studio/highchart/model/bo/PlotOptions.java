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
