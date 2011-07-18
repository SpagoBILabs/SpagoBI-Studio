package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.editors.HighChartEditor;
import it.eng.spagobi.studio.highchart.model.bo.Chart;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.utils.ColorButton;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
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


public class ChartSection extends AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(ChartSection.class);

	Label typeLabel;



	Combo typeCombo;
	Text styleText;
	ColorButton backgroundColorButton;
	Button animationCheck;
	Button shadowCheck;
	Text margintext;
	Spinner widthSpinner;
	Spinner heightSpinner;
	Spinner marginTopSpinner;
	Spinner marginRightSpinner;
	Spinner marginBottomSpinner;
	Spinner marginLeftSpinner;
	Spinner spacingTopSpinner;
	Spinner spacingRightSpinner;
	Spinner spacingBottomSpinner;
	Spinner spacingLeftSpinner;

	Button showAxesCheck;
	ColorButton plotBackgroundColorButton;
	Text plotBackgroundImageText;
	ColorButton plotBorderColorButton;
	Spinner plotBorderWidthSpinner;
	Button plotShadowCheck;
	Button ignoreHiddenSeriesCheck;
	Text classNameText;
	Button invertedCheck;	
	Combo zoomTypeCombo;
	ColorButton borderColorButton;
	Spinner borderRadiusSpinner;
	Spinner borderWidthSpinner;
	Button alignTicksButton;
	Button renderToText; 


	Group generalGroup; 
	Group plotGroup; 
	Group marginGroup; 
	Group borderGroup; 

	public ChartSection(HighChart highChart) {
		super(highChart);
	}


	public void addListeners(){
		logger.debug("IN");
		styleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = styleText.getText();
				highChart.getChart().setStyle(value);
			}
		});
		typeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = typeCombo.getItem(typeCombo.getSelectionIndex());
				highChart.getChart().setDefaultSeriesType(value);
			}
		});
		borderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  borderColorButton.handleSelctionEvent(borderColorButton.getColorLabel().getShell());
				highChart.getChart().setBorderColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		

		backgroundColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  backgroundColorButton.handleSelctionEvent(backgroundColorButton.getColorLabel().getShell());
				highChart.getChart().setBackgroundColor(colorSelected);
				editor.setIsDirty(true);
			}
		});		

		animationCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = animationCheck.getSelection();
				highChart.getChart().setAnimation(selection);
				editor.setIsDirty(true);
			}
		});

		shadowCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = shadowCheck.getSelection();
				highChart.getChart().setShadow(selection);
				editor.setIsDirty(true);
			}
		});

		margintext.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = margintext.getText();
				highChart.getChart().setMargin(value);
			}
		});

		widthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = widthSpinner.getSelection();
				String s = Integer.valueOf(val).toString();
				highChart.getChart().setWidth(s+"%");
			}
		});

		heightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = heightSpinner.getSelection();
				String s = Integer.valueOf(val).toString();
				highChart.getChart().setHeight(s+"%");
			}
		});

		marginTopSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = marginTopSpinner.getSelection();
				highChart.getChart().setMarginTop(val);
			}
		});

		marginRightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = marginRightSpinner.getSelection();
				highChart.getChart().setMarginRight(val);
			}
		});

		marginBottomSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = marginBottomSpinner.getSelection();
				highChart.getChart().setMarginBottom(val);
			}
		});

		marginLeftSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = marginLeftSpinner.getSelection();
				highChart.getChart().setMarginLeft(val);
			}
		});

		spacingTopSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = spacingTopSpinner.getSelection();
				highChart.getChart().setSpacingTop(val);
			}
		});

		spacingRightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = spacingRightSpinner.getSelection();
				highChart.getChart().setSpacingRight(val);
			}
		});

		spacingBottomSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = spacingBottomSpinner.getSelection();
				highChart.getChart().setSpacingBottom(val);
			}
		});

		spacingLeftSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = spacingLeftSpinner.getSelection();
				highChart.getChart().setSpacingLeft(val);
			}
		});


		showAxesCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = showAxesCheck.getSelection();
				highChart.getChart().setShowAxes(selection);
				editor.setIsDirty(true);
			}
		});

		plotBackgroundColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  plotBackgroundColorButton.handleSelctionEvent(plotBackgroundColorButton.getColorLabel().getShell());
				highChart.getChart().setPlotBackgroundColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	

		plotBackgroundImageText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = plotBackgroundImageText.getText();
				highChart.getChart().setPlotBackgroundImage(value);
			}
		});

		plotBackgroundColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  plotBackgroundColorButton.handleSelctionEvent(plotBackgroundColorButton.getColorLabel().getShell());
				highChart.getChart().setPlotBackgroundColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	
		
		plotBorderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  plotBorderColorButton.handleSelctionEvent(plotBorderColorButton.getColorLabel().getShell());
				highChart.getChart().setPlotBorderColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	

		plotBorderWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = plotBorderWidthSpinner.getSelection();
				highChart.getChart().setPlotBorderWidth(val);
			}
		});


		plotShadowCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = plotShadowCheck.getSelection();
				highChart.getChart().setPlotShadow(selection);
				editor.setIsDirty(true);
			}
		});

		ignoreHiddenSeriesCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = ignoreHiddenSeriesCheck.getSelection();
				highChart.getChart().setIgnoreHiddenSeries(selection);
				editor.setIsDirty(true);
			}
		});

		classNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = classNameText.getText();
				highChart.getChart().setClassName(value);
			}
		});

		invertedCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = invertedCheck.getSelection();
				highChart.getChart().setInverted(selection);
				editor.setIsDirty(true);
			}
		});

		zoomTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = zoomTypeCombo.getItem(zoomTypeCombo.getSelectionIndex());
				highChart.getChart().setZoomType(value);
			}
		});

		borderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  borderColorButton.handleSelctionEvent(borderColorButton.getColorLabel().getShell());
				highChart.getChart().setBorderColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	

		borderRadiusSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = borderRadiusSpinner.getSelection();
				highChart.getChart().setBorderRadius(val);
			}
		});

		borderWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = borderWidthSpinner.getSelection();
				highChart.getChart().setBorderWidth(val);
			}
		});


		alignTicksButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = alignTicksButton.getSelection();
				highChart.getChart().setAlignTicks(selection);
				editor.setIsDirty(true);
			}
		});

		renderToText.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = renderToText.getSelection();
				highChart.getChart().setRenderTo(selection);
				editor.setIsDirty(true);
			}
		});
		logger.debug("OUT");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);

		// ++++++++++++++  Chart common settings section ++++++++++++++ 
		logger.debug("IN");

		final Chart chart = highChart.getChart();

		section.setText("Chart section");
		section.setDescription("Fill attribute regarding the chart");

		//************ Margin Group ************

		generalGroup = createNColGroup("General Settings: ", 8);
		generalGroup.setBackground(SWTUtils.getColor(generalGroup.getDisplay(), SWTUtils.LIGHT_GREEN));

		toolkit.createLabel(generalGroup, "Style: ");	
		styleText = toolkit.createText(generalGroup, chart.getStyle(), SWT.BORDER);
		styleText.setLayoutData(SWTUtils.getGridDataSpan(3, GridData.FILL_HORIZONTAL));

		typeCombo = SWTUtils.drawCombo(generalGroup, new String[]{"", "line", "spline", "area", "areaspline", "column", "bar", "pie", "scatter"}, chart.getDefaultSeriesType(), "Default Series Type: ");
		typeCombo.setEnabled(false);
		animationCheck = SWTUtils.drawCheck(generalGroup, chart.isAnimation(), "Animation: ");
		shadowCheck = SWTUtils.drawCheck(generalGroup, chart.isShadow(), "Shadow: ");

		backgroundColorButton = SWTUtils.drawColorButton(toolkit, generalGroup, chart.getBackgroundColor(), "Background color");


		widthSpinner = SWTUtils.drawSpinner(generalGroup, chart.getMarginTop(),"Width: ");
		heightSpinner = SWTUtils.drawSpinner(generalGroup, chart.getMarginTop(),"Height: ");


		plotGroup = createNColGroup("Plot Settings: ", 6);
		plotGroup.setBackground(SWTUtils.getColor(plotGroup.getDisplay(), SWTUtils.VERY_LIGHT_GREEN));


		plotBorderWidthSpinner = SWTUtils.drawSpinner(plotGroup, chart.getPlotBorderWidth(), "Plot border Width:");
		plotShadowCheck= SWTUtils.drawCheck(plotGroup, chart.isPlotShadow(), "Plot Shadow: ");
		showAxesCheck = SWTUtils.drawCheck(plotGroup, chart.isShowAxes(), "Show Axes: ");
		plotBackgroundImageText = SWTUtils.drawText(toolkit, plotGroup, chart.getPlotBackgroundImage(), "Plot background Image: ");
		plotBackgroundColorButton = SWTUtils.drawColorButton(toolkit, plotGroup, chart.getPlotBackgroundColor(), "Plot bck color");
		plotBorderColorButton = SWTUtils.drawColorButton(toolkit, plotGroup, chart.getPlotBorderColor(), "Plot Border Color");


		marginGroup = createNColGroup("Margins and Spacings: ", 8);
		marginGroup.setBackground(SWTUtils.getColor(marginGroup.getDisplay(), SWTUtils.VERY_LIGHT_GREEN));

		
		margintext = SWTUtils.drawText(toolkit, marginGroup, chart.getMargin(), "Margin: ");
		margintext.setLayoutData(SWTUtils.getGridDataSpan(7, -1));

		marginTopSpinner = SWTUtils.drawSpinner(marginGroup, chart.getMarginTop(), "Top margin: ");
		marginBottomSpinner = SWTUtils.drawSpinner(marginGroup, chart.getMarginBottom(), "Bottom margin: ");
		marginLeftSpinner = SWTUtils.drawSpinner(marginGroup, chart.getMarginLeft(), "Left margin: ");
		marginRightSpinner = SWTUtils.drawSpinner(marginGroup, chart.getMarginRight(), "Right margin: ");

		marginBottomSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setMarginBottom(marginBottomSpinner.getSelection());
			}
		});
		marginTopSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setMarginTop(marginTopSpinner.getSelection());
			}
		});
		marginLeftSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setMarginBottom(marginLeftSpinner.getSelection());
			}
		});
		marginRightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setMarginBottom(marginRightSpinner.getSelection());
			}
		});

		spacingTopSpinner = SWTUtils.drawSpinner(marginGroup, chart.getSpacingTop(), "Top spacing: ");
		spacingBottomSpinner = SWTUtils.drawSpinner(marginGroup, chart.getSpacingBottom(), "Bottom spacing: ");
		spacingLeftSpinner = SWTUtils.drawSpinner(marginGroup, chart.getSpacingLeft(), "Left spacing: ");
		spacingRightSpinner = SWTUtils.drawSpinner(marginGroup, chart.getSpacingRight(), "Right spacing: ");
		spacingBottomSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setSpacingBottom(spacingBottomSpinner.getSelection());
			}
		});
		spacingTopSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setSpacingTop(spacingTopSpinner.getSelection());
			}
		});
		spacingLeftSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setSpacingBottom(spacingLeftSpinner.getSelection());
			}
		});
		spacingRightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				chart.setSpacingBottom(spacingRightSpinner.getSelection());
			}
		});


		borderGroup = createNColGroup("Borders and others: ", 6);
		borderGroup.setBackground(SWTUtils.getColor(borderGroup.getDisplay(), SWTUtils.LIGHT_GREEN));
		borderColorButton = SWTUtils.drawColorButton(toolkit, borderGroup, chart.getBorderColor(), "Border Color: ");
		plotBorderColorButton = SWTUtils.drawColorButton(toolkit, borderGroup, chart.getPlotBorderColor(), "Plot Border color");
		zoomTypeCombo = SWTUtils.drawCombo(borderGroup, new String[]{"", "x", "y", "xy"}, chart.getZoomType(), "Zoom type: ");
		classNameText = SWTUtils.drawText(toolkit, borderGroup, chart.getClassName(), "Class Name: ");
		borderRadiusSpinner = SWTUtils.drawSpinner(borderGroup, chart.getBorderRadius(), "Border radius: ");
		borderWidthSpinner = SWTUtils.drawSpinner(borderGroup, chart.getBorderWidth(), "Border width: ");
		invertedCheck = SWTUtils.drawCheck(borderGroup, chart.isInverted(), "Inverted: ");
		renderToText =  SWTUtils.drawCheck(borderGroup, chart.isRenderTo(), "Render to: ");
		alignTicksButton = SWTUtils.drawCheck(borderGroup, chart.isAlignTicks(), "Align Ticks");
		ignoreHiddenSeriesCheck = SWTUtils.drawCheck(borderGroup, chart.isIgnoreHiddenSeries(), "Ignore Hidden Series: ");


		section.setClient(composite);
		addListeners();
		logger.debug("OUT");

	}


	public HighChartEditor getEditor() {
		return editor;
	}


	public void setEditor(HighChartEditor editor) {
		this.editor = editor;
	}






}
