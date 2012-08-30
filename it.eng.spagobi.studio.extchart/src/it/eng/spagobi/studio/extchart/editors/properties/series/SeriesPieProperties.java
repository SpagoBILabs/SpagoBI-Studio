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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.slf4j.LoggerFactory;

public class SeriesPieProperties  extends SeriesProperties{

	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesPieProperties.class);
	
	Spinner donutSpinner;

	private Button showLegendButton;


	public SeriesPieProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		setDrawField(true);
		setDrawHighlightSegment(true);

	}
	
	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
		Label description = toolkit.createLabel(dialogDescription, "Select series information, what field of the dataset \ndo you want to use for drawing the pie? ", SWT.NULL);
		description.setBackground(defaultBackground);
		/*
		GridData gd=new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;

		gd.horizontalSpan=2	;
		
		description.setLayoutData(gd);
		*/
		
		Label type = toolkit.createLabel(dialog, "Type: ");
		type.setBackground(defaultBackground);
		Label pie = toolkit.createLabel(dialog, "pie");
		pie.setBackground(defaultBackground);
		
		serie.setType("pie");
		super.drawProperties();
		
		// -----------------------------------------------

		donutSpinner = SWTUtils.drawSpinner(dialog, serie.getDonut(), "Donut: ");

		// -----------------------------------------------

		logger.debug("Show in legend");
		showLegendButton = toolkit.createButton(dialog, "Show in legend: ", SWT.CHECK);
		showLegendButton.setBackground(defaultBackground);
		if(serie.getShowInLegened() != null && serie.getShowInLegened().booleanValue() == true){
			showLegendButton.setSelection(true);
		}
		
		toolkit.createLabel(dialog, "");
		
		logger.debug("OUT");
	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(380, 350);
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

		serie.setType("pie");
		logger.debug("type pie");
		
		boolean selectionShowLegend = showLegendButton.getSelection();
		serie.setShowInLegened(selectionShowLegend);
		logger.debug("ShowInLegend " +selectionShowLegend);

		int valDonut = donutSpinner.getSelection();
		if (valDonut > 0 ){
			serie.setDonut(valDonut);			
		}
		logger.debug("donut: " + valDonut);

		
		logger.debug("OUT");
	}
}
