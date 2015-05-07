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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import it.eng.spagobi.studio.console.editors.internal.MultiLedsSettingDialogTableRow;
import it.eng.spagobi.studio.console.model.bo.Field;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementLiveLine;
import it.eng.spagobi.studio.console.model.bo.WidgetConfigElementMultiLeds;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
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
	
	public static final int COLUMN_NAME = 0;
	public static final int COLUMN_HEADER = 1;
	public static final int COLUMN_RANGE_MAX_VALUE = 2;
	public static final int COLUMN_RANGE_MIN_VALUE = 3;
	public static final int COLUMN_FIRST_INTERVAL = 4;
	public static final int COLUMN_SECOND_INTERVAL = 5;

	
	private List<MultiLedsSettingDialogTableRow> multiLedsSettingDialogTableRows;
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
		multiLedsSettingDialogTableRows = new ArrayList<MultiLedsSettingDialogTableRow>();
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
		btnAddField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkRequiredInput()){
					
					//Add a table Item
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(COLUMN_HEADER, textHeader.getText());
					item.setText(COLUMN_NAME, textName.getText());
					item.setText(COLUMN_RANGE_MAX_VALUE, textRangeMaxValue.getText());
					item.setText(COLUMN_RANGE_MIN_VALUE, textRangeMinValue.getText());
					item.setText(COLUMN_FIRST_INTERVAL, textFirstIntervalUb.getText());
					item.setText(COLUMN_SECOND_INTERVAL, textSecondIntervalUb.getText());
					
					//Add a corresponding object in the internal model
					String header = textHeader.getText();
					String name = textName.getText();
					int rangeMaxValue = Integer.parseInt(textRangeMaxValue.getText());
					int rangeMinValue = Integer.parseInt(textRangeMinValue.getText());
					int firstIntervalUb = Integer.parseInt(textFirstIntervalUb.getText());
					int secondIntervalUb = Integer.parseInt(textSecondIntervalUb.getText());
					MultiLedsSettingDialogTableRow newRow = new MultiLedsSettingDialogTableRow(header,name,rangeMaxValue,rangeMinValue,firstIntervalUb,secondIntervalUb);					
					multiLedsSettingDialogTableRows.add(newRow);
					
					//clear input UI
					clearInputUI();
					
				}
				
			}
		});
		btnAddField.setText("Add Field");
		
		Button btnRemoveField = new Button(compositeButtons, SWT.NONE);
		btnRemoveField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() != -1){
					//remove from the internal model
					multiLedsSettingDialogTableRows.remove(table.getSelectionIndex());
					//remove from the Table UI
					table.remove(table.getSelectionIndex());

				}
			}
		});
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
		
		//---------------------------------------------------
		//check if existing Widget is found and populate UI
		if (widgetConfigElementMultiLeds != null){
			populateUI();
		}
		//---------------------------------------------------	

		return container;
	}
	
	public void populateUI(){
		Vector<Field> fields = widgetConfigElementMultiLeds.getFields();
		if (!fields.isEmpty()){
			for (Field field:fields){
				createTableItem(field.getHeader(),
						field.getName(),
						String.valueOf(field.getRangeMaxValue()),
						String.valueOf(field.getRangeMinValue()),
						String.valueOf(field.getFirstIntervalUb()),
						String.valueOf(field.getSecondIntervalUb()));
			}
		}
	}
	
	public void createTableItem(String header,String name,String rangeMaxValue,String rangeMinValue,String firstIntervalUb,String secondIntervalUb){
		//Add a table Item
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(COLUMN_HEADER, header);
		item.setText(COLUMN_NAME, name);
		item.setText(COLUMN_RANGE_MAX_VALUE, rangeMaxValue);
		item.setText(COLUMN_RANGE_MIN_VALUE, rangeMinValue);
		item.setText(COLUMN_FIRST_INTERVAL, firstIntervalUb);
		item.setText(COLUMN_SECOND_INTERVAL, secondIntervalUb);
		
		//Add a corresponding object in the internal model

		MultiLedsSettingDialogTableRow newRow = new MultiLedsSettingDialogTableRow(header,name,Integer.valueOf(rangeMaxValue),Integer.valueOf(rangeMinValue),Integer.valueOf(firstIntervalUb),Integer.valueOf(secondIntervalUb));					
		multiLedsSettingDialogTableRows.add(newRow);
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
		return new Point(600, 354);
	}

	@Override
	protected void okPressed() {
		if (isValidInput()) {
			createWidgetConfigElementMultiLeds();
			super.okPressed();
		} else {
			MessageDialog.openWarning(new Shell(), "Warning", "Please insert at least one Field "); 
		}

	}
	
	//create a WidgetConfigElementMultiLeds object when OK is pressed
	public void createWidgetConfigElementMultiLeds() {
		widgetConfigElementMultiLeds = new WidgetConfigElementMultiLeds();
		Vector<Field> fields = widgetConfigElementMultiLeds.getFields();
		for (MultiLedsSettingDialogTableRow element:multiLedsSettingDialogTableRows){
			Field newField = new Field();
			newField.setHeader(element.getHeader());
			newField.setName(element.getName());
			newField.setRangeMaxValue(element.getRangeMaxValue());
			newField.setRangeMinValue(element.getRangeMinValue());
			newField.setFirstIntervalUb(element.getFirstIntervalUb());
			newField.setSecondIntervalUb(element.getSecondIntervalUb());
			fields.add(newField);
		}
		
		
	}
	
	private boolean checkRequiredInput(){
		
		boolean valid = true;
		if (textHeader.getText().length() == 0) {
			valid = false;
		}
		if (textName.getText().length() == 0) {
			valid = false;
		}
		if (textRangeMaxValue.getText().length() == 0) {
			valid = false;
		}
		if (textRangeMinValue.getText().length() == 0) {
			valid = false;
		}		    
		if (textFirstIntervalUb.getText().length() == 0) {
			valid = false;
		}
		if (textSecondIntervalUb.getText().length() == 0) {
			valid = false;
		}
	
		return valid;
	}
	
	public void clearInputUI(){
		textHeader.clearSelection();
		textHeader.setText("");
		textName.clearSelection();
		textName.setText("");
		textRangeMaxValue.clearSelection();
		textRangeMaxValue.setText("");		
		textRangeMinValue.clearSelection();
		textRangeMinValue.setText("");
		textFirstIntervalUb.clearSelection();
		textFirstIntervalUb.setText("");	
		textSecondIntervalUb.clearSelection();	
		textSecondIntervalUb.setText("");		
	}
	
	//check if at least one Field is found
	private boolean isValidInput() {
		if (!multiLedsSettingDialogTableRows.isEmpty()){
			return true;
		} else {
			return false;
		}
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
