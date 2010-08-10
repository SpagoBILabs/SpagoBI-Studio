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
