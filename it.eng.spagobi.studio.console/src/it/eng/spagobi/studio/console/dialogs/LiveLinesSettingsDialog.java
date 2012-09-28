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
public class LiveLinesSettingsDialog extends Dialog {
	private Text textRangeminvalue;
	private Text textRangemaxvalue;
	private Text textStepY;
	private Text textDomainvaluenumber;
	private Text textDomainvalues;
	private Text textFields;

	private  WidgetConfigElementLiveLine widgetConfigElementLiveLine;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LiveLinesSettingsDialog(Shell parentShell) {
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
		
		Label lblRangeminvalue = new Label(compositeMain, SWT.NONE);
		lblRangeminvalue.setSize(83, 15);
		lblRangeminvalue.setText("rangeMinValue:");
		
		textRangeminvalue = new Text(compositeMain, SWT.BORDER);
		textRangeminvalue.setSize(76, 21);
		
		Label lblRangemaxvalue = new Label(compositeMain, SWT.NONE);
		lblRangemaxvalue.setSize(84, 15);
		lblRangemaxvalue.setText("rangeMaxValue:");
		
		textRangemaxvalue = new Text(compositeMain, SWT.BORDER);
		textRangemaxvalue.setSize(76, 21);
		
		Label lblStepY = new Label(compositeMain, SWT.NONE);
		lblStepY.setSize(32, 15);
		lblStepY.setText("stepY:");
		
		textStepY = new Text(compositeMain, SWT.BORDER);
		textStepY.setSize(76, 21);
		
		Label lblDomainvaluenumber = new Label(compositeMain, SWT.NONE);
		lblDomainvaluenumber.setSize(117, 15);
		lblDomainvaluenumber.setText("domainValueNumber:");
		
		textDomainvaluenumber = new Text(compositeMain, SWT.BORDER);
		textDomainvaluenumber.setSize(76, 21);
		
		Composite compositeValues = new Composite(container, SWT.NONE);
		compositeValues.setLayout(new GridLayout(2, true));
		compositeValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblDomainvalues = new Label(compositeValues, SWT.NONE);
		lblDomainvalues.setSize(78, 15);
		lblDomainvalues.setText("domainValues:");
		
		Label lblFields = new Label(compositeValues, SWT.NONE);
		lblFields.setText("fields:");
		
		textDomainvalues = new Text(compositeValues, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		textDomainvalues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		textDomainvalues.setSize(76, 21);
		
		textFields = new Text(compositeValues, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		textFields.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

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
		createWidgetConfigElementLiveLine();
		super.okPressed();
	}
	
	//create a WidgetConfigElementLiveLine object when OK is pressed
	public void createWidgetConfigElementLiveLine() {
		widgetConfigElementLiveLine = new WidgetConfigElementLiveLine();
		widgetConfigElementLiveLine.setRangeMinValue(Integer.parseInt(textRangeminvalue.getText()));
		widgetConfigElementLiveLine.setRangeMaxValue(Integer.parseInt(textRangemaxvalue.getText()));
		widgetConfigElementLiveLine.setStepY(Integer.parseInt(textStepY.getText()));
		widgetConfigElementLiveLine.setDomainValueNumber(Integer.parseInt(textDomainvaluenumber.getText()));

		Vector<Integer> domainValues = widgetConfigElementLiveLine.getDomainValues();
		StringTokenizer st = new StringTokenizer(textDomainvalues.getText(),",");
		while (st.hasMoreElements()) {
			String token = st.nextToken();
			domainValues.add(Integer.parseInt(token));
		}
		
		Vector<String> fields = widgetConfigElementLiveLine.getFields();
		StringTokenizer stFields = new StringTokenizer(textFields.getText(),",");
		while (stFields.hasMoreElements()) {
			String token = stFields.nextToken();
			fields.add(token);
		}
	}
	
	//check if all the required input are inserted
	private boolean isValidInput() {
		boolean valid = true;
		if (textRangeminvalue.getText().length() == 0) {
			valid = false;
		}
		if (textRangemaxvalue.getText().length() == 0) {
			valid = false;
		}
		if (textStepY.getText().length() == 0) {
			valid = false;
		}
		if (textDomainvaluenumber.getText().length() == 0) {
			valid = false;
		}		    
		if (textFields.getText().length() == 0) {
			valid = false;
		}
		return valid;
	}

	/**
	 * @return the widgetConfigElementLiveLine
	 */
	public WidgetConfigElementLiveLine getWidgetConfigElementLiveLine() {
		return widgetConfigElementLiveLine;
	}

	/**
	 * @param widgetConfigElementLiveLine the widgetConfigElementLiveLine to set
	 */
	public void setWidgetConfigElementLiveLine(
			WidgetConfigElementLiveLine widgetConfigElementLiveLine) {
		this.widgetConfigElementLiveLine = widgetConfigElementLiveLine;
	}
}
