/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

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
import it.eng.spagobi.studio.extchart.model.bo.StyleSubTitle;
import it.eng.spagobi.studio.extchart.model.bo.StyleTitle;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;


/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class SubTitleProperties extends PopupPropertiesDialog {
	FormToolkit toolkit;
	//Shell comp;
	ExtChartEditor editor;
	ExtChart chart;
	private Text colorTxt;
	private Text fontSizeTxt;
	private Combo fontWeightCombo;
	String fontWeight;
	StyleSubTitle styleSubTitle;


	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SubTitleProperties.class);

	public SubTitleProperties(ExtChartEditor editor, Shell  comp) {
		super(editor, comp);
		this.editor = editor;
		toolkit = new FormToolkit(comp.getDisplay());
		chart = editor.getExtChart();

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
		dialog.setLocation (x, y);
		
		dialog.setText("Subtitle Style Properties");

		container.setLayout(new GridLayout(2, false));
		Label lblColorhex = new Label(container, SWT.NONE);
		lblColorhex.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblColorhex.setText("Color (HEX) :");
		
		colorTxt = new Text(container, SWT.BORDER);
		if (chart.getSubTitle().getStyle().getColor() != null){
			colorTxt.setText(chart.getSubTitle().getStyle().getColor());			
		}
		else {
			colorTxt.setText("#000000");
		}
		GridData gd_colorTxt = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_colorTxt.widthHint = 200;
		colorTxt.setLayoutData(gd_colorTxt);
		
		Label lblFontWeight = new Label(container, SWT.NONE);
		lblFontWeight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFontWeight.setText("Font Weight :");
		
		fontWeightCombo = new Combo(container, SWT.READ_ONLY);
		fontWeightCombo.setItems(new String[] {"normal", "bold"});
		if (chart.getSubTitle().getStyle().getFontWeight() != null){
			String currentSelection = chart.getSubTitle().getStyle().getFontWeight();
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
		lblFontSize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFontSize.setText("Font Size : ");
		
		fontSizeTxt = new Text(container, SWT.BORDER);
		if (chart.getSubTitle().getStyle().getFontSize() != null){
			fontSizeTxt.setText(chart.getSubTitle().getStyle().getFontSize().toString());			
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
		
		dialog.setSize(300, 200);
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!dialog.getDisplay().readAndDispatch()) {
		    	dialog.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}	



	public void performOk(){
		logger.debug("IN");
		editor.setIsDirty(true);

		chart.getSubTitle().getStyle().setColor(colorTxt.getText());
		chart.getSubTitle().getStyle().setFontWeight(fontWeightCombo.getText());
		chart.getSubTitle().getStyle().setFontSize(fontSizeTxt.getText());

		logger.debug("OUT");		
	}



}
