/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreFieldMetadata;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.maps.bo.SDKMap;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;

public class DataStoreMetadataField implements IDataStoreMetadataField{

	private java.lang.String className;

	private java.lang.String name;

	private java.util.HashMap properties;

	public DataStoreMetadataField() {
	}

	public DataStoreMetadataField(
			java.lang.String className,
			java.lang.String name,
			java.util.HashMap properties) {
		this.className = className;
		this.name = name;
		this.properties = properties;
	}


	public DataStoreMetadataField(SDKDataStoreFieldMetadata sdkDataStoreMetadata) {
		this.className = sdkDataStoreMetadata.getClassName();
		this.name = sdkDataStoreMetadata.getName();
		this.properties = sdkDataStoreMetadata.getProperties();
	}


	/**
	 * Gets the className value for this SDKDataStoreFieldMetadata.
	 * 
	 * @return className
	 */
	public java.lang.String getClassName() {
		return className;
	}


	/**
	 * Sets the className value for this SDKDataStoreFieldMetadata.
	 * 
	 * @param className
	 */
	public void setClassName(java.lang.String className) {
		this.className = className;
	}


	/**
	 * Gets the name value for this SDKDataStoreFieldMetadata.
	 * 
	 * @return name
	 */
	public java.lang.String getName() {
		return name;
	}


	/**
	 * Sets the name value for this SDKDataStoreFieldMetadata.
	 * 
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}


	/**
	 * Gets the properties value for this SDKDataStoreFieldMetadata.
	 * 
	 * @return properties
	 */
	public java.util.HashMap getProperties() {
		return properties;
	}


	/**
	 * Sets the properties value for this SDKDataStoreFieldMetadata.
	 * 
	 * @param properties
	 */
	public void setProperties(java.util.HashMap properties) {
		this.properties = properties;
	}


}