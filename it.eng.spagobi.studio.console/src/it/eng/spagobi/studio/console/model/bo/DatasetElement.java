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

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class DatasetElement {
	
	private String id;
	private String label;
	private int refreshTime;
	private boolean memoryPagination;
	private int rowsLimit;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	 * @return the refreshTime
	 */
	public int getRefreshTime() {
		return refreshTime;
	}
	/**
	 * @param refreshTime the refreshTime to set
	 */
	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
	/**
	 * @return the memoryPagination
	 */
	public boolean isMemoryPagination() {
		return memoryPagination;
	}
	/**
	 * @param memoryPagination the memoryPagination to set
	 */
	public void setMemoryPagination(boolean memoryPagination) {
		this.memoryPagination = memoryPagination;
	}
	/**
	 * @return the rowsLimit
	 */
	public int getRowsLimit() {
		return rowsLimit;
	}
	/**
	 * @param rowsLimit the rowsLimit to set
	 */
	public void setRowsLimit(int rowsLimit) {
		this.rowsLimit = rowsLimit;
	}

}
