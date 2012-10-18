/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors;


import it.eng.spagobi.studio.extchart.editors.pages.AdvancedChartPage;
import it.eng.spagobi.studio.extchart.editors.pages.MainChartPage;
import it.eng.spagobi.studio.extchart.model.XmlTemplateGenerator;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SaveChecks;
import it.eng.spagobi.studio.utils.exceptions.SavingEditorException;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

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
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.slf4j.LoggerFactory;

/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class ExtChartEditor extends MultiPageEditorPart implements IResourceChangeListener{





	/** The text editor used in page 0. */
//	private TextEditor editor;
	/** The font chosen in page 1. */
//	private Font font;
	/** The text widget used in page 2. */
//	private StyledText text;


	/** the model **/
	protected ExtChart extChart = null;

	protected boolean isDirty = false;

	private MainChartPage mainChartPage;
	private AdvancedChartPage advancedChartPage;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExtChartEditor.class);

	IFile file;

	String projectname = null;

	/**
	 * Creates a multi-page editor example.
	 */
	public ExtChartEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Creates page 0 which contains general properties
	 */

	void createMainPage() {
		logger.debug("IN");
		//		setIsDirty(true);
		

		mainChartPage= new MainChartPage(getContainer(), SWT.NONE);
		mainChartPage.setEditor(this);
		mainChartPage.setExtChart(extChart);
		mainChartPage.setProjectName(projectname);
		mainChartPage.drawPage();

		int index = addPage(mainChartPage);
		setPageText(index, "Properties");
		logger.debug("OUT");
	}
	
	/**
	 * Creates page 0 which contains general properties
	 */

	void createAdvancedPage() {
		logger.debug("IN");
		
		advancedChartPage= new AdvancedChartPage(getContainer(), SWT.NONE);
		advancedChartPage.setEditor(this);
		advancedChartPage.setExtChart(extChart);
		advancedChartPage.setProjectName(projectname);
		advancedChartPage.drawPage();

		int index = addPage(advancedChartPage);
		setPageText(index, "Advanced");
		logger.debug("OUT");
	}	



	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
//	void createPage0() {
//		try {
//			editor = new TextEditor();
//			int index = addPage(editor, getEditorInput());
//			setPageText(index, editor.getTitle());
//		} catch (PartInitException e) {
//			ErrorDialog.openError(
//					getSite().getShell(),
//					"Error creating nested text editor",
//					null,
//					e.getStatus());
//		}
//	}

	/**
	 * Creates page 1 of the multi-page editor,
	 * which allows you to change the font used in page 2.
	 */
//	void createPage1() {
//
//		Composite composite = new Composite(getContainer(), SWT.NONE);
//		GridLayout layout = new GridLayout();
//		composite.setLayout(layout);
//		layout.numColumns = 2;
//
//		Button fontButton = new Button(composite, SWT.NONE);
//		GridData gd = new GridData(GridData.BEGINNING);
//		gd.horizontalSpan = 2;
//		fontButton.setLayoutData(gd);
//		fontButton.setText("Change Font...");
//
//		fontButton.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent event) {
//				setFont();
//			}
//		});
//
//		int index = addPage(composite);
//		setPageText(index, "Properties");
//	}
	/**
	 * Creates page 2 of the multi-page editor,
	 * which shows the sorted text.
	 */
