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
public class DatasetPage extends AbstractPage {
	private Text textId;
	private Text txtRefreshTime;
	private Text txtRowsLimit;
	private Table table;
	private ConsoleEditor editor;
	private String projectName;
	private ConsoleTemplateModel consoleTemplateModel;


	/**
	 * @param parent
	 * @param style
	 */
	public DatasetPage(Composite parent, int style) {
		super(parent, style);

	}
	
	public void drawPage(){
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Group groupSelection = new Group(mainComposite, SWT.NONE);
		groupSelection.setText("Dataset Selection");
		groupSelection.setLayout(new GridLayout(4, false));
		groupSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblDatasetToVisualize = new Label(groupSelection, SWT.NONE);
		lblDatasetToVisualize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDatasetToVisualize.setText("Dataset to visualize: ");
		
		Combo comboDatasets = new Combo(groupSelection, SWT.READ_ONLY);
		comboDatasets.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(groupSelection, SWT.NONE);
		new Label(groupSelection, SWT.NONE);
		
		Label lblIdToAssign = new Label(groupSelection, SWT.NONE);
		lblIdToAssign.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblIdToAssign.setText("Id to assign:");
		
		textId = new Text(groupSelection, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblRefreshTime = new Label(groupSelection, SWT.NONE);
		lblRefreshTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRefreshTime.setText("Refresh time: ");
		
		txtRefreshTime = new Text(groupSelection, SWT.BORDER);
		txtRefreshTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblMemoryPagination = new Label(groupSelection, SWT.NONE);
		lblMemoryPagination.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMemoryPagination.setText("Memory Pagination:");
		
		Combo comboMemoryPagination = new Combo(groupSelection, SWT.READ_ONLY);
		comboMemoryPagination.setItems(new String[] {"true", "false"});
		comboMemoryPagination.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblRowsLimit = new Label(groupSelection, SWT.NONE);
		lblRowsLimit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRowsLimit.setText("Rows Limit:");
		
		txtRowsLimit = new Text(groupSelection, SWT.BORDER);
		txtRowsLimit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group groupDatasetTable = new Group(mainComposite, SWT.NONE);
		groupDatasetTable.setText("Datasets added");
		groupDatasetTable.setLayout(new GridLayout(1, false));
		groupDatasetTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite compositeButtons = new Composite(groupDatasetTable, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(2, false));
		compositeButtons.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
		
		Button btnAdd = new Button(compositeButtons, SWT.NONE);
		btnAdd.setText("Add");
		
		Button btnRemove = new Button(compositeButtons, SWT.NONE);
		btnRemove.setText("Remove");
		
		table = new Table(groupDatasetTable, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(229);
		tblclmnType.setText("Name");
		
		TableColumn tblclmnType_1 = new TableColumn(table, SWT.NONE);
		tblclmnType_1.setWidth(178);
		tblclmnType_1.setText("Type");		
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
