/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class PopupPropertiesDialog {

	protected Shell dialogMain;
	protected Composite dialog;
	protected Composite dialogDescription;
	protected FormToolkit toolkit;
	protected ExtChartEditor editor;
	String title;
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(PopupPropertiesDialog.class);
	
	public PopupPropertiesDialog(ExtChartEditor editor, Shell composite) {
		super();
		dialogMain = new Shell (composite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		//GridLayout gridLayout = new GridLayout(1, true);
		//dialogMain.setLayout(gridLayout);
		dialogMain.setLayout(new GridLayout(1, false));
		
		dialogDescription = new Composite(dialogMain,SWT.NONE);
		dialogDescription.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
		dialogDescription.setLayout(new GridLayout(1, false));

		dialog = new Composite(dialogMain,SWT.NONE);
		dialog.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		dialog.setLayout(new GridLayout(2, false));

	
	}

	
	public void drawProperties(){
		logger.debug("IN");
		logger.debug("OUT");
	}
	
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
		dialogMain.setText(title);
	}


	public void showPopup(){
		logger.debug("IN");
		Monitor primary = dialogMain.getDisplay().getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = dialogMain.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		dialogMain.setLocation (x, y);
		
		//dialog.pack ();
		dialogMain.open ();
		while (!dialogMain.isDisposed()) {
		    if (!dialogMain.getDisplay().readAndDispatch()) {
		    	dialogMain.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}
	
	public void drawButtons(){
		logger.debug("IN");
		/*
		Composite compButton = new Composite(dialogMain,SWT.NONE);
		GridLayout gridLayoutComp = new GridLayout(2, false);
		compButton.setLayout(gridLayoutComp);
		*/
		Composite compButton = new Composite(dialogMain, SWT.NONE);
		compButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compButton.setLayout(new GridLayout(2, false));


		Button ok = new Button (compButton, SWT.PUSH);
		ok.setText ("   OK   ");
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				System.out.println("salva");
				performOk();
				((Shell)dialogMain).close ();
			}
		});
		ok.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));


		Button cancel = new Button (compButton, SWT.PUSH);
		cancel.setText ("Cancel");
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				System.out.println("Cancella");
				((Shell)dialogMain).close ();
			}
		});	
		cancel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		logger.debug("OUT");
	}
	
	public void performOk(){
		
	}
	
	public ExtChartEditor getEditor() {
		return editor;
	}


	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}


	public Shell getDialog() {
		return dialogMain;
	}


	public void setDialog(Shell dialog) {
		this.dialogMain = dialog;
	}

	


}
