/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.util;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImageDescriptorGatherer {

	public static ImageDescriptor getImageDesc(String imageName, String pluginId) {
		String imagePath = "icons" + File.separator + imageName;
		ImageDescriptor imagePathDesc = AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, imagePath);
		
		return imagePathDesc;
	}
	


}
