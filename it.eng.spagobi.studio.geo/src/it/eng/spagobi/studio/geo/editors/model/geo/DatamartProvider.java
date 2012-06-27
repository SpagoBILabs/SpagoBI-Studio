/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
