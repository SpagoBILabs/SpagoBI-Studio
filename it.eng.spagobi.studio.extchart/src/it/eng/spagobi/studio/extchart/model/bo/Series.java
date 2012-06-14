package it.eng.spagobi.studio.extchart.model.bo;

public class Series {
	
	public static final String TYPE = "type";
	public static final String AXIS = "axis";
	public static final String SHOW_IN_LEGEND = "showInLegend";
	//public static final String FIELD = "xField";
	public static final String FIELD = "field";
	public static final String X_FIELD = "xField";
	public static final String Y_FIELD = "yField";
	public static final String Y_FIELD_LIST = "yField_list";
	public static final String DONUT = "donut";
	public static final String STYLE = "style";
	public static final String HIGHLIGHT = "highlight";
	public static final String HIGHLIGHT_SEGMENT= "highlight";
	public static final String STACKED = "stacked";
	public static final String SMOOTH = "smooth";
	public static final String FILL = "fill";
	public static final String GUTTER = "gutter";
	public static final String COLOR = "color";
	public static final String SHOW_MARKERS = "showMarkers";
	public static final String LABEL = "label";
	public static final String MARKER_CONFIG = "markerConfig";
	
	private String type; // combo
	private String axis; // combo
	private Boolean showInLegend;
	private String field; // combo on metadata editable
	private String xField;
	private String yField;
	private String yFieldList;
	private StyleSeries style;
	private Tips tips;
	private Highlight highlightSegments;
	private Integer donut;
	private Boolean highlight;
	private Boolean stacked;
	private Boolean smooth;
	private Boolean fill;
	private Integer gutter;
	private String color;
	private Boolean showMarkers;
	private MarkerConfig markerConfig;
	
	private Label label;
	
	
	public Tips getTips() {
		if( tips == null) return new Tips();
		return tips;
	}
	public void setTips(Tips tips) {
		this.tips = tips;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAxis() {
		return axis;
	}
	public void setAxis(String axis) {
		this.axis = axis;
	}
	public Boolean getShowInLegened() {
		return showInLegend;
	}
	public void setShowInLegened(Boolean showInLegened) {
		this.showInLegend = showInLegened;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Integer getDonut() {
		return donut;
	}
	public void setDonut(Integer donut) {
		this.donut = donut;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public String getyField() {
		return yField;
	}
	public void setyField(String yField) {
		this.yField = yField;
	}
	public String getyFieldList() {
		return yFieldList;
	}
	public void setyFieldList(String yFieldList) {
		this.yFieldList = yFieldList;
	}
	public StyleSeries getStyle() {
		if(style == null) style = new StyleSeries();
		return style;
	}
	public void setStyle(StyleSeries style) {
		this.style = style;
	}
	public Boolean getHighlight() {
		return highlight;
	}
	public void setHighlight(Boolean highlight) {
		this.highlight = highlight;
	}
	public Boolean getStacked() {
		return stacked;
	}
	public void setStacked(Boolean stacked) {
		this.stacked = stacked;
	}
	public Boolean getSmooth() {
		return smooth;
	}
	public void setSmooth(Boolean smooth) {
		this.smooth = smooth;
	}
	public Integer getGutter() {
		return gutter;
	}
	public void setGutter(Integer gutter) {
		this.gutter = gutter;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Boolean getShowMarkers() {
		return showMarkers;
	}
	public void setShowMarkers(Boolean showMarkers) {
		this.showMarkers = showMarkers;
	}
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	public MarkerConfig getMarkerConfig() {
		if(markerConfig == null) return new MarkerConfig();
		return markerConfig;
	}
	public void setMarkerConfig(MarkerConfig markerConfig) {
		this.markerConfig = markerConfig;
	}

	public Boolean getFill() {
		return fill;
	}

	public void setFill(Boolean fill) {
		this.fill = fill;
	}
	/**
	 * @return the highlightSegment
	 */
	public Highlight getHighlightSegment() {
		if( highlightSegments == null) return new Highlight();
		return highlightSegments;	}
	/**
	 * @param highlightSegment the highlightSegment to set
	 */
	public void setHighlightSegment(Highlight highlightSegment) {
		this.highlightSegments = highlightSegment;
	}
	
	
	
	
}
