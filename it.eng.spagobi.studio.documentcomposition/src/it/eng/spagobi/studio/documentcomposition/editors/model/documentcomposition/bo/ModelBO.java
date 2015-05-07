/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;
import it.eng.spagobi.studio.documentcomposition.util.XmlTemplateGenerator;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ModelBO {

/** create the DocumentCOmposition model by reading the file: the documentCOmposition model will contain everything that will be mapped on the template on saving action
 * 
 * @param file
 * @return
 * @throws CoreException
 */
	
	public DocumentComposition createModel(IFile file) throws CoreException{
		// read the XML File
		DocumentComposition documentComposition = XmlTemplateGenerator.readXml(file);
		if(documentComposition.getDocumentsConfiguration()==null){
			documentComposition.setDocumentsConfiguration(new DocumentsConfiguration());
		}

		return documentComposition;
	}

	public void saveModel(DocumentComposition documentComposition){
		Activator.getDefault().setDocumentComposition(documentComposition);
	}

	public DocumentComposition getModel(){
		return Activator.getDefault().getDocumentComposition();
	}


	/** update the model with a new document!
	 * 
	 */
	public void addNewDocumentToModel(MetadataDocument _metadataDocument, Style style){
		DocumentComposition documentComposition=getModel();
		DocumentsConfiguration documentsConfiguration=documentComposition.getDocumentsConfiguration();
		if(documentsConfiguration.getDocuments()==null){
			documentsConfiguration.setDocuments(new Vector<Document>());
		}
		Document newDocument=new Document(_metadataDocument,style);
		
		// add parameters
		//addMetadataParametersToDocumentParameters(documentComposition, newDocument, _metadataDocument);
		
		//		newDocument.setSbiObjLabel(_metadataDocument.getLabel());
		//		newDocument.setLocalFileName(_metadataDocument.getLocalFileName());
		//		newDocument.setStyle(style);
		//		newDocument.setId(_metadataDocument.getIdMetadataDocument());
		
		documentsConfiguration.getDocuments().add(newDocument);
		saveModel(documentComposition);
	}

	/** delete a document from the model!
	 * 
	 */
	public void deleteDocumentFromModel(MetadataDocument _metadataDocument){
		DocumentComposition documentComposition=getModel();
		DocumentsConfiguration documentsConfiguration=documentComposition.getDocumentsConfiguration();
		Vector<Document> documents=documentsConfiguration.getDocuments();
		boolean found=false;
		for (Iterator iterator = documents.iterator(); iterator.hasNext() && found==false;) {
			Document document = (Document) iterator.next();
			//if(document.getSbiObjLabel().equals(_metadataDocument.getLabel())){
			if(document.getId().equals(_metadataDocument.getLabel())){
				documents.remove(document);
				found=true;
			}
		}
		saveModel(documentComposition);
	}

	/** delete a document from the model!
	 * 
	 */
	public void deleteDocumentFromModel(Document documentToDelete){
		DocumentComposition documentComposition=getModel();
		DocumentsConfiguration documentsConfiguration=documentComposition.getDocumentsConfiguration();
		Vector<Document> documents=documentsConfiguration.getDocuments();
		boolean found=false;
		for (Iterator iterator = documents.iterator(); iterator.hasNext() && found==false;) {
			Document document = (Document) iterator.next();
			//if(document.getSbiObjLabel().equals(_metadataDocument.getLabel())){
			if(document.getSbiObjLabel().equals(documentToDelete.getSbiObjLabel())){
				documents.remove(document);
				found=true;
			}
		}
		saveModel(documentComposition);
	}


	/** update the model with a new document!
	 * 
	 */
	public void updateModelModifyDocument(MetadataDocument _metadataDocument, Style style){
		DocumentComposition documentComposition=getModel();
		DocumentsConfiguration documentsConfiguration=documentComposition.getDocumentsConfiguration();
		Vector<Document> documents=documentsConfiguration.getDocuments();
		if(documents!=null){
			for (Iterator iterator = documents.iterator(); iterator.hasNext();) {
				Document document = (Document) iterator.next();
				// Modify the current document!
				if(document.getId().equals(_metadataDocument.getLabel())){
					document.setStyle(style);
				}

			}
			saveModel(documentComposition);
		}
	}



	/** insert into the model all parameters SpagoBi from contained documetn that are not already in template
	 *  called when inserting a new Document and when loading the designer
	 * @param documentComposition
	 * @param document
	 * @param metadataDocument
	 */
		
		public void addMetadataParametersToDocumentParameters(DocumentComposition documentComposition, Document document, MetadataDocument metadataDocument){
			// add to the model all parameters found in metadata, if not already present, because we have to put in template all inputs parameters
			Vector<MetadataParameter> metaParameters = metadataDocument.getMetadataParameters();
			for (Iterator iterator2 = metaParameters.iterator(); iterator2.hasNext();) {
				MetadataParameter metadataParameter = (MetadataParameter) iterator2.next();
				// if not alredy contained in document
				if(!document.containsParameter(metadataParameter.getUrlName())){
					Parameter par = new Parameter(documentComposition);
					par.setSbiParLabel(metadataParameter.getUrlName());
					par.setType("IN");
					par.setNavigationName(metadataParameter.getUrlName());
					//par.setNavigationName(metadataParameter.getUrlName());
					// add the analaytical driver of the contained document to in parameters.
					document.getParameters().getParameter().add(par);
				}
			}
		return;
		}
		

}
