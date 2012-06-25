package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.slf4j.LoggerFactory;

public class SeriesBarAndLineProperties extends SeriesProperties {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesBarAndLineProperties.class);

	Combo typeCombo;
	Spinner gutterSpinner;
	Button highlightButton; 
	Button stackedButton;
	Button smoothButton;
	Button fillButton;

	public SeriesBarAndLineProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		//setDrawXField(true);
		//setDrawYField(true);
		setDrawYFieldList(true);
		setDrawMarkerConfig(true);		

	}


	public void drawProperties(){
		logger.debug("IN");


		String[] types = new String[]{"bar", "column", "line"};
		typeCombo = SWTUtils.drawCombo(dialog, types, serie.getType(), "Type: ");

		super.drawProperties();

		// -----------------------------------------------

		gutterSpinner = SWTUtils.drawSpinner(dialog, serie.getGutter(), "Gutter: ");

		// -----------------------------------------------

		logger.debug("Highlight");
		highlightButton = toolkit.createButton(dialog, "Highlight: ", SWT.CHECK);
		if(serie.getHighlight() != null && serie.getHighlight().booleanValue() == true){
			highlightButton.setSelection(true);
		}
		
		// -----------------------------------------------

		logger.debug("Stacked");
		stackedButton = toolkit.createButton(dialog, "Stacked: ", SWT.CHECK);
		if(serie.getStacked() != null && serie.getStacked().booleanValue() == true){
			stackedButton.setSelection(true);
		}

		logger.debug("Smooth");
		smoothButton = toolkit.createButton(dialog, "Smooth: ", SWT.CHECK);
		if(serie.getSmooth() != null && serie.getSmooth().booleanValue() == true){
			smoothButton.setSelection(true);
		}
		
		logger.debug("Fill");
		fillButton = toolkit.createButton(dialog, "Fill: ", SWT.CHECK);
		if(serie.getFill() != null && serie.getFill().booleanValue() == true){
			fillButton.setSelection(true);
		}
		
		toolkit.createLabel(dialog, " ");
		toolkit.createLabel(dialog, " ");

		
		logger.debug("OUT");
	}



	public void performOk(){
		logger.debug("IN");
		super.performOk();		

		String valueType = typeCombo.getItem(typeCombo.getSelectionIndex());
		serie.setType(valueType);
		logger.debug("type " +valueType);

		boolean selectionHigh = highlightButton.getSelection();
		serie.setHighlight(selectionHigh);
		logger.debug("highlight " +selectionHigh);

		int valGutter = gutterSpinner.getSelection();
		serie.setGutter(valGutter);
		logger.debug("gutter " + valGutter);

		boolean selectionStacked = stackedButton.getSelection();
		serie.setStacked(selectionStacked);
		logger.debug("stacked " +selectionStacked);
		
		boolean selectionSmooth = smoothButton.getSelection();
		serie.setSmooth(selectionSmooth);
		logger.debug("smooth " +selectionSmooth);
		

		boolean selectionFill = fillButton.getSelection();
		serie.setFill(selectionFill);
		logger.debug("fill " +selectionFill);
		
		logger.debug("OUT");
	}

}
