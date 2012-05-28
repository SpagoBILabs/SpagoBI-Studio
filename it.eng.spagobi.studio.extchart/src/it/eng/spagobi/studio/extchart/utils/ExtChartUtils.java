package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.configuration.ExtChartConfigurations;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.AxesList;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtChartUtils {



	private static Logger logger = LoggerFactory.getLogger(ExtChartUtils.class);


	/** Get input stream from a resource
	 * 
	 * @param resourcePath
	 * @return
	 * @throws IOException
	 */

	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		Bundle b = Platform.getBundle(it.eng.spagobi.studio.extchart.Activator.PLUGIN_ID);
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}



	/** Get the chart image path for the selected type
	 * 
	 * @param imageType
	 * @return
	 * @throws Exception
	 */

	public static String getChartImagePath(String imageType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ExtChartConfigurations.INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//EXTCHARTS/EXTCHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (imageType.equalsIgnoreCase(type)) {
				String imagePath = chart.valueOf("@imagePath");
				toReturn = imagePath;
				break;
			}
		}
		return toReturn;
	}


	public static String getXAxeTypeFromChartType(String chartType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ExtChartConfigurations.INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//EXTCHARTS/EXTCHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (chartType.equalsIgnoreCase(type)) {
				String imagePath = chart.valueOf("@xAxeName");
				toReturn = imagePath;
				break;
			}
		}
		return toReturn;
	}


	public static String getYAxeTypeFromChartType(String chartType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ExtChartConfigurations.INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//EXTCHARTS/EXTCHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (chartType.equalsIgnoreCase(type)) {
				String imagePath = chart.valueOf("@yAxeName");
				toReturn = imagePath;
				break;
			}
		}
		return toReturn;
	}


	public static String getSerieTypeFromChartType(String chartType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(ExtChartConfigurations.INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//EXTCHARTS/EXTCHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (chartType.equalsIgnoreCase(type)) {
				String imagePath = chart.valueOf("@defaultSerieType");
				toReturn = imagePath;
				break;
			}
		}
		return toReturn;
	}



	/** 
	 * @return
	 */
	static public Axes getAxeFromPosition(ExtChart extChart, String position){
		logger.debug("IN");		
		Axes toReturn = null;
		AxesList axesList = extChart.getAxesList();

		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getPosition()!= null
					&&
					axe.getPosition().equalsIgnoreCase(position)
			){
				toReturn = axe;

				break;
			}
		}
		if(toReturn != null) logger.debug("found axe on the "+position);
		else logger.debug(position+" axe not found");
		logger.debug("OUT");
		return toReturn;
	}


	/** 
	 * @return
	 */
	static public Axes getAxeFromPositionAndType(ExtChart extChart, String type, String position){
		logger.debug("IN");		
		Axes toReturn = null;
		AxesList axesList = extChart.getAxesList();

		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getType()!= null && axe.getType().equalsIgnoreCase(type)){
				if(axe.getPosition()!= null
						&&
						axe.getPosition().equalsIgnoreCase(position)
				){
					toReturn = axe;

					break;
				}
			}
		}
		if(toReturn != null) logger.debug("found axe of type "+type+ " on the "+position);
		else logger.debug(type+" axe at position "+position+" not found");
		logger.debug("OUT");
		return toReturn;
	}

	static public Axes getXAxe(ExtChart extChart){
		logger.debug("IN");		
		Axes toReturn = null;
		AxesList axesList = extChart.getAxesList();

		String typeToSearch = null;
		try {
			typeToSearch = getXAxeTypeFromChartType(extChart.getType());
		} catch (Exception e) {
			logger.error("error in reading xml configuration file, check its syntax", e);
			throw new RuntimeException("error in reading xml configuration file, check its syntax", e);
		}
		logger.debug("return axe of type "+typeToSearch);		

		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getType()!= null
					&&
					axe.getType().equalsIgnoreCase(typeToSearch)
			){
				toReturn = axe;

				break;
			}
		}
		if(toReturn != null) logger.debug("found axe");
		else logger.debug(" axe not found");
		logger.debug("OUT");
		return toReturn;
	}


	//	// order of retrieval: the order is left, bottom, right top
	//	static public ArrayList<Axes> orderAxes(AxesList axesList){
	//		logger.debug("IN");	
	//		Vector<Axes> axes = axesList.getAxes();
	//		Axes leftAxe;
	//		Axes rightAxe;
	//		Axes rightAxe;
	//		
	//		
	//		logger.debug("OUT");	
	//	}


	// order can be 1 or 2
	static public Axes getYAxe(ExtChart extChart){
		logger.debug("IN");		
		Axes toReturn = null;
		AxesList axesList = extChart.getAxesList();
		String typeToSearch = null;
		try {
			typeToSearch = getYAxeTypeFromChartType(extChart.getType());
		} catch (Exception e) {
			logger.error("error in reading xml configuration file, check its syntax", e);
			throw new RuntimeException("error in reading xml configuration file, check its syntax", e);
		}
		logger.debug("return axe of type "+typeToSearch);		

		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getType()!= null
					&&
					axe.getType().equalsIgnoreCase(typeToSearch)
			)
			{

				toReturn = axe;
				break;
			}
		}
		if(toReturn != null) logger.debug("found axe");
		else logger.debug(" axe not found");
		logger.debug("OUT");
		return toReturn;
	}







	// order can be 1 or 2; order is left = 0, bottom = 1, right = 2, top = 3   
	static public Axes getYAxe(ExtChart extChart, int order){
		final Integer LEFT = 0;  
		final Integer BOTTOM = 1;  
		final Integer RIGHT = 2;  
		final Integer TOP = 3;  

		logger.debug("IN");
		Axes[] axes = new Axes[4];
		Axes toReturn = null;
		AxesList axesList = extChart.getAxesList();
		String typeToSearch = null;
		try {
			typeToSearch = getYAxeTypeFromChartType(extChart.getType());
		} catch (Exception e) {
			logger.error("error in reading xml configuration file, check its syntax", e);
			throw new RuntimeException("error in reading xml configuration file, check its syntax", e);
		}
		logger.debug("return axe of type "+typeToSearch);		

		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getType()!= null
					&&
					axe.getType().equalsIgnoreCase(typeToSearch)
			)
			{
				String position = axe.getPosition();
				if(position.equalsIgnoreCase("left"))axes[LEFT]= axe;
				else if(position.equalsIgnoreCase("bottom"))axes[BOTTOM]= axe;
				else if(position.equalsIgnoreCase("right"))axes[RIGHT]= axe;
				else if(position.equalsIgnoreCase("top"))axes[TOP]= axe;
			}
		}
		if(toReturn != null) logger.debug("found axe");
		else logger.debug(" axe not found");

		int counter = 0;
		for (int i = 0; i < axes.length; i++) {
			Axes a = axes[i];
			if(a != null){
				counter++;
				if(counter == order){
					logger.debug("found axe");
					toReturn = a;
				}
			}
		}

		logger.debug("OUT");
		return toReturn;
	}












	static public void deleteNumericAxe(ExtChart extChart, String typeToSearch, String position){
		logger.debug("IN");
		AxesList axesList = extChart.getAxesList();


		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getType()!= null && axe.getType().equalsIgnoreCase(typeToSearch))
			{
				if(axe.getPosition() != null && axe.getPosition() == position){
					logger.debug("delete axe in position "+position);
					int index = axesList.getAxes().indexOf(axe);
					axesList.getAxes().remove(index);
					break;
				}
			}
		}

		logger.debug("OUT");
	}

	static public void updateAxesField(ExtChart extChart, String typeToSearch, String fieldsToInsert, String position){
		logger.debug("IN");
		AxesList axesList = extChart.getAxesList();
		for (Iterator iterator = axesList.getAxes().iterator(); iterator.hasNext();) {
			Axes axe = (Axes) iterator.next();
			if(axe.getType()!= null && axe.getType().equalsIgnoreCase(typeToSearch))
			{
				if(axe.getPosition() != null && axe.getPosition() == position){
					logger.debug("change fields_list of axe in position "+position);
					logger.debug("fields_list to insert are "+fieldsToInsert);
					axe.setFields_list(fieldsToInsert);
					break;
				}
			}
		}

		logger.debug("OUT");


	}



}
