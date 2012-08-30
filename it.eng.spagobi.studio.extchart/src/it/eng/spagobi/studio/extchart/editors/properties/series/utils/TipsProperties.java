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
import it.eng.spagobi.studio.extchart.model.bo.Label;
import it.eng.spagobi.studio.extchart.model.bo.Tips;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class TipsProperties extends PopupPropertiesDialog{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(LabelProperties.class);

	Button trackMouseButton;
	Spinner widthSpinner;
	Spinner heightSpinner;

	Text textText;
	String labelForText;
	Tips tips;
	SeriesProperties father;

	public TipsProperties(ExtChartEditor editor, Tips tip, Shell comp, SeriesProperties father) {
		super(editor, comp);
		this.tips = tip; 
		this.editor = editor;
		this.father = father;
		toolkit = new FormToolkit(comp.getDisplay());
	}


	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 

		logger.debug("track mouse");
		
		org.eclipse.swt.widgets.Label trackLabel =toolkit.createLabel(dialog, "Track Mouse: ");
		trackLabel.setBackground(defaultBackground);

		trackMouseButton = SWTUtils.drawCheck(dialog, 
				tips.getTrackMouse() != null ? tips.getTrackMouse() : null
						, null);


		widthSpinner =SWTUtils.drawSpinner(dialog, 
				tips.getWidth() != null ? tips.getWidth() : null
						, "Width: ");

		heightSpinner =SWTUtils.drawSpinner(dialog, 
				tips.getHeight() != null ? tips.getHeight() : null
						, "Height: ");
		org.eclipse.swt.widgets.Label textHelp = toolkit.createLabel(dialog, "Text: ");
		textHelp.setBackground(defaultBackground);
		textText = SWTUtils.drawText(toolkit, dialog, 
				tips.getText() != null ? tips.getText() : null
				, null);
		
		org.eclipse.swt.widgets.Label description = toolkit.createLabel(dialogDescription, labelForText, SWT.NULL);
		description.setBackground(defaultBackground);

	
		logger.debug("OUT");
	}


	public void performOk(){
		logger.debug("IN");
		editor.setIsDirty(true);

		if(tips == null ){
			logger.debug("create a label definition");
			tips = new Tips();
		}

		int valWidth = widthSpinner.getSelection();
		tips.setWidth(valWidth);
		logger.debug("width " + valWidth);
		
		int valHeight = heightSpinner.getSelection();
		tips.setHeight(valHeight);
		logger.debug("height " + valHeight);
		
		String valText = textText.getText();
		tips.setText(valText);		
		logger.debug("text " + valText);

		Boolean track = trackMouseButton.getSelection();
		tips.setTrackMouse(track);		
		logger.debug(" mouse track. "+track);

		// memorize new Label
		father.getTipsHolder()[0] = tips;

		//father.getSerie().setTips(tips);

		logger.debug("OUT");
	}


	public String getLabelForText() {
		return labelForText;
	}


	public void setLabelForText(String labelForText) {
		this.labelForText = labelForText;
	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(310, 250);
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}	

}
