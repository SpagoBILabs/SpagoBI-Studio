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

import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.properties.PropertyPage;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ChartModelFactory {

	/** This method create the mdoel of the chart starting from actually opened file and from template file
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */

	public static final String BARCHART="BARCHART";
	public static final String PIECHART="PIECHART";
	public static final String DIALCHART="DIALCHART";
	public static final String CLUSTERCHART="CLUSTERCHART";
	public static final String BOXCHART="BOXCHART";
	public static final String XYCHART="XYCHART";
	public static final String SCATTERCHART="SCATTERCHART";

	public static ChartModel createChartModel(IFile file, Shell shell) throws Exception  {
		SpagoBILogger.infoLog("Start Creating Chart Model");
		ChartModel model = null;
		InputStream thisIs = null;
		InputStream configurationIs = null;
		InputStream templateIs = null;

		try {
			// reads the template file
			SpagoBILogger.infoLog("Getting present file content");
			thisIs = file.getContents();
			SAXReader reader = new SAXReader();
			Document thisDocument = reader.read(thisIs);

			Document configurationDocument = null;

			Element root = thisDocument.getRootElement();
			String type=root.getName();
			SpagoBILogger.infoLog("User selected a chart of type "+type);						
			// here check if a subtype is defined (should be), then get the right template path!
			String subType=root.valueOf("@type");

			// if subtype is null I get the default!!
			if(subType==null || subType.equalsIgnoreCase("")){
				SpagoBILogger.infoLog("Get default subtype");			
				subType=ChartEditorUtils.getDefaultSubtype(type);
			}
			// if subtype is overlaid_stacked_barline put it to overlaid_barline 
			if(subType.equalsIgnoreCase("overlaid_stacked_barline")){
				SpagoBILogger.infoLog("Type changed from overlaid_stacked_barline no more supported to overlaid_barline");			
				MessageDialog.openInformation(shell, "No more supported type", "Chart subType overlaid_stacked_barline no more supported, you can use an overlaid_barline and stack bars by checking configuration parameters");
				subType = "overlaid_barline";
			}

			SpagoBILogger.infoLog("Actual subtype is "+subType);			

			SpagoBILogger.infoLog("Getting config file content");						
			String configPath="";			
			// get Configuration File
			try {
				configPath=ChartEditorUtils.getChartConfigPath(type);
				configurationIs = ChartEditorUtils.getInputStreamFromResource(configPath);
				configurationDocument = reader.read(configurationIs);
			} catch (Exception e) {
				SpagoBILogger.errorLog("Error while getting config file content",e);
				throw new Exception("Error while reading " + ChartModel.CHART_INFO_FILE + " file: " + e.getMessage());
			}


			// get the general template Path
			SpagoBILogger.infoLog("Getting template file content");									
			String templatePath="";
			//			Document templateDocument = null;
			//			try {
			//				templatePath=ChartEditorUtils.getChartTemplatePath(type, subType);
			//				templateIs = ChartEditorUtils.getInputStreamFromResource(templatePath);
			//				templateDocument = reader.read(templateIs);
			//			} catch (Exception e) {
			//				SpagoBILogger.errorLog("Error while getting template file content",e);
			//				throw new Exception("Error while reading Template file: " + e.getMessage());
			//			}


			// **** CREATE THE MODEL	****

			if(type.equalsIgnoreCase(ChartModelFactory.BARCHART))
			{
				model=new BarChartModel(type,subType,file,configurationDocument);
			}
			else if(type.equalsIgnoreCase(ChartModelFactory.PIECHART))
			{
				model=new PieChartModel(type,subType, file,configurationDocument);
			}
			else if(type.equalsIgnoreCase(ChartModelFactory.CLUSTERCHART))
			{
				model=new ClusterChartModel(type,subType, file,configurationDocument);
			}
			else if(type.equalsIgnoreCase(ChartModelFactory.BOXCHART))
			{
				model=new BoxChartModel(type,subType, file,configurationDocument);
			}
			else if(type.equalsIgnoreCase(ChartModelFactory.DIALCHART))
			{
				model=new DialChartModel(type, subType,file,configurationDocument);
			}
			else if(type.equalsIgnoreCase(ChartModelFactory.XYCHART))
			{
				model=new XYChartModel(type, subType,file,configurationDocument);
			}
			else if(type.equalsIgnoreCase(ChartModelFactory.SCATTERCHART))
			{
				model=new ScatterChartModel(type, subType,file,configurationDocument);
			}


			// Set the dataset if present!

			String dataSetIdS=file.getPersistentProperty(SpagoBIStudioConstants.DATASET_ID);
			if(dataSetIdS!=null){
				Integer dataSetId=Integer.valueOf(dataSetIdS);
				model.setSdkDataSetId(dataSetId);
			}



		} 
		catch (Exception e) {
			SpagoBILogger.errorLog("Error in reading the xml template", e);
			throw new Exception("Error while reading xml file: check Xml syntax and assure that type and subtype of the chart are supported by SpagoBI");
		}
		finally {
			if (thisIs != null) thisIs.close();
			if (configurationIs != null) configurationIs.close();
			if (templateIs != null) templateIs.close();
		}

		return model;
	}

	static public String parseISToString(java.io.InputStream is){
		java.io.DataInputStream din = new java.io.DataInputStream(is);
		StringBuffer sb = new StringBuffer();
		try{
			String line = null;
			while((line=din.readLine()) != null){
				sb.append(line+"\n");
			}
		}catch(Exception ex){
			ex.getMessage();
		}finally{
			try{
				is.close();
			}catch(Exception ex){}
		}
		return sb.toString();
	}

}
