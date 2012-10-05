/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

**/
package it.eng.spagobi.studio.console.editors.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import it.eng.spagobi.studio.console.dialogs.LiveLinesSettingsDialog;
import it.eng.spagobi.studio.console.dialogs.MultiLedsSettingsDialog;
import it.eng.spagobi.studio.console.dialogs.SemaphoreSettingsDialog;
import it.eng.spagobi.studio.console.dialogs.SpeedometerSettingsDialog;
import it.eng.spagobi.studio.console.editors.ConsoleEditor;
import it.eng.spagobi.studio.console.editors.internal.SummaryPanelPageTableRow;
import it.eng.spagobi.studio.console.model.bo.Chart;
import it.eng.spagobi.studio.console.model.bo.ConsoleTemplateModel;
import it.eng.spagobi.studio.console.model.bo.DatasetElement;
import it.eng.spagobi.studio.console.model.bo.LayoutManagerConfig;
import it.eng.spagobi.studio.console.model.bo.SummaryPanel;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElement;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementLiveLine;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementMultiLeds;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementSemaphore;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementSpeedometer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class SummaryPanelPage extends AbstractPage {
	private ConsoleEditor editor;
	private String projectName;
	private ConsoleTemplateModel consoleTemplateModel;
	private Text textHeight;
	private Table tableWidgets;
	private Text textColumnNumber;
	private Text textColumnsWidth;
	private Combo comboCollapsed;
	private Combo comboCollasable;
	private Combo comboHidden;
	
	public static final int COLUMN_TITLE = 0;
	public static final int COLUMN_DATASET = 1;
	public static final int COLUMN_WIDTH = 2;
	public static final int COLUMN_HEIGHT = 3;
	public static final int COLUMN_TYPE = 4;
	public static final int COLUMN_DEFINE_WIDGET_BUTTON = 5;
	public static final int COLUMN_REMOVE_WIDGET_BUTTON = 6;

	
	private List<SummaryPanelPageTableRow> summaryPanelPageTableRows;

	
	
	/**
	 * @param parent
	 * @param style
	 */
	public SummaryPanelPage(Composite parent, int style) {
		super(parent, style);

	}
	public void drawPage(){
		summaryPanelPageTableRows = new ArrayList<SummaryPanelPageTableRow>();
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group grpLayoutProperties = new Group(mainComposite, SWT.NONE);
		grpLayoutProperties.setText("Layout Properties");
		grpLayoutProperties.setLayout(new GridLayout(2, false));
		grpLayoutProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Composite compositeGeneralProperties = new Composite(grpLayoutProperties, SWT.NONE);
		compositeGeneralProperties.setLayout(new GridLayout(4, false));
		
		Label lblCollapsed = new Label(compositeGeneralProperties, SWT.NONE);
		lblCollapsed.setSize(55, 15);
		lblCollapsed.setText("Collapsed:");
		
		comboCollapsed = new Combo(compositeGeneralProperties, SWT.READ_ONLY);
		comboCollapsed.setSize(53, 23);
		comboCollapsed.setItems(new String[] {"true", "false"});
		//Check if previous created object is found
		if (consoleTemplateModel.getSummaryPanel() != null){
			boolean value = consoleTemplateModel.getSummaryPanel().isCollapsed();
			String[] items = comboCollapsed.getItems();
			for (int i=0; i<items.length ;i++){
				if(items[i].equals(String.valueOf(value))){
					comboCollapsed.select(i);
					break;
				}
			}
		}	
		comboCollapsed.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				SummaryPanel summaryPanel = getSummaryPanel();
				boolean isCollapsed = Boolean.parseBoolean(comboCollapsed.getItem(comboCollapsed.getSelectionIndex()));
				summaryPanel.setCollapsed(isCollapsed);
				
			}
		});
		
		Label lblCollasable = new Label(compositeGeneralProperties, SWT.NONE);
		lblCollasable.setSize(57, 15);
		lblCollasable.setText("Collasable:");
		
		comboCollasable = new Combo(compositeGeneralProperties, SWT.READ_ONLY);
		comboCollasable.setSize(76, 23);
		comboCollasable.setItems(new String[] {"true", "false"});
		//Check if previous created object is found
		if (consoleTemplateModel.getSummaryPanel() != null){
			boolean value = consoleTemplateModel.getSummaryPanel().isCollassable();
			String[] items = comboCollasable.getItems();
			for (int i=0; i<items.length ;i++){
				if(items[i].equals(String.valueOf(value))){
					comboCollasable.select(i);
					break;
				}
			}
		}		
		comboCollasable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				SummaryPanel summaryPanel = getSummaryPanel();
				boolean isCollasable = Boolean.parseBoolean(comboCollasable.getItem(comboCollasable.getSelectionIndex()));
				summaryPanel.setCollassable(isCollasable);
				
			}
		});
		
		Label lblHidden = new Label(compositeGeneralProperties, SWT.NONE);
		lblHidden.setSize(42, 15);
		lblHidden.setText("Hidden:");
		
		comboHidden = new Combo(compositeGeneralProperties, SWT.READ_ONLY);
		comboHidden.setSize(53, 23);
		comboHidden.setItems(new String[] {"true", "false"});
		//Check if previous created object is found
		if (consoleTemplateModel.getSummaryPanel() != null){
			boolean value = consoleTemplateModel.getSummaryPanel().isHidden();
			String[] items = comboHidden.getItems();
			for (int i=0; i<items.length ;i++){
				if(items[i].equals(String.valueOf(value))){
					comboHidden.select(i);
					break;
				}
			}
		}	
		comboHidden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				SummaryPanel summaryPanel = getSummaryPanel();
				boolean isHidden = Boolean.parseBoolean(comboHidden.getItem(comboHidden.getSelectionIndex()));
				summaryPanel.setHidden(isHidden);
				
			}
		});
		
		Label lblHeight = new Label(compositeGeneralProperties, SWT.NONE);
		lblHeight.setSize(39, 15);
		lblHeight.setText("Height:");
		
		textHeight = new Text(compositeGeneralProperties, SWT.BORDER);
		textHeight.setSize(76, 21);
		//Check if previous created object is found
		if (consoleTemplateModel.getSummaryPanel() != null){
			String value = String.valueOf(consoleTemplateModel.getSummaryPanel().getHeight());
			if (consoleTemplateModel.getSummaryPanel().getHeight() != 0){
				textHeight.setText(value);				
			}
		}	
		textHeight.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				SummaryPanel summaryPanel = getSummaryPanel();
				int heightValue =  Integer.parseInt(textHeight.getText());
				summaryPanel.setHeight(heightValue);				
			}
		});
		
		Group groupLayoutType = new Group(grpLayoutProperties, SWT.NONE);
		groupLayoutType.setText("Column Layout");
		groupLayoutType.setLayout(new GridLayout(2, false));
		groupLayoutType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblNumOfColumns = new Label(groupLayoutType, SWT.NONE);
		lblNumOfColumns.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNumOfColumns.setText("Num. of columns:");
		
		textColumnNumber = new Text(groupLayoutType, SWT.BORDER);
		textColumnNumber.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		//Check if previous created object is found
		if (consoleTemplateModel.getSummaryPanel() != null){
			int value = consoleTemplateModel.getSummaryPanel().getLayoutConfig().getColumnNumber();
			String columnNumber = String.valueOf(value);
			if (value != 0){
				textColumnNumber.setText(columnNumber);				
			}
		}	
		textColumnNumber.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				SummaryPanel summaryPanel = getSummaryPanel();
				LayoutManagerConfig layoutManagerConfig = summaryPanel.getLayoutConfig();
				int columnNumber =  Integer.parseInt(textColumnNumber.getText());
				layoutManagerConfig.setColumnNumber(columnNumber);	
				//update also columnsWidth if necessary 
				if (!textColumnsWidth.getText().isEmpty()){
					//add columns width element to the object model
					if (columnNumber >= 0){
						editor.setIsDirty(true);
						insertColumnsWidth(columnNumber,textColumnsWidth.getText());
					}	
				}
			}
		});
		
		Label lblColumnsWidths = new Label(groupLayoutType, SWT.NONE);
		lblColumnsWidths.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblColumnsWidths.setText("Columns Widths:");
		
		textColumnsWidth = new Text(groupLayoutType, SWT.BORDER);
		textColumnsWidth.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		//Check if previous created object is found
		if (consoleTemplateModel.getSummaryPanel() != null){
			if (!consoleTemplateModel.getSummaryPanel().getLayoutConfig().getColumnWidths().isEmpty()){
				//get first element in ColumnWidths
				String columnWidth = consoleTemplateModel.getSummaryPanel().getLayoutConfig().getColumnWidths().get(0);		
				textColumnsWidth.setText(columnWidth);
			}
		}	
		textColumnsWidth.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				String columnsWidth = textColumnsWidth.getText();
				if (!columnsWidth.isEmpty()){
					int columnNumber =  Integer.parseInt(textColumnNumber.getText());
					//add columns width element to the object model
					if (columnNumber >= 0){
						editor.setIsDirty(true);
						insertColumnsWidth(columnNumber,columnsWidth);
					}						
				}
		
			}
		});
		new Label(grpLayoutProperties, SWT.NONE);
		
		Group grpWidgets = new Group(mainComposite, SWT.NONE);
		grpWidgets.setText("Widgets");
		grpWidgets.setLayout(new GridLayout(1, false));
		grpWidgets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite composite = new Composite(grpWidgets, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnAddWidget = new Button(composite, SWT.NONE);
		btnAddWidget.setText("Add Widget");
		btnAddWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				createTableWidgetItem(null);
			}
		});
		
		/*
		Button btnRemoveWidget = new Button(composite, SWT.NONE);
		btnRemoveWidget.setText("Remove Widget");
		btnRemoveWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//remove table widget item from UI and from model
				if (tableWidgets.getSelectionIndex() != -1){
					editor.setIsDirty(true);
					//remove widget item from UI
					int index = tableWidgets.getSelectionIndex();
					summaryPanelPageTableRows.get(index).disposeRowElements();
					
					//remove widget from model
					if (consoleTemplateModel.getSummaryPanel() != null){
						if (!consoleTemplateModel.getSummaryPanel().getCharts().isEmpty()){
							
							consoleTemplateModel.getSummaryPanel().getCharts().remove(index);
						}
					}
					
					tableWidgets.redraw();
				}
			}
		});
		*/
		
		Label lblCurrentWidgets = new Label(grpWidgets, SWT.NONE);
		lblCurrentWidgets.setText("Current Widgets");
		
		tableWidgets = new Table(grpWidgets, SWT.BORDER | SWT.FULL_SELECTION);
		tableWidgets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableWidgets.setHeaderVisible(true);
		tableWidgets.setLinesVisible(true);
		tableWidgets.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				Rectangle clientArea = tableWidgets.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = tableWidgets.getTopIndex(); 
			}
			}); 
		
		TableColumn tblclmnWidgetTitle = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnWidgetTitle.setWidth(100);
		tblclmnWidgetTitle.setText("Title");
		
		TableColumn tblclmnDataset = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnDataset.setWidth(156);
		tblclmnDataset.setText("Dataset");	
		
		TableColumn tblclmnWidgetWidth = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnWidgetWidth.setWidth(100);
		tblclmnWidgetWidth.setText("Width");
		
		TableColumn tblclmnWidgetHeight = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnWidgetHeight.setWidth(100);
		tblclmnWidgetHeight.setText("Height");		
		
		TableColumn tblclmnWidgetType = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnWidgetType.setWidth(177);
		tblclmnWidgetType.setText("Widget Type");
		
		TableColumn tblclmnButtonDefine = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnButtonDefine.setWidth(100);
		tblclmnButtonDefine.setText("Define Widget");	
		
		TableColumn tblclmnSelectItem = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnSelectItem.setWidth(100);
		tblclmnSelectItem.setText("Remove widget");
		
		
		//-----------------------
		
		//Check for previously created Widget Items and populate the table
		if (consoleTemplateModel.getSummaryPanel() != null){
			if (!consoleTemplateModel.getSummaryPanel().getCharts().isEmpty()){
				populateWidgetTable(consoleTemplateModel.getSummaryPanel().getCharts());
			}
		}

		
		
	}
	
	//populate the WidgetTable with the passed charts as argument
	public void populateWidgetTable(Vector<Chart> charts){
		for (Chart chart:charts){
			//create empty widget row
			SummaryPanelPageTableRow summaryPanelPageTableRow = createTableWidgetItem(chart);			
			//populate each column of the row with the existing value found
			
			//Text Title column
			Text textTitle = summaryPanelPageTableRow.getTextTitle();
			String titleValue = chart.getWidgetConfig().getTitle();
			textTitle.setText(titleValue);
			//Combo Dataset column
			CCombo comboDataset = summaryPanelPageTableRow.getComboDataset();
			String datasetValue = chart.getDataset();
			selectCComboElement(comboDataset,datasetValue);
			//Text Width column
			Text textWidth = summaryPanelPageTableRow.getTextWidth();
			String widthValue = String.valueOf(chart.getWidth());
			textWidth.setText(widthValue);			
			//Text Height column
			Text textHeight = summaryPanelPageTableRow.getTextHeight();
			String heightValue = String.valueOf(chart.getHeight());;
			textHeight.setText(heightValue);	
			//Combo Type colum
			CCombo comboWidgetType = summaryPanelPageTableRow.getComboWidgetType();
			String widgetTypeValue = chart.getWidgetConfig().getType();
			selectCComboElement(comboWidgetType,widgetTypeValue);

		}
		//editor is not dirty
		editor.setIsDirty(false);

		tableWidgets.redraw();
	}
	
	public SummaryPanelPageTableRow createTableWidgetItem(Chart existingChart){
		final TableItem item = new TableItem(tableWidgets, SWT.NONE);
		//create a new Chart object model and add to the Summary panel
		if (existingChart == null){
			Chart newChart = new Chart();
			//generic WidgetConfigElement only used as placeholder, will be transformed to a specific type
			WidgetConfigElement genericWidgetConfigElement = new WidgetConfigElement();
			newChart.setWidgetConfig(genericWidgetConfigElement);
			getSummaryPanel().getCharts().add(newChart);
			item.setData(newChart);
		} else {
			item.setData(existingChart);			
		}
			

		
		
		//create Cell Editor Text Title
		TableEditor editor_title = new TableEditor(tableWidgets);
		final Text textTitle = new Text(tableWidgets, SWT.NONE);
		editor_title.grabHorizontal = true;
		textTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				if (!textTitle.getText().isEmpty()){
					Chart itemChart = (Chart)item.getData();
					WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
					widgetConfigElement.setTitle(textTitle.getText());
				}
				
			}
		});
		editor_title.setEditor(textTitle,item, COLUMN_TITLE);

		
		//create Cell Editor Combo Dataset
		TableEditor editor_dataset = new TableEditor(tableWidgets);
		final CCombo comboDataset = new CCombo(tableWidgets, SWT.READ_ONLY);
		editor_dataset.grabHorizontal = true;
		populateDatasetLabelCombo(comboDataset);
		comboDataset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				Chart itemChart = (Chart)item.getData();
				itemChart.setDataset(comboDataset.getText());				
			}
		});
		editor_dataset.setEditor(comboDataset,item, COLUMN_DATASET);

		
		//create Cell Editor Text Width
		TableEditor editor_width = new TableEditor(tableWidgets);
		final Text textWidth = new Text(tableWidgets, SWT.NONE);
		editor_width.grabHorizontal = true;
		textWidth.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				if (!textWidth.getText().isEmpty()){
					int width = Integer.parseInt(textWidth.getText());
					Chart itemChart = (Chart)item.getData();
					itemChart.setWidth(width);				
				}
				
			}
		});
		editor_width.setEditor(textWidth,item, COLUMN_WIDTH);

		
		//create Cell Editor Text Height
		TableEditor editor_height = new TableEditor(tableWidgets);
		final Text textHeight = new Text(tableWidgets, SWT.NONE);
		editor_height.grabHorizontal = true;
		textHeight.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				if (!textHeight.getText().isEmpty()){
					int height = Integer.parseInt(textHeight.getText());
					Chart itemChart = (Chart)item.getData();
					itemChart.setHeight(height);				
				}
				
				
			}
		});
		editor_height.setEditor(textHeight,item, COLUMN_HEIGHT);

		
		//create Cell Editor Combo Type
		TableEditor editor_type = new TableEditor(tableWidgets);
		final CCombo comboType = new CCombo(tableWidgets, SWT.READ_ONLY);
		comboType.add("chart.sbi.livelines");
		comboType.add("chart.sbi.multileds");
		comboType.add("chart.sbi.speedometer");
		comboType.add("chart.sbi.semaphore");
		editor_type.grabHorizontal = true;
		comboType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				Chart itemChart = (Chart)item.getData();
				WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
				widgetConfigElement.setType(comboType.getText());			
				
			}
		});
		editor_type.setEditor(comboType,item, COLUMN_TYPE);
		
		//create Cell Editor Button Define Widget
		TableEditor editor_button = new TableEditor(tableWidgets);
		final Button buttonDefineWidget = new Button(tableWidgets, SWT.NONE);
		buttonDefineWidget.setText("Define Widget");
		editor_button.grabHorizontal = true;
		buttonDefineWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				//Open a popup to define the properties of the widget depending on the type
				//check if Widget Type is set
				if (comboType.getSelectionIndex() == -1){
					//No type selected
					MessageDialog.openWarning(new Shell(), "Warning", "No Widget Type Select, please select a type."); 
				} else {
					if (comboType.getText().equals("chart.sbi.livelines")){
						//Search previous widget settings
						Chart itemChart = (Chart)item.getData();
						WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
						LiveLinesSettingsDialog dialog = new LiveLinesSettingsDialog(new Shell());
						if (widgetConfigElement instanceof WidgetConfigElementLiveLine){
							dialog.setWidgetConfigElementLiveLine((WidgetConfigElementLiveLine) widgetConfigElement);
						}
						dialog.create();
						if (dialog.open() == Window.OK) {
							WidgetConfigElementLiveLine widgetConfigElementLiveLine = dialog.getWidgetConfigElementLiveLine();
							//replace generic WidgetConfigElement object with specific WidgetConfigElementLiveLine
							//Chart itemChart = (Chart)item.getData();
							//WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
							widgetConfigElementLiveLine = (WidgetConfigElementLiveLine) copyGenericProperties(widgetConfigElement,widgetConfigElementLiveLine);
							itemChart.setWidgetConfig(widgetConfigElementLiveLine);


						} 
						
					} else if (comboType.getText().equals("chart.sbi.multileds")){
						MultiLedsSettingsDialog dialog = new MultiLedsSettingsDialog(new Shell());
						dialog.create();
						if (dialog.open() == Window.OK) {
							WidgetConfigElementMultiLeds widgetConfigElementMultiLeds = dialog.getWidgetConfigElementMultiLeds();
							//replace generic WidgetConfigElement object with specific WidgetConfigElementMultiLeds
							Chart itemChart = (Chart)item.getData();
							WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
							widgetConfigElementMultiLeds = (WidgetConfigElementMultiLeds) copyGenericProperties(widgetConfigElement,widgetConfigElementMultiLeds);
							itemChart.setWidgetConfig(widgetConfigElementMultiLeds);
						} 
						
					} else if (comboType.getText().equals("chart.sbi.speedometer")){
						//Search previous widget settings
						Chart itemChart = (Chart)item.getData();
						WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
						SpeedometerSettingsDialog dialog = new SpeedometerSettingsDialog(new Shell());
						if (widgetConfigElement instanceof WidgetConfigElementSpeedometer){
							dialog.setWidgetConfigElementSpeedometer((WidgetConfigElementSpeedometer) widgetConfigElement);
						}
						dialog.create();
						if (dialog.open() == Window.OK) {
							WidgetConfigElementSpeedometer widgetConfigElementSpeedometer = dialog.getWidgetConfigElementSpeedometer();
							//replace generic WidgetConfigElement object with specific WidgetConfigElementSpeedometer
							//Chart itemChart = (Chart)item.getData();
							//WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
							widgetConfigElementSpeedometer = (WidgetConfigElementSpeedometer) copyGenericProperties(widgetConfigElement,widgetConfigElementSpeedometer);
							itemChart.setWidgetConfig(widgetConfigElementSpeedometer);
						} 
					} else if (comboType.getText().equals("chart.sbi.semaphore")){
						//Search previous widget settings
						Chart itemChart = (Chart)item.getData();
						WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();
						SemaphoreSettingsDialog dialog = new SemaphoreSettingsDialog(new Shell());
						if (widgetConfigElement instanceof WidgetConfigElementSemaphore){
							dialog.setWidgetConfigElementSemaphore((WidgetConfigElementSemaphore) widgetConfigElement);
						}
						dialog.create();
						if (dialog.open() == Window.OK) {
							WidgetConfigElementSemaphore widgetConfigElementSemaphore = dialog.getWidgetConfigElementSemaphore();
							//replace generic WidgetConfigElement object with specific WidgetConfigElementSemaphore
							//Chart itemChart = (Chart)item.getData();
							//WidgetConfigElement widgetConfigElement = itemChart.getWidgetConfig();

							widgetConfigElementSemaphore = (WidgetConfigElementSemaphore) copyGenericProperties(widgetConfigElement,widgetConfigElementSemaphore);
							itemChart.setWidgetConfig(widgetConfigElementSemaphore);
						} 

					} 
				}
				
			}
		});
		editor_button.setEditor(buttonDefineWidget,item, COLUMN_DEFINE_WIDGET_BUTTON);
		
		//create Cell Editor Button Remove Widget
		TableEditor editor_button_remove = new TableEditor(tableWidgets);
		final Button buttonRemoveWidget = new Button(tableWidgets, SWT.NONE);
		buttonRemoveWidget.setText("Remove Widget");
		editor_button_remove.grabHorizontal = true;
		buttonRemoveWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				//find item in summaryPanelPageTableRows
				int index = findItemInSummaryPanelPageTableRows(summaryPanelPageTableRows,item);
				if (index >= 0){
					//remove widget item from UI
					summaryPanelPageTableRows.get(index).disposeRowElements();
					summaryPanelPageTableRows.remove(index);
					//remove widget from model
					if (consoleTemplateModel.getSummaryPanel() != null){
						if (!consoleTemplateModel.getSummaryPanel().getCharts().isEmpty()){
							
							consoleTemplateModel.getSummaryPanel().getCharts().remove(index);
						}
					}
				}

				tableWidgets.redraw();
				
			}
		});
		editor_button_remove.setEditor(buttonRemoveWidget,item, COLUMN_REMOVE_WIDGET_BUTTON);
		
		//---------
		
		//create internal object with UI elements of this item
		SummaryPanelPageTableRow summaryPanelPageTableRow = new SummaryPanelPageTableRow(item,textTitle,comboDataset,textWidth,textHeight,comboType,buttonDefineWidget,buttonRemoveWidget);
		summaryPanelPageTableRows.add(summaryPanelPageTableRow);
		
		tableWidgets.redraw();
		return summaryPanelPageTableRow;


	}
	
	//search a TableItem inside the collection of SummaryPanelPageTableRow and if found return the index position of the element
	private int findItemInSummaryPanelPageTableRows(List<SummaryPanelPageTableRow> summaryPanelPageTableRows, TableItem item){
		int index = -1;

		if (summaryPanelPageTableRows != null){
			index = 0;
			boolean found = false;
			for(SummaryPanelPageTableRow element:summaryPanelPageTableRows){
				if (element.getTableItem().equals(item)){
					return index;
				} else {
					index++;
				}
			}
			if (!found){
				index= -1;
			}
		}
		return index;

	}
	
	public WidgetConfigElement copyGenericProperties(WidgetConfigElement oldObject, WidgetConfigElement newObject ){
		String title = oldObject.getTitle();
		String type = oldObject.getType();
		
		newObject.setTitle(title);
		newObject.setType(type);
		
		return newObject;
	}
	
	public void populateDatasetLabelCombo(CCombo comboDatasetLabel){
		comboDatasetLabel.removeAll();

		Vector<DatasetElement> datasets = consoleTemplateModel.getDatasets();
		if (!datasets.isEmpty() ){
			for (DatasetElement datasetElement:datasets){
				comboDatasetLabel.add(datasetElement.getId());
			}
		}
		

	}
	
	
	//create n elements of columnsWidth in layoutManagerConfig object.
	//columnNumber must be >= 0
	public void insertColumnsWidth(int columnNumber,String columnsWidth){
		SummaryPanel summaryPanel = getSummaryPanel();
		LayoutManagerConfig layoutManagerConfig = summaryPanel.getLayoutConfig();
		if (layoutManagerConfig != null){
			Vector<String> columnWidths = layoutManagerConfig.getColumnWidths();
			columnWidths.clear();
			for (int i=0; i<columnNumber; i++){
				columnWidths.add(columnsWidth);
			}
		}
	}

	
	//create a SummaryPanel or return existing one
	public SummaryPanel getSummaryPanel(){
		SummaryPanel summaryPanel = consoleTemplateModel.getSummaryPanel();
		//check for previously defined Summary panel or create one
		if (summaryPanel != null){
			return summaryPanel;
		} else {
			//create a new SummaryPanel
			summaryPanel = new SummaryPanel();
			//create also LayoutManagerConfig (with layout set to column by default)
			LayoutManagerConfig layoutManagerConfig = new LayoutManagerConfig();
			layoutManagerConfig.setLayout("column");
			summaryPanel.setLayoutConfig(layoutManagerConfig);
			consoleTemplateModel.setSummaryPanel(summaryPanel);

		}
		return summaryPanel;
	}
	
	public void selectCComboElement(CCombo combo, String element){
		String[] items = combo.getItems();
		for (int i=0; i<items.length; i++){
			if (items[i].equals(element)){
				combo.select(i);
				break;
			}
		}
	}
	
	public ConsoleEditor getEditor() {
		return editor;
	}

	public void setEditor(ConsoleEditor editor) {
		this.editor = editor;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the consoleTemplateModel
	 */
	public ConsoleTemplateModel getConsoleTemplateModel() {
		return consoleTemplateModel;
	}

	/**
	 * @param consoleTemplateModel the consoleTemplateModel to set
	 */
	public void setConsoleTemplateModel(ConsoleTemplateModel consoleTemplateModel) {
		this.consoleTemplateModel = consoleTemplateModel;
	}
}
