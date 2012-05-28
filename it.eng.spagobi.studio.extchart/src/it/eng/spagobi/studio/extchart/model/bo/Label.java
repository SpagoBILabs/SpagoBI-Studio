package it.eng.spagobi.studio.extchart.model.bo;

public class Label {

	public static final String FIELD = "field";
	public static final String DISPLAY = "display";
	public static final String CONTRAST = "contrast";
	public static final String FONT = "font";
	public static final String ORIENTATION = "orientation";
	public static final String COLOR = "color";
	public static final String TEXT_ANCHOR = "textAnchor";

	
	private String field;
	private String display; // combo
	private Boolean contrast;
	private String font;
	private String orientation;
	private String color;
	private String textAnchor;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public Boolean getContrast() {
		return contrast;
	}
	public void setContrast(Boolean contrast) {
		this.contrast = contrast;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTextAnchor() {
		return textAnchor;
	}
	public void setTextAnchor(String textAnchor) {
		this.textAnchor = textAnchor;
	}
	
	
}
