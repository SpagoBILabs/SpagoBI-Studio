/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.wizards.pages.util;

import org.eclipse.swt.widgets.Text;

public class DestinationInfo{
	private String docDestName;
	private String paramDestName;
	private Text paramDefaultValue;
	private String paramDestId;
	
	public String getParamDestId() {
		return paramDestId;
	}
	public void setParamDestId(String paramDestId) {
		this.paramDestId = paramDestId;
	}
	public Text getParamDefaultValue() {
		return paramDefaultValue;
	}
	public void setParamDefaultValue(Text paramDefaultValue) {
		this.paramDefaultValue = paramDefaultValue;
	}
	public String getDocDestName() {
		return docDestName;
	}
	public void setDocDestName(String docDestName) {
		this.docDestName = docDestName;
	}
	public String getParamDestName() {
		return paramDestName;
	}
	public void setParamDestName(String paramDestName) {
	
		this.paramDestName = paramDestName;
	}

}
