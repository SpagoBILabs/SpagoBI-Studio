/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.LoggerFactory;

public class SeriesRadarProperties  extends SeriesProperties{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesRadarProperties.class);
	
	Button showInLegendButton;
	Button showmarkersButton;
	
	public SeriesRadarProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		setDrawXField(true);
		setDrawYField(true);
		setDrawMarkerConfig(true);		

	}

	
	public void drawProperties(){
		logger.debug("IN");
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "area");
		serie.setType("radar");
		super.drawProperties();
		
		logger.debug("Show in legend");
		showInLegendButton = toolkit.createButton(dialog, "Show In Legend", SWT.CHECK);
		if(serie.getShowInLegened() != null && serie.getShowInLegened().booleanValue() == true){
			showInLegendButton.setSelection(true);
		}
		
		logger.debug("Show markers: ");
		showmarkersButton = toolkit.createButton(dialog, "Show Markers", SWT.CHECK);
		if(serie.getShowMarkers() != null && serie.getShowMarkers().booleanValue() == true){
			showmarkersButton.setSelection(true);
		}
		
		logger.debug("OUT");
		
	}

	
	public void performOk(){
		logger.debug("IN");
		super.performOk();	
		
	
		boolean selectionLegend = showInLegendButton.getSelection();
		serie.setShowInLegened(selectionLegend);
		logger.debug("selectionLegend " +selectionLegend);

		boolean showmarkers = showmarkersButton.getSelection();
		serie.setShowMarkers(selectionLegend);
		logger.debug("showmarkers " + showmarkers);
		
		
		logger.debug("OUT");
	}
	
}
