/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.utils;

import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrillConfiguration {

	String url=null;
	String categoryUrlName=null;
	String seriesUrlName=null;
	private static Logger logger = LoggerFactory.getLogger(DrillConfiguration.class);
	HashMap<String, DrillParameters> drillParameters=new HashMap<String, DrillParameters>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategoryUrlName() {
		return categoryUrlName;
	}

	public void setCategoryUrlName(String categoryUrlName) {
		this.categoryUrlName = categoryUrlName;
	}

	public String getSeriesUrlName() {
		return seriesUrlName;
	}

	public void setSeriesUrlName(String seriesUrlName) {
		this.seriesUrlName = seriesUrlName;
	}

	public HashMap<String, DrillParameters> getDrillParameters() {
		return drillParameters;
	}

	public void setDrillParameters(HashMap<String, DrillParameters> drillParameters) {
		this.drillParameters = drillParameters;
	}


	public String toXml(){
		String toReturn="<DRILL ";
		String docDef=url!=null ? url : "";
		toReturn+="document=\""+docDef+"\" ";
		toReturn+=">\n";
		if(categoryUrlName!=null && !categoryUrlName.equalsIgnoreCase("")){
			toReturn+="<PARAM name=\"categoryurlname\" value=\""+categoryUrlName+"\"/>\n";	
		}
		if(seriesUrlName!=null && !seriesUrlName.equalsIgnoreCase("")){
			toReturn+="<PARAM name=\"seriesurlname\" value=\""+seriesUrlName+"\"/>\n";	
		}

		// run all parameters
		for (Iterator iterator = drillParameters.keySet().iterator(); iterator.hasNext();) {
			String namePar = (String) iterator.next();
			DrillParameters drillPar=drillParameters.get(namePar);
			if(drillPar!=null){
				toReturn+="		";
				String drillParXml=drillPar.toXml();
				toReturn+=drillParXml;
			}
		}
		toReturn+="</DRILL>\n ";
		return toReturn;


	}


	public void fillDrillConfigurations(String type, Document thisDocument){
		logger.debug("Recording and Filling te drill configurations");

		Node drill=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL");
		if(drill!=null){
			ChartEditorUtils.print("", drill);
			String document=drill.valueOf("@document");
			if(document!=null)url=document;
		}
		if(url!=null){
			logger.debug("Url for drill is: "+url);
		}

		Node catUrlName=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL/PARAM[@name='categoryurlname']");
		if(catUrlName!=null){
			String catUrlNameVal=catUrlName.valueOf("@value");
			if(catUrlNameVal!=null){
				categoryUrlName=catUrlNameVal;
			}
		}

		if(categoryUrlName!=null){
			logger.debug("Category name label is: "+categoryUrlName );
		}

		Node serUrlName=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL/PARAM[@name='seriesurlname']");
		if(serUrlName!=null){
			String serUrlNameVal=serUrlName.valueOf("@value");
			if(serUrlNameVal!=null){
				seriesUrlName=serUrlNameVal;
			}
		}

		if(seriesUrlName!=null){		
			logger.debug("Serie name label is: "+seriesUrlName );
		}
		logger.debug("check other parameters for drill");

		ChartEditorUtils.print("", thisDocument);

		//Node hasDrill=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL");
		// If has no drill does not go to search on template, otherwise yes
		if(drill!=null){
			List<Node> listOthers=thisDocument.selectNodes("//"+type.toUpperCase()+"/DRILL/PARAM");

			for (Iterator iterator = listOthers.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				String nameParam=node.valueOf("@name");
				String valueParam=node.valueOf("@value");
				valueParam=valueParam!=null ? valueParam : "";
				String typeParam=node.valueOf("@type");
				typeParam=typeParam!=null ? typeParam : DrillParameters.ABSOLUTE;
				if(!nameParam.equalsIgnoreCase("categoryurlname") && !nameParam.equalsIgnoreCase("seriesurlname")){
					if(!drillParameters.containsKey(nameParam)){
						DrillParameters drillParams=new DrillParameters(nameParam,valueParam,typeParam);
						drillParameters.put(nameParam, drillParams);	
					}
					else{
						DrillParameters drillParams=drillParameters.get(nameParam);
						drillParams.setType(typeParam);
						drillParams.setValue(valueParam);
					}
				}
			}
		}

	}




}
