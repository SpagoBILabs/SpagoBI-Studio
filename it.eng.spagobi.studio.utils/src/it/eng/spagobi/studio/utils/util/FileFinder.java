package it.eng.spagobi.studio.utils.util;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;



public class FileFinder {


	public static boolean fileExistsInSubtree(String fileName,String directoryPath){

		File directory=new File(directoryPath);
		boolean found=searchInSubtree(fileName,directory);
		return found;
	}
	
	public static IPath retrieveFile(String fileName,String directoryPath, IPath workspacePath){

		File directory=new File(directoryPath);
		File file=getFileFromSubtree(fileName,directory);

		// remove from absolutePath workspace part
		String workspacePathS=workspacePath.toString();
		String filePath=file.getAbsolutePath();
		String relPath=filePath.substring(workspacePathS.length());
		//relPath=relPath.replaceAll("\\", "/");
		relPath=relPath.replaceAll("\\\\", "/");
		//relPath=relPath.replaceAll("\\", "/");
		IPath iPath=new Path(relPath);		


		return iPath;
	}

	

	public static boolean searchInSubtree(String fileName, File directory){

		boolean found=false;
		//if is a directory
		if(directory.isDirectory()){	// hould always be a directory
			String[] children = directory.list(); 
			for (int i = 0; i < children.length && found==false; i++) {
				// Build filePath
				String absPath=directory.getAbsolutePath()+"/"+children[i];
				try{
					absPath=absPath.replaceAll("\\\\", "/");
					absPath=absPath.replaceAll("\\", "/");
				}
				catch (Exception e) {
					int ii=0;
				}

				File file=new File(absPath);
//				if(file.isFile())System.out.println("- File: "+file.getName());
//				if(file.isDirectory())System.out.println("Directory: "+file.getName());
				// if it is a file check if it's name is equal to file we are searching
				if(file.isFile() && file.getName().equals(fileName)){
//					System.out.println("TROVATO");
					found=true;
				}
				else{
					// if it is a directory call it recursively
					if(file.isDirectory()){
						found=searchInSubtree(fileName, file);
					}
				}

			}
		}
		return found;
	}

	
	/** given the projects directory returns in subtree file with than name
	 * 
	 * @param fileName
	 * @param directory
	 * @return
	 */

	public static File getFileFromSubtree(String fileName, File directory){

		File toReturn=null;
		//if is a directory
		if(directory.isDirectory()){	// hould always be a directory
			String[] children = directory.list(); 
			for (int i = 0; i < children.length && toReturn==null; i++) {
				// Build filePath
				String absPath=directory.getAbsolutePath()+"/"+children[i];
				try{
					absPath=absPath.replaceAll("\\\\", "/");
					absPath=absPath.replaceAll("\\", "/");
				}
				catch (Exception e) {
					int ii=0;
				}

				File file=new File(absPath);
//				if(file.isFile())System.out.println("- File: "+file.getName());
//				if(file.isDirectory())System.out.println("Directory: "+file.getName());
				// if it is a file check if it's name is equal to file we are searching
				if(file.isFile() && file.getName().equals(fileName)){
//					System.out.println("TROVATO");
					toReturn=file;
				}
				else{
					// if it is a directory call it recursively
					if(file.isDirectory()){
						toReturn=getFileFromSubtree(fileName, file);
					}
				}

			}
		}
		return toReturn;
	}


	public static void main(String[] args) {
		String PATH="C:/prova";
		String FILE="filefile.txt";
		boolean found=FileFinder.fileExistsInSubtree(FILE, PATH);
//		System.out.println("Trovato? "+found);
	}

}
