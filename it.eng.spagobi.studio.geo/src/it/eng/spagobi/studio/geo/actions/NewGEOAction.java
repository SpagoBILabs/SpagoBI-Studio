package it.eng.spagobi.studio.geo.actions;


import it.eng.spagobi.studio.geo.wizards.SpagoBIGEOWizard;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class NewGEOAction implements IObjectActionDelegate {

	private IViewPart view = null;

	ISelection selection;

	public NewGEOAction() {
		// TODO Auto-generated constructor stub
	}

	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {
		SpagoBIGEOWizard sbindw = new SpagoBIGEOWizard();
//		CommonViewer commViewer=((CommonNavigator) view).getCommonViewer();
//		IStructuredSelection sel=(IStructuredSelection)commViewer.getSelection();
		IStructuredSelection sel=(IStructuredSelection)selection;

		Object objSel = sel.toList().get(0);
		Folder folderSel = null;		
		try{
			
			// FolderSel is the folder in wich to insert the new template
			folderSel=(Folder)objSel;

			sbindw.init(PlatformUI.getWorkbench(), sel);
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
			// Open the wizard dialog
			dialog.open();

		}
		catch (Exception e) {
			//			SpagoBILogger.errorLog("no selected folder", e);			
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "You must select a folder in wich to insert the GEO document");		
		}


	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		
	}

	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub
		
	}
	


}
