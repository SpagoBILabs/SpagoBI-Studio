package it.eng.spagobi.studio.extchart.editors.properties.axes;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.pages.editorComponent.YAxePanel;
import it.eng.spagobi.studio.extchart.utils.ExtChartConstants;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.LoggerFactory;


public class AxesNumericProperties  extends AxesProperties{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AxesNumericProperties.class);

	//Combo fieldCombo;

	Button gridButton;
	


	public AxesNumericProperties(ExtChartEditor editor, 
			Shell comp) {
		super(editor, comp);
		dialog.setSize(300,250);

	}

	public void drawProperties(){
		logger.debug("IN");

		toolkit.createLabel(dialog, "");
		toolkit.createLabel(dialog, "");
		
//		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();	
//		if(metadatas == null) metadatas = new String[0];
//		fieldCombo = SWTUtils.drawCombo(dialog, metadatas, axes.getFields(), "Field: ");

		super.drawProperties();

		toolkit.createLabel(dialog, "Grid: ");

		gridButton = SWTUtils.drawCheck(dialog, 
				axes != null && axes.getGrid() != null ? axes.getGrid() : false
						, "");



		logger.debug("OUT");

	}	



	public void performOk(){
		logger.debug("IN");
		// save
		super.performOk();

		axes.setType(ExtChartConstants.AXE_TYPE_NUMERIC);
//		if(fieldCombo.getSelectionIndex() != -1){
//			String valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
//			axes.setFields(valueField);
//			logger.debug("field " +valueField);
//		}
//		else{
//			logger.debug("no field selected");
//		}

		Boolean track = gridButton.getSelection();
		axes.setGrid(track);		
		logger.debug("grid: "+track);

		yAxePanel.getTitleLabel().setText(titleText.getText());

		logger.debug("OUT");

	}

	public YAxePanel getyAxePanel() {
		return yAxePanel;
	}

	public void setyAxePanel(YAxePanel yAxePanel) {
		this.yAxePanel = yAxePanel;
	}
	
	

	
}
