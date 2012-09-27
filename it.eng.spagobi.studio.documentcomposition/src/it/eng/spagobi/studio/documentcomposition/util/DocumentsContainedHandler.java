/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.util;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.views.DocumentParametersView;
import it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView;
import it.eng.spagobi.studio.documentcomposition.views.NavigationView;
import it.eng.spagobi.studio.utils.exceptions.NoActiveServerException;
import it.eng.spagobi.studio.utils.exceptions.NoDocumentException;
import it.eng.spagobi.studio.utils.services.metadata.MetadataHandler;
import it.eng.spagobi.studio.utils.util.FileFinder;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentsContainedHandler {

	private static Logger logger = LoggerFactory.getLogger(DocumentsContainedHandler.class);

	/** called to refresh metatas of contained documetns
	 * 
	 * @param container
	 */
	public void refreshMetadataOfContainedDocuments(final Composite container){
		logger.debug("IN");
		final MetadataHandler metadataHandler = new MetadataHandler();
		final NoDocumentException noDocumentException=new NoDocumentException();
		final NoActiveServerException noActiveServerException=new NoActiveServerException();

/**
 *  Refresh of metadata of all documents contained in composite document
 */
		
		IRunnableWithProgress op = new IRunnableWithProgress() {			
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Refreshing ", IProgressMonitor.UNKNOWN);
				try {
					logger.debug("Monitor for metadata refresh of contained documents has started");
					DocumentComposition doccomp = Activator.getDefault().getDocumentComposition();
					Vector<Document> documents = doccomp.getDocumentsConfiguration().getDocuments();
					if(documents == null){
						logger.debug("No documents present, refresh finished");
						return;
					}
					String label = null;
					
					// run all contained documents
					for (Iterator iterator = documents.iterator(); iterator.hasNext();) {
						Document document = (Document) iterator.next();
						logger.debug("document eith label "+document.getSbiObjLabel());
						
						noDocumentException.setDocumentLabel(null);
						
						String localFileName = document.getLocalFileName();

						IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);					
						String projectName = ((DocumentCompositionEditor)editorPart).getDesigner().getProjectName();

						IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
						IPath workspacePath=root.getLocation();
						IProject project = root.getProject(projectName);
						IPath projectLocation=project.getLocation();

						// get file from workspace
						IPath pathRetrieved = FileFinder.retrieveFile(localFileName, projectLocation.toString(), workspacePath);
						if(pathRetrieved != null){
							logger.debug("search for file on "+pathRetrieved.toOSString());
							IFile fileToGet = ResourcesPlugin.getWorkspace().getRoot().getFile(pathRetrieved);
							if(fileToGet.exists()){
								logger.debug("file found "+fileToGet.getName());
								label = fileToGet.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_LABEL);
								noDocumentException.setDocumentLabel(label);

								metadataHandler.refreshMetadata(fileToGet, noDocumentException, noActiveServerException);
								// after refreshing there is to update the object
								logger.debug("update current object MetadataDocument");
								final MetadataDocument metadataDocument= refreshObject(document,fileToGet);
								// refresh also graphical view
								logger.debug("refresh graphic");
								if(metadataDocument != null){
									logger.debug("metadata object refreshed");


									new Thread(new Runnable() {
										public void run() {
												try { Thread.sleep(1000); } catch (Exception e) { }
												Display.getDefault().asyncExec(new Runnable() {
													public void run() {
														try{
															refreshGraphic(metadataDocument);
														}
														catch (CoreException e) {
															MessageDialog.openWarning(container.getShell(), "Warning", "COuld not refresh graphic properties: select any document to refresh it manually");
														}

													}
												});
										}
									}).start();


								}
								else{
									logger.error("metadata object could not be refreshed refreshed");
									throw noDocumentException;
								}

							}
						}
					}

					} catch (Exception e) {
						logger.error("Error in monitor retieving metadata ",e);
						MessageDialog.openError(container.getShell(), "Exception", "Exception");
					}
				}			
			};
			
			
						ProgressMonitorDialog dialog=new ProgressMonitorDialog(container.getShell());		
						try {
							dialog.run(true, true, op);
						} catch (InvocationTargetException e1) {
							logger.error("No comunication with SpagoBI server: could not refresh metadata", e1);
							dialog.close();
							MessageDialog.openError(container.getShell(), "Error", "No comunication with server: Could not refresh metadata");	
							return;
						} catch (InterruptedException e1) {
							logger.error("No comunication with SpagoBI server: could not refresh metadata", e1);
							dialog.close();
							MessageDialog.openError(container.getShell(), "Error", "No comunication with server: Could not refresh metadata");	
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
							logger.error("Document "+noDocumentException.getDocumentLabel() != null ? noDocumentException.getDocumentLabel() : " "+" not retrieved; check it is still on server and you have enough permission to reach it");			
							MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
									"Error", "Document "+(noDocumentException.getDocumentLabel() != null ? noDocumentException.getDocumentLabel() : " ") +" not retrieved; check it is still on server and you have enough permission to reach it");	
							return;
						}

						MessageDialog.openInformation(container.getShell(), "Information", "Metadata refreshed");
						logger.debug("OUT");				
	}

	/** refresh document with fresh Metadata
	 * 
	 * @param document
	 * @param file
	 */

	MetadataDocument refreshObject(Document document, IFile file) throws CoreException{
		logger.debug("IN");
		MetadataDocument metadataDocument = new MetadataDocument(file);		
		MetadataDocumentComposition metadataDocumentComposition = (new MetadataBO()).getMetadataDocumentComposition();
		boolean found = metadataDocumentComposition.substituteMetadataDocument(metadataDocument);
		logger.debug("	OUT");
		return metadataDocument;
	}		

	
	void refreshGraphic(MetadataDocument metadataDocument) throws CoreException{
		logger.debug("IN");
		IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		try{
			// document properties
			IViewPart object=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
			if(object!=null){
				DocumentPropertiesView view=(DocumentPropertiesView)object;
				view.reloadProperties(metadataDocument);
			}

			// Document parameters
			IViewPart object2=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PARAMETERS_VIEW_ID);
			if(object2!=null){
				DocumentParametersView view=(DocumentParametersView)object2;
				view.reloadParametersProperties(metadataDocument);
			}
			
			// finally set on navigation to avoid on eparticular document to be selected	
			IViewPart object3=DocCompUtilities.getViewReference(DocCompUtilities.NAVIGATION_VIEW_ID);
			if(object3!=null){
				NavigationView view=(NavigationView)object3;
				view.getClient().redraw();
			}
		}
		catch (Exception e) {
			logger.error("Reload Document Properties", e);
			e.printStackTrace();
		}
		logger.debug("OUT");
	}	
	
}

