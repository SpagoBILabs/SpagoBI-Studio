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
public class WidgetConfigElementLiveLine extends WidgetConfigElement {
	
	private int rangeMinValue;
	private int rangeMaxValue;
	private int stepY;
	private int domainValueNumber;
	private Vector<Integer> domainValues;
	private Vector<String> fields;
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
	 * @return the stepY
	 */
	public int getStepY() {
		return stepY;
	}
	/**
	 * @param stepY the stepY to set
	 */
	public void setStepY(int stepY) {
		this.stepY = stepY;
	}
	/**
	 * @return the domainValueNumber
	 */
	public int getDomainValueNumber() {
		return domainValueNumber;
	}
	/**
	 * @param domainValueNumber the domainValueNumber to set
	 */
	public void setDomainValueNumber(int domainValueNumber) {
		this.domainValueNumber = domainValueNumber;
	}
	/**
	 * @return the domainValues
	 */
	public Vector<Integer> getDomainValues() {
		if(domainValues == null) domainValues = new Vector<Integer>();

		return domainValues;
	}
	/**
	 * @param domainValues the domainValues to set
	 */
	public void setDomainValues(Vector<Integer> domainValues) {
		this.domainValues = domainValues;
	}
	/**
	 * @return the fields
	 */
	public Vector<String> getFields() {
		if(fields == null) fields = new Vector<String>();

		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(Vector<String> fields) {
		this.fields = fields;
	}
	

}
