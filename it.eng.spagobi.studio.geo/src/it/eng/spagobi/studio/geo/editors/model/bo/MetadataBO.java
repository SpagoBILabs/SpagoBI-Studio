/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.Column;
import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Metadata;

import java.util.Vector;

public class MetadataBO {
	
	public static void setNewMetadata(GEODocument geoDocument, String datasetName) {
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		Metadata metadata = new Metadata();
		metadata.setDataset(datasetName);
		dmProvider.setMetadata(metadata);

		//add columns
		Vector<Column> column= new Vector<Column>();
		metadata.setColumn(column);
	}	
	public static Metadata getMetadata(GEODocument geoDocument){
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		return dmProvider.getMetadata();
	}
	public static Column geoidColumnExists(GEODocument geoDocument){
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		Metadata metadata = dmProvider.getMetadata();
		if(metadata != null && metadata.getColumn() != null){
			for(int i=0; i<metadata.getColumn().size(); i++){
				Column col = metadata.getColumn().elementAt(i);
				if(col.getType() != null && col.getType().equalsIgnoreCase("geoid")){
					return col;
				}
			}
		}
		return null;
	}
}
