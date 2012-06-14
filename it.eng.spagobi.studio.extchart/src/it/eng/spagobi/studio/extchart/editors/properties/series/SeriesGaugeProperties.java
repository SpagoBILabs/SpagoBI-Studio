package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

public class SeriesGaugeProperties extends SeriesProperties {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesGaugeProperties.class);
	Text donutText;
	
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
		toolkit.createLabel(dialog, "Donut (number or 'false'): ");
		donutText = toolkit.createText(dialog, "default value", SWT.NULL);
		if(serie.getDonut() != null){
			donutText.setText(serie.getDonut().toString());
		}
	
		logger.debug("OUT");
	}

	
	public void performOk(){
		logger.debug("IN");
		super.performOk();	
		
		serie.setDonut(Integer.parseInt(donutText.getText()));
		logger.debug("donut " +donutText.getText());
		logger.debug("OUT");
	}
	
}
