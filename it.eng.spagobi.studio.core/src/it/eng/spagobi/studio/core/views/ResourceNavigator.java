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
package it.eng.spagobi.studio.core.views;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.util.ImageDescriptorGatherer;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.NavigatorDecoratingLabelProvider;
import org.eclipse.ui.navigator.CommonViewer;

public class ResourceNavigator extends org.eclipse.ui.navigator.CommonNavigator {

	public static final String VIEW_ID = "it.eng.spagobi.studio.core.views.ResourceNavigator";





	/** inner class: Personalize folders with icons
	 * 
	 * @author gavardi
	 *
	 */
	//	public class MyLabelProvider extends LabelProvider {
	public class MyLabelProvider extends NavigatorDecoratingLabelProvider {
		// Prepare Icons
		ImageDescriptor datasourceDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_DATA_SOURCE);
		ImageDescriptor dataseDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_DATASET);
		ImageDescriptor analysisDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_ANALYSIS);
		ImageDescriptor metadataDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_METADATA_MODEL);
		ImageDescriptor privateDocumentsDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_PRIVATE_DOCUMENTS);
		ImageDescriptor resourceDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_RESOURCE);
		ImageDescriptor serverDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SERVER);
		ImageDescriptor sbiProjectDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SBI_PROJECT);
		/**  vector containing images*/
		Vector<Image> images = new Vector<Image>();


		public MyLabelProvider(ILabelProvider commonLabelProvider) {
			super(commonLabelProvider);
		}

		/**
		 *  Use personalized icons for SpagoBi structure project folders
		 */
		public Image getImage(Object object) {
			SpagoBILogger.infoLog("IN");

			Image imageToReturn = null;

			if (object instanceof IFolder) {	
				Folder folder = (Folder) object;
				org.eclipse.core.resources.IContainer container = folder.getParent();

				// if father is project means they are system tables (to avoid name mismatches)
				String name = folder.getName();
				if(container instanceof IProject){
					if(name.equals(SpagoBIStudioConstants.FOLDER_RESOURCE)){
						imageToReturn = resourceDescriptor.createImage();
						images.add(imageToReturn);
					}
					else if(name.equals(SpagoBIStudioConstants.FOLDER_ANALYSIS)){
						imageToReturn = analysisDescriptor.createImage();
						images.add(imageToReturn);
					}
					else if(name.equals(SpagoBIStudioConstants.FOLDER_METADATA_MODEL)){
						imageToReturn = metadataDescriptor.createImage();
						images.add(imageToReturn);
					}
					else if(name.equals(SpagoBIStudioConstants.FOLDER_PRIVATE_DOCUMENTS)){
						imageToReturn = privateDocumentsDescriptor.createImage();
						images.add(imageToReturn);
					}
				}
				else{
					// there asre some structure folders that have a father folder

					String fatherName = container != null ? container.getName() : "";

					// dataset have father metadata
					if(name.equals(SpagoBIStudioConstants.FOLDER_DATASET)){
						if(fatherName.equals(SpagoBIStudioConstants.FOLDER_METADATA_MODEL)){
							imageToReturn = dataseDescriptor.createImage();
							images.add(imageToReturn);
						}
					}
					// data Source and server have father resources
					else if(name.equals(SpagoBIStudioConstants.FOLDER_DATA_SOURCE)){
						if(fatherName.equals(SpagoBIStudioConstants.FOLDER_RESOURCE)){
							imageToReturn = datasourceDescriptor.createImage();
							images.add(imageToReturn);
						}
					}					
					else if(name.equals(SpagoBIStudioConstants.FOLDER_SERVER)){
						if(fatherName.equals(SpagoBIStudioConstants.FOLDER_RESOURCE)){
							imageToReturn = serverDescriptor.createImage();
							images.add(imageToReturn);
						}
					}					

				}
			}
			// if it is a spagobi project folder
			else if(object instanceof IProject){
				imageToReturn = sbiProjectDescriptor.createImage();
				images.add(imageToReturn);
			}

			// default case
			if(imageToReturn == null){
				imageToReturn = super.getImage(object);
			}
			SpagoBILogger.infoLog("OUT");
			return imageToReturn;
		}

		
		public void dispose() {
			for (Iterator iterator = images.iterator(); iterator.hasNext();) {
				Image imagesIt = (Image) iterator.next();
				imagesIt.dispose();
			}
			images.clear();
		}
	} 


	@Override
	public void createPartControl(Composite aParent) {
		super.createPartControl(aParent);

		// Override Label provider to use personalized folder
		CommonViewer viewer = getCommonViewer();
		viewer.setLabelProvider(new MyLabelProvider((ILabelProvider)viewer.getLabelProvider()));



	}




















}
