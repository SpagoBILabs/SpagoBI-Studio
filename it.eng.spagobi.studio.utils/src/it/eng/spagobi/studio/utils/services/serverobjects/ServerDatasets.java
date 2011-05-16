package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.exceptions.MissingParameterValue;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.exceptions.NoServerException;
import it.eng.spagobi.studio.utils.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.utils.services.ProxyHandler;
import it.eng.spagobi.studio.utils.services.ServerObjectsTranslator;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;

import java.rmi.RemoteException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDatasets {

	private static Logger logger = LoggerFactory.getLogger(ServerDatasets.class);
	ProxyHandler proxyHandler = null;
	
	public Integer saveNewDocument(Document newDocument, Template template, Integer functionalityId) throws RemoteException{
		Integer returnCode = null;
		SDKDocument sdkDocument = ServerObjectsTranslator.createSDKDocument(newDocument);
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDocumentsServiceProxy().saveNewDocument(sdkDocument, sdkTemplate, functionalityId);

		return returnCode;
	}

	public void uploadTemplate(Integer id, Template template) throws RemoteException{
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			proxyHandler.getDocumentsServiceProxy().uploadTemplate(id, sdkTemplate);

		return;
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
		SDKDataSet sdkDataSet = ServerObjectsTranslator.createSDKDataSet(newDataset);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDataSetsSDKServiceProxy().saveDataset(sdkDataSet);
		return returnCode;
	}
	
	public DataStoreMetadata getDataStoreMetadata(Integer datasetId) throws NoServerException, MissingParameterValue{
		logger.debug("IN");
		SDKDataStoreMetadata sdkDataStoreMetadata=null;
		DataStoreMetadata toReturn=null;
		try{
//			Server server = new ServerHandler().getCurrentActiveServer(projectName);			
//			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
//			DataSetsSDKServiceProxy datasetsServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
		
			DataSetsSDKServiceProxy proxy = proxyHandler.getDataSetsSDKServiceProxy();		
			SDKDataSet sdkDataSet=proxy.getDataSet(datasetId);

			sdkDataStoreMetadata=proxy.getDataStoreMetadata(sdkDataSet);
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
	

	public Vector<Dataset> getAllDatasets() throws NoServerException{
		logger.debug("IN");
		Vector<Dataset> toReturn=new Vector<Dataset>();

		SDKDataSet[] sdkDataSets=null;
		try{
//			Server server = new ServerHandler().getCurrentActiveServer(projectName);
//			SDKProxyFactory proxyFactory=new SDKProxyFactory(server);
//			DataSetsSDKServiceProxy datasetsServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();

			DataSetsSDKServiceProxy proxy = proxyHandler.getDataSetsSDKServiceProxy();
			sdkDataSets=proxy.getDataSets();
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

	public ProxyHandler getProxyHandler() {
		return proxyHandler;
	}

	public void setProxyHandler(ProxyHandler proxyHandler) {
		this.proxyHandler = proxyHandler;
	}
	
	
	
}
