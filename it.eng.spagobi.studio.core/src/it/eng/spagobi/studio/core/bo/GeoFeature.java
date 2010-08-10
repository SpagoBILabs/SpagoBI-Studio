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
package it.eng.spagobi.studio.core.bo;

import it.eng.spagobi.sdk.maps.bo.SDKFeature;

public class GeoFeature {

	private String descr;

	private Integer featureId;

	private String name;

	private String svgGroup;

	private String type;

	private Boolean visibleFlag;


	public GeoFeature(SDKFeature sdkFeature) {
		featureId=sdkFeature.getFeatureId();
		name=sdkFeature.getName();
		descr=sdkFeature.getDescr();
		svgGroup=sdkFeature.getSvgGroup();
		type=sdkFeature.getSvgGroup();
		visibleFlag=sdkFeature.getVisibleFlag();
	}

	

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSvgGroup() {
		return svgGroup;
	}

	public void setSvgGroup(String svgGroup) {
		this.svgGroup = svgGroup;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(Boolean visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

}	    



