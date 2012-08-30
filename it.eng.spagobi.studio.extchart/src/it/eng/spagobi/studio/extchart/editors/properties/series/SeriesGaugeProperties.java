/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

public class SeriesGaugeProperties extends SeriesProperties {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesGaugeProperties.class);
	Text donutText;
	Spinner donutSpinner;

	
	public SeriesGaugeProperties(ExtChartEditor editor,
			Shell comp) {
		super(editor, comp);
		setDrawField(true);
	}

	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
		Label description = toolkit.createLabel(dialogDescription, "Select series information, what field of the dataset \ndo you want to use for drawing the gauge? ", SWT.NULL);
		description.setBackground(defaultBackground);
		Label type = toolkit.createLabel(dialog, "Type: ");
		type.setBackground(defaultBackground);
		Label gauge = toolkit.createLabel(dialog, "gauge");
		gauge.setBackground(defaultBackground);
		serie.setType("gauge");
		super.drawProperties();
	
		logger.debug("Donut");
		donutSpinner = SWTUtils.drawSpinner(dialog, serie.getDonut(), "Donut: ");
	
		logger.debug("OUT");
	}

	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(320, 300);
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}
	
	public void performOk(){
		logger.debug("IN");
		super.performOk();	
		serie.setType("gauge");
		int valDonut = donutSpinner.getSelection();
		if (valDonut > 0 ){
			serie.setDonut(valDonut);			
		}
		logger.debug("donut: " + valDonut);
		
		logger.debug("OUT");
	}
	
}
