/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.studio.geo.editors;

import it.eng.spagobi.studio.geo.Activator;
import it.eng.spagobi.studio.geo.editors.model.bo.CrossNavigationBO;
import it.eng.spagobi.studio.geo.editors.model.bo.HierarchyBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LevelBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LinkBO;
import it.eng.spagobi.studio.geo.editors.model.geo.CrossNavigation;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchies;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchy;
import it.eng.spagobi.studio.geo.editors.model.geo.Level;
import it.eng.spagobi.studio.geo.editors.model.geo.Link;
import it.eng.spagobi.studio.geo.editors.model.geo.LinkParam;

import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class CrossNavigationDesigner {
	private GEOEditor editor=null;
	private Composite mainComposite;
	private GEODocument geoDocument;
	private Vector<TableEditor> tableEditors = new Vector<TableEditor>();

	Button addParameter=null;
	Button detail=null;	
	Button erase=null;	

	final ImageDescriptor addIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/add.gif");

	final ImageDescriptor paramsIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/linkParams.gif");

	final ImageDescriptor eraseIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/erase.gif");

	final ImageDescriptor detailIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/detail.gif");

	public CrossNavigationDesigner(Composite _composite, GEOEditor _editor, GEODocument _geoDocument) {
		super();
		mainComposite= _composite;
		editor = _editor;
		geoDocument = _geoDocument;
	}
	public void createCrossnavigationTable(final Composite sectionClient, FormToolkit toolkit){
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan =2;
		gd.verticalAlignment=SWT.TOP;
		gd.widthHint= 700;
		gd.minimumWidth=600;
		gd.grabExcessHorizontalSpace=true;
		gd.heightHint=150;

		final Composite crossNavGroup = new Composite(sectionClient, SWT.RESIZE );

		crossNavGroup.setLayoutData(gd);
		crossNavGroup.setLayout(sectionClient.getLayout());


		final Table crossNavTable = toolkit.createTable(crossNavGroup, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);

		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan =2;
		gd.verticalAlignment=SWT.TOP;
		gd.widthHint= 500;
		gd.minimumWidth=400;
		gd.grabExcessHorizontalSpace=true;
		gd.heightHint=130;

		crossNavTable.setLayoutData(gd);
		crossNavTable.setLinesVisible(true);
		crossNavTable.setHeaderVisible(true);

		String[] titles = { "  Hierarchy   ","     Level     ", "    ", "    ", "    "};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(crossNavTable, SWT.NONE);
			column.setText(titles[i]);

		}
		// look up for crossNavigation stored in geodocument
		final CrossNavigation crossNavigation = CrossNavigationBO.getCrossNavigation(geoDocument);

		if (crossNavigation != null && crossNavigation.getLinks() != null) {
			for(int i=0; i<crossNavigation.getLinks().size(); i++){
				TableItem item = new TableItem(crossNavTable, SWT.TRANSPARENT);
				createTableItemRow(item, crossNavTable, crossNavigation.getLinks().elementAt(i));
			}
		}
		for (int i = 0; i < titles.length; i++) {
			crossNavTable.getColumn(i).pack();
		}
		// resize the row height using a MeasureItem listener
		crossNavTable.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = 23;
			}
		});


		RowLayout layout = new RowLayout();
		// Optionally set layout fields.
		layout.wrap = false;
		layout.marginWidth=10;
		layout.fill=true;
		layout.spacing=20;


		Composite addComp = toolkit.createComposite(sectionClient, SWT.NONE | SWT.FILL);
		// Set the layout into the composite.
		addComp.setLayout(layout);        
		Label labelForAdd = toolkit.createLabel(addComp, "Click here to add a new link");
		Button addNewLine = new Button(addComp, SWT.PUSH);

		Image addImage = addIcon.createImage();
		addNewLine.setImage(addImage);

		addNewLine.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {

				Hierarchies hierarchies=HierarchyBO.getAllHierarchies(geoDocument);
				if(hierarchies == null || hierarchies.getHierarchy() == null){
					MessageDialog.openWarning(sectionClient.getShell(), "Warning", "No hierarchies defined");

				}else{				
					TableItem item = new TableItem(crossNavTable, SWT.NONE);
					createTableItemRow(item, crossNavTable, null);				

					getEditor().setIsDirty(true);
					crossNavTable.redraw();
				}
			}
		});
		addNewLine.pack();
		addNewLine.setToolTipText("Add new link");

		crossNavTable.pack();
		crossNavTable.redraw();
		sectionClient.redraw();
	}

	private void createOptionalShell(Composite sectionClient, final Link link){

		Vector<LinkParam>params = link.getParam();
		//LinkBO.getLinkParam(link, param);

		final Shell dialog = new Shell (mainComposite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("Optional Parameter");

		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label label = new Label (dialog, SWT.RIGHT);
		label.setText ("Parameter Name:");
		FormData data = new FormData ();
		data.width = 100;
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

		final Text text = new Text (dialog, SWT.BORDER);

		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (label, 0, SWT.CENTER);
		text.setLayoutData (data);


		//type
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(text, 5);

		Label labelType = new Label (dialog, SWT.RIGHT);
		labelType.setText ("Parameter Type:");		
		labelType.setLayoutData (data);

		final Combo textType = new Combo (dialog, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		textType.add("absolute");
		textType.add("relative");
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelType, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelType, 0, SWT.CENTER);

		textType.setLayoutData (data);

		//value
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(textType, 5);

		Label labelValue = new Label (dialog, SWT.RIGHT);
		labelValue.setText ("Parameter Value:");		
		labelValue.setLayoutData (data);

		final Text textValue = new Text (dialog, SWT.BORDER);
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelValue, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelValue, 0, SWT.CENTER);
		textValue.setLayoutData (data);

		//value
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(textValue, 5);

		Label labelScope = new Label (dialog, SWT.RIGHT);
		labelScope.setText ("Parameter Scope:");		
		labelScope.setLayoutData (data);

		final Text textScope = new Text (dialog, SWT.BORDER);
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelScope, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelScope, 0, SWT.CENTER);
		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		textScope.setLayoutData (data);

		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("Finish");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				//create tree item
				String type = textType.getText();
				String name = text.getText();
				String scope =textScope.getText();
				String value = textValue.getText();

				LinkParam param = new LinkParam();
				param.setName(name);
				param.setScope(scope);
				param.setType(type);
				param.setValue(value);

				LinkBO.addParamToLink(geoDocument, link, param);
				editor.setIsDirty(true);
				dialog.close ();
			}
		});

		Button more = new Button (dialog, SWT.PUSH);
		more.setText ("Add more");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (ok, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		more.setLayoutData (data);
		more.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				//create tree item
				String type = textType.getText();
				String name = text.getText();
				String scope =textScope.getText();
				String value = textValue.getText();

				LinkParam param = new LinkParam();
				param.setName(name);
				param.setScope(scope);
				param.setType(type);
				param.setValue(value);

				LinkBO.addParamToLink(geoDocument, link, param);
				editor.setIsDirty(true);
				createOptionalShell(dialog.getParent(), link);

				dialog.close ();
			}
		});
		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();

	}
	private void createTableItemRow(TableItem item, final Table crossNavTable, Link link){

		final TableEditor editor1 = new TableEditor(crossNavTable);

		final Combo hierarchiesCombo = createHierachiesCombo(crossNavTable);
		if(link != null){
			String hierarchySel = link.getHierarchy();
			for (int k = 0; k < hierarchiesCombo.getItemCount(); k++) {
				String text = hierarchiesCombo.getItem(k);
				if (hierarchySel.equals(text)) {
					hierarchiesCombo.select(k);
				}
			}
			hierarchiesCombo.setData(link.getId());
			hierarchiesCombo.setEnabled(false);
		}

		editor1.minimumWidth = hierarchiesCombo.getBounds().x;
		editor1.horizontalAlignment = SWT.CENTER;
		editor1.grabHorizontal = true;
		editor1.minimumHeight = hierarchiesCombo.getBounds().y;
		editor1.verticalAlignment = SWT.CENTER;
		editor1.grabVertical = true;
		editor1.setEditor(hierarchiesCombo, item, 0);
		editor1.layout();

		final TableEditor editor2 = new TableEditor(crossNavTable);

		final Combo levelCombo = createLevelsCombo(crossNavTable, hierarchiesCombo.getText());
		boolean isThereLink=false;
		if(link != null){
			String levelSel = link.getLevel();
			for (int k = 0; k < levelCombo.getItemCount(); k++) {
				String text = levelCombo.getItem(k);
				if (levelSel.equals(text)) {
					levelCombo.select(k);
				}
			}
			levelCombo.setData(link.getId());
			isThereLink=true;
		}
		editor2.minimumWidth = levelCombo.getBounds().x;
		editor2.horizontalAlignment = SWT.CENTER;
		editor2.grabHorizontal = true;
		editor2.minimumHeight = levelCombo.getBounds().y;
		editor2.verticalAlignment = SWT.CENTER;
		editor2.grabVertical = true;
		editor2.setEditor(levelCombo, item, 1);

		hierarchiesCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String hierarchySelected = ((Combo)e.widget).getText();
				recreateLevelsCombo(crossNavTable,levelCombo, hierarchySelected);
				Integer idLink=null;

				// modifying hiearchi combo can only update, but to insert one must also define level
				if(hierarchiesCombo.getData()!=null){
					idLink=(Integer)hierarchiesCombo.getData();
					Link link = LinkBO.getLinkByHierarchyAndLevel(geoDocument,idLink);	
					if (link == null) {
						//						link = LinkBO.setNewLink(geoDocument, hierarchiesCombo.getText(), levelCombo.getText());
					}
					// should not be null
					// TODO update
				}
				hierarchiesCombo.setEnabled(false);
				getEditor().setIsDirty(true);				
				crossNavTable.redraw();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		/*	    Listener listener = new Listener() {
	        public void handleEvent(Event e) {
	          if(e.type == SWT.KeyDown){
	        	  if(hierarchiesCombo.getItemCount()==0){
						createHierachiesCombo(crossNavTable);
				}
	          }
	        }
	      };
	    hierarchiesCombo.addListener(SWT.KeyDown, listener);*/

		levelCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {

				// lets see if there is already an Id assigned
				Integer idLink=null;
				if(hierarchiesCombo.getData()!=null){
					idLink=(Integer)hierarchiesCombo.getData();
				}
				Link link = LinkBO.getLinkByHierarchyAndLevel(geoDocument, idLink);	
				// if link is new
				if (link == null) {
					link = LinkBO.setNewLink(geoDocument, hierarchiesCombo.getText(), levelCombo.getText());
					hierarchiesCombo.setData(link.getId());
					levelCombo.setData(link.getId());
				}
				else{ // if link is not new update
					LinkBO.updateExisting(geoDocument, idLink, levelCombo.getText());
				}
				getEditor().setIsDirty(true);
				addParameter.setEnabled(true);
				detail.setEnabled(true);
				erase.setEnabled(true);

			}
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});



		final TableEditor editor4 = new TableEditor(crossNavTable);
		addParameter = new Button(crossNavTable, SWT.PUSH);
		addParameter.setSize(paramsIcon.createImage().getBounds().width, paramsIcon.createImage().getBounds().height);
		addParameter.setImage(paramsIcon.createImage());
		if(!isThereLink)addParameter.setEnabled(false);
		addParameter.setToolTipText("Add parameters");
		editor4.minimumWidth = addParameter.getBounds().x;
		editor4.horizontalAlignment = SWT.CENTER;
		editor4.grabHorizontal = true;
		editor4.minimumHeight = addParameter.getBounds().y;
		editor4.verticalAlignment = SWT.TOP;
		editor4.grabVertical = true;
		editor4.setEditor(addParameter, item, 2);


		addParameter.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				Integer idLink=null;
				if(hierarchiesCombo.getData()!=null){
					idLink=(Integer)hierarchiesCombo.getData();
				}
				Link link = LinkBO.getLinkByHierarchyAndLevel(geoDocument,idLink);	
				if (link == null) {
					link = LinkBO.setNewLink(geoDocument, hierarchiesCombo.getText(), levelCombo.getText());
				} 
				createOptionalShell(crossNavTable.getParent(), link);
			}
		});
		addParameter.pack();

		final TableEditor editor5 = new TableEditor(crossNavTable);
		detail = new Button(crossNavTable, SWT.PUSH);
		detail.setSize(detailIcon.createImage().getBounds().width, detailIcon.createImage().getBounds().height);
		detail.setImage(detailIcon.createImage());
		if(!isThereLink)detail.setEnabled(false);
		detail.setToolTipText("View detail");
		editor5.minimumWidth = detail.getBounds().x;
		editor5.horizontalAlignment = SWT.CENTER;
		editor5.grabHorizontal = true;
		editor5.minimumHeight = detail.getBounds().y;
		editor5.verticalAlignment = SWT.TOP;
		editor5.grabVertical = true;
		editor5.setEditor(detail, item, 3);


		detail.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				Integer idLink=null;
				if(hierarchiesCombo.getData()!=null){
					idLink=(Integer)hierarchiesCombo.getData();
				}				
				Link link = LinkBO.getLinkByHierarchyAndLevel(geoDocument,idLink);	
				if (link != null) {
					createDetailShell(crossNavTable.getParent(), link);					
				}
			}
		});

		final TableEditor  editor6 = new TableEditor(crossNavTable);
		erase = new Button(crossNavTable, SWT.PUSH);
		erase.setSize(eraseIcon.createImage().getBounds().width, eraseIcon.createImage().getBounds().height);
		erase.setImage(eraseIcon.createImage());
		if(!isThereLink)erase.setEnabled(false);

		erase.setToolTipText("Delete link");
		editor6.minimumWidth = erase.getBounds().x;
		editor6.horizontalAlignment = SWT.CENTER;
		editor6.grabHorizontal = true;
		editor6.minimumHeight = erase.getBounds().y;
		editor6.verticalAlignment = SWT.TOP;
		editor6.grabVertical = true;
		editor6.setEditor(erase, item, 4);


		erase.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				Integer idLink=null;
				if(hierarchiesCombo.getData()!=null){
					idLink=(Integer)hierarchiesCombo.getData();
				}				
				Link link = LinkBO.getLinkByHierarchyAndLevel(geoDocument,idLink);	
				TableItem item = crossNavTable.getItem(new Point(hierarchiesCombo.getBounds().x, hierarchiesCombo.getBounds().y));

				if (link != null) {
					LinkBO.deleteLink(geoDocument, hierarchiesCombo.getText(), levelCombo.getText());
					getEditor().setIsDirty(true);
				}
				Control old1 = editor1.getEditor();
				if (old1 != null)
					old1.dispose();
				Control old2 = editor2.getEditor();
				if (old2 != null)
					old2.dispose();
				Control old4 = editor4.getEditor();
				if (old4 != null)
					old4.dispose();
				Control old5 = editor5.getEditor();
				if (old5 != null)
					old5.dispose();
				Control old6 = editor6.getEditor();
				if (old6 != null)
					old6.dispose();
				item.dispose();
				crossNavTable.layout(true);
				crossNavTable.redraw();

			}
		});

	}
	private void createDetailShell(Composite sectionClient, final Link link){

		Vector<LinkParam>params = link.getParam();

		final Shell dialog = new Shell (mainComposite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("Optional Parameters");

		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		final Table paramsTable = new Table(dialog, SWT.MULTI | SWT.BORDER	| SWT.FULL_SELECTION);
		paramsTable.setLinesVisible(true);
		paramsTable.setHeaderVisible(true);
		String[] titles = { "  Name  ", "  Type  ", "  Scope  ", "  Value  "};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(paramsTable, SWT.NONE);
			column.setText(titles[i]);		
		}
		if(params != null){
			for(int i = 0; i < params.size(); i++){
				TableItem item = new TableItem(paramsTable, SWT.NONE);
				item.setText(0, params.elementAt(i).getName()!=null ? params.elementAt(i).getName() : "");
				item.setText(1, params.elementAt(i).getType() !=null ? params.elementAt(i).getType() : "");
				item.setText(2, params.elementAt(i).getScope() != null ? params.elementAt(i).getScope() : "");
				item.setText(3, params.elementAt(i).getValue()!=null ? params.elementAt(i).getValue() : "");
			}
		}
		for (int i = 0; i < titles.length; i++) {
			paramsTable.getColumn(i).pack();
		}
		paramsTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.button == 3) {
					createMenu(dialog, paramsTable, link);					
				}
			}
		});
		paramsTable.redraw();

		FormData data = new FormData ();
		data.width = 300;
		paramsTable.setLayoutData (data);

		Button cancel = new Button (dialog, SWT.PUSH);
		cancel.setText ("Close");
		data = new FormData ();
		data.width = 40;
		data.top = new FormAttachment(paramsTable, 5);
		data.right = new FormAttachment (100, 0);
		cancel.setLayoutData (data);
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
//				System.out.println("User cancelled dialog");
				dialog.close ();
			}
		});

		dialog.setDefaultButton (cancel);
		dialog.pack ();
		dialog.open ();

	}
	private void createMenu(final Shell dialog,final Table table, final Link link){		
		Menu menu = new Menu (dialog, SWT.POP_UP);    	
		MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText ("Delete");
		menuItem.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event event) { 
				TableItem[] sel = table.getSelection();
				//delete parameter
				LinkBO.deleteLinkParam(link, sel[0].getText());
				sel[0].dispose();
				table.redraw();

			}
		});	
		table.setMenu(menu);
	}
	private Combo createHierachiesCombo(Table crossNavTable){
		Combo hierCombo = new Combo(crossNavTable, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		Hierarchies hierarchies=HierarchyBO.getAllHierarchies(geoDocument);
		if(hierarchies != null && hierarchies.getHierarchy() != null){
			for(int i=0; i< hierarchies.getHierarchy().size(); i++){
				Hierarchy hier = hierarchies.getHierarchy().elementAt(i);
				String name = hier.getName();
				hierCombo.add(name);				
			}
		}
		return hierCombo;
	}
	private Combo createLevelsCombo(Table crossNavTable, String hierarchyName){
		Combo levelCombo = new Combo(crossNavTable, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		if(hierarchyName != null && !hierarchyName.equals("")){
			Vector<Level> levels=LevelBO.getLevelsByHierarchyName(geoDocument, hierarchyName);
			if(levels != null){
				for(int i=0; i< levels.size(); i++){
					Level level = levels.elementAt(i);
					String name = level.getName();
					levelCombo.add(name);				
				}
			}
		}
		return levelCombo;
	}
	private void recreateLevelsCombo(Table crossNavTable,Combo levelCombo, String hierarchyName){
		levelCombo.removeAll();
		if(hierarchyName != null && !hierarchyName.equals("")){
			Vector<Level> levels=LevelBO.getLevelsByHierarchyName(geoDocument, hierarchyName);
			if(levels != null){
				for(int i=0; i< levels.size(); i++){
					Level level = levels.elementAt(i);
					String name = level.getName();
					levelCombo.add(name);				
				}
			}
		}
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
	public Vector<TableEditor> getTableEditors() {
		return tableEditors;
	}
	public void setTableEditors(Vector<TableEditor> tableEditors) {
		this.tableEditors = tableEditors;
	}

}
