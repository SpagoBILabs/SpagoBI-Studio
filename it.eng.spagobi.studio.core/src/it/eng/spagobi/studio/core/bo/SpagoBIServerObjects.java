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
package it.eng.spagobi.studio.core.bo;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.exceptions.MissingParameterValue;
import it.eng.spagobi.sdk.maps.bo.SDKFeature;
import it.eng.spagobi.sdk.maps.bo.SDKMap;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.MapsSDKServiceProxy;
import it.eng.spagobi.studio.core.exceptions.NoServerException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.properties.PropertyPage;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;


public class SpagoBIServerObjects {

	private static Logger logger = LoggerFactory.getLogger(SpagoBIServerObjects.class);

	String projectName = null;
	
	

	public SpagoBIServerObjects(String projectName) {
		super();
		this.projectName = projectName;
	}


	public DataStoreMetadata getDataStoreMetadata(Integer datasetId) throws NoServerException, MissingParameterValue{
		logger.debug("IN");
		SDKDataStoreMetadata sdkDataStoreMetadata=null;
		DataStoreMetadata toReturn=null;
		try{
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			DataSetsSDKServiceProxy datasetsServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
			SDKDataSet sdkDataSet=datasetsServiceProxy.getDataSet(datasetId);

			sdkDataStoreMetadata=datasetsServiceProxy.getDataStoreMetadata(sdkDataSet);
		}
		catch (Exception e) {
			if(e instanceof MissingParameterValue){
				throw (MissingParameterValue)e;
			}
			else{
				SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve dataset metadata informations", e);
				throw(new NoServerException(e));
			}
		}
		if(sdkDataStoreMetadata!=null){
			toReturn=new DataStoreMetadata(sdkDataStoreMetadata);
		}
		logger.debug("OUT");

		return toReturn;
	}


	public DataSource getDataSourceById(Integer dsId) throws NoServerException{
		logger.debug("IN");
		DataSource toReturn=null;
		SDKDataSource sdkDS=null;
		try{
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			DataSourcesSDKServiceProxy dsServiceProxy=proxyFactory.getDataSourcesSDKServiceProxy();
			sdkDS = dsServiceProxy.getDataSource(dsId);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve map informations", e);
			throw(new NoServerException(e));
		}	
		if(sdkDS!=null){
			toReturn=new DataSource(sdkDS);
		}
		logger.debug("OUT");
		return toReturn;	
	}


	public GeoFeature[] getFeaturesByMapId(Integer mapId) throws NoServerException{
		logger.debug("IN");
		GeoFeature[] toReturn=null;
		SDKFeature[] sdkFeatures=null;
		try{
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkFeatures =mapsServiceProxy.getMapFeatures(mapId);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve map informations", e);
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
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkFeatures =mapsServiceProxy.getFeatures();
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve map informations", e);
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
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkMap=mapsServiceProxy.getMapById(geoId);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve map informations", e);
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
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkMaps=mapsServiceProxy.getMaps();
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve map informations", e);
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


	public Vector<Dataset> getAllDatasets() throws NoServerException{
		logger.debug("IN");
		Vector<Dataset> toReturn=new Vector<Dataset>();

		SDKDataSet[] sdkDataSets=null;
		try{
			SDKProxyFactory proxyFactory=new SDKProxyFactory(projectName);
			DataSetsSDKServiceProxy datasetsServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
			sdkDataSets=datasetsServiceProxy.getDataSets();
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve dataset informations", e);
			throw(new NoServerException(e));
		}


		for (int i = 0; i < sdkDataSets.length; i++) {
			SDKDataSet sdkDataSet=sdkDataSets[i];
			Dataset dataset=new Dataset(sdkDataSet);
			if(dataset!=null){
				toReturn.add(dataset);
			}
		}
		logger.debug("OUT");
		return toReturn;		
	}


}
