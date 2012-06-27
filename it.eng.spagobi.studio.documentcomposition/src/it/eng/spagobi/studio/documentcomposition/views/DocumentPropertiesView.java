/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.views;

import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;

import java.util.HashMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.ui.part.ViewPart;

public class DocumentPropertiesView extends ViewPart {

	private DocumentComposition documentComp;
	Label idLabelName;
	Label idLabelValue;
	Label labelLabelName;
	Label labelLabelValue;
	Label nameLabelName;
	Label nameLabelValue;
	Label descriptionLabelName;
	Label descriptionLabelValue;
	Label typeLabelName;
	Label typeLabelValue;
	Label engineLabelName;
	Label engineLabelValue;
	Label dataSetLabelName;
	Label dataSetLabelValue;
	Label dataSourceLabelName;
	Label dataSourceLabelValue;
	Text textStyle;
	Button automaticButton;
	Button manualButton;
	MetadataDocument metadataDocument;
	// Style parameters, if present means we are in manual mode
	HashMap<Integer, String> styleParameters=new HashMap<Integer, String>();
	Integer id;
	String styleCurrent;
	boolean manualMode=false;

	Composite client;
	Table table;
	public static final int ID=0;
	public static final int LABEL=1;
	public static final int NAME=2;
	public static final int DESCRIPTION=3;
	public static final int TYPE=4;
	public static final int ENGINE=5;
	public static final int DATA_SET=6;
	public static final int DATA_SOURCE=7;
	public static final int STATE=8;

	public void setFocus() {
	}

	public void init(IViewSite site) throws PartInitException {
		// TODO Auto-generated method stub
		super.init(site);
		//documentComp= (new ModelBO()).getModel();
	}

	public void viewSelectedProperties() {


	}



	public void createPartControl(Composite parent) {

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		// Lets make a layout for the first section of the screen
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		// Creating the Screen
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText("Properties of selected document"); //$NON-NLS-1$
		client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);

