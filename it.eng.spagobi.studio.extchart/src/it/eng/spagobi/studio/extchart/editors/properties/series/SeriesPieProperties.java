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
import org.eclipse.swt.widgets.Button;
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
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "pie");
		serie.setType("pie");
		super.drawProperties();
		
		// -----------------------------------------------

		donutSpinner = SWTUtils.drawSpinner(dialog, serie.getDonut(), "Donut: ");

		// -----------------------------------------------

		logger.debug("Show in legend");
		showLegendButton = toolkit.createButton(dialog, "Show in legend: ", SWT.CHECK);
		if(serie.getShowInLegened() != null && serie.getShowInLegened().booleanValue() == true){
			showLegendButton.setSelection(true);
		}
		
		toolkit.createLabel(dialog, "");
		
		logger.debug("OUT");
	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialog.setSize(450, 300);
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!dialog.getDisplay().readAndDispatch()) {
		    	dialog.getDisplay().sleep();
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
