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
