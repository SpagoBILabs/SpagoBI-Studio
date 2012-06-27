/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.util;

import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.utils.util.ImageDescriptorGatherer;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import org.eclipse.jface.resource.ImageDescriptor;

public class ImageConstants {

	// IMAGE
	public static final	ImageDescriptor datasourceDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_DATA_SOURCE, Activator.PLUGIN_ID);
	public static final ImageDescriptor dataseDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_DATASET, Activator.PLUGIN_ID);
	public static final ImageDescriptor analysisDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_ANALYSIS, Activator.PLUGIN_ID);
	public static final	ImageDescriptor metadataDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_METADATA_MODEL, Activator.PLUGIN_ID);
	public static final	ImageDescriptor privateDocumentsDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_PRIVATE_DOCUMENTS, Activator.PLUGIN_ID);
	public static final ImageDescriptor resourceDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_RESOURCE, Activator.PLUGIN_ID);
	public static final	ImageDescriptor serverDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SERVER, Activator.PLUGIN_ID);
	public static final	ImageDescriptor serverBigDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SERVER_BIG, Activator.PLUGIN_ID);
	public static final	ImageDescriptor serverActiveDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SERVER_ACTIVE, Activator.PLUGIN_ID);
	public static final ImageDescriptor serverInactiveDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SERVER_INACTIVE, Activator.PLUGIN_ID);
	public static final ImageDescriptor sbiProjectDescriptor=ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_SBI_PROJECT, Activator.PLUGIN_ID);

	
}
