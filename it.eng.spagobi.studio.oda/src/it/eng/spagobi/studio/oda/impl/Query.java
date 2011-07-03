/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

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

package it.eng.spagobi.studio.oda.impl;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasets.bo.SDKDataSetParameter;
import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.sdk.exceptions.NotAllowedOperationException;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.Dataset;
import it.eng.spagobi.studio.utils.bo.DatasetParameter;
import it.eng.spagobi.studio.utils.services.serverobjects.ServerDatasets;
import it.eng.spagobi.tools.dataset.common.datareader.JSONDataReader;
import it.eng.spagobi.tools.dataset.common.datareader.XmlDataReader;
import it.eng.spagobi.tools.dataset.common.datastore.IDataStore;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.datatools.connectivity.oda.SortSpec;
import org.eclipse.datatools.connectivity.oda.spec.QuerySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation class of IQuery for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class Query implements IQuery
{
	int maxRows;
	String queryString;
	Map<String, Integer> parameterNamesToIndexMap;
	
	ServerDatasets dataSetServiceProxy;
	Dataset dataSetMeta;
	DatasetParameter[] dataSetParametersMeta;
	DataStoreMetadata dataStoreMeta;
	
	private static Logger logger = LoggerFactory.getLogger(Query.class);
	
	public Query(ServerDatasets dataSetServiceProxy) {
		this.maxRows = -1;
		this.queryString = null;
		this.parameterNamesToIndexMap = new HashMap<String, Integer>();
		this.dataSetServiceProxy = dataSetServiceProxy;
		
	}
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#prepare(java.lang.String)
	 */
	public void prepare( String queryText ) throws OdaException
	{		
		
		logger.debug("IN");
	
		try {
			logger.debug("Prepare query for dataset [" + queryText + "]");
			
			if(queryText == null || queryText.trim().length() == 0) {
				throw new RuntimeException("Query cannot be null or empty");
			}
			this.queryString = queryText;
			
			logger.debug("Retriving dataset list from spagobi server...");
			Vector<Dataset> datasets = null;
			try {
				datasets = dataSetServiceProxy.getAllDatasets();
			} catch(Throwable t) {
				throw new RuntimeException("Impossible to retrive spagobi's dataset list", t);
			}
			logger.debug("Dataset list from spagobi server retrieved succesfully");
			
			logger.debug("Look up dataset list for [" + queryText + "]");
			dataSetMeta = null;
			try {
				
				for(int i =0; i<datasets.size(); i++){
					Dataset datsSet = (Dataset)datasets.get(i);
					if(queryText.equals(datsSet.getLabel())){
						dataSetMeta = datsSet;
						break;
					}
				}
			} catch (Throwable t) {
				throw new RuntimeException("Impossible to retrive store metadata for dataset [" + dataSetMeta.getName() + "]", t);
			} 
			
			if(dataSetMeta == null) {
				throw new RuntimeException("Impssible to find on server a dataset named [" + queryText + "]");
			}
			logger.debug("Dataset [" + queryText + "] succesfully found");
			
			logger.debug("Retrieving data store meta for dataset [" + queryText + "]...");
			dataStoreMeta = null;
			try {
				dataStoreMeta =  dataSetServiceProxy.getDataStoreMetadata(dataSetMeta.getId());
				if(dataStoreMeta == null) {
					throw new RuntimeException("Bad server response [null] for service [getDataStoreMetadata]");
				}
			} catch(Throwable t) {
				throw new RuntimeException("Impossible to retrive store metadata for dataset [" + dataSetMeta.getName() + "]", t);
			}
			logger.debug("Data store meta for dataset [" + queryText + "] retrieved succesfully");
			
			logger.debug("Parsing dataset's parameters meta for dataset[" + queryText + "]...");
			dataSetParametersMeta = dataSetMeta.getParameters();
			if(dataSetParametersMeta != null) {
				for(int i = 0; i < dataSetParametersMeta.length; i++) {
					parameterNamesToIndexMap.put(dataSetParametersMeta[i].getName(), new Integer(i+1));
				}
			}
			logger.debug("Dataset's parameters meta for dataset[" + queryText + "] parsed succesfully");
			
			logger.debug("Query for dataset [" + queryText + "] prepared successfully");
		} catch (Exception t) {
			throw (OdaException) new OdaException("Impossible to prepare query [" + queryText +"]").initCause(t);
		}  finally {
			logger.debug("OUT");
		}
		
	}
	

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setAppContext(java.lang.Object)
	 */
	public void setAppContext( Object context ) throws OdaException
	{
	    // do nothing; assumes no support for pass-through context
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#close()
	 */
	public void close() throws OdaException
	{
		dataSetServiceProxy = null;
		dataStoreMeta = null;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getMetaData()
	 */
	public IResultSetMetaData getMetaData() throws OdaException
	{
		return new ResultSetMetaData( dataStoreMeta );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#executeQuery()
	 */
	public IResultSet executeQuery() throws OdaException
	{
		String result;
		IDataStore dataStore;
		
		logger.debug("IN");
		
		try {
			for(int i = 0; i < dataSetParametersMeta.length; i++) {
				String name = dataSetParametersMeta[i].getName();
				String value = dataSetParametersMeta[i].getValues() != null ? dataSetParametersMeta[i].getValues()[0]: "NULL";
				
				//System.err.println("Input parameter [" + name + "] is equal to [" + value + "]");
			}
			result = dataSetServiceProxy.executeDataSet( dataSetMeta.getLabel(), dataSetParametersMeta );
		} catch (Throwable t) {
			throw (OdaException) new OdaException("Impossible to execute dataset [" + dataSetMeta.getLabel() + "]").initCause(t);
		}
		
		dataStore = null;
		try {
			//XmlDataReader dataReader = new XmlDataReader();
			JSONDataReader dataReader = new JSONDataReader ();
			dataStore = dataReader.read( result );
		} catch (Throwable t) {
			throw (OdaException) new OdaException("Impossible to parse resultset [" + result + "]").initCause(t);
		}
		
		logger.debug("OUT");
		
		return new ResultSet( dataStore, dataStoreMeta );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setProperty(java.lang.String, java.lang.String)
	 */
	public void setProperty( String name, String value ) throws OdaException
	{
		// do nothing; assumes no data set query property
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setMaxRows(int)
	 */
	public void setMaxRows( int max ) throws OdaException
	{
	    maxRows = max;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getMaxRows()
	 */
	public int getMaxRows() throws OdaException
	{
		return maxRows;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#clearInParameters()
	 */
	public void clearInParameters() throws OdaException
	{
       for(int i = 0; i < dataSetParametersMeta.length; i++) {
    	   dataSetParametersMeta[i].setValues(new String[]{""});
       }
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setInt(java.lang.String, int)
	 */
	public void setInt( String parameterName, int value ) throws OdaException
	{
		setInt ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setInt(int, int)
	 */
	public void setInt( int parameterId, int value ) throws OdaException
	{
		//System.err.println("Paraeter [" + dataSetParametersMeta[parameterId-1].getName() + "] is equal to [" + value + "]");
		dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setDouble(java.lang.String, double)
	 */
	public void setDouble( String parameterName, double value ) throws OdaException
	{
		setDouble ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setDouble(int, double)
	 */
	public void setDouble( int parameterId, double value ) throws OdaException
	{
		//System.err.println("Paraeter [" + dataSetParametersMeta[parameterId-1].getName() + "] is equal to [" + value + "]");
		dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setBigDecimal(java.lang.String, java.math.BigDecimal)
	 */
	public void setBigDecimal( String parameterName, BigDecimal value ) throws OdaException
	{
		setBigDecimal ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setBigDecimal(int, java.math.BigDecimal)
	 */
	public void setBigDecimal( int parameterId, BigDecimal value ) throws OdaException
	{
		//System.err.println("Paraeter [" + dataSetParametersMeta[parameterId-1].getName() + "] is equal to [" + value + "]");
		dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setString(java.lang.String, java.lang.String)
	 */
	public void setString( String parameterName, String value ) throws OdaException
	{
		//System.err.println("Paraeter [" + parameterName + "] is equal to [" + value + "]");
		setString ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setString(int, java.lang.String)
	 */
	public void setString( int parameterId, String value ) throws OdaException
	{
		//System.err.println("Paraeter [" + dataSetParametersMeta[parameterId-1].getName() + "] is equal to [" + value + "]");
        dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setDate(java.lang.String, java.sql.Date)
	 */
	public void setDate( String parameterName, Date value ) throws OdaException
	{
		setDate ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setDate(int, java.sql.Date)
	 */
	public void setDate( int parameterId, Date value ) throws OdaException
	{
		dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setTime(java.lang.String, java.sql.Time)
	 */
	public void setTime( String parameterName, Time value ) throws OdaException
	{
		setTime ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setTime(int, java.sql.Time)
	 */
	public void setTime( int parameterId, Time value ) throws OdaException
	{
		dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setTimestamp(java.lang.String, java.sql.Timestamp)
	 */
	public void setTimestamp( String parameterName, Timestamp value ) throws OdaException
	{
		setTimestamp ( findInParameter( parameterName ), value);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setTimestamp(int, java.sql.Timestamp)
	 */
	public void setTimestamp( int parameterId, Timestamp value ) throws OdaException
	{
		dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
	}

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IQuery#setBoolean(java.lang.String, boolean)
     */
    public void setBoolean( String parameterName, boolean value )
            throws OdaException
    {
    	setBoolean ( findInParameter( parameterName ), value);
    }

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IQuery#setBoolean(int, boolean)
     */
    public void setBoolean( int parameterId, boolean value )
            throws OdaException
    {
    	dataSetParametersMeta[parameterId-1].setValues(new String[]{"" + value});
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IQuery#setNull(java.lang.String)
     */
    public void setNull( String parameterName ) throws OdaException
    {
    	setNull ( findInParameter( parameterName ));
    }

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IQuery#setNull(int)
     */
    public void setNull( int parameterId ) throws OdaException
    {
    	dataSetParametersMeta[parameterId-1].setValues(new String[]{""});
    }

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#findInParameter(java.lang.String)
	 */
	public int findInParameter( String parameterName ) throws OdaException
	{
		Integer index = parameterNamesToIndexMap.get(parameterName);
		return index != null? index.intValue(): -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getParameterMetaData()
	 */
	public IParameterMetaData getParameterMetaData() throws OdaException
	{
		return new ParameterMetaData(dataSetParametersMeta);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#setSortSpec(org.eclipse.datatools.connectivity.oda.SortSpec)
	 */
	public void setSortSpec( SortSpec sortBy ) throws OdaException
	{
        throw new UnsupportedOperationException();
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IQuery#getSortSpec()
	 */
	public SortSpec getSortSpec() throws OdaException
	{
		return null;
	}
	@Override
	public void cancel() throws OdaException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getEffectiveQueryText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QuerySpecification getSpecification() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setObject(String arg0, Object arg1) throws OdaException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setObject(int arg0, Object arg1) throws OdaException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSpecification(QuerySpecification arg0) throws OdaException,
			UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}
    
}
