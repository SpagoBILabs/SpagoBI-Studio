package it.eng.spagobi.studio.utils.wizard;


import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSpagoBIDocumentWizard extends Wizard implements INewWizard {

	protected IStructuredSelection selection;
	private static Logger logger = LoggerFactory.getLogger(AbstractSpagoBIDocumentWizard.class);
	protected IWorkbench workbench;

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

	public void launchWizard(IStructuredSelection selection, String title){
		init(workbench, selection);
		setWindowTitle(title);
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),this);
		dialog.open();
	}


	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}



}
