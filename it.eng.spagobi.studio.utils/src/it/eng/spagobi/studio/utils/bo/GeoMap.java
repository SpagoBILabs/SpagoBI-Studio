/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

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


import it.eng.spagobi.sdk.maps.bo.SDKMap;

import java.io.Serializable;



public class GeoMap  implements Serializable   {

	private int mapId;
	private String name;
	private String descr;
	private String url;	
	private String format;	
	private int binId;



	public GeoMap(SDKMap sdkMap) {
		super();
		mapId=sdkMap.getMapId();
		name=sdkMap.getName();
		descr=sdkMap.getDescr();
		url=sdkMap.getUrl();
		format=sdkMap.getFormat();
		if(sdkMap.getBinId()!=null){
			binId=sdkMap.getBinId();
		}
	}


	public GeoMap() {
		super();
		// TODO Auto-generated constructor stub
	}




	/**
	 * Gets the descr.
	 * 
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}




	/**
	 * Sets the descr.
	 * 
	 * @param descr the new descr
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * Gets the format.
	 * 
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the format.
	 * 
	 * @param format the new format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Gets the map id.
	 * 
	 * @return the map id
	 */
	public int getMapId() {
		return mapId;
	}

	/**
	 * Sets the map id.
	 * 
	 * @param mapId the new map id
	 */
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the binary id of the map file (ie. the svg).
	 * 
	 * @return the binId
	 */
	public int getBinId() {
		return binId;
	}

	/**
	 * Sets the  binary id of the map file (ie. the svg)..
	 * 
	 * @param binId the binId to set
	 */
	public void setBinId(int binId) {
		this.binId = binId;
	}



}
