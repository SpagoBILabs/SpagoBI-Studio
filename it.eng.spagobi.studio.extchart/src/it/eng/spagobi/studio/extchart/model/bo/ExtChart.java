/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.model.bo;

import java.util.Vector;

public class ExtChart {

	
	public static final String LEGEND = "legend";
	public static final String TITLE = "title";
	public static final String SUBTITLE = "subTitle";
	public static final String COLORS = "colors";
	public static final String AXES_STYLE = "axesStyle";
	public static final String LABEL_STYLE = "labelsStyle";
	public static final String AXES_LIST = "axesList";
	public static final String SERIES_LIST = "seriesList";
	public static final String DRILL = "drill";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String ANIMATE = "animate";
	public static final String REFRESH_TIME = "refreshTime";
	public static final String SHADOW = "shadow";
	public static final String TYPE = "type";

	
	private Legend legend;
	private Title title;
	private SubTitle subTitle;
	private Colors colors;
	private AxesStyle axesStyle;
	private LabelsStyle labelsStyle;
	private AxesList axesList;
	private SeriesList seriesList;
	private Drill drill;
	private Dataset dataset;
	
	private Integer width;
	private Integer height;
	private Boolean animate;	
	private Integer refreshTime;
	private Boolean shadow;
	
	// configuration one
	private String type; 
	
	
	
	
	public Title getTitle() {
		if(title == null) title = new Title();
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public SubTitle getSubTitle() {
		if(subTitle == null) subTitle = new SubTitle();
		return subTitle;
	}
	public void setSubTitle(SubTitle subTitle) {
		this.subTitle = subTitle;
	}
	public Colors getColors() {
		if(colors == null) colors = new Colors();		
		return colors;
	}
	public void setColors(Colors colors) {
		this.colors = colors;
	}
	public AxesStyle getAxesStyle() {
		if(axesStyle == null) axesStyle = new AxesStyle();
		return axesStyle;
	}
	public void setAxesStyle(AxesStyle axesStyle) {
		this.axesStyle = axesStyle;
	}
	public LabelsStyle getLabelsStyle() {
		if(labelsStyle == null) labelsStyle = new LabelsStyle();
		return labelsStyle;
	}
	public void setLabelsStyle(LabelsStyle labelsStyle) {
		this.labelsStyle = labelsStyle;
	}
	public AxesList getAxesList() {
		if(axesList == null) axesList = new AxesList();
		return axesList;
	}
	public void setAxesList(AxesList axesList) {
		this.axesList = axesList;
	}
	public SeriesList getSeriesList() {
		if(seriesList == null) seriesList = new SeriesList();
		return seriesList;
	}
	public void setSeriesList(SeriesList seriesList) {
		this.seriesList = seriesList;
	}
	public Drill getDrill() {
		if(drill == null) drill = new Drill();
		return drill;
	}
	public void setDrill(Drill drill) {
		this.drill = drill;
	}
	public Legend getLegend() {
		if(legend == null) legend = new Legend();
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Boolean getAnimate() {
		return animate;
	}
	public void setAnimate(Boolean animate) {
		this.animate = animate;
	}
	public Integer getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Integer refreshTime) {
		this.refreshTime = refreshTime;
	}
	public Boolean getShadow() {
		return shadow;
	}
	public void setShadow(Boolean shadow) {
		this.shadow = shadow;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Dataset getDataset() {
		if(dataset == null ) dataset = new Dataset(); 
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
	
	
	
}
