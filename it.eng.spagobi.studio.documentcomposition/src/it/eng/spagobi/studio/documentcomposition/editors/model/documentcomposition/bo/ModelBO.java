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
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.util.XmlTemplateGenerator;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ModelBO {

	public DocumentComposition createModel(IFile file) throws CoreException{
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
			if(document.getId().equals(_metadataDocument.getIdMetadataDocument())){
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
				if(document.getId().equals(_metadataDocument.getIdMetadataDocument())){
					document.setStyle(style);
				}

			}
			saveModel(documentComposition);
		}
	}





}
