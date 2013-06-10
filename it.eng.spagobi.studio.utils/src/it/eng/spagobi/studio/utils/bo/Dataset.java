/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.

 **/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.container.ObjectUtils;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter;
import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataSetParameter;
import it.eng.spagobi.utilities.json.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Dataset implements IDataSet {

	private String fileName;

	private Integer id;

	private String label;

	private String name;

	private String description;

	private Boolean numberingRows;

	private IDataSetParameter[] parameters;

	private String pivotColumnName;

	private String pivotColumnValue;

	private String pivotRowName;

	private String type;

	private String configuration;

	public static final String QUERY = "Query";
	public static final String QUERY_SCRIPT = "queryScript";
	public static final String QUERY_SCRIPT_LANGUAGE = "queryScriptLanguage";
	public static final String DATA_SOURCE ="dataSource";
	public static final String QBE_DATAMARTS = "qbeDatamarts";
	public static final String QBE_JSON_QUERY = "qbeJSONQuery";
	public static final String QBE_SQL_QUERY = "qbeSQLQuery";
	public static final String QBE_DATA_SOURCE = "qbeDataSource";
	
	//	private String javaClassName;
	//
	//	private Integer jdbcDataSourceId;
	//
	//	private String jdbcQuery;



		private String transformer;
	//
	//	private String jsonQuery;
	//
	//	private String datamarts;


	//private it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter[] parameters;


	//	private String scriptLanguage;

	//	private String scriptText;
	//
	//
	//
	//	private String webServiceAddress;

	//	private String webServiceOperation;

	public Dataset(SDKDataSet sdk) {
		super();
		description=sdk.getDescription();
		//fileName=sdk.getFileName();
		id=sdk.getId();
		//		javaClassName=sdk.getJavaClassName();
		//		jdbcDataSourceId=sdk.getJdbcDataSourceId();
		//		jdbcQuery=sdk.getJdbcQuery();

		label=sdk.getLabel();
		name=sdk.getName();
		numberingRows=sdk.getNumberingRows();
		pivotColumnName=sdk.getPivotColumnName();
		pivotColumnValue=sdk.getPivotColumnValue();
		pivotRowName=sdk.getPivotRowName();
		//		scriptLanguage=sdk.getScriptLanguage();
		//		scriptText=sdk.getScriptText();
		type=sdk.getType();
		configuration = sdk.getConfiguration();
		//		webServiceAddress=sdk.getWebServiceAddress();
		//		webServiceOperation=sdk.getWebServiceOperation();
		//		jsonQuery = sdk.getJsonQuery();
		//		datamarts = sdk.getDatamarts();






		// fill parameters
		if(sdk.getParameters() != null){
			IDataSetParameter[] dsParArray = new DatasetParameter[sdk.getParameters().length];	
			for (int i = 0; i < sdk.getParameters().length; i++) {
				SDKDataSetParameter sdkPar = sdk.getParameters()[i];
				DatasetParameter dsPar = new DatasetParameter(sdkPar);
				dsParArray[i] =dsPar;
			}
			parameters = dsParArray;
		}

	}


	public Dataset() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * Gets the description value for this SDKDataSet.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Sets the description value for this SDKDataSet.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * Gets the fileName value for this SDKDataSet.
	 * 
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * Sets the fileName value for this SDKDataSet.
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * Gets the id value for this SDKDataSet.
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * Sets the id value for this SDKDataSet.
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Gets the javaClassName value for this SDKDataSet.
	 * 
	 * @return javaClassName
	 */
	//	public String getJavaClassName() {
	//		return javaClassName;
	//	}
	//
	//
	//	/**
	//	 * Sets the javaClassName value for this SDKDataSet.
	//	 * 
	//	 * @param javaClassName
	//	 */
	//	public void setJavaClassName(String javaClassName) {
	//		this.javaClassName = javaClassName;
	//	}
	//
	//
	//	/**
	//	 * Gets the jdbcDataSourceId value for this SDKDataSet.
	//	 * 
	//	 * @return jdbcDataSourceId
	//	 */
	//	public Integer getJdbcDataSourceId() {
	//		return jdbcDataSourceId;
	//	}
	//
	//
	//	/**
	//	 * Sets the jdbcDataSourceId value for this SDKDataSet.
	//	 * 
	//	 * @param jdbcDataSourceId
	//	 */
	//	public void setJdbcDataSourceId(Integer jdbcDataSourceId) {
	//		this.jdbcDataSourceId = jdbcDataSourceId;
	//	}
	//
	//
	//	/**
	//	 * Gets the jdbcQuery value for this SDKDataSet.
	//	 * 
	//	 * @return jdbcQuery
	//	 */
	//	public String getJdbcQuery() {
	//		return jdbcQuery;
	//	}
	//
	//
	//	/**
	//	 * Sets the jdbcQuery value for this SDKDataSet.
	//	 * 
	//	 * @param jdbcQuery
	//	 */
	//	public void setJdbcQuery(String jdbcQuery) {
	//		this.jdbcQuery = jdbcQuery;
	//	}


	/**
	 * Gets the label value for this SDKDataSet.
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * Sets the label value for this SDKDataSet.
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * Gets the name value for this SDKDataSet.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name value for this SDKDataSet.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the numberingRows value for this SDKDataSet.
	 * 
	 * @return numberingRows
	 */
	public Boolean getNumberingRows() {
		return numberingRows;
	}


	/**
	 * Sets the numberingRows value for this SDKDataSet.
	 * 
	 * @param numberingRows
	 */
	public void setNumberingRows(Boolean numberingRows) {
		this.numberingRows = numberingRows;
	}

	//    public it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter[] getParameters() {
	//        return parameters;
	//    }
	//
	//
	//    public void setParameters(it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter[] parameters) {
	//        this.parameters = parameters;
	//    }


	/**
	 * Gets the pivotColumnName value for this SDKDataSet.
	 * 
	 * @return pivotColumnName
	 */
	public String getPivotColumnName() {
		return pivotColumnName;
	}


	/**
	 * Sets the pivotColumnName value for this SDKDataSet.
	 * 
	 * @param pivotColumnName
	 */
	public void setPivotColumnName(String pivotColumnName) {
		this.pivotColumnName = pivotColumnName;
	}


	/**
	 * Gets the pivotColumnValue value for this SDKDataSet.
	 * 
	 * @return pivotColumnValue
	 */
	public String getPivotColumnValue() {
		return pivotColumnValue;
	}


	/**
	 * Sets the pivotColumnValue value for this SDKDataSet.
	 * 
	 * @param pivotColumnValue
	 */
	public void setPivotColumnValue(String pivotColumnValue) {
		this.pivotColumnValue = pivotColumnValue;
	}


	/**
	 * Gets the pivotRowName value for this SDKDataSet.
	 * 
	 * @return pivotRowName
	 */
	public String getPivotRowName() {
		return pivotRowName;
	}


	/**
	 * Sets the pivotRowName value for this SDKDataSet.
	 * 
	 * @param pivotRowName
	 */
	public void setPivotRowName(String pivotRowName) {
		this.pivotRowName = pivotRowName;
	}


	/**
	 * Gets the scriptLanguage value for this SDKDataSet.
	 * 
	 * @return scriptLanguage
	 */
	//	public String getScriptLanguage() {
	//		return scriptLanguage;
	//	}
	//
	//
	//	/**
	//	 * Sets the scriptLanguage value for this SDKDataSet.
	//	 * 
	//	 * @param scriptLanguage
	//	 */
	//	public void setScriptLanguage(String scriptLanguage) {
	//		this.scriptLanguage = scriptLanguage;
	//	}
	//
	//
	//	/**
	//	 * Gets the scriptText value for this SDKDataSet.
	//	 * 
	//	 * @return scriptText
	//	 */
	//	public String getScriptText() {
	//		return scriptText;
	//	}
	//
	//
	//	/**
	//	 * Sets the scriptText value for this SDKDataSet.
	//	 * 
	//	 * @param scriptText
	//	 */
	//	public void setScriptText(String scriptText) {
	//		this.scriptText = scriptText;
	//	}


	/**
	 * Gets the type value for this SDKDataSet.
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}


	/**
	 * Sets the type value for this SDKDataSet.
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Gets the webServiceAddress value for this SDKDataSet.
	 * 
	 * @return webServiceAddress
	 */
	//	public String getWebServiceAddress() {
	//		return webServiceAddress;
	//	}
	//
	//
	//	/**
	//	 * Sets the webServiceAddress value for this SDKDataSet.
	//	 * 
	//	 * @param webServiceAddress
	//	 */
	//	public void setWebServiceAddress(String webServiceAddress) {
	//		this.webServiceAddress = webServiceAddress;
	//	}
	//
	//
	//	/**
	//	 * Gets the webServiceOperation value for this SDKDataSet.
	//	 * 
	//	 * @return webServiceOperation
	//	 */
	//	public String getWebServiceOperation() {
	//		return webServiceOperation;
	//	}
	//
	//
		public String getTransformer() {
			return transformer;
		}
		public void setTransformer(String transformer) {
			this.transformer = transformer;
		}
	//
	//
	//	public String getJsonQuery() {
	//		return jsonQuery;
	//	}
	//
	//
	//	public void setJsonQuery(String jsonQuery) {
	//		this.jsonQuery = jsonQuery;
	//	}
	//
	//
	//	public String getDatamarts() {
	//		return datamarts;
	//	}
	//
	//
	//	public void setDatamarts(String datamarts) {
	//		this.datamarts = datamarts;
	//	}


	public IDataSetParameter[] getParameters() {
		return parameters;
	}


	public void setParameters(IDataSetParameter[] parameters) {
		this.parameters = parameters;
	}


	public String getConfiguration() {
		return configuration;
	}


	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

/**
 * Configuration is a JSON obhject contaning several properties
 */

	public Object getFromConfiguration(String fieldName) throws Exception{
		Object toReturn = null;

		if(configuration != null){
			try{
				String config = JSONUtils.escapeJsonString(configuration);
				JSONObject jsonConf  = ObjectUtils.toJSONObject(config);
				toReturn = jsonConf.getString(fieldName);			
			}
			catch (Exception e) {
				throw e;
			}
		}
		return toReturn;
	}

	public void addToConfiguration(String fieldName, String fieldValue) throws Exception{
		String toReturn = null;
		try{
			JSONObject jsonConf  = null;

			if(configuration == null) jsonConf  = new JSONObject();
			else{
				String config = JSONUtils.escapeJsonString(configuration);

				//jsonConf  = ObjectUtils.toJSONObject(config);
				jsonConf  = toJSONObject(config);
			}
			
			jsonConf.put(fieldName, fieldValue);
		
			toReturn = jsonConf.toString();

		}
		catch (Exception e) {
				throw e;
			}
		configuration = toReturn;
	}
	

	// shold call ObjectUtils.toJSONObject but could not initialize class. TODO
	public JSONObject toJSONObject(Object o) throws JSONException {
		JSONObject toReturn = null;
		toReturn = null;
		toReturn = new JSONObject( toString(o) );
		return toReturn;	
	}
	
	public String toString(Object o) {
		String toReturn;
		toReturn = o.toString();
		return toReturn;	
	}
	

}
