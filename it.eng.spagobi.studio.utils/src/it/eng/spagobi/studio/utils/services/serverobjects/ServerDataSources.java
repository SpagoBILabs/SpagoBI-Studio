/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.studio.utils.bo.DataSource;
import it.eng.spagobi.studio.utils.exceptions.NoServerException;
import it.eng.spagobi.studio.utils.services.ProxyHandler;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDataSources {

	private static Logger logger = LoggerFactory.getLogger(ServerDataSources.class);
	ProxyHandler proxyHandler = null;

	public DataSource[] getDataSourceList() throws RemoteException{
		DataSource[] toReturn = null;
		SDKDataSource[] sdkDataSources = null;

		if(proxyHandler.getDataSourcesSDKServiceProxy() != null)
			sdkDataSources = proxyHandler.getDataSourcesSDKServiceProxy().getDataSources();
		if(sdkDataSources != null){
			toReturn = new DataSource[sdkDataSources.length];
			for (int i = 0; i < sdkDataSources.length; i++) {
				SDKDataSource sdkDs =  sdkDataSources[i];
				toReturn[i] = new DataSource(sdkDs);
			}
		}
		return toReturn;
	}

	public DataSource getDataSource(Integer id) throws RemoteException{
		DataSource toReturn = null;
		SDKDataSource sdkDataSource = null;
		if(proxyHandler.getDataSetsSDKServiceProxy() != null)
			sdkDataSource = proxyHandler.getDataSourcesSDKServiceProxy().getDataSource(id);
		if(sdkDataSource!= null){
			toReturn = new DataSource(sdkDataSource);

		}
		return toReturn;
	}

	public DataSource getDataSourceById(Integer dsId) throws NoServerException{
		logger.debug("IN");
		DataSource toReturn=null;
		SDKDataSource sdkDS=null;
		
		try{
			DataSourcesSDKServiceProxy dsServiceProxy = proxyHandler.getDataSourcesSDKServiceProxy();
			sdkDS = dsServiceProxy.getDataSource(dsId);
			
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve map informations", e);
			throw(new NoServerException(e));
		}	
		if(sdkDS!=null){
			toReturn=new DataSource(sdkDS);
		}
		logger.debug("OUT");
		return toReturn;	
	}

	public ProxyHandler getProxyHandler() {
		return proxyHandler;
	}

	public void setProxyHandler(ProxyHandler proxyHandler) {
		this.proxyHandler = proxyHandler;
	}
	
}
