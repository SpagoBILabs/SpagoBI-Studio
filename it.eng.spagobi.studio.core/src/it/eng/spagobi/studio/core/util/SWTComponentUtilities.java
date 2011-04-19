package it.eng.spagobi.studio.core.util;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorReference;

public class SWTComponentUtilities {

	
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
	
}
