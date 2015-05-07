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
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.ExtChartConstants;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;
import it.eng.spagobi.studio.extchart.utils.ImageDescriptors;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SerieTableItemContent;

import java.math.BigDecimal;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class SeriesPanel {

	Vector<Series> series;
	Group group;
	HashMap<Integer, Button> deleteButtons;
	HashMap<Integer, Button> customButtons;
	ExtChartEditor editor;

	Table seriesTable;

	public final static int INFO=0;
	public final static int CAT_AXE=1;
	public final static int AXES=2;	
	public final static int CUSTOMIZE=3;
	public final static int DELETE=4;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesPanel.class);


	public SeriesPanel(Composite parent, int style, Vector<Series> series) {
		this.series = series;
		group = new Group(parent, style);

		group.setLayout(SWTUtils.makeGridLayout(1));
		//group.setLayout(new FillLayout());
	}


	public void drawSerieComposite(){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(group.getParent());

		seriesTable = new Table (group, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);

		seriesTable.setLinesVisible (true);
		seriesTable.setHeaderVisible (true);

		GridData dataOrder = new GridData(SWT.FILL, SWT.FILL, true, true);
		dataOrder.heightHint = 150;
		dataOrder.widthHint=250;
		seriesTable.setLayoutData(dataOrder);

		String[] titles = {"    Serie Info    ", "    Category Axe    ", "       Value Axe      ", "    Customize    ", "    Delete    "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (seriesTable, SWT.NONE);
			column.setText (titles [i]);
		}

		for (Iterator iterator = series.iterator(); iterator.hasNext();) {
			Series serie = (Series) iterator.next();
			TableItem item = new TableItem (seriesTable, SWT.NONE);

			SerieTableItemContent serieTableItemContent = new SerieTableItemContent();
			serieTableItemContent.setSerie(serie);

			item.setData(serieTableItemContent);
			createButtons(seriesTable, item, serie, serieTableItemContent);
			item.setText(INFO, serie.getyFieldList());
			item.setText(CAT_AXE, serie.getxField() != null ? serie.getxField() : "");
		}

		for (int i=0; i<titles.length; i++) {
			seriesTable.getColumn (i).pack ();
		}

		logger.debug("Set drop function");
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		DropTarget target = new DropTarget(seriesTable, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
				}

				// Allow dropping text only
				for (int i = 0, n = event.dataTypes.length; i < n; i++) {
					if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
						event.currentDataType = event.dataTypes[i];
					}
				}
			}

			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
			}
			public void drop(DropTargetEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
					// Get the dropped data aaa
					ExtChart extChart = editor.getExtChart();
					DropTarget target = (DropTarget) event.widget;
					Table table = (Table) target.getControl();
					String data = (String) event.data;
					logger.debug("recieved from drop "+data);
					DraggedObject draggedObject = DraggedObject.fromString(data);
					
					
					// check there are only numeric type
					boolean onlyNumeric  = checkOnlyNumeric(draggedObject, group.getDisplay());

					if(onlyNumeric){
						Series newSerie= new Series();
						// map dragged info to serie, if single raw ask user if is x or y information, if multiple put to fieldList 
						if(draggedObject.getSize() > 0){
							logger.debug("More field selected: put field List");
							newSerie.setyFieldList(draggedObject.toFieldString());
						}
						else{
							// ask user if it is an x or y information
							//						String val = draggedObject.getFirstElement();
							//						new FieldXYPopup(seriesTable.getDisplay(), newSerie, val).drawXYPopup();						
						}

						logger.debug("update model adding new Serie");

						Axes xAxe = ExtChartUtils.getXAxe(extChart);
						newSerie.setxField(xAxe != null && xAxe.getFields() != null ? xAxe.getFields() : "");

						String serieType = null;
						try{
							serieType = ExtChartUtils.getSerieTypeFromChartType(extChart.getType());
							logger.debug("Serie type default for chart type is "+serieType);
						}
						catch (Exception e) {
							logger.warn("could not find default serie type, check congfiguration");
							return;
						}
						newSerie.setType(serieType);

						extChart.getSeriesList().getSeries().add(newSerie);

						logger.debug("update table");
						// Create a new item in the table to hold the dropped data
						TableItem item = new TableItem(table, SWT.NONE);

						SerieTableItemContent serieTableItemContent = new SerieTableItemContent();
						serieTableItemContent.setSerie(newSerie);

						item.setData(serieTableItemContent);
						//item.setText(new String[] { data });
						//item.setText(INFO, data);
						item.setText(INFO, draggedObject.toFieldString());
						item.setText(CAT_AXE, newSerie.getxField() != null ? newSerie.getxField() : "");


						createButtons(table, item, newSerie, serieTableItemContent);
						editor.setIsDirty(true);
					
					}
					else{
						logger.debug("Series not added");
					}
					
					table.redraw();
				}
			}
		});


		logger.debug("fix table height");
		//		if (group.getLayout() == null) { // <---
		//			seriesTable.setSize(200,300);
		//		} else {
		//			seriesTable.setLayoutData(new GridData(200, 300));
		//
		//		}

		//seriesTable.pack();
		logger.debug("OUT");


	}

	/**
	 *  Check only numric types can be dropped on serie
	 */
	boolean checkOnlyNumeric(DraggedObject draggedObject, Display display){
		logger.debug("IN");
		boolean onlyNumeric = true;
		for (Iterator iterator = draggedObject.getIndexTypeSelected().keySet().iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			System.out.println(Double.class.getName());
			String type = draggedObject.getIndexTypeSelected().get(key);
			if(type.equalsIgnoreCase(Double.class.getName())
			||
			type.equalsIgnoreCase(Integer.class.getName())
			||
			type.equalsIgnoreCase(Float.class.getName())
			||
			type.equalsIgnoreCase(Long.class.getName())
			||
			type.equalsIgnoreCase(Short.class.getName())
			||
			type.equalsIgnoreCase(BigDecimal.class.getName())
			)
			{
				logger.debug("Numeric type: "+type);
			}
			else{
				logger.debug("found also not numeric type "+type);
				onlyNumeric = false;
				MessageDialog.openWarning(display.getActiveShell(), "Warning", "Column of type "+type+" cannot be a serie: only numeric types");
				break;
			}		
		}
		logger.debug("OUT");
		return onlyNumeric;
	}
	

	void createButtons(final Table seriesTable, final TableItem item, final Series serie, SerieTableItemContent serieTableItemContent){
		logger.debug("IN");

		logger.debug("draw customize button");
		TableEditor tableEditor = new TableEditor(seriesTable);


		String[] positions =new String[]{"", "left", "right", "bottom", "top"};
		final CCombo positionCombo = SWTUtils.drawCCombo(seriesTable, positions, serie.getAxis(), "Axis: ");
		serieTableItemContent.setPositionCombo(positionCombo);
		positionCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String position = positionCombo.getItem(positionCombo.getSelectionIndex());
				ExtChart extChart = editor.getExtChart();
				// check Numeric axis at this position really exist otherwise throw warning
				Axes returned = ExtChartUtils.getAxeFromPositionAndType(extChart, ExtChartConstants.AXE_TYPE_NUMERIC, position);
				if(returned != null || position.equals("")){
					editor.setIsDirty(true);
					logger.debug("selected axe at position: "+position);
					serie.setAxis(position);
				}
				else{
					MessageDialog.openWarning(seriesTable.getShell(), "Warning", "Could not find a numeric axe on position "+position);
					int previousIndex = positionCombo.indexOf(serie.getAxis() != null ? serie.getAxis() : "");
					positionCombo.select(previousIndex);
				}

				// update axes
				//ExtChartUtils.updateAxesField(extChart, "Numeric", serie.getyFieldList(), position);
			}
		});
		//		tableEditor.minimumWidth = positionCombo.getSize().x;
		//		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		tableEditor.setEditor(positionCombo, item, AXES);



		tableEditor = new TableEditor(seriesTable);
		Image customImage = ImageDescriptors.getEditIcon().createImage();
		final Button buttonCus = new Button(seriesTable, SWT.PUSH);
		serieTableItemContent.setCustomButton(buttonCus);
		buttonCus.setImage(customImage);
		buttonCus.pack();

		tableEditor.minimumWidth = buttonCus.getSize().x;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.setEditor(buttonCus, item, CUSTOMIZE);

		buttonCus.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				// open edit window
				logger.debug("Open serie edit cell");
				ExtChart chart = editor.getExtChart();
				String type = chart.getType();
				logger.debug("Series properties for type "+type);
				SeriesProperties seriesProperties = PropertiesFactory.getSeriesProperties(type, editor, serie, seriesTable.getShell());
				seriesProperties.setTitle("Define serie properties: ");
				seriesProperties.drawProperties();
				seriesProperties.drawButtons();
				seriesProperties.getDialog().setSize(300,500);
				seriesProperties.showPopup();	
			}

		}
		);

		// delete
		logger.debug("draw delete button");
		tableEditor = new TableEditor(seriesTable);
		Image deleteImage = ImageDescriptors.getEraseIcon().createImage();
		final  Button buttonDel = new Button(seriesTable, SWT.PUSH);
		serieTableItemContent.setDeleteButton(buttonDel);

		buttonDel.setImage(deleteImage);
		buttonDel.pack();	

		tableEditor.minimumWidth = buttonDel.getSize().x;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.setEditor(buttonDel, item, DELETE);

		buttonDel.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				//delete table item
				ExtChart extChart = editor.getExtChart();
				Object serieO = item.getData();
				SerieTableItemContent serieTableItemContent = (SerieTableItemContent)serieO;
				editor.setIsDirty(true);
				Series serie = serieTableItemContent.getSerie();
				// delete serie from model

				int index = seriesTable.indexOf(item);
				logger.debug("remove row"+item.getText(INFO)+ " at index "+index);
				seriesTable.remove(index);
				buttonDel.dispose();
				buttonCus.dispose();
				positionCombo.dispose();
				extChart.getSeriesList().getSeries().remove(serie);


				logger.debug("row removed");

			}

		}
		);
		logger.debug("OUT");

	}


	public void deleteItems(){
		logger.debug("IN");
		TableItem[] items = seriesTable.getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem it = items[i]; 
			SerieTableItemContent serieTableItemContent = (SerieTableItemContent)it.getData();

			if(serieTableItemContent.getCustomButton() != null)
				serieTableItemContent.getCustomButton().dispose();
			if(serieTableItemContent.getDeleteButton() != null)
				serieTableItemContent.getDeleteButton().dispose();
			if(serieTableItemContent.getPositionCombo() != null)
				serieTableItemContent.getPositionCombo().dispose();
		}

		logger.debug("OUT");
	}


	public void deleteAllSeries(){
		logger.debug("IN");
		ExtChart extChart = editor.getExtChart();
		extChart.getSeriesList().setSeries(null);
		deleteItems();
		seriesTable.removeAll();
		seriesTable.redraw();
		logger.debug("OUT");
	}


	public ExtChartEditor getEditor() {
		return editor;
	}


	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	public Table getSeriesTable() {
		return seriesTable;
	}


	public void setSeriesTable(Table seriesTable) {
		this.seriesTable = seriesTable;
	}





}


