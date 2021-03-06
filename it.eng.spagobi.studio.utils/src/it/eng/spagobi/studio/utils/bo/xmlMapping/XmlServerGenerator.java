/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo.xmlMapping;

import it.eng.spagobi.studio.utils.bo.Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class XmlServerGenerator {

	private static Logger logger = LoggerFactory.getLogger(XmlServerGenerator.class);

	public static void setServerAlias(XStream xstream){
		xstream.alias("SERVER", Server.class);
		xstream.useAttributeFor(Server.class, "name");
		xstream.aliasField("name", Server.class, "name");
		xstream.useAttributeFor(Server.class, "url");
		xstream.aliasField("url", Server.class, "url");
		xstream.useAttributeFor(Server.class, "user");
		xstream.aliasField("user", Server.class, "user");
		xstream.useAttributeFor(Server.class, "password");
		xstream.aliasField("password", Server.class, "password");
		xstream.useAttributeFor(Server.class, "active");
		xstream.aliasField("active", Server.class, "active");
	}


	public static String transformToXml(Server bean) {

		XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
		XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
		xstream.setMode(XStream.NO_REFERENCES);

		setServerAlias(xstream);	

		String xml = xstream.toXML(bean);
		return xml;
	}

	/** populate the Server Object from template*/
	public static Server readXml(IFile file) throws CoreException{
		XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
		XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
		setServerAlias(xstream);	
		Server objFromXml = (Server)xstream.fromXML(file.getContents());
		return objFromXml;
	}


	public static void deactivateServer(IFile file) throws CoreException{
		logger.debug("IN");
		ByteArrayInputStream bais = null;

		try{
			Server server = readXml(file);
			server.setActive(false);
			String xmlString = transformToXml(server);

			byte[] bytes = xmlString.getBytes();

			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);
		}
		catch (Exception e) {
			logger.error("error in deactivating file "+file.getName());
		}
		finally{
			if (bais != null)
				try {
					bais.close();
				} catch (IOException e) {
					logger.error("error in closing the byte array");
					return;
				}
		}
		logger.debug("OUT");
	}
	
	/**
	 *  check if server defined in file is active
	 * @param file
	 * @return
	 */
	public boolean isServerActive(IFile file){
		logger.debug("IN");
		boolean active = false;
		try{
			Server server = XmlServerGenerator.readXml(file);
			active = server.isActive();
		}
		catch (Exception e) {
			logger.error("error in setting server icon to active, put default none",e);
			active=false;
		}
		logger.debug("OUT");
		return active;
	}


	public static void main(String[] args) {

	}
}
