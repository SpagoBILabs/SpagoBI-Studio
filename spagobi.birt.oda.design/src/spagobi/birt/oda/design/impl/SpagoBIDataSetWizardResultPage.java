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
package spagobi.birt.oda.design.impl;


import org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class SpagoBIDataSetWizardResultPage extends DataSetWizardPage {
    private static String DEFAULT_MESSAGE = "Preview of SpagoBI dataset results";
    
	public SpagoBIDataSetWizardResultPage( String pageName )
	{
        super( pageName );
        setTitle( pageName );
        setMessage( DEFAULT_MESSAGE );
	}
	
	public SpagoBIDataSetWizardResultPage( String pageName, String title, ImageDescriptor titleImage )
	{
        super( pageName, title, titleImage );
        setMessage( DEFAULT_MESSAGE );
	}

	@Override
	public void createPageCustomControl(Composite parent) {
	      Composite composite = createResultsComponents(parent);
	      setPageComplete( true );
	      //return composite;
	}

	private Composite createResultsComponents(Composite parent){		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(container, SWT.NONE);
		FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
		fl_composite.marginWidth = 2;
		fl_composite.marginHeight = 2;
		composite.setLayout(fl_composite);

		Group groupQueryResult = new Group(composite, SWT.NONE);
		groupQueryResult.setText("Query Result");
		GridLayout gl_groupQueryResult = new GridLayout(1, false);
		gl_groupQueryResult.marginRight = 1;
		gl_groupQueryResult.marginTop = 1;
		gl_groupQueryResult.marginLeft = 1;
		gl_groupQueryResult.marginBottom = 1;
		groupQueryResult.setLayout(gl_groupQueryResult);

		//Create Table widget to host results
		//createResultsTableViewer(groupQueryResult);

		return container;
	}

}
