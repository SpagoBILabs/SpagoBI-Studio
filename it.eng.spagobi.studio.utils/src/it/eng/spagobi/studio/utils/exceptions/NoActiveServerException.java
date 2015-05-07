/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.exceptions;

public class NoActiveServerException extends Exception{

	boolean noServer = false;
	
	public NoActiveServerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoActiveServerException(Exception e) {
		super(e);
	}

	public boolean isNoServer() {
		return noServer;
	}

	public void setNoServer(boolean noServer) {
		this.noServer = noServer;
	}

	

	
	
}
