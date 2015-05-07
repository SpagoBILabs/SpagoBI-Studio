/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.model;

import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.AxesList;
import it.eng.spagobi.studio.extchart.model.bo.AxesStyle;
import it.eng.spagobi.studio.extchart.model.bo.Colors;
import it.eng.spagobi.studio.extchart.model.bo.Dataset;
import it.eng.spagobi.studio.extchart.model.bo.Drill;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Highlight;
import it.eng.spagobi.studio.extchart.model.bo.Label;
import it.eng.spagobi.studio.extchart.model.bo.LabelsStyle;
import it.eng.spagobi.studio.extchart.model.bo.Legend;
import it.eng.spagobi.studio.extchart.model.bo.MarkerConfig;
import it.eng.spagobi.studio.extchart.model.bo.Param;
import it.eng.spagobi.studio.extchart.model.bo.ParamList;
import it.eng.spagobi.studio.extchart.model.bo.Segment;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.model.bo.SeriesList;
import it.eng.spagobi.studio.extchart.model.bo.StyleSeries;
import it.eng.spagobi.studio.extchart.model.bo.StyleSubTitle;
import it.eng.spagobi.studio.extchart.model.bo.StyleTitle;
import it.eng.spagobi.studio.extchart.model.bo.SubTitle;
import it.eng.spagobi.studio.extchart.model.bo.Tips;
import it.eng.spagobi.studio.extchart.model.bo.Title;
import it.eng.spagobi.studio.extchart.utils.AxesUtilities;
import it.eng.spagobi.studio.utils.exceptions.SavingEditorException;

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
		logger.debug("IN");
		xstream.alias("EXTCHART", ExtChart.class);
			
//		xstream.useAttributeFor(ExtChart.class, ExtChart.AXES_LIST);
//		xstream.aliasField("AXES_LIST", ExtChart.class, ExtChart.AXES_LIST);
//
//		xstream.useAttributeFor(ExtChart.class, ExtChart.AXES_STYLE);
//		xstream.aliasField("AXES_STYLE", ExtChart.class, ExtChart.AXES_STYLE);

		xstream.useAttributeFor(ExtChart.class, ExtChart.ANIMATE);
		xstream.aliasField(ExtChart.ANIMATE, ExtChart.class, ExtChart.ANIMATE);

//		xstream.useAttributeFor(ExtChart.class, ExtChart.COLORS);
//		xstream.aliasField("COLORS", ExtChart.class, ExtChart.COLORS);
//
//		xstream.useAttributeFor(ExtChart.class, ExtChart.DRILL);
//		xstream.aliasField("DRILL", ExtChart.class, ExtChart.DRILL);			

		xstream.useAttributeFor(ExtChart.class, ExtChart.HEIGHT);
		xstream.aliasField(ExtChart.HEIGHT, ExtChart.class, ExtChart.HEIGHT);	
		
//		xstream.useAttributeFor(ExtChart.class, ExtChart.LABEL_STYLE);
//		xstream.aliasField("LABEL_STYLE", ExtChart.class, ExtChart.LABEL_STYLE);
//
//		xstream.useAttributeFor(ExtChart.class, ExtChart.LEGEND);
//		xstream.aliasField("LEGEND", ExtChart.class, ExtChart.LEGEND);

		xstream.useAttributeFor(ExtChart.class, ExtChart.REFRESH_TIME);
		xstream.aliasField(ExtChart.REFRESH_TIME, ExtChart.class, ExtChart.REFRESH_TIME);

//		xstream.useAttributeFor(ExtChart.class, ExtChart.SERIES_LIST);
//		xstream.aliasField("SERIES_LIST", ExtChart.class, ExtChart.SERIES_LIST);

		xstream.useAttributeFor(ExtChart.class, ExtChart.SHADOW);
		xstream.aliasField(ExtChart.SHADOW, ExtChart.class, ExtChart.SHADOW);

