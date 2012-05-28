package it.eng.spagobi.studio.extchart.model.bo;

public class Title {

	public static final String TEXT = "text";
	public static final String STYLE = "style";
	
	private String text;
	private StyleTitle style;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public StyleTitle getStyle() {
		if(style == null) style = new StyleTitle();
		return style;
	}
	public void setStyle(StyleTitle style) {
		this.style = style;
	}
	
	
	
}
