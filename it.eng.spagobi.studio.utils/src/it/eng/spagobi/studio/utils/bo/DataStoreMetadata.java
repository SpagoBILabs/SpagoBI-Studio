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

import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;

import java.util.HashMap;

public class DataStoreMetadata {

	private DataStoreMetadataField[] fieldsMetadata;

	private HashMap properties;

	public DataStoreMetadata() {
	}

	public DataStoreMetadata(
			DataStoreMetadataField[] fieldsMetadata,
			java.util.HashMap properties) {
		this.fieldsMetadata = fieldsMetadata;
		this.properties = properties;
	}

	public DataStoreMetadata(SDKDataStoreMetadata sdkDataStoreMetadata) {
		if(sdkDataStoreMetadata==null || sdkDataStoreMetadata.getFieldsMetadata()==null) return;
		fieldsMetadata=new DataStoreMetadataField[sdkDataStoreMetadata.getFieldsMetadata().length];
		for (int i = 0; i < sdkDataStoreMetadata.getFieldsMetadata().length; i++) {
			fieldsMetadata[i]=new DataStoreMetadataField(sdkDataStoreMetadata.getFieldsMetadata()[i]);
		}
		this.properties = sdkDataStoreMetadata.getProperties();
	}


	/**
	 * Gets the fieldsMetadata value for this SDKDataStoreMetadata.
	 * 
	 * @return fieldsMetadata
	 */
	public DataStoreMetadataField[] getFieldsMetadata() {
		return fieldsMetadata;
	}


	/**
	 * Sets the fieldsMetadata value for this SDKDataStoreMetadata.
	 * 
	 * @param fieldsMetadata
	 */
	public void setFieldsMetadata(DataStoreMetadataField[] fieldsMetadata) {
		this.fieldsMetadata = fieldsMetadata;
	}


	/**
	 * Gets the properties value for this SDKDataStoreMetadata.
	 * 
	 * @return properties
	 */
	public java.util.HashMap getProperties() {
		return properties;
	}


	/**
	 * Sets the properties value for this SDKDataStoreMetadata.
	 * 
	 * @param properties
	 */
	public void setProperties(java.util.HashMap properties) {
		this.properties = properties;
	}


}
