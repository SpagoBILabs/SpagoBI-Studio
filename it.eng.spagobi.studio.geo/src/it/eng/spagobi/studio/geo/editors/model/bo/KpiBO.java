/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.editors.model.bo;

import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.KPI;
import it.eng.spagobi.studio.geo.editors.model.geo.MapRenderer;
import it.eng.spagobi.studio.geo.editors.model.geo.Measures;

import java.util.Vector;

public class KpiBO {
	
	public static void setNewMeasure(GEODocument geoDocument, KPI kpiToAdd){
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		Measures measures = mapRenderer.getMeasures();
		if(measures == null){
			measures = new Measures();
			mapRenderer.setMeasures(measures);
		}
		Vector<KPI> kpis= measures.getKpi();
		if(kpis == null){
			kpis = new Vector<KPI>();
			measures.setKpi(kpis);
		}
		boolean isKpiModified = false;
		for(int i=0; i<kpis.size(); i++){
			KPI kpi = kpis.elementAt(i);
			String columnId = kpi.getColumnId();
			if(kpiToAdd.getColumnId().equals(columnId)){
				//va in modifica
				kpi = fillKPI(kpi, kpiToAdd);
				isKpiModified = true;
			}
		}
		if(!isKpiModified){
			//aggiunge kpi
			kpis.add(kpiToAdd);
		}
	}
	private static KPI fillKPI(KPI kpi, KPI kpiToUse){
		kpi.setAggFunct(kpiToUse.getAggFunct());
		kpi.setColor(kpiToUse.getColor());
		kpi.setColours(kpiToUse.getColours());
		kpi.setColumnId(kpiToUse.getColumnId());
		kpi.setDescription(kpiToUse.getDescription());
		kpi.setTresholds(kpiToUse.getTresholds());
		return kpi;
	}
	public static void deleteMeasure(GEODocument geoDocument, KPI kpiToDelete){
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		Measures measures = mapRenderer.getMeasures();
		if(measures != null){
			Vector<KPI> kpis= measures.getKpi();
			if(kpis != null){
				int kpisSize =kpis.size();
				Vector<KPI> kpisToRemove = new Vector<KPI>();
				for(int i=0; i< kpisSize; i++){
					KPI kpi = kpis.elementAt(i);
					String columnId = kpi.getColumnId();
					if(kpiToDelete.getColumnId().equals(columnId)){
						//delete
						//kpis.remove(i);
						kpisToRemove.add(kpi);
					}
				}
				kpis.removeAll(kpisToRemove);
			}
		}
	}
	
	public static KPI getMeasureByColumnId(GEODocument geoDocument, String columnIdToSearch){
		KPI kpiToReturn = null;
		MapRenderer mapRenderer = geoDocument.getMapRenderer();
		Measures measures = mapRenderer.getMeasures();
		if(measures != null){
			Vector<KPI> kpis= measures.getKpi();
			if(kpis != null){
				int kpisSize =kpis.size();
				for(int i=0; i< kpisSize; i++){
					KPI kpi = kpis.elementAt(i);
					String columnId = kpi.getColumnId();
					if(columnIdToSearch.equals(columnId)){
						kpiToReturn = kpi;
					}
				}

			}
		}
		return kpiToReturn;
	}
}
