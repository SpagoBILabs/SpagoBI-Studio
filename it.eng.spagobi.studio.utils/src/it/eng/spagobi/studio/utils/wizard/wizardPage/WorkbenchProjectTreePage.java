/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.wizard.wizardPage;


import java.util.HashMap;

import it.eng.spagobi.studio.utils.util.WorkbenchProjectTreeGenerator;
import it.eng.spagobi.studio.utils.util.WorkspaceHandler;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WorkbenchProjectTreePage extends WizardPage {


	private IStructuredSelection selection;
	private Combo projectCombo;
	private static Logger logger = LoggerFactory.getLogger(WorkbenchProjectTreePage.class);
	private WorkbenchProjectTreeGenerator workbenchProjectTreeGenerator;
	private Tree tree;

	// Associate Tree item name with the folder it represent
	HashMap<String, IFolder> itemFolderMap = null;


	public WorkbenchProjectTreePage(String pageName, IStructuredSelection _selection) {
		super("Projects tree");
		setTitle("Select the project folder");
		setDescription("Select the project folder in which insert your new template");
		this.selection = _selection;
	}

	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		logger.debug("IN");	

//		Shell shell = parent.getShell();
//		int x = shell.getSize().x;		
//		int y = shell.getSize().y;
//		shell.setSize(x, y);
		
		Composite composite =  new Composite(parent, SWT.NULL);
		composite.setLayout(new RowLayout(SWT.VERTICAL));
		setPageComplete(false);

		// Combo to select project
		final Group projectGroup=  new org.eclipse.swt.widgets.Group(composite, SWT.BORDER);
		GridLayout nameLayout = new GridLayout();
		int ncol = 2;
		nameLayout.numColumns = ncol;
		projectGroup.setLayout(nameLayout);
		projectGroup.setLayoutData(new RowData(600,90));

		Label aaName=new Label(projectGroup, SWT.NONE);
		aaName.setText("Select Project: ");	


		// Tree to select folder

		final Group belowGroup=new Group(composite,SWT.BORDER);		
		belowGroup.setLayoutData(new RowData(600,300));
		//		final StackLayout layout = new StackLayout();
		//		belowGroup.setLayout(layout);
		final FillLayout layout = new FillLayout();
		belowGroup.setLayout(layout);

		Label setName=new Label(belowGroup, SWT.NONE);
		setName.setText("Select Folder: ");				
		GridData gridDataName = new GridData();
		gridDataName.horizontalAlignment = GridData.FILL;
		gridDataName.grabExcessHorizontalSpace = true;
		setName.setLayoutData(gridDataName);
		workbenchProjectTreeGenerator = new WorkbenchProjectTreeGenerator();
		tree = workbenchProjectTreeGenerator.initializeTree(belowGroup);

		getTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//check if page is complete
				boolean complete=isPageComplete();
				if(complete){
					setPageComplete(true);
				}
				else{
					setPageComplete(false);	        	
				}
			}
		});




		projectCombo = new Combo(projectGroup, SWT.NONE | SWT.READ_ONLY);
		projectCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));		

		// if selected just one project disable project seleztion
		String projectSelected = null;
		String[] projects = null;
		if(selection != null && selection.toList().size()==1){
			Object objSel = selection.toList().get(0);
			if (objSel instanceof IProject){
				projectSelected = ((IProject)objSel).getName();
				logger.debug("Project selected is "+projectSelected);
			}
			else if (objSel instanceof IFolder){
				projectSelected = ((IFolder)objSel).getProject().getName();
				logger.debug("Project selected is "+projectSelected);
			}
		}

		if(projectSelected == null){
			projects = new WorkspaceHandler().getProjectNames();
		}
		else{
			projects = new String[1];
			projects[0] = projectSelected;
		}

		for (int i = 0; i < projects.length; i++) {
			projectCombo.add(projects[i]);
		}

		// selection change tree
		projectCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// get selected project
				String prName = projectCombo.getItem(projectCombo.getSelectionIndex());
				// erase old tree
				workbenchProjectTreeGenerator.removeOldTree(getTree());

				itemFolderMap = workbenchProjectTreeGenerator.generateTree(getTree(), belowGroup, prName, itemFolderMap);
				setControl(belowGroup);
				belowGroup.redraw();

			}
		});

		// if project is already selected disable combo and draw tree Once for all
		if(projectSelected != null){
			projectCombo.select(0);
			projectCombo.setEnabled(false);
			// erase old tree
			workbenchProjectTreeGenerator.removeOldTree(getTree());

			itemFolderMap = workbenchProjectTreeGenerator.generateTree(getTree(), belowGroup, projectSelected, itemFolderMap);
			setControl(belowGroup);
			belowGroup.redraw();
		}

		setControl(composite);
		logger.debug("OUT");	
	}



	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	/**
	 * to be complete only one resource selected and not the project root
	 */
	public boolean isPageComplete() {
		boolean isComplete=false;
		if(getTree()!=null){
			int count = getTree().getSelectionCount();
			if(count==1){
				//check the selected is not project root
				TreeItem item = getTree().getSelection()[0];
				if(item.getData() != null){
					String data = item.getData().toString();
					if(data.equalsIgnoreCase(WorkbenchProjectTreeGenerator.TREE_ROOT)){
						isComplete = false;
					}
					else isComplete = true;

				}
				else isComplete = true;

			}
			else isComplete = false;
		}
		return isComplete;
	}

	public HashMap<String, IFolder> getItemFolderMap() {
		return itemFolderMap;
	}

	public void setItemFolderMap(HashMap<String, IFolder> itemFolderMap) {
		this.itemFolderMap = itemFolderMap;
	}



}