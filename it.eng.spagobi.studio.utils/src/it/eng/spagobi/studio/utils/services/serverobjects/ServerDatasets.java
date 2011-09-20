package it.eng.spagobi.studio.utils.services.serverobjects;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.sdk.exceptions.MissingParameterValue;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.server.services.api.ISpagoBIServerDatasetServiceProxy;
import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataSetParameter;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDocument;
import it.eng.spagobi.server.services.api.bo.ITemplate;
import it.eng.spagobi.server.services.api.exception.MissingParValueException;
import it.eng.spagobi.server.services.api.exception.NoServerException;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.DatasetParameter;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Template;
import it.eng.spagobi.studio.utils.services.ProxyHandler;
import it.eng.spagobi.studio.utils.services.ServerObjectsTranslator;

import java.rmi.RemoteException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDatasets implements ISpagoBIServerDatasetServiceProxy {

	private static Logger logger = LoggerFactory.getLogger(ServerDatasets.class);
	ProxyHandler proxyHandler = null;
	
	public Integer saveNewDocument(IDocument newDocument, ITemplate template, Integer functionalityId) throws RemoteException{
		Integer returnCode = null;
		SDKDocument sdkDocument = ServerObjectsTranslator.createSDKDocument(newDocument);
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDocumentsServiceProxy().saveNewDocument(sdkDocument, sdkTemplate, functionalityId);

		return returnCode;
	}

	public void uploadTemplate(Integer id, ITemplate template) throws RemoteException{
		SDKTemplate sdkTemplate = ServerObjectsTranslator.createSDKTemplate(template);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			proxyHandler.getDocumentsServiceProxy().uploadTemplate(id, sdkTemplate);

		return;
	}

	public IDataSet[] getDataSetList() throws RemoteException{
		IDataSet[] toReturn = null;
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




	public IDataSet getDataSet(Integer id) throws RemoteException{
		Dataset toReturn = null;
		SDKDataSet sdkDataset = null;
		if(proxyHandler.getDataSetsSDKServiceProxy() != null)
			sdkDataset = proxyHandler.getDataSetsSDKServiceProxy().getDataSet(id);
		if(sdkDataset != null){
			toReturn = new Dataset(sdkDataset);

		}
		return toReturn;
	}
	
	public Integer saveDataSet(IDataSet newDataset) throws RemoteException{
		Integer returnCode = null;
		SDKDataSet sdkDataSet = ServerObjectsTranslator.createSDKDataSet(newDataset);
		if(proxyHandler.getDocumentsServiceProxy() != null)
			returnCode = proxyHandler.getDataSetsSDKServiceProxy().saveDataset(sdkDataSet);
		return returnCode;
	}
	
	
	
	public String executeDataSet(String dataSetLabel, IDataSetParameter[] parameters) throws RemoteException{
		String returnCode = null;
		SDKDataSetParameter[] sdkParArray = null;
		if(parameters != null){
			sdkParArray = new SDKDataSetParameter[parameters.length];
			for (int i = 0; i < sdkParArray.length; i++) {
				IDataSetParameter dsP = parameters[i];
				sdkParArray[i] = ServerObjectsTranslator.createSDKDataSetParameter(dsP);
			}
		}
		
		if(proxyHandler.getDataSetsSDKServiceProxy() != null)
			returnCode = proxyHandler.getDataSetsSDKServiceProxy().executeDataSet(dataSetLabel, sdkParArray);
		return returnCode;
	}
	
	
	
	public IDataStoreMetadata getDataStoreMetadata(Integer datasetId) throws NoServerException, MissingParValueException{
		logger.debug("IN");
		SDKDataStoreMetadata sdkDataStoreMetadata=null;
		IDataStoreMetadata toReturn=null;
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
				throw new MissingParValueException();
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
	

	public Vector<IDataSet> getAllDatasets() throws NoServerException{
		logger.debug("IN");
		Vector<IDataSet> toReturn=new Vector<IDataSet>();

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
			IDataSet dataset=new Dataset(sdkDataSet);
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
