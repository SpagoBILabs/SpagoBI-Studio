/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
