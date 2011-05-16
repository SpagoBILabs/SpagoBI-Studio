package it.eng.spagobi.studio.core.wizards.deployDatasetWizard;


import it.eng.spagobi.studio.core.util.JSONReader;
import it.eng.spagobi.studio.core.wizards.SpagoBIDeployDatasetWizard;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployDatasetService {

	ISelection selection; 
	private static Logger logger = LoggerFactory.getLogger(DeployDatasetService.class);
	String projectname = null;
	SpagoBIDeployDatasetWizard sbdw = null;

	public DeployDatasetService(ISelection _selection, SpagoBIDeployDatasetWizard _sbdw) {
		selection = _selection;	
		sbdw = _sbdw;
	}


	/** if document has meadata associated do the automated deploy
	 * 
	 * @return if automated eply has been done
	 */
	public boolean tryAutomaticDeploy(){
		logger.debug("IN");

		IStructuredSelection sel=(IStructuredSelection)selection;

		// go on only if ysou selected a document
		Object objSel = sel.toList().get(0);
		org.eclipse.core.internal.resources.File fileSel = null;		
		fileSel=(org.eclipse.core.internal.resources.File)objSel;
		projectname = fileSel.getProject().getName();

		//if file has document metadata associated upload it, else call wizard

		String datasetId=null;
		String datasetLabel=null;
		try {
			datasetId=fileSel.getPersistentProperty(SpagoBIStudioConstants.DATASET_ID);			
			datasetLabel=fileSel.getPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL);
		} catch (CoreException e) {
			logger.error("Error in retrieving dataset Label", e);		
		}

		// IF File selected has already an id of datasetassociated do the upload wiyhout asking further informations
		boolean automatic = false;
		if(datasetId!=null){
			logger.debug("Query already associated to dataset"+datasetId+" - "+datasetLabel);	
			final Integer idInteger=Integer.valueOf(datasetId);
			final String label2=datasetLabel;
			final org.eclipse.core.internal.resources.File fileSel2=fileSel;
			final NoDocumentException datasetException=new NoDocumentException();
			final NoActiveServerException noActiveServerException=new NoActiveServerException();

			IRunnableWithProgress op = new IRunnableWithProgress() {			
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					monitor.beginTask("Deploying to dataset "+label2, IProgressMonitor.UNKNOWN);

					if(projectname == null){
						projectname = fileSel2.getProject().getName();
					}

					try{
						logger.debug("dataset associated, upload the query to dataset "+label2);

						SpagoBIServerObjectsFactory spagoBIServerObjects = new SpagoBIServerObjectsFactory(projectname);

						// check ataset still exists
						Dataset ds=spagoBIServerObjects.getServerDatasets().getDataSet(idInteger);
						if(ds==null){
							datasetException.setNoDocument(true);
							logger.warn("Dataset no more present on server: with id "+idInteger);					
						}
						else{
							logger.debug("update query to dataset");

							String queryStr = null;
							String adaptedQueryStrList = null;
							try{
								JSONObject obj = JSONReader.createJSONObject(fileSel2);
								queryStr = obj.optString("query");
								logger.debug("query is "+queryStr);					
								adaptedQueryStrList = DeployDatasetService.adaptQueryToList(queryStr);								
								logger.debug("adapted query list is "+adaptedQueryStrList);					
								ds.setJsonQuery(adaptedQueryStrList);
								datasetException.setNoDocument(false);
								spagoBIServerObjects.getServerDatasets().saveDataSet(ds);							}
							catch (Exception e) {
								logger.error("error in reading JSON object, update failed", e);
							}							


						}
					}

					catch (NoActiveServerException e1) {
						// no active server found 
						noActiveServerException.setNoServer(true);
					}
					catch (RemoteException e) {
						logger.error("Error comunicating with server", e);		
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error comunicating with server", "Error while uploading the template: missing comunication with server");	
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
			if(datasetException.isNoDocument()){
				logger.error("Document no more present");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error upload", "Dataset is no more present on server; you can do a new deploy");	
				sbdw.setNewDeployFromOld(true);
				return false;
			}
			if(noActiveServerException.isNoServer()){
				logger.error("No server is defined active");			
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "No server is defined active");	
				return false;
			}

			dialog.close();
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Deploy succesfull", "Deployed to the associated dataset "+datasetLabel+" succesfull");		
			logger.debug("Deployed to the associated document "+datasetLabel+" succesfull");		
			automatic = true;
		}
		else{
			automatic = false;
		}

		if(!automatic)
		{
			logger.debug("deploy a new Dataset: start wizard");		
			// init wizard
			sbdw.init(PlatformUI.getWorkbench(), sel);
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbdw);

			// Open the wizard dialog
			dialog.open();	
		}


		logger.debug("OUT");
		return automatic;

	}

	/**
	 *  TODO CHECK not to lose some configurations
	 * @param object
	 * @param query
	 * @return
	 */

	public static String adaptQueryToList(String query){
		String toReturn = null;
		toReturn = "{\"catalogue\": " +
		"{\"queries\": " +
		"[";
		toReturn+=query;
		toReturn+="], "+
		"	\"distinct\":false,	" +
		"	\"filters\":[],	" +
		"	\"expression\":{},	" +
		"	\"isNestedExpression\":false,	" +
		"	\"havings\":[],	" +
		"	\"subqueries\":[]}]},	"+
		"	\"version\":7}	";
		logger.debug("adapted query is "+toReturn);
		return toReturn;

	}

	/** get metaQuery from JSON Object
	 * 
	 * @param objSel
	 * @return
	 */
	public static String getMetaQuery(IFile objSel){

		logger.debug("IN");

		String queryStr = null;
		try{
			JSONObject obj = JSONReader.createJSONObject(objSel);
			//JSONObject queryMeta = obj.optJSONObject("queryMeta");
			queryStr = obj.optString("query");
			logger.debug("query is :"+queryStr);
		}
		catch (Exception e) {
			logger.error("error in reading JSON object", e);
		}
		return queryStr;
	}


}
