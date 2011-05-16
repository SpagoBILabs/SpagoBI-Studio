package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.services.ProxyHandler;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerEngines {

	private static Logger logger = LoggerFactory.getLogger(ServerEngines.class);
	ProxyHandler proxyHandler = null;
	
	
	public Engine[] getEnginesList() throws RemoteException{
		Engine[] toReturn = null;
		SDKEngine[] sdkEngines = null;
		if(proxyHandler.getEnginesServiceProxy()!= null)
			sdkEngines = proxyHandler.getEnginesServiceProxy().getEngines();
		if(sdkEngines != null){
			toReturn = new Engine[sdkEngines.length];
			for (int i = 0; i < sdkEngines.length; i++) {
				SDKEngine sdkEng =  sdkEngines[i];
				toReturn[i] = new Engine(sdkEng);
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
