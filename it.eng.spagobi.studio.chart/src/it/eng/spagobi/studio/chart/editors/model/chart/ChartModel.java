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
import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;
import it.eng.spagobi.studio.chart.utils.SeriePersonalization;
import it.eng.spagobi.studio.chart.utils.Style;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ChartModel {

	private static Logger logger = LoggerFactory.getLogger(ChartModel.class);
	
	public static final String CHART_INFO_FILE = "it/eng/spagobi/studio/chart/editors/model/chart/chartsInformation.xml";
	public static final String BARCHART_INFO_FILE = "it/eng/spagobi/studio/chart/editors/model/chart/barChartsConfig.xml";

	protected String title;
	protected String subTitle;
	protected String type;
	protected String subType;
	protected Dimension dimension;
	protected RGB backgroundColor;

	protected String configFilePath;
	protected Document configDocument;
	protected Document thisDocument;

	protected ChartEditor editor;

	protected Integer sdkDataSetId;

	// ********* Configuration Parameters ***********
	//protected HashMap<String, Object> confParametersValues=new HashMap<String, Object>();
	// parameters to be designed in Editor, each in his section:  Parameter -> Section
	protected TreeMap<String, ArrayList<String>> confSectionParametersEditor=new TreeMap<String, ArrayList<String>>();

	// parameters to be designed in Editor, each in his section:  Parameter -> Section
	protected TreeMap<String, ArrayList<String>> confSpecificSectionParametersEditor=new TreeMap<String, ArrayList<String>>();



	// Series COlors and series Labels Map:    SERIE => COLOR  and SERIE => LABEL
	HashMap<String, SeriePersonalization> seriesPersonalizationHashMap;
	Vector<RGB> seriesOrderPersonalizationVector;
	boolean seriesLabelPersonalization=false;
	boolean seriesColorPersonalization=false;
	boolean seriesOrderColorPersonalization=false;
	boolean seriesDrawPersonalization=false;
	boolean seriesScalesPersonalization=false;

	// configurationParameters
	protected HashMap<String, Parameter> confParametersEditor=new HashMap<String, Parameter>();

	// configurationParameters
	protected HashMap<String, Parameter> confSpecificParametersEditor=new HashMap<String, Parameter>();


	// Map that records the name of the style and the Style Object
	//protected HashMap<String, Style> styleParametersValues= new HashMap<String, Style>();
	protected HashMap<String, Style> styleParametersEditors= new HashMap<String, Style>();
	protected boolean legendPositionStyle= false;
	protected String legendPositionValue= null;

	// ********* Style Parameters ***********


	boolean displayTitleBar;

	public class Dimension {
		private int width;
		private int height;
		public Dimension () {}
		public Dimension (int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}


	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}



	/**
	 * 	The constuctor search in the present file and fill all the field with present data
	 * @param type
	 * @param file
	 * @throws Exception
	 */

	public ChartModel(String type_, String subType_, IFile thisFile, Document configDocument_) throws Exception {
		logger.debug("Start model constructor");

		// Initialise some fields
		this.type=type_;
		this.subType=subType_;
		// This is like BarchartsConfig.xml
		this.configDocument=configDocument_;

		// Read the present file
		InputStream thisIs = null;
		thisIs = thisFile.getContents();
		SAXReader reader = new SAXReader();
		thisDocument = reader.read(thisIs);

		// Get the title
		String typeUpperCase=type.toUpperCase();
		Node chart = thisDocument.selectSingleNode("//"+typeUpperCase);
		if (chart == null) {
			logger.error("Error in reading the actual file root");
			throw new Exception("xml not valid");
		}

		title = chart.valueOf("@name");
		if(title!=null && !title.equalsIgnoreCase("")){
			logger.debug("Title is "+title!=null ? title : "");
		}


		Node subTitleNode = thisDocument.selectSingleNode("//"+typeUpperCase+"/STYLE_SUBTITLE");
		if(subTitleNode!=null){
			String subTitleS=subTitleNode.valueOf("@name");
			if(subTitleS!=null){
				subTitle=subTitleS;
			}
		}
		if(subTitle==null)subTitle="";

		// finds the dimension
		String widthStr = null;
		String heightStr =null;
		Node dimensioneNode = thisDocument.selectSingleNode("//"+typeUpperCase+"/DIMENSION");
		if (dimensioneNode != null) {
			widthStr = dimensioneNode.valueOf("@width");
			heightStr = dimensioneNode.valueOf("@height");
		}
		else{
			Node heightNode=configDocument.selectSingleNode("//"+type.toUpperCase()+"S/"+type.toUpperCase()+"/DIMENSIONS/DIMENSION[@name='height']");
			if(heightNode!=null)heightStr=heightNode.valueOf("@defaultValue");
			Node widthNode=configDocument.selectSingleNode("//"+type.toUpperCase()+"S/"+type.toUpperCase()+"/DIMENSIONS/DIMENSION[@name='width']");
			if(widthNode!=null)widthStr=widthNode.valueOf("@defaultValue");
			if(heightStr==null || heightStr.equals(""))heightStr="400";
			if(widthStr==null || widthStr.equals(""))widthStr="400";			
		}

		int width;
		int height;
		try {
			width = Integer.parseInt(widthStr);
			height = Integer.parseInt(heightStr);
		} catch (NumberFormatException nfe) {
			logger.error("Dimensions value not number",nfe);
			throw new Exception("Dimension not valid");
		}
		Dimension dimension = new Dimension(width, height);
		setDimension(dimension);
		logger.debug("Dimensions set");


		// find the backround Color
		String colorString="";
		Node colorNode=thisDocument.selectSingleNode("//"+typeUpperCase+"/COLORS");
		if(colorNode!=null){
			colorString=colorNode.valueOf("@background");
		}
		if(colorString==null || colorString.equals("")){
			colorNode=configDocument.selectSingleNode("//"+type.toUpperCase()+"S/"+type.toUpperCase()+"/COLORS[@name='background']");
			if(colorNode!=null){
				colorString=colorNode.valueOf("@defaultValue");
			}
			else{
				colorString="#FFFFFF";
			}
		}
		backgroundColor = ChartEditor.convertHexadecimalToRGB(colorString);

		// Fill style parameters
		fillStyleParameters();
		// Fill configurations parameter belonging to that type
		fillCommonsConfParameters();
		// Fill configurations parameter belonging to default subtyp
		fillSpecificConfParameters();

		// Fill series personalization
		fillSeriesPersonalization();


	}






	public String toXML() {
		String toReturn="";
		//Dimension
		int width=dimension!=null?dimension.getWidth():400;
		int height=dimension!=null?dimension.getHeight():400;
		toReturn+="<DIMENSION width='"+width+"' height='"+height+"' />\n";

		if(backgroundColor!=null){
			toReturn+="<COLORS background='"+ChartEditor.convertRGBToHexadecimal(backgroundColor)+"' />\n";
		}

		logger.debug("Style settings");
		// Style
		if(styleParametersEditors!=null){
			for (Iterator iterator = styleParametersEditors.keySet().iterator(); iterator.hasNext();) {
				String styleName = (String) iterator.next();
				Style style=styleParametersEditors.get(styleName);
				String styleXml=style.toXML(this);
				toReturn+=styleXml;
			}
		}
		//		if(legendPositionStyle==true && legendPositionValue!=null){
		//			toReturn+="<PARAMETER name='LEGEND_POSITION' value='"+legendPositionValue+"' />";
		//		}

		// Confguration parameters
		if(confParametersEditor!=null || confSpecificParametersEditor!=null){
			logger.debug("Commons configuration parameter");
			toReturn+="<CONF>\n";
			for (Iterator iterator = confParametersEditor.keySet().iterator(); iterator.hasNext();) {
				String parName = (String) iterator.next();
				Parameter par=confParametersEditor.get(parName);
				String parXML=par.toXML();
				toReturn+=parXML;
			}

			logger.debug("Specific configuration parameter");			
			for (Iterator iterator = confSpecificParametersEditor.keySet().iterator(); iterator.hasNext();) {
				String parName = (String) iterator.next();
				Parameter par=confSpecificParametersEditor.get(parName);
				String parXML=par.toXML();
				toReturn+=parXML;
			}
			toReturn+="</CONF>\n";

		}


		// Serie Personalization
		String drawPersonalization="";
		String colorPersonalization="";
		String labelPersonalization="";
		String scalesPersonalization="";
		String orderColorPersonalization="";

		logger.debug("Series personalization: labels, colors, draws");		
		if(seriesDrawPersonalization){
			drawPersonalization="<SERIES_DRAW ";
			for (Iterator iterator = seriesPersonalizationHashMap.keySet().iterator(); iterator.hasNext();) {
				String serName = (String) iterator.next();
				SeriePersonalization serPers=seriesPersonalizationHashMap.get(serName);
				if(serPers.getDraw()!=null){
					drawPersonalization+=" "+serName+"=\""+serPers.getDraw()+"\" ";
				}
			}
			drawPersonalization+=" />\n";
		}

		if(seriesLabelPersonalization){
			labelPersonalization="<SERIES_LABELS ";
			for (Iterator iterator = seriesPersonalizationHashMap.keySet().iterator(); iterator.hasNext();) {
				String serName = (String) iterator.next();
				SeriePersonalization serPers=seriesPersonalizationHashMap.get(serName);
				if(serPers.getLabel()!=null){
					labelPersonalization+=" "+serName+"=\""+serPers.getLabel()+"\" ";
				}
			}
			labelPersonalization+=" />\n";
		}

		if(seriesColorPersonalization){
			colorPersonalization="<SERIES_COLORS ";
			for (Iterator iterator = seriesPersonalizationHashMap.keySet().iterator(); iterator.hasNext();) {
				String serName = (String) iterator.next();
				SeriePersonalization serPers=seriesPersonalizationHashMap.get(serName);
				if(serPers.getColor()!=null){
					colorPersonalization+=" "+serName+"=\""+ChartEditor.convertRGBToHexadecimal(serPers.getColor())+"\" ";
				}
			}
			colorPersonalization+=" />\n";
		}

		String root = "a_";
		int i = 0;
		if(seriesOrderColorPersonalization){
			orderColorPersonalization="<SERIES_ORDER_COLORS ";
			for (Iterator iterator = seriesOrderPersonalizationVector.iterator(); iterator.hasNext();) {
				RGB color = (RGB) iterator.next();
				orderColorPersonalization+=" "+root+Integer.valueOf(i).toString()+"=\""+ChartEditor.convertRGBToHexadecimal(color)+"\" ";
				i++;
			}
			orderColorPersonalization+=" />\n";
		}

		if(seriesScalesPersonalization){
			scalesPersonalization="<SERIES_SCALES";
			for (Iterator iterator = seriesPersonalizationHashMap.keySet().iterator(); iterator.hasNext();) {
				String serName = (String) iterator.next();
				SeriePersonalization serPers=seriesPersonalizationHashMap.get(serName);
				scalesPersonalization+=" "+serName+"=\""+Integer.valueOf(serPers.getScale()).toString()+"\" ";
			}
			scalesPersonalization+=" />\n";
		}

		toReturn+=colorPersonalization;
		toReturn+=drawPersonalization;
		toReturn+=labelPersonalization;
		toReturn+=scalesPersonalization;
		toReturn+=orderColorPersonalization;

		return toReturn;
	}

	/**
	 * Static method that returns all possibile subtypes for a given type, as configured in config file 
	 * @param chartType
	 * @return
	 * @throws Exception
	 */
	public static List getConfiguredChartSubTypes(String chartType) throws Exception {
		List toReturn = new ArrayList();
		String configPath=ChartEditorUtils.getChartConfigPath(chartType);
		InputStream is = ChartEditorUtils.getInputStreamFromResource(configPath);
		Document document = new SAXReader().read(is);

		String upperCaseNameSl=chartType.toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";

		List allCharts = document.selectNodes("//"+upperCaseNamePl+"/"+upperCaseNameSl);
		if (allCharts == null || allCharts.isEmpty()) throw new Exception("No common configuration set");

		for (Iterator iterator = allCharts.iterator(); iterator.hasNext();) {
			Node chart = (Node) iterator.next();
			String name=chart.valueOf("@name");
			if(!name.equalsIgnoreCase("commons")){
				logger.debug("Add possible subtype "+name);
				toReturn.add(name);
			}
		}
		return toReturn;
	}


	public static List getConfiguredChartTypes() throws Exception {
		List toReturn = new ArrayList();
		InputStream is = ChartEditorUtils.getInputStreamFromResource(CHART_INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//CHARTS/CHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (type == null || type.trim().equals("")) continue;
			toReturn.add(type);
		}
		return toReturn;
	}




	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDisplayTitleBar() {
		return displayTitleBar;
	}

	public void setDisplayTitleBar(boolean displayTitleBar) {
		this.displayTitleBar = displayTitleBar;
	}



	public void fillStyleParameters() throws Exception{
		logger.debug("Filling style configurations from actual file, otherwise from template");		
		String upperCaseNameSl=type.toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";

		logger.debug("Read all styles parameters from config file and record");		
		Node commonConfig = configDocument.selectSingleNode("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='commons']");
		if (commonConfig == null ) throw new Exception("No common configuration set");
		List allStyles = commonConfig.selectNodes("//STYLES/STYLE");
		// Iterate over the styles filling StyleParametersEditors
		for (Iterator iterator = allStyles.iterator(); iterator.hasNext();) {
			Element styleEl = (Element) iterator.next();
			Style toInsert=new Style();
			String nameStyle=styleEl.valueOf("@name");
			toInsert.setName(nameStyle);
			String descriptionStyle=styleEl.valueOf("@description");
			if(descriptionStyle==null || descriptionStyle.equalsIgnoreCase("")){
				descriptionStyle=nameStyle;
			}
			toInsert.setDescription(descriptionStyle);

			String tooltipStyle=styleEl.valueOf("@tooltip");			
			toInsert.setTooltip(tooltipStyle);

			toInsert.setHasFont(true);
			toInsert.setHasSize(true);
			toInsert.setHasColor(true);
			toInsert.setHasOrientation(true);

			// first I must check wich of these parameters are configurable
			Node fontNode1=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='font']");
			if(fontNode1==null){
				toInsert.setHasFont(false);
			}
			Node colorNode1=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='color']");
			if(colorNode1==null){
				toInsert.setHasFont(false);
			}
			Node orientationNode1=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='orientation']");
			if(orientationNode1==null){
				toInsert.setHasOrientation(false);
			}
			Node sizeNode1=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='size']");
			if(sizeNode1==null){
				toInsert.setHasSize(false);
			}

			// Fill the style values!!
			boolean useThisDocument=false;

			Node thisStyleNode=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/"+nameStyle.toUpperCase());
			if(thisStyleNode!=null)useThisDocument=true;

			if(toInsert.isHasFont()){
				String fontVal=null;
				if(useThisDocument==true) {
					fontVal=thisStyleNode.valueOf("@font");
				}
				else {
					Node fontNode=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='font']");
					if(fontNode!=null){
						fontVal=fontNode.valueOf("@defaultValue");
					}
				}
				if(fontVal!=null)	toInsert.setFont(fontVal);
			}

			if(toInsert.isHasOrientation()){			
				String orientationVal=null;
				if(useThisDocument==true) {
					orientationVal=thisStyleNode.valueOf("@orientation");
				}
				else{
					Node orientationNode=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='orientation']");
					if(orientationNode!=null){
						orientationVal=orientationNode.valueOf("@defaultValue");
					}
				}
				if(orientationVal!=null) toInsert.setOrientation(orientationVal);
			}


			if(toInsert.isHasColor()){
				String colorVal=null;
				if(useThisDocument==true) {
					colorVal=thisStyleNode.valueOf("@color");
				}
				else {
					Node colorNode=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='color']");
					if(colorNode!=null){
						colorVal=colorNode.valueOf("@defaultValue");
					}
				}
				if(colorVal!=null) toInsert.setColor(ChartEditor.convertHexadecimalToRGB(colorVal));
			}

			if(toInsert.isHasSize()){
				String sizeVal=null;
				if(useThisDocument==true) {
					sizeVal=thisStyleNode.valueOf("@size");
				}
				else {
					Node sizeNode=commonConfig.selectSingleNode("//STYLES/STYLE[@name='"+nameStyle+"']/STYLE_INFO[@name='size']");
					if(sizeNode!=null){
						sizeVal=sizeNode.valueOf("@defaultValue");
					}
				}
				if(sizeVal!=null){
					Integer intt=null;
					try{
						intt=Integer.valueOf(sizeVal);
					}
					catch (Exception e) {
						intt=10;
					}
					toInsert.setSize(intt);
				}
			}

			styleParametersEditors.put(toInsert.getName(), toInsert);
		}

	}






	/** Record commons configuration parameters, then fills model with values in actuale or in template file
	 *   
	 * @throws Exception
	 */

	public void fillCommonsConfParameters() throws Exception{
		logger.debug("Start recording commons configuration parameters");		
		// search for the root
		String upperCaseNameSl=type.toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";

		Node commonConfig = configDocument.selectSingleNode("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='commons']");
		if (commonConfig == null ) throw new Exception("No common configuration set");

		List allSections = commonConfig.selectNodes("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='commons']/CONF/SECTION");
		// Iterate over the section
		for (Iterator iterator = allSections.iterator(); iterator.hasNext();) {
			Element section = (Element) iterator.next();
			String nameSection=section.valueOf("@name");
			// iterate over the parameters for the current section
			List configuredParameters = section.selectNodes("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='commons']/CONF/SECTION[@name='"+nameSection+"']/PARAMETER");
			for (int j = 0; j < configuredParameters.size(); j++) {
				Node aConfiguredParameter = (Node) configuredParameters.get(j);
				String namePar = aConfiguredParameter.valueOf("@name");
				String description = aConfiguredParameter.valueOf("@description");
				String typeStr = aConfiguredParameter.valueOf("@type");
				String toolTipStr = aConfiguredParameter.valueOf("@tooltip");
				ArrayList<String> predefinedValues=null;
				int type;
				if (typeStr.equals("INTEGER")) type = Parameter.INTEGER_TYPE;
				else if (typeStr.equals("FLOAT")) type = Parameter.FLOAT_TYPE;
				else if (typeStr.equals("STRING")) type = Parameter.STRING_TYPE;
				else if (typeStr.equals("COLOR")) type = Parameter.COLOR_TYPE;
				else if (typeStr.equals("BOOLEAN")) type = Parameter.BOOLEAN_TYPE;
				else if (typeStr.equals("COMBO")) {
					type = Parameter.COMBO_TYPE;
					predefinedValues=new ArrayList<String>();
					String valueString = aConfiguredParameter.valueOf("@values");
					StringTokenizer st=new StringTokenizer(valueString,",");
					while(st.hasMoreTokens()){
						String val=st.nextToken();
						predefinedValues.add(val);
					}
				}
				else throw new Exception("Parameter type for parameter " + namePar + " not supported");
				Parameter par = new Parameter(namePar, "", description, type);
				par.setTooltip(toolTipStr);
				if(predefinedValues!=null){
					par.setPredefinedValues(predefinedValues);
				}

				// Add parameter in confParameterEditor
				if(!confParametersEditor.containsKey(par)){
					confParametersEditor.put(par.getName(), par);

					// add in section information, a map that gets name section and list of parameters in her.
					if(confSectionParametersEditor.get(nameSection)==null){
						confSectionParametersEditor.put(nameSection, new ArrayList<String>());
					}
					ArrayList<String> list=confSectionParametersEditor.get(nameSection);
					list.add(par.getName());	
				}

				// Get the value, search first in thisDocument, otherwise in configDocument as defaultValue
				Node parameterNode=thisDocument.selectSingleNode("//"+this.type.toUpperCase()+"/CONF/PARAMETER[@name='"+par.getName()+"'");
				String val=null;
				if(parameterNode!=null && parameterNode.valueOf("@value")!=null){
					val=parameterNode.valueOf("@value");

				}
				else // search in config
				{
					parameterNode=configDocument.selectSingleNode("//"+this.type.toUpperCase()+"S/"+this.type.toUpperCase()+"[@name='commons']/CONF/SECTION/PARAMETER[@name='"+par.getName()+"']");
					if(parameterNode!=null && parameterNode.valueOf("@defaultValue")!=null){
						val=parameterNode.valueOf("@defaultValue");

					}
				}

				if(type==Parameter.INTEGER_TYPE){
					// if it is not a number (maybe empty) don't fill anything
					try{
						Integer d=Integer.valueOf(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a number");
					}
				}
				else if(type==Parameter.FLOAT_TYPE){
					// if it is not a number (maybe empty) don't fill anything
					try{
						Double d=Double.valueOf(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a number");
					}
				}
				else if(type==Parameter.STRING_TYPE || type==Parameter.COMBO_TYPE){
					par.setValue(val);
				}
				else if(type==Parameter.COLOR_TYPE){
					try{
						RGB d=ChartEditor.convertHexadecimalToRGB(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a Hexadecimal color rapresentation");
					}				
				}
				else if(type==Parameter.BOOLEAN_TYPE){
					try{
						Boolean d=Boolean.valueOf(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a bool, set default false");
						par.setValue(new Boolean("false"));
					}
				}
			}
		}

		return;
	}











	/** Gets from actual template you are opening informations about series personalization
	 * 
	 * @param chartSubType
	 * @param thisDocument
	 */


	public void fillSeriesPersonalization() {
		logger.debug("Start recording and filling series personalization");

		String upperCaseNameSl=getType().toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";
		seriesPersonalizationHashMap=new HashMap<String, SeriePersonalization>();
		seriesOrderPersonalizationVector = new Vector<RGB>();

		// SERIES ORDE COLOR
		Element orderColorsThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_ORDER_COLORS");
		ChartEditorUtils.print("", orderColorsThis);
		if(orderColorsThis!=null){
			for (Iterator iterator = orderColorsThis.attributeIterator(); iterator.hasNext();) {
				DefaultAttribute att = (DefaultAttribute) iterator.next();
				//String name=att.getName();
				String value=null;
				if(att.getData()!=null){
					value=att.getData().toString();
					// add seriePers if not present
					RGB rgb=null;
					try{
						rgb=ChartEditor.convertHexadecimalToRGB(value);
					}
					catch (Exception e) {
						continue;
					}

					seriesOrderPersonalizationVector.add(rgb);
				}
				//					else{
				//						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
				//						serPers.setColor(ChartEditor.convertHexadecimalToRGB(value));
				//					}

			}

		}



		// COLOR
		Element colorsThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_COLORS");
		ChartEditorUtils.print("", colorsThis);
		if(colorsThis!=null){
			for (Iterator iterator = colorsThis.attributeIterator(); iterator.hasNext();) {
				DefaultAttribute att = (DefaultAttribute) iterator.next();
				String name=att.getName();
				String value=null;
				if(att.getData()!=null){
					value=att.getData().toString();
					// add seriePers if not present
					if(!seriesPersonalizationHashMap.containsKey(name)){
						SeriePersonalization serPers=new SeriePersonalization(name);
						RGB rgb=null;
						try{
							rgb=ChartEditor.convertHexadecimalToRGB(value);
						}
						catch (Exception e) {
							rgb=new RGB(255,0,0);
						}

						serPers.setColor(rgb);
						seriesPersonalizationHashMap.put(name, serPers);
					}
					else{
						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
						serPers.setColor(ChartEditor.convertHexadecimalToRGB(value));
					}

				}

			}
		}


		// LABEL
		Element labelThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_LABELS");
		ChartEditorUtils.print("", labelThis);
		if(labelThis!=null){
			for (Iterator iterator = labelThis.attributeIterator(); iterator.hasNext();) {
				DefaultAttribute att = (DefaultAttribute) iterator.next();
				String name=att.getName();
				String value=null;
				if(att.getData()!=null){
					value=att.getData().toString();
					// add seriePers if not present
					if(!seriesPersonalizationHashMap.containsKey(name)){
						SeriePersonalization serPers=new SeriePersonalization(name);
						serPers.setLabel(value);
						seriesPersonalizationHashMap.put(name, serPers);
					}
					else{
						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
						serPers.setLabel(value);
					}

				}
			}
		}

		// DRAW
		Element drawThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_DRAW");
		ChartEditorUtils.print("", labelThis);
		if(drawThis!=null){
			for (Iterator iterator = drawThis.attributeIterator(); iterator.hasNext();) {
				DefaultAttribute att = (DefaultAttribute) iterator.next();
				String name=att.getName();
				String value=null;
				if(att.getData()!=null){
					value=att.getData().toString();
					// add seriePers if not present
					if(!seriesPersonalizationHashMap.containsKey(name)){
						SeriePersonalization serPers=new SeriePersonalization(name);
						serPers.setDraw(value);
						seriesPersonalizationHashMap.put(name, serPers);
					}
					else{
						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
						serPers.setDraw(value);
					}

				}
			}
		}

		// SCALES
		Element scaleThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_SCALES");
		ChartEditorUtils.print("", scaleThis);
		if(scaleThis!=null){
			for (Iterator iterator = scaleThis.attributeIterator(); iterator.hasNext();) {
				DefaultAttribute att = (DefaultAttribute) iterator.next();
				String name=att.getName();
				String value=null;
				if(att.getData()!=null){
					value=att.getData().toString();
					// add seriePers if not present
					if(!seriesPersonalizationHashMap.containsKey(name)){
						SeriePersonalization serPers=new SeriePersonalization(name);
						serPers.setScale(Integer.valueOf(value).intValue());
						seriesPersonalizationHashMap.put(name, serPers);
					}
					else{
						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
						serPers.setScale(Integer.valueOf(value).intValue());
					}

				}
			}
		}

	}


	//	public void fillSeriesPersonalization() {
	//		logger.debug("Start recording and filling series personalization");
	//
	//		String upperCaseNameSl=getType().toUpperCase();
	//		String upperCaseNamePl=upperCaseNameSl+"S";
	//		seriesPersonalizationHashMap=new HashMap<String, SeriePersonalization>();
	//
	//		// COLOR
	//		Element colorsThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_COLORS");
	//		ChartEditorUtils.print("", colorsThis);
	//		if(colorsThis!=null){
	//			for (Iterator iterator = colorsThis.attributeIterator(); iterator.hasNext();) {
	//				DefaultAttribute att = (DefaultAttribute) iterator.next();
	//				String name=att.getName();
	//				String value=null;
	//				if(att.getData()!=null){
	//					value=att.getData().toString();
	//					// add seriePers if not present
	//					if(!seriesPersonalizationHashMap.containsKey(name)){
	//						SeriePersonalization serPers=new SeriePersonalization(name);
	//						RGB rgb=null;
	//						try{
	//							rgb=ChartEditor.convertHexadecimalToRGB(value);
	//						}
	//						catch (Exception e) {
	//							rgb=new RGB(255,0,0);
	//						}
	//
	//						serPers.setColor(rgb);
	//						seriesPersonalizationHashMap.put(name, serPers);
	//					}
	//					else{
	//						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
	//						serPers.setColor(ChartEditor.convertHexadecimalToRGB(value));
	//					}
	//
	//				}
	//
	//			}
	//		}
	//
	//		// LABEL
	//		Element labelThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_LABELS");
	//		ChartEditorUtils.print("", labelThis);
	//		if(labelThis!=null){
	//			for (Iterator iterator = labelThis.attributeIterator(); iterator.hasNext();) {
	//				DefaultAttribute att = (DefaultAttribute) iterator.next();
	//				String name=att.getName();
	//				String value=null;
	//				if(att.getData()!=null){
	//					value=att.getData().toString();
	//					// add seriePers if not present
	//					if(!seriesPersonalizationHashMap.containsKey(name)){
	//						SeriePersonalization serPers=new SeriePersonalization(name);
	//						serPers.setLabel(value);
	//						seriesPersonalizationHashMap.put(name, serPers);
	//					}
	//					else{
	//						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
	//						serPers.setLabel(value);
	//					}
	//
	//				}
	//			}
	//		}
	//
	//		// DRAW
	//		Element drawThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_DRAW");
	//		ChartEditorUtils.print("", labelThis);
	//		if(drawThis!=null){
	//			for (Iterator iterator = drawThis.attributeIterator(); iterator.hasNext();) {
	//				DefaultAttribute att = (DefaultAttribute) iterator.next();
	//				String name=att.getName();
	//				String value=null;
	//				if(att.getData()!=null){
	//					value=att.getData().toString();
	//					// add seriePers if not present
	//					if(!seriesPersonalizationHashMap.containsKey(name)){
	//						SeriePersonalization serPers=new SeriePersonalization(name);
	//						serPers.setDraw(value);
	//						seriesPersonalizationHashMap.put(name, serPers);
	//					}
	//					else{
	//						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
	//						serPers.setDraw(value);
	//					}
	//
	//				}
	//			}
	//		}
	//
	//		// SCALES
	//		Element scaleThis = (Element)thisDocument.selectSingleNode("//"+upperCaseNameSl+"/SERIES_SCALES");
	//		ChartEditorUtils.print("", scaleThis);
	//		if(scaleThis!=null){
	//			for (Iterator iterator = scaleThis.attributeIterator(); iterator.hasNext();) {
	//				DefaultAttribute att = (DefaultAttribute) iterator.next();
	//				String name=att.getName();
	//				String value=null;
	//				if(att.getData()!=null){
	//					value=att.getData().toString();
	//					// add seriePers if not present
	//					if(!seriesPersonalizationHashMap.containsKey(name)){
	//						SeriePersonalization serPers=new SeriePersonalization(name);
	//						serPers.setScale(Integer.valueOf(value).intValue());
	//						seriesPersonalizationHashMap.put(name, serPers);
	//					}
	//					else{
	//						SeriePersonalization serPers = seriesPersonalizationHashMap.get(name);
	//						serPers.setScale(Integer.valueOf(value).intValue());
	//					}
	//
	//				}
	//			}
	//		}
	//
	//
	//
	//	}





	/** Fill the specific conf parameters founf in the actual document, otherwise in the template docuement
	 * 
	 * @throws Exception
	 */

	public void fillSpecificConfParameters() throws Exception{
		logger.debug("Record and fill specific conf parameters");
		// search for the root
		String upperCaseNameSl=getType().toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";

		Node specificConfig = configDocument.selectSingleNode("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='"+subType+"']");
		if (specificConfig == null ) throw new Exception("No specific configuration set");

		List allSections = specificConfig.selectNodes("//"+type.toUpperCase()+"[@name='"+subType+"']/CONF/SECTION");
		// Iterate over the section
		for (Iterator iterator = allSections.iterator(); iterator.hasNext();) {
			Element section = (Element) iterator.next();
			String nameSection=section.valueOf("@name");
			// iterate over the parameters for the current section
			List configuredParameters = section.selectNodes("//"+type.toString()+"[@name='"+subType+"']/CONF/SECTION/PARAMETER");
			for (int j = 0; j < configuredParameters.size(); j++) {
				Node aConfiguredParameter = (Node) configuredParameters.get(j);
				//				System.out.println(aConfiguredParameter.asXML());
				String namePar = aConfiguredParameter.valueOf("@name");
				String description = aConfiguredParameter.valueOf("@description");
				String typeStr = aConfiguredParameter.valueOf("@type");
				String tooltipStr = aConfiguredParameter.valueOf("@tooltip");
				ArrayList<String> predefinedValues=null;
				int type;
				if (typeStr.equals("INTEGER")) type = Parameter.INTEGER_TYPE;
				else if (typeStr.equals("FLOAT")) type = Parameter.FLOAT_TYPE;
				else if (typeStr.equals("STRING")) type = Parameter.STRING_TYPE;
				else if (typeStr.equals("COLOR")) type = Parameter.COLOR_TYPE;
				else if (typeStr.equals("BOOLEAN")) type = Parameter.BOOLEAN_TYPE;
				else if (typeStr.equals("COMBO")) {
					type = Parameter.COMBO_TYPE;
					predefinedValues=new ArrayList<String>();
					String valueString = aConfiguredParameter.valueOf("@values");
					StringTokenizer st=new StringTokenizer(valueString,",");
					while(st.hasMoreTokens()){
						String val=st.nextToken();
						predefinedValues.add(val);
					}

				}
				else throw new Exception("Parameter type for parameter " + namePar + " not supported");

				Parameter par = new Parameter(namePar, "", description, type);
				par.setTooltip(tooltipStr);
				if(predefinedValues!=null){
					par.setPredefinedValues(predefinedValues);
				}

				if(!confSpecificParametersEditor.containsKey(par)){
					confSpecificParametersEditor.put(par.getName(), par);

					// add in section information
					if(confSpecificSectionParametersEditor.get(nameSection)==null){
						confSpecificSectionParametersEditor.put(nameSection, new ArrayList<String>());
					}
					ArrayList<String> list=confSpecificSectionParametersEditor.get(nameSection);
					list.add(par.getName());
				}

				// Get the value, search first in thisDocument, otherwise in configDocument as defaultValue
				Node parameterNode=thisDocument.selectSingleNode("//"+this.type.toUpperCase()+"/CONF/PARAMETER[@name='"+par.getName()+"'");
				String val=null;
				if(parameterNode!=null && parameterNode.valueOf("@value")!=null){
					val=parameterNode.valueOf("@value");

				}
				else // search in config
				{
					//parameterNode=thisDocument.selectSingleNode("//"+this.type.toUpperCase()+"/CONF/PARAMETER[@name='"+par.getName()+"'");	
					parameterNode=configDocument.selectSingleNode("//"+this.type.toUpperCase()+"S/"+this.type.toUpperCase()+"[@name='"+this.subType+"']/CONF/SECTION/PARAMETER[@name='"+par.getName()+"']");
					if(parameterNode!=null && parameterNode.valueOf("@defaultValue")!=null){
						val=parameterNode.valueOf("@defaultValue");

					}
				}

				if(type==Parameter.INTEGER_TYPE){
					// if it is not a number (maybe empty) don't fill anything
					try{
						Integer d=Integer.valueOf(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a number");
					}
				}
				else if(type==Parameter.FLOAT_TYPE){
					// if it is not a number (maybe empty) don't fill anything
					try{
						Double d=Double.valueOf(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a number");
					}
				}

				else if(type==Parameter.STRING_TYPE || type==Parameter.COMBO_TYPE){
					par.setValue(val);
				}
				else if(type==Parameter.COLOR_TYPE){
					try{
						RGB d=ChartEditor.convertHexadecimalToRGB(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a Hexadecimal color rapresentation");
					}				
				}
				else if(type==Parameter.BOOLEAN_TYPE){
					try{
						Boolean d=Boolean.valueOf(val);
						par.setValue(d);
					}
					catch (Exception e) {
						logger.warn("Not a bool, set default false");
						par.setValue(new Boolean("false"));
					}
				}
			}
		}

		return;
	}






	/** 
	 * 
	 * @param chartSubType
	 * @param configDocument
	 * @param templateDocument
	 * @throws Exception
	 * 
	 * returns if sub type let series personalization, it is if config contains series personalization tag
	 */

	public boolean isSeriesPersonalization(String chartSubType) throws Exception{
		// check the type and search for the root
		String upperCaseNameSl=getType().toUpperCase();
		String upperCaseNamePl=upperCaseNameSl+"S";

		seriesColorPersonalization=false;
		seriesDrawPersonalization=false;
		seriesLabelPersonalization=false;
		seriesScalesPersonalization=false;
seriesOrderColorPersonalization = false;
		
		// Get the node configuration
		Node specificConfig = configDocument.selectSingleNode("//"+upperCaseNamePl+"/"+upperCaseNameSl+"[@name='"+chartSubType.trim()+"']");

		Node seriesLabelsNode = specificConfig.selectSingleNode("//"+upperCaseNameSl+"[@name='"+chartSubType+"']/SERIES_LABELS");
		if(seriesLabelsNode!=null){
			seriesLabelPersonalization=true;
		}

		Node seriesColorsNode = specificConfig.selectSingleNode("//"+upperCaseNameSl+"[@name='"+chartSubType+"']/SERIES_COLORS");
		if(seriesColorsNode!=null){
			seriesColorPersonalization=true;
		}

		Node seriesDrawNode = specificConfig.selectSingleNode("//"+upperCaseNameSl+"[@name='"+chartSubType+"']/SERIES_DRAW");
		if(seriesDrawNode!=null){
			seriesDrawPersonalization=true;
		}

		Node seriesScalesNode = specificConfig.selectSingleNode("//"+upperCaseNameSl+"[@name='"+chartSubType+"']/SERIES_SCALES");
		if(seriesScalesNode!=null){
			seriesScalesPersonalization=true;
		}

		Node seriesOrderColorsNode = specificConfig.selectSingleNode("//"+upperCaseNameSl+"[@name='"+chartSubType+"']/SERIES_ORDER_COLORS");
		if(seriesOrderColorsNode!=null){
			seriesOrderColorPersonalization=true;
		}


		if(seriesLabelPersonalization==true || seriesColorPersonalization==true || seriesDrawPersonalization==true || seriesScalesPersonalization==true){
			logger.debug("Let series personalisation");
			return true;
		}
		else {
			logger.debug("Does not let series personalization");
			return false;
		}

	}	




	/** Aim of this methos is to azerate the specific parameters when user changes subtype
	 * 
	 */

	public void eraseSpecificParameters(){
		// erase configuration parameters
		confSpecificParametersEditor=new HashMap<String, Parameter>();
		confSpecificSectionParametersEditor=new TreeMap<String, ArrayList<String>>();
		// erase serie personalizations
		seriesPersonalizationHashMap=new HashMap<String, SeriePersonalization>();
		seriesColorPersonalization=false;
		seriesDrawPersonalization=false;
		seriesLabelPersonalization=false;
		seriesScalesPersonalization=false;
	}


	public void initializeEditor(ChartEditor editor,ChartEditorComponents components,  FormToolkit toolkit, ScrolledForm form) throws Exception{
		// Once retrieved the subtype fill specific conf parameter
		logger.debug("Specific configuraiton parameters");
		components.createSpecificConfigurationSection(this,editor, toolkit,form);

		// CREATE THE SERIES PERSONALIZATION PARAMETER: At the beginning set invisible
		//components.getSeriesPersonalizationEditor().eraseComposite();
		logger.debug("Series personalization section");
		boolean isSerieLabel=isSeriesPersonalization(subType);
		components.createSeriesPersonalizationSection(this,toolkit, form);		
		components.getSeriesPersonalizationEditor().setVisible(false);

		if(isSerieLabel==true){
			components.getSeriesPersonalizationEditor().setVisible(true);
			logger.debug("Enable only allowed series personalization");
			components.getSeriesPersonalizationEditor().enablePersonalizations(seriesLabelPersonalization, seriesColorPersonalization, seriesDrawPersonalization, seriesScalesPersonalization, seriesOrderColorPersonalization);
		}
		else{
			components.getSeriesPersonalizationEditor().setVisible(false);
		}




	}

	public void refreshEditor(ChartEditor editor,ChartEditorComponents components,  FormToolkit toolkit, ScrolledForm form) throws Exception{
		//eraseSpecificParameters();
		logger.debug("Fill again specific parameters");						
		fillSpecificConfParameters();
		logger.debug("Create specific configuration parameters section");
		components.createSpecificConfigurationSection(this, null, toolkit,form);
		//components.createConfigurationSection(null, toolkit);

		boolean isSerieLabel=isSeriesPersonalization(subType);						
		logger.debug("Erase fields of editor");
		components.getSeriesPersonalizationEditor().eraseComposite();
		if(isSerieLabel==true){
			logger.debug("Fill series personalization fields");
			fillSeriesPersonalization();
			// Now I should fill again the editor (already created) with new Data
			logger.debug("re fill the fields");
			components.getSeriesPersonalizationEditor().refillFieldsSeriesPersonalization(this, null, toolkit, form);
			logger.debug("Enable personalizations");
			components.getSeriesPersonalizationEditor().enablePersonalizations(seriesLabelPersonalization, seriesColorPersonalization, seriesDrawPersonalization,seriesScalesPersonalization, seriesOrderColorPersonalization);
			components.getSeriesPersonalizationEditor().setVisible(true);
		}
		else{
			components.getSeriesPersonalizationEditor().setVisible(false);
		}

	}



	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}


	public TreeMap<String, ArrayList<String>> getConfSectionParametersEditor() {
		return confSectionParametersEditor;
	}

	public void setConfSectionParametersEditor(
			TreeMap<String, ArrayList<String>> confSectionParametersEditor) {
		this.confSectionParametersEditor = confSectionParametersEditor;
	}


	public TreeMap<String, ArrayList<String>> getConfSpecificSectionParametersEditor() {
		return confSpecificSectionParametersEditor;
	}

	public void setConfSpecificSectionParametersEditor(
			TreeMap<String, ArrayList<String>> confSpecificSectionParametersEditor) {
		this.confSpecificSectionParametersEditor = confSpecificSectionParametersEditor;
	}

	public HashMap<String, Parameter> getConfParametersEditor() {
		return confParametersEditor;
	}

	public void setConfParametersEditor(
			HashMap<String, Parameter> confParametersEditor) {
		this.confParametersEditor = confParametersEditor;
	}

	public HashMap<String, Parameter> getConfSpecificParametersEditor() {
		return confSpecificParametersEditor;
	}

	public void setConfSpecificParametersEditor(
			HashMap<String, Parameter> confSpecificParametersEditor) {
		this.confSpecificParametersEditor = confSpecificParametersEditor;
	}



	public HashMap<String, Style> getStyleParametersEditors() {
		return styleParametersEditors;
	}

	public void setStyleParametersEditors(
			HashMap<String, Style> styleParametersEditors) {
		this.styleParametersEditors = styleParametersEditors;
	}

	public Document getConfigDocument() {
		return configDocument;
	}

	public void setConfigDocument(Document configDocument) {
		this.configDocument = configDocument;
	}



	public HashMap<String, SeriePersonalization> getSeriesPersonalizationHashMap() {
		return seriesPersonalizationHashMap;
	}

	public void setSeriesPersonalizationHashMap(
			HashMap<String, SeriePersonalization> seriesPersonalizationHashMap) {
		this.seriesPersonalizationHashMap = seriesPersonalizationHashMap;
	}

	public boolean isSeriesLabelPersonalization() {
		return seriesLabelPersonalization;
	}

	public void setSeriesLabelPersonalization(boolean seriesLabelPersonalization) {
		this.seriesLabelPersonalization = seriesLabelPersonalization;
	}

	public boolean isSeriesColorPersonalization() {
		return seriesColorPersonalization;
	}

	public void setSeriesColorPersonalization(boolean seriesColorPersonalization) {
		this.seriesColorPersonalization = seriesColorPersonalization;
	}

	public boolean isSeriesDrawPersonalization() {
		return seriesDrawPersonalization;
	}

	public void setSeriesDrawPersonalization(boolean seriesDrawPersonalization) {
		this.seriesDrawPersonalization = seriesDrawPersonalization;
	}


	//	protected Style createStyle(String styleName, Document templateDocument){
	//		Node style1Node = templateDocument.selectSingleNode("//BARCHART/STYLE_LABELS_DEFAULT");
	//		String font=style1Node.valueOf("@font");
	//		String size=style1Node.valueOf("@size");
	//		String color=style1Node.valueOf("@color");
	//		String orientation=style1Node.valueOf("@orientation");
	//		Integer sizeInt=null;
	//		if(size!=null){
	//			sizeInt=Integer.parseInt(size);
	//		}
	//		RGB colorColor=null;
	//		if(color!=null){
	//			colorColor=ChartEditor.convertHexadecimalToRGB(color);
	//		}
	//		Style style=new Style(font, sizeInt, colorColor, orientation);
	//		style.setName(styleName);
	//		return style;
	//	}

	public Document getThisDocument() {
		return thisDocument;
	}

	public void setThisDocument(Document d) {
		thisDocument=d;
	}


	public boolean isSeriesScalesPersonalization() {
		return seriesScalesPersonalization;
	}

	public void setSeriesScalesPersonalization(boolean seriesScalesPersonalization) {
		this.seriesScalesPersonalization = seriesScalesPersonalization;
	}

	public boolean isLegendPositionStyle() {
		return legendPositionStyle;
	}

	public void setLegendPositionStyle(boolean legendPositionStyle) {
		this.legendPositionStyle = legendPositionStyle;
	}

	public String getLegendPositionValue() {
		return legendPositionValue;
	}

	public void setLegendPositionValue(String legendPositionValue) {
		this.legendPositionValue = legendPositionValue;
	}

	public void setBackgroundColor(RGB backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public RGB getBackgroundColor() {
		return backgroundColor;
	}

	public ChartEditor getEditor() {
		return editor;
	}

	public void setEditor(ChartEditor editor) {
		this.editor = editor;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Integer getSdkDataSetId() {
		return sdkDataSetId;
	}

	public void setSdkDataSetId(Integer sdkDataSetId) {
		this.sdkDataSetId = sdkDataSetId;
	}

	public Vector<RGB> getSeriesOrderPersonalizationVector() {
		return seriesOrderPersonalizationVector;
	}

	public void setSeriesOrderPersonalizationVector(
			Vector<RGB> seriesOrderPersonalizationVector) {
		this.seriesOrderPersonalizationVector = seriesOrderPersonalizationVector;
	}

	public boolean isSeriesOrderColorPersonalization() {
		return seriesOrderColorPersonalization;
	}

	public void setSeriesOrderColorPersonalization(
			boolean seriesOrderColorPersonalization) {
		this.seriesOrderColorPersonalization = seriesOrderColorPersonalization;
	}




}
