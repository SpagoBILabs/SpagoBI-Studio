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
public class SummaryPanel {
	
	//Composite Widget not supported at this time

	private boolean collassable;
	private boolean collapsed;
	private boolean hidden;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
	private int height;
	private LayoutManagerConfig layoutConfig;
	private Vector<Chart> charts;
	/**
	 * @return the collassable
	 */
	public boolean isCollassable() {
		return collassable;
	}
	/**
	 * @param collassable the collassable to set
	 */
	public void setCollassable(boolean collassable) {
		this.collassable = collassable;
	}
	/**
	 * @return the collapsed
	 */
	public boolean isCollapsed() {
		return collapsed;
	}
	/**
	 * @param collapsed the collapsed to set
	 */
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}
	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the layoutConfig
	 */
	public LayoutManagerConfig getLayoutConfig() {
		return layoutConfig;
	}
	/**
	 * @param layoutConfig the layoutConfig to set
	 */
	public void setLayoutConfig(LayoutManagerConfig layoutConfig) {
		this.layoutConfig = layoutConfig;
	}
	/**
	 * @return the charts
	 */
	public Vector<Chart> getCharts() {
		if(charts == null) charts = new Vector<Chart>();

		return charts;
	}
	/**
	 * @param charts the charts to set
	 */
	public void setCharts(Vector<Chart> charts) {
		this.charts = charts;
	}
	
	
	

}
