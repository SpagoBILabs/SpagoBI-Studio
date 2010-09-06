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
package it.eng.spagobi.studio.documentcomposition.util;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorReference;
import org.osgi.framework.Bundle;

public class DocCompUtilities {


	/** Get input stream from a resource
	 * 
	 * @param resourcePath
	 * @return
	 * @throws IOException
	 */

	public static String DOCUMENT_COMPOSITION_EDITOR_ID="it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor";
	public static String NAVIGATION_VIEW_ID="it.eng.spagobi.studio.documentcomposition.views.NavigationView";
	public static String DOCUMENT_PROPERTIES_VIEW_ID="it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView";
	public static String DOCUMENT_PARAMETERS_VIEW_ID="it.eng.spagobi.studio.documentcomposition.views.DocumentParametersView";
	public static String VIDEO_SIZE_VIEW_ID="it.eng.spagobi.studio.documentcomposition.views.VideoSizeView";



	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		SpagoBILogger.infoLog("IN");
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(it.eng.spagobi.studio.documentcomposition.Activator.PLUGIN_ID);
		SpagoBILogger.infoLog(b.getLocation()+" -  "+b.getSymbolicName());
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}

	public boolean isDocumentDeletable(Document doc) {
		boolean ret = true;

		//ricava DocumentsComposition salvato
		DocumentComposition documentComposition =Activator.getDefault().getDocumentComposition();
		if(documentComposition != null){
			Vector<Document> docs = documentComposition.getDocumentsConfiguration().getDocuments();
			if(docs.contains(doc)){
				ret = false;
			}
		}

		return ret;

	}


	public static IEditorPart getEditorReference(String editorId){
		IEditorPart toReturn=null;
		IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		IWorkbenchPage aa=a.getActivePage();		
		if(aa != null){
			IEditorReference[] editors=aa.findEditors(null, editorId, IWorkbenchPage.MATCH_ID);
			if(editors!=null && editors.length>0){
				EditorReference editorReference=(EditorReference)editors[0];
				toReturn=(IEditorPart)editorReference.getPart(false);
			}
		}
		return toReturn;
	}

	public static IViewPart getViewReference(String viewId){
		IViewPart toReturn=null;
		IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		// Document properties
		IWorkbenchPage aa=a.getActivePage();
		if(aa != null){
			IViewReference w=aa.findViewReference(viewId);
			Object p=w.getPart(false);
			if(p!=null){
				toReturn=(IViewPart)p;
			}
		}
		return toReturn;
	}





}
