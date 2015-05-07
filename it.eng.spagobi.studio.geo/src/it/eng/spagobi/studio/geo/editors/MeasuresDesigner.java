/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors;

import it.eng.spagobi.studio.geo.editors.model.bo.KpiBO;
import it.eng.spagobi.studio.geo.editors.model.geo.Colours;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.KPI;
import it.eng.spagobi.studio.geo.editors.model.geo.Measures;
import it.eng.spagobi.studio.geo.editors.model.geo.Param;
import it.eng.spagobi.studio.geo.editors.model.geo.Tresholds;
import it.eng.spagobi.studio.geo.util.DesignerUtils;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class MeasuresDesigner {
	private GEOEditor editor=null;
	private Composite mainComposite;
	private GEODocument geoDocument;

	public MeasuresDesigner(Composite _composite, GEOEditor _editor) {
		super();
		mainComposite= _composite;
		editor = _editor;
	}

	public MeasuresDesigner(Composite _composite, GEOEditor _editor, GEODocument _geoDocument) {
		super();
		mainComposite= _composite;
		editor = _editor;
		geoDocument = _geoDocument;
	}

	private KPI fillMeasure(Shell dialog, String columnName){
		KPI kpi = new KPI();
		kpi.setColumnId(columnName);
		Tresholds tresholds = new Tresholds();
		kpi.setTresholds(tresholds);

		Text desc = (Text)dialog.getData("Description");
		kpi.setDescription(desc.getText());

		Combo aggFunct = (Combo)dialog.getData("AggFunction");
		kpi.setAggFunct(aggFunct.getText());

		Label col = (Label)dialog.getData("Color");
		Color bgCol =col.getBackground();
		String hexCol = DesignerUtils.convertRGBToHexadecimal(bgCol.getRGB());
		kpi.setColor(hexCol);

		Combo treshType = (Combo)dialog.getData("TresholdsType");
		tresholds.setType(treshType.getText());
		Text treshLb = (Text)dialog.getData("TresholdsLb");
		tresholds.setLbValue(treshLb.getText());
		Text treshUb = (Text)dialog.getData("TresholdsUb");
		tresholds.setUbValue(treshUb.getText());

		Param param = new Param();
		tresholds.setParam(param);

		Combo treshParamName = (Combo)dialog.getData("TresholdsParamName");
		param.setName(treshParamName.getText());
		Text treshParamVal = (Text)dialog.getData("TresholdsParamValue");
		param.setValue(treshParamVal.getText());

		Colours colours = new Colours();
		kpi.setColours(colours);


		Combo colType = (Combo)dialog.getData("ColType");
		colours.setType(colType.getText());
		Label colOutbound = (Label)dialog.getData("ColOutbound");
		Color bgCol2 =colOutbound.getBackground();
		String hexCol2 = DesignerUtils.convertRGBToHexadecimal(bgCol2.getRGB());
		colours.setOutboundColour(hexCol2);

		Label colNullVal = (Label)dialog.getData("ColNullVal");
		Color bgCol3 =colNullVal.getBackground();
		String hexCol3 = DesignerUtils.convertRGBToHexadecimal(bgCol3.getRGB());
		colours.setNullValuesColor(hexCol3);

		Param colParam = new Param();
		colours.setParam(colParam);

		Combo colParamName = (Combo)dialog.getData("ColParamName");
		colParam.setName(colParamName.getText());
		Label colParamVal = (Label)dialog.getData("ColParamVal");
		Color bgCol4 =colParamVal.getBackground();
		String hexCol4 = DesignerUtils.convertRGBToHexadecimal(bgCol4.getRGB());
		colParam.setValue(hexCol4);

		return kpi;
	} 

	public void createMeasuresShell(final Composite sectionClient, final String columnName){

		KPI kpi = KpiBO.getMeasureByColumnId(geoDocument, columnName);


		final Shell dialog = new Shell (mainComposite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("New Measure for "+columnName);
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label labelIsDef = new Label (dialog, SWT.RIGHT);
		labelIsDef.setText ("Is default:");
		FormData data = new FormData ();
		data.width = 140;
		labelIsDef.setLayoutData (data);


		//checkbox default
		final Button isDefault = new Button(dialog, SWT.CHECK| SWT.RIGHT);
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelIsDef, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelIsDef, 0, SWT.CENTER);
		isDefault.setLayoutData (data);
		dialog.setData("defaultKpi", columnName);
		isDefault.setSelection(true);

		final boolean[] isDefaultRes = new boolean[1];
		if(kpi != null){
			Measures measures =geoDocument.getMapRenderer().getMeasures();
			if(measures != null){
				String columnNameDef = measures.getDefaultKpi();
				if(columnNameDef != null && columnNameDef.equals(columnName)){
					isDefault.setSelection(true);
					isDefaultRes[0]=true;
				}else{
					isDefault.setSelection(false);
					isDefaultRes[0]=false;
				}

			}
		}
		isDefault.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				isDefaultRes[0] = event.widget == isDefault;
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				isDefaultRes[0] = event.widget == isDefault;
			}
		});
		Label label = new Label (dialog, SWT.RIGHT);
		label.setText ("Description:");
		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(isDefault, 5);
		label.setLayoutData (data);

		Button cancel = new Button (dialog, SWT.PUSH);
		cancel.setText ("Cancel");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (100, 0);
		data.bottom = new FormAttachment (100, 0);
		cancel.setLayoutData (data);
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				//				System.out.println("User cancelled dialog");
				dialog.close ();
			}
		});

		final Text text = createTextWithLayout(dialog, label, data, "Description");
		if(kpi != null){
			text.setText(kpi.getDescription());
		}
		//aggregate function
		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(text, 5);

		Label labelAgg = new Label (dialog, SWT.RIGHT);
		labelAgg.setText ("Aggregate function:");		
		labelAgg.setLayoutData (data);

		final Combo textAgg = createComboWithLayout(dialog, labelAgg, data, "AggFunction");
		textAgg.add("sum");
		textAgg.add("avg");
		if(kpi != null){
			for(int i =0; i<textAgg.getItemCount(); i++){
				if(kpi.getAggFunct() != null && kpi.getAggFunct().equalsIgnoreCase(textAgg.getItem(i))){
					textAgg.select(i);
				}
			}
		}
		//color
		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textAgg, 5);
		Label labelCol = new Label (dialog, SWT.RIGHT);
		labelCol.setText ("Colour:");		
		labelCol.setLayoutData (data);
		Composite textColor = null;
		if(kpi != null){
			textColor = createColorPickWithLayout(dialog, labelCol, data, "Color", kpi.getColor());
		}else{
			textColor = createColorPickWithLayout(dialog, labelCol, data, "Color");
		}

		//tresholds
		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textColor, 5);
		Label labelTreshType = new Label (dialog, SWT.RIGHT);
		labelTreshType.setText ("Tresholds type:");		
		labelTreshType.setLayoutData (data);

		final Combo textTreshType = createComboWithLayout(dialog, labelTreshType, data, "TresholdsType");
		textTreshType.add("static");
		textTreshType.add("uniform");
		textTreshType.add("perc");
		textTreshType.add("quantile");
		if(kpi != null){
			if(kpi != null && kpi.getTresholds()!= null && kpi.getTresholds().getParam() != null){
				for(int i =0; i<textTreshType.getItemCount(); i++){
					if(kpi.getTresholds().getType().equalsIgnoreCase(textTreshType.getItem(i))){
						textTreshType.select(i);
					}
				}
			}
		}

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textTreshType, 5);
		Label labelTreshLb = new Label (dialog, SWT.RIGHT);
		labelTreshLb.setText ("Tresholds lb value:");		
		labelTreshLb.setLayoutData (data);
		final Text textTreshLb = createTextWithLayout(dialog.getShell(), labelTreshLb, data, "TresholdsLb");
		if(kpi != null && kpi.getTresholds()!= null){
			textTreshLb.setText(kpi.getTresholds().getLbValue());
		}

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textTreshLb, 5);
		Label labelTreshUb = new Label (dialog, SWT.RIGHT);
		labelTreshUb.setText ("Tresholds ub value:");		
		labelTreshUb.setLayoutData (data);
		final Text textTreshUb = createTextWithLayout(dialog.getShell(), labelTreshUb, data, "TresholdsUb");
		if(kpi != null && kpi.getTresholds()!= null){
			textTreshUb.setText(kpi.getTresholds().getUbValue());
		}

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textTreshUb, 5);
		Label labelTreshParamName = new Label (dialog, SWT.RIGHT);
		labelTreshParamName.setText ("Tresholds param name:");		
		labelTreshParamName.setLayoutData (data);

		final Combo textTreshParamName = createComboWithLayout(dialog, labelTreshParamName, data, "TresholdsParamName");
		textTreshParamName.setEnabled(false);
		textTreshParamName.add("range");
		textTreshParamName.add("GROUPS_NUMBER");
		if(kpi != null){
			if(kpi != null && kpi.getTresholds()!= null && kpi.getTresholds().getParam() != null){
				for(int i =0; i<textTreshParamName.getItemCount(); i++){
					if(kpi.getTresholds().getParam().getName().equalsIgnoreCase(textTreshParamName.getItem(i))){
						textTreshParamName.select(i);
					}
				}
			}
		}

		//listener to change threshold param name
		textTreshType.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				if(textTreshType.getText() != null && textTreshType.getText() != ""){
					if(textTreshType.getText().equals("static") || textTreshType.getText().equals("perc")){
						textTreshParamName.setText("range");
					}else if(textTreshType.getText().equals("uniform") || textTreshType.getText().equals("quantile")){
						textTreshParamName.setText("GROUPS_NUMBER");
					}
				}
			}
		});
		////////////end 

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textTreshParamName, 5);
		Label labelTreshParamVal = new Label (dialog, SWT.RIGHT);
		labelTreshParamVal.setText ("Tresholds param value:");		
		labelTreshParamVal.setLayoutData (data);
		final Text textTreshParamVal = createTextWithLayout(dialog.getShell(), labelTreshParamVal, data, "TresholdsParamValue");
		if(kpi != null && kpi.getTresholds()!= null && kpi.getTresholds().getParam() != null){
			textTreshParamVal.setText(kpi.getTresholds().getParam().getValue());
		}

		//colours
		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textTreshParamVal, 5);
		Label labelColoursType = new Label (dialog, SWT.RIGHT);
		labelColoursType.setText ("Colours type:");		
		labelColoursType.setLayoutData (data);
		final Combo textColoursType = createComboWithLayout(dialog, labelColoursType, data, "ColType");
		textColoursType.add("grad");
		textColoursType.add("static");
		if(kpi != null && kpi.getColours() != null){
			for(int i =0; i<textColoursType.getItemCount(); i++){
				if(kpi.getColours().getType().equalsIgnoreCase(textColoursType.getItem(i))){
					textColoursType.select(i);
				}
			}
		}

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textColoursType, 5);
		Label labelColoursOutbound = new Label (dialog, SWT.RIGHT);
		labelColoursOutbound.setText ("Colours outbound colour:");		
		labelColoursOutbound.setLayoutData (data);
		Composite textColoursOutbound = null;
		if(kpi != null && kpi.getColours() != null){
			textColoursOutbound = createColorPickWithLayout(dialog, labelColoursOutbound, data, "ColOutbound", kpi.getColours().getOutboundColour());
		}else{
			textColoursOutbound = createColorPickWithLayout(dialog, labelColoursOutbound, data, "ColOutbound");
		}

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textColoursOutbound, 5);
		Label labelColoursNullVal = new Label (dialog, SWT.RIGHT);
		labelColoursNullVal.setText ("Colours null values:");		
		labelColoursNullVal.setLayoutData (data);
		Composite textColoursNullVal = null;
		if(kpi != null && kpi.getColours() != null){
			textColoursNullVal = createColorPickWithLayout(dialog, labelColoursNullVal, data, "ColNullVal", kpi.getColours().getNullValuesColor());
		}else{
			textColoursNullVal = createColorPickWithLayout(dialog, labelColoursNullVal, data, "ColNullVal");
		}

		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textColoursNullVal, 5);
		Label labelColParamName = new Label (dialog, SWT.RIGHT);
		labelColParamName.setText ("Colours param name:");		
		labelColParamName.setLayoutData (data);

		final Combo textColParamName = createComboWithLayout(dialog, labelColParamName, data, "ColParamName");
		textColParamName.setEnabled(false);
		textColParamName.add("BASE_COLOR");
		textColParamName.add("range");
		if(kpi != null && kpi.getColours() != null){
			for(int i =0; i<textColParamName.getItemCount(); i++){
				if(kpi != null && kpi.getColours()!= null && kpi.getColours().getParam() != null ){
					if(kpi.getColours().getParam().getName().equalsIgnoreCase(textColParamName.getItem(i))){
						textColParamName.select(i);
					}
				}
			}
		}
		//listener to change color param name 
		textColoursType.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				if(textColoursType.getText() != null && textColoursType.getText() != ""){
					if(textColoursType.getText().equals("grad")){
						textColParamName.setText("BASE_COLOR");
					}else if(textColoursType.getText().equals("static")){
						textColParamName.setText("range");
					}
				}
			}
		});
		////////////end 
		data = new FormData ();
		data.width = 140;
		data.top = new FormAttachment(textColParamName, 5);
		Label labelColParamVal = new Label (dialog, SWT.RIGHT);
		labelColParamVal.setText ("Colours param value:");		
		labelColParamVal.setLayoutData (data);
		Composite textColParamVal = null;
		if(kpi != null && kpi.getColours() != null && kpi.getColours().getParam() != null){
			textColParamVal = createColorPickWithLayout(dialog, labelColParamVal, data, "ColParamVal", kpi.getColours().getParam().getValue());
		}else{
			textColParamVal = createColorPickWithLayout(dialog, labelColParamVal, data, "ColParamVal");
		}

		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);

		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				//add or modify measure
				KPI kpiToAdd = fillMeasure(dialog, columnName);

				KpiBO.setNewMeasure(geoDocument, kpiToAdd);
				if(isDefaultRes[0]){
					Measures measures = geoDocument.getMapRenderer().getMeasures();
					if(measures != null){
						measures.setDefaultKpi(columnName);
					}
				}
				editor.setIsDirty(true);
				dialog.close ();
			}
		});

		Button delete = new Button (dialog, SWT.PUSH);
		delete.setText ("Delete");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (ok, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		delete.setLayoutData (data);
		delete.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				//delete measure
				boolean confirm = MessageDialog.openConfirm(dialog.getShell(), "Confirm delete?", "Confirm delete of the current measure?");
				if(confirm){
					KPI kpiToDelete = fillMeasure(dialog, columnName);
					KpiBO.deleteMeasure(geoDocument, kpiToDelete);
					Table datasetTable = editor.getDatasetTable();
					TableItem[] selectedRow = datasetTable.getSelection();
					Combo combo =(Combo)selectedRow[0].getData();
					combo.deselectAll();
				}
				editor.setIsDirty(true);
				dialog.close ();
			}
		});

		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();

	}


	private Text createTextWithLayout(Shell dialog, Label itsLabel, FormData data, String dataKey){
		Text text = new Text (dialog, SWT.BORDER);
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (itsLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (itsLabel, 0, SWT.CENTER);
		text.setLayoutData (data);
		dialog.setData(dataKey, text);
		return text;
	}
	private Combo createComboWithLayout(Shell dialog, Label itsLabel, FormData data, String dataKey){
		Combo combo = new Combo (dialog, SWT.BORDER | SWT.READ_ONLY);
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (itsLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (itsLabel, 0, SWT.CENTER);
		combo.setLayoutData (data);
		dialog.setData(dataKey, combo);
		return combo;
	}

	private Composite createColorPickWithLayout(Shell dialog, Label itsLabel, FormData data, String dataKey){
		final Label colorLabel = new Label(dialog, SWT.BORDER);
		data = new FormData ();
		data.width = 50;
		data.left = new FormAttachment (itsLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (50, 0);
		data.top = new FormAttachment (itsLabel, 0, SWT.CENTER);
		colorLabel.setLayoutData(data);
		dialog.setData(dataKey, colorLabel);

		Composite colorSection = DesignerUtils.createColorPicker(dialog, "#FF0000", colorLabel);
		data = new FormData ();
		data.width = 50;
		data.left = new FormAttachment (colorLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (70, 50);
		data.top = new FormAttachment (colorLabel, 0, SWT.CENTER);
		colorSection.setLayoutData (data);
		return colorSection;
	}
	private Composite createColorPickWithLayout(Shell dialog, Label itsLabel, FormData data, String dataKey, String selectedColor){
		final Label colorLabel = new Label(dialog, SWT.BORDER);
		data = new FormData ();
		data.width = 50;
		data.left = new FormAttachment (itsLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (50, 0);
		data.top = new FormAttachment (itsLabel, 0, SWT.CENTER);
		colorLabel.setLayoutData(data);
		dialog.setData(dataKey, colorLabel);

		Composite colorSection = DesignerUtils.createColorPicker(dialog, selectedColor, colorLabel);
		data = new FormData ();
		data.width = 50;
		data.left = new FormAttachment (colorLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (70, 50);
		data.top = new FormAttachment (colorLabel, 0, SWT.CENTER);
		colorSection.setLayoutData (data);
		return colorSection;
	}

	public GEOEditor getEditor() {
		return editor;
	}

	public void setEditor(GEOEditor editor) {
		this.editor = editor;
	}

	public Composite getMainComposite() {
		return mainComposite;
	}

	public void setMainComposite(Composite mainComposite) {
		this.mainComposite = mainComposite;
	}

	public GEODocument getGeoDocument() {
		return geoDocument;
	}

	public void setGeoDocument(GEODocument geoDocument) {
		this.geoDocument = geoDocument;
	}

}