//		xstream.useAttributeFor(ExtChart.class, ExtChart.SUBTITLE);
//		xstream.aliasField("SUBTITLE", ExtChart.class, ExtChart.SUBTITLE);
//
//		xstream.useAttributeFor(ExtChart.class, ExtChart.TITLE);
//		xstream.aliasField("TITLE", ExtChart.class, ExtChart.TITLE);

		xstream.useAttributeFor(ExtChart.class, ExtChart.WIDTH);
		xstream.aliasField(ExtChart.WIDTH, ExtChart.class, ExtChart.WIDTH);
		
		xstream.useAttributeFor(ExtChart.class, ExtChart.TYPE);
		xstream.aliasField(ExtChart.TYPE, ExtChart.class, ExtChart.TYPE);

		// -------------------------------------------------------
		
		xstream.aliasField("DATASET", ExtChart.class, "dataset");
		xstream.useAttributeFor(Dataset.class, Dataset.LABEL);
		xstream.aliasField(Dataset.LABEL, Dataset.class, Dataset.LABEL);
		
		// -------------------------------------------------------
		
		xstream.aliasField("TITLE", ExtChart.class, "title");
		
		xstream.useAttributeFor(Title.class, Title.STYLE);
		xstream.aliasField(Title.STYLE, Title.class, Title.STYLE);	

		xstream.useAttributeFor(Title.class, Title.TEXT);
		xstream.aliasField(Title.TEXT, Title.class, Title.TEXT);	
		
		// -------------------------------------------------------
		
		xstream.aliasField("AXES", AxesList.class, "axes");
		
		xstream.useAttributeFor(Axes.class, Axes.FIELDS);
		xstream.aliasField(Axes.FIELDS, Axes.class, Axes.FIELDS);

		xstream.useAttributeFor(Axes.class, Axes.FIELDS_LIST);
		xstream.aliasField(Axes.FIELDS_LIST, Axes.class, Axes.FIELDS_LIST);

		xstream.useAttributeFor(Axes.class, Axes.MINIMUM);
		xstream.aliasField(Axes.MINIMUM, Axes.class, Axes.MINIMUM);
		
		xstream.useAttributeFor(Axes.class, Axes.MAXIMUM);
		xstream.aliasField(Axes.MAXIMUM, Axes.class, Axes.MAXIMUM);
		
		xstream.useAttributeFor(Axes.class, Axes.STEPS);
		xstream.aliasField(Axes.STEPS, Axes.class, Axes.STEPS);
		
		xstream.useAttributeFor(Axes.class, Axes.MARGIN);
		xstream.aliasField(Axes.MARGIN, Axes.class, Axes.MARGIN);		

		xstream.useAttributeFor(Axes.class, Axes.POSITION);
		xstream.aliasField(Axes.POSITION, Axes.class, Axes.POSITION);

		xstream.useAttributeFor(Axes.class, Axes.TITLE);
		xstream.aliasField(Axes.TITLE, Axes.class, Axes.TITLE);

		xstream.useAttributeFor(Axes.class, Axes.TYPE);
		xstream.aliasField(Axes.TYPE, Axes.class, Axes.TYPE);

		xstream.useAttributeFor(Axes.class, Axes.GRID);
		xstream.aliasField(Axes.GRID, Axes.class, Axes.GRID);

		// -------------------------------------------------------
		
		xstream.aliasField("AXES_LIST", ExtChart.class, "axesList");
		xstream.addImplicitCollection(AxesList.class, "axes", "AXES", Axes.class);
		
		// -------------------------------------------------------
		
		xstream.aliasField("AXES_STYLE", ExtChart.class, "axesStyle");
		
		xstream.useAttributeFor(AxesStyle.class, AxesStyle.COLOR);
		xstream.aliasField(AxesStyle.COLOR, AxesStyle.class, AxesStyle.COLOR);

		xstream.useAttributeFor(AxesStyle.class, AxesStyle.FONT_FAMILY);
		xstream.aliasField(AxesStyle.FONT_FAMILY, AxesStyle.class, AxesStyle.FONT_FAMILY);

		xstream.useAttributeFor(AxesStyle.class, AxesStyle.FONT_SIZE);
		xstream.aliasField(AxesStyle.FONT_SIZE, AxesStyle.class, AxesStyle.FONT_SIZE);

		xstream.useAttributeFor(AxesStyle.class, AxesStyle.FONT_WEIGHT);
		xstream.aliasField(AxesStyle.FONT_WEIGHT, AxesStyle.class, AxesStyle.FONT_WEIGHT);

		
		// -------------------------------------------------------
		
		xstream.aliasField("LABELS_STYLE", ExtChart.class, "labelsStyle");
	
		xstream.useAttributeFor(LabelsStyle.class, LabelsStyle.COLOR);
		xstream.aliasField(LabelsStyle.COLOR, LabelsStyle.class, LabelsStyle.COLOR);

		xstream.useAttributeFor(LabelsStyle.class, LabelsStyle.FONT_FAMILY);
		xstream.aliasField(LabelsStyle.FONT_FAMILY, LabelsStyle.class, LabelsStyle.FONT_FAMILY);

		xstream.useAttributeFor(LabelsStyle.class, LabelsStyle.FONT_SIZE);
		xstream.aliasField(LabelsStyle.FONT_SIZE, LabelsStyle.class, LabelsStyle.FONT_SIZE);

		xstream.useAttributeFor(LabelsStyle.class, LabelsStyle.FONT_WEIGHT);
		xstream.aliasField(LabelsStyle.FONT_WEIGHT, LabelsStyle.class, LabelsStyle.FONT_WEIGHT);		

		
		// -------------------------------------------------------
		
		xstream.aliasField("COLORS", ExtChart.class, "colors");
		
		xstream.useAttributeFor(Colors.class, Colors.COLOR);
		xstream.aliasField(Colors.COLOR, Colors.class, Colors.COLOR);

		xstream.useAttributeFor(Colors.class, Colors.BASE_COLOR);
		xstream.aliasField(Colors.BASE_COLOR, Colors.class, Colors.BASE_COLOR);
		
		// -------------------------------------------------------
		
		xstream.aliasField("DRILL", ExtChart.class, "drill");
		
		xstream.useAttributeFor(Drill.class, Drill.DOCUMENT);
		xstream.aliasField(Drill.DOCUMENT, Drill.class, Drill.DOCUMENT);
		
		// -------------------------------------------------------
		
		//xstream.aliasField("HIGHLIGHT", Series.class, "Highlight");

		//xstream.useAttributeFor(Highlight.class, Highlight.SEGMENT);
		//xstream.aliasField(Highlight.SEGMENT, Highlight.class, Highlight.SEGMENT);

		// -------------------------------------------------------
		
		xstream.aliasField("LABEL", Series.class, "label");
		
		xstream.useAttributeFor(Label.class, Label.CONTRAST);
		xstream.aliasField(Label.CONTRAST, Label.class, Label.CONTRAST);	

		xstream.useAttributeFor(Label.class, Label.DISPLAY);
		xstream.aliasField(Label.DISPLAY, Label.class, Label.DISPLAY);	

		xstream.useAttributeFor(Label.class, Label.FIELD);
		xstream.aliasField(Label.FIELD, Label.class, Label.FIELD);	

		xstream.useAttributeFor(Label.class, Label.FONT);
		xstream.aliasField(Label.FONT, Label.class, Label.FONT);	

		xstream.useAttributeFor(Label.class, Label.ORIENTATION);
		xstream.aliasField(Label.ORIENTATION, Label.class, Label.ORIENTATION);	

		xstream.useAttributeFor(Label.class, Label.COLOR);
		xstream.aliasField(Label.COLOR, Label.class, Label.COLOR);	

		xstream.useAttributeFor(Label.class, Label.TEXT_ANCHOR);
		xstream.aliasField("text-anchor", Label.class, Label.TEXT_ANCHOR);	

		
		// -------------------------------------------------------
		
		xstream.aliasField("LEGEND", ExtChart.class, "legend");
		
		xstream.useAttributeFor(Legend.class, Legend.POSITION);
		xstream.aliasField(Legend.POSITION, Legend.class, Legend.POSITION);	

		// -------------------------------------------------------
		
		xstream.aliasField("MARKER_CONFIG", Series.class, "markerConfig");

		xstream.useAttributeFor(MarkerConfig.class, MarkerConfig.RADIUS);
		xstream.aliasField(MarkerConfig.RADIUS, MarkerConfig.class, MarkerConfig.RADIUS);	

		xstream.useAttributeFor(MarkerConfig.class, MarkerConfig.SIZE);
		xstream.aliasField(MarkerConfig.SIZE, MarkerConfig.class, MarkerConfig.SIZE);	
		
		xstream.useAttributeFor(MarkerConfig.class, MarkerConfig.TYPE);
		xstream.aliasField(MarkerConfig.TYPE, MarkerConfig.class, MarkerConfig.TYPE);	
	
		
		// -------------------------------------------------------
