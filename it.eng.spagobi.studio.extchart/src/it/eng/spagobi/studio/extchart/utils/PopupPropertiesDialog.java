package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class PopupPropertiesDialog {

	protected Shell dialog;
	protected FormToolkit toolkit;
	protected ExtChartEditor editor;
	String title;
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(PopupPropertiesDialog.class);
	
	public PopupPropertiesDialog(ExtChartEditor editor, Shell composite) {
		super();
		dialog = new Shell (composite.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		GridLayout gridLayout = new GridLayout(2, true);
		dialog.setLayout(gridLayout);
	
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
		dialog.setText(title);
	}


	public void showPopup(){
		logger.debug("IN");
		Monitor primary = dialog.getDisplay().getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = dialog.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		dialog.setLocation (x, y);
		
		//dialog.pack ();
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!dialog.getDisplay().readAndDispatch()) {
		    	dialog.getDisplay().sleep();
		    }
		}
		logger.debug("OUT");

	}
	
	public void drawButtons(){
		logger.debug("IN");
		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("   OK   ");
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				System.out.println("salva");
				performOk();
				((Shell)dialog).close ();
			}
		});
		ok.setLayoutData(new GridData(GridData.END));

		Button cancel = new Button (dialog, SWT.PUSH);
		cancel.setText ("Cancel");
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				System.out.println("Cancella");
				((Shell)dialog).close ();
			}
		});	
		cancel.setLayoutData(new GridData(GridData.BEGINNING));
		
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
		return dialog;
	}


	public void setDialog(Shell dialog) {
		this.dialog = dialog;
	}


}
