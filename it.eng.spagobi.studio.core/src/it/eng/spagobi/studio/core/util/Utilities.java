package it.eng.spagobi.studio.core.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {

	private static Logger logger = LoggerFactory.getLogger(Utilities.class);

	
	public Properties getStudioMetaProperties(){
        logger.debug("IN");
		Properties properties = new Properties();
		try {
            properties.load(this.getClass().getResourceAsStream("/it/eng/spagobi/studio/core/config/config.properties"));
        } catch (IOException e) {
			logger.error("Error in reading properties file; using Studio settings as default");
        }
        logger.debug("OUT");
        return properties;
	}
	
	public static boolean readBooleanProperty(Properties properties, String propertyKey){
		boolean tpreturn = false;
		String value = properties.getProperty(propertyKey, "false");
		if(value.equalsIgnoreCase("true")){
			tpreturn = true;
		}
		return tpreturn;
	}
	
	
}
