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
package it.eng.spagobi.server.services.api;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 *
 */
public class SpagoBIServerAPI {
	// This is the ID from your extension point
	private static final String DATASET_EP_ID = "it.eng.spagobi.server.services.api.dataset";

	public static SpagoBIServerAPI INSTANCE = new SpagoBIServerAPI();
	
	ISpagoBIServerDatasetServiceProxy cachedProxy = null;

	public ISpagoBIServerDatasetServiceProxy getDatsetServiceProxy() {
		if(cachedProxy != null) return cachedProxy;
		
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(DATASET_EP_ID);
		try {
			for (IConfigurationElement e : config) {
				System.out.println("Evaluating extension");
				final Object o = e.createExecutableExtension("class");
				if (o instanceof ISpagoBIServerDatasetServiceProxy) {
					cachedProxy = ((ISpagoBIServerDatasetServiceProxy) o); // return the first
				}
			}
			
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
		
		return cachedProxy;
	}
}
