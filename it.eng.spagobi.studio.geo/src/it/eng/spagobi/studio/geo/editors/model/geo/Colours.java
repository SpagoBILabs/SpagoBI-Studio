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

public class Colours implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5638393931138636831L;
	private String type;
	private String outboundColour;
	private String nullValuesColor;
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
	public String getOutboundColour() {
		return outboundColour;
	}
	public void setOutboundColour(String outboundColour) {
		this.outboundColour = outboundColour;
	}
	public String getNullValuesColor() {
		return nullValuesColor;
	}
	public void setNullValuesColor(String nullValuesColor) {
		this.nullValuesColor = nullValuesColor;
	}

}
