/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
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
