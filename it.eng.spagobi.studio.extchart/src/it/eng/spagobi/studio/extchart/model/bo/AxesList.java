package it.eng.spagobi.studio.extchart.model.bo;

import java.util.Vector;

public class AxesList {

	public static final String AXES = "axes";
	
	private Vector<Axes> axes;

	public Vector<Axes> getAxes() {
		if(axes == null) axes = new Vector<Axes>();
		return axes;
	}

	public void setAxes(Vector<Axes> axes) {
		this.axes = axes;
	}
	
	
	
}
