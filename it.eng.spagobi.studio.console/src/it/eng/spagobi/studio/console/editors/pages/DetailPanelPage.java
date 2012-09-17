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

import it.eng.spagobi.studio.console.editors.ConsoleEditor;
import it.eng.spagobi.studio.console.model.bo.ConsoleTemplateModel;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class DetailPanelPage extends AbstractPage {
	private ConsoleEditor editor;
	private String projectName;
	private ConsoleTemplateModel consoleTemplateModel;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Table table;
	/**
	 * @param parent
	 * @param style
	 */
	public DetailPanelPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group grpPageDetail = new Group(mainComposite, SWT.NONE);
		grpPageDetail.setText("Page Detail");
		grpPageDetail.setLayout(new GridLayout(1, false));
		grpPageDetail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpNavigationBar = new Group(grpPageDetail, SWT.NONE);
		grpNavigationBar.setText("Navigation Bar");
		grpNavigationBar.setLayout(new GridLayout(1, false));
		grpNavigationBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Composite composite = new Composite(grpNavigationBar, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		composite.setBounds(0, 0, 64, 64);
		
		Label lblText = new Label(composite, SWT.NONE);
		lblText.setSize(25, 15);
		lblText.setText("Text:");
		
		text = new Text(composite, SWT.BORDER);
		text.setSize(76, 21);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setSize(41, 15);
		lblNewLabel.setText("Tooltip:");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setSize(245, 21);
		
		Label lblLabel = new Label(composite, SWT.NONE);
		lblLabel.setSize(31, 15);
		lblLabel.setText("Label:");
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setSize(76, 21);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(grpNavigationBar, SWT.NONE);
		
		Button btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAdd.setBounds(0, 0, 75, 25);
		btnAdd.setText("Add");
		
		table = new Table(grpNavigationBar, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnText = new TableColumn(table, SWT.NONE);
		tblclmnText.setWidth(100);
		tblclmnText.setText("Text");
		
		TableColumn tblclmnTooltip = new TableColumn(table, SWT.NONE);
		tblclmnTooltip.setWidth(100);
		tblclmnTooltip.setText("Tooltip");
		
		TableColumn tblclmnLabel = new TableColumn(table, SWT.NONE);
		tblclmnLabel.setWidth(100);
		tblclmnLabel.setText("Label");
		// TODO Auto-generated constructor stub
	}
	public void drawPage(){
		
	}
	public ConsoleEditor getEditor() {
		return editor;
	}

	public void setEditor(ConsoleEditor editor) {
		this.editor = editor;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the consoleTemplateModel
	 */
	public ConsoleTemplateModel getConsoleTemplateModel() {
		return consoleTemplateModel;
	}

	/**
	 * @param consoleTemplateModel the consoleTemplateModel to set
	 */
	public void setConsoleTemplateModel(ConsoleTemplateModel consoleTemplateModel) {
		this.consoleTemplateModel = consoleTemplateModel;
	}
}
