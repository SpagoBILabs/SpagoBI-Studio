/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
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
