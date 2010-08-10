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

public class Tresholds  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7314038587889055886L;
	private String type;
	private String lbValue;
	private String ubValue;
	private Param param;
	public Param getParam() {
		return param;
	}
	public void setParam(Param param) {
		this.param = param;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLbValue() {
		return lbValue;
	}
	public void setLbValue(String lbValue) {
		this.lbValue = lbValue;
	}
	public String getUbValue() {
		return ubValue;
	}
	public void setUbValue(String ubValue) {
		this.ubValue = ubValue;
	}

}
