package it.eng.spagobi.server.services.api.bo;

public interface IDataStoreMetadataField {

	/**
	 * Gets the className value for this SDKDataStoreFieldMetadata.
	 * 
	 * @return className
	 */
	public abstract java.lang.String getClassName();

	/**
	 * Sets the className value for this SDKDataStoreFieldMetadata.
	 * 
	 * @param className
	 */
	public abstract void setClassName(java.lang.String className);

	/**
	 * Gets the name value for this SDKDataStoreFieldMetadata.
	 * 
	 * @return name
	 */
	public abstract java.lang.String getName();

	/**
	 * Sets the name value for this SDKDataStoreFieldMetadata.
	 * 
	 * @param name
	 */
	public abstract void setName(java.lang.String name);

	/**
	 * Gets the properties value for this SDKDataStoreFieldMetadata.
	 * 
	 * @return properties
	 */
	public abstract java.util.HashMap getProperties();

	/**
	 * Sets the properties value for this SDKDataStoreFieldMetadata.
	 * 
	 * @param properties
	 */
	public abstract void setProperties(java.util.HashMap properties);

}