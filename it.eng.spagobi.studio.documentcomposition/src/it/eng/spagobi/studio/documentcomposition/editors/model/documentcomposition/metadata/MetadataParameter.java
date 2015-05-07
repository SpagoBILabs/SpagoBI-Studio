/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata;

import it.eng.spagobi.studio.utils.bo.DocumentParameter;

public class MetadataParameter {

    private java.lang.Integer id;

    private java.lang.String label;

    private java.lang.String type;

    private java.lang.String urlName;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getLabel() {
		return label;
	}

	public void setLabel(java.lang.String label) {
		this.label = label;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getUrlName() {
		return urlName;
	}

	public void setUrlName(java.lang.String urlName) {
		this.urlName = urlName;
	}

	public MetadataParameter(DocumentParameter docPar) {
		super();
		this.id = docPar.getId();
		this.label = docPar.getLabel();
		this.type = docPar.getType();
		this.urlName = docPar.getUrlName();
	}

    public MetadataParameter(){
    	super();
    }
}
