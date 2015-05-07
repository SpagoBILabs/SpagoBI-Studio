/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

import it.eng.spagobi.studio.documentcomposition.editors.Designer;
import it.eng.spagobi.studio.documentcomposition.editors.DocContainer;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;
import it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;


public class DocumentComposition {

	private DocumentsConfiguration documentsConfiguration;
	private String templateValue;

	public DocumentsConfiguration getDocumentsConfiguration() {
		return documentsConfiguration;
	}
	public void setDocumentsConfiguration(
			DocumentsConfiguration documentsConfiguration) {
		this.documentsConfiguration = documentsConfiguration;
	}
	public String getTemplateValue() {
		return templateValue;
	}
	public void setTemplateValue(String templateValue) {
		this.templateValue = templateValue;
	}
	public DocumentComposition() {
		documentsConfiguration=new DocumentsConfiguration();
		templateValue="/jsp/engines/documentcomposition/template/dynamicTemplate.jsp";
	}



	/** Calld before saving recalculate styles for each document (filled) contestualized to the actual video size (except for those set to manual!
	 * 	
	 */
	public void reloadAllStylesContained(){
		// take desginer and run all the containers
		DocumentPropertiesView docPropertiesView=null;
		IViewPart viewPart=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
		if(viewPart!=null)docPropertiesView=(DocumentPropertiesView)viewPart;

		IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);	
		if(editorPart!=null){
			DocumentCompositionEditor editor=(DocumentCompositionEditor)editorPart;
			Designer designer=editor.getDesigner();
			for (Iterator iterator = designer.getContainers().keySet().iterator(); iterator.hasNext();) {
				Integer id = (Integer) iterator.next();
				DocContainer docContainer=designer.getContainers().get(id);
				if(docContainer.getDocumentContained()!=null && docContainer.getDocumentContained().getMetadataDocument()!=null) {
					// If in manual state shell take manual configuration!
					String manualString=null;
					if(docPropertiesView!=null){
						manualString=docPropertiesView.getStyleParameters().get(docContainer.getIdContainer());
					}
					// manual mode
					Style style=null;
					if(manualString!=null){
						style=new Style();	
						style.setStyle(manualString);
						style.setMode("manual");
					}
					else{	
						style=docContainer.calculateTemplateStyle(true);
						style.setMode("auto");						
					}
					MetadataDocument metadataDocument=docContainer.getDocumentContained().getMetadataDocument();
					new ModelBO().updateModelModifyDocument(metadataDocument, style);

				}

			}
		}
	}

	/** return the parameters vector associated to a document
	 * 
	 * @param label
	 * @return
	 * @throws Exception
	 */
	
	
	public Vector<Parameter> retrieveParametersVectorFromDocumentLabel(String label) throws Exception{
		// get the document
		Vector<Document> documents = getDocumentsConfiguration().getDocuments();
		Document actualDoc = null;
		for (Iterator iterator = documents.iterator(); iterator.hasNext();) {
			Document doc = (Document) iterator.next();
			if (doc.getSbiObjLabel().equals(label)){
				actualDoc = doc;						
			}
		}
		Parameters parameters = actualDoc.getParameters();
		Vector<Parameter> vectPars = parameters.getParameter();
		return vectPars;
	}
	
	/** return the document associated to the doucment label
	 * 
	 * @param label
	 * @return
	 * @throws Exception
	 */
	
	public Document retrieveDocumentFromDocumentLabel(String label) throws Exception{
		// get the document
		Vector<Document> documents = getDocumentsConfiguration().getDocuments();
		Document actualDoc = null;
		for (Iterator iterator = documents.iterator(); iterator.hasNext();) {
			Document doc = (Document) iterator.next();
			if (doc.getSbiObjLabel().equals(label)){
				actualDoc = doc;						
			}
		}
		return actualDoc;
	}
	
	
	
}
