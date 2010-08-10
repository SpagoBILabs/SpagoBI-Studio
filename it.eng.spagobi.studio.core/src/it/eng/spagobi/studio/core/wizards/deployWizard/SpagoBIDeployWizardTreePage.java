package it.eng.spagobi.studio.core.wizards.deployWizard;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * Thre Download Wizard let the user to navigate the funcitonalities tree and select a document to download
 *
 */

public class SpagoBIDeployWizardTreePage extends WizardPage {
	//private Text containerText;

	//private Text fileText;

	private IStructuredSelection selection;
	private Tree tree;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SpagoBIDeployWizardTreePage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("Download Document Wizard");
		setDescription("This wizard lets you download a BI document template from SpagoBI Server");
		this.selection = selection;
	}

	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
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
		if(tree!=null){
			TreeItem[] treeItems=tree.getSelection();
			if(treeItems!=null && treeItems.length==1){
				TreeItem treeItem=treeItems[0];
				Object data=treeItem.getData();
				if(data!=null && data instanceof SDKDocument){
					isComplete=true;
				}
			}
		}

		return isComplete;
	}






	//	public String getContainerName() {
	//		return containerText.getText();
	//	}

	//	public String getFileName() {
	//		return fileText.getText();
	//	}
}