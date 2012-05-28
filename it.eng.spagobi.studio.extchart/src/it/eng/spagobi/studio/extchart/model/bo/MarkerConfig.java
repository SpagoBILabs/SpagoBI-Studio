package it.eng.spagobi.studio.extchart.model.bo;

public class MarkerConfig {

	public static final String TYPE = "type";
	public static final String SIZE = "size";
	public static final String RADIUS = "radius";
	
	private String type; //combo
	private Integer size;
	private Integer radius;
	
	public String getType() {
		return type;
	}
	public void setTyp(String type) {
		this.type = type;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getRadius() {
		return radius;
	}
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	
	
}
