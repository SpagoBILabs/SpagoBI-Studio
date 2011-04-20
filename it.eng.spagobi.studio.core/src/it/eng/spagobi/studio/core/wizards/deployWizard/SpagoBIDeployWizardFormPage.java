package it.eng.spagobi.studio.core.wizards.deployWizard;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
import it.eng.spagobi.studio.core.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.core.util.BiObjectUtilities;
import it.eng.spagobi.studio.core.util.SdkSelectFolderTreeGenerator;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * Thre Download Wizard let the user to navigate the funcitonalities tree and select a document to download
 *
 */

public class SpagoBIDeployWizardFormPage extends WizardPage {
	private Text labelText;
	private Text nameText;
	private Text descriptionText;
	private Combo engineCombo;
	private Combo dataSetCombo; 
	private Combo dataSourceCombo; 
	private Combo stateCombo; 
	private Spinner refreshSecondsSpinner; 
	String projectName = null;

	private IStructuredSelection selection;
	private Tree tree;
	private String typeLabel;

	private Map<String, Integer> engineLabelIdMap;
	private Map<String, Integer> dataSetLabelIdMap;
	private Map<String, Integer> dataSourceLabelIdMap;

	private ProgressMonitorPart monitor;
	// Filter By type
	SDKEngine[] enginesList;
	SDKDataSet[] datasetList;		
	SDKDataSource[] datasourceList;		
	SDKFunctionality functionality=null;


	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SpagoBIDeployWizardFormPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("Deploy Document Wizard");
		setDescription("Deploy a new document; select the new document properties");
		this.selection = selection;
	}


	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Shell shell = parent.getShell();
		//shell.setSize(1300,500);
		monitor=new ProgressMonitorPart(getShell(), null);


		Object objSel = selection.toList().get(0);
		File fileSelected=(File)objSel;
		projectName = fileSelected.getProject().getName();

		// first of all get info from server		
		SDKProxyFactory proxyFactory= null;
		try{
			 proxyFactory=new SDKProxyFactory(projectName);
		}
		catch (NoActiveServerException e1) {
			SpagoBILogger.errorLog("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return;
		}

		final EnginesServiceProxy engineService=proxyFactory.getEnginesServiceProxy();
		final DataSetsSDKServiceProxy datasetService=proxyFactory.getDataSetsSDKServiceProxy();
		final DocumentsServiceProxy docService=proxyFactory.getDocumentsServiceProxy();
		final DataSourcesSDKServiceProxy datasourceService=proxyFactory.getDataSourcesSDKServiceProxy();

		IRunnableWithProgress op = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Deploy a new Document: ", IProgressMonitor.UNKNOWN);

				try{
					enginesList=engineService.getEngines();
					datasetList=datasetService.getDataSets();
					datasourceList=datasourceService.getDataSources();
					functionality=docService.getDocumentsAsTree(null);			
					String ciao= functionality.getId().toString()+" "+functionality.getName()+" label: "+functionality.getName();
				}
				catch (Exception e) {
					SpagoBILogger.errorLog("No comunication with SpagoBI server",e);		
					MessageDialog.openError(getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
					return;
				}
				monitor.done();
				if (monitor.isCanceled())
					SpagoBILogger.errorLog("Operation not ended",new InterruptedException("The long running operation was cancelled"));		
				SpagoBILogger.errorLog("Operation not ended",new InterruptedException("The long running operation was cancelled"));
			}
		};

		ProgressMonitorDialog dialog=new ProgressMonitorDialog(getShell());		
		try {
			dialog.run(true, true, op);
		} catch (InvocationTargetException e1) {
			SpagoBILogger.errorLog("Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible",e1);		
			dialog.close();
			MessageDialog.openError(getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		} catch (InterruptedException e1) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server", e1);
			dialog.close();
			MessageDialog.openError(getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		}	
		dialog.close();




		FillLayout fl2=new FillLayout();
		fl2.type=SWT.HORIZONTAL;
		parent.setLayout(fl2);




		Composite left=new Composite(parent,SWT.BORDER);
		Composite right =  new Composite(parent, SWT.BORDER);

		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		left.setLayout(gl);

		FillLayout fl=new FillLayout();
		right.setLayout(fl);

		// *************** Left Layout **********************

		new Label(left, SWT.NONE).setText("Label:");				
		labelText = new Text(left, SWT.BORDER);
		labelText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		labelText.setTextLimit(SpagoBIStudioConstants.BIOBJECT_LABEL_LIMIT);
		labelText.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				//check if page is complete
				boolean complete=isPageComplete();
				if(complete){
					setPageComplete(true);
				}
				else{
					setPageComplete(false);	        	
				}
			}
		});


		new Label(left, SWT.NONE).setText("Name:");				
		nameText = new Text(left, SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		nameText.setTextLimit(SpagoBIStudioConstants.BIOBJECT_NAME_LIMIT);
		nameText.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				//check if page is complete
				boolean complete=isPageComplete();
				if(complete){
					setPageComplete(true);
				}
				else{
					setPageComplete(false);	        	
				}
			}
		});

		new Label(left, SWT.NONE).setText("Description:");				
		descriptionText = new Text(left, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		descriptionText.setTextLimit(SpagoBIStudioConstants.BIOBJECT_DESCRIPTION_LIMIT);

		typeLabel=BiObjectUtilities.getTypeFromExtension(fileSelected.getName());

		if(typeLabel==null){
			SpagoBILogger.errorLog("File "+fileSelected.getName()+" has unknown exstension",null);
			MessageDialog.openError(getShell(), "No type", "File "+fileSelected.getName()+" has unknown exstension");
			return;
		}

		new Label(left, SWT.NONE).setText("Type: ");				
		new Label(left, SWT.NONE).setText(typeLabel);				

		new Label(left, SWT.NONE).setText("Engines");
		engineCombo = new Combo(left, SWT.NONE | SWT.READ_ONLY);
		engineCombo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		engineLabelIdMap=new HashMap<String, Integer>();


		for (SDKEngine engine : enginesList) {
			if(engine.getDocumentType().equalsIgnoreCase(typeLabel)){		
				engineCombo.add(engine.getLabel());
				engineLabelIdMap.put(engine.getLabel(), engine.getId());					
			}
		}

		// Select dataset
		new Label(left, SWT.NONE).setText("Dataset");
		dataSetCombo = new Combo(left, SWT.NONE | SWT.READ_ONLY);

		dataSetLabelIdMap=new HashMap<String, Integer>();
		// sort the items
		String[] datasetLabels = new String[datasetList.length];
		for (int i = 0; i < datasetLabels.length; i++) {
			SDKDataSet dataSet =datasetList[i];
			datasetLabels[i] = dataSet.getLabel();
			dataSetLabelIdMap.put(dataSet.getLabel(), dataSet.getId());
		}
		Arrays.sort(datasetLabels);
		dataSetCombo.setItems(datasetLabels);
		//		for (SDKDataSet dataSet : datasetList) {
		//			dataSetCombo.add(dataSet.getLabel());
		//			dataSetLabelIdMap.put(dataSet.getLabel(), dataSet.getId());
		//		}


		dataSetCombo.setEnabled(false);


		// Select datasource
		new Label(left, SWT.NONE).setText("Datasource");
		dataSourceCombo = new Combo(left, SWT.NONE | SWT.READ_ONLY);
		dataSourceLabelIdMap=new HashMap<String, Integer>();
		String[] datasourceLabels = new String[datasourceList.length];
		for (int i = 0; i < datasourceLabels.length; i++) {
			SDKDataSource dataSource =datasourceList[i];
			datasourceLabels[i] = dataSource.getLabel();
			dataSourceLabelIdMap.put(dataSource.getLabel(), dataSource.getId());
		}
		Arrays.sort(datasourceLabels);
		dataSourceCombo.setItems(datasourceLabels);

		//		for (SDKDataSource dataSource : datasourceList) {
		//			dataSourceCombo.add(dataSource.getLabel());
		//			dataSourceLabelIdMap.put(dataSource.getLabel(), dataSource.getId());
		//		}



		dataSourceCombo.setEnabled(false);


		engineCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				String comboText = engineCombo.getText();
				boolean found=false;
				Integer id=engineLabelIdMap.get(comboText);
				SDKEngine sdkEngine=null;
				for (int i = 0; i < enginesList.length; i++) {
					SDKEngine temp=enginesList[i];
					if(temp.getId().equals(id)){
						sdkEngine=temp;
						found=true;
					}
				}
				if(sdkEngine!=null){
					boolean useDataset=sdkEngine.getUseDataSet()!=null ? sdkEngine.getUseDataSet() : false;
					boolean useDatasource=sdkEngine.getUseDataSource()!=null ? sdkEngine.getUseDataSource() : false;
					dataSetCombo.setEnabled(useDataset);
					dataSourceCombo.setEnabled(useDatasource);


				}

			}
		});



		// Select State
		new Label(left, SWT.NONE).setText("State");
		stateCombo = new Combo(left, SWT.NONE | SWT.READ_ONLY);
		stateCombo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		stateCombo.add("REL");
		stateCombo.add("DEV");
		stateCombo.add("TEST");
		stateCombo.add("SUSP");


		new Label(left, SWT.NONE).setText("Refresh Seconds:");				
		refreshSecondsSpinner = new Spinner(left, SWT.NONE);

		setControl(left);



		// *************** Right Composite **********************


		SdkSelectFolderTreeGenerator treeGenerator=new SdkSelectFolderTreeGenerator();			
		tree=treeGenerator.generateTree(right, functionality);

		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//check if page is complete
				boolean complete=isPageComplete();
				if(complete){
					setPageComplete(true);
				}
				else{
					setPageComplete(false);	        	
				}
			}
		});

		setControl(right);

	}








	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
			}
		}
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
		"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				//containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */


	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}



	public boolean isPageComplete() {
		boolean isComplete=false;

		boolean labelAndNameFieldSet=false;
		String labelTextS=labelText.getText();
		String nameTextS=nameText.getText();

		if(labelTextS==null || nameTextS==null || labelTextS.equalsIgnoreCase("") || nameTextS.equalsIgnoreCase("")){
			return false;
		}


		if(tree!=null){
			TreeItem[] treeItems=tree.getSelection();
			if(treeItems!=null && treeItems.length==1){
				TreeItem treeItem=treeItems[0];
				Object data=treeItem.getData();
				if(data!=null && data instanceof SDKFunctionality){
					isComplete=true;
				}
			}
		}

		return isComplete;
	}



	public Text getLabelText() {
		return labelText;
	}

	public void setLabelText(Text labelText) {
		this.labelText = labelText;
	}

	public Text getNameText() {
		return nameText;
	}

	public void setNameText(Text nameText) {
		this.nameText = nameText;
	}

	public Text getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(Text descriptionText) {
		this.descriptionText = descriptionText;
	}

	public Combo getEngineCombo() {
		return engineCombo;
	}

	public void setEngineCombo(Combo engineCombo) {
		this.engineCombo = engineCombo;
	}

	public Combo getDataSetCombo() {
		return dataSetCombo;
	}

	public void setDataSetCombo(Combo dataSetCombo) {
		this.dataSetCombo = dataSetCombo;
	}

	public Combo getDataSourceCombo() {
		return dataSourceCombo;
	}

	public void setDataSourceCombo(Combo dataSourceCombo) {
		this.dataSourceCombo = dataSourceCombo;
	}

	public Combo getStateCombo() {
		return stateCombo;
	}

	public void setStateCombo(Combo stateCombo) {
		this.stateCombo = stateCombo;
	}

	public Spinner getRefreshSecondsSpinner() {
		return refreshSecondsSpinner;
	}

	public void setRefreshSecondsSpinner(Spinner refreshSecondsSpinner) {
		this.refreshSecondsSpinner = refreshSecondsSpinner;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String type) {
		this.typeLabel = type;
	}

	public Map<String, Integer> getEngineLabelIdMap() {
		return engineLabelIdMap;
	}

	public void setEngineLabelIdMap(Map<String, Integer> engineLabelIdMap) {
		this.engineLabelIdMap = engineLabelIdMap;
	}

	public Map<String, Integer> getDataSetLabelIdMap() {
		return dataSetLabelIdMap;
	}

	public void setDataSetLabelIdMap(Map<String, Integer> dataSetLabelIdMap) {
		this.dataSetLabelIdMap = dataSetLabelIdMap;
	}


	public Map<String, Integer> getDataSourceLabelIdMap() {
		return dataSourceLabelIdMap;
	}


	public void setDataSourceLabelIdMap(Map<String, Integer> dataSourceLabelIdMap) {
		this.dataSourceLabelIdMap = dataSourceLabelIdMap;
	}

}