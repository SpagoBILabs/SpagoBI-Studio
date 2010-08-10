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

import it.eng.spagobi.studio.geo.editors.model.geo.CrossNavigation;
import it.eng.spagobi.studio.geo.editors.model.geo.DatamartProvider;
import it.eng.spagobi.studio.geo.editors.model.geo.GEODocument;
import it.eng.spagobi.studio.geo.editors.model.geo.Link;
import it.eng.spagobi.studio.geo.editors.model.geo.LinkParam;

import java.util.Vector;

public class LinkBO {

	public static Link setNewLink(GEODocument geoDocument, String hierarchy,
			String level) {
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		CrossNavigation crossNavigation = dmProvider.getCrossNavigation();
		Vector<Link> links = null;
		Link link = new Link();
		if (crossNavigation != null) {
			links = crossNavigation.getLinks();
			if(links == null){
				links = new Vector<Link>();
				crossNavigation.setLinks(links);
			}
		}else{
			crossNavigation = new CrossNavigation();
			links = new Vector<Link>();
			crossNavigation.setLinks(links);
			dmProvider.setCrossNavigation(crossNavigation);
		}
		link.setHierarchy(hierarchy);
		link.setLevel(level);
		links.add(link);
		return link;

	}


	public static void updateExisting(GEODocument geoDocument, Integer idLink, String levelName) {
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		CrossNavigation crossNavigation = dmProvider.getCrossNavigation();
		if(idLink!=null){
			if (crossNavigation != null) {
				Vector<Link> links = crossNavigation.getLinks();
				if(links!=null){
					for (int i = 0; i < links.size(); i++) {
						Link linkI = links.elementAt(i);
						if(linkI.getId().equals(idLink)){
							linkI.setLevel(levelName);
						}
					}
				}

			}
		}
	}



	public static Link addParamToLink(GEODocument geoDocument, Link link, LinkParam param) {
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		CrossNavigation crossNavigation = dmProvider.getCrossNavigation();
		Vector<Link> links = crossNavigation.getLinks();
		Vector<LinkParam> params = link.getParam();
		if(params == null){
			params = new Vector<LinkParam>();
			link.setParam(params);
		}
		params.add(param);
		return link;
	}

	public static LinkParam getLinkParam(Link link, LinkParam param) {
		Vector<LinkParam> params = link.getParam();
		if(params != null){
			params = new Vector<LinkParam>();
			if(params.contains(param)){
				return param;
			}
		}
		return null;
	}
	public static void deleteLinkParam(Link link, String paramName) {
		Vector<LinkParam> params = link.getParam();
		if(params != null){
			for(int i=0; i<params.size(); i++){
				LinkParam param = params.elementAt(i);
				if(param.getName()!= null && param.getName().equals(paramName)){
					params.remove(param);
				}
			}
		}
	}

	public static Link getLinkByHierarchyAndLevel(GEODocument geoDocument, Integer idLink) {
		Link link = null;
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		CrossNavigation crossNavigation = dmProvider.getCrossNavigation();
		if(idLink!=null){
			if (crossNavigation != null) {
				Vector<Link> links = crossNavigation.getLinks();
				if(links!=null){
					for (int i = 0; i < links.size(); i++) {
						Link linkI = links.elementAt(i);
						//					if (hierarchyName != null && levelName != null
						//							&& linkI.getHierarchy().equals(hierarchyName)
						//							&& linkI.getLevel().equals(levelName)) {
						if(linkI.getId().equals(idLink)){
							link = linkI;
						}
					}
				}

			}
		}
		return link;
	}

	public static void deleteLink(GEODocument geoDocument,
			String hierarchyName, String levelName){
		DatamartProvider dmProvider = geoDocument.getDatamartProvider();
		CrossNavigation crossNavigation = dmProvider.getCrossNavigation();
		if (crossNavigation != null) {
			Vector<Link> links = crossNavigation.getLinks();
			for (int i = 0; i < links.size(); i++) {
				Link linkI = links.elementAt(i);
				if (hierarchyName != null && levelName != null
						&& linkI.getHierarchy().equals(hierarchyName)
						&& linkI.getLevel().equals(levelName)) {
					links.remove(linkI);
				}

			}
		}
	}

}
