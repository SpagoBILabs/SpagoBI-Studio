/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.editors.model.dashboard;

import it.eng.spagobi.studio.dashboard.editors.DashboardEditor;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class Configuration {

	protected Parameter[] parameters;

	public Configuration(String movie, Document configurationDocument) {
		try {
			parameters = getConfigurationParametersForMovie(movie, configurationDocument);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Configuration() {
		// TODO Auto-generated constructor stub
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
	public void setParameterValue(String parameterName, String parameterValue) throws Exception {
		if (parameters == null || parameters.length == 0) return;
		for (int i = 0; i < parameters.length; i++) {
			Parameter aParameter = parameters[i];
			String name = aParameter.getName();
			if (name.equals(parameterName)) {
				int type = aParameter.getType();
				switch (type) {
				case Parameter.NUMBER_TYPE:
					try {
						Integer.parseInt(parameterValue);
					} catch (NumberFormatException nfe) {
						throw new Exception("Parameter '" + parameterName + "' is not a valid integer");
					}
					break;
				case Parameter.COLOR_TYPE:
					// TODO check color syntax
					break;
				default:
					break;
				}
				aParameter.setValue(parameterValue);
			}
		}
	}

	public Parameter[] getConfigurationParametersForMovie(String dashboardMovie, Document document) throws Exception {
		Parameter[] toReturn = null;
		List dashboards = document.selectNodes("//DASHBOARDS/DASHBOARD");
		if (dashboards == null || dashboards.size() == 0) throw new Exception("No dashboards configured");
		for (int i = 0; i < dashboards.size(); i++) {
			Node dashboard = (Node) dashboards.get(i);
			String movie = dashboard.valueOf("@movie");
			if (dashboardMovie.equals(movie)) {
				List configuredParameters = dashboard.selectNodes("CONF/PARAMETER");
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

	public void createForm(final DashboardEditor editor, Composite section, FormToolkit toolkit) {
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		section.setLayout(gl);
		Parameter[] parameters = this.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			final Parameter aParameter = parameters[i];
			Label parameterDescriptionLabel = new Label(section, SWT.NULL);
			parameterDescriptionLabel.setText(aParameter.getDescription() + ":");
			int parameterType = aParameter.getType();
			switch (parameterType) {
			case Parameter.COLOR_TYPE:
				Composite innerSection = toolkit.createComposite(section);
				GridLayout colorGd = new GridLayout();
				colorGd.numColumns = 2;
				colorGd.marginHeight = 0;
				colorGd.marginBottom = 0;
				innerSection.setLayout(colorGd);
				final Label colorLabel = new Label(innerSection, SWT.BORDER);
				colorLabel.setText("          ");
				String hexadecimal = aParameter.getValue();
				RGB rgb = DashboardEditor.convertHexadecimalToRGB(hexadecimal);
				final Color color = new Color(section.getDisplay(), rgb);
				colorLabel.setBackground(color);
				Button button = new Button(innerSection, SWT.PUSH);
				button.setText("Color...");
				final Shell parentShell = section.getShell();
				button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
						centerShell.setLocation(
								(parentShell.getSize().x - DashboardEditor.COLORDIALOG_WIDTH) / 2,
								(parentShell.getSize().y - DashboardEditor.COLORDIALOG_HEIGHT) / 2);
						ColorDialog colorDg = new ColorDialog(centerShell,
								SWT.APPLICATION_MODAL);
						colorDg.setRGB(colorLabel.getBackground().getRGB());
						//colorDg.setText("Choose a color");
						RGB rgb = colorDg.open();
						if (rgb != null) {
							// Dispose the old color, create the
							// new one, and set into the label
							color.dispose();
							Color newColor = new Color(parentShell.getDisplay(), rgb);
							colorLabel.setBackground(newColor);
							editor.setIsDirty(true);
							String newHexadecimal = DashboardEditor.convertRGBToHexadecimal(rgb);
							aParameter.setValue(newHexadecimal);
						}
						centerShell.dispose();
					}
				});
				break;
			case Parameter.BOOLEAN_TYPE:
				final Button check = toolkit.createButton(section, "", SWT.CHECK);
				check.setSelection(Boolean.parseBoolean(aParameter.getValue()));
				check.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						editor.setIsDirty(true);
						aParameter.setValue(Boolean.toString(check.getSelection()));
					}
				});
				break;
			default:
				final Text parameterValueText = new Text(section, SWT.BORDER);
			parameterValueText.setText(aParameter.getValue());
			parameterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			parameterValueText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					editor.setIsDirty(true);
					String parameterValueStr = parameterValueText.getText();
					aParameter.setValue(parameterValueStr);
				}
			});
			}
		}
	}

	public String toXML() {
		String toReturn = 
			"    <CONF>\n";
		for (int i = 0; i < parameters.length; i++) {
			Parameter aParameter = parameters[i];
			if(aParameter.getValue() != null && !aParameter.getValue().equals("")){
				toReturn +=
					"		<PARAMETER name='" + aParameter.getName() + "' value='" + aParameter.getValue() + "' />\n";
			}
		}
		toReturn +=
			"    </CONF>\n";
		return toReturn;
	}
}
