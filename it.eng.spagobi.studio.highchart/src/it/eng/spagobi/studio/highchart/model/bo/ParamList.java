package it.eng.spagobi.studio.highchart.model.bo;

import java.util.Vector;

public class ParamList {

	Vector<Param> params;

	public Vector<Param> getParams() {
		if(params == null) params = new Vector<Param>();
		return params;
	}

	public void setParams(Vector<Param> params) {
		this.params = params;
	}
	
	
	
}
