/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.util;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.DocContainer;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocCompUtilities {

	private static Logger logger = LoggerFactory.getLogger(DocCompUtilities.class);

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
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(it.eng.spagobi.studio.documentcomposition.Activator.PLUGIN_ID);
		logger.debug(b.getLocation()+" -  "+b.getSymbolicName());
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
