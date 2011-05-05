package it.eng.spagobi.studio.utils.services;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Template;

public class ServerObjectsComparator {



//	static public boolean isObjectSDKDocument(Object ob){
//		if(ob instanceof SDKDocument)
//			return true;
//		else return false;	
//
//	}
//
//	static public boolean isObjectSDKFunctionality(Object ob){
//		if(ob instanceof SDKFunctionality)
//			return true;
//		else return false;	
//	}
//
//	static public Functionality getFunctionality(Object ob){
//		Functionality toRet = null;
//		if(isObjectSDKFunctionality(ob)){
//			toRet = new Functionality((SDKFunctionality)ob);
//		}
//		return toRet;
//	}
//
//	static public Document getDocument(Object ob){
//		Document toRet = null;
//		if(isObjectSDKDocument(ob)){
//			toRet = new Document((SDKDocument)ob);
//		}
//		return toRet;
//	}

	static public SDKDocument createSDKDocument(Document doc){
		SDKDocument sdkDocument = new SDKDocument();
		sdkDocument.setDataSetId(doc.getDataSetId());
		sdkDocument.setDataSourceId(doc.getDataSourceId());
		sdkDocument.setDescription(doc.getDescription());
		sdkDocument.setEngineId(doc.getEngineId());
		sdkDocument.setId(doc.getId());
		sdkDocument.setLabel(doc.getLabel());
		sdkDocument.setName(doc.getName());
		sdkDocument.setState(doc.getState());
		sdkDocument.setType(doc.getType());
		return sdkDocument;
	}
	
	static public SDKTemplate createSDKTemplate(Template temp){
		SDKTemplate sdkTemplate = new SDKTemplate();
		sdkTemplate.setContent(temp.getContent());
		sdkTemplate.setFileName(temp.getFileName());

		return sdkTemplate;
	}


}

