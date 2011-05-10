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
package it.eng.spagobi.studio.core.properties;

import it.eng.spagobi.studio.core.util.ImageConstants;
import it.eng.spagobi.studio.utils.util.ResourceNavigatorHandler;

import org.eclipse.core.internal.resources.Container;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpagoBIPropertyPage extends org.eclipse.ui.dialogs.PropertyPage implements
IWorkbenchPropertyPage {

	private static Logger logger = LoggerFactory.getLogger(SpagoBIPropertyPage.class);

	private ProgressMonitorPart monitor;

	IPropertyPageSettings pageSettings;



	public SpagoBIPropertyPage() {
		super();
	}



	@Override
	protected Control createContents(Composite parent) {
		logger.debug("IN");
		IAdaptable adap = this.getElement();
		IFile file = (IFile) this.getElement();

		// check wich file it is
		String contextSelection = ResourceNavigatorHandler.getStateOfSelectedFile(file);
		logger.debug("context selection is "+contextSelection);
		// set particular page settings
		if(contextSelection.equalsIgnoreCase(ResourceNavigatorHandler.FILE_ANALYSIS_HIER))
			pageSettings = new DocumentPropertyPageSettings(file);
		else if(contextSelection.equalsIgnoreCase(ResourceNavigatorHandler.FILE_SERVER_HIER))
			pageSettings = new ServerPropertyPageSettings(file);
		else if(contextSelection.equalsIgnoreCase(ResourceNavigatorHandler.FILE_MODEL_HIER))
			pageSettings = new ModelPropertyPageSettings(file);
		else return null;


		setTitle("SpagoBI Metadata");
		setDescription(pageSettings.getDescription());
		setImageDescriptor(ImageConstants.metadataDescriptor);

		Composite container = new Composite(parent, SWT.NULL);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = true;
		rowLayout.justify = false;
		rowLayout.type = SWT.VERTICAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 0;
		rowLayout.fill = true;
		container.setLayout(rowLayout);

		// hide default buttons
		this.noDefaultAndApplyButton();
		Control containerToReturn = pageSettings.createContents(container);
		logger.debug("OUT");
		return containerToReturn;
	}





	public boolean performOk() {
		return super.performOk();
	}

}
