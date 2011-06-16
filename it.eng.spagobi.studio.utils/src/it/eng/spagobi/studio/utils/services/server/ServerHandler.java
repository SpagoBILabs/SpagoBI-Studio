package it.eng.spagobi.studio.utils.services.server;

import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.bo.xmlMapping.XmlServerGenerator;
import it.eng.spagobi.studio.utils.sdk.ProxyDataRetriever;
import it.eng.spagobi.studio.utils.util.ClassLoaderUtilities;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.util.Vector;

import org.apache.axis.AxisFault;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to handle server files
 * @author gavardi
 *
 */

public class ServerHandler {

	it.eng.spagobi.studio.utils.bo.Server server;
	String message;
	private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);	

	public ServerHandler(Server server) {
		super();
		this.server = server;
	}

	public ServerHandler() {
		super();
		this.server = null;
	}


	/**
	 *  test the connection of server
	 * @return boolean if test sccesfull and set test message
	 */
	public boolean testConnection() {
		logger.debug("IN");
		boolean testResult = false;
		message = "";
		try {
			//ClassLoaderUtilities.setSpagoBIClassLoader();
			TestConnectionServiceProxy proxy = new TestConnectionServiceProxy(server.getUser(), server.getPassword());
			proxy.setEndpoint(server.getUrl() + "/sdk/TestConnectionService");
			// set proxy configurations!
			new ProxyDataRetriever().initProxyData(proxy,server.getUrl());        

			// testing connection
			boolean result = proxy.connect();
			if (result) {
				logger.debug("test to "+server.getUrl()+ " succesfull");
				message = "                                                                                                     Test Succesfull";
				testResult = true;
			} else {
				logger.warn("test to "+server.getUrl()+ " failed");
				message = "Error in connection: check server definition";
				testResult = false;
			}
		} catch (AxisFault e) {
			logger.error("test to "+server.getUrl()+ " failed ",e);
			if (e.getFaultString().startsWith("WSDoAllReceiver")) {
				message = "Error inconnection: Authentication failed";
				testResult = false;
			} else {
				message = "Could not connect to SpagoBI Server "+e.getMessage();
				testResult = false;
			}
		} catch (Exception e) {
			logger.error("test to "+server.getUrl()+ " failed ",e);
			message = "Could not connect to SpagoBI Server: "+e.getMessage();
			testResult = false;
		}
		logger.debug("OUT");
		return testResult;
	}


	/**
	 *  Deactivate all other server except the selected one
	 * @param file
	 * @return
	 */

	public boolean deactivateOtherServers(IFile file){
		logger.debug("IN");
		boolean result = true;
		// get Server folder
		IFolder folder = (IFolder)file.getParent();
		try{
			IResource[] resources = folder.members();

			for (int i = 0; i < resources.length; i++) {
				IResource res = resources[i];
				logger.debug("resource "+res.getName());
				// check it is a file and it is a server
				if(res instanceof IFile && res.getName().endsWith("."+SpagoBIStudioConstants.SERVER_EXTENSION)){
					// if it is a server deactivate it
					logger.debug("reading file  "+res.getName());
					if(!res.getName().equals(file.getName())){  // do not deactivate current file
						XmlServerGenerator.deactivateServer((IFile)res);
					}
				}
				else{
					logger.debug("file "+res.getName()+ " present in Server folder, only server files should be here");					
				}
			}
		}
		catch (Exception e) {
			logger.error("Error in setting the document as template ", e);
			result = false;
		}
		logger.debug("OUT");
		return result;
	}


	/**
	 *  get active server
	 * @param projectname
	 * @return
	 */
	public Server getCurrentActiveServer(String projectname){
		logger.debug("IN");
		Server active = null;
		// when dealing with server check class loader
		//ClassLoaderUtilities.setSpagoBIClassLoader();
		
		Vector<Server> names = getCurrentActiveServers(projectname);

		if(names.size()== 0){
			logger.warn("No active server found");
		}
		else{
			active = names.get(0);
			logger.debug("active server is "+active.getName());
			if(names.size()>1){
				logger.warn("more than one active server found, by default is taken the first found "+active.getName());
			}
		}

		logger.debug("OUT");
		return active;
	}

	
	
	
	
	
	
	
	
	/**
	 *  getCurrentActiveServers from project analysis file
	 * @return
	 */

	public Vector<Server> getCurrentActiveServers(String projectname){
		logger.debug("IN");
		// get Server folder
		Vector<Server> servers = new Vector<Server>();

		IFolder serverFolder = null;
		serverFolder = getServerRoot(projectname);

		if(serverFolder == null){
			logger.error("Error in retrieving server root folder; not found");
			return null;
		}


		try{
			IResource[] resources = serverFolder.members();

			for (int i = 0; i < resources.length; i++) {
				IResource res = resources[i];
				logger.debug("resource "+res.getName());
				// check it is a file and it is a server
				if(res instanceof IFile && res.getName().endsWith("."+SpagoBIStudioConstants.SERVER_EXTENSION)){
					// if it is a server deactivate it
					logger.debug("reading file  "+res.getName());
					Server server = XmlServerGenerator.readXml((IFile)res);
					if(server.isActive()){
						servers.add(server);
					}
				}
				else{
					logger.debug("file "+res.getName()+ " present in Server folder, only server files should be here");					
				}
			}
		} 
		catch (ResourceException e) {
			logger.error("Check file is sync: try refreshing the server folder", e);
		MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning", "Error in reading the file: to check the resource is sync with file systrem please try refreshing the server folder");
		}
		catch (Exception e) {
			logger.error("Error in reading xml file ", e);
			return null;
		}

		logger.debug("OUT");
		return servers;
	}
	
	
	

