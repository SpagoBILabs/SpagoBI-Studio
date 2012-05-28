package it.eng.spagobi.studio.extchart.model.bo;

public class Drill {

	public static final String DOCUMENT = "document";
	public static final String PARAM_LIST = "paramList";

	private String document; //combo
	private ParamList paramList; //combo

	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}

	public ParamList getParamList() {
		if(paramList == null) return new ParamList();
		return paramList;
	}
	public void setParamList(ParamList paramList) {
		this.paramList = paramList;
	}


}
