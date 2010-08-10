/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.ScatterChartModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * 
 * @author gavardi
 *
 *	Thios class has method for the Scatter range parameters form creation
 */

public class ScatterRangeMarkerEditor {

	Section sectionScatter=null;
	Composite sectionClientScatter=null;

	final Label	xRangeValueLowLabel;
	final Spinner xRangeValueLowSpinner;
	final Label	xRangeValueHighLabel;
	final Spinner xRangeValueHighSpinner;

	final Label	yRangeValueLowLabel;
	final Spinner yRangeValueLowSpinner;
	final Label	yRangeValueHighLabel;
	final Spinner yRangeValueHighSpinner;

	// xMarker
	final Label xMarkerLabelLabel;
	final Text xMarkerLabelText;
	final Label xMarkerValueStartIntLabel;
	final Spinner xMarkerValueStartIntSpinner;
	final Label xMarkerValueEndIntLabel;
	final Spinner xMarkerValueEndIntSpinner;
	final Label xMarkerValueMarkerLabel;
	final Spinner xMarkerValueMarkerSpinner;
	final Label xMarkerColorIntLabel;
	final Spinner xMarkerColorIntSpinner;
	//final Label xMarkerColorLabelLabel;
	Composite xMarkerColorSection; 
	final Label xMarkerColorLabel;
	final Button xMarkerColorButton;	

	// yMarker
	final Label yMarkerLabelLabel;
	final Text yMarkerLabelText;
	final Label yMarkerValueStartIntLabel;
	final Spinner yMarkerValueStartIntSpinner;
	final Label yMarkerValueEndIntLabel;
	final Spinner yMarkerValueEndIntSpinner;
	final Label yMarkerValueMarkerLabel;
	final Spinner yMarkerValueMarkerSpinner;
	final Label yMarkerColorIntLabel;
	final Spinner yMarkerColorIntSpinner;
	//final Label xMarkerColorLabelLabel;
	Composite yMarkerColorSection; 
	final Label yMarkerColorLabel;
	final Button yMarkerColorButton;	





	public ScatterRangeMarkerEditor(final ScatterChartModel scatterModel, FormToolkit toolkit, final ScrolledForm form) {

		sectionScatter = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		sectionClientScatter=toolkit.createComposite(sectionScatter);
		sectionScatter.setText("Ranges and Markers");
		sectionScatter.setDescription("Set x-y ranges and x-y markers");

		GridLayout gridLayout=new GridLayout();
		gridLayout.numColumns=4;
		sectionClientScatter.setLayout(gridLayout);

		Label xRangeTitle = new Label(sectionClientScatter, SWT.SHADOW_ETCHED_OUT | SWT.BORDER); 
		xRangeTitle.setForeground(new Color(sectionClientScatter.getDisplay(), new RGB(0,0,255)));
		xRangeTitle.setText("X RANGE SETTINGS");
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);

		xRangeValueLowLabel = new Label(sectionClientScatter, SWT.NULL); 
		xRangeValueLowLabel.setText("     Low Value: ");
		xRangeValueLowSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		xRangeValueLowSpinner.setToolTipText("Minimum value of the interval");
		xRangeValueLowSpinner.setMaximum(1000000);
		xRangeValueLowSpinner.setMinimum(-1000000);
		xRangeValueLowSpinner.setDigits(1);
		if(scatterModel.getScatterRangeMarker().getXRangeValueLow()!=null){
			String val=scatterModel.getScatterRangeMarker().getXRangeValueLow().toString();
			val=ChartEditorUtils.removeChar(val, '.');
			xRangeValueLowSpinner.setSelection(Integer.valueOf(val));
		}

		xRangeValueLowSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				double newMin = xRangeValueLowSpinner.getSelection()/ Math.pow(10, xRangeValueLowSpinner.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				scatterModel.getScatterRangeMarker().setXRangeValueLow(newMinD);
			}
		});

		xRangeValueHighLabel = new Label(sectionClientScatter, SWT.NULL); 
		xRangeValueHighLabel.setText("     High Value: ");
		xRangeValueHighSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		xRangeValueHighSpinner.setToolTipText("Minimum value of the interval");
		xRangeValueHighSpinner.setMaximum(1000000);
		xRangeValueHighSpinner.setMinimum(-1000000);
		xRangeValueHighSpinner.setDigits(1);
		if(scatterModel.getScatterRangeMarker().getXRangeValueHigh()!=null){
			String val=scatterModel.getScatterRangeMarker().getXRangeValueHigh().toString();
			val=ChartEditorUtils.removeChar(val, '.');
			xRangeValueHighSpinner.setSelection(Integer.valueOf(val));
		}
		
		xRangeValueHighSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				double newMin = xRangeValueHighSpinner.getSelection()/ Math.pow(10, xRangeValueHighSpinner.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				scatterModel.getScatterRangeMarker().setXRangeValueHigh(newMinD);
			}
		});



		Label yRangeTitle = new Label(sectionClientScatter, SWT.SHADOW_ETCHED_OUT | SWT.BORDER); 
		yRangeTitle.setForeground(new Color(sectionClientScatter.getDisplay(), new RGB(0,0,255)));
		yRangeTitle.setText("Y RANGE SETTINGS");
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);

		yRangeValueLowLabel = new Label(sectionClientScatter, SWT.NULL); 
		yRangeValueLowLabel.setText("     Low Value: ");
		yRangeValueLowSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		yRangeValueLowSpinner.setToolTipText("Minimum value of the interval");
		yRangeValueLowSpinner.setMaximum(1000000);
		yRangeValueLowSpinner.setMinimum(-1000000);
		yRangeValueLowSpinner.setDigits(1);
		if(scatterModel.getScatterRangeMarker().getYRangeValueLow()!=null){
			String val=scatterModel.getScatterRangeMarker().getYRangeValueLow().toString();
			val=ChartEditorUtils.removeChar(val, '.');
			yRangeValueLowSpinner.setSelection(Integer.valueOf(val));
		}

		yRangeValueLowSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				double newMin = yRangeValueLowSpinner.getSelection()/ Math.pow(10, yRangeValueLowSpinner.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				scatterModel.getScatterRangeMarker().setYRangeValueLow(newMinD);
			}
		});

		yRangeValueHighLabel = new Label(sectionClientScatter, SWT.NULL); 
		yRangeValueHighLabel.setText("     High Value: ");
		yRangeValueHighSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		yRangeValueHighSpinner.setToolTipText("Minimum value of the interval");
		yRangeValueHighSpinner.setMaximum(1000000);
		yRangeValueHighSpinner.setMinimum(-1000000);
		yRangeValueHighSpinner.setDigits(1);
		if(scatterModel.getScatterRangeMarker().getYRangeValueHigh()!=null){
			String val=scatterModel.getScatterRangeMarker().getYRangeValueHigh().toString();
			val=ChartEditorUtils.removeChar(val, '.');
			yRangeValueHighSpinner.setSelection(Integer.valueOf(val));
		}

		yRangeValueHighSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				double newMin = yRangeValueHighSpinner.getSelection()/ Math.pow(10, yRangeValueHighSpinner.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				scatterModel.getScatterRangeMarker().setYRangeValueHigh(newMinD);
			}
		});

		Label xMarkerTitle = new Label(sectionClientScatter, SWT.SHADOW_ETCHED_OUT | SWT.BORDER); 
		xMarkerTitle.setForeground(new Color(sectionClientScatter.getDisplay(), new RGB(0,0,255)));
		xMarkerTitle.setText("X MARKER SETTINGS");
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);



		xMarkerValueMarkerLabel = new Label(sectionClientScatter, SWT.NULL); 
		xMarkerValueMarkerLabel.setText("     Marker Value: ");
		xMarkerValueMarkerSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		xMarkerValueMarkerSpinner.setToolTipText("Marker Value for the x Marker");
		xMarkerValueMarkerSpinner.setMaximum(1000000);
		xMarkerValueMarkerSpinner.setMinimum(-1000000);
		xMarkerValueMarkerSpinner.setDigits(1);
		if(scatterModel.getScatterRangeMarker().getXMarker().getValueMarker()!=null){
			String val=scatterModel.getScatterRangeMarker().getXMarker().getValueMarker().toString();
			val=ChartEditorUtils.removeChar(val, '.');
			xMarkerValueMarkerSpinner.setSelection(Integer.valueOf(val));
		}

		xMarkerValueMarkerSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				double newMin = xMarkerValueMarkerSpinner.getSelection() /  Math.pow(10, xMarkerValueMarkerSpinner.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				scatterModel.getScatterRangeMarker().getXMarker().setValueMarker(newMinD);
			}
		});


		xMarkerLabelLabel=new Label(sectionClientScatter, SWT.NULL);
		xMarkerLabelLabel.setText("     Label: ");

		xMarkerLabelText=new Text(sectionClientScatter, SWT.BORDER);;
		xMarkerLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		xMarkerLabelText.setToolTipText("Label for x Marker");
		if(scatterModel.getScatterRangeMarker().getXMarker().getLabel()!=null){
			String val=scatterModel.getScatterRangeMarker().getXMarker().getLabel().toString();
			xMarkerLabelText.setText(val);
		}

		xMarkerLabelText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				String newLabel = xMarkerLabelText.getText();
				scatterModel.getScatterRangeMarker().getXMarker().setLabel(newLabel);
			}
		});



		xMarkerValueStartIntLabel = new Label(sectionClientScatter, SWT.NULL); 
		xMarkerValueStartIntLabel.setText("     Start Value: ");
		xMarkerValueStartIntSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		xMarkerValueStartIntSpinner.setToolTipText("Start Value for the x Marker");
		xMarkerValueStartIntSpinner.setMaximum(1000000);
		xMarkerValueStartIntSpinner.setMinimum(-1000000);
		if(scatterModel.getScatterRangeMarker().getXMarker().getValueStartInt()!=null){
			Integer val=scatterModel.getScatterRangeMarker().getXMarker().getValueStartInt();
			xMarkerValueStartIntSpinner.setSelection(val);
		}

		xMarkerValueStartIntSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				int newMin = xMarkerValueStartIntSpinner.getSelection();
				Integer newMinI=null;
				try{
					newMinI=Integer.valueOf(newMin);
				}
				catch (Exception e) {
					newMinI=new Integer(0);
				}
				scatterModel.getScatterRangeMarker().getXMarker().setValueStartInt(newMinI);
			}
		});


		xMarkerValueEndIntLabel = new Label(sectionClientScatter, SWT.NULL); 
		xMarkerValueEndIntLabel.setText("     End Value: ");
		xMarkerValueEndIntSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		xMarkerValueEndIntSpinner.setToolTipText("End Value for the x Marker");
		xMarkerValueEndIntSpinner.setMaximum(1000000);
		xMarkerValueEndIntSpinner.setMinimum(-1000000);
		if(scatterModel.getScatterRangeMarker().getXMarker().getValueEndInt()!=null){
			Integer val=scatterModel.getScatterRangeMarker().getXMarker().getValueEndInt();
			xMarkerValueEndIntSpinner.setSelection(Integer.valueOf(val));
		}

		
		xMarkerValueEndIntSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				int newMin = xMarkerValueEndIntSpinner.getSelection();
				Integer newMinI=null;
				try{
					newMinI=Integer.valueOf(newMin);
				}
				catch (Exception e) {
					newMinI=new Integer(0);
				}
				scatterModel.getScatterRangeMarker().getXMarker().setValueEndInt(newMinI);
			}
		});





		xMarkerColorIntLabel = new Label(sectionClientScatter, SWT.NULL); 
		xMarkerColorIntLabel.setText("     Color Int: ");
		xMarkerColorIntSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		xMarkerColorIntSpinner.setToolTipText("Color int");
		xMarkerColorIntSpinner.setMaximum(1000000);
		xMarkerColorIntSpinner.setMinimum(-1000000);
		if(scatterModel.getScatterRangeMarker().getXMarker().getColorInt()!=null){
			Integer val=scatterModel.getScatterRangeMarker().getXMarker().getColorInt();
			xMarkerColorIntSpinner.setSelection(Integer.valueOf(val));
		}

		xMarkerColorIntSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				int newMin = xMarkerColorIntSpinner.getSelection();
				Integer newMinI=null;
				try{
					newMinI=Integer.valueOf(newMin);
				}
				catch (Exception e) {
					newMinI=new Integer(0);
				}
				scatterModel.getScatterRangeMarker().getXMarker().setColorInt(newMinI);
			}
		});

		xMarkerColorSection = toolkit.createComposite(sectionClientScatter);
		xMarkerColorButton= new Button(xMarkerColorSection, SWT.PUSH);
		final Color color = new org.eclipse.swt.graphics.Color(sectionClientScatter.getDisplay(), new RGB(255,255,255));
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		xMarkerColorSection.setLayout(colorGd);
		xMarkerColorLabel=new Label(sectionClientScatter, SWT.BORDER);
		xMarkerColorLabel.setText("          ");
		xMarkerColorLabel.setBackground(color);
		xMarkerColorButton.setText("Color...");
		final Shell parentShell = sectionClientScatter.getShell();
		xMarkerColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				scatterModel.getEditor().setIsDirty(true);
				centerShell.setLocation(
						(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(xMarkerColorLabel.getBackground().getRGB());
				colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					xMarkerColorLabel.setBackground(newColor);
					scatterModel.getEditor().setIsDirty(true);
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
					scatterModel.getScatterRangeMarker().getXMarker().setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));						}
				//centerShell.pack();
				centerShell.dispose();
			}
		});			






		Label yMarkerTitle = new Label(sectionClientScatter, SWT.SHADOW_ETCHED_OUT | SWT.BORDER); 
		yMarkerTitle.setForeground(new Color(sectionClientScatter.getDisplay(), new RGB(0,0,255)));
		yMarkerTitle.setText("Y MARKER SETTINGS");
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);
		ChartEditorUtils.addBlanckSpace(sectionClientScatter);



		yMarkerValueMarkerLabel = new Label(sectionClientScatter, SWT.NULL); 
		yMarkerValueMarkerLabel.setText("     Marker Value: ");
		yMarkerValueMarkerSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		yMarkerValueMarkerSpinner.setToolTipText("Marker Value for the y Marker");
		yMarkerValueMarkerSpinner.setMaximum(1000000);
		yMarkerValueMarkerSpinner.setMinimum(-1000000);
		yMarkerValueMarkerSpinner.setDigits(1);		
		if(scatterModel.getScatterRangeMarker().getYMarker().getValueMarker()!=null){
			String val=scatterModel.getScatterRangeMarker().getYMarker().getValueMarker().toString();
			val=ChartEditorUtils.removeChar(val, '.');
			yMarkerValueMarkerSpinner.setSelection(Integer.valueOf(val));
		}
		
		yMarkerValueMarkerSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				double newMin = yMarkerValueMarkerSpinner.getSelection() / Math.pow(10, yMarkerValueMarkerSpinner.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				scatterModel.getScatterRangeMarker().getYMarker().setValueMarker(newMinD);
			}
		});

		yMarkerLabelLabel=new Label(sectionClientScatter, SWT.NULL);
		yMarkerLabelLabel.setText("     Label: ");
		yMarkerLabelText=new Text(sectionClientScatter, SWT.BORDER);;
		yMarkerLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		yMarkerLabelText.setToolTipText("Label for y Marker");
		if(scatterModel.getScatterRangeMarker().getYMarker().getLabel()!=null){
			String val=scatterModel.getScatterRangeMarker().getYMarker().getLabel().toString();
			yMarkerLabelText.setText(val);
		}
		
		yMarkerLabelText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				String newLabel = yMarkerLabelText.getText();
				scatterModel.getScatterRangeMarker().getYMarker().setLabel(newLabel);
			}
		});


		yMarkerValueStartIntLabel = new Label(sectionClientScatter, SWT.NULL); 
		yMarkerValueStartIntLabel.setText("     Start Value: ");
		yMarkerValueStartIntSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		yMarkerValueStartIntSpinner.setToolTipText("Start Value for the y Marker");
		yMarkerValueStartIntSpinner.setMaximum(1000000);
		yMarkerValueStartIntSpinner.setMinimum(-1000000);
		if(scatterModel.getScatterRangeMarker().getYMarker().getValueStartInt()!=null){
			Integer val=scatterModel.getScatterRangeMarker().getYMarker().getValueStartInt();
			yMarkerValueStartIntSpinner.setSelection(val);
		}
		yMarkerValueStartIntSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				int newMin = yMarkerValueStartIntSpinner.getSelection();
				Integer newMinI=null;
				try{
					newMinI=Integer.valueOf(newMin);
				}
				catch (Exception e) {
					newMinI=new Integer(0);
				}
				scatterModel.getScatterRangeMarker().getYMarker().setValueStartInt(newMinI);
			}
		});


		yMarkerValueEndIntLabel = new Label(sectionClientScatter, SWT.NULL); 
		yMarkerValueEndIntLabel.setText("     End Value: ");
		yMarkerValueEndIntSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		yMarkerValueEndIntSpinner.setToolTipText("End Value for the y Marker");
		yMarkerValueEndIntSpinner.setMaximum(1000000);
		yMarkerValueEndIntSpinner.setMinimum(-1000000);
		if(scatterModel.getScatterRangeMarker().getYMarker().getValueEndInt()!=null){
			Integer val=scatterModel.getScatterRangeMarker().getYMarker().getValueEndInt();
			yMarkerValueEndIntSpinner.setSelection(Integer.valueOf(val));
		}
		yMarkerValueEndIntSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				int newMin = yMarkerValueEndIntSpinner.getSelection();
				Integer newMinI=null;
				try{
					newMinI=Integer.valueOf(newMin);
				}
				catch (Exception e) {
					newMinI=new Integer(0);
				}
				scatterModel.getScatterRangeMarker().getYMarker().setValueEndInt(newMinI);
			}
		});


		yMarkerColorIntLabel = new Label(sectionClientScatter, SWT.NULL); 
		yMarkerColorIntLabel.setText("     Color Int: ");
		yMarkerColorIntSpinner = new Spinner(sectionClientScatter, SWT.BORDER);
		yMarkerColorIntSpinner.setToolTipText("Color int");
		yMarkerColorIntSpinner.setMaximum(1000000);
		yMarkerColorIntSpinner.setMinimum(-1000000);
		if(scatterModel.getScatterRangeMarker().getYMarker().getColorInt()!=null){
			Integer val=scatterModel.getScatterRangeMarker().getYMarker().getColorInt();
			yMarkerColorIntSpinner.setSelection(Integer.valueOf(val));
		}

		
		yMarkerColorIntSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				int newMin = yMarkerColorIntSpinner.getSelection();
				Integer newMinI=null;
				try{
					newMinI=Integer.valueOf(newMin);
				}
				catch (Exception e) {
					newMinI=new Integer(0);
				}
				scatterModel.getScatterRangeMarker().getYMarker().setColorInt(newMinI);
			}
		});


		yMarkerColorSection = toolkit.createComposite(sectionClientScatter);
		yMarkerColorButton= new Button(yMarkerColorSection, SWT.PUSH);
		final Color colorY = new org.eclipse.swt.graphics.Color(sectionClientScatter.getDisplay(), new RGB(255,255,255));
		GridLayout colorGdY = new GridLayout();
		colorGdY.numColumns = 2;
		colorGdY.marginHeight = 0;
		colorGdY.marginBottom = 0;
		yMarkerColorSection.setLayout(colorGdY);
		yMarkerColorLabel=new Label(sectionClientScatter, SWT.BORDER);
		yMarkerColorLabel.setText("          ");
		yMarkerColorLabel.setBackground(colorY);
		yMarkerColorButton.setText("Color...");
		final Shell parentShellY = sectionClientScatter.getShell();
		yMarkerColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				scatterModel.getEditor().setIsDirty(true);
				final Shell centerShell = new Shell(parentShellY, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShellY.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
						(parentShellY.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(yMarkerColorLabel.getBackground().getRGB());
				colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShellY.getDisplay(), rgb);
					yMarkerColorLabel.setBackground(newColor);
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
					scatterModel.getScatterRangeMarker().getYMarker().setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));						}
				//centerShell.pack();
				centerShell.dispose();
			}
		});			








		sectionScatter.setClient(sectionClientScatter);

	}




	public void setVisible(boolean visible){
		sectionScatter.setVisible(visible);

	}

	public boolean isVisible(){
		if(sectionScatter.isVisible())return true;
		else return false;
	}



	public void eraseComposite(){
		xRangeValueHighSpinner.setSelection(00);
		xRangeValueLowSpinner.setSelection(00);
		yRangeValueHighSpinner.setSelection(00);
		yRangeValueLowSpinner.setSelection(00);

		xMarkerColorIntSpinner.setSelection(0);
		xMarkerLabelText.setText("");
		xMarkerValueEndIntSpinner.setSelection(0);
		xMarkerValueStartIntSpinner.setSelection(0);
		xMarkerValueMarkerSpinner.setSelection(00);
		xMarkerColorLabel.setBackground(null);

		yMarkerColorIntSpinner.setSelection(0);
		yMarkerLabelText.setText("");
		yMarkerValueEndIntSpinner.setSelection(0);
		yMarkerValueStartIntSpinner.setSelection(0);
		yMarkerValueMarkerSpinner.setSelection(00);
		yMarkerColorLabel.setBackground(null);

	}

	public void refillFieldsScatterRangeMarker(final ChartModel model, final ChartEditor editor, FormToolkit toolkit, final ScrolledForm form){
		//	if(model.getSeriesPersonalizationHashMap()!=null){
		//		for (Iterator iterator = model.getSeriesPersonalizationHashMap().keySet().iterator(); iterator.hasNext();) {
		//			String serName = (String) iterator.next();
		//			parsList.add(serName);
		//		}
		//		parsList.redraw();
		//	}
	}



}
