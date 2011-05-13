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

import java.rmi.RemoteException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpagoBIServerObjects {

	private static Logger logger = LoggerFactory.getLogger(SpagoBIServerObjects.class);

	String projectName = null;

	ProxyHandler proxyHandler = null;

	public SpagoBIServerObjects(String projectName) throws NoActiveServerException {
		super();
		this.projectName = projectName;
		proxyHandler = new ProxyHandler(projectName);

	}

	public String getServerName(){
		if(proxyHandler != null)
			return proxyHandler.getServerName();
		else return null;
	}


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


	public Dataset[] getDataSetList() throws RemoteException{
		Dataset[] toReturn = null;
		SDKDataSet[] sdkDatasets = null;
		if(proxyHandler.getDataSetsSDKServiceProxy() != null)
			sdkDatasets = proxyHandler.getDataSetsSDKServiceProxy().getDataSets();
		if(sdkDatasets != null){
			toReturn = new Dataset[sdkDatasets.length];
			for (int i = 0; i < sdkDatasets.length; i++) {
				SDKDataSet sdkDs =  sdkDatasets[i];
				toReturn[i] = new Dataset(sdkDs);
			}
		}
		return toReturn;
	}


	public Integer saveNewDocument(Document newDocument, Template template, Integer functionalityId) throws RemoteException{
		Integer returnCode = null;
		SDKDocument sdkDocument = ServerObjectsComparator.createSDKDocument(newDocument);
		SDKTemplate sdkTemplate = ServerObjectsComparator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDocumentsServiceProxy().saveNewDocument(sdkDocument, sdkTemplate, functionalityId);

		return returnCode;
	}

	public void uploadTemplate(Integer id, Template template) throws RemoteException{
		SDKTemplate sdkTemplate = ServerObjectsComparator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			proxyHandler.getDocumentsServiceProxy().uploadTemplate(id, sdkTemplate);

		return;
	}


	public Dataset getDataSet(Integer id) throws RemoteException{
		Dataset toReturn = null;
		SDKDataSet sdkDataset = null;
		if(proxyHandler.getDataSetsSDKServiceProxy() != null)
			sdkDataset = proxyHandler.getDataSetsSDKServiceProxy().getDataSet(id);
		if(sdkDataset != null){
			toReturn = new Dataset(sdkDataset);

		}
		return toReturn;
	}
	
	public Integer saveDataSet(Dataset newDataset) throws RemoteException{
		Integer returnCode = null;
		SDKDataSet sdkDataSet = ServerObjectsComparator.createSDKDataSet(newDataset);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDataSetsSDKServiceProxy().saveDataset(sdkDataSet);
		return returnCode;
	}
	
	

	public DataSource[] getDataSourceList() throws RemoteException{
		DataSource[] toReturn = null;
		SDKDataSource[] sdkDataSources = null;

		if(proxyHandler.getDataSourcesSDKServiceProxy() != null)
			sdkDataSources = proxyHandler.getDataSourcesSDKServiceProxy().getDataSources();
		if(sdkDataSources != null){
			toReturn = new DataSource[sdkDataSources.length];
			for (int i = 0; i < sdkDataSources.length; i++) {
				SDKDataSource sdkDs =  sdkDataSources[i];
				toReturn[i] = new DataSource(sdkDs);
			}
		}
		return toReturn;
	}

	public DataSource getDataSource(Integer id) throws RemoteException{
		DataSource toReturn = null;
		SDKDataSource sdkDataSource = null;
		if(proxyHandler.getDataSetsSDKServiceProxy() != null)
			sdkDataSource = proxyHandler.getDataSourcesSDKServiceProxy().getDataSource(id);
		if(sdkDataSource!= null){
			toReturn = new DataSource(sdkDataSource);

		}
		return toReturn;
	}

	public Functionality getDocumentsAsTree(String str) throws RemoteException{
		Functionality toReturn = null;
		SDKFunctionality sdkFunctionality = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkFunctionality = proxyHandler.getDocumentsServiceProxy().getDocumentsAsTree(str);
		if(sdkFunctionality != null){
			toReturn = new Functionality(sdkFunctionality);
		}
		return toReturn;
	}

	public Document getDocumentById(Integer id) throws RemoteException{
		Document toReturn = null;
		SDKDocument sdkDocument = null;
		if(proxyHandler.getDocumentsServiceProxy() != null)
			sdkDocument = proxyHandler.getDocumentsServiceProxy().getDocumentById(id);
		if(sdkDocument != null){
			toReturn = new Document(sdkDocument);

		}
		return toReturn;
	}


	public Template downloadTemplate(Integer id) throws RemoteException{
		Template toReturn = null;
		SDKTemplate sdkTemplate = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkTemplate = proxyHandler.getDocumentsServiceProxy().downloadTemplate(id);
		if(sdkTemplate != null){
			toReturn = new Template(sdkTemplate);
		}
		return toReturn;
	}


	public Document getDocumentByLabel(String label) throws RemoteException{
		Document toReturn = null;
		SDKDocument sdkDocument = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkDocument = proxyHandler.getDocumentsServiceProxy().getDocumentByLabel(label);
		if(sdkDocument != null){
			toReturn = new Document(sdkDocument);
		}
		return toReturn;
	}

	public String[] getCorrectRolesForExecution(Integer id) throws RemoteException{
		String[] toReturn = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			toReturn = proxyHandler.getDocumentsServiceProxy().getCorrectRolesForExecution(id);
		return toReturn;
	}


	public DocumentParameter[] 	getDocumentParameters(Integer id, String role) throws RemoteException{
		DocumentParameter[] toReturn = null;
		SDKDocumentParameter[] sdkDocumentParameters = null;

		if(proxyHandler.getDocumentsServiceProxy()!= null)
			sdkDocumentParameters = proxyHandler.getDocumentsServiceProxy().getDocumentParameters(id, role);
		if(sdkDocumentParameters != null){
			toReturn = new DocumentParameter[sdkDocumentParameters.length];
			for (int i = 0; i < sdkDocumentParameters.length; i++) {
				toReturn[i] = new DocumentParameter(sdkDocumentParameters[i]);
			}
		}
		return toReturn;
	}




	public DataStoreMetadata getDataStoreMetadata(Integer datasetId) throws NoServerException, MissingParameterValue{
		logger.debug("IN");
		SDKDataStoreMetadata sdkDataStoreMetadata=null;
		DataStoreMetadata toReturn=null;
		try{
			Server server = new ServerHandler().getCurrentActiveServer(projectName);			
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			DataSetsSDKServiceProxy datasetsServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
			SDKDataSet sdkDataSet=datasetsServiceProxy.getDataSet(datasetId);

			sdkDataStoreMetadata=datasetsServiceProxy.getDataStoreMetadata(sdkDataSet);
		}
		catch (Exception e) {
			if(e instanceof MissingParameterValue){
				throw (MissingParameterValue)e;
			}
			else{
				logger.error("No comunication with SpagoBI server, could not retrieve dataset metadata informations", e);
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
			Server server = new ServerHandler().getCurrentActiveServer(projectName);
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			DataSourcesSDKServiceProxy dsServiceProxy=proxyFactory.getDataSourcesSDKServiceProxy();
			sdkDS = dsServiceProxy.getDataSource(dsId);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve map informations", e);
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
			Server server = new ServerHandler().getCurrentActiveServer(projectName);
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkFeatures =mapsServiceProxy.getMapFeatures(mapId);
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
			Server server = new ServerHandler().getCurrentActiveServer(projectName);
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkFeatures =mapsServiceProxy.getFeatures();
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
			Server server = new ServerHandler().getCurrentActiveServer(projectName);
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
			sdkMap=mapsServiceProxy.getMapById(geoId);
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
			Server server = new ServerHandler().getCurrentActiveServer(projectName);
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			MapsSDKServiceProxy mapsServiceProxy=proxyFactory.getMapsSDKServiceProxy();
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


	public Vector<Dataset> getAllDatasets() throws NoServerException{
		logger.debug("IN");
		Vector<Dataset> toReturn=new Vector<Dataset>();

		SDKDataSet[] sdkDataSets=null;
		try{
			Server server = new ServerHandler().getCurrentActiveServer(projectName);
			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
			DataSetsSDKServiceProxy datasetsServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
			sdkDataSets=datasetsServiceProxy.getDataSets();
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve dataset informations", e);
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
