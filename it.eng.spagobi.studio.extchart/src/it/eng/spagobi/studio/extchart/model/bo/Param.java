package it.eng.spagobi.studio.extchart.model.bo;

public class Param {

	
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String VALUE = "value";	
	
	private String name;
	private String type;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
