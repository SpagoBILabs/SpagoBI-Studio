package it.eng.spagobi.studio.console.editors;


import it.eng.spagobi.studio.console.editors.pages.DatasetPage;
import it.eng.spagobi.studio.console.editors.pages.DetailPanelPage;
import it.eng.spagobi.studio.console.editors.pages.SummaryPanelPage;
import it.eng.spagobi.studio.console.model.bo.ConsoleTemplateModel;
import it.eng.spagobi.studio.console.model.bo.JsonTemplateGenerator;
import it.eng.spagobi.studio.utils.exceptions.SavingEditorException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.ide.IDE;
import org.slf4j.LoggerFactory;



public class ConsoleEditor extends MultiPageEditorPart implements IResourceChangeListener{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConsoleEditor.class);
	protected ConsoleTemplateModel consoleTemplateModel = null;
	
	String projectname = null;
	protected boolean isDirty = false;

	IFile file;

	
	private DatasetPage datasetPage;
	private SummaryPanelPage summaryPanelPage;
	private DetailPanelPage detailPanelPage;
	

	/**
	 * Creates a multi-page editor example.
	 */
	public ConsoleEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createDatasetPage();
		createSummaryPanelPage();
		createDetailPanelPage();
	}
	
	private void createDatasetPage() {
		logger.debug("IN");
		
		datasetPage= new DatasetPage(getContainer(), SWT.NONE);
		datasetPage.setEditor(this);
		datasetPage.setConsoleTemplateModel(consoleTemplateModel);
		datasetPage.setProjectName(projectname);
		datasetPage.drawPage();

		int index = addPage(datasetPage);
		setPageText(index, "Dataset");
		logger.debug("OUT");
	}
	
	private void createSummaryPanelPage() {
		logger.debug("IN");
		

		summaryPanelPage= new SummaryPanelPage(getContainer(), SWT.NONE);
		summaryPanelPage.setEditor(this);
		summaryPanelPage.setConsoleTemplateModel(consoleTemplateModel);
		summaryPanelPage.setProjectName(projectname);
		summaryPanelPage.drawPage();

		int index = addPage(summaryPanelPage);
		setPageText(index, "Summary Panel");
		logger.debug("OUT");		
	}
	
	private void createDetailPanelPage() {
		logger.debug("IN");
		

		detailPanelPage= new DetailPanelPage(getContainer(), SWT.NONE);
		detailPanelPage.setEditor(this);
		detailPanelPage.setConsoleTemplateModel(consoleTemplateModel);
		detailPanelPage.setProjectName(projectname);
		detailPanelPage.drawPage();

		int index = addPage(detailPanelPage);
		setPageText(index, "Detail Panel");
		logger.debug("OUT");		
	}
	
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		logger.debug("IN");
		
		// checks on chart before saving
		//SaveChecks saveChecks =new SaveChecks();
		//saveChecks.checksBeforeSave(extChart, this);
		
		ByteArrayInputStream bais = null;
		// reload file
		try {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			String newContent =  JsonTemplateGenerator.transformToJson(consoleTemplateModel);
			byte[] bytes = newContent.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);
						

		} 
		catch (SavingEditorException e) {
			logger.error("Error while Saving console template: \n reason is "+e.getSavingMessage(),e);
			MessageDialog.openWarning(datasetPage.getShell(), "Cannot save chart:", e.getSavingMessage());
		}
		catch (Exception e2) {
			logger.error("Error while Saving Console Template File",e2);
			MessageDialog.openError(datasetPage.getShell(), "Error during saving", e2.getMessage());
		}

		finally { 
			if (bais != null)
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		setIsDirty(false);
		logger.debug("OUT");
	}

	public void setIsDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}
	
	@Override
	public boolean isDirty() {
		return isDirty;
	}
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput input)
		throws PartInitException {
		this.setPartName(input.getName());
		logger.debug("Start Editor Initialization");		
		FileEditorInput fei = (FileEditorInput) input;
		file = fei.getFile();
		projectname = file.getProject().getName();

		try {
			// Create the model of the Console Template that will store informations
			consoleTemplateModel = JsonTemplateGenerator.readJson(file); 

		} catch (Exception e) {
			logger.error("Error during template reading "+e.getMessage(),e);
			MessageDialog.openError(site.getShell(), "Error", "Error during template reading "+e.getMessage());
			return;
		}

		setInput(input);
		setSite(site);
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){

	}

	/**
	 * @return the consoleTemplateModel
	 */
	public ConsoleTemplateModel getConsoleTemplateModel() {
		return consoleTemplateModel;
	}

	/**
	 * @param consoleTemplateModel the consoleTemplateModel to set
	 */
	public void setConsoleTemplateModel(ConsoleTemplateModel consoleTemplateModel) {
		this.consoleTemplateModel = consoleTemplateModel;
	}

	/**
	 * @return the datasetPage
	 */
	public DatasetPage getDatasetPage() {
		return datasetPage;
	}

	/**
	 * @param datasetPage the datasetPage to set
	 */
	public void setDatasetPage(DatasetPage datasetPage) {
		this.datasetPage = datasetPage;
	}

	/**
	 * @return the summaryPanelPage
	 */
	public SummaryPanelPage getSummaryPanelPage() {
		return summaryPanelPage;
	}

	/**
	 * @param summaryPanelPage the summaryPanelPage to set
	 */
	public void setSummaryPanelPage(SummaryPanelPage summaryPanelPage) {
		this.summaryPanelPage = summaryPanelPage;
	}

	/**
	 * @return the detailPanelPage
	 */
	public DetailPanelPage getDetailPanelPage() {
		return detailPanelPage;
	}

	/**
	 * @param detailPanelPage the detailPanelPage to set
	 */
	public void setDetailPanelPage(DetailPanelPage detailPanelPage) {
		this.detailPanelPage = detailPanelPage;
	}


}
