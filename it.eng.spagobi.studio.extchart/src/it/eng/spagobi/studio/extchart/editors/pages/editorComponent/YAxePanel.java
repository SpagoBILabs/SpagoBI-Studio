/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.pages.editorComponent;

import java.util.Vector;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.PropertiesFactory;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.AxesList;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class YAxePanel{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(YAxePanel.class);

	Group group;


	ExtChartEditor editor;
	Axes axe;
	String projectName;
	Combo positionCombo;
	Label titleLabel;
	Button customAxeButton;
	String axeType;
	Text titleText;
	Button gridButton;
	

	public YAxePanel(Composite parent, int style, Axes axe, String _axeType) {
		group = new Group(parent, style);
		group.setLayout(SWTUtils.makeGridLayout(2));
		group.setText("Y Axe");
		this.axe = axe;
		this.axeType = axeType;
	}



	public void drawAxeComposite(){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(group.getParent());

		String[] positions = new String[]{"", "left", "top", "right", "bottom"};
		positionCombo = SWTUtils.drawCombo(group, positions, axe != null ? axe.getPosition() : "", "Position: ");

		// if selecting a position add a new Axe, if selecting none delete the axis from chart 
		final String axesType = axeType;
		positionCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String position = positionCombo.getItem(positionCombo.getSelectionIndex());
				ExtChart extChart = editor.getExtChart();

				// if null position delete a previously defined axis
				if(position == null || position.equals("")){
					logger.debug("Selected null position");	
					if(axe != null){
						logger.debug("delete axes previously present in position "+axe.getPosition());	
						ExtChartUtils.deleteNumericAxe(extChart, axesType, axe.getPosition());
						axe= null;
					}
					disableAxe();

				}
				else{
					// if not null position add a new Axis or change the previously defined
					if(axe == null){
						logger.debug("Add a new Axis");
						axe = new Axes();
						axe.setPosition(position);
						axe.setType(axesType);
						AxesList axesList = editor.getExtChart().getAxesList();
						Vector<Axes> axis = axesList.getAxes();
						axis.add(axe);
						editor.getExtChart().setAxesList(axesList);
					}
					else{
						logger.debug("modify previously defined");
						String oldPosition = axe.getPosition();
						Axes prevAxe = ExtChartUtils.getAxeFromPosition(editor.getExtChart(), oldPosition);
						if(prevAxe != null){
							prevAxe.setPosition(position);
						}
					}
					enableAxe();
				}
			}
		});





		//Label whatIsLabel= toolkit.createLabel(group, "Y Axe:");
		//titleLabel = toolkit.createLabel(group, "No title set");

		toolkit.createLabel(group, "Type: ");
		toolkit.createLabel(group, "Numeric");


		GridData gd=new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=2	;

		/*
		customAxeButton = SWTUtils.drawButton(group, "Customize");
		final YAxePanel thisPanel = this;
		customAxeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				logger.debug("Press customize Y Axe button");
				ExtChart chart = editor.getExtChart();
				String type = chart.getType();
				logger.debug("Y Axes  properties for type "+type);
				
				// axe cannot be null if could open customize
				AxesProperties axesProperties = PropertiesFactory.getYAxesProperties(type, editor, axe, group.getShell());
				axesProperties.drawProperties();
				axesProperties.drawButtons();
				axesProperties.setyAxePanel(thisPanel);
				axesProperties.showPopup();	
				
				
//				Axes yAxes = ExtChartUtils.getYAxe(chart);
//				if(yAxes != null) logger.debug("found an y Axe in position "+yAxes);


			}
		});
		customAxeButton.setLayoutData(gd);
		*/
		
		
		Color defaultBackground = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 

		Label titleLable = toolkit.createLabel(group, "Title: ");
		titleText = SWTUtils.drawText(toolkit, group, 
				axe != null && axe.getTitle() != null ? axe.getTitle() : null
						, null);
		titleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		titleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String titleValue = titleText.getText();
				axe.setTitle(titleValue);
			}
		});
		
		Label grid = toolkit.createLabel(group, "Grid: ");
		
		gridButton = SWTUtils.drawCheck(group, 
				axe != null && axe.getGrid() != null ? axe.getGrid() : false
						, "");
		gridButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				Boolean track = gridButton.getSelection();
				axe.setGrid(track);		
				logger.debug("grid: "+track);
			}
		});
		
		
		ExtChart chart = editor.getExtChart();
		Axes yAxes = ExtChartUtils.getYAxe(chart);
		if(yAxes != null) logger.debug("found an y Axe in position "+yAxes);
		 
		
		// if position is not set do not enable other widgets
		boolean  enable = true;
		if(axe == null || axe.getPosition() == null || axe.getPosition().equals("")) 
			disableAxe();
		else
			enableAxe();
		
		logger.debug("OUT");
		
		
		
	}




	public ExtChartEditor getEditor() {
		return editor;
	}

	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}



	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void enableAxe(){
		//customAxeButton.setEnabled(true);
		//titleLabel.setEnabled(true);
		
		titleText.setEnabled(true);
		gridButton.setEnabled(true);
		
	}

	public void disableAxe(){
		//customAxeButton.setEnabled(false);
		//titleLabel.setEnabled(false);
		
		titleText.setEnabled(false);
		gridButton.setEnabled(false);
		

	}



	public Label getTitleLabel() {
		return titleLabel;
	}



	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}



	public String getAxeType() {
		return axeType;
	}



	public void setAxeType(String axeType) {
		this.axeType = axeType;
	}
	
	//reset UI components to initial status
	public void clearAll(){
		if(titleText.isEnabled()){
			titleText.clearSelection();
			titleText.setText("");
			titleText.update();
			titleText.isEnabled();
		}
		if (gridButton.isEnabled()){
			gridButton.setSelection(false);
		}
		if (positionCombo.isEnabled()){
			positionCombo.clearSelection();
			positionCombo.deselectAll();
		}
		
		//reset also model object
		axe=null;

		disableAxe();
	}


}
