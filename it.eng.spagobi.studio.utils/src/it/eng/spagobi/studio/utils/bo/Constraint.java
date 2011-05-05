package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKConstraint;

public class Constraint {
	private  String description;

	private  String firstValue;

	private  Integer id;

	private  String label;

	private  String name;

	private  String secondValue;

	private  String type;



	public Constraint(SDKConstraint sdk) {

		description = sdk.getDescription();
		firstValue = sdk.getFirstValue();
		id = sdk.getId();
		label = sdk.getLabel();
		name = sdk.getName();
		secondValue = sdk.getSecondValue();
		type = sdk.getType();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
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

	public String getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
