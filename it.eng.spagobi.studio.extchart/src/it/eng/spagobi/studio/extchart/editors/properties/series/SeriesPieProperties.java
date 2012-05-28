package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;

import org.eclipse.swt.widgets.Shell;
import org.slf4j.LoggerFactory;

public class SeriesPieProperties  extends SeriesProperties{

	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesPieProperties.class);

	public SeriesPieProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		setDrawField(true);
	}
	
	public void drawProperties(){
		logger.debug("IN");
		toolkit.createLabel(dialog, "Type: ");
		toolkit.createLabel(dialog, "pie");
		serie.setType("pie");
		super.drawProperties();
		logger.debug("OUT");
	}
	

}
