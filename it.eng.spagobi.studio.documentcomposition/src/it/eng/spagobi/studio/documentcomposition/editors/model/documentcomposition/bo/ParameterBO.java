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

import java.util.Vector;

public class ParameterBO {
	
	public String getLastId(DocumentComposition docComp){
		int counter=0;

		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();
		    		
	    			Parameters parameters = doc.getParameters();
	    			if(parameters != null){
	    				Vector<Parameter> params = parameters.getParameter();
	    				if(params != null){
	    					for(int j=0;j<params.size(); j++){
	    						Parameter param = params.elementAt(j);
	    						int last =Integer.valueOf(param.getId()).intValue();
	    						if(last > counter){
	    							counter = last;
	    						}
	    					}
	    				}
	    			}
		    	    		
		    	}
		    }
		}
		return String.valueOf(counter);
	}
	
	public Parameter getParameterById(String id , Vector<Parameter> parameters){
		Parameter paramFound = null; 
		for(int i=0; i<parameters.size(); i++){
			Parameter param = parameters.elementAt(i);
			if(param.getId().equals(id)){
				paramFound = param;
			}
		}		
		return paramFound;
	}
	public void cleanUnusedInputParameters(DocumentComposition docComp, Vector<String> idParamUsedByRefresh){
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();
	    			Parameters parameters = doc.getParameters();
	    			if(parameters != null){
	    				Vector<Parameter> params = parameters.getParameter();
	    				if(params != null){
	    					for(int j=0;j<params.size(); j++){
	    						Parameter param = params.elementAt(j);
	    						if(param.getType().equalsIgnoreCase("IN") && !idParamUsedByRefresh.contains(param.getId())){
	    							params.remove(param);
	    						}
	    					}
	    				}
	    			}
	    		
		    	}
		    }
		}
	}
	public Parameter getParameterById(DocumentComposition docComp, String id){
		Parameter paramFound = null;
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();

		    			Parameters parameters = doc.getParameters();
		    			if(parameters != null){
		    				Vector<Parameter> params = parameters.getParameter();
		    				if(params != null){
		    					for(int j=0;j<params.size(); j++){
		    						Parameter param = params.elementAt(j);
		    						if(param.getType().equals("IN") && param.getId().equals(id)){
		    							paramFound = param;
		    						}
		    					}
		    				}
		    			}
	    		
		    	}
		    }
		}
	
		return paramFound;
	}
	public String getParameterDocumentName(DocumentComposition docComp, String id){
		String docName = null;
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();

		    			Parameters parameters = doc.getParameters();
		    			if(parameters != null){
		    				Vector<Parameter> params = parameters.getParameter();
		    				if(params != null){
		    					for(int j=0;j<params.size(); j++){
		    						Parameter param = params.elementAt(j);
		    						if(param.getType().equals("IN") && param.getId().equals(id)){
		    							docName = docLabel;
		    						}
		    					}
		    				}
		    			}
	    		
		    	}
		    }
		}
	
		return docName;
	}
	public boolean deleteParameterById(DocumentComposition docComp, String id){
		boolean paramFound = false;
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();

		    			Parameters parameters = doc.getParameters();
		    			if(parameters != null){
		    				Vector<Parameter> params = parameters.getParameter();
		    				if(params != null){
		    					for(int j=0;j<params.size(); j++){
		    						Parameter param = params.elementAt(j);
		    						if(param.getType().equals("IN") && param.getId().equals(id)){
		    							params.remove(param);
		    						}
		    					}
		    				}
		    			}
	    		
		    	}
		    }
		}
	
		return paramFound;
	}
	
	public Parameter getDocOutputParameter(Vector<Parameter> parameters, String paramLabel){
		Parameter paramFound = null; 
		if(parameters != null){
			for(int i=0; i<parameters.size(); i++){
				Parameter param = parameters.elementAt(i);
				if(paramLabel != null){
					if(param.getType().equals("OUT") && paramLabel.equals(param.getSbiParLabel())){
						paramFound = param;
					}
				}
			}
		}
		return paramFound;
	}
	
	public boolean outputParameterExists(DocumentComposition docComp, String masterDocLabel, String masterParamLabel){

		boolean ret = false;
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();
		    		if(masterDocLabel.equals(docLabel)){
		    			Parameters parameters = doc.getParameters();
		    			if(parameters != null){
		    				Vector<Parameter> params = parameters.getParameter();
		    				if(params != null){
		    					for(int j=0;j<params.size(); j++){
		    						Parameter param = params.elementAt(j);
		    						if(param.getType().equals("OUT") && param.getSbiParLabel().equals(masterParamLabel)){
		    							ret = true;
		    						}
		    					}
		    				}
		    			}
		    			
		    		}		    		
		    	}
		    }
		}
		
		return ret;
	}
	public Parameter getDocInputParameterByLabel(Vector<Parameter> parameters, String label){
		Parameter paramFound = null; 
		if(parameters != null){
			for(int i=0; i<parameters.size(); i++){
				Parameter param = parameters.elementAt(i);
				if(param.getType().equals("IN") && param.getSbiParLabel().equals(label)){
					paramFound = param;
				}
			}
		}
		return paramFound;
	}

	public boolean inputParameterExists(DocumentComposition docComp, String destinDocLabel, String destinParamLabel){
		boolean ret = false;
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	for (int i = 0; i< documents.size(); i++){
		    		Document doc = (Document)documents.elementAt(i);
		    		String docLabel =doc.getSbiObjLabel();
		    		if(destinDocLabel.equals(docLabel)){
		    			Parameters parameters = doc.getParameters();
		    			if(parameters != null){
		    				Vector<Parameter> params = parameters.getParameter();
		    				if(params != null){
		    					for(int j=0;j<params.size(); j++){
		    						Parameter param = params.elementAt(j);
		    						if(param.getType().equals("IN") && param.getSbiParLabel().equals(destinParamLabel)){
		    							ret = true;
		    						}
		    					}
		    				}
		    			}
		    			
		    		}		    		
		    	}
		    }
		}
		
		return ret;
	}

}
