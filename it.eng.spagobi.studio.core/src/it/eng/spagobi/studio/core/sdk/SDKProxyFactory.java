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

import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
import it.eng.spagobi.sdk.proxy.MapsSDKServiceProxy;
import it.eng.spagobi.studio.core.bo.Server;
import it.eng.spagobi.studio.core.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.core.services.server.ServerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SDKProxyFactory {

	private static Logger logger = LoggerFactory.getLogger(SDKProxyFactory.class);


	Server server = null;

	public SDKProxyFactory(String projectname) throws NoActiveServerException {
		super();
		logger.debug("IN");
		server = new ServerHandler().getCurrentActiveServer(projectname);
		if(server == null){
			logger.error("active server not defined");
			throw new NoActiveServerException();
		}
		logger.debug("active server "+server.getName());
		logger.debug("OUT");
		
	}


	public DocumentsServiceProxy getDocumentsServiceProxy() {
		DocumentsServiceProxy proxy = new DocumentsServiceProxy(server.getUser(), server.getPassword());
		String serverUrl = server.getUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DocumentsService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public EnginesServiceProxy getEnginesServiceProxy() {
		EnginesServiceProxy proxy = new EnginesServiceProxy(server.getUser(), server.getPassword());
		String serverUrl = server.getUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/EnginesService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public DataSetsSDKServiceProxy getDataSetsSDKServiceProxy() {
		DataSetsSDKServiceProxy proxy = new DataSetsSDKServiceProxy(server.getUser(), server.getPassword());
		String serverUrl = server.getUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DataSetsSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public DataSourcesSDKServiceProxy getDataSourcesSDKServiceProxy() {

		DataSourcesSDKServiceProxy proxy = new DataSourcesSDKServiceProxy(server.getUser(), server.getPassword());
		String serverUrl = server.getUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DataSourcesSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public MapsSDKServiceProxy getMapsSDKServiceProxy() {
		MapsSDKServiceProxy proxy = new MapsSDKServiceProxy(server.getUser(), server.getPassword());
		String serverUrl = server.getUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/MapsSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}


	public Server getServer() {
		return server;
	}


	public void setServer(Server server) {
		this.server = server;
	}




	
	
	

}
