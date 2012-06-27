/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.model;

import it.eng.spagobi.studio.highchart.model.bo.Area;
import it.eng.spagobi.studio.highchart.model.bo.Chart;
import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.Legend;
import it.eng.spagobi.studio.highchart.model.bo.PlotOptions;
import it.eng.spagobi.studio.highchart.model.bo.Series;
import it.eng.spagobi.studio.highchart.model.bo.SeriesList;
import it.eng.spagobi.studio.highchart.model.bo.SubTitle;
import it.eng.spagobi.studio.highchart.model.bo.Title;
import it.eng.spagobi.studio.highchart.model.bo.XAxis;
import it.eng.spagobi.studio.highchart.model.bo.YAxis;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class Prova {

	private static Logger logger = LoggerFactory.getLogger(XmlTemplateGenerator.class);


	public static void setAlias(XStream xstream){
		xstream.alias("AREA", Area.class);
		xstream.useAttributeFor(Area.class, "lineWidth");
		xstream.useAttributeFor(Area.class, "ciao");
		xstream.aliasField("lineWidth", Area.class, "lineWidth");
		xstream.aliasField("ciao", Area.class, "ciao");


		}


		public static String transformToXml(Object bean) {


			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("_", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			xstream.setMode(XStream.NO_REFERENCES);
			setAlias(xstream);	
			String xml = xstream.toXML(bean);
			return xml;
		}

/** populate the HighChart Object from template*/
		public static HighChart readXml(IFile file) throws CoreException{
			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			setAlias(xstream);	
			HighChart objFromXml = (HighChart)xstream.fromXML(file.getContents());
			return objFromXml;
		}



		public static void main(String[] args) {

			HighChart docComp = new HighChart();

			Area area = new Area();
			
			area.setLineWidth(2);
			
			String cia= transformToXml(area);
			System.out.println(area);
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
