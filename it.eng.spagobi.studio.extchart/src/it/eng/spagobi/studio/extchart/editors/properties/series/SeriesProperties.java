package it.eng.spagobi.studio.extchart.editors.properties.series;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.series.utils.LabelProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.utils.MarkerConfigProperties;
import it.eng.spagobi.studio.extchart.editors.properties.series.utils.TipsProperties;
import it.eng.spagobi.studio.extchart.model.bo.Label;
import it.eng.spagobi.studio.extchart.model.bo.MarkerConfig;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.model.bo.Tips;
import it.eng.spagobi.studio.extchart.utils.PopupPropertiesDialog;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class SeriesProperties extends PopupPropertiesDialog{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SeriesProperties.class);
	FormToolkit toolkit;
	//Shell comp;
	ExtChartEditor editor;
	Spinner opacitySpinner;

	Tips[] tipsHolder=  new Tips[1];
	Label[] labelHolder= new Label[1];
	MarkerConfig[] markerConfigHolder=  new MarkerConfig[1];


	Button useLabelCheck;
	Button useTipsCheck;
	Button usemarkerConfigCheck;

	// SWT objects:
	//	Text donutText;
	//	Button smoothButton;


	//	Combo axisCombo; 
	//	Button showInLegendButton;
	Combo fieldCombo;
	Combo xFieldCombo; 
	Combo yFieldCombo;
	Table fieldTable;

	boolean drawField = false;
	boolean drawXField = false;
	boolean drawYField = false;
	boolean drawYFieldList = false;
	boolean drawMarkerConfig = false;

	/**
	 * serie to be modified
	 */
	Series serie;

	public SeriesProperties(ExtChartEditor editor, Shell  comp) {
		super(editor, comp);
		//this.comp = comp;
		this.editor = editor;
		toolkit = new FormToolkit(comp.getDisplay());

	}


	public void drawProperties(){
		logger.debug("IN");
		//		GridLayout gridlayout = new GridLayout(2, true);
		//		comp.setLayout(gridlayout);

		// -----------------------------------------------

		//		toolkit.createLabel(comp, "Type: ");
		//		toolkit.createLabel(comp, serie.getType() != null ? serie.getType() : "");

		// -----------------------------------------------
		//		logger.debug("Donut");
		//		toolkit.createLabel(comp, "Donut (number or 'false'): ");
		//		donutText = toolkit.createText(comp, "default value", SWT.NULL);
		//		if(serie.getDonut() != null){
		//			donutText.setText(serie.getDonut());
		//		}

		// -----------------------------------------------

		//		logger.debug("Smooth");
		//		smoothButton = toolkit.createButton(comp, "Smooth: : ", SWT.CHECK);
		//		if(serie.getHighlight() != null && serie.getSmooth().booleanValue() == true){
		//			smoothButton.setSelection(true);
		//		}





		// -----------------------------------------------

		//		logger.debug("Show in legend");
		//		showInLegendButton = toolkit.createButton(comp, "Show In Legend", SWT.CHECK);
		//		if(serie.getShowInLegened() != null && serie.getShowInLegened().booleanValue() == true){
		//			showInLegendButton.setSelection(true);
		//		}

		// -----------------------------------------------

		//		String[] axis = new String[]{"left", "top", "right", "bottom"};
		//		axisCombo = SWTUtils.drawCombo(dialog, axis, serie.getAxis(), "Axis: ");


		// -----------------------------------------------

		// get All column from dataset metadata table
		String[] metadatas = editor.getMainChartPage().getLeftPage().getDatasetMetadataTableContent();
		if(metadatas == null) metadatas = new String[0];

		if(drawField)
			fieldCombo = SWTUtils.drawCombo(dialog, metadatas, serie.getField(), "Field: ");

		// ----------------------- X FIELD ------------------------
		if(drawXField)		
			xFieldCombo = SWTUtils.drawCombo(dialog, metadatas, serie.getxField(), "xField: ");


		// ---------------------------- Y FIELD -------------------
		if(drawYField)
			yFieldCombo = SWTUtils.drawCombo(dialog, metadatas, serie.getyField(), "yField: ");


		// --------------------  Y FIELD LIST---------------------------
		if(drawYFieldList){
			toolkit.createLabel(dialog, "yFieldList: \n(overwrites \nwith multiple \nselection other \nfields settings).", SWT.NULL);
			GridData gd=new GridData(GridData.FILL_BOTH);
			gd.horizontalSpan=2	;
			fieldTable = new Table (dialog, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
			fieldTable.setLinesVisible (true);
			fieldTable.setHeaderVisible (true);
			fieldTable.setLayoutData(gd);
			GridData dataOrder = new GridData(SWT.FILL, SWT.FILL, true, true);
			dataOrder.heightHint = 100;
			dataOrder.widthHint=50;
			fieldTable.setLayoutData(dataOrder);
			String[] titlesOrder = {"   Field   "};
			for (int i=0; i<titlesOrder.length; i++) {
				TableColumn column = new TableColumn (fieldTable, SWT.NONE);
				column.setText (titlesOrder [i]);
			}
			logger.debug("YField_list is "+serie.getyFieldList());
			// select present situation, use a vector to use contains function
			Vector<String> selectedFieldsVector = new Vector<String>();
			// use a vector to collect selected fields
			if(serie.getyFieldList() != null ){
				String[] selectedFields = serie.getyFieldList().split(",");
				List<String> list = Arrays.asList(selectedFields);
				selectedFieldsVector = new Vector(list);
			}

			//fill table with Metadata
			for (int i = 0; i < metadatas.length; i++) {
				String meta = metadatas[i];
				TableItem item = new TableItem (fieldTable, SWT.NONE);
				item.setText(meta);
				if(selectedFieldsVector.contains(meta)){
					int index = fieldTable.indexOf(item);
					logger.debug("select "+meta+" item at index "+index);
					fieldTable.select(index);
				}
			}


			for (int i=0; i<titlesOrder.length; i++) {
				fieldTable.getColumn (i).pack ();
			}	
			// for yField_list get a table
		}
		// TODO: unblock and let user change fields
		fieldTable.setEnabled(false);


		// Check if define label

		useLabelCheck = SWTUtils.drawCheck(dialog, serie.getLabel() != null, "Use Label?");
		final Button labelButton = SWTUtils.drawButton(dialog, "Define Label");
		final SeriesProperties toPass = this;
		final Series serieIns = serie;
		labelButton.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				logger.debug("Open label editor");
				Label labelToPass = labelHolder[0] != null ? labelHolder[0] : serieIns.getLabel();
				LabelProperties labelProperties = new LabelProperties(editor, labelToPass, dialog, toPass);
				labelProperties.drawProperties();
				labelProperties.drawButtons();
				labelProperties.showPopup();

			}
		}
		);

		if(serie.getLabel() != null && serie.getLabel().getField() != null) {
			labelButton.setEnabled(true);
			useLabelCheck.setSelection(true);
		}
		else {
			useLabelCheck.setSelection(false);
			labelButton.setEnabled(false);
		}

		useLabelCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = useLabelCheck.getSelection();
				if(selection == true){
					labelButton.setEnabled(true);
				}
				else{
					// deleet from serie the label
					serie.setLabel(null);
					labelButton.setEnabled(false);

				}
			}
		});


		//Check if define Tips
		tipsHolder[0] = serie.getTips();
		useTipsCheck = SWTUtils.drawCheck(dialog, serie.getTips() != null, "Use Tips?");
		final Button tipsButton = SWTUtils.drawButton(dialog, "Define Tips");
		tipsButton.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				logger.debug("Open Tips editor");
				Tips tipsToPass = tipsHolder[0] != null ? tipsHolder[0] : serieIns.getTips();
				TipsProperties tipsProperties = new TipsProperties(editor, tipsToPass, dialog, toPass);
				tipsProperties.setTitle("Define tips");
				tipsProperties.setLabelForText("Text:\n {CATEGORY} for category \n {SERIE} for serie");
				tipsProperties.drawProperties();
				tipsProperties.drawButtons();
				tipsProperties.showPopup();

			}
		}
		);

		if(serie.getTips() != null && serie.getTips().getText() != null) {
			useTipsCheck.setSelection(true);
			tipsButton.setEnabled(true);
		}
		else {
			useTipsCheck.setSelection(false);
			tipsButton.setEnabled(false);
		}

		useTipsCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean selection = useTipsCheck.getSelection();
				if(selection == true){
					tipsButton.setEnabled(true);
				}
				else{
					// deleet from serie the Tips
					serie.setTips(null);
					tipsButton.setEnabled(false);

				}
			}
		});

		if(drawMarkerConfig){
			logger.debug("draw marker config designer");
			markerConfigHolder[0] = serie.getMarkerConfig();
			usemarkerConfigCheck = SWTUtils.drawCheck(dialog, serie.getMarkerConfig() != null, "Use markerConfig?");
			final Button markerConfigButton = SWTUtils.drawButton(dialog, "Define MarkerConfig");

			markerConfigButton.addListener(SWT.Selection, 
					new Listener() {
				public void handleEvent(Event event) {
					logger.debug("Open markerConfig editor");
					MarkerConfig markerConfigToPass = markerConfigHolder[0] != null ? markerConfigHolder[0] : serieIns.getMarkerConfig();
					MarkerConfigProperties markerConfigProperties = new MarkerConfigProperties(editor, markerConfigToPass, dialog, toPass);
					markerConfigProperties.setTitle("Define Marker Config");
					markerConfigProperties.drawProperties();
					markerConfigProperties.drawButtons();
					markerConfigProperties.showPopup();

				}
			}
			);

			if(serie.getMarkerConfig() != null && serie.getMarkerConfig().getType()!= null) {
				markerConfigButton.setEnabled(true);
				usemarkerConfigCheck.setSelection(true);
			}
			else {
				markerConfigButton.setEnabled(false);
				usemarkerConfigCheck.setSelection(false);
			}

			usemarkerConfigCheck.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					boolean selection = usemarkerConfigCheck.getSelection();
					if(selection == true){
						markerConfigButton.setEnabled(true);
					}
					else{
						// deleet from serie the markerConfig
						serie.setMarkerConfig(null);
						markerConfigButton.setEnabled(false);

					}
				}
			});
		}

		opacitySpinner = SWTUtils.drawSpinner(dialog, serie.getStyle().getOpacity(), "Opacity: ");

		logger.debug("OUT");
	}



	public void performOk(){
		logger.debug("IN");
		// save
		editor.setIsDirty(true);

		if(serie == null){
			logger.debug("create new Serie");
			serie = new Series();
		}

		//		String valueAxis = axisCombo.getItem(axisCombo.getSelectionIndex());
		//		serie.setAxis(valueAxis);
		//		logger.debug("axis " +valueAxis);

		if(drawField){
			String valueField = fieldCombo.getItem(fieldCombo.getSelectionIndex());
			serie.setField(valueField);
			logger.debug("field " +valueField);
		}
		if(drawXField){
			String valueX = xFieldCombo.getItem(xFieldCombo.getSelectionIndex());
			serie.setxField(valueX);
			logger.debug("Xfield " +valueX);
		}

		if(drawYField){
			String valueY = yFieldCombo.getItem(yFieldCombo.getSelectionIndex());
			serie.setyField(valueY);
			logger.debug("Yfield " +valueY);
		}

		if(drawYFieldList){
			TableItem[] items = fieldTable.getSelection();
			String selections ="";
			for (int i = 0; i < items.length; i++) {
				TableItem item = items[i];
				selections += item.getText(0);
				if(i <items.length-1){
					selections += ",";
				}
			}
			serie.setyFieldList(selections);
			logger.debug("Yfield List " +selections);
		}

		if(useLabelCheck.getSelection() == true && labelHolder[0] != null){
			logger.debug("save label");		
			serie.setLabel(labelHolder[0]);
		}
		else{
			logger.debug("delete label");		
			serie.setLabel(null);
		}

		if(usemarkerConfigCheck.getSelection() == true && markerConfigHolder[0] != null){
			logger.debug("save marker Config");		
			serie.setMarkerConfig(markerConfigHolder[0]);
		}
		else{
			logger.debug("delete marker Config");		
			serie.setMarkerConfig(null);
		}

		if(useTipsCheck.getSelection() == true && tipsHolder[0] != null){
			logger.debug("save Tips");		
			serie.setTips(tipsHolder[0]);
		}
		else{
			logger.debug("delete Tips");		
			serie.setTips(null);
		}


		double opacityValue  = opacitySpinner.getSelection()/ Math.pow(10, opacitySpinner.getDigits());
		// check it is a number 

		serie.getStyle().setOpacity((float)opacityValue);
		logger.debug("opacity " + opacityValue);

		logger.debug("OUT");
	}


	public boolean isDrawField() {
		return drawField;
	}


	public void setDrawField(boolean drawField) {
		this.drawField = drawField;
	}


	public boolean isDrawXField() {
		return drawXField;
	}


	public void setDrawXField(boolean drawXField) {
		this.drawXField = drawXField;
	}


	public boolean isDrawYField() {
		return drawYField;
	}


	public void setDrawYField(boolean drawYField) {
		this.drawYField = drawYField;
	}


	public boolean isDrawYFieldList() {
		return drawYFieldList;
	}


	public void setDrawYFieldList(boolean drawYFieldList) {
		this.drawYFieldList = drawYFieldList;
	}


	public Series getSerie() {
		return serie;
	}


	public void setSerie(Series serie) {
		this.serie = serie;
	}


	public Tips[] getTipsHolder() {
		return tipsHolder;
	}


	public void setTipsHolder(Tips[] tipsHolder) {
		this.tipsHolder = tipsHolder;
	}

	public MarkerConfig[] getMarkerConfigHolder() {
		return markerConfigHolder;
	}


	public void setMarkerConfigHolder(MarkerConfig[] markerConfigHolder) {
		this.markerConfigHolder = markerConfigHolder;
	}

	public Label[] getLabelHolder() {
		return labelHolder;
	}


	public void setLabelHolder(Label[] labelHolder) {
		this.labelHolder = labelHolder;
	}


	public boolean isDrawMarkerConfig() {
		return drawMarkerConfig;
	}


	public void setDrawMarkerConfig(boolean drawMarkerConfig) {
		this.drawMarkerConfig = drawMarkerConfig;
	}







}