//		xstream.aliasField("SERIES_LIST", ExtChart.class, "seriesList");
//		xstream.addImplicitCollection(SeriesList.class, "series", "SERIES", Series.class);

		//		xstream.aliasField("PARAM_LIST", Drill.class, "paramList"); 
//		xstream.addImplicitCollection(ParamList.class, "params", "PARAM", Param.class);

		xstream.aliasField("PARAM_LIST", Drill.class, "paramList"); 
		xstream.addImplicitCollection(ParamList.class, "params", "PARAM", Param.class);
				
		xstream.useAttributeFor(Param.class, "name");
		xstream.aliasField("name", Param.class, "name");	
		xstream.useAttributeFor(Param.class, "type");
		xstream.aliasField("type", Param.class, "type");	
		xstream.useAttributeFor(Param.class, "value");
		xstream.aliasField("value", Param.class, "value");	
		
		// -------------------------------------------------------
		
//		xstream.aliasField("PARAM", ParamList.class, "param");
//		
//		xstream.useAttributeFor(Param.class, Param.TYPE);
//		xstream.aliasField(Param.TYPE, Param.class, Param.TYPE);	
//
//		xstream.useAttributeFor(Param.class, Param.NAME);
//		xstream.aliasField(Param.NAME, Param.class, Param.NAME);	
//
//		xstream.useAttributeFor(Param.class, Param.VALUE);
//		xstream.aliasField(Param.VALUE, Param.class, Param.VALUE);	

		

		// -------------------------------------------------------

		//xstream.aliasField("SEGMENT", Highlight.class, "segment");

		//xstream.useAttributeFor(Segment.class, Segment.MARGIN);
		//xstream.aliasField(Segment.MARGIN, Segment.class, Segment.MARGIN);		

		// -------------------------------------------------------
		
		xstream.aliasField("SERIES", SeriesList.class, "series");

		xstream.useAttributeFor(Series.class, Series.AXIS);
		xstream.aliasField(Series.AXIS, Series.class, Series.AXIS);		

		xstream.useAttributeFor(Series.class, Series.DONUT);
		xstream.aliasField(Series.DONUT, Series.class, Series.DONUT);	

		xstream.useAttributeFor(Series.class, Series.FIELD);
		xstream.aliasField(Series.FIELD, Series.class, Series.FIELD);	

		xstream.useAttributeFor(Series.class, Series.X_FIELD);
		xstream.aliasField(Series.X_FIELD, Series.class, Series.X_FIELD);	

		xstream.useAttributeFor(Series.class, Series.Y_FIELD);
		xstream.aliasField(Series.Y_FIELD, Series.class, Series.Y_FIELD);	

		xstream.useAttributeFor(Series.class, "yFieldList");
		xstream.aliasField(Series.Y_FIELD_LIST, Series.class, "yFieldList");	

		xstream.useAttributeFor(Series.class, Series.SHOW_IN_LEGEND);
		xstream.aliasField(Series.SHOW_IN_LEGEND, Series.class, Series.SHOW_IN_LEGEND);	

		xstream.useAttributeFor(Series.class, Series.TYPE);
		xstream.aliasField(Series.TYPE, Series.class, Series.TYPE);	

		xstream.useAttributeFor(Series.class, Series.HIGHLIGHT);
		xstream.aliasField(Series.HIGHLIGHT, Series.class, Series.HIGHLIGHT);	
		
		xstream.useAttributeFor(Series.class, Series.STACKED);
		xstream.aliasField(Series.STACKED, Series.class, Series.STACKED);

		xstream.useAttributeFor(Series.class, Series.SMOOTH);
		xstream.aliasField(Series.SMOOTH, Series.class, Series.SMOOTH);
		
		xstream.useAttributeFor(Series.class, Series.FILL);
		xstream.aliasField(Series.FILL, Series.class, Series.FILL);
		
		xstream.useAttributeFor(Series.class, Series.GUTTER);
		xstream.aliasField(Series.GUTTER, Series.class, Series.GUTTER);

		xstream.useAttributeFor(Series.class, Series.COLOR);
		xstream.aliasField(Series.COLOR, Series.class, Series.COLOR);

		xstream.useAttributeFor(Series.class, Series.SHOW_MARKERS);
		xstream.aliasField(Series.SHOW_MARKERS, Series.class, Series.SHOW_MARKERS);


		// -------------------------------------------------------
		
		xstream.aliasField("SERIES_LIST", ExtChart.class, "seriesList");
		xstream.addImplicitCollection(SeriesList.class, "series", "SERIES", Series.class);


		// -------------------------------------------------------
		
		xstream.aliasField("STYLE", Series.class, "style");	

		xstream.useAttributeFor(StyleSeries.class, StyleSeries.OPACITY);
		xstream.aliasField(StyleSeries.OPACITY, StyleSeries.class, StyleSeries.OPACITY);	
		
		// -------------------------------------------------------

		
		
		xstream.aliasField("STYLE", SubTitle.class, "style");	

		xstream.useAttributeFor(StyleSubTitle.class, StyleSubTitle.COLOR);
		xstream.aliasField(StyleSubTitle.COLOR, StyleSubTitle.class, StyleSubTitle.COLOR);	
		
		xstream.useAttributeFor(StyleSubTitle.class, StyleSubTitle.FONT_SIZE);
		xstream.aliasField(StyleSubTitle.FONT_SIZE, StyleSubTitle.class, StyleSubTitle.FONT_SIZE);	
		
		xstream.useAttributeFor(StyleSubTitle.class, StyleSubTitle.FONT_WEIGHT);
		xstream.aliasField(StyleSubTitle.FONT_WEIGHT, StyleSubTitle.class, StyleSubTitle.FONT_WEIGHT);	
		
		
		// -------------------------------------------------------

		
		
		xstream.aliasField("STYLE", Title.class, "style");	

		xstream.useAttributeFor(StyleTitle.class, StyleTitle.COLOR);
		xstream.aliasField(StyleTitle.COLOR, StyleTitle.class, StyleTitle.COLOR);	
		
		xstream.useAttributeFor(StyleTitle.class, StyleTitle.FONT_SIZE);
		xstream.aliasField(StyleTitle.FONT_SIZE, StyleTitle.class, StyleTitle.FONT_SIZE);	
		
		xstream.useAttributeFor(StyleTitle.class, StyleTitle.FONT_WEIGHT);
		xstream.aliasField(StyleTitle.FONT_WEIGHT, StyleTitle.class, StyleTitle.FONT_WEIGHT);	
		
		
		// -------------------------------------------------------
		
		xstream.aliasField("SUBTITLE", ExtChart.class, "subTitle");
		
		xstream.useAttributeFor(SubTitle.class, SubTitle.STYLE);
		xstream.aliasField(SubTitle.STYLE, SubTitle.class, SubTitle.STYLE);	

		xstream.useAttributeFor(SubTitle.class, SubTitle.TEXT);
		xstream.aliasField(SubTitle.TEXT, SubTitle.class, SubTitle.TEXT);	

		// -------------------------------------------------------
		
		xstream.aliasField("TIPS", Series.class, "tips");

		xstream.useAttributeFor(Tips.class, Tips.FORMATTER);
		xstream.aliasField(Tips.FORMATTER, Tips.class, Tips.FORMATTER);	

		xstream.useAttributeFor(Tips.class, Tips.HEIGHT);
		xstream.aliasField(Tips.HEIGHT, Tips.class, Tips.HEIGHT);	

		xstream.useAttributeFor(Tips.class, Tips.TRACK_MOUSE);
		xstream.aliasField(Tips.TRACK_MOUSE, Tips.class, Tips.TRACK_MOUSE);	

		xstream.useAttributeFor(Tips.class, Tips.WIDTH);
		xstream.aliasField(Tips.WIDTH, Tips.class, Tips.WIDTH);	
		
		xstream.useAttributeFor(Tips.class, Tips.TEXT);
		xstream.aliasField(Tips.TEXT, Tips.class, Tips.TEXT);	
		
		// -------------------------------------------------------


		xstream.aliasField("HIGHLIGHT", Series.class, "highlightSegments");
		xstream.useAttributeFor(Highlight.class, Highlight.SEGMENT);
		xstream.aliasField(Highlight.SEGMENT, Highlight.class, Highlight.SEGMENT);
		
		xstream.aliasField("SEGMENT", Highlight.class, "segment");

		
		xstream.useAttributeFor(Segment.class, Segment.MARGIN);
		xstream.aliasField(Segment.MARGIN, Segment.class, Segment.MARGIN);			


		logger.debug("OUT");
		
		}


		public static String transformToXml(Object bean) throws SavingEditorException {

			ExtChart extChart = (ExtChart)bean;
			AxesUtilities axesUtilities = new AxesUtilities();
			axesUtilities.updateNumericFields(extChart);
			axesUtilities.updateCategoryFields(extChart);
					
			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
			XStream xstream = new XStream(new DomDriver("ISO-8859-15", replacer)); 
			xstream.setMode(XStream.NO_REFERENCES);
			setAlias(xstream);	
			String xml = xstream.toXML(bean);
			return xml;
		}

/** populate the ExtChart Object from template*/
		public static ExtChart readXml(IFile file) throws CoreException{
			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
			XStream xstream = new XStream(new DomDriver("ISO-8859-15", replacer)); 
			setAlias(xstream);	
			ExtChart objFromXml = (ExtChart)xstream.fromXML(file.getContents());
			return objFromXml;
		}



		public static void main(String[] args) {

		}
	}
