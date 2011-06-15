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
package it.eng.spagobi.studio.utils.services;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.sdk.exceptions.MissingParameterValue;
import it.eng.spagobi.sdk.maps.bo.SDKFeature;
import it.eng.spagobi.sdk.maps.bo.SDKMap;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.MapsSDKServiceProxy;
import it.eng.spagobi.studio.utils.bo.DataSource;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.GeoFeature;
import it.eng.spagobi.studio.utils.bo.GeoMap;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoServerException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDataSources;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDatasets;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDocuments;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerEngines;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerMaps;

import java.rmi.RemoteException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpagoBIServerObjectsFactory {

	private static Logger logger = LoggerFactory.getLogger(SpagoBIServerObjectsFactory.class);

	String projectName = null;

	ProxyHandler proxyHandler = null;

	public SpagoBIServerObjectsFactory(String projectName) throws NoActiveServerException {
		super();
		this.projectName = projectName;
		proxyHandler = new ProxyHandler(projectName);

	}
	
	public SpagoBIServerObjectsFactory(Server server) throws NoActiveServerException {
		super();
		this.projectName = "";
		proxyHandler = new ProxyHandler(server);

	}
	

	public String getServerName(){
		if(proxyHandler != null)
			return proxyHandler.getServerName();
		else return null;
	}


	public ServerDatasets getServerDatasets(){
		ServerDatasets ds = new ServerDatasets();
		ds.setProxyHandler(proxyHandler);
		return ds;
	}

	public ServerDataSources getServerDataSources(){
		ServerDataSources ds = new ServerDataSources();
		ds.setProxyHandler(proxyHandler);
		return ds;	
		}

	public ServerDocuments getServerDocuments(){
		ServerDocuments ds = new ServerDocuments();
		ds.setProxyHandler(proxyHandler);
		return ds;	
		}

	public ServerEngines getServerEngines(){
		ServerEngines ds = new ServerEngines();
		ds.setProxyHandler(proxyHandler);
		return ds;	
		}

	public ServerMaps getServerMaps(){
		ServerMaps ds = new ServerMaps();
		ds.setProxyHandler(proxyHandler);
		return ds;	
		}

















}
