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
package it.eng.spagobi.studio.documentcomposition.editors;

import it.eng.spagobi.studio.core.log.SpagoBILogger;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataStyle;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;
import it.eng.spagobi.studio.documentcomposition.views.DocumentParametersView;
import it.eng.spagobi.studio.documentcomposition.views.DocumentPropertiesView;
import it.eng.spagobi.studio.documentcomposition.views.NavigationView;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/** DocContainer class represents a square added in a designer, identified by a idCOntainer,
 *  contains a documentContained
 *  defines mouse controls on it that let resize, drag, delete options
 * @author gavardi
 *
 */

public class DocContainer {

	final Integer idContainer;
	Designer designer;
	DocumentContained documentContained;

	String title="";

	public static final int DEFAULT_WIDTH=200;
	public static final int DEFAULT_HEIGHT=200;
	public static final int MIN_MARGIN_BOUNDS=0;
	public static final int ALIGNMENT_MARGIN=20;
	
	// default color for containers
	public static RGB COLOR_CONTAINERS = new RGB(255,255,255);

	Cursor cursor=null;

	/** Document Container Contrusctor
	 * 
	 * @param _designer
	 * @param mainComposite
	 * @param x
	 * @param y
	 * @param tempWidth
	 * @param tempHeight
	 */

	public DocContainer(Designer _designer,Composite mainComposite, int x, int y, int tempWidth, int tempHeight) {
		super();
		designer=_designer;
		// Set incremental Id
		idContainer=Integer.valueOf(designer.createID());
		try{
			documentContained=new DocumentContained(mainComposite, SWT.NONE, designer);
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Error in creating the group", e);
			e.printStackTrace();
			return;	
		}
		title="NUMBER "+(idContainer.toString());
		documentContained.getGroup().setText(title);

		GridLayout layout=new GridLayout();
		layout.numColumns=1;
		documentContained.getGroup().setLayout(layout);
		documentContained.getGroup().setSize(tempWidth, tempHeight);
		documentContained.getGroup().setLocation(x, y);
		designer.setState(Designer.NORMAL);

		// Add mouse controls on container
		addContainerMouseControls(mainComposite, documentContained.getGroup());
		// Add context menu on container
		addContextMenu(mainComposite.getShell(), documentContained.getGroup());
		// Add drag and drop from navigation tree to container		
		addDragAndDropDocument(documentContained.getGroup());

		documentContained.getGroup().layout();
		documentContained.getGroup().redraw();
		documentContained.getGroup().getParent().redraw();
		documentContained.getGroup().getParent().layout();

	}


	/**
	 *  Add Mouse controls on document container
	 * @param mainComposite
	 * @param composite
	 */

