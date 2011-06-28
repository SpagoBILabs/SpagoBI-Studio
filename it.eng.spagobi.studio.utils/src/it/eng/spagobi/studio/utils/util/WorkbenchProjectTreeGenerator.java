package it.eng.spagobi.studio.utils.util;

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

import it.eng.spagobi.studio.utils.Activator;

import java.util.HashMap;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** this class creates the workbenchfolder tree
 * 
 * @author gavardi
 *
 */

public class WorkbenchProjectTreeGenerator {


	Display display=null;
	Composite container=null;
	ImageDescriptor folderDescriptor=null;
	ImageDescriptor treeBaseDescriptor=null;
	ImageDescriptor analysisDescriptor=null;

	HashMap<String, ImageDescriptor> imageDescriptors=null;

	public static final String TREE_ROOT = "treeRot";
	private static Logger logger = LoggerFactory.getLogger(WorkbenchProjectTreeGenerator.class);



	/**
	 *  Initialize the tree
	 * @param parent
	 */
	public Tree initializeTree(Composite parent){
		Tree tree = new Tree(parent, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		return tree;
	}

	/**
	 *  Tree generation
	 * @param parent
	 * @param projectName
	 * @return
	 */
	public HashMap<String, IFolder> generateTree(Tree tree, Composite parent, String projectName, HashMap<String, IFolder> itemFolderMap){
		logger.debug("IN");
		container=parent;
		folderDescriptor=ImageDescriptorGatherer.getImageDesc("folder.gif", Activator.PLUGIN_ID);
		treeBaseDescriptor=ImageDescriptorGatherer.getImageDesc("treebase.gif", Activator.PLUGIN_ID);
		analysisDescriptor=ImageDescriptorGatherer.getImageDesc("analysis.png", Activator.PLUGIN_ID);

		IProject project = new WorkspaceHandler().getProjectRootFolder(projectName);
		if( tree == null ){
			tree = new Tree(parent, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL);
		}

		TreeItem root=new TreeItem(tree,SWT.SINGLE);
		root.setText(project.getName());
		root.setImage(treeBaseDescriptor.createImage());
		// mark project root
		root.setData(TREE_ROOT);

		itemFolderMap = new HashMap<String, IFolder> ();

		IFolder analysisFolder = project.getFolder(SpagoBIStudioConstants.FOLDER_ANALYSIS);
		/** How any time an item name has been retrieved, in order to rename it */
		HashMap<String, Integer> nameOccurencesMap = new HashMap<String, Integer>();

		try{
			if(analysisFolder != null && analysisFolder.exists()){
				// only if the folder exist otherwise don't draw it
				itemFolderMap.put(SpagoBIStudioConstants.FOLDER_ANALYSIS, analysisFolder);
				TreeItem analysis = new TreeItem(root,SWT.SINGLE);
				analysis.setText(SpagoBIStudioConstants.FOLDER_ANALYSIS);
				analysis.setImage(analysisDescriptor.createImage());
				createItemsList(analysis, analysisFolder, itemFolderMap, nameOccurencesMap);
			}
			else {
				logger.warn("Could not find analysis folder, probably not a spagoBIProject");
				MessageDialog.openWarning(parent.getShell(), "Warning", "Could not find Sbi Analysys folder: probably not a SpagoBi Project");
			}

			tree.redraw();
			parent.redraw();
		}
		catch (Exception e) {
			logger.error("Error in generating tree for project  "+projectName, e);
		}
		logger.debug("OUT");
		return itemFolderMap;
	}


	/**
	 *  remove old tre at combo change
	 */
	public void removeOldTree(Tree tree) {
		tree.removeAll();
	}


	/**
	 *  Create recursively the tree
	 */
	private void createItemsList(TreeItem parent, IFolder func, HashMap<String, IFolder> itemFolderMap, HashMap<String, Integer> nameOccurencesMap ) throws CoreException{
		IResource[] containers = func.members();
		if(containers != null){
			for (int i = 0; i < containers.length; i++) {
				IResource res = containers[i];
				if(res instanceof IFolder){
					TreeItem currItem=new TreeItem(parent,SWT.SINGLE);
					String suffix = "";
					String name = res.getName();
					// if item in array is already present with the same name add a number
					if(nameOccurencesMap.get(res.getName()) != null){
						Integer occ = nameOccurencesMap.get(res.getName());
						occ++;
						suffix = occ.toString();
					}
					else{ // else ad it to array of already present
						nameOccurencesMap.put(name, Integer.valueOf(1));
					}
					String nameTreeItem = res.getName()+suffix;
					currItem.setText(nameTreeItem);					
					currItem.setImage(folderDescriptor.createImage());

					itemFolderMap.put(nameTreeItem, (IFolder)res);

					createItemsList(currItem, (IFolder)res, itemFolderMap, nameOccurencesMap);
				}
			}
		}
	}





}
