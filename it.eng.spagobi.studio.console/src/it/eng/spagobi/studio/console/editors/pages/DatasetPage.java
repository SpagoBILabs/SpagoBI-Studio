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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.studio.console.editors.ConsoleEditor;
import it.eng.spagobi.studio.console.model.bo.ConsoleTemplateModel;
import it.eng.spagobi.studio.console.model.bo.DatasetElement;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.slf4j.LoggerFactory;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class DatasetPage extends AbstractPage {
	private Text textId;
	private Text txtRefreshTime;
	private Text txtRowsLimit;
	private Table table;
	private ConsoleEditor editor;
	private String projectName;
	private ConsoleTemplateModel consoleTemplateModel;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(DatasetPage.class);

	private Combo comboDatasets;
	private Combo comboMemoryPagination;
	
	private Vector<DatasetElement> datasets;
	
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_LABEL = 1;
	public static final int COLUMN_REFRESH_TIME = 2;
	public static final int COLUMN_ROWS_LIMIT = 3;
	public static final int COLUMN_MEMORY_PAGINATION = 4;




	/**
	 * @param parent
	 * @param style
	 */
	public DatasetPage(Composite parent, int style) {
		super(parent, style);
	}
	
	public void drawPage(){
		datasets = consoleTemplateModel.getDataset();

		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group groupSelection = new Group(mainComposite, SWT.NONE);
		groupSelection.setText("Dataset Selection");
		groupSelection.setLayout(new GridLayout(4, false));
		groupSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblDatasetToSelect = new Label(groupSelection, SWT.NONE);
		lblDatasetToSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDatasetToSelect.setText("Dataset to select: ");
		
		//Dataset combo
		comboDatasets = new Combo(groupSelection, SWT.READ_ONLY);
		comboDatasets.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(groupSelection, SWT.NONE);
		new Label(groupSelection, SWT.NONE);
		
		populateDatasetCombo();
		
		Label lblIdToAssign = new Label(groupSelection, SWT.NONE);
		lblIdToAssign.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblIdToAssign.setText("Id to assign:");
		
		textId = new Text(groupSelection, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblRefreshTime = new Label(groupSelection, SWT.NONE);
		lblRefreshTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRefreshTime.setText("Refresh time: ");
		
		txtRefreshTime = new Text(groupSelection, SWT.BORDER);
		txtRefreshTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblMemoryPagination = new Label(groupSelection, SWT.NONE);
		lblMemoryPagination.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMemoryPagination.setText("Memory Pagination:");
		
		comboMemoryPagination = new Combo(groupSelection, SWT.READ_ONLY);
		comboMemoryPagination.setItems(new String[] {"true", "false"});
		comboMemoryPagination.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblRowsLimit = new Label(groupSelection, SWT.NONE);
		lblRowsLimit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRowsLimit.setText("Rows Limit:");
		
		txtRowsLimit = new Text(groupSelection, SWT.BORDER);
		txtRowsLimit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group groupDatasetTable = new Group(mainComposite, SWT.NONE);
		groupDatasetTable.setText("Datasets added");
		groupDatasetTable.setLayout(new GridLayout(1, false));
		groupDatasetTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		
		Composite compositeButtons = new Composite(groupDatasetTable, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(2, false));
		compositeButtons.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
		
		Button btnAdd = new Button(compositeButtons, SWT.NONE);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if ( (comboDatasets.getSelectionIndex()!= -1 ) && (!textId.getText().isEmpty()) ){
					DatasetElement datasetElement = new DatasetElement();
					datasetElement.setId(textId.getText());
					datasetElement.setLabel(comboDatasets.getItem(comboDatasets.getSelectionIndex()));
					if (!txtRefreshTime.getText().isEmpty()){
						datasetElement.setRefreshTime(Integer.parseInt(txtRefreshTime.getText()));
					}
					if (!txtRowsLimit.getText().isEmpty()){
						datasetElement.setRowsLimit(Integer.parseInt(txtRowsLimit.getText()));
					}
					if (comboMemoryPagination.getSelectionIndex() != -1){
						boolean valueMemoryPaginationCombo = Boolean.parseBoolean(comboMemoryPagination.getItem(comboMemoryPagination.getSelectionIndex()));
						datasetElement.setMemoryPagination(valueMemoryPaginationCombo);
					}
					
					//add to the set of datasets
					datasets.add(datasetElement);
					//add to Table GUI
					addTableItem(datasetElement);
					
					//clear UI DatasetSelection
					clearDatasetSelectionGUI();
					
					editor.setIsDirty(true);
				}
			}
		});
		
		Button btnRemove = new Button(compositeButtons, SWT.NONE);
		btnRemove.setText("Remove");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (table.getSelectionIndex() != -1){
					int index = table.getSelectionIndex();
					//remove Table Item (GUI)
					table.remove(index);					
					//remove from set of datasets
					datasets.remove(index);
					
					table.redraw();
					
					editor.setIsDirty(true);
				}
			}
		});
		
		table = new Table(groupDatasetTable, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setWidth(229);
		tblclmnId.setText("Id");
		
		TableColumn tblclmnLabel = new TableColumn(table, SWT.NONE);
		tblclmnLabel.setWidth(178);
		tblclmnLabel.setText("Label");	
		
		TableColumn tblclmnRefreshTime = new TableColumn(table, SWT.NONE);
		tblclmnRefreshTime.setWidth(178);
		tblclmnRefreshTime.setText("Refresh Time");	
		
		TableColumn tblclmnRowsLimit = new TableColumn(table, SWT.NONE);
		tblclmnRowsLimit.setWidth(178);
		tblclmnRowsLimit.setText("Rows Limit");	
		
		TableColumn tblclmnMemoryPagination = new TableColumn(table, SWT.NONE);
		tblclmnMemoryPagination.setWidth(178);
		tblclmnMemoryPagination.setText("Memory Pagination");	
		
		//Check for previously defined Dataset to show in the Table
		populateDatasetTable();
	}
	
	public void populateDatasetTable(){
		if (!datasets.isEmpty())		{
			for (DatasetElement datasetElement:datasets){
				addTableItem(datasetElement);
			}
		}
	}
	
	public void addTableItem(DatasetElement datasetElement){
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(COLUMN_ID, datasetElement.getId());
		item.setText(COLUMN_LABEL, datasetElement.getLabel());
		item.setText(COLUMN_REFRESH_TIME, String.valueOf(datasetElement.getRefreshTime()));
		item.setText(COLUMN_ROWS_LIMIT, String.valueOf(datasetElement.getRowsLimit()));
		item.setText(COLUMN_MEMORY_PAGINATION, String.valueOf(datasetElement.isMemoryPagination()));

		table.redraw();

	}
	
	public void clearDatasetSelectionGUI(){
		comboDatasets.clearSelection();
		comboDatasets.deselectAll();
		textId.clearSelection();
		textId.setText("");
		txtRefreshTime.clearSelection();
		txtRefreshTime.setText("");
		txtRowsLimit.clearSelection();
		txtRowsLimit.setText("");
		comboMemoryPagination.clearSelection();
		comboMemoryPagination.deselectAll();
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
	
	public void populateDatasetCombo(){
		HashMap<String, Dataset> datasetList = retrieveDatasetList();
		
		if(datasetList == null) {
			logger.warn("dataset list returned is empty");
		} else {
			logger.debug("retrieved "+datasetList.keySet().size()+" datasets");
			String[] datasets = new String[datasetList.keySet().size()];

			Iterator<String> iterator = datasetList.keySet().iterator();

			// fill the combo
			int index = 0;
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				datasets[index] = name;
				index++;
			}
			Arrays.sort(datasets);
			comboDatasets.setItems(datasets);
		}


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
}
