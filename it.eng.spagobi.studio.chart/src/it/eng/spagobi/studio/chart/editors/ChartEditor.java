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
import it.eng.spagobi.studio.chart.editors.model.chart.ChartModelFactory;
import it.eng.spagobi.studio.core.log.SpagoBILogger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * 
 * @author gavardi
 *
 * The chart document editor
 *
 */


public final class ChartEditor extends EditorPart {

	protected boolean isDirty = false;
	protected ChartModel model = null;
	static public final int COLORDIALOG_WIDTH = 222;
	static public final int COLORDIALOG_HEIGHT = 306;

	protected ChartEditorComponents components = null;

	public ChartEditor() {
		super();
	}

	public void doSave(IProgressMonitor monitor) {
		SpagoBILogger.infoLog("Start Saving Chart Template File");
		ByteArrayInputStream bais = null;
		try {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			String newContent = model.toXML();
			byte[] bytes = newContent.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);

			// reload the this document for the model!
			InputStream thisIs = null;
			thisIs = file.getContents();
			SAXReader reader = new SAXReader();
			Document thisDocument = reader.read(thisIs);
			model.setThisDocument(thisDocument);

		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error while Saving Chart Template File",e);
			e.printStackTrace();
		}	catch (DocumentException e) {
			SpagoBILogger.errorLog("Error while reloading current template",e);
			e.printStackTrace();
		}
		finally { 
			if (bais != null)
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		setIsDirty(false);



	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input) {
		this.setPartName(input.getName());
		SpagoBILogger.infoLog("Start Editor Initialization");		
		FileEditorInput fei = (FileEditorInput) input;
		IFile file = fei.getFile();

		try {
			// Create the model of the chart that will store informations
			components=new ChartEditorComponents();
			model = ChartModelFactory.createChartModel(file);
			model.setEditor(this);
		} catch (Exception e) {
			SpagoBILogger.errorLog("Error during Editor Initialization",e);
			return;
		}
		setInput(input);
		setSite(site);
	}

