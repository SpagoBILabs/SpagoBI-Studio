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
import it.eng.spagobi.studio.core.bo.Server;
import it.eng.spagobi.studio.core.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.core.exceptions.NoDocumentException;
import it.eng.spagobi.studio.core.exceptions.NoServerException;
import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.sdk.SDKProxyFactory;
import it.eng.spagobi.studio.core.services.server.MetadataHandler;
import it.eng.spagobi.studio.core.services.server.ServerHandler;
import it.eng.spagobi.studio.core.util.BiObjectUtilities;
import it.eng.spagobi.studio.core.util.SDKDocumentParameters;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class PropertyPage extends org.eclipse.ui.dialogs.PropertyPage implements
IWorkbenchPropertyPage {

	private static Logger logger = LoggerFactory.getLogger(PropertyPage.class);


	private ProgressMonitorPart monitor;

	Integer documentId;
	Group docGroup = null;
	Group dataSetGroup = null;
	Group dataSourceGroup = null;
	Group engineGroup = null;
	Group parametersGroup = null;


	Label prevServerLabel=null;
	Label activeServerLabel=null;

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
		setTitle("SpagoBI Metadata");
		setDescription("SpagoBI Metadata");
		setImageDescriptor(SpagoBIStudioConstants.metadataDescriptor);
		
	
		
		
		// hide default buttons
		this.noDefaultAndApplyButton();
		monitor=new ProgressMonitorPart(getShell(), null);

		IFile file = (IFile) this.getElement();
		String documentIdS=null;
		try {
			documentIdS=file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);
		} catch (CoreException e2) {
			logger.error("Error in retrieving Id", e2);
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

		Composite servers = new Composite(container, SWT.NULL);
		servers.setLayout(layout);

		Label titleLabel = new Label(servers, SWT.NULL);
		titleLabel.setForeground(new Color(servers.getDisplay(), SpagoBIStudioConstants.BLUE));
		titleLabel.setText("Current active server ");
		activeServerLabel = new Label(servers, SWT.NULL);	

		titleLabel = new Label(servers, SWT.NULL);
		titleLabel.setForeground(new Color(servers.getDisplay(), SpagoBIStudioConstants.BLUE));
		titleLabel.setText("Last server deployd: ");
		prevServerLabel = new Label(servers, SWT.NULL);	

		Button buttonRefresh=new Button(servers, SWT.PUSH);
		buttonRefresh.setText("Refresh Metadata on active server");

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

		new Label(docContainer, SWT.NULL).setText(SpagoBIStudioConstants.DOCUMENT_ID.getLocalName());
		documentIdValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(SpagoBIStudioConstants.DOCUMENT_LABEL.getLocalName());
		documentLabelValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(SpagoBIStudioConstants.DOCUMENT_NAME.getLocalName());
		documentNameValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(SpagoBIStudioConstants.DOCUMENT_DESCRIPTION.getLocalName());
		documentDescriptionValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(SpagoBIStudioConstants.DOCUMENT_TYPE.getLocalName());
		documentTypeValue=new Label(docContainer, SWT.NULL);
		new Label(docContainer, SWT.NULL).setText(SpagoBIStudioConstants.DOCUMENT_STATE.getLocalName());
		documentStateValue=new Label(docContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(SpagoBIStudioConstants.ENGINE_ID.getLocalName());
		engineIdValue=new Label(engineContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(SpagoBIStudioConstants.ENGINE_LABEL.getLocalName());
		engineLabelValue=new Label(engineContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(SpagoBIStudioConstants.ENGINE_NAME.getLocalName());
		engineNameValue=new Label(engineContainer, SWT.NULL);
		new Label(engineContainer, SWT.NULL).setText(SpagoBIStudioConstants.ENGINE_DESCRIPTION.getLocalName());
		engineDescriptionValue=new Label(engineContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATASET_ID.getLocalName());
		datasetIdValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATASET_LABEL.getLocalName());
		datasetLabelValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATASET_NAME.getLocalName());
		datasetNameValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasetContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATASET_DESCRIPTION.getLocalName());
		datasetDescriptionValue=new Label(datasetContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATA_SOURCE_ID.getLocalName());
		dataSourceIdValue=new Label(datasourceContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATA_SOURCE_LABEL.getLocalName());
		dataSourceLabelValue=new Label(datasourceContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATA_SOURCE_NAME.getLocalName());
		dataSourceNameValue=new Label(datasourceContainer, SWT.NULL);
		new Label(datasourceContainer, SWT.NULL).setText(SpagoBIStudioConstants.DATA_SOURCE_DESCRIPTION.getLocalName());
		dataSourceDescriptionValue=new Label(datasourceContainer, SWT.NULL);

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

		new Label(container,SWT.NULL).setText(SpagoBIStudioConstants.LAST_REFRESH_DATE.getLocalName());
		lastRefreshDateLabel=new Label(container,SWT.NULL);
		try{
			fillValues();
		}
		catch (Exception e) {
			MessageDialog.openError(container.getShell(), "Error", "Error while retrieving metadata informations");
			logger.error("Error in retrieving metadata", e);
		}

		// refresh button listener
		Listener refreshListener = new Listener() {
			public void handleEvent(Event event) {

				if(documentId==null){
					logger.error("Cannot retrieve metadata cause no document is associated");
					MessageDialog.openWarning(getShell(), "Warning", "No document is associated: cannot retrieve metadata");
				}
				else{
					final NoDocumentException noDocumentException=new NoDocumentException();
					final NoActiveServerException noActiveServerException=new NoActiveServerException();

					IRunnableWithProgress op = new IRunnableWithProgress() {			
						public void run(IProgressMonitor monitor) throws InvocationTargetException {
							monitor.beginTask("Refreshing ", IProgressMonitor.UNKNOWN);
							try {
								new MetadataHandler().refreshMetadata((IFile)getElement(), noDocumentException, noActiveServerException);
							} catch (Exception e) {
								logger.error("Error in monitor retieving metadata ",e);
								MessageDialog.openError(getShell(), "Exception", "Exception");
							}
						}			
					};
					ProgressMonitorDialog dialog=new ProgressMonitorDialog(getShell());		
					try {
						dialog.run(true, true, op);
					} catch (InvocationTargetException e1) {
						logger.error("No comunication with SpagoBI server: could not refresh metadata", e1);
						dialog.close();
						MessageDialog.openError(getShell(), "Error", "No comunication with server: Could not refresh metadata");	
						return;
					} catch (InterruptedException e1) {
						logger.error("No comunication with SpagoBI server: could not refresh metadata", e1);
						dialog.close();
						MessageDialog.openError(getShell(), "Error", "No comunication with server: Could not refresh metadata");	
						return;	
					}	

					dialog.close();

					if(noActiveServerException.isNoServer()){
						logger.error("No Server is defined active");			
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error refresh", "No Server is defined active");	
						return;
					}
					if(noDocumentException.isNoDocument()){
						logger.error("Document no more present");			
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								"Error refresh", "Document is no more present on server");	
						return;
					}

					try{
						// fill current values
						fillValues();
					}
					catch (Exception e) {
						MessageDialog.openError(container.getShell(), "Error", "Error while retrieving metadata informations from file");
						logger.error("Error in retrieving metadata informations from file", e);
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

		// get active server name, there could be more than one, in that case take the first one and advise user
		String activeName = "NONE";
		Vector<Server> names = new ServerHandler().getCurrentActiveServers(file.getProject().getName());
		if(names.size() == 0){
			logger.debug("Noa ctive server was found");
			activeName = "NONE";
		}
		else {
			activeName = names.get(0).getName();
			logger.debug("Active server "+activeName);
			if(names.size() > 1){
				MessageDialog.openWarning(container.getShell(), "Warning", "More than one server is set to active; for default will be taken "+activeName);
				logger.warn("More than one server is set to active; for default will be taken "+activeName);
			}
		}
		activeServerLabel.setText(activeName);

		String serverName = file.getPersistentProperty(SpagoBIStudioConstants.SERVER);
		if(serverName==null) serverName="NONE";
		prevServerLabel.setText(serverName);

		String documentId = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);
		if(documentId==null) documentId="EMPTY";
		String documentLabel = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL);
		if(documentLabel==null) documentLabel="None";
		setTitle("Document Label: "+documentLabel);

		String documentName = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_NAME);
		if(documentName==null) documentName="EMPTY";
		String documentDescription = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_DESCRIPTION);
		if(documentDescription==null) documentDescription="EMPTY";
		String documentType = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_TYPE);
		if(documentType==null) documentType="EMPTY";
		String documentState = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_STATE);
		if(documentState==null) documentState="EMPTY";

		String engineId = file.getPersistentProperty(SpagoBIStudioConstants.ENGINE_ID);
		if(engineId==null) engineId="EMPTY";
		String engineLabel = file.getPersistentProperty(SpagoBIStudioConstants.ENGINE_LABEL);
		if(engineLabel==null) engineLabel="EMPTY";
		String engineName = file.getPersistentProperty(SpagoBIStudioConstants.ENGINE_NAME);
		if(engineName==null) engineName="EMPTY";
		String engineDescription = file.getPersistentProperty(SpagoBIStudioConstants.ENGINE_DESCRIPTION);
		if(engineDescription==null) engineDescription="EMPTY";

		String datasourceId = file.getPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_ID);
		if(datasourceId==null) datasourceId="EMPTY";
		String datasourceLabel = file.getPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_LABEL);
		if(datasourceLabel==null) datasourceLabel="EMPTY";
		String datasourceName = file.getPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_NAME);
		if(datasourceName==null) datasourceName="EMPTY";
		String datasourceDescription = file.getPersistentProperty(SpagoBIStudioConstants.DATA_SOURCE_DESCRIPTION);
		if(datasourceDescription==null) datasourceDescription="EMPTY";

		String datasetId = file.getPersistentProperty(SpagoBIStudioConstants.DATASET_ID);
		if(datasetId==null) datasetId="EMPTY";
		String datasetLabel = file.getPersistentProperty(SpagoBIStudioConstants.DATASET_LABEL);
		if(datasetLabel==null) datasetLabel="EMPTY";
		String datasetName = file.getPersistentProperty(SpagoBIStudioConstants.DATASET_NAME);
		if(datasetName==null) datasetName="EMPTY";
		String datasetDescription = file.getPersistentProperty(SpagoBIStudioConstants.DATASET_DESCRIPTION);
		if(datasetDescription==null) datasetDescription="EMPTY";

		String xmlParameters = file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_PARAMETERS_XML);
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


		String date=file.getPersistentProperty(SpagoBIStudioConstants.LAST_REFRESH_DATE);
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
			logger.error("Error while recovering property " + qn.getLocalName(), e);
			return "Error while recovering property " + qn.getLocalName();
		}
	}

	
	public boolean performOk() {
		return super.performOk();
	}

}
