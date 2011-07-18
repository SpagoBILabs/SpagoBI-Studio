package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.editors.HighChartEditor;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.SubTitle;
import it.eng.spagobi.studio.highchart.model.bo.Title;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeneralSection extends AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(GeneralSection.class);

	Label typeLabel;
	Label titleLabel;
	Text  titleText;	
	Label subTitleLabel;
	Text  subTitleText;

	Spinner widthSpinner;
	Spinner heightSpinner;

	Composite styleComposite;
	Group titleGroup;
	Group subTitleGroup;

	Combo titleAlignCombo; 
	Button titleFloatingCheck;
	Spinner titleMarginSpinner;
	Text titleStyleText;
	Combo titleVerticalAlignCombo;
	Spinner titleXSpinner;
	Spinner titleYSpinner;

	Combo subTitleAlignCombo; 
	Button subTitleFloatingCheck;
	Spinner subTitleMarginSpinner;
	Text subTitleStyleText;
	Combo subTitleVerticalAlignCombo;
	Spinner subTitleXSpinner;
	Spinner subTitleYSpinner;


	public GeneralSection(HighChart highChart) {
		super(highChart);
	}

	public void addListeners(){
		logger.debug("IN");

		final Title title = highChart.getTitle();
		final SubTitle subTitle = highChart.getSubTitle();

		widthSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = widthSpinner.getSelection();
				String valS = Integer.valueOf(val)+"%";
				highChart.setWidth(valS);
			}
		});
		heightSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = heightSpinner.getSelection();
				String valS = Integer.valueOf(val)+"%";
				highChart.setHeight(valS);
			}
		});

		titleAlignCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = titleAlignCombo.getItem(titleAlignCombo.getSelectionIndex());
				title.setAlign(value);
			}
		});
		subTitleAlignCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = subTitleAlignCombo.getItem(subTitleAlignCombo.getSelectionIndex());
				subTitle.setAlign(value);
			}
		});
		titleVerticalAlignCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = titleVerticalAlignCombo.getItem(titleVerticalAlignCombo.getSelectionIndex());
				title.setVerticalAlign(value);
			}
		});
		subTitleVerticalAlignCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.setIsDirty(true);
				String value = subTitleVerticalAlignCombo.getItem(subTitleVerticalAlignCombo.getSelectionIndex());
				subTitle.setVerticalAlign(value);
			}
		});

		titleMarginSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = titleMarginSpinner.getSelection();
				title.setMargin(val);
			}
		});
		subTitleMarginSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = subTitleMarginSpinner.getSelection();
				subTitle.setMargin(val);
			}
		});
		titleXSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = titleXSpinner.getSelection();
				title.setX(val);
			}
		});	
		subTitleXSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = subTitleXSpinner.getSelection();
				subTitle.setX(val);
			}
		});
		titleYSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = titleYSpinner.getSelection();
				title.setY(val);
			}
		});	
		subTitleYSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				int val = subTitleYSpinner.getSelection();
				subTitle.setY(val);
			}
		});
		logger.debug("OUT");
	}


	@Override
	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);

		// ++++++++++++++  Chart common settings section ++++++++++++++ 
		logger.debug("IN");

		section.setText("Chart information");
		section.setDescription("Below you see some chart general informations");

		Label tLabel= toolkit.createLabel(composite, "Type");
		typeLabel = toolkit.createLabel(composite, highChart.getChart().getDefaultSeriesType());

		titleLabel= toolkit.createLabel(composite, "Title:");
		titleText = toolkit.createText(composite, highChart.getTitle().getText(), SWT.BORDER);
		titleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		titleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String titleValue = titleText.getText();
				highChart.getTitle().setText(titleValue);
			}
		});

		subTitleLabel= toolkit.createLabel(composite, "Subtitle:");
		subTitleText = toolkit.createText(composite, highChart.getSubTitle().getText(), SWT.BORDER);
		subTitleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		subTitleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String titleValue = subTitleText.getText();
				highChart.getSubTitle().setText(titleValue);
			}
		});

		widthSpinner = SWTUtils.drawSpinner(composite, highChart.getIntegerWidth(), "Width (%):");
		heightSpinner = SWTUtils.drawSpinner(composite, highChart.getIntegerHeight(), "Height (%):");

		styleComposite = toolkit.createComposite(composite);
		styleComposite.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		GridLayout gla = new GridLayout();
		gla.numColumns = 1;
		styleComposite.setLayout(gla);
		Title title = highChart.getTitle();
		SubTitle subTitle = highChart.getSubTitle();
		titleGroup = createNColGroup(styleComposite,"Title Style: ", 14);
		titleGroup.setBackground(SWTUtils.getColor(titleGroup.getDisplay(), SWTUtils.LIGHT_GRAY));
		titleAlignCombo = SWTUtils.drawCombo(titleGroup, new String[]{"", "left", "center","right"},title.getAlign() , "Align: ");
		titleVerticalAlignCombo = SWTUtils.drawCombo(titleGroup, new String[]{"", "top", "middle", "bottom"},title.getVerticalAlign() , "Vertical Align: ");
		titleFloatingCheck = SWTUtils.drawCheck(titleGroup, title.isFloating(), "Floating: ");
		titleStyleText = SWTUtils.drawText(toolkit, titleGroup, title.getStyle(), "Style: ");
		titleMarginSpinner = SWTUtils.drawSpinner(titleGroup, title.getMargin(), "Margin: ");
		titleXSpinner = SWTUtils.drawSpinner(titleGroup, title.getX(), "X: ");
		titleYSpinner = SWTUtils.drawSpinner(titleGroup, title.getY(), "Y: ");

		subTitleGroup = createNColGroup(styleComposite,"SubTitle style: ", 14);
		subTitleGroup.setBackground(SWTUtils.getColor(titleGroup.getDisplay(), SWTUtils.LIGHT_GRAY));
		subTitleAlignCombo = SWTUtils.drawCombo(subTitleGroup, new String[]{"", "left", "center","right"},subTitle.getAlign() , "Align: ");
		subTitleVerticalAlignCombo = SWTUtils.drawCombo(subTitleGroup, new String[]{"", "top", "middle", "bottom"},subTitle.getVerticalAlign() , "Vertical Align: ");
		subTitleFloatingCheck = SWTUtils.drawCheck(subTitleGroup, subTitle.isFloating(), "Floating: ");
		subTitleStyleText = SWTUtils.drawText(toolkit, subTitleGroup, subTitle.getStyle(), "Style: ");
		subTitleMarginSpinner = SWTUtils.drawSpinner(subTitleGroup, subTitle.getMargin(), "Margin: ");
		subTitleXSpinner = SWTUtils.drawSpinner(subTitleGroup, subTitle.getX(), "X: ");
		subTitleYSpinner = SWTUtils.drawSpinner(subTitleGroup, subTitle.getY(), "Y: ");

		titleGroup.pack();
		subTitleGroup.pack();
addListeners();
		section.setClient(composite);
		//		section.pack();
		//		composite.pack();		

		logger.debug("OUT");

	}


	public HighChartEditor getEditor() {
		return editor;
	}


	public void setEditor(HighChartEditor editor) {
		this.editor = editor;
	}






}
