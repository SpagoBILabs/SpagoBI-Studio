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
package it.eng.spagobi.studio.core.util;

import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.util.ImageDescriptorGatherer;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class SdkFunctionalityTreeGenerator {

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
		initializeField();


		tree = new Tree(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		TreeItem root=new TreeItem(tree,SWT.SINGLE);
		root.setText("Functionalities");
		root.setImage(treeBaseDescriptor.createImage());
		createItemsList(root, func);

		return tree;
	}

	// Private recursive method that adds documents in tree
	// builds the tree with TreeItem named parent as root
	private void createItemsList(TreeItem parent,Functionality func){

		Functionality[] sdkFuncArray=func.getContainedFunctionalities();


		if(sdkFuncArray!=null){
			for (Functionality functionality : sdkFuncArray) {
				TreeItem currItem=new TreeItem(parent,SWT.SINGLE);
				currItem.setText(functionality.getName());
				currItem.setData(functionality);

				currItem.setImage(folderDescriptor.createImage());
				createItemsList(currItem, functionality);

				Document[] sdkDocuments=functionality.getContainedDocuments(); 
				if(sdkDocuments!=null){
					for (Document document : sdkDocuments) {
						createDocumentsList(currItem, document);
					}
				}
			}
		}


	}

	private void createDocumentsList(TreeItem parent,Document doc){
		TreeItem currDoc=new TreeItem(parent,SWT.CHECK);
		currDoc.setText(doc.getName());
		currDoc.setData(doc);
		if(doc.getType()!=null){
			ImageDescriptor descriptor=imageDescriptors.get(doc.getType().toUpperCase());
			if(descriptor!=null){
				currDoc.setImage(descriptor.createImage());
			}
		}
	}

	private void initializeField(){
		imageDescriptors=new HashMap<String, ImageDescriptor>();		
		ImageDescriptor reportDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_REPORT.png", Activator.PLUGIN_ID);
		ImageDescriptor olapDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_OLAP.png", Activator.PLUGIN_ID);
		ImageDescriptor dashDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_DASH.png", Activator.PLUGIN_ID);
		ImageDescriptor geoDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_MAP.png", Activator.PLUGIN_ID);
		ImageDescriptor officeDocumentDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_OFFICE_DOC.png", Activator.PLUGIN_ID);
		ImageDescriptor etlDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_ETL.png", Activator.PLUGIN_ID);
		ImageDescriptor qbeDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_DATAMART.png", Activator.PLUGIN_ID);
		ImageDescriptor dossierDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_DOSSIER.png", Activator.PLUGIN_ID);
		ImageDescriptor compositeDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_COMPOSITE_DOCUMENT.png", Activator.PLUGIN_ID);
		ImageDescriptor kpiDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_KPI.png", Activator.PLUGIN_ID);
		ImageDescriptor dataMiningDescriptor=ImageDescriptorGatherer.getImageDesc("objecticon_DATA_MINING.png", Activator.PLUGIN_ID);

		imageDescriptors.put("REPORT", reportDescriptor);
		imageDescriptors.put("OLAP", olapDescriptor);
		imageDescriptors.put("DASH", dashDescriptor);
		imageDescriptors.put("MAP", geoDescriptor);
		imageDescriptors.put("OFFICE_DOC", officeDocumentDescriptor);
		imageDescriptors.put("ETL", etlDescriptor);
		imageDescriptors.put("DOSSIER", dossierDescriptor);
		imageDescriptors.put("DOCUMENT_COMPOSITE", compositeDescriptor);
		imageDescriptors.put("DATAMART", qbeDescriptor);
		imageDescriptors.put("KPI", kpiDescriptor);
		imageDescriptors.put("DATA_MINING", kpiDescriptor);



	}


}
