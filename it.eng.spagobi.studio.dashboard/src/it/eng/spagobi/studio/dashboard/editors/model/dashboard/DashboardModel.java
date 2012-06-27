/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.editors.model.dashboard;

import it.eng.spagobi.studio.dashboard.Activator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class DashboardModel {

	public static final String DASHBOARD_INFO_FILE = "it/eng/spagobi/studio/dashboard/editors/model/dashboard/dashboardsInformation.xml";
	
	protected String type;
	protected String movie;
	protected boolean displayTitleBar;
	protected Dimension dimension;
	protected Configuration configuration;
	protected Data data;
	protected Lov lov;

	public DashboardModel() {}
	
	public class Dimension {
		private int width;
		private int height;
		public Dimension () {}
		public Dimension (int width, int height) {
			this.width = width;
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public boolean isDisplayTitleBar() {
		return displayTitleBar;
	}

	public void setDisplayTitleBar(boolean displayTitleBar) {
		this.displayTitleBar = displayTitleBar;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public String toXML() {
		String toReturn = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
			"<DASHBOARD movie='" + movie + "' displayTitleBar='" + displayTitleBar + "'>\n" +
			"    <DIMENSION width='" + dimension.getWidth() + "' height='" + dimension.getHeight() + "' />\n";
		String configurationXML = configuration.toXML();
//		toReturn += configurationXML +
//			"    <DATA url='" + data.getUrl() + "'>\n";
		toReturn += configurationXML +
		"    <DATA url=\"/servlet/AdapterHTTP?ACTION_NAME=GET_DATASET_RESULT\">\n";
		
		Parameter[] dataParameters = data.getParameters();
			for (int i = 0; i < dataParameters.length; i++) {
				Parameter aParameter = dataParameters[i];
				toReturn +=
					"		<PARAMETER name='" + aParameter.getName() + "' value='" + aParameter.getValue() + "' />\n";
			}
		toReturn +=
			"    </DATA>\n" +
			"</DASHBOARD>\n";
		return toReturn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDashboardTypeForMovie(Document document) throws Exception {
		String toReturn = null;
		List dashboards = document.selectNodes("//DASHBOARDS/DASHBOARD");
		if (dashboards == null || dashboards.size() == 0) throw new Exception("No dashboards configured");
		for (int i = 0; i < dashboards.size(); i++) {
			Node dashboard = (Node) dashboards.get(i);
			String movie = dashboard.valueOf("@movie");
			if (this.movie.equals(movie)) {
				toReturn = dashboard.valueOf("@type");
				break;
			}
		}
		return toReturn;
	}
	
	public static List getConfiguredDashboardTypes() throws Exception {
		List toReturn = new ArrayList();
		InputStream is = getInputStreamFromResource(DASHBOARD_INFO_FILE);
		Document document = new SAXReader().read(is);
		List dashboards = document.selectNodes("//DASHBOARDS/DASHBOARD");
		if (dashboards == null || dashboards.size() == 0) throw new Exception("No dashboards configured");
		for (int i = 0; i < dashboards.size(); i++) {
			Node dashboard = (Node) dashboards.get(i);
			String type = dashboard.valueOf("@type");
			if (type == null || type.trim().equals("")) continue;
			toReturn.add(type);
		}
		return toReturn;
	}

	public static String getDashboardTemplatePath(String dashboardType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(DASHBOARD_INFO_FILE);
		Document document = new SAXReader().read(is);
		List dashboards = document.selectNodes("//DASHBOARDS/DASHBOARD");
		if (dashboards == null || dashboards.size() == 0) throw new Exception("No dashboards configured");
		for (int i = 0; i < dashboards.size(); i++) {
			Node dashboard = (Node) dashboards.get(i);
			String type = dashboard.valueOf("@type");
			if (dashboardType.equals(type)) {
				String templatePath = dashboard.valueOf("@templatePath");
				toReturn = templatePath;
				break;
			}
		}
		return toReturn;
	}
	
	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		Bundle b = Platform.getBundle(Activator.PLUGIN_ID);
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}

	public Lov getLov() {
		return lov;
	}

	public void setLov(Lov lov) {
		this.lov = lov;
	}
	
}
