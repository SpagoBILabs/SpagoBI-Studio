/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKFunctionality;

public class Functionality {

	private  String code;

	private Document[] containedDocuments;

	private Functionality[] containedFunctionalities;

	private  String description;

	private  Integer id;

	private  String name;

	private  Integer parentId;

	private  String path;

	private  Integer prog;

	public Functionality(SDKFunctionality sdk ) {
		code = sdk.getCode();
		description = sdk.getDescription();
		id = sdk.getId();
		name = sdk.getName();
		parentId = sdk.getParentId();
		path = sdk.getPath();
		prog = sdk.getProg();
		SDKDocument[] documents = sdk.getContainedDocuments();
		if(documents != null){
			containedDocuments = new Document[documents.length];
			for (int i = 0; i < documents.length; i++) {
				containedDocuments[i] = new Document(documents[i]);
			}
		}
		
		SDKFunctionality[] functionalities = sdk.getContainedFunctionalities();
		if(functionalities != null){
			containedFunctionalities = new Functionality[functionalities.length];
			for (int i = 0; i < functionalities.length; i++) {
				containedFunctionalities[i] = new Functionality(functionalities[i]);
			}
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Document[] getContainedDocuments() {
		return containedDocuments;
	}

	public void setContainedDocuments(Document[] containedDocuments) {
		this.containedDocuments = containedDocuments;
	}

	public Functionality[] getContainedFunctionalities() {
		return containedFunctionalities;
	}

	public void setContainedFunctionalities(Functionality[] containedFunctionalities) {
		this.containedFunctionalities = containedFunctionalities;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getProg() {
		return prog;
	}

	public void setProg(Integer prog) {
		this.prog = prog;
	}

	
	
}
