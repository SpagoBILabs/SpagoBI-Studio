/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors;


import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;
import it.eng.spagobi.server.services.api.exception.MissingParValueException;
import it.eng.spagobi.server.services.api.exception.NoServerException;
import it.eng.spagobi.studio.geo.editors.model.bo.DatamartProviderBO;
import it.eng.spagobi.studio.geo.editors.model.bo.HierarchyBO;
import it.eng.spagobi.studio.geo.editors.model.bo.LevelBO;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchies;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchy;
import it.eng.spagobi.studio.geo.editors.model.geo.Level;
import it.eng.spagobi.studio.geo.editors.model.geo.Link;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadataField;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.GeoFeature;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HierarchiesDesigner {

	private static Logger logger = LoggerFactory.getLogger(HierarchiesDesigner.class);


	private GEOEditor editor=null;
	private Composite mainComposite;
	private GEODocument geoDocument;
	Label emptyTree=null;

	public HierarchiesDesigner(Composite _composite, GEOEditor _editor) {
		super();
		mainComposite= _composite;
		editor = _editor;
	}

	public HierarchiesDesigner(Composite _composite, GEOEditor _editor, GEODocument _geoDocument) {
		super();
		mainComposite= _composite;
		editor = _editor;
		geoDocument = _geoDocument;
	}
	private void fillHierarchiesTree(Tree hierarchiesTree){
		Hierarchies hierarchies = HierarchyBO.getAllHierarchies(geoDocument);
		//create hierarchies
		if(hierarchies != null && hierarchies.getHierarchy()!= null){
			for(int i=0; i < hierarchies.getHierarchy().size(); i++){
				Hierarchy hierarchy = hierarchies.getHierarchy().elementAt(i);
				TreeItem item = new TreeItem(hierarchiesTree, SWT.NONE);
				item.setText(hierarchy.getName());
				if(hierarchy.getLevels() != null){
					for(int j=0; j < hierarchy.getLevels().size(); j++){
						Level level = hierarchy.getLevels().elementAt(j);
						TreeItem itemLev = new TreeItem(item, SWT.NONE);
						itemLev.setText(level.getName());
					}
				}
			}			
		}
		hierarchiesTree.getParent().getParent().redraw();
	}
	private void createNewHierarchy(Tree hierarchiesTree, String name, String type){		
		TreeItem iItem = new TreeItem(hierarchiesTree, SWT.NONE);
		iItem.setText(name);

		hierarchiesTree.getParent().getParent().redraw();
		//crea oggetto java con name+type
		HierarchyBO.setNewHierarchy(geoDocument, name, type);
		editor.setIsDirty(true);

	}

	private void updateHierarchy(Tree hierarchiesTree, String name, String type, Hierarchy hierarchy){		
		TreeItem iItem = hierarchiesTree.getSelection()[0];
		iItem.setText(name);
		hierarchy.setName(name);
		hierarchy.setType(type);
		editor.setIsDirty(true);

	}
	private void createNewLevel(Tree hierarchiesTree, Level newLevel, TreeItem parent, boolean isDefault){		
		TreeItem iItem = new TreeItem(parent, SWT.NONE);
		iItem.setText(newLevel.getName());

		hierarchiesTree.getParent().getParent().redraw();

		LevelBO.setNewLevel(geoDocument, parent.getText(), newLevel);
		if(isDefault){
			DatamartProviderBO.setHierarchy(geoDocument, parent.getText(), newLevel.getName());
		}
		editor.setIsDirty(true);
	}
	private void updateLevel(Tree hierarchiesTree, Level newLevel, TreeItem parent, Level oldLevel, boolean isDefault){		
		TreeItem iItem = hierarchiesTree.getSelection()[0];
		iItem.setText(newLevel.getName());

		LevelBO.updateLevel(geoDocument, iItem.getParentItem().getText(), oldLevel, newLevel);
		if(isDefault){
			DatamartProviderBO.setHierarchy(geoDocument, parent.getText(), oldLevel.getName());
		}
		hierarchiesTree.getParent().getParent().redraw();
		oldLevel = newLevel;	
		editor.setIsDirty(true);
	}

	private void deleteItem(Tree hierarchiesTree, TreeItem item){
		//elimina oggetto java
		if(item.getParentItem() == null){
			// check hierarchy is not used by cross navigation
			if(canEraseHierarchy(geoDocument, item.getText())){
				//hierarchy--> delete hierarchy
				HierarchyBO.deleteHierarchy(geoDocument, item.getText());
				item.dispose();
				editor.setIsDirty(true);
			}
			else{
				MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Cannot delete hierarchy because is used in cross navigation section");
			}
		}else{
			// check level is not used by cross navigation
			if(canEraseLevel(geoDocument, item.getText())){
				//level--> deleteLevel
				LevelBO.deleteLevel(geoDocument, item.getParentItem().getText(), item.getText());
				item.dispose();
				editor.setIsDirty(true);
			}
			else{
				MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Cannot delete Level because is used in cross navigation section");
			}
		}
		//hierarchiesTree.pack();
		hierarchiesTree.redraw();
	}


	private boolean canEraseHierarchy(GEODocument document, String hierarchyName){
		boolean can=true;
		if(geoDocument.getDatamartProvider()!=null && geoDocument.getDatamartProvider().getCrossNavigation()!=null && geoDocument.getDatamartProvider().getCrossNavigation().getLinks()!=null){
			Vector<Link> links=geoDocument.getDatamartProvider().getCrossNavigation().getLinks();
			for (Iterator iterator = links.iterator(); iterator.hasNext() && can==true;) {
				Link link = (Link) iterator.next();
				if(link.getHierarchy()!=null && link.getHierarchy().equalsIgnoreCase(hierarchyName)){
					can=false;
				}
			}
		}
		return can;
	}

	private boolean canEraseLevel(GEODocument document, String levelName){
		boolean can=true;
		if(geoDocument.getDatamartProvider()!=null && geoDocument.getDatamartProvider().getCrossNavigation()!=null && geoDocument.getDatamartProvider().getCrossNavigation().getLinks()!=null){
			Vector<Link> links=geoDocument.getDatamartProvider().getCrossNavigation().getLinks();
			for (Iterator iterator = links.iterator(); iterator.hasNext() && can==true;) {
				Link link = (Link) iterator.next();
				if(link.getLevel()!=null && link.getLevel().equalsIgnoreCase(levelName)){
					can=false;
				}
			}
		}
		return can;
	}


	private void createMenu(final Composite sectionClient, final Tree hierarchiesTree){

		Menu menu = new Menu (sectionClient.getShell(), SWT.POP_UP);
		MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText ("New Hierarchy");
		menuItem.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event event) { 
				TreeItem[] sel = hierarchiesTree.getSelection();
				if(sel.length>=1 && sel[0] != null && sel[0].getParentItem() != null){//ha selez un livello--> errore
					MessageDialog.openError(sectionClient.getShell(), "Error", "Wrong position");
				}else{
					createNewHierarchyShell(hierarchiesTree, null);
					if(emptyTree!=null && emptyTree.isVisible()){
						emptyTree.setVisible(false);
					}
				}

			}
		});
		menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText ("New Level");
		menuItem.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event event) { 
				TreeItem[] sel = hierarchiesTree.getSelection();
				if(sel[0] != null && sel[0].getParentItem() == null){
					createNewLevelShell(hierarchiesTree, sel[0], null);

				}else{
					MessageDialog.openError(sectionClient.getShell(), "Error", "Wrong position. Please select a hierarchy");
				}

			}
		});	 
		menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText ("Delete");
		menuItem.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event event) { 
				TreeItem[] sel = hierarchiesTree.getSelection();
				if(sel[0] != null){
					deleteItem(hierarchiesTree, sel[0]);
				}else{
					MessageDialog.openWarning(sectionClient.getShell(), "Warning", "Please select an item to delete");
				}

			}
		});	
		hierarchiesTree.setMenu(menu);
	}
	public void createHierarchiesTree(final Composite sectionClient, FormToolkit toolkit){

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan =4;
		gd.heightHint=150;
		gd.minimumHeight=70;
		gd.verticalAlignment=SWT.TOP;
		gd.verticalSpan=1;
		gd.grabExcessVerticalSpace=true;

		final Composite hierarchiesGroup = new Composite(sectionClient, SWT.RESIZE);
		//hierarchiesGroup.setText("Hierarchies");
		hierarchiesGroup.setLayoutData(gd);
		hierarchiesGroup.setLayout(sectionClient.getLayout());

		final Tree hierarchiesTree = toolkit.createTree(hierarchiesGroup, SWT.NONE | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE );
		hierarchiesTree.setLayoutData(gd);

		Color color = new org.eclipse.swt.graphics.Color(sectionClient.getDisplay(), 255,0,0);

		if(geoDocument.getDatamartProvider().getHierarchies() == null || 
				geoDocument.getDatamartProvider().getHierarchies().getHierarchy() == null || 
				geoDocument.getDatamartProvider().getHierarchies().getHierarchy().size() == 0){
			emptyTree = toolkit.createLabel(hierarchiesGroup, "empty hierarchies tree...right click here to create", SWT.CENTER | SWT.TOP);

			emptyTree.setForeground(color);
			emptyTree.setLayoutData(gd);
			emptyTree.addListener(SWT.MouseDown, new Listener () {
				public void handleEvent (Event event) {            	
					if (event.button==3){	
						createNewHierarchyShell(hierarchiesTree, null);   
						emptyTree.setVisible(false);
					}
				}
			});	    
			gd = new GridData(GridData.FILL_BOTH);
			gd.horizontalSpan =4;
			gd.heightHint=50;
			gd.minimumHeight=40;
			gd.verticalSpan=1;
			emptyTree.setLayoutData(gd);
		}else{
			fillHierarchiesTree(hierarchiesTree);
		}

		//mouseDoubleClick --> modify
		hierarchiesTree.addListener(SWT.MouseDoubleClick, new Listener () {
			public void handleEvent (Event event) {
				TreeItem[] sel = hierarchiesTree.getSelection();
				if(sel[0] != null){
					if(sel[0].getParentItem()== null){
						//hierarchy
						Hierarchy hierarchy= HierarchyBO.getHierarchyByName(geoDocument, sel[0].getText());
						createNewHierarchyShell(hierarchiesTree, hierarchy);

					}else{
						//level
						Level level = LevelBO.getLevelByName(geoDocument, sel[0].getParentItem().getText(), sel[0].getText());
						createNewLevelShell(hierarchiesTree, sel[0], level);

					}
				}else{
					MessageDialog.openWarning(sectionClient.getShell(), "Warning", "Please select an item to update");
				}
			}
		});
		//rightClick --> menu
		hierarchiesTree.addListener(SWT.MouseDown, new Listener () {
			public void handleEvent (Event event) {            	
				if (event.button==3){	
					createMenu(sectionClient, hierarchiesTree);	            	            	
				}
			}
		});	    

		hierarchiesGroup.redraw();
		sectionClient.getParent().redraw();
	}
	private void createNewHierarchyShell(final Tree hierarchiesTree, final Hierarchy hierarchy){
		final Shell dialog = new Shell (mainComposite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("New Hierarchy");
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label label = new Label (dialog, SWT.RIGHT);
		label.setText ("Hierarchy name:");
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
		if(hierarchy != null){
			text.setText(hierarchy.getName());
		}
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (label, 0, SWT.CENTER);
		//data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		text.setLayoutData (data);


		//type
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(text, 5);

		Label labelType = new Label (dialog, SWT.RIGHT);
		labelType.setText ("Type:");		
		labelType.setLayoutData (data);

		final Combo textType	= new Combo(dialog, SWT.BORDER);	
		//textType.add("default");
		textType.add("custom");
		//		final Text textType = new Text (dialog, SWT.BORDER);
		textType.select(0);
		if(hierarchy != null){
			if(hierarchy.getType()!=null){
				if(hierarchy.getType().equalsIgnoreCase("default")){
					textType.select(0);
				}
				else{
					textType.select(1);					
				}
			}
		}


		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelType, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelType, 0, SWT.CENTER);
		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		textType.setLayoutData (data);


		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
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
				if(hierarchy == null){
					createNewHierarchy(hierarchiesTree, name, type);
				}else{
					updateHierarchy(hierarchiesTree, name, type, hierarchy);
				}

				dialog.close ();
			}
		});

		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();

	}
	private void createNewLevelShell(final Tree hierarchiesTree, final TreeItem selectedItem, final Level level){

		final Shell dialog = new Shell (mainComposite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("New Level for "+selectedItem);
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label label = new Label (dialog, SWT.RIGHT);
		label.setText ("Level name:");
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
		if(level != null){
			text.setText(level.getName());
		}
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (label, 0, SWT.CENTER);
		text.setLayoutData (data);

		//dataset column 
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(text, 5);
		Label labelColumn = new Label (dialog, SWT.RIGHT);
		labelColumn.setText ("Dataset column:");
		labelColumn.setLayoutData (data);		


		final Combo textColumn = drawColumnIdCombo(dialog);
		if(textColumn == null){
			MessageDialog.openError(mainComposite.getShell(), "Error", "Select a dataset!");
			return;
		}
		if(level != null){
			for(int i=0; i<textColumn.getItemCount();i++){
				String val = textColumn.getItem(i);
				if(val.equals(level.getColumnId())){
					textColumn.select(i);

				}
			}

		}
		data = new FormData ();
		data.width = 125;
		data.left = new FormAttachment (labelColumn, 0, SWT.DEFAULT);
		data.right = new FormAttachment (75, 0);
		data.top = new FormAttachment (labelColumn, 0, SWT.CENTER);
		textColumn.setLayoutData (data);

		//is default
		data = new FormData ();
		data.width = 75;
		data.left = new FormAttachment (textColumn, 5, SWT.DEFAULT);
		data.top = new FormAttachment(text, 5);

		final Button checkIsDef = new Button(dialog, SWT.CHECK | SWT.RIGHT);
		final boolean[] isDefault = new boolean[1];
		isDefault[0] = true;
		checkIsDef.setText("Is default");
		checkIsDef.setSelection(true);
		if(level != null){
			String defHier =geoDocument.getDatamartProvider().getHierarchy();
			String defLev =geoDocument.getDatamartProvider().getLevel();
			if(defHier!=null && selectedItem.getParentItem()!=null && defLev!=null && level!=null && level.getName()!=null){
				if(defHier.equals(selectedItem.getParentItem().getText()) && defLev.equals(level.getName())){
					checkIsDef.setSelection(true);
				}
			}
		}

		checkIsDef.setLayoutData (data);
		checkIsDef.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				isDefault[0] = event.widget == checkIsDef;
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				isDefault[0] = event.widget == checkIsDef;
			}
		});


		//description
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(checkIsDef, 5);
		Label labelDescr = new Label (dialog, SWT.RIGHT);
		labelDescr.setText ("Description:");
		labelDescr.setLayoutData (data);	

		final Text textDescription = new Text (dialog, SWT.BORDER);
		if(level != null){
			textDescription.setText(level.getColumnDesc());
		}
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelDescr, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelDescr, 0, SWT.CENTER);
		textDescription.setLayoutData (data);

		//feature
		data = new FormData ();
		data.width = 100;
		data.top = new FormAttachment(textDescription, 5);
		Label labelFeature = new Label (dialog, SWT.RIGHT);
		labelFeature.setText ("Feature:");
		labelFeature.setLayoutData (data);	

		final Combo textFeature = drawFeaturesNameCombo(dialog);
		if(textFeature == null){
			MessageDialog.openError(mainComposite.getShell(), "Error", "Select a map!");
			return;
		}
		if(level != null){
			for(int i=0; i<textFeature.getItemCount();i++){
				String val = textFeature.getItem(i);
				if(val.equals(level.getFeatureName())){
					textFeature.select(i);
				}
			}

		}
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (labelFeature, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (labelFeature, 0, SWT.CENTER);
		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		textFeature.setLayoutData (data);

		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				//create tree item
				String columnId = textColumn.getText();
				String columnDesc = textDescription.getText();
				String feature = textFeature.getText();
				String name = text.getText();

				Level newLevel = new Level();
				newLevel.setName(name);
				newLevel.setColumnId(columnId);
				newLevel.setColumnDesc(columnDesc);
				newLevel.setFeatureName(feature);
				if(level == null){
					if(selectedItem.getParentItem() != null){ // should not happen because hierarchy node is root
						createNewLevel(hierarchiesTree, newLevel,  selectedItem.getParentItem(), isDefault[0]);
					}
					else{ 
						createNewLevel(hierarchiesTree, newLevel,  selectedItem, isDefault[0]);
					}
				}else{
					if(selectedItem.getParentItem() != null){
						updateLevel(hierarchiesTree, newLevel,  selectedItem.getParentItem(), level, isDefault[0]);
				}
				else{ //should not happen because level node is not root
					updateLevel(hierarchiesTree, newLevel,  selectedItem, level, isDefault[0]);
				}
				}

				dialog.close ();
			}
		});

		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();

	}

	private Combo drawColumnIdCombo(final Shell dialog){
		final Combo textColumn = new Combo(dialog, SWT.SINGLE | SWT.READ_ONLY);



		String datasetLabel=editor.getSelectedDataset();
		if(datasetLabel == null){

			return null;
		}
		IDataStoreMetadata dataStoreMetadata=null;
		// get the metadata
		if(editor.getTempDsMetadataInfos().get(datasetLabel)!=null){
			dataStoreMetadata=editor.getTempDsMetadataInfos().get(datasetLabel);
		}
		else{
			Dataset dataset = editor.getDatasetInfos().get(datasetLabel);

			SpagoBIServerObjectsFactory sbso= null;

			try{
				sbso =new SpagoBIServerObjectsFactory(editor.getProjectName());
			}catch (NoActiveServerException e1) {
				logger.error("No active server found",e1);
				return null;
			}

			try{

				if(dataset.getId() != null){
					dataStoreMetadata=sbso.getServerDatasets().getDataStoreMetadata(dataset.getId());
				}

				if(dataStoreMetadata!=null){
					//editor.getTempDsMetadataInfos().put(datasetLabel, dataStoreMetadata);
				}
				else{
					logger.warn("Dataset returned no metadata");
					MessageDialog.openWarning(mainComposite.getShell(), "Warning", "Dataset with label = "+datasetLabel+" returned no metadata");			
				}
			}
			catch (MissingParValueException e2) {
				logger.error("Could not execute dataset with label = "+datasetLabel+" metadata: execute dataset test in server to retrieve metadata", e2);
				MessageDialog.openError(mainComposite.getShell(), "Error", "Could not execute dataset with label = "+datasetLabel+" metadata: probably missing parameter");
			}
			catch (NoServerException e1) {
				logger.error("Error No comunciation with server retrieving dataset with label = "+datasetLabel+" metadata", e1);
				MessageDialog.openError(mainComposite.getShell(), "Error", "No comunciation with server retrieving dataset with label = "+datasetLabel+" metadata");
			}
		}
		if(dataStoreMetadata!=null){

			for (int i = 0; i < dataStoreMetadata.getFieldsMetadata().length; i++) {
				IDataStoreMetadataField dsmf=dataStoreMetadata.getFieldsMetadata()[i];
				String column = dsmf.getName();
				textColumn.add(column);				
			}
			//dialog.redraw();
		}
		return textColumn;
	}
	private Combo drawFeaturesNameCombo(final Shell dialog){
		final Combo textFeature = new Combo(dialog, SWT.SINGLE | SWT.READ_ONLY);

		GeoFeature[] geoFeatures=null;

		SpagoBIServerObjectsFactory sbso= null;

		try{
			sbso =new SpagoBIServerObjectsFactory(editor.getProjectName());
		}catch (NoActiveServerException e1) {
			logger.error("No active server found",e1);
			return null;
		}

		try{
			geoFeatures=sbso.getServerMaps().getAllFeatures();
			if(geoFeatures==null){
				logger.warn("No features returned");
				MessageDialog.openWarning(mainComposite.getShell(), "Warning", "No features returned");			
			}
		}
		catch (Exception e1) {
			logger.error("Could not get features", e1);
			MessageDialog.openError(mainComposite.getShell(), "Error", "Could not get features");
		}

		if(geoFeatures!=null){
			for (int i = 0; i < geoFeatures.length; i++) {
				GeoFeature geoFeature=geoFeatures[i];
				textFeature.add(geoFeature.getName());


			}
		}
		return textFeature;
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
