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
public class WidgetConfigElementSemaphore extends WidgetConfigElement {
	private int paramWidth;
	private int paramHeight;
	private int rangeMinValue;
	private int rangeMaxValue;
	private int rangeFirstInterval;
	private int rangeSecondInterval;
	private String field;
	private StyleSemaphore styles;
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
	 * @return the rangeMinValue
	 */
	public int getRangeMinValue() {
		return rangeMinValue;
	}
	/**
	 * @param rangeMinValue the rangeMinValue to set
	 */
	public void setRangeMinValue(int rangeMinValue) {
		this.rangeMinValue = rangeMinValue;
	}
	/**
	 * @return the rangeMaxValue
	 */
	public int getRangeMaxValue() {
		return rangeMaxValue;
	}
	/**
	 * @param rangeMaxValue the rangeMaxValue to set
	 */
	public void setRangeMaxValue(int rangeMaxValue) {
		this.rangeMaxValue = rangeMaxValue;
	}
	/**
	 * @return the rangeFirstInterval
	 */
	public int getRangeFirstInterval() {
		return rangeFirstInterval;
	}
	/**
	 * @param rangeFirstInterval the rangeFirstInterval to set
	 */
	public void setRangeFirstInterval(int rangeFirstInterval) {
		this.rangeFirstInterval = rangeFirstInterval;
	}
	/**
	 * @return the rangeSecondInterval
	 */
	public int getRangeSecondInterval() {
		return rangeSecondInterval;
	}
	/**
	 * @param rangeSecondInterval the rangeSecondInterval to set
	 */
	public void setRangeSecondInterval(int rangeSecondInterval) {
		this.rangeSecondInterval = rangeSecondInterval;
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
	 * @return the styles
	 */
	public StyleSemaphore getStyles() {
		return styles;
	}
	/**
	 * @param styles the styles to set
	 */
	public void setStyles(StyleSemaphore styles) {
		this.styles = styles;
	}
	
	

}
