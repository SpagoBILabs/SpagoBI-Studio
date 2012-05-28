package it.eng.spagobi.studio.extchart.model.bo;

import java.util.Vector;

public class ParamList {

	public static final String PARAMS = "params";
	
	private Vector<Param> params;

	public Vector<Param> getParams() {
		if(params == null) params = new Vector<Param>();
		return params;
	}

	public void setParams(Vector<Param> params) {
		this.params = params;
	}
	
	
	
}
