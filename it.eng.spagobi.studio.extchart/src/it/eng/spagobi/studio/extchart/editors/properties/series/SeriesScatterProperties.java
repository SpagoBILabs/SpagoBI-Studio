/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.utils.ColorButton;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.LoggerFactory;

public class SeriesScatterProperties extends SeriesProperties {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesScatterProperties.class);

	ColorButton colorButton;
	
	public SeriesScatterProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		setDrawXField(true);
		setDrawYField(true);
		setDrawMarkerConfig(true);		
	}
	
	public void drawProperties(){
		logger.debug("IN");
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "scatter");
		serie.setType("scatter");
		super.drawProperties();
		
		colorButton = SWTUtils.drawColorButton(toolkit, dialog, serie.getColor(), "Color: ");
		
		
		logger.debug("OUT");
		
	}

	
	public void performOk(){
		logger.debug("IN");
		super.performOk();	

		String colorSelected =  colorButton.handleSelctionEvent(colorButton.getColorLabel().getShell());
		serie.setColor(colorSelected);
		editor.setIsDirty(true);
		
		logger.debug("OUT");
	}
	
}
