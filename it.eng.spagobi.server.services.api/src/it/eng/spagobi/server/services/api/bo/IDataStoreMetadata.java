package it.eng.spagobi.server.services.api.bo;

public interface IDataStoreMetadata {

	/**
	 * Gets the fieldsMetadata value for this SDKDataStoreMetadata.
	 * 
	 * @return fieldsMetadata
	 */
	public abstract IDataStoreMetadataField[] getFieldsMetadata();

	/**
	 * Sets the fieldsMetadata value for this SDKDataStoreMetadata.
	 * 
	 * @param fieldsMetadata
	 */
	public abstract void setFieldsMetadata(
			IDataStoreMetadataField[] fieldsMetadata);

	/**
	 * Gets the properties value for this SDKDataStoreMetadata.
	 * 
	 * @return properties
	 */
	public abstract java.util.HashMap getProperties();

	/**
	 * Sets the properties value for this SDKDataStoreMetadata.
	 * 
	 * @param properties
	 */
	public abstract void setProperties(java.util.HashMap properties);

}