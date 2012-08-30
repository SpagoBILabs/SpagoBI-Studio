/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.axes;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.pages.editorComponent.SeriesPanel;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SerieTableItemContent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.LoggerFactory;


public class AxesCategoryProperties  extends AxesProperties{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesCategoryProperties.class);

	Combo fieldCombo;
	Combo positionCombo;
	Button gridButton;

	public AxesCategoryProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);

	}

	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 

		toolkit.createLabel(dialog, "");
		toolkit.createLabel(dialog, "");

		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();	
		if(metadatas == null) metadatas = new String[0];
		fieldCombo = SWTUtils.drawCombo(dialog, metadatas, axes != null && axes.getFields() != null ? axes.getFields() : null, "Field: ");
		fieldCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		super.drawProperties();

		Label grid = toolkit.createLabel(dialog, "Grid: ");
		grid.setBackground(defaultBackground);
		
		gridButton = SWTUtils.drawCheck(dialog, 
				axes != null && axes.getGrid() != null ? axes.getGrid() : false
						, "");

		String[] positions = new String[]{"left", "top", "right", "bottom"};

		positionCombo = SWTUtils.drawCombo(dialog, positions, axes != null && axes.getPosition() != null ? axes.getPosition() : null, "Position: ");
		positionCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		toolkit.createLabel(dialog, "");
		toolkit.createLabel(dialog, "");


		logger.debug("OUT");

	}	



	public void performOk(){
		logger.debug("IN");
		// save
		super.performOk();
		String valueField = null;
		axes.setType("Category");
		if(fieldCombo.getSelectionIndex() != -1){
			valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
			axes.setFields(valueField);
			logger.debug("field " +valueField);
		}
		else{
			logger.debug("no field selected");
		}

		Boolean track = gridButton.getSelection();
		axes.setGrid(track);		
		logger.debug("grid: "+track);

		String value = positionCombo.getItem(positionCombo.getSelectionIndex());
		axes.setPosition(value);
		logger.debug("psoition: "+value);

		// update the xChart in serie table
		TableItem[] items = editor.getMainChartPage().getRightPage().getSeriesBuilder().getSeriesTable().getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			SerieTableItemContent serieTableItemContent = (SerieTableItemContent)item.getData();
			Series serie = serieTableItemContent.getSerie();
			item.setText(SeriesPanel.CAT_AXE, valueField);
			serie.setxField(valueField);
		}
		
		// update the title in XPanel
		editor.getMainChartPage().getRightPage().getBottomAxeBuilder().getTitleLabel().setText(titleText.getText());
		
		logger.debug("OUT");

	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(300, 220);
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}
}
