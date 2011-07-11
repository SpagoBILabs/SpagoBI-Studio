/*
 *************************************************************************
 * Copyright (c) 2008 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package it.eng.spagobi.studio.oda.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import it.eng.spagobi.services.proxy.DataSetServiceProxy;
import it.eng.spagobi.studio.oda.impl.runtime.RuntimeConnection;
import it.eng.spagobi.utilities.engines.EngineConstants;

import org.eclipse.datatools.connectivity.oda.IConnection;
import org.eclipse.datatools.connectivity.oda.IDriver;
import org.eclipse.datatools.connectivity.oda.LogConfiguration;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.datatools.connectivity.oda.util.manifest.DataTypeMapping;
import org.eclipse.datatools.connectivity.oda.util.manifest.ExtensionManifest;
import org.eclipse.datatools.connectivity.oda.util.manifest.ManifestExplorer;


/**
 * Implementation class of IDriver for an ODA runtime driver.
 */
public class Driver implements IDriver
{
    static String ODA_DATA_SOURCE_ID = "spagobi.birt.oda";  //$NON-NLS-1$
    
    public static String CONTEXT_KEY_SBI_BIRT_RUNTIME_DATASET_PROXY = "SBI_BIRT_RUNTIME_DATASET_PROXY";
    public static String BIRT_VIEWER_HTTPSERVET_REQUEST = "BIRT_VIEWER_HTTPSERVET_REQUEST";
        
    public static String SBI_BIRT_RUNTIME_IS_RUNTIME = "SBI_BIRT_RUNTIME_IS_RUNTIME";
    public static String SBI_BIRT_RUNTIME_USER_ID = "SBI_BIRT_RUNTIME_USER_ID";
    public static String SBI_BIRT_RUNTIME_SECURE_ATTRS = "SBI_BIRT_RUNTIME_SECURE_ATTRS";
    public static String SBI_BIRT_RUNTIME_SERVICE_URL = "SBI_BIRT_RUNTIME_SERVICE_URL";
    public static String SBI_BIRT_RUNTIME_SERVER_URL = "SBI_BIRT_RUNTIME_SERVER_URL";
    public static String SBI_BIRT_RUNTIME_TOKEN = "SBI_BIRT_RUNTIME_TOKEN";
    public static String SBI_BIRT_RUNTIME_PASS = "SBI_BIRT_RUNTIME_PASS";
    
    private Object context = null;
    
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDriver#getConnection(java.lang.String)
	 */
	public IConnection getConnection( String dataSourceType ) throws OdaException
	{
		if (isBirtRuntimeContext()) {
			
			DataSetServiceProxy proxyDataset = null;
			try {
				System.out.println("Tring to get the DataSetServiceProxy ...");
				proxyDataset = getDataSetProxy();
				System.out.println("DataSetServiceProxy obtained correctly: " + proxyDataset);
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw e;
			}
			return new RuntimeConnection(proxyDataset);
		}
        return new Connection();
	}
	
