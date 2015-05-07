/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.Legend;
import it.eng.spagobi.studio.highchart.utils.ColorButton;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class LegendSection extends AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(LegendSection.class);

	Group positionGroup; 
	Group styleGroup; 

	// Position group
	Combo verticalAlignCombo;
	Combo alignCombo;
	Spinner xSpinner;
	Spinner ySpinner;
	Button floatingCheck;
	Combo layoutCombo;
	Text labelFormatterText;
	Spinner lineHeightSpinner;
	Spinner marginSpinner;
	Button reversedCheck;
	Spinner symbolPaddingSpinner;
	Spinner symbolWidthSpinner;
	Spinner widthSpinner; 

	//Style
	ColorButton backgroundColorButton;
	ColorButton borderColorButton;
	Spinner borderRadiusSpinner;
	Spinner borderWidthSpinner;
	Text itemHiddenStyleText;
	Text itemHoverStyleText;
	Text itemStyleText;
	Text styleText;
	Spinner itemWidthSpinner;
	Button shadowCheck;


	public LegendSection(HighChart highChart) {
		super(highChart);
	}


	public void addListeners(){
		logger.debug("IN");


		verticalAlignCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = verticalAlignCombo.getItem(verticalAlignCombo.getSelectionIndex());
				highChart.getLegend().setVerticalAlign(value);
			}
		});


		alignCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = alignCombo.getItem(alignCombo.getSelectionIndex());
				highChart.getLegend().setAlign(value);
			}
		});

		layoutCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = layoutCombo.getItem(layoutCombo.getSelectionIndex());
				highChart.getLegend().setLayout(value);
			}
		});


		xSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = xSpinner.getSelection();
				highChart.getLegend().setX(val);
			}
		});
		ySpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = ySpinner.getSelection();
				highChart.getLegend().setY(val);
			}
		});
		lineHeightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = lineHeightSpinner.getSelection();
				highChart.getLegend().setLineHeight(val);
			}
		});
		marginSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = marginSpinner.getSelection();
				highChart.getLegend().setMargin(val);
			}
		});

		symbolPaddingSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = symbolPaddingSpinner.getSelection();
				highChart.getLegend().setSymbolPadding(val);
			}
		});

		symbolWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = symbolWidthSpinner.getSelection();
				highChart.getLegend().setSymbolWidth(val);
			}
		});

		widthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = widthSpinner.getSelection();
				highChart.getLegend().setWidth(val);
			}
		});

		borderRadiusSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = borderRadiusSpinner.getSelection();
				highChart.getLegend().setBorderRadius(val);
			}
		});

		borderWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = borderWidthSpinner.getSelection();
				highChart.getLegend().setBorderWidth(val);
			}
		});

		itemWidthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = itemWidthSpinner.getSelection();
				highChart.getLegend().setItemWidth(val);
			}
		});

		floatingCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = floatingCheck.getSelection();
				highChart.getLegend().setFloating(selection);
				editor.setIsDirty(true);
			}
		});

		reversedCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = reversedCheck.getSelection();
				highChart.getLegend().setReversed(selection);
				editor.setIsDirty(true);
			}
		});

		shadowCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = shadowCheck.getSelection();
				highChart.getLegend().setShadow(selection);
				editor.setIsDirty(true);
			}
		});


		labelFormatterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = labelFormatterText.getText();
				highChart.getLegend().setLabelFormatter(value);
			}
		});

		itemHiddenStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = itemHiddenStyleText.getText();
				highChart.getLegend().setItemHiddenStyle(value);
			}
		});

		itemHoverStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = itemHoverStyleText.getText();
				highChart.getLegend().setItemHoverStyle(value);
			}
		});

		itemStyleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = itemStyleText.getText();
				highChart.getLegend().setItemStyle(value);
			}
		});

		styleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = styleText.getText();
				highChart.getLegend().setStyle(value);
			}
		});

		backgroundColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  backgroundColorButton.handleSelctionEvent(backgroundColorButton.getColorLabel().getShell());
				highChart.getLegend().setBackgroundColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	

		borderColorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  borderColorButton.handleSelctionEvent(borderColorButton.getColorLabel().getShell());
				highChart.getLegend().setBorderColor(colorSelected);
				editor.setIsDirty(true);
			}
		});	


		logger.debug("OUT");
	}

	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);
		logger.debug("IN");
		final Legend legend= highChart.getLegend();

		section.setText("Legend section");
		section.setDescription("Fill attribute regarding the Legend");

		positionGroup = createNColGroup("Position and Dimensions: ", 16);
		positionGroup.setBackground(SWTUtils.getColor(positionGroup.getDisplay(), SWTUtils.LIGHT_RED));

		marginSpinner = SWTUtils.drawSpinner(positionGroup, legend.getMargin(), "Margin: ");
		lineHeightSpinner = SWTUtils.drawSpinner(positionGroup, legend.getLineHeight(), "Line Height: ");
		symbolPaddingSpinner =SWTUtils.drawSpinner(positionGroup, legend.getSymbolPadding(), "Symbol Padding: ");
		symbolWidthSpinner =SWTUtils.drawSpinner(positionGroup, legend.getSymbolWidth(), "Symbol Width: ");
		widthSpinner = SWTUtils.drawSpinner(positionGroup, legend.getWidth(), "Width: ");
		itemWidthSpinner = SWTUtils.drawSpinner(positionGroup, legend.getItemWidth(), "Item Width: ");
		xSpinner = SWTUtils.drawSpinner(positionGroup, legend.getX(), "X: ");		
		ySpinner = SWTUtils.drawSpinner(positionGroup, legend.getY(), "Y: ");		

		verticalAlignCombo= SWTUtils.drawCombo(positionGroup, new String[]{"", "top", "middle","bottom"}, legend.getVerticalAlign(), "Vertical Align: ");
		alignCombo = SWTUtils.drawCombo(positionGroup, new String[]{"", "left", "center","right"}, legend.getAlign(), "Align: ");
		layoutCombo = SWTUtils.drawCombo(positionGroup, new String[]{"horizontal","vertical"}, legend.getLayout(), "Layout: ");
		reversedCheck = SWTUtils.drawCheck(positionGroup, legend.isReversed(), "Reversed: ");
		floatingCheck = SWTUtils.drawCheck(positionGroup, legend.isFloating(), "Floating");	
		labelFormatterText = SWTUtils.drawText(toolkit, positionGroup, legend.getLabelFormatter(), "Label Formatter: ");




		styleGroup = createNColGroup("Style: ", 9);
		styleGroup.setBackground(SWTUtils.getColor(styleGroup.getDisplay(), SWTUtils.LIGHT_RED));

		itemStyleText = SWTUtils.drawText(toolkit, styleGroup, legend.getItemStyle(), "Item style: ");
		itemHiddenStyleText = SWTUtils.drawText(toolkit, styleGroup, legend.getItemHiddenStyle(), "Item hidden style: ");
		itemHoverStyleText = SWTUtils.drawText(toolkit, styleGroup, legend.getItemHoverStyle(), "Item hover style: ");
		styleText = SWTUtils.drawText(toolkit, styleGroup, legend.getStyle(), "Style: ");
		toolkit.createLabel(styleGroup, "");

		backgroundColorButton = SWTUtils.drawColorButton(toolkit, styleGroup, legend.getBackgroundColor(), "background Color: ");
		borderColorButton = SWTUtils.drawColorButton(toolkit, styleGroup, legend.getBorderColor(), "Border Color: ");
		borderRadiusSpinner = SWTUtils.drawSpinner(styleGroup, legend.getBorderRadius(), "Border Radius: ");
		borderWidthSpinner = SWTUtils.drawSpinner(styleGroup, legend.getBorderWidth(), "Border Width: ");

		shadowCheck = SWTUtils.drawCheck(styleGroup, legend.isShadow(), "Shadow: ");

		section.setClient(composite);

		addListeners();

		logger.debug("OUT");
	}

}
