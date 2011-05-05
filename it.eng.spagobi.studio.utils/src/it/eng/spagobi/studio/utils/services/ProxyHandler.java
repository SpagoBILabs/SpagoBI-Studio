package it.eng.spagobi.studio.utils.services;

import java.rmi.RemoteException;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.sdk.exceptions.NotAllowedOperationException;
import it.eng.spagobi.sdk.proxy.AbstractSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
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
TestConnectionServiceProxy testConnectionServiceProxy = null;

String serverName = null;


	public ProxyHandler(String projectName) throws NoActiveServerException {
		super();
		Server server = new ServerHandler().getCurrentActiveServer(projectName);
		serverName = server.getName();
		proxyFactory=new SDKProxyFactory(server);
	}




	public EnginesServiceProxy getEnginesServiceProxy() {
		return (enginesServiceProxy != null) ? 
				enginesServiceProxy : 
					proxyFactory.getEnginesServiceProxy();
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
