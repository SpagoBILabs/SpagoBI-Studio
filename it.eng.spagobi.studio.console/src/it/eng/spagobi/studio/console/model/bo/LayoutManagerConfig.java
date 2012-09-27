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

import java.util.Vector;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class LayoutManagerConfig {
	
	private String layout;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
	private int columnNumber;
	private Vector<String> columnWidths;
	
	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}
	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}
	/**
	 * @return the columnNumber
	 */
	public int getColumnNumber() {
		return columnNumber;
	}
	/**
	 * @param columnNumber the columnNumber to set
	 */
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	/**
	 * @return the columnWidths
	 */
	public Vector<String> getColumnWidths() {
		if(columnWidths == null) columnWidths = new Vector<String>();

		return columnWidths;
	}
	/**
	 * @param columnWidths the columnWidths to set
	 */
	public void setColumnWidths(Vector<String> columnWidths) {
		this.columnWidths = columnWidths;
	}
	

}
