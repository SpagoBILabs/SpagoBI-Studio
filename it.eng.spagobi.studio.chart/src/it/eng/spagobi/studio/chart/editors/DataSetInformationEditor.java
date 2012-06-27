/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors;


import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadataField;
import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadata;
import it.eng.spagobi.studio.utils.bo.DataStoreMetadataField;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.services.SpagoBIServerObjectsFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSetInformationEditor {

	private static Logger logger = LoggerFactory.getLogger(DataSetInformationEditor.class);
	Section sectionDatasetInformation=null;
	Composite sectionClientDatasetInformation=null;
	private Table datasetTable;
	Label noDataSet;
	public DataSetInformationEditor(final ChartModel model, FormToolkit toolkit, final ScrolledForm form, String projectname) {

		sectionDatasetInformation= toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		sectionClientDatasetInformation=toolkit.createComposite(sectionDatasetInformation);

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		sectionClientDatasetInformation.setLayoutData(td);
		sectionDatasetInformation.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionDatasetInformation.setText("Dataset Metadata (Read only)");
		//sectionDatasetInformation.setDescription("All the selected Dataset Metadata");


		GridLayout gridLayout=new GridLayout();
		gridLayout.numColumns=1;
		sectionClientDatasetInformation.setLayout(gridLayout);

		SpagoBIServerObjectsFactory sbso = null;
		try{
			sbso =new SpagoBIServerObjectsFactory(projectname);
		}
		catch (NoActiveServerException e) {
			logger.error("No Active server defined");
		}
		noDataSet=new org.eclipse.swt.widgets.Label(sectionClientDatasetInformation, SWT.NULL);

		if(sbso != null && model.getSdkDataSetId()!=null){
			IDataStoreMetadata dataStoreMetadata= null;
			try {
				dataStoreMetadata=sbso.getServerDatasets().getDataStoreMetadata(model.getSdkDataSetId());
				//				SDKProxyFactory proxyFactory = new SDKProxyFactory();
				//				DataSetsSDKServiceProxy datasetSDKServiceProxy = proxyFactory.getDataSetsSDKServiceProxy();
				//				SDKDataSet sdkDataSet = datasetSDKServiceProxy.getDataSet(model.getSdkDataSetId());
				//				if(sdkDataSet!=null){
				//					dataSetName=sdkDataSet.getName();
				//					sdkDataStoreMetadata=datasetSDKServiceProxy.getDataStoreMetadata(sdkDataSet);
				//					createDatasetTable(sectionClientDatasetInformation);
				//					fillDatasetTable(sdkDataStoreMetadata);
				//				}

				if(dataStoreMetadata!=null){
					createDatasetTable(sectionClientDatasetInformation);
					fillDatasetTable(dataStoreMetadata);
				}
				else{
					//MessageDialog.openError(sectionDatasetInformation.getShell(), "Error", "Could not retrieve metadata for dataset with ID "+model.getSdkDataSetId());
					noDataSet.setText("Could not retrieve dataset informations, check that: \n" +
							" - a dataset is associated to the document (you can associate during first deploy or directly via SpagoBI Server) \n" +
							" - communication with SpagoBIServer is avalaible \n" +
							" - document metadata are refreshed and referring to the right dataset (right click on resource => SpagoBI => Properties => Refresh metadata) \n" +
					" - the dataset on Server is rightly configured (in order to obtain its metadata you should have succesfully tested it at least once on server)");
				}
			} catch (Exception e) {
				//MessageDialog.openError(sectionDatasetInformation.getShell(), "Error", "Could not retrieve metadata for dataset with ID "+model.getSdkDataSetId());
				noDataSet.setText("Could not retrieve dataset informations, check that: \n" +
						" - communication with SpagoBIServer is avalaible \n" +
						" - document metadata are refreshed and referring to the right dataset (right click on resource => SpagoBI => Properties => Refresh metadata) \n" +
				" - the dataset on Server is rightly configured (in order to obtain its metadata you should have succesfully tested it at least once on server)");
			}

		}
		else{
			noDataSet.setText("No Dataset Associated to the opened document");
		}

		sectionDatasetInformation.setClient(sectionClientDatasetInformation);


	}



	private void createDatasetTable(final Composite sectionClient) {

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 1;

		datasetTable = new Table(sectionClient, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		datasetTable.setLayoutData(gd);
		datasetTable.setLinesVisible(true);
		datasetTable.setHeaderVisible(true);

		String[] titles = { "            Column name             ",
		"               Type               "};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(datasetTable, SWT.NONE);
			column.setText(titles[i]);
			// column.setResizable(true);
		}
		for (int i = 0; i < titles.length; i++) {
			datasetTable.getColumn(i).pack();
		}
		datasetTable.redraw();

	}


	private void fillDatasetTable(IDataStoreMetadata dataStoreMetadata) {
		// if dataset changed than new Metadata

		for (int i = 0; i < dataStoreMetadata.getFieldsMetadata().length; i++) {
			TableItem item = new TableItem(datasetTable, SWT.TRANSPARENT);
			IDataStoreMetadataField dsmf = dataStoreMetadata.getFieldsMetadata()[i];
			// find out the current column
			item.setText(0, dsmf.getName());
			item.setText(1, dsmf.getClassName());
		}

		datasetTable.pack();
		datasetTable.redraw();

	}
}
