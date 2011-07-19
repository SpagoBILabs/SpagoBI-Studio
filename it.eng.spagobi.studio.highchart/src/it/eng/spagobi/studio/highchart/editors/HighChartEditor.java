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
package it.eng.spagobi.studio.highchart.editors;

import it.eng.spagobi.studio.highchart.editors.sections.AxisSection;
import it.eng.spagobi.studio.highchart.editors.sections.ChartSection;
import it.eng.spagobi.studio.highchart.editors.sections.DrillSection;
import it.eng.spagobi.studio.highchart.editors.sections.GeneralSection;
import it.eng.spagobi.studio.highchart.editors.sections.LegendSection;
import it.eng.spagobi.studio.highchart.editors.sections.PlotOptionsSection;
import it.eng.spagobi.studio.highchart.editors.sections.SeriesListSection;
import it.eng.spagobi.studio.highchart.model.XmlTemplateGenerator;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author gavardi
 *
 * The chart document editor
 *
 */


public final class HighChartEditor extends EditorPart {

	protected boolean isDirty = false;
	protected HighChart highChart = null;
	static public final int COLORDIALOG_WIDTH = 222;
	static public final int COLORDIALOG_HEIGHT = 306;
	String projectname = null;
	IFile file;



	private static Logger logger = LoggerFactory.getLogger(HighChartEditor.class);

	public HighChartEditor() {
		super();
	}

	public void doSave(IProgressMonitor monitor) {
		logger.debug("IN");
		ByteArrayInputStream bais = null;

		// reload styles

		try {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			String newContent =  XmlTemplateGenerator.transformToXml(highChart);
			byte[] bytes = newContent.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);

		} catch (CoreException e) {
			logger.error("Error while Saving Document Composition Template File",e);
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
		logger.debug("OUT");

	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input) {
		this.setPartName(input.getName());
		logger.debug("Start Editor Initialization");		
		FileEditorInput fei = (FileEditorInput) input;
		file = fei.getFile();
		projectname = file.getProject().getName();

		try {
			// Create the model of the chart that will store informations
			highChart = XmlTemplateGenerator.readXml(file); 

		} catch (Exception e) {
			logger.error("Error during template reading "+e.getMessage(),e);
			MessageDialog.openError(site.getShell(), "Error", "Error during template reading "+e.getMessage());
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
		logger.debug("IN");

		Shell shell = parent.getShell();
		//		shell.setSize(800,600);	
		parent.setSize(800,600);

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
		logger.debug("Creating the common informations section");

		if(highChart.getWidth() == null) highChart.setWidth("100%");
		if(highChart.getHeight() == null) highChart.setHeight("100%");

		GeneralSection generalSection= new GeneralSection(highChart);
		generalSection.setEditor(this);
		generalSection.drawSection(toolkit, form, 2);
		generalSection.getSection().setExpanded(true);

		ChartSection chartSection= new ChartSection(highChart);
		chartSection.setEditor(this);
		chartSection.drawSection(toolkit, form, 2);
		chartSection.getSection().setExpanded(false);

		LegendSection legendSection= new LegendSection(highChart);
		legendSection.setEditor(this);
		legendSection.drawSection(toolkit, form, 1);
		legendSection.getSection().setExpanded(false);

		AxisSection axisSection= new AxisSection(highChart);
		axisSection.setEditor(this);
		axisSection.drawSection(toolkit, form, 2);
		axisSection.getSection().setExpanded(false);

		SeriesListSection seriesListSection= new SeriesListSection(highChart);
		seriesListSection.setEditor(this);
		seriesListSection.drawSection(toolkit, form, 2);
		seriesListSection.getSection().setExpanded(false);

		PlotOptionsSection plotOptionsSection= new PlotOptionsSection(highChart);
		plotOptionsSection.setEditor(this);
		plotOptionsSection.drawSection(toolkit, form,1);
		plotOptionsSection.getSection().setExpanded(false);

		DrillSection drillSection= new DrillSection(highChart);
		drillSection.setEditor(this);
		drillSection.drawSection(toolkit, form, 2);
		drillSection.getSection().setExpanded(false);

		// necessary for inizialization
		setIsDirty(true);

		logger.debug("OUT");
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



}
