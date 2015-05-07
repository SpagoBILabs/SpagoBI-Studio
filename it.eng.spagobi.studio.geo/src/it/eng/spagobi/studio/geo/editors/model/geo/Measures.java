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

public class Measures  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3393925648919400182L;
	private Vector<KPI> kpi;
	private String defaultKpi;

	public String getDefaultKpi() {
		return defaultKpi;
	}

	public void setDefaultKpi(String defaultKpi) {
		this.defaultKpi = defaultKpi;
	}

	public Vector<KPI> getKpi() {
		return kpi;
	}

	public void setKpi(Vector<KPI> kpi) {
		this.kpi = kpi;
	}

}
