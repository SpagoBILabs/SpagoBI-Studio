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

		XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
		XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
		DocumentParameters pars=new DocumentParameters(list);
		xstream.alias("SDK_DOCUMENT_PARAMETERS", DocumentParameters.class);
		xstream.alias("PARAMETER", DocumentParameter.class);
		xstream.useAttributeFor(DocumentParameter.class, "id");
		xstream.useAttributeFor(DocumentParameter.class, "label");
		xstream.useAttributeFor(DocumentParameter.class, "type");
		xstream.useAttributeFor(DocumentParameter.class, "urlName");
		xstream.omitField(DocumentParameter.class, "values");		
		xstream.omitField(DocumentParameter.class, "constraints");
		xstream.omitField(DocumentParameter.class, "__hashCodeCalc");
		xml = xstream.toXML(pars);		
		newFile.setPersistentProperty(SpagoBIStudioConstants.DOCUMENT_PARAMETERS_XML,xml);
		return newFile;
	}



	public static List<DocumentParameter> getParametersFromXML(String xmlParameters) throws CoreException{
		List<DocumentParameter> list=null;
		if(xmlParameters!=null && !xmlParameters.equalsIgnoreCase(""))
		{
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
			DocumentParameters parametersMetaDataObject= (DocumentParameters)xstream.fromXML(xmlParameters);
			list=parametersMetaDataObject.getContent();
		}
		return list;
	}


}
