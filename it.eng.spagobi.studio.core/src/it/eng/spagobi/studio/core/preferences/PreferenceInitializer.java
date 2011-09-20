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
package it.eng.spagobi.studio.core.preferences;

import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(SpagoBIStudioConstants.SPAGOBI_SERVER_URL, "http://localhost:8080/SpagoBI");
		store.setDefault(SpagoBIStudioConstants.SPABOGI_USER_NAME, "biadmin");
		store.setDefault(SpagoBIStudioConstants.SPABOGI_USER_PASSWORD,
				"biadmin");
		store.setDefault(SpagoBIStudioConstants.IREPORT_EXEC_FILE,
		"C:/Programmi/JasperSoft/iReport-3.0.0/iReport.exe");

	}

}
