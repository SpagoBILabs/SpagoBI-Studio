/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.services.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class ResourcesHandler {

	private static Logger logger = LoggerFactory.getLogger(ResourcesHandler.class);

	
	
	
	public boolean deleteResources(ISelection selection){
		
		logger.debug("IN");
		
		IStructuredSelection structuredSelection = (IStructuredSelection)selection;
		List<Object> selectedObjects = structuredSelection.toList();
		
		boolean delete = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Delete Resource", "Confirm deleting resource(s)");
		if(delete){
			for (Object selectedObject : selectedObjects) {
				if(selectedObject instanceof IResource){
					IResource res = (IResource) selectedObject;
					deleteResource(res);
				}
			}
		}
		
		logger.debug("OUT");
		
		return true;
	}
	
	public boolean deleteResource(IResource resource) {
		try{
			if ((resource.getFileExtension()!= null ) && (resource.getFileExtension().equals("sbimodel"))){
				deleteModelResource(resource);
			} else {
				resource.delete(true, null);
				logger.debug("resource [" + resource.getName() + "] succesfully deleted ");
			}
		}
		catch (Exception e) {
			logger.error("Error in deleting the resource", e);	
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					, "Error", "Error in deleting the resource; try deleting it from resource navigato view");
		}
		
		return true;
	}
	
	public boolean deleteModelResource(IResource modelResource) {
		try {
	
			String modelName = null;
			try {
				modelName = getModelName(modelResource);
			} catch(Throwable t) {
				logger.warn("Impossible to delete resources related to model stored in file [" + modelResource.getRawLocation().toFile() + "]");
			}
		    
			modelResource.delete(true, null);
			
			if(modelName != null) {
				deleteModelMappingFolder(modelResource, modelName);
				deleteModelQueries(modelResource, modelName);	
			}
			

			logger.debug("resource cancelled " + modelResource.getName());
		
		} catch (Exception e) {
			logger.error("Error in deleting the resource", e);	
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in deleting the resource; try deleting it from resource navigato view");
		}
		
		return true;
	}
	
	public void deleteModelQueries(IResource modelResource, String modelName) {
		List<IResource> projectResources = getProjectResource(modelResource.getProject()) ;
		for ( IResource projectResource : projectResources )  {
			if ((projectResource.getFileExtension()!= null) && ( projectResource.getFileExtension().equals("metaquery"))){
				
				String queryFileModelName = null;
				try {
					queryFileModelName = projectResource.getPersistentProperty(new QualifiedName("it.eng.spagobi.meta.editor.modelId", "modelId"));
				} catch (CoreException e) {
					logger.warn("Impossible to read the value of property [modelId] from query ["+ projectResource.getName() + "]");
				}
				
				if(modelName.equals(queryFileModelName)){
					try {
						projectResource.delete(true, null);
					} catch (Throwable t) {
						logger.warn("Impossible ro delete query ["+ projectResource.getName() + "]");
					}
				}
			}
		}
	}
	
	public void deleteModelMappingFolder(IResource modelResource, String modelName) {
		
		File parentDirectory = modelResource.getParent().getRawLocation().toFile();
		File mappingDirectory = new File(parentDirectory, modelName);
	    if (deleteDir(mappingDirectory)){
	    	logger.debug("Mapping folder of model [" + modelName + "] deleted succesfully");
	    	try {
	    		modelResource.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
	    	} catch(Throwable t) {
				logger.warn("Impossibe to refresh contents of folder [" + parentDirectory + "] after the deletion its subfolder [" + mappingDirectory + "] ");
			}
		    
		} else  {
			logger.warn("Impossible to delete mapping folder of model [" + modelName + "]");
		}
		
	} 
	
	public String getModelName(IResource resources) {
		File modelFile;
		String modelName;
		
		logger.debug("IN");
		
		modelFile = null;
		modelName = null;
		try  {
			modelFile = resources.getRawLocation().toFile();
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true); 
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(modelFile);
		    
			XPath xpath = XPathFactory.newInstance().newXPath();   
		    XPathExpression expr = xpath.compile("//*[@name]");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
			    
		    if  (nodes.getLength()>0) {
		    	//get the first node (root) 
		    	NamedNodeMap nodeAttributes = nodes.item(1).getAttributes();
	        	if (nodeAttributes != null) {
	        		//retrieve Metamodel Name
	        		modelName = nodeAttributes.getNamedItem("name").getNodeValue();
	        	}
		    }
		    
		    return modelName;
		    
		} catch(Throwable t) {
			throw new RuntimeException("Impossible to read model name from file [" + modelFile + "]. Please check if it is a valid .sbimodel file");
		} finally {
			logger.debug("OUT");
		}
	}
	
	public List<IResource> getProjectResource(IProject project) {
		List<IResource> projectContents = new ArrayList<IResource>();
		try {
			IResource[] projectMembers = project.members();
			getFolderChildren(Arrays.asList(projectMembers), projectContents);
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return projectContents;
		
	}
	
	public void getFolderChildren(List<IResource> levelElements, List<IResource> allContents){
		allContents.addAll(levelElements);
		try {
			for (IResource resource : levelElements){
				if (resource.getType() == IResource.FOLDER){
					IResource[] folderMembers = ((IFolder)resource).members();
					if (folderMembers.length > 0){
						getFolderChildren( Arrays.asList(folderMembers), allContents );
					} 				
				}
			}
			return;
		} catch (CoreException e){
			e.printStackTrace();
		}

	}

	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}



}
