package it.eng.spagobi.studio.utils.services;

import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
import it.eng.spagobi.sdk.proxy.MapsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;

public class ProxyHandler {

	SDKProxyFactory proxyFactory = null;
	EnginesServiceProxy enginesServiceProxy =null;
	DataSetsSDKServiceProxy dataSetsSDKServiceProxy = null;
	DataSourcesSDKServiceProxy dataSourcesSDKServiceProxy = null;
	DocumentsServiceProxy documentsServiceProxy = null;
	MapsSDKServiceProxy mapsServiceProxy = null;
	TestConnectionServiceProxy testConnectionServiceProxy = null;

	String serverName = null;


	public ProxyHandler(String projectName) throws NoActiveServerException {
		super();
		Server server = new ServerHandler().getCurrentActiveServer(projectName);
		if(server == null) throw new NoActiveServerException();
		serverName = server.getName();
		proxyFactory=new SDKProxyFactory(server);
	}

	// caled by ODA project
	public ProxyHandler(Server server) throws NoActiveServerException {
		super();
		//Server server = new ServerHandler().getCurrentActiveServer(projectName);
		if(server == null) throw new NoActiveServerException();
		serverName = server.getName();
		proxyFactory=new SDKProxyFactory(server);
	}




	public EnginesServiceProxy getEnginesServiceProxy() {
		return (enginesServiceProxy != null) ? 
				enginesServiceProxy : 
					proxyFactory.getEnginesServiceProxy();
	}


	public MapsSDKServiceProxy getMapsServiceProxy() {
		return (mapsServiceProxy != null) ? 
				mapsServiceProxy : 
					proxyFactory.getMapsSDKServiceProxy();
	}



	public DataSetsSDKServiceProxy getDataSetsSDKServiceProxy() {
		return (dataSetsSDKServiceProxy != null) ? 
				dataSetsSDKServiceProxy : 
					proxyFactory.getDataSetsSDKServiceProxy();
	}




	public DataSourcesSDKServiceProxy getDataSourcesSDKServiceProxy() {
		return (dataSourcesSDKServiceProxy != null) ? 
				dataSourcesSDKServiceProxy : 
					proxyFactory.getDataSourcesSDKServiceProxy();
	}





	public DocumentsServiceProxy getDocumentsServiceProxy() {
		return (documentsServiceProxy != null) ? 
				documentsServiceProxy : 
					proxyFactory.getDocumentsServiceProxy();
	}



	public String getServerName() {
		return serverName;
	}



	public void setServerName(String serverName) {
		this.serverName = serverName;
	}










}
