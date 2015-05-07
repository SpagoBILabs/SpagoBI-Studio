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
public class InlineAction {
	private String name;
	private boolean hidden;
	private String checkColumn;
	private String flagColumn;
	private String tooltip;
	private String tooltipActive;
	private String tooltipInactive;
	private String titleWin;
	private Config config;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the checkColumn
	 */
	public String getCheckColumn() {
		return checkColumn;
	}
	/**
	 * @param checkColumn the checkColumn to set
	 */
	public void setCheckColumn(String checkColumn) {
		this.checkColumn = checkColumn;
	}
	/**
	 * @return the flagColumn
	 */
	public String getFlagColumn() {
		return flagColumn;
	}
	/**
	 * @param flagColumn the flagColumn to set
	 */
	public void setFlagColumn(String flagColumn) {
		this.flagColumn = flagColumn;
	}
	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}
	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	/**
	 * @return the tooltipActive
	 */
	public String getTooltipActive() {
		return tooltipActive;
	}
	/**
	 * @param tooltipActive the tooltipActive to set
	 */
	public void setTooltipActive(String tooltipActive) {
		this.tooltipActive = tooltipActive;
	}
	/**
	 * @return the tooltipInactive
	 */
	public String getTooltipInactive() {
		return tooltipInactive;
	}
	/**
	 * @param tooltipInactive the tooltipInactive to set
	 */
	public void setTooltipInactive(String tooltipInactive) {
		this.tooltipInactive = tooltipInactive;
	}
	/**
	 * @return the titleWin
	 */
	public String getTitleWin() {
		return titleWin;
	}
	/**
	 * @param titleWin the titleWin to set
	 */
	public void setTitleWin(String titleWin) {
		this.titleWin = titleWin;
	}
	/**
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}
	/**
	 * @param config the config to set
	 */
	public void setConfig(Config config) {
		this.config = config;
	}
	
	
}
