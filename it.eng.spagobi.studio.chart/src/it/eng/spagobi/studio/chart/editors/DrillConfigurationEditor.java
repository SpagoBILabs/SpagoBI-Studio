/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.editors.model.chart.LinkableChartModel;
import it.eng.spagobi.studio.chart.utils.DrillConfiguration;
import it.eng.spagobi.studio.chart.utils.DrillParameters;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author gavardi
 *
 *	Thios class has method for the Drilling configuration parameters form creation (for linkable charts)
 */

public class DrillConfigurationEditor {

	Section sectionDrill = null;
	Composite sectionClientDrill = null;

	// texts of url and common pars
	final Group group;

	final Text serValueText;
	final Text catValueText;
	final Text urlValueText;

	final Text newParName;
	final Text newParVal;
	final Combo newComboType;

	final Table parsTable;

	public static final int NAME=0;
	public static final int VALUE=1;
	public static final int TYPE=2;

	private static Logger logger = LoggerFactory.getLogger(DrillConfigurationEditor.class);

	/**
	 * Constructor of the drillConfiguration Editor
	 * 
	 * @param toolkit
	 * @param form
	 */

	public DrillConfigurationEditor(final LinkableChartModel model, FormToolkit toolkit, final ScrolledForm form) {
		logger.debug("Constructor of drill configuration editor");
		sectionDrill = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE);
		setSectionClientDrill(toolkit.createComposite(sectionDrill));
		logger.debug("Create the drill informations form");
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		sectionDrill.setLayoutData(td);
		sectionDrill.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionDrill.setText("Drill parameters");
		sectionDrill.setDescription("Set all the drill parameteres");

		Composite sectionClientDrill = getSectionClientDrill();
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		sectionClientDrill.setLayout(gl);


