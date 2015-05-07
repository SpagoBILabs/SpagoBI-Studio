/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

import java.util.Iterator;
import java.util.Vector;

public class Parameters {

	private Vector<Parameter> parameter;

	public Vector<Parameter> getParameter() {
		return parameter;
	}

	public void setParameter(Vector<Parameter> parameter) {
		this.parameter = parameter;
	}


	public Parameter getINParameterById(Integer id){
		Parameter toReturn = null;
		for (Iterator iterator = parameter.iterator(); iterator.hasNext() && toReturn == null;) {
			Parameter par = (Parameter) iterator.next();
			if(par.getType().equalsIgnoreCase("IN") && par.getId().equals(id)){
				toReturn = par;
			}
		}
		return toReturn;
	}
}