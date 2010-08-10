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

import it.eng.spagobi.studio.geo.editors.model.geo.Column;
import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Metadata;

import java.util.Vector;

public class ColumnBO {
	public static Column setNewColumn(GEODocument geoDocument, 
									String columnId, String datasetName){
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		Metadata metadata =dmProvider.getMetadata();
		if(metadata == null){
			metadata = new Metadata();
			metadata.setDataset(datasetName);
			dmProvider.setMetadata(metadata);
		}
		Vector<Column> columns = metadata.getColumn();
		if(columns== null){
			columns = new Vector<Column>();
			metadata.setColumn(columns);
		}
		Column column = new Column();
		column.setColumnId(columnId);
		
		columns.add(column);
		return column;

	}
	public static Column getColumnByName(GEODocument geoDocument, 
			String columnId){
		Column column = null;
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		Metadata metadata =dmProvider.getMetadata();
		Vector<Column> columns = metadata.getColumn();
		if(columns != null){
			for(int i=0; i<columns.size(); i++){
				if(columns.elementAt(i).getColumnId().equals(columnId)){
					column=columns.elementAt(i);
				}
				
			}
		}
		return column;
	}

}
