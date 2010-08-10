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

import java.io.Serializable;

public class Column  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9181247915522064183L;
	private String type;
	private String columnId;
	private String hierarchy;
	private String level;
	private String aggFunction;
	
	private boolean choosenForTemplate;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
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
	public String getAggFunction() {
		return aggFunction;
	}
	public void setAggFunction(String aggFunction) {
		this.aggFunction = aggFunction;
	}
	public boolean isChoosenForTemplate() {
		return choosenForTemplate;
	}
	public void setChoosenForTemplate(boolean choosenForTemplate) {
		this.choosenForTemplate = choosenForTemplate;
	}


}
