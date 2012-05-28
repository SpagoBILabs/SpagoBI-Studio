package it.eng.spagobi.studio.extchart.editors.popup;

import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class FieldXYPopup{

	Display display;
	Series serie;
	String value;	

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FieldXYPopup.class);



	public FieldXYPopup(Display display, Series serie, String value) {
		super();
		this.display = display;
		this.serie = serie;
		this.value = value;
	}



	public void drawXYPopup(){
		logger.debug("IN");
		
		
		final Shell dialog = new Shell (display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		GridLayout gridLayout = new GridLayout(2, true);
		dialog.setLayout(gridLayout);
		dialog.setText("Choose X or Y field");
		dialog.setSize(250,200);
		
		FormToolkit toolkit = SWTUtils.createFormToolkit(dialog);
		toolkit.createLabel(dialog, "");
		toolkit.createLabel(dialog, "");
		
		final Button xButton = new Button(dialog, SWT.RADIO);
		xButton.setText("set as X-field");
		xButton.setSelection(true);
		final Button yButton = new Button(dialog, SWT.RADIO);
		yButton.setText("set as Y-field");

		final Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		ok.setLayoutData(gd);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				System.out.println("salva");
				setSelection(xButton, yButton);
				dialog.close ();
			}
		});

		
		

		toolkit.createLabel(dialog, "");
		toolkit.createLabel(dialog, "");
		
		//center the dialog screen to the monitor
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = dialog.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		dialog.setLocation (x, y);
		
		dialog.pack();
		dialog.open ();
		while (!dialog.isDisposed()) {
		    if (!display.readAndDispatch()) {
		        display.sleep();
		    }
		}
		
		
		logger.debug("OUT");
	}

	public void setSelection(Button xbutton, Button yButton){
		if(xbutton.getSelection()==true){
			logger.debug("Set as xField");
			serie.setxField(value);
		}
		else{
			logger.debug("Set as yField");
			serie.setyField(value);
		}
	}

}
