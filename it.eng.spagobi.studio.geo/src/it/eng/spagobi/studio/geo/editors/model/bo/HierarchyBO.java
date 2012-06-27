/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchies;
import it.eng.spagobi.studio.geo.editors.model.geo.Hierarchy;

import java.util.Vector;

public class HierarchyBO {
	
	public static void setNewHierarchy(GEODocument geoDocument, String name, String type){
		DatamartProvider dmProvider =geoDocument.getDatamartProvider();
		if(dmProvider != null){
			Hierarchies hierarchies = dmProvider.getHierarchies();
			Vector<Hierarchy> vectHier = null;
			if(hierarchies == null){
				hierarchies = new Hierarchies();
				vectHier = new Vector<Hierarchy>();
				hierarchies.setHierarchy(vectHier);
				dmProvider.setHierarchies(hierarchies);
			}else{
				vectHier = hierarchies.getHierarchy();
				if(vectHier == null){
					vectHier = new Vector<Hierarchy>();
					hierarchies.setHierarchy(vectHier);
				}				
			}
			//add new hierarchy
			Hierarchy newHierarchy = new Hierarchy();
			newHierarchy.setName(name);
			newHierarchy.setType(type);
			vectHier.add(newHierarchy);
		}
	}

	public static Hierarchy getHierarchyByName(GEODocument geoDocument, String name){
		Hierarchy hierarchy = null;
		DatamartProvider dmProvider =geoDocument.getDatamartProvider();
		if(dmProvider != null){
			Hierarchies hierarchies = dmProvider.getHierarchies();
			if(hierarchies != null && hierarchies.getHierarchy() != null){
				for(int i =0; i< hierarchies.getHierarchy().size(); i++){
					Hierarchy h = hierarchies.getHierarchy().elementAt(i);
					if(h.getName().equals(name)){
						hierarchy = h;
					}
				}
				
			}
		}
		return hierarchy;
	}

	public static Hierarchies getAllHierarchies(GEODocument geoDocument){
		DatamartProvider dmProvider =geoDocument.getDatamartProvider();
		if(dmProvider != null){
			Hierarchies hierarchies = dmProvider.getHierarchies();
			return hierarchies;
		}
		return null;
	}
	public static void deleteHierarchy(GEODocument geoDocument, String name){
		DatamartProvider dmProvider =geoDocument.getDatamartProvider();
		if(dmProvider != null){
			Hierarchies hierarchies = dmProvider.getHierarchies();
			if(hierarchies != null && hierarchies.getHierarchy() != null){
				Vector <Hierarchy> hierToRemove = new Vector<Hierarchy>();
				for(int i =0; i< hierarchies.getHierarchy().size(); i++){
					Hierarchy h = hierarchies.getHierarchy().elementAt(i);
					if(h.getName().equals(name)){
						//hierarchies.getHierarchy().remove(h);
						hierToRemove.add(h);
					}
				}
				hierarchies.getHierarchy().removeAll(hierToRemove);
			}
		}

	}

}
