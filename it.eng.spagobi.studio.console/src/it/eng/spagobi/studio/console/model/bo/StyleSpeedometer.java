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
public class StyleSpeedometer {
	
	private TitleSpeedometer title;
	private Font font;
	private Padding padding;
	private Header header;
	private HeaderValue headerValue;
	/**
	 * @return the title
	 */
	public TitleSpeedometer getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(TitleSpeedometer title) {
		this.title = title;
	}
	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}
	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
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
	 * @return the header
	 */
	public Header getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
	}
	/**
	 * @return the headerValue
	 */
	public HeaderValue getHeaderValue() {
		return headerValue;
	}
	/**
	 * @param headerValue the headerValue to set
	 */
	public void setHeaderValue(HeaderValue headerValue) {
		this.headerValue = headerValue;
	}
	
	

}
