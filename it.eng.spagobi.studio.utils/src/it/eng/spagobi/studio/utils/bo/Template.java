package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKTemplate;

public class Template {

	private javax.activation.DataHandler content;

	private String fileName;

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

	public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}




}
