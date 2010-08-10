package it.eng.spagobi.studio.geo.editors;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.exceptions.MissingParameterValue;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.studio.core.bo.DataSource;
import it.eng.spagobi.studio.core.bo.DataStoreMetadata;
import it.eng.spagobi.studio.core.bo.DataStoreMetadataField;
import it.eng.spagobi.studio.core.bo.Dataset;
import it.eng.spagobi.studio.core.bo.GeoFeature;
import it.eng.spagobi.studio.core.bo.GeoMap;
import it.eng.spagobi.studio.core.bo.SpagoBIServerObjects;
import it.eng.spagobi.studio.core.exceptions.NoServerException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.properties.PropertyPage;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.geo.Activator;
import it.eng.spagobi.studio.geo.editors.model.bo.ColumnBO;
import it.eng.spagobi.studio.geo.editors.model.bo.DatasetBO;
import it.eng.spagobi.studio.geo.editors.model.bo.DatasourceBO;
import it.eng.spagobi.studio.geo.editors.model.bo.HierarchyBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LayerBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LayersBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LevelBO;
import it.eng.spagobi.studio.geo.editors.model.bo.MetadataBO;
import it.eng.spagobi.studio.geo.editors.model.bo.ModelBO;
import it.eng.spagobi.studio.geo.editors.model.geo.Column;
import it.eng.spagobi.studio.geo.editors.model.geo.Datasource;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchies;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchy;
import it.eng.spagobi.studio.geo.editors.model.geo.Layer;
import it.eng.spagobi.studio.geo.editors.model.geo.Layers;
import it.eng.spagobi.studio.geo.editors.model.geo.Level;
import it.eng.spagobi.studio.geo.editors.model.geo.Metadata;
import it.eng.spagobi.studio.geo.util.DeepCopy;
import it.eng.spagobi.studio.geo.util.DesignerUtils;
import it.eng.spagobi.studio.geo.util.XmlTemplateGenerator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class GEOEditor extends EditorPart {

	protected boolean isDirty = false;
	final ImageDescriptor measureIcon = AbstractUIPlugin
			.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/measure.gif");
	
	final ImageDescriptor idIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/key.gif");

	private Vector<String> dataSets;
	private Vector<String> maps;
	private HashMap<String, Dataset> datasetInfos;
	private HashMap<String, GeoMap> mapInfos;

	private HashMap<String, DataStoreMetadata> tempDsMetadataInfos;
	private HashMap<String, GeoFeature[]> tempMapMetadataInfos;

	private String selectedDataset;
	private String selectedMap;


	private Table datasetTable;
	private Combo datasetCombo;
	private Table mapTable;

	private Vector<TableEditor> datasetTableEditors = new Vector<TableEditor>();
	private Vector<TableEditor> mapTableEditors = new Vector<TableEditor>();
	private MeasuresDesigner measuresDesigner;

	private static final int DATASET_NAME = 0;
	private static final int DATASET_CLASS = 1;
	private static final int DATASET_SELECT = 2;
	private static final int DATASET_AGGREGATION = 3;

	private static final int FEATURE_NAME = 0;
	private static final int FEATURE_DESCR = 1;
	private static final int FEATURE_DEFAULT_LEVEL = 2;
	private static final int FEATURE_DEFAULT_COLORS = 3;

	private GEODocument geoDocument;

	public void init(IEditorSite site, IEditorInput input) {
		try {
			this.setPartName(input.getName());

			QualifiedName ciao = PropertyPage.MADE_WITH_STUDIO;
			FileEditorInput fei = (FileEditorInput) input;
			IFile file = fei.getFile();
			ModelBO bo = new ModelBO();
			try {
				geoDocument = bo.createModel(file);
				bo.saveModel(geoDocument);

			} catch (CoreException e) {
				e.printStackTrace();
				SpagoBILogger.errorLog(GEOEditor.class.toString()
						+ ": Error in reading template", e);
				throw (new PartInitException("Error in reading template"));
			}
			setInput(input);
			setSite(site);

			mapInfos = new HashMap<String, GeoMap>();
			datasetInfos = new HashMap<String, Dataset>();
			tempDsMetadataInfos = new HashMap<String, DataStoreMetadata>();
			tempMapMetadataInfos = new HashMap<String, GeoFeature[]>();
		} catch (Exception e) {
			SpagoBILogger.warningLog("Error occurred:" + e.getMessage());
		}
	}

	public void initializeEditor(GEODocument geoDocument) {
		SpagoBILogger.infoLog("START: " + GEOEditor.class.toString()
				+ " initialize Editor");
		// clean the properties View
		IWorkbenchWindow a = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		// Document properties
		IWorkbenchPage aa = a.getActivePage();

		SDKDataSet[] sdkDataSets = null;
		try {
			SDKProxyFactory proxyFactory = new SDKProxyFactory();
			DataSetsSDKServiceProxy dataSetsServiceProxy = proxyFactory
					.getDataSetsSDKServiceProxy();
			sdkDataSets = dataSetsServiceProxy.getDataSets();
			int i = 0;
		} catch (Exception e) {
			SpagoBILogger
					.errorLog(
							"No comunication with SpagoBI server, could not retrieve dataset informations",
							e);
		}

		SpagoBIServerObjects sbso = new SpagoBIServerObjects();
		Vector<Dataset> datasetVector;
		try {
			datasetVector = sbso.getAllDatasets();

			for (Iterator iterator = datasetVector.iterator(); iterator
					.hasNext();) {
				Dataset dataset = (Dataset) iterator.next();
				datasetInfos.put(dataset.getLabel(), dataset);
			}
			Vector<GeoMap> mapVector = sbso.getAllGeoMaps();
			for (Iterator iterator = mapVector.iterator(); iterator.hasNext();) {
				GeoMap geoMap = (GeoMap) iterator.next();
				mapInfos.put(geoMap.getName(), geoMap);
			}
		} catch (NoServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SpagoBILogger.infoLog("END: " + GEOEditor.class.toString()
				+ " initialize Editor");
	}

	@Override
	public void createPartControl(Composite parent) {

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 10;
		layout.topMargin = 20;
		layout.leftMargin = 20;

		form.getBody().setLayout(layout);

		final Section section = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | SWT.RESIZE | SWT.TOP);

		//section.setSize(1000, 1000);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				// parent.setSize(width, height);
				form.reflow(true);
			}
		});
		section.setText("GEO designer");

		Composite sectionClient = toolkit.createComposite(section, SWT.RESIZE);
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		gl.makeColumnsEqualWidth = true;
		gl.marginHeight = 5;
		gl.marginRight = 5;
		gl.marginLeft = 5;
		sectionClient.setLayout(gl);


		measuresDesigner = new MeasuresDesigner(sectionClient, this,
				geoDocument);

		initializeEditor(geoDocument);
		// creazione delle combo e tabelle

		Group datasetGroup = new Group(sectionClient, SWT.FILL | SWT.RESIZE);
		datasetGroup.setSize(800, 600);
		datasetGroup.setLayout(sectionClient.getLayout());
		
		
		Group mapGroup = new Group(sectionClient, SWT.FILL | SWT.RESIZE);
		mapGroup.setSize(800, 600);
		mapGroup.setLayout(sectionClient.getLayout());

		createDatasetCombo(sectionClient, datasetGroup, form);
		createMapCombo(sectionClient, mapGroup, form);

		createDatasetTable(sectionClient, datasetGroup);
		//enter couple of indications to the user:
		Color color = new org.eclipse.swt.graphics.Color(sectionClient.getDisplay(), 255,0,0);
		String infoText ="Once hierarchies are set:\n right click on 'measure' typed column to add KPI settings \n right click on 'geoid' typed column to add granularity level.";
		Label infos = new Label(sectionClient, SWT.LEFT);		
		infos.setText(infoText);
		infos.setForeground(color);

		createMapTable(sectionClient, mapGroup);
		section.setClient(sectionClient);
		
		Section section0 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		Composite sectionHier = toolkit.createComposite(section0, SWT.RESIZE);
		sectionHier.setLayout(gl);
		section0.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		

		section0.setText("Hierarchies");
		section0.setDescription("Define Hierarchies and Levels");
		HierarchiesDesigner designer = new HierarchiesDesigner(sectionHier, this);

		geoDocument = Activator.getDefault().getGeoDocument();
		designer.setGeoDocument(geoDocument);
		designer.createHierarchiesTree(sectionHier, toolkit);
		
		section0.setClient(sectionHier);
		
		Section section1 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		Composite sectionCrossNav = toolkit.createComposite(section1, SWT.RESIZE);
		sectionCrossNav.setLayout(gl);
		section1.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section1.setText("Cross Navigation");
		section1.setDescription("Fill parameters for Cross Navigation");
		CrossNavigationDesigner crossNavigationDesigner = new CrossNavigationDesigner(sectionCrossNav, this, geoDocument);
		crossNavigationDesigner.createCrossnavigationTable(sectionCrossNav, toolkit);
		
		section1.setClient(sectionCrossNav);
		
		
		Section section2 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		Composite sectionGUI = toolkit.createComposite(section2, SWT.RESIZE);
		sectionGUI.setLayout(gl);
		section2.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section2.setText("GUI Settings - Windows");
		section2.setDescription("Insert parameters for GUI Settings Windows");
		GuiSettingsDesigner guiSettingsDesigner = new GuiSettingsDesigner(sectionGUI, this, geoDocument);
		guiSettingsDesigner.createGuiSettingsWindows(sectionGUI, toolkit);
		
		section2.setClient(sectionGUI);
		
		Section section3 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		Composite sectionGUIParams = toolkit.createComposite(section3, SWT.RESIZE);
		sectionGUIParams.setLayout(gl);
		section3.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section3.setText("GUI Settings - Params");
		section3.setDescription("Insert parameters for GUI Settings Params");
		GuiSettingsDesigner guiSettingsDesignerParams = new GuiSettingsDesigner(sectionGUIParams, this, geoDocument);
		guiSettingsDesignerParams.createGuiSettingsParams(sectionGUIParams, toolkit);
		
		section3.setClient(sectionGUIParams);
		
		Section section4 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE | SWT.RESIZE);
		
		Composite sectionGUILabels = toolkit.createComposite(section4, SWT.NONE);
		sectionGUILabels.setLayout(gl);
		section4.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section4.setText("GUI Settings - Labels");
		section4.setDescription("Insert parameters for GUI Settings Labels");
		GuiSettingsLabelDesigner guiSettingsDesignerLabels = new GuiSettingsLabelDesigner(sectionGUILabels, this, geoDocument);
		guiSettingsDesignerLabels.createGuiSettingsLabels(sectionGUILabels, toolkit, form);
		
		section4.setClient(sectionGUILabels);

		section.pack();
		sectionClient.pack();

		SpagoBILogger.infoLog("END " + GEOEditor.class.toString()
				+ ": create Part Control function");

	}

	private void createDatasetCombo(final Composite sectionClient,
			final Group datasetGroup, final ScrolledForm form) {

		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = SWT.END;
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 120;
		gd.verticalAlignment = SWT.TOP;

		Label datasetLabel = new Label(datasetGroup, SWT.SIMPLE);
		datasetLabel.setText("Data Set");
		datasetLabel.setAlignment(SWT.RIGHT);

		Metadata metadata = MetadataBO.getMetadata(geoDocument);

		datasetCombo = new Combo(datasetGroup, SWT.SIMPLE | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		int index = 0;
		Iterator<String> iterator = datasetInfos.keySet().iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			datasetCombo.add(name);
			if (metadata != null && metadata.getDataset() != null
					&& metadata.getDataset().equals(name)) {
				datasetCombo.select(index);

			}
			index++;
		}
		///datasource
		if(metadata != null){
			Dataset dataset = datasetInfos.get(metadata.getDataset());
/*			if(dataset != null){
				Integer datasourceId = dataset.getJdbcDataSourceId();
				
				SpagoBIServerObjects sbso=new SpagoBIServerObjects();
				DataSource sdkdataSource;
				try {
					sdkdataSource = sbso.getDataSourceById(datasourceId);
					sdkdataSource.getUrlConnection();
					
					Datasource datasource = DatasourceBO.setNewDatasource(geoDocument);
					datasource.setDriver(sdkdataSource.getDriver());
					datasource.setPassword(sdkdataSource.getPwd());
					datasource.setType("connection");
					datasource.setUrl(sdkdataSource.getUrlConnection());
					datasource.setUser(sdkdataSource.getName());
				} catch (NoServerException e3) {
					SpagoBILogger.errorLog(e3.getMessage(), e3);
				}
			}*/
		}
		datasetCombo.setLayoutData(gd);

		datasetCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// Once selected the dataset fill the table with its metadata,
				// check first if they have been already recovered!
				datasetTable.removeAll();
				datasetTable.setItemCount(0);
				datasetTable.pack();

				if (datasetTableEditors != null) {
					for (int i = 0; i < datasetTableEditors.size(); i++) {
						TableEditor editor = datasetTableEditors.elementAt(i);
						Control old = editor.getEditor();
						if (old != null)
							old.dispose();
					}
				}
				int indexSelection = datasetCombo.getSelectionIndex();
				String datasetLabel = datasetCombo.getItem(indexSelection);
				selectedDataset = datasetLabel;
				DataStoreMetadata dataStoreMetadata = null;
				// get the metadata
				if (tempDsMetadataInfos.get(datasetLabel) != null) {
					dataStoreMetadata = tempDsMetadataInfos.get(datasetLabel);
				} else {
					Dataset dataset = datasetInfos.get(datasetLabel);
/*					it.eng.spagobi.studio.geo.editors.model.geo.Dataset datasetGeo = DatasetBO
							.setNewDataset(geoDocument, dataset.getJdbcQuery());*/
					Integer datasourceId = dataset.getJdbcDataSourceId();
					
					SpagoBIServerObjects sbso=new SpagoBIServerObjects();
					DataSource sdkdataSource;
					try {
						sdkdataSource = sbso.getDataSourceById(datasourceId);
						sdkdataSource.getUrlConnection();
						
/*						Datasource datasource = DatasourceBO.setNewDatasource(geoDocument);
						datasource.setDriver(sdkdataSource.getDriver());
						datasource.setPassword(sdkdataSource.getPwd());
						datasource.setType("connection");
						datasource.setUrl(sdkdataSource.getUrlConnection());
						datasource.setUser(sdkdataSource.getName());*/
					} catch (NoServerException e3) {
						SpagoBILogger.errorLog(e3.getMessage(), e3);
					}

					try {
						dataStoreMetadata = new SpagoBIServerObjects()
								.getDataStoreMetadata(dataset.getId());
						if (dataStoreMetadata != null) {
							tempDsMetadataInfos.put(datasetLabel,
									dataStoreMetadata);
						} else {
							SpagoBILogger
									.warningLog("Dataset returned no metadata");
							MessageDialog.openWarning(sectionClient.getShell(),
									"Warning", "Dataset with label = "
											+ datasetLabel
											+ " returned no metadata");
						}
					} catch (MissingParameterValue e2) {
						SpagoBILogger
								.errorLog(
										"Could not execute dataset with label = "
												+ datasetLabel
												+ " due to parameters lack: execute dataset test in server to retrieve metadata",
										e2);
						MessageDialog
								.openError(
										sectionClient.getShell(),
										"Error",
										"Could not execute dataset with label = "
												+ datasetLabel
												+ " due to parameters lack: execute dataset test in server to retrieve metadata");
					} catch (NoServerException e1) {
						SpagoBILogger.errorLog(
								"Error No comunciation with server retrieving dataset with label = "
										+ datasetLabel + " metadata", e1);
						MessageDialog.openError(sectionClient.getShell(),
								"Error",
								"No comunciation with server retrieving dataset with label = "
										+ datasetLabel + " metadata");
					}
				}
				if (dataStoreMetadata != null) {
					fillDatasetTable(dataStoreMetadata, true);
				}
				// resize the row height using a MeasureItem listener
				datasetTable.addListener(SWT.MeasureItem, new Listener() {
					public void handleEvent(Event event) {
						// height cannot be per row so simply set
						event.height = 20;
					}
				});

				datasetTable.pack();
				datasetGroup.pack();
				datasetGroup.redraw();
/*				sectionClient.getParent().pack();
				sectionClient.getParent().redraw();*/
				form.reflow(true);
				setIsDirty(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	private void createMapCombo(final Composite sectionClient, final Group mapGroup, final ScrolledForm form) {

		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = SWT.END;
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 120;
		gd.verticalAlignment = SWT.TOP;

		Label mapLabel = new Label(mapGroup, SWT.SIMPLE);
		mapLabel.setText("Map");
		mapLabel.setAlignment(SWT.RIGHT);

		Layers layers = LayersBO.getLayers(geoDocument);

		final Combo mapCombo = new Combo(mapGroup, SWT.SIMPLE | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		int index = 0;
		for (Iterator<String> iterator = mapInfos.keySet().iterator(); iterator
				.hasNext();) {
			String name = (String) iterator.next();
			mapCombo.add(name);
			if (layers != null && layers.getMapName() != null
					&& layers.getMapName().equals(name)) {
				mapCombo.select(index);
			}
			index++;
		}

		mapCombo.setLayoutData(gd);

		mapCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// Once selected the dataset fill the table with its metadata,
				// check first if they have been already recovered!
				mapTable.removeAll();
				mapTable.setItemCount(0);
				mapTable.pack();

				if (mapTableEditors != null) {
					for (int i = 0; i < mapTableEditors.size(); i++) {
						TableEditor editor = mapTableEditors.elementAt(i);
						Control old = editor.getEditor();
						if (old != null)
							old.dispose();
					}
				}
				int indexSelection = mapCombo.getSelectionIndex();
				String mapLabel = mapCombo.getItem(indexSelection);
				selectedMap = mapLabel;
				//add mapName to mapprovider
				geoDocument.getMapProvider().setMapName(selectedMap);
				
				GeoFeature[] geoFeatures = null;
				// get the metadata
				if (tempMapMetadataInfos.get(mapLabel) != null) {
					geoFeatures = tempMapMetadataInfos.get(mapLabel);
				} else {
					GeoMap geoMap = mapInfos.get(mapLabel);
					try {
						geoFeatures = new SpagoBIServerObjects()
								.getFeaturesByMapId(geoMap.getMapId());
						if (geoFeatures != null) {
							tempMapMetadataInfos.put(mapLabel, geoFeatures);
						} else {
							SpagoBILogger
									.warningLog("No features returned from map with label "
											+ mapLabel);
							MessageDialog.openWarning(sectionClient.getShell(),
									"Warning",
									"No features returned from map with label "
											+ mapLabel);
						}
					} catch (NoServerException e1) {
						SpagoBILogger.errorLog(
								"Could not get features associated to map with label = "
										+ mapLabel, e1);
						MessageDialog.openError(sectionClient.getShell(),
								"Error",
								"Could not get features associated to map with label = "
										+ mapLabel);
					}
				}
				if (geoFeatures != null) {

					fillMapTable(geoFeatures, sectionClient, true);
				}
				// resize the row height using a MeasureItem listener
				mapTable.addListener(SWT.MeasureItem, new Listener() {
					public void handleEvent(Event event) {
						// height cannot be per row so simply set
						event.height = 25;
					}
				});

				mapTable.redraw();
				mapGroup.pack();
				mapGroup.redraw();

				form.reflow(true);
				setIsDirty(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}
	private void createDatasetTable(final Composite sectionClient,
			Group datasetGroup) {

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;

		datasetTable = new Table(datasetGroup, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.CHECK);
		datasetTable.setLayoutData(gd);
		datasetTable.setLinesVisible(true);
		datasetTable.setHeaderVisible(true);

		String[] titles = { "  Column name   ",
				"               Type               ", "     Select       ",
				"   Aggregation mode   " };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(datasetTable, SWT.NONE);
			column.setText(titles[i]);
			// column.setResizable(true);

		}
		// look up for metadata stored in geodocument
		final Metadata metadata = MetadataBO.getMetadata(geoDocument);

		if (metadata != null && metadata.getDataset() != null
				&& !metadata.getDataset().equals("")) {
			selectDataset(sectionClient, metadata);

		} else {
			for (int i = 0; i < 5; i++) {
				TableItem item = new TableItem(datasetTable, SWT.TRANSPARENT);
			}
		}
		for (int i = 0; i < titles.length; i++) {
			datasetTable.getColumn(i).pack();
		}
		// resize the row height using a MeasureItem listener
		datasetTable.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = 20;
			}
		});
		datasetTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem)e.item;
				if(item != null){
					String selType = e.detail == SWT.CHECK ? "Checked" : "Selected";
					if(selType != null && selType.equals("Checked")){
						String columnName = item.getText();
						Column col = ColumnBO.getColumnByName(geoDocument, columnName);
						col.setChoosenForTemplate(item.getChecked());
						setIsDirty(true);
					}
				}
			}
		});
		// listener per measures --> right click
		datasetTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.button == 3) {
					TableItem[] selection = datasetTable.getSelection();
					// find the column
					Column col = ColumnBO.getColumnByName(geoDocument,
							selection[0].getText());

					if (col != null
							&& col.getType().equalsIgnoreCase("measure")) {
						measuresDesigner.createMeasuresShell(sectionClient, col
								.getColumnId());
					}else if (col != null
							&& col.getType().equalsIgnoreCase("geoid")) {
						//check if another geoid is already defined
						Column colGeoid = MetadataBO.geoidColumnExists(geoDocument);
						if(colGeoid == null ||(colGeoid != null && colGeoid.equals(col))){
							createGeoIdHierarchiesShell(sectionClient, col);
						}else{
							MessageDialog.openWarning(sectionClient.getShell(), "Warning", "Another column of type geoid is already defined.");							
						}
						
					} else {
						MessageDialog.openWarning(sectionClient.getShell(),
								"Warning", "Operation denied.");
					}
				}
			}
		});
		datasetTable.redraw();

	}
	private void createGeoIdHierarchiesShell(Composite sectionClient, final Column column){
		final Shell dialog = new Shell (sectionClient.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("Granularity Level");
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label label = new Label (dialog, SWT.RIGHT);
		label.setText ("Hierarchy:");
		FormData data = new FormData ();
		data.width = 100;
		label.setLayoutData (data);

		Button cancel = new Button (dialog, SWT.PUSH);
		cancel.setText ("Cancel");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (100, 0);
		data.bottom = new FormAttachment (100, 0);
		cancel.setLayoutData (data);
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				dialog.close ();
			}
		});

		final Combo hierCombo = createHierachiesCombo(dialog);

		if(column != null && column.getHierarchy()!= null){
			hierCombo.setText(column.getHierarchy());
		}
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (label, 0, SWT.CENTER);

		hierCombo.setLayoutData (data);
		
		
		//Level
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(hierCombo, 5);

		Label labelLevel = new Label (dialog, SWT.RIGHT);
		labelLevel.setText ("Level:");		
		labelLevel.setLayoutData (data);
		
		String hierarchyName = hierCombo.getText();
		final Combo levelCombo = createLevelsCombo(dialog, hierarchyName);
		if(column != null && column.getLevel()!= null){
			levelCombo.setText(column.getLevel());
		}
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelLevel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelLevel, 0, SWT.CENTER);
		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		levelCombo.setLayoutData (data);
		
		hierCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String hierarchySelected = ((Combo)e.widget).getText();
				recreateLevelsCombo(dialog, levelCombo, hierarchySelected);
				setIsDirty(true);
				
				levelCombo.redraw();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {

				String level = levelCombo.getText();
				String hierarchy = hierCombo.getText();
				if(hierarchy != null && level != null){
					column.setHierarchy(hierarchy);
					column.setLevel(level);
				}
				
				dialog.close ();
			}
		});

		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();
	}
	
	private Combo createHierachiesCombo(Composite dialog){
		Combo hierCombo = new Combo(dialog, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		Hierarchies hierarchies=HierarchyBO.getAllHierarchies(geoDocument);
		if(hierarchies != null && hierarchies.getHierarchy() != null){
			for(int i=0; i< hierarchies.getHierarchy().size(); i++){
				Hierarchy hier = hierarchies.getHierarchy().elementAt(i);
				String name = hier.getName();
				hierCombo.add(name);				
			}
		}
		return hierCombo;
	}
	private Combo createLevelsCombo(Composite dialog, String hierarchyName){
		Combo levelCombo = new Combo(dialog, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		if(hierarchyName != null && !hierarchyName.equals("")){
			Vector<Level> levels=LevelBO.getLevelsByHierarchyName(geoDocument, hierarchyName);
			if(levels != null){
				for(int i=0; i< levels.size(); i++){
					Level level = levels.elementAt(i);
					String name = level.getName();
					levelCombo.add(name);				
				}
			}
		}
		return levelCombo;
	}
	private void recreateLevelsCombo(Composite dialog,Combo levelCombo, String hierarchyName){
		levelCombo.removeAll();
		if(hierarchyName != null && !hierarchyName.equals("")){
			Vector<Level> levels=LevelBO.getLevelsByHierarchyName(geoDocument, hierarchyName);
			if(levels != null){
				for(int i=0; i< levels.size(); i++){
					Level level = levels.elementAt(i);
					String name = level.getName();
					levelCombo.add(name);				
				}
			}
		}
	}
	private void selectFeature(Composite sectionClient, Layers layers) {
		try {
			selectedMap = layers.getMapName();
			GeoMap geoMap = mapInfos.get(selectedMap);
			GeoFeature[] geoFeatures = new SpagoBIServerObjects()
					.getFeaturesByMapId(geoMap.getMapId());
			if (geoFeatures != null) {
				tempMapMetadataInfos.put(selectedMap, geoFeatures);
				fillMapTable(geoFeatures, sectionClient, false);
			} else {
				SpagoBILogger
						.warningLog("No features returned from map with label "
								+ selectedMap);
				MessageDialog.openWarning(sectionClient.getShell(), "Warning",
						"No features returned from map with label "
								+ selectedMap);
			}
		} catch (NoServerException e1) {
			SpagoBILogger.errorLog(
					"Could not get features associated to map with label = "
							+ selectedMap, e1);
			MessageDialog.openError(sectionClient.getShell(), "Error",
					"Could not get features associated to map with label = "
							+ selectedMap);
		}
	}

	private void selectDataset(Composite sectionClient, Metadata metadata) {

		try {
			selectedDataset = metadata.getDataset();
			Dataset dataset = datasetInfos.get(metadata.getDataset());
			DataStoreMetadata dataStoreMetadata = new SpagoBIServerObjects()
					.getDataStoreMetadata(dataset.getId());
			if (dataStoreMetadata != null) {
				tempDsMetadataInfos.put(metadata.getDataset(),
						dataStoreMetadata);
				fillDatasetTable(dataStoreMetadata, false);
			} else {
				SpagoBILogger.warningLog("Dataset returned no metadata");
				MessageDialog.openWarning(sectionClient.getShell(), "Warning",
						"Dataset with label = " + metadata.getDataset()
								+ " returned no metadata");
			}
		} catch (MissingParameterValue e2) {
			SpagoBILogger.errorLog("Could not execute dataset with label = "
					+ metadata.getDataset()
					+ " due to parameter lack: execute dataset test in server to retrieve metadata", e2);
			MessageDialog.openError(sectionClient.getShell(), "Error",
					"Could not execute dataset with label = "
							+ metadata.getDataset()
							+ " due to parameter lack: execute dataset test in server to retrieve metadata");
		} catch (NoServerException e1) {
			SpagoBILogger.errorLog(
					"Error No comunciation with server retrieving dataset with label = "
							+ metadata.getDataset() + " metadata", e1);
			MessageDialog.openError(sectionClient.getShell(), "Error",
					"No comunciation with server retrieving dataset with label = "
							+ metadata.getDataset() + " metadata");
		}
	}

	

	private void createMapTable(Composite sectionClient, Group mapGroup) {

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;

		mapTable = new Table(mapGroup, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.CHECK);
		mapTable.setLayoutData(gd);
		mapTable.setLinesVisible(true);
		mapTable.setHeaderVisible(true);

		String[] titles = { "   Feature name      ",
				"           Description        ", "Select Default ",
				"  Default Color    " };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(mapTable, SWT.NONE);
			column.setText(titles[i]);
			// column.setResizable(true);
		}
		Layers layers = LayersBO.getLayers(geoDocument);

		if (layers != null && layers.getMapName() != null
				&& !layers.getMapName().equals("")) {
			selectFeature(sectionClient, layers);

		} else {
			for (int i = 0; i < 5; i++) {
				TableItem item = new TableItem(mapTable, SWT.TRANSPARENT);
			}
		}

		for (int i = 0; i < titles.length; i++) {
			mapTable.getColumn(i).pack();
		}
		// resize the row height using a MeasureItem listener
		mapTable.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = 25;

			}
		});
		mapTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem)e.item;
				if(item != null){
					String selType = e.detail == SWT.CHECK ? "Checked" : "Selected";
					if(selType != null && selType.equals("Checked")){
						String featureId = item.getText();
						Layer layer = LayerBO.getLayerByName(geoDocument, featureId);
						layer.setChoosenForTemplate(item.getChecked());
						setIsDirty(true);
					}
				}
			}
		});
		mapTable.redraw();

	}

	private void fillMapTable(GeoFeature[] geoFeatures,
			Composite sectionClient, boolean replace) {
		if (replace) {
			LayersBO.setNewLayers(geoDocument, selectedMap);
		}
		for (int i = 0; i < geoFeatures.length; i++) {
			GeoFeature geoFeature = geoFeatures[i];

			Layer layer = LayerBO.getLayerByName(geoDocument, geoFeature
					.getName());
			if (layer == null) {
				// if no column exists than create it
				layer = LayerBO.setNewLayer(geoDocument, geoFeature.getName(),
						selectedMap);
			}
			final Layer selectedLayer = layer;
			TableItem item = new TableItem(mapTable, SWT.CENTER);
			item.setChecked(selectedLayer.isChoosenForTemplate());

			item.setText(FEATURE_NAME, geoFeature.getName());

			TableEditor editor = new TableEditor(mapTable);
			Text newDescr = new Text(mapTable, SWT.BORDER);
			newDescr.setBackground(new Color(sectionClient.getDisplay(),
					new RGB(245, 245, 245)));
			newDescr.setText(geoFeature.getDescr() != null ? geoFeature
					.getDescr() : "");
			if (layer != null && selectedLayer.getDescription() != null) {
				newDescr.setText(selectedLayer.getDescription());
			}

			newDescr.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent me) {
//					System.out.println("Changed");
					selectedLayer.setDescription(((Text) me.widget).getText());
					setIsDirty(true);
				}
			});

			editor.minimumWidth = newDescr.getBounds().x;
			editor.horizontalAlignment = SWT.CENTER;
			editor.grabHorizontal = true;
			editor.minimumHeight = newDescr.getBounds().y;
			editor.verticalAlignment = SWT.CENTER;
			editor.grabVertical = true;
			newDescr.selectAll();
			newDescr.setFocus();
			editor.setEditor(newDescr, item, FEATURE_DESCR);
			mapTableEditors.add(editor);

			final Button selButton = new Button(mapTable, SWT.RADIO);
			selButton.setText("");
			editor = new TableEditor(mapTable);
			editor.minimumWidth = selButton.getBounds().x;
			editor.horizontalAlignment = SWT.CENTER;
			editor.grabHorizontal = true;
			editor.minimumHeight = selButton.getBounds().y;
			editor.verticalAlignment = SWT.CENTER;
			editor.grabVertical = true;
			editor.setEditor(selButton, item, FEATURE_DEFAULT_LEVEL);
			editor.layout();

			final boolean[] isSelected = new boolean[1];

			selButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					isSelected[0] = e.widget == selButton;
					selectedLayer.setSelected(String.valueOf(isSelected[0]));

					setIsDirty(true);
				}
			});

			if (selectedLayer.getSelected() != null
					&& selectedLayer.getSelected().equals("true")) {
				selButton.setSelection(true);
			}

			final String[] defaultFillColour = new String[1];
			defaultFillColour[0] = "#FF0000";
			if (selectedLayer.getDefaultFillColour() != null) {
				defaultFillColour[0] = selectedLayer.getDefaultFillColour();
			}
			final Composite colorSection = DesignerUtils
					.createColorPickerFillLayer(mapTable, defaultFillColour[0],
							selectedLayer, this);
			String col = (String) colorSection.getData();
			selectedLayer.setDefaultFillColour(defaultFillColour[0]);

			mapTableEditors.add(editor);

			editor = new TableEditor(mapTable);
			editor.horizontalAlignment = SWT.LEFT;
			editor.grabHorizontal = true;
			editor.setEditor(colorSection, item, FEATURE_DEFAULT_COLORS);
			editor.layout();
			mapTableEditors.add(editor);

		}
		mapTable.pack();
		mapTable.redraw();
	}

	private void fillDatasetTable(DataStoreMetadata dataStoreMetadata,
			boolean replace) {
		// if dataset changed than new Metadata
		if (replace) {
			MetadataBO.setNewMetadata(geoDocument, selectedDataset);
		}

		for (int i = 0; i < dataStoreMetadata.getFieldsMetadata().length; i++) {

			DataStoreMetadataField dsmf = dataStoreMetadata.getFieldsMetadata()[i];
			// find out the current column
			Column column = ColumnBO.getColumnByName(geoDocument, dsmf
					.getName());
			if (column == null) {
				// if no column exists than create it
				column = ColumnBO.setNewColumn(geoDocument, dsmf.getName(),
						selectedDataset);
			}
			final Column selectedColumn = column;

			final TableItem item = new TableItem(datasetTable, SWT.NONE);
			item.setChecked(selectedColumn.isChoosenForTemplate());

			item.setText(DATASET_NAME, dsmf.getName());
			item.setText(DATASET_CLASS, dsmf.getClassName());
			// combo per geoid, measure
			final Combo comboSel = new Combo(datasetTable, SWT.SIMPLE
					| SWT.DROP_DOWN | SWT.READ_ONLY);
			//geoid is unique
			comboSel.add("geoid");
			comboSel.add("measure");
			//comboSel.add("geocd");
			for (int k = 0; k < comboSel.getItemCount(); k++) {
				String typeText = comboSel.getItem(k);
				if (selectedColumn.getType() != null
						&& selectedColumn.getType().equals(typeText)) {
					comboSel.select(k);
				}
			}
			if (comboSel.getText() != null) {
				item.setText(2, comboSel.getText());
				if (comboSel.getText().equalsIgnoreCase("measure")) {
					item.setImage(0, measureIcon.createImage());
					
				} else if(comboSel.getText().equalsIgnoreCase("geoid")){
					item.setImage(0, idIcon.createImage());
				}else{
					if (item.getImage() != null) {
						item.setImage(0, null);
					}
				}
				
			}
			

			comboSel.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					// per valorizzare table item col valore del widget
					// contenuto
					item.setText(2, comboSel.getText());
					Column col = ColumnBO.getColumnByName(geoDocument,
							item.getText());
					if (comboSel.getText().equalsIgnoreCase("measure")) {
						item.setImage(0, measureIcon.createImage());
					} else if(comboSel.getText().equalsIgnoreCase("geoid")){
						//check if another geoid is already defined
						Column colGeoid = MetadataBO.geoidColumnExists(geoDocument);
						if(colGeoid == null ||(colGeoid != null && colGeoid.equals(col))){
							item.setImage(0, idIcon.createImage());
						}else{
							MessageDialog.openWarning(datasetTable.getParent().getShell(), "Warning", "Another column of type geoid is already defined.");		
							comboSel.deselectAll();
						}					
					}else {
						if (item.getImage() != null) {
							item.setImage(0, null);
						}
					}
					// add type
					selectedColumn.setType(comboSel.getText());
					setIsDirty(true);
				}
			});
			selectedColumn.setType(comboSel.getText());
			comboSel.pack();
			TableEditor editor = new TableEditor(datasetTable);
			editor.minimumWidth = comboSel.getBounds().x;
			editor.horizontalAlignment = SWT.CENTER;
			editor.grabHorizontal = true;
			editor.minimumHeight = comboSel.getBounds().y;
			editor.verticalAlignment = SWT.CENTER;
			editor.grabVertical = true;

			editor.setEditor(comboSel, item, DATASET_SELECT);

			final Combo comboAgg = new Combo(datasetTable, SWT.SIMPLE
					| SWT.DROP_DOWN | SWT.READ_ONLY);
			comboAgg.add("");
			comboAgg.add("sum");
			comboAgg.add("media");


			for (int k = 0; k < comboAgg.getItemCount(); k++) {
				String aggText = comboAgg.getItem(k);
				if (selectedColumn.getAggFunction() != null
						&& selectedColumn.getAggFunction().equals(aggText)) {
					comboAgg.select(k);
				}
			}

			comboAgg.pack();
			// add aggregate function
			comboAgg.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					selectedColumn.setAggFunction(comboAgg.getText());
					setIsDirty(true);
				}
			});
			datasetTableEditors.add(editor);

			editor = new TableEditor(datasetTable);
			editor.minimumWidth = comboAgg.getBounds().x;
			editor.horizontalAlignment = SWT.CENTER;
			editor.grabHorizontal = true;
			editor.minimumHeight = comboAgg.getBounds().y;
			editor.verticalAlignment = SWT.CENTER;
			editor.grabVertical = true;
			editor.setEditor(comboAgg, item, DATASET_AGGREGATION);
			datasetTableEditors.add(editor);
			
			item.setData(comboSel);

			datasetTable.pack();
			datasetTable.redraw();

		}
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return isDirty;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void setIsDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		SpagoBILogger.infoLog("Start Saving GEO Template File");
		ByteArrayInputStream bais = null;

		try {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			
			ModelBO modelBO = new ModelBO();
			
			GEODocument geoDocumentToSaveOnFile = (GEODocument)DeepCopy.copy(geoDocument);
			modelBO.cleanGEODocument(geoDocumentToSaveOnFile);
			String newContent = XmlTemplateGenerator
					.transformToXml(geoDocumentToSaveOnFile);
//			System.out.println("******** SAVING ***************");
//			System.out.println(newContent);
			byte[] bytes = newContent.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);

		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error while Saving GEO Template File", e);
			e.printStackTrace();
		} finally {
			if (bais != null)
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		setIsDirty(false);
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getSelectedDataset() {
		return selectedDataset;
	}

	public void setSelectedDataset(String selectedDataset) {
		this.selectedDataset = selectedDataset;
	}

	public HashMap<String, DataStoreMetadata> getTempDsMetadataInfos() {
		return tempDsMetadataInfos;
	}

	public void setTempDsMetadataInfos(
			HashMap<String, DataStoreMetadata> tempDsMetadataInfos) {
		this.tempDsMetadataInfos = tempDsMetadataInfos;
	}

	public HashMap<String, GeoFeature[]> getTempMapMetadataInfos() {
		return tempMapMetadataInfos;
	}

	public void setTempMapMetadataInfos(
			HashMap<String, GeoFeature[]> tempMapMetadataInfos) {
		this.tempMapMetadataInfos = tempMapMetadataInfos;
	}

	public HashMap<String, Dataset> getDatasetInfos() {
		return datasetInfos;
	}

	public void setDatasetInfos(HashMap<String, Dataset> datasetInfos) {
		this.datasetInfos = datasetInfos;
	}

	public HashMap<String, GeoMap> getMapInfos() {
		return mapInfos;
	}

	public void setMapInfos(HashMap<String, GeoMap> mapInfos) {
		this.mapInfos = mapInfos;
	}

	public String getSelectedMap() {
		return selectedMap;
	}

	public void setSelectedMap(String selectedMap) {
		this.selectedMap = selectedMap;
	}
	public Table getDatasetTable() {
		return datasetTable;
	}

	public void setDatasetTable(Table datasetTable) {
		this.datasetTable = datasetTable;
	}
}
