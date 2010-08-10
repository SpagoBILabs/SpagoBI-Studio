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

public class SDKProxyFactory {

	public static DocumentsServiceProxy getDocumentsServiceProxy() {
		SpagoBIServerConnectionDefinition def = new SpagoBIServerConnectionDefinition();
		DocumentsServiceProxy proxy = new DocumentsServiceProxy(def.getUserName(), def.getPassword());
		String serverUrl = def.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DocumentsService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}
	
	public static EnginesServiceProxy getEnginesServiceProxy() {
		SpagoBIServerConnectionDefinition def = new SpagoBIServerConnectionDefinition();
		EnginesServiceProxy proxy = new EnginesServiceProxy(def.getUserName(), def.getPassword());
		String serverUrl = def.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/EnginesService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}
	
	public static DataSetsSDKServiceProxy getDataSetsSDKServiceProxy() {
		SpagoBIServerConnectionDefinition def = new SpagoBIServerConnectionDefinition();
		DataSetsSDKServiceProxy proxy = new DataSetsSDKServiceProxy(def.getUserName(), def.getPassword());
		String serverUrl = def.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DataSetsSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public static DataSourcesSDKServiceProxy getDataSourcesSDKServiceProxy() {
		SpagoBIServerConnectionDefinition def = new SpagoBIServerConnectionDefinition();
		DataSourcesSDKServiceProxy proxy = new DataSourcesSDKServiceProxy(def.getUserName(), def.getPassword());
		String serverUrl = def.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/DataSourcesSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}

	public static MapsSDKServiceProxy getMapsSDKServiceProxy() {
		SpagoBIServerConnectionDefinition def = new SpagoBIServerConnectionDefinition();
		MapsSDKServiceProxy proxy = new MapsSDKServiceProxy(def.getUserName(), def.getPassword());
		String serverUrl = def.getServerUrl();
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		proxy.setEndpoint(serverUrl + "sdk/MapsSDKService");
		new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		return proxy;
	}
	
}
