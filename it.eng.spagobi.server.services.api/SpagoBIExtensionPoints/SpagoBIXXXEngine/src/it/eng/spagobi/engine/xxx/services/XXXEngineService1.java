/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2009 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.engine.xxx.services;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import it.eng.spagobi.utilities.engines.AbstractEngineStartServlet;
import it.eng.spagobi.utilities.engines.EngineStartServletIOManager;
import it.eng.spagobi.utilities.engines.SpagoBIEngineException;
import it.eng.spagobi.utilities.exceptions.SpagoBIServiceException;



/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 */
public class XXXEngineService1 extends AbstractEngineStartServlet {
	
	private static final String ENGINE_NAME = "XXXEngine";
	private static final String REQUEST_DISPATCHER_URL = "/WEB-INF/jsp/result.jsp";
	
	public void doService( EngineStartServletIOManager servletIOManager ) throws SpagoBIEngineException {
	
		RequestDispatcher requestDispatcher;
		
		// dispatch the request to the presentation layer
     	requestDispatcher = getServletContext().getRequestDispatcher( REQUEST_DISPATCHER_URL );
        try {
         	requestDispatcher.forward(servletIOManager.getRequest(), servletIOManager.getResponse());
 		} catch (Throwable t) {
 			throw new SpagoBIServiceException(ENGINE_NAME, "An error occurred while dispatching request to [" + REQUEST_DISPATCHER_URL + "]", t);
 		} 

	}

}
