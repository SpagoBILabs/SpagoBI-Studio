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
package it.eng.spagobi.studio.console.editors.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class TestUI extends Composite {
	private Text textHeight;
	private Table tableWidgets;
	private Text text;
	private Text text_1;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TestUI(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group grpLayoutProperties = new Group(mainComposite, SWT.NONE);
		grpLayoutProperties.setText("Layout Properties");
		grpLayoutProperties.setLayout(new GridLayout(2, false));
		grpLayoutProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Composite compositeGeneralProperties = new Composite(grpLayoutProperties, SWT.NONE);
		compositeGeneralProperties.setLayout(new GridLayout(4, false));
		
		Label lblCollapsed = new Label(compositeGeneralProperties, SWT.NONE);
		lblCollapsed.setSize(55, 15);
		lblCollapsed.setText("Collapsed:");
		
		Combo comboCollapsed = new Combo(compositeGeneralProperties, SWT.READ_ONLY);
		comboCollapsed.setSize(53, 23);
		comboCollapsed.setItems(new String[] {"true", "false"});
		
		Label lblCollasable = new Label(compositeGeneralProperties, SWT.NONE);
		lblCollasable.setSize(57, 15);
		lblCollasable.setText("Collasable:");
		
		Combo comboCollasable = new Combo(compositeGeneralProperties, SWT.READ_ONLY);
		comboCollasable.setSize(76, 23);
		comboCollasable.setItems(new String[] {"true", "false"});
		
		Label lblHidden = new Label(compositeGeneralProperties, SWT.NONE);
		lblHidden.setSize(42, 15);
		lblHidden.setText("Hidden:");
		
		Combo comboHidden = new Combo(compositeGeneralProperties, SWT.READ_ONLY);
		comboHidden.setSize(53, 23);
		comboHidden.setItems(new String[] {"true", "false"});
		
		Label lblHeight = new Label(compositeGeneralProperties, SWT.NONE);
		lblHeight.setSize(39, 15);
		lblHeight.setText("Height:");
		
		textHeight = new Text(compositeGeneralProperties, SWT.BORDER);
		textHeight.setSize(76, 21);
		
		Group groupLayoutType = new Group(grpLayoutProperties, SWT.NONE);
		groupLayoutType.setText("Column Layout");
		groupLayoutType.setLayout(new GridLayout(2, false));
		groupLayoutType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblNumOfColumns = new Label(groupLayoutType, SWT.NONE);
		lblNumOfColumns.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNumOfColumns.setText("Num. of columns:");
		
		text = new Text(groupLayoutType, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblColumnsWidths = new Label(groupLayoutType, SWT.NONE);
		lblColumnsWidths.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblColumnsWidths.setText("Columns Widths:");
		
		text_1 = new Text(groupLayoutType, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		new Label(grpLayoutProperties, SWT.NONE);
		
		Group grpWidgets = new Group(mainComposite, SWT.NONE);
		grpWidgets.setText("Widgets");
		grpWidgets.setLayout(new GridLayout(1, false));
		grpWidgets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite composite = new Composite(grpWidgets, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnAddWidget = new Button(composite, SWT.NONE);
		btnAddWidget.setText("Add Widget");
		
		Button btnRemoveWidget = new Button(composite, SWT.NONE);
		btnRemoveWidget.setText("Remove Widget");
		
		Label lblCurrentWidgets = new Label(grpWidgets, SWT.NONE);
		lblCurrentWidgets.setText("Current Widgets");
		
		tableWidgets = new Table(grpWidgets, SWT.BORDER | SWT.FULL_SELECTION);
		tableWidgets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableWidgets.setHeaderVisible(true);
		tableWidgets.setLinesVisible(true);
		
		TableColumn tblclmnWidgetType = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnWidgetType.setWidth(177);
		tblclmnWidgetType.setText("Widget Type");
		
		TableColumn tblclmnDataset = new TableColumn(tableWidgets, SWT.NONE);
		tblclmnDataset.setWidth(156);
		tblclmnDataset.setText("Dataset");		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
