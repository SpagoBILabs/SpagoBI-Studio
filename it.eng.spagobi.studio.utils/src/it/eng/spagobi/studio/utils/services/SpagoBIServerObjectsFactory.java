/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.services;

import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDataSources;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDatasets;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDocuments;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerEngines;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerMaps;

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
