/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.series.utils;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Highlight;
import it.eng.spagobi.studio.extchart.model.bo.Label;
import it.eng.spagobi.studio.extchart.model.bo.Tips;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class HighlightProperties extends PopupPropertiesDialog{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(HighlightProperties.class);

	Button trackMouseButton;
	Spinner segmentSpinner;
	

	Text textText;
	String labelForText;
	Highlight highlight;
	SeriesProperties father;

	public HighlightProperties(ExtChartEditor editor, Highlight highlight, Shell comp, SeriesProperties father) {
		super(editor, comp);
		this.highlight = highlight; 
		this.editor = editor;
		this.father = father;
		toolkit = new FormToolkit(comp.getDisplay());
	}


	public void drawProperties(){
		logger.debug("IN");

		logger.debug("segment margin");
		
		//toolkit.createLabel(dialog, "");

		segmentSpinner =SWTUtils.drawSpinner(dialog, 
				highlight.getSegment().getMargin() != null ? highlight.getSegment().getMargin() : null
						, "Segment Margin: ");
	
		logger.debug("OUT");
	}


	public void performOk(){
		logger.debug("IN");
		editor.setIsDirty(true);

		if(highlight == null ){
			logger.debug("create a label definition");
			highlight = new Highlight();
		}

		int valSegment = segmentSpinner.getSelection();
		highlight.getSegment().setMargin(valSegment);
		logger.debug("width " + valSegment);


		// memorize new highlight
		father.getHighlightHolder()[0] = highlight;



		logger.debug("OUT");
	}


	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialog.setSize(200, 100);
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!dialog.getDisplay().readAndDispatch()) {
		    	dialog.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}	

}
