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
package it.eng.spagobi.studio.documentcomposition.util;

import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Refresh;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;

import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class XmlTemplateGenerator {


	public static void setAlias(XStream xstream){
		xstream.alias("DOCUMENTS_COMPOSITION", DocumentComposition.class);
		xstream.useAttributeFor(DocumentComposition.class, "templateValue");
		xstream.aliasField("template_value", DocumentComposition.class, "templateValue");


		xstream.aliasField("DOCUMENTS_CONFIGURATION", DocumentComposition.class, "documentsConfiguration"); 
		xstream.useAttributeFor(DocumentsConfiguration.class, "videoWidth");
		xstream.aliasField("video_width", DocumentsConfiguration.class, "videoWidth");        

		xstream.useAttributeFor(DocumentsConfiguration.class, "videoHeight");
		xstream.aliasField("video_height", DocumentsConfiguration.class, "videoHeight");

		xstream.addImplicitCollection(DocumentsConfiguration.class, "documents", "DOCUMENT", Document.class);

		xstream.useAttributeFor(Document.class, "sbiObjLabel");
		xstream.aliasField("sbi_obj_label", Document.class, "sbiObjLabel");

		try{
			xstream.useAttributeFor(Document.class, "localFileName");
			xstream.aliasField("local_file_name", Document.class, "localFileName");
		}
		catch (Exception e) {
			// if not treated		
			}

			xstream.aliasField("STYLE", Document.class, "style");
			xstream.useAttributeFor(Style.class, "style");
			xstream.useAttributeFor(Style.class, "mode");
			xstream.aliasField("style", Style.class, "style");        

			xstream.aliasField("PARAMETERS", Document.class, "parameters");

			xstream.addImplicitCollection(Parameters.class, "parameter", "PARAMETER", Parameter.class);

			xstream.omitField(Parameter.class, "bo");
			xstream.useAttributeFor(Parameter.class, "navigationName");
			xstream.aliasField("navigationName", Parameter.class, "navigationName");

			xstream.useAttributeFor(Parameter.class, "type");
			xstream.aliasField("type", Parameter.class, "type");
			
			xstream.useAttributeFor(Parameter.class, "id");
			xstream.aliasField("id", Parameter.class, "id");

			xstream.useAttributeFor(Parameter.class, "sbiParLabel");
			xstream.aliasField("sbi_par_label", Parameter.class, "sbiParLabel");

			xstream.useAttributeFor(Parameter.class, "defaultVal");
			xstream.aliasField("default_value", Parameter.class, "defaultVal");

			xstream.aliasField("REFRESH", Parameter.class, "refresh");

			xstream.addImplicitCollection(Refresh.class, "refreshDocLinked", "REFRESH_DOC_LINKED", RefreshDocLinked.class);

			xstream.useAttributeFor(RefreshDocLinked.class, "labelDoc");
			xstream.aliasField("labelDoc", RefreshDocLinked.class, "labelDoc");

			xstream.useAttributeFor(RefreshDocLinked.class, "labelParam");
			xstream.aliasField("labelParam", RefreshDocLinked.class, "labelParam");
			
			xstream.useAttributeFor(RefreshDocLinked.class, "idParam");
			xstream.aliasField("idParam", RefreshDocLinked.class, "idParam");

		}


		public static String transformToXml(Object bean) {


			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			xstream.setMode(XStream.NO_REFERENCES);

			setAlias(xstream);	

			String xml = xstream.toXML(bean);
			//System.out.println(xml);
			return xml;
		}


		public static DocumentComposition readXml(IFile file) throws CoreException{
			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			setAlias(xstream);	
			DocumentComposition objFromXml = (DocumentComposition)xstream.fromXML(file.getContents());
			objFromXml.setTemplateValue("/jsp/engines/documentcomposition/template/dynamicTemplate.jsp");
			return objFromXml;
		}



		public static void main(String[] args) {
//
//			DocumentComposition docComp = new DocumentComposition();
//
//			DocumentsConfiguration documentsConfiguration = new DocumentsConfiguration();
//
//			RefreshDocLinked refreshDocLinked = new RefreshDocLinked();
//			refreshDocLinked.setLabelDoc("doc1");
//			refreshDocLinked.setLabelParam("i1");
//
//
//			Vector rv = new Vector();
//			rv.add(refreshDocLinked);
//
//			Refresh refresh = new Refresh();
//			refresh.setRefreshDocLinked(rv);
//		Parameter i1= new Parameter();
//		i1.setDefaultVal("");
//	
//		i1.setSbiParLabel("sb1");
//		i1.setType("IN");
//		i1.setRefresh(refresh);
//
//			Parameter i1= new Parameter();
//			i1.setDefaultVal("");
//			i1.setSbiParLabel("sb1");
//			i1.setType("IN");
//			i1.setRefresh(refresh);
//		Parameter i2= new Parameter();
//		i2.setDefaultVal("");
//	
//		i2.setSbiParLabel("sb2");
//		i2.setType("IN");
//		i2.setRefresh(refresh);
//
//			Parameter i2= new Parameter();
//			i2.setDefaultVal("");
//			i2.setSbiParLabel("sb2");
//			i2.setType("IN");
//			i2.setRefresh(refresh);
//
//			Vector p = new Vector();
//			p.add(i1);
//			p.add(i2);
//
//			Parameters parameters = new Parameters();
//			parameters.setParameter(p);
//
//			Style style = new Style();
//			style.setStyle("float:left; width:49%;");
//		Document doc1 = new Document();
//
//		doc1.setSbiObjLabel("sbi doc1 label");
//		doc1.setStyle(style);
//		doc1.setParameters(parameters);
//
//			Document doc1 = new Document();
//			doc1.setSbiObjLabel("sbi doc1 label");
//			doc1.setStyle(style);
//			doc1.setParameters(parameters);
//		Document doc2 = new Document();
//
//		doc2.setSbiObjLabel("sbi doc2 label");
//		doc2.setStyle(style);
//
//			Document doc2 = new Document();
//			doc2.setSbiObjLabel("sbi doc2 label");
//			doc2.setStyle(style);
//		Document doc3 = new Document();
//
//		doc3.setSbiObjLabel("sbi doc3 label");
//		doc3.setStyle(style);
//
//			Document doc3 = new Document();
//			doc3.setSbiObjLabel("sbi doc3 label");
//			doc3.setStyle(style);
//
//			Vector docsVector = new Vector();
//			docsVector.add(doc1);
//			docsVector.add(doc2);
//			docsVector.add(doc3);
//
//
//			documentsConfiguration.setVideoWidth("1400");
//			documentsConfiguration.setVideoHeight("1050");
//			documentsConfiguration.setDocuments(docsVector);
//
//
//			docComp.setTemplateValue("xxx.jsp");
//			docComp.setDocumentsConfiguration(documentsConfiguration);
//
//
//
//			transformToXml(docComp);
		}
	}
