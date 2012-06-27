/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.exceptions;

public class NotAllowedOperationStudioException extends Exception{

	boolean notAllowed = false;
	
	public NotAllowedOperationStudioException () {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotAllowedOperationStudioException (Exception e) {
		super(e);
	}

	public boolean isNotAllowed() {
		return notAllowed;
	}

	public void setNotAllowed(boolean notAllowed) {
		this.notAllowed = notAllowed;
	}



	
	
}
