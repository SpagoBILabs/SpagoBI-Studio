/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/

package it.eng.spagobi.studio.oda.impl.runtime;

import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.services.proxy.DataSetServiceProxy;
import it.eng.spagobi.tools.dataset.bo.IDataSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
public class RuntimeConnection implements IConnection
{
	
	boolean isOpen;
	
	private static Logger logger = LoggerFactory.getLogger(RuntimeConnection.class);
	
	private DataSetServiceProxy proxyDataset = null;
	
	public RuntimeConnection(DataSetServiceProxy proxyDataset) {
		isOpen = false;
		this.proxyDataset = proxyDataset;
	}
	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#open(java.util.Properties)
	 */
	public void open( Properties connProperties ) throws OdaException
	{
		logger.trace("IN");

		IDataSet ds = this.proxyDataset.getDataSetByLabel("SBI_DS_HIGH2");
		System.out.println(ds.getQuery());
		logger.debug("Connection opened");
		isOpen = true;    
		
		logger.trace("OUT");
 	}

	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#setAppContext(java.lang.Object)
	 */
	public void setAppContext( Object context ) throws OdaException
	{
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#close()
	 */
	public void close() throws OdaException
	{
	    isOpen = false;
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
		return null;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#newQuery(java.lang.String)
	 */
	public IQuery newQuery( String dataSetType ) throws OdaException
	{
        // assumes that this driver supports only one type of data set,
        // ignores the specified dataSetType
		return null;
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
