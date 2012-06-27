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
