package it.eng.spagobi.studio.core.wizards.downloadWizard;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.util.SdkFunctionalityTreeGenerator;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;

import java.lang.reflect.InvocationTargetException;

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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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

public class SpagoBIDownloadWizardPage extends WizardPage {
	//private Text containerText;

	//private Text fileText;
	private static Logger logger = LoggerFactory.getLogger(SpagoBIDownloadWizardPage.class);

	private IStructuredSelection selection;
	private Tree tree;
	private ProgressMonitorPart monitor;
	private Functionality functionality;
	String projectName = null;


	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SpagoBIDownloadWizardPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("Download Document Wizard");
		setDescription("This wizard lets you download a BI document template from SpagoBI Server");
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
//		SDKProxyFactory proxyFactory = null;
//		try{
//			Server server = new ServerHandler().getCurrentActiveServer(projectName);
//			proxyFactory=new SDKProxyFactory(server);
//		}
//		catch (NoActiveServerException e1) {
//			SpagoBILogger.errorLog("No active server found", e1);			
//			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
//					"Error", "No active server found");	
//			return;
//		}


		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Download documents tree", IProgressMonitor.UNKNOWN);
				SDKProxyFactory proxyFactory = null;
//				try {
//					Server server = new ServerHandler().getCurrentActiveServer(projectName);
//					proxyFactory=new SDKProxyFactory(server);
//				}
//				catch (NoActiveServerException e1) {
//					SpagoBILogger.errorLog("No active server found", e1);			
//					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
//							"Error", "No active server found");	
//					return;
//				}


				try{

					SpagoBIServerObjectsFactory spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectName);

					functionality=spagoBIServerObjects.getServerDocuments().getDocumentsAsTree(null);			

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

		ProgressMonitorDialog dialog=new ProgressMonitorDialog(getShell());		
		try {
			dialog.run(true, true, op);
		} catch (InvocationTargetException e1) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server", e1);
			dialog.close();
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		} catch (InterruptedException e1) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server", e1);
			dialog.close();
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "No comunication with server", "Error in comunication with SpagoBi Server; check its definition and check if the service is avalaible");	
			return;
		}	
		dialog.close();


		SdkFunctionalityTreeGenerator treeGenerator=new SdkFunctionalityTreeGenerator();			

		try{
			tree=treeGenerator.generateTree(container, functionality);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error while generating tree", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in generating the tree, control if SpagoBI Server is defined and service is avalaible");	
		}

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
			}
		}
		logger.debug("OUT");

	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		logger.debug("IN");
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
		"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				//containerText.setText(((Path) result[0]).toString());
			}
		}
		logger.debug("OUT");
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

	/**
	 *  DOwnlaod wizard page is complete if something has been selected, both one or more document or one or more folders
	 */

	public boolean isPageComplete() {
		boolean isComplete=false;
		if(tree!=null){
			TreeItem[] treeItems=tree.getSelection();
			if(treeItems!=null && treeItems.length>=1){
				isComplete = true;
			}			
		}

		return isComplete;
	}

}