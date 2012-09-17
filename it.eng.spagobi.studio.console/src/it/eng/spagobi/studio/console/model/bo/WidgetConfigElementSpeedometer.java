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
public class WidgetConfigElementSpeedometer extends WidgetConfigElement {
	
	private int paramWidth;
	private int paramHeight;
	private int minValue;
	private int maxValue;
	private int lowValue;
	private int highValue;
	private String field;
	private StyleSpeedometer style;
	/**
	 * @return the paramWidth
	 */
	public int getParamWidth() {
		return paramWidth;
	}
	/**
	 * @param paramWidth the paramWidth to set
	 */
	public void setParamWidth(int paramWidth) {
		this.paramWidth = paramWidth;
	}
	/**
	 * @return the paramHeight
	 */
	public int getParamHeight() {
		return paramHeight;
	}
	/**
	 * @param paramHeight the paramHeight to set
	 */
	public void setParamHeight(int paramHeight) {
		this.paramHeight = paramHeight;
	}
	/**
	 * @return the minValue
	 */
	public int getMinValue() {
		return minValue;
	}
	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	/**
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}
	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	/**
	 * @return the lowValue
	 */
	public int getLowValue() {
		return lowValue;
	}
	/**
	 * @param lowValue the lowValue to set
	 */
	public void setLowValue(int lowValue) {
		this.lowValue = lowValue;
	}
	/**
	 * @return the highValue
	 */
	public int getHighValue() {
		return highValue;
	}
	/**
	 * @param highValue the highValue to set
	 */
	public void setHighValue(int highValue) {
		this.highValue = highValue;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return the style
	 */
	public StyleSpeedometer getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(StyleSpeedometer style) {
		this.style = style;
	}

	
}
