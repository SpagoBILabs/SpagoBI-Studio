/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedAreaProperties;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedBarAndLineProperties;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedGaugeProperties;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedPieProperties;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedProperties;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedRadarProperties;
import it.eng.spagobi.studio.extchart.editors.properties.advanced.AdvancedScatterProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesCategoryProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesGaugeProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesNumericProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesPieProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesRadarProperties;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesScatterProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesAreaProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesBarAndLineProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesGaugeProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesPieProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesRadarProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesScatterProperties;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.ExtChartConstants;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFactory {

	private static Logger logger = LoggerFactory.getLogger(PropertiesFactory.class);

	public static AxesProperties getAxesProperties(String type
			, ExtChartEditor editor, Axes axes, Shell comp){
		logger.debug("IN");
		logger.debug("get Axes Properties designer for type "+type);
		AxesProperties toReturn = null;

		if(type.equals(ExtChartConstants.EXT_CHART_TYPE_AREA))
		{
			toReturn = new AxesCategoryProperties(editor, comp); 
			toReturn.setTitle("Define category Axe ");
			toReturn.setAxes(axes);

		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_BAR_AND_LINE)){
			toReturn =new AxesCategoryProperties(editor, comp); 
			toReturn.setTitle("Define category Axe ");
			toReturn.setAxes(axes);

		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_GAUGE)){
			toReturn =new AxesGaugeProperties(editor, comp); 
			toReturn.setTitle("Define Gauge Axe");
			toReturn.setAxes(axes);
		}
//		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_PIE)){
//			return new AxesPieProperties();
//		}
//		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_RADAR)){
//			return new AxesRadarProperties();
//		}
//		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_SCATTER)){
//			return new AxesScatterProperties();
//		}
//		else{
//			throw new RuntimeException("Type "+type+" not handled");
//		}
		logger.debug("OUT");
		return toReturn;
	}

	public static AxesProperties getYAxesProperties(String type
			, ExtChartEditor editor, Axes axes, Shell comp){
		logger.debug("IN");
		logger.debug("get Axes Properties designer for type "+type);
		if(type.equals(ExtChartConstants.EXT_CHART_TYPE_BAR_AND_LINE)){
			AxesNumericProperties axesProperties =new AxesNumericProperties(editor, comp); 
			axesProperties.setAxes(axes);
			axesProperties.setTitle("Define numeric axe properties");
			
			return axesProperties;
		}

		logger.debug("OUT");
		return null;


	}


	public static AdvancedProperties getAdvancedProperties(String type){
		logger.debug("IN");
		logger.debug("get Advanced Properties designer for type "+type);
		if(type.equals(ExtChartConstants.EXT_CHART_TYPE_AREA))
		{
			return new AdvancedAreaProperties();
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_BAR_AND_LINE)){
			return new AdvancedBarAndLineProperties();
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_GAUGE)){
			return new AdvancedGaugeProperties();
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_PIE)){
			return new AdvancedPieProperties();
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_RADAR)){
			return new AdvancedRadarProperties();
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_SCATTER)){
			return new AdvancedScatterProperties();
		}
		else{
			throw new RuntimeException("Type "+type+" not handled");
		}
	}

	public static SeriesProperties getSeriesProperties(String type, ExtChartEditor editor, Series serie, Shell comp){
		logger.debug("IN");
		logger.debug("get Series Properties designer for type "+type);
		if(type.equals(ExtChartConstants.EXT_CHART_TYPE_AREA))
		{
			SeriesProperties seriesProperties =new SeriesAreaProperties(editor, comp); 
			seriesProperties.setSerie(serie);
			return seriesProperties;
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_BAR_AND_LINE)){
			SeriesProperties seriesProperties =new SeriesBarAndLineProperties(editor, comp);
			seriesProperties.setSerie(serie);
			return seriesProperties;
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_GAUGE)){
			SeriesProperties seriesProperties =new SeriesGaugeProperties(editor, comp);
			seriesProperties.setSerie(serie);
			return seriesProperties;
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_PIE)){
			SeriesProperties seriesProperties =new SeriesPieProperties(editor, comp);
			seriesProperties.setSerie(serie);
			return seriesProperties;
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_RADAR)){
			SeriesProperties seriesProperties =new SeriesRadarProperties(editor, comp);
			seriesProperties.setSerie(serie);
			return seriesProperties;
		}
		else if(type.equals(ExtChartConstants.EXT_CHART_TYPE_SCATTER)){
			SeriesProperties seriesProperties =new SeriesScatterProperties(editor, comp);
			seriesProperties.setSerie(serie);
			return seriesProperties;
		}
		else{
			throw new RuntimeException("Type "+type+" not handled");
		}
	}

}
