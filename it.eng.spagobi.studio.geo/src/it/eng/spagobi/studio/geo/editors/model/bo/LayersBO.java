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
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Layer;
import it.eng.spagobi.studio.geo.editors.model.geo.Layers;
import it.eng.spagobi.studio.geo.editors.model.geo.MapRenderer;

import java.util.Vector;

public class LayersBO {
	public static Layers getLayers(GEODocument geoDocument){
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		return mapRenderer.getLayers();
	}
	
	public static void setNewLayers(GEODocument geoDocument, String mapName) {
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		Layers layers = new Layers();
		layers.setMapName(mapName);
		mapRenderer.setLayers(layers);

		//add columns
		Vector<Layer> layer= new Vector<Layer>();
		layers.setLayer(layer);
	}
}
