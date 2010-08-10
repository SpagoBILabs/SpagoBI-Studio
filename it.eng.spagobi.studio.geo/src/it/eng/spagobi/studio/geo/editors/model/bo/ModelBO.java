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

import it.eng.spagobi.studio.geo.Activator;
import it.eng.spagobi.studio.geo.editors.model.geo.Column;
import it.eng.spagobi.studio.geo.editors.model.geo.CrossNavigation;
import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Label;
import it.eng.spagobi.studio.geo.editors.model.geo.Labels;
import it.eng.spagobi.studio.geo.editors.model.geo.Layer;
import it.eng.spagobi.studio.geo.editors.model.geo.Layers;
import it.eng.spagobi.studio.geo.editors.model.geo.Link;
import it.eng.spagobi.studio.geo.editors.model.geo.MapProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.MapRenderer;
import it.eng.spagobi.studio.geo.editors.model.geo.Metadata;
import it.eng.spagobi.studio.geo.util.XmlTemplateGenerator;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ModelBO {
	private ResourceBundle rb = ResourceBundle.getBundle("config", Locale.ENGLISH);
	
	public GEODocument createModel(IFile file) throws CoreException{
		GEODocument geoDocument = XmlTemplateGenerator.readXml(file);
		if(geoDocument.getMapProvider()==null){
			MapProvider mapProvider =new MapProvider();
			geoDocument.setMapProvider(mapProvider);
			mapProvider.setClassName(rb.getString("mapprovider.class.name"));
			
		}
		if(geoDocument.getDatamartProvider()==null){
			DatamartProvider datamartProvider = new DatamartProvider();
			geoDocument.setDatamartProvider(datamartProvider);
			datamartProvider.setClassName(rb.getString("datamartprovider.class.name"));
		}
		if(geoDocument.getMapRenderer()==null){
			MapRenderer mapRenderer= new MapRenderer();
			geoDocument.setMapRenderer(mapRenderer);
			mapRenderer.setClassName(rb.getString("maprenderer.class.name"));
		}
		
		//put id to links
		if(geoDocument.getDatamartProvider()!=null && geoDocument.getDatamartProvider().getCrossNavigation()!=null && geoDocument.getDatamartProvider().getCrossNavigation().getLinks()!=null){
			Vector<Link> links=geoDocument.getDatamartProvider().getCrossNavigation().getLinks();
			for (Iterator iterator = links.iterator(); iterator.hasNext();) {
				Link link = (Link) iterator.next();
				link.setId(CrossNavigation.idLink++);
			}
		}
		
		return geoDocument;
	}
	public void cleanGEODocument(GEODocument geoDocumentToSaveOnFile){
		DatamartProvider dmProvider = geoDocumentToSaveOnFile.getDatamartProvider();
		Metadata metadata = dmProvider.getMetadata();
		
		if(metadata != null){
			Vector<Column> colToRemove= new Vector<Column>();
			Vector<Column> columns = metadata.getColumn();
			if(columns != null){
				for(int j=0; j<columns.size(); j++){
					Column col = columns.elementAt(j);
					if(!col.isChoosenForTemplate()){
						colToRemove.add(col);
					}else if(col.isChoosenForTemplate() && (col.getType()== null || col.getType().equals(""))){
						colToRemove.add(col);
					}
				}
				columns.removeAll(colToRemove);
			}
		}
		
		MapRenderer mapRenderer = geoDocumentToSaveOnFile.getMapRenderer();
		Layers layers = mapRenderer.getLayers();
		if(layers != null){
			Vector<Layer> layToRemove= new Vector<Layer>();
			Vector<Layer> layerVect = layers.getLayer();
			if(layerVect != null){
		
				for(int j=0; j<layerVect.size(); j++){
					Layer layer = layerVect.elementAt(j);
					if(!layer.isChoosenForTemplate()){
						//layerVect.remove(layer);
						layToRemove.add(layer);
					}
				}
//				System.out.println(layToRemove.size());
				layerVect.removeAll(layToRemove);
//				System.out.println("layers left on doc :"+layerVect.size());
			}
		}
		//clean and rebiuld labels
		Labels labels = mapRenderer.getGuiSettings().getLabels();
		if(labels != null){
			Vector<Label> labelVect = labels.getLabel();
			if(labelVect != null){
				Vector<Label> labelsToRemove= new Vector<Label>();
				for(int i = 0; i<labelVect.size(); i++){
					Label label = labelVect.elementAt(i);
					if(label.getPosition() == null || label.getPosition().equalsIgnoreCase("")){
						labelsToRemove.add(label);
					}else{
						//set className
						String className=rb.getString("label.class.name");
						label.setClassName(className);
					}
				}
				labelVect.removeAll(labelsToRemove);
			}
		}
	}
	public void saveModel(GEODocument geoDocument){
		Activator.getDefault().setGeoDocument(geoDocument);
	}

	public GEODocument getModel(){
		return Activator.getDefault().getGeoDocument();
	}

}
