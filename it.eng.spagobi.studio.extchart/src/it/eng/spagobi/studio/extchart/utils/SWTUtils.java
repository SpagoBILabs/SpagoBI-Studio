/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.osgi.framework.Bundle;
import org.slf4j.LoggerFactory;

public class SWTUtils {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SWTUtils.class);

	public static final String LIGHT_GRAY = "#ECE8E8";
	public static final String VERY_LIGHT_GRAY = "#ECECEC";
	public static final String VERY_LIGHT_GREEN = "#DFF9DA";
	public static final String LIGHT_GREEN = "#D6F4D1";
	public static final String LIGHT_RED = "#F7DADF";
	public static final String LIGHT_YELLOW = "#FDFCA9";
	public static final String LIGHT_ORANGE = "#FDDAA8";
	public static final String LIGHT_BLUE = "#C9F9EB";
	public static final String WHITE = "#FFFFFF";

	static public final int COLORDIALOG_WIDTH = 222;
	static public final int COLORDIALOG_HEIGHT = 306;
	
	static FormToolkit toolkit;

	

	public static FormToolkit createFormToolkit(Composite container){
		logger.debug("IN");
		if(toolkit == null){
			toolkit = new FormToolkit(container.getDisplay());
		}
		logger.debug("OUT");
		return toolkit;
	}

	public static ScrolledForm createScrolledForm(Composite container){
		logger.debug("IN");
		FormToolkit toolkit = createFormToolkit(container);
		ScrolledForm scrolledForm = toolkit.createScrolledForm(container);
		
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 10;
		layout.topMargin = 20;
		layout.leftMargin = 20;
		scrolledForm.getBody().setLayout(layout);
		logger.debug("OUT");
		return scrolledForm;
	}

	public static Section createSection(Composite container){
		logger.debug("IN");
		FormToolkit toolkit = createFormToolkit(container);
		final ScrolledForm form = createScrolledForm(container);
		Section section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(td);
		
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		logger.debug("OUT");
		return section;
	}
	
	
	public static Composite createGridCompositeOnSection(Section section, int columns){
		logger.debug("IN");
		FormToolkit toolkit = createFormToolkit(section);
		Composite composite = toolkit.createComposite(section);
		GridLayout gla = new GridLayout();
		gla.numColumns = columns;
		composite.setLayout(gla);
		logger.debug("OUT");
		return composite;
	}
	
	public static Composite createGridCompositeOnComposite(Composite fatherComposite, int columns){
		logger.debug("IN");
		Composite composite = new Composite(fatherComposite, SWT.NULL);
		GridLayout gla = new GridLayout();
		gla.numColumns = columns;
		composite.setLayout(gla);
		logger.debug("OUT");
		return composite;
	}
	

	public static Composite createComposite(Composite container){
		logger.debug("IN");
		FormToolkit toolkit = createFormToolkit(container);
		Composite composite = toolkit.createComposite(container);
		return composite;
	}

	public static Composite createComposite(Section section, int columns){
		logger.debug("IN");
		Composite composite = createFormToolkit(section).createComposite(section);
		GridLayout gla = new GridLayout();
		gla.numColumns = columns;
		composite.setLayout(gla);
		logger.debug("OUT");
		return composite;
	}

	public static GridLayout makeGridLayout(int columns){
		GridLayout gla = new GridLayout();
		gla.numColumns = columns;
		return gla;		

	}

	public static GridData makeGridDataLayout(int style, Integer horizontalSpan, Integer verticalSpan){
		GridData gd = new GridData(style);
		if(horizontalSpan != null){
			gd.horizontalSpan = horizontalSpan;
		}
		if(verticalSpan != null){
			gd.verticalSpan = verticalSpan;
		}
		return gd;
	}



	public static Spinner drawSpinner(Composite composite, Integer value, String title){
		logger.debug("IN");
		if(title!= null){
			Label label = new Label(composite, SWT.NULL);
			label.setText(title);
		}

		final Spinner parIntegerSpinner = new Spinner (composite, SWT.BORDER);
		parIntegerSpinner.setMaximum(1000);
		parIntegerSpinner.setMinimum(-1000);

		if(value != null){
			parIntegerSpinner.setSelection(value.intValue());
		}
		logger.debug("OUT");
		return parIntegerSpinner;
	}

	public static Spinner drawSpinner(Composite composite, Float value, String title){
		logger.debug("IN");
		if(title!= null){
			Label label = new Label(composite, SWT.NULL);
			label.setText(title);
		}

		final Spinner parFloatSpinner = new Spinner (composite, SWT.BORDER);
		parFloatSpinner.setMaximum(1000);
		parFloatSpinner.setMinimum(-1000);
		parFloatSpinner.setDigits(2);

//		if(value != null){
//			parFloatSpinner.setSelection(value.intValue());
//		}
		
		if(value != null){
			//parameterNumberValueText.setText(aParameter.getValue().toString());
			String valueToSet = null;
			//Double doubleVal=Double.valueOf(value);
			String doubleString=value.toString();
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
		
		logger.debug("OUT");
		return parFloatSpinner;
	}

	public static Combo drawCombo(Composite composite, String[] content, String value, String title){
		logger.debug("IN");
		if(title!= null){
			Label label = new Label(composite, SWT.NULL);
			label.setText(title);
		}
		final Combo combo = new Combo(composite,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		boolean foundValue = false;
		int selIndex = 0;

		for (int i = 0; i < content.length; i++) {
			String comboContent = content[i];
			combo.add(comboContent);
			if(foundValue==false){
				if(value != null && comboContent.equals(value)){
					selIndex = i;
					foundValue = true;
				}
				//				else{
				//					if(comboContent.equals(defaultValue)){
				//						selIndex = i;
				//					}
				//
				//				}
			}
		}
		combo.select(selIndex);
		logger.debug("OUT");
		return combo;
	}
	
	
	
	public static CCombo drawCCombo(Table table, String[] content, String value, String title){
		logger.debug("IN");
		if(title!= null){
			Label label = new Label(table, SWT.NULL);
			label.setText(title);
		}
		//final CCombo combo = new CCombo(table,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		final CCombo combo = new CCombo(table,  SWT.NONE);
		boolean foundValue = false;
		int selIndex = 0;

		for (int i = 0; i < content.length; i++) {
			String comboContent = content[i];
			combo.add(comboContent);
			if(foundValue==false){
				if(value != null && comboContent.equals(value)){
					selIndex = i;
					foundValue = true;
				}
				//				else{
				//					if(comboContent.equals(defaultValue)){
				//						selIndex = i;
				//					}
				//
				//				}
			}
		}
		combo.select(selIndex);
		logger.debug("OUT");
		return combo;
	}
	public static Text drawText(FormToolkit toolkit, Composite composite, String value, String title){
		logger.debug("IN");
		if(title!= null){
			toolkit.createLabel(composite, title, SWT.NULL);
		}
		Text text = toolkit.createText(composite, value, SWT.BORDER);
		logger.debug("OUT");
		return text;
	}

	public static Button drawCheck(Composite composite, Boolean value, String title){
		logger.debug("IN");
		Button check = new Button(composite, SWT.CHECK);
		if(title!= null){
			check.setText(title);
		}
		if(value != null){
			check.setSelection(value);
		}
		logger.debug("OUT");
		return check;
	}

	// button for popup
	public static Button drawButton(Composite composite, String title){
		logger.debug("IN");
		Button check = new Button(composite, SWT.PUSH);
		if(title!= null){
			check.setText(title);
		}
		logger.debug("OUT");
		return check;
	}



	public static GridData getGridDataSpan(int span, int style){
		logger.debug("IN");
		GridData gd = null;
		if(style != -1)
			gd = new GridData(style);
		else
			gd = new GridData();
		gd.horizontalSpan = span;
		logger.debug("OUT");
		return gd;
	}


	public static GridData getGridDataSpan(int style){
		GridData gd = new GridData(style);
		return gd;
	}


	public static String convertRGBToHexadecimal(RGB rgb) {
		int red = rgb.red;
		int green = rgb.green;
		int blue = rgb.blue;
		String redHexadecimal = Integer.toHexString(red);
		String greenHexadecimal = Integer.toHexString(green);
		String blueHexadecimal = Integer.toHexString(blue);
		if (redHexadecimal.length() == 1) redHexadecimal = "0" + redHexadecimal;
		if (greenHexadecimal.length() == 1) greenHexadecimal = "0" + greenHexadecimal;
		if (blueHexadecimal.length() == 1) blueHexadecimal = "0" + blueHexadecimal;
		return "#" + redHexadecimal + greenHexadecimal + blueHexadecimal;
	}




	public static RGB convertHexadecimalToRGB(String hexadecimal) throws NumberFormatException{
		logger.debug("IN");
		java.awt.Color col=null;
		try{
			col=java.awt.Color.decode(hexadecimal);
		}
		catch (Exception e) {
			col=java.awt.Color.WHITE;
		}
		int red=col.getRed();
		int blue=col.getBlue();
		int green=col.getGreen();
		logger.debug("OUT");
		return new RGB(red, green, blue);
	}

	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(it.eng.spagobi.studio.extchart.Activator.PLUGIN_ID);
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}



	public static ColorButton drawColorButton(FormToolkit toolkit, Composite composite, String colorString, String title){
		logger.debug("IN");
		ColorButton toReturn = new ColorButton();
		//		Composite innerSection = toolkit.createComposite(composite);
		//		innerSection.setBackground(SWTUtils.getColor(innerSection.getDisplay(), "#000000"));

		final Label colorLabel = new Label(composite, SWT.BORDER);
		colorLabel.setText("colorami");
		Button 	colorButton = new Button(composite, SWT.PUSH);
		final Color color = new org.eclipse.swt.graphics.Color(composite.getDisplay(), new RGB(255,255,255));
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		//innerSection.setLayout(colorGd);
		colorLabel.setText("          ");
		colorLabel.setBackground(color);
		colorButton.setText(title);
		//		final Shell parentShell = innerSection.getShell();
		//		colorButton.addSelectionListener(new SelectionAdapter() {
		//			public void widgetSelected(SelectionEvent event) {
		//				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
		//				centerShell.setLocation(
		//						(parentShell.getSize().x - COLORDIALOG_WIDTH) / 2,
		//						(parentShell.getSize().y - COLORDIALOG_HEIGHT) / 2);
		//				ColorDialog colorDg = new ColorDialog(centerShell,
		//						SWT.APPLICATION_MODAL);
		//				colorDg.setRGB(colorLabel.getBackground().getRGB());
		//				colorDg.setText("Choose a color");
		//				RGB rgb = colorDg.open();
		//				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
		//				if (	rgb != null) {
		//					// Dispose the old color, create the
		//					// new one, and set into the label
		//					color.dispose();
		//					Color newColor = new Color(parentShell.getDisplay(), rgb);
		//					colorLabel.setBackground(newColor);
		//					String newHexadecimal = convertRGBToHexadecimal(rgb);
		//					RGB color = convertHexadecimalToRGB(newHexadecimal);
		//
		//					//centerShell.pack();
		//					centerShell.dispose();
		//				}
		//			}
		//		});		
		toReturn.setColorLabel(colorLabel);
		//toReturn.setInnerSection(innerSection);
		toReturn.setColorButton(colorButton);
		logger.debug("OUT");
		return toReturn;
	}




	public static Color getColor(Display display, String hex){
		RGB rgb = convertHexadecimalToRGB(hex);
		Color color = new Color(display, rgb);
		return color;
	}


}
