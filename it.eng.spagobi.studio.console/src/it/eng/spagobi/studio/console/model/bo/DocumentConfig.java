/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.studio.console.model.bo;

import java.util.Map;
import java.util.Vector;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class DocumentConfig {
	private String label;
	private Map<String,String> staticParams;
	private Vector<DynamicParameter> dynamicParams;
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the staticParams
	 */
	public Map<String, String> getStaticParams() {
		return staticParams;
	}
	/**
	 * @param staticParams the staticParams to set
	 */
	public void setStaticParams(Map<String, String> staticParams) {
		this.staticParams = staticParams;
	}
	/**
	 * @return the dynamicParams
	 */
	public Vector<DynamicParameter> getDynamicParams() {
		return dynamicParams;
	}
	/**
	 * @param dynamicParams the dynamicParams to set
	 */
	public void setDynamicParams(Vector<DynamicParameter> dynamicParams) {
		this.dynamicParams = dynamicParams;
	}
	
}
