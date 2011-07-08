package it.eng.spagobi.studio.utils.util;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
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
	public static final String FILE_BCK_MODEL_HIER = "FileInBckModelHierarchy";
	public static final String FILE_SERVER_HIER = "FileInServerHierarchy";
	public static final String FILE_METAQUERY_HIER = "FileInMetaQueryHierarchy";

	public static final String FOLDER_SYSTEM = "FolderSystem";
	public static final String FOLDER_PROJECT = "FolderProject";


	private static Logger logger = LoggerFactory.getLogger(ResourceNavigatorHandler.class);



	public static String getStateOfSelectedObject(Object objSel){
		logger.debug("IN");
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
						if(objSel instanceof IFile && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_METADATA_MODEL, (File)objSel) 
								&& ((File)objSel).getName().endsWith(SpagoBIStudioConstants.MODEL_EXTENSION) )
							toReturn = ResourceNavigatorHandler.FILE_MODEL_HIER;
						else
							if(objSel instanceof IFile && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_METADATA_MODEL, (File)objSel) 
									&& ((File)objSel).getName().endsWith(SpagoBIStudioConstants.BACKUP_EXTENSION) )
								toReturn = ResourceNavigatorHandler.FILE_BCK_MODEL_HIER;
							else
								if(objSel instanceof IFile && ((File)objSel).getName().endsWith(SpagoBIStudioConstants.META_QUERY_EXTENSION) 
										&& isFileInHierarchy(SpagoBIStudioConstants.FOLDER_DATASET, ((File)objSel)))
									toReturn = ResourceNavigatorHandler.FILE_METAQUERY_HIER;
		logger.debug("OUT");
		return toReturn;
	}

	/** in order to enable delete
	 *  check that selection has no structure folders
	 * @param selList
	 * @return
	 */
	public static boolean isSelectedObjSystemFolder(List<IStructuredSelection> selList){
		logger.debug("IN");
		boolean toreturn = false;

		for (Iterator iterator = selList.iterator(); iterator.hasNext() && !toreturn;) {
			Object objSel = iterator.next();
			if(objSel instanceof Folder){
				IFolder fold = (IFolder)objSel;
				toreturn = isSpagoBISystemFolder(fold);	
			}
		}
		logger.debug("OUT");
		return toreturn;
	}




	public static String getStateOfSelectedFile(IFile file){
		logger.debug("IN");
		String toReturn = "";		

		if(isFileInHierarchy(SpagoBIStudioConstants.FOLDER_ANALYSIS, file))
			toReturn = ResourceNavigatorHandler.FILE_ANALYSIS_HIER;
		else
			if(file.getName().endsWith(SpagoBIStudioConstants.SERVER_EXTENSION) && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_SERVER, file))
				toReturn = ResourceNavigatorHandler.FILE_SERVER_HIER;
			else
				if(file.getName().endsWith(SpagoBIStudioConstants.MODEL_EXTENSION) && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_METADATA_MODEL, file))
					toReturn = ResourceNavigatorHandler.FILE_MODEL_HIER;
				else
					if(file.getName().endsWith(SpagoBIStudioConstants.META_QUERY_EXTENSION) && isFileInHierarchy(SpagoBIStudioConstants.FOLDER_DATASET, file))
						toReturn = ResourceNavigatorHandler.FILE_METAQUERY_HIER;
		logger.debug("OUT");
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


	public static boolean isSpagoBISystemFolder(IFolder folder ){
		logger.debug("IN");
		boolean toreturn = false;

		String projectName = folder.getProject().getName();
		IPath resourcePath =folder.getFullPath();
		String resPath = resourcePath.toOSString();

		String[] systemFolders = new String[]{
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_ANALYSIS,				
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_ANALYSIS,				
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_DATA_SOURCE,
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_DATA_SOURCE,
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_DATASET,
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_DATASET,
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_METADATA_MODEL,
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_METADATA_MODEL,
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_PRIVATE_DOCUMENTS,
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_PRIVATE_DOCUMENTS,
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_RESOURCE+"/"+SpagoBIStudioConstants.FOLDER_SERVER,
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_RESOURCE+"\\"+SpagoBIStudioConstants.FOLDER_SERVER,
				"/"+projectName+"/"+SpagoBIStudioConstants.FOLDER_SERVER,
				"\\"+projectName+"\\"+SpagoBIStudioConstants.FOLDER_RESOURCE
		};

		for (int i = 0; i < systemFolders.length && !toreturn; i++) {
			if(resPath.equals(systemFolders[i])){
				toreturn = true;
			}
		}

		logger.debug("OUT");
		return toreturn;
	}


}
