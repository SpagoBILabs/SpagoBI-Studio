package it.eng.spagobi.studio.core.services.server;

import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
import it.eng.spagobi.studio.core.bo.Server;
import it.eng.spagobi.studio.core.bo.xmlMapping.XmlServerGenerator;
import it.eng.spagobi.studio.core.sdk.ProxyDataRetriever;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import org.apache.axis.AxisFault;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to handle server files
 * @author gavardi
 *
 */

public class ServerHandler {

	Server server;
	String message;
	private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);	

	public ServerHandler(Server server) {
		super();
		this.server = server;
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




	public boolean setActiveServer(IFile file){
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




	private boolean disactiveOtherServers() {
		logger.debug("IN");

		logger.debug("OUT");
		return false;
	}




	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}



}
