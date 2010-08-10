package it.eng.spagobi.studio.dashboard.editors.model.dashboard;

public class Parameter {
	
	public static final int STRING_TYPE = 0;
	public static final int NUMBER_TYPE = 1;
	public static final int COLOR_TYPE = 2;
	public static final int BOOLEAN_TYPE = 3;
	
	private String name;
	private String value;
	private String description;
	private int type;
	public Parameter () {}
	public Parameter (String name, String value, String description, int type) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
