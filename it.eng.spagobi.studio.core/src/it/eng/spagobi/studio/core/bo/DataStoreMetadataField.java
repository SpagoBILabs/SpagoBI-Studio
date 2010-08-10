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
package it.eng.spagobi.studio.core.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreFieldMetadata;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.maps.bo.SDKMap;

public class DataStoreMetadataField {

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