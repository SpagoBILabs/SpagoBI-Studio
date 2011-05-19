package it.eng.spagobi.studio.utils.services;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Template;

public class ServerObjectsTranslator {



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
		sdkTemplate.setFolderName(temp.getFolderName());
		return sdkTemplate;
	}

	static public SDKDataSet createSDKDataSet(Dataset ds){
		SDKDataSet sdkDataset = new SDKDataSet();
		sdkDataset.setLabel(ds.getLabel());
		sdkDataset.setName(ds.getName());
		sdkDataset.setDescription(ds.getDescription());
		sdkDataset.setType(ds.getType());
		sdkDataset.setJdbcDataSourceId(ds.getJdbcDataSourceId());
		if(ds.getTransformer() != null)
			sdkDataset.setTransformer(ds.getTransformer().toString());
		sdkDataset.setNumberingRows(ds.getNumberingRows());
		sdkDataset.setPivotColumnName(ds.getPivotColumnName());
		sdkDataset.setPivotColumnValue(ds.getPivotColumnValue());
		sdkDataset.setPivotRowName(ds.getPivotRowName());
		sdkDataset.setJsonQuery(ds.getJsonQuery());
		sdkDataset.setDatamarts(ds.getDatamarts());
		
		return sdkDataset;
	}



}

