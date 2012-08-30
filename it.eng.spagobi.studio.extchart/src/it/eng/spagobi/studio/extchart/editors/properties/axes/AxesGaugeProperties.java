/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.axes;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.slf4j.LoggerFactory;

public class AxesGaugeProperties extends AxesProperties {

	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesGaugeProperties.class);
	Spinner minimumSpinner, maximumSpinner,stepsSpinner,marginSpinner;
	
	/**
	 * @param editor
	 * @param comp
	 */
	public AxesGaugeProperties(ExtChartEditor editor, Shell comp) {
		super(editor, comp);
	}
	
	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 

		super.drawProperties();
		Label type = toolkit.createLabel(dialog, "Type: ");
		type.setBackground(defaultBackground);
		Label gauge = toolkit.createLabel(dialog, "Gauge");
		gauge.setBackground(defaultBackground);
		Label position = toolkit.createLabel(dialog, "Position: ");
		position.setBackground(defaultBackground);
		Label gauge_two = toolkit.createLabel(dialog, "Gauge");
		gauge_two.setBackground(defaultBackground);


		//minimum
		minimumSpinner = SWTUtils.drawSpinner(dialog, axes.getMinimum(), "Minimum: ");
		
		//maximum
		maximumSpinner = SWTUtils.drawSpinner(dialog, axes.getMaximum(), "Maximum: ");

		//steps
		stepsSpinner = SWTUtils.drawSpinner(dialog, axes.getSteps(), "Steps: ");
		
		//margin
		marginSpinner = SWTUtils.drawSpinner(dialog, axes.getMargin(), "Margin: ");

		logger.debug("OUT");

	}
	
	public void performOk(){
		logger.debug("IN");
		super.performOk();		

		axes.setType("gauge");
		axes.setPosition("gauge");
		logger.debug("type gauge");
		
		int valMinimum = minimumSpinner.getSelection();
		if (valMinimum >= 0 ){
			axes.setMinimum(valMinimum);			
		}
		logger.debug("minimum: " + valMinimum);

		int valMaximum = maximumSpinner.getSelection();
		if (valMaximum >= 0 ){
			axes.setMaximum(valMaximum);			
		}
		logger.debug("maximum: " + valMaximum);
		
		int valSteps = stepsSpinner.getSelection();
		if (valSteps >= 0 ){
			axes.setSteps(valSteps);			
		}
		logger.debug("steps: " + valSteps);
		
		int valMargin = marginSpinner.getSelection();
		axes.setMargin(valMargin);			

		logger.debug("margin: " + valMargin);
		
		logger.debug("OUT");
	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(250, 250);
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}

}
