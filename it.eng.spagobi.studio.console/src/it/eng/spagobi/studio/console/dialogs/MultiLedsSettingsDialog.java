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
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementMultiLeds;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class MultiLedsSettingsDialog extends Dialog {
	private Text textHeader;
	private Text textName;
	private Text textRangeMaxValue;
	private Text textRangeMinValue;

	private WidgetConfigElementMultiLeds widgetConfigElementMultiLeds;
	private Text textFirstIntervalUb;
	private Text textSecondIntervalUb;
	private Table table;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public MultiLedsSettingsDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.RESIZE);
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
		
		Label lblHeader = new Label(compositeMain, SWT.NONE);
		lblHeader.setSize(83, 15);
		lblHeader.setText("Header:");
		
		textHeader = new Text(compositeMain, SWT.BORDER);
		textHeader.setSize(76, 21);
		
		Label lblName = new Label(compositeMain, SWT.NONE);
		lblName.setSize(84, 15);
		lblName.setText("Name:");
		
		textName = new Text(compositeMain, SWT.BORDER);
		textName.setSize(76, 21);
		
		Label lblRangeMaxValue = new Label(compositeMain, SWT.NONE);
		lblRangeMaxValue.setSize(32, 15);
		lblRangeMaxValue.setText("rangeMaxValue:");
		
		textRangeMaxValue = new Text(compositeMain, SWT.BORDER);
		textRangeMaxValue.setSize(76, 21);
		
		Label lblRangeMinValue = new Label(compositeMain, SWT.NONE);
		lblRangeMinValue.setSize(117, 15);
		lblRangeMinValue.setText("rangeMinValue:");
		
		textRangeMinValue = new Text(compositeMain, SWT.BORDER);
		textRangeMinValue.setSize(76, 21);
		
		Label lblFirstintervalub = new Label(compositeMain, SWT.NONE);
		lblFirstintervalub.setText("firstIntervalUb:");
		
		textFirstIntervalUb = new Text(compositeMain, SWT.BORDER);
		textFirstIntervalUb.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblSecondintervalub = new Label(compositeMain, SWT.NONE);
		lblSecondintervalub.setText("secondIntervalUb:");
		
		textSecondIntervalUb = new Text(compositeMain, SWT.BORDER);
		textSecondIntervalUb.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Composite compositeButtons = new Composite(container, SWT.NONE);
		compositeButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeButtons.setLayout(new GridLayout(2, false));
		
		Button btnAddField = new Button(compositeButtons, SWT.NONE);
		btnAddField.setText("Add Field");
		
		Button btnRemoveField = new Button(compositeButtons, SWT.NONE);
		btnRemoveField.setText("Remove Field");
		
		Composite compositeValues = new Composite(container, SWT.NONE);
		compositeValues.setLayout(new GridLayout(1, true));
		compositeValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblFields = new Label(compositeValues, SWT.NONE);
		lblFields.setSize(78, 15);
		lblFields.setText("Fields:");
		
		table = new Table(compositeValues, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnHeader = new TableColumn(table, SWT.NONE);
		tblclmnHeader.setWidth(71);
		tblclmnHeader.setText("Header");
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(71);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnRangeMaxValue = new TableColumn(table, SWT.NONE);
		tblclmnRangeMaxValue.setWidth(100);
		tblclmnRangeMaxValue.setText("RangeMaxValue");
		
		TableColumn tblclmnRangeminvalue = new TableColumn(table, SWT.NONE);
		tblclmnRangeminvalue.setWidth(100);
		tblclmnRangeminvalue.setText("RangeMinValue");
		
		TableColumn tblclmnFirstintervalub = new TableColumn(table, SWT.NONE);
		tblclmnFirstintervalub.setWidth(100);
		tblclmnFirstintervalub.setText("FirstIntervalUb");
		
		TableColumn tblclmnSecondintervalub = new TableColumn(table, SWT.NONE);
		tblclmnSecondintervalub.setWidth(110);
		tblclmnSecondintervalub.setText("SecondIntervalUb");

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
		return new Point(600, 354);
	}

	@Override
	protected void okPressed() {
		createWidgetConfigElementMultiLeds();
		super.okPressed();
	}
	
	//create a WidgetConfigElementLiveLine object when OK is pressed
	public void createWidgetConfigElementMultiLeds() {

	}
	
	//check if all the required input are inserted
	private boolean isValidInput() {
		//TODO
		return true;
	}

	/**
	 * @return the widgetConfigElementMultiLeds
	 */
	public WidgetConfigElementMultiLeds getWidgetConfigElementMultiLeds() {
		return widgetConfigElementMultiLeds;
	}

	/**
	 * @param widgetConfigElementMultiLeds the widgetConfigElementMultiLeds to set
	 */
	public void setWidgetConfigElementMultiLeds(
			WidgetConfigElementMultiLeds widgetConfigElementMultiLeds) {
		this.widgetConfigElementMultiLeds = widgetConfigElementMultiLeds;
	}


}
