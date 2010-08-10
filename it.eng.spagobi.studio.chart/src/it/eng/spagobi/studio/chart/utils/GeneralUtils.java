/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.studio.chart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class GeneralUtils {

	public static Shell getActiveWorkbenchShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
	
	public static void flushFromInputStreamToOutputStream(InputStream is, OutputStream os, 
			           boolean closeStreams) throws Exception  {
		try{	
			int c = 0;
			byte[] b = new byte[1024];
			while ((c = is.read(b)) != -1) {
				if (c == 1024)
					os.write(b);
				else
					os.write(b, 0, c);
			}
			os.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (closeStreams) {
				try {
					if (os != null) os.close();
					if (is != null) is.close();
				} catch (IOException e) {
					throw e;
				}
				
			}
		}
	}
}