	public boolean isDirty() {
		return isDirty;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void createPartControl(final Composite parent) {
		SpagoBILogger.infoLog("Creating the editor for charts");

		final FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 10;
		layout.topMargin = 20;
		layout.leftMargin = 20;
		form.getBody().setLayout(layout);

		// ++++++++++++++  Chart common settings section ++++++++++++++ 
		SpagoBILogger.infoLog("Creating the common informations section");
		Section sectionInformation = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		sectionInformation.setLayoutData(td);
		sectionInformation.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionInformation.setText("Chart information");
		sectionInformation.setDescription("Below you see some chart general informations");

		components.setSectionClientInformation(toolkit.createComposite(sectionInformation));
		Composite sectionClientInformation = components.getSectionClientInformation();
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		sectionClientInformation.setLayout(gl);

		Label typeLabel = new Label(sectionClientInformation, SWT.NULL);
		typeLabel.setText("Title:");
		final Text titleText=new Text(sectionClientInformation, SWT.BORDER);
		titleText.setText(model.getTitle());
		titleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		titleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String titleValue = titleText.getText();
				model.setTitle(titleValue);
			}
		});

		Label subTitleLabel = new Label(sectionClientInformation, SWT.NULL);
		subTitleLabel.setText("Sub Title:");
		final Text subTitleText=new Text(sectionClientInformation, SWT.BORDER);
		subTitleText.setText(model.getSubTitle());
		subTitleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		subTitleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String subTitleValue = subTitleText.getText();
				model.setSubTitle(subTitleValue);
			}
		});

		Label movieLabel = new Label(sectionClientInformation, SWT.NULL);
		movieLabel.setText("Type:");
		Label movie = new Label(sectionClientInformation, SWT.NULL);
		movie.setText(model.getType());

		sectionInformation.setClient(sectionClientInformation);
		sectionInformation.pack();
		sectionClientInformation.pack();

		// ++++++++++++++  Chart dimension settings section ++++++++++++++ 
		SpagoBILogger.infoLog("Creating the dimension informations section");
		Section sectionDimension = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		sectionDimension.setLayoutData(td);
		sectionDimension.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionDimension.setText("Chart dimension");
		sectionDimension.setDescription("Below you see the chart dimension settings:");

		components.setSectionClientDimension(toolkit.createComposite(sectionDimension));

		Composite sectionClientDimension = components.getSectionClientDimension();
		gl = new GridLayout();
		gl.numColumns = 2;
		sectionClientDimension.setLayout(gl);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		Label widthLabel = new Label(sectionClientDimension, SWT.NULL);
		widthLabel.setText("Width (in pixel):");

		final Spinner widthText = new Spinner (sectionClientDimension, SWT.BORDER);
		widthText.setMaximum(100000);
		widthText.setMinimum(0);
		widthText.setSelection(new Integer(model.getDimension().getWidth()));		
		widthText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				setIsDirty(true);
				int val = widthText.getSelection();
				// check it is a number 
				model.getDimension().setWidth(val);
			}
		});

		//		final Text widthText = new Text(sectionClientDimension, SWT.BORDER);
		//		widthText.setText(new Integer(model.getDimension().getWidth()).toString());
		//		widthText.addModifyListener(new ModifyListener() {
		//			public void modifyText(ModifyEvent event) {
		//				setIsDirty(true);
		//				String widthStr = widthText.getText();
		//				model.getDimension().setWidth(Integer.parseInt(widthStr));
		//				int i=model.getSeriesPersonalizationHashMap().size();
		//				int j=0;
		//			}
		//		});

		Label heightLabel = new Label(sectionClientDimension, SWT.NULL);
		heightLabel.setText("Height (in pixel):");

		final Spinner heightText = new Spinner (sectionClientDimension, SWT.BORDER);
		heightText.setMaximum(100000);
		heightText.setMinimum(0);
		heightText.setSelection(new Integer(model.getDimension().getHeight()));		
		heightText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				setIsDirty(true);
				int val = heightText.getSelection();
				// check it is a number 
				model.getDimension().setHeight(val);
			}
		});


		sectionDimension.setClient(sectionClientDimension);



		// ++++++++++++++ Chart Style Settings ++++++++++++++ 
		SpagoBILogger.infoLog("Creating style informations section");
		final Section sectionStyle = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		td = new TableWrapData(TableWrapData.FILL);
		sectionStyle.setLayoutData(td);
		sectionStyle.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionStyle.setText("Chart Style settings");
		sectionStyle.setDescription("Below you see the chart style youc an define:");

		components.setSectionClientStyle(toolkit.createComposite(sectionStyle));
		Composite sectionClientStyle = components.getSectionClientStyle();
		components.createStyleParametersForm(model,this, sectionClientStyle, toolkit);
		sectionStyle.setClient(sectionClientStyle);



		// ++++++++++++++ Chart Conf Settings ++++++++++++++ 
		SpagoBILogger.infoLog("Creating Common configuration informations section");
		components.setConfigurationEditor(new ConfigurationEditor(toolkit,form));
		components.createConfigurationSection(model,null, toolkit, form);




		// ******************	get subtypes COMBO	******************
		SpagoBILogger.infoLog("Create the sub type combo: ");
		List<String> subTypes=null;
		try {
			SpagoBILogger.infoLog("Get all configured sub types");
			subTypes=ChartModel.getConfiguredChartSubTypes(model.getType());
		} catch (Exception e1) {
			SpagoBILogger.errorLog("Error while retrieving possible subtypes",e1);
			e1.printStackTrace();
			return;		
		}
		Label subTypeLabel = new Label(sectionClientInformation, SWT.NULL);
		subTypeLabel.setText("SubType:");
		final Combo combo = new Combo(sectionClientInformation,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		for (Iterator iterator = subTypes.iterator(); iterator.hasNext();) {
			String subType = (String) iterator.next();
			combo.add(subType);
		}
		combo.setToolTipText("Select the chart subtype among "+model.getType()+": the specific configuration parameters and the forms will change accordingly");

		// Put default subtype on combo
		SpagoBILogger.infoLog("Model subtype is "+subTypeLabel);
		int index=combo.indexOf(model.getSubType());
		combo.select(index);


		// ******************	Background color	******************
		Label labelColor=new Label(sectionClientInformation, SWT.NULL);
		labelColor.setText("Background color");
		labelColor.setToolTipText("Background color for the chart");
		Composite innerSection = toolkit.createComposite(sectionClientInformation);
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);
		final Label colorLabel = new Label(innerSection, SWT.BORDER);
		colorLabel.setText("          ");
		RGB rgb=null;
		if(model.getBackgroundColor()!=null){
			rgb = model.getBackgroundColor();
		}
		else{
			rgb=new RGB(255,0,0);
		}
		final Color color = new Color(sectionClientInformation.getDisplay(), rgb);
		colorLabel.setBackground(color);
		Button button = new Button(innerSection, SWT.PUSH);
		button.setText("Color...");
		final Shell parentShell = sectionClientInformation.getShell();
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
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
					model.setBackgroundColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));
					setIsDirty(true);
				}
				centerShell.dispose();
			}
		});

		//setIsDirty(true);
		try{
			model.initializeEditor(this,components,toolkit,form);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error while generating static form",e);		
			e.printStackTrace();
		}


		//*********** DATASET INFORMATION SECTION **********
		components.createDataSetInformationSection(model, toolkit, form);		


		//*************************	CHANGE SUBTYPE: ERASE PREVIOUS SPECIFIC MODEL and CREATE NEW FORM	*********************
		SpagoBILogger.infoLog("Add listener for subtype selection combo: erase specific settings defined in model and change form according to new subtype");
		combo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(model.getSubType()!=null && model.getSubType().equalsIgnoreCase(combo.getText())){
					SpagoBILogger.warningLog("Already Selected");
				}
				else{
					try{
						model.setSubType(combo.getText());
						// close configuration parameter section
						(components.getConfigurationEditor().getSectionConf()).setExpanded(false);
						model.refreshEditor(null, components, toolkit, form);

					}
					catch (Exception ex) {
						SpagoBILogger.errorLog("Error while changing subtype form",ex);						
						ex.printStackTrace();	
					}
				}
				setIsDirty(true);	
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		setIsDirty(true); // first time must be dirty
	}


	public void setFocus() {
	}




	public void setIsDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}






	public static String convertRGBToHexadecimal(RGB rgb) {
		int red = rgb.red;
		int green = rgb.green;
		int blue = rgb.blue;
		String redHexadecimal = Integer.toHexString(red);
		String greenHexadecimal = Integer.toHexString(green);
		String blueHexadecimal = Integer.toHexString(blue);
		if (redHexadecimal.length() == 1) redHexadecimal = "0" + redHexadecimal;
		if (greenHexadecimal.length() == 1) greenHexadecimal = "0" + greenHexadecimal;
		if (blueHexadecimal.length() == 1) blueHexadecimal = "0" + blueHexadecimal;
		return "#" + redHexadecimal + greenHexadecimal + blueHexadecimal;
	}




	public static RGB convertHexadecimalToRGB(String hexadecimal) throws NumberFormatException{
		java.awt.Color col=null;
		try{
			col=java.awt.Color.decode(hexadecimal);
		}
		catch (Exception e) {
			col=java.awt.Color.WHITE;
		}
		int red=col.getRed();
		int blue=col.getBlue();
		int green=col.getGreen();

		return new RGB(red, green, blue);
	}


	public ChartModel getModel() {
		return model;
	}

}
