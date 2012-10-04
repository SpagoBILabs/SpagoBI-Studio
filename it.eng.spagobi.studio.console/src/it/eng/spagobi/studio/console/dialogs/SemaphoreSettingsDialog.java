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
package it.eng.spagobi.studio.console.dialogs;

import java.util.StringTokenizer;
import java.util.Vector;

import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementLiveLine;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementSemaphore;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementSpeedometer;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class SemaphoreSettingsDialog extends Dialog {
	private Text textParamWidth;
	private Text textParamHeight;
	private Text textMinValue;
	private Text textMaxValue;

	private  WidgetConfigElementSemaphore widgetConfigElementSemaphore;
	private Text textFirstInterval;
	private Text textSecondInterval;
	private Text textField;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SemaphoreSettingsDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		
		Composite compositeMain = new Composite(container, SWT.NONE);
		compositeMain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeMain.setLayout(new GridLayout(4, false));
		
		Label lblParamWidth = new Label(compositeMain, SWT.NONE);
		lblParamWidth.setSize(83, 15);
		lblParamWidth.setText("paramWidth:");
		
		textParamWidth = new Text(compositeMain, SWT.BORDER);
		textParamWidth.setSize(76, 21);
		
		Label lblParamHeight = new Label(compositeMain, SWT.NONE);
		lblParamHeight.setSize(84, 15);
		lblParamHeight.setText("paramHeight:");
		
		textParamHeight = new Text(compositeMain, SWT.BORDER);
		textParamHeight.setSize(76, 21);
		
		Label lblRangeMinValue = new Label(compositeMain, SWT.NONE);
		lblRangeMinValue.setSize(32, 15);
		lblRangeMinValue.setText("rangeMinValue:");
		
		textMinValue = new Text(compositeMain, SWT.BORDER);
		textMinValue.setSize(76, 21);
		
		Label lblRangeMaxValue = new Label(compositeMain, SWT.NONE);
		lblRangeMaxValue.setSize(117, 15);
		lblRangeMaxValue.setText("rangeMaxValue:");
		
		textMaxValue = new Text(compositeMain, SWT.BORDER);
		textMaxValue.setSize(76, 21);
		
		Label lblRangeFirstInterval = new Label(compositeMain, SWT.NONE);
		lblRangeFirstInterval.setText("rangeFirstInterval:");
		
		textFirstInterval = new Text(compositeMain, SWT.BORDER);
		
		Label lblRangeSecondInterval = new Label(compositeMain, SWT.NONE);
		lblRangeSecondInterval.setText("rangeSecondInterval:");
		
		textSecondInterval = new Text(compositeMain, SWT.BORDER);
		
		Label lblField = new Label(compositeMain, SWT.NONE);
		lblField.setText("Field:");
		
		textField = new Text(compositeMain, SWT.BORDER);
		new Label(compositeMain, SWT.NONE);
		new Label(compositeMain, SWT.NONE);
		
		//---------------------------------------------------
		//check if existing Widget is found and populate UI
		if (widgetConfigElementSemaphore != null){
			populateUI();
		}
		//---------------------------------------------------	

		return container;
	}
	
	public void populateUI(){
		textParamWidth.setText(String.valueOf(widgetConfigElementSemaphore.getParamWidth()));
		textParamHeight.setText(String.valueOf(widgetConfigElementSemaphore.getParamHeight()));
		textMinValue.setText(String.valueOf(widgetConfigElementSemaphore.getRangeMinValue()));
		textMaxValue.setText(String.valueOf(widgetConfigElementSemaphore.getRangeMaxValue()));
		textFirstInterval.setText(String.valueOf(widgetConfigElementSemaphore.getRangeFirstInterval()));
		textSecondInterval.setText(String.valueOf(widgetConfigElementSemaphore.getRangeSecondInterval()));
		textField.setText(widgetConfigElementSemaphore.getField());

	}
	


	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button buttonOK = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected void okPressed() {
		//first check the inputs
		if (isValidInput()) {
			createWidgetConfigElementSemaphore();
			super.okPressed();				
		} else {
			MessageDialog.openWarning(new Shell(), "Warning", "Please insert all the required values"); 
		}

	}
	
	//create a WidgetConfigElementSemaphore object when OK is pressed
	public void createWidgetConfigElementSemaphore() {
		widgetConfigElementSemaphore = new WidgetConfigElementSemaphore();
		widgetConfigElementSemaphore.setParamWidth(Integer.parseInt(textParamWidth.getText()));
		widgetConfigElementSemaphore.setParamHeight(Integer.parseInt(textParamHeight.getText()));
		widgetConfigElementSemaphore.setRangeMinValue(Integer.parseInt(textMinValue.getText()));
		widgetConfigElementSemaphore.setRangeMaxValue(Integer.parseInt(textMaxValue.getText()));
		widgetConfigElementSemaphore.setRangeFirstInterval(Integer.parseInt(textFirstInterval.getText()));
		widgetConfigElementSemaphore.setRangeSecondInterval(Integer.parseInt(textSecondInterval.getText()));
		widgetConfigElementSemaphore.setField(textField.getText());

	}
	
	//check if all the required input are inserted
	private boolean isValidInput() {
		boolean valid = true;
		if (textParamWidth.getText().length() == 0) {
			valid = false;
		}
		if (textParamHeight.getText().length() == 0) {
			valid = false;
		}
		if (textMinValue.getText().length() == 0) {
			valid = false;
		}
		if (textMaxValue.getText().length() == 0) {
			valid = false;
		}		    
		if (textFirstInterval.getText().length() == 0) {
			valid = false;
		}
		if (textSecondInterval.getText().length() == 0) {
			valid = false;
		}
		if (textField.getText().length() == 0) {
			valid = false;
		}		
		return valid;
	}

	/**
	 * @return the widgetConfigElementSemaphore
	 */
	public WidgetConfigElementSemaphore getWidgetConfigElementSemaphore() {
		return widgetConfigElementSemaphore;
	}

	/**
	 * @param widgetConfigElementSemaphore the widgetConfigElementSemaphore to set
	 */
	public void setWidgetConfigElementSemaphore(
			WidgetConfigElementSemaphore widgetConfigElementSemaphore) {
		this.widgetConfigElementSemaphore = widgetConfigElementSemaphore;
	}


}
