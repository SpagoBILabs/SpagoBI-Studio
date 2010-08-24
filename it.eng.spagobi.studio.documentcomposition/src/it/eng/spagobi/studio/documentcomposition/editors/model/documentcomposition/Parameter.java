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
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;


public class Parameter {	
	private ParameterBO bo = new ParameterBO();
	private static long idCounter = 0;

	public static synchronized String createID(String lastId)
	{
		long last = Integer.valueOf(lastId).intValue();
		last = last + (idCounter++);
		return String.valueOf(last);
	}


	public Parameter(DocumentComposition docComp) {

		this.id = createID(bo.getLastId(docComp));
		// TODO Auto-generated constructor stub
	}

	private String type;
	private String sbiParLabel;
	private String defaultVal;
	private Refresh refresh;

	//per gestire modifica/cancellazione parametro
	private String id ;

	//attributo che non verrà salvato nell'xml del template ma individua la navigazione
	private String navigationName;


	public String getNavigationName() {
		return navigationName;
	}
	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}
	public Refresh getRefresh() {
		return refresh;
	}
	public void setRefresh(Refresh refresh) {
		this.refresh = refresh;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSbiParLabel() {
		return sbiParLabel;
	}
	public void setSbiParLabel(String sbiParLabel) {
		this.sbiParLabel = sbiParLabel;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
