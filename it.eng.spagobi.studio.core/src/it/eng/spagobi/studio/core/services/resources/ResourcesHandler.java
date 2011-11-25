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


	public boolean deleteResource(ISelection selection){
		logger.debug("IN");
		boolean toreturn = false;

		IStructuredSelection sel=(IStructuredSelection)selection;
		
		List<Object> list = sel.toList();
		boolean delete = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Delete Resource", "Confirm Deleting resource");
		if(delete){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object objSel = (Object) iterator.next();

				if(objSel instanceof IResource){
					IResource res = (IResource) objSel;
					try{
						//case SbiModel(SpagoBI Meta)
						if ((res.getFileExtension()!= null ) && (res.getFileExtension().equals("sbimodel"))){

							File modelFile = res.getRawLocation().toFile();
							File parentDirectory = res.getParent().getRawLocation().toFile();
							
							DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
							domFactory.setNamespaceAware(true); 
							DocumentBuilder builder = domFactory.newDocumentBuilder();
							Document doc = builder.parse(modelFile);
						    XPath xpath = XPathFactory.newInstance().newXPath();
						    
						    XPathExpression expr = xpath.compile("//*[@name]");

						    Object result = expr.evaluate(doc, XPathConstants.NODESET);
						    NodeList nodes = (NodeList) result;
						    
						    String modelName=null;
						    if  (nodes.getLength()>0) {
						    	//get the first node (root) 
						    	NamedNodeMap nodeAttributes = nodes.item(1).getAttributes();
					        	if (nodeAttributes != null) {
					        		//retrieve Metamodel Name
					        		modelName = nodeAttributes.getNamedItem("name").getNodeValue();
					        		System.out.println(modelName);
					        	}
						    }
						    File mappingDirectory = new File(parentDirectory,modelName);
							
						    /* Delete directory and files created for mapping */
							
						    //if (deleteMapping){
							    if (deleteDir(mappingDirectory)){
									res.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
							    	logger.debug("Mapping directory of ["+modelName+"] removed");
							    }
							//}
							    
							/* Delete query files */
							List<IResource> projectContents = getProjectContents(res.getProject()) ;
							for (IResource resource : projectContents )  {
								if ((resource.getFileExtension()!= null) && ( resource.getFileExtension().equals("metaquery"))){
									String queryFileModelName = resource.getPersistentProperty(new QualifiedName("it.eng.spagobi.meta.editor.modelId", "modelId"));
									if(queryFileModelName.equals(modelName)){
										resource.delete(true, null);
									}
								}
							}
							    
						    /* Delete .sbimodel file */
							res.delete(true, null);
							logger.debug("resource cancelled "+res.getName());
						    
						}
						else {
							res.delete(true, null);
							logger.debug("resource cancelled "+res.getName());
						}

				
					}
					catch (Exception e) {
						logger.error("Error in deleting the resource", e);	
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Error in deleting the resource; try deleting it from resource navigato view");
						toreturn = false;
					}
				}
			}
			toreturn = true;
		}
		logger.debug("OUT");
		return true;


	}
	
	public List<IResource> getProjectContents(IProject project) {
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
