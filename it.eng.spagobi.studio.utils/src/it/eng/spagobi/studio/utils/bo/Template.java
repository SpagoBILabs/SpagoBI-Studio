package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKTemplate;
import it.eng.spagobi.server.services.api.bo.ITemplate;

public class Template implements ITemplate {

	private javax.activation.DataHandler content;

	private String fileName;

    private String folderName;
	
	public Template() {
	}


	public Template(SDKTemplate sdkTemplate) {
		content = sdkTemplate.getContent();
		fileName = sdkTemplate.getFileName();
	}

	public javax.activation.DataHandler getContent() {
		return content;
	}

	public void setContent(javax.activation.DataHandler content) {
		this.content = content;
	}

	public java.lang.String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}




}
