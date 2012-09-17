/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.console.wizard.pages;



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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewConsoleTemplateWizardPage extends WizardPage {

	Text templateNameText;

	final HashMap<String, Composite> composites=new HashMap<String, Composite>();
	static String selectedType=null;
	private static Logger logger = LoggerFactory.getLogger(NewConsoleTemplateWizardPage.class);
	private IWorkbench workbench;

	public NewConsoleTemplateWizardPage( IWorkbench _workbench, String pageName) {
		super(pageName);
		setTitle("New Template Console");
		workbench = _workbench;
	}

	public void createControl(Composite parent) {
		logger.debug("IN");
		try{


			Composite all=new Composite(parent, SWT.NONE);
			Shell shell = all.getShell();

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

			templateNameText = new Text(nameComposite, SWT.BORDER);
			templateNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));		




			templateNameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					if(templateNameText.getText().equalsIgnoreCase("")){
						setPageComplete(false);
					}
					else{
							setPageComplete(true);					
					}
				}
			});


			// Group down
			final Group belowComposite=new Group(all,SWT.BORDER);		
			belowComposite.setLayoutData(new RowData(500,300));
			final StackLayout layout = new StackLayout();
			belowComposite.setLayout(layout);





			setControl(all);

		}
		catch (Exception e) {
			logger.error("Error in opening the wizard", e);
		}


		logger.debug("OUT");

	}

	public Text getTemplateNameText() {
		return templateNameText;
	}





	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return super.isPageComplete();
	}

	public void setPageComplete(boolean complete) {
		// TODO Auto-generated method stub
		super.setPageComplete(complete);
	}







}
