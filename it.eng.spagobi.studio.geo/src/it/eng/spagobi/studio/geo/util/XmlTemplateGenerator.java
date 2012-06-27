/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.util;

import it.eng.spagobi.studio.geo.editors.model.geo.Colours;
import it.eng.spagobi.studio.geo.editors.model.geo.Column;
import it.eng.spagobi.studio.geo.editors.model.geo.CrossNavigation;
import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.Defaults;
import it.eng.spagobi.studio.geo.editors.model.geo.Format;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiSettings;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchies;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchy;
import it.eng.spagobi.studio.geo.editors.model.geo.KPI;
import it.eng.spagobi.studio.geo.editors.model.geo.Label;
import it.eng.spagobi.studio.geo.editors.model.geo.Labels;
import it.eng.spagobi.studio.geo.editors.model.geo.Layer;
import it.eng.spagobi.studio.geo.editors.model.geo.Layers;
import it.eng.spagobi.studio.geo.editors.model.geo.Level;
import it.eng.spagobi.studio.geo.editors.model.geo.Link;
import it.eng.spagobi.studio.geo.editors.model.geo.LinkParam;
import it.eng.spagobi.studio.geo.editors.model.geo.MapProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.MapRenderer;
import it.eng.spagobi.studio.geo.editors.model.geo.Measures;
import it.eng.spagobi.studio.geo.editors.model.geo.Metadata;
import it.eng.spagobi.studio.geo.editors.model.geo.Param;
import it.eng.spagobi.studio.geo.editors.model.geo.Tresholds;
import it.eng.spagobi.studio.geo.editors.model.geo.Window;
import it.eng.spagobi.studio.geo.editors.model.geo.Windows;
import it.eng.spagobi.studio.geo.util.xml.CdataPrettyPrintWriter;
import it.eng.spagobi.studio.geo.util.xml.ParamConverter;

