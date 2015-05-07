/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
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
