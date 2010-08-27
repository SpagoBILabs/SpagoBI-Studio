/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.chart.wizards.pages;

import it.eng.spagobi.studio.chart.editors.ChartEditor;
import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;
import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.core.log.SpagoBILogger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewChartWizardPage extends WizardPage {

	Text chartNameText;
	// Map of Radio Buttons
	final HashMap<String, Composite> composites=new HashMap<String, Composite>();
	static String selectedType=null;


	public NewChartWizardPage(String pageName) {
		super(pageName);
		setTitle("New Chart ...");
	}

	public void createControl(Composite parent) {

		Shell shell = parent.getShell();

		try{
			//Type 
			final List chartTypes = ChartModel.getConfiguredChartTypes();
			if (chartTypes == null || chartTypes.size() == 0) {
				MessageDialog.openInformation(shell, "Error", "No Charts configured, see the ChartsInformation.xml file");
			}
			setPageComplete(false);
			Composite all=new Composite(parent, SWT.NONE);
			all.setLayout(new RowLayout(SWT.VERTICAL));

			Group nameComposite=  new org.eclipse.swt.widgets.Group(all, SWT.BORDER);
			GridLayout nameLayout = new GridLayout();
			int ncol = 2;
			nameLayout.numColumns = ncol;
			nameComposite.setLayout(nameLayout);

			nameComposite.setLayoutData(new RowData(500,90));

			//Name Field
			Label setName=new Label(nameComposite, SWT.NONE);
			setName.setText("Name:");				
			GridData gridDataName = new GridData();
			gridDataName.horizontalAlignment = GridData.FILL;
			gridDataName.grabExcessHorizontalSpace = true;
			setName.setLayoutData(gridDataName);

			chartNameText = new Text(nameComposite, SWT.BORDER);
			chartNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));		

			
			//Name Field
			Label setType=new Label(nameComposite, SWT.NONE);
			setType.setText("Type:");				
			GridData gridDataType= new GridData();
			gridDataType.horizontalAlignment = GridData.FILL;
			gridDataType.grabExcessHorizontalSpace = true;
			setType.setLayoutData(gridDataType);

			final Combo comboType = new Combo(nameComposite, SWT.BORDER);
			comboType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));		
			for (Iterator iterator = chartTypes.iterator(); iterator.hasNext();) {
				String type = (String) iterator.next();
				comboType.add(type);
			}

			chartNameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					if(chartNameText.getText().equalsIgnoreCase("")){
						setPageComplete(false);
					}
					else{
						int index=comboType.getSelectionIndex();
						if(index!=-1){
							setPageComplete(true);
						}
						else{
							setPageComplete(false);
						}
					}
				}
			});

			
			// Group down
			final Group belowComposite=new Group(all,SWT.BORDER);		
			belowComposite.setLayoutData(new RowData(500,300));
			final StackLayout layout = new StackLayout();
			belowComposite.setLayout(layout);

			for (Iterator iterator = chartTypes.iterator(); iterator.hasNext();) {
				final String t = (String) iterator.next();
				final Composite compImage = new Composite(belowComposite, SWT.NONE);
				compImage.setLayout(new RowLayout());
				//compImage.setLayoutData(new RowData(300,200));

				compImage.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						Image image = null;
						Image scaledImage = null;
						try {
							String imagePath=ChartEditorUtils.getChartImagePath(t.toUpperCase());
							InputStream is=ChartEditorUtils.getInputStreamFromResource(imagePath);
							image = new Image(compImage.getDisplay(), is);
					
							final int originalWidth = image.getBounds().width;
							final int originalHeight = image.getBounds().height; 				
							int containerHeight=compImage.getBounds().height;
							int containerWidth=compImage.getBounds().width;
							double rapportoHeight=(double)containerHeight / (double)originalHeight;
							double rapportoWidth=(double)containerWidth / (double)originalWidth;
							
							scaledImage = new Image(compImage.getDisplay(),
									image.getImageData().scaledTo((int)(originalWidth*rapportoWidth-20),(int)(originalHeight*rapportoHeight-20)));
						
						} catch (FileNotFoundException e1) {
							SpagoBILogger.errorLog("could not find image for type "+t, e1);
						}
						catch (Exception e2) {
							SpagoBILogger.errorLog("Error while drawing image for type "+t, e2);
						}
						// insert scaledImage to Have image scalation!
						e.gc.drawImage(image, 20, 30);
						image.dispose();
					}
				});
				compImage.redraw();

				composites.put(t, compImage);
			}

			comboType.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					// disable all other rafio buttons!
					int selection = comboType.getSelectionIndex();
					final String type=comboType.getItem(selection);
					selectedType=type;
					Composite comp=composites.get(type);
					layout.topControl=comp;
					belowComposite.layout();
					if(chartNameText.getText()!=null && !chartNameText.getText().equalsIgnoreCase("")){
						setPageComplete(true);
					}
					else{
						setPageComplete(false);
					}
				}
			});



			setControl(nameComposite);

		}
		catch (Exception e) {
			// TODO: handle exception
		}






	}

	public Text getChartNameText() {
		return chartNameText;
	}


	


	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return super.isPageComplete();
	}

	public void setPageComplete(boolean complete) {
		// TODO Auto-generated method stub
		super.setPageComplete(complete);
	}

	static void doSelection(Button button) {
		if (button.getSelection()){
//			System.out.println("do work for selection "+button);
			selectedType=(String)button.getData();
		} else {
//			System.out.println("do work for deselection "+button);
		}
	}

	public static String getSelectedType() {
		return selectedType;
	}

	public static void setSelectedType(String selectedType) {
		NewChartWizardPage.selectedType = selectedType;
	}



}
