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
package it.eng.spagobi.studio.console.editors.internal;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class MultiLedsSettingDialogTableRow {
	private String header;
	private String name;
	private int rangeMaxValue;
	private int rangeMinValue;
	private int firstIntervalUb;
	private int secondIntervalUb;
	
	
	
	/**
	 * @param header
	 * @param name
	 * @param rangeMaxValue
	 * @param rangeMinValue
	 * @param firstIntervalUb
	 * @param secondIntervalUb
	 */
	public MultiLedsSettingDialogTableRow(String header, String name,
			int rangeMaxValue, int rangeMinValue, int firstIntervalUb,
			int secondIntervalUb) {
		this.header = header;
		this.name = name;
		this.rangeMaxValue = rangeMaxValue;
		this.rangeMinValue = rangeMinValue;
		this.firstIntervalUb = firstIntervalUb;
		this.secondIntervalUb = secondIntervalUb;
	}
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
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
	 * @return the firstIntervalUb
	 */
	public int getFirstIntervalUb() {
		return firstIntervalUb;
	}
	/**
	 * @param firstIntervalUb the firstIntervalUb to set
	 */
	public void setFirstIntervalUb(int firstIntervalUb) {
		this.firstIntervalUb = firstIntervalUb;
	}
	/**
	 * @return the secondIntervalUb
	 */
	public int getSecondIntervalUb() {
		return secondIntervalUb;
	}
	/**
	 * @param secondIntervalUb the secondIntervalUb to set
	 */
	public void setSecondIntervalUb(int secondIntervalUb) {
		this.secondIntervalUb = secondIntervalUb;
	}
	
	
}
