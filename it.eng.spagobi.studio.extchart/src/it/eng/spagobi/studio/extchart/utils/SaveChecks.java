package it.eng.spagobi.studio.extchart.utils;

import org.eclipse.jface.dialogs.MessageDialog;
import org.slf4j.LoggerFactory;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;

public class SaveChecks {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SaveChecks.class);

	public boolean checksBeforeSave(ExtChart extChart, ExtChartEditor editor){
		logger.debug("IN");
		
//		extChart.setAnimate(true);
//		extChart.setRefreshTime(5);
		
		String warningMessage = "";
		Axes xAxe = ExtChartUtils.getXAxe(extChart);
		if(xAxe != null){
			logger.debug("Category axe found");
		}
		else{
			logger.warn("Category axe not defined");
			warningMessage += "Category axe not defined: customize it in order to choose category field\n";
		}

		Axes yAxe = ExtChartUtils.getYAxe(extChart);
		if(yAxe != null){
			logger.debug("Numeric axe found");
		}
		else{
			logger.warn("Numeric axe not defined");
			warningMessage += "Numeric axe not defined: set axis position it in order to add numeric axes\n";
		}
		
		logger.debug("OUT");
		if (!warningMessage.equals("")) {
			MessageDialog.openWarning(editor.getMainChartPage().getShell(), "Warning in saving", warningMessage);
			return false;
		}
		else{
			return true;
		}


	}

}
