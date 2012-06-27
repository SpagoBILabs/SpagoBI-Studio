/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.util;

//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class takes a map of <label,object> and makes a dialog to let user choose among these
 */
public class ComboSelectionDialog extends Dialog {
	private String input;
	private String message;
	private String label;

	private Combo optionsCombo; 

	String[] options = null;

	private static Logger logger = LoggerFactory.getLogger(ComboSelectionDialog.class);

	/**
	 * InputDialog constructor
	 * 
	 * @param parent the parent
	 */
	public ComboSelectionDialog(Shell parent) {
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

	}

	/**
	 * InputDialog constructor
	 * 
	 * @param parent the parent
	 * @param style the style
	 */
	public ComboSelectionDialog(Shell parent, int style) {
		// Let users override the default styles
		super(parent, style);
		setText("Input Dialog");
		setMessage("Please enter a value:");
	}

	/**
	 * Gets the message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message
	 * 
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the input
	 * 
	 * @return String
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Sets the input
	 * 
	 * @param input the new input
	 */
	public void setInput(String input) {
		this.input = input;
	}





	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}



	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public String open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());

		shell.setSize(200, 200);
		Display display = shell.getDisplay();
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		createContents(shell);
		shell.pack();
		shell.open();
		Display displayParent = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!displayParent.readAndDispatch()) {
				displayParent.sleep();
			}
		}
		// Return the entered value, or null
		return input;
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell the dialog window
	 */
	private void createContents(final Shell shell) {
		logger.debug("IN");
		shell.setLayout(new GridLayout(2, true));

		new Label(shell, SWT.NONE).setText("");
		new Label(shell, SWT.NONE).setText("");

		// Show the message
		Label label = new Label(shell, SWT.NONE);
		label.setText(message);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);
		optionsCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		optionsCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		Arrays.sort(options);
		optionsCombo.setItems(options);

		new Label(shell, SWT.NONE).setText("");
		new Label(shell, SWT.NONE).setText("");
		new Label(shell, SWT.NONE).setText("");
		new Label(shell, SWT.NONE).setText("");

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("Deploy");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int index = optionsCombo.getSelectionIndex();
				if(index != -1) 
					input = optionsCombo.getItem(index);
				else input = null;
				shell.close();
			}
		});

		//		// Create the cancel button and add a handler
		//		// so that pressing it will set input to null
		//		Button cancel = new Button(shell, SWT.PUSH);
		//		cancel.setText("Cancel");
		//		data = new GridData(GridData.FILL_HORIZONTAL);
		//		cancel.setLayoutData(data);
		//		cancel.addSelectionListener(new SelectionAdapter() {
		//			public void widgetSelected(SelectionEvent event) {
		//				input = null;
		//				shell.close();
		//			}
		//		});

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);
		logger.debug("OUT");
	}


}



