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

public class PieChartModel extends LinkableChartModel {

	

	public void eraseSpecificParameters() {
		super.eraseSpecificParameters();
		drillConfiguration=new DrillConfiguration();
	}

	
	public PieChartModel(String type, String subType, IFile thisFile, Document configDocument_) throws Exception {
		super(type, subType, thisFile, configDocument_);
		drillConfiguration=new DrillConfiguration();
		if(isSubtypeLinkable(subType)){
			drillConfiguration.fillDrillConfigurations(type, thisDocument);
		}
		// Fill the drill configuration (TODO: only if present)! is enough to check if it is Linkable
		SpagoBILogger.infoLog("Check drill status");
		boolean isLinkable=isSubtypeLinkable(subType);
		SpagoBILogger.infoLog("The chart is linkable? "+Boolean.valueOf(isLinkable));
		if(isLinkable){
			drillConfiguration.fillDrillConfigurations(type, thisDocument);
		}

	}

	
	@Override
	public void initializeEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		// TODO Auto-generated method stub
		super.initializeEditor(editor, components, toolkit, form);

		// CREATE THE Drill CONFIGURATION PARAMETER: At the beginning set invisible
		//components.getDrillConfigurationEditor().eraseComposite();
		SpagoBILogger.infoLog("Drill configuration section");
		components.createDrillConfigurationSection(this,toolkit, form);		
		components.getDrillConfigurationEditor().setVisible(false);

		
		boolean isLinkable=isSubtypeLinkable(subType);
		if(isLinkable==true){
			components.getDrillConfigurationEditor().setVisible(true);
		}
		else{
			components.getDrillConfigurationEditor().setVisible(false);
		}


	}



	@Override
	public void refreshEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		eraseSpecificParameters();

		super.refreshEditor(editor, components, toolkit, form);
		
		boolean isLinkable=isSubtypeLinkable(subType);
		SpagoBILogger.infoLog("Erase fields of editor");
		components.getDrillConfigurationEditor().eraseComposite();
		if(isLinkable==true){
			SpagoBILogger.infoLog("Fill drill configurations parameters");
			getDrillConfiguration().fillDrillConfigurations(type, thisDocument);
			SpagoBILogger.infoLog("re fill the fields");
			components.getDrillConfigurationEditor().refillFieldsDrillConfiguration(drillConfiguration, null, toolkit, form);							
			components.getDrillConfigurationEditor().setVisible(true);
		}
		else{
			components.getDrillConfigurationEditor().setVisible(false);
		}

	}

	/** 
	 * 
	 * @param chartSubType
	 * @param configDocument
	 * @param templateDocument
	 * @throws Exception
	 * 
	 * This method read the chart template and returns if it is linkable: it is if config document as a drill tag
	 */

	public boolean isSubtypeLinkable(String chartSubType){
		// check the type and search for the root
		String upperCaseNameSl=getType().toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";

		// Get the node configuration
		Node specificConfig = configDocument.selectSingleNode("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='"+chartSubType.trim()+"']");

		Node drillNode = specificConfig.selectSingleNode("//"+upperCaseNameSl+"[@name='"+chartSubType+"']/DRILL");

		if(drillNode==null){
			SpagoBILogger.infoLog("No lnkable document");				
			return false;
		}
		else{
			SpagoBILogger.infoLog("Linkable document");						
			return true;
		}
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
		toReturn+="<PIECHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();

		// Drill Configuration
		SpagoBILogger.infoLog("Drill configurations XML");
		if(drillConfiguration!=null && isSubtypeLinkable(subType)==true){
			String drillXML=drillConfiguration.toXml();
			toReturn+=drillXML;
		}

		
		toReturn+="</PIECHART>\n";

		SpagoBILogger.infoLog("Final Template is\n:" + toReturn);
		return toReturn;
	}

	
	
	
}