	private DataSetServiceProxy getDataSetProxy() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException(
					"This method must be invoked in Birt runtime context!!!");
		}
		try {
			HashMap map = (HashMap) context;
			String userId = getUserId();
			String secureAttributes = getSecureAttrs();
			String serviceUrlStr = getServiceUrl();
			String spagoBiServerURL = getSpagoBIServerUrl();
			String token = getToken();
			String pass = getPass();
			DataSetServiceProxy proxy = new DataSetServiceProxy(userId, secureAttributes, serviceUrlStr, spagoBiServerURL, token, pass);
			return proxy;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting DataSetServiceProxy from Birt runtime context", e);
		}
	}
	
	private String getUserId() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException("This method must be invoked in Birt runtime context!!!");
		}
		try {
		    HashMap map = (HashMap) context;
		    String userId = (String) map.get(SBI_BIRT_RUNTIME_USER_ID);
		    return userId;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting user id from Birt runtime context", e);
		}
	}
	
	private String getSecureAttrs() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException("This method must be invoked in Birt runtime context!!!");
		}
		try {
		    HashMap map = (HashMap) context;
		    String secureAttributes = (String) map.get(SBI_BIRT_RUNTIME_SECURE_ATTRS);
		    return secureAttributes;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting user id from Birt runtime context", e);
		}
	}
	
	private String getServiceUrl() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException("This method must be invoked in Birt runtime context!!!");
		}
		try {
		    HashMap map = (HashMap) context;
		    String serviceUrlStr = (String) map.get(SBI_BIRT_RUNTIME_SERVICE_URL);
		    return serviceUrlStr;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting user id from Birt runtime context", e);
		}
	}
	
	private String getSpagoBIServerUrl() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException("This method must be invoked in Birt runtime context!!!");
		}
		try {
		    HashMap map = (HashMap) context;
		    String spagoBiServerURL = (String) map.get(SBI_BIRT_RUNTIME_SERVER_URL);
		    return spagoBiServerURL;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting user id from Birt runtime context", e);
		}
	}
	
	private String getToken() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException("This method must be invoked in Birt runtime context!!!");
		}
		try {
		    HashMap map = (HashMap) context;
		    String token = (String) map.get(SBI_BIRT_RUNTIME_TOKEN);
		    return token;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting user id from Birt runtime context", e);
		}
	}
	
	private String getPass() {
		if (!isBirtRuntimeContext()) {
			throw new RuntimeException("This method must be invoked in Birt runtime context!!!");
		}
		try {
		    HashMap map = (HashMap) context;
		    String pass = (String) map.get(SBI_BIRT_RUNTIME_PASS);
		    return pass;
		} catch (Exception e) {
			throw new RuntimeException("Error while getting user id from Birt runtime context", e);
		}
	}

	private boolean isBirtRuntimeContext() {
		System.out.println("Entering isBirtRuntimeContext");
	    if (context != null && context instanceof HashMap) {
	    	HashMap map = (HashMap) context;
	    	String isRuntime = (String) map.get(SBI_BIRT_RUNTIME_IS_RUNTIME);
	    	if(isRuntime!=null && isRuntime.equals("true")){
	    		System.out.println("Ok runtime");
	    		return true;	
	    	}else{
	    		System.out.println("NOT runtime");
	    		return false;
	    	}
	    }
	    return false;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDriver#setLogConfiguration(org.eclipse.datatools.connectivity.oda.LogConfiguration)
	 */
	public void setLogConfiguration( LogConfiguration logConfig ) throws OdaException
	{
		// do nothing; assumes simple driver has no logging
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDriver#getMaxConnections()
	 */
	public int getMaxConnections() throws OdaException
	{
		return 0;	// no limit
	}
	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDriver#setAppContext(java.lang.Object)
	 */
	public void setAppContext( Object context ) throws OdaException
	{
		this.context = context;
	    System.out.println("Driver: start setAppContext");
	    System.out.println(context != null ? context.getClass().getCanonicalName() : "null");
	    if (context != null && context instanceof HashMap) {
	    	HashMap map = (HashMap) context;
	    	EngineConstants d = null;
	    	Set<Map.Entry> entries = map.entrySet();
	    	Iterator<Map.Entry> it = entries.iterator();
	    	while (it.hasNext()) {
	    		Map.Entry entry = it.next();
	    		Object key = entry.getKey();
	    		Object value = entry.getValue();
	    		System.out.println("Entry key [" + key + "], value [" + value + "]");
	    	}
	    }
	    System.out.println("Driver: end setAppContext");
	}

    /**
     * Returns the object that represents this extension's manifest.
     * @throws OdaException
     */
    static ExtensionManifest getManifest() throws OdaException
    {
        return ManifestExplorer.getInstance().getExtensionManifest( ODA_DATA_SOURCE_ID );
    }
    
    /**
     * Returns the native data type name of the specified code, as
     * defined in this data source extension's manifest.
     * @param nativeTypeCode    the native data type code
     * @return                  corresponding native data type name
     * @throws OdaException     if lookup fails
     */
    public static String getNativeDataTypeName( int nativeDataTypeCode ) 
        throws OdaException
    {
        DataTypeMapping typeMapping = 
                            getManifest().getDataSetType( null )
                                .getDataTypeMapping( nativeDataTypeCode );
        if( typeMapping != null )
            return typeMapping.getNativeType();
        return "Non-defined"; 
    }

}
