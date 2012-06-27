/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors.model.chart;

import it.eng.spagobi.studio.chart.utils.DrillConfiguration;

import org.dom4j.Document;
import org.eclipse.core.resources.IFile;

public class LinkableChartModel extends ChartModel {

	// ********* subtype drill Parameters ***********
	protected DrillConfiguration drillConfiguration=null;


	public DrillConfiguration getDrillConfiguration() {
		return drillConfiguration;
	}

	public void setDrillConfiguration(DrillConfiguration drillConfiguration) {
		this.drillConfiguration = drillConfiguration;
	}

	
	public LinkableChartModel(String type_, String subType_, IFile thisFile,
			Document configDocument_)
			throws Exception {
		super(type_, subType_, thisFile, configDocument_);
		// TODO Auto-generated constructor stub
	}

	
}
