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

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class ConsoleTemplateModel {
	
	private Vector<DatasetElement> dataset;
	private SummaryPanel summaryPanel;
	private DetailPanel detailPanel;

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Vector<DatasetElement> dataset) {
		this.dataset = dataset;
	}
	/**
	 * @return the dataset
	 */
	public Vector<DatasetElement> getDataset() {
		if(dataset == null) dataset = new Vector<DatasetElement>();
		return dataset;
	}
	/**
	 * @return the summaryPanel
	 */
	public SummaryPanel getSummaryPanel() {
		return summaryPanel;
	}
	/**
	 * @param summaryPanel the summaryPanel to set
	 */
	public void setSummaryPanel(SummaryPanel summaryPanel) {
		this.summaryPanel = summaryPanel;
	}
	/**
	 * @return the detailPanel
	 */
	public DetailPanel getDetailPanel() {
		return detailPanel;
	}
	/**
	 * @param detailPanel the detailPanel to set
	 */
	public void setDetailPanel(DetailPanel detailPanel) {
		this.detailPanel = detailPanel;
	}

}
