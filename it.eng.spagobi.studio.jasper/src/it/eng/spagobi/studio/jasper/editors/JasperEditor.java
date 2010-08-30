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
package it.eng.spagobi.studio.jasper.editors;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.preferences.PreferenceConstants;

import java.io.File;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.PlatformUI;

public final class JasperEditor implements IEditorLauncher {

	/**
	 *  Editor for Jasper document: opens ireport editor 
	 */

	public void open(IPath fileToEditIPath) {

		try{
			// Catch the file to call path
			File fileT=fileToEditIPath.toFile();
			String fileToEditPath=fileT.getPath();
			String fileToEditDirectoryPath=fileT.getParent();
			IPath fileToEditDirectoryIPath=new Path(fileToEditDirectoryPath);
			// Launch I report, get from preferences the IReport Path
			IPreferenceStore store = it.eng.spagobi.studio.jasper.Activator.getDefault().getPreferenceStore();

			String iReportPathString = store.getString(PreferenceConstants.IREPORT_EXEC_FILE);

			if(iReportPathString==null || iReportPathString.equalsIgnoreCase("")){
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Warning", "You must define IReport path in preferences");
			}
			else{
				SpagoBILogger.infoLog("iReport path is "+iReportPathString);
				// get the directory Path
				Path iReportPath=new Path(iReportPathString);
				File iReportExec=iReportPath.toFile();
				File iReportDirectory=iReportExec.getParentFile();
				//				Date newD=new Date();
				//				String temp=iReportDirectory.getPath()+"/"+newD.toString()+".txt";
				//				new File(temp);
				String command=iReportPath+" \""+fileToEditPath+"\"";
				SpagoBILogger.infoLog("Command to launch: "+command+" --- in iReport Directory: "+iReportDirectory);
				Runtime rt = Runtime.getRuntime();
				SpagoBILogger.infoLog("start execution");

				Process proc  = rt.exec(command, null, iReportDirectory);		
				//proc.waitFor();
//				System.out.println(proc.toString());
				//int returnValue=proc.waitFor();
//				int returnValue=0;
//				
//				SpagoBILogger.infoLog("Return value is "+returnValue);
//				if(returnValue!=0){
//					SpagoBILogger.infoLog("Error during iReport Execution");
//					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
//							"Error", "Generic error after closing iReport: code "+Integer.valueOf(returnValue).toString());			
//				}
				IFile fileToEditIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(fileToEditIPath);
				IFile fileToEditDirectory = ResourcesPlugin.getWorkspace().getRoot().getFile(fileToEditDirectoryIPath);

				boolean isSync=fileToEditIFile.isSynchronized(2);				
				fileToEditIFile.refreshLocal(IResource.DEPTH_INFINITE, null);
				boolean isSync2=fileToEditDirectory.isSynchronized(2);				
				fileToEditDirectory.refreshLocal(IResource.DEPTH_INFINITE, null);
				SpagoBILogger.infoLog("Refreshed, exit jasper editor");
			}
		}
		catch(Exception e)
		{
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "Could not start iReport; check you selected the right execution file in preferences");			
		}

	}



}
