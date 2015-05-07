/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.server.services.api.bo.IDocument;

public class Document implements IDocument{

	private  Integer dataSetId;

	private  Integer dataSourceId;

	private  String description;

	private  Integer engineId;

	private  Integer id;

	private  String label;

	private  String name;

	private  String state;

	private  String type;


	public Document(){
		
	}

	public Document(SDKDocument sdk) {
		dataSetId = sdk.getDataSetId();
		dataSourceId = sdk.getDataSourceId();
		description = sdk.getDescription();
		engineId = sdk.getEngineId();
		id = sdk.getId();
		label = sdk.getLabel();
		name = sdk.getName();
		state = sdk.getState();
		type = sdk.getType();

	}

	public Integer getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(Integer dataSetId) {
		this.dataSetId = dataSetId;
	}

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEngineId() {
		return engineId;
	}

	public void setEngineId(Integer engineId) {
		this.engineId = engineId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