//	void createPage2() {
//		Composite composite = new Composite(getContainer(), SWT.NONE);
//		FillLayout layout = new FillLayout();
//		composite.setLayout(layout);
//		text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
//		text.setEditable(false);
//
//		int index = addPage(composite);
//		setPageText(index, "Preview");
//	}
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		SWTUtils.createFormToolkit(getContainer());
//		createPage0();
//		createPage1();
//		createPage2();
		createMainPage();
		createAdvancedPage();
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
		//		getEditor(0).doSave(monitor);
		logger.debug("IN");
	
		// checks on chart before saving
		SaveChecks saveChecks =new SaveChecks();
		saveChecks.checksBeforeSave(extChart, this);
		
		ByteArrayInputStream bais = null;
		// reload styles
		try {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			String newContent =  XmlTemplateGenerator.transformToXml(extChart);
			byte[] bytes = newContent.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);
						
			setDatasetMetadata(file, extChart);

		} 
		catch (SavingEditorException e) {
			logger.error("Error while Saving chart: \n reason is "+e.getSavingMessage(),e);
			MessageDialog.openWarning(mainChartPage.getShell(), "Cannot save chart:", e.getSavingMessage());
		}
		catch (Exception e2) {
			logger.error("Error while Saving ExtChart Template File",e2);
			MessageDialog.openError(mainChartPage.getShell(), "Error during saving", e2.getMessage());
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

	
	public void setDatasetMetadata(IFile file, ExtChart extChart){
		logger.debug("IN");
		String label = extChart.getDataset().getLabel();
		try{
			if(label != null){
				file.setPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL_INSIDE, label);
				logger.debug("Set dataset with label "+label);
			}
			else{
				file.setPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL_INSIDE, "");
			}
		}
		catch (CoreException e) {
			logger.error("Could not set dataset metadat property, go on anyway");
		}

		logger.debug("OUT");
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
			// Create the model of the chart that will store informations
			extChart = XmlTemplateGenerator.readXml(file); 
			//file.getPaaa recupera daaset dai metadati e metti quello

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
		return true;
	}
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
//	protected void pageChange(int newPageIndex) {
//		super.pageChange(newPageIndex);
//		if (newPageIndex == 2) {
//			sortWords();
//		}
//	}
	/**
	 * Closes all project files on project close.
	 */
//	public void resourceChanged(final IResourceChangeEvent event){
//		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
//			Display.getDefault().asyncExec(new Runnable(){
//				public void run(){
//					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
//					for (int i = 0; i<pages.length; i++){
//						if(((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())){
//							IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
//							pages[i].closeEditor(editorPart,true);
//						}
//					}
//				}            
//			});
//		}
//	}
	/**
	 * Sets the font related data to be applied to the text in page 2.
	 */
//	void setFont() {
//		FontDialog fontDialog = new FontDialog(getSite().getShell());
//		fontDialog.setFontList(text.getFont().getFontData());
//		FontData fontData = fontDialog.open();
//		if (fontData != null) {
//			if (font != null)
//				font.dispose();
//			font = new Font(text.getDisplay(), fontData);
//			text.setFont(font);
//		}
//	}
	/**
	 * Sorts the words in page 0, and shows them in page 2.
	 */
//	void sortWords() {
//
//		String editorText =
//			editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
//
//		StringTokenizer tokenizer =
//			new StringTokenizer(editorText, " \t\n\r\f!@#\u0024%^&*()-_=+`~[]{};:'\",.<>/?|\\");
//		ArrayList editorWords = new ArrayList();
//		while (tokenizer.hasMoreTokens()) {
//			editorWords.add(tokenizer.nextToken());
//		}
//
//		Collections.sort(editorWords, Collator.getInstance());
//		StringWriter displayText = new StringWriter();
//		for (int i = 0; i < editorWords.size(); i++) {
//			displayText.write(((String) editorWords.get(i)));
//			displayText.write(System.getProperty("line.separator"));
//		}
//		text.setText(displayText.toString());
//	}

	public MainChartPage getMainChartPage() {
		return mainChartPage;
	}

	public void setMainChartPage(MainChartPage mainChartPage) {
		this.mainChartPage = mainChartPage;
	}

	/**
	 * @return the advancedChartPage
	 */
	public AdvancedChartPage getAdvancedChartPage() {
		return advancedChartPage;
	}

	/**
	 * @param advancedChartPage the advancedChartPage to set
	 */
	public void setAdvancedChartPage(AdvancedChartPage advancedChartPage) {
		this.advancedChartPage = advancedChartPage;
	}

	public ExtChart getExtChart() {
		return extChart;
	}

	public void setExtChart(ExtChart extChart) {
		this.extChart = extChart;
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
	}



}
