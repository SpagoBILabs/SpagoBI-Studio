/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.services.intro;

import it.eng.spagobi.studio.core.perspectives.SpagoBIPerspective;

import java.util.Properties;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PerspectiveOpener implements IIntroAction {

	private static Logger logger = LoggerFactory.getLogger(PerspectiveOpener.class);



	public void run(IIntroSite site, Properties params) {
		logger.debug("IN");
		try{
			PlatformUI.getWorkbench().showPerspective(SpagoBIPerspective.PERSPECTIVE_ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow()); 
		}catch(Exception e){
			logger.error("Error in opening the perspective and closign the welcome view",e);
		}
		
		logger.debug("OUT");

	}


} 