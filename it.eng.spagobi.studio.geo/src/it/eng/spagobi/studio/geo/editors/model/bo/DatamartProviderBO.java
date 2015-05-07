/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;

public class DatamartProviderBO {
	public static void setHierarchy(GEODocument geoDocument,
			String hierarchyName, String levelName){
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		dmProvider.setHierarchy(hierarchyName);
		dmProvider.setLevel(levelName);
	}
}