//	/**
//	 *  getCurrentActiveServers from project analysis file
//	 * @return
//	 */
//
//	public Vector<String> getCurrentActiveServers(IFile analysisFile){
//		logger.debug("IN");
//		// get Server folder
//		Vector<String> names = new Vector<String>();
//
//		IFolder serverFolder = null;
//		serverFolder = getServerRootFromFile(analysisFile);
//
//		if(serverFolder == null){
//			logger.error("Error in retrieving server root folder; not found");
//			return null;
//		}
//
//
//		try{
//			IResource[] resources = serverFolder.members();
//
//			for (int i = 0; i < resources.length; i++) {
//				IResource res = resources[i];
//				logger.debug("resource "+res.getName());
//				// check it is a file and it is a server
//				if(res instanceof IFile && res.getName().endsWith("."+SpagoBIStudioConstants.SERVER_EXTENSION)){
//					// if it is a server deactivate it
//					logger.debug("reading file  "+res.getName());
//					Server server = XmlServerGenerator.readXml((IFile)res);
//					if(server.isActive()){
//						names.add(server.getName());
//					}
//				}
//				else{
//					logger.debug("file "+res.getName()+ " present in Server folder, only server files should be here");					
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Error in reading xml file ", e);
//			return null;
//		}
//
//		logger.debug("OUT");
//		return names;
//	}

	/**
	 *  get SERVER root file from current file
	 * @param analysisFile
	 * @return
	 */
	public IFolder getServerRootFromFile(IFile analysisFile){
		logger.debug("IN");
		IFolder serverFolder = null;
		try{

			IProject project = analysisFile.getProject();

			IPath resourcePath = project.getProjectRelativePath().append(SpagoBIStudioConstants.FOLDER_RESOURCE);
			IFolder  resourceFolder = 	project.getFolder(resourcePath);

			serverFolder = resourceFolder.getFolder(SpagoBIStudioConstants.FOLDER_SERVER);
		}
		catch (Exception e) {
			logger.error("Error in retrieving server root folder ", e);
			return null;
		}
		logger.debug("OUT");
		return serverFolder;
	}



	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 *  get SERVER root file from current file
	 * @param analysisFile
	 * @return
	 */
	public IFolder getServerRoot(String projectName){
		logger.debug("IN");
		IFolder serverFolder = null;
		try{

			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project= root.getProject(projectName);
			IPath porjPath = project.getFullPath();
			IPath resourcePath = porjPath.append(SpagoBIStudioConstants.FOLDER_RESOURCE);
			IPath serverPath = resourcePath.append(SpagoBIStudioConstants.FOLDER_SERVER);
			 serverFolder = 	root.getFolder(serverPath);

//			serverFolder = resourceFolder.getFolder(SpagoBIStudioConstants.FOLDER_SERVER);
		}
		catch (Exception e) {
			logger.error("Error in retrieving server root folder ", e);
			return null;
		}
		logger.debug("OUT");
		return serverFolder;
	}

}
