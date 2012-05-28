package it.eng.spagobi.studio.extchart.model.bo;

public class Highlight {

	public static final String SEGMENT = "segment";

	
	private Segment segment;

	public Segment getSegment() {
		if(segment == null) segment = new Segment();
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
	
	
}
