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
package it.eng.spagobi.studio.chart.editors.model.chart;

import it.eng.spagobi.studio.chart.editors.ChartEditor;
import it.eng.spagobi.studio.chart.editors.ChartEditorComponents;
import it.eng.spagobi.studio.chart.utils.ScatterRangeMarker;

import org.dom4j.Document;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScatterChartModel extends ChartModel {

	ScatterRangeMarker scatterRangeMarker;
	private static Logger logger = LoggerFactory.getLogger(ScatterChartModel.class);

	public void eraseSpecificParameters() {
		scatterRangeMarker=new ScatterRangeMarker();
		super.eraseSpecificParameters();
	}


	public ScatterChartModel(String type, String subType_, IFile thisFile, Document configDocument_) throws Exception {
		super(type, subType_, thisFile, configDocument_);
		scatterRangeMarker=new ScatterRangeMarker();
		scatterRangeMarker.fillScatterRangeRankConfigurations(type, thisDocument);
	}


	@Override
	public void initializeEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		// TODO Auto-generated method stub
		super.initializeEditor(editor, components, toolkit, form);
		components.createScatterRangeMarkerSection(this, toolkit, form);
		components.getScatterRangeMarkerEditor().setVisible(true);

	}



	@Override
	public void refreshEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		logger.debug("Erase fields of editor");
		eraseSpecificParameters();
		super.refreshEditor(editor, components, toolkit, form);
		components.getScatterRangeMarkerEditor().eraseComposite();
		getScatterRangeMarker().fillScatterRangeRankConfigurations(type, thisDocument);
		components.getScatterRangeMarkerEditor().refillFieldsScatterRangeMarker(this,editor, toolkit, form);							
		components.getScatterRangeMarkerEditor().setVisible(true);

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
		toReturn+="<SCATTERCHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();
		
		toReturn+=scatterRangeMarker.toXML();

		toReturn+="</SCATTERCHART>\n";

		logger.debug("Final Template is\n:" + toReturn);
		return toReturn;
	}


	public ScatterRangeMarker getScatterRangeMarker() {
		return scatterRangeMarker;
	}


	public void setScatterRangeMarker(ScatterRangeMarker scatterRangeMarker) {
		this.scatterRangeMarker = scatterRangeMarker;
	}



}
