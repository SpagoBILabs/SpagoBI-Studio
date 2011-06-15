/*
 *************************************************************************
 * Copyright (c) 2008 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package it.eng.spagobi.studio.oda.impl;

import it.eng.spagobi.sdk.datasets.bo.SDKDataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.tools.dataset.common.datastore.IDataStoreMetaData;

import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;


/**
 * Implementation class of IResultSetMetaData for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class ResultSetMetaData implements IResultSetMetaData
{
	DataStoreMetadata dataStoreMeta;
	
	public ResultSetMetaData(DataStoreMetadata dataStoreMeta) {
		
		this.dataStoreMeta = dataStoreMeta;
	}
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnCount()
	 */
	public int getColumnCount() throws OdaException
	{
        int columnCount = 0;
		
        try {
			columnCount = dataStoreMeta.getFieldsMetadata().length;
		} catch(Throwable t) {
			throw (OdaException) new OdaException("Impossible to extract column count from data store meta").initCause(t);
		}
		
		return columnCount;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnName(int)
	 */
	public String getColumnName( int index ) throws OdaException
	{
		String name = "undefined";
		
		 try {
			 name = dataStoreMeta.getFieldsMetadata()[index-1].getName();
		} catch(Throwable t) {
			throw (OdaException) new OdaException("Impossible to extract column-" + index + "'s name from data store meta").initCause(t);
		}
      
        return name;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnLabel(int)
	 */
	public String getColumnLabel( int index ) throws OdaException
	{
		return getColumnName( index );		// default
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnType(int)
	 */
	public int getColumnType( int index ) throws OdaException
	{
		String className =  dataStoreMeta.getFieldsMetadata()[index-1].getClassName();
		
        if( className.endsWith("Integer") ) {
            return java.sql.Types.INTEGER;   
        }
        return java.sql.Types.CHAR;          
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnTypeName(int)
	 */
	public String getColumnTypeName( int index ) throws OdaException
	{
        int nativeTypeCode = getColumnType( index );
        return Driver.getNativeDataTypeName( nativeTypeCode );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnDisplayLength(int)
	 */
	public int getColumnDisplayLength( int index ) throws OdaException
	{
        // hard-coded for demo purpose
		return 8;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getPrecision(int)
	 */
	public int getPrecision( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getScale(int)
	 */
	public int getScale( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#isNullable(int)
	 */
	public int isNullable( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return IResultSetMetaData.columnNullableUnknown;
	}
    
}
