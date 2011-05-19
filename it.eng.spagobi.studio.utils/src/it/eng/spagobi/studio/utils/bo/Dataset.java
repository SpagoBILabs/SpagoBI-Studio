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
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.maps.bo.SDKMap;

public class Dataset {



	private String description;

	private String fileName;

	private Integer id;

	private String javaClassName;

	private Integer jdbcDataSourceId;

	private String jdbcQuery;

	private String label;

	private String name;

	private Boolean numberingRows;

	private String transformer;
	
	private String jsonQuery;

	private String datamarts;
	

	//private it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter[] parameters;

	private String pivotColumnName;

	private String pivotColumnValue;

	private String pivotRowName;

	private String scriptLanguage;

	private String scriptText;

	private String type;

	private String webServiceAddress;

	private String webServiceOperation;

	public Dataset(SDKDataSet sdk) {
		super();
		description=sdk.getDescription();
		fileName=sdk.getFileName();
		id=sdk.getId();
		javaClassName=sdk.getJavaClassName();
		jdbcDataSourceId=sdk.getJdbcDataSourceId();
		jdbcQuery=sdk.getJdbcQuery();
		label=sdk.getLabel();
		name=sdk.getName();
		numberingRows=sdk.getNumberingRows();
		pivotColumnName=sdk.getPivotColumnName();
		pivotColumnValue=sdk.getPivotColumnValue();
		pivotRowName=sdk.getPivotRowName();
		scriptLanguage=sdk.getScriptLanguage();
		scriptText=sdk.getScriptText();
		type=sdk.getType();
		webServiceAddress=sdk.getWebServiceAddress();
		webServiceOperation=sdk.getWebServiceOperation();

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
	public String getJavaClassName() {
		return javaClassName;
	}


	/**
	 * Sets the javaClassName value for this SDKDataSet.
	 * 
	 * @param javaClassName
	 */
	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}


	/**
	 * Gets the jdbcDataSourceId value for this SDKDataSet.
	 * 
	 * @return jdbcDataSourceId
	 */
	public Integer getJdbcDataSourceId() {
		return jdbcDataSourceId;
	}


	/**
	 * Sets the jdbcDataSourceId value for this SDKDataSet.
	 * 
	 * @param jdbcDataSourceId
	 */
	public void setJdbcDataSourceId(Integer jdbcDataSourceId) {
		this.jdbcDataSourceId = jdbcDataSourceId;
	}


	/**
	 * Gets the jdbcQuery value for this SDKDataSet.
	 * 
	 * @return jdbcQuery
	 */
	public String getJdbcQuery() {
		return jdbcQuery;
	}


	/**
	 * Sets the jdbcQuery value for this SDKDataSet.
	 * 
	 * @param jdbcQuery
	 */
	public void setJdbcQuery(String jdbcQuery) {
		this.jdbcQuery = jdbcQuery;
	}


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
	public String getScriptLanguage() {
		return scriptLanguage;
	}


	/**
	 * Sets the scriptLanguage value for this SDKDataSet.
	 * 
	 * @param scriptLanguage
	 */
	public void setScriptLanguage(String scriptLanguage) {
		this.scriptLanguage = scriptLanguage;
	}


	/**
	 * Gets the scriptText value for this SDKDataSet.
	 * 
	 * @return scriptText
	 */
	public String getScriptText() {
		return scriptText;
	}


	/**
	 * Sets the scriptText value for this SDKDataSet.
	 * 
	 * @param scriptText
	 */
	public void setScriptText(String scriptText) {
		this.scriptText = scriptText;
	}


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
	public String getWebServiceAddress() {
		return webServiceAddress;
	}


	/**
	 * Sets the webServiceAddress value for this SDKDataSet.
	 * 
	 * @param webServiceAddress
	 */
	public void setWebServiceAddress(String webServiceAddress) {
		this.webServiceAddress = webServiceAddress;
	}


	/**
	 * Gets the webServiceOperation value for this SDKDataSet.
	 * 
	 * @return webServiceOperation
	 */
	public String getWebServiceOperation() {
		return webServiceOperation;
	}


	public String getTransformer() {
		return transformer;
	}


	public void setTransformer(String transformer) {
		this.transformer = transformer;
	}


	public String getJsonQuery() {
		return jsonQuery;
	}


	public void setJsonQuery(String jsonQuery) {
		this.jsonQuery = jsonQuery;
	}


	public String getDatamarts() {
		return datamarts;
	}


	public void setDatamarts(String datamarts) {
		this.datamarts = datamarts;
	}

	
	
}
