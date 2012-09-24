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
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class AxesProperties extends PopupPropertiesDialog{

	FormToolkit toolkit;
	//Shell comp;
	ExtChartEditor editor;
	Text titleText;
	//Combo positionCombo;
	Axes axes;
	YAxePanel yAxePanel;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesProperties.class);

	public AxesProperties(ExtChartEditor editor, Shell  comp) {
		super(editor, comp);
		this.editor = editor;
		toolkit = new FormToolkit(comp.getDisplay());
	}

	public void drawProperties(){
		logger.debug("IN");
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 

		super.drawProperties();
		Label titleLable = toolkit.createLabel(dialog, "Title: ");
		titleLable.setBackground(defaultBackground);
		titleText = SWTUtils.drawText(toolkit, dialog, 
				axes != null && axes.getTitle() != null ? axes.getTitle() : null
						, null);
		titleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		logger.debug("OUT");
	};


	

	public void performOk(){
		logger.debug("IN");
		editor.setIsDirty(true);

		if(axes == null){
			logger.debug("create new Axes");
			axes = new Axes();
	
			// add it to chart
			ExtChart chart = editor.getExtChart();
			chart.getAxesList().getAxes().add(axes);
			
		}
		else{
			logger.debug("modifying existing axe");
		}


		String valText = titleText.getText();
		axes.setTitle(valText);		
		logger.debug("text " + valText);

//		String value = positionCombo.getItem(positionCombo.getSelectionIndex());
//		axes.setPosition(value);
		logger.debug("OUT");		
	}

	public Axes getAxes() {
		return axes;
	}

	public void setAxes(Axes axes) {
		this.axes = axes;
	}

	public YAxePanel getyAxePanel() {
		return yAxePanel;
	}

	public void setyAxePanel(YAxePanel yAxePanel) {
		this.yAxePanel = yAxePanel;
	}


}
