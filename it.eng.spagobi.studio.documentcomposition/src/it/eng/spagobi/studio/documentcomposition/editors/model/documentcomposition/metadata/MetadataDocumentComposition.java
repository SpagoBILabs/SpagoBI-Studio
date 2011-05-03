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
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata;

import it.eng.spagobi.studio.documentcomposition.actions.NewWorkbenchDocumentCompositionAction;
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


	public boolean removeMetadataDocument(MetadataDocument _metadataDocument){
		boolean found=false;
		for (Iterator iterator = metadataDocuments.iterator(); iterator.hasNext() && found==false;) {
			MetadataDocument metaDocument = (MetadataDocument) iterator.next();
			if(metaDocument.getIdMetadataDocument().equals(_metadataDocument.getIdMetadataDocument())){
				metadataDocuments.remove(metaDocument);
				found=true;
			}
		}
		return found;
	}

	public void addMetadataDocument(MetadataDocument _metadataDocument){
		metadataDocuments.add(_metadataDocument);
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
