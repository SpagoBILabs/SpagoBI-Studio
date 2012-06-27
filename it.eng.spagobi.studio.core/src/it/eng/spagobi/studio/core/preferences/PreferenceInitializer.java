/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
