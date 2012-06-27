/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import java.util.Vector;

import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiSettings;

public class GuiSettingsBO {
	public static GuiSettings getGuiSettings(GEODocument geoDocument){
		return geoDocument.getMapRenderer().getGuiSettings();
	}
	public static void deleteParamByName(GEODocument geoDocument, String paramName){
		GuiSettings guiSettings= geoDocument.getMapRenderer().getGuiSettings();
		Vector<GuiParam> params = guiSettings.getParams();
		if(params != null){
			for(int i=0; i<params.size(); i++){
				if(params.elementAt(i).getName()!= null && params.elementAt(i).getName().equalsIgnoreCase(paramName)){
					params.remove(i);
				}
			}
			
		}
	}
	public static GuiParam getParamByName(GEODocument geoDocument, String paramName){
		GuiSettings guiSettings= geoDocument.getMapRenderer().getGuiSettings();
		Vector<GuiParam> params = guiSettings.getParams();
		if(params != null){
			for(int i=0; i<params.size(); i++){
				if(params.elementAt(i).getName()!= null && params.elementAt(i).getName().equalsIgnoreCase(paramName)){
					return params.elementAt(i);
				}
			}
			
		}
		return null;
	}
}