	public void addContainerMouseControls(final Composite mainComposite, final Composite composite){
		final Point[] offset = new Point[1];
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				;				
				switch (event.type) {
				case SWT.MouseDown:			// *********  MOUSE DOWN   ***************

					// Reload views
					reloadDocumentPropertiesView(idContainer.toString());
					reloadStyleDocumentProperties();
					//System.out.println(designer.getEditor().isDirty);

					// Reload navigations view (Really need???)
					if(documentContained.getMetadataDocument()!=null){
						designer.reloadNavigationView();
					}

					//  If in resizing state mouse button on Container causes end resizing	
					if(designer.getState().equals(Designer.RESIZE)){
						// only if click on the current selected!
						if(idContainer.equals(designer.getCurrentSelection())){
							designer.setState(Designer.NORMAL);
							cursor=new Cursor(designer.getMainComposite().getDisplay(), SWT.CURSOR_ARROW);						
							designer.getMainComposite().setCursor(cursor);							
							offset[0] = null;							
							DesignerUtilities designerUtilities=new DesignerUtilities();
							int setWidth=designerUtilities.calculateWidth(documentContained.getGroup(), mainComposite.getBounds().width);
							int setHeight=designerUtilities.calculateHeight(documentContained.getGroup(), mainComposite.getBounds().height);
							documentContained.getGroup().setSize(setWidth, setHeight);
							reloadStyleDocumentProperties();
							// Update Model if present document
							if(documentContained.getMetadataDocument()!=null){
								(new ModelBO()).updateModelModifyDocument(documentContained.getMetadataDocument(), calculateTemplateStyle(false));
							}
							if(documentContained!=null && documentContained.getMetadataDocument()!=null){
								documentContained.drawImage();
							}
							designer.setCurrentSelection(Integer.valueOf(-1));
							designer.getEditor().setIsDirty(true);
						}
					}
					/**  IF in normal state mouse button on Container causes selection**/					
					else if(designer.getState().equals(Designer.NORMAL)){
						//****** SELECTION OF A RECTANGLE **********
						Rectangle rect = composite.getBounds();
						Point pt1 = composite.toDisplay(0, 0);
						Point pt2 = mainComposite.toDisplay(event.x, event.y);
						offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
						// while up put on DRAG situation
						designer.setState(Designer.DRAG);
						cursor=new Cursor(designer.getMainComposite().getDisplay(), SWT.CURSOR_HAND);						
						designer.getMainComposite().setCursor(cursor);
						composite.setBackground(new Color(composite.getDisplay(),new RGB(165,195,210)));
						designer.setCurrentSelection(idContainer);
					}
					/**  IF in selection state mouse button on Container causes restart DRAG or another selection!**/					
					else if(designer.getState().equals(Designer.SELECTION)){
						// check if already selected or is changing selection!
						Integer idPreviousSel=designer.getCurrentSelection();
						if(Integer.valueOf(idContainer).equals(idPreviousSel)){
							//composite.setBackground(new Color(composite.getDisplay(),new RGB(0,255,0)));
						}
						else{ 
							if(idPreviousSel.intValue()!=-1){
								Composite toDeselect=designer.getContainers().get(idPreviousSel).getDocumentContained().getGroup();
								toDeselect.setBackground(new Color(toDeselect.getDisplay(),COLOR_CONTAINERS));
							}
						}

						composite.setBackground(new Color(composite.getDisplay(),new RGB(165,195,210)));							
						designer.setState(Designer.DRAG);
						cursor=new Cursor(designer.getMainComposite().getDisplay(), SWT.CURSOR_HAND);						
						designer.getMainComposite().setCursor(cursor);						
						designer.setCurrentSelection(idContainer);
						Rectangle rect = composite.getBounds();
						Point pt1 = composite.toDisplay(0, 0);
						Point pt2 = mainComposite.toDisplay(event.x, event.y);
						offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
					}					
					break;
				case SWT.MouseMove:
					if(designer.getState().equals(Designer.RESIZE)){
						if(idContainer.equals(designer.getCurrentSelection())){
							Rectangle rect=composite.getBounds();
							int x=event.x;
							int y=event.y;
							int nuova_larghezza=rect.width;
							int nuova_altezza=rect.height;
							if(x<rect.x+rect.width ){
								nuova_larghezza=rect.width+(x-rect.x-rect.width);
								//composite.setSize(nuova_larghezza, rect.height);
							}
							if(y<rect.y+rect.height){
								nuova_altezza=rect.height+(y-rect.y-rect.height);
							}
							if(nuova_altezza<DEFAULT_HEIGHT)nuova_altezza=DEFAULT_HEIGHT;
							if(nuova_larghezza<DEFAULT_WIDTH)nuova_larghezza=DEFAULT_WIDTH;

							//check if intersect or exceed!
							boolean doesIntersect=DocContainer.doesIntersect(idContainer,designer,documentContained.getGroup().getLocation().x, documentContained.getGroup().getLocation().y, nuova_larghezza, nuova_altezza,false);
							boolean doesExceed=DocContainer.doesExceed(idContainer,designer,documentContained.getGroup().getLocation().x, documentContained.getGroup().getLocation().y, nuova_larghezza, nuova_altezza,false);

							if(doesIntersect==false && doesExceed==false){
								composite.setSize(nuova_larghezza, nuova_altezza);
								// Update model if present document
								if(documentContained.getMetadataDocument()!=null){
									(new ModelBO()).updateModelModifyDocument(documentContained.getMetadataDocument(), calculateTemplateStyle(false));
								}
								designer.getEditor().setIsDirty(true);
							}

						}
					}
					/**  IF in Selection state mouse moving on container causes drag and drop**/
					else if(designer.getState().equals(Designer.DRAG)){
						if(idContainer.equals(designer.getCurrentSelection())){
							if (offset[0] != null) {
								Point pt = offset[0];							
								int newX=event.x - pt.x;
								int newY=event.y - pt.y;
								boolean doesIntersect=doesIntersect(idContainer, designer,newX, newY, documentContained.getGroup().getBounds().width,documentContained.getGroup().getBounds().height,false);
								boolean doesExceed=doesExceed(idContainer, designer,newX, newY, documentContained.getGroup().getBounds().width, documentContained.getGroup().getBounds().height,false);
								if(doesIntersect==false && doesExceed==false){
									composite.setLocation(newX, newY);
									// Update model if document is present!
									if(documentContained.getMetadataDocument()!=null){
										(new ModelBO()).updateModelModifyDocument(documentContained.getMetadataDocument(), calculateTemplateStyle(false));
									}
									designer.getEditor().setIsDirty(true);								
								}

							}
						}			
					}
					break;
				case SWT.MouseUp:

					/**  IF in SELECTION state mouse up on container causes selection started from DRAG**/
					if(designer.getState().equals(Designer.DRAG)){
						// ---------- Try alignment MArgin-----------

						int tempX=documentContained.getGroup().getLocation().x;
						int tempY=documentContained.getGroup().getLocation().y;
						tempX=tempX/ALIGNMENT_MARGIN;
						tempX=tempX*ALIGNMENT_MARGIN;
						tempY=tempY/ALIGNMENT_MARGIN;
						tempY=tempY*ALIGNMENT_MARGIN;

						// check if space is almost filled: autofill  DISABLED AUTOFILL WITH BOUNDS IN DRAG!
						//						int width=documentContained.getGroup().getBounds().width;
						//						int height=documentContained.getGroup().getBounds().height;
						//						int totalX=width+tempX;
						//						int mainWidth=mainComposite.getBounds().width;		
						//						if((mainWidth-totalX)<=(DocContainer.ALIGNMENT_MARGIN+10)){
						//							// increase the width to fill							
						//							int newwidth=width+((mainWidth-totalX));
						//							//documentContained.getGroup().getBounds().width=width;
						//							documentContained.getGroup().setSize(newwidth, height);
						//						}
						//						int totalY=height+tempY;
						//						int mainHeight=mainComposite.getBounds().height;		
						//						if((mainHeight-totalY)<=(DocContainer.ALIGNMENT_MARGIN+10)){
						//							// increase the width to fill							
						//							int newheight=height+((mainHeight-totalY));
						//							//documentContained.getGroup().getBounds().width=width;
						//							documentContained.getGroup().setSize(width, newheight);
						//						}

						documentContained.getGroup().setLocation(tempX, tempY);
						reloadStyleDocumentProperties();						
						if(idContainer.equals(designer.getCurrentSelection())){
							composite.setBackground(new Color(composite.getDisplay(),new RGB(193,214,255)));
							designer.setCurrentSelection(idContainer);
							designer.setState(Designer.SELECTION);
							designer.setCurrentSelection(idContainer);
							cursor=new Cursor(designer.getMainComposite().getDisplay(), SWT.CURSOR_ARROW);						
							designer.getMainComposite().setCursor(cursor);
						}
					}		
					else if(designer.getState().equals(Designer.SELECTION)){
						if(designer.getCurrentSelection().equals(idContainer)){
							designer.setState(Designer.NORMAL);
							designer.setCurrentSelection(Integer.valueOf(-1));
							offset[0] = null;							
						}
					}
					documentContained.getGroup().redraw();
					break;
				}
			}
		};
		composite.addListener(SWT.MouseDown, listener);
		composite.addListener(SWT.MouseUp, listener);
		composite.addListener(SWT.MouseMove, listener);
	}





	/**
	 *  Add the context menu on document containers
	 * @param mainComposite
	 * @param composite
	 */

	public void addContextMenu(final Composite mainComposite, final Composite composite){
		composite.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				Menu menu = new Menu(mainComposite.getShell(), SWT.POP_UP);
				MenuItem item = new MenuItem(menu, SWT.PUSH);
				item.setText("Resize");
				item.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						composite.setBackground(new Color(composite.getDisplay(),new RGB(248,191,129)));
						cursor=new Cursor(designer.getMainComposite().getDisplay(), SWT.CURSOR_CROSS);						
						designer.getMainComposite().setCursor(cursor);
						designer.setState(Designer.RESIZE);
					}
				});

				MenuItem delDocItem = new MenuItem(menu, SWT.PUSH);
				delDocItem.setText("Delete Document");
				delDocItem.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						Integer idSel=designer.getCurrentSelection();
						String title=designer.getContainers().get(idSel).getDocumentContained().getGroup().getText();

						// delete document 
						if(documentContained.getMetadataDocument()!=null){  // has a doc associated???
							(new ModelBO()).deleteDocumentFromModel(documentContained.getMetadataDocument());
							// delete metadata document
							(new MetadataBO()).getMetadataDocumentComposition().removeMetadataDocument(documentContained.getMetadataDocument());
						}
						else{
							designer.getEditor().setIsDirty(true);
							designer.setCurrentSelection(-1);
							designer.setState(Designer.NORMAL);
							designer.getMainComposite().layout();
							designer.getMainComposite().redraw();
							return;
						}

						designer.getEditor().setIsDirty(true);
						designer.setCurrentSelection(-1);
						designer.setState(Designer.NORMAL);
						//composite.dispose();
						//designer.getContainers().remove(idSel);
						DocContainer docContainer=designer.getContainers().get(idSel);
						//docContainer.setDocumentContained(null);
						docContainer.setTitle("");
						docContainer.getDocumentContained().setMetadataDocument(null);
						docContainer.getDocumentContained().getScaledImage().dispose();
						docContainer.getDocumentContained().setImage(null);
						docContainer.getDocumentContained().setScaledImage(null);
						docContainer.getDocumentContained().getGroup().setText(idContainer.toString());
						docContainer.getDocumentContained().getGroup().setBackground(new Color(docContainer.getDocumentContained().getGroup().getDisplay(),COLOR_CONTAINERS));

						IViewPart viewPart=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
						if(viewPart!=null)((DocumentPropertiesView)viewPart).setVisible(false);
						IViewPart viewPart2=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PARAMETERS_VIEW_ID);
						if(viewPart2!=null)((DocumentParametersView)viewPart2).setVisible(false);						
						designer.getMainComposite().layout();
						designer.getMainComposite().redraw();
						//						designer.getMainComposite().pack();

					}
				});	

				MenuItem delItem = new MenuItem(menu, SWT.PUSH);
				delItem.setText("Delete Container");
				delItem.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						Integer idSel=designer.getCurrentSelection();
						String title=designer.getContainers().get(idSel).getDocumentContained().getGroup().getText();

						// delete document 
						if(documentContained.getMetadataDocument()!=null){  // has a doc associated???
							(new ModelBO()).deleteDocumentFromModel(documentContained.getMetadataDocument());
							// delete metadata document
							(new MetadataBO()).getMetadataDocumentComposition().removeMetadataDocument(documentContained.getMetadataDocument());
						}
						designer.getEditor().setIsDirty(true);
						designer.setCurrentSelection(-1);
						designer.setState(Designer.NORMAL);
						composite.dispose();
						designer.getContainers().remove(idSel);
						IViewPart viewPart=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
						if(viewPart!=null)((DocumentPropertiesView)viewPart).setVisible(false);
						IViewPart viewPart2=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PARAMETERS_VIEW_ID);
						if(viewPart2!=null)((DocumentParametersView)viewPart2).setVisible(false);						
						designer.getMainComposite().layout();
						designer.getMainComposite().redraw();
						//						designer.getMainComposite().pack();

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



	}


	public Integer getIdContainer() {
		return idContainer;
	}





	public DocumentContained getDocumentContained() {
		return documentContained;
	}


	public void setDocumentContained(DocumentContained documentContained) {
		this.documentContained = documentContained;
	}


	/**
	 *  Add Drag and drop function of a document to its document container
	 * @param composite
	 */

	protected void addDragAndDropDocument(final Composite composite){
		// Allow data to be copied or moved to the drop target
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(composite, operations);
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		final LocalSelectionTransfer localTransfer = LocalSelectionTransfer.getTransfer();
		Transfer[] types = new Transfer[] {fileTransfer, localTransfer};
		target.setTransfer(types);
		target.addDropListener(new DropTargetListener() {
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					if ((event.operations & DND.DROP_COPY) != 0) {
						event.detail = DND.DROP_COPY;
					} else {
						event.detail = DND.DROP_NONE;
					}
				}
				for (int i = 0; i < event.dataTypes.length; i++) {
					if (fileTransfer.isSupportedType(event.dataTypes[i])){
						event.currentDataType = event.dataTypes[i];
						// files should only be copied
						if (event.detail != DND.DROP_COPY) {
							event.detail = DND.DROP_NONE;
						}
						break;
					}
				}
				for (int i = 0; i < event.dataTypes.length; i++) {
					if (localTransfer.isSupportedType(event.dataTypes[i])){
						event.currentDataType = event.dataTypes[i];
						break;
					}
				}				
			}
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
			}
			public void dragOperationChanged(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					if ((event.operations & DND.DROP_COPY) != 0) {
						event.detail = DND.DROP_COPY;
					} else {
						event.detail = DND.DROP_NONE;
					}
				}
				// allow text to be moved but files should only be copied
				if (fileTransfer.isSupportedType(event.currentDataType)){
					if (event.detail != DND.DROP_COPY) {
						event.detail = DND.DROP_NONE;
					}
				}
			}
			public void dragLeave(DropTargetEvent event) {
			}
			public void dropAccept(DropTargetEvent event) {
			}
			public void drop(DropTargetEvent event) {
				boolean doTransfer=false;
				if (localTransfer.isSupportedType(event.currentDataType)){
					// Associate a document to a container
					Object selectedObject = event.data;
					if(selectedObject instanceof TreeSelection)
					{
						TreeSelection selectedTreeSelection=(TreeSelection)selectedObject;
						IFile file=(IFile)selectedTreeSelection.getFirstElement();
						// check not inserting a doc comp in a doc comp
						if(file.getFileExtension().equalsIgnoreCase(SpagoBIStudioConstants.DOCUMENT_COMPOSITION_ENGINE_EXTENSION)){
							MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning", "Cannot insert a document composition document ina document composition document!");
							return;
						}
						doTransfer=documentContained.recoverDocumentMetadata(idContainer, file);

						// add the document!!
						(new ModelBO()).addNewDocumentToModel(documentContained.getMetadataDocument(), calculateTemplateStyle(false));
					}
				}
				if(doTransfer==true){
					// Select the component!
					if(documentContained.getMetadataDocument()!=null)
						reloadDocumentPropertiesView(idContainer.toString());
					reloadStyleDocumentProperties();
					// Reload navigations view (Really need???)
					if(documentContained.getMetadataDocument()!=null){
						designer.reloadNavigationView();
					}
					designer.setState(Designer.SELECTION);
					composite.setBackground(new Color(composite.getDisplay(),new RGB(193,214,255)));
					if(designer.getCurrentSelection().intValue()!=-1){
						Composite toDeselect=designer.getContainers().get(designer.getCurrentSelection()).getDocumentContained().getGroup();
						toDeselect.setBackground(new Color(toDeselect.getDisplay(),COLOR_CONTAINERS));
					}
					designer.setCurrentSelection(idContainer);
				}
				if(doTransfer==true){
					if (fileTransfer.isSupportedType(event.currentDataType)){
						String[] files = (String[])event.data;
						for (int i = 0; i < files.length; i++) {
							//							Label label=new Label(composite, SWT.NULL);
							//							label.setText(files[i]);
						}					
					}
				}
				designer.getEditor().setIsDirty(true);				
				composite.redraw();
				composite.layout();
				composite.getParent().redraw();
				composite.getParent().layout();
			}
		});

	}











	/**
	 *  Calculate the style string from the Style class
	 * @param saving: if isSaving is true than decrease width by one percentage point to avoid swapping in HTML
	 */
	public Style calculateTemplateStyle(boolean isSaving){
		Style style=new Style();	
		//		String toAdd="float:left;margin:0px;";
		String toAdd="position:absolute;margin:0px;";

		// get the bounds
		Point location=documentContained.getGroup().getLocation();
		int x=location.x;
		int y=location.y;

		// make proportion of x and y second o to actual Video Size
		String videoHeight=(new ModelBO()).getModel().getDocumentsConfiguration().getVideoHeight();
		String videoWidth=(new ModelBO()).getModel().getDocumentsConfiguration().getVideoWidth();
		int videoHeightI=Integer.valueOf(videoHeight).intValue();
		int videoWidthI=Integer.valueOf(videoWidth).intValue();

		// Lo stile deve essere scalato alla dimensione reale
		int realX=(x*videoWidthI) / Designer.DESIGNER_WIDTH;
		int realY=(y*videoHeightI) / Designer.DESIGNER_HEIGHT;

		Rectangle rect=documentContained.getGroup().getBounds();
		int width =rect.width;
		int height =rect.height;

		// get the left margin: arrotondo alla decina
		toAdd+="left:"+Integer.valueOf(realX).toString()+"px;";

		// get the top margin: arrotondo alla decina
		int marginTopTemp=y/DocContainer.ALIGNMENT_MARGIN;
		int marginTop=y*DocContainer.ALIGNMENT_MARGIN;
		toAdd+="top:"+Integer.valueOf(realY).toString()+"px;";

		// get the total height and width of the container
		Point point=designer.getMainComposite().getSize();
		int totalWidth=point.x;
		int totalHeight=point.y;

		// calculate width and height percentage
		/*int widthPerc=(width*100)/totalWidth;
		int heightPerc=(height*100)/totalHeight;
		if(isSaving==true){
			widthPerc=widthPerc-1;
		}
		toAdd+="width:"+Integer.valueOf(widthPerc).toString()+"%;";
		toAdd+="height:"+Integer.valueOf(heightPerc).toString()+"%;";
		 */

		// calculate height and width as absolute value

		//		Integer convertedWidth=(videoWidthI * width) / totalWidth;
		//		Integer convertedHeight=(videoHeightI * height) / totalHeight;

		int convertedWidth=MetadataStyle.fromDesignerWidthToVideoWidth(width, videoWidthI, totalWidth);
		int convertedHeight=MetadataStyle.fromDesignerHeightToVideoHeight(height, videoHeightI, totalHeight);

		toAdd+="width:"+Integer.valueOf(convertedWidth).toString()+"px;";
		toAdd+="height:"+Integer.valueOf(convertedHeight).toString()+"px;";


		style.setStyle(toAdd);
		return style;
	}

	/**
	 * check if next drag event inteferes with other containers
	 * @return
	 */

	public static boolean doesIntersect (Integer currentId,Designer designer,int newX, int newY, int newWidth, int newHeight, boolean fromDesigner){
		boolean doesIntersect=false;
		Rectangle thisRectangle=new Rectangle(newX,newY,newWidth,newHeight);
		for (Iterator iterator = designer.getContainers().keySet().iterator(); iterator.hasNext() && doesIntersect==false;) {
			Integer idOther = (Integer) iterator.next();
			if(!idOther.equals(currentId)){
				DocContainer otherContainer = designer.getContainers().get(idOther);
				Group otherGroup=otherContainer.documentContained.getGroup();
				Rectangle otherRectangle=otherGroup.getBounds();
				doesIntersect=thisRectangle.intersects(otherRectangle);	
				doesIntersect=thisRectangle.intersects(otherRectangle.x, otherRectangle.y, otherRectangle.width, otherRectangle.height);
			}

		}
		return doesIntersect;
	}


	/**
	 * check if next drag event exceeds bounds!
	 * @return
	 */

	public static boolean doesExceed (Integer currentId, Designer designer, int newX, int newY, int newWidth, int newHeight, boolean fromDesigner){
		boolean doesExceed=false;
		Composite mainComposite=designer.getMainComposite();
		int heightMain=mainComposite.getSize().y;
		int widthMain=mainComposite.getSize().x;
		//-------give bound of 5 -------
		if(newX<MIN_MARGIN_BOUNDS || (newX+newWidth)>(widthMain-MIN_MARGIN_BOUNDS)){
			return true;
		}
		else if(newY<MIN_MARGIN_BOUNDS || (newY+newHeight)>(heightMain-MIN_MARGIN_BOUNDS)){
			return true;
		}
		else return false;
	}




	/**
	 *  Call to reload the style document properties view when a container is selected
	 * @param composite
	 */
	public void reloadStyleDocumentProperties(){
		Style style=calculateTemplateStyle(false);
		IViewPart object=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
		if(object!=null){
			DocumentPropertiesView view=(DocumentPropertiesView)object;
			view.reloadStyle(idContainer, style.getStyle(), documentContained.getMetadataDocument());
		}
	}

	/** Reload the view with document property and with document parameters
	 * 
	 * @param id
	 */

	public void reloadDocumentPropertiesView(String id){
		IWorkbenchWindow a=PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		try{
			IViewPart object=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PROPERTIES_VIEW_ID);
			if(object!=null){
				DocumentPropertiesView view=(DocumentPropertiesView)object;
				view.reloadProperties(documentContained.getMetadataDocument());
			}

			// Document parameters
			IViewPart object2=DocCompUtilities.getViewReference(DocCompUtilities.DOCUMENT_PARAMETERS_VIEW_ID);
			if(object2!=null){
				DocumentParametersView view=(DocumentParametersView)object2;
				view.reloadParametersProperties(documentContained.getMetadataDocument());
			}
		}
		catch (Exception e) {
			SpagoBILogger.errorLog("Reload Document Properties", e);
			e.printStackTrace();
		}

	}



	public Designer getDesigner() {
		return designer;
	}


	public void setDesigner(Designer designer) {
		this.designer = designer;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}










}
