/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors;


import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataStyle;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;
import it.eng.spagobi.studio.documentcomposition.views.DocumentParametersView;
import it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView;
import it.eng.spagobi.studio.documentcomposition.views.NavigationView;
import it.eng.spagobi.studio.utils.util.FileFinder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Designer {

	private static long idCounter = 0;

	DocumentCompositionEditor editor=null;
	private static Logger logger = LoggerFactory.getLogger(Designer.class);

	public static synchronized String createID()
	{
		return String.valueOf(idCounter++);
	}

	String state=NORMAL;
	Integer currentSelection=-1;
	int currentX=0;
	int currentY=0;
	Composite mainComposite;
	HashMap<Integer, DocContainer> containers;

	String projectName=null;


	public static final String NORMAL="normal";
	public static final String SELECTION="selection";
	public static final String RESIZE="resize";
	public static final String DRAG="drag";
	public static final int MOUSE_LEFT_KEY=1;
	public static final int MOUSE_RIGHT_KEY=3;

	public static final int DESIGNER_WIDTH=800;
	public static final int DESIGNER_HEIGHT=600;

	/** 
	 * Adds a new Document Container in the designer
	 * 
	 * @param mainComposite
	 * @param x
	 * @param y
	 * @param _width
	 * @param _height
	 * @return
	 */

	public Integer addNewDocContainer(Composite mainComposite, int x, int y, int _width, int _height, boolean isNew){
		logger.debug("IN");

		// shell check if overlaids or exceeds
		int tempWidth=_width;
		//		tempWidth=tempWidth/DocContainer.ALIGNMENT_MARGIN;
		//		tempWidth=tempWidth*DocContainer.ALIGNMENT_MARGIN;
		int tempHeight=_height;
		//		tempHeight=tempHeight/DocContainer.ALIGNMENT_MARGIN;
		//		tempHeight=tempHeight*DocContainer.ALIGNMENT_MARGIN;

		Rectangle rectangle=new Rectangle(x,y, tempWidth, tempHeight);

		boolean doesExceed=DocContainer.doesExceed(Integer.valueOf(-1), this, x, y, tempWidth, tempHeight, false);
		boolean doesIntersect=DocContainer.doesIntersect(Integer.valueOf(-1), this, x, y, tempWidth, tempHeight, false);

		if(!isNew){
			doesExceed=false;
			doesIntersect=false;
		}
		if(doesExceed==true){
			MessageDialog.openWarning(mainComposite.getShell(), 
					"Warning", "Container you want to insert exceeds shell!");
			logger.warn(Designer.class.toString()+": add New Container function: Container exceed shell limits");
			return null;
		}
		if(doesIntersect==true){
			MessageDialog.openWarning(mainComposite.getShell(), 
					"Warning", "Container you want to insert intersects other composites!");
			logger.warn(Designer.class.toString()+": add New Container function: Container you want to insert intersects other composites");
			return null;
		}

		DocContainer group=new DocContainer(this, mainComposite, x, y, tempWidth, tempHeight);
		containers.put(group.getIdContainer(), group);
		Composite g=group.getDocumentContained().getGroup();
		mainComposite.layout();
		mainComposite.update();
		mainComposite.redraw();
		mainComposite.setFocus();
		logger.debug("OUT");
		return group.getIdContainer();
	}

	/** Add Container reading the template
	 * 
	 * @param mainComposite
	 * @param x
	 * @param y
	 * @param _width
	 * @param _height
	 * @param metadataDocument
	 *
	 * 
	 */

	public void addDocContainerFromTemplate(Composite mainComposite, int x, int y, int _width, int _height, MetadataDocument metadataDocument, Document document){
		logger.debug("IN");

		Integer id=addNewDocContainer(mainComposite, x, y, _width, _height, false);
		if(id==null) return;
		else{
			DocContainer docContainer=containers.get(id);
			docContainer.setTitle(metadataDocument!=null ? metadataDocument.getLabel() : "NoDocument");
			if(metadataDocument!=null){
				docContainer.getDocumentContained().setMetadataDocument(metadataDocument);
				metadataDocument.setIdMetadataDocument(id+"_"+metadataDocument.getLabel());
				document.setId(id+"_"+metadataDocument.getLabel());
			}
			docContainer.getDocumentContained().viewDocumentMetadata(metadataDocument);
		}
		logger.debug("OUT");


	}

	public Designer(Composite composite, DocumentCompositionEditor _editor) {
		super();
		logger.debug("IN");
		FormLayout layout=new FormLayout();
		//composite.setLayout(layout);
		mainComposite=composite;
		setMouseControls(mainComposite);
		addContextMenu(mainComposite);
		containers=new HashMap<Integer, DocContainer>();
		addShellMouseControls(mainComposite);
		this.editor=_editor;
		logger.debug("OUT");

	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}




	public Integer getCurrentSelection() {
		return currentSelection;
	}



	public void setCurrentSelection(Integer currentSelection) {
		this.currentSelection = currentSelection;
	}


	/** Track the mous eposition when clicking
	 * 
	 * @param shell
	 */
	public void setMouseControls(final Composite shell){
		shell.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent arg0) {
			}
			public void mouseDown(MouseEvent arg0) {
				if(arg0.button==MOUSE_LEFT_KEY){
					currentX=arg0.x;
					currentY=arg0.y;
				}
				else if(arg0.button==MOUSE_RIGHT_KEY){
					// memorize th eposition of the menu 
					currentX=arg0.x;
					currentY=arg0.y;
				}
			}

			public void mouseUp(MouseEvent arg0) {

			}

		});

	}


	/** Add control on mouse events won the shell
	 * 
	 * @param shell
	 */

	public void addShellMouseControls(final Composite shell){
		logger.debug("IN");
		final Point[] offset = new Point[1];
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MouseDown:
					/**  IF in resizing state mouse button on shell causes end resizing**/
					DocContainer selectedDoc=currentSelection.intValue()!=-1 ? containers.get(currentSelection) : null ;
					Composite selected=currentSelection.intValue()!=-1 ? containers.get(currentSelection).getDocumentContained().getGroup() : null ;
					if(getState().equals(Designer.RESIZE)){
						setState(Designer.NORMAL);
						offset[0] = null;							
						Cursor cursor=new Cursor(getMainComposite().getDisplay(), SWT.CURSOR_ARROW);						
						getMainComposite().setCursor(cursor);						

						DesignerUtilities designerUtilities=new DesignerUtilities();
						//int tempWidth=selected.getBounds().width;
						//int tempHeight=selected.getBounds().height;
						int setWidth=designerUtilities.calculateWidth(selected, mainComposite.getBounds().width);
						int setHeight=designerUtilities.calculateHeight(selected, mainComposite.getBounds().height);

						//						int tempWidth=selected.getBounds().width;
						//						tempWidth=tempWidth/DocContainer.ALIGNMENT_MARGIN;
						//						tempWidth=tempWidth*DocContainer.ALIGNMENT_MARGIN;
						//						int tempHeight=selected.getBounds().height;
						//						tempHeight=tempHeight/DocContainer.ALIGNMENT_MARGIN;
						//						tempHeight=tempHeight*DocContainer.ALIGNMENT_MARGIN;

						selected.setSize(setWidth, setHeight);						
						DocContainer docContainerSelected=containers.get(currentSelection);
						docContainerSelected.reloadStyleDocumentProperties();
						// Update only if document is present
						if(selectedDoc.getDocumentContained().getMetadataDocument()!=null){
							(new ModelBO()).updateModelModifyDocument(selectedDoc.getDocumentContained().getMetadataDocument(), selectedDoc.calculateTemplateStyle(false));						
						}
						if(selectedDoc.getDocumentContained()!=null && selectedDoc.getDocumentContained().getMetadataDocument()!=null){
							selectedDoc.getDocumentContained().drawImage();						
						}
						setCurrentSelection(new Integer(-1));
						if(selected!=null){

							selected.setBackground(new Color(selected.getDisplay(), DocContainer.COLOR_CONTAINERS));
						}
						editor.setIsDirty(true);
					}
					/**  IF in Selection state mouse button on shell causes end selection**/
					else if(getState().equals(Designer.SELECTION)){
						setState(Designer.NORMAL);
						offset[0] = null;							
						setCurrentSelection(new Integer(-1));
						if(selected!=null){
							selected.setBackground(new Color(selected.getDisplay(), DocContainer.COLOR_CONTAINERS));
						}
						// set Views Invisible
						IViewPart viewPart=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
						if(viewPart!=null)((DocumentPropertiesView)viewPart).setVisible(false);
						IViewPart viewPart2=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PARAMETERS_VIEW_ID);
						if(viewPart2!=null)((DocumentParametersView)viewPart2).setVisible(false);

					}
					break;
				case SWT.MouseMove:
					/**  IF in resizing state mouse moving on shell causes resizing**/
					DocContainer selectedDoc1=currentSelection.intValue()!=-1 ? containers.get(currentSelection) : null ;
					if(selectedDoc1 != null){
						Composite selected1=selectedDoc1.getDocumentContained().getGroup();
						if(getState().equals(Designer.RESIZE)){
							if(selected1!=null){
								Rectangle rect=selected1.getBounds();
								int x=event.x;
								int y=event.y;
								int nuova_larghezza=rect.width;
								int nuova_altezza=rect.height;
								if(x>rect.x+rect.width){
									nuova_larghezza=rect.width+(x-rect.x-rect.width);
									//selected1.setSize(nuova_larghezza, rect.height);
								}
								if(y>rect.y+rect.height){
									nuova_altezza=rect.height+(y-rect.y-rect.height);
								}
								// check if intersects other components
								boolean doesIntersect=DocContainer.doesIntersect(selectedDoc1.getIdContainer(), selectedDoc1.getDesigner(),selectedDoc1.getDocumentContained().getGroup().getLocation().x, selectedDoc1.getDocumentContained().getGroup().getLocation().y, nuova_larghezza,nuova_altezza, true);
								// check if exceeds bounds
								boolean doesExceed=DocContainer.doesExceed(selectedDoc1.getIdContainer(), selectedDoc1.getDesigner(),selectedDoc1.getDocumentContained().getGroup().getLocation().x, selectedDoc1.getDocumentContained().getGroup().getLocation().y, nuova_larghezza,nuova_altezza, true);							
								if(doesIntersect==false && doesExceed==false){
									selected1.setSize(nuova_larghezza, nuova_altezza);
									// Update model if document is present
									if(selectedDoc1.getDocumentContained().getMetadataDocument()!=null){
										(new ModelBO()).updateModelModifyDocument(selectedDoc1.getDocumentContained().getMetadataDocument(), selectedDoc1.calculateTemplateStyle(false));
									}
									editor.setIsDirty(true);								
								}
								//shell.redraw();
							}
						}
						else if(getState().equals(Designer.DRAG)){
							if(selectedDoc1.getIdContainer().equals(selectedDoc1.getDesigner().getCurrentSelection())){
								if (offset[0] != null) {
									Point pt = offset[0];							
									int newX=event.x - pt.x;
									int newY=event.y - pt.y;
									boolean doesIntersect=DocContainer.doesIntersect(selectedDoc1.getIdContainer(), selectedDoc1.getDesigner(),newX, newY, selectedDoc1.getDocumentContained().getGroup().getBounds().width,selectedDoc1.getDocumentContained().getGroup().getBounds().height,true);
									boolean doesExceed=DocContainer.doesExceed(selectedDoc1.getIdContainer(), selectedDoc1.getDesigner(),newX, newY, selectedDoc1.getDocumentContained().getGroup().getBounds().width,selectedDoc1.getDocumentContained().getGroup().getBounds().height,true);

									if(doesIntersect==false && doesExceed==false){
										selectedDoc1.getDocumentContained().getGroup().setLocation(newX, newY);
										// Update model if document is present
										if(selectedDoc1.getDocumentContained().getMetadataDocument()!=null){
											(new ModelBO()).updateModelModifyDocument(selectedDoc1.getDocumentContained().getMetadataDocument(), selectedDoc1.calculateTemplateStyle(false));
										}
										editor.setIsDirty(true);								
									}
								}
							}
						}
					}
					break;
				case SWT.MouseUp:
					//					Composite selectedDoc2=currentSelection.intValue()!=-1 ? containers.get(currentSelection).getContainer() : null ;
					//					if(getState().equals(Designer.DRAG)){
					//						int tempX=selectedDoc2.getLocation().x;
					//						tempX=tempX/DocContainer.ALIGNMENT_MARGIN;
					//						tempX=tempX*DocContainer.ALIGNMENT_MARGIN;
					//						int tempY=selectedDoc2.getLocation().y;
					//						tempY=tempY/DocContainer.ALIGNMENT_MARGIN;
					//						tempY=tempY*DocContainer.ALIGNMENT_MARGIN;
					//						selectedDoc2.setLocation(tempX, tempY);
					//						
					//					}					
					break;
				}
			}
		};
		mainComposite.addListener(SWT.MouseDown, listener);
		mainComposite.addListener(SWT.MouseUp, listener);
		mainComposite.addListener(SWT.MouseMove, listener);

		logger.debug("OUT");
	}


	/** Add the context menu
	 * 
	 * @param composite
	 */

	public void addContextMenu(final Composite composite){
		logger.debug("IN");
		composite.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				Menu menu = new Menu(composite.getShell(), SWT.POP_UP);
				MenuItem item = new MenuItem(menu, SWT.PUSH);
				item.setText("Add Doc");
				item.addListener(SWT.Selection, new Listener() {

					public void handleEvent(Event e) {
						if(getState().equals(Designer.NORMAL)){
							addNewDocContainer(composite, currentX, currentY, DocContainer.DEFAULT_WIDTH+50, DocContainer.DEFAULT_HEIGHT+50, true);
							editor.setIsDirty(true);
						}
					}
				});

				menu.setLocation(event.x, event.y);
				menu.setVisible(true);
				while (!menu.isDisposed() && menu.isVisible()) {
					if (!composite.getDisplay().readAndDispatch())
						composite.getDisplay().sleep();
				}
				menu.dispose();
			}
		});

		logger.debug("OUT");

	}



	public int getCurrentX() {
		return currentX;
	}



	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}



	public int getCurrentY() {
		return currentY;
	}



	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}



	public HashMap<Integer, DocContainer> getContainers() {
		return containers;
	}



	public void setContainers(HashMap<Integer, DocContainer> containers) {
		this.containers = containers;
	}



	public Composite getMainComposite() {
		return mainComposite;
	}



	public void setMainComposite(Composite mainComposite) {
		this.mainComposite = mainComposite;
	}



	public DocumentCompositionEditor getEditor() {
		return editor;
	}



	public void setEditor(DocumentCompositionEditor editor) {
		this.editor = editor;
	}

	/**
	 * 	Initizialize the designer by the template
	 * @param documentComposition
	 */

	public void initializeDesigner(DocumentComposition documentComposition){
		logger.debug("IN");
		if(documentComposition == null || 
				documentComposition.getDocumentsConfiguration()==null 
				|| (documentComposition.getDocumentsConfiguration()!= null && documentComposition.getDocumentsConfiguration().getDocuments()==null) 
				|| (documentComposition.getDocumentsConfiguration()!= null && documentComposition.getDocumentsConfiguration().getDocuments()!=null && documentComposition.getDocumentsConfiguration().getDocuments().size()==0)){
			return;
		}
		Vector<Document> documentsToIterate=new Vector<Document>(documentComposition.getDocumentsConfiguration().getDocuments());
		// Run all the documents, for each one calculate the style and search for paramters.. 
		for (Iterator iterator = documentsToIterate.iterator(); iterator.hasNext();) {
			Document document = (Document) iterator.next();
			String sbiObjectLabel =	document.getSbiObjLabel();
			MetadataStyle metadataStyle=null;
			// Recover style informations!
			try{
				Style styleS=document.getStyle();
				metadataStyle=new MetadataStyle(styleS);
			}
			catch (Exception e) {
				MessageDialog.openError(mainComposite.getShell(), 
						"Error", "Error in retrieving positioning metadata for the document with label "+sbiObjectLabel);
				logger.error("END "+Designer.class.toString()+": Initialize designer function: " +
						"Error in retrieving positioning metadata for the file with name "+sbiObjectLabel);				
				new ModelBO().deleteDocumentFromModel(document);
				continue;
			}

			String localFileName = "";
			try{
				// Recover documentInformations from File
				localFileName=document.getLocalFileName();	
				MetadataDocument metadataDocument=null;
				if(localFileName!=null){

					// search for the right file

					IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
					IPath workspacePath=root.getLocation();
					IProject project = root.getProject(getProjectName());
					IPath projectLocation=project.getLocation();
					IPath pathRetrieved=FileFinder.retrieveFile(localFileName, projectLocation.toString(), workspacePath);
					if(pathRetrieved != null){

						IFile fileToGet = ResourcesPlugin.getWorkspace().getRoot().getFile(pathRetrieved);

						if(fileToGet.exists()){
							// not put yet Id beacuase not linked yet to the group;  create the metadata document from FIles METADATA
							// add the metadata document to the metadata container
							metadataDocument=new MetadataDocument(fileToGet);
							(new MetadataBO()).getMetadataDocumentComposition().addMetadataDocument(metadataDocument);


							// ************		PREPARE MEASURES FOR THE DESIGNER	*****************
							String videoWidth=(new ModelBO()).getModel().getDocumentsConfiguration().getVideoWidth();
							String videoHeight=(new ModelBO()).getModel().getDocumentsConfiguration().getVideoHeight();
							int vWidth=Integer.valueOf(videoWidth);
							int vHeight=Integer.valueOf(videoHeight);

							int widthToPut=MetadataStyle.fromVideoWidthToDesignerWidth(metadataStyle.getWidth(), vWidth, DESIGNER_WIDTH);
							int heightToPut=MetadataStyle.fromVideoHeightToDesignerHeight(metadataStyle.getHeight(), vHeight, DESIGNER_HEIGHT);

							// scale x and y to default designer sizes
							int scaledX=(DESIGNER_WIDTH*metadataStyle.getX())/vWidth;
							int scaledY=(DESIGNER_HEIGHT*metadataStyle.getY())/vHeight;

							addDocContainerFromTemplate(mainComposite, scaledX, scaledY, widthToPut, heightToPut, metadataDocument, document);


						}
						else{
							MessageDialog.openError(mainComposite.getShell(), 
									"Error", "Could not find file "+localFileName+", download idt again!");
							logger.error("END "+Designer.class.toString()+": Initialize designer function: " +
									"Could not find file "+localFileName+", download idt again!");
							// delete from model!!!!
							new ModelBO().deleteDocumentFromModel(document);
						}
					}
					else{
						MessageDialog.openError(mainComposite.getShell(), 
								"Error", "Could not find file "+localFileName+", download idt again!");
						logger.error("END "+Designer.class.toString()+": Initialize designer function: " +
								"Could not find file "+localFileName+", download idt again!");
						// delete from model!!!!
						new ModelBO().deleteDocumentFromModel(document);					
					}
				}
			}
			catch (Exception e) {
				// Error in retrieving the document, download idt again
				MessageDialog.openError(mainComposite.getShell(), 
						"Error", "The file with name "+localFileName+" was not found, download the document with label "+sbiObjectLabel+" or check that in tempate you have the right file name defined");
				logger.error("END Initialize designer function: Error in retrieving metadata the file with name "+localFileName+", download idt again!",e);

			}
		}

		// reloading navigation view
		reloadNavigationView();

		logger.debug("OUT");

	}


	public boolean isDocumentContained(Integer idActual, String label){
		boolean found=false;
		for (Iterator iterator = containers.keySet().iterator(); iterator.hasNext() && found==false;) {
			Integer id = (Integer) iterator.next();
			DocContainer docContainer=containers.get(id);
			if(!id.equals(idActual)){
				DocumentContained documentContained=docContainer.getDocumentContained();
				if(documentContained!=null){
					MetadataDocument metadata=documentContained.getMetadataDocument();
					if(metadata!=null){
						if(label.equals(metadata.getLabel())){
							found=true;
						}
					}
				}

			}
		}
		return found;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}




	/** Reload the view with navigations
	 * 
	 * @param id
	 */

	public void reloadNavigationView(){
		IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		try{
			// Navigation
			IViewPart object=DocCompUtilities.getViewReference(DocCompUtilities.NAVIGATION_VIEW_ID);
			if(object!=null && object instanceof NavigationView){
				NavigationView view=(NavigationView)object;
				view.reloadNavigations();
			}
			else{
				logger.warn("view Document navigation closed");
			}

		}catch (Exception e) {
			logger.error("Error reloading navigation view", e);
			e.printStackTrace();
		}
	}

}
