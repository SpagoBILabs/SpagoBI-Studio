package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter;

public class DatasetParameter {

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
