/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.util;

import it.eng.spagobi.studio.geo.editors.GEOEditor;
import it.eng.spagobi.studio.geo.editors.model.geo.Layer;

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

public class DesignerUtils {

	static public final int COLORDIALOG_WIDTH = 222;
	static public final int COLORDIALOG_HEIGHT = 306;


	public static Composite createColorPicker(Composite parent, String colorHex){
		//		Composite innerSection = toolkit.createComposite(section);
		final Composite innerSection = new Composite(parent, SWT.BORDER);
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);
		final Label colorLabel = new Label(innerSection, SWT.BORDER);
		colorLabel.setText("           ");
		colorLabel.setSize(50, 20);
		RGB rgb=null;
		if(colorHex!=null){
			//String hexadecimal = aParameter.getValue().toString();
			try{
				rgb= convertHexadecimalToRGB(colorHex);
			}
			catch (Exception e) {
				rgb=new RGB(255,0,0);
			}
		}
		else{
			rgb=new RGB(255,0,0);
		}
		final Color color = new org.eclipse.swt.graphics.Color(parent.getDisplay(), rgb);
		colorLabel.setBackground(color);
		Button button = new Button(innerSection, SWT.PUSH);
		button.setText("Color...");
		button.setSize(50, 10);
		final Shell parentShell = parent.getShell();
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShell.getSize().x - COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(colorLabel.getBackground().getRGB());
				//colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					colorLabel.setBackground(newColor);
					String newHexadecimal = convertRGBToHexadecimal(rgb);
					innerSection.setData(newHexadecimal);
				}
				centerShell.dispose();
			}
		});
		return innerSection;
	}
	public static Composite createColorPickerFillLayer(Composite parent, String colorHex,final Layer selectedLayer, final GEOEditor editor){
		//		Composite innerSection = toolkit.createComposite(section);
		final Composite innerSection = new Composite(parent, SWT.BORDER);
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);
		final Label colorLabel = new Label(innerSection, SWT.BORDER);
		colorLabel.setText("           ");
		colorLabel.setSize(50, 20);
		RGB rgb=null;
		if(colorHex!=null){
			//String hexadecimal = aParameter.getValue().toString();
			try{
				rgb= convertHexadecimalToRGB(colorHex);
			}
			catch (Exception e) {
				rgb=new RGB(255,0,0);
			}
		}
		else{
			rgb=new RGB(255,0,0);
		}
		final Color color = new org.eclipse.swt.graphics.Color(parent.getDisplay(), rgb);
		colorLabel.setBackground(color);
		Button button = new Button(innerSection, SWT.PUSH);
		button.setText("Color");
		button.setSize(30, 10);
		final Shell parentShell = parent.getShell();
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShell.getSize().x - COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(colorLabel.getBackground().getRGB());
				//colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					colorLabel.setBackground(newColor);
					String newHexadecimal = convertRGBToHexadecimal(rgb);
					innerSection.setData(newHexadecimal);
					selectedLayer.setDefaultFillColour(newHexadecimal);
					editor.setIsDirty(true);
				}
				centerShell.dispose();
			}
		});
		return innerSection;
	}
	/**
	 * @param parent
	 * @param colorHex
	 * @param labelToColour
	 * @return
	 */
	public static Composite createColorPicker(Composite parent, String colorHex, final Label labelToColour){

		Composite innerSection = new Composite(parent, SWT.NONE);
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);

		labelToColour.setText("           ");
		labelToColour.setSize(50, 20);
		RGB rgb=null;
		if(colorHex!=null){
			try{
				rgb= convertHexadecimalToRGB(colorHex);
			}
			catch (Exception e) {
				rgb=new RGB(255,0,0);
			}
		}
		else{
			rgb=new RGB(255,0,0);
		}
		final Color color = new org.eclipse.swt.graphics.Color(parent.getDisplay(), rgb);
		labelToColour.setBackground(color);
		Button button = new Button(innerSection, SWT.PUSH);
		button.setText("Color...");
		button.setSize(50, 10);
		final Shell parentShell = parent.getShell();
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShell.getSize().x - COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(labelToColour.getBackground().getRGB());
				//colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					labelToColour.setBackground(newColor);
					String newHexadecimal = convertRGBToHexadecimal(rgb);
					//aParameter.setValue(newHexadecimal);
				}
				centerShell.dispose();
			}
		});
		return innerSection;
	}

	public static String convertRGBToHexadecimal(RGB rgb) {
		int red = rgb.red;
		int green = rgb.green;
		int blue = rgb.blue;
		String redHexadecimal = Integer.toHexString(red);
		String greenHexadecimal = Integer.toHexString(green);
		String blueHexadecimal = Integer.toHexString(blue);
		if (redHexadecimal.length() == 1) redHexadecimal = "0" + redHexadecimal;
		if (greenHexadecimal.length() == 1) greenHexadecimal = "0" + greenHexadecimal;
		if (blueHexadecimal.length() == 1) blueHexadecimal = "0" + blueHexadecimal;
		return "#" + redHexadecimal + greenHexadecimal + blueHexadecimal;
	}




	public static RGB convertHexadecimalToRGB(String hexadecimal) throws NumberFormatException{
		java.awt.Color col=null;
		try{
			col=java.awt.Color.decode(hexadecimal);
		}
		catch (Exception e) {
			col=java.awt.Color.WHITE;
		}
		int red=col.getRed();
		int blue=col.getBlue();
		int green=col.getGreen();

		return new RGB(red, green, blue);
	}


}
