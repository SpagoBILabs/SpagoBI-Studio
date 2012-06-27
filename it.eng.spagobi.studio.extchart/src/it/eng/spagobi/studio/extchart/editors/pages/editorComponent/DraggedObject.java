/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.pages.editorComponent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;

public class DraggedObject implements Serializable{

	Map<Integer, String> indexNameSelected;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(DraggedObject.class);

	public DraggedObject() {
		super();
		indexNameSelected = new HashMap<Integer, String>();
	}

	public Map<Integer, String> getIndexNameSelected() {
		return indexNameSelected;
	}

	public void setIndexNameSelected(Map<Integer, String> indexNameSelected) {
		this.indexNameSelected = indexNameSelected;
	}

	@Override
	public String toString() {
		logger.debug("IN");
		String buffer="";

		for (Iterator iterator = indexNameSelected.keySet().iterator(); iterator.hasNext();) {
			Integer index = (Integer) iterator.next();
			String name = indexNameSelected.get(index);
			buffer+=index+","+name;
			if(iterator.hasNext()) 
				buffer+=",";
		}
		logger.debug("Dragged information is "+buffer);
		logger.debug("OUT");
		return buffer;
	}

	static public DraggedObject fromString(String string) {
		logger.debug("IN");
		logger.debug("String to parse is "+string);
		DraggedObject toReturn = new DraggedObject();

		String[] splits = string.split(",");
		// one index and one name
		for (int i = 0; i < splits.length; i++) {
			String indexS = splits[i];
			Integer index = Integer.valueOf(indexS);
			i++;
			String name = splits[i];
			toReturn.getIndexNameSelected().put(index, name);
		}		
		logger.debug("OUT");
		return toReturn;
	}
	
	public String toFieldString(){
		logger.debug("IN");
		String buffer="";

		for (Iterator iterator = indexNameSelected.keySet().iterator(); iterator.hasNext();) {
			Integer index = (Integer) iterator.next();
			String name = indexNameSelected.get(index);
			buffer+=name;
			if(iterator.hasNext()) 
				buffer+=",";
		}
		logger.debug("Field list is "+buffer);
		logger.debug("OUT");
		return buffer;
	}

	public int getSize(){
		return indexNameSelected.keySet().size();
	}

	public String getFirstElement(){

		Set<Integer> keys =  indexNameSelected.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			return indexNameSelected.get(key);
		}
		
		return null;
		
	}
	


}
