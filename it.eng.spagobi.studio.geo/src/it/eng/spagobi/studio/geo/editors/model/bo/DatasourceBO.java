/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
