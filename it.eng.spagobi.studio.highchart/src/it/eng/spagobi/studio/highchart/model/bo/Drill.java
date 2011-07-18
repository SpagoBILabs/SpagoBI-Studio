package it.eng.spagobi.studio.highchart.model.bo;


public class Drill {

	private String document;
	private ParamList paramList;

	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public ParamList getParamList() {
		if(paramList == null) paramList = new ParamList();
		return paramList;
	}
	public void setParamList(ParamList paramList) {
		this.paramList = paramList;
	}


}
