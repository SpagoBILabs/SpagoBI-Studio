/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONReader {


	private static Logger logger = LoggerFactory.getLogger(JSONReader.class);


	public static JSONObject createJSONObject(IFile objSel){
		logger.debug("IN");
		JSONObject o=null;
		try{
			InputStream inputStream = objSel.getContents();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);

			String line = null;
			StringBuffer stringBuffer  = new StringBuffer();
			while((line = reader.readLine())!= null) {
				stringBuffer.append(line);
			}

			inputStreamReader.close();
			reader.close();
			inputStream.close();

			String contentString = stringBuffer.toString();

			o = new JSONObject(contentString);


		}
		catch (Exception e) {
			logger.error("error in reading JSON object", e);
			return null;
		}
		catch (Throwable e) {
			logger.error("error in reading JSON object", e);
			return null;
		}
		
		logger.debug("OUT");
		return o;
	}




}
