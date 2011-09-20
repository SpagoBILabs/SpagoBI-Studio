package it.eng.spagobi.studio.utils.services;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;
import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataSetParameter;
import it.eng.spagobi.server.services.api.bo.IDocument;
import it.eng.spagobi.server.services.api.bo.ITemplate;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.DatasetParameter;
import it.eng.spagobi.studio.utils.bo.Document;
import it.eng.spagobi.studio.utils.bo.Functionality;
import it.eng.spagobi.studio.utils.bo.Template;

public class ServerObjectsTranslator {



	static public SDKDocument createSDKDocument(IDocument doc){
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

	static public SDKTemplate createSDKTemplate(ITemplate temp){
		SDKTemplate sdkTemplate = new SDKTemplate();
		sdkTemplate.setContent(temp.getContent());
		sdkTemplate.setFileName(temp.getFileName());
		sdkTemplate.setFolderName(temp.getFolderName());
		return sdkTemplate;
	}

	static public SDKDataSet createSDKDataSet(IDataSet ds){
		SDKDataSet sdkDataset = new SDKDataSet();
		sdkDataset.setId(ds.getId());
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
		
		SDKDataSetParameter[] sdkDataSetParameters = null;
		IDataSetParameter[] parArray = ds.getParameters();
		if(parArray != null){
			sdkDataSetParameters = new SDKDataSetParameter[parArray.length];
			for (int i = 0; i < parArray.length; i++) {
				IDataSetParameter dsPar = parArray[i];
				SDKDataSetParameter sdkPar = createSDKDataSetParameter(dsPar);
				sdkDataSetParameters[i] = sdkPar;
			}
		}
		sdkDataset.setParameters(sdkDataSetParameters);
		
		return sdkDataset;
	}

	static public SDKDataSetParameter createSDKDataSetParameter(IDataSetParameter dsPar){
		SDKDataSetParameter sdkDataSetParameter = new SDKDataSetParameter();
		sdkDataSetParameter.setName(dsPar.getName());
		sdkDataSetParameter.setType(dsPar.getType());
		sdkDataSetParameter.setValues(dsPar.getValues());
		return sdkDataSetParameter;
	}

}

