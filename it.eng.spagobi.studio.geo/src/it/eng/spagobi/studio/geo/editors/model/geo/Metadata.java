/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.geo;

import java.io.Serializable;
import java.util.Vector;

public class Metadata  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8324517477227898967L;
	private Vector<Column> column;
	private String dataset;

	public Vector<Column> getColumn() {
		return column;
	}

	public void setColumn(Vector<Column> column) {
		this.column = column;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}


}
