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

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.engines.bo.SDKEngine;
import it.eng.spagobi.sdk.proxy.DataSetsSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DataSourcesSDKServiceProxy;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.EnginesServiceProxy;
import it.eng.spagobi.studio.core.exceptions.NoDocumentException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.core.util.BiObjectUtilities;
import it.eng.spagobi.studio.core.util.SDKDocumentParameters;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class PropertyPage extends org.eclipse.ui.dialogs.PropertyPage implements
IWorkbenchPropertyPage {

	public static QualifiedName DOCUMENT_ID = new QualifiedName("it.eng.spagobi.sdk.document.id", "Identifier");
	public static QualifiedName DOCUMENT_LABEL = new QualifiedName("it.eng.spagobi.sdk.document.label", "Label");
	public static QualifiedName DOCUMENT_NAME = new QualifiedName("it.eng.spagobi.sdk.document.name", "Name");
	public static QualifiedName DOCUMENT_DESCRIPTION = new QualifiedName("it.eng.spagobi.sdk.document.description", "Description");
	public static QualifiedName DOCUMENT_TYPE = new QualifiedName("it.eng.spagobi.sdk.document.type", "Type");
	public static QualifiedName DOCUMENT_STATE = new QualifiedName("it.eng.spagobi.sdk.document.description", "State");
	public static QualifiedName DOCUMENT_PARAMETERS_XML = new QualifiedName("it.eng.spagobi.sdk.document.parametersxml", "Parameters");

	public static QualifiedName DATASET_ID = new QualifiedName("it.eng.spagobi.sdk.dataset.id", "Identifier");
	public static QualifiedName DATASET_LABEL = new QualifiedName("it.eng.spagobi.sdk.dataset.label", "Label");
	public static QualifiedName DATASET_NAME = new QualifiedName("it.eng.spagobi.sdk.dataset.name", "Name");
	public static QualifiedName DATASET_DESCRIPTION = new QualifiedName("it.eng.spagobi.sdk.dataset.description", "Description");

	public static QualifiedName DATA_SOURCE_ID = new QualifiedName("it.eng.spagobi.sdk.datasource.id", "Identifier");
	public static QualifiedName DATA_SOURCE_NAME = new QualifiedName("it.eng.spagobi.sdk.datasource.name", "Name");
	public static QualifiedName DATA_SOURCE_LABEL = new QualifiedName("it.eng.spagobi.sdk.datasource.label", "Label");
	public static QualifiedName DATA_SOURCE_DESCRIPTION = new QualifiedName("it.eng.spagobi.sdk.datasource.description", "Description");

	public static QualifiedName ENGINE_ID = new QualifiedName("it.eng.spagobi.sdk.engine.id", "Identifier");
	public static QualifiedName ENGINE_LABEL = new QualifiedName("it.eng.spagobi.sdk.engine.label", "Label");
	public static QualifiedName ENGINE_NAME = new QualifiedName("it.eng.spagobi.sdk.engine.name", "Name");
	public static QualifiedName ENGINE_DESCRIPTION = new QualifiedName("it.eng.spagobi.sdk.engine.description", "Description");

	public static QualifiedName LAST_REFRESH_DATE = new QualifiedName("last_refresh_date", "Last Refresh Date");
	public static QualifiedName MADE_WITH_STUDIO = new QualifiedName("made_with_studio", "Created With SpagoBi Studio");

	private ProgressMonitorPart monitor;
	public final NoDocumentException noDocumentException=new NoDocumentException();

	Integer documentId;
	Group docGroup = null;
	Group dataSetGroup = null;
	Group dataSourceGroup = null;
	Group engineGroup = null;
	Group parametersGroup = null;

	Label documentIdValue=null;
	Label documentLabelValue=null;
	Label documentNameValue=null;
	Label documentDescriptionValue=null;
	Label documentTypeValue=null;
	Label documentStateValue=null;

	Label engineIdValue=null;
	Label engineLabelValue=null;
	Label engineNameValue=null;
	Label engineDescriptionValue=null;

	Label datasetIdValue=null;
	Label datasetLabelValue=null;
	Label datasetNameValue=null;
	Label datasetDescriptionValue=null;

	Label dataSourceIdValue=null;
	Label dataSourceLabelValue=null;
	Label dataSourceNameValue=null;
	Label dataSourceDescriptionValue=null;
	Label lastRefreshDateLabel=null;

	Table parametersTable=null;


	Composite container=null;

	public PropertyPage() {
		super();
	}


	@Override
	protected Control createContents(Composite parent) {
		// hide default buttons
		this.noDefaultAndApplyButton();
		monitor=new ProgressMonitorPart(getShell(), null);

		// if it's not a file don't draw
		if (!(this.getElement() instanceof IFile)){
			return null;
		}
		
		IFile file = (IFile) this.getElement();
		String documentIdS=null;
		try {
			documentIdS=file.getPersistentProperty(DOCUMENT_ID);
		} catch (CoreException e2) {
			SpagoBILogger.errorLog("Error in retrieving Id", e2);
		}
		if(documentIdS!=null)
			documentId=Integer.valueOf(documentIdS);
		else
			documentId=null;


		container = new Composite(parent, SWT.NULL);
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

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;

		docGroup = new Group(container, SWT.NULL);
		docGroup.setText("Document's information:");
		docGroup.setLayout(new FillLayout());
		Composite docContainer = new Composite(docGroup, SWT.NULL);
		docContainer.setLayout(layout);

		dataSetGroup = new Group(container, SWT.NULL);
		dataSetGroup.setText("Dataset's information:");
		dataSetGroup.setLayout(new FillLayout());
		Composite datasetContainer = new Composite(dataSetGroup, SWT.NULL);
		datasetContainer.setLayout(layout);

		dataSourceGroup = new Group(container, SWT.NULL);
		dataSourceGroup.setText("Datasource's information:");
		dataSourceGroup.setLayout(new FillLayout());
		Composite datasourceContainer = new Composite(dataSourceGroup, SWT.NULL);
		datasourceContainer.setLayout(layout);

		engineGroup = new Group(container, SWT.NULL);
		engineGroup.setText("Engine's information:");
		engineGroup.setLayout(new FillLayout());
		Composite engineContainer = new Composite(engineGroup, SWT.NULL);
		engineContainer.setLayout(layout);

		parametersGroup=new Group(container, SWT.NULL);
		parametersGroup.setText("Parameters's information:");
		parametersGroup.setLayout(new FillLayout());
		Composite parametersContainer = new Composite(parametersGroup, SWT.NULL);
		parametersContainer.setLayout(layout);

		new Label(docContainer, SWT.NULL).setText(DOCUMENT_ID.getLocalName());
		documentIdValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(DOCUMENT_LABEL.getLocalName());
		documentLabelValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(DOCUMENT_NAME.getLocalName());
		documentNameValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(DOCUMENT_DESCRIPTION.getLocalName());
		documentDescriptionValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(DOCUMENT_TYPE.getLocalName());
		documentTypeValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(DOCUMENT_STATE.getLocalName());
		documentStateValue=new Label(docContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(ENGINE_ID.getLocalName());
		engineIdValue=new Label(engineContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(ENGINE_LABEL.getLocalName());
		engineLabelValue=new Label(engineContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(ENGINE_NAME.getLocalName());
		engineNameValue=new Label(engineContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(ENGINE_DESCRIPTION.getLocalName());
		engineDescriptionValue=new Label(engineContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(DATASET_ID.getLocalName());
		datasetIdValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(DATASET_LABEL.getLocalName());
		datasetLabelValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(DATASET_NAME.getLocalName());
		datasetNameValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(DATASET_DESCRIPTION.getLocalName());
		datasetDescriptionValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(DATA_SOURCE_ID.getLocalName());
		dataSourceIdValue=new Label(datasourceContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(DATA_SOURCE_LABEL.getLocalName());
		dataSourceLabelValue=new Label(datasourceContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(DATA_SOURCE_NAME.getLocalName());
		dataSourceNameValue=new Label(datasourceContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(DATA_SOURCE_DESCRIPTION.getLocalName());
		dataSourceDescriptionValue=new Label(datasourceContainer, SWT.NULL);


		//		addPropertyContent(docContainer, DOCUMENT_ID);
		//		addPropertyContent(docContainer, DOCUMENT_LABEL);
		//		addPropertyContent(docContainer, DOCUMENT_NAME);
		//		addPropertyContent(docContainer, DOCUMENT_DESCRIPTION);
		//		addPropertyContent(docContainer, DOCUMENT_TYPE);
		//		addPropertyContent(docContainer, DOCUMENT_STATE);
		//		addPropertyContent(datasetContainer, DATASET_ID);
		//		addPropertyContent(datasetContainer, DATASET_LABEL);
		//		addPropertyContent(datasetContainer, DATASET_NAME);
		//		addPropertyContent(datasetContainer, DATASET_DESCRIPTION);
		//		addPropertyContent(engineContainer, ENGINE_ID);
		//		addPropertyContent(engineContainer, ENGINE_LABEL);
		//		addPropertyContent(engineContainer, ENGINE_NAME);
		//		addPropertyContent(engineContainer, ENGINE_DESCRIPTION);
		//		addPropertyContent(datasourceContainer, DATA_SOURCE_ID);
		//		addPropertyContent(datasourceContainer, DATA_SOURCE_NAME);

		parametersTable = new Table (parametersContainer, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		parametersTable.setLinesVisible (true);
		parametersTable.setHeaderVisible (true);
		String[] titles = {"Parameter Name","Parameter Url"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (parametersTable, SWT.NONE);
			column.setText (titles [i]);
		}
		for (int i=0; i<titles.length; i++) {
			parametersTable.getColumn (i).pack ();
		}			

		Button buttonRefresh=new Button(container, SWT.PUSH);
		buttonRefresh.setText("Refresh Metadata");

		new Label(container,SWT.NULL).setText(LAST_REFRESH_DATE.getLocalName());
		lastRefreshDateLabel=new Label(container,SWT.NULL);

		try{
			fillValues();
		}
		catch (Exception e) {
			MessageDialog.openError(container.getShell(), "Error", "Error while retrieving metadata informations");
			SpagoBILogger.errorLog("Error in retrieving metadata", e);
		}


		Listener refreshListener = new Listener() {
			public void handleEvent(Event event) {

				if(documentId==null){
					SpagoBILogger.errorLog("Cannot retrieve metadata cause no document is associated",null);
					MessageDialog.openWarning(getShell(), "Warning", "No document is associated");
				}
				else{

					IRunnableWithProgress op = new IRunnableWithProgress() {			
						public void run(IProgressMonitor monitor) throws InvocationTargetException {
							monitor.beginTask("Refreshing metadata", IProgressMonitor.UNKNOWN);
							try {
								refreshMetadata();
							} catch (Exception e) {
								MessageDialog.openError(getShell(), "Exception", "Exception");
							}
						}			
					};
					ProgressMonitorDialog dialog=new ProgressMonitorDialog(getShell());		
					try {
						dialog.run(true, true, op);
					} catch (InvocationTargetException e1) {
						SpagoBILogger.errorLog("No comunication with SpagoBI server: could not refresh metadata", e1);
						dialog.close();
						MessageDialog.openError(getShell(), "Error", "No comunication with server: Could not refresh metadata");	
						return;
					} catch (InterruptedException e1) {
						SpagoBILogger.errorLog("No comunication with SpagoBI server: could not refresh metadata", e1);
						dialog.close();
						MessageDialog.openError(getShell(), "Error", "No comunication with server: Could not refresh metadata");	
						return;	
					}	
					
					dialog.close();

					if(noDocumentException.isNoDocument()){
						SpagoBILogger.errorLog("Document no more present", null);			
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error refresh", "Document is no more present on server");	
						return;
					}
					
					try{
						fillValues();
					}
					catch (Exception e) {
						MessageDialog.openError(container.getShell(), "Error", "Error while retrieving metadata informations from file");
						SpagoBILogger.errorLog("Error in retrieving metadata informations from file", e);
						return;
					}

					MessageDialog.openInformation(container.getShell(), "Information", "Metadata refreshed");
				}
			}
		};

		buttonRefresh.addListener(SWT.Selection, refreshListener);



		return container;
	}

	public void fillValues() throws CoreException{
		IFile file = (IFile) this.getElement();
		String documentId = file.getPersistentProperty(DOCUMENT_ID);
		if(documentId==null) documentId="EMPTY";
		String documentLabel = file.getPersistentProperty(DOCUMENT_LABEL);
		if(documentLabel==null) documentLabel="EMPTY";
		String documentName = file.getPersistentProperty(DOCUMENT_NAME);
		if(documentName==null) documentName="EMPTY";
		String documentDescription = file.getPersistentProperty(DOCUMENT_DESCRIPTION);
		if(documentDescription==null) documentDescription="EMPTY";
		String documentType = file.getPersistentProperty(DOCUMENT_TYPE);
		if(documentType==null) documentType="EMPTY";
		String documentState = file.getPersistentProperty(DOCUMENT_STATE);
		if(documentState==null) documentState="EMPTY";

		String engineId = file.getPersistentProperty(ENGINE_ID);
		if(engineId==null) engineId="EMPTY";
		String engineLabel = file.getPersistentProperty(ENGINE_LABEL);
		if(engineLabel==null) engineLabel="EMPTY";
		String engineName = file.getPersistentProperty(ENGINE_NAME);
		if(engineName==null) engineName="EMPTY";
		String engineDescription = file.getPersistentProperty(ENGINE_DESCRIPTION);
		if(engineDescription==null) engineDescription="EMPTY";

		String datasourceId = file.getPersistentProperty(DATA_SOURCE_ID);
		if(datasourceId==null) datasourceId="EMPTY";
		String datasourceLabel = file.getPersistentProperty(DATA_SOURCE_LABEL);
		if(datasourceLabel==null) datasourceLabel="EMPTY";
		String datasourceName = file.getPersistentProperty(DATA_SOURCE_NAME);
		if(datasourceName==null) datasourceName="EMPTY";
		String datasourceDescription = file.getPersistentProperty(DATA_SOURCE_DESCRIPTION);
		if(datasourceDescription==null) datasourceDescription="EMPTY";

		String datasetId = file.getPersistentProperty(DATASET_ID);
		if(datasetId==null) datasetId="EMPTY";
		String datasetLabel = file.getPersistentProperty(DATASET_LABEL);
		if(datasetLabel==null) datasetLabel="EMPTY";
		String datasetName = file.getPersistentProperty(DATASET_NAME);
		if(datasetName==null) datasetName="EMPTY";
		String datasetDescription = file.getPersistentProperty(DATASET_DESCRIPTION);
		if(datasetDescription==null) datasetDescription="EMPTY";

		String xmlParameters = file.getPersistentProperty(DOCUMENT_PARAMETERS_XML);
		if(datasetDescription==null) datasetDescription="EMPTY";
		List<SDKDocumentParameter> list=null;
		if(xmlParameters!=null && !xmlParameters.equalsIgnoreCase(""))
		{
			XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("grfthscv", "_");
			XStream xstream = new XStream(new DomDriver("UTF-8", replacer)); 
			xstream.alias("SDK_DOCUMENT_PARAMETERS", SDKDocumentParameters.class);
			xstream.alias("PARAMETER", SDKDocumentParameter.class);
			xstream.useAttributeFor(SDKDocumentParameter.class, "id");
			xstream.useAttributeFor(SDKDocumentParameter.class, "label");
			xstream.useAttributeFor(SDKDocumentParameter.class, "type");
			xstream.useAttributeFor(SDKDocumentParameter.class, "urlName");
			xstream.omitField(SDKDocumentParameter.class, "values");		
			xstream.omitField(SDKDocumentParameter.class, "constraints");
			xstream.omitField(SDKDocumentParameter.class, "__hashCodeCalc");
			SDKDocumentParameters parametersMetaDataObject= (SDKDocumentParameters)xstream.fromXML(xmlParameters);
			list=parametersMetaDataObject.getContent();

		}


		String date=file.getPersistentProperty(LAST_REFRESH_DATE);
		lastRefreshDateLabel.setText(date!=null ? date : "");

		documentIdValue.setText(documentId);
		documentLabelValue.setText(documentLabel);
		documentNameValue.setText(documentName);
		documentDescriptionValue.setText(documentDescription);
		documentTypeValue.setText(documentType);
		documentStateValue.setText(documentState);
		engineIdValue.setText(engineId);
		engineLabelValue.setText(engineLabel);
		engineNameValue.setText(engineName);
		engineDescriptionValue.setText(engineDescription);
		dataSourceIdValue.setText(datasourceId);
		dataSourceLabelValue.setText(datasourceLabel);
		dataSourceNameValue.setText(datasourceName);
		dataSourceDescriptionValue.setText(datasourceDescription);
		datasetIdValue.setText(datasetId);
		datasetLabelValue.setText(datasetLabel);
		datasetNameValue.setText(datasetName);
		datasetDescriptionValue.setText(datasetDescription);
		parametersTable.removeAll();
		if(list!=null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SDKDocumentParameter documentParameter = (SDKDocumentParameter) iterator.next();
				TableItem item = new TableItem (parametersTable, SWT.NONE);
				item.setText(0, documentParameter.getLabel()!=null ? documentParameter.getLabel() : "");
				item.setText(1, documentParameter.getUrlName()!=null ? documentParameter.getUrlName() : "");
			}

		}


		docGroup.redraw();
		if(engineGroup!=null){
			engineGroup.redraw();
		}
		dataSetGroup.redraw();
		dataSourceGroup.redraw();

		container.layout();
		container.redraw();




	}


	private void addPropertyContent(Composite composite, QualifiedName qn) {
		Label label = new Label(composite, SWT.NULL);
		label.setText(qn.getLocalName());
		Label value = new Label(composite, SWT.NULL);
		value.setText(getProperty(qn));
	}

	protected String getProperty(QualifiedName qn) {
		IFile file = (IFile) this.getElement();
		try {
			String value = file.getPersistentProperty(qn);
			if (value == null)
				return "EMPTY";
			return value;
		} catch (CoreException e) {
			SpagoBILogger.errorLog("Error while recovering property " + qn.getLocalName(), e);
			return "Error while recovering property " + qn.getLocalName();
		}
	}


	protected void refreshMetadata() throws Exception{
		IFile file = (IFile) this.getElement();
		String documentId=null;

		// Recover document
		SDKDocument document=null;
		try{
			documentId=file.getPersistentProperty(DOCUMENT_ID);

			SDKProxyFactory proxyFactory=new SDKProxyFactory();
			DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy();
			document=docServiceProxy.getDocumentById(Integer.valueOf(documentId));
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Could not recover document Id",e);		
			throw e;	
		}

		if(document==null){
				noDocumentException.setNoDocument(true);
				return;
		}
		
		// Recover DataSource

		Integer dataSourceId=document.getDataSourceId();
		SDKDataSource dataSource=null;
		if(dataSourceId!=null){
			try{
				SDKProxyFactory proxyFactory=new SDKProxyFactory();
				DataSourcesSDKServiceProxy dataSourceServiceProxy=proxyFactory.getDataSourcesSDKServiceProxy();
				dataSource=dataSourceServiceProxy.getDataSource(Integer.valueOf(dataSourceId));
			}
			catch (Exception e) {
				SpagoBILogger.warningLog("Could not recover data source",e);		
			}
		}


		// Recover DataSet

		Integer dataSetId=document.getDataSetId();
		SDKDataSet dataSet=null;
		if(dataSetId!=null){
			try{
				SDKProxyFactory proxyFactory=new SDKProxyFactory();
				DataSetsSDKServiceProxy dataSetServiceProxy=proxyFactory.getDataSetsSDKServiceProxy();
				dataSet=dataSetServiceProxy.getDataSet(Integer.valueOf(dataSetId));
			}
			catch (Exception e) {
				SpagoBILogger.warningLog("Could not recover data set",e);		
			}
		}


		// Recover Engine

		Integer engineId=document.getEngineId();
		SDKEngine engine=null;
		if(engineId!=null){
			try{
				SDKProxyFactory proxyFactory=new SDKProxyFactory();
				EnginesServiceProxy engineServiceProxy=proxyFactory.getEnginesServiceProxy();
				engine=engineServiceProxy.getEngine(Integer.valueOf(engineId));
			}
			catch (Exception e) {
				SpagoBILogger.warningLog("Could not recover engine",e);		
			}
		}

		String[] roles=null;
		try{
			SDKProxyFactory proxyFactory=new SDKProxyFactory();
			DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy(); 		
			roles=docServiceProxy.getCorrectRolesForExecution(document.getId());
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve roles for execution", e);
		}			
		if(roles==null || roles.length==0){
			SpagoBILogger.errorLog("No roles for execution found",null);
		}


		SDKDocumentParameter[] parameters=null;
		try{
			SDKProxyFactory proxyFactory=new SDKProxyFactory();
			DocumentsServiceProxy docServiceProxy=proxyFactory.getDocumentsServiceProxy(); 		
			parameters=docServiceProxy.getDocumentParameters(document.getId(), roles[0]);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("No comunication with SpagoBI server, could not retrieve document parameters", e);
		}			

		// firstly I have to call delete on all metadata in order to refresh!
		BiObjectUtilities.erasePersistentProperties(file);

		// Reload Documents Metadata
		if(document!=null){
			try{
				file.setPersistentProperty(PropertyPage.DOCUMENT_ID, document.getId().toString());
				file.setPersistentProperty(PropertyPage.DOCUMENT_LABEL, document.getLabel());
				file.setPersistentProperty(PropertyPage.DOCUMENT_NAME, document.getName()!=null ? document.getName() : "");
				file.setPersistentProperty(PropertyPage.DOCUMENT_DESCRIPTION, document.getDescription()!=null ? document.getDescription() : "");
				file.setPersistentProperty(PropertyPage.DOCUMENT_TYPE, document.getType()!=null ? document.getType() : "");
				file.setPersistentProperty(PropertyPage.DOCUMENT_STATE, document.getState()!=null ? document.getState() : "");
			}
			catch (Exception e) {
				SpagoBILogger.errorLog("Error while refreshing meta data",e);		
			}
		}
		// Reload Engine Metadata
		if(engine!=null){
			try{
				file.setPersistentProperty(PropertyPage.ENGINE_ID, engine.getId().toString());
				file.setPersistentProperty(PropertyPage.ENGINE_LABEL, engine.getLabel());
				file.setPersistentProperty(PropertyPage.ENGINE_NAME, engine.getName()!=null ? engine.getName() : "");
				file.setPersistentProperty(PropertyPage.ENGINE_DESCRIPTION, engine.getDescription()!=null ? engine.getDescription() : "");
			}
			catch (Exception e) {
				SpagoBILogger.errorLog("Error while refreshing engine meta data",e);		
			}
		}

		// Reload dataSet Metadata
		if(dataSet!=null){
			try{
				file.setPersistentProperty(PropertyPage.DATASET_ID, dataSet.getId().toString());
				file.setPersistentProperty(PropertyPage.DATASET_LABEL, dataSet.getLabel());
				file.setPersistentProperty(PropertyPage.DATASET_NAME, dataSet.getName()!=null ? dataSet.getName() : "");
				file.setPersistentProperty(PropertyPage.DATASET_DESCRIPTION, dataSet.getDescription()!=null ? dataSet.getDescription() : "");
			}
			catch (Exception e) {
				SpagoBILogger.errorLog("Error while refreshing dataset meta data",e);		
			}
		}
		// Reload dataSource Metadata
		if(dataSource!=null){
			try{
				file.setPersistentProperty(PropertyPage.DATA_SOURCE_ID, dataSource.getId().toString());
				file.setPersistentProperty(PropertyPage.DATA_SOURCE_LABEL, dataSource.getLabel()!=null ? dataSource.getLabel() : "");
				file.setPersistentProperty(PropertyPage.DATA_SOURCE_NAME, dataSource.getName()!=null ? dataSource.getName() : "");
				file.setPersistentProperty(PropertyPage.DATA_SOURCE_DESCRIPTION, dataSource.getDescr()!=null ? dataSource.getDescr() : "");
			}
			catch (Exception e) {
				SpagoBILogger.errorLog("Error while refreshing dataSouce meta data",e);		
			}
		}

		try{
			BiObjectUtilities.setFileParametersMetaData(file, parameters);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error in retrieving parameters metadata", e);
		}


		try{
			Date dateCurrent=new Date();
			String currentStr=dateCurrent.toString();
			file.setPersistentProperty(LAST_REFRESH_DATE, currentStr);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error while refreshing update date",e);		
		}

	}


	//	protected void setDocumentId(String documentId) {
	//		IFile file = (IFile) this.getElement();
	//		String value = documentId;
	//		if (value.equals("")) value = null;
	//		try {
	//			file.setPersistentProperty(DOCUMENT_ID_PROP_KEY, value);
	//		} catch (CoreException e) {}
	//	}

	public boolean performOk() {
		//		setDocumentId(textField.getText());
		return super.performOk();
	}

}
