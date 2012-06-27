/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.properties;

import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaQueryPropertyPageSettings implements IPropertyPageSettings {

	IFile fileSel = null;
	Composite container = null;
	private static Logger logger = LoggerFactory.getLogger(MetaQueryPropertyPageSettings.class);


	public String getDescription() {
		return "Model properties";
	}

	public MetaQueryPropertyPageSettings(IFile filSel) {
		super();
		this.fileSel = filSel;
	}

	public Control createContents(Composite contents) {
		logger.debug("IN");
		container = contents;

		String datasetId;
		String datasetLabel;
		String modelName;
		String modelFileName;
		try{
			logger.debug("file "+fileSel.getName());
			datasetId=fileSel.getPersistentProperty(SpagoBIStudioConstants.DATASET_ID);
			datasetLabel= fileSel.getPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL);
			
			modelName=fileSel.getPersistentProperty(SpagoBIStudioConstants.MODEL_NAME);
			modelFileName= fileSel.getPersistentProperty(SpagoBIStudioConstants.MODEL_FILE_NAME);
		}
		catch (Exception e) {
			logger.error("error in recovering metadata", e);	
			return null;
		}
		modelName = (modelName != null) ? modelName : "none"; 
		modelFileName = (modelFileName != null) ? modelFileName : "none"; 
		
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;

		Group docGroup = new Group(container, SWT.NULL);

		Composite model = new Composite(container, SWT.NULL);
		docGroup.setText("Metaquery's information:");
		docGroup.setLayout(new FillLayout());

		Composite docContainer = new Composite(docGroup, SWT.NULL);
		docContainer.setLayout(layout);

		new Label(docContainer, SWT.NULL).setText("Dataset Id: ");
		new Label(docContainer, SWT.NULL).setText(datasetId != null ? datasetId : "");

		new Label(docContainer, SWT.NULL).setText("Dataset Label: ");
		new Label(docContainer, SWT.NULL).setText(datasetLabel != null ? datasetLabel : "");
		
		new Label(docContainer, SWT.NULL).setText("Model's business name: ");
		new Label(docContainer, SWT.NULL).setText(modelName);

		new Label(docContainer, SWT.NULL).setText("Model file name: ");
		new Label(docContainer, SWT.NULL).setText(modelFileName);

		logger.debug("OUT");

		return container;
	}

	public String fillValues() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}


}
