package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.model.bo.Chart;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.XAxis;
import it.eng.spagobi.studio.highchart.model.bo.YAxis;
import it.eng.spagobi.studio.highchart.utils.ColorButton;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AxisSection extends AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(AxisSection.class);

	Group xValuesGroup;
	Group xLabelsGroup;
	Group xTicksGroup;
	Group xStyleGroup;

	Button xAllowDecimalsCheck;
	ColorButton xAlternateGridColorButton;
	Text xCategoriesText;
	Text xDateTimeLabelFormatsText;
	Button xEndOnTickCheck;
	ColorButton xGridLineColorButton;
	Text xGridLineDashStyleText;
	Spinner xGridLineWidthSpinner;
	Spinner xIdSpinner;
	Text xLabelsText;
	ColorButton xLineColorButton;
	Spinner xLineWidthSpinner;
	Text xLinkedToText;
	Spinner xMaxSpinner;
	Spinner xMaxPaddingSpinner;
	Spinner xMaxZoomSpinner;
	Spinner xMinSpinner;
	ColorButton xMinorGridLineColorButton;
	Combo xMinorGridLineDashStyleCombo;
	Spinner xMinorGridLineWidthSpinner;
	ColorButton xMinorTickColorButton;
	Combo xMinorTickIntervalCombo;
	Spinner xMinorTickLengthSpinner;
	Combo xMinorTickPosition;
	Spinner xMinorTickWidthSpinner;
	Spinner xMinPaddingSpinner;
	Spinner xOffset;
	Button xOppositeCheck;
	Text xPlotBandsText;
	Text xPlotLinesText;
	Button xReversedCheck;
	Button xShowFirstLabelCheck;
	Button xShowLastLabelCheck;
	Spinner xStartOfWeekSpinner;
	Button xStartOnTickButton;
	ColorButton xTickColorButton;
	Spinner xTickIntervalSpinner;
	Spinner xTickLengthSpinner;
	Combo xTickmarkPlacementCombo;
	Spinner xTickPixelIntervalSpinner;
	Combo xTickPositionCombo; //combo
	Spinner xTickWidthSpinner;
	Text xTitleText;
	Combo xTypeCombo; //combo
	Text xAliasText;

	
	
	Group yValuesGroup;
	Group yLabelsGroup;
	Group yTicksGroup;
	Group yStyleGroup;

	Button yAllowDecimalsCheck;
	ColorButton yAlternateGridColorButton;
	Text yCategoriesText;
	Text yDateTimeLabelFormatsText;
	Button yEndOnTickCheck;
	ColorButton yGridLineColorButton;
	Text yGridLineDashStyleText;
	Spinner yGridLineWidthSpinner;
	Spinner yIdSpinner;
	Text yLabelsText;
	ColorButton yLineColorButton;
	Spinner yLineWidthSpinner;
	Text yLinkedToText;
	Spinner yMaxSpinner;
	Spinner yMaxPaddingSpinner;
	Spinner yMaxZoomSpinner;
	Spinner yMinSpinner;
	ColorButton yMinorGridLineColorButton;
	Combo yMinorGridLineDashStyleCombo;
	Spinner yMinorGridLineWidthSpinner;
	ColorButton yMinorTickColorButton;
	Combo yMinorTickIntervalCombo;
	Spinner yMinorTickLengthSpinner;
	Combo yMinorTickPosition;
	Spinner yMinorTickWidthSpinner;
	Spinner yMinPaddingSpinner;
	Spinner yOffset;
	Button yOppositeCheck;
	Text yPlotBandsText;
	Text yPlotLinesText;
	Button yReversedCheck;
	Button yShowFirstLabelCheck;
	Button yShowLastLabelCheck;
	Spinner yStartOfWeekSpinner;
	Button yStartOnTickButton;
	ColorButton yTickColorButton;
	Spinner yTickIntervalSpinner;
	Spinner yTickLengthSpinner;
	Combo yTickmarkPlacementCombo;
	Spinner yTickPixelIntervalSpinner;
	Combo yTickPositionCombo; //combo
	Spinner yTickWidthSpinner;
	Text yTitleText;
	Combo yTypeCombo; //combo
	Text yAliasText;

	
	public AxisSection(HighChart highChart) {
		super(highChart);

	}

	public void addListeners(){
		logger.debug("IN");
	

		xMinorGridLineDashStyleCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = xMinorGridLineDashStyleCombo.getItem(xMinorGridLineDashStyleCombo.getSelectionIndex());
				highChart.getxAxis().setMinorGridLineDashStyle(value);
			}
		});

		xTickmarkPlacementCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = xTickmarkPlacementCombo.getItem(xTickmarkPlacementCombo.getSelectionIndex());
				highChart.getxAxis().setTickmarkPlacement(value);
			}
		});
		xTickPositionCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = xTickPositionCombo.getItem(xTickPositionCombo.getSelectionIndex());
				highChart.getxAxis().setTickPosition(value);
			}
		});

		xTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = xTypeCombo.getItem(xTypeCombo.getSelectionIndex());
				highChart.getxAxis().setType(value);
			}
		});

		xMinorTickIntervalCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = xMinorTickIntervalCombo.getItem(xMinorTickIntervalCombo.getSelectionIndex());
				highChart.getxAxis().setMinorTickInterval(value);
			}
		});

		xAlternateGridColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  xAlternateGridColorButton.handleSelctionEvent(xAlternateGridColorButton.getColorLabel().getShell());
				highChart.getxAxis().setAlternateGridColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	
		
		xGridLineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  xGridLineColorButton.handleSelctionEvent(xGridLineColorButton.getColorLabel().getShell());
				highChart.getxAxis().setGridLineColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	
		xLineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  xLineColorButton.handleSelctionEvent(xLineColorButton.getColorLabel().getShell());
				highChart.getxAxis().setLineColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		

		xTickColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  xTickColorButton.handleSelctionEvent(xTickColorButton.getColorLabel().getShell());
				highChart.getxAxis().setTickColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		

		xMinorGridLineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  xMinorGridLineColorButton.handleSelctionEvent(xMinorGridLineColorButton.getColorLabel().getShell());
				highChart.getxAxis().setMinorGridLineColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		
		
		xMinorTickColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  xMinorTickColorButton.handleSelctionEvent(xMinorTickColorButton.getColorLabel().getShell());
				highChart.getxAxis().setMinorTickColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		
		
		xEndOnTickCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xEndOnTickCheck.getSelection();
				highChart.getxAxis().setEndOnTick(selection);
				editor.setIsDirty(true);
			}
		});

		xAllowDecimalsCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xAllowDecimalsCheck.getSelection();
				highChart.getxAxis().setAllowDecimals(selection);
				editor.setIsDirty(true);
			}
		});
		
		xOppositeCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xOppositeCheck.getSelection();
				highChart.getxAxis().setOpposite(selection);
				editor.setIsDirty(true);
			}
		});
		
		xReversedCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xReversedCheck.getSelection();
				highChart.getxAxis().setReversed(selection);
				editor.setIsDirty(true);
			}
		});

		xShowFirstLabelCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xShowFirstLabelCheck.getSelection();
				highChart.getxAxis().setShowFirstLabel(selection);
				editor.setIsDirty(true);
			}
		});

		xShowLastLabelCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xShowLastLabelCheck.getSelection();
				highChart.getxAxis().setShowLastLabel(selection);
				editor.setIsDirty(true);
			}
		});

		xStartOnTickButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = xStartOnTickButton.getSelection();
				highChart.getxAxis().setStartOnTick(selection);
				editor.setIsDirty(true);
			}
		});

		xCategoriesText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xCategoriesText.getText();
				highChart.getxAxis().setCategories(value);
			}
		});
		
		xDateTimeLabelFormatsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xDateTimeLabelFormatsText.getText();
				highChart.getxAxis().setDateTimeLabelFormats(value);
			}
		});

		xLabelsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xLabelsText.getText();
				highChart.getxAxis().setLabels(value);
			}
		});
	
		xTitleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xTitleText.getText();
				highChart.getxAxis().getTitleAxis().setText(value);
			}
		});
		xAliasText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xAliasText.getText();
				if(value.equalsIgnoreCase(""))value=null;
				highChart.getxAxis().setAlias(value);
			}
		});
		xPlotBandsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xPlotBandsText.getText();
				highChart.getxAxis().setPlotBands(value);
			}
		});
		
		xPlotLinesText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xPlotLinesText.getText();
				highChart.getxAxis().setPlotLines(value);
			}
		});
		
		xGridLineDashStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xGridLineDashStyleText.getText();
				highChart.getxAxis().setGridLineDashStyle(value);
			}
		});
		
		xGridLineDashStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xGridLineDashStyleText.getText();
				highChart.getxAxis().setGridLineDashStyle(value);
			}
		});
		
		xLinkedToText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = xLinkedToText.getText();
				highChart.getxAxis().setLinkedTo(value);
			}
		});
	

		xGridLineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xGridLineWidthSpinner.getSelection();
				highChart.getxAxis().setGridLineWidth(val);
			}
		});

