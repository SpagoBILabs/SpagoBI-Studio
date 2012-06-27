/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.utils.exceptions.SavingEditorException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.slf4j.LoggerFactory;

public class AxesUtilities {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesUtilities.class);

	/**
	 *  run all series and parse fields in order to fill numeric axes fields list
	 * 
	 */

	public void updateNumericFields(ExtChart extChart){
		logger.debug("IN");

		Set<String> bottomSet = new TreeSet<String>();
		Set<String> leftSet = new TreeSet<String>();
		Set<String> rightSet = new TreeSet<String>();
		Set<String> topSet = new TreeSet<String>();

		Vector<Series> series =	extChart.getSeriesList().getSeries();

		for (Iterator iterator = series.iterator(); iterator.hasNext();) {
			Series series2 = (Series) iterator.next();
			String fields = series2.getyFieldList();
			fields = fields != null ? fields : "";
			String[] fieldArray = fields.split(",");
			String axis = series2.getAxis() != null ? series2.getAxis() : "left"; 

			for (int i = 0; i < fieldArray.length; i++) {
				String field = fieldArray[i];
				if(axis.equalsIgnoreCase("left")) {
					leftSet.add(field);
				}
				else if(axis.equalsIgnoreCase("right")) rightSet.add(field);
				else if(axis.equalsIgnoreCase("bottom")) bottomSet.add(field);
				else if(axis.equalsIgnoreCase("top")) topSet.add(field);
			}
		}

		// get Numeric axes and fill them with position
		Vector<Axes> axes =extChart.getAxesList().getAxes();
		for (Iterator iterator = axes.iterator(); iterator.hasNext();) {
			Axes axes2 = (Axes) iterator.next();
			if (axes2.getType()!=null){
				if(axes2.getType().equalsIgnoreCase("Numeric")){
					String position = axes2.getPosition();
					if(position.equalsIgnoreCase("left")) {
						String fieldsList = getStringFromSet(leftSet);
						logger.debug("Numeric axe on position "+position+ " set fields  ");
						axes2.setFields_list(fieldsList);
					}
					else if(position.equalsIgnoreCase("right")) {
						String fieldsList = getStringFromSet(rightSet);
						logger.debug("Numeric axe on position "+position+ " set fields  ");
						axes2.setFields_list(fieldsList);
					}
					else if(position.equalsIgnoreCase("top")) {
						String fieldsList = getStringFromSet(topSet);
						logger.debug("Numeric axe on position "+position+ " set fields  ");
						axes2.setFields_list(fieldsList);
					}
					else if(position.equalsIgnoreCase("bottom")) {
						String fieldsList = getStringFromSet(bottomSet);
						logger.debug("Numeric axe on position "+position+ " set fields  ");
						axes2.setFields_list(fieldsList);
					}
				}				
			}

		}
		logger.debug("OUT");
	}




	private String getStringFromSet(Set fielsdsSet){
		String toReturn="";
		for (Iterator iterator2 = fielsdsSet.iterator(); iterator2
		.hasNext();) {
			String str = (String) iterator2.next();
			toReturn += str;
			if(iterator2.hasNext()){
				toReturn+=",";
			}
		}
		return toReturn;
	}


	/** set series xField as category field
	 * 
	 * @param extChart
	 */

	public void updateCategoryFields(ExtChart extChart) throws SavingEditorException{
		logger.debug("IN");

		Axes xAxe = ExtChartUtils.getXAxe(extChart);
		//		if(xAxe == null){
		//			logger.warn("No category axe is defined: customize category axis in order to choose the category field");
		//			throw new SavingEditorException("No category axe is defined: customize category axis in order to choose the category field");
		//		}
		if(xAxe != null){
			String field = xAxe.getFields();
			logger.debug("xField is "+field);
			Vector<Series> series =	extChart.getSeriesList().getSeries();
			for (Iterator iterator = series.iterator(); iterator.hasNext();) {
				Series series2 = (Series) iterator.next();
				series2.setxField(field);
			}
		}
		else{
			logger.debug("No X axe defined for chart");
		}
		logger.debug("OUT");
	}


}
