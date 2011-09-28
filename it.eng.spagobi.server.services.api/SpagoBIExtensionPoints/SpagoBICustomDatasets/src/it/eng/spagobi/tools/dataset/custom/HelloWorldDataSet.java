package it.eng.spagobi.tools.dataset.custom;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.spago.base.SourceBean;
import it.eng.spago.security.IEngUserProfile;
import it.eng.spagobi.tools.dataset.bo.IJavaClassDataSet;

public class HelloWorldDataSet implements IJavaClassDataSet {

	private static transient Logger logger = Logger.getLogger(HelloWorldDataSet.class);
	
	public List getNamesOfProfileAttributeRequired() {
		// no profile attributes required
		return null;
	}

	public String getValues(IEngUserProfile userProfile, Map parameters) {
		SourceBean resultSet;
		SourceBean row;
		
		logger.debug("IN");
		
		try {
				
			resultSet = new SourceBean("ROWS");
						
			// row #1
			row = new SourceBean("ROW");
			row.setAttribute("Column-1", "Hello");
			row.setAttribute("Column-2", "world");
			row.setAttribute("Column-3", new Integer(10));
			resultSet.setAttribute(row);
			
			// row #2
			row = new SourceBean("ROW");
			row.setAttribute("Column-1", "Ciao");
			row.setAttribute("Column-2", "mondo");
			row.setAttribute("Column-3", new Integer(99));
			resultSet.setAttribute(row);
			
			return resultSet.toXML();
			
		} catch (Throwable t) {
			throw new RuntimeException("Impossible to generate dataset", t);
		} finally {
			logger.debug("OUT");
		}
	}
}