		// URL PAR
		Label urlLabel = new Label(sectionClientDrill, SWT.NULL);
		urlLabel.setText("Document Label:");
		urlLabel.pack();
		urlValueText = new Text(sectionClientDrill, SWT.BORDER);
		urlValueText.setToolTipText("The label of the document to drill in");
		if (model.getDrillConfiguration().getUrl() != null) {
			urlValueText.setText(model.getDrillConfiguration().getUrl());
		}
		urlValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		urlValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String parameterValueStr = urlValueText.getText();
				// model.getDrillConfiguration().setUrl(parameterValueStr);
				model.getDrillConfiguration().setUrl(parameterValueStr);
			}
		});
		urlValueText.pack();



		// CAT PAR
		Label catLabel = new Label(sectionClientDrill, SWT.NULL);
		catLabel.setText("Category Url Name:");
		catLabel.pack();
		catValueText = new Text(sectionClientDrill, SWT.BORDER);
		catValueText.setToolTipText("the name with wich the category you choose will be passed to the drill document");
		if (model.getDrillConfiguration().getCategoryUrlName() != null) {
			catValueText.setText(model.getDrillConfiguration()
					.getCategoryUrlName());
		}
		catValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		catValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String parameterValueStr = catValueText.getText();
				// model.getDrillConfiguration().setCategoryUrlName(parameterValueStr);
				model.getDrillConfiguration().setCategoryUrlName(
						parameterValueStr);
			}
		});
		catValueText.pack();


		// SER PAR
		Label serLabel = new Label(sectionClientDrill, SWT.NULL);
		serLabel.setText("Serie Url Name:");
		serLabel.pack();
		serValueText = new Text(sectionClientDrill, SWT.BORDER);
		serValueText.setToolTipText("the name with wich the serie you choose will be passed to the drill document");
		if (model.getDrillConfiguration().getSeriesUrlName() != null) {
			serValueText.setText(model.getDrillConfiguration()
					.getSeriesUrlName());
		}
		serValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String parameterValueStr = serValueText.getText();
				model.getDrillConfiguration().setSeriesUrlName(
						parameterValueStr);
			}
		});
		serValueText.pack();




		group = new Group(sectionClientDrill, SWT.NULL);


		group.setText("ADD PARAMETER");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		group.setLayout(gridLayout);		
		GridData gridDataView = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridDataView.horizontalSpan=2;
		group.setLayoutData(gridDataView);

		final Button buttonAdd = new Button(group, SWT.PUSH);
		buttonAdd.setToolTipText("Add the parameter");		
		buttonAdd.setText("Add");
		buttonAdd.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		buttonAdd.pack();

		final Button buttonCancel = new Button(group, SWT.PUSH);
		buttonCancel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		buttonCancel.setText("Cancel");
		buttonCancel.setToolTipText("Erase Parameter");
		buttonCancel.pack();
		buttonCancel.setEnabled(false);


		//		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
		//		gridData.horizontalSpan = 2;
		//		gridData.horizontalAlignment = GridData.FILL;
		//		group.setLayoutData(gridData);

		parsTable = new Table (group, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		parsTable.setLinesVisible (true);
		parsTable.setHeaderVisible (true);
		GridData g=new GridData(GridData.FILL_BOTH);
		g.horizontalSpan=2;
		g.verticalSpan=2;
		g.grabExcessHorizontalSpace=true;
		g.grabExcessVerticalSpace=true;
		g.heightHint = 200;
		g.widthHint = 400;
		parsTable.setLayoutData(g);
		parsTable.setToolTipText("Parameters to pass");

		String[] titles = {"             Name             ", "           Value           ", "               Type               "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (parsTable, SWT.NONE);
			column.setText (titles [i]);
		}
		if(model.getDrillConfiguration().getDrillParameters()!=null){
			for (Iterator iterator = model.getDrillConfiguration().getDrillParameters().keySet().iterator(); iterator.hasNext();) {
				String parName = (String) iterator.next();
				DrillParameters drillPar=model.getDrillConfiguration().getDrillParameters().get(parName);
				TableItem item = new TableItem (parsTable, SWT.NONE);
				item.setText(NAME, parName);
				if(drillPar.getValue()!=null){
					item.setText(VALUE,drillPar.getValue());
				}
				if(drillPar.getType()!=null){
					item.setText(TYPE,drillPar.getType());
				}
			}
			for (int i=0; i<titles.length; i++) {
				parsTable.getColumn (i).pack ();
			}	

		} 

		parsTable.redraw();
		//parsTable.pack();


		Label newNameLabel = new Label(group, SWT.NULL);
		newNameLabel.setText("Parameter Name: ");
		newNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		
		newNameLabel.pack();
		newParName = new Text(group, SWT.BORDER);
		newParName.setToolTipText("name of a parameter to pass");		
		newParName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		// newParName.setLocation(5,50);
		newParName.pack();



		Label newValLabel = new Label(group, SWT.NULL);
		newValLabel.setText("Parameter Value: ");
		newValLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		
		newValLabel.pack();
		newParVal = new Text(group, SWT.BORDER);
		newParVal.setToolTipText("value of a parameter to pass");		
		newParVal.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		newParVal.pack();
		newParVal.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String val = newParVal.getText();
				int selection = parsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem tableItem=parsTable.getItem(selection);
					String parName=tableItem.getText(NAME);
					DrillParameters drillPar=model.getDrillConfiguration().getDrillParameters().get(parName);
					if(drillPar!=null){drillPar.setValue(val);
					tableItem.setText(VALUE, val);
					}
				}
			}
		});
		


		Label newTypeLabel = new Label(group, SWT.NULL);
		newTypeLabel.setText("Parameter Type: ");
		newTypeLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));				
		newTypeLabel.pack();

		newComboType = new Combo(group, SWT.NULL);
		newComboType.setToolTipText("Type of the parameter to pass: ABSOLUTE means that take the specified value, RELATIVE means that search in request for the value");				
		newComboType.add("RELATIVE");
		newComboType.add("ABSOLUTE");
		newParVal.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		newComboType.select(0);
		newComboType.pack();

		newComboType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);
				String comboText = newComboType.getText();
				int selection = parsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=parsTable.getItem(selection);
					String parNameSelected=item.getText(NAME);
					DrillParameters drillPar=model.getDrillConfiguration().getDrillParameters().get(parNameSelected);
					if(parNameSelected!=null){
						drillPar.setType(comboText);
						item.setText(TYPE,comboText);
					}
				}
			}
		});







		// Add listener that show details of parameter selected
		parsTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				model.getEditor().setIsDirty(true);
				int selection = parsTable.getSelectionIndex();
				TableItem item=parsTable.getItem(selection);
				String parNameSelected = item.getText(NAME);
				DrillParameters drillPar = model.getDrillConfiguration().getDrillParameters().get(parNameSelected);
				newParName.setText(drillPar.getName());
				newParVal.setText(drillPar.getValue()!=null ? drillPar.getValue() : "");
				int indexOf=newComboType.indexOf(drillPar.getType());
				newComboType.select(indexOf);

				buttonCancel.setEnabled(true);
			}
		});


		// Add Button Listener
		Listener addListener = new Listener() {
			public void handleEvent(Event event) {
				model.getEditor().setIsDirty(true);
				String nameToAdd = newParName.getText();
				String valueToAdd = newParVal.getText();
				Map<String, DrillParameters> mapDrillPars = model.getDrillConfiguration().getDrillParameters();
				//if not already present
				if(nameToAdd==null || nameToAdd.equalsIgnoreCase("")){
					logger.warn("Error in inserting parameter, no name specified");
					MessageDialog.openWarning(group.getShell(), "Warning", "Specify a parameter name");
				}
				else if(mapDrillPars.keySet().contains(nameToAdd)){
					logger.warn("Error in inserting parameter, already present or with no name");
					MessageDialog.openWarning(group.getShell(), "Warning", "Parameter name already present");
				}
				else {
					String valueValToAdd = newParVal.getText();
					valueValToAdd=valueValToAdd!=null ? valueValToAdd : "";
					String typeToAdd = newComboType.getItem(newComboType.getSelectionIndex());
					TableItem item = new TableItem (parsTable, SWT.NONE);
					item.setText (NAME, nameToAdd);
					item.setText (VALUE, valueValToAdd);
					item.setText (TYPE, typeToAdd);

					DrillParameters par = new DrillParameters(nameToAdd,
							valueToAdd, typeToAdd);
					par.setValue(valueValToAdd);
					mapDrillPars.put(nameToAdd, par);
					// erase insert fields
					newParName.setText("");
					newParVal.setText("");
					buttonCancel.setEnabled(false);

				}

			}
		};
		buttonAdd.addListener(SWT.Selection, addListener);


		// Add Button Listener
		Listener cancelListener = new Listener() {
			public void handleEvent(Event event) {
				model.getEditor().setIsDirty(true);
				int index = parsTable.getSelectionIndex();
				TableItem item=parsTable.getItem(index);
				String namePar=item.getText(NAME);
				// remove from java list
				if (model.getDrillConfiguration().getDrillParameters()
						.containsKey(namePar)) {
					model.getDrillConfiguration().getDrillParameters().remove(
							namePar);
				}
				parsTable.remove(index);

				buttonCancel.setEnabled(false);

			}
		};
		buttonCancel.addListener(SWT.Selection, cancelListener);


		sectionDrill.setClient(sectionClientDrill);


	}


	public Composite getSectionClientDrill() {
		return sectionClientDrill;
	}

	public void setSectionClientDrill(Composite sectionClientDrill) {
		this.sectionClientDrill = sectionClientDrill;
	}

	public Section getSectionDrill() {
		return sectionDrill;
	}

	public void setSectionDrill(Section sectionDrill) {
		this.sectionDrill = sectionDrill;
	}


	public void setVisible(boolean visible) {
		sectionDrill.setVisible(visible);

	}

	public boolean isVisible() {
		if (sectionDrill.isVisible())
			return true;
		else
			return false;
	}

	public Text getSerValueText() {
		return serValueText;
	}

	public Text getCatValueText() {
		return catValueText;
	}

	public Text getUrlValueText() {
		return urlValueText;
	}

	public void eraseComposite() {
		serValueText.setText("");
		urlValueText.setText("");

		catValueText.setText("");
		newParName.setText("");
		newParVal.setText("");
		newComboType.select(0);
		parsTable.removeAll();
	}

	public void refillFieldsDrillConfiguration(
			final DrillConfiguration drillConfiguration,
			final ChartEditor editor, FormToolkit toolkit,
			final ScrolledForm form) {
		if (drillConfiguration != null) {
			DrillConfiguration drill = drillConfiguration;
			if (drill != null) {
				urlValueText.setText(drill.getUrl() != null ? drill.getUrl()
						: "");
				catValueText.setText(drill.getCategoryUrlName() != null ? drill
						.getCategoryUrlName() : "");
				serValueText.setText(drill.getSeriesUrlName() != null ? drill
						.getSeriesUrlName() : "");

				if (drill.getDrillParameters() != null) {
					for (Iterator iterator = drill.getDrillParameters()
							.keySet().iterator(); iterator.hasNext();) {
						String parName = (String) iterator.next();
						DrillParameters par=drill.getDrillParameters().get(parName);
						TableItem tI=new TableItem(parsTable, SWT.NONE);

						tI.setText(NAME, par.getName());
						tI.setText(VALUE,par.getValue()!=null ? par.getValue():"");
						tI.setText(TYPE,par.getType()!=null ?  par.getType(): newComboType.getItem(newComboType.getSelectionIndex()));

					}
				}
				parsTable.redraw();

			}
		}
	}

}
