/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors;

import it.eng.spagobi.studio.geo.Activator;
import it.eng.spagobi.studio.geo.editors.model.bo.DefaultsBO;
import it.eng.spagobi.studio.geo.editors.model.bo.GuiSettingsBO;
import it.eng.spagobi.studio.geo.editors.model.bo.WindowBO;
import it.eng.spagobi.studio.geo.editors.model.geo.Defaults;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;
import it.eng.spagobi.studio.geo.editors.model.geo.GuiSettings;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchy;
import it.eng.spagobi.studio.geo.editors.model.geo.Param;
import it.eng.spagobi.studio.geo.editors.model.geo.Window;

import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class GuiSettingsDesigner {
	
	private GEOEditor editor=null;
	private Composite mainComposite;
	private GEODocument geoDocument;
	
	private GuiSettings guiSettings;
	private final int TYPE_WINDOWS=1;
	private final int TYPE_PARAMS=2;
	
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
	
	public GuiSettingsDesigner(Composite _composite, GEOEditor _editor, GEODocument _geoDocument) {
		super();
		mainComposite= _composite;
		editor = _editor;
		geoDocument = _geoDocument;
	}
	
	public void createGuiSettingsWindows(final Composite sectionClient, FormToolkit toolkit){
		
		RowLayout rl = new RowLayout();
		rl.fill=true;
		rl.wrap=true;
		// look up for guisettings stored in geodocument
		guiSettings = GuiSettingsBO.getGuiSettings(geoDocument);
		//create defaults group
		createDefaults(sectionClient, toolkit);
		
		//windows section - navigation
		createWindowGroup("Navigation", toolkit,  rl);
		createWindowGroup("Measures", toolkit,  rl);
		createWindowGroup("Layers", toolkit,  rl);
		//createWindowGroup("Detail", toolkit,  rl);
		createWindowGroup("Legend", toolkit,  rl);
		createWindowGroup("Colourpicker", toolkit,  rl);
	
		sectionClient.redraw();
	}
	private void createDefaults(final Composite sectionClient, FormToolkit toolkit){
		
		Defaults defaults = DefaultsBO.setNewDefaults(geoDocument);
		final Vector<GuiParam> params =defaults.getParams();
		
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint=340;
		
		
		final Group guiGroup = new Group(mainComposite, SWT.FILL);
		guiGroup.setText("Defaults");
		//guiGroup.setLayout(mainComposite.getLayout());		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		guiGroup.setLayout(gridLayout);

		
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 5;
		formLayout.marginHeight = 5;
		formLayout.spacing = 5;		
				
		Composite formComp = toolkit.createComposite(guiGroup, SWT.NONE);
		formComp.setLayout (formLayout);
		formComp.setLayoutData(gridData);
		
		Label visLabel = new Label (formComp, SWT.RIGHT);
		visLabel.setText ("Visible:");
		FormData data = new FormData ();
		data.width = 40;
		visLabel.setLayoutData (data);

		final Combo visible = new Combo(formComp, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		visible.add("true");
		visible.add("false");
		final GuiParam[] paramVis = new GuiParam[1];
		for(int i=0; i<params.size(); i++){
			GuiParam param = params.elementAt(i);
			if(param.getName().equalsIgnoreCase("visible")){
				String val = param.getValue();
				visible.getItems();
				for(int j=0; j<visible.getItems().length; j++){
					if(visible.getItems()[j].equalsIgnoreCase(val)){
						visible.select(j);
						paramVis[0]=param;
					}					
				}				
			}
		}	
		
		visible.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	
				if(paramVis[0] == null){
					paramVis[0] = new GuiParam();
					paramVis[0].setValue(visible.getText());
					paramVis[0].setName("visible");
					params.add(paramVis[0]);
				}else
					paramVis[0].setValue(visible.getText());
				
				editor.setIsDirty(true);
			}
		});
		data = new FormData ();
		data.width = 120;
		data.left = new FormAttachment (visLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		visible.setLayoutData (data);
		
		Label yLabel = new Label (formComp, SWT.RIGHT);
		yLabel.setText ("Y:");
		data = new FormData ();
		data.width = 40;
		data.top = new FormAttachment(visible, 5);
		yLabel.setLayoutData (data);

		final Text y = toolkit.createText(formComp, "", SWT.BORDER);
		data = new FormData ();
		data.width = 120;
		data.left = new FormAttachment (yLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (yLabel, 0, SWT.CENTER);
		y.setLayoutData (data);
		final GuiParam[] paramY = new GuiParam[1];
		
		for(int i=0; i<params.size(); i++){
			GuiParam param = params.elementAt(i);
			if(param.getName().equalsIgnoreCase("y")){
				String val = param.getValue();
				y.setText(val);	
				paramY[0]= param;
			}
		}		
		y.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	
				if(paramY[0] == null){
					paramY[0] = new GuiParam();
					paramY[0].setValue(y.getText());
					paramY[0].setName("y");
					params.add(paramY[0]);
				}else
					paramY[0].setValue(y.getText());
				
				editor.setIsDirty(true);
			}
		});
		
		/*Modified 20/01/10 (IT WAS A BUG)*/
		
		Label transformLabel = new Label (formComp, SWT.RIGHT);
		transformLabel.setText ("Transform:");
		data = new FormData ();
		data.width = 60;
		data.top = new FormAttachment(y, 5);
		transformLabel.setLayoutData (data);

		final Text transform = toolkit.createText(formComp, "scale(1.0)", SWT.BORDER);
		data = new FormData ();
		data.width = 100;
		data.left = new FormAttachment (transformLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (transformLabel, 0, SWT.CENTER);
		transform.setLayoutData (data);
		final GuiParam[] paramTransform = new GuiParam[1];
		
		for(int i=0; i<params.size(); i++){
			GuiParam param = params.elementAt(i);
			if(param.getName().equalsIgnoreCase("transform")){
				String val = param.getValue();
				transform.setText(val);	
				paramTransform[0]= param;
			}
		}		
		transform.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	
				if(paramTransform[0] == null){
					paramTransform[0] = new GuiParam();
					paramTransform[0].setValue(transform.getText());
					paramTransform[0].setName("transform");
					params.add(paramTransform[0]);
				}else
					paramTransform[0].setValue(transform.getText());
				
				editor.setIsDirty(true);
			}
		});
		/*end modified*/
		
		Label styleLabel = new Label (formComp, SWT.RIGHT);
		styleLabel.setText ("Styles:");
		data = new FormData ();
		data.width = 40;
		data.top = new FormAttachment(transform, 5);
		styleLabel.setLayoutData (data);

		final Text style = toolkit.createText(formComp, "", SWT.BORDER | SWT.MULTI | SWT.WRAP );
		data = new FormData ();
		data.width = 120;
		data.height=60;
		data.left = new FormAttachment (styleLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (styleLabel, 0, SWT.TOP);
		style.setLayoutData (data);
		final GuiParam[] paramStyle = new GuiParam[1];
		for(int i=0; i<params.size(); i++){
			GuiParam param = params.elementAt(i);
			if(param.getName().equalsIgnoreCase("styles")){
				String val = param.getValue();
				style.setText(val);	
				paramStyle[0]=param;
			}
		}		
		style.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	
				if(paramStyle[0] == null){
					paramStyle[0] = new GuiParam();
					paramStyle[0].setValue(style.getText());
					paramStyle[0].setName("styles");
					params.add(paramStyle[0]);
				}else
					paramStyle[0].setValue(style.getText());
				
				editor.setIsDirty(true);
			}
		});
		Label minLabel = new Label (formComp, SWT.RIGHT);
		minLabel.setText ("Minimized:");
		data = new FormData ();
		data.width = 40;
		data.top = new FormAttachment(style, 5);
		minLabel.setLayoutData (data);

		final Combo minim = new Combo(formComp, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		minim.add("true");
		minim.add("false");
		
		final GuiParam[] paramMin = new GuiParam[1];
		for(int i=0; i<params.size(); i++){			
			GuiParam param = params.elementAt(i);
			if(param.getName().equalsIgnoreCase("minimized")){
				String val = param.getValue();
				minim.getItems();
				for(int j=0; j<minim.getItems().length; j++){
					if(minim.getItems()[j].equalsIgnoreCase(val)){
						minim.select(j);
						paramMin[0]=param;
					}					
				}				
			}
		}		
		minim.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				if(paramMin[0] == null){
					paramMin[0] = new GuiParam();
					paramMin[0].setValue(minim.getText());
					paramMin[0].setName("minimized");
					params.add(paramMin[0]);
				}else
					paramMin[0].setValue(minim.getText());
				
				editor.setIsDirty(true);
			}
		});
		
		data = new FormData ();
		data.width = 120;
		data.left = new FormAttachment (minLabel, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (minLabel, 0, SWT.CENTER);
		minim.setLayoutData (data);
		
	}
	public void createGuiSettingsParams(final Composite sectionClient, FormToolkit toolkit){
		
		RowLayout rl = new RowLayout();
		rl.fill=true;
		rl.wrap=true;
		
		// look up for guisettings stored in geodocument
		guiSettings = GuiSettingsBO.getGuiSettings(geoDocument);

		createParamsGroup(toolkit,  rl);
	
		sectionClient.redraw();
	}

	private void createParamsGroup(FormToolkit toolkit, RowLayout rl){
		
		final Group guiGroup = new Group(mainComposite, SWT.FILL);
		
		guiGroup.setLayout(rl);
		guiGroup.setLayout(mainComposite.getLayout());
		
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan =4;
		gd.heightHint=75;
		gd.minimumHeight=60;
		gd.verticalAlignment=SWT.TOP;	

		final Table guiWindowsTable = toolkit.createTable(guiGroup, SWT.MULTI | SWT.BORDER	| SWT.FULL_SELECTION);
		guiWindowsTable.setLayoutData(gd);
		guiWindowsTable.setLinesVisible(true);
		guiWindowsTable.setHeaderVisible(true);

		String[] titles = { "  Parameter Name  ", "  Value  "};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(guiWindowsTable, SWT.NONE);
			column.setText(titles[i]);

		}

		if (guiSettings != null && guiSettings.getParams() != null ) {
		
			Vector<GuiParam> params = guiSettings.getParams();
			for(int j=0; j< params.size(); j++){
				TableItem item = new TableItem(guiWindowsTable, SWT.TRANSPARENT);
				createGUIRow(item, guiWindowsTable, params.elementAt(j));
			}
		}
		for (int i = 0; i < titles.length; i++) {
			guiWindowsTable.getColumn(i).pack();
		}
		 //rightClick --> menu
		guiWindowsTable.addListener(SWT.MouseDown, new Listener () {
            public void handleEvent (Event event) {            	
            	if (event.button==3){	
            		createMenu(guiWindowsTable, null);	            	            	
            	}
            }
        });
		guiWindowsTable.redraw();
		//form to add parameters
		createInsertParamForm(null, toolkit, guiGroup, guiWindowsTable);

		
	}
	private void createWindowGroup(final String forWindow, FormToolkit toolkit, RowLayout rl){
		
		final Group guiGroup = new Group(mainComposite, SWT.FILL);
		guiGroup.setText(forWindow);
		
		guiGroup.setLayout(rl);
		guiGroup.setLayout(mainComposite.getLayout());
		
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan =1;
		gd.heightHint=75;
		gd.minimumHeight=60;
		gd.verticalAlignment=SWT.TOP;	

		final Table guiWindowsTable = toolkit.createTable(guiGroup, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		guiWindowsTable.setLayoutData(gd);
		guiWindowsTable.setLinesVisible(true);
		guiWindowsTable.setHeaderVisible(true);

		String[] titles = { "Parameter Name", "Value"};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(guiWindowsTable, SWT.NONE);
			column.setText(titles[i]);

		}

		if (guiSettings != null && guiSettings.getWindows() != null && guiSettings.getWindows().getWindow() != null) {
			Window window = WindowBO.getWindowByName(geoDocument, forWindow);
			if(window != null){				
				Vector<GuiParam> params = window.getParams();
				for(int j=0; j< params.size(); j++){
					TableItem item = new TableItem(guiWindowsTable, SWT.TRANSPARENT);
					createGUIRow(item, guiWindowsTable, params.elementAt(j));
				}
			}
		} 
		for (int i = 0; i < titles.length; i++) {
			guiWindowsTable.getColumn(i).pack();
		}
		 //rightClick --> menu
		guiWindowsTable.addListener(SWT.MouseDown, new Listener () {
            public void handleEvent (Event event) {            	
            	if (event.button==3){	
            		Window window = WindowBO.getWindowByName(geoDocument, forWindow);
            		createMenu(guiWindowsTable, window);	            	            	
            	}
            }
        });
		guiWindowsTable.redraw();
		//form to add parameters
		createInsertParamForm(forWindow, toolkit, guiGroup, guiWindowsTable);

		
	}
	private void createMenu(final Table table, final Window window){		
    	Menu menu = new Menu (mainComposite.getShell(), SWT.POP_UP);    	
    	MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText ("Delete");
		menuItem.addListener(SWT.Selection, new Listener () {
            public void handleEvent (Event event) { 
            	TableItem[] sel = table.getSelection();
            	if(sel[0] != null){
            		if(window != null){
            			deleteItemWindow(table, sel[0], window);
            		}else{
            			deleteItemParams(table, sel[0]);
            		}
                	
            	}else{
            		MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Please select an item to delete");
            	}

            }
        });	
		table.setMenu(menu);
	}
	private void deleteItemWindow(Table table, TableItem item, Window window){
		WindowBO.deleteParamByName(window, item.getText(0));
		item.dispose();
        //table.pack();
        table.redraw();
        editor.setIsDirty(true);
	}
	private void deleteItemParams(Table table, TableItem item){
		GuiSettingsBO.deleteParamByName(geoDocument, item.getText(0));
		item.dispose();
        //table.pack();
        table.redraw();
        editor.setIsDirty(true);
	}

	private void createInsertParamForm(final String windowName, FormToolkit toolkit, Group group, final Table table){
		
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 5;
		formLayout.marginHeight = 5;
		formLayout.spacing = 5;		
				
		final Composite formComp = toolkit.createComposite(group, SWT.NONE);
		formComp.setLayout (formLayout);
		
		Label label = new Label (formComp, SWT.RIGHT);
		label.setText ("Name:");
		FormData data = new FormData ();
		data.width = 40;
		label.setLayoutData (data);
		int type =TYPE_WINDOWS;
		if(windowName == null){
			type =TYPE_PARAMS;
		}
		final Combo text = createParamCombo(formComp, type);
		

		data = new FormData ();
		data.width = 80;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		text.setLayoutData (data);
		formComp.setData(text.getText());
		
		Label labelVal = new Label (formComp, SWT.RIGHT);
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
		//listener on name
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {				
				if(windowName == null){
					GuiParam param = GuiSettingsBO.getParamByName(geoDocument, text.getText());
					if(param != null){
						MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Another parameter with the same name is already defined.");		
						text.deselectAll();
					}
				}else{					
					Window window = WindowBO.getWindowByName(geoDocument, windowName);
					if(window != null){
						GuiParam param = WindowBO.getParamByName(window, text.getText());
						if(param != null){
							MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Another parameter with the same name is already defined.");		
							text.deselectAll();
						}
					}
					//if styles selected--> textarea
					if(text.getText().equals("styles")){
						createInputStyleShell(window, table, windowName);
					}
					
				}
			}
		});
		
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
				if(windowName != null){
					Window window = WindowBO.getWindowByName(geoDocument, windowName);					
					//insert in geodocument
					if(window != null){
						Vector params = window.getParams();
						if(params != null){
							params.add(param);
						}else{
							params = new Vector<Param>();
							window.setParams(params);						
						}					
					}else{
						//crea window
						Window newWindow = WindowBO.setNewWindow(geoDocument);
						newWindow.setName(windowName.toLowerCase());
						Vector params = newWindow.getParams();
						if(params != null){
							params.add(param);
						}else{
							params = new Vector<Param>();
							params.add(param);
							newWindow.setParams(params);						
						}	
					}

				}else{
					//add parameter to guisettings
					Vector<GuiParam> params = guiSettings.getParams();
					if(params == null){
						params = new Vector<GuiParam>();
						guiSettings.setParams(params);
					}
					params.add(param);
				}
				TableItem item = new TableItem(table, SWT.NONE);
				createGUIRow(item, table, param);
				//clean combo and text
				text.deselectAll();
				textVal.setText("");
				text.redraw();
				textVal.redraw();
				
				editor.setIsDirty(true);
			}
		});
	}
	private void createInputStyleShell(final Window window, final Table table, final String windowName){
//		System.out.println(window);
		final Shell dialog = new Shell (mainComposite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("Insert Styles");
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label label = new Label (dialog, SWT.RIGHT);
		label.setText ("Styles:");
		FormData data = new FormData ();
		data.width = 40;
		label.setLayoutData (data);

		Button cancel = new Button (dialog, SWT.PUSH);
		cancel.setText ("Cancel");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (100, 0);
		data.bottom = new FormAttachment (100, 0);
		cancel.setLayoutData (data);
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
//				System.out.println("User cancelled dialog");
				dialog.close ();
			}
		});
		final GuiParam[] paramStyle = new GuiParam[1];
		final Text text = new Text (dialog, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		if(window != null && window.getParams() != null){
			for(int i=0; i<window.getParams().size(); i++){
				GuiParam param = window.getParams().elementAt(i);
				if(param.getName().equalsIgnoreCase("styles")){
					String val = param.getValue();
					text.setText(val);
					paramStyle[0]=param;
				}
			}
		}
		data = new FormData ();
		data.width = 180;
		data.height=100;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (label, 0, SWT.TOP);
		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		text.setLayoutData (data);
		
		final Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {			
				Window win = window;
				if(window == null){
					win = WindowBO.setNewWindow(geoDocument);
					win.setName(windowName);
				}				
				String style = text.getText();				
				if(paramStyle[0] == null){
					paramStyle[0] = new GuiParam();
					paramStyle[0].setValue(style);
					paramStyle[0].setName("styles");
					if(win.getParams() != null){
						win.getParams().add(paramStyle[0]);
					}else{
						Vector<GuiParam> params = new Vector<GuiParam>();
						params.add(paramStyle[0]);
						win.setParams(params);
					}
					
				}else{
					paramStyle[0].setValue(style);
				}
				TableItem item = new TableItem(table, SWT.NONE);	
//				System.out.println(item);
//				System.out.println(table);
//				System.out.println(paramStyle[0]);
				createGUIRow(item, table, paramStyle[0]);	
				table.redraw();
				editor.setIsDirty(true);				
				dialog.close ();
			}
		});

		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();

	}
	

	private void createGUIRow(TableItem item, final Table guiTable, GuiParam param){
		if(param.getName() != null){
			item.setText(0, param.getName());
		}
		if(param.getValue() != null ){
			if(param.getName().equalsIgnoreCase("styles")){
				String shortVal = param.getValue();
				if(shortVal.length()>10){
					shortVal = shortVal.substring(0, 10);
				}
				item.setText(1, shortVal);
			}else{
				item.setText(1, param.getValue());
			}
		}
		guiTable.redraw();
	}
	private Combo createParamCombo(Composite composite, int type){
		Combo combo= new Combo(composite, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		if(type == TYPE_PARAMS){
			combo.add("defaultDrillNav");
			combo.add("highlightOnMouseOver");
			combo.add("normalizeChartValues");
			combo.add("chartScale");
			combo.add("chartWidth");
			combo.add("chartHeight");
			combo.add("valueFont");
			combo.add("valueScale");
		}else if(type == TYPE_WINDOWS){
			combo.add("visible");
			combo.add("width");
			combo.add("height");
			combo.add("x");
			combo.add("y");
			combo.add("moovable");
			combo.add("xMin");
			combo.add("yMin");
			combo.add("xMax");
			combo.add("yMax");
			combo.add("showContent");
			combo.add("margin");
			combo.add("titleBarVisible");
			combo.add("statusBarVisible");
			combo.add("title");
			combo.add("statusBarContent");
			combo.add("closeButtonVisible");
			combo.add("minimizeButtonVisible");
			combo.add("maximizeButtonVisible");
			combo.add("minimized");
			combo.add("transform");
			combo.add("styles");
		}
		return combo;
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
