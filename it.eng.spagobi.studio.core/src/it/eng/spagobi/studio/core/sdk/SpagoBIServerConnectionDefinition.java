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
import it.eng.spagobi.studio.core.bo.Server;
import it.eng.spagobi.studio.core.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.core.preferences.PreferenceConstants;
import it.eng.spagobi.studio.core.properties.PropertyPage;
import it.eng.spagobi.studio.core.services.server.ServerHandler;

import org.eclipse.jface.preference.IPreferenceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBIServerConnectionDefinition {

	private static Logger logger = LoggerFactory.getLogger(SpagoBIServerConnectionDefinition.class);

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

	public SpagoBIServerConnectionDefinition(String serverUrl, String userName,String password) {
		this.serverUrl = serverUrl;
		this.userName = userName;
		this.password = password;
	}

	public static	SpagoBIServerConnectionDefinition createErrorConnection(){
		return new SpagoBIServerConnectionDefinition("Url not defined", "User not defined", "password not defined");

	}

	public SpagoBIServerConnectionDefinition(String projectname) throws NoActiveServerException {
		logger.debug("IN");
		Server server = new ServerHandler().getCurrentActiveServer(projectname);
		if(server == null){
			throw new NoActiveServerException();
		}
		
		serverUrl = server.getUrl();
		userName = server.getUser();
		password = server.getPassword();

		logger.debug("OUT");

	}

}
