/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.pages;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.LoggerFactory;

public class MainChartPage extends AbstractPage {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(MainChartPage.class);

	MainChartLeftPage leftPage;
	MainChartRightPage rightPage;

	ExtChartEditor editor;
	ExtChart extChart;
	String projectName;


	public MainChartPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
	}

	public void drawPage(){
		logger.debug("IN");

		leftPage = new MainChartLeftPage(this, SWT.NULL);
		leftPage.setEditor(editor);
		leftPage.setExtChart(extChart);
		leftPage.setProjectName(projectName);
		
		rightPage = new MainChartRightPage(this, SWT.NULL);
		rightPage.setEditor(editor);
		rightPage.setExtChart(extChart);

		leftPage.drawPage();
		rightPage.drawPage();

		logger.debug("OUT");
	}

	public ExtChartEditor getEditor() {
		return editor;
	}

	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}

	public ExtChart getExtChart() {
		return extChart;
	}

	public void setExtChart(ExtChart extChart) {
		this.extChart = extChart;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public MainChartLeftPage getLeftPage() {
		return leftPage;
	}

	public void setLeftPage(MainChartLeftPage leftPage) {
		this.leftPage = leftPage;
	}

	public MainChartRightPage getRightPage() {
		return rightPage;
	}

	public void setRightPage(MainChartRightPage rightPage) {
		this.rightPage = rightPage;
	}




}
