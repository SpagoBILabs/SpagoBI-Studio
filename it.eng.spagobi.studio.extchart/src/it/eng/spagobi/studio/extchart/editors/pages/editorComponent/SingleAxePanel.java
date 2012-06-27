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
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.ExtChartConstants;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;
import it.eng.spagobi.studio.extchart.utils.ImageDescriptors;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SerieTableItemContent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class SingleAxePanel {

	Vector<Axes> axes;
	Composite container;
	ExtChartEditor editor;
	Axes newAxe;
	String chartType;


	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SingleAxePanel.class);


	public SingleAxePanel(Composite parent, int style, Vector<Axes> axes) {
		this.axes = axes;
		
		container = new Composite(parent,SWT.NONE);
		container.setLayout(SWTUtils.makeGridLayout(1));
		
		if (axes.size() >0 ){
			newAxe = axes.get(0);
		} else {
			newAxe = new Axes();		
		}
		
	}


	public void drawAxeComposite(){
		logger.debug("IN");

		ExtChart extChart = editor.getExtChart();

		if (extChart.getAxesList().getAxes().isEmpty()){
			extChart.getAxesList().getAxes().add(newAxe);
		}
		
		Group grpAxeProperties = new Group(container, SWT.NONE);

		grpAxeProperties.setText("Axe Properties");
		grpAxeProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpAxeProperties.setLayout(new GridLayout(2, false));
		
		Label lblThisTypeOf = new Label(grpAxeProperties, SWT.NONE);
		lblThisTypeOf.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblThisTypeOf.setText("This type of chart has only one axe.");
		
		Button btnCustomizeAxe = new Button(grpAxeProperties, SWT.NONE);
		btnCustomizeAxe.setText("Customize Axe");
		btnCustomizeAxe.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				// open edit window
				logger.debug("Open axe edit cell");
				ExtChart chart = editor.getExtChart();
				String type = chart.getType();
				logger.debug("Axe properties for type "+type);
				AxesProperties axesProperties = PropertiesFactory.getAxesProperties(type, editor, newAxe, container.getShell());
				axesProperties.setTitle("Define axe properties: ");
				axesProperties.drawProperties();
				axesProperties.drawButtons();
				axesProperties.showPopup();	
			}

		}
		);
		logger.debug("OUT");


	}



	public ExtChartEditor getEditor() {
		return editor;
	}


	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}


	/**
	 * @return the container
	 */
	public Composite getContainer() {
		return container;
	}


	/**
	 * @param container the container to set
	 */
	public void setContainer(Composite container) {
		this.container = container;
	}





}


