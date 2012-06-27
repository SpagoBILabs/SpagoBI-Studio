/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata;

import it.eng.spagobi.studio.documentcomposition.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class MetadataBO {

	public void createMetadataDocumentComposition(IFile file) throws CoreException{
		MetadataDocumentComposition metadataDocumentComposition=new MetadataDocumentComposition(file);
		Activator.getDefault().setMetadataDocumentComposition(metadataDocumentComposition);

	}

	public void saveModel(MetadataDocumentComposition metadataDocumentComposition){
		Activator.getDefault().setMetadataDocumentComposition(metadataDocumentComposition);
	}

	public MetadataDocumentComposition getMetadataDocumentComposition(){
		return Activator.getDefault().getMetadataDocumentComposition();
	}

	
	
	
}
