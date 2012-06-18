package it.eng.spagobi.studio.extchart.editors.properties.axes;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.slf4j.LoggerFactory;

public class AxesGaugeProperties extends AxesProperties {

	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesGaugeProperties.class);
	Spinner minimumSpinner, maximumSpinner,stepsSpinner,marginSpinner;
	
	/**
	 * @param editor
	 * @param comp
	 */
	public AxesGaugeProperties(ExtChartEditor editor, Shell comp) {
		super(editor, comp);
		dialog.setSize(300,250);
	}
	
	public void drawProperties(){
		logger.debug("IN");
		super.drawProperties();
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "Gauge");
		toolkit.createLabel(dialog, "Position: ");
		toolkit.createLabel(dialog, "Gauge");

		//minimum
		minimumSpinner = SWTUtils.drawSpinner(dialog, axes.getMinimum(), "Minimum: ");
		
		//maximum
		maximumSpinner = SWTUtils.drawSpinner(dialog, axes.getMaximum(), "Maximum: ");

		//steps
		stepsSpinner = SWTUtils.drawSpinner(dialog, axes.getSteps(), "Steps: ");
		
		//margin
		marginSpinner = SWTUtils.drawSpinner(dialog, axes.getMargin(), "Margin: ");

		logger.debug("OUT");

	}
	
	public void performOk(){
		logger.debug("IN");
		super.performOk();		

		axes.setType("gauge");
		axes.setPosition("gauge");
		logger.debug("type gauge");
		
		int valMinimum = minimumSpinner.getSelection();
		if (valMinimum >= 0 ){
			axes.setMinimum(valMinimum);			
		}
		logger.debug("minimum: " + valMinimum);

		int valMaximum = maximumSpinner.getSelection();
		if (valMaximum >= 0 ){
			axes.setMaximum(valMaximum);			
		}
		logger.debug("maximum: " + valMaximum);
		
		int valSteps = stepsSpinner.getSelection();
		if (valSteps >= 0 ){
			axes.setSteps(valSteps);			
		}
		logger.debug("steps: " + valSteps);
		
		int valMargin = marginSpinner.getSelection();
		axes.setMargin(valMargin);			

		logger.debug("margin: " + valMargin);
		
		logger.debug("OUT");
	}

}
