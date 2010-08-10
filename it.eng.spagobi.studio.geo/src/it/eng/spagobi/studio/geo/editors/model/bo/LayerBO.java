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

public class LayerBO {
	public static Layer setNewLayer(GEODocument geoDocument, String featureId,
			String mapName) {
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		Layers layers = mapRenderer.getLayers();
		if (layers == null) {
			layers = new Layers();
			layers.setMapName(mapName);
			mapRenderer.setLayers(layers);
		}
		Vector<Layer> layer = layers.getLayer();
		if (layer == null) {
			layer = new Vector<Layer>();
			layers.setLayer(layer);
		}
		Layer lay = new Layer();
		lay.setName(featureId);

		layer.add(lay);
		return lay;

	}

	public static Layer getLayerByName(GEODocument geoDocument, String featureId) {
		Layer layerRet = null;
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		Layers layers = mapRenderer.getLayers();
		Vector<Layer> layer = layers.getLayer();
		if(layer != null){
			for (int i = 0; i < layer.size(); i++) {
				if (layer.elementAt(i).getName().equals(featureId)) {
					layerRet = layer.elementAt(i);
				}
	
			}
		}
		return layerRet;
	}

}
