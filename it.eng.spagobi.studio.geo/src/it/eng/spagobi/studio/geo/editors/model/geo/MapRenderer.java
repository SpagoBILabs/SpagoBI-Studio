/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.studio.geo.editors.model.geo;

import java.io.Serializable;

public class MapRenderer  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8609302485242818211L;
	private String className;
	private Measures measures;
	private Layers layers;
	private GuiSettings guiSettings;

	public GuiSettings getGuiSettings() {
		return guiSettings;
	}

	public void setGuiSettings(GuiSettings guiSettings) {
		this.guiSettings = guiSettings;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Measures getMeasures() {
		return measures;
	}

	public void setMeasures(Measures measures) {
		this.measures = measures;
	}

	public Layers getLayers() {
		return layers;
	}

	public void setLayers(Layers layers) {
		this.layers = layers;
	}

}
