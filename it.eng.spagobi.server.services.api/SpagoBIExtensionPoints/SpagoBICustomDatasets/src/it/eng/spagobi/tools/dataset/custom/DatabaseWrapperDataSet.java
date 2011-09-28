package it.eng.spagobi.tools.dataset.custom;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.spago.base.SourceBean;
import it.eng.spago.security.IEngUserProfile;
import it.eng.spagobi.commons.dao.DAOFactory;
import it.eng.spagobi.tools.dataset.bo.IJavaClassDataSet;
import it.eng.spagobi.tools.dataset.common.dataproxy.JDBCDataProxy;
import it.eng.spagobi.tools.dataset.common.datareader.JDBCDataReader;
import it.eng.spagobi.tools.dataset.common.datastore.IDataStore;
import it.eng.spagobi.tools.dataset.common.datastore.IField;
import it.eng.spagobi.tools.dataset.common.datastore.IRecord;
import it.eng.spagobi.tools.datasource.bo.IDataSource;
import it.eng.spagobi.tools.datasource.dao.IDataSourceDAO;

public class DatabaseWrapperDataSet implements IJavaClassDataSet {

	private IDataSourceDAO dataSourceDAO;
	
	private final static String SPAGOBI_DATA_SOURCE_LABEL = "FoodMart";
	private final static String QUERY = "SELECT * FROM customer";
	
	private static transient Logger logger = Logger.getLogger(DatabaseWrapperDataSet.class);
	
	public String getValues(IEngUserProfile userProfile, Map parameters) {
		
		SourceBean resultSet;
		SourceBean row;
		IDataSource dataSource;
		JDBCDataProxy dataProxy;
		IDataStore dataStore;
		
		logger.debug("IN");
		
		try {
			resultSet = new SourceBean("ROWS");
			
			// get data source
			try {
				if(dataSourceDAO == null) {
					dataSourceDAO = DAOFactory.getDataSourceDAO();
				}
	        	dataSource = dataSourceDAO.loadDataSourceByLabel(SPAGOBI_DATA_SOURCE_LABEL);
			} catch (Throwable t1){
				throw new RuntimeException("Impossible to get datasource [" + SPAGOBI_DATA_SOURCE_LABEL + "]", t1);
			}
			
	        // query execution
			try {
		        dataProxy = new JDBCDataProxy(dataSource, QUERY);
		        dataStore = dataProxy.load( new JDBCDataReader() );
			} catch (Throwable t1){
				throw new RuntimeException("Impossible to execute query [" + QUERY + "] on datasource [" + SPAGOBI_DATA_SOURCE_LABEL + "]", t1);
			}
			
			// apply some business logic to the original dataStore
			// ....
	       
	        // serialize the dataStore
	        Iterator it = dataStore.iterator();
	        while(it.hasNext()) {
	        	IRecord record = (IRecord)it.next();
	        	row = new SourceBean("ROW");
	        	
	        	List fields = record.getFields();
	        	for(int i = 0; i < fields.size(); i++) {
	        		IField field = (IField)fields.get(i);
	        		row.setAttribute("column-" + (i+1), field.getValue() != null? field.getValue(): "");
	        	}
	        	resultSet.setAttribute(row);
	        }
	        
			
			return resultSet.toXML();
		} catch (Throwable t) {
			throw new RuntimeException("Impossible to generate dataset", t);
		} finally {
			logger.debug("OUT");
		}
	}
		
	public List getNamesOfProfileAttributeRequired() {
		// no profile attributes required
		return null;
	}
}
