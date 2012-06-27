/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtChartConfigurations {

	public static final String INFO_FILE = "it/eng/spagobi/studio/extchart/configuration/extcharts.xml";

	private static Logger logger = LoggerFactory.getLogger(ExtChartConfigurations.class);

	public static List getConfiguredChartTypes() throws Exception {
		List toReturn = null;
		try{
			InputStream is = getInputStreamFromResource(INFO_FILE);
			Document document = new SAXReader().read(is);
			List charts = document.selectNodes("//EXTCHARTS/EXTCHART");
			if (charts == null || charts.size() == 0) return null;
			toReturn = new ArrayList();
			for (int i = 0; i < charts.size(); i++) {
				Node chart = (Node) charts.get(i);
				String type = chart.valueOf("@type");
				if (type == null || type.trim().equals("")) continue;
				toReturn.add(type);
			}
		}catch (Exception e) {
			logger.error("Error in reading configuration types from file "+INFO_FILE);
		}
		return toReturn;
	}


	
	

	/** Get input stream from a resource
	 * 
	 * @param resourcePath
	 * @return
	 * @throws IOException
	 */

	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		Bundle b = Platform.getBundle(it.eng.spagobi.studio.extchart.Activator.PLUGIN_ID);
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}

}
