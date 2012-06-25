package it.eng.spagobi.studio.extchart.editors.properties.series.utils;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Label;
import it.eng.spagobi.studio.extchart.utils.ColorButton;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class LabelProperties extends PopupPropertiesDialog{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(LabelProperties.class);

	Label label; 
	Combo fieldCombo;
	Combo displayCombo;
	Combo orientationCombo;
	Combo anchorCombo;
	ColorButton colorButton;
	SeriesProperties father;
	
	public LabelProperties(ExtChartEditor editor, Label label, Shell comp, SeriesProperties father) {
		super(editor, comp);
		this.label = label; 
		this.editor = editor;
		this.father = father;
		toolkit = new FormToolkit(comp.getDisplay());
	}

	
	
	
	public void drawProperties(){
		logger.debug("IN");
		GridLayout gridlayout = new GridLayout(2, true);
		dialog.setLayout(gridlayout);

		
		// get All column from dataset metadata table
		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();
		if(metadatas == null) metadatas = new String[0];
		fieldCombo = SWTUtils.drawCombo(dialog, metadatas
				, label != null ? label.getField() : null
				, "Field: ");

		
		
		String[] displays =new String[]{"none", "rotate", "middle", "insideStart", "insideEnd", "outside", "over", "under"};
		
		displayCombo = SWTUtils.drawCombo(dialog, displays
				, label != null && label.getDisplay() != null ? label.getDisplay() : "none"
				, "Display: ");

		String[] orientations =new String[]{"horizontal", "vertical"};
		orientationCombo = SWTUtils.drawCombo(dialog, orientations
				, label != null && label.getOrientation() != null ? label.getOrientation() : "horizontal"
				, "Orientation: ");
		
		String[] anchors =new String[]{"middle", "top", "bottom"};
		anchorCombo = SWTUtils.drawCombo(dialog, anchors
				, label != null && label.getTextAnchor() != null ? label.getTextAnchor() : "middle"
				, "Text Anchor: ");

		colorButton = SWTUtils.drawColorButton(toolkit, dialog, 
				label != null && label.getColor() != null ? label.getColor() : "#000000"
				, "Color: ");
		if(label != null && label.getColor() != null) displayCombo.setData(label.getColor());
		colorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  colorButton.handleSelctionEvent(colorButton.getColorLabel().getShell());
				displayCombo.setData(colorSelected);
			}
		});	
		
		toolkit.createLabel(dialog, "");
		
		
		logger.debug("OUT");
	}

	
	public void performOk(){
		logger.debug("IN");
		editor.setIsDirty(true);

		if(label == null ){
			logger.debug("create a label definition");
			label = new Label();
		}
		

		String colorSelected = displayCombo.getData() != null ? displayCombo.getData().toString() : "";
		label.setColor(colorSelected);
		logger.debug("color " +colorSelected);

		String valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
		label.setField(valueField);
		logger.debug("field " +valueField);

		String displayField = displayCombo.getItem(displayCombo.getSelectionIndex());
		label.setDisplay(displayField);
		logger.debug("display " +displayField);

		String orientationField = orientationCombo.getItem(orientationCombo.getSelectionIndex());
		label.setOrientation(orientationField);
		logger.debug("orientation " +orientationField);
		
		String anchorField = anchorCombo.getItem(anchorCombo.getSelectionIndex());
		label.setTextAnchor(anchorField);
		logger.debug("text-anchor " +anchorField);
	
		// memorize new Label
		father.getLabelHolder()[0] = label;
		//father.getSerie().setLabel(label);
		logger.debug("OUT");
	}
	
	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialog.setSize(300, 200);
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!dialog.getDisplay().readAndDispatch()) {
		    	dialog.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}	
	
}
