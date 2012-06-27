/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition;

import java.util.Iterator;
import java.util.Vector;

public class DocumentsConfiguration {

	private String videoWidth;
	private String videoHeight;
	private Vector<Document> documents=new Vector<Document>();

	private static int VIDEO_WIDTH=1200;
	private static int VIDEO_HEIGHT=1064;

	public Vector<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Vector<Document> documents) {
		this.documents = documents;
	}

	public String getVideoWidth() {
		return videoWidth;
	}

	public void setVideoWidth(String videoWidth) {
		this.videoWidth = videoWidth;
	}

	public String getVideoHeight() {
		return videoHeight;
	}

	public void setVideoHeight(String videoHeight) {
		this.videoHeight = videoHeight;
	}

	public DocumentsConfiguration() {
		documents=new Vector<Document>();
		videoHeight=Integer.valueOf(VIDEO_HEIGHT).toString();
		videoWidth=Integer.valueOf(VIDEO_WIDTH).toString();
	}


	/** get a document by label
	 * 
	 * @param labeò
	 * @return
	 */
	public Document getDocumentByLabel(String labeò){
		Document toReturn = null;
		for (Iterator iterator = documents.iterator(); iterator.hasNext() && toReturn == null;) {
			Document doc = (Document) iterator.next();
			if(doc.getSbiObjLabel().equals(labeò)){
				toReturn = doc;
			}
		}
		return toReturn;
	}

}
