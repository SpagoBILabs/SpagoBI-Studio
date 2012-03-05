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
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.Activator;
import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.Parameter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartEditorUtils {

	private static Logger logger = LoggerFactory.getLogger(ChartEditorUtils.class);
	/** Get the chart template file path; if subtype is specified go and check in the configuration file wich is the particular template
	 *  otherwise getDefault
	 * 
	 * @param chartType
	 * @return
	 * @throws Exception
	 */


	public static String getDefaultSubtype(String chartType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ChartModel.CHART_INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//CHARTS/CHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (chartType.equalsIgnoreCase(type)) {
				ChartEditorUtils.print("CHART", chart);
				String def = chart.valueOf("@defaultSubType");
				toReturn = def;
				break;
			}
		}
		return toReturn;
	}




	/** Get the configuration file path for the selected type
	 * 
	 * @param chartType
	 * @return
	 * @throws Exception
	 */

	public static String getChartConfigPath(String chartType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ChartModel.CHART_INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//CHARTS/CHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (chartType.equalsIgnoreCase(type)) {
				String templatePath = chart.valueOf("@configPath");
				toReturn = templatePath;
				break;
			}
		}
		return toReturn;
	}


	/** Get the chart image path for the selected type
	 * 
	 * @param imageType
	 * @return
	 * @throws Exception
	 */

	public static String getChartImagePath(String imageType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ChartModel.CHART_INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//CHARTS/CHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (imageType.equalsIgnoreCase(type)) {
				String imagePath = chart.valueOf("@imagePath");
				toReturn = imagePath;
				break;
			}
		}
		return toReturn;
	}


	/** Get input stream from a resource
	 * 
	 * @param resourcePath
	 * @return
	 * @throws IOException
	 */

	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		Bundle b = Platform.getBundle(Activator.PLUGIN_ID);
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}



	/**	Draw a configuration parameter
	 * 
	 * @param model
	 * @param section
	 * @param aParameter
	 * @param toolkit
	 */

	static public void drawParameter(final ChartModel model, Composite section, final Parameter aParameter, FormToolkit toolkit){
		logger.debug("Draw Parameter "+aParameter.getName());
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
			RGB rgb=null;
			if(aParameter.getValue()!=null){
				String hexadecimal = aParameter.getValue().toString();
				try{
					rgb= ChartEditor.convertHexadecimalToRGB(hexadecimal);
				}
				catch (Exception e) {
					rgb=new RGB(255,0,0);
				}
			}
			else{
				rgb=new RGB(255,0,0);
			}
			final Color color = new org.eclipse.swt.graphics.Color(section.getDisplay(), rgb);
			colorLabel.setBackground(color);
			Button button = new Button(innerSection, SWT.PUSH);
			button.setText("Color...");
			if(aParameter.getTooltip()!=null){
				button.setToolTipText(aParameter.getTooltip());
			}
			final Shell parentShell = section.getShell();
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
					centerShell.setLocation(
							(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
							(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
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
						model.getEditor().setIsDirty(true);
						String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
						aParameter.setValue(newHexadecimal);
					}
					centerShell.dispose();
				}
			});
			break;
		case Parameter.BOOLEAN_TYPE:
			final Button check = toolkit.createButton(section, "", SWT.CHECK);
			if(aParameter.getValue()!=null){
				check.setSelection(Boolean.parseBoolean(aParameter.getValue().toString()));
			}
			if(aParameter.getTooltip()!=null){
				check.setToolTipText(aParameter.getTooltip());
			}
			check.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					model.getEditor().setIsDirty(true);
					aParameter.setValue(Boolean.toString(check.getSelection()));
				}
			});
			break;
		case Parameter.INTEGER_TYPE:
			//final Text parameterNumberValueText = new Text(section, SWT.BORDER);
			final Spinner parIntegerSpinner = new Spinner (section, SWT.BORDER);
			parIntegerSpinner.setMaximum(1000);
			parIntegerSpinner.setMinimum(-1000);
			if(aParameter.getTooltip()!=null){
				parIntegerSpinner.setToolTipText(aParameter.getTooltip());
			}
			if(aParameter.getValue()!=null && !aParameter.getValue().toString().equals("")){
				//parameterNumberValueText.setText(aParameter.getValue().toString());
				parIntegerSpinner.setSelection((Integer.valueOf(aParameter.getValue().toString())).intValue());
			}
			//parIntegerSpinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			parIntegerSpinner.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent event) {
					model.getEditor().setIsDirty(true);
					int parameterValue = parIntegerSpinner.getSelection();
					// check it is a number 
					aParameter.setValue(new Integer(parameterValue));
				}
			});
			break;
		case Parameter.FLOAT_TYPE:
			//final Text parameterNumberValueText = new Text(section, SWT.BORDER);
			final Spinner parFloatSpinner = new Spinner (section, SWT.BORDER);
			parFloatSpinner.setDigits(2);
			parFloatSpinner.setMaximum(100000);
			parFloatSpinner.setMinimum(-100000);
			if(aParameter.getTooltip()!=null){
				parFloatSpinner.setToolTipText(aParameter.getTooltip());
			}

			if(aParameter.getValue()!=null && !aParameter.getValue().toString().equals("")){
				//parameterNumberValueText.setText(aParameter.getValue().toString());
				String valueToSet = null;
				Double doubleVal=Double.valueOf(aParameter.getValue().toString());
				String doubleString=doubleVal.toString();
				int indexPoint = doubleString.indexOf('.');
				if(indexPoint != -1){
					String intPart= doubleString.substring(0, indexPoint); 
					String decimalPart= doubleString.substring(indexPoint+1, doubleString.length());
					if(decimalPart.length() == 1){
						decimalPart+="0";
					}
					valueToSet = intPart+decimalPart;
				}
				else{
					valueToSet = doubleString;
				}
				//doubleString=removeChar(doubleString, '.');
				Integer fakeIntVal=Integer.valueOf(valueToSet);
				parFloatSpinner.setSelection(fakeIntVal);
			}
			//parFloatSpinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			parFloatSpinner.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					model.getEditor().setIsDirty(true);
					//int parameterValue = parFloatSpinner.getSelection();
					double parameterValue  = parFloatSpinner.getSelection()/ Math.pow(10, parFloatSpinner.getDigits());
					// check it is a number 
					aParameter.setValue(new Double(parameterValue));
				}
			});
			break;
		case Parameter.COMBO_TYPE:
			ArrayList<String> values=aParameter.getPredefinedValues();
			if(values!=null && !values.isEmpty()){
				final Combo combo = new Combo(section,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
				boolean selected=false;
				if(aParameter.getTooltip()!=null){
					combo.setToolTipText(aParameter.getTooltip());
				}

				for (Iterator iterator = values.iterator(); iterator.hasNext();) {
					String val = (String) iterator.next();
					combo.add(val);
					if(aParameter.getValue()!=null && val.equalsIgnoreCase(aParameter.getValue().toString())){
						int index=combo.indexOf(val);
						combo.select(index);
						selected=true;
					}	
				}
				if(selected==false){
					combo.select(0);
					String comboValue=combo.getItem(0);
					aParameter.setValue(comboValue);
				}
				combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				combo.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						model.getEditor().setIsDirty(true);
						String parameterValueStr = combo.getText();
						aParameter.setValue(parameterValueStr);
					}
				});
			}			
			else{ // if no predefined value are set it is a simple text form
				final Text parameterValueText = new Text(section, SWT.BORDER);
				if(aParameter.getValue()!=null){
					parameterValueText.setText(aParameter.getValue().toString());
				}
				if(aParameter.getTooltip()!=null){
					parameterValueText.setToolTipText(aParameter.getTooltip());
				}
				parameterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				parameterValueText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						model.getEditor().setIsDirty(true);
						String parameterValueStr = parameterValueText.getText();
//						System.out.println("modificato parametro "+aParameter.getName());
						aParameter.setValue(parameterValueStr);
					}
				});

			}
			break;
		default:
			final Text parameterValueText = new Text(section, SWT.BORDER);
		if(aParameter.getValue()!=null){
			parameterValueText.setText(aParameter.getValue().toString());
		}
		if(aParameter.getTooltip()!=null){
			parameterValueText.setToolTipText(aParameter.getTooltip());
		}
		parameterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		parameterValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String parameterValueStr = parameterValueText.getText();
