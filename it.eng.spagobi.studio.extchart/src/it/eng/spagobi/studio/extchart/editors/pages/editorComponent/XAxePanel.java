/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.pages.editorComponent;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.PropertiesFactory;
import it.eng.spagobi.studio.extchart.editors.properties.axes.AxesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class XAxePanel{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(XAxePanel.class);

	Group group;

	ExtChartEditor editor;
	Axes axe;
	String projectName;
	Label titleLabel;

	public XAxePanel(Composite parent, int style, Axes axe) {
		logger.debug("IN");
		group = new Group(parent, style);
		group.setLayout(SWTUtils.makeGridLayout(2));
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


		Label whatIsLabel= toolkit.createLabel(group, "X Axe:");

		String title="no title set";
		if(axe != null && axe.getTitle() != null){
			title = axe.getTitle();
		}

		titleLabel= toolkit.createLabel(group, title);

		toolkit.createLabel(group, "Type: ");
		toolkit.createLabel(group, axeType);

		GridData gd=new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=2	;

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


		//		Table fieldTable = new Table (group, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		//		fieldTable.setLinesVisible (true);
		//		fieldTable.setHeaderVisible (true);
		//		fieldTable.setLayoutData(gd);
		//
		//		GridData dataOrder = new GridData(SWT.FILL, SWT.FILL, true, true);
		//		dataOrder.heightHint = 100;
		//		dataOrder.widthHint=50;
		//		fieldTable.setLayoutData(dataOrder);
		//
		//		String[] titlesOrder = {"   Field   "};
		//		for (int i=0; i<titlesOrder.length; i++) {
		//			TableColumn column = new TableColumn (fieldTable, SWT.NONE);
		//			column.setText (titlesOrder [i]);
		//		}
		//		if(axe != null && axe.getFields() != null){
		//			logger.debug("put fields in table ");
		//			String[] fields = axe.getFields().split(",");
		//			for (int i = 0; i<fields.length; i++) {
		//				logger.debug("field: " + fields[i]);
		//				String field = fields[i];
		//				TableItem item = new TableItem (fieldTable, SWT.NONE);
		//				item.setText(field);
		//			}
		//
		//		}
		//		for (int i=0; i<titlesOrder.length; i++) {
		//			fieldTable.getColumn (i).pack ();
		//		}	



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



	public Label getTitleLabel() {
		return titleLabel;
	}



	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}




}
