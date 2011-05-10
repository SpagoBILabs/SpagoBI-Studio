package it.eng.spagobi.studio.core.services.template;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import it.eng.spagobi.studio.core.actions.DeployDocumentAction;
import it.eng.spagobi.studio.core.wizards.deployWizard.SpagoBIDeployWizard;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjects;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployTemplateService {

	ISelection selection; 
	private static Logger logger = LoggerFactory.getLogger(DeployTemplateService.class);
	String projectname = null;
	SpagoBIDeployWizard sbdw = null;

	public DeployTemplateService(ISelection _selection, SpagoBIDeployWizard _sbdw) {
		selection = _selection;	
		sbdw = _sbdw;
	}


	/** if document has meadata associated do the automated deploy
	 * 
	 * @return if automated eply has been done
	 */
	public boolean doAutomaticDeploy(){
		logger.debug("IN");
		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if you selected a document
		Object objSel = sel.toList().get(0);
		org.eclipse.core.internal.resources.File fileSel = null;		
		fileSel=(org.eclipse.core.internal.resources.File)objSel;
		projectname = fileSel.getProject().getName();

		//if file has document metadata associated upload it, else call wizard

		String document_idString=null;
		String document_label=null;
		try {
			document_idString=fileSel.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);			
			document_label=fileSel.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL);
		} catch (CoreException e) {
			logger.error("Error in retrieving document Label", e);		
		}

		// IF File selected has already and id of document associated do the upload wiyhout asking further informations
		boolean newDeploy = false;
		if(document_idString!=null){
			logger.debug("Template already associated to document "+document_idString);	
			final Integer idInteger=Integer.valueOf(document_idString);
			final String label2=document_label;
			final org.eclipse.core.internal.resources.File fileSel2=fileSel;
			final NoDocumentException documentException=new NoDocumentException();
			final NoActiveServerException noActiveServerException=new NoActiveServerException();

			IRunnableWithProgress op = new IRunnableWithProgress() {			
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					monitor.beginTask("Deploying to document "+label2, IProgressMonitor.UNKNOWN);

					if(projectname == null){
						projectname = fileSel2.getProject().getName();
					}

					try{
						// document associated, upload the template

						SpagoBIServerObjects spagoBIServerObjects = new SpagoBIServerObjects(projectname);


						URI uri=fileSel2.getLocationURI();

						File fileJava=new File(uri.getPath()); 
						FileDataSource fileDataSource=new FileDataSource(fileJava);
						DataHandler dataHandler=new DataHandler(fileDataSource);			
						Template template=new Template();
						template.setFileName(fileSel2.getName());
						template.setContent(dataHandler);

						// check document still exists
						Document doc=spagoBIServerObjects.getDocumentById(idInteger);
						if(doc==null){
							documentException.setNoDocument(true);
							logger.warn("Document no more present on server: with id "+idInteger);					
							return;
						}
						else{
							documentException.setNoDocument(false);
							spagoBIServerObjects.uploadTemplate(idInteger, template);
						}
					}

					catch (NoActiveServerException e1) {
						// no active server found 
						noActiveServerException.setNoServer(true);
						return;
					}
					catch (RemoteException e) {
						logger.error("Error comunicating with server", e);		
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error comunicating with server", "Error while uploading the template: missing comunication with server");	
						return;
					}

					monitor.done();
					if (monitor.isCanceled())
						logger.error("Operation not ended",new InterruptedException("The long running operation was cancelled"));
				}
			};


			ProgressMonitorDialog dialog=new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		
			try {
				dialog.run(true, true, op);
			} 
			catch (InvocationTargetException e1) {
				logger.error("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return false;
			} catch (InterruptedException e1) {
				logger.error("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return false;
			} 
			if(documentException.isNoDocument()){
				logger.error("Document no more present");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error upload", "Document is no more present on server; you can do a new deploy");	
				newDeploy = true;
				sbdw.setNewDeployFromOld(true);
			}
			if(noActiveServerException.isNoServer()){
				logger.error("No server is defined active");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "No server is defined active");	
				return false;
			}

			dialog.close();
			if(!newDeploy){
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Deploy succesfull", "Deployed to the associated document "+document_label+" succesfull");		
				logger.debug("Deployed to the associated document "+document_label+" succesfull");		
			}		
		}
		else{
			newDeploy = true;
		}

		if(newDeploy)
		{
			logger.debug("deploy a new Document: start wizard");		
			// init wizard
			sbdw.init(PlatformUI.getWorkbench(), sel);
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbdw);

			//			{
			//				@Override
			//				protected void configureShell(Shell newShell) {
			//					super.configureShell(newShell);
			//					newShell.setSize(1300, 600);
			//				}
			//			};

			// Open the wizard dialog
			dialog.open();	
		}
		logger.debug("OUT");
		return true;



	}


}
