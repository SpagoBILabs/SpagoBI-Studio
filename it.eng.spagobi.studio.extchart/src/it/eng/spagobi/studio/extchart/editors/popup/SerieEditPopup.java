/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.popup;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.PropertiesFactory;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class SerieEditPopup{

	Shell shell;
	ExtChartEditor editor;
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SerieEditPopup.class);


	public void drawSeriePopupD(Shell shell, Series serie){
		logger.debug("IN");

		// draw series properties depending on the type
		ExtChart chart = editor.getExtChart();
		String type = chart.getType();
		logger.debug("Series properties for type "+type);
		SeriesProperties seriesProperties = PropertiesFactory.getSeriesProperties(type, editor, serie, shell);
		seriesProperties.setTitle("Define serie properties: ");
		seriesProperties.drawProperties();
		seriesProperties.drawButtons();
		seriesProperties.showPopup();		
		
		logger.debug("OUT");

	}


	public ExtChartEditor getEditor() {
		return editor;
	}


	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}

	
}