//		xIdSpinner.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent event) {
//				editor.setIsDirty(true);
//				int val = xIdSpinner.getSelection();
//				highChart.getxAxis().setId(val);
//			}
//		});
		
		xLineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xLineWidthSpinner.getSelection();
				highChart.getxAxis().setLineWidth(val);
			}
		});
		
		xMaxSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xMaxSpinner.getSelection();
				highChart.getxAxis().setMax(val);
			}
		});
		
		xMaxPaddingSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xMaxPaddingSpinner.getSelection();
				//TODO FLOAT
				float valf = 0;
				highChart.getxAxis().setMaxPadding(valf);
			}
		});

		xMaxZoomSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xMaxZoomSpinner.getSelection();
				highChart.getxAxis().setMaxZoom(val);
			}
		});
		xMinSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xMinSpinner.getSelection();
				highChart.getxAxis().setMin(val);
			}
		});
		
		xMinorGridLineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xMinorGridLineWidthSpinner.getSelection();
				highChart.getxAxis().setMinorGridLineWidth(val);
			}
		});

		xMinorTickLengthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xMinorTickLengthSpinner.getSelection();
				highChart.getxAxis().setMinorTickLength(val);
			}
		});
		
		xMinorTickWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xMinorTickWidthSpinner.getSelection();
				highChart.getxAxis().setMinorTickWidth(val);
			}
		});

		xMinPaddingSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xMinPaddingSpinner.getSelection();
				//TODO FLOAT
				float valf = 0;
				highChart.getxAxis().setMinPadding(valf);
			}
		});
		
		xOffset.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xOffset.getSelection();
				highChart.getxAxis().setOffset(val);
			}
		});

		xStartOfWeekSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xStartOfWeekSpinner.getSelection();
				highChart.getxAxis().setStartOfWeek(val);
			}
		});
		
		xTickIntervalSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xTickIntervalSpinner.getSelection();
				highChart.getxAxis().setTickInterval(val);
			}
		});
		
		xTickLengthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xTickLengthSpinner.getSelection();
				highChart.getxAxis().setTickLength(val);
			}
		});

		xTickPixelIntervalSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xTickPixelIntervalSpinner.getSelection();
				highChart.getxAxis().setTickPixelInterval(val);
			}
		});
		
		xTickWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =xTickWidthSpinner.getSelection();
				highChart.getxAxis().setTickWidth(val);
			}
		});

		
		
		
		// -------------- Y ----------------------------
		
		yMinorGridLineDashStyleCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = yMinorGridLineDashStyleCombo.getItem(yMinorGridLineDashStyleCombo.getSelectionIndex());
				highChart.getyAxis().setMinorGridLineDashStyle(value);
			}
		});

		yTickmarkPlacementCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = yTickmarkPlacementCombo.getItem(yTickmarkPlacementCombo.getSelectionIndex());
				highChart.getyAxis().setTickmarkPlacement(value);
			}
		});
		yTickPositionCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = yTickPositionCombo.getItem(yTickPositionCombo.getSelectionIndex());
				highChart.getyAxis().setTickPosition(value);
			}
		});

		yTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = yTypeCombo.getItem(yTypeCombo.getSelectionIndex());
				highChart.getyAxis().setType(value);
			}
		});

		yMinorTickIntervalCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = yMinorTickIntervalCombo.getItem(yMinorTickIntervalCombo.getSelectionIndex());
				highChart.getyAxis().setMinorTickInterval(value);
			}
		});

		yAlternateGridColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  yAlternateGridColorButton.handleSelctionEvent(yAlternateGridColorButton.getColorLabel().getShell());
				highChart.getyAxis().setAlternateGridColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	
		
		yGridLineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  yGridLineColorButton.handleSelctionEvent(yGridLineColorButton.getColorLabel().getShell());
				highChart.getyAxis().setGridLineColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	
		yLineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  yLineColorButton.handleSelctionEvent(yLineColorButton.getColorLabel().getShell());
				highChart.getyAxis().setLineColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		

		yTickColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  yTickColorButton.handleSelctionEvent(yTickColorButton.getColorLabel().getShell());
				highChart.getyAxis().setTickColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		

		yMinorGridLineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  yMinorGridLineColorButton.handleSelctionEvent(yMinorGridLineColorButton.getColorLabel().getShell());
				highChart.getyAxis().setMinorGridLineColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		
		
		yMinorTickColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  yMinorTickColorButton.handleSelctionEvent(yMinorTickColorButton.getColorLabel().getShell());
				highChart.getyAxis().setMinorTickColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		
		
		yEndOnTickCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yEndOnTickCheck.getSelection();
				highChart.getyAxis().setEndOnTick(selection);
				editor.setIsDirty(true);
			}
		});

		yAllowDecimalsCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yAllowDecimalsCheck.getSelection();
				highChart.getyAxis().setAllowDecimals(selection);
				editor.setIsDirty(true);
			}
		});
		
		yOppositeCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yOppositeCheck.getSelection();
				highChart.getyAxis().setOpposite(selection);
				editor.setIsDirty(true);
			}
		});
		
		yReversedCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yReversedCheck.getSelection();
				highChart.getyAxis().setReversed(selection);
				editor.setIsDirty(true);
			}
		});

		yShowFirstLabelCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yShowFirstLabelCheck.getSelection();
				highChart.getyAxis().setShowFirstLabel(selection);
				editor.setIsDirty(true);
			}
		});

		yShowLastLabelCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yShowLastLabelCheck.getSelection();
				highChart.getyAxis().setShowLastLabel(selection);
				editor.setIsDirty(true);
			}
		});

		yStartOnTickButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = yStartOnTickButton.getSelection();
				highChart.getyAxis().setStartOnTick(selection);
				editor.setIsDirty(true);
			}
		});

		yCategoriesText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yCategoriesText.getText();
				highChart.getyAxis().setCategories(value);
			}
		});
		
		yDateTimeLabelFormatsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yDateTimeLabelFormatsText.getText();
				highChart.getyAxis().setDateTimeLabelFormats(value);
			}
		});

		yLabelsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yLabelsText.getText();
				highChart.getyAxis().setLabels(value);
			}
		});
	
		yTitleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yTitleText.getText();
				highChart.getyAxis().getTitleAxis().setText(value);
			}
		});
		yAliasText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yAliasText.getText();
				if(value.equalsIgnoreCase(""))value=null;
				highChart.getyAxis().setAlias(value);
			}
		});
	
		yPlotBandsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yPlotBandsText.getText();
				highChart.getyAxis().setPlotBands(value);
			}
		});
		
		yPlotLinesText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yPlotLinesText.getText();
				highChart.getyAxis().setPlotLines(value);
			}
		});
		
		yGridLineDashStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yGridLineDashStyleText.getText();
				highChart.getyAxis().setGridLineDashStyle(value);
			}
		});
		
		yGridLineDashStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yGridLineDashStyleText.getText();
				highChart.getyAxis().setGridLineDashStyle(value);
			}
		});
		
		yLinkedToText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = yLinkedToText.getText();
				highChart.getyAxis().setLinkedTo(value);
			}
		});
	

		yGridLineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = yGridLineWidthSpinner.getSelection();
				highChart.getyAxis().setGridLineWidth(val);
			}
		});