import java.io.Writer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class XmlTemplateGenerator {


	public static void setAlias(XStream xstream){

		xstream.registerConverter(new ParamConverter());
		xstream.alias("MAP", GEODocument.class);

		xstream.aliasField("MAP_PROVIDER", GEODocument.class, "mapProvider"); 
		xstream.useAttributeFor(MapProvider.class, "className");
		xstream.aliasField("class_name", MapProvider.class, "className");
		xstream.useAttributeFor(MapProvider.class, "mapName");
		xstream.aliasField("map_name", MapProvider.class, "mapName");

		xstream.aliasField("DATAMART_PROVIDER", GEODocument.class, "datamartProvider"); 
		xstream.useAttributeFor(DatamartProvider.class, "className");
		xstream.aliasField("class_name", DatamartProvider.class, "className");
		xstream.useAttributeFor(DatamartProvider.class, "hierarchy");
		xstream.aliasField("hierarchy", DatamartProvider.class, "hierarchy");
		xstream.useAttributeFor(DatamartProvider.class, "level");
		xstream.aliasField("level", DatamartProvider.class, "level");
		/**figli di datamart provider**/
		xstream.aliasField("CROSS_NAVIGATION", DatamartProvider.class, "crossNavigation"); 
		xstream.addImplicitCollection(CrossNavigation.class, "links", "LINK", Link.class);
		xstream.useAttributeFor(Link.class, "hierarchy");
		xstream.aliasField("hierarchy", Link.class, "hierarchy");
		xstream.useAttributeFor(Link.class, "level");
		xstream.aliasField("level", Link.class, "level");
		xstream.addImplicitCollection(Link.class, "param", "PARAM", LinkParam.class);
		xstream.omitField(Link.class, "id");
		xstream.useAttributeFor(LinkParam.class, "name");
		xstream.aliasField("name", LinkParam.class, "name");
		xstream.useAttributeFor(LinkParam.class, "value");
		xstream.aliasField("value", LinkParam.class, "value");
		xstream.useAttributeFor(LinkParam.class, "scope");
		xstream.aliasField("scope", LinkParam.class, "scope");
		xstream.useAttributeFor(LinkParam.class, "type");
		xstream.aliasField("type", LinkParam.class, "type");

		xstream.omitField(DatamartProvider.class, "DATASET");
/*		xstream.aliasField("DATASET", DatamartProvider.class, "dataset"); 
		xstream.aliasField("QUERY", Dataset.class, "query");

		xstream.aliasField("DATASOURCE", Dataset.class, "datasource");
		xstream.useAttributeFor(Datasource.class, "type");
		xstream.aliasField("type", Datasource.class, "type");
		xstream.useAttributeFor(Datasource.class, "driver");
		xstream.aliasField("driver", Datasource.class, "driver");
		xstream.useAttributeFor(Datasource.class, "url");
		xstream.aliasField("url", Datasource.class, "url");
		xstream.useAttributeFor(Datasource.class, "user");
		xstream.aliasField("user", Datasource.class, "user");
		xstream.useAttributeFor(Datasource.class, "password");
		xstream.aliasField("password", Datasource.class, "password");*/

		xstream.aliasField("METADATA", DatamartProvider.class, "metadata"); 
		xstream.useAttributeFor(Metadata.class, "dataset");
		xstream.aliasField("dataset", Metadata.class, "dataset");
		xstream.addImplicitCollection(Metadata.class, "column", "COLUMN", Column.class);

		xstream.useAttributeFor(Column.class, "type");
		xstream.aliasField("type", Column.class, "type");
		xstream.useAttributeFor(Column.class, "columnId");
		xstream.aliasField("column_id", Column.class, "columnId");
		xstream.useAttributeFor(Column.class, "hierarchy");
		xstream.aliasField("hierarchy", Column.class, "hierarchy");
		xstream.useAttributeFor(Column.class, "level");
		xstream.aliasField("level", Column.class, "level");
		xstream.useAttributeFor(Column.class, "aggFunction");
		xstream.aliasField("agg_func", Column.class, "aggFunction");
		xstream.useAttributeFor(Column.class, "choosenForTemplate");
		xstream.aliasField("choosenForTemplate", Column.class, "choosenForTemplate");

		xstream.aliasField("HIERARCHIES", DatamartProvider.class, "hierarchies");
		xstream.addImplicitCollection(Hierarchies.class, "hierarchy", "HIERARCHY", Hierarchy.class);

		xstream.useAttributeFor(Hierarchy.class, "name");
		xstream.aliasField("name", Hierarchy.class, "name");
		xstream.useAttributeFor(Hierarchy.class, "type");
		xstream.aliasField("type", Hierarchy.class, "type");
		xstream.addImplicitCollection(Hierarchy.class, "levels", "LEVEL", Level.class);

		xstream.useAttributeFor(Level.class, "name");
		xstream.aliasField("name", Level.class, "name");
		xstream.useAttributeFor(Level.class, "columnId");
		xstream.aliasField("column_id", Level.class, "columnId");
		xstream.useAttributeFor(Level.class, "columnDesc");
		xstream.aliasField("column_desc", Level.class, "columnDesc");
		xstream.useAttributeFor(Level.class, "featureName");
		xstream.aliasField("feature_name", Level.class, "featureName");

		xstream.aliasField("MAP_RENDERER", GEODocument.class, "mapRenderer");
		xstream.useAttributeFor(MapRenderer.class, "className");
		xstream.aliasField("class_name", MapRenderer.class, "className");
		xstream.aliasField("MEASURES", MapRenderer.class, "measures");
		xstream.useAttributeFor(Measures.class, "defaultKpi");
		xstream.aliasField("default_kpi", Measures.class, "defaultKpi");
		xstream.addImplicitCollection(Measures.class, "kpi", "KPI", KPI.class);
		xstream.useAttributeFor(KPI.class, "columnId");
		xstream.aliasField("column_id", KPI.class, "columnId");	
		xstream.useAttributeFor(KPI.class, "description");
		xstream.aliasField("description", KPI.class, "description");	
		xstream.useAttributeFor(KPI.class, "aggFunct");
		xstream.aliasField("agg_funct", KPI.class, "aggFunct");	
		xstream.useAttributeFor(KPI.class, "color");
		xstream.aliasField("colour", KPI.class, "color");	
		xstream.aliasField("TRESHOLDS", KPI.class, "tresholds");
		xstream.useAttributeFor(Tresholds.class, "type");
		xstream.aliasField("type", Tresholds.class, "type");
		xstream.useAttributeFor(Tresholds.class, "lbValue");
		xstream.aliasField("lb_value", Tresholds.class, "lbValue");
		xstream.useAttributeFor(Tresholds.class, "ubValue");
		xstream.aliasField("ub_value", Tresholds.class, "ubValue");
		xstream.aliasField("PARAM", Tresholds.class, "param");
		xstream.useAttributeFor(Param.class, "name");
		xstream.aliasField("name", Param.class, "name");
		xstream.useAttributeFor(Param.class, "value");
		xstream.aliasField("value", Param.class, "value");

		xstream.aliasField("COLOURS", KPI.class, "colours");
		xstream.useAttributeFor(Colours.class, "type");
		xstream.aliasField("type", Colours.class, "type");
		xstream.useAttributeFor(Colours.class, "outboundColour");
		xstream.aliasField("outbound_colour", Colours.class, "outboundColour");
		xstream.useAttributeFor(Colours.class, "nullValuesColor");
		xstream.aliasField("null_values_color", Colours.class, "nullValuesColor");
		xstream.aliasField("PARAM", Colours.class, "param");
		xstream.useAttributeFor(Param.class, "name");
		xstream.aliasField("name", Param.class, "name");
		xstream.useAttributeFor(Param.class, "value");
		xstream.aliasField("value", Param.class, "value");

		xstream.aliasField("LAYERS", MapRenderer.class, "layers");
		xstream.useAttributeFor(Layers.class, "mapName");
		xstream.aliasField("mapName", Layers.class, "mapName");
		xstream.addImplicitCollection(Layers.class, "layer", "LAYER", Layer.class);
		xstream.useAttributeFor(Layer.class, "name");
		xstream.aliasField("name", Layer.class, "name");
		xstream.useAttributeFor(Layer.class, "defaultFillColour");
		xstream.aliasField("default_fill_color", Layer.class, "defaultFillColour");
		xstream.useAttributeFor(Layer.class, "description");
		xstream.aliasField("description", Layer.class, "description");
		xstream.useAttributeFor(Layer.class, "selected");
		xstream.aliasField("selected", Layer.class, "selected");
		xstream.useAttributeFor(Layer.class, "choosenForTemplate");
		xstream.aliasField("choosenForTemplate", Layer.class, "choosenForTemplate");

		//gui settings
		xstream.aliasField("GUI_SETTINGS", MapRenderer.class, "guiSettings");
		xstream.addImplicitCollection(GuiSettings.class, "params", "PARAM", GuiParam.class);
		xstream.useAttributeFor(GuiParam.class, "name");
		xstream.aliasField("name", GuiParam.class, "name");

		xstream.aliasField("WINDOWS", GuiSettings.class, "windows");
		xstream.addImplicitCollection(Windows.class, "window", "WINDOW", Window.class);
		xstream.useAttributeFor(Window.class, "name");
		xstream.aliasField("name", Window.class, "name");
		xstream.addImplicitCollection(Window.class, "params", "PARAM", GuiParam.class);
		xstream.useAttributeFor(GuiParam.class, "name");
		xstream.aliasField("name", GuiParam.class, "name");

		xstream.aliasField("DEFAULTS", Windows.class, "defaults");
		xstream.addImplicitCollection(Defaults.class, "params", "PARAM", GuiParam.class);
		xstream.useAttributeFor(GuiParam.class, "name");
		xstream.aliasField("name", GuiParam.class, "name");

		xstream.aliasField("LABELS", GuiSettings.class, "labels");
		xstream.addImplicitCollection(Labels.class, "label", "LABEL", Label.class);
		xstream.useAttributeFor(Label.class, "position");
		xstream.aliasField("position", Label.class, "position");
		xstream.useAttributeFor(Label.class, "className");
		xstream.aliasField("class_name", Label.class, "className");
		xstream.aliasField("TEXT", Label.class, "text");
		xstream.addImplicitCollection(Label.class, "params", "PARAM", GuiParam.class);
		xstream.aliasField("FORMAT", Label.class, "format");
		xstream.useAttributeFor(Format.class, "day");
		xstream.aliasField("day", Format.class, "day");
		xstream.useAttributeFor(Format.class, "hour");
		xstream.aliasField("hour", Format.class, "hour");



	}


	public static String transformToXml(Object bean) {

		final XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
		//XStream xstream = new XStream(new DomDriver("UTF-8", replacer));
		XStream xstream = new XStream(new DomDriver("UTF-8", replacer) {
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new CdataPrettyPrintWriter(out, replacer) {
					protected void writeText(QuickWriter writer, String text) {
						writer.write(text);                         
					}
				};
			}
		}); 

		xstream.setMode(XStream.NO_REFERENCES);

		setAlias(xstream);	

		String xml = xstream.toXML(bean);
//		System.out.println(xml);
		return xml;
	}


	public static GEODocument readXml(IFile file) throws CoreException{
		XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
		XStream xstream = new XStream(new DomDriver("UTF-8", replacer));

		setAlias(xstream);	
		GEODocument objFromXml = (GEODocument)xstream.fromXML(file.getContents());

		return objFromXml;
	}



	public static void main(String[] args) {

	}
}
