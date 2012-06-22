package it.eng.spagobi.studio.extchart.editors.pages;

import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;
import it.eng.spagobi.server.services.api.exception.MissingParValueException;
import it.eng.spagobi.server.services.api.exception.NoServerException;
import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.pages.editorComponent.DraggedObject;
import it.eng.spagobi.studio.extchart.editors.properties.title.SubTitleProperties;
import it.eng.spagobi.studio.extchart.editors.properties.title.TitleProperties;
import it.eng.spagobi.studio.extchart.model.bo.Drill;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Param;
import it.eng.spagobi.studio.extchart.model.bo.ParamList;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.ImageDescriptors;
import it.eng.spagobi.studio.extchart.utils.ParamTableItemContent;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SerieTableItemContent;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.LoggerFactory;

public class AdvancedChartLeftPage extends AbstractPage {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AdvancedChartLeftPage.class);


	Composite advancedPropertiesComposite;

	ExtChartEditor editor;
	ExtChart extChart;
	String projectName;
	private Text textParameterName;
	private Text textValue;
	private Table tableParameters;
	private Combo comboType;
	Drill drill;

	Vector<Param> params;
	HashMap<Integer, Button> deleteButtons;
	
	public final static int NAME=0;
	public final static int TYPE=1;
	public final static int VALUE=2;	
	public final static int DELETE=3;



	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_TYPE = 1;
	
	public AdvancedChartLeftPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));


	}

	public void drawPage(){
		logger.debug("IN");
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new FillLayout(SWT.VERTICAL));
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		drill = extChart.getDrill();

		drawPropertiesSection(mainComposite);
		drawCrossNavigationSection(mainComposite);

		logger.debug("OUT");
	}

	public void drawPropertiesSection(Composite mainComposite){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(getParent());
		Section sectionProp = SWTUtils.createSection(mainComposite);
		sectionProp.setText("Advanced Chart Properties");
		sectionProp.setDescription("Below you see some chart advanced informations");
		sectionProp.setExpanded(true);

		Composite compositeProp = SWTUtils.createGridCompositeOnSection(sectionProp, 4);
		compositeProp.setLayoutData(SWTUtils.makeGridDataLayout(SWT.NONE, null, null));

		final ExtChartEditor extEditor = editor;
		
		//Width
		Label widthLabel= toolkit.createLabel(compositeProp, "Width:");
		String currentWidthValue;
		if (extChart.getWidth() != null){
			currentWidthValue = extChart.getWidth().toString();
		} else {
			currentWidthValue = ""; 
		}
		final Text widthText = toolkit.createText(compositeProp,currentWidthValue, SWT.BORDER);
		GridData gd_widthText = new GridData(SWT.NONE);
		gd_widthText.widthHint = 100;
		widthText.setLayoutData(gd_widthText);
		widthText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String widthValue = widthText.getText();
				extChart.setWidth(Integer.parseInt(widthValue));
			}
		});

		//Height
		Label heightLabel= toolkit.createLabel(compositeProp, "Height:");
		String currentHeightValue;
		if (extChart.getHeight() != null){
			currentHeightValue = extChart.getHeight().toString();
		} else {
			currentHeightValue = ""; 
		}
		final Text heightText = toolkit.createText(compositeProp, currentHeightValue, SWT.BORDER);
		GridData gd_heightText = new GridData(SWT.NONE);
		gd_heightText.widthHint = 100;
		heightText.setLayoutData(gd_heightText);
		heightText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String heightValue = heightText.getText();
				extChart.setHeight(Integer.parseInt(heightValue));
			}
		});
		
		//Animate
		Label AnimateLabel= toolkit.createLabel(compositeProp, "Animate: ");
		Boolean currentAnimateValue;
		if (extChart.getAnimate() != null){
			currentAnimateValue = extChart.getAnimate();
		} else {
			currentAnimateValue = false;
		}
		final Button useAnimateCheck = SWTUtils.drawCheck(compositeProp,currentAnimateValue, "");
		useAnimateCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				extEditor.setIsDirty(true);
				boolean selection = useAnimateCheck.getSelection();
				if(selection == true){
					extChart.setAnimate(true);
				}
				else{
					extChart.setAnimate(false);

				}
			}
		});

		//Shadow
		Label shadowLabel= toolkit.createLabel(compositeProp, "Shadow: ");
		Boolean currentShadowValue;
		if (extChart.getShadow() != null){
			currentShadowValue = extChart.getShadow();
		} else {
			currentShadowValue = false;
		}
		final Button useShadowCheck = SWTUtils.drawCheck(compositeProp, currentShadowValue, "");
		useShadowCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				extEditor.setIsDirty(true);
				boolean selection = useShadowCheck.getSelection();
				if(selection == true){
					extChart.setShadow(true);
				}
				else{
					extChart.setShadow(false);

				}
			}
		});
		

		
		//RefreshTime
		Label refreshTimeLabel= toolkit.createLabel(compositeProp, "Refresh Time:");
		String currentRefreshTimeValue;
		if (extChart.getRefreshTime() != null){
			currentRefreshTimeValue = extChart.getRefreshTime().toString();
		} else {
			currentRefreshTimeValue = ""; 
		}
		final Text refreshTimeText = toolkit.createText(compositeProp,currentRefreshTimeValue, SWT.BORDER);
		GridData gd_refreshTimeText = new GridData(SWT.NONE);
		gd_refreshTimeText.widthHint = 100;
		refreshTimeText.setLayoutData(gd_refreshTimeText);
		refreshTimeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String refreshTimeValue = refreshTimeText.getText();
				extChart.setRefreshTime(Integer.parseInt(refreshTimeValue));
			}
		});
		
		
		toolkit.createLabel(compositeProp, "");
		toolkit.createLabel(compositeProp, "");

		
		//Colors

		Label lblColors = new Label(compositeProp, SWT.NONE);
		lblColors.setText("Colors (HEX) separated by comma:");
		toolkit.createLabel(compositeProp, "");
		toolkit.createLabel(compositeProp, "");

		String currentColorsValue;
		if (extChart.getColors() != null){
			currentColorsValue = extChart.getColors().getColor();
		} else {
			currentColorsValue = ""; 
		}
		final Text textColors = toolkit.createText(compositeProp,currentColorsValue, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		GridData gd_textColors = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textColors.heightHint = 50;
		textColors.setLayoutData(gd_textColors);
		textColors.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String colorsValue = textColors.getText();
				extChart.getColors().setColor(colorsValue);
			}
		});
		
		
		
		//Important: set section Client
		sectionProp.setClient(compositeProp);
		logger.debug("OUT");
	}
	
	public void drawCrossNavigationSection(Composite mainComposite){
		logger.debug("IN");
		FormToolkit toolkit = SWTUtils.createFormToolkit(getParent());
		Section sectionProp = SWTUtils.createSection(mainComposite);
		sectionProp.setText("Chart Cross Navigation Properties");
		sectionProp.setDescription("Below you see some cross navigation informations");
		sectionProp.setExpanded(true);
		
		Composite compositeProp = SWTUtils.createGridCompositeOnSection(sectionProp, 1);
		compositeProp.setLayoutData(SWTUtils.makeGridDataLayout(GridData.FILL_BOTH, null, null));

		
		final ExtChartEditor extEditor = editor;

		//Target
		Composite compositeTargetDocument = new Composite(compositeProp, SWT.NONE);
		compositeTargetDocument.setLayout(new GridLayout(2, false));
		
		Label targetLabel= toolkit.createLabel(compositeTargetDocument, "Target Document:");
		
		String currentTargetValue;
		if (drill.getDocument() != null){
			currentTargetValue = drill.getDocument().toString();
		} else {
			currentTargetValue = ""; 
		}
		final Text targetText = toolkit.createText(compositeTargetDocument,currentTargetValue, SWT.BORDER);
		targetText.setLayoutData(new GridData(SWT.NONE));
		targetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				extEditor.setIsDirty(true);
				String targetValue = targetText.getText();
				extChart.getDrill().setDocument(targetValue);
			}
		});
		
		drawParametersTable(compositeProp);
		
		sectionProp.setClient(compositeProp);

		
	}
	
	public void drawParametersTable(Composite container){
		//Input parameters part
		Composite compositeTableParameters = new Composite(container, SWT.NONE);
		compositeTableParameters.setLayout(new GridLayout(8, false));
		
		Label lblParameterName = new Label(compositeTableParameters, SWT.NONE);
		lblParameterName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblParameterName.setText("Parameter Name: ");
		
		textParameterName = new Text(compositeTableParameters, SWT.BORDER);
		GridData gd_textParameterName = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textParameterName.widthHint = 100;
		textParameterName.setLayoutData(gd_textParameterName);
		
		Label lblType = new Label(compositeTableParameters, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Type:");
		
		comboType = new Combo(compositeTableParameters, SWT.READ_ONLY);
		comboType.add("CATEGORY");
		comboType.add("ABSOLUTE");
		comboType.add("SERIE");
		comboType.add("RELATIVE");
		GridData gd_comboType = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_comboType.widthHint = 100;
		comboType.setLayoutData(gd_comboType);
		comboType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if ( comboType.getText().equals("ABSOLUTE") ){
					textValue.setEnabled(true);
				} else {
					textValue.setEnabled(false);
				}
			}
		});	
		
		Label lblValue = new Label(compositeTableParameters, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblValue.setText("Value:");
		
		textValue = new Text(compositeTableParameters, SWT.BORDER);
		GridData gd_textValue = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textValue.widthHint = 100;
		textValue.setLayoutData(gd_textValue);
		textValue.setEnabled(false);
		new Label(compositeTableParameters, SWT.NONE);
		
		Button buttonAdd = new Button(compositeTableParameters, SWT.NONE);
		buttonAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Param newParam = new Param();
				newParam.setName(textParameterName.getText());
				newParam.setType(comboType.getText());
				newParam.setValue(textValue.getText() != null ? textValue.getText() : "");
				
				//add new drill param to the model object
				ParamList paramList = drill.getParamList();
				paramList.getParams().add(newParam);
				drill.setParamList(paramList);
				
				logger.debug("update table");
				// Create a new item in the table to hold the new data
				TableItem item = new TableItem(tableParameters, SWT.NONE);
				
				ParamTableItemContent paramTableItemContent = new ParamTableItemContent();
				paramTableItemContent.setParam(newParam);
				
				item.setData(paramTableItemContent);
				item.setText(NAME, newParam.getName());
				item.setText(TYPE, newParam.getType());
				item.setText(VALUE, newParam.getValue() != null ? newParam.getValue() : "");
				createButtons(tableParameters, item, newParam, paramTableItemContent);
				editor.setIsDirty(true);
				tableParameters.redraw();
				
				//clean input texts
				textParameterName.setText("");
				comboType.deselectAll();
				comboType.clearSelection();
				textValue.setText("");
				
			}
		});
		buttonAdd.setText("Add");
		
		//Table Part
		Composite compositeTable = new Composite(container, SWT.NONE);
		compositeTable.setLayout(new GridLayout(1, false));
		compositeTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		tableParameters = new Table(compositeTable, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		tableParameters.setHeaderVisible(true);
		tableParameters.setLinesVisible(true);
		
		GridData dataOrder = new GridData(SWT.FILL, SWT.FILL, true, true);
		dataOrder.heightHint = 150;
		tableParameters.setLayoutData(dataOrder);
		
		TableColumn tblclmnName = new TableColumn(tableParameters, SWT.NONE);
		tblclmnName.setWidth(127);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnType = new TableColumn(tableParameters, SWT.NONE);
		tblclmnType.setWidth(127);
		tblclmnType.setText("Type");
		
		TableColumn tblclmnValue = new TableColumn(tableParameters, SWT.NONE);
		tblclmnValue.setWidth(127);
		tblclmnValue.setText("Value");
		
		TableColumn tblclmnDelete = new TableColumn(tableParameters, SWT.NONE);
		tblclmnDelete.setWidth(59);
		tblclmnDelete.setText("Delete");
		
		//Populate table
		params = extChart.getDrill().getParamList().getParams();
		for (Iterator iterator = params.iterator(); iterator.hasNext();) {
			Param param = (Param) iterator.next();
			TableItem item = new TableItem (tableParameters, SWT.NONE);

			ParamTableItemContent paramTableItemContent = new ParamTableItemContent();
			paramTableItemContent.setParam(param);

			item.setData(paramTableItemContent);
			createButtons(tableParameters, item, param, paramTableItemContent);
			item.setText(NAME, param.getName());
			item.setText(TYPE, param.getType());
			item.setText(VALUE, param.getValue() != null ? param.getValue() : "");

		}
		
		tableParameters.redraw();
		compositeTable.redraw();
	}
	
	void createButtons(final Table tableParameters, final TableItem item, final Param param, ParamTableItemContent paramTableItemContent){
		logger.debug("IN");

		// delete
		logger.debug("draw delete button");
		TableEditor tableEditor = new TableEditor(tableParameters);
		Image deleteImage = ImageDescriptors.getEraseIcon().createImage();
		final  Button buttonDel = new Button(tableParameters, SWT.PUSH);
		paramTableItemContent.setDeleteButton(buttonDel);

		buttonDel.setImage(deleteImage);
		buttonDel.pack();	

		tableEditor.minimumWidth = buttonDel.getSize().x;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.setEditor(buttonDel, item, DELETE);

		buttonDel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//delete table item
				ExtChart extChart = editor.getExtChart();
				Object paramO = item.getData();
				ParamTableItemContent paramTableItemContent = (ParamTableItemContent)paramO;
				editor.setIsDirty(true);
				Param param = paramTableItemContent.getParam();
				// delete param from model

				int index = tableParameters.indexOf(item);
				logger.debug("remove row"+item.getText(NAME)+ " at index "+index);
				tableParameters.remove(index);
				buttonDel.dispose();
				ParamList paramList = drill.getParamList();
				paramList.getParams().remove(param);
				drill.setParamList(paramList);
				tableParameters.redraw();
				tableParameters.getParent().redraw();

				logger.debug("row removed");

			}

		}
		);
		logger.debug("OUT");

	}
	

	public ExtChartEditor getEditor() {
		return editor;
	}

	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}

	public ExtChart getExtChart() {
		return extChart;
	}

	public void setExtChart(ExtChart extChart) {
		this.extChart = extChart;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	

}
