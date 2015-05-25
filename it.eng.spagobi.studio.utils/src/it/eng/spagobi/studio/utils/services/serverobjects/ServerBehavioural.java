/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.behavioural.bo.SDKAttribute;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.documents.bo.SDKSchema;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.exceptions.NotAllowedOperationException;
import it.eng.spagobi.server.services.api.bo.IDocument;
import it.eng.spagobi.server.services.api.bo.ITemplate;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.ProfileAttribute;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.services.ProxyHandler;
import it.eng.spagobi.studio.utils.services.ServerObjectsTranslator;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerBehavioural {

	private static Logger logger = LoggerFactory.getLogger(ServerBehavioural.class);
	ProxyHandler proxyHandler = null;
	
	
	public ProfileAttribute[] 	getAttributes(String role) throws RemoteException{
		ProfileAttribute[] toReturn = null;
		SDKAttribute[] attributes = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			attributes = proxyHandler.getBehaviouralServiceProxy().getAllAttributes(role);
		if(attributes != null){
			toReturn = new ProfileAttribute[attributes.length];
			for (int i = 0; i < attributes.length; i++) {
				toReturn[i] = new ProfileAttribute(attributes[i]);
			}
		}
		return toReturn;
	}
	
	
	public ProxyHandler getProxyHandler() {
		return proxyHandler;
	}

	public void setProxyHandler(ProxyHandler proxyHandler) {
		this.proxyHandler = proxyHandler;
	}

	
	
}
