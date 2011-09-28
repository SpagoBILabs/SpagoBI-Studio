package it.eng.spagobi.server.services.api.bo;

public interface IDataSet {

	/**
	 * Gets the description value for this SDKDataSet.
	 * 
	 * @return description
	 */
	public abstract String getDescription();

	/**
	 * Sets the description value for this SDKDataSet.
	 * 
	 * @param description
	 */
	public abstract void setDescription(String description);

	/**
	 * Gets the fileName value for this SDKDataSet.
	 * 
	 * @return fileName
	 */
	public abstract String getFileName();

	/**
	 * Sets the fileName value for this SDKDataSet.
	 * 
	 * @param fileName
	 */
	public abstract void setFileName(String fileName);

	/**
	 * Gets the id value for this SDKDataSet.
	 * 
	 * @return id
	 */
	public abstract Integer getId();

	/**
	 * Sets the id value for this SDKDataSet.
	 * 
	 * @param id
	 */
	public abstract void setId(Integer id);

	/**
	 * Gets the javaClassName value for this SDKDataSet.
	 * 
	 * @return javaClassName
	 */
	public abstract String getJavaClassName();

	/**
	 * Sets the javaClassName value for this SDKDataSet.
	 * 
	 * @param javaClassName
	 */
	public abstract void setJavaClassName(String javaClassName);

	/**
	 * Gets the jdbcDataSourceId value for this SDKDataSet.
	 * 
	 * @return jdbcDataSourceId
	 */
	public abstract Integer getJdbcDataSourceId();

	/**
	 * Sets the jdbcDataSourceId value for this SDKDataSet.
	 * 
	 * @param jdbcDataSourceId
	 */
	public abstract void setJdbcDataSourceId(Integer jdbcDataSourceId);

	/**
	 * Gets the jdbcQuery value for this SDKDataSet.
	 * 
	 * @return jdbcQuery
	 */
	public abstract String getJdbcQuery();

	/**
	 * Sets the jdbcQuery value for this SDKDataSet.
	 * 
	 * @param jdbcQuery
	 */
	public abstract void setJdbcQuery(String jdbcQuery);

	/**
	 * Gets the label value for this SDKDataSet.
	 * 
	 * @return label
	 */
	public abstract String getLabel();

	/**
	 * Sets the label value for this SDKDataSet.
	 * 
	 * @param label
	 */
	public abstract void setLabel(String label);

	/**
	 * Gets the name value for this SDKDataSet.
	 * 
	 * @return name
	 */
	public abstract String getName();

	/**
	 * Sets the name value for this SDKDataSet.
	 * 
	 * @param name
	 */
	public abstract void setName(String name);

	/**
	 * Gets the numberingRows value for this SDKDataSet.
	 * 
	 * @return numberingRows
	 */
	public abstract Boolean getNumberingRows();

	/**
	 * Sets the numberingRows value for this SDKDataSet.
	 * 
	 * @param numberingRows
	 */
	public abstract void setNumberingRows(Boolean numberingRows);

	/**
	 * Gets the pivotColumnName value for this SDKDataSet.
	 * 
	 * @return pivotColumnName
	 */
	public abstract String getPivotColumnName();

	/**
	 * Sets the pivotColumnName value for this SDKDataSet.
	 * 
	 * @param pivotColumnName
	 */
	public abstract void setPivotColumnName(String pivotColumnName);

	/**
	 * Gets the pivotColumnValue value for this SDKDataSet.
	 * 
	 * @return pivotColumnValue
	 */
	public abstract String getPivotColumnValue();

	/**
	 * Sets the pivotColumnValue value for this SDKDataSet.
	 * 
	 * @param pivotColumnValue
	 */
	public abstract void setPivotColumnValue(String pivotColumnValue);

	/**
	 * Gets the pivotRowName value for this SDKDataSet.
	 * 
	 * @return pivotRowName
	 */
	public abstract String getPivotRowName();

	/**
	 * Sets the pivotRowName value for this SDKDataSet.
	 * 
	 * @param pivotRowName
	 */
	public abstract void setPivotRowName(String pivotRowName);

	/**
	 * Gets the scriptLanguage value for this SDKDataSet.
	 * 
	 * @return scriptLanguage
	 */
	public abstract String getScriptLanguage();

	/**
	 * Sets the scriptLanguage value for this SDKDataSet.
	 * 
	 * @param scriptLanguage
	 */
	public abstract void setScriptLanguage(String scriptLanguage);

	/**
	 * Gets the scriptText value for this SDKDataSet.
	 * 
	 * @return scriptText
	 */
	public abstract String getScriptText();

	/**
	 * Sets the scriptText value for this SDKDataSet.
	 * 
	 * @param scriptText
	 */
	public abstract void setScriptText(String scriptText);

	/**
	 * Gets the type value for this SDKDataSet.
	 * 
	 * @return type
	 */
	public abstract String getType();

	/**
	 * Sets the type value for this SDKDataSet.
	 * 
	 * @param type
	 */
	public abstract void setType(String type);

	/**
	 * Gets the webServiceAddress value for this SDKDataSet.
	 * 
	 * @return webServiceAddress
	 */
	public abstract String getWebServiceAddress();

	/**
	 * Sets the webServiceAddress value for this SDKDataSet.
	 * 
	 * @param webServiceAddress
	 */
	public abstract void setWebServiceAddress(String webServiceAddress);

	/**
	 * Gets the webServiceOperation value for this SDKDataSet.
	 * 
	 * @return webServiceOperation
	 */
	public abstract String getWebServiceOperation();

	public abstract String getTransformer();

	public abstract void setTransformer(String transformer);

	public abstract String getJsonQuery();

	public abstract void setJsonQuery(String jsonQuery);

	public abstract String getDatamarts();

	public abstract void setDatamarts(String datamarts);

	public abstract IDataSetParameter[] getParameters();

	public abstract void setParameters(IDataSetParameter[] parameters);

}