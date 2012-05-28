package it.eng.spagobi.studio.extchart.model.bo;

public class AxesStyle {

	public static final String COLOR = "color";
	public static final String FONT_WEIGHT = "fontWeight";
	public static final String FONT_SIZE = "fontSize";
	public static final String FONT_FAMILY = "fontFamily";

	
	private String color;
	private String fontWeight; // combo
	private String fontSize;
	private String fontFamily;  // combo
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFontWeight() {
		return fontWeight;
	}
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}	
	
}
