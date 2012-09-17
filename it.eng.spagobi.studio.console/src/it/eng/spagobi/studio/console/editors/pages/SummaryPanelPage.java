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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class SummaryPanelPage extends AbstractPage {
	private ConsoleEditor editor;
	private String projectName;
	private ConsoleTemplateModel consoleTemplateModel;
	private Text textHeight;
	private Table tableWidgets;
	
	
	/**
	 * @param parent
	 * @param style
	 */
	public SummaryPanelPage(Composite parent, int style) {
		super(parent, style);

	}
	public void drawPage(){
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group grpLayoutProperties = new Group(mainComposite, SWT.NONE);
		grpLayoutProperties.setText("Layout Properties");
		grpLayoutProperties.setLayout(new GridLayout(4, false));
		grpLayoutProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblCollapsed = new Label(grpLayoutProperties, SWT.NONE);
		lblCollapsed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCollapsed.setText("Collapsed:");
		
		Combo comboCollapsed = new Combo(grpLayoutProperties, SWT.READ_ONLY);
		comboCollapsed.setItems(new String[] {"true", "false"});
		comboCollapsed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblCollasable = new Label(grpLayoutProperties, SWT.NONE);
		lblCollasable.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCollasable.setText("Collasable:");
		
		Combo comboCollasable = new Combo(grpLayoutProperties, SWT.READ_ONLY);
		comboCollasable.setItems(new String[] {"true", "false"});
		comboCollasable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblHidden = new Label(grpLayoutProperties, SWT.NONE);
		lblHidden.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHidden.setText("Hidden:");
		
		Combo comboHidden = new Combo(grpLayoutProperties, SWT.READ_ONLY);
		comboHidden.setItems(new String[] {"true", "false"});
		comboHidden.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblHeight = new Label(grpLayoutProperties, SWT.NONE);
		lblHeight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHeight.setText("Height:");
		
		textHeight = new Text(grpLayoutProperties, SWT.BORDER);
		textHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
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
