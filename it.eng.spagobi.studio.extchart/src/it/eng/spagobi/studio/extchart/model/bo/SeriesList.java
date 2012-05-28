package it.eng.spagobi.studio.extchart.model.bo;
import java.util.Vector;

public class SeriesList {
	
	public static final String SERIES = "series";
	
	private Vector<Series> series;

	public Vector<Series> getSeries() {
		if(series == null) series = new Vector<Series>();
		return series;
	}

	public void setSeries(Vector<Series> series) {
		this.series = series;
	}


}
