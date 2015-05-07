/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.wizards.pages;

import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;
import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewChartWizardPage extends WizardPage {

	Text chartNameText;
	// Map of Radio Buttons
	final HashMap<String, Composite> composites=new HashMap<String, Composite>();
	static String selectedType=null;
	private static Logger logger = LoggerFactory.getLogger(NewChartWizardPage.class);
	private IWorkbench workbench;
	
	public NewChartWizardPage( IWorkbench _workbench, String pageName) {
		super(pageName);
		setTitle("New Chart ...");
		workbench = _workbench;
	}

	public void createControl(Composite parent) {


		try{
			//Type 
			final List chartTypes = ChartModel.getConfiguredChartTypes();
			Composite all=new Composite(parent, SWT.NONE);
			
			Shell shell = all.getShell();
			if (chartTypes == null || chartTypes.size() == 0) {
				MessageDialog.openInformation(shell, "Error", "No Charts configured, see the ChartsInformation.xml file");
			}
			setPageComplete(false);
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
							logger.error("could not find image for type "+t, e1);
						}
						catch (Exception e2) {
							logger.error("Error while drawing image for type "+t, e2);
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



			setControl(all);

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