//				System.out.println("modificato parametro "+aParameter.getName());
				aParameter.setValue(parameterValueStr);
			}
		});


		}

	}


	/** Draw a specific configuration parameter
	 * 
	 * @param model
	 * @param editor
	 * @param toolkit
	 * @param form
	 */

	static public void drawSpecificParameter(final ChartModel model, Composite section, final Parameter aParameter, FormToolkit toolkit, int order){
		logger.debug("Draw Parameter "+aParameter.getName());
		Label parameterDescriptionLabel = new Label(section, SWT.NULL);
		parameterDescriptionLabel.setText(aParameter.getDescription() + ":");
		//parameterDescriptionLabel.setLocation(5, 30*order);
		parameterDescriptionLabel.pack();

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
			RGB rgb=null;
			if(aParameter.getValue()!=null){
				String hexadecimal = aParameter.getValue().toString();
				try{
					rgb= ChartEditor.convertHexadecimalToRGB(hexadecimal);
				}
				catch (Exception e) {
					rgb=new RGB(255,0,0);
				}
			}
			else{
				rgb=new RGB(255,0,0);
			}
			final Color color = new org.eclipse.swt.graphics.Color(section.getDisplay(), rgb);
			colorLabel.setBackground(color);
			Button button = new Button(innerSection, SWT.PUSH);
			button.setText("Color...");
			if(aParameter.getTooltip()!=null){
				button.setToolTipText(aParameter.getTooltip());
			}			
			final Shell parentShell = section.getShell();
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					model.getEditor().setIsDirty(true);									
					final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
					centerShell.setLocation(
							(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
							(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
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
						model.getEditor().setIsDirty(true);
						String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
						aParameter.setValue(newHexadecimal);
					}
					centerShell.dispose();
				}
			});
			break;
		case Parameter.BOOLEAN_TYPE:
			final Button check = toolkit.createButton(section, "", SWT.CHECK);
			if(aParameter.getValue()!=null){
				check.setSelection(Boolean.parseBoolean(aParameter.getValue().toString()));
			}
			if(aParameter.getTooltip()!=null){
				check.setToolTipText(aParameter.getTooltip());
			}			
			check.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					model.getEditor().setIsDirty(true);
					boolean sel=check.getSelection();
					String s=Boolean.valueOf(sel).toString();
					aParameter.setValue(s);
				}
			});
			//check.setLocation(100,order*30);
			check.pack();
			break;
		case Parameter.INTEGER_TYPE:
			//final Text parameterNumberValueText = new Text(section, SWT.BORDER);
			final Spinner parIntegerSpinner = new Spinner (section, SWT.BORDER);
			parIntegerSpinner.setMaximum(1000);
			parIntegerSpinner.setMinimum(-1000);
			if(aParameter.getTooltip()!=null){
				parIntegerSpinner.setToolTipText(aParameter.getTooltip());
			}
			if(aParameter.getValue()!=null && !aParameter.getValue().toString().equals("")){
				//parameterNumberValueText.setText(aParameter.getValue().toString());
				parIntegerSpinner.setSelection((Integer.valueOf(aParameter.getValue().toString())).intValue());
			}
			//parIntegerSpinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			parIntegerSpinner.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent event) {
					model.getEditor().setIsDirty(true);
					int parameterValue = parIntegerSpinner.getSelection();
					// check it is a number 
					aParameter.setValue(new Integer(parameterValue));
				}
			});
			break;
		case Parameter.FLOAT_TYPE:
			//final Text parameterNumberValueText = new Text(section, SWT.BORDER);
			final Spinner parFloatSpinner = new Spinner (section, SWT.BORDER);
			parFloatSpinner.setDigits(2);
			parFloatSpinner.setMaximum(100000);
			parFloatSpinner.setMinimum(-100000);
			if(aParameter.getTooltip()!=null){
				parFloatSpinner.setToolTipText(aParameter.getTooltip());
			}
			if(aParameter.getValue()!=null && !aParameter.getValue().toString().equals("")){
				//parameterNumberValueText.setText(aParameter.getValue().toString());
				Double doubleVal=Double.valueOf(aParameter.getValue().toString());
				String doubleString=doubleVal.toString();
				doubleString=removeChar(doubleString, '.');
				Integer fakeIntVal=Integer.valueOf(doubleString);
				parFloatSpinner.setSelection(fakeIntVal);
			}
			//parFloatSpinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			parFloatSpinner.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					model.getEditor().setIsDirty(true);
					//int parameterValue = parFloatSpinner.getSelection();
					double parameterValue  = parFloatSpinner.getSelection()/ Math.pow(10, parFloatSpinner.getDigits());
					// check it is a number 
					aParameter.setValue(new Double(parameterValue));
				}
			});
			break;
		case Parameter.COMBO_TYPE:
			ArrayList<String> values=aParameter.getPredefinedValues();
			if(values!=null && !values.isEmpty()){
				final Combo combo = new Combo(section,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
				if(aParameter.getTooltip()!=null){
					combo.setToolTipText(aParameter.getTooltip());
				}
				boolean selected=false;
				for (Iterator iterator = values.iterator(); iterator.hasNext();) {
					String val = (String) iterator.next();
					combo.add(val);
					if(aParameter.getValue()!=null && val.equalsIgnoreCase(aParameter.getValue().toString())){
						int index=combo.indexOf(val);
						combo.select(index);
						selected=true;
					}	
				}
				if(selected==false){
					combo.select(0);
					String comboValue=combo.getItem(0);
					aParameter.setValue(comboValue);
				}
				combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				combo.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						model.getEditor().setIsDirty(true);
						String parameterValueStr = combo.getText();
						aParameter.setValue(parameterValueStr);
					}
				});
				//combo.setLocation(100,order*30);
				combo.pack();

			}
			else{ // if no predefined value are set it is a simple text form
				final Text parameterValueText = new Text(section, SWT.BORDER);
				if(aParameter.getValue()!=null){
					parameterValueText.setText(aParameter.getValue().toString());
				}
				if(aParameter.getTooltip()!=null){
					parameterValueText.setToolTipText(aParameter.getTooltip());
				}				
				parameterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				parameterValueText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						model.getEditor().setIsDirty(true);
						String parameterValueStr = parameterValueText.getText();
						aParameter.setValue(parameterValueStr);
					}
				});
				//parameterValueText.setLocation(100, 30*order);
				parameterValueText.pack();
			}
			break;
		default:
			final Text parameterValueText = new Text(section, SWT.BORDER);
		if(aParameter.getValue()!=null){
			parameterValueText.setText(aParameter.getValue().toString());
		}
		if(aParameter.getTooltip()!=null){
			parameterValueText.setToolTipText(aParameter.getTooltip());
		}
		parameterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		parameterValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String parameterValueStr = parameterValueText.getText();
				aParameter.setValue(parameterValueStr);
			}
		});
		//parameterValueText.setLocation(100, 30*order);
		parameterValueText.pack();

		}

	}





	/**
	 *  return tru if chart inserted is a number or at most a . (for doubles)
	 * @return
	 */
	static public boolean validateNumberField(String a){
		// try to convert in Double, if succeed is ok, otherwise check if last chart is a ., then is ok
		try{
			Double.valueOf(a);	
			return true;
		}
		catch (Exception e) {
			logger.warn("Not a number");
		}
		int len=a.length();
		char c=a.charAt(len - 1);
		if(c=='.') return true;
		else return false;
	}



	static public void print(String titolo, Node node){
//		System.out.println("******** "+titolo+" **********");
//		System.out.println(node!=null ? node.asXML() : "NULLO");
//		System.out.println("******************************");
	}

	public static String removeChar(String s, char c) {
		String r = "";
		for (int i = 0; i < s.length(); i ++) {
			if (s.charAt(i) != c) r += s.charAt(i);
		}
		return r;
	}

	public static void addBlanckSpace(Composite composite){
		Label spaceLabel=new Label(composite,SWT.NULL);
		spaceLabel.setText("");
	}

}