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
package it.eng.spagobi.studio.core.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
import it.eng.spagobi.sdk.proxy.MapsSDKServiceProxy;
import it.eng.spagobi.studio.core.exceptions.NoActiveServerException;

public class SDKProxyFactory {

	private static Logger logger = LoggerFactory.getLogger(SDKProxyFactory.class);


	SpagoBIServerConnectionDefinition connection = null;


	public SDKProxyFactory(String projectname) throws NoActiveServerException {
		super();
		connection = new SpagoBIServerConnectionDefinition(projectname);
		if(connection == null){
			logger.error("active server not defined");
			connection = SpagoBIServerConnectionDefinition.createErrorConnection();
		}
		else{
			logger.debug("active connection to "+connection.getServerUrl());
		}
	}


	public DocumentsServiceProxy getDocumentsServiceProxy() {
		DocumentsServiceProxy proxy = new DocumentsServiceProxy(connection.getUserName(), connection.getPassword());
		String serverUrl = connection.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DocumentsService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public EnginesServiceProxy getEnginesServiceProxy() {
		EnginesServiceProxy proxy = new EnginesServiceProxy(connection.getUserName(), connection.getPassword());
		String serverUrl = connection.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/EnginesService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public DataSetsSDKServiceProxy getDataSetsSDKServiceProxy() {
		DataSetsSDKServiceProxy proxy = new DataSetsSDKServiceProxy(connection.getUserName(), connection.getPassword());
		String serverUrl = connection.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DataSetsSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public DataSourcesSDKServiceProxy getDataSourcesSDKServiceProxy() {

		DataSourcesSDKServiceProxy proxy = new DataSourcesSDKServiceProxy(connection.getUserName(), connection.getPassword());
		String serverUrl = connection.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DataSourcesSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public MapsSDKServiceProxy getMapsSDKServiceProxy() {
		MapsSDKServiceProxy proxy = new MapsSDKServiceProxy(connection.getUserName(), connection.getPassword());
		String serverUrl = connection.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/MapsSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

}
