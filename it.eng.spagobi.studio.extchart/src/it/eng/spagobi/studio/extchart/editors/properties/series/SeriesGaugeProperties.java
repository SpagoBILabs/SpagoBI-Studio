package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

public class SeriesGaugeProperties extends SeriesProperties {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesGaugeProperties.class);
	Text donutText;
	Spinner donutSpinner;

	
	public SeriesGaugeProperties(ExtChartEditor editor,
			Shell comp) {
		super(editor, comp);
		setDrawField(true);
	}

	public void drawProperties(){
		logger.debug("IN");
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "gauge");
		serie.setType("gauge");
		super.drawProperties();
	
		logger.debug("Donut");
		donutSpinner = SWTUtils.drawSpinner(dialog, serie.getDonut(), "Donut: ");
	
		logger.debug("OUT");
	}

	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialog.setSize(200, 250);
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!dialog.getDisplay().readAndDispatch()) {
		    	dialog.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}
	
	public void performOk(){
		logger.debug("IN");
		super.performOk();	
		serie.setType("gauge");
		int valDonut = donutSpinner.getSelection();
		if (valDonut > 0 ){
			serie.setDonut(valDonut);			
		}
		logger.debug("donut: " + valDonut);
		
		logger.debug("OUT");
	}
	
}
