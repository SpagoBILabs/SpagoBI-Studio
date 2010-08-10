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

import it.eng.spagobi.studio.geo.editors.model.geo.Dataset;
import it.eng.spagobi.studio.geo.editors.model.geo.Datasource;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;

public class DatasourceBO {

/*	public static void addDatasource(Datasource datasource, Dataset dataset, String type,
			String driver, String url, String user, String password) {
		datasource.setDriver(driver);
		datasource.setPassword(password);
		datasource.setType(type);
		datasource.setUrl(url);
		datasource.setUser(user);
	}
	
	public static Datasource setNewDatasource(GEODocument geoDocument){
		Dataset dataset = geoDocument.getDatamartProvider().getDataset();
		if(dataset == null){
			dataset = new Dataset();
			geoDocument.getDatamartProvider().setDataset(dataset);
		}
		Datasource datasource = dataset.getDatasource();
		if(datasource == null){
			datasource = new Datasource();
			dataset.setDatasource(datasource);
		}
		return datasource;
	}*/

}
