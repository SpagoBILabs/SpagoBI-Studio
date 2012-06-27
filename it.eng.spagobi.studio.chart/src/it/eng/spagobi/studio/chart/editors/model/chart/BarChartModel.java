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
import it.eng.spagobi.studio.chart.utils.DrillConfiguration;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarChartModel extends LinkableChartModel {

	private static Logger logger = LoggerFactory.getLogger(BarChartModel.class);

	@Override
	public void eraseSpecificParameters() {
		super.eraseSpecificParameters();
		drillConfiguration=new DrillConfiguration();
	}

	public BarChartModel(String type, String subType_, IFile thisFile, Document configDocument_) throws Exception {
		super(type, subType_, thisFile, configDocument_);
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
		toReturn+="<BARCHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";


		toReturn+=super.toXML();

		// Drill Configuration
		logger.debug("Drill configurations XML");
		if(drillConfiguration!=null && isSubtypeLinkable(subType)==true){
			String drillXML=drillConfiguration.toXml();
			toReturn+=drillXML;
		}

		toReturn+="</BARCHART>\n";

		logger.debug("Final Template is\n:" + toReturn);
		return toReturn;

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
		components.createDrillConfigurationSection(this, toolkit, form);		
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






}