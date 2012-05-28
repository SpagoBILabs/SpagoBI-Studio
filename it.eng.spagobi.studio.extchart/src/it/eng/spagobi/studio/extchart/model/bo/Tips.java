package it.eng.spagobi.studio.extchart.model.bo;

public class Tips {

	public static final String TRACK_MOUSE = "trackMouse";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String FORMATTER = "formatter";
	public static final String TEXT = "text";

	private Boolean trackMouse; //combo
	private Integer width;
	private Integer height;
	private String formatter; //combo
	private String text;
	
	public Boolean getTrackMouse() {
		return trackMouse;
	}
	public void setTrackMouse(Boolean trackMouse) {
		this.trackMouse = trackMouse;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

	
}
