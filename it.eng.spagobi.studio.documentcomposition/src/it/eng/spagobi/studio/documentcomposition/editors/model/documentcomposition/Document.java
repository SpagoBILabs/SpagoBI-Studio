/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;

import java.util.Iterator;
import java.util.Vector;



public class Document {

	private String sbiObjLabel;
	private String localFileName;	
	private Parameters parameters;//parameters
	private Style style; 
	private String id;

	//	private static long idCounter = 0;
	//
	//	public static synchronized String createID()
	//	{
	//		return String.valueOf(idCounter++);
	//	}

	public Document() {
		//this.id = createID();
	}

	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public String getLocalFileName() {
		return localFileName;
	}
	public void setLocalFileName(String localFileName) {
		this.localFileName = localFileName;
	}
	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	public String getSbiObjLabel() {
		return sbiObjLabel;
	}
	public void setSbiObjLabel(String sbiObjLabel) {
		this.sbiObjLabel = sbiObjLabel;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/** crate a new document: populate wth parameters taken from file metadata
	 * 
	 * @param metadataDocument
	 * @param _style
	 */
	
	public Document(MetadataDocument metadataDocument, Style _style) {
		super();
		sbiObjLabel=metadataDocument.getLabel();
		localFileName=metadataDocument.getLocalFileName();
		style=_style;
		parameters = new Parameters(); 
		parameters.setParameter(new Vector<Parameter>());
		
		DocumentComposition docComposition=Activator.getDefault().getDocumentComposition();
 		// NO MORE 20100902
		//new ModelBO().addMetadataParametersToDocumentParameters(docComposition, this, metadataDocument);
		
		
		// Set also the input parameters of the document!
//		if(metadataDocument.getMetadataParameters()!=null){
//			for (Iterator iterator = metadataDocument.getMetadataParameters().iterator(); iterator.hasNext();) {
//				MetadataParameter metaParameter = (MetadataParameter) iterator.next();
//				String label=metaParameter.getLabel();
//				//String type=metaParameter.getType();			
//				String type="IN";
//				String label = metaParameter.getLabel();
//				String urlName=metaParameter.getUrlName();
//				Integer id=metaParameter.getId();
//				// if not already present add it
//				if(parameters==null) {
//					parameters=new Parameters();	
//				}
//				Vector<Parameter> vector=parameters.getParameter();
//				if(vector==null){
//					parameters.setParameter(new Vector<Parameter>());
//				}
//				DocumentComposition docComposition=Activator.getDefault().getDocumentComposition();
//				Parameter par=new Parameter(docComposition);
//				par.setDefaultVal("");
//				par.setSbiParLabel(label);
//				par.setType(type);
//				par.setNavigationName(label);
//				parameters.getParameter().add(par);
//			}
//		}
		this.id = metadataDocument.getIdMetadataDocument();
	}	


	/** return true if document already contains the parameter with the label
	 * 
	 */
	public boolean containsParameter(String urlName){
		boolean toRet = false;
		
		for (Iterator iterator = getParameters().getParameter().iterator(); iterator.hasNext() && !toRet;) {
			Parameter par = (Parameter) iterator.next();
			if(par.getSbiParLabel().equals(urlName)){
				toRet = true;
			}
		}
		return toRet;
	}
	

}
