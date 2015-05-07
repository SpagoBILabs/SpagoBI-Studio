/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo;

import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Refresh;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;

import java.util.Iterator;
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


	// No more used, TODO CAncel

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



	/** method used to see if a parameter is already present in input destination parameters TODO: cancel
	 * 
	 * @param docComp
	 * @param destinDocLabel
	 * @param destinParamLabel
	 * @return
	 */

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



	public Parameter getInputParameterByDocumentLabelAndParameterLabel(DocumentComposition docComp, String documentLabel, String parameterLabel){
		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		Parameter toReturn = null;
		if(docConf != null){
			Document document = docConf.getDocumentByLabel(documentLabel);
			Vector<Parameter> parameters = document.getParameters().getParameter();
			if(parameters != null){
				for (Iterator iterator = parameters.iterator(); iterator.hasNext() && toReturn == null;) {
					Parameter parameter = (Parameter) iterator.next();
					if(parameter.getType().equalsIgnoreCase("IN") && parameter.getSbiParLabel().equals(parameterLabel)){
						toReturn = parameter;
					}

				}

			}
		}		
		return toReturn;
	}

	/** Returns navigation name  if the parameter is used in a navigation
	 * 
	 * @return
	 */

	public String isParameterUsedInNavigation(DocumentComposition docComposition, String inputId){
		String used = null;

		DocumentsConfiguration documentsConfiguration = docComposition.getDocumentsConfiguration();
		Vector<Document> documents = documentsConfiguration.getDocuments();

		for (Iterator iterator = documents.iterator(); iterator.hasNext() && used == null;) {
			Document document = (Document) iterator.next();
			Parameters pars = document.getParameters();
			Vector<Parameter> parameters = pars.getParameter();
			if(parameters != null){
				for (Iterator iterator2 = parameters.iterator(); iterator2.hasNext() && used == null;) {
					Parameter parameter2 = (Parameter) iterator2.next();
					if(parameter2.getType().equalsIgnoreCase("OUT")){
						Refresh refresh = parameter2.getRefresh();
						Vector<RefreshDocLinked> docsLinked = refresh.getRefreshDocLinked();
						for (Iterator iterator3 = docsLinked.iterator(); iterator3.hasNext() && used == null;) {
							RefreshDocLinked refreshDocLinked = (RefreshDocLinked) iterator3.next();
							if( refreshDocLinked.getIdParam().equals(inputId)){
								used = "document's "+ refreshDocLinked.getLabelDoc()+" parameter "+refreshDocLinked.getLabelParam()+" is used as input parameter for a navigation";
							}
						}
					}

				}
			}
		}
		return used;
	}








}
