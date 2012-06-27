/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.util;

import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.util.ImageDescriptorGatherer;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class SdkSelectFolderTreeGenerator {

	Tree tree=null;
	Display display=null;
	Composite container=null;
	ImageDescriptor folderDescriptor=null;
	ImageDescriptor treeBaseDescriptor=null;

	HashMap<String, ImageDescriptor> imageDescriptors=null;


	public Tree generateTree(Composite parent,Functionality func){
		container=parent;
		folderDescriptor=ImageDescriptorGatherer.getImageDesc("folder.gif", Activator.PLUGIN_ID);
		treeBaseDescriptor=ImageDescriptorGatherer.getImageDesc("treebase.gif", Activator.PLUGIN_ID);

		tree = new Tree(parent, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		TreeItem root=new TreeItem(tree,SWT.SINGLE);
		root.setText("Functionalities");
		root.setImage(treeBaseDescriptor.createImage());
		createItemsList(root, func);

		return tree;
	}

	// Private recursive method that adds documents in tree
	// builds the tree with TreeItem named parent as root
	private void createItemsList(TreeItem parent, Functionality func){

		Functionality[] funcArray=func.getContainedFunctionalities();


		if(funcArray!=null){
			for (Functionality functionality : funcArray) {
				TreeItem currItem=new TreeItem(parent,SWT.CHECK);
				currItem.setText(functionality.getName());
				currItem.setData(functionality);

				currItem.setImage(folderDescriptor.createImage());
				createItemsList(currItem, functionality);

			}
		}


	}



	
	
	
	
	
}
