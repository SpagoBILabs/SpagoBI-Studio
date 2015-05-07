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

public class Layers  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 719689238722747829L;
	private Vector<Layer> layer;
	private String mapName;

	public Vector<Layer> getLayer() {
		return layer;
	}

	public void setLayer(Vector<Layer> layer) {
		this.layer = layer;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

}
