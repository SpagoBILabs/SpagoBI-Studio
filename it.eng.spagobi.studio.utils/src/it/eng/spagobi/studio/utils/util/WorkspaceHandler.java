/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.util;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkspaceHandler {

	private static Logger logger = LoggerFactory.getLogger(WorkspaceHandler.class);

	public IProject[] getProjects(){
		logger.debug("IN");
		IWorkspace workspace= ResourcesPlugin.getWorkspace();  		
		IWorkspaceRoot root= workspace.getRoot();
		IProject[] projects = root.getProjects();

		logger.debug("OUT");
		return projects;
	}
	
	public String[] getProjectNames(){
		logger.debug("IN");
		String[] toReturn = null;
		IWorkspace workspace= ResourcesPlugin.getWorkspace();  		
		IWorkspaceRoot root= workspace.getRoot();
		IProject[] projects = root.getProjects();

		toReturn = new String[projects.length];
		for (int i = 0; i < projects.length; i++) {
			toReturn[i] = projects[i].getName();
		}
		
		logger.debug("OUT");
		return toReturn;
	}

	
	public IProject getProjectRootFolder(String projectName){
		logger.debug("IN");
		IWorkspace workspace= ResourcesPlugin.getWorkspace();  		
		IWorkspaceRoot root= workspace.getRoot();
		IProject project = root.getProject(projectName);
		if(project == null){
			logger.error("Could not find root for project "+projectName);
		}
		
		//IFolder prFolder = project.get
		
		logger.debug("OUT");
	return project;
	}

}
