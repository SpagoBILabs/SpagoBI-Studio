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
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo;

import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;

import java.util.Vector;

public class RefreshDocLinkedBO {
	
	/**Method used to get all idParam referencing inputPrameters used by master documents
	 * @param docComp
	 * @return Vector<String> of idParam
	 */
	public Vector<String> getIdParamsUsedByRefreshes(DocumentComposition docComp){
		Vector<String> idParamsUsed = new Vector<String>();
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
	    Vector documents = docConf.getDocuments();
	    if(documents != null){
		    for (int i = 0; i< documents.size(); i++){
		    	Document doc = (Document)documents.get(i);
	    		Parameters params = doc.getParameters();
	    		if(params != null){
	    			Vector<Parameter> parameters =params.getParameter();
	    			if(parameters != null){
	    				for(int j=0; j<parameters.size(); j++){	    			
	    					Parameter param = parameters.elementAt(j);
	    					if(param.getType().equalsIgnoreCase("OUT")){
		    					if(param.getRefresh() != null && param.getRefresh().getRefreshDocLinked() != null){									
									for(int k=0; k<param.getRefresh().getRefreshDocLinked().size(); k++){
										RefreshDocLinked docRef = param.getRefresh().getRefreshDocLinked().elementAt(k);
										idParamsUsed.add(docRef.getIdParam());
									}
		    					}
	    					}
	    				}
	    			}
	    		}
		    }
	    }			
		
		return idParamsUsed;
	}
	
	public RefreshDocLinked deleteRefreshedDocLink(DocumentComposition docComp, String id, String sbiParLabel, String  sbiObjLabel){
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
	    Vector documents = docConf.getDocuments();
	    RefreshDocLinked docRefrFound = null; 
	    if(documents != null){
		    //elabora documento master
		    for (int i = 0; i< documents.size(); i++){
		    	Document doc = (Document)documents.get(i);
	    		Parameters params = doc.getParameters();
	    		if(params != null){
	    			Vector<Parameter> parameters =params.getParameter();
	    			if(parameters != null){
	    				for(int j=0; j<parameters.size(); j++){	    			
	    					Parameter param = parameters.elementAt(j);
	    					if(param.getRefresh() != null && param.getRefresh().getRefreshDocLinked() != null){
								
								for(int k=0; k<param.getRefresh().getRefreshDocLinked().size(); k++){
									RefreshDocLinked docRef = param.getRefresh().getRefreshDocLinked().elementAt(k);
									if(docRef.getLabelDoc().equals(sbiObjLabel) && docRef.getLabelParam().equals(sbiParLabel) && docRef.getIdParam().equals(id)){
										param.getRefresh().getRefreshDocLinked().remove(docRef);
									}
								}
	    					}
	    				}
	    			}
	    		}
		    }
	    }				
		
		return docRefrFound;
	}
	public RefreshDocLinked refreshDocAlreadyExists(String id, String docName, Vector<RefreshDocLinked> refreshes){
		RefreshDocLinked docRefrFound = null; 
		for(int i=0; i<refreshes.size(); i++){
			RefreshDocLinked doc = refreshes.elementAt(i);
			if(doc.getLabelDoc().equals(docName) && doc.getIdParam().equals(id)){
				docRefrFound = doc;
			}
		}		
		return docRefrFound;
	}
	public RefreshDocLinked refreshByParamIdAlreadyExists(String id, Vector<RefreshDocLinked> refreshes){
		RefreshDocLinked docRefrFound = null; 
		for(int i=0; i<refreshes.size(); i++){
			RefreshDocLinked doc = refreshes.elementAt(i);
			if(doc.getIdParam().equals(id)){
				docRefrFound = doc;
			}
		}		
		return docRefrFound;
	}
	public RefreshDocLinked upadateRefreshedDocLink(DocumentComposition docComp, String id, String sbiParLabel, String  sbiObjLabel){
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
	    Vector documents = docConf.getDocuments();
	    RefreshDocLinked docRefrFound = null; 
	    if(documents != null){
		    //elabora documento master
		    for (int i = 0; i< documents.size(); i++){
		    	Document doc = (Document)documents.get(i);
	    		Parameters params = doc.getParameters();
	    		if(params != null){
	    			Vector<Parameter> parameters =params.getParameter();
	    			if(parameters != null){
	    				for(int j=0; j<parameters.size(); j++){	    			
	    					Parameter param = parameters.elementAt(j);
	    					if(param.getRefresh() != null && param.getRefresh().getRefreshDocLinked() != null){
								
							//non esisteva--> insersco
								RefreshDocLinked docRef = new RefreshDocLinked();
								docRef.setIdParam(id);
								docRef.setLabelDoc(sbiObjLabel);
								docRef.setLabelParam(sbiParLabel);
								param.getRefresh().getRefreshDocLinked().add(docRef);
							
	    					}
	    				}
	    			}
	    		}
		    }
	    }				
		
		return docRefrFound;
	}

	public boolean inputParameterIsUsedByOther(DocumentComposition docComp, String id){
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
	    Vector documents = docConf.getDocuments();
	    boolean isUsedMoreThanOnce = false;
	    int counter =0;
	    if(documents != null){
		    //elabora documento master
		    for (int i = 0; i< documents.size(); i++){
		    	Document doc = (Document)documents.get(i);
	    		Parameters params = doc.getParameters();
	    		if(params != null){
	    			Vector<Parameter> parameters =params.getParameter();
	    			if(parameters != null){
	    				for(int j=0; j<parameters.size(); j++){	    			
	    					Parameter param = parameters.elementAt(j);
	    					if(param.getRefresh() != null && param.getRefresh().getRefreshDocLinked() != null){
	    						for(int k=0; k<param.getRefresh().getRefreshDocLinked().size(); k++){
	    							RefreshDocLinked docRef = param.getRefresh().getRefreshDocLinked().elementAt(k);
	    							if(docRef.getIdParam().equals(id)){
	    								counter++;
	    							}
	    						}	
							
	    					}
	    				}
	    			}
	    		}
		    }
	    }		

		if(counter > 1){
			isUsedMoreThanOnce = true;
		}
		return isUsedMoreThanOnce;
	}
}
