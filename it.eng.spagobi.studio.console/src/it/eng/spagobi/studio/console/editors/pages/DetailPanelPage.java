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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;
import it.eng.spagobi.server.services.api.exception.MissingParValueException;
import it.eng.spagobi.server.services.api.exception.NoServerException;
import it.eng.spagobi.studio.console.editors.ConsoleEditor;
import it.eng.spagobi.studio.console.editors.internal.DetailPanelPageTableRow;
import it.eng.spagobi.studio.console.model.bo.ColumnConfig;
import it.eng.spagobi.studio.console.model.bo.ConsoleTemplateModel;
import it.eng.spagobi.studio.console.model.bo.DatasetElement;
import it.eng.spagobi.studio.console.model.bo.DetailPanel;
import it.eng.spagobi.studio.console.model.bo.Page;
import it.eng.spagobi.studio.console.model.bo.TablePage;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.PlatformUI;
import org.slf4j.LoggerFactory;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class DetailPanelPage extends AbstractPage {
	private ConsoleEditor editor;
	private String projectName;
	private ConsoleTemplateModel consoleTemplateModel;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Table table;
	private Table tableColumns;
	private Combo comboDataset;
	private Combo comboDatasetLabel;
	private Combo comboColumnId;
	private List<DetailPanelPageTableRow> detailPanelPageTableRows;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(DetailPanelPage.class);
	private TableItem selectedRow;
	
	private Page firstPage;
	
	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_HEADER = 1;
	public static final int COLUMN_HEADER_TYPE = 2;
	public static final int COLUMN_TYPE = 3;
	public static final int COLUMN_WIDTH = 4;
	private Text textTitle;
	private boolean previousTitleFound;




	/**
	 * @param parent
	 * @param style
	 */
	public DetailPanelPage(Composite parent, int style) {
		super(parent, style);

	}
	public void drawPage(){		
		detailPanelPageTableRows = new ArrayList<DetailPanelPageTableRow>();
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group grpPageDetail = new Group(mainComposite, SWT.NONE);
		grpPageDetail.setText("Page Detail");
		grpPageDetail.setLayout(new GridLayout(1, false));
		grpPageDetail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite compositeTitle = new Composite(grpPageDetail, SWT.NONE);
		compositeTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeTitle.setLayout(new GridLayout(2, false));
		
		Label lblTitle = new Label(compositeTitle, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle.setText("Title");
		
		textTitle = new Text(compositeTitle, SWT.BORDER);
		textTitle.setText("Page Title");

		textTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if (firstPage != null){
					if (previousTitleFound){
						previousTitleFound = false;
					} else {
						editor.setIsDirty(true);
					}
					firstPage.setTitle(textTitle.getText());				
				}
			}
		});
		GridData gd_textTitle = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_textTitle.widthHint = 119;
		textTitle.setLayoutData(gd_textTitle);
		
		Group grpTable = new Group(grpPageDetail, SWT.NONE);
		grpTable.setText("Table");
		grpTable.setLayout(new GridLayout(1, false));
		grpTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite compositeTable = new Composite(grpTable, SWT.NONE);
		compositeTable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeTable.setBounds(0, 0, 408, 61);
		compositeTable.setLayout(new GridLayout(4, false));
		
		Label lblDatasetSelection = new Label(compositeTable, SWT.NONE);
		lblDatasetSelection.setSize(93, 15);
		lblDatasetSelection.setText("Dataset Selection:");
		
		comboDataset = new Combo(compositeTable, SWT.READ_ONLY);
		comboDataset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);

				int index = comboDataset.getSelectionIndex();
				String dsId = comboDataset.getItem(index);
				String dsLabel = null;
				
				firstPage.getTable().setDataset(dsId);
				Vector<DatasetElement> datasets = consoleTemplateModel.getDataset();
				if (!datasets.isEmpty() ){
					for (DatasetElement datasetElement:datasets){
						if (datasetElement.getId().equals(dsId)){
							dsLabel = datasetElement.getLabel();
						}
					}
				}
				
				if (dsLabel != null){
					populateColumnsTable(dsLabel);
					//Populate columnId combo
					populateColumnIdCombo(dsLabel);					
				}


			}
		});
		comboDataset.setSize(111, 23);
		
		Label lblDatasetlabels = new Label(compositeTable, SWT.NONE);
		lblDatasetlabels.setSize(78, 15);
		lblDatasetlabels.setText("Dataset Labels:");
		
		comboDatasetLabel = new Combo(compositeTable, SWT.READ_ONLY);
		comboDatasetLabel.setSize(91, 23);
		comboDatasetLabel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);

				int index = comboDatasetLabel.getSelectionIndex();
				String dsId = comboDatasetLabel.getItem(index);

				if (dsId != null){
					firstPage.getTable().setDatasetLabels(dsId);
				}
				
			}
		});
		
		Label lblColumnId = new Label(compositeTable, SWT.NONE);
		lblColumnId.setSize(60, 15);
		lblColumnId.setText("Column ID:");
		
		comboColumnId = new Combo(compositeTable, SWT.READ_ONLY);
		comboColumnId.setSize(111, 23);
		comboColumnId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);

				int index = comboColumnId.getSelectionIndex();
				String columnId = comboColumnId.getItem(index);
				firstPage.getTable().setColumnId(columnId);
				
			}
		});
		
		Group grpColumnConfig = new Group(grpTable, SWT.NONE);
		grpColumnConfig.setText("Column Config");
		grpColumnConfig.setLayout(new GridLayout(1, false));
		grpColumnConfig.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpColumnConfig.setBounds(0, 0, 70, 82);
		
		tableColumns = new Table(grpColumnConfig, SWT.BORDER | SWT.FULL_SELECTION);
		tableColumns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableColumns.setHeaderVisible(true);
		tableColumns.setLinesVisible(true);
		
		TableColumn tblclmnColumnName = new TableColumn(tableColumns, SWT.NONE);
		tblclmnColumnName.setWidth(100);
		tblclmnColumnName.setText("Column Name");
		
		TableColumn tblclmnHeader = new TableColumn(tableColumns, SWT.NONE);
		tblclmnHeader.setWidth(100);
		tblclmnHeader.setText("Header");
		
		TableColumn tblclmnHeaderType = new TableColumn(tableColumns, SWT.NONE);
		tblclmnHeaderType.setWidth(86);
		tblclmnHeaderType.setText("Header Type");
		
		TableColumn tblclmnType = new TableColumn(tableColumns, SWT.NONE);
		tblclmnType.setWidth(66);
		tblclmnType.setText("Type");
		
		TableColumn tblclmnWidth = new TableColumn(tableColumns, SWT.NONE);
		tblclmnWidth.setWidth(51);
		tblclmnWidth.setText("Width");


		/*
		Group grpNavigationBar = new Group(grpPageDetail, SWT.NONE);
		grpNavigationBar.setText("Navigation Bar");
		grpNavigationBar.setLayout(new GridLayout(1, false));
		grpNavigationBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Composite composite = new Composite(grpNavigationBar, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		composite.setBounds(0, 0, 64, 64);
		
		Label lblText = new Label(composite, SWT.NONE);
		lblText.setSize(25, 15);
		lblText.setText("Text:");
		
		text = new Text(composite, SWT.BORDER);
		text.setSize(76, 21);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setSize(41, 15);
		lblNewLabel.setText("Tooltip:");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setSize(245, 21);
		
		Label lblLabel = new Label(composite, SWT.NONE);
		lblLabel.setSize(31, 15);
		lblLabel.setText("Label:");
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setSize(76, 21);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(grpNavigationBar, SWT.NONE);
		
		Button btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAdd.setBounds(0, 0, 75, 25);
		btnAdd.setText("Add");
		
		table = new Table(grpNavigationBar, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnText = new TableColumn(table, SWT.NONE);
		tblclmnText.setWidth(100);
		tblclmnText.setText("Text");
		
		TableColumn tblclmnTooltip = new TableColumn(table, SWT.NONE);
		tblclmnTooltip.setWidth(100);
		tblclmnTooltip.setText("Tooltip");
		
		TableColumn tblclmnLabel = new TableColumn(table, SWT.NONE);
		tblclmnLabel.setWidth(100);
		tblclmnLabel.setText("Label");
		*/		
		
		//check for previously defined pages or create one
		
		if (consoleTemplateModel.getDetailPanel() == null){
			DetailPanel detailPanel = new DetailPanel();
			consoleTemplateModel.setDetailPanel(detailPanel);
		}
		
		Vector<Page> pages = consoleTemplateModel.getDetailPanel().getPages();
		if (!pages.isEmpty()){
			firstPage = pages.get(0);
			previousTitleFound = true;
			textTitle.setText(firstPage.getTitle());
		} else {
			Page newPage = new Page();
			//newPage.setTitle("Page title");
			if (textTitle.getText() != null){
				newPage.setTitle(textTitle.getText());				
			}
			TablePage newTable = new TablePage();
			newPage.setTable(newTable);
			consoleTemplateModel.getDetailPanel().getPages().add(newPage);
			firstPage = newPage;
	
		}

		
		populateDatasetCombo();
		populateDatasetLabelCombo();
		
		//populate ColumnId combo if dataset already selected
		if (firstPage.getTable().getDataset() != null){
			int index = comboDataset.getSelectionIndex();
			String dsId = comboDataset.getItem(index);
			String dsLabel = null;

			Vector<DatasetElement> datasets = consoleTemplateModel.getDataset();
			if (!datasets.isEmpty() ){
				for (DatasetElement datasetElement:datasets){
					if (datasetElement.getId().equals(dsId)){
						dsLabel = datasetElement.getLabel();
					}
				}
				String columnId = null;
				if (firstPage != null){
					if(firstPage.getTable() != null){
						if(firstPage.getTable().getColumnId() != null){
							columnId = firstPage.getTable().getColumnId();
						}
					}
				}
				populateColumnIdCombo(dsLabel);

				if (columnId != null){
					String[] comboItems = comboColumnId.getItems();
					for(int i=0; i<comboItems.length;i++){
						if (comboItems[i].equals(columnId)){
							comboColumnId.select(i);
							break;
						}
					}
				}

			}
		}
		
		//populate tableColumns if found already existing objects
		Map<String,ColumnConfig> columnConfigSet = firstPage.getTable().getColumnConfig();
		if (!columnConfigSet.isEmpty()){
			for(Map.Entry<String,ColumnConfig> entry:columnConfigSet.entrySet()  ){
				createTableItem(entry.getKey(),entry.getValue());				
			}

		}
		
	}
	
	public void populateColumnIdCombo(String datasetLabel){
		comboColumnId.removeAll();
		firstPage.getTable().setColumnId(null);

		IDataStoreMetadata dataStoreMetadata = retrieveDatasetMetadata(datasetLabel);
		if (dataStoreMetadata != null){
			for (int i = 0; i < dataStoreMetadata.getFieldsMetadata().length; i++) {
				IDataStoreMetadataField dsmf = dataStoreMetadata.getFieldsMetadata()[i];
				//create Table Columns in object Model				
				comboColumnId.add(dsmf.getName());
			}
		}
	}
	
	public void populateDatasetCombo(){
		comboDataset.removeAll();
		Vector<DatasetElement> datasets = consoleTemplateModel.getDataset();
		if (!datasets.isEmpty() ){
			for (DatasetElement datasetElement:datasets){
				comboDataset.add(datasetElement.getId());
			}
		}
		
		//check previously defined Dataset in detailPanel Page
		if (firstPage != null){
			if(firstPage.getTable() != null){
				if(firstPage.getTable().getDataset() != null){
					String datasetName = firstPage.getTable().getDataset();
					String[] comboItems = comboDataset.getItems();
					for(int i=0; i<comboItems.length;i++){
						if (comboItems[i].equals(datasetName)){
							comboDataset.select(i);
							break;
						}
					}
				}
			}
		}
		
	}
	
	public void populateDatasetLabelCombo(){
		comboDatasetLabel.removeAll();
		//firstPage.getTable().setDatasetLabels(null);

		Vector<DatasetElement> datasets = consoleTemplateModel.getDataset();
		if (!datasets.isEmpty() ){
			for (DatasetElement datasetElement:datasets){
				comboDatasetLabel.add(datasetElement.getId());
			}
		}
		
		//check previously defined Dataset in detailPanel Page
		if (firstPage != null){
			if(firstPage.getTable() != null){
				if(firstPage.getTable().getDatasetLabels() != null){
					String datasetLabelName = firstPage.getTable().getDatasetLabels();
					String[] comboItems = comboDatasetLabel.getItems();
					for(int i=0; i<comboItems.length;i++){
						if (comboItems[i].equals(datasetLabelName)){
							comboDatasetLabel.select(i);
							break;
						}
					}
				}
			}
		}
	}
	
	public void populateColumnsTable(String dsLabel){
		//First, clean all UI elements in the table

		for (DetailPanelPageTableRow detailPanelPageTableRow:detailPanelPageTableRows){
			detailPanelPageTableRow.disposeRowElements();	
		}
		//remove all elements from the internal list
		detailPanelPageTableRows.clear();
		
		tableColumns.clearAll();

		tableColumns.redraw();

		Map<String,ColumnConfig> columnConfigSet = firstPage.getTable().getColumnConfig();
		
		columnConfigSet.clear();
		
		IDataStoreMetadata dataStoreMetadata = retrieveDatasetMetadata(dsLabel);
		if (dataStoreMetadata != null){
			for (int i = 0; i < dataStoreMetadata.getFieldsMetadata().length; i++) {
				IDataStoreMetadataField dsmf = dataStoreMetadata.getFieldsMetadata()[i];
				//create Table Columns in object Model
				ColumnConfig column = new ColumnConfig();
				column.setHeader(dsmf.getName());
				columnConfigSet.put(dsmf.getName(), column);
				//add Table Item to Columns Table (GUI)
				createTableItem(dsmf.getName(),column);				
			}
		}
	}
	
	public void createTableItem(String columnName, ColumnConfig column ){
		final TableItem item = new TableItem(tableColumns, SWT.NONE);
		item.setData(column);
		//set Column name
		item.setText(COLUMN_NAME, columnName);
		
		//create Cell Editor Text Header
		TableEditor editor_header = new TableEditor(tableColumns);
		final Text textHeader = new Text(tableColumns, SWT.NONE);
		textHeader.setText(column.getHeader());

		editor_header.grabHorizontal = true;
		
		textHeader.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				if (item != null){
					ColumnConfig columnConfig = (ColumnConfig)item.getData();
					columnConfig.setHeader(textHeader.getText());
				}
			}
		});
		
		editor_header.setEditor(textHeader,item, COLUMN_HEADER);

		//create Cell Editor Combo Header Type
		TableEditor editor_headerType = new TableEditor(tableColumns);
		final CCombo comboHeaderType = new CCombo(tableColumns, SWT.READ_ONLY);
		comboHeaderType.add("static");
		comboHeaderType.add("dataset");
		comboHeaderType.add("i18N");
		comboHeaderType.add("datasetI18N");
		editor_headerType.grabHorizontal = true;
		//check previously defined objects
		if(column.getHeaderType() != null){
			selectCComboElement(comboHeaderType,column.getHeaderType());
		} else {
			//default value is static
			comboHeaderType.select(0);
			column.setHeaderType("static");
		}
		
		comboHeaderType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				ColumnConfig columnConfig = (ColumnConfig)item.getData();
				columnConfig.setHeaderType(comboHeaderType.getText());
			}
		});
		editor_headerType.setEditor(comboHeaderType,item, COLUMN_HEADER_TYPE);
		
		//create Cell Editor Combo Type
		TableEditor editor_type = new TableEditor(tableColumns);
		final CCombo comboType = new CCombo(tableColumns, SWT.READ_ONLY);
		comboType.add("string");
		comboType.add("int");
		comboType.add("date");
		comboType.add("timestamp");
		editor_type.grabHorizontal = true;
		//check previously defined objects
		if(column.getType() != null){
			selectCComboElement(comboType,column.getType());
		}  else {
			//default value is string
			comboType.select(0);
			column.setType("string");
		}
		comboType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setIsDirty(true);
				ColumnConfig columnConfig = (ColumnConfig)item.getData();
				columnConfig.setType(comboType.getText());
			}
		});
		editor_type.setEditor(comboType,item, COLUMN_TYPE);		
		//create Cell Editor Text Width
		TableEditor editor_width = new TableEditor(tableColumns);
		final Text textWidth = new Text(tableColumns, SWT.NONE);
		editor_width.grabHorizontal = true;
		if(column.getWidth() != 0){
			textWidth.setText(String.valueOf(column.getWidth()));
		}
			
		textWidth.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				editor.setIsDirty(true);
				if (item != null){
					ColumnConfig columnConfig = (ColumnConfig)item.getData();
					columnConfig.setWidth(Integer.parseInt(textWidth.getText()));
				}
			}
		});
		editor_width.setEditor(textWidth,item, COLUMN_WIDTH);			
		
		//create internal object with UI elements of this item
		DetailPanelPageTableRow detailPanelPageTableRow = new DetailPanelPageTableRow(item,textHeader,comboHeaderType,comboType,textWidth);
		detailPanelPageTableRows.add(detailPanelPageTableRow);
		tableColumns.redraw();
		
	}
	
	private IDataStoreMetadata retrieveDatasetMetadata(String dsLabel) {
		logger.debug("IN");
		try {
			logger.debug("retrieve metadata for dataset with label "+dsLabel);
			SpagoBIServerObjectsFactory sbso= null;

			try{
				sbso =new SpagoBIServerObjectsFactory(projectName);
			}catch (NoActiveServerException e1) {
				logger.error("No active server found",e1);
				return null;
			}
			
			// get the dataset
			HashMap<String, it.eng.spagobi.studio.utils.bo.Dataset> datasetInfos;
			datasetInfos = retrieveDatasetList();

			it.eng.spagobi.studio.utils.bo.Dataset dataset = datasetInfos.get(dsLabel);
			
			IDataStoreMetadata dataStoreMetadata = sbso.getServerDatasets().getDataStoreMetadata(dataset.getId());
			HashMap<String, IDataStoreMetadata> datasetMetadataInfos;

			
			if (dataStoreMetadata != null) {
				return dataStoreMetadata;
			} else {
				logger.warn("Dataset returned no metadata");
				MessageDialog.openWarning(this.getShell(), "Warning", "Dataset with label = " + dsLabel + " returned no metadata: test it on server to have metadata avalaible");
			}
		} catch (MissingParValueException e2) {
			logger.error("Could not execute dataset with label = "+ dsLabel + " due to parameter lack: execute dataset test in server to retrieve metadata", e2);
			MessageDialog.openError(this.getShell(), "Error",
					"Could not execute dataset with label = "+dsLabel+ " due to parameter lack: execute dataset test in server to retrieve metadata");
		} catch (NoServerException e1) {
			logger.error("Error No comunciation with server retrieving dataset with label = "+ dsLabel + " metadata", e1);
			MessageDialog.openError(this.getShell(), "Error", "No comunciation with server retrieving dataset with label = "+ dsLabel + " metadata");
		}
		logger.debug("OUT");
		return null;


	}	
	
	public HashMap<String, it.eng.spagobi.studio.utils.bo.Dataset> retrieveDatasetList(){
		logger.debug("IN");
		HashMap<String, it.eng.spagobi.studio.utils.bo.Dataset> datasetInfosPar = null;
		try{
			SpagoBIServerObjectsFactory proxyServerObjects = null;
			proxyServerObjects = new SpagoBIServerObjectsFactory(projectName);
			//IDataSet[] dataSets = proxyServerObjects.getServerDatasets().getDataSetList();
			Vector<IDataSet> datasetVector = proxyServerObjects.getServerDatasets().getAllDatasets();
			datasetInfosPar = new HashMap<String, it.eng.spagobi.studio.utils.bo.Dataset>();
			for (Iterator iterator = datasetVector.iterator(); iterator.hasNext();) {
				it.eng.spagobi.studio.utils.bo.Dataset dataset = (it.eng.spagobi.studio.utils.bo.Dataset) iterator.next();
				datasetInfosPar.put(dataset.getLabel(), dataset);
			}
			logger.debug("Retrieved "+datasetInfosPar.size()+" datasets");
		}
		catch (NoActiveServerException e1) {
			logger.error("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return null;
		}
		catch (Exception e1) {
			logger.error("Not working server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "Not working server found");	
			return null;
		}
		logger.debug("OUT");
		return datasetInfosPar;
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
