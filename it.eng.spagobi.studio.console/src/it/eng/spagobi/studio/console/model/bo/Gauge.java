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
public class Gauge {
	private Padding padding;
	private Colors colors;
	private Led led;
	private TitleGauge title;
	private Value value;
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
	 * @return the colors
	 */
	public Colors getColors() {
		return colors;
	}
	/**
	 * @param colors the colors to set
	 */
	public void setColors(Colors colors) {
		this.colors = colors;
	}
	/**
	 * @return the led
	 */
	public Led getLed() {
		return led;
	}
	/**
	 * @param led the led to set
	 */
	public void setLed(Led led) {
		this.led = led;
	}
	/**
	 * @return the title
	 */
	public TitleGauge getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(TitleGauge title) {
		this.title = title;
	}
	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Value value) {
		this.value = value;
	}
	
	

}
