/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.core.actions;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.exceptions.NotAllowedOperationException;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.studio.core.exceptions.NoDocumentException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.properties.PropertyPage;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.core.wizards.deployWizard.SpagoBIDeployWizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

public class DeployDocumentAction implements IViewActionDelegate {

	private IViewPart view = null;



	public DeployDocumentAction() {
	}


	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {

		SpagoBIDeployWizard sbindw = new SpagoBIDeployWizard();

		CommonViewer commViewer=((CommonNavigator) view).getCommonViewer();
		IStructuredSelection sel=(IStructuredSelection)commViewer.getSelection();

		// go on only if you selected a document
		Object objSel = sel.toList().get(0);
		org.eclipse.core.internal.resources.File fileSel = null;		
		try{
			fileSel=(org.eclipse.core.internal.resources.File)objSel;
		}
		catch (Exception e) {
			SpagoBILogger.warningLog("No file selected to deploy");					
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Not a file", "You must select a file to deploy");		
			return;
		}

		//if file has document metadata associated upload it, else call wizard

		String document_idString=null;
		String document_label=null;
		try {
			document_idString=fileSel.getPersistentProperty(PropertyPage.DOCUMENT_ID);			
			document_label=fileSel.getPersistentProperty(PropertyPage.DOCUMENT_LABEL);
		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error in retrieving document Label", e);		
			//SpagoBILogger.errorLog("Error in retrieving document Label", e);
		}

		// IF File selected has already and id of document associated do the upload wiyhout asking further informations
		if(document_idString!=null){
			SpagoBILogger.infoLog("Template already associated to document "+document_idString);	
			final Integer idInteger=Integer.valueOf(document_idString);
			final String label2=document_label;
			final org.eclipse.core.internal.resources.File fileSel2=fileSel;
			final NoDocumentException documentException=new NoDocumentException();
			IRunnableWithProgress op = new IRunnableWithProgress() {			
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					monitor.beginTask("Deploying to document "+label2, IProgressMonitor.UNKNOWN);
			
					// document associated, upload the template
					SDKProxyFactory proxyFactory=new SDKProxyFactory();
					DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy();

					URI uri=fileSel2.getLocationURI();

					File fileJava=new File(uri.getPath()); 
					FileDataSource fileDataSource=new FileDataSource(fileJava);
					DataHandler dataHandler=new DataHandler(fileDataSource);			
					SDKTemplate sdkTemplate=new SDKTemplate();
					sdkTemplate.setFileName(fileSel2.getName());
					sdkTemplate.setContent(dataHandler);

					try {
						// check document still exists
						SDKDocument doc=docServiceProxy.getDocumentById(idInteger);
						if(doc==null){
							documentException.setNoDocument(true);
							SpagoBILogger.warningLog("Document no more present on server: with id "+idInteger);					
							return;
						}
						else{
							documentException.setNoDocument(false);
							docServiceProxy.uploadTemplate(idInteger, sdkTemplate);
						}
					}
					catch (NotAllowedOperationException e) {
						SpagoBILogger.errorLog("Not Allowed Operation", e);		

						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error upload", "Error while uploading the template: not allowed operation");	
						return;
					} catch (RemoteException e) {
						SpagoBILogger.errorLog("Error comunicating with server", e);		
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error comunicating with server", "Error while uploading the template: missing comunication with server");	
						return;
					}

					monitor.done();
					if (monitor.isCanceled())
						SpagoBILogger.errorLog("Operation not ended",new InterruptedException("The long running operation was cancelled"));
				}
			};


			ProgressMonitorDialog dialog=new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());		
			try {
				dialog.run(true, true, op);
			} catch (InvocationTargetException e1) {
				SpagoBILogger.errorLog("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return;
			} catch (InterruptedException e1) {
				SpagoBILogger.errorLog("Error comunicating with server", e1);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Missing comunication with server; check server definition and if service is avalaible");	
				dialog.close();
				return;
			} 
			if(documentException.isNoDocument()){
				SpagoBILogger.errorLog("Document no more present", null);			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error upload", "Document is no more present on server");	
				return;
			}


			dialog.close();

			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Deploy succesfull", "Deployed to the associated document "+document_label+" succesfull");		
			SpagoBILogger.infoLog("Deployed to the associated document "+document_label+" succesfull");		
		}
		else{
			SpagoBILogger.infoLog("deploy a new Document: start wizard");		
			// init wizard
			sbindw.init(PlatformUI.getWorkbench(), sel);
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
			// Open the wizard dialog
			dialog.open();	
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}