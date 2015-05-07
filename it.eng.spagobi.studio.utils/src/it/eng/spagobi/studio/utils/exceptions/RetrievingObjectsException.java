/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.exceptions;

public class RetrievingObjectsException extends Exception{

	String object = null;
	
	public RetrievingObjectsException () {
		super();
		// TODO Auto-generated constructor stub
	}

	public RetrievingObjectsException (Exception e) {
		super(e);
	}
	
	public RetrievingObjectsException (String _object) {
		object = _object;	
		}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
	



	
	
}
