/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.geo;


import java.io.Serializable;
import java.util.Vector;

public class Labels implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6947472784492592874L;

	private Vector<Label> label;

	public Vector<Label> getLabel() {
		return label;
	}

	public void setLabel(Vector<Label> label) {
		this.label = label;
	}
}
