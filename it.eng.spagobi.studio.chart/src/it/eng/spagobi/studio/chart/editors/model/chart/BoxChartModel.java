/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors.model.chart;

import it.eng.spagobi.studio.chart.editors.ChartEditor;
import it.eng.spagobi.studio.chart.editors.ChartEditorComponents;

import org.dom4j.Document;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoxChartModel extends ChartModel {
	private static Logger logger = LoggerFactory.getLogger(BoxChartModel.class);
	

	public void eraseSpecificParameters() {
		super.eraseSpecificParameters();
	}

	
	public BoxChartModel(String type, String subType_, IFile thisFile, Document configDocument_) throws Exception {
		super(type, subType_, thisFile, configDocument_);
	}

	
	@Override
	public void initializeEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		// TODO Auto-generated method stub
		super.initializeEditor(editor, components, toolkit, form);

	}



	@Override
	public void refreshEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		logger.debug("Erase fields of editor");
		eraseSpecificParameters();
		super.refreshEditor(editor, components, toolkit, form);

	}


	@Override
	public String toXML() {
		String toReturn="";
		logger.debug("Write XML for Model");
		toReturn = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
		if(subType==null) {
			logger.error("Sub Type not defined");
			return "";
		}

		logger.debug("General settings");

		//intestazione
		toReturn+="<BOXCHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();
		
		toReturn+="</BOXCHART>\n";

		logger.debug("Final Template is\n:" + toReturn);
		return toReturn;
	}

	
	
	
}
