package it.eng.spagobi.studio.core.wizards.downloadModelWizard;

import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.ImageDescriptorGatherer;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Download Wizard let the user to select a model to download
 *
 */

public class SpagoBIDownloadModelWizardPage extends WizardPage {

	//private Text fileText;
	private static Logger logger = LoggerFactory.getLogger(SpagoBIDownloadModelWizardPage.class);

	private IStructuredSelection selection;
	private Tree tree;
	private Composite container;
	private ProgressMonitorPart monitor;
	private HashMap<String,String> models;
	private List<String> actualModels = null;
	String projectName = null;
	private boolean viewTree = false;
	
	private static ImageDescriptor treeBaseDescriptor 	= ImageDescriptorGatherer.getImageDesc("treebase.gif", Activator.PLUGIN_ID);
	private static ImageDescriptor folderDescriptor 	= ImageDescriptorGatherer.getImageDesc("folder.gif", Activator.PLUGIN_ID);
	private static ImageDescriptor fileDescriptor 		= ImageDescriptorGatherer.getImageDesc("spagobi_img16.bmp", Activator.PLUGIN_ID);

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SpagoBIDownloadModelWizardPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("Download Models Wizard");
		setDescription("This wizard lets you download a model template from SpagoBI Resources");
		this.selection = selection;
	}

	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		logger.debug("IN");
		monitor=new ProgressMonitorPart(getShell(), null);
		initialize();

		Composite container = new Composite(parent, SWT.NULL);
		FillLayout layout= new FillLayout();
		container.setLayout(layout);

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Download models", IProgressMonitor.UNKNOWN);
				try{

					SpagoBIServerObjectsFactory spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectName);
					models = spagoBIServerObjects.getServerDocuments().getAllDatamartModels();	
				}
				catch (NoActiveServerException e1) {
					SpagoBILogger.errorLog("No active server found", e1);			
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							"Error", "No active server found");	
					return;
				}
				catch (Exception e) {
					SpagoBILogger.errorLog("No comunication with SpagoBI server", e);
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
					return;
				}
				monitor.done();
				if (monitor.isCanceled())
					SpagoBILogger.errorLog("Operation not ended",new InterruptedException("The long running operation was cancelled"));
			}
		};	

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());		
		try {
			dialog.run(true, true, op);
		} catch (InvocationTargetException e1) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server", e1);
			dialog.close();
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		} catch (InterruptedException e2) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server", e2);
			dialog.close();
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		}	
		dialog.close();	
		
		try{
			if (models == null || models.size() == 0){
				SpagoBILogger.warningLog("No new models to download found");	
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Warning", "No Models to download found on the server!");	
				return;
			}
			tree = generateTree(container, models);
			SpagoBILogger.infoLog("getChildren: " + tree.getItems().length);
			if (tree == null || !isViewTree()){
				SpagoBILogger.warningLog("No new models to download found");	
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Warning", "No new Models to download found!");	
				return;
			}
			
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error while generating tree", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in generating the tree, control if SpagoBI Server is defined and service is avalaible");	
		}


		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//check if page is complete
				boolean complete = isPageComplete(event);
				if(complete){
					setPageComplete(true);
				}
				else{
					setPageComplete(false);	        	
				}
			}
		});


		setControl(container);
		logger.debug("OUT");

	}


	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		logger.debug("IN");

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

				projectName =container.getProject().getName();
				
				//gets all models yet present
				try{
					List tmpActualModels = new ArrayList();
					IResource[]  modelResources = container.members();
					for (int i=0; i < modelResources.length; i++){
						Object objRes = modelResources[i];
						if (objRes instanceof IFile) {
							IFile file = (IFile) objRes;
							if (file.getName().endsWith(SpagoBIStudioConstants.MODEL_EXTENSION)){
								tmpActualModels.add(file.getName()); 
							}
						}
						
					}
				 this.setActualModels(tmpActualModels);
				}catch(Exception e){
					SpagoBILogger.errorLog("Error while getting actual models ", e);
				}
			}
		}
		logger.debug("OUT");

	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	

	public HashMap<String, String> getModels() {
		return models;
	}

	public void setModels(HashMap<String, String> models) {
		this.models = models;
	}

	public List<String> getActualModels() {
		return actualModels;
	}

	public void setActualModels(List<String> actualModels) {
		this.actualModels = actualModels;
	}

	public boolean isViewTree() {
		return viewTree;
	}

	public void setViewTree(boolean viewTree) {
		this.viewTree = viewTree;
	}

	/**
	 *  Downlaod wizard page is complete if something has been selected, 
	 *  both one or more model
	 */

	public boolean isPageComplete(Event event) {
		boolean isComplete=false;
		if(tree!=null){
			TreeItem[] treeItems = tree.getSelection();
			if(treeItems != null && treeItems.length >= 1){
				if (event.item.getData() != null && event.item.getData().toString().endsWith(SpagoBIStudioConstants.MODEL_EXTENSION)){
					isComplete = true;
				}
			}					
		}

		return isComplete;
	}
	
	private Tree generateTree(Composite parent, HashMap mapModels){
		container = parent;
		
		tree = new Tree(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		TreeItem root = new TreeItem(tree,SWT.SINGLE);
		root.setText("Resources/qbe/datamarts");
		root.setImage(treeBaseDescriptor.createImage());
		createItemsList(root, mapModels);
		
		setPageComplete(false);	
		
		return tree;
	}

	private void createItemsList(TreeItem parent, HashMap mapModels){
		if (mapModels != null){
			
			for (Iterator iterator = mapModels.keySet().iterator(); iterator.hasNext();) {
				String folderName = (String) iterator.next();
				String fileName = (String)mapModels.get(folderName);
				
				if (!existModel(fileName)) {
					setViewTree(true);
					TreeItem currFolder = new TreeItem(parent, SWT.SINGLE);
					currFolder.setText(folderName);
					currFolder.setData(folderName);
					currFolder.setImage(folderDescriptor.createImage());
					
					TreeItem currModel = new TreeItem(currFolder, SWT.CHECK);
					currModel.setText(fileName);
					currModel.setData(fileName);
					currModel.setImage(fileDescriptor.createImage());
				}
			}				
		}
	}
	
	private boolean existModel(String fileName){
		boolean toReturn = false;
		
		List actualModels = this.getActualModels();
		for(int i=0; i < actualModels.size(); i++){
			String modelName = (String)actualModels.get(i);
			if (modelName.equals(fileName)){
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}

}