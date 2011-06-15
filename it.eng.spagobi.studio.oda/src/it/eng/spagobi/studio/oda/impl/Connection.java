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

import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;

import java.util.Properties;

import org.eclipse.datatools.connectivity.oda.IConnection;
import org.eclipse.datatools.connectivity.oda.IDataSetMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.ibm.icu.util.ULocale;

/**
 * Implementation class of IConnection for an ODA runtime driver.
 */
public class Connection implements IConnection
{
	
	boolean isOpen;
	DataSetsSDKServiceProxy dataSetServiceProxy;
	
	public static final String CONN_PROP_SERVER_URL = "ServerUrl";
	public static final String CONN_PROP_USER = "Username";
	public static final String CONN_PROP_PASSWORD = "Password";
	
	private static Logger logger = LoggerFactory.getLogger(Connection.class);
	
	
	public Connection() {
		isOpen = false;
		dataSetServiceProxy = null;
	}
	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#open(java.util.Properties)
	 */
	public void open( Properties connProperties ) throws OdaException
	{
		String serverUrl;
		String username;
		String password;
		
		logger.trace("IN");
		
		try {
			
			serverUrl = connProperties.getProperty( CONN_PROP_SERVER_URL );
			logger.debug("Connection properties [" + CONN_PROP_SERVER_URL + "] is equal to [" + serverUrl + "]");
			
			username = connProperties.getProperty( CONN_PROP_USER );
			logger.debug("Connection properties [" + CONN_PROP_USER + "] is equal to [" + username + "]");
			
			password = connProperties.getProperty( CONN_PROP_PASSWORD);
			logger.debug("Connection properties [" + CONN_PROP_PASSWORD + "] is equal to [" + password + "]");
					
			if(serverUrl == null || username == null || password == null) {
				throw new RuntimeException("Connection paramters (["+CONN_PROP_SERVER_URL+"],["+CONN_PROP_USER+"],["+CONN_PROP_PASSWORD+"]) cannot be null");
			}
			
			dataSetServiceProxy = createDataSetServiceProxy(serverUrl, username, password);
			
			logger.info("Connection sucesfully opened");
			
		} catch (Throwable t) {
			throw (OdaException) new OdaException("Impossible to open connection").initCause(t);
		}
		

		logger.debug("Data source initialized");

		logger.debug("Connection opened");
		isOpen = true;    
		
		logger.trace("OUT");
 	}
	
	private DataSetsSDKServiceProxy createDataSetServiceProxy(String serverUrl, String username, String password) {
		
		DataSetsSDKServiceProxy proxy;
		
		Properties props = System.getProperties();
		props.put("http.proxyHost", "proxy.eng.it");
		props.put("http.proxyPort", 3128);
		
		proxy = null;
		
		try {
			proxy = new DataSetsSDKServiceProxy(username, password);
		} catch(Throwable t) {
			throw new RuntimeException("Impossible to create dataset proxy", t);
		}
		
		if (serverUrl != null && !serverUrl.endsWith("/")) {
			serverUrl += "/";
		}
		
		logger.debug("Dataset proxy created succesfully");
		
		try {
			proxy.setEndpoint(serverUrl + "sdk/DataSetsSDKService");	
		} catch(Throwable t) {
			throw  new RuntimeException("Impossible to set dataset proxy's endpoint", t);
		}
		
		logger.debug("Dataset proxy's endpoint succesfully set to [" + serverUrl + "sdk/DataSetsSDKService" + "]");
		
		try {
			//new ProxyDataRetriever().initProxyData(proxy, serverUrl);
		} catch(Throwable t) {
			throw new RuntimeException("Impossible to initialize dataset proxy", t);
		}
		
		logger.debug("Dataset proxy initialized succesfully");
		
		return proxy;
	}

	
	
	
	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#setAppContext(java.lang.Object)
	 */
	public void setAppContext( Object context ) throws OdaException
	{
	    // do nothing; assumes no support for pass-through context
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#close()
	 */
	public void close() throws OdaException
	{
	    isOpen = false;
	    dataSetServiceProxy = null;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#isOpen()
	 */
	public boolean isOpen() throws OdaException
	{
		return isOpen;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#getMetaData(java.lang.String)
	 */
	public IDataSetMetaData getMetaData( String dataSetType ) throws OdaException
	{
	    // assumes that this driver supports only one type of data set,
        // ignores the specified dataSetType
		return new DataSetMetaData( this );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#newQuery(java.lang.String)
	 */
	public IQuery newQuery( String dataSetType ) throws OdaException
	{
        // assumes that this driver supports only one type of data set,
        // ignores the specified dataSetType
		return new Query(dataSetServiceProxy);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#getMaxQueries()
	 */
	public int getMaxQueries() throws OdaException
	{
		return 0;	// no limit
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#commit()
	 */
	public void commit() throws OdaException
	{
	    // do nothing; assumes no transaction support needed
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#rollback()
	 */
	public void rollback() throws OdaException
	{
        // do nothing; assumes no transaction support needed
	}
	@Override
	public void setLocale(ULocale arg0) throws OdaException {
		// TODO Auto-generated method stub
		
	}
    
}
