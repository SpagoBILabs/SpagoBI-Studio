/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.dashboard.utils;

import it.eng.spagobi.studio.dashboard.Activator;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImageDescriptorGatherer {

	public static ImageDescriptor getFolderOpenIDesc() {
		String folderopenimgPath = "icons" + File.separator + "folderopen.gif";
		ImageDescriptor folderopenimgdesc = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, folderopenimgPath);
		return folderopenimgdesc;
	}
	
	public static ImageDescriptor getFolderIDesc() {
		String folderimgPath = "icons" + File.separator + "folder.gif";
		ImageDescriptor folderimgdesc = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, folderimgPath);
		return folderimgdesc;
	}
	
	public static ImageDescriptor getDocumentIDesc() {
		String documentimgPath = "icons" + File.separator + "sbidocument.gif";
		ImageDescriptor documentimgdesc = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, documentimgPath);
		return documentimgdesc;
	}
	
}
