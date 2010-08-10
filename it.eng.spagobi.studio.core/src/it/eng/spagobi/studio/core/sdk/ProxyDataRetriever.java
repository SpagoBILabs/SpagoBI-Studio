package it.eng.spagobi.studio.core.sdk;

import java.util.Iterator;

import it.eng.spagobi.sdk.proxy.AbstractSDKServiceProxy;
import it.eng.spagobi.tools.dataset.common.dataproxy.IDataProxy;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.ServiceTracker;

public class ProxyDataRetriever {

	ServiceTracker proxyTracker;



	//					System.setProperty("http.proxySet", "true");  
	//					System.setProperty("http.proxyHost", data.getHost());  
	public void initProxyData(AbstractSDKServiceProxy proxy, String URL){

		String protocol=getProtocol(URL);
		String host=getHost(URL);
		IProxyService proxyService=retrieveProxyService();

		boolean isProxyEnabled=proxyService.isProxiesEnabled();
		boolean isSystemProxyEnabled=proxyService.isSystemProxiesEnabled();

		if(proxyService==null)return;

		// Only if manual configuration is specified!
		if(isProxyEnabled==true && isSystemProxyEnabled==false){

			boolean excluded=isNonProxiedHosts(proxyService.getNonProxiedHosts(),host);

			if(excluded) return;


			// retrieve proxyData
			IProxyData data=retrieveProxyData(proxyService,protocol);	

			// search if there is a proxy for the url protocol
			if(data!=null){
				if (data.getHost() != null) {  
					proxy.setProxyHost(data.getHost());
				}
				proxy.setProxyPort(Integer.valueOf(data.getPort()).toString());
				if (data.getUserId() != null) {  
					proxy.setProxyUserId(data.getUserId());
				}				
				if (data.getPassword() != null) {  
					proxy.setProxyPassword(data.getPassword());
				}

			}				
		} // end proxyData==null
	} // end function


	public String getProtocol(String URL){
		String toReturn="";
		int index=URL.indexOf('/');
		if(index!=-1){
			toReturn=URL.substring(0,index-1);
		}
		return toReturn;
	}


	public String getHost(String URL){
		String toReturn="";
		int index=URL.indexOf("://");
		int indexEnd=URL.indexOf(":", index+3);

		toReturn=URL.substring(index+3,indexEnd);
		return toReturn;
	}


	public IProxyService retrieveProxyService(){
		Bundle bundle=Platform.getBundle("org.eclipse.ui.net");

		try {
			bundle.start();
		} catch (BundleException e) {
			e.printStackTrace();
		}

		proxyTracker = new ServiceTracker(bundle.getBundleContext(),
				IProxyService.class.getName(), null);
		proxyTracker.open();
		IProxyService proxyService = getProxyService();  
		return proxyService;		
	}



	public IProxyData retrieveProxyData(IProxyService proxyService, String protocol){


		IProxyData proxyDataForHost = proxyService.getProxyData(protocol);

		if(proxyDataForHost==null){
			proxyDataForHost=proxyService.getProxyData(protocol.toUpperCase());

		}
		return proxyDataForHost;

	}

	public boolean isNonProxiedHosts(String[] nonHosts, String host){
		boolean excluded=false;
		if(nonHosts==null) excluded=false;
		else{
			for (int i = 0; i < nonHosts.length && excluded==false; i++) {
				if(nonHosts[i].equals(host))excluded=true;				
			}
		}
		return excluded;

	}



	public IProxyService getProxyService() {  
		return (IProxyService) proxyTracker.getService();  
	}  


}
