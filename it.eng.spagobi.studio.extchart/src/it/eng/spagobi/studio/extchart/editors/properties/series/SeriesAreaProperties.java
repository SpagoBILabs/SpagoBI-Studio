package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.model.bo.Series;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;


public class SeriesAreaProperties  extends SeriesProperties{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesAreaProperties.class);
	Button showInLegendButton;
	Text donutText;
	
	public SeriesAreaProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		setDrawXField(true);
		setDrawYFieldList(true);
		setDrawMarkerConfig(true);		
	}
	
	public void drawProperties(){
		logger.debug("IN");
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "area");
		serie.setType("area");
		super.drawProperties();
		
		logger.debug("Donut");
		toolkit.createLabel(dialog, "Donut (number or 'false'): ");
		donutText = toolkit.createText(dialog, "default value", SWT.NULL);
		if(serie.getDonut() != null){
			donutText.setText(serie.getDonut().toString());
		}
		
		logger.debug("Show in legend");
		showInLegendButton = toolkit.createButton(dialog, "Show In Legend", SWT.CHECK);
		if(serie.getShowInLegened() != null && serie.getShowInLegened().booleanValue() == true){
			showInLegendButton.setSelection(true);
		}
		
		
		logger.debug("OUT");
		
	}
	
	
	
	public void performOk(){
		logger.debug("IN");
		super.performOk();	
		
		serie.setDonut(Integer.parseInt(donutText.getText()));
		logger.debug("donut " +donutText.getText());
		
		boolean selectionLegend = showInLegendButton.getSelection();
		serie.setShowInLegened(selectionLegend);
		logger.debug("selectionLegend " +selectionLegend);
		logger.debug("OUT");
	}

}
