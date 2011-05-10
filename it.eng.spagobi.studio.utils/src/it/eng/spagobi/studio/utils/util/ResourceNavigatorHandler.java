package it.eng.spagobi.studio.utils.util;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  This class gives some utilities on project structure in resource navigator
 * @author gavardi
 *
 */

public class ResourceNavigatorHandler {

	/**
	 *  return the selection state
	 */
	public static final String FOLDER_ANALYSIS_HIER = "FolderInAnalysisHierarchy";
	public static final String FOLDER_SERVER_HIER = "FolderInServerHierarchy";
	public static final String FOLDER_MODEL_HIER = "FolderInModelHierarchy";

	public static final String FILE_ANALYSIS_HIER = "FileInAnalysisHierarchy";
	public static final String FILE_MODEL_HIER = "FileInModelHierarchy";
	public static final String FILE_SERVER_HIER = "FileInServerHierarchy";

	
	private static Logger logger = LoggerFactory.getLogger(ResourceNavigatorHandler.class);



	public static String getStateOfSelectedObject(Object objSel){
		String toReturn = null;		
		if(objSel instanceof Folder && isInHierarchy(SpagoBIStudioConstants.FOLDER_ANALYSIS, (Folder)objSel))
			toReturn = ResourceNavigatorHandler.FOLDER_ANALYSIS_HIER;
		else
			if(objSel instanceof IFile && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_ANALYSIS, (File)objSel))
				toReturn = ResourceNavigatorHandler.FILE_ANALYSIS_HIER;
			else
				if(objSel instanceof Folder && isInHierarchy(SpagoBIStudioConstants.FOLDER_SERVER, (Folder)objSel))
					toReturn = ResourceNavigatorHandler.FOLDER_SERVER_HIER;
				else
					if(objSel instanceof Folder && isInHierarchy(SpagoBIStudioConstants.FOLDER_METADATA_MODEL, (Folder)objSel))
						toReturn = ResourceNavigatorHandler.FOLDER_MODEL_HIER;
					else
						if(objSel instanceof IFile && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_METADATA_MODEL, (File)objSel))
							toReturn = ResourceNavigatorHandler.FILE_MODEL_HIER;


		return toReturn;
	}






	public static String getStateOfSelectedFile(IFile file){
		String toReturn = "";		

		if(isFileInHierarchy(SpagoBIStudioConstants.FOLDER_ANALYSIS, file))
			toReturn = ResourceNavigatorHandler.FILE_ANALYSIS_HIER;
		else
			if(file.getName().endsWith(SpagoBIStudioConstants.SERVER_EXTENSION) && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_SERVER, file))
				toReturn = ResourceNavigatorHandler.FILE_SERVER_HIER;
			else
				if(file.getName().endsWith(SpagoBIStudioConstants.MODEL_EXTENSION) && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_METADATA_MODEL, file))
					toReturn = ResourceNavigatorHandler.FILE_MODEL_HIER;
		return toReturn;
	}








	/** search if current folder has a parent folder naming like tosearch
	 * 
	 * @param toSearch
	 * @param folder
	 * @return
	 */

	public static boolean isInHierarchy(String toSearch, Folder folder){
		if(folder.getName().equals(toSearch)){
			return true;
		}
		else{
			//
			if(folder.getParent() == null || !(folder.getParent() instanceof Folder)){
				return false;
			}
			else{
				return isInHierarchy(toSearch, (Folder)folder.getParent());
			}
		}

	}

	public static boolean isFileInHierarchy(String toSearch, IFile  file ){
		if(file.getParent() != null 
				|| 
				!(file.getParent() instanceof Folder)){
			return isInHierarchy(toSearch, (Folder)file.getParent() );
		}
		return false;
	}


}
