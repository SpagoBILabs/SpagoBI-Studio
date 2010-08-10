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
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

import it.eng.spagobi.studio.documentcomposition.editors.Designer;
import it.eng.spagobi.studio.documentcomposition.editors.DocContainer;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;
import it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView;

import java.util.Iterator;

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

	
}
