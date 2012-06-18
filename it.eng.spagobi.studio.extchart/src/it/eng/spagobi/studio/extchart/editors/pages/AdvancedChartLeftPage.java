package it.eng.spagobi.studio.extchart.editors.pages;

import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;
import it.eng.spagobi.server.services.api.exception.MissingParValueException;
import it.eng.spagobi.server.services.api.exception.NoServerException;
import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.pages.editorComponent.DraggedObject;
import it.eng.spagobi.studio.extchart.editors.properties.title.SubTitleProperties;
import it.eng.spagobi.studio.extchart.editors.properties.title.TitleProperties;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.LoggerFactory;

public class AdvancedChartLeftPage extends AbstractPage {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AdvancedChartLeftPage.class);


	Composite advancedPropertiesComposite;
	//Composite dataComposite;

	ExtChartEditor editor;
	ExtChart extChart;
	String projectName;
	
	/*Table datasetMetadataTable;

	private HashMap<String, Dataset> datasetInfos;
	private HashMap<String, IDataStoreMetadata> datasetMetadataInfos;
	*/

	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_TYPE = 1;
	
	public AdvancedChartLeftPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
	}

	public void drawPage(){
		logger.debug("IN");
		drawPropertiesSection();
		//drawDatasetSection();

		logger.debug("OUT");
	}

	public void drawPropertiesSection(){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(getParent());
		Section sectionProp = SWTUtils.createSection(this);
		sectionProp.setText("Advanced Chart Properties");
		sectionProp.setDescription("Below you see some chart advanced informations");
		sectionProp.setExpanded(true);

		Composite compositeProp = SWTUtils.createGridCompositeOnSection(sectionProp, 4);
		compositeProp.setLayoutData(SWTUtils.makeGridDataLayout(SWT.NONE, null, null));

		final ExtChartEditor extEditor = editor;
		
		//Width
		Label widthLabel= toolkit.createLabel(compositeProp, "Width:");
		String currentWidthValue;
		if (extChart.getWidth() != null){
			currentWidthValue = extChart.getWidth().toString();
		} else {
			currentWidthValue = ""; 
		}
		final Text widthText = toolkit.createText(compositeProp,currentWidthValue, SWT.BORDER);
		widthText.setLayoutData(new GridData(SWT.NONE));
		widthText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String widthValue = widthText.getText();
				extChart.setWidth(Integer.parseInt(widthValue));
			}
		});

		//Height
		Label heightLabel= toolkit.createLabel(compositeProp, "Height:");
		String currentHeightValue;
		if (extChart.getHeight() != null){
			currentHeightValue = extChart.getHeight().toString();
		} else {
			currentHeightValue = ""; 
		}
		final Text heightText = toolkit.createText(compositeProp, currentHeightValue, SWT.BORDER);
		heightText.setLayoutData(new GridData(SWT.NONE));
		heightText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String heightValue = heightText.getText();
				extChart.setHeight(Integer.parseInt(heightValue));
			}
		});
		
		//Animate
		Label AnimateLabel= toolkit.createLabel(compositeProp, "Animate: ");
		final Button useAnimateCheck = SWTUtils.drawCheck(compositeProp, extChart.getAnimate() != null, "");
		useAnimateCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				extEditor.setIsDirty(true);
				boolean selection = useAnimateCheck.getSelection();
				if(selection == true){
					extChart.setAnimate(true);
				}
				else{
					extChart.setAnimate(false);

				}
			}
		});

		//Shadow
		Label shadowLabel= toolkit.createLabel(compositeProp, "Shadow: ");
		final Button useShadowCheck = SWTUtils.drawCheck(compositeProp, extChart.getShadow() != null, "");
		useShadowCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				extEditor.setIsDirty(true);
				boolean selection = useShadowCheck.getSelection();
				if(selection == true){
					extChart.setShadow(true);
				}
				else{
					extChart.setShadow(false);

				}
			}
		});
		

		
		//RefreshTime
		Label refreshTimeLabel= toolkit.createLabel(compositeProp, "Refresh Time:");
		String currentRefreshTimeValue;
		if (extChart.getRefreshTime() != null){
			currentRefreshTimeValue = extChart.getRefreshTime().toString();
		} else {
			currentRefreshTimeValue = ""; 
		}
		final Text refreshTimeText = toolkit.createText(compositeProp,currentRefreshTimeValue, SWT.BORDER);
		refreshTimeText.setLayoutData(new GridData(SWT.NONE));
		refreshTimeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String refreshTimeValue = refreshTimeText.getText();
				extChart.setRefreshTime(Integer.parseInt(refreshTimeValue));
			}
		});
		
		
		widthLabel= toolkit.createLabel(compositeProp, "");

		sectionProp.setClient(compositeProp);
		logger.debug("OUT");
	}

	/**
	 *  DATASET SECTION
	 */
	/*
	public void drawDatasetSection(){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(getParent());
		Section sectionProp = SWTUtils.createSection(this);
		sectionProp.setText("Chart Dataset");
		sectionProp.setDescription("Choose dataset");
		sectionProp.setExpanded(true);

		Composite compositeProp = SWTUtils.createGridCompositeOnSection(sectionProp, 3);
		compositeProp.setLayoutData(SWTUtils.makeGridDataLayout(GridData.FILL_BOTH, null, null));

		Combo datasetCombo = createDatasetCombo(compositeProp);
		createDatasetMetadataTable(compositeProp, datasetCombo);

		sectionProp.setClient(compositeProp);
		logger.debug("OUT");
	}




	private Table createDatasetMetadataTable(final Composite container, final Combo datasetCombo) {
		logger.debug("IN");

		datasetMetadataTable = new Table(container
				, 
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION
				
		);
		this.datasetMetadataTable = datasetMetadataTable;
		
		datasetMetadataTable.setLinesVisible(true);
		datasetMetadataTable.setHeaderVisible(true);
		GridData dataOrder = new GridData(SWT.FILL, SWT.FILL, true, true);
		dataOrder.heightHint = 150;
		dataOrder.widthHint=250;
		dataOrder.horizontalSpan = 2;
		datasetMetadataTable.setLayoutData(dataOrder);
		
		String[] titles = { "                     Column name                     ",
		"               Type               "};
		
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(datasetMetadataTable, SWT.NONE);
			column.setText(titles[i]);
			// column.setResizable(true);
		}

		logger.debug("Set drag and drop table");
		Transfer[] types = new Transfer[] { TextTransfer.getInstance()};
		DragSource source = new DragSource(datasetMetadataTable, DND.DROP_MOVE | DND.DROP_COPY);
		source.setTransfer(types);
		source.addDragListener(new DragSourceAdapter() {
		      public void dragSetData(DragSourceEvent event) {
		        // Get the selected items in the drag source
		        DragSource ds = (DragSource) event.widget;
		        Table table = (Table) ds.getControl();
		        TableItem[] selection = table.getSelection();

		        DraggedObject draggedObject = new DraggedObject();
		        
		        int[] indices =table.getSelectionIndices();
		        for (int i = 0, n = indices.length; i < n; i++) {
		        	int index = indices[i];
		        	TableItem selItem = table.getItem(index);
		        	draggedObject.getIndexNameSelected().put(index, selItem.getText(COLUMN_NAME));
		        }
		        StringBuffer buff = new StringBuffer(draggedObject.toString());

		        event.data = buff.toString();
		        
//		        StringBuffer buff = new StringBuffer();
//		        for (int i = 0, n = selection.length; i < n; i++) {
//		        	TableItem selecteditem = selection[i];
//		        	buff.append(selection[i].getText());
//		        }
//		         event.data = buff.toString();
		      }
		    });

		
		
		// fill table with default dataset
		if (extChart != null && extChart.getDataset().getLabel() != null) {
			logger.debug("dataset selected label is "+extChart.getDataset().getLabel());
			retrieveDatasetMetadata(container, datasetMetadataTable, extChart.getDataset().getLabel());

		} else {
			logger.debug("No dataset selected, fill table with empty rows");
//			for (int i = 0; i < 5; i++) {
//				TableItem item = new TableItem(datasetMetadataTable, SWT.TRANSPARENT);
//			}
		}
		for (int i = 0; i < titles.length; i++) {
			datasetMetadataTable.getColumn(i).pack();
		}
		// resize the row height using a MeasureItem listener
		datasetMetadataTable.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = 20;
			}
		});
				
		// ad event manager for combo; fill dataset metadata table at dataset selection
		datasetCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				logger.debug("Choose dataset");
				// Once selected the dataset fill the table with its metadata,
				// check first if they have been already recovered!
				datasetMetadataTable.removeAll();
				datasetMetadataTable.setItemCount(0);
				//datasetMetadataTable.pack();
				editor.setIsDirty(true);
				
				SpagoBIServerObjectsFactory sbso= null;

				try{
					sbso =new SpagoBIServerObjectsFactory(projectName);
				}catch (NoActiveServerException e1) {
					logger.error("No active server found",e1);
					return;
				}
 
				String selectedDatasetLabel = datasetCombo.getItem(datasetCombo.getSelectionIndex());
				logger.debug("The dataset with label "+selectedDatasetLabel+" has been selected");
				
				IDataStoreMetadata dataStoreMetadata = null;
				retrieveDatasetMetadata(container, datasetMetadataTable, selectedDatasetLabel);
				extChart.getDataset().setLabel(selectedDatasetLabel);

				// clean all other entities if dataset has changed: series and axes
				getEditor().getMainChartPage().getRightPage().getSeriesBuilder().deleteAllSeries();
				extChart.getAxesList().setAxes(null);

				
				
				datasetMetadataTable.redraw();
				datasetCombo.redraw();

				container.redraw();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		datasetMetadataTable.redraw();
		logger.debug("OUT");
		return datasetMetadataTable;

	}


	public HashMap<String, Dataset> retrieveDatasetList(){
		logger.debug("IN");
		HashMap<String, Dataset> datasetInfosPar = null;
		try{
			SpagoBIServerObjectsFactory proxyServerObjects = null;
			proxyServerObjects = new SpagoBIServerObjectsFactory(projectName);
			//IDataSet[] dataSets = proxyServerObjects.getServerDatasets().getDataSetList();
			Vector<IDataSet> datasetVector = proxyServerObjects.getServerDatasets().getAllDatasets();
			datasetInfosPar = new HashMap<String, Dataset>();
			for (Iterator iterator = datasetVector.iterator(); iterator.hasNext();) {
				Dataset dataset = (Dataset) iterator.next();
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



	private Combo createDatasetCombo(final Composite container){
		logger.debug("IN");
		Label datasetLabel = new Label(container, SWT.SIMPLE);
		datasetLabel.setText("Data Set");
		datasetLabel.setAlignment(SWT.RIGHT);

		// create the combo
		Combo datasetCombo = new Combo(container, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		datasetInfos = retrieveDatasetList();
		datasetMetadataInfos = new HashMap<String, IDataStoreMetadata>();
		
		if(datasetInfos == null) {
			logger.warn("dataset list returned is empty");
			return datasetCombo;
		}

		logger.debug("retrieved "+datasetInfos.keySet().size()+" datasets");
		String[] datasets = new String[datasetInfos.keySet().size()];

		Iterator<String> iterator = datasetInfos.keySet().iterator();

		// fill the combo
		int index = 0;
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			datasets[index] = name;
			index++;
		}
		Arrays.sort(datasets);
		datasetCombo.setItems(datasets);

		// get the selection one! Index are changed so check out for name
		String dsLabel = extChart.getDataset().getLabel();
		if(dsLabel != null){
			logger.debug("selected dataset is : "+dsLabel);
			boolean found = false;
			for (int j = 0; j<datasetCombo.getItems().length && !found;j++) {
				String name = datasetCombo.getItems()[j];
				if (dsLabel.equals(name)) {
					datasetCombo.select(j);	
					found = true;
				}
			}
		}
		else{
			logger.debug("No dataset ios selected");
		}

		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = SWT.END;
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 120;
		gd.verticalAlignment = SWT.TOP;		
		datasetCombo.setLayoutData(gd);
		
		logger.debug("OUT");
		return datasetCombo;
	}

	
	private void retrieveDatasetMetadata(Composite container, Table dsMetadatatable, String dsLabel) {
		logger.debug("IN");
		try {
			logger.debug("retrieve metadata for dataset with label "+dsLabel);
			SpagoBIServerObjectsFactory sbso= null;

			try{
				sbso =new SpagoBIServerObjectsFactory(projectName);
			}catch (NoActiveServerException e1) {
				logger.error("No active server found",e1);
				return;
			}
			
			// get the dataset
			Dataset dataset = datasetInfos.get(dsLabel);
			
			IDataStoreMetadata dataStoreMetadata = sbso.getServerDatasets().getDataStoreMetadata(dataset.getId());
			
			if (dataStoreMetadata != null) {
				datasetMetadataInfos.put(dsLabel, dataStoreMetadata);
				fillDatasetMetadataTable(dataStoreMetadata, dsMetadatatable);
			} else {
				logger.warn("Dataset returned no metadata");
				MessageDialog.openWarning(container.getShell(), "Warning", "Dataset with label = " + dsLabel + " returned no metadata: test it on server to have metadata avalaible");
			}
		} catch (MissingParValueException e2) {
			logger.error("Could not execute dataset with label = "+ dsLabel + " due to parameter lack: execute dataset test in server to retrieve metadata", e2);
			MessageDialog.openError(container.getShell(), "Error",
					"Could not execute dataset with label = "+dsLabel+ " due to parameter lack: execute dataset test in server to retrieve metadata");
		} catch (NoServerException e1) {
			logger.error("Error No comunciation with server retrieving dataset with label = "+ dsLabel + " metadata", e1);
			MessageDialog.openError(container.getShell(), "Error", "No comunciation with server retrieving dataset with label = "+ dsLabel + " metadata");
		}
		logger.debug("OUT");

	}
	
	
	
	
	
	private void fillDatasetMetadataTable(IDataStoreMetadata dataStoreMetadata
			, Table datasetMetadataTable) {
		// if dataset changed than new Metadata
		logger.debug("IN");
		logger.debug("In metadata there are "+dataStoreMetadata.getFieldsMetadata().length+" columns");
		
		for (int i = 0; i < dataStoreMetadata.getFieldsMetadata().length; i++) {
			IDataStoreMetadataField dsmf = dataStoreMetadata.getFieldsMetadata()[i];
			TableItem item = new TableItem(datasetMetadataTable, SWT.NONE);
			item.setText(COLUMN_NAME, dsmf.getName());
			item.setText(COLUMN_TYPE, dsmf.getClassName());
		}
		//datasetMetadataTable.pack();
		datasetMetadataTable.redraw();
		logger.debug("OUT");

	}
	
	*/
	
	

	public ExtChartEditor getEditor() {
		return editor;
	}

	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}

	public ExtChart getExtChart() {
		return extChart;
	}

	public void setExtChart(ExtChart extChart) {
		this.extChart = extChart;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/*
	public Table getDatasetMetadataTable() {
		return datasetMetadataTable;
	}

	public void setDatasetMetadataTable(Table datasetMetadataTable) {
		this.datasetMetadataTable = datasetMetadataTable;
	}


	public String[] getDatasetMetadataTableContent(){
	logger.debug("IN");
	String[] toReturn = null;
	TableItem[] items = datasetMetadataTable.getItems();
	if(items != null){
		toReturn = new String[items.length];
		logger.debug("There are "+items.length+ " items");
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			String value = item.getText(0);
			toReturn[i] = value;
		}
	}
	logger.debug("OUT");
	return toReturn;
	}
	*/

}
