/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter;
import it.eng.spagobi.server.services.api.bo.IDataSetParameter;

public class DatasetParameter implements IDataSetParameter {

	private java.lang.String name;

	private java.lang.String type;


	private java.lang.String[] values;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String[] getValues() {
		return values;
	}

	public void setValues(java.lang.String[] values) {
		this.values = values;
	}


	public DatasetParameter(SDKDataSetParameter sdkPar) {
		name = name = sdkPar.getName();
		type = sdkPar.getType();
		values = sdkPar.getValues();
	}
}
