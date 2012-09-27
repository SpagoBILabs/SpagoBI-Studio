/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata;

import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataDocumentComposition {

	boolean madeWithStudio=false;
	String madeWithStudioDate;
	private static Logger logger = LoggerFactory.getLogger(MetadataDocumentComposition.class);

	public MetadataDocumentComposition(IFile newFile) {
		madeWithStudio=calculateIfMadeWithStudio(newFile);
		/*		if(Activator.getDefault().getMetadataDocumentComposition() != null){
			metadataDocuments = Activator.getDefault().getMetadataDocumentComposition().getMetadataDocuments();
		}*/
	}

	private Vector<MetadataDocument> metadataDocuments=new Vector<MetadataDocument>();

	public Vector<MetadataDocument> getMetadataDocuments() {
		return metadataDocuments;
	}

	public void setMetadataDocuments(Vector<MetadataDocument> metadataDocuments) {
		this.metadataDocuments = metadataDocuments;
	}

	public boolean substituteMetadataDocument(MetadataDocument metadataDocument){
		logger.debug("IN");
		boolean found = false;
		
		for (int i = 0; i < metadataDocuments.size(); i++) {
			MetadataDocument iterMetaDocument = metadataDocuments.get(i);

			if(iterMetaDocument.getLabel().equals(metadataDocument.getLabel())){
				metadataDocuments.set(i, null);
				metadataDocuments.set(i, metadataDocument);
				found = true;
			}
		}
		logger.debug("OUT");
		return found;

	}
	

		
	
	public MetadataDocument findMetadataDocument(String label){
		logger.debug("IN");
		MetadataDocument toReturn = null;

		for (int i = 0; i < metadataDocuments.size(); i++) {
			MetadataDocument iterMetaDocument = metadataDocuments.get(i);

			if(iterMetaDocument.getLabel().equals(label)){
				toReturn = iterMetaDocument;
			}
		}
		logger.debug("OUT");
		return toReturn;

	}
	
	
	public boolean removeMetadataDocumentByLabel(MetadataDocument _metadataDocument){
		boolean found=false;
		for (Iterator iterator = metadataDocuments.iterator(); iterator.hasNext() && found==false;) {
			MetadataDocument metaDocument = (MetadataDocument) iterator.next();
			if(metaDocument.getLabel().equals(_metadataDocument.getLabel())){
				metadataDocuments.remove(metaDocument);
				found=true;
			}
		}
		return found;
	}
	
	
	

	public boolean removeMetadataDocument(MetadataDocument _metadataDocument){
		boolean found=false;
		for (Iterator iterator = metadataDocuments.iterator(); iterator.hasNext() && found==false;) {
			MetadataDocument metaDocument = (MetadataDocument) iterator.next();
			if(metaDocument.getLabel().equals(_metadataDocument.getLabel())){
				metadataDocuments.remove(metaDocument);
				found=true;
			}
		}
		return found;
	}

	public void addMetadataDocument(MetadataDocument _metadataDocument){
		logger.debug("IN");
		if(findMetadataDocument(_metadataDocument.getLabel())!= null){
			this.substituteMetadataDocument(_metadataDocument);
		}
		else{
			metadataDocuments.add(_metadataDocument);
		}
		logger.debug("OUT");
	}

	public boolean isMadeWithStudio(){
		return madeWithStudio;
	}

	public boolean calculateIfMadeWithStudio(IFile newFile) {
		String date=null;
		try {
			date=newFile.getPersistentProperty(SpagoBIStudioConstants.MADE_WITH_STUDIO);
		} catch (CoreException e) {
			logger.error("Could not retrieve metadata", e);			
			e.printStackTrace();
		}
		if(date==null)madeWithStudio=false;
		else{
			madeWithStudioDate=date;
			madeWithStudio=true;
		}
		return madeWithStudio;
	}

	public void setMadeWithStudio(boolean madeWithStudio) {
		this.madeWithStudio = madeWithStudio;
	}


	/** taking in input a label return the relative metadataDOcument
	 * 
	 * @param _metadataDocument
	 * @return
	 */

	public MetadataDocument retrieveMetadataDocumentByLabel(String label){
		MetadataDocument toReturn = null;
		for (Iterator iterator = metadataDocuments.iterator(); iterator.hasNext() && toReturn == null;) {
			MetadataDocument metaDocument = (MetadataDocument) iterator.next();
			if(metaDocument.getLabel().equals(label)){
				toReturn = metaDocument;
			}
		}
		return toReturn;
	}


}
