package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;

public class Document {

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
