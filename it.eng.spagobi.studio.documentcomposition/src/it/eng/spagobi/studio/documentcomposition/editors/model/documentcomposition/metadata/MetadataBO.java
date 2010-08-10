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
