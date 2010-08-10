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
import it.eng.spagobi.studio.geo.editors.model.geo.Level;

import java.util.Vector;

public class LevelBO {

	public static void setNewLevel(GEODocument geoDocument,
			String hierarchyName, Level newLevel) {
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		if (dmProvider != null) {
			Hierarchies hierarchies = dmProvider.getHierarchies();
			Vector<Hierarchy> vectHier = null;
			if (hierarchies != null) {
				vectHier = hierarchies.getHierarchy();
				if (vectHier != null) {

					for (int i = 0; i < vectHier.size(); i++) {
						if (vectHier.elementAt(i).getName().equals(
								hierarchyName)) {
							Vector<Level> vectLevels = null;
							if (vectHier.elementAt(i).getLevels() == null) {
								vectLevels = new Vector<Level>();
								vectHier.elementAt(i).setLevels(vectLevels);
							}
							else{
								vectLevels=vectHier.elementAt(i).getLevels();
							}
							vectLevels.add(newLevel);
						}
					}
				}
			}
		}
	}

	public static void deleteLevel(GEODocument geoDocument,
			String hierarchyName, String toDeleteLevel) {
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		if (dmProvider != null) {
			Hierarchies hierarchies = dmProvider.getHierarchies();
			Vector<Hierarchy> vectHier = null;
			if (hierarchies != null) {
				vectHier = hierarchies.getHierarchy();
				if (vectHier != null) {
					for (int i = 0; i < vectHier.size(); i++) {
						if (vectHier.elementAt(i).getName().equals(
								hierarchyName)) {
							Vector<Level> vectLevels = null;
							if (vectHier.elementAt(i).getLevels() != null && vectHier.elementAt(i).getLevels() != null) {
								Vector<Level> vectLevelsToRemove = new Vector<Level>();
								for(int j=0; j<vectHier.elementAt(i).getLevels().size(); j++){
									Level l = vectHier.elementAt(i).getLevels().elementAt(j);
									if(l.getName().equals(toDeleteLevel)){
										//vectHier.elementAt(i).getLevels().getLevel().remove(l);
										vectLevelsToRemove.add(l);
									}

								}
								vectHier.elementAt(i).getLevels().removeAll(vectLevelsToRemove);
							}
						}
					}
				}
			}
		}
	}
	public static Level getLevelByName(GEODocument geoDocument,
			String hierarchyName, String level) {
		Level levelret = null;
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		if (dmProvider != null) {
			Hierarchies hierarchies = dmProvider.getHierarchies();
			Vector<Hierarchy> vectHier = null;
			if (hierarchies != null) {
				vectHier = hierarchies.getHierarchy();
				if (vectHier != null) {
					for (int i = 0; i < vectHier.size(); i++) {
						Hierarchy hier = vectHier.elementAt(i);
						if (hier.getName().equals(	hierarchyName)) {
							if (hier.getLevels() != null && hier.getLevels() != null) {
								for(int j=0; j<vectHier.elementAt(i).getLevels().size(); j++){
									Level l = hier.getLevels().elementAt(j);
									if(l.getName().equals(level)){
										levelret = l;
									}

								}
							}
						}
					}
				}
			}
		}
		return levelret;
	}
	public static Vector<Level> getLevelsByHierarchyName(GEODocument geoDocument,
			String hierarchyName){
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		if (dmProvider != null) {
			Hierarchies hierarchies = dmProvider.getHierarchies();
			Vector<Hierarchy> vectHier = null;
			if (hierarchies != null) {
				vectHier = hierarchies.getHierarchy();
				if (vectHier != null) {
					for (int i = 0; i < vectHier.size(); i++) {
						Hierarchy hier = vectHier.elementAt(i);
						if (hier.getName().equals(	hierarchyName)) {
							return hier.getLevels();
						}
					}
				}
			}
		}

		return null;
	}
	public static void updateLevel(GEODocument geoDocument,
			String hierarchyName, Level oldLevel, Level newLevel){
		Level levelToUpdate = getLevelByName(geoDocument, hierarchyName, oldLevel.getName());
		if(levelToUpdate != null){
			levelToUpdate.setName(newLevel.getName());
			levelToUpdate.setColumnDesc(newLevel.getColumnDesc());
			levelToUpdate.setColumnId(newLevel.getColumnId());
			levelToUpdate.setFeatureName(newLevel.getFeatureName());
		}

	}
}
