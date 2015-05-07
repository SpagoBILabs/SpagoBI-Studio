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

public class Hierarchy  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1082027570216423244L;
	private String name;
	private String type;
	private Vector<Level> levels;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Vector<Level> getLevels() {
		return levels;
	}
	public void setLevels(Vector<Level> levels) {
		this.levels = levels;
	}


}
