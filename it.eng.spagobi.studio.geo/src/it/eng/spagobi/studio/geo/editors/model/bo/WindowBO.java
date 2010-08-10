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
