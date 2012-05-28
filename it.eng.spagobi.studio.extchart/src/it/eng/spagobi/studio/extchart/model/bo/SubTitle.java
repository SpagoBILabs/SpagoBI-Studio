package it.eng.spagobi.studio.extchart.model.bo;

public class SubTitle {

	public static final String TEXT = "text";
	public static final String STYLE = "style";
	
	private String text;
	private StyleSubTitle style;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public StyleSubTitle getStyle() {
		if(style == null) style = new StyleSubTitle();
		return style;
	}
	public void setStyle(StyleSubTitle style) {
		this.style = style;
	}
	
	
}
