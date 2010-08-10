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
package it.eng.spagobi.studio.core.sdk;

import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.core.preferences.PreferenceConstants;

import org.eclipse.jface.preference.IPreferenceStore;

public class SpagoBIServerConnectionDefinition {
	
	private String serverUrl;
	private String userName;
	private String password;
	
	public String getServerUrl() {
		return serverUrl;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public SpagoBIServerConnectionDefinition() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		serverUrl = store.getString(PreferenceConstants.SPAGOBI_SERVER_URL);
		userName = store.getString(PreferenceConstants.SPABOGI_USER_NAME);
		password = store.getString(PreferenceConstants.SPABOGI_USER_PASSWORD);
	}
	
}
