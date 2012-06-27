/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.views;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentParametersView extends ViewPart {

	private DocumentComposition documentComp;
	Composite client;
	Table table;
	private static Logger logger = LoggerFactory.getLogger(DocumentParametersView.class);

	public static final int ID=0;
	public static final int LABEL=1;
	public static final int TYPE=2;
	public static final int URLNAME=3;
	public static final int DEFAULT_VALUE=4;
	public static final int ADD=4;


	public void init(IViewSite site) throws PartInitException {
		// TODO Auto-generated method stub
		super.init(site);
		//documentComp= (new ModelBO()).getModel();
	}

	public void viewSelectedProperties() {


	}



	public void createPartControl(Composite parent) {
logger.debug("IN");
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		// Lets make a layout for the first section of the screen
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		// Creating the Screen
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText("Parameters of selected document: select a row to edit default value"); //$NON-NLS-1$
		client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);


		table = new Table (client, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.CHECK);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = {"Use as Input Parameter","          Label          ", "          Type          ","          UrlName          ", "      Default Value (Editable)    "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
		}	
		for (int i=0; i<titles.length; i++) {
			table.getColumn (i).pack ();
		}	

		final TableEditor editor = new TableEditor(table);


		//		table.addListener(SWT.Selection, new Listener() {
		//			public void handleEvent(Event e) {
		//				TableItem item = (TableItem)e.item;
		//				if(item != null){
		//					String selType = e.detail == SWT.CHECK ? "Checked" : "Selected";
		//					if(selType != null && selType.equals("Checked")){
		//						String columnName = item.getText();
		////						Column col = ColumnBO.getColumnByName(geoDocument, columnName);
		////						col.setChoosenForTemplate(item.getChecked());
		////						setIsDirty(true);
		//					}
		//				}
		//			}
		//		});

		// Add selection listener, could be a selection or a check event
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				ModelBO modelBO = new ModelBO();
				final TableItem item = (TableItem)e.item;
				if(item == null) return;

				if(SWT.CHECK == e.detail){
					IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);					
					if(editorPart!=null) ((DocumentCompositionEditor)editorPart).setIsDirty(true);				

					boolean checked = item.getChecked();
					ParameterBO parameterBO = new ParameterBO();
					// if checked == true is going to be selected, if checked == false is going to be deselected
					try{
						//String parameterId = item.getText(ID);
						DocumentComposition docComposition=Activator.getDefault().getDocumentComposition();
						String documentLabel = item.getData().toString();

						// add parameter to model
						if(checked){  

							Document document = docComposition.retrieveDocumentFromDocumentLabel(documentLabel);
							Vector<Parameter> vectPars = document.getParameters().getParameter();

							// if it is selected add it to document!
							String parameterLabel = item.getText(LABEL);
							String parameterUrl = item.getText(URLNAME);
							String parameterType = item.getText(TYPE);
							String defaultValue = item.getText(DEFAULT_VALUE);						

							Parameter parameter = new Parameter(docComposition);
							parameter.setDefaultVal(defaultValue);
							parameter.setSbiParLabel(parameterUrl);
							parameter.setType("IN");

							// devo mettere il navigation name????
							if(vectPars == null){
								vectPars = new Vector<Parameter>();
							}
							vectPars.add(parameter);
							document.getParameters().setParameter(vectPars);

							int i = 0;
						}
						else // remove parameter from model if not associated with a navigation
						{
							String parameterUrl = item.getText(URLNAME);

							// get parameter ID from template 
							if(parameterUrl == null) {
								logger.error("parameter url not found");
								return;
							}

							Parameter par = parameterBO.getInputParameterByDocumentLabelAndParameterLabel(docComposition, documentLabel, parameterUrl);
							if(par == null) return;

							String navigationId = par.getId();

							// check parameter not used! I must check all documentCOmposition Navigation assuring the id is not used
							String  navigation = parameterBO.isParameterUsedInNavigation(docComposition, navigationId);

							if(navigation != null){	// if used in navigation cannot remove
								MessageDialog.openWarning(table.getShell(), "Warning", "Cannot remove parameter from template; " +
										navigation);
								item.setChecked(true);
							}
							else{	// if not used can remove
								boolean deleted = parameterBO.deleteParameterById(docComposition, navigationId);

							}


						}	
						modelBO.saveModel(docComposition);

					}

					catch (Exception ex) {
						logger.error("Error in treating parameter", ex);
					}
				}
				else // This is selection Mode
				{

					// Clean up any previous editor control
					Control oldEditor = editor.getEditor();
					if (oldEditor != null) oldEditor.dispose();

					// The control that will be the editor must be a child of the Table
					Text newEditor = new Text(table, SWT.NONE);
					newEditor.setText(item.getText(DEFAULT_VALUE));
					newEditor.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							Text text = (Text)editor.getEditor();
							try{
								editor.getItem().setText(DEFAULT_VALUE, text.getText());
								String documentLabel = item.getData().toString();
								String label = item.getText(LABEL);							
								String urlName = item.getText(URLNAME);
								DocumentComposition docComposition=Activator.getDefault().getDocumentComposition();
								Vector<Parameter> vectPars = docComposition.retrieveParametersVectorFromDocumentLabel(documentLabel);
								String defaultVal = editor.getItem().getText(DEFAULT_VALUE);

								// get the parameter to insert the value
								for (Iterator iterator = vectPars.iterator(); iterator.hasNext();) {
									Parameter parameter = (Parameter) iterator.next();
									if(parameter.getSbiParLabel().equals(urlName)){
										parameter.setDefaultVal(defaultVal);
									}

								}
								IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);
								if(editorPart!=null) ((DocumentCompositionEditor)editorPart).setIsDirty(true);				
							}
							catch (Exception e) {
								logger.warn("error in modifying default value");
							}

						}
					});
					newEditor.selectAll();
					newEditor.setFocus();
					editor.setEditor(newEditor, item, DEFAULT_VALUE);

				}
			}
		});



		client.pack();


		toolkit.paintBordersFor(client);
		section.setClient(client);
		viewSelectedProperties();
		setVisible(false);
		logger.debug("OUT");

	}

	public void reloadParametersProperties(MetadataDocument metadataDocument){
		logger.debug("IN");
		table.removeAll();
		if(metadataDocument!=null){		
			DocumentComposition docComposition=Activator.getDefault().getDocumentComposition();


			// check if I can find a default value, these are also all selected!
			Map<String, String> defaults = new HashMap<String, String>();

			try{
				String label = metadataDocument.getLabel();
				Vector<Parameter> vectPars = docComposition.retrieveParametersVectorFromDocumentLabel(label);
				// fill the map with default values
				if(vectPars != null){
					for (Iterator iterator = vectPars.iterator(); iterator.hasNext();) {
						Parameter parameter = (Parameter) iterator.next();
						defaults.put(parameter.getSbiParLabel(), parameter.getDefaultVal());
					}
				}
			}
			catch (Exception e) {
				// exception proof cause next to release!
				logger.error("Error in recovering default value");
			}

			Vector<MetadataParameter> parameters=metadataDocument.getMetadataParameters();
			if(parameters!=null){
				for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
					MetadataParameter metadataParameter = (MetadataParameter) iterator.next();
					TableItem item = new TableItem (table, SWT.NONE);
					// put as data the label f the document
					item.setData(metadataDocument.getLabel());
					//item.setText (ID, metadataParameter.getId()!=null ? metadataParameter.getId().toString() : "");
					item.setText (ID, "");					
					item.setText (LABEL, metadataParameter.getLabel()!=null ? metadataParameter.getLabel() : "");
					item.setText (TYPE, metadataParameter.getType()!=null ? metadataParameter.getType() : "");
					item.setText (URLNAME, metadataParameter.getUrlName()!=null ? metadataParameter.getUrlName() : "");

					// try searching for a default value
					String def = defaults.get(metadataParameter.getUrlName());
					item.setText (DEFAULT_VALUE, def != null ? def : "");
					item.setBackground(DEFAULT_VALUE, new Color(item.getDisplay(), new RGB(220,220,245)));

					// if value is inside the map means it is selected!
					if(defaults.containsKey(metadataParameter.getUrlName())){
						item.setChecked(true);	
					}
					else {
						item.setChecked(false);	
					}

				}
			}
		}
		client.layout();
		client.redraw();
		setVisible(true);
		logger.debug("OUT");

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

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Composite getClient() {
		return client;
	}

	public void setClient(Composite client) {
		this.client = client;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void cleanParameters(){
		table.removeAll();
	}


	public void setVisible(boolean visible){
		client.setVisible(visible);
	}
	public boolean isVisible(){
		return client.isVisible();
	}



}
