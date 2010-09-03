/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

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
