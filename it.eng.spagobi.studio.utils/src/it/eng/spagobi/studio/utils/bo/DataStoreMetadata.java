/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;

import java.util.HashMap;

public class DataStoreMetadata implements IDataStoreMetadata {

	private IDataStoreMetadataField[] fieldsMetadata;

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
	public IDataStoreMetadataField[] getFieldsMetadata() {
		return fieldsMetadata;
	}


	/**
	 * Sets the fieldsMetadata value for this SDKDataStoreMetadata.
	 * 
	 * @param fieldsMetadata
	 */
	public void setFieldsMetadata(IDataStoreMetadataField[] fieldsMetadata) {
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
