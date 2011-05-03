/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

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
