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
package it.eng.spagobi.studio.highchart.model;

import it.eng.spagobi.studio.highchart.model.bo.Area;
import it.eng.spagobi.studio.highchart.model.bo.AreaSpline;
import it.eng.spagobi.studio.highchart.model.bo.Bar;
import it.eng.spagobi.studio.highchart.model.bo.Chart;
import it.eng.spagobi.studio.highchart.model.bo.Column;
import it.eng.spagobi.studio.highchart.model.bo.DataLabels;
import it.eng.spagobi.studio.highchart.model.bo.Drill;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.Legend;
import it.eng.spagobi.studio.highchart.model.bo.Line;
import it.eng.spagobi.studio.highchart.model.bo.Param;
import it.eng.spagobi.studio.highchart.model.bo.ParamList;
import it.eng.spagobi.studio.highchart.model.bo.Pie;
import it.eng.spagobi.studio.highchart.model.bo.PlotOptions;
import it.eng.spagobi.studio.highchart.model.bo.Scatter;
import it.eng.spagobi.studio.highchart.model.bo.Serie;
import it.eng.spagobi.studio.highchart.model.bo.Series;
import it.eng.spagobi.studio.highchart.model.bo.SeriesList;
import it.eng.spagobi.studio.highchart.model.bo.Spline;
import it.eng.spagobi.studio.highchart.model.bo.SubTitle;
import it.eng.spagobi.studio.highchart.model.bo.Title;
import it.eng.spagobi.studio.highchart.model.bo.TitleAxis;
import it.eng.spagobi.studio.highchart.model.bo.XAxis;
import it.eng.spagobi.studio.highchart.model.bo.YAxis;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class XmlTemplateGenerator {

	private static Logger logger = LoggerFactory.getLogger(XmlTemplateGenerator.class);


	public static void setAlias(XStream xstream){
		xstream.alias("HIGHCHART", HighChart.class);
		
		
		xstream.useAttributeFor(HighChart.class, "width");
		xstream.useAttributeFor(HighChart.class, "height");
		xstream.aliasField("width", HighChart.class, "width");
		xstream.aliasField("height", HighChart.class, "height");

		xstream.aliasField("CHART", HighChart.class, "chart"); 

		xstream.useAttributeFor(Chart.class, "alignTicks");
		xstream.aliasField("alignTicks", Chart.class, "alignTicks");
		xstream.useAttributeFor(Chart.class, "animation");
		xstream.aliasField("animation", Chart.class, "animation");
		xstream.useAttributeFor(Chart.class, "backgroundColor");
		xstream.aliasField("backgroundColor", Chart.class, "backgroundColor");
		xstream.useAttributeFor(Chart.class, "borderColor");
		xstream.aliasField("backgroundColor", Chart.class, "borderColor");
		xstream.useAttributeFor(Chart.class, "borderRadius");
		xstream.aliasField("borderRadius", Chart.class, "borderRadius");
		xstream.useAttributeFor(Chart.class, "borderWidth");
		xstream.aliasField("borderWidth", Chart.class, "borderWidth");
		xstream.useAttributeFor(Chart.class, "className");
		xstream.aliasField("className", Chart.class, "className");
		xstream.useAttributeFor(Chart.class, "defaultSeriesType");
		xstream.aliasField("defaultSeriesType", Chart.class, "defaultSeriesType");
		xstream.useAttributeFor(Chart.class, "height");
		xstream.aliasField("height", Chart.class, "height");
		xstream.useAttributeFor(Chart.class, "ignoreHiddenSeries");
		xstream.aliasField("ignoreHiddenSeries", Chart.class, "ignoreHiddenSeries");
		xstream.useAttributeFor(Chart.class, "inverted");
		xstream.aliasField("inverted", Chart.class, "inverted");
		xstream.useAttributeFor(Chart.class, "margin");
		xstream.aliasField("margin", Chart.class, "margin");
		xstream.useAttributeFor(Chart.class, "marginTop");
		xstream.aliasField("marginTop", Chart.class, "marginTop");
		xstream.useAttributeFor(Chart.class, "marginRight");
		xstream.aliasField("marginRight", Chart.class, "marginRight");
		xstream.useAttributeFor(Chart.class, "marginBottom");
		xstream.aliasField("marginBottom", Chart.class, "marginBottom");
		xstream.useAttributeFor(Chart.class, "marginLeft");
		xstream.aliasField("marginLeft", Chart.class, "marginLeft");
		xstream.useAttributeFor(Chart.class, "plotBackgroundColor");
		xstream.aliasField("plotBackgroundColor", Chart.class, "plotBackgroundColor");
		xstream.useAttributeFor(Chart.class, "plotBackgroundImage");
		xstream.aliasField("plotBackgroundImage", Chart.class, "plotBackgroundImage");
		xstream.useAttributeFor(Chart.class, "plotBorderColor");
		xstream.aliasField("plotBorderColor", Chart.class, "plotBorderColor");
		xstream.useAttributeFor(Chart.class, "plotBorderWidth");
		xstream.aliasField("plotBorderWidth", Chart.class, "plotBorderWidth");
		xstream.useAttributeFor(Chart.class, "plotShadow");
		xstream.aliasField("plotShadow", Chart.class, "plotShadow");
		xstream.useAttributeFor(Chart.class, "reflow");
		xstream.aliasField("reflow", Chart.class, "reflow");
		xstream.useAttributeFor(Chart.class, "renderTo");
		xstream.aliasField("renderTo", Chart.class, "renderTo");
		xstream.useAttributeFor(Chart.class, "shadow");
		xstream.aliasField("shadow", Chart.class, "shadow");
		xstream.useAttributeFor(Chart.class, "showAxes");
		xstream.aliasField("showAxes", Chart.class, "showAxes");
		xstream.useAttributeFor(Chart.class, "spacingTop");
		xstream.aliasField("spacingTop", Chart.class, "spacingTop");	
		xstream.useAttributeFor(Chart.class, "spacingRight");
		xstream.aliasField("spacingRight", Chart.class, "spacingRight");		
		xstream.useAttributeFor(Chart.class, "spacingBottom");
		xstream.aliasField("spacingBottom", Chart.class, "spacingBottom");	
		xstream.useAttributeFor(Chart.class, "spacingLeft");
		xstream.aliasField("spacingLeft", Chart.class, "spacingLeft");		
		xstream.useAttributeFor(Chart.class, "style");
		xstream.aliasField("style", Chart.class, "style");
//		xstream.useAttributeFor(Chart.class, "type");
//		xstream.aliasField("type", Chart.class, "type");
		xstream.useAttributeFor(Chart.class, "width");
		xstream.aliasField("width", Chart.class, "width");
		xstream.useAttributeFor(Chart.class, "zoomType");
		xstream.aliasField("zoomType", Chart.class, "zoomType");

		xstream.aliasField("TITLE", HighChart.class, "title"); 
		xstream.useAttributeFor(Title.class, "text");
		xstream.aliasField("text", Title.class, "text");
		xstream.useAttributeFor(Title.class, "align");
		xstream.aliasField("align", Title.class, "align");
		xstream.useAttributeFor(Title.class, "floating");
		xstream.aliasField("floating", Title.class, "floating");
		xstream.useAttributeFor(Title.class, "margin");
		xstream.aliasField("margin", Title.class, "margin");
		xstream.useAttributeFor(Title.class, "style");
		xstream.aliasField("style", Title.class, "style");
		xstream.useAttributeFor(Title.class, "verticalAlign");
		xstream.aliasField("verticalAlign", Title.class, "verticalAlign");
		xstream.useAttributeFor(Title.class, "x");
		xstream.aliasField("x", Title.class, "x");
		xstream.useAttributeFor(Title.class, "y");
		xstream.aliasField("y", Title.class, "y");
		
		xstream.aliasField("SUBTITLE", HighChart.class, "subTitle"); 
		xstream.useAttributeFor(SubTitle.class, "text");
		xstream.aliasField("text", SubTitle.class, "text");
		xstream.useAttributeFor(SubTitle.class, "align");
		xstream.aliasField("align", SubTitle.class, "align");
		xstream.useAttributeFor(SubTitle.class, "floating");
		xstream.aliasField("floating", SubTitle.class, "floating");
		xstream.useAttributeFor(SubTitle.class, "margin");
		xstream.aliasField("margin", SubTitle.class, "margin");
		xstream.useAttributeFor(SubTitle.class, "style");
		xstream.aliasField("style", SubTitle.class, "style");
		xstream.useAttributeFor(SubTitle.class, "verticalAlign");
		xstream.aliasField("verticalAlign", SubTitle.class, "verticalAlign");
		xstream.useAttributeFor(SubTitle.class, "x");
		xstream.aliasField("x", SubTitle.class, "x");
		xstream.useAttributeFor(SubTitle.class, "y");
		xstream.aliasField("y", SubTitle.class, "y");

		
		
		xstream.aliasField("X_AXIS", HighChart.class, "xAxis"); 
		xstream.useAttributeFor(XAxis.class, "allowDecimals");
		xstream.aliasField("allowDecimals", XAxis.class, "allowDecimals");
		xstream.useAttributeFor(XAxis.class, "alternateGridColor");
		xstream.aliasField("alternateGridColor", XAxis.class, "alternateGridColor");
		xstream.useAttributeFor(XAxis.class, "categories");
		xstream.aliasField("categories", XAxis.class, "categories");
		xstream.useAttributeFor(XAxis.class, "dateTimeLabelFormats");
		xstream.aliasField("dateTimeLabelFormats", XAxis.class, "dateTimeLabelFormats");
		xstream.useAttributeFor(XAxis.class, "endOnTick");
		xstream.aliasField("endOnTick", XAxis.class, "endOnTick");
		xstream.useAttributeFor(XAxis.class, "gridLineColor");
		xstream.aliasField("gridLineColor", XAxis.class, "gridLineColor");
		xstream.useAttributeFor(XAxis.class, "gridLineDashStyle");
		xstream.aliasField("gridLineDashStyle", XAxis.class, "gridLineDashStyle");
		xstream.useAttributeFor(XAxis.class, "gridLineWidth");
		xstream.aliasField("gridLineWidth", XAxis.class, "gridLineWidth");
		xstream.useAttributeFor(XAxis.class, "labels");
		xstream.aliasField("labels", XAxis.class, "labels");
		xstream.useAttributeFor(XAxis.class, "lineColor");
		xstream.aliasField("lineColor", XAxis.class, "lineColor");
		xstream.useAttributeFor(XAxis.class, "lineWidth");
		xstream.aliasField("lineWidth", XAxis.class, "lineWidth");
		xstream.useAttributeFor(XAxis.class, "linkedTo");
		xstream.aliasField("linkedTo", XAxis.class, "linkedTo");
		xstream.useAttributeFor(XAxis.class, "max");
		xstream.aliasField("max", XAxis.class, "max");
		xstream.useAttributeFor(XAxis.class, "maxPadding");
		xstream.aliasField("maxPadding", XAxis.class, "maxPadding");
		xstream.useAttributeFor(XAxis.class, "maxZoom");
		xstream.aliasField("maxZoom", XAxis.class, "maxZoom");
		xstream.useAttributeFor(XAxis.class, "min");
		xstream.aliasField("min", XAxis.class, "min");
		xstream.useAttributeFor(XAxis.class, "minorGridLineColor");
		xstream.aliasField("minorGridLineColor", XAxis.class, "minorGridLineColor");
		xstream.useAttributeFor(XAxis.class, "minorGridLineDashStyle");
		xstream.aliasField("minorGridLineDashStyle", XAxis.class, "minorGridLineDashStyle");
		xstream.useAttributeFor(XAxis.class, "minorGridLineWidth");
		xstream.aliasField("minorGridLineWidth", XAxis.class, "minorGridLineWidth");
		xstream.useAttributeFor(XAxis.class, "minorTickColor");
		xstream.aliasField("minorTickColor", XAxis.class, "minorTickColor");
		xstream.useAttributeFor(XAxis.class, "minorTickInterval");
		xstream.aliasField("minorTickInterval", XAxis.class, "minorTickInterval");
		xstream.useAttributeFor(XAxis.class, "minorTickLength");
		xstream.aliasField("minorTickLength", XAxis.class, "minorTickLength");
		xstream.useAttributeFor(XAxis.class, "minorTickPosition");
		xstream.aliasField("minorTickPosition", XAxis.class, "minorTickPosition");
		xstream.useAttributeFor(XAxis.class, "minorTickWidth");
		xstream.aliasField("minorTickWidth", XAxis.class, "minorTickWidth");
		xstream.useAttributeFor(XAxis.class, "minPadding");
		xstream.aliasField("minPadding", XAxis.class, "minPadding");
		xstream.useAttributeFor(XAxis.class, "offset");
		xstream.aliasField("offset", XAxis.class, "offset");
		xstream.useAttributeFor(XAxis.class, "opposite");
		xstream.aliasField("opposite", XAxis.class, "opposite");
		xstream.useAttributeFor(XAxis.class, "plotBands");
		xstream.aliasField("plotBands", XAxis.class, "plotBands");
		xstream.useAttributeFor(XAxis.class, "plotLines");
		xstream.aliasField("plotLines", XAxis.class, "plotLines");
		xstream.useAttributeFor(XAxis.class, "reversed");
		xstream.aliasField("reversed", XAxis.class, "reversed");
		xstream.useAttributeFor(XAxis.class, "showFirstLabel");
		xstream.aliasField("showFirstLabel", XAxis.class, "showFirstLabel");
		xstream.useAttributeFor(XAxis.class, "showLastLabel");
		xstream.aliasField("showLastLabel", XAxis.class, "showLastLabel");
		xstream.useAttributeFor(XAxis.class, "startOfWeek");
		xstream.aliasField("startOfWeek", XAxis.class, "startOfWeek");
		xstream.useAttributeFor(XAxis.class, "startOnTick");
		xstream.aliasField("startOnTick", XAxis.class, "startOnTick");
		xstream.useAttributeFor(XAxis.class, "tickColor");
		xstream.aliasField("tickColor", XAxis.class, "tickColor");
		xstream.useAttributeFor(XAxis.class, "tickInterval");
		xstream.aliasField("tickInterval", XAxis.class, "tickInterval");
		xstream.useAttributeFor(XAxis.class, "tickLength");
		xstream.aliasField("tickLength", XAxis.class, "tickLength");
		xstream.useAttributeFor(XAxis.class, "tickmarkPlacement");
		xstream.aliasField("tickmarkPlacement", XAxis.class, "tickmarkPlacement");
		xstream.useAttributeFor(XAxis.class, "tickPixelInterval");
		xstream.aliasField("tickPixelInterval", XAxis.class, "tickPixelInterval");
		xstream.useAttributeFor(XAxis.class, "tickPosition");
		xstream.aliasField("tickPosition", XAxis.class, "tickPosition");
		xstream.useAttributeFor(XAxis.class, "tickWidth");
		xstream.aliasField("tickWidth", XAxis.class, "tickWidth");
		xstream.useAttributeFor(XAxis.class, "type");
		xstream.aliasField("type", XAxis.class, "type");
		xstream.useAttributeFor(XAxis.class, "alias");
		xstream.aliasField("alias", XAxis.class, "alias");
		
		xstream.aliasField("TITLE", XAxis.class, "titleAxis"); 
		xstream.useAttributeFor(TitleAxis.class, "text");
		xstream.aliasField("text", TitleAxis.class, "text");
		
	
		xstream.aliasField("Y_AXIS", HighChart.class, "yAxis"); 
		xstream.useAttributeFor(YAxis.class, "allowDecimals");
		xstream.aliasField("allowDecimals", YAxis.class, "allowDecimals");
		xstream.useAttributeFor(YAxis.class, "alternateGridColor");
		xstream.aliasField("alternateGridColor", YAxis.class, "alternateGridColor");
		xstream.useAttributeFor(YAxis.class, "categories");
		xstream.aliasField("categories", YAxis.class, "categories");
		xstream.useAttributeFor(YAxis.class, "dateTimeLabelFormats");
		xstream.aliasField("dateTimeLabelFormats", YAxis.class, "dateTimeLabelFormats");
		xstream.useAttributeFor(YAxis.class, "endOnTick");
		xstream.aliasField("endOnTick", YAxis.class, "endOnTick");
		xstream.useAttributeFor(YAxis.class, "gridLineColor");
		xstream.aliasField("gridLineColor", YAxis.class, "gridLineColor");
		xstream.useAttributeFor(YAxis.class, "gridLineDashStyle");
		xstream.aliasField("gridLineDashStyle", YAxis.class, "gridLineDashStyle");
		xstream.useAttributeFor(YAxis.class, "gridLineWidth");
		xstream.aliasField("gridLineWidth", YAxis.class, "gridLineWidth");
		xstream.useAttributeFor(YAxis.class, "labels");
		xstream.aliasField("labels", YAxis.class, "labels");
		xstream.useAttributeFor(YAxis.class, "lineColor");
		xstream.aliasField("lineColor", YAxis.class, "lineColor");
		xstream.useAttributeFor(YAxis.class, "lineWidth");
		xstream.aliasField("lineWidth", YAxis.class, "lineWidth");
		xstream.useAttributeFor(YAxis.class, "linkedTo");
		xstream.aliasField("linkedTo", YAxis.class, "linkedTo");
		xstream.useAttributeFor(YAxis.class, "max");
		xstream.aliasField("max", YAxis.class, "max");
		xstream.useAttributeFor(YAxis.class, "maxPadding");
		xstream.aliasField("maxPadding", YAxis.class, "maxPadding");
		xstream.useAttributeFor(YAxis.class, "maxZoom");
		xstream.aliasField("maxZoom", YAxis.class, "maxZoom");
		xstream.useAttributeFor(YAxis.class, "min");
		xstream.aliasField("min", YAxis.class, "min");
		xstream.useAttributeFor(YAxis.class, "minorGridLineColor");
		xstream.aliasField("minorGridLineColor", YAxis.class, "minorGridLineColor");
		xstream.useAttributeFor(YAxis.class, "minorGridLineDashStyle");
		xstream.aliasField("minorGridLineDashStyle", YAxis.class, "minorGridLineDashStyle");
		xstream.useAttributeFor(YAxis.class, "minorGridLineWidth");
		xstream.aliasField("minorGridLineWidth", YAxis.class, "minorGridLineWidth");
		xstream.useAttributeFor(YAxis.class, "minorTickColor");
		xstream.aliasField("minorTickColor", YAxis.class, "minorTickColor");
		xstream.useAttributeFor(YAxis.class, "minorTickInterval");
		xstream.aliasField("minorTickInterval", YAxis.class, "minorTickInterval");
		xstream.useAttributeFor(YAxis.class, "minorTickLength");
		xstream.aliasField("minorTickLength", YAxis.class, "minorTickLength");
		xstream.useAttributeFor(YAxis.class, "minorTickPosition");
		xstream.aliasField("minorTickPosition", YAxis.class, "minorTickPosition");
		xstream.useAttributeFor(YAxis.class, "minorTickWidth");
		xstream.aliasField("minorTickWidth", YAxis.class, "minorTickWidth");
		xstream.useAttributeFor(YAxis.class, "minPadding");
		xstream.aliasField("minPadding", YAxis.class, "minPadding");
		xstream.useAttributeFor(YAxis.class, "offset");
		xstream.aliasField("offset", YAxis.class, "offset");
		xstream.useAttributeFor(YAxis.class, "opposite");
		xstream.aliasField("opposite", YAxis.class, "opposite");
		xstream.useAttributeFor(YAxis.class, "plotBands");
		xstream.aliasField("plotBands", YAxis.class, "plotBands");
		xstream.useAttributeFor(YAxis.class, "plotLines");
		xstream.aliasField("plotLines", YAxis.class, "plotLines");
		xstream.useAttributeFor(YAxis.class, "reversed");
		xstream.aliasField("reversed", YAxis.class, "reversed");
		xstream.useAttributeFor(YAxis.class, "showFirstLabel");
		xstream.aliasField("showFirstLabel", YAxis.class, "showFirstLabel");
		xstream.useAttributeFor(YAxis.class, "showLastLabel");
		xstream.aliasField("showLastLabel", YAxis.class, "showLastLabel");
		xstream.useAttributeFor(YAxis.class, "startOfWeek");
		xstream.aliasField("startOfWeek", YAxis.class, "startOfWeek");
		xstream.useAttributeFor(YAxis.class, "startOnTick");
		xstream.aliasField("startOnTick", YAxis.class, "startOnTick");
		xstream.useAttributeFor(YAxis.class, "tickColor");
		xstream.aliasField("tickColor", YAxis.class, "tickColor");
		xstream.useAttributeFor(YAxis.class, "tickInterval");
		xstream.aliasField("tickInterval", YAxis.class, "tickInterval");
		xstream.useAttributeFor(YAxis.class, "tickLength");
		xstream.aliasField("tickLength", YAxis.class, "tickLength");
		xstream.useAttributeFor(YAxis.class, "tickmarkPlacement");
		xstream.aliasField("tickmarkPlacement", YAxis.class, "tickmarkPlacement");
		xstream.useAttributeFor(YAxis.class, "tickPixelInterval");
		xstream.aliasField("tickPixelInterval", YAxis.class, "tickPixelInterval");
		xstream.useAttributeFor(YAxis.class, "tickPosition");
		xstream.aliasField("tickPosition", YAxis.class, "tickPosition");
		xstream.useAttributeFor(YAxis.class, "tickWidth");
		xstream.aliasField("tickWidth", YAxis.class, "tickWidth");
		xstream.useAttributeFor(YAxis.class, "type");
		xstream.aliasField("type", YAxis.class, "type");
		xstream.useAttributeFor(YAxis.class, "alias");
		xstream.aliasField("alias", YAxis.class, "alias");


		xstream.aliasField("TITLE", YAxis.class, "titleAxis"); 
		xstream.useAttributeFor(TitleAxis.class, "text");
		xstream.aliasField("text", TitleAxis.class, "text");
		
		xstream.aliasField("LEGEND", HighChart.class, "legend"); 
		xstream.useAttributeFor(Legend.class, "align");
		xstream.aliasField("align", Legend.class, "align");
		xstream.useAttributeFor(Legend.class, "layout");
		xstream.aliasField("layout", Legend.class, "layout");
		xstream.useAttributeFor(Legend.class, "verticalAlign");
		xstream.aliasField("verticalAlign", Legend.class, "verticalAlign");
		xstream.useAttributeFor(Legend.class, "x");
		xstream.aliasField("x", Legend.class, "x");
		xstream.useAttributeFor(Legend.class, "y");
		xstream.aliasField("y", Legend.class, "y");
		xstream.useAttributeFor(Legend.class, "floating");
		xstream.aliasField("floating", Legend.class, "floating");
		xstream.useAttributeFor(Legend.class, "borderWidth");
		xstream.aliasField("borderWidth", Legend.class, "borderWidth");
		xstream.useAttributeFor(Legend.class, "backgroundColor");
		xstream.aliasField("backgroundColor", Legend.class, "backgroundColor");
		xstream.useAttributeFor(Legend.class, "shadow");
		xstream.aliasField("shadow", Legend.class, "shadow");
		xstream.useAttributeFor(Legend.class, "borderColor");
		xstream.aliasField("borderColor", Legend.class, "borderColor");
		xstream.useAttributeFor(Legend.class, "borderRadius");
		xstream.aliasField("borderRadius", Legend.class, "borderRadius");
		xstream.useAttributeFor(Legend.class, "enabled");
		xstream.aliasField("enabled", Legend.class, "enabled");
		xstream.useAttributeFor(Legend.class, "itemHiddenStyle");
		xstream.aliasField("itemHiddenStyle", Legend.class, "itemHiddenStyle");
		xstream.useAttributeFor(Legend.class, "itemHoverStyle");
		xstream.aliasField("itemHoverStyle", Legend.class, "itemHoverStyle");
		xstream.useAttributeFor(Legend.class, "itemStyle");
		xstream.aliasField("itemStyle", Legend.class, "itemStyle");
		xstream.useAttributeFor(Legend.class, "itemWidth");
		xstream.aliasField("itemWidth", Legend.class, "itemWidth");
		xstream.useAttributeFor(Legend.class, "labelFormatter");
		xstream.aliasField("labelFormatter", Legend.class, "labelFormatter");
		xstream.useAttributeFor(Legend.class, "lineHeight");
		xstream.aliasField("lineHeight", Legend.class, "lineHeight");
		xstream.useAttributeFor(Legend.class, "margin");
		xstream.aliasField("margin", Legend.class, "margin");
		xstream.useAttributeFor(Legend.class, "reversed");
		xstream.aliasField("reversed", Legend.class, "reversed");
		xstream.useAttributeFor(Legend.class, "style");
		xstream.aliasField("style", Legend.class, "style");
		xstream.useAttributeFor(Legend.class, "symbolPadding");
		xstream.aliasField("symbolPadding", Legend.class, "symbolPadding");
		xstream.useAttributeFor(Legend.class, "symbolWidth");
		xstream.aliasField("symbolWidth", Legend.class, "symbolWidth");
		xstream.useAttributeFor(Legend.class, "width");
		xstream.aliasField("width", Legend.class, "width");

		xstream.aliasField("PLOT_OPTIONS", HighChart.class, "plotOptions");
		
		xstream.aliasField("SERIES", PlotOptions.class, "series"); 
		xstream.useAttributeFor(Series.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Series.class, "allowPointSelect");
		xstream.useAttributeFor(Series.class, "animation");
		xstream.aliasField("animation", Series.class, "animation");
		xstream.useAttributeFor(Series.class, "color");
		xstream.aliasField("color", Series.class, "color");
		xstream.useAttributeFor(Series.class, "cursor");
		xstream.aliasField("cursor", Series.class, "cursor");
		xstream.useAttributeFor(Series.class, "dashStyle");
		xstream.aliasField("dashStyle", Series.class, "dashStyle");
		xstream.useAttributeFor(Series.class, "dataLabels");
		xstream.aliasField("dataLabels", Series.class, "dataLabels");
		xstream.useAttributeFor(Series.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Series.class, "enableMouseTracking");
		xstream.useAttributeFor(Series.class, "lineWidth");
		xstream.aliasField("lineWidth", Series.class, "lineWidth");
		xstream.useAttributeFor(Series.class, "marker");
		xstream.aliasField("marker", Series.class, "marker");
		xstream.useAttributeFor(Series.class, "pointStart");
		xstream.aliasField("pointStart", Series.class, "pointStart");
		xstream.useAttributeFor(Series.class, "pointInterval");
		xstream.aliasField("pointInterval", Series.class, "pointInterval");
		xstream.useAttributeFor(Series.class, "selected");
		xstream.aliasField("selected", Series.class, "selected");
		xstream.useAttributeFor(Series.class, "shadow");
		xstream.aliasField("shadow", Series.class, "shadow");
		xstream.useAttributeFor(Series.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Series.class, "showCheckbox");
		xstream.useAttributeFor(Series.class, "showInLegend");
		xstream.aliasField("showInLegend", Series.class, "showInLegend");
		xstream.useAttributeFor(Series.class, "stacking");
		xstream.aliasField("stacking", Series.class, "stacking");
		xstream.useAttributeFor(Series.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Series.class, "stickyTracking");
		xstream.useAttributeFor(Series.class, "visible");
		xstream.aliasField("visible", Series.class, "visible");
		xstream.useAttributeFor(Series.class, "zIndex");
		xstream.aliasField("zIndex", Series.class, "zIndex");
		

		xstream.aliasField("AREA", PlotOptions.class, "area"); 
		xstream.useAttributeFor(Area.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Area.class, "allowPointSelect");
		xstream.useAttributeFor(Area.class, "animation");
		xstream.aliasField("animation", Area.class, "animation");
		xstream.useAttributeFor(Area.class, "color");
		xstream.aliasField("color", Area.class, "color");
		xstream.useAttributeFor(Area.class, "cursor");
		xstream.aliasField("cursor", Area.class, "cursor");
		xstream.useAttributeFor(Area.class, "dashStyle");
		xstream.aliasField("dashStyle", Area.class, "dashStyle");
		xstream.useAttributeFor(Area.class, "dataLabels");
		xstream.aliasField("dataLabels", Area.class, "dataLabels");
		xstream.useAttributeFor(Area.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Area.class, "enableMouseTracking");
		xstream.useAttributeFor(Area.class, "lineWidth");
		xstream.aliasField("lineWidth", Area.class, "lineWidth");
		xstream.useAttributeFor(Area.class, "marker");
		xstream.aliasField("marker", Area.class, "marker");
		xstream.useAttributeFor(Area.class, "pointStart");
		xstream.aliasField("pointStart", Area.class, "pointStart");
		xstream.useAttributeFor(Area.class, "pointInterval");
		xstream.aliasField("pointInterval", Area.class, "pointInterval");
		xstream.useAttributeFor(Area.class, "selected");
		xstream.aliasField("selected", Area.class, "selected");
		xstream.useAttributeFor(Area.class, "shadow");
		xstream.aliasField("shadow", Area.class, "shadow");
		xstream.useAttributeFor(Area.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Area.class, "showCheckbox");
		xstream.useAttributeFor(Area.class, "showInLegend");
		xstream.aliasField("showInLegend", Area.class, "showInLegend");
		xstream.useAttributeFor(Area.class, "stacking");
		xstream.aliasField("stacking", Area.class, "stacking");
		xstream.useAttributeFor(Area.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Area.class, "stickyTracking");
		xstream.useAttributeFor(Area.class, "visible");
		xstream.aliasField("visible", Area.class, "visible");
		xstream.useAttributeFor(Area.class, "zIndex");
		xstream.aliasField("zIndex", Area.class, "zIndex");
		xstream.useAttributeFor(Area.class, "fillColor");
		xstream.aliasField("fillColor", Area.class, "fillColor");
		xstream.useAttributeFor(Area.class, "fillOpacity");
		xstream.aliasField("fillOpacity", Area.class, "fillOpacity");
		xstream.useAttributeFor(Area.class, "lineColor");
		xstream.aliasField("lineColor", Area.class, "lineColor");
		xstream.useAttributeFor(Area.class, "threshold");
		xstream.aliasField("threshold", Area.class, "threshold");
		
		xstream.aliasField("DATA_LABELS", Area.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");

		
		xstream.aliasField("BAR", PlotOptions.class, "bar"); 
		xstream.useAttributeFor(Bar.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Bar.class, "allowPointSelect");
		xstream.useAttributeFor(Bar.class, "animation");
		xstream.aliasField("animation", Bar.class, "animation");
		xstream.useAttributeFor(Bar.class, "color");
		xstream.aliasField("color", Bar.class, "color");
		xstream.useAttributeFor(Bar.class, "cursor");
		xstream.aliasField("cursor", Bar.class, "cursor");
		xstream.useAttributeFor(Bar.class, "dashStyle");
		xstream.aliasField("dashStyle", Bar.class, "dashStyle");
		xstream.useAttributeFor(Bar.class, "dataLabels");
		xstream.aliasField("dataLabels", Bar.class, "dataLabels");
		xstream.useAttributeFor(Bar.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Bar.class, "enableMouseTracking");
		xstream.useAttributeFor(Bar.class, "lineWidth");
		xstream.aliasField("lineWidth", Bar.class, "lineWidth");
		xstream.useAttributeFor(Bar.class, "marker");
		xstream.aliasField("marker", Bar.class, "marker");
		xstream.useAttributeFor(Bar.class, "pointStart");
		xstream.aliasField("pointStart", Bar.class, "pointStart");
		xstream.useAttributeFor(Bar.class, "pointInterval");
		xstream.aliasField("pointInterval", Bar.class, "pointInterval");
		xstream.useAttributeFor(Bar.class, "selected");
		xstream.aliasField("selected", Bar.class, "selected");
		xstream.useAttributeFor(Bar.class, "shadow");
		xstream.aliasField("shadow", Bar.class, "shadow");
		xstream.useAttributeFor(Bar.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Bar.class, "showCheckbox");
		xstream.useAttributeFor(Bar.class, "showInLegend");
		xstream.aliasField("showInLegend", Bar.class, "showInLegend");
		xstream.useAttributeFor(Bar.class, "stacking");
		xstream.aliasField("stacking", Bar.class, "stacking");
		xstream.useAttributeFor(Bar.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Bar.class, "stickyTracking");
		xstream.useAttributeFor(Bar.class, "visible");
		xstream.aliasField("visible", Bar.class, "visible");
		xstream.useAttributeFor(Bar.class, "zIndex");
		xstream.aliasField("zIndex", Bar.class, "zIndex");
		xstream.useAttributeFor(Bar.class, "borderColor");
		xstream.aliasField("borderColor", Bar.class, "borderColor");
		xstream.useAttributeFor(Bar.class, "borderRadius");
		xstream.aliasField("borderRadius", Bar.class, "borderRadius");
		xstream.useAttributeFor(Bar.class, "borderWidth");
		xstream.aliasField("borderWidth", Bar.class, "borderWidth");
		xstream.useAttributeFor(Bar.class, "colorByPoint");
		xstream.aliasField("colorByPoint", Bar.class, "colorByPoint");
		xstream.useAttributeFor(Bar.class, "minPointLength");
		xstream.aliasField("minPointLength", Bar.class, "minPointLength");
		xstream.useAttributeFor(Bar.class, "groupPadding");
		xstream.aliasField("groupPadding", Bar.class, "groupPadding");
		xstream.useAttributeFor(Bar.class, "pointPadding");
		xstream.aliasField("pointPadding", Bar.class, "pointPadding");
		xstream.useAttributeFor(Bar.class, "pointWidth");
		xstream.aliasField("pointWidth", Bar.class, "pointWidth");
		xstream.aliasField("DATA_LABELS", Bar.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");

		
		
		xstream.aliasField("LINE", PlotOptions.class, "line");
		xstream.useAttributeFor(Line.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Line.class, "allowPointSelect");
		xstream.useAttributeFor(Line.class, "animation");
		xstream.aliasField("animation", Line.class, "animation");
		xstream.useAttributeFor(Line.class, "color");
		xstream.aliasField("color", Line.class, "color");
		xstream.useAttributeFor(Line.class, "cursor");
		xstream.aliasField("cursor", Line.class, "cursor");
		xstream.useAttributeFor(Line.class, "dashStyle");
		xstream.aliasField("dashStyle", Line.class, "dashStyle");
		xstream.useAttributeFor(Line.class, "dataLabels");
		xstream.aliasField("dataLabels", Line.class, "dataLabels");
		xstream.useAttributeFor(Line.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Line.class, "enableMouseTracking");
		xstream.useAttributeFor(Line.class, "lineWidth");
		xstream.aliasField("lineWidth", Line.class, "lineWidth");
		xstream.useAttributeFor(Line.class, "marker");
		xstream.aliasField("marker", Line.class, "marker");
		xstream.useAttributeFor(Line.class, "pointStart");
		xstream.aliasField("pointStart", Line.class, "pointStart");
		xstream.useAttributeFor(Line.class, "pointInterval");
		xstream.aliasField("pointInterval", Line.class, "pointInterval");
		xstream.useAttributeFor(Line.class, "selected");
		xstream.aliasField("selected", Line.class, "selected");
		xstream.useAttributeFor(Line.class, "shadow");
		xstream.aliasField("shadow", Line.class, "shadow");
		xstream.useAttributeFor(Line.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Line.class, "showCheckbox");
		xstream.useAttributeFor(Line.class, "showInLegend");
		xstream.aliasField("showInLegend", Line.class, "showInLegend");
		xstream.useAttributeFor(Line.class, "stacking");
		xstream.aliasField("stacking", Line.class, "stacking");
		xstream.useAttributeFor(Line.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Line.class, "stickyTracking");
		xstream.useAttributeFor(Line.class, "visible");
		xstream.aliasField("visible", Line.class, "visible");
		xstream.useAttributeFor(Line.class, "zIndex");
		xstream.aliasField("zIndex", Line.class, "zIndex");
		xstream.useAttributeFor(Line.class, "step");
		xstream.aliasField("step", Line.class, "step");
		xstream.aliasField("DATA_LABELS", Line.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");
		
		
		xstream.aliasField("AREASPLINE", PlotOptions.class, "areaSpline"); 
		xstream.useAttributeFor(AreaSpline.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", AreaSpline.class, "allowPointSelect");
		xstream.useAttributeFor(AreaSpline.class, "animation");
		xstream.aliasField("animation", AreaSpline.class, "animation");
		xstream.useAttributeFor(AreaSpline.class, "color");
		xstream.aliasField("color", AreaSpline.class, "color");
		xstream.useAttributeFor(AreaSpline.class, "cursor");
		xstream.aliasField("cursor", AreaSpline.class, "cursor");
		xstream.useAttributeFor(AreaSpline.class, "dashStyle");
		xstream.aliasField("dashStyle", AreaSpline.class, "dashStyle");
		xstream.useAttributeFor(AreaSpline.class, "dataLabels");
		xstream.aliasField("dataLabels", AreaSpline.class, "dataLabels");
		xstream.useAttributeFor(AreaSpline.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", AreaSpline.class, "enableMouseTracking");
		xstream.useAttributeFor(AreaSpline.class, "lineWidth");
		xstream.aliasField("lineWidth", AreaSpline.class, "lineWidth");
		xstream.useAttributeFor(AreaSpline.class, "marker");
		xstream.aliasField("marker", AreaSpline.class, "marker");
		xstream.useAttributeFor(AreaSpline.class, "pointStart");
		xstream.aliasField("pointStart", AreaSpline.class, "pointStart");
		xstream.useAttributeFor(AreaSpline.class, "pointInterval");
		xstream.aliasField("pointInterval", AreaSpline.class, "pointInterval");
		xstream.useAttributeFor(AreaSpline.class, "selected");
		xstream.aliasField("selected", AreaSpline.class, "selected");
		xstream.useAttributeFor(AreaSpline.class, "shadow");
		xstream.aliasField("shadow", AreaSpline.class, "shadow");
		xstream.useAttributeFor(AreaSpline.class, "showCheckbox");
		xstream.aliasField("showCheckbox", AreaSpline.class, "showCheckbox");
		xstream.useAttributeFor(AreaSpline.class, "showInLegend");
		xstream.aliasField("showInLegend", AreaSpline.class, "showInLegend");
		xstream.useAttributeFor(AreaSpline.class, "stacking");
		xstream.aliasField("stacking", AreaSpline.class, "stacking");
		xstream.useAttributeFor(AreaSpline.class, "stickyTracking");
		xstream.aliasField("stickyTracking", AreaSpline.class, "stickyTracking");
		xstream.useAttributeFor(AreaSpline.class, "visible");
		xstream.aliasField("visible", AreaSpline.class, "visible");
		xstream.useAttributeFor(AreaSpline.class, "zIndex");
		xstream.aliasField("zIndex", AreaSpline.class, "zIndex");
		xstream.useAttributeFor(AreaSpline.class, "fillColor");
		xstream.aliasField("fillColor", AreaSpline.class, "fillColor");
		xstream.useAttributeFor(AreaSpline.class, "fillOpacity");
		xstream.aliasField("fillOpacity", AreaSpline.class, "fillOpacity");
		xstream.useAttributeFor(AreaSpline.class, "lineColor");
		xstream.aliasField("lineColor", AreaSpline.class, "lineColor");
		xstream.useAttributeFor(AreaSpline.class, "threshold");
		xstream.aliasField("threshold", AreaSpline.class, "threshold");
		xstream.aliasField("DATA_LABELS", AreaSpline.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");
		
		
		
		
		xstream.aliasField("SCATTER", PlotOptions.class, "scatter");
		xstream.useAttributeFor(Scatter.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Scatter.class, "allowPointSelect");
		xstream.useAttributeFor(Scatter.class, "animation");
		xstream.aliasField("animation", Scatter.class, "animation");
		xstream.useAttributeFor(Scatter.class, "color");
		xstream.aliasField("color", Scatter.class, "color");
		xstream.useAttributeFor(Scatter.class, "cursor");
		xstream.aliasField("cursor", Scatter.class, "cursor");
		xstream.useAttributeFor(Scatter.class, "dashStyle");
		xstream.aliasField("dashStyle", Scatter.class, "dashStyle");
		xstream.useAttributeFor(Scatter.class, "dataLabels");
		xstream.aliasField("dataLabels", Scatter.class, "dataLabels");
		xstream.useAttributeFor(Scatter.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Scatter.class, "enableMouseTracking");
		xstream.useAttributeFor(Scatter.class, "lineWidth");
		xstream.aliasField("lineWidth", Scatter.class, "lineWidth");
		xstream.useAttributeFor(Scatter.class, "marker");
		xstream.aliasField("marker", Scatter.class, "marker");
		xstream.useAttributeFor(Scatter.class, "pointStart");
		xstream.aliasField("pointStart", Scatter.class, "pointStart");
		xstream.useAttributeFor(Scatter.class, "pointInterval");
		xstream.aliasField("pointInterval", Scatter.class, "pointInterval");
		xstream.useAttributeFor(Scatter.class, "selected");
		xstream.aliasField("selected", Scatter.class, "selected");
		xstream.useAttributeFor(Scatter.class, "shadow");
		xstream.aliasField("shadow", Scatter.class, "shadow");
		xstream.useAttributeFor(Scatter.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Scatter.class, "showCheckbox");
		xstream.useAttributeFor(Scatter.class, "showInLegend");
		xstream.aliasField("showInLegend", Scatter.class, "showInLegend");
		xstream.useAttributeFor(Scatter.class, "stacking");
		xstream.aliasField("stacking", Scatter.class, "stacking");
		xstream.useAttributeFor(Scatter.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Scatter.class, "stickyTracking");
		xstream.useAttributeFor(Scatter.class, "visible");
		xstream.aliasField("visible", Scatter.class, "visible");
		xstream.useAttributeFor(Scatter.class, "zIndex");
		xstream.aliasField("zIndex", Area.class, "zIndex");
		xstream.aliasField("DATA_LABELS", Scatter.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");

		xstream.aliasField("SPLINE", PlotOptions.class, "spline");
		xstream.useAttributeFor(Spline.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Spline.class, "allowPointSelect");
		xstream.useAttributeFor(Spline.class, "animation");
		xstream.aliasField("animation", Spline.class, "animation");
		xstream.useAttributeFor(Spline.class, "color");
		xstream.aliasField("color", Spline.class, "color");
		xstream.useAttributeFor(Spline.class, "cursor");
		xstream.aliasField("cursor", Spline.class, "cursor");
		xstream.useAttributeFor(Spline.class, "dashStyle");
		xstream.aliasField("dashStyle", Spline.class, "dashStyle");
		xstream.useAttributeFor(Spline.class, "dataLabels");
		xstream.aliasField("dataLabels", Spline.class, "dataLabels");
		xstream.useAttributeFor(Spline.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Spline.class, "enableMouseTracking");
		xstream.useAttributeFor(Spline.class, "lineWidth");
		xstream.aliasField("lineWidth", Spline.class, "lineWidth");
		xstream.useAttributeFor(Spline.class, "marker");
		xstream.aliasField("marker", Spline.class, "marker");
		xstream.useAttributeFor(Spline.class, "pointStart");
		xstream.aliasField("pointStart", Spline.class, "pointStart");
		xstream.useAttributeFor(Spline.class, "pointInterval");
		xstream.aliasField("pointInterval", Spline.class, "pointInterval");
		xstream.useAttributeFor(Spline.class, "selected");
		xstream.aliasField("selected", Spline.class, "selected");
		xstream.useAttributeFor(Spline.class, "shadow");
		xstream.aliasField("shadow", Spline.class, "shadow");
		xstream.useAttributeFor(Spline.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Spline.class, "showCheckbox");
		xstream.useAttributeFor(Spline.class, "showInLegend");
		xstream.aliasField("showInLegend", Spline.class, "showInLegend");
		xstream.useAttributeFor(Spline.class, "stacking");
		xstream.aliasField("stacking", Spline.class, "stacking");
		xstream.useAttributeFor(Spline.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Spline.class, "stickyTracking");
		xstream.useAttributeFor(Spline.class, "visible");
		xstream.aliasField("visible", Spline.class, "visible");
		xstream.useAttributeFor(Spline.class, "zIndex");
		xstream.aliasField("DATA_LABELS", Spline.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");
		
		
		
		
		xstream.aliasField("PIE", PlotOptions.class, "pie"); 	
		xstream.useAttributeFor(Pie.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Pie.class, "allowPointSelect");
		xstream.useAttributeFor(Pie.class, "animation");
		xstream.aliasField("animation", Pie.class, "animation");
		xstream.useAttributeFor(Pie.class, "color");
		xstream.aliasField("color", Pie.class, "color");
		xstream.useAttributeFor(Pie.class, "cursor");
		xstream.aliasField("cursor", Pie.class, "cursor");
		xstream.useAttributeFor(Pie.class, "dashStyle");
		xstream.aliasField("dashStyle", Pie.class, "dashStyle");
		xstream.useAttributeFor(Pie.class, "dataLabels");
		xstream.aliasField("dataLabels", Pie.class, "dataLabels");
		xstream.useAttributeFor(Pie.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Pie.class, "enableMouseTracking");
		xstream.useAttributeFor(Pie.class, "lineWidth");
		xstream.aliasField("lineWidth", Pie.class, "lineWidth");
		xstream.useAttributeFor(Pie.class, "marker");
		xstream.aliasField("marker", Pie.class, "marker");
		xstream.useAttributeFor(Pie.class, "pointStart");
		xstream.aliasField("pointStart", Pie.class, "pointStart");
		xstream.useAttributeFor(Pie.class, "pointInterval");
		xstream.aliasField("pointInterval", Pie.class, "pointInterval");
		xstream.useAttributeFor(Pie.class, "selected");
		xstream.aliasField("selected", Pie.class, "selected");
		xstream.useAttributeFor(Pie.class, "shadow");
		xstream.aliasField("shadow", Pie.class, "shadow");
		xstream.useAttributeFor(Pie.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Pie.class, "showCheckbox");
		xstream.useAttributeFor(Pie.class, "showInLegend");
		xstream.aliasField("showInLegend", Pie.class, "showInLegend");
		xstream.useAttributeFor(Pie.class, "stacking");
		xstream.aliasField("stacking", Pie.class, "stacking");
		xstream.useAttributeFor(Pie.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Pie.class, "stickyTracking");
		xstream.useAttributeFor(Pie.class, "visible");
		xstream.aliasField("visible", Pie.class, "visible");
		xstream.useAttributeFor(Pie.class, "zIndex");
		xstream.aliasField("borderColor", Pie.class, "borderColor");
		xstream.useAttributeFor(Pie.class, "borderWidth");
		xstream.aliasField("borderWidth", Pie.class, "borderWidth");
		xstream.useAttributeFor(Pie.class, "center");
		xstream.aliasField("center", Pie.class, "center");
		xstream.useAttributeFor(Pie.class, "innerSize");
		xstream.aliasField("innerSize", Pie.class, "innerSize");
		xstream.useAttributeFor(Pie.class, "size");
		xstream.aliasField("size", Pie.class, "size");
		xstream.useAttributeFor(Pie.class, "slicedOffset");
		xstream.aliasField("slicedOffset", Pie.class, "slicedOffset");
		xstream.aliasField("DATA_LABELS", Pie.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");
		
		
		
		xstream.aliasField("COLUMN", PlotOptions.class, "column");	
		xstream.useAttributeFor(Column.class, "allowPointSelect");
		xstream.aliasField("allowPointSelect", Column.class, "allowPointSelect");
		xstream.useAttributeFor(Column.class, "animation");
		xstream.aliasField("animation", Column.class, "animation");
		xstream.useAttributeFor(Column.class, "color");
		xstream.aliasField("color", Column.class, "color");
		xstream.useAttributeFor(Column.class, "cursor");
		xstream.aliasField("cursor", Column.class, "cursor");
		xstream.useAttributeFor(Column.class, "dashStyle");
		xstream.aliasField("dashStyle", Column.class, "dashStyle");
		xstream.useAttributeFor(Column.class, "dataLabels");
		xstream.aliasField("dataLabels", Column.class, "dataLabels");
		xstream.useAttributeFor(Column.class, "enableMouseTracking");
		xstream.aliasField("enableMouseTracking", Column.class, "enableMouseTracking");
		xstream.useAttributeFor(Column.class, "lineWidth");
		xstream.aliasField("lineWidth", Column.class, "lineWidth");
		xstream.useAttributeFor(Column.class, "marker");
		xstream.aliasField("marker", Column.class, "marker");
		xstream.useAttributeFor(Column.class, "pointStart");
		xstream.aliasField("pointStart", Column.class, "pointStart");
		xstream.useAttributeFor(Column.class, "pointInterval");
		xstream.aliasField("pointInterval", Column.class, "pointInterval");
		xstream.useAttributeFor(Column.class, "selected");
		xstream.aliasField("selected", Column.class, "selected");
		xstream.useAttributeFor(Column.class, "shadow");
		xstream.aliasField("shadow", Column.class, "shadow");
		xstream.useAttributeFor(Column.class, "showCheckbox");
		xstream.aliasField("showCheckbox", Column.class, "showCheckbox");
		xstream.useAttributeFor(Column.class, "showInLegend");
		xstream.aliasField("showInLegend", Column.class, "showInLegend");
		xstream.useAttributeFor(Column.class, "stacking");
		xstream.aliasField("stacking", Column.class, "stacking");
		xstream.useAttributeFor(Column.class, "stickyTracking");
		xstream.aliasField("stickyTracking", Column.class, "stickyTracking");
		xstream.useAttributeFor(Column.class, "visible");
		xstream.aliasField("visible", Column.class, "visible");
		xstream.useAttributeFor(Column.class, "zIndex");
		xstream.aliasField("zIndex", Column.class, "zIndex");
		xstream.useAttributeFor(Column.class, "borderColor");
		xstream.aliasField("borderColor", Column.class, "borderColor");
		xstream.useAttributeFor(Column.class, "borderRadius");
		xstream.aliasField("borderRadius", Column.class, "borderRadius");
		xstream.useAttributeFor(Column.class, "borderWidth");
		xstream.aliasField("borderWidth", Column.class, "borderWidth");
		xstream.useAttributeFor(Column.class, "colorByPoint");
		xstream.aliasField("colorByPoint", Column.class, "colorByPoint");
		xstream.useAttributeFor(Column.class, "minPointLength");
		xstream.aliasField("minPointLength", Column.class, "minPointLength");
		xstream.useAttributeFor(Column.class, "groupPadding");
		xstream.aliasField("groupPadding", Column.class, "groupPadding");
		xstream.useAttributeFor(Column.class, "pointPadding");
		xstream.aliasField("pointPadding", Column.class, "pointPadding");
		xstream.useAttributeFor(Column.class, "pointWidth");
		xstream.aliasField("pointWidth", Column.class, "pointWidth");
		xstream.aliasField("DATA_LABELS", Column.class, "dataLabels"); 
		xstream.useAttributeFor(DataLabels.class, "enabled");
		xstream.aliasField("enabled", DataLabels.class, "enabled");
		
		
		
		
		
		xstream.aliasField("DRILL", HighChart.class, "drill"); 
		xstream.useAttributeFor(Drill.class, "document");
		xstream.aliasField("document", Drill.class, "document");	
		
		xstream.aliasField("PARAM_LIST", Drill.class, "paramList"); 
		xstream.addImplicitCollection(ParamList.class, "params", "PARAM", Param.class);
		
		xstream.useAttributeFor(Param.class, "name");
		xstream.aliasField("name", Param.class, "name");	
		xstream.useAttributeFor(Param.class, "type");
		xstream.aliasField("type", Param.class, "type");	
		xstream.useAttributeFor(Param.class, "value");
		xstream.aliasField("value", Param.class, "value");	
		
		xstream.aliasField("SERIES_LIST", HighChart.class, "seriesList"); 		
		xstream.addImplicitCollection(SeriesList.class, "series", "SERIES", Serie.class);
		xstream.useAttributeFor(Serie.class, "name");
		xstream.aliasField("name", Serie.class, "name");	
		xstream.useAttributeFor(Serie.class, "color");
		xstream.aliasField("color", Serie.class, "color");	
		xstream.useAttributeFor(Serie.class, "alias");
		xstream.aliasField("alias", Serie.class, "alias");	
		xstream.useAttributeFor(Serie.class, "type");
		xstream.aliasField("type", Serie.class, "type");	
		xstream.useAttributeFor(Serie.class, "size");
		xstream.aliasField("size", Serie.class, "size");	
		xstream.useAttributeFor(Serie.class, "innerSize");
		xstream.aliasField("innerSize", Serie.class, "innerSize");	


		}


		public static String transformToXml(Object bean) {


			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			xstream.setMode(XStream.NO_REFERENCES);
			setAlias(xstream);	
			String xml = xstream.toXML(bean);
			return xml;
		}

/** populate the HighChart Object from template*/
		public static HighChart readXml(IFile file) throws CoreException{
			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			setAlias(xstream);	
			HighChart objFromXml = (HighChart)xstream.fromXML(file.getContents());
			return objFromXml;
		}



		public static void main(String[] args) {

		}
	}
