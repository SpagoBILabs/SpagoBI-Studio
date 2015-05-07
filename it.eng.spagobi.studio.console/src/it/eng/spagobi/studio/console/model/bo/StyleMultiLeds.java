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
public class StyleMultiLeds {
	private Border border;
	private Title title;
	private Padding padding;
	private Gauge gauge;
	/**
	 * @return the border
	 */
	public Border getBorder() {
		return border;
	}
	/**
	 * @param border the border to set
	 */
	public void setBorder(Border border) {
		this.border = border;
	}
	/**
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}
	/**
	 * @return the padding
	 */
	public Padding getPadding() {
		return padding;
	}
	/**
	 * @param padding the padding to set
	 */
	public void setPadding(Padding padding) {
		this.padding = padding;
	}
	/**
	 * @return the gauge
	 */
	public Gauge getGauge() {
		return gauge;
	}
	/**
	 * @param gauge the gauge to set
	 */
	public void setGauge(Gauge gauge) {
		this.gauge = gauge;
	}

	
}
