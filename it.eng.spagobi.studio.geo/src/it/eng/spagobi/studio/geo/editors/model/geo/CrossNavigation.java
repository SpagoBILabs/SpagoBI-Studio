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

public class CrossNavigation  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5915328890533988875L;
	private Vector<Link> links = new Vector<Link>();
	public static int idLink=0;

	public Vector<Link> getLinks() {
		return links;
	}

	public void setLinks(Vector<Link> links) {
		this.links = links;
	}

}
