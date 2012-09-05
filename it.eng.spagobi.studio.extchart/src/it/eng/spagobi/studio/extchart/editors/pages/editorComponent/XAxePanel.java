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
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SerieTableItemContent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class XAxePanel{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(XAxePanel.class);

	Group group;

	ExtChartEditor editor;
	Axes axe;
	String projectName;
	Label titleLabel;
	Combo fieldCombo;
	Text titleText;
	Button gridButton;
	Combo positionCombo;

	public XAxePanel(Composite parent, int style, Axes axe) {
		logger.debug("IN");
		group = new Group(parent, style);
		group.setLayout(SWTUtils.makeGridLayout(2));
		//group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		this.axe = axe;
		logger.debug("xAxe is "+ axe == null ? "null" : "not null" );
		logger.debug("OUT");
	}



	public void drawAxeComposite(){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(group.getParent());

		ExtChart chart = editor.getExtChart();
		String type = chart.getType();
		logger.debug("chart of type "+type);

		String axeType = null;
		try{
			axeType = ExtChartUtils.getXAxeTypeFromChartType(type);
		}
		catch (Exception e) {
			logger.error("error in retrieving axe type from chart type "+type+": check configuration", e);
			throw new RuntimeException("error in retrieving axe type from chart type "+type+": check configuration");
		}
		logger.debug("X Axe type for the specified chart type is "+axeType);


		//Label whatIsLabel= toolkit.createLabel(group, "X Axe:");

//		String title="no title set";
//		if(axe != null && axe.getTitle() != null){
//			title = axe.getTitle();
//		}

		//titleLabel= toolkit.createLabel(group, title);
		
		group.setText("X Axe");
		Composite leftGroup = new Composite(group,SWT.NONE);
		leftGroup.setLayout(new GridLayout(2,false));
		leftGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));


		toolkit.createLabel(leftGroup, "Type: ");
		toolkit.createLabel(leftGroup, axeType);
		
		//
		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();	
		if(metadatas == null) metadatas = new String[0];
		fieldCombo = SWTUtils.drawCombo(leftGroup, metadatas, axe != null && axe.getFields() != null ? axe.getFields() : null, "Field: ");
		fieldCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		fieldCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String field = fieldCombo.getItem(fieldCombo.getSelectionIndex());
				// if null position delete a previously defined axis
				if(field == null || field.equals("")){
					logger.debug("Selected null position");	
					//delete axe object
					axe = null;
					disableAxe();

				} else {
					if(axe == null){
						axe = new Axes();
						axe.setType("Category");
						String valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
						axe.setFields(valueField);
						logger.debug("field " +valueField);
						updateSeriesTable(valueField);
						AxesList axesList = editor.getExtChart().getAxesList();
						Vector<Axes> axis = axesList.getAxes();
						axis.add(axe);
						editor.getExtChart().setAxesList(axesList);
					} else{
						logger.debug("modify previously defined");
						Axes xAxe =   ExtChartUtils.getXAxe(editor.getExtChart());
						if(xAxe != null){
							String valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
							axe.setFields(valueField);
							updateSeriesTable(valueField);
						}
					}
					enableAxe();

						
				}
			}
		});
		Composite rightGroup = new Composite(group,SWT.NONE);
		rightGroup.setLayout(new GridLayout(2,false));
		rightGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		
		Label titleLable = toolkit.createLabel(rightGroup, "Title: ");
		titleText = SWTUtils.drawText(toolkit, rightGroup, 
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
		
		Label grid = toolkit.createLabel(rightGroup, "Grid: ");
		
		gridButton = SWTUtils.drawCheck(rightGroup, 
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

		String[] positions = new String[]{"left", "top", "right", "bottom"};

		positionCombo = SWTUtils.drawCombo(rightGroup, positions, axe != null && axe.getPosition() != null ? axe.getPosition() : null, "Position: ");
		positionCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		positionCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String position = positionCombo.getItem(positionCombo.getSelectionIndex());
				axe.setPosition(position);

			}
		});
		
		if (axe == null)
			disableAxe();
		//


		GridData gd=new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=2	;
		
		

		/*
		Button customAxeButton = SWTUtils.drawButton(group, "Customize");
		customAxeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				logger.debug("Press customize X Axe button");

				ExtChart chart = editor.getExtChart();
				String type = chart.getType();
				logger.debug("X Axes  properties for type "+type);

				// return the axe to be modified, if exist, otherwise create it

				Axes xAxes = ExtChartUtils.getXAxe(chart);
				if(xAxes != null) logger.debug("found an x Axe");

				//				if(xAxes == null){
				//					logger.debug("No X Axes was found so create it");
				//					Axes xAxe= new Axes();
				//					String chartType;
				//					try {
				//						chartType = ExtChartUtils.getXAxeTypeFromChartType(type);
				//					} catch (Exception e) {
				//						logger.error("Error in retrieving correct axe type",e);
				//						throw new RuntimeException("Error in retrieving correct axe type",e);
				//					}
				//					xAxe.setType(chartType);
				//					// do not add axe unless user press ok
				//					//chart.getAxesList().getAxes().add(xAxe);
				//				}

				AxesProperties axesProperties = PropertiesFactory.getAxesProperties(type, editor, xAxes, group.getShell());
				axesProperties.drawProperties();
				axesProperties.drawButtons();
				axesProperties.showPopup();	



			}
		});
		customAxeButton.setLayoutData(gd);
		*/






		logger.debug("OUT");
	}
	
	public void enableAxe(){
		titleText.setEnabled(true);
		gridButton.setEnabled(true);	
		positionCombo.setEnabled(true);
	}

	public void disableAxe(){
		titleText.setEnabled(false);
		gridButton.setEnabled(false);
		positionCombo.setEnabled(false);
		
	}
	
	private void updateSeriesTable(String valueField){
		// update the xChart in serie table
		TableItem[] items = editor.getMainChartPage().getRightPage().getSeriesBuilder().getSeriesTable().getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			SerieTableItemContent serieTableItemContent = (SerieTableItemContent)item.getData();
			Series serie = serieTableItemContent.getSerie();
			item.setText(SeriesPanel.CAT_AXE, valueField);
			serie.setxField(valueField);
		}
	}
	
	//reset UI components to initial status
	public void clearAll(){
		if (fieldCombo.isEnabled()){
			fieldCombo.clearSelection();
			fieldCombo.deselectAll();
		}
		
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



	public Label getTitleLabel() {
		return titleLabel;
	}



	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public void refreshFieldCombo(){
		fieldCombo.removeAll();
		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();	
		if(metadatas == null) metadatas = new String[0];
	
		for (int i = 0; i < metadatas.length; i++) {
			String comboContent = metadatas[i];
			fieldCombo.add(comboContent);
			}
		}
	
	



}
