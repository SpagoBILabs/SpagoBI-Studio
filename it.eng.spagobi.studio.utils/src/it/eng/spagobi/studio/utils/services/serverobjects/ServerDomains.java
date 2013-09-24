/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.domains.bo.SDKDomain;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.studio.utils.bo.Domain;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.services.ProxyHandler;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDomains {

	private static Logger logger = LoggerFactory.getLogger(ServerDomains.class);
	ProxyHandler proxyHandler = null;
	
	
	public Domain[] getDomainsListByDomainCd(String domainCd) throws RemoteException{
		Domain[] toReturn = null;
		SDKDomain[] sdkDomains = null;
		if(proxyHandler.getDomainsServiceProxy()!= null)
			sdkDomains = proxyHandler.getDomainsServiceProxy().getDomainsListByDomainCd(domainCd);
		if(sdkDomains != null){
			toReturn = new Domain[sdkDomains.length];
			for (int i = 0; i < sdkDomains.length; i++) {
				SDKDomain sdkDomain =  sdkDomains[i];
				toReturn[i] = new Domain(sdkDomain);
			}
		}
		return toReturn;
	}


	public Engine getEngine(Integer id) throws RemoteException{
		Engine toReturn = null;
		SDKEngine sdkEngine = null;
		if(proxyHandler.getEnginesServiceProxy()!= null)
			sdkEngine = proxyHandler.getEnginesServiceProxy().getEngine(id);
		toReturn = new Engine(sdkEngine);

		return toReturn;
	}


	public ProxyHandler getProxyHandler() {
		return proxyHandler;
	}


	public void setProxyHandler(ProxyHandler proxyHandler) {
		this.proxyHandler = proxyHandler;
	}


	
}
