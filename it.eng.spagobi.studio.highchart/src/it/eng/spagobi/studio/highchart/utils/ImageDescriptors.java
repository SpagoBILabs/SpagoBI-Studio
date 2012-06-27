/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/

package it.eng.spagobi.studio.highchart.utils;

import it.eng.spagobi.studio.highchart.Activator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImageDescriptors {

	final static ImageDescriptor addIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/add.gif");

	final static ImageDescriptor eraseIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/erase.gif");

	final static ImageDescriptor saveIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/save.png");


	final static ImageDescriptor upIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ArrowUp.gif");

	final static ImageDescriptor downIcon = AbstractUIPlugin
	.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ArrowDown.gif");

	
	
	public static ImageDescriptor getUpicon() {
		return upIcon;
	}

	public static ImageDescriptor getDownicon() {
		return downIcon;
	}

	public static ImageDescriptor getAddIcon() {
		return addIcon;
	}

	public static ImageDescriptor getEraseIcon() {
		return eraseIcon;
	}

	public static ImageDescriptor getSaveIcon() {
		return saveIcon;
	}
	
	
	
}
