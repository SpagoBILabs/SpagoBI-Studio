/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.geo;

import it.eng.spagobi.studio.geo.editors.CrossNavigationDesigner;

import java.io.Serializable;
import java.util.Vector;

public class Link  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1535770528379383577L;
	private String hierarchy;
	private String level;
	private Vector<LinkParam> param;
	public Integer id=null;
	
	
	public Link() {
		super();
		id=Integer.valueOf(CrossNavigation.idLink++);
	}
	public String getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Vector<LinkParam> getParam() {
		return param;
	}
	public void setParam(Vector<LinkParam> param) {
		this.param = param;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	
}
