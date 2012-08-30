/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.properties.title;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.StyleTitle;
import it.eng.spagobi.studio.extchart.utils.ColorButton;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;


/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class TitleProperties extends PopupPropertiesDialog {
	FormToolkit toolkit;
	//Shell comp;
	ExtChartEditor editor;
	ExtChart chart;
	private Text colorTxt;
	private Text fontSizeTxt;
	private Combo fontWeightCombo;
	String fontWeight;
	StyleTitle styleTitle;
	ColorButton colorButton;
	String colorSelected;


	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TitleProperties.class);

	public TitleProperties(ExtChartEditor editor, Shell  comp) {
		super(editor, comp);
		this.editor = editor;
		toolkit = new FormToolkit(comp.getDisplay());
		chart = editor.getExtChart();
		colorSelected = "#000000";

	}

	public void drawProperties(){
		logger.debug("IN");

		super.drawProperties();

		//container.setLayout(new GridLayout(2, false));
        
		Composite container = dialog;
		Monitor primary = dialog.getDisplay().getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = dialog.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		dialogMain.setLocation (x, y);
		
		dialogMain.setText("Title Style Properties");

		container.setLayout(new GridLayout(2, false));

		//check if there is a previously definied color
		if (chart.getTitle()!=null && chart.getTitle().getStyle() != null && chart.getTitle().getStyle().getColor() != null) {
			colorSelected = chart.getTitle().getStyle().getColor();
		}
		
		
		colorButton = SWTUtils.drawColorButton(toolkit, dialog, 
				chart.getTitle()!=null && chart.getTitle().getStyle() != null && chart.getTitle().getStyle().getColor() != null ? chart.getTitle().getStyle().getColor() : "#000000"
				, "Color: ");
		colorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				colorSelected =  colorButton.handleSelctionEvent(colorButton.getColorLabel().getShell());
			}
		});	
		GridData gd_colorTxt = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_colorTxt.widthHint = 200;
		
		Label lblFontWeight = new Label(container, SWT.NONE);
		lblFontWeight.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		lblFontWeight.setText("Font Weight :");
		
		fontWeightCombo = new Combo(container, SWT.READ_ONLY);
		fontWeightCombo.setItems(new String[] {"normal", "bold"});
		if (chart.getTitle().getStyle().getFontWeight() != null){
			String currentSelection = chart.getTitle().getStyle().getFontWeight();
			String[] items = fontWeightCombo.getItems();
			for (int i=0; i< items.length; i++){
				if (items[i].equals(currentSelection)){
					fontWeightCombo.select(i);			
				}
			}
		}
		fontWeightCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fontWeight = fontWeightCombo.getText();
			}
		});
		fontWeightCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblFontSize = new Label(container, SWT.NONE);
		lblFontSize.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		lblFontSize.setText("Font Size : ");
		
		fontSizeTxt = new Text(container, SWT.BORDER);
		if (chart.getTitle().getStyle().getFontSize() != null){
			fontSizeTxt.setText(chart.getTitle().getStyle().getFontSize().toString());			
		}
		else {
			fontSizeTxt.setText("11px");
		}
		fontSizeTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		
		logger.debug("OUT");
	};

	//overwrite
	public void showPopup(){
		logger.debug("IN");
		
		dialogMain.setSize(300, 200);
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}	



	public void performOk(){
		logger.debug("IN");
		editor.setIsDirty(true);

		chart.getTitle().getStyle().setColor(colorSelected);
		chart.getTitle().getStyle().setFontWeight(fontWeightCombo.getText());
		//chart.getTitle().getStyle().setFontSize(Integer.parseInt(fontSizeTxt.getText()));
		chart.getTitle().getStyle().setFontSize(fontSizeTxt.getText());

		logger.debug("OUT");		
	}



}
