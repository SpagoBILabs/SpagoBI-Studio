/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiSettings;
import it.eng.spagobi.studio.geo.editors.model.geo.Window;
import it.eng.spagobi.studio.geo.editors.model.geo.Windows;

import java.util.Vector;

public class WindowBO {
	
	public static Window setNewWindow(GEODocument geoDocument){
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
		Vector <Window> windowVect = windows.getWindow();
		if(windowVect == null){
			windowVect = new Vector<Window>();
			windows.setWindow(windowVect);
		}
		Window window = new Window();
		windowVect.add(window);
		return window;
	}
	public static Window getWindowByName(GEODocument geoDocument, String name){
		Window window = null;
		try{
			GuiSettings guiSettings = GuiSettingsBO.getGuiSettings(geoDocument);
			Windows windows = guiSettings.getWindows();
			Vector <Window> windowVect = windows.getWindow();
			for(int i=0; i<windowVect.size(); i++){
				if(windowVect.elementAt(i).getName().equalsIgnoreCase(name)){
					window = windowVect.elementAt(i);
				}
			}
		}finally{
			return window;
		}
		
	}
	public static void deleteParamByName(Window window, String paramName){
		
		Vector<GuiParam> params = window.getParams();
		if(params != null){
			for(int i=0; i<params.size(); i++){
				if(params.elementAt(i).getName()!= null && params.elementAt(i).getName().equalsIgnoreCase(paramName)){
					params.remove(i);
				}
			}
			
		}
		
	}
	public static GuiParam getParamByName(Window window, String paramName){
		
		Vector<GuiParam> params = window.getParams();
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
