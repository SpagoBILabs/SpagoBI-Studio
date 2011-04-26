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