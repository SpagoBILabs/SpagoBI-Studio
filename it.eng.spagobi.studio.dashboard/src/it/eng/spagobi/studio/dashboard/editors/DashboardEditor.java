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
package it.eng.spagobi.studio.dashboard.editors;

import it.eng.spagobi.studio.dashboard.editors.model.dashboard.DashboardModel;
import it.eng.spagobi.studio.dashboard.editors.model.dashboard.DashboardModelFactory;
import it.eng.spagobi.studio.dashboard.editors.model.dashboard.Parameter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardEditor extends EditorPart {

	protected boolean isDirty = false;
	protected DashboardModel model = null;
	static public final int COLORDIALOG_WIDTH = 222;
	static public final int COLORDIALOG_HEIGHT = 306;
	private static Logger logger = LoggerFactory.getLogger(DashboardEditor.class);

	
	public void doSave(IProgressMonitor monitor) {
		ByteArrayInputStream bais = null;
		try {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			String newContent = model.toXML();
			byte[] bytes = newContent.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);
		} catch (CoreException e) {
			// TODO manage exception
			e.printStackTrace();
		} finally { 
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
		FileEditorInput fei = (FileEditorInput) input;
		IFile file = fei.getFile();
		try {
			model = DashboardModelFactory.createDashboardModel(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void createPartControl(Composite parent) {
		// if model type == null type is not supported
		if(model.getType() == null){
			logger.error("type not supported");
			MessageDialog.openError(parent.getShell(), "Error", "movie type "+model.getMovie()+" is not supported by SpagoBIStudio 2.5");
			return;
		}
		logger.debug("Creating the editor for dashboard");
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 10;
		layout.topMargin = 20;
		layout.leftMargin = 20;
		form.getBody().setLayout(layout);

		// Dashboard general information section
		Section section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("Dashboard information");
		section.setDescription("Below you see the Dashboard type and associated movie:");
		Composite sectionClient = toolkit.createComposite(section);
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		sectionClient.setLayout(gl);
		Label typeLabel = new Label(sectionClient, SWT.NULL);
		typeLabel.setText("Type:");
		Label type = new Label(sectionClient, SWT.NULL);

		type.setText(model.getType());


		Label movieLabel = new Label(sectionClient, SWT.NULL);
		movieLabel.setText("Movie:");
		Label movie = new Label(sectionClient, SWT.NULL);
		movie.setText(model.getMovie());
		section.setClient(sectionClient);

		// Dashboard internal settings section
		section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.rowspan = 3;
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("Dashboard internal settings");
		section.setDescription("Below you see the Dashboard internal settings:");
		sectionClient = toolkit.createComposite(section);
		model.getConfiguration().createForm(this, sectionClient, toolkit);
		section.setClient(sectionClient);

		// Dashboard dimension settings section
		section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("Dashboard dimension");
		section.setDescription("Below you see the Dashboard dimension settings:");
		sectionClient = toolkit.createComposite(section);
		gl = new GridLayout();
		gl.numColumns = 2;
		sectionClient.setLayout(gl);

		/*no more display title bar, linked to old portlet mode

				final Button button = toolkit.createButton(sectionClient, "Display title bar", SWT.CHECK);
		button.setSelection(model.isDisplayTitleBar());
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setIsDirty(true);
				model.setDisplayTitleBar(button.getSelection());
			}

		});
		button.setVisible(false);		
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		button.setLayoutData(gd);
		 */

		Label widthLabel = new Label(sectionClient, SWT.NULL);
		widthLabel.setText("Width (in pixel):");
		final Text widthText = new Text(sectionClient, SWT.BORDER);
		widthText.setText(new Integer(model.getDimension().getWidth()).toString());
		widthText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				setIsDirty(true);
				String widthStr = widthText.getText();
				model.getDimension().setWidth(Integer.parseInt(widthStr));
			}
		});
		Label heightLabel = new Label(sectionClient, SWT.NULL);
		heightLabel.setText("Height (in pixel):");
		final Text heightText = new Text(sectionClient, SWT.BORDER);
		heightText.setText(new Integer(model.getDimension().getHeight()).toString());
		heightText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				setIsDirty(true);
				String heightStr = heightText.getText();
				model.getDimension().setHeight(Integer.parseInt(heightStr));
			}
		});
		section.setClient(sectionClient);

		// Dashboard source configuration section
		/*	section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("Dashboard source configuration");
		section.setDescription("Below you see the Dashboard source configuration:");
		sectionClient = toolkit.createComposite(section);
		gl = new GridLayout();
		gl.numColumns = 2;
		sectionClient.setLayout(gl);*/	


		Parameter[] dataParameters = model.getData().getParameters();
		for (int i = 0; i < dataParameters.length; i++) {
			final Parameter aParameter = dataParameters[i];
			Label parameterDescriptionLabel = new Label(sectionClient, SWT.NULL);
			parameterDescriptionLabel.setText(aParameter.getDescription() + ":");
			int parameterType = aParameter.getType();
			switch (parameterType) {
			case Parameter.COLOR_TYPE:
				// TODO show an error if there is a color parameter
				break;
			default:
				final Text parameterValueText = new Text(sectionClient, SWT.BORDER);
				parameterValueText.setText(aParameter.getValue());
				parameterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				parameterValueText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						setIsDirty(true);
						String parameterValueStr = parameterValueText.getText();
						aParameter.setValue(parameterValueStr);
					}
				});
			}
		}
		section.setClient(sectionClient);

	}

	public void setFocus() {
	}

	public void setIsDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	//	public static RGB convertHexadecimalToRGB(String hexadecimal) {
	//	
	//		
	//		
	//		String redHexadecimal = hexadecimal.substring(2, 4);
	//	    String greenHexadecimal = hexadecimal.substring(4, 6);
	//	    String blueHexadecimal = hexadecimal.substring(6, 8);
	//	    int red = Integer.parseInt(redHexadecimal, 16);
	//	    int green = Integer.parseInt(greenHexadecimal, 16);
	//	    int blue = Integer.parseInt(blueHexadecimal, 16);
	//	    return new RGB(red, green, blue);
	//
	//	
	//	}




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
		return "0x" + redHexadecimal + greenHexadecimal + blueHexadecimal;
	}

	public DashboardModel getModel() {
		return model;
	}

}
