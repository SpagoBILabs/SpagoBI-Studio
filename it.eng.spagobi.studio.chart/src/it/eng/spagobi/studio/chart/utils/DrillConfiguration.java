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
package it.eng.spagobi.studio.chart.utils;

import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;
import it.eng.spagobi.studio.core.log.SpagoBILogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

public class DrillConfiguration {

	String url=null;
	String categoryUrlName=null;
	String seriesUrlName=null;

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
		SpagoBILogger.infoLog("Recording and Filling te drill configurations");

		Node drill=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL");
		if(drill!=null){
			ChartEditorUtils.print("", drill);
			String document=drill.valueOf("@document");
			if(document!=null)url=document;
		}
		if(url!=null){
			SpagoBILogger.infoLog("Url for drill is: "+url);
		}

		Node catUrlName=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL/PARAM[@name='categoryurlname']");
		if(catUrlName!=null){
			String catUrlNameVal=catUrlName.valueOf("@value");
			if(catUrlNameVal!=null){
				categoryUrlName=catUrlNameVal;
			}
		}

		if(categoryUrlName!=null){
			SpagoBILogger.infoLog("Category name label is: "+categoryUrlName );
		}

		Node serUrlName=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/DRILL/PARAM[@name='seriesurlname']");
		if(serUrlName!=null){
			String serUrlNameVal=serUrlName.valueOf("@value");
			if(serUrlNameVal!=null){
				seriesUrlName=serUrlNameVal;
			}
		}

		if(seriesUrlName!=null){		
			SpagoBILogger.infoLog("Serie name label is: "+seriesUrlName );
		}
		SpagoBILogger.infoLog("check other parameters for drill");

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
