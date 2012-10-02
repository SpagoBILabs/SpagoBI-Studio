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
public class SpeedometerSettingsDialog extends Dialog {
	private Text textParamWidth;
	private Text textParamHeight;
	private Text textMinValue;
	private Text textMaxValue;

	private  WidgetConfigElementSpeedometer widgetConfigElementSpeedometer;
	private Text textLowValue;
	private Text textHighValue;
	private Text textField;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SpeedometerSettingsDialog(Shell parentShell) {
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
		
		Label lblMinValue = new Label(compositeMain, SWT.NONE);
		lblMinValue.setSize(32, 15);
		lblMinValue.setText("minValue:");
		
		textMinValue = new Text(compositeMain, SWT.BORDER);
		textMinValue.setSize(76, 21);
		
		Label lblmaxValue = new Label(compositeMain, SWT.NONE);
		lblmaxValue.setSize(117, 15);
		lblmaxValue.setText("maxValue:");
		
		textMaxValue = new Text(compositeMain, SWT.BORDER);
		textMaxValue.setSize(76, 21);
		
		Label lblLowvalue = new Label(compositeMain, SWT.NONE);
		lblLowvalue.setText("lowValue:");
		
		textLowValue = new Text(compositeMain, SWT.BORDER);
		
		Label lblHighvalue = new Label(compositeMain, SWT.NONE);
		lblHighvalue.setText("highValue:");
		
		textHighValue = new Text(compositeMain, SWT.BORDER);
		
		Label lblField = new Label(compositeMain, SWT.NONE);
		lblField.setText("Field:");
		
		textField = new Text(compositeMain, SWT.BORDER);
		new Label(compositeMain, SWT.NONE);
		new Label(compositeMain, SWT.NONE);

		return container;
	}
	


	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button buttonOK = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		buttonOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//first check the inputs
				if (isValidInput()) {
			          okPressed();					
				} else {
					MessageDialog.openWarning(new Shell(), "Warning", "Please insert all the required values"); 
				}
			}
		});
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
		createWidgetConfigElementSpeedometer();
		super.okPressed();
	}
	
	//create a WidgetConfigElementLiveLine object when OK is pressed
	public void createWidgetConfigElementSpeedometer() {
		widgetConfigElementSpeedometer = new WidgetConfigElementSpeedometer();
		widgetConfigElementSpeedometer.setParamWidth(Integer.parseInt(textParamWidth.getText()));
		widgetConfigElementSpeedometer.setParamHeight(Integer.parseInt(textParamHeight.getText()));
		widgetConfigElementSpeedometer.setMinValue(Integer.parseInt(textMinValue.getText()));
		widgetConfigElementSpeedometer.setMaxValue(Integer.parseInt(textMaxValue.getText()));
		widgetConfigElementSpeedometer.setLowValue(Integer.parseInt(textLowValue.getText()));
		widgetConfigElementSpeedometer.setHighValue(Integer.parseInt(textHighValue.getText()));
		widgetConfigElementSpeedometer.setField(textField.getText());
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
		if (textLowValue.getText().length() == 0) {
			valid = false;
		}
		if (textHighValue.getText().length() == 0) {
			valid = false;
		}
		if (textField.getText().length() == 0) {
			valid = false;
		}		
		return valid;
	}

	/**
	 * @return the widgetConfigElementSpeedometer
	 */
	public WidgetConfigElementSpeedometer getWidgetConfigElementSpeedometer() {
		return widgetConfigElementSpeedometer;
	}

	/**
	 * @param widgetConfigElementSpeedometer the widgetConfigElementSpeedometer to set
	 */
	public void setWidgetConfigElementSpeedometer(
			WidgetConfigElementSpeedometer widgetConfigElementSpeedometer) {
		this.widgetConfigElementSpeedometer = widgetConfigElementSpeedometer;
	}


}
