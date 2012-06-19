package it.eng.spagobi.studio.extchart.editors.pages;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.LoggerFactory;

public class AdvancedChartPage extends AbstractPage {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AdvancedChartPage.class);

	AdvancedChartLeftPage leftPage;

	ExtChartEditor editor;
	ExtChart extChart;
	String projectName;


	public AdvancedChartPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
	}

	public void drawPage(){
		logger.debug("IN");

		leftPage = new AdvancedChartLeftPage(this, SWT.NULL);
		leftPage.setEditor(editor);
		leftPage.setExtChart(extChart);
		leftPage.setProjectName(projectName);

		leftPage.drawPage();

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

	/**
	 * @return the leftPage
	 */
	public AdvancedChartLeftPage getLeftPage() {
		return leftPage;
	}

	/**
	 * @param leftPage the leftPage to set
	 */
	public void setLeftPage(AdvancedChartLeftPage leftPage) {
		this.leftPage = leftPage;
	}






}
