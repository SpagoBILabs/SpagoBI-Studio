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

public class DatamartProvider  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -347688907924281162L;
	private String className;
	private String hierarchy;
	private String level;
	private Metadata metadata;
	private Hierarchies hierarchies;
	//private Dataset dataset;
	private CrossNavigation crossNavigation;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public Hierarchies getHierarchies() {
		return hierarchies;
	}
	public void setHierarchies(Hierarchies hierarchies) {
		this.hierarchies = hierarchies;
	}
/*	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}*/
	public CrossNavigation getCrossNavigation() {
		return crossNavigation;
	}
	public void setCrossNavigation(CrossNavigation crossNavigation) {
		this.crossNavigation = crossNavigation;
	}

}
