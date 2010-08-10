package it.eng.spagobi.studio.dashboard.editors.model.dashboard;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

public class Data {

	private String url;
	private Parameter[] parameters;
	
	public Data (String url, String movie, Document configurationDocument) {
		this.url = url;
		try {
			this.parameters = getDataParametersForMovie(movie, configurationDocument);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Parameter[] getParameters() {
		return parameters;
	}
	public String getParameterValue(String parameterName) {
		if (parameters == null || parameters.length == 0) return null;
		for (int i = 0; i < parameters.length; i++) {
			Parameter aParameter = parameters[i];
			String name = aParameter.getName();
			if (name.equals(parameterName)) return aParameter.getValue();
		}
		return null;
	}
	public void setParameterValue(String parameterName, String parameterValue) {
		if (parameters == null || parameters.length == 0) return;
		for (int i = 0; i < parameters.length; i++) {
			Parameter aParameter = parameters[i];
			String name = aParameter.getName();
			if (name.equals(parameterName)) aParameter.setValue(parameterValue);
		}
	}
	
	public Parameter[] getDataParametersForMovie(String dashboardMovie, Document document) throws Exception {
		Parameter[] toReturn = null;
		List dashboards = document.selectNodes("//DASHBOARDS/DASHBOARD");
		if (dashboards == null || dashboards.size() == 0) throw new Exception("No dashboards configured");
		for (int i = 0; i < dashboards.size(); i++) {
			Node dashboard = (Node) dashboards.get(i);
			String movie = dashboard.valueOf("@movie");
			if (dashboardMovie.equals(movie)) {
				List configuredParameters = dashboard.selectNodes("DATA/PARAMETER");
				toReturn = new Parameter[configuredParameters.size()];
				for (int j = 0; j < configuredParameters.size(); j++) {
					Node aConfiguredParameter = (Node) configuredParameters.get(j);
					String name = aConfiguredParameter.valueOf("@name");
					String description = aConfiguredParameter.valueOf("@description");
					String typeStr = aConfiguredParameter.valueOf("@type");
					int type;
					if (typeStr.equals("NUMBER")) type = Parameter.NUMBER_TYPE;
					else if (typeStr.equals("STRING")) type = Parameter.STRING_TYPE;
					else if (typeStr.equals("COLOR")) type = Parameter.COLOR_TYPE;
					else if (typeStr.equals("BOOLEAN")) type = Parameter.BOOLEAN_TYPE;
					else throw new Exception("Parameter type for parameter " + name + " not supported");
					toReturn[j] = new Parameter(name, "", description, type);
				}
				break;
			}
		}
		return toReturn;
	}
}
