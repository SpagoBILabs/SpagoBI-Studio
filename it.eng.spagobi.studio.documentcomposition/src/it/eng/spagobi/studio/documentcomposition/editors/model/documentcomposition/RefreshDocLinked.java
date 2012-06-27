/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

public class RefreshDocLinked {
	private String labelDoc;
	private String labelParam;
	private String idParam;
	
	public String getIdParam() {
		return idParam;
	}
	public void setIdParam(String idParam) {
		this.idParam = idParam;
	}
	public String getLabelDoc() {
		return labelDoc;
	}
	public void setLabelDoc(String labelDoc) {
		this.labelDoc = labelDoc;
	}
	public String getLabelParam() {
		return labelParam;
	}
	public void setLabelParam(String labelParam) {
		this.labelParam = labelParam;
	}



}
