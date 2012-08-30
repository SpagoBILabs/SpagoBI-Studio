/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.axes;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.pages.editorComponent.YAxePanel;
import it.eng.spagobi.studio.extchart.utils.ExtChartConstants;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.LoggerFactory;


public class AxesNumericProperties  extends AxesProperties{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesNumericProperties.class);

	//Combo fieldCombo;

	Button gridButton;
	


	public AxesNumericProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);

	}

	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 

		toolkit.createLabel(dialog, "");
		toolkit.createLabel(dialog, "");
		
//		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();	
//		if(metadatas == null) metadatas = new String[0];
//		fieldCombo = SWTUtils.drawCombo(dialog, metadatas, axes.getFields(), "Field: ");

		super.drawProperties();

		Label grid = toolkit.createLabel(dialog, "Grid: ");
		grid.setBackground(defaultBackground);
		
		gridButton = SWTUtils.drawCheck(dialog, 
				axes != null && axes.getGrid() != null ? axes.getGrid() : false
						, "");



		logger.debug("OUT");

	}	



	public void performOk(){
		logger.debug("IN");
		// save
		super.performOk();

		axes.setType(ExtChartConstants.AXE_TYPE_NUMERIC);
//		if(fieldCombo.getSelectionIndex() != -1){
//			String valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
//			axes.setFields(valueField);
//			logger.debug("field " +valueField);
//		}
//		else{
//			logger.debug("no field selected");
//		}

		Boolean track = gridButton.getSelection();
		axes.setGrid(track);		
		logger.debug("grid: "+track);

		yAxePanel.getTitleLabel().setText(titleText.getText());

		logger.debug("OUT");

	}

	public YAxePanel getyAxePanel() {
		return yAxePanel;
	}

	public void setyAxePanel(YAxePanel yAxePanel) {
		this.yAxePanel = yAxePanel;
	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(200, 200);
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}
	

	
}
