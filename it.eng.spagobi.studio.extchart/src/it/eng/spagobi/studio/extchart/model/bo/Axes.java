package it.eng.spagobi.studio.extchart.model.bo;

public class Axes {

	public static final String TITLE = "title";
	public static final String TYPE = "type";
	public static final String MINIMUM = "minimum";
	public static final String POSITION = "position";
	public static final String FIELDS = "fields";
	public static final String FIELDS_LIST = "fields_list";
	public static final String GRID = "grid";
	
	private String title;
	private String type; 		// combo
	private Integer minimum;
	private String position; 	//combo
	private String fields;		//combo
	private String fields_list; //combo
	private Boolean grid;
		
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getFields_list() {
		return fields_list;
	}
	public void setFields_list(String fields_list) {
		this.fields_list = fields_list;
	}
	public Boolean getGrid() {
		return grid;
	}
	public void setGrid(Boolean grid) {
		this.grid = grid;
	}
	
}
