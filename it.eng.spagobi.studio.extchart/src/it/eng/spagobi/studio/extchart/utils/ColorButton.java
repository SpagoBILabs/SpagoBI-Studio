package it.eng.spagobi.studio.extchart.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ColorButton {

	Button colorButton;

	Label colorLabel;

	Composite innerSection;

	public Button getColorButton() {
		return colorButton;
	}

	public void setColorButton(Button colorButton) {
		this.colorButton = colorButton;
	}

	public Label getColorLabel() {
		return colorLabel;
	}

	public void setColorLabel(Label colorLabel) {
		this.colorLabel = colorLabel;
	}

	public Composite getInnerSection() {
		return innerSection;
	}

	public void setInnerSection(Composite innerSection) {
		this.innerSection = innerSection;
	}

	public String handleSelctionEvent(Shell parentShell){
		final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
		centerShell.setLocation(
				(parentShell.getSize().x - SWTUtils.COLORDIALOG_WIDTH) / 2,
				(parentShell.getSize().y - SWTUtils.COLORDIALOG_HEIGHT) / 2);
		ColorDialog colorDg = new ColorDialog(centerShell,
				SWT.APPLICATION_MODAL);
		colorDg.setRGB(colorLabel.getBackground().getRGB());
		colorDg.setText("Choose a color");
		RGB rgb = colorDg.open();
		//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
		if (	rgb != null) {
			// Dispose the old color, create the
			// new one, and set into the label
			Color color = colorLabel.getBackground();
			color.dispose();
			Color newColor = new Color(parentShell.getDisplay(), rgb);
			colorLabel.setBackground(newColor);
			String newHexadecimal = SWTUtils.convertRGBToHexadecimal(rgb);
//			RGB color1 = SWTUtils.convertHexadecimalToRGB(newHexadecimal);

			//centerShell.pack();
			centerShell.dispose();
			return newHexadecimal;
		}
		return null;
	}


}
