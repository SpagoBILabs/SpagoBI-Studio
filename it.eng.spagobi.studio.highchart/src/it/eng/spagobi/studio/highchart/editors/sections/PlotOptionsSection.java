package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.model.bo.Area;
import it.eng.spagobi.studio.highchart.model.bo.AreaSpline;
import it.eng.spagobi.studio.highchart.model.bo.Bar;
import it.eng.spagobi.studio.highchart.model.bo.Column;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.InterfaceType;
import it.eng.spagobi.studio.highchart.model.bo.Line;
import it.eng.spagobi.studio.highchart.model.bo.Pie;
import it.eng.spagobi.studio.highchart.model.bo.PlotOptions;
import it.eng.spagobi.studio.highchart.model.bo.Series;
import it.eng.spagobi.studio.highchart.utils.ColorButton;
import it.eng.spagobi.studio.highchart.utils.HighChartUtils;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlotOptionsSection extends AbstractSection{

	private static Logger logger = LoggerFactory.getLogger(PlotOptionsSection.class);
	Group commonGroup;
	Group specificGroup;

	// COmmons
	Button allowPointSelectCheck;
	Button animationCheck;
	ColorButton colorButton;
	Combo cursorCombo; // combo
	Combo dashStyleCombo; // combo
	Button dataLabelsCheck;
	Button enableMouseTrackingCheck;
	Spinner lineWidthSpinner;
	Text markerText;
	Spinner pointStartSpinner;
	Spinner pointIntervalSpinner;
	Button selectedCheck;
	Button shadowCheck;
	Button showCheckboxCheck;
	Button showInLegendCheck;
	Combo stackingCombo; // combo
	Button stickyTrackingCheck;
	Button visibleCheck;
	Spinner zIndexSpinner;

	//Speciic for Area
	private ColorButton fillColorButton;
	private Spinner fillOpacitySpinner;
	private ColorButton lineColorButton;
	private Spinner thresholdSpinner;

	//Specific for Bar
	ColorButton borderColorButton;
	Spinner borderRadiusSpinner;
	Spinner borderWidthSpinner;
	Button colorByPointButton;
	Spinner groupPaddingSpinner;
	Spinner minPointLengthSpinner;
	Spinner pointPaddingSpinner;
	Spinner pointWidthSpinner;

	Button stepCheck;
	Text innerSizeText;
	Text sizeText;
	Spinner slicedOffsetSpinner;


	public PlotOptionsSection(HighChart highChart) {
		super(highChart);
	}

	public void addListeners(){
		logger.debug("IN");
		final InterfaceType toModify = getObjectToModify(highChart);

		allowPointSelectCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = allowPointSelectCheck.getSelection();
				toModify.setAllowPointSelect(selection);
				editor.setIsDirty(true);
			}
		});

		animationCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = animationCheck.getSelection();
				toModify.setAnimation(selection);
				editor.setIsDirty(true);
			}
		});

		enableMouseTrackingCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = enableMouseTrackingCheck.getSelection();
				toModify.setEnableMouseTracking(selection);
				editor.setIsDirty(true);
			}
		});

		selectedCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = selectedCheck.getSelection();
				toModify.setSelected(selection);
				editor.setIsDirty(true);
			}
		});

		shadowCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = shadowCheck.getSelection();
				toModify.setShadow(selection);
				editor.setIsDirty(true);
			}
		});

		showCheckboxCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = showCheckboxCheck.getSelection();
				toModify.setShowCheckbox(selection);
				editor.setIsDirty(true);
			}
		});

		showInLegendCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = showInLegendCheck.getSelection();
				toModify.setShowInLegend(selection);
				editor.setIsDirty(true);
			}
		});

		stickyTrackingCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = stickyTrackingCheck.getSelection();
				toModify.setStickyTracking(selection);
				editor.setIsDirty(true);
			}
		});

		visibleCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = visibleCheck.getSelection();
				toModify.setVisible(selection);
				editor.setIsDirty(true);
			}
		});


		colorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  colorButton.handleSelctionEvent(colorButton.getColorLabel().getShell());
				toModify.setColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	

		
		dataLabelsCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = dataLabelsCheck.getSelection();
				toModify.getDataLabels().setEnabled(selection);
				editor.setIsDirty(true);
			}
		});
		
		markerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = markerText.getText();
				toModify.setMarker(value);
			}
		});

		zIndexSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = zIndexSpinner.getSelection();
				toModify.setzIndex(val);
			}
		});

		lineWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = lineWidthSpinner.getSelection();
				toModify.setLineWidth(val);
			}
		});

		pointStartSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = pointStartSpinner.getSelection();
				toModify.setPointStart(val);
			}
		});

		pointIntervalSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = pointIntervalSpinner.getSelection();
				toModify.setPointInterval(val);
			}
		});

		stackingCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = stackingCombo.getItem(stackingCombo.getSelectionIndex());
				toModify.setStacking(value);
			}
		}); // combo

		cursorCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = cursorCombo.getItem(cursorCombo.getSelectionIndex());
				toModify.setCursor(value);
			}
		});

		dashStyleCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = dashStyleCombo.getItem(dashStyleCombo.getSelectionIndex());
				toModify.setDashStyle(value);
			}
		});


		// AREA case
		if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_AREA)){
			fillColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					String colorSelected =  fillColorButton.handleSelctionEvent(fillColorButton.getColorLabel().getShell());
					((Area)toModify).setFillColor(colorSelected);
					editor.setIsDirty(true);
				}
			});	

			lineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					String colorSelected =  lineColorButton.handleSelctionEvent(lineColorButton.getColorLabel().getShell());
					((Area)toModify).setLineColor(colorSelected);
					editor.setIsDirty(true);
				}
			});	 
			fillOpacitySpinner.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					editor.setIsDirty(true);
					int val = fillOpacitySpinner.getSelection();
					//FLOAT TODO
					float valf = 0;
					((Area)toModify).setFillOpacity(valf);
				}
			});
			thresholdSpinner.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					editor.setIsDirty(true);
					int val = thresholdSpinner.getSelection();
					((Area)toModify).setThreshold(val);
				}
			});
		}
		else 		// AREASPLINE case
			if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_AREASPLINE)){
				fillColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						String colorSelected =  fillColorButton.handleSelctionEvent(fillColorButton.getColorLabel().getShell());
						((AreaSpline)toModify).setFillColor(colorSelected);
						editor.setIsDirty(true);
					}
				});	

				lineColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						String colorSelected =  lineColorButton.handleSelctionEvent(lineColorButton.getColorLabel().getShell());
						((AreaSpline)toModify).setLineColor(colorSelected);
						editor.setIsDirty(true);
					}
				});	 
				fillOpacitySpinner.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						editor.setIsDirty(true);
						int val = fillOpacitySpinner.getSelection();
						//FLOAT TODO
						float valf = 0;
						((AreaSpline)toModify).setFillOpacity(valf);
					}
				});
				thresholdSpinner.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						editor.setIsDirty(true);
						int val = thresholdSpinner.getSelection();
						((AreaSpline)toModify).setThreshold(val);
					}
				});
			}
		// END AREA case
			else
				if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_BAR)){
					borderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							String colorSelected =  borderColorButton.handleSelctionEvent(borderColorButton.getColorLabel().getShell());
							((Bar)toModify).setBorderColor(colorSelected);
							editor.setIsDirty(true);
						}
					});

					borderRadiusSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = borderRadiusSpinner.getSelection();
							float valf = 0; //TODO
							((Bar)toModify).setBorderRadius(valf);
						}
					});

					borderWidthSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = borderWidthSpinner.getSelection();
							((Bar)toModify).setBorderWidth(val);
						}
					});

					groupPaddingSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = groupPaddingSpinner.getSelection();
							float valf = 0; //TODO
							((Bar)toModify).setGroupPadding(valf);
						}
					});


					minPointLengthSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = minPointLengthSpinner.getSelection();
							((Bar)toModify).setMinPointLength(val);
						}
					});

					pointPaddingSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = pointPaddingSpinner.getSelection();
							float valf = 0; //TODO
							((Bar)toModify).setPointPadding(valf);
						}
					});

					pointWidthSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = pointWidthSpinner.getSelection();
							((Bar)toModify).setPointWidth(val);
						}
					});
					colorByPointButton.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event e) {
							boolean selection = colorByPointButton.getSelection();
							((Bar)toModify).setColorByPoint(selection);
							editor.setIsDirty(true);
						}
					});


				}
				else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_COLUMN)){
					borderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							String colorSelected =  borderColorButton.handleSelctionEvent(borderColorButton.getColorLabel().getShell());
							((Column)toModify).setBorderColor(colorSelected);
							editor.setIsDirty(true);
						}
					});

					borderRadiusSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = borderRadiusSpinner.getSelection();
							float valf = 0; //TODO
							((Column)toModify).setBorderRadius(valf);
						}
					});

					borderWidthSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = borderWidthSpinner.getSelection();
							((Column)toModify).setBorderWidth(val);
						}
					});

					groupPaddingSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = groupPaddingSpinner.getSelection();
							float valf = 0; //TODO
							((Column)toModify).setGroupPadding(valf);
						}
					});


					minPointLengthSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = minPointLengthSpinner.getSelection();
							((Column)toModify).setMinPointLength(val);
						}
					});

				}
				else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_LINE)){
					stepCheck.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event e) {
							boolean selection = stepCheck.getSelection();
							((Line)toModify).setStep(selection);
							editor.setIsDirty(true);
						}
					});


					

				}
				else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_LINE)){
					borderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							String colorSelected =  borderColorButton.handleSelctionEvent(borderColorButton.getColorLabel().getShell());
							((Column)toModify).setBorderColor(colorSelected);
							editor.setIsDirty(true);
						}
					});
					borderWidthSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = borderWidthSpinner.getSelection();
							((Column)toModify).setBorderWidth(val);
						}
					});

					innerSizeText.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							String value = innerSizeText.getText();
							((Pie)toModify).setInnerSize(value);
						}
					});
					
					sizeText.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							String value = innerSizeText.getText();
							((Pie)toModify).setSize(value);
						}
					});
					
					slicedOffsetSpinner.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							editor.setIsDirty(true);
							int val = slicedOffsetSpinner.getSelection();
							((Pie)toModify).setSlicedOffset(val);
						}
					});
					
				}
					

		logger.debug("OUT");

	}



	@Override
	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);

		// ++++++++++++++  Chart common settings section ++++++++++++++ 
		logger.debug("IN");

		final PlotOptions plotOptions = highChart.getPlotOptions();

		final InterfaceType toModify = getObjectToModify(highChart);

		//final Series series = plotOptions.getSeries();
		section.setText("Plot Options section");
		section.setDescription("Fill attribute the plot");

		//************ Margin Group ************

		//// ------------------------ XAxis --------------------

		commonGroup = createNColGroup("Common settings: ", 12);
		commonGroup.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.GRAB_HORIZONTAL));
		commonGroup.setBackground(SWTUtils.getColor(commonGroup.getDisplay(), SWTUtils.LIGHT_BLUE));

		colorButton  = SWTUtils.drawColorButton(toolkit, commonGroup, toModify.getColor(), "Color: "); 
		lineWidthSpinner = SWTUtils.drawSpinner(commonGroup, toModify.getLineWidth(),"Line width: ");
		pointIntervalSpinner = SWTUtils.drawSpinner(commonGroup, toModify.getPointInterval(),"Point interval: ");
		zIndexSpinner = SWTUtils.drawSpinner(commonGroup, toModify.getzIndex(),"Z-index: ");
		cursorCombo = SWTUtils.drawCombo(commonGroup, new String[]{"", "pointer"}, toModify.getCursor(), "Cursor: ");
		// TODO Applies only to toModify type having a graph, like line, spline, area and scatter in case it has a lineWidth
		dashStyleCombo= SWTUtils.drawCombo(commonGroup, new String[]{"", "ShortDash","ShortDot","ShortDashDot","ShortDashDotDot","Dot","Dash","LongDash","DashDot","LongDashDot","LongDashDotDo"}, toModify.getDashStyle(), "Dash style: ");
		stackingCombo= SWTUtils.drawCombo(commonGroup, new String[]{"", "normal","percent"}, toModify.getStacking(), "Stacking: ");
		dataLabelsCheck = SWTUtils.drawCheck(commonGroup, toModify.getDataLabels().getEnabled(), "Data labels: ");
		markerText = SWTUtils.drawText(toolkit, commonGroup, toModify.getMarker(), "Marker: ");
		pointStartSpinner = SWTUtils.drawSpinner(commonGroup, toModify.getPointStart(),"Point start: ");
		allowPointSelectCheck = SWTUtils.drawCheck(commonGroup, toModify.isAllowPointSelect(), "Allow point select: ");
		animationCheck = SWTUtils.drawCheck(commonGroup, toModify.isAnimation(), "Animation: ");
		selectedCheck = SWTUtils.drawCheck(commonGroup, toModify.isSelected(), "Selected: ");
		shadowCheck = SWTUtils.drawCheck(commonGroup, toModify.isShadow(), "Shadow: ");
		showCheckboxCheck = SWTUtils.drawCheck(commonGroup, toModify.isShowCheckbox(), "Show CheckBox: ");
		showInLegendCheck = SWTUtils.drawCheck(commonGroup, toModify.isShowInLegend(), "Show In Legend: ");
		stickyTrackingCheck = SWTUtils.drawCheck(commonGroup, toModify.isStickyTracking(), "Sticky tracking: ");
		visibleCheck = SWTUtils.drawCheck(commonGroup, toModify.isVisible(), "Visible: ");
		enableMouseTrackingCheck = SWTUtils.drawCheck(commonGroup, toModify.isEnableMouseTracking(), "enable mouse tracking: ");


		specificGroup = createNColGroup("Specific for "+highChart.getChart().getDefaultSeriesType()+" type : ", 12);
		specificGroup.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.GRAB_HORIZONTAL));
		specificGroup.setBackground(SWTUtils.getColor(specificGroup.getDisplay(), SWTUtils.LIGHT_BLUE));

		if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_AREA)){
			fillColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((Area)toModify).getFillColor(), "Fill Color: "); 
			lineColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((Area)toModify).getLineColor(), "Line color: "); 
			fillOpacitySpinner = SWTUtils.drawSpinner(specificGroup, ((Area)toModify).getFillOpacity(),"Fill Opacity: ");
			thresholdSpinner = SWTUtils.drawSpinner(specificGroup, ((Area)toModify).getThreshold(),"Threshold: ");
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_AREASPLINE)){
			fillColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((AreaSpline)toModify).getFillColor(), "Fill Color: "); 
			lineColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((AreaSpline)toModify).getLineColor(), "Line color: "); 
			fillOpacitySpinner = SWTUtils.drawSpinner(specificGroup, ((AreaSpline)toModify).getFillOpacity(),"Fill Opacity: ");
			thresholdSpinner = SWTUtils.drawSpinner(specificGroup, ((AreaSpline)toModify).getThreshold(),"Threshold: ");
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_BAR)){
			borderColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((Bar)toModify).getBorderColor(), "Border Color: "); 
			borderRadiusSpinner = SWTUtils.drawSpinner(specificGroup, ((Bar)toModify).getBorderRadius(),"Border radius: ");
			borderWidthSpinner = SWTUtils.drawSpinner(specificGroup, ((Bar)toModify).getBorderWidth(),"Border width: ");
			groupPaddingSpinner = SWTUtils.drawSpinner(specificGroup, ((Bar)toModify).getGroupPadding(),"Group padding: ");
			minPointLengthSpinner = SWTUtils.drawSpinner(specificGroup, ((Bar)toModify).getMinPointLength(),"Min point lenght: ");
			pointPaddingSpinner = SWTUtils.drawSpinner(specificGroup, ((Bar)toModify).getPointPadding(),"Point Padding: ");
			pointWidthSpinner = SWTUtils.drawSpinner(specificGroup, ((Bar)toModify).getPointWidth(),"Point Width: ");
			colorByPointButton = SWTUtils.drawCheck(specificGroup, ((Bar)toModify).getColorByPoint(), "Color by point: ");
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_COLUMN)){
			borderColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((Column)toModify).getBorderColor(), "Border Color: "); 
			borderRadiusSpinner = SWTUtils.drawSpinner(specificGroup, ((Column)toModify).getBorderRadius(),"Border radius: ");
			borderWidthSpinner = SWTUtils.drawSpinner(specificGroup, ((Column)toModify).getBorderWidth(),"Border width: ");
			groupPaddingSpinner = SWTUtils.drawSpinner(specificGroup, ((Column)toModify).getGroupPadding(),"Group padding: ");
			minPointLengthSpinner = SWTUtils.drawSpinner(specificGroup, ((Column)toModify).getMinPointLength(),"Min point lenght: ");
			pointPaddingSpinner = SWTUtils.drawSpinner(specificGroup, ((Column)toModify).getPointPadding(),"Point Padding: ");
			pointWidthSpinner = SWTUtils.drawSpinner(specificGroup, ((Column)toModify).getPointWidth(),"Point Width: ");
			colorByPointButton = SWTUtils.drawCheck(specificGroup, ((Column)toModify).getColorByPoint(), "Color by point: ");
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_LINE)){
			stepCheck = SWTUtils.drawCheck(specificGroup, ((Line)toModify).isStep(), "Step: ");
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_PIE)){
			borderColorButton = SWTUtils.drawColorButton(toolkit, specificGroup, ((Pie)toModify).getBorderColor(), "Border Color: "); 
			borderWidthSpinner = SWTUtils.drawSpinner(specificGroup, ((Pie)toModify).getBorderWidth(),"Border width: ");
			innerSizeText = SWTUtils.drawText(toolkit, specificGroup, ((Pie)toModify).getInnerSize(), "Inner size: ");
			sizeText = SWTUtils.drawText(toolkit, specificGroup, ((Pie)toModify).getSize(), "Size: ");
			slicedOffsetSpinner = SWTUtils.drawSpinner(specificGroup, ((Pie)toModify).getSlicedOffset(),"Sliced offset: ");
	
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_SCATTER)){
	
		}
		else if(highChart.getChart().getDefaultSeriesType().equals(HighChartUtils.TYPE_SPLINE)){
			
		}

		section.setClient(composite);
		addListeners();
		logger.debug("OUT");
	}


	public InterfaceType getObjectToModify(HighChart highChart){
		InterfaceType interfaceType = null;
		String type = highChart.getChart().getDefaultSeriesType();
		if(type.equals(HighChartUtils.TYPE_AREA)){
			interfaceType = highChart.getPlotOptions().getArea();
		}
		else if(type.equals(HighChartUtils.TYPE_BAR)){
			interfaceType = highChart.getPlotOptions().getBar();
		}
		else if(type.equals(HighChartUtils.TYPE_AREASPLINE)){
			interfaceType = highChart.getPlotOptions().getAreaSpline();
		}
		else if(type.equals(HighChartUtils.TYPE_COLUMN)){
			interfaceType = highChart.getPlotOptions().getColumn();
		}
		else if(type.equals(HighChartUtils.TYPE_LINE)){
			interfaceType = highChart.getPlotOptions().getLine();
		}
		else if(type.equals(HighChartUtils.TYPE_PIE)){
			interfaceType = highChart.getPlotOptions().getPie();
		}
		else if(type.equals(HighChartUtils.TYPE_SCATTER)){
			interfaceType = highChart.getPlotOptions().getScatter();
		}
		else if(type.equals(HighChartUtils.TYPE_SPLINE)){
			interfaceType = highChart.getPlotOptions().getSpline();
		}

		return interfaceType;		
	}

}
