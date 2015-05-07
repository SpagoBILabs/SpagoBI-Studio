/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.editors.model.dashboard;

import it.eng.spagobi.studio.dashboard.editors.DashboardEditor;
import it.eng.spagobi.studio.dashboard.editors.model.dashboard.DashboardModel.Dimension;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardModelFactory {

	private static Logger logger = LoggerFactory.getLogger(DashboardEditor.class);

	
	public static DashboardModel createDashboardModel(IFile file) throws Exception  {
		DashboardModel model = new DashboardModel();
		InputStream templateIs = null;
		InputStream configurationIs = null;
		try {
			// reads the template file
			templateIs = file.getContents();
			SAXReader reader = new SAXReader();
			Document templateDocument = reader.read(templateIs);
			
			// reads the dashboards configuration file
			Document configurationDocument = null;
			try {
				configurationIs = DashboardModel.getInputStreamFromResource(DashboardModel.DASHBOARD_INFO_FILE);
				configurationDocument = reader.read(configurationIs);
			} catch (Exception e) {
				throw new Exception("Error while reading " + DashboardModel.DASHBOARD_INFO_FILE + " file: " + e.getMessage());
			}
			
			// finds the movie
			Node dashboard = templateDocument.selectSingleNode("//DASHBOARD");
			if (dashboard == null) {
				throw new Exception("xml not valid");
			}
			String movie = dashboard.valueOf("@movie");
			if (movie == null || movie.trim().equals("")) {
				// TODO manage movie not found
				throw new Exception("Movie not found");
			}
			model.setMovie(movie);
			
			// finds the type
			String type = model.getDashboardTypeForMovie(configurationDocument);
			model.setType(type);
			
			// finds the displayTitleBar
			String displayTitleBarStr = dashboard.valueOf("@displayTitleBar");
			// TODO control default value
			boolean displayTitleBar = Boolean.parseBoolean(displayTitleBarStr);
			model.setDisplayTitleBar(displayTitleBar);
			
			// finds the dimension
			Node dimensioneNode = templateDocument.selectSingleNode("//DASHBOARD/DIMENSION");
			if (dimensioneNode == null) {
				// TODO manage exception
				throw new Exception("Dimension is missing");
			}
			String widthStr = dimensioneNode.valueOf("@width");
			String heightStr = dimensioneNode.valueOf("@height");
			int width;
			int height;
			try {
				width = Integer.parseInt(widthStr);
				height = Integer.parseInt(heightStr);
			} catch (NumberFormatException nfe) {
				// TODO manage exception
				throw new Exception("Dimension not valid");
			}
			Dimension dimension = model.new Dimension(width, height);
			model.setDimension(dimension);
			
			// finds data
			Node dataNode = templateDocument.selectSingleNode("//DASHBOARD/DATA");
			String url = dataNode.valueOf("@url");
			List dataParametersList = templateDocument.selectNodes("//DASHBOARD/DATA/PARAMETER");
			if (dataParametersList == null || dataParametersList.size() == 0) {
				// TODO manage exception
				//throw new Exception("missing data parameters");
			}
			Data data = new Data(url, movie, configurationDocument);
			for (int i = 0; i < dataParametersList.size(); i++) {
				Node node = (Node) dataParametersList.get(i);
				String name = node.valueOf("@name");
				String value = node.valueOf("@value");
				data.setParameterValue(name, value);
			}
			model.setData(data);
			
			// finds configuration parameters
			Configuration configuration = null;
		/*	if (movie.equals("sbigrid_jsd.lzx.swf")) {
				List confParametersList = templateDocument.selectNodes("//DASHBOARD/CONFIGURATION/PARAMETERS/PARAMETER");
				if (confParametersList == null || confParametersList.size() == 0) {
					// TODO manage exception
					throw new Exception("missing configuration parameters");
				}
				configuration = new GridConfiguration(movie, configurationDocument);
				for (int i = 0; i < confParametersList.size(); i++) {
					Node node = (Node) confParametersList.get(i);
					String name = node.valueOf("@name");
					String value = node.valueOf("@value");
					configuration.setParameterValue(name, value);
				}
				// link columns
				List linkColumnsList = templateDocument.selectNodes("//DASHBOARD/CONFIGURATION/LINKCOLUMNS/COLUMN");
				LinkColumn[] linkColumns = new LinkColumn[linkColumnsList.size()];
				for (int i = 0; i < linkColumnsList.size(); i++) {
					Node node = (Node) linkColumnsList.get(i);
					String index = node.valueOf("@index");
					String onlyheader = node.valueOf("@onlyheader");
					String fixedquerystring = node.valueOf("@fixedquerystring");
					String prefixvalue = node.valueOf("@prefixvalue");
					LinkColumn linkColumn = new LinkColumn();
					linkColumn.setIndex(Integer.parseInt(index));
					linkColumn.setOnlyheader(Boolean.parseBoolean(onlyheader));
					linkColumn.setFixedquerystring(fixedquerystring);
					linkColumn.setPrefixvalue(prefixvalue);
					linkColumns[i] = linkColumn;
				}
				((GridConfiguration) configuration).setLinkColumns(linkColumns);
				
				// light columns
				List lightColumnsList = templateDocument.selectNodes("//DASHBOARD/CONFIGURATION/LINKCOLUMNS/COLUMN");
				LightColumn[] lightColumns = new LightColumn[lightColumnsList.size()];
				for (int i = 0; i < lightColumnsList.size(); i++) {
					Node node = (Node) lightColumnsList.get(i);
					String index = node.valueOf("@index");
					String defaultcolor = node.valueOf("@defaultcolor");
					String defaulttooltip = node.valueOf("@defaulttooltip");
					List conditionsList = node.selectNodes("CONDITIONS/CONDITION");
					Condition[] conditions = new Condition[conditionsList.size()];
					for (int j = 0; j < conditions.length; j++) {
						Node aCondition = (Node) conditionsList.get(j);
						Condition condition = new Condition();
						condition.setConditioncolor(node.valueOf("@conditioncolor"));
						condition.setValue1(node.valueOf("@value1"));
						condition.setValue2(node.valueOf("@value2"));
						condition.setOperator(node.valueOf("@operator"));
						condition.setTooltip(node.valueOf("@tooltip"));
						condition.setShowvalueintotooltip(Boolean.parseBoolean(node.valueOf("@conditioncolor")));
						conditions[j] = condition;
					}
					LightColumn lightColumn = new LightColumn();
					lightColumn.setIndex(Integer.parseInt(index));
					lightColumn.setDefaultcolor(defaultcolor);
					lightColumn.setDefaulttooltip(defaulttooltip);
					lightColumn.setConditions(conditions);
					lightColumns[i] = lightColumn;
				}
				((GridConfiguration) configuration).setLightColumns(lightColumns);
				
				// name columns
				List nameColumnsList = templateDocument.selectNodes("//DASHBOARD/CONFIGURATION/NAMECOLUMNS/COLUMN");
				NameColumn[] nameColumns = new NameColumn[nameColumnsList.size()];
				for (int i = 0; i < nameColumnsList.size(); i++) {
					Node node = (Node) nameColumnsList.get(i);
					String index = node.valueOf("@index");
					String assignedName = node.valueOf("@name");
					NameColumn nameColumn = new NameColumn();
					nameColumn.setIndex(Integer.parseInt(index));
					nameColumn.setAssignedName(assignedName);
					nameColumns[i] = nameColumn;
				}
				((GridConfiguration) configuration).setNameColumns(nameColumns);
				
				// dimension columns
				List dimensionColumnsList = templateDocument.selectNodes("//DASHBOARD/CONFIGURATION/DIMENSIONCOLUMNS/COLUMN");
				DimensionColumn[] dimensionColumns = new DimensionColumn[dimensionColumnsList.size()];
				for (int i = 0; i < dimensionColumnsList.size(); i++) {
					Node node = (Node) dimensionColumnsList.get(i);
					String index = node.valueOf("@index");
					String columnWidth = node.valueOf("@width");
					DimensionColumn dimensionColumn = new DimensionColumn();
					dimensionColumn.setIndex(Integer.parseInt(index));
					dimensionColumn.setWidth(Integer.parseInt(columnWidth));
					dimensionColumns[i] = dimensionColumn;
				}
				((GridConfiguration) configuration).setDimensionColumns(dimensionColumns);
				
			} else {
				*/
			List confParametersList = templateDocument.selectNodes("//DASHBOARD/CONF/PARAMETER");
				if (confParametersList == null || confParametersList.size() == 0) {
					// TODO manage exception
					logger.warn("missing configuration parameters");
					//throw new Exception("missing configuration parameters");
				}
				configuration = new Configuration(movie, configurationDocument);
				for (int i = 0; i < confParametersList.size(); i++) {
					Node node = (Node) confParametersList.get(i);
					String name = node.valueOf("@name");
					String value = node.valueOf("@value");
					configuration.setParameterValue(name, value);
				}
			//}
			model.setConfiguration(configuration);
			
			// TODO change to service from spagobi server
			Lov lov = new Lov();
			lov.setColumns(new String[]{"Colonna 0", "Colonna 1", "Colonna 3", "Colonna 4"});
			model.setLov(lov);
			
		} finally {
			if (templateIs != null) templateIs.close();
			if (configurationIs != null) configurationIs.close();
		}
		
		return model;
	}
	
}