//		xIdSpinner.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent event) {
//				editor.setIsDirty(true);
//				int val = xIdSpinner.getSelection();
//				highChart.getxAxis().setId(val);
//			}
//		});
		
		yLineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = yLineWidthSpinner.getSelection();
				highChart.getyAxis().setLineWidth(val);
			}
		});
		
		yMaxSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = yMaxSpinner.getSelection();
				highChart.getyAxis().setMax(val);
			}
		});
		
		yMaxPaddingSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = yMaxPaddingSpinner.getSelection();
				//TODO FLOAT
				float valf = 0;
				highChart.getyAxis().setMaxPadding(valf);
			}
		});

		yMaxZoomSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = yMaxZoomSpinner.getSelection();
				highChart.getyAxis().setMaxZoom(val);
			}
		});
		yMinSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = yMinSpinner.getSelection();
				highChart.getyAxis().setMin(val);
			}
		});
		
		yMinorGridLineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yMinorGridLineWidthSpinner.getSelection();
				highChart.getyAxis().setMinorGridLineWidth(val);
			}
		});

		yMinorTickLengthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yMinorTickLengthSpinner.getSelection();
				highChart.getyAxis().setMinorTickLength(val);
			}
		});
		
		yMinorTickWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yMinorTickWidthSpinner.getSelection();
				highChart.getyAxis().setMinorTickWidth(val);
			}
		});

		yMinPaddingSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yMinPaddingSpinner.getSelection();
				//TODO FLOAT
				float valf = 0;
				highChart.getyAxis().setMinPadding(valf);
			}
		});
		
		yOffset.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yOffset.getSelection();
				highChart.getyAxis().setOffset(val);
			}
		});

		yStartOfWeekSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yStartOfWeekSpinner.getSelection();
				highChart.getyAxis().setStartOfWeek(val);
			}
		});
		
		yTickIntervalSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yTickIntervalSpinner.getSelection();
				highChart.getyAxis().setTickInterval(val);
			}
		});
		
		yTickLengthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yTickLengthSpinner.getSelection();
				highChart.getyAxis().setTickLength(val);
			}
		});

		yTickPixelIntervalSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yTickPixelIntervalSpinner.getSelection();
				highChart.getyAxis().setTickPixelInterval(val);
			}
		});
		
		yTickWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val =yTickWidthSpinner.getSelection();
				highChart.getyAxis().setTickWidth(val);
			}
		});
		
		
		
		
		
	}
	
	
	
	
	
	

	@Override
	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);

		// ++++++++++++++  Chart common settings section ++++++++++++++ 
		logger.debug("IN");

		final Chart chart = highChart.getChart();
		XAxis xAxis = highChart.getxAxis();
		YAxis yAxis = highChart.getyAxis();

		section.setText("Axis section");
		section.setDescription("Fill attribute regarding the chart");

		//************ Margin Group ************

		//// ------------------------ XAxis --------------------
		Label xLabel = new Label(composite, SWT.BORDER | SWT.BOLD );
		xLabel.setForeground(new Color(composite.getDisplay(),0,200,200));
		xLabel.setSize(14, 14);
		xLabel.setText("X AXIS");
		Label spaceLabel = new Label(composite, SWT.NULL);
		spaceLabel.setText("");
		
		xLabelsGroup = createNColGroup("Text and labels: ", 6);
		xLabelsGroup.setBackground(SWTUtils.getColor(xLabelsGroup.getDisplay(), SWTUtils.LIGHT_YELLOW));

		xTitleText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getTitleAxis().getText(), "Title: ");
		xAliasText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getAlias(), "Alias: ");
		xCategoriesText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getCategories(), "Categories: ");
		xDateTimeLabelFormatsText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getDateTimeLabelFormats(), "Date time label format: ");
		xLabelsText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getLabels(), "Labels: ");
		xLinkedToText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getLinkedTo(), "Linked to: ");
		xPlotBandsText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getPlotBands(), "Plot Band: ");
		xPlotLinesText = SWTUtils.drawText(toolkit, xLabelsGroup, xAxis.getPlotLines(), "Plot Lines: ");
		xReversedCheck = SWTUtils.drawCheck(xLabelsGroup, xAxis.getReversed(), "Reversed: ");
		xShowFirstLabelCheck = SWTUtils.drawCheck(xLabelsGroup, xAxis.getShowFirstLabel(), "Show First Label: ");
		xShowLastLabelCheck = SWTUtils.drawCheck(xLabelsGroup, xAxis.getShowLastLabel(), "Show Last Label");
		
		
		xValuesGroup = createNColGroup("Number and Values Settings: ", 6);
		xValuesGroup.setBackground(SWTUtils.getColor(xValuesGroup.getDisplay(), SWTUtils.LIGHT_YELLOW));

		xMaxSpinner = SWTUtils.drawSpinner(xValuesGroup, xAxis.getMax(),"Max: ");
		xMinSpinner = SWTUtils.drawSpinner(xValuesGroup, xAxis.getMin(),"Min: ");
		xStartOfWeekSpinner = SWTUtils.drawSpinner(xValuesGroup, xAxis.getStartOfWeek(),"Start of Week: ");
		xAllowDecimalsCheck = SWTUtils.drawCheck(xValuesGroup, xAxis.isAllowDecimals(), "Opposite: ");
		xOppositeCheck = SWTUtils.drawCheck(xValuesGroup, xAxis.getOpposite(), "Opposite: ");
		xTypeCombo = SWTUtils.drawCombo(xValuesGroup, new String[]{"", "linear", "datetime"}, xAxis.getType(), "Type: ");



		xTicksGroup = createNColGroup("Ticks: ", 6);
		xTicksGroup.setBackground(SWTUtils.getColor(xTicksGroup.getDisplay(), SWTUtils.LIGHT_YELLOW));
		xMinorTickColorButton = SWTUtils.drawColorButton(toolkit, xTicksGroup, xAxis.getMinorTickColor(), "Minor Tick Color: ");
		xTickColorButton =  SWTUtils.drawColorButton(toolkit, xTicksGroup, xAxis.getTickColor(), "Tick color: ");
		xMinorTickIntervalCombo = SWTUtils.drawCombo(xTicksGroup, new String[]{"", "null", "auto"}, xAxis.getMinorTickInterval(), "Minor Tick Interval: ");
		xTickmarkPlacementCombo = SWTUtils.drawCombo(xTicksGroup, new String[]{"", "between", "on"}, xAxis.getTickmarkPlacement(), "Tickmarc Placement: ");
		xTickPositionCombo = SWTUtils.drawCombo(xTicksGroup, new String[]{"", "outside", "inside"}, xAxis.getTickPosition(), "Tick Position: ");
		xMinorTickPosition = SWTUtils.drawCombo(xTicksGroup, new String[]{"", "outside", "inside"}, xAxis.getMinorTickPosition(), "Minor Tick Position: ");
		xTickWidthSpinner = SWTUtils.drawSpinner(xTicksGroup, xAxis.getTickWidth(),"Tick Width: ");
		xTickPixelIntervalSpinner = SWTUtils.drawSpinner(xTicksGroup, xAxis.getTickPixelInterval(),"Tick Pixel Interval: ");
		xTickIntervalSpinner = SWTUtils.drawSpinner(xTicksGroup, xAxis.getTickInterval(),"Tick Interval: ");
		xTickLengthSpinner = SWTUtils.drawSpinner(xTicksGroup, xAxis.getTickLength(),"Tick Lenght: ");
		xMinorTickWidthSpinner = SWTUtils.drawSpinner(xTicksGroup, xAxis.getMinorTickWidth(),"Minor Tick Width: ");
		xMinorTickLengthSpinner = SWTUtils.drawSpinner(xTicksGroup, xAxis.getMinorTickLength(),"Minor Tick Lenght: ");
		xStartOnTickButton = SWTUtils.drawCheck(xTicksGroup, xAxis.getStartOnTick(), "Start on tick: ");
		xEndOnTickCheck = SWTUtils.drawCheck(xTicksGroup, xAxis.isEndOnTick(), "End on tick: ");


		xStyleGroup = createNColGroup("Colors and Style: ", 6);
		xStyleGroup.setBackground(SWTUtils.getColor(xStyleGroup.getDisplay(), SWTUtils.LIGHT_YELLOW));
		xMinorGridLineWidthSpinner =  SWTUtils.drawSpinner(xStyleGroup, xAxis.getMinorGridLineWidth(),"Minor Grid Line Width: ");
		xMaxPaddingSpinner =  SWTUtils.drawSpinner(xStyleGroup, xAxis.getMaxPadding(),"Max Padding Spinner: ");
		xMaxZoomSpinner = SWTUtils.drawSpinner(xStyleGroup, xAxis.getMaxZoom(),"Max Zoom Spinner: ");
		xMinPaddingSpinner = SWTUtils.drawSpinner(xStyleGroup, xAxis.getMinPadding(),"Min Padding Spinner: ");
		xGridLineWidthSpinner = SWTUtils.drawSpinner(xStyleGroup, xAxis.getGridLineWidth(),"Grid line width: "); 
		xLineWidthSpinner = SWTUtils.drawSpinner(xStyleGroup, xAxis.getMaxPadding(),"Line Width: ");
		xOffset = SWTUtils.drawSpinner(xStyleGroup, xAxis.getOffset(),"Offset: ");
		xMinorGridLineDashStyleCombo = SWTUtils.drawCombo(xStyleGroup, new String[]{"", "between", "on"}, xAxis.getMinorGridLineDashStyle(), "Minor Grid Line dash Style: ");
		xGridLineColorButton =  SWTUtils.drawColorButton(toolkit, xStyleGroup, xAxis.getGridLineColor(), "Grid line color: ");
		xAlternateGridColorButton =  SWTUtils.drawColorButton(toolkit, xStyleGroup, xAxis.getAlternateGridColor(), "Alternate Grid Color: ");
		xLineColorButton =  SWTUtils.drawColorButton(toolkit, xStyleGroup, xAxis.getLineColor(), "Line color: ");
		xMinorGridLineColorButton =  SWTUtils.drawColorButton(toolkit, xStyleGroup, xAxis.getMinorGridLineColor(), "Minor grid line color: ");
		xGridLineDashStyleText = SWTUtils.drawText(toolkit, xStyleGroup, xAxis.getGridLineDashStyle(), "Grid Line Dash Style: ");

		//------------------------------------------ Y AXIS --------------------------------------------------------
		Label yLabel = new Label(composite, SWT.BORDER | SWT.BOLD );
		yLabel.setForeground(new Color(composite.getDisplay(),0,200,200));
		yLabel.setSize(14, 14);
		yLabel.setText("Y AXIS");
		Label spaceLabel2 = new Label(composite, SWT.NULL);
		spaceLabel2.setText("");
		
		yLabelsGroup = createNColGroup("Y Text and labels: ", 6);
		yLabelsGroup.setBackground(SWTUtils.getColor(yLabelsGroup.getDisplay(), SWTUtils.LIGHT_ORANGE));
		yTitleText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getTitleAxis().getText(), "Title: ");
		yAliasText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getAlias(), "Alias: ");

		yCategoriesText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getCategories(), "Categories: ");
		yDateTimeLabelFormatsText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getDateTimeLabelFormats(), "Date time label format: ");
		yLabelsText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getLabels(), "Labels: ");
		yLinkedToText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getLinkedTo(), "Linked to: ");
		yPlotBandsText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getPlotBands(), "Plot Band: ");
		yPlotLinesText = SWTUtils.drawText(toolkit, yLabelsGroup, yAxis.getPlotLines(), "Plot Lines: ");
		yReversedCheck = SWTUtils.drawCheck(yLabelsGroup, yAxis.getReversed(), "Reversed: ");
		yShowFirstLabelCheck = SWTUtils.drawCheck(yLabelsGroup, yAxis.getShowFirstLabel(), "Show First Label: ");
		yShowLastLabelCheck = SWTUtils.drawCheck(yLabelsGroup, yAxis.getShowLastLabel(), "Show Last Label");

		
		
		yValuesGroup = createNColGroup("Number and Values Settings: ", 6);
		yValuesGroup.setBackground(SWTUtils.getColor(yValuesGroup.getDisplay(), SWTUtils.LIGHT_ORANGE));

		yMaxSpinner = SWTUtils.drawSpinner(yValuesGroup, yAxis.getMax(),"Max: ");
		yMinSpinner = SWTUtils.drawSpinner(yValuesGroup, yAxis.getMin(),"Min: ");
		yStartOfWeekSpinner = SWTUtils.drawSpinner(yValuesGroup, yAxis.getStartOfWeek(),"Start of Week: ");
		yAllowDecimalsCheck = SWTUtils.drawCheck(yValuesGroup, yAxis.isAllowDecimals(), "Opposite: ");
		yOppositeCheck = SWTUtils.drawCheck(yValuesGroup, yAxis.getOpposite(), "Opposite: ");
		yTypeCombo = SWTUtils.drawCombo(yValuesGroup, new String[]{"", "linear", "datetime"}, yAxis.getType(), "Type: ");



		yTicksGroup = createNColGroup("Ticks: ", 6);
		yTicksGroup.setBackground(SWTUtils.getColor(yTicksGroup.getDisplay(), SWTUtils.LIGHT_ORANGE));
		yMinorTickColorButton = SWTUtils.drawColorButton(toolkit, yTicksGroup, yAxis.getMinorTickColor(), "Minor Tick Color: ");
		yTickColorButton =  SWTUtils.drawColorButton(toolkit, yTicksGroup, yAxis.getTickColor(), "Tick color: ");
		yMinorTickLengthSpinner = SWTUtils.drawSpinner(yTicksGroup, yAxis.getMinorTickLength(),"Minor Tick Lenght: ");
		yTickIntervalSpinner = SWTUtils.drawSpinner(yTicksGroup, yAxis.getTickInterval(),"Tick Interval: ");
		yTickPixelIntervalSpinner = SWTUtils.drawSpinner(yTicksGroup, yAxis.getTickPixelInterval(),"Tick Pixel Interval: ");
		yTickWidthSpinner = SWTUtils.drawSpinner(yTicksGroup, yAxis.getTickWidth(),"Tick Width: ");
		yTickLengthSpinner = SWTUtils.drawSpinner(yTicksGroup, yAxis.getTickLength(),"Tick Lenght: ");
		yMinorTickWidthSpinner = SWTUtils.drawSpinner(yTicksGroup, yAxis.getMinorTickWidth(),"Minor Tick Width: ");
		yTickmarkPlacementCombo = SWTUtils.drawCombo(yTicksGroup, new String[]{"", "between", "on"}, yAxis.getTickmarkPlacement(), "Tickmarc Placement: ");
		yMinorTickIntervalCombo = SWTUtils.drawCombo(yTicksGroup, new String[]{"", "null", "auto"}, yAxis.getMinorTickInterval(), "Minor Tick Interval: ");
		yMinorTickPosition = SWTUtils.drawCombo(yTicksGroup, new String[]{"", "outside", "inside"}, yAxis.getMinorTickPosition(), "Minor Tick Position: ");
		yTickPositionCombo = SWTUtils.drawCombo(yTicksGroup, new String[]{"", "outside", "inside"}, yAxis.getTickPosition(), "Tick Position: ");
		yEndOnTickCheck = SWTUtils.drawCheck(yTicksGroup, yAxis.isEndOnTick(), "End on tick: ");
		yStartOnTickButton = SWTUtils.drawCheck(yTicksGroup, yAxis.getStartOnTick(), "Start on tick: ");



		yStyleGroup = createNColGroup("Colors and Style: ", 6);
		yStyleGroup.setBackground(SWTUtils.getColor(yStyleGroup.getDisplay(), SWTUtils.LIGHT_ORANGE));
		yGridLineWidthSpinner = SWTUtils.drawSpinner(yStyleGroup, yAxis.getGridLineWidth(),"Grid line width: "); 
		yMinorGridLineWidthSpinner =  SWTUtils.drawSpinner(yStyleGroup, yAxis.getMinorGridLineWidth(),"Minor Grid Line Width: ");
		yMaxPaddingSpinner =  SWTUtils.drawSpinner(yStyleGroup, yAxis.getMaxPadding(),"Max Padding Spinner: ");
		yMaxZoomSpinner = SWTUtils.drawSpinner(yStyleGroup, yAxis.getMaxZoom(),"Max Zoom Spinner: ");
		yMinPaddingSpinner = SWTUtils.drawSpinner(yStyleGroup, yAxis.getMinPadding(),"Min Padding Spinner: ");
		yLineWidthSpinner = SWTUtils.drawSpinner(yStyleGroup, yAxis.getMaxPadding(),"Line Width: ");
		yOffset = SWTUtils.drawSpinner(yStyleGroup, yAxis.getOffset(),"Offset: ");
		yMinorGridLineDashStyleCombo = SWTUtils.drawCombo(yStyleGroup, new String[]{"", "between", "on"}, yAxis.getMinorGridLineDashStyle(), "Minor Grid Line dash Style: ");
		yAlternateGridColorButton =  SWTUtils.drawColorButton(toolkit, yStyleGroup, yAxis.getAlternateGridColor(), "Alternate Grid Color: ");
		yGridLineColorButton =  SWTUtils.drawColorButton(toolkit, yStyleGroup, yAxis.getGridLineColor(), "Grid line color: ");
		yMinorGridLineColorButton =  SWTUtils.drawColorButton(toolkit, yStyleGroup, yAxis.getMinorGridLineColor(), "Minor grid line color: ");
		yLineColorButton =  SWTUtils.drawColorButton(toolkit, yStyleGroup, yAxis.getLineColor(), "Line color: ");
		yGridLineDashStyleText = SWTUtils.drawText(toolkit, yStyleGroup, yAxis.getGridLineDashStyle(), "Grid Line Dash Style: ");

		section.setClient(composite);
		
		addListeners();
		
		logger.debug("OUT");
	}
}
