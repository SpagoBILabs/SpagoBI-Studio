/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.wizards.deployOlapWizard;

import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.studio.core.util.SdkSelectFolderTreeGenerator;
import it.eng.spagobi.studio.utils.bo.DataSource;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NotAllowedOperationStudioException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.BiObjectUtilities;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thre Download Wizard let the user to navigate the funcitonalities tree and select a document to download
 *
 */

public class SpagoBIDeployOlapTemplateWizardFormPage extends WizardPage {
	//private Text labelText;
	private Text nameText;
	private Text descriptionText;
	//private Combo engineCombo;
	//private Combo dataSetCombo; 
	private Combo dataSourceCombo; 
	//private Combo stateCombo; 
	//private Spinner refreshSecondsSpinner; 
	String projectName = null;

	private IStructuredSelection selection;
//	private Tree tree;
	private String typeLabel;

	//private Map<String, Integer> engineLabelIdMap;
	//private Map<String, Integer> dataSetLabelIdMap;
	private Map<String, Integer> dataSourceLabelIdMap;

	private ProgressMonitorPart monitor;
	// Filter By type
	//Engine[] enginesList;
	//IDataSet[] datasetList;		
	DataSource[] datasourceList;		
	Functionality functionality=null;
	
	String insideLabelDataset = null;

	private static Logger logger = LoggerFactory.getLogger(SpagoBIDeployOlapTemplateWizardFormPage.class);

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SpagoBIDeployOlapTemplateWizardFormPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("Deploy Olap Template Wizard");
		setDescription("Deploy a new document; select the new document properties");
		this.selection = selection;
	}


	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		logger.debug("IN");
		Shell shell = parent.getShell();
		//shell.setSize(1300,500);
		monitor=new ProgressMonitorPart(getShell(), null);


		Object objSel = selection.toList().get(0);
		File fileSelected=(File)objSel;
		projectName = fileSelected.getProject().getName();

		final SpagoBIServerObjectsFactory proxyObjects;
		// first of all get info from server		
		SDKProxyFactory proxyFactory= null;
		try{
			proxyObjects = new SpagoBIServerObjectsFactory(projectName);

		}
		catch (NoActiveServerException e1) {
			logger.error("No active server found", e1);			
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "No active server found");	
			return;
		}




		final NotAllowedOperationStudioException notAllowedOperationStudioException = new NotAllowedOperationStudioException();	

		IRunnableWithProgress op = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Deploy a new Document: ", IProgressMonitor.UNKNOWN);

				try{

					datasourceList=proxyObjects.getServerDataSources().getDataSourceList();

				}
				catch (Exception e) {
					if(e.getClass().toString().equalsIgnoreCase("class it.eng.spagobi.sdk.exceptions.NotAllowedOperationException")){	
						logger.error("NotAllowed User Permission", e);
						notAllowedOperationStudioException.setNotAllowed(true);
					}
					else{
						logger.error("No comunication with SpagoBI server",e);		
						MessageDialog.openError(getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
					}
					return;
				}
				monitor.done();
				if (monitor.isCanceled())
					logger.error("The long running operation was cancelled");					
			}
		};

		ProgressMonitorDialog dialog=new ProgressMonitorDialog(getShell());		
		try {
			dialog.run(true, true, op);
		} catch (InvocationTargetException e1) {
			logger.error("Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible",e1);		
			dialog.close();
			MessageDialog.openError(getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		} catch (InterruptedException e1) {
			logger.error("No comunication with SpagoBI server", e1);
			dialog.close();
			MessageDialog.openError(getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		}	
		dialog.close();

		if(notAllowedOperationStudioException.isNotAllowed()){
			logger.error("User has no permission to complete the operation");
			MessageDialog.openError(getShell(), "Error", "User has no permission to complete the operation");	
			return;
		}


		
		FillLayout fl2=new FillLayout();
		fl2.type=SWT.HORIZONTAL;
		parent.setLayout(fl2);




		Composite left=new Composite(parent,SWT.BORDER);

		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = 2;
		left.setLayout(gl);

		FillLayout fl=new FillLayout();
		
		// *************** Left Layout **********************


		Label label_1 = new Label(left, SWT.NONE);
		label_1.setText("Name:");
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

		Label label_2 = new Label(left, SWT.NONE);
		label_2.setText("Description:");
		descriptionText = new Text(left, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		descriptionText.setTextLimit(SpagoBIStudioConstants.BIOBJECT_DESCRIPTION_LIMIT);

		typeLabel=BiObjectUtilities.getTypeFromExtension(fileSelected.getName());

		int indexPoint=fileSelected.getName().indexOf('.');
		String extension = "";
		if(indexPoint!=-1){
			extension=fileSelected.getName().substring(indexPoint+1);
		}

		if(typeLabel==null){
			logger.error("File "+fileSelected.getName()+" has unknown exstension");
			MessageDialog.openError(getShell(), "No type", "File "+fileSelected.getName()+" has unknown exstension");
			return;
		}
		

		// Select datasource
		Label label = new Label(left, SWT.NONE);
		label.setText("Datasource");


		String[] datasourceLabels = new String[datasourceList.length];
		dataSourceLabelIdMap=new HashMap<String, Integer>();

		for (int i = 0; i < datasourceLabels.length; i++) {
			DataSource dataSource =datasourceList[i];
			datasourceLabels[i] = dataSource.getLabel();
			dataSourceLabelIdMap.put(dataSource.getLabel(), dataSource.getId());
		}
		Arrays.sort(datasourceLabels);
		dataSourceCombo = new Combo(left, SWT.NONE | SWT.READ_ONLY);
		dataSourceCombo.setItems(datasourceLabels);

	
		setControl(left);



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




	public boolean isPageComplete() {

		String nameTextS=nameText.getText();

		if( nameTextS==null || nameTextS.equalsIgnoreCase("") ){
			return false;
		} else {
			return true;
		}

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



	public Combo getDataSourceCombo() {
		return dataSourceCombo;
	}

	public void setDataSourceCombo(Combo dataSourceCombo) {
		this.dataSourceCombo = dataSourceCombo;
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


	public Map<String, Integer> getDataSourceLabelIdMap() {
		return dataSourceLabelIdMap;
	}


	public void setDataSourceLabelIdMap(Map<String, Integer> dataSourceLabelIdMap) {
		this.dataSourceLabelIdMap = dataSourceLabelIdMap;
	}


	public String getInsideLabelDataset() {
		return insideLabelDataset;
	}


	public void setInsideLabelDataset(String insideLabelDataset) {
		this.insideLabelDataset = insideLabelDataset;
	}

	
}