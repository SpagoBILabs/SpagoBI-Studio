/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo.xmlMapping;

import it.eng.spagobi.studio.utils.bo.DocumentParameter;
import it.eng.spagobi.studio.utils.bo.DocumentParameters;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class XmlParametersMapping {

	public static IFile setFileParametersMetaData(IFile newFile, DocumentParameter[] parameters) throws CoreException{
		String xml="";
		ArrayList<DocumentParameter> list=new ArrayList<DocumentParameter>();
		for (int i = 0; i < parameters.length; i++) {
			list.add(parameters[i]);
		}
		DocumentParameters pars=new DocumentParameters(list);
	
		XStream xstream = setParametersXStream();
		xml = xstream.toXML(pars);		
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_PARAMETERS_XML,xml);
		return newFile;
	}



	public static List<DocumentParameter> getParametersFromXML(String xmlParameters) throws CoreException{
		List<DocumentParameter> list=null;
		if(xmlParameters!=null && !xmlParameters.equalsIgnoreCase(""))
		{
			XStream xstream = setParametersXStream();
			DocumentParameters parametersMetaDataObject= (DocumentParameters)xstream.fromXML(xmlParameters);
			list=parametersMetaDataObject.getContent();
		}
		return list;
	}
	
	
	public static DocumentParameters getDocumentParametersFromXML(String xmlParameters) throws CoreException{
		DocumentParameters parametersMetaDataObject=null;
		if(xmlParameters!=null && !xmlParameters.equalsIgnoreCase(""))
		{
			XStream xstream = setParametersXStream();
			parametersMetaDataObject= (DocumentParameters)xstream.fromXML(xmlParameters);
		}
		return parametersMetaDataObject;
	}
	
	
	
	public static XStream setParametersXStream(){
		XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");

		XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
		xstream.alias("SDK_DOCUMENT_PARAMETERS", DocumentParameters.class);
		xstream.alias("PARAMETER", DocumentParameter.class);
		xstream.useAttributeFor(DocumentParameter.class, "id");
		xstream.useAttributeFor(DocumentParameter.class, "label");
		xstream.useAttributeFor(DocumentParameter.class, "type");
		xstream.useAttributeFor(DocumentParameter.class, "urlName");
		xstream.omitField(DocumentParameter.class, "values");		
		xstream.omitField(DocumentParameter.class, "constraints");
		xstream.omitField(DocumentParameter.class, "__hashCodeCalc");

		return xstream;
	}
	


}
