/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.documents.bo.SDKSchema;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.exceptions.NotAllowedOperationException;
import it.eng.spagobi.sdk.importexport.bo.SDKFile;
import it.eng.spagobi.server.services.api.bo.IDocument;
import it.eng.spagobi.server.services.api.bo.ITemplate;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.services.ProxyHandler;
import it.eng.spagobi.studio.utils.services.ServerObjectsTranslator;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDocuments {

	private static Logger logger = LoggerFactory.getLogger(ServerDocuments.class);
	ProxyHandler proxyHandler = null;
	
	
	public Integer saveNewDocument(IDocument newDocument, ITemplate template, Integer functionalityId) throws RemoteException{
		Integer returnCode = null;
		SDKDocument sdkDocument = ServerObjectsTranslator.createSDKDocument(newDocument);
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDocumentsServiceProxy().saveNewDocument(sdkDocument, sdkTemplate, functionalityId);

		return returnCode;
	}

	public void uploadTemplate(Integer id, ITemplate template) throws RemoteException{
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			proxyHandler.getDocumentsServiceProxy().uploadTemplate(id, sdkTemplate);

		return;
	}
	
	public void uploadMondrianSchema(IDocument newDocument, ITemplate template, String dataSourceLabel ) throws  RemoteException{
		SDKSchema sdkSchema = ServerObjectsTranslator.createSDKSchema(newDocument, template,dataSourceLabel );
		if(proxyHandler.getDocumentsServiceProxy() != null)

			try {
				proxyHandler.getDocumentsServiceProxy().uploadMondrianSchema(sdkSchema);			
			} catch (NotAllowedOperationException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "Error during file deploy: NotAllowedOperationException");		
				e.printStackTrace();
			} 

	
		
	}
	
	public void uploadDatamartTemplate(ITemplate template, ITemplate calculatedFields, String dataSourceLabel) throws RemoteException{
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		SDKTemplate sdkCalculatedFields = ServerObjectsTranslator.createSDKTemplate(calculatedFields);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			proxyHandler.getDocumentsServiceProxy().uploadDatamartTemplate(sdkTemplate, sdkCalculatedFields, dataSourceLabel);
		return;
	}

	public void uploadDatamartModel(ITemplate template) throws RemoteException{
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			proxyHandler.getDocumentsServiceProxy().uploadDatamartModel(sdkTemplate);
		return;
	}

	public Functionality getDocumentsAsTree(String str) throws RemoteException{
		Functionality toReturn = null;
		SDKFunctionality sdkFunctionality = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkFunctionality = proxyHandler.getDocumentsServiceProxy().getDocumentsAsTree(str);
		if(sdkFunctionality != null){
			toReturn = new Functionality(sdkFunctionality);
		}
		return toReturn;
	}

	public Document getDocumentById(Integer id) throws RemoteException{
		Document toReturn = null;
		SDKDocument sdkDocument = null;
		if(proxyHandler.getDocumentsServiceProxy() != null)
			sdkDocument = proxyHandler.getDocumentsServiceProxy().getDocumentById(id);
		if(sdkDocument != null){
			toReturn = new Document(sdkDocument);

		}
		return toReturn;
	}


	public Template downloadTemplate(Integer id) throws RemoteException{
		Template toReturn = null;
		SDKTemplate sdkTemplate = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkTemplate = proxyHandler.getDocumentsServiceProxy().downloadTemplate(id);
		if(sdkTemplate != null){
			toReturn = new Template(sdkTemplate);
		}
		return toReturn;
	}

	public Template downloadDatamartFile(String folderName, String fileName) throws RemoteException{
		Template toReturn = null;
		SDKTemplate sdkTemplate = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkTemplate = proxyHandler.getDocumentsServiceProxy().downloadDatamartFile(folderName, fileName);
		if(sdkTemplate != null){
			toReturn = new Template(sdkTemplate);
		}
		return toReturn;
	}
	
	public Template downloadDatamartModelFiles(String folderName, String fileDatamartName , String fileModelName) throws RemoteException{
		Template toReturn = null;
		SDKTemplate sdkTemplate = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkTemplate = proxyHandler.getDocumentsServiceProxy().downloadDatamartModelFiles(folderName, fileDatamartName, fileModelName);
		if(sdkTemplate != null){
			toReturn = new Template(sdkTemplate);
		}
		return toReturn;
	}

	public Document getDocumentByLabel(String label) throws RemoteException{
		Document toReturn = null;
		SDKDocument sdkDocument = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkDocument = proxyHandler.getDocumentsServiceProxy().getDocumentByLabel(label);
		if(sdkDocument != null){
			toReturn = new Document(sdkDocument);
		}
		return toReturn;
	}

	public String[] getCorrectRolesForExecution(Integer id) throws RemoteException{
		String[] toReturn = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			toReturn = proxyHandler.getDocumentsServiceProxy().getCorrectRolesForExecution(id);
		return toReturn;
	}


	public DocumentParameter[] 	getDocumentParameters(Integer id, String role) throws RemoteException{
		DocumentParameter[] toReturn = null;
		SDKDocumentParameter[] sdkDocumentParameters = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkDocumentParameters = proxyHandler.getDocumentsServiceProxy().getDocumentParameters(id, role);
		if(sdkDocumentParameters != null){
			toReturn = new DocumentParameter[sdkDocumentParameters.length];
			for (int i = 0; i < sdkDocumentParameters.length; i++) {
				toReturn[i] = new DocumentParameter(sdkDocumentParameters[i]);
			}
		}
		return toReturn;
	}

	public HashMap<String, String> getAllDatamartModels()  throws RemoteException{
		HashMap<String,String> toReturn = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			toReturn = proxyHandler.getDocumentsServiceProxy().getAllDatamartModels();

		return toReturn;
	}
	
	public ProxyHandler getProxyHandler() {
		return proxyHandler;
	}

	public void setProxyHandler(ProxyHandler proxyHandler) {
		this.proxyHandler = proxyHandler;
	}

	
	
}
