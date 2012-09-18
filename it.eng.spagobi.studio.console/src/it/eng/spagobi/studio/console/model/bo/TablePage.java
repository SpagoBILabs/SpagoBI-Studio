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
public class TablePage {
	private String dataset;
	private String datasetLabels;
	private String columnId;
	private Map<String,ColumnConfig> columnConfig;
	private FilterBar filterBar;
	private Vector<InlineChart> inlineCharts;
	private Vector<InlineAction> inlineActions;
	/**
	 * @return the dataset
	 */
	public String getDataset() {
		return dataset;
	}
	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	/**
	 * @return the datasetLabels
	 */
	public String getDatasetLabels() {
		return datasetLabels;
	}
	/**
	 * @param datasetLabels the datasetLabels to set
	 */
	public void setDatasetLabels(String datasetLabels) {
		this.datasetLabels = datasetLabels;
	}
	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}
	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	/**
	 * @return the columnConfig
	 */
	public Map<String, ColumnConfig> getColumnConfig() {
		return columnConfig;
	}
	/**
	 * @param columnConfig the columnConfig to set
	 */
	public void setColumnConfig(Map<String, ColumnConfig> columnConfig) {
		this.columnConfig = columnConfig;
	}
	/**
	 * @return the filterBar
	 */
	public FilterBar getFilterBar() {
		return filterBar;
	}
	/**
	 * @param filterBar the filterBar to set
	 */
	public void setFilterBar(FilterBar filterBar) {
		this.filterBar = filterBar;
	}
	/**
	 * @return the inlineCharts
	 */
	public Vector<InlineChart> getInlineCharts() {
		return inlineCharts;
	}
	/**
	 * @param inlineCharts the inlineCharts to set
	 */
	public void setInlineCharts(Vector<InlineChart> inlineCharts) {
		this.inlineCharts = inlineCharts;
	}
	/**
	 * @return the inlineActions
	 */
	public Vector<InlineAction> getInlineActions() {
		return inlineActions;
	}
	/**
	 * @param inlineActions the inlineActions to set
	 */
	public void setInlineActions(Vector<InlineAction> inlineActions) {
		this.inlineActions = inlineActions;
	}
	
	

}
