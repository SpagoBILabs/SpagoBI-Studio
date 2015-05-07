/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.DialChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.LinkableChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.ScatterChartModel;
import it.eng.spagobi.studio.chart.editors.model.chart.XYChartModel;
import it.eng.spagobi.studio.chart.utils.Style;

import java.util.Iterator;
import java.util.Set;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gavardi
 *
 *	This class contains the element present in the editor (common for each chart type, 
 *	its fields let the creation for style parameters, configuration parameters, serie personalization parameters forms
 *
 */

public class ChartEditorComponents {

	private static Logger logger = LoggerFactory.getLogger(ChartEditorComponents.class);
	// Sections
	Composite sectionClientInformation=null;
	Composite sectionClientDimension=null;
	Composite sectionClientStyle=null;
String projectname = null;
	// editors components
	ConfigurationEditor configurationEditor=null;
	DrillConfigurationEditor drillConfigurationEditor=null;
	SeriesPersonalizationEditor seriesPersonalizationEditor=null;
	IntervalsInformationEditor intervalsInformationEditor=null;
	YZRangesEditor yzRangesEditor=null;
	ScatterRangeMarkerEditor scatterRangeMarkerEditor=null;
	DataSetInformationEditor dataInformationEditor=null;


	public ChartEditorComponents() {
		super();
	}




	/** Create Style parameters form
	 * 
	 * @param model
	 * @param editor
	 * @param section
	 * @param toolkit
	 */

	public void createStyleParametersForm(final ChartModel model, final ChartEditor editor, final Composite section, FormToolkit toolkit){
		logger.debug("Start Style parameters form creation");
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		section.setLayout(gl);

		Set<String> stylesTrattati=model.getStyleParametersEditors().keySet();

		for (Iterator iterator = stylesTrattati.iterator(); iterator.hasNext();) {
			String styleName = (String) iterator.next();
			logger.debug("Style parameter "+styleName);			
			final Style style=model.getStyleParametersEditors().get(styleName);
			Group group=new Group(section,SWT.NULL);
			group.setText(style.getDescription());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setToolTipText(style.getTooltip());
			GridLayout gl1 = new GridLayout();
			gl1.numColumns = 4;
			group.setLayout(gl1);			
//			Label styleLabel = new Label(group, SWT.BORDER_DOT);
//			styleLabel.setText(style.getDescription());
//			styleLabel.setForeground(new Color(group.getDisplay(),0,0,255));
			if(style.getTooltip()!=null){
//				styleLabel.setToolTipText(style.getTooltip());
			}
			//			Label spaceLabel1 = new Label(section, SWT.BORDER_DOT);
			//			spaceLabel1.setText("");
			//			spaceLabel1 = new Label(section, SWT.BORDER_DOT);
			//			spaceLabel1.setText("");
			//			spaceLabel1 = new Label(section, SWT.BORDER_DOT);
			//			spaceLabel1.setText("");

			// Draw Parameters form
			if(style.isHasSize()){
				Label sizeLabel = new Label(group, SWT.NULL);
				sizeLabel.setText("		Size:");
				sizeLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));

				final Spinner styleSizeText = new Spinner (group, SWT.BORDER);
				styleSizeText.setMaximum(100000);
				styleSizeText.setMinimum(0);
				styleSizeText.setSelection(style.getSize()!=null?style.getSize() : 10);
				//styleSizeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				styleSizeText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						if(editor!=null) editor.setIsDirty(true);
						int newSize = styleSizeText.getSelection();
						Integer newSizeInt=null;
						try{
							newSizeInt=Integer.valueOf(newSize);
						}
						catch (Exception e) {
							newSizeInt=new Integer(10);
						}
						style.setSize(newSizeInt);
					}


				});
			}
			else{
				Label sl=new Label(group,SWT.NULL);
				sl.setText("");
				sl=new Label(group,SWT.NULL);
				sl.setText("");
			}



			if(style.isHasFont()){
				Label fontLabel = new Label(group, SWT.NULL);
				fontLabel.setText("			Font:");
				fontLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				//fontLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				final Combo styleFontCombo = new Combo(group,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
				styleFontCombo.add("Helvetica");
				styleFontCombo.add("Times_New_Roman");
				styleFontCombo.add("Arial");
				int index2=styleFontCombo.indexOf(style.getFont()!=null ? style.getFont() : "");
				if(index2!=-1) styleFontCombo.select(index2);
				else	styleFontCombo.select(0);
				//styleFontCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				styleFontCombo.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						String newFont = styleFontCombo.getText();
						style.setFont(newFont);
						if(editor!=null)editor.setIsDirty(true);
					}
				});
			}
			else{
				Label sl=new Label(group,SWT.NULL);
				sl.setText("");
				sl=new Label(group,SWT.NULL);
				sl.setText("");
			}

			if(style.isHasColor()){
				Label colorLabel1 = new Label(group, SWT.NULL);
				colorLabel1.setText("		Color:");
				colorLabel1.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				Composite innergroup = toolkit.createComposite(group);
				GridLayout colorGd = new GridLayout();
				colorGd.numColumns = 2;
				colorGd.marginHeight = 0;
				colorGd.marginBottom = 0;
				innergroup.setLayout(colorGd);
				final Label colorLabel = new Label(innergroup, SWT.BORDER);
				colorLabel.setText("          ");
				String hexadecimal = style.getColor()!=null ? ChartEditor.convertRGBToHexadecimal(style.getColor()) : "#FFFFFF";
				RGB rgb =null;
				try{
					rgb= ChartEditor.convertHexadecimalToRGB(hexadecimal);
				}
				catch (Exception e) {
					rgb=new RGB(255,0,0);
				}
				final Color color = new org.eclipse.swt.graphics.Color(group.getDisplay(), rgb);
				colorLabel.setBackground(color);
				Button button = new Button(innergroup, SWT.PUSH);
				button.setText("Color...");
				final Shell parentShell = group.getShell();
				button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
						centerShell.setLocation(
								(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
								(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
						ColorDialog colorDg = new ColorDialog(centerShell,
								SWT.APPLICATION_MODAL);
						colorDg.setRGB(colorLabel.getBackground().getRGB());
						//colorDg.setText("Choose a color");
						RGB rgb = colorDg.open();
						if (rgb != null) {
							// Dispose the old color, create the
							// new one, and set into the label
							color.dispose();
							Color newColor = new Color(parentShell.getDisplay(), rgb);
							colorLabel.setBackground(newColor);
							if(editor!=null) editor.setIsDirty(true);
							String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
							style.setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));
						}
						if(editor!=null)editor.setIsDirty(true);
						centerShell.dispose();
					}
				});
			}
			else{
				Label sl=new Label(group,SWT.NULL);
				sl.setText("");
				sl=new Label(group,SWT.NULL);
				sl.setText("");
			}



			if(style.isHasOrientation()){
				Label orientationLabel = new Label(group, SWT.NULL);
				orientationLabel.setText("			Orientation:");
				orientationLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));

				final Combo combo = new Combo(group,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
				boolean selected=false;
				combo.add(Style.HORIZONTAL);
				combo.add(Style.VERTICAL);

				int index=combo.indexOf(style.getOrientation()!=null ? style.getOrientation().toUpperCase() : "");
				if(index!=-1) combo.select(index);
				else index=0;
				//combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				combo.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						String newOrientation = combo.getText();
						style.setOrientation(newOrientation);
						if(editor!=null)editor.setIsDirty(true);
					}
				});
			}
			else{
				Label sl=new Label(group,SWT.NULL);
				sl.setText("");
				sl=new Label(group,SWT.NULL);
				sl.setText("");
			}


		}

	}






	public Composite getSectionClientInformation() {
		return sectionClientInformation;
	}

	public void setSectionClientInformation(Composite sectionClientInformation) {
		this.sectionClientInformation = sectionClientInformation;
	}

	public Composite getSectionClientDimension() {
		return sectionClientDimension;
	}

	public void setSectionClientDimension(Composite sectionClientDimension) {
		this.sectionClientDimension = sectionClientDimension;
	}


	public Composite getSectionClientStyle() {
		return sectionClientStyle;
	}

	public void setSectionClientStyle(Composite sectionClientStyle) {
		this.sectionClientStyle = sectionClientStyle;
	}


	/** Calls the creation of configuration form
	 * 
	 * @param model
	 * @param editor
	 * @param formToolkit
	 * @param scrolledForm
	 */

	public void createConfigurationSection(final ChartModel model, ChartEditor editor, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		configurationEditor.createConfigurationParametersForm(model,editor,formToolkit, scrolledForm);
	}




	/** Calls the creation of specific configuration form
	 * 
	 * @param model
	 * @param editor
	 * @param formToolkit
	 * @param scrolledForm
	 */

	public void createSpecificConfigurationSection(final ChartModel model, ChartEditor editor, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		configurationEditor.createSpecificConfigurationParametersForm(model,editor,formToolkit);
	}


	/** Calls the creation of drill configuration form
	 * 
	 * @param model
	 * @param editor
	 * @param formToolkit
	 * @param scrolledForm
	 */

	public void createDrillConfigurationSection(final LinkableChartModel model, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		drillConfigurationEditor=new DrillConfigurationEditor(model,formToolkit, scrolledForm);
	}


	/** Calls the creation of seres personalization form
	 * 
	 * @param model
	 * @param editor
	 * @param formToolkit
	 * @param scrolledForm
	 */

	public void createSeriesPersonalizationSection(final ChartModel model, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		seriesPersonalizationEditor=new SeriesPersonalizationEditor(model, formToolkit, scrolledForm);
	}


	/** Calls the creation of intervals infroamtion form
	 * 
	 * @param model
	 * @param editor
	 * @param formToolkit
	 * @param scrolledForm
	 */

	public void createIntervalsInformationsSection(final DialChartModel model, ChartEditor editor, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		intervalsInformationEditor=new IntervalsInformationEditor(model, formToolkit, scrolledForm);
	}

	/** Calls the creation for range marker section form
	 * 
	 * @param model
	 * @param formToolkit
	 * @param scrolledForm
	 */

	public void createScatterRangeMarkerSection(final ScatterChartModel model, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		scatterRangeMarkerEditor=new ScatterRangeMarkerEditor(model, formToolkit, scrolledForm);
	}

	/** Calls the creation for Y Z Range form
	 * 
	 * @param model
	 * @param formToolkit
	 * @param scrolledForm
	 */


	public void createYZRangesSection(final XYChartModel model, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		yzRangesEditor=new YZRangesEditor(model, formToolkit, scrolledForm);
	}


	public void createDataSetInformationSection(final ChartModel model, FormToolkit formToolkit, final ScrolledForm scrolledForm){
		dataInformationEditor=new DataSetInformationEditor(model, formToolkit, scrolledForm, projectname);
	}


	public DrillConfigurationEditor getDrillConfigurationEditor() {
		return drillConfigurationEditor;
	}

	public void setDrillConfigurationEditor(
			DrillConfigurationEditor drillConfigurationEditor) {
		this.drillConfigurationEditor = drillConfigurationEditor;
	}

	public ConfigurationEditor getConfigurationEditor() {
		return configurationEditor;
	}

	public void setConfigurationEditor(ConfigurationEditor configurationEditor) {
		this.configurationEditor = configurationEditor;
	}




	public SeriesPersonalizationEditor getSeriesPersonalizationEditor() {
		return seriesPersonalizationEditor;
	}




	public void setSeriesPersonalizationEditor(
			SeriesPersonalizationEditor seriesPersonalizationEditor) {
		this.seriesPersonalizationEditor = seriesPersonalizationEditor;
	}




	public IntervalsInformationEditor getIntervalsInformationEditor() {
		return intervalsInformationEditor;
	}




	public void setIntervalsInformationEditor(
			IntervalsInformationEditor intervalsInformationEditor) {
		this.intervalsInformationEditor = intervalsInformationEditor;
	}




	public YZRangesEditor getYzRangesEditor() {
		return yzRangesEditor;
	}




	public void setYzRangesEditor(YZRangesEditor yzRangesEditor) {
		this.yzRangesEditor = yzRangesEditor;
	}




	public ScatterRangeMarkerEditor getScatterRangeMarkerEditor() {
		return scatterRangeMarkerEditor;
	}




	public void setScatterRangeMarkerEditor(
			ScatterRangeMarkerEditor scatterRangeMarkerEditor) {
		this.scatterRangeMarkerEditor = scatterRangeMarkerEditor;
	}




	public String getProjectname() {
		return projectname;
	}




	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}



}
