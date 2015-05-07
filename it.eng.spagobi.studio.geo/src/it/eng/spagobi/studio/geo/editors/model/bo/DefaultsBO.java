/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.Defaults;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiSettings;
import it.eng.spagobi.studio.geo.editors.model.geo.Windows;

import java.util.Vector;

public class DefaultsBO {
	public static Defaults setNewDefaults(GEODocument geoDocument){
		GuiSettings guiSettings = GuiSettingsBO.getGuiSettings(geoDocument);
		if(guiSettings == null){
			guiSettings = new GuiSettings();
			geoDocument.getMapRenderer().setGuiSettings(guiSettings);
		}		
		Windows windows = guiSettings.getWindows();
		if(windows == null){
			windows = new Windows();
			guiSettings.setWindows(windows);			
		}
		Defaults defaults = windows.getDefaults();
		if(defaults == null){
			defaults = new Defaults();
			windows.setDefaults(defaults);
		}
		Vector<GuiParam> params = defaults.getParams();
		if(params == null){
			params = new Vector<GuiParam>();
			defaults.setParams(params);
		}
		return defaults;
	}
}
