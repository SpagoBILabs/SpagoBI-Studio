/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import java.util.ArrayList;
import java.util.List;

public class DocumentParameters {

	private List<DocumentParameter> parameters = new ArrayList<DocumentParameter>();

	

	public DocumentParameters(List<DocumentParameter> parameters) {
		super();
		this.parameters = parameters;
	}

	public void add(DocumentParameter parameter) {
		parameters.add(parameter);
	}

	public List getContent() {
		return parameters;
	}



}
