package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.sdk.maps.bo.SDKFeature;
import it.eng.spagobi.sdk.maps.bo.SDKMap;
import it.eng.spagobi.sdk.proxy.MapsSDKServiceProxy;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.bo.GeoFeature;
import it.eng.spagobi.studio.utils.bo.GeoMap;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoServerException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.ProxyHandler;
import it.eng.spagobi.studio.utils.services.ServerObjectsTranslator;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;

import java.rmi.RemoteException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMaps {

	private static Logger logger = LoggerFactory.getLogger(ServerMaps.class);
	ProxyHandler proxyHandler = null;
	

	public GeoFeature[] getFeaturesByMapId(Integer mapId) throws NoServerException{
		logger.debug("IN");
		GeoFeature[] toReturn=null;
		SDKFeature[] sdkFeatures=null;
		try{
			MapsSDKServiceProxy mapProxy = proxyHandler.getMapsServiceProxy();
			sdkFeatures =mapProxy.getMapFeatures(mapId);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve map informations", e);
			throw(new NoServerException(e));
		}	

		if(sdkFeatures!=null){
			toReturn=new GeoFeature[sdkFeatures.length];
			for (int i = 0; i < sdkFeatures.length; i++) {
				SDKFeature sdkFeature=sdkFeatures[i];
				GeoFeature geoFeature=new GeoFeature(sdkFeature);
				toReturn[i]=geoFeature;
			}
		}
		logger.debug("OUT");
		return toReturn;	
	}


	public GeoFeature[] getAllFeatures() throws NoServerException{
		logger.debug("IN");
		GeoFeature[] toReturn=null;
		SDKFeature[] sdkFeatures=null;
		try{
			MapsSDKServiceProxy mapProxy = proxyHandler.getMapsServiceProxy();
			sdkFeatures =mapProxy.getFeatures();
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve map informations", e);
			throw(new NoServerException(e));
		}	

		if(sdkFeatures!=null){
			toReturn=new GeoFeature[sdkFeatures.length];
			for (int i = 0; i < sdkFeatures.length; i++) {
				SDKFeature sdkFeature=sdkFeatures[i];
				GeoFeature geoFeature=new GeoFeature(sdkFeature);
				toReturn[i]=geoFeature;
			}
		}
		logger.debug("OUT");
		return toReturn;	
	}



	public GeoMap getGeoMapById(Integer geoId) throws NoServerException{
		logger.debug("IN");
		GeoMap toReturn=null;

		SDKMap sdkMap=null;
		try{
			MapsSDKServiceProxy mapServiceProxy = proxyHandler.getMapsServiceProxy();
			sdkMap=mapServiceProxy.getMapById(geoId);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve map informations", e);
			throw(new NoServerException(e));
		}	
		if(sdkMap!=null){
			toReturn=new GeoMap(sdkMap);

		}
		logger.debug("OUT");
		return toReturn;		

	}



	public Vector<GeoMap> getAllGeoMaps() throws NoServerException{
		logger.debug("IN");
		Vector<GeoMap> toReturn=new Vector<GeoMap>();

		SDKMap[] sdkMaps=null;
		try{
//			Server server = new ServerHandler().getCurrentActiveServer(projectName);
//			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
//			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			MapsSDKServiceProxy mapsServiceProxy=proxyHandler.getMapsServiceProxy();
			sdkMaps=mapsServiceProxy.getMaps();
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve map informations", e);
			throw(new NoServerException(e));
		}	

		for (int i = 0; i < sdkMaps.length; i++) {
			SDKMap sdkMap=sdkMaps[i];
			GeoMap geoMap=new GeoMap(sdkMap);
			if(geoMap!=null){
				toReturn.add(geoMap);
			}
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
