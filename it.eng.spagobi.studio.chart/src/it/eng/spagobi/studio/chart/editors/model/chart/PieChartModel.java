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

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PieChartModel extends LinkableChartModel {

	private static Logger logger = LoggerFactory.getLogger(PieChartModel.class);

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
		logger.debug("Check drill status");
		boolean isLinkable=isSubtypeLinkable(subType);
		logger.debug("The chart is linkable? "+Boolean.valueOf(isLinkable));
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
		logger.debug("Drill configuration section");
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
		logger.debug("Erase fields of editor");
		components.getDrillConfigurationEditor().eraseComposite();
		if(isLinkable==true){
			logger.debug("Fill drill configurations parameters");
			getDrillConfiguration().fillDrillConfigurations(type, thisDocument);
			logger.debug("re fill the fields");
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
			logger.debug("No lnkable document");				
			return false;
		}
		else{
			logger.debug("Linkable document");						
			return true;
		}
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
		toReturn+="<PIECHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();

		// Drill Configuration
		logger.debug("Drill configurations XML");
		if(drillConfiguration!=null && isSubtypeLinkable(subType)==true){
			String drillXML=drillConfiguration.toXml();
			toReturn+=drillXML;
		}

		
		toReturn+="</PIECHART>\n";

		logger.debug("Final Template is\n:" + toReturn);
		return toReturn;
	}

	
	
	
}
