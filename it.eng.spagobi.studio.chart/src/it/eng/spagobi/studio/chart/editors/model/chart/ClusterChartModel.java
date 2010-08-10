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
import it.eng.spagobi.studio.chart.utils.DrillConfiguration;
import it.eng.spagobi.studio.core.log.SpagoBILogger;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ClusterChartModel extends ChartModel {

	

	public void eraseSpecificParameters() {
		super.eraseSpecificParameters();
	}

	
	public ClusterChartModel(String type, String subType_, IFile thisFile, Document configDocument_) throws Exception {
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
		SpagoBILogger.infoLog("Erase fields of editor");
		eraseSpecificParameters();
		super.refreshEditor(editor, components, toolkit, form);
	}


	@Override
	public String toXML() {
		String toReturn="";
		SpagoBILogger.infoLog("Write XML for Model");
		toReturn = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
		if(subType==null) {
			SpagoBILogger.errorLog("Sub Type not defined",null);
			return "";
		}

		SpagoBILogger.infoLog("General settings");

		//intestazione
		toReturn+="<CLUSTERCHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();
		
		toReturn+="</CLUSTERCHART>\n";

		SpagoBILogger.infoLog("Final Template is\n:" + toReturn);
		return toReturn;
	}

	
	
	
}
