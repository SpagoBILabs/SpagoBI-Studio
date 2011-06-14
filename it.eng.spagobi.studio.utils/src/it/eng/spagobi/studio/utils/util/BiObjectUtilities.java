package it.eng.spagobi.studio.utils.util;

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

import it.eng.spagobi.commons.constants.SpagoBIConstants;
import it.eng.spagobi.studio.utils.bo.DataSource;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Engine;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiObjectUtilities {


	/**
	 *   TYPE => EXTENSION
	 */

	private static Logger logger = LoggerFactory.getLogger(BiObjectUtilities.class);




	public static String getTypeFromExtension(String fileName){

		int indexPoint=fileName.indexOf('.');
		if(indexPoint==-1) return null;

		String extension=fileName.substring(indexPoint+1);
		if(extension.equalsIgnoreCase(SpagoBIStudioConstants.DASHBOARD_ENGINE_EXTENSION)){
			return SpagoBIConstants.DASH_TYPE_CODE;
		}
		else if(extension.equalsIgnoreCase(SpagoBIStudioConstants.CHART_ENGINE_EXTENSION)){
			return SpagoBIConstants.DASH_TYPE_CODE;
		}
		else if(extension.equalsIgnoreCase(SpagoBIStudioConstants.JASPER_REPORT_ENGINE_EXTENSION)){
			return SpagoBIConstants.REPORT_TYPE_CODE;
		}
		else if(extension.equalsIgnoreCase(SpagoBIStudioConstants.BIRT_REPORT_ENGINE_EXTENSION)){
			return SpagoBIConstants.REPORT_TYPE_CODE;
		}
		else if(extension.equalsIgnoreCase(SpagoBIStudioConstants.DOCUMENT_COMPOSITION_ENGINE_EXTENSION)){
			return SpagoBIConstants.DOCUMENT_COMPOSITE_TYPE;
		}
		else if(extension.equalsIgnoreCase(SpagoBIStudioConstants.GEO_ENGINE_EXTENSION)){
			return SpagoBIConstants.MAP_TYPE_CODE;
		}		
		else if(extension.equalsIgnoreCase("xml")){
			return SpagoBIConstants.REPORT_TYPE_CODE;
		}		
		else return null;


	}


	/** erase all persistent properties from the file, called before doing a new deploy after a previous one
	 * 
	 * @param newFile
	 */

	public static void erasePersistentProperties(IFile newFile) throws CoreException{
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_NAME, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_DESCRIPTION, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_STATE, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_TYPE, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_ID, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_LABEL, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_NAME, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_DESCRIPTION, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_NAME, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_DESCRIPTION, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_ID, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_LABEL, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_NAME, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_DESCRIPTION, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.LAST_REFRESH_DATE, null);
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_PARAMETERS_XML, null);
	}



	/** Sets all file metadata, for what regards document, engine, datasource, dataset. If is a new deploy from a previous one delete all existings metadata
	 * 
	 * @param newFile
	 * @param document
	 * @param newDeployFromOld
	 * @return
	 * @throws CoreException
	 */

	public static IFile setFileMetaData(IFile newFile, Document document, boolean newDeployFromOld, SpagoBIServerObjectsFactory proxyServer) throws CoreException, NoActiveServerException{

		if(newDeployFromOld){
			erasePersistentProperties(newFile);
		}

		String projectname = newFile.getProject().getName();
		if(proxyServer == null){
			proxyServer = new SpagoBIServerObjectsFactory(projectname);
		}


		if(proxyServer == null){
			logger.error("No active server is defined");
			return null;
		}

		// DAtaset Infomation field
		Dataset dataSet=null;
		if(document.getDataSetId()!=null){
			try{
				dataSet=proxyServer.getServerDatasets().getDataSet(document.getDataSetId());
			}
			catch (Exception e) {
				logger.error("No comunication with SpagoBI server, could not retrieve dataset informations", e);
			}			
		}

		// DAtasource Infomation field
		DataSource dataSource=null;
		if(document.getDataSourceId()!=null){
			try{
				dataSource=proxyServer.getServerDataSources().getDataSource(document.getDataSourceId());
			}
			catch (Exception e) {
				e.printStackTrace(); 
				logger.error("No comunic8ation with SpagoBI server, could not retrieve dataSource informations", e);
			}			
		}

		// get the extension
		Integer engineId=document.getEngineId();


		Engine engine=null;
		try{
			engine=proxyServer.getServerEngines().getEngine(engineId);
		}
		catch (Exception e) {
			logger.error("No comunication with SpagoBI server, could not get engine", e);
		}	

		if(document.getId()!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID, document.getId().toString());			
		}
		if(document.getLabel()!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL, document.getLabel());
		}
		if(document.getName()!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_NAME, document.getName());
		}
		if(document.getDescription()!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_DESCRIPTION, document.getDescription());
		}
		if(document.getState()!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_STATE, document.getState());
		}
		if(document.getType()!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_TYPE, document.getType());
		}

		newFile.setPersistentProperty(SpagoBIStudioConstants.SERVER, proxyServer.getServerName());


		setFileEngineMetaData(newFile, engine);
		setFileDataSetMetaData(newFile, dataSet);
		setFileDataSourceMetaData(newFile, dataSource);

		return newFile;
	}

	public static IFile setFileEngineMetaData(IFile newFile, Engine engine) throws CoreException{
		if(engine!=null){
			if(engine.getId()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_ID, engine.getId().toString());			
			}
			if(engine.getLabel()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_LABEL, engine.getLabel());
			}
			if(engine.getName()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_NAME, engine.getName());
			}
			if(engine.getDescription()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_DESCRIPTION, engine.getDescription());
			}
		}
		return newFile;
	}

	public static IFile setFileDataSetMetaData(IFile newFile, Dataset dataset) throws CoreException{
		if(dataset!=null){
			if(dataset.getId()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_ID, dataset.getId().toString());			
			}
			if(dataset.getLabel()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL, dataset.getLabel());
			}
			if(dataset.getName()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_NAME, dataset.getName());
			}
			if(dataset.getDescription()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_DESCRIPTION, dataset.getDescription());
			}
		}
		return newFile;
	}

	public static IFile setFileDataSourceMetaData(IFile newFile, DataSource datasource) throws CoreException{
		if(datasource!=null){
			if(datasource.getId()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_ID, datasource.getId().toString());			
			}
			if(datasource.getLabel()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_LABEL, datasource.getLabel());
			}
			if(datasource.getName()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_NAME, datasource.getName());
			}
			if(datasource.getDescr()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_DESCRIPTION, datasource.getDescr());
			}
		}
		return newFile;
	}


	public static IFile setFileInformativeMetaData(IFile newFile, String engineName, String dataSetName, String dataSourceName) throws CoreException{
		if(dataSetName!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_NAME, dataSetName);			
		}
		if(engineName!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.ENGINE_NAME, engineName);			
		}
		if(dataSourceName!=null){
			newFile.setPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_NAME, dataSourceName);			
		}
		return newFile;
	}





	public static IFile setFileLastRefreshDateMetaData(IFile newFile) throws CoreException {
		Date current=new Date();
		String currentString=current.toString();
		newFile.setPersistentProperty(SpagoBIStudioConstants.LAST_REFRESH_DATE, currentString);
		return newFile;
	}	





	public static String getFileExtension(String type, String engine){
		String extension=null;
		if(type.equalsIgnoreCase(SpagoBIConstants.DASH_TYPE_CODE) && engine.equalsIgnoreCase(SpagoBIStudioConstants.DASHBOARD_ENGINE_LABEL) ){
			extension="."+SpagoBIStudioConstants.DASHBOARD_ENGINE_EXTENSION;
		}
		else if(type.equalsIgnoreCase(SpagoBIConstants.DASH_TYPE_CODE) && engine.equalsIgnoreCase(SpagoBIStudioConstants.CHART_ENGINE_LABEL)){
			extension="."+SpagoBIStudioConstants.CHART_ENGINE_EXTENSION;
		}
		else if(type.equalsIgnoreCase(SpagoBIConstants.REPORT_TYPE_CODE) && engine.equalsIgnoreCase(SpagoBIStudioConstants.BIRT_REPORT_ENGINE_LABEL)){
			extension="."+SpagoBIStudioConstants.BIRT_REPORT_ENGINE_EXTENSION;
		}
		else if(type.equalsIgnoreCase(SpagoBIConstants.REPORT_TYPE_CODE) && engine.equalsIgnoreCase(SpagoBIStudioConstants.JASPER_REPORT_ENGINE_LABEL)){
			extension="."+SpagoBIStudioConstants.JASPER_REPORT_ENGINE_EXTENSION;
		}	
		else if(type.equalsIgnoreCase(SpagoBIConstants.OLAP_TYPE_CODE)){
		}
		else if(type.equalsIgnoreCase("MAP") && engine.equalsIgnoreCase(SpagoBIStudioConstants.GEO_ENGINE_LABEL)){
			extension="."+SpagoBIStudioConstants.GEO_ENGINE_EXTENSION;
		}		
		else if(type.equalsIgnoreCase("OFFICE_DOC")){
		}
		else if(type.equalsIgnoreCase("ETL") && engine.equalsIgnoreCase(SpagoBIStudioConstants.ETL_ENGINE_LABEL)){
		}		
		else if(type.equalsIgnoreCase("Dossier")){
		}
		else if(type.equalsIgnoreCase(SpagoBIConstants.DOCUMENT_COMPOSITE_TYPE)){
			extension="."+SpagoBIStudioConstants.DOCUMENT_COMPOSITION_ENGINE_EXTENSION;
		}
		else if(type.equalsIgnoreCase("DATA_MINING")){
		}
		else if(type.equalsIgnoreCase("DATAMART")){
		}
		else if(type.equalsIgnoreCase("QBE")){
		}		
		return extension;

	}






	public static IFile setDataSetMetaData(IFile newFile, Dataset dataset) throws CoreException{
		if(dataset!=null){
			if(dataset.getId()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_ID, dataset.getId().toString());			
			}
			if(dataset.getLabel()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL, dataset.getLabel());
			}
			if(dataset.getName()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_NAME, dataset.getName());
			}
			if(dataset.getDescription()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_DESCRIPTION, dataset.getDescription());
			}
			if(dataset.getDatamarts()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_DATAMARTS, dataset.getDatamarts());
			}
			if(dataset.getJsonQuery()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_JSON_QUERY, dataset.getJsonQuery());
			}
			if(dataset.getTransformer()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_TRANSFORMER, dataset.getTransformer());
			}
			if(dataset.getPivotColumnName()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_COLUMN_NAME, dataset.getPivotColumnName());
			}
			if(dataset.getPivotColumnValue()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_COLUMN_VALUE, dataset.getPivotColumnValue());
			}
			if(dataset.getPivotRowName()!=null){
				newFile.setPersistentProperty(SpagoBIStudioConstants.DATASET_ROW_NAME, dataset.getPivotRowName());
			}

		}
		return newFile;
	}







}
