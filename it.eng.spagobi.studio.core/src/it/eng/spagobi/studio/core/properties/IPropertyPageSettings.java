/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public interface IPropertyPageSettings {


	/**
	 *  get window description
	 * @return
	 */
	public String getDescription();

	/**
	 *  Draw section
	 * @param contents
	 * @return
	 */
	
	public Control createContents(Composite contents);

	
	public String fillValues() throws CoreException;
}
