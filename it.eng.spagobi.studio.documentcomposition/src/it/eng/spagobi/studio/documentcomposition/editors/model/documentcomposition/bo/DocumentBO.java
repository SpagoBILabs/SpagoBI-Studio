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

import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;

import java.util.Vector;

public class DocumentBO {
	
	public static Parameters getParametersByDocumentLabel(DocumentComposition docComp, String docLabel){
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		Parameters params = null;
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String sbiLabel =doc.getSbiObjLabel();
	    			if(sbiLabel.equals(docLabel)){
	    				params= doc.getParameters();
	    			}		    			
		    	}
		    }
		}
		return params;
	}

}
