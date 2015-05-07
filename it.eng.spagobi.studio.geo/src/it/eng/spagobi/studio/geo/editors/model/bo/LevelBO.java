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