		Composite comp=new Composite(client, SWT.NULL);			
		GridLayout gl=new GridLayout();
		gl.numColumns=4;
		comp.setLayout(gl);
		Label label=new Label(comp, SWT.NULL);
		label.setText("Style info: ");
		textStyle=new Text(comp, SWT.BORDER);
		textStyle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textStyle.setBounds(new Rectangle(10,10,500,10));
		textStyle.setEditable(false);
		// put in map the style
		textStyle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				String t = textStyle.getText();
				if(t!=null && !t.equalsIgnoreCase("") && manualMode==true){
//					System.out.println(id);
					styleParameters.put(id, t);
					if(metadataDocument!=null){
						(new ModelBO()).updateModelModifyDocument(metadataDocument, new Style(t));
					}
					IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
					IWorkbenchPage aa=a.getActivePage();
					
					IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);
					if(editorPart!=null) ((DocumentCompositionEditor)editorPart).setIsDirty(true);
				
				}

			}
		});
		automaticButton = new Button(comp, SWT.RADIO);
		automaticButton.setText("Auto");
		automaticButton.setSelection(true);
		manualButton = new Button(comp, SWT.RADIO);
		manualButton.setText("Manual");

		automaticButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				enableManualMode(false);
			}
		});
		manualButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				enableManualMode(true);
			}
		});

		table = new Table (client, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = {"        Property        ", "              Value              "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
		}	

		TableItem item = new TableItem (table, SWT.NONE,ID);
		item.setText (0, "Id: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,LABEL);
		item.setText (0, "Label: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,NAME);
		item.setText (0, "Name: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,DESCRIPTION);
		item.setText (0, "Description: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,TYPE);
		item.setText (0, "Type: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,ENGINE);
		item.setText (0, "Engine: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,DATA_SET);
		item.setText (0, "Data Set: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,DATA_SOURCE);
		item.setText (0, "Data Source: ");
		item.setText (1, "");
		item = new TableItem (table, SWT.NONE,STATE);
		item.setText (0, "State: ");
		item.setText (1, "");
		for (int i=0; i<titles.length; i++) {
			table.getColumn (i).pack ();
		}	
		client.pack();


		toolkit.paintBordersFor(client);
		section.setClient(client);
		viewSelectedProperties();
		setVisible(false);		
	}

	public void reloadProperties(MetadataDocument documentMeta){
		String id=(documentMeta!=null && documentMeta.getId()!=null) ? documentMeta.getId().toString() : "";
		String label=(documentMeta!=null && documentMeta.getLabel()!=null) ? documentMeta.getLabel() : "";
		String name=(documentMeta!=null && documentMeta.getName()!=null) ? documentMeta.getName() : "";
		String description=(documentMeta!=null && documentMeta.getDescription()!=null) ? documentMeta.getDescription() : "";
		String type=(documentMeta!=null && documentMeta.getType()!=null) ? documentMeta.getType() : "";
		String engine=(documentMeta!=null && documentMeta.getEngine()!=null) ? documentMeta.getEngine() : "";
		String dataSet=(documentMeta!=null && documentMeta.getDataSet()!=null) ? documentMeta.getDataSet() : "";
		String dataSource=(documentMeta!=null && documentMeta.getDataSource()!=null) ? documentMeta.getDataSource() : "";
		String state=(documentMeta!=null && documentMeta.getState()!=null) ? documentMeta.getState() : "";

		table.getItem(ID).setText(1, id );
		table.getItem(LABEL).setText(1, label);
		table.getItem(NAME).setText(1, name);
		table.getItem(DESCRIPTION).setText(1, description);
		table.getItem(TYPE).setText(1, type);
		table.getItem(ENGINE).setText(1, engine);
		table.getItem(DATA_SET).setText(1, dataSet);
		table.getItem(DATA_SOURCE).setText(1, dataSource);
		table.getItem(STATE).setText(1, state);
		client.layout();
		client.redraw();
		setVisible(true);
	}

	public void reloadStyle(Integer docContainerId, String style, MetadataDocument _metadataDocument){
		// check if present document is in manual mode
		setVisible(true);
		String stylePrec=styleParameters.get(docContainerId);
		id=docContainerId;
		metadataDocument=_metadataDocument;
		styleCurrent=style;
		if(stylePrec==null){ // set  automatic mode
			enableManualMode(false);
			textStyle.setText(style);
			textStyle.redraw();
		}
		else{ 				// set manual model
			enableManualMode(true);
			textStyle.setText(stylePrec);
			textStyle.redraw();			
		}

		client.layout();
		client.redraw();


	}


	public void enableManualMode(boolean manual){
		if(manual==false){
			manualMode=false;
			automaticButton.setSelection(true);
			manualButton.setSelection(false);
			textStyle.setEditable(false);
			textStyle.setText(styleCurrent!=null ? styleCurrent : "");
			//textStyle.pack();
		}
		else{
			manualMode=true;
			automaticButton.setSelection(false);
			manualButton.setSelection(true);
			textStyle.setEditable(true);
			if(styleParameters.get(id)!=null){
				styleParameters.remove(id);
			}
		}

	}

	public void cleanSizeAndProperties(){
		automaticButton.setEnabled(true);
		textStyle.setText("");
		id=null;
		styleCurrent=null;
		TableItem[] tableItems=table.getItems();
		for (int i = 0; i < tableItems.length; i++) {
			tableItems[i].setText(1,"");
		}
		enableManualMode(false);
		styleParameters=new HashMap<Integer, String>();
		setVisible(false);
	}



	@Override
	public IViewSite getViewSite() {
		// TODO Auto-generated method stub
		return super.getViewSite();
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		// TODO Auto-generated method stub
		super.init(site, memento);
	}

	@Override
	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub
		super.saveState(memento);
	}

	@Override
	protected void setContentDescription(String description) {
		// TODO Auto-generated method stub
		super.setContentDescription(description);
	}

	@Override
	public void setInitializationData(IConfigurationElement cfig,
			String propertyName, Object data) {
		// TODO Auto-generated method stub
		super.setInitializationData(cfig, propertyName, data);
	}

	@Override
	protected void setPartName(String partName) {
		// TODO Auto-generated method stub
		super.setPartName(partName);
	}



	public DocumentComposition getDocumentComp() {
		return documentComp;
	}

	public void setDocumentComp(DocumentComposition documentComp) {
		this.documentComp = documentComp;
	}

	public Label getIdLabelName() {
		return idLabelName;
	}

	public void setIdLabelName(Label idLabelName) {
		this.idLabelName = idLabelName;
	}

	public Label getIdLabelValue() {
		return idLabelValue;
	}

	public void setIdLabelValue(Label idLabelValue) {
		this.idLabelValue = idLabelValue;
	}

	public Label getLabelLabelName() {
		return labelLabelName;
	}

	public void setLabelLabelName(Label labelLabelName) {
		this.labelLabelName = labelLabelName;
	}

	public Label getLabelLabelValue() {
		return labelLabelValue;
	}

	public void setLabelLabelValue(Label labelLabelValue) {
		this.labelLabelValue = labelLabelValue;
	}

	public Label getNameLabelName() {
		return nameLabelName;
	}

	public void setNameLabelName(Label nameLabelName) {
		this.nameLabelName = nameLabelName;
	}

	public Label getNameLabelValue() {
		return nameLabelValue;
	}

	public void setNameLabelValue(Label nameLabelValue) {
		this.nameLabelValue = nameLabelValue;
	}

	public Label getDescriptionLabelName() {
		return descriptionLabelName;
	}

	public void setDescriptionLabelName(Label descriptionLabelName) {
		this.descriptionLabelName = descriptionLabelName;
	}

	public Label getDescriptionLabelValue() {
		return descriptionLabelValue;
	}

	public void setDescriptionLabelValue(Label descriptionLabelValue) {
		this.descriptionLabelValue = descriptionLabelValue;
	}

	public Label getTypeLabelName() {
		return typeLabelName;
	}

	public void setTypeLabelName(Label typeLabelName) {
		this.typeLabelName = typeLabelName;
	}

	public Label getTypeLabelValue() {
		return typeLabelValue;
	}

	public void setTypeLabelValue(Label typeLabelValue) {
		this.typeLabelValue = typeLabelValue;
	}

	public Label getEngineLabelName() {
		return engineLabelName;
	}

	public void setEngineLabelName(Label engineLabelName) {
		this.engineLabelName = engineLabelName;
	}

	public Label getEngineLabelValue() {
		return engineLabelValue;
	}

	public void setEngineLabelValue(Label engineLabelValue) {
		this.engineLabelValue = engineLabelValue;
	}

	public Label getDataSetLabelName() {
		return dataSetLabelName;
	}

	public void setDataSetLabelName(Label dataSetLabelName) {
		this.dataSetLabelName = dataSetLabelName;
	}

	public Label getDataSetLabelValue() {
		return dataSetLabelValue;
	}

	public void setDataSetLabelValue(Label dataSetLabelValue) {
		this.dataSetLabelValue = dataSetLabelValue;
	}

	public Label getDataSourceLabelName() {
		return dataSourceLabelName;
	}

	public void setDataSourceLabelName(Label dataSourceLabelName) {
		this.dataSourceLabelName = dataSourceLabelName;
	}

	public Label getDataSourceLabelValue() {
		return dataSourceLabelValue;
	}

	public void setDataSourceLabelValue(Label dataSourceLabelValue) {
		this.dataSourceLabelValue = dataSourceLabelValue;
	}

	public Composite getClient() {
		return client;
	}

	public void setClient(Composite client) {
		this.client = client;
	}

	public boolean isManualMode() {
		return manualMode;
	}

	public void setManualMode(boolean manualMode) {
		this.manualMode = manualMode;
	}


	public void setVisible(boolean visible){
		client.setVisible(visible);
	}
	public boolean isVisible(){
		return client.isVisible();
		}

	public HashMap<Integer, String> getStyleParameters() {
		return styleParameters;
	}

	public void setStyleParameters(HashMap<Integer, String> styleParameters) {
		this.styleParameters = styleParameters;
	}






}
