/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors;

import it.eng.spagobi.studio.geo.Activator;
import it.eng.spagobi.studio.geo.editors.model.bo.GuiSettingsBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LabelBO;
import it.eng.spagobi.studio.geo.editors.model.geo.Format;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiSettings;
import it.eng.spagobi.studio.geo.editors.model.geo.Label;
import it.eng.spagobi.studio.geo.editors.model.geo.Labels;
import it.eng.spagobi.studio.geo.editors.model.geo.Param;

import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class GuiSettingsLabelDesigner {

	private GEOEditor editor=null;
	private Composite mainComposite;
	private GEODocument geoDocument;
	
	private GuiSettings guiSettings;
	
	public GuiSettings getGuiSettings() {
		return guiSettings;
	}

	public void setGuiSettings(GuiSettings guiSettings) {
		this.guiSettings = guiSettings;
	}

	final ImageDescriptor addIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/add.gif");
	
	final ImageDescriptor paramsIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/linkParams.gif");
	
	final ImageDescriptor eraseIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/erase.gif");
	
	final ImageDescriptor detailIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/detail.gif");
	
	public GuiSettingsLabelDesigner(Composite _composite, GEOEditor _editor, GEODocument _geoDocument) {
		super();
		mainComposite= _composite;
		editor = _editor;
		geoDocument = _geoDocument;
	}
	
	
	public void createGuiSettingsLabels(final Composite sectionClient, FormToolkit toolkit, ScrolledForm form){
		
		// look up for guisettings stored in geodocument
		guiSettings = GuiSettingsBO.getGuiSettings(geoDocument);
		createLabelsGroup(toolkit, form);	
		sectionClient.redraw();
	}
	private void createLabelsGroup(final FormToolkit toolkit, final ScrolledForm form){
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan =4;
		mainComposite.setLayoutData(gd);

		final Button add = toolkit.createButton(mainComposite, "Add", SWT.PUSH);
		add.setSize(addIcon.createImage().getBounds().width, addIcon.createImage().getBounds().height);
		add.setImage(addIcon.createImage());
		
		add.setToolTipText("Add label");
		add.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				final Group guiGroup = new Group(mainComposite, SWT.FILL);
				createLabelGroup(toolkit, guiGroup, null);
				guiGroup.forceFocus();
				guiGroup.layout();
				guiGroup.redraw();
				mainComposite.redraw();
				form.reflow(true);
				((Section)(mainComposite.getParent())).redraw();
				
			}
		});
		add.setLayoutData(gd);
		if (guiSettings != null && guiSettings.getLabels() != null && guiSettings.getLabels().getLabel() != null) {		
			Labels labels = guiSettings.getLabels();
			if(labels != null && labels.getLabel() != null){
				Vector<Label> labelV= labels.getLabel();
				for(int i=0; i< labelV.size(); i++){
					if(labelV.elementAt(i).getPosition() != null){
						final Group guiGroup = new Group(mainComposite, SWT.FILL);
						createLabelGroup(toolkit, guiGroup, labelV.elementAt(i));	
					}
									
				}				
			}
		}else{
			final Group guiGroup = new Group(mainComposite, SWT.FILL);
			createLabelGroup(toolkit, guiGroup, null);
		}
		
	}	
	private void createLabelGroup(FormToolkit toolkit,final Group guiGroup, Label label){
		if(label == null){
			label = LabelBO.setNewLabel(geoDocument);
		}
		Format format = label.getFormat();
		if(format == null){
			format = new Format();
			label.setFormat(format);
		}
		RowLayout fillLayout = new RowLayout();
		fillLayout.type = SWT.VERTICAL;
		fillLayout.wrap=true;
		
		guiGroup.setLayout(fillLayout);
		final Label[] theLabel = {label};
		
		final Button deleteLabel =toolkit.createButton(guiGroup, "Delete", SWT.PUSH);
		deleteLabel.setSize(eraseIcon.createImage().getBounds().width, eraseIcon.createImage().getBounds().height);
		deleteLabel.setImage(eraseIcon.createImage());
		
		deleteLabel.setToolTipText("Delete label");
		
		deleteLabel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				
				Labels labels =guiSettings.getLabels();
				labels.getLabel().remove(theLabel[0]);
				
				guiGroup.dispose();
				mainComposite.pack();
				mainComposite.redraw();
				//form.reflow(true);
				((Section)(mainComposite.getParent())).redraw();
				
			}
		});
		//flag position for this label
		
		createPositionCheck(toolkit, guiGroup, label);
		createText(toolkit, guiGroup, label);
		createFormatInput(toolkit, guiGroup, label);
		createParamsGroup(toolkit, guiGroup, label);
	}
	private void createParamsGroup(FormToolkit toolkit, Group guiGroup, final Label label){	
		Composite comp = toolkit.createComposite(guiGroup, SWT.NONE);
		RowLayout rl = new RowLayout();
		rl.fill=true;
		rl.justify=true;
		rl.spacing=5;
		rl.marginLeft=5;
		rl.marginTop=5;
		comp.setLayout(rl);
		
		final Table guiTable = toolkit.createTable(comp, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		guiTable.setLayout(guiGroup.getLayout());
		guiTable.setLinesVisible(true);
		guiTable.setHeaderVisible(true);

		String[] titles = { "Parameter Name", "Value"};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(guiTable, SWT.NONE);
			column.setText(titles[i]);

		}
		if (label != null) {		
			Vector<GuiParam> params = label.getParams();
			if(params != null){
				for(int i=0; i< params.size(); i++){
					GuiParam param = params.elementAt(i);
					TableItem item = new TableItem(guiTable, SWT.TRANSPARENT);
					createGUIRow(item, guiTable, param);
				}

			}
		}
		for (int i = 0; i < titles.length; i++) {
			guiTable.getColumn(i).pack();
		}
		 //rightClick --> menu
		guiTable.addListener(SWT.MouseDown, new Listener () {
            public void handleEvent (Event event) {            	
            	if (event.button==3){	
            		createMenu(guiTable, label);	            	            	
            	}
            }
        });
		guiTable.redraw();
		//form to add parameters
		createInsertParamForm(label, toolkit, comp, guiTable);

	}
	private void createInsertParamForm(final Label label, FormToolkit toolkit, Composite group, final Table table){
		
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 5;
		formLayout.marginHeight = 5;
		formLayout.spacing = 5;		
				
		Composite formComp = toolkit.createComposite(group, SWT.NONE);
		formComp.setLayout (formLayout);
		
		org.eclipse.swt.widgets.Label name = new org.eclipse.swt.widgets.Label (formComp, SWT.RIGHT);
		name.setText ("Name:");
		FormData data = new FormData ();
		data.width = 40;
		name.setLayoutData (data);

		final Text text = new Text(formComp, SWT.BORDER);
		
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {			


			}
		});
		data = new FormData ();
		data.width = 80;
		data.left = new FormAttachment (name, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		text.setLayoutData (data);
		formComp.setData(text.getText());
		org.eclipse.swt.widgets.Label labelVal = new org.eclipse.swt.widgets.Label (formComp, SWT.RIGHT);
		labelVal.setText ("Value:");
		data = new FormData ();
		data.width = 40;
		data.top = new FormAttachment(text, 5);
		labelVal.setLayoutData (data);

		final Text textVal = toolkit.createText(formComp, "", SWT.BORDER);
		data = new FormData ();
		data.width = 80;
		data.left = new FormAttachment (labelVal, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelVal, 0, SWT.CENTER);
		textVal.setLayoutData (data);
		formComp.setData(textVal.getText());
		
		Button ok = new Button (formComp, SWT.PUSH);
		ok.setText ("Add");
		data = new FormData ();
		data.width = 40;
		data.top = new FormAttachment(textVal, 5);
		data.right = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				GuiParam param = new GuiParam();
				param.setName(text.getText());	
				param.setValue(textVal.getText());
				//add parameter to windows bean
				if(label != null){
					Vector params = label.getParams();
					if(params != null){
						params.add(param);
					}else{
						params = new Vector<Param>();
						label.setParams(params);
						params.add(param);
					}				

				}
				TableItem item = new TableItem(table, SWT.NONE);
				createGUIRow(item, table, param);
				//clean combo and text
				text.setText("");
				textVal.setText("");
				text.redraw();
				textVal.redraw();
				
				editor.setIsDirty(true);
			}
		});
	}
	private void createGUIRow(TableItem item, final Table guiTable, GuiParam param){
		if(param.getName() != null)
			item.setText(0, param.getName());
		if(param.getValue() != null )
			item.setText(1, param.getValue());
		guiTable.redraw();
	}
	private void createPositionCheck(FormToolkit toolkit, Group guiGroup, final Label label){
		Composite posComp = toolkit.createComposite(guiGroup, SWT.NONE);
		RowLayout rl = new RowLayout();
		rl.fill=true;
		rl.justify=true;
		rl.spacing=5;
		rl.marginLeft=5;
		rl.marginTop=5;
		posComp.setLayout(rl);
		
		org.eclipse.swt.widgets.Label position = toolkit.createLabel(posComp, "Position:", SWT.BOLD);
		createSinglePosition(toolkit, posComp, label, "header-left");
		createSinglePosition(toolkit, posComp, label, "header-center");
		createSinglePosition(toolkit, posComp, label, "header-right");
		Composite posComp2 = toolkit.createComposite(guiGroup, SWT.NONE);
		rl = new RowLayout();
		rl.fill=true;
		rl.justify=true;
		rl.spacing=5;
		rl.marginLeft=55;
		rl.marginTop=5;
		posComp2.setLayout(rl);
		createSinglePosition(toolkit, posComp2, label, "footer-left");
		createSinglePosition(toolkit, posComp2, label, "footer-center");
		createSinglePosition(toolkit, posComp2, label, "footer-right");		
	}
	private void createText(FormToolkit toolkit, Group guiGroup, final Label label){
		Composite textComp = toolkit.createComposite(guiGroup, SWT.FILL);
		RowLayout rl = new RowLayout();
		rl.fill=true;
		rl.wrap=true;
		//rl.justify=true;
		rl.spacing=5;
		rl.marginLeft=5;
		rl.marginTop=5;
		textComp.setLayout(rl);
		
		org.eclipse.swt.widgets.Label labelText = toolkit.createLabel(textComp, "Text:", SWT.RIGHT);

		final Text text = toolkit.createText(textComp, "", SWT.BORDER);
		text.setSize(20, 200);
		if(label != null && label.getText() != null){
			text.setText(label.getText());
		}
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	

				label.setText(text.getText());
				editor.setIsDirty(true);
			}
		});
		if(label != null && label.getText() != null){
			text.setText(label.getText());
			text.redraw();
		}
		
	}
	private void createSinglePosition(FormToolkit toolkit, Composite posComp, final Label label, String pos){
		final Button btn = toolkit.createButton(posComp, pos, SWT.RADIO);
		btn.addListener(SWT.Selection, new Listener () {
            public void handleEvent (Event event) { 
            	//add to label
            	if(!btn.getText().equals(label.getPosition())){
	            	Label labelExistent = LabelBO.getLabelByPosition(geoDocument, btn.getText());
	            	if(labelExistent == null){
	            		label.setPosition(btn.getText());
	            	}else{
	            		MessageDialog.openError(mainComposite.getShell(), "Warning", "Label in the same position already exists.");
	            	}
	            	editor.setIsDirty(true);
            	}
            }
        });
		if(label != null && label.getPosition() != null && label.getPosition().equalsIgnoreCase(pos)){
			btn.setSelection(true);
			btn.redraw();
		}
		
	}
	private void createFormatInput(FormToolkit toolkit, Group guiGroup, final Label label){
		Composite formComp = toolkit.createComposite(guiGroup, SWT.NONE);

		RowLayout rl = new RowLayout();
		rl.fill=true;
		rl.justify=false;
		rl.spacing=10;
		rl.marginLeft=10;
		rl.marginTop=10;
		formComp.setLayout(rl);
		org.eclipse.swt.widgets.Label formatlabel = toolkit.createLabel(formComp, "Format:", SWT.BOLD);
		final Text day = toolkit.createText(formComp, "dd/MM/yyyy");
		label.getFormat().setDay(day.getText());
		final Text hour = toolkit.createText(formComp, "HH:mm");
		label.getFormat().setHour(hour.getText());
		hour.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	
				label.getFormat().setDay(day.getText());
				label.getFormat().setHour(hour.getText());
			}
		});
		if(label != null && label.getFormat().getDay() != null){
			day.setText(label.getFormat().getDay());
			day.redraw();
		}
		if(label != null && label.getFormat().getHour() != null){
			hour.setText(label.getFormat().getHour());
			hour.redraw();
		}
	}
	private void createMenu(final Table table, final Label label){		
    	Menu menu = new Menu (mainComposite.getShell(), SWT.POP_UP);    	
    	MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText ("Delete");
		menuItem.addListener(SWT.Selection, new Listener () {
            public void handleEvent (Event event) { 
            	TableItem[] sel = table.getSelection();
            	if(sel[0] != null){
                	deleteItem(label, sel[0]);
            	}else{
            		MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Please select an item to delete");
            	}

            }
        });	
		table.setMenu(menu);
	}
	
	private void deleteItem(Label label, TableItem item){
		LabelBO.deleteParamByName(label, item.getText());
		item.dispose();
		editor.setIsDirty(true);
		item.getParent().redraw();
		
	}
	public GEOEditor getEditor() {
		return editor;
	}

	public void setEditor(GEOEditor editor) {
		this.editor = editor;
	}

	public Composite getMainComposite() {
		return mainComposite;
	}

	public void setMainComposite(Composite mainComposite) {
		this.mainComposite = mainComposite;
	}

	public GEODocument getGeoDocument() {
		return geoDocument;
	}

	public void setGeoDocument(GEODocument geoDocument) {
		this.geoDocument = geoDocument;
	}

}
