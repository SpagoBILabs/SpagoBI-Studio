/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.model.bo.Drill;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.Param;
import it.eng.spagobi.studio.highchart.utils.ImageDescriptors;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrillSection extends AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(DrillSection.class);

	Text documentText;

	Button insertButton;
	Button saveButton;
	Button deleteButton;

	Text nameText;
	Combo categoryCombo;
	Text valueText;

	Table parsTable;

	private static final String ABSOLUTE = "Absolute";

	public DrillSection(HighChart highChart) {
		super(highChart);
	}

	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);
		logger.debug("IN");

		section.setText("Drill");
		section.setDescription("Select drill options");

		Drill drill = highChart.getDrill();

		section.setClient(composite);

		documentText = SWTUtils.drawText(toolkit, composite, drill.getDocument(), "Document label:");
		documentText.setLayoutData(SWTUtils.getGridDataSpan(1, GridData.FILL_HORIZONTAL));
		documentText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				editor.setIsDirty(true);
				String value = documentText.getText();
				highChart.getDrill().setDocument(value);
			}
		});
		
		
		// Parameters
		Composite paramComposite = toolkit.createComposite(composite);
		GridLayout gla = new GridLayout();
		gla.numColumns = 6;
		paramComposite.setLayout(gla);
		paramComposite.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		// three buttons
		insertButton = toolkit.createButton(paramComposite, "", SWT.PUSH);
		Image insertImage = ImageDescriptors.getAddIcon().createImage();
		insertButton.setImage(insertImage);
		insertButton.setEnabled(true);
		saveButton = toolkit.createButton(paramComposite, "", SWT.PUSH);		
		Image saveImage = ImageDescriptors.getSaveIcon().createImage();
		saveButton.setImage(saveImage);
		saveButton.setEnabled(false);
		deleteButton = toolkit.createButton(paramComposite, "", SWT.PUSH);
		Image deleteImage = ImageDescriptors.getEraseIcon().createImage();
		deleteButton.setImage(deleteImage);
		deleteButton.setEnabled(false);

		// the table		
		parsTable = new Table (paramComposite, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		parsTable.setLinesVisible (true);
		parsTable.setHeaderVisible (true);
		GridData g=new GridData(GridData.FILL_BOTH);
		g.horizontalSpan=3;
		g.verticalSpan=4;
		g.grabExcessHorizontalSpace=true;
		g.grabExcessVerticalSpace=true;
		g.heightHint = 200;
		g.widthHint = 400;
		parsTable.setLayoutData(g);

		String[] titles = {"             Name             ", "           Type           ", "               Value               "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (parsTable, SWT.NONE);
			column.setText (titles [i]);
		}
		if(drill.getParamList().getParams()!=null){
			Vector<Param> params = drill.getParamList().getParams();

			for (Iterator iterator = params.iterator(); iterator.hasNext();) {
				Param param = (Param) iterator.next();
				TableItem item = new TableItem (parsTable, SWT.NONE);
				if(param.getName()!=null)
					item.setText(0, param.getName());
				if(param.getType()!=null)
					item.setText(1, param.getType());
				if(param.getValue()!=null)
					item.setText(2, param.getValue());
			}

			for (int i=0; i<titles.length; i++) {
				parsTable.getColumn (i).pack ();
			}	

		}

		nameText = SWTUtils.drawText(toolkit, paramComposite, "", "Name: ");
		nameText.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		nameText.setEnabled(false);
		nameText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));
		categoryCombo= SWTUtils.drawCombo(paramComposite,new String[]{"", "CATEGORY", "SERIE", "ABSOLUTE", "RELATIVE"}, null, "Category: ");
		categoryCombo.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		categoryCombo.setEnabled(false);
		valueText = SWTUtils.drawText(toolkit, paramComposite, "", "Value: ");
		valueText.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		valueText.setEnabled(false);
		valueText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

		addButtonListeners(drill);

		logger.debug("OUT");

	}


	public void addButtonListeners(final Drill drill){

		insertButton.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				editor.setIsDirty(true);
				// ADD button: 1) remove selection from table 2)disable cancel, enable save, 3) enable fields
				parsTable.setSelection(-1);
				insertButton.setEnabled(false);
				deleteButton.setEnabled(false);
				saveButton.setEnabled(true);
				nameText.setText("");
				nameText.setEnabled(true);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));

				categoryCombo.select(0);
				categoryCombo.setEnabled(true);
				valueText.setText("");
				valueText.setEnabled(false);
				valueText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

			}

		}
		);

		deleteButton.addListener(SWT.Selection, 
				new Listener() {

			public void handleEvent(Event event) {
				editor.setIsDirty(true);
				// delete button: 1) remove item2) remove selection from table 3)disable cancel, disable save, enable insert, 3) disable fields
				int index = parsTable.getSelectionIndex();
				TableItem toremove = parsTable.getItem(index);
				String name = toremove.getText(0);

				Vector<Param> params = drill.getParamList().getParams();
				int indexTRoRemove = searchOnVectorParams(drill, name);
				params.remove(indexTRoRemove);

				parsTable.remove(indexTRoRemove);
				parsTable.setSelection(-1);
				insertButton.setEnabled(true);
				deleteButton.setEnabled(false);
				saveButton.setEnabled(false);
				nameText.setText("");
				nameText.setEnabled(false);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));
				categoryCombo.select(0);
				categoryCombo.setEnabled(false);
				valueText.setText("");
				valueText.setEnabled(false);
				valueText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

			}

		}
		);

		saveButton.addListener(SWT.Selection, 
				new Listener() {

			public void handleEvent(Event event) {
				editor.setIsDirty(true);
				// save button: 1) check name is not existing and category is not empty, 2) check if something selected , 3) save new item and new param or update existing one  

				// check name
				String name = nameText.getText();
				String category = categoryCombo.getItem(categoryCombo.getSelectionIndex());
				String value = valueText.getText();

				// is tu update or insert
				int tableIndex = parsTable.getSelectionIndex();
				Vector<Param> params = drill.getParamList().getParams();
				if(tableIndex == -1){
					//insert: check not already existing
					int indexfound = searchOnVectorParams(drill, name);
					if(indexfound != -1){
						MessageDialog.openWarning(composite.getShell(), "Warning", "Name "+name+" already present.");
						return;
					}
					if(category.equals("")){
						MessageDialog.openWarning(composite.getShell(), "Warning", "Select a category");
						return;						
					}
					//can insert
					Param param = new Param();
					param.setName(name);
					param.setType(category);
					if(valueText.isEnabled()) param.setValue(value);
					drill.getParamList().getParams().add(param);
					TableItem item = new TableItem (parsTable, SWT.NONE);
					item.setText(0, name);					
					item.setText(1, category);					
					if(valueText.isEnabled()) item.setText(2, value);		
					parsTable.redraw();
				}
				else{
					// update
					TableItem selecteditem = parsTable.getItem(parsTable.getSelectionIndex());
					String nameSelected = selecteditem.getText(0);
					int indexParam = searchOnVectorParams(drill, nameSelected);
					Param param = drill.getParamList().getParams().get(indexParam);

					int indexNewParam = searchOnVectorParams(drill, name);
					// check new Name does not exist
					if(indexNewParam != -1 && indexNewParam != indexParam){
						MessageDialog.openWarning(composite.getShell(), "Warning", "Name "+name+" already present.");
						return;
					}
					if(category.equals("")){
						MessageDialog.openWarning(composite.getShell(), "Warning", "Select a category");
						return;						
					}
					param.setName(name);
					param.setType(category);
					if(valueText.isEnabled())param.setValue(value);
					else param.setValue(null);
					selecteditem.setText(0,name);
					selecteditem.setText(1,category);
					if(valueText.isEnabled()) selecteditem.setText(2,value);
					else selecteditem.setText(2,"");
					parsTable.redraw();
				}

				// enable insert, disable delete, disable selection	
				parsTable.setSelection(-1);

				insertButton.setEnabled(true);
				deleteButton.setEnabled(false);
				saveButton.setEnabled(false);
				nameText.setEnabled(false);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

				categoryCombo.select(0);
				categoryCombo.setEnabled(false);
				valueText.setText("");
				valueText.setEnabled(false);
				valueText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));
			}

		}
		);


		// Add listener that show details of parameter selected
		parsTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TableItem tableItem = parsTable.getItem(parsTable.getSelectionIndex());
				String name = tableItem.getText(0);
				String category = tableItem.getText(1);
				String value = tableItem.getText(2);
				nameText.setText(name);
				nameText.setEnabled(true);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));

				categoryCombo.select(categoryCombo.indexOf(category));
				categoryCombo.setEnabled(true);
				if(category.equalsIgnoreCase(ABSOLUTE)){
					valueText.setText(value);
					valueText.setEnabled(true);
					valueText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));
				}
				else {
					valueText.setEnabled(false);
					valueText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));		
				}
				saveButton.setEnabled(true);
				deleteButton.setEnabled(true);
				insertButton.setEnabled(true);
				parsTable.redraw();
			}
		});

		categoryCombo.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event e) {
				String selected = categoryCombo.getItem(categoryCombo.getSelectionIndex());
				if(selected.equals(ABSOLUTE)){
					valueText.setEnabled(true);
					valueText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));		
				}
				else{
					valueText.setEnabled(false);			
					valueText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));		
				}
			}
		}
		);

	}


	public int searchOnVectorParams(Drill drill, String name){
		Vector<Param> params = drill.getParamList().getParams();
		int indexTRoRemove = -1;
		for (Iterator iterator = params.iterator(); iterator.hasNext() && indexTRoRemove == -1;) {
			Param param = (Param) iterator.next();
			if(param.getName().equals(name)){
				indexTRoRemove = params.indexOf(param);
			}
		}
		return indexTRoRemove;
	}

}
