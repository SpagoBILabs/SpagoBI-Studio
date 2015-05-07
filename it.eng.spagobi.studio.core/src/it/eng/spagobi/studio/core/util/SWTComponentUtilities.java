/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.util;

import it.eng.spagobi.studio.core.views.ResourceNavigator;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SWTComponentUtilities {

	private static Logger logger = LoggerFactory.getLogger(SWTComponentUtilities.class);
	
	public static IEditorPart getEditorReference(String editorId){
		logger.debug("IN");
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
		logger.debug("OUT");
		return toReturn;
	}

	public static IEditorPart getNavigatorReference(String navigatorId){
		logger.debug("IN");
		IEditorPart toReturn=null;
		IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		IWorkbenchPage aa=a.getActivePage();		
		if(aa != null){
			IViewReference navigator=aa.findViewReference(navigatorId);
			IViewPart navigatorView=aa.findView(navigatorId);
			
			if(navigatorView!=null && navigatorView instanceof ResourceNavigator){
				ResourceNavigator resourceNavigator = (ResourceNavigator)navigatorView;
				resourceNavigator.getCommonViewer().refresh();
			}
		}
		logger.debug("OUT");
		return toReturn;
	}

	
	
	
}
