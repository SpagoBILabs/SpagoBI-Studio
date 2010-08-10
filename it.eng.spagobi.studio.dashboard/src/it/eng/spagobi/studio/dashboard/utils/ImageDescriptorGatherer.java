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
