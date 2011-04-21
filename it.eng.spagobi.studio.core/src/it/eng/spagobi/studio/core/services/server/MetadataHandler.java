package it.eng.spagobi.studio.core.services.server;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
import it.eng.spagobi.studio.core.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.core.exceptions.NoDocumentException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.core.util.BiObjectUtilities;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataHandler {


	private static Logger logger = LoggerFactory.getLogger(MetadataHandler.class);



	/**
	 *  refresh metadata of the given file 
	 * @param file
	 * @param noDocumentException: this is passed for use insieed a monitor
	 * @throws Exception
	 */

	public void refreshMetadata(IFile file, NoDocumentException noDocumentException, NoActiveServerException noActiveServerException) throws Exception{
		logger.debug("IN");
		String documentId=null;
		String projectname = file.getProject().getName();
		// Recover document
		SDKDocument document=null;

		SDKProxyFactory proxyFactory= null;
		try{	
			proxyFactory=new SDKProxyFactory(projectname);
		}
		catch (NoActiveServerException e) {
			noActiveServerException.setNoServer(true);
			return;
		}

		try{
			documentId=file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);


			DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy();
			document=docServiceProxy.getDocumentById(Integer.valueOf(documentId));
		}
		catch (Exception e) {
			logger.error("Could not recover document Id",e);		
			throw e;	
		}

		if(document==null){
			noDocumentException.setNoDocument(true);
			return;
		}

		// Recover DataSource

		Integer dataSourceId=document.getDataSourceId();
		SDKDataSource dataSource=null;
		if(dataSourceId!=null){
			try{
				DataSourcesSDKServiceProxy dataSourceServiceProxy=proxyFactory.getDataSourcesSDKServiceProxy();
				dataSource=dataSourceServiceProxy.getDataSource(Integer.valueOf(dataSourceId));
			}
			catch (Exception e) {
				SpagoBILogger.warningLog("Could not recover data source",e);		
			}
		}


		// Recover DataSet

		Integer dataSetId=document.getDataSetId();
		SDKDataSet dataSet=null;
		if(dataSetId!=null){
			try{

				DataSetsSDKServiceProxy dataSetServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
				dataSet=dataSetServiceProxy.getDataSet(Integer.valueOf(dataSetId));
			}
			catch (Exception e) {
				SpagoBILogger.warningLog("Could not recover data set",e);		
			}
		}


		// Recover Engine

		Integer engineId=document.getEngineId();
		SDKEngine engine=null;
		if(engineId!=null){
			try{
				EnginesServiceProxy engineServiceProxy=proxyFactory.getEnginesServiceProxy();
				engine=engineServiceProxy.getEngine(Integer.valueOf(engineId));
			}
			catch (Exception e) {
				SpagoBILogger.warningLog("Could not recover engine",e);		
			}
		}

		String[] roles=null;
		try{
			DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy(); 		
			roles=docServiceProxy.getCorrectRolesForExecution(document.getId());
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve roles for execution", e);
		}			
		if(roles==null || roles.length==0){
			logger.error("No roles for execution found");
		}


		SDKDocumentParameter[] parameters=null;
		try{
			DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy(); 		
			parameters=docServiceProxy.getDocumentParameters(document.getId(), roles[0]);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not retrieve document parameters", e);
		}			

		// firstly I have to call delete on all metadata in order to refresh!
		BiObjectUtilities.erasePersistentProperties(file);

		// Reload Documents Metadata
		if(document!=null){
			try{
				file.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID, document.getId().toString());
				file.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL, document.getLabel());
				file.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_NAME, document.getName()!=null ? document.getName() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_DESCRIPTION, document.getDescription()!=null ? document.getDescription() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_TYPE, document.getType()!=null ? document.getType() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_STATE, document.getState()!=null ? document.getState() : "");
			}
			catch (Exception e) {
				logger.error("Error while refreshing meta data",e);		
			}
		}
		// Reload Engine Metadata
		if(engine!=null){
			try{
				file.setPersistentProperty(SpagoBIStudioConstants.ENGINE_ID, engine.getId().toString());
				file.setPersistentProperty(SpagoBIStudioConstants.ENGINE_LABEL, engine.getLabel());
				file.setPersistentProperty(SpagoBIStudioConstants.ENGINE_NAME, engine.getName()!=null ? engine.getName() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.ENGINE_DESCRIPTION, engine.getDescription()!=null ? engine.getDescription() : "");
			}
			catch (Exception e) {
				logger.error("Error while refreshing engine meta data",e);		
			}
		}

		// Reload dataSet Metadata
		if(dataSet!=null){
			try{
				file.setPersistentProperty(SpagoBIStudioConstants.DATASET_ID, dataSet.getId().toString());
				file.setPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL, dataSet.getLabel());
				file.setPersistentProperty(SpagoBIStudioConstants.DATASET_NAME, dataSet.getName()!=null ? dataSet.getName() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.DATASET_DESCRIPTION, dataSet.getDescription()!=null ? dataSet.getDescription() : "");
			}
			catch (Exception e) {
				logger.error("Error while refreshing dataset meta data",e);		
			}
		}
		// Reload dataSource Metadata
		if(dataSource!=null){
			try{
				file.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_ID, dataSource.getId().toString());
				file.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_LABEL, dataSource.getLabel()!=null ? dataSource.getLabel() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_NAME, dataSource.getName()!=null ? dataSource.getName() : "");
				file.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_DESCRIPTION, dataSource.getDescr()!=null ? dataSource.getDescr() : "");
			}
			catch (Exception e) {
				logger.error("Error while refreshing dataSouce meta data",e);		
			}
		}

		try{
			BiObjectUtilities.setFileParametersMetaData(file, parameters);
		}
		catch (Exception e) {
			logger.error("Error in retrieving parameters metadata", e);
		}


		try{
			Date dateCurrent=new Date();
			String currentStr=dateCurrent.toString();
			file.setPersistentProperty(SpagoBIStudioConstants.LAST_REFRESH_DATE, currentStr);
		}
		catch (Exception e) {
			logger.error("Error while refreshing update date",e);		
		}
		logger.debug("OUT");
	}






}
