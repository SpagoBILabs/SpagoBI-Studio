/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.jasper.editors;


import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JasperEditor implements IEditorLauncher {

	private static Logger logger = LoggerFactory.getLogger(JasperEditor.class);

	
	/**
	 *  Editor for Jasper document: opens ireport editor 
	 */

	
	public void open(IPath fileToEditIPath) {
logger.debug("in");
		try{
			// Catch the file to call path
			File fileT=fileToEditIPath.toFile();
			String fileToEditPath=fileT.getPath();
			String fileToEditDirectoryPath=fileT.getParent();
			IPath fileToEditDirectoryIPath=new Path(fileToEditDirectoryPath);
			// Launch I report, get from preferences the IReport Path
			IPreferenceStore store = it.eng.spagobi.studio.jasper.Activator.getDefault().getPreferenceStore();

			String iReportPathString = store.getString(SpagoBIStudioConstants.IREPORT_EXEC_FILE);

			if(iReportPathString==null || iReportPathString.equalsIgnoreCase("")){
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Warning", "You must define IReport path in preferences");
			}
			else{
				logger.debug("iReport path is "+iReportPathString);
				// get the directory Path
				Path iReportPath=new Path(iReportPathString);
				File iReportExec=iReportPath.toFile();
				File iReportDirectory=iReportExec.getParentFile();
				//				Date newD=new Date();
				//				String temp=iReportDirectory.getPath()+"/"+newD.toString()+".txt";
				//				new File(temp);
				String command=iReportPath+" \""+fileToEditPath+"\"";
				logger.debug("Command to launch: "+command+" --- in iReport Directory: "+iReportDirectory);
				Runtime rt = Runtime.getRuntime();
				logger.debug("start execution");

				Process proc  = rt.exec(command, null, iReportDirectory);		
				//proc.waitFor();
//				System.out.println(proc.toString());
				//int returnValue=proc.waitFor();
//				int returnValue=0;
//				
//				logger.debug("Return value is "+returnValue);
//				if(returnValue!=0){
//					logger.debug("Error during iReport Execution");
//					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
//							"Error", "Generic error after closing iReport: code "+Integer.valueOf(returnValue).toString());			
//				}
				IFile fileToEditIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(fileToEditIPath);
				IFile fileToEditDirectory = ResourcesPlugin.getWorkspace().getRoot().getFile(fileToEditDirectoryIPath);

				boolean isSync=fileToEditIFile.isSynchronized(2);				
				fileToEditIFile.refreshLocal(IResource.DEPTH_INFINITE, null);
				boolean isSync2=fileToEditDirectory.isSynchronized(2);				
				fileToEditDirectory.refreshLocal(IResource.DEPTH_INFINITE, null);
				logger.debug("Refreshed, exit jasper editor");
			}
		}
		catch(Exception e)
		{
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "Could not start iReport; check you selected the right execution file in preferences");			
		}
		logger.debug("OUT");

	}



}
