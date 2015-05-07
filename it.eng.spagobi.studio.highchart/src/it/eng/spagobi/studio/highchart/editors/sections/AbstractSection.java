/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.editors.HighChartEditor;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(AbstractSection.class);
	protected Section section;
	protected Composite composite;
	protected HighChart highChart;
	public HighChartEditor editor;

	public HighChart getHighChart() {
		return highChart;
	}

	public void setHighChart(HighChart highChart) {
		this.highChart = highChart;
	}

	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int columns){
		logger.debug("IN");
		section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		composite = toolkit.createComposite(section);
		GridLayout gla = new GridLayout();
		gla.numColumns = columns;
		composite.setLayout(gla);
		
		
		logger.debug("OUT");
	}

	public AbstractSection(HighChart highChart) {
		super();
		this.highChart = highChart;
	}


	public Group createNColGroup(String title, int numCols){
		Group group=new Group(composite,SWT.NULL);
		group.setText(title);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout gl1 = new GridLayout();
		gl1.numColumns = numCols;
		group.setLayout(gl1);
		return group;
	}
	
	public Group createNColGroup(Composite _composite, String title, int numCols){
		Group group=new Group(_composite,SWT.NULL);
		group.setText(title);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout gl1 = new GridLayout();
		gl1.numColumns = numCols;
		group.setLayout(gl1);
		return group;
	}

	
	public HighChartEditor getEditor() {
		return editor;
	}

	
	public void setEditor(HighChartEditor editor) {
		this.editor = editor;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Composite getComposite() {
		return composite;
	}

	public void setComposite(Composite composite) {
		this.composite = composite;
	}


}
