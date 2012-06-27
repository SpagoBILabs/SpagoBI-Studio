/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
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
