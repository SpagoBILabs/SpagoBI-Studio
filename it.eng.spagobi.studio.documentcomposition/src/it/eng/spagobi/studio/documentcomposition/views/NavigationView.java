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
package it.eng.spagobi.studio.documentcomposition.views;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.RefreshDocLinkedBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;
import it.eng.spagobi.studio.documentcomposition.wizards.SpagoBIModifyNavigationWizard;
import it.eng.spagobi.studio.documentcomposition.wizards.SpagoBINavigationWizard;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class NavigationView extends ViewPart {

	Table table;
	Composite client ;
	FormToolkit toolkit;
	Label labelNoDocs;

	
	HashMap <String, String> docInfoUtil = new HashMap<String, String>();
	
	private Button newButton; 
	private Button deleteButton;
	private Button updateButton;
	
	private DocumentComposition documentComp;
	private MetadataDocumentComposition metadataDoc;
	
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		documentComp = Activator.getDefault().getDocumentComposition();
		metadataDoc = Activator.getDefault().getMetadataDocumentComposition();
		
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {		
		documentComp = Activator.getDefault().getDocumentComposition();
		metadataDoc = Activator.getDefault().getMetadataDocumentComposition();
		
		fillDocumentsNames();
		toolkit = new FormToolkit(parent.getDisplay());

		// Lets make a layout for the first section of the screen
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		// Creating the Screen
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText("Document composition navigations"); //$NON-NLS-1$
		section.setDescription("Navigations");
		// Composite for storing the data
		client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		

		if(documentComp != null && documentComp.getDocumentsConfiguration()!= null 
				&& documentComp.getDocumentsConfiguration().getDocuments()!= null && documentComp.getDocumentsConfiguration().getDocuments().size() != 0){
			loadNavigations(toolkit, parent);
		}else{			
			labelNoDocs = new Label(client, SWT.NONE);
			labelNoDocs.setText("No documents configured.");
		}
		toolkit.paintBordersFor(client);
		section.setClient(client);
		client.pack();
		client.redraw();

	}
	
	protected Button createDeleteButton(Composite parent, FormToolkit toolkit, Composite client){
		
		Button deleteButton = toolkit.createButton(client, "Delete", SWT.PUSH | SWT.BORDER_DOT);
		deleteButton.pack();
		return deleteButton;
	}
	
	protected Shell createConfirmDialog(Composite client, final boolean[] result){
		final Shell confirm = new Shell(client.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		confirm.setLayout(new RowLayout());

		confirm.setSize(200, 80);
		Point pt = client.getDisplay().getCursorLocation ();
		confirm.setLocation (pt.x-250, pt.y);

	    final Button ok = new Button(confirm, SWT.PUSH);
	    ok.setText("Confirm");
	    Button cancel = new Button(confirm, SWT.PUSH);
	    cancel.setText("Cancel");
	    confirm.isReparentable();

	    Listener dialogListener = new Listener() {
	        public void handleEvent(Event event) {
	          result[0] = event.widget == ok;
	          confirm.notifyListeners(event.type, event);
	          confirm.close();
			  int selection = table.getSelectionIndex();
			  TableItem tableItem=table.getItem(selection);

			  if(result[0]){
				  
				  deleteNavigationFromModel();				  
				  table.redraw();
			  }
	        }
	      };
	      
	    ok.addListener(SWT.Selection, dialogListener);
	    cancel.addListener(SWT.Selection, dialogListener);
	    return confirm;
		
	}
	protected Table createTable(Composite parent, FormToolkit toolkit, Composite client){
		//fillDocumentsNames();
		table = toolkit.createTable(client, SWT.SINGLE  | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 200;
		gd.horizontalSpan =3;
		table.setLayoutData(gd);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		table.setSize(300, 300);
		
	    String[] titles = { "Navigation name" , "Master document", "Destination documents"};
	    for (int i = 0; i < titles.length; i++) {
		      TableColumn column = new TableColumn(table, SWT.NONE);
		      column.setText(titles[i]);
		}   
	    //ricavata quando apro documento salvato in precedenza--> gira all'inizio
	    if(documentComp != null && documentComp.getDocumentsConfiguration() != null){
			Vector docs = documentComp.getDocumentsConfiguration().getDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){
					//recupera ogni documento
					Document doc = (Document)docs.elementAt(i);
					Parameters params = doc.getParameters();
					if(params != null && params.getParameter() != null){
						Vector par = params.getParameter();
						for(int j =0; j<par.size(); j++){
							Parameter param = (Parameter)par.elementAt(j);
							String navName = param.getNavigationName();
							String type = param.getType();
							if(navName != null && type != null && type.equalsIgnoreCase("OUT")){
							      TableItem item = new TableItem(table, SWT.NONE);
							      item.setText(0, navName);
							      String sbiLabel = doc.getSbiObjLabel();
							      
							      /*BUG!!!!*/
							      String name =docInfoUtil.get(sbiLabel);		
							      if(name == null){
							    	  name = sbiLabel;
							      }
							      item.setText(1, name);
							      /*FINE*/
							      StringBuffer dest = new StringBuffer();
							      if(param.getRefresh()!= null){
							    	  Vector <RefreshDocLinked> destinatons =param.getRefresh().getRefreshDocLinked();
							    	  if(destinatons != null){
								    	  for(int k =0; k<destinatons.size(); k++){
										      /*BUG!!!!*/
										      String docdest =docInfoUtil.get(((RefreshDocLinked)destinatons.elementAt(k)).getLabelDoc());		
										      if(docdest == null){
										    	  docdest = ((RefreshDocLinked)destinatons.elementAt(k)).getLabelDoc();
										      }
										      /*FINE*/
								    		  dest.append(docdest);
								    		  dest.append("(");
								    		  dest.append(((RefreshDocLinked)destinatons.elementAt(k)).getLabelParam());
								    		  dest.append(")");
								    		  if(k != destinatons.size()-1){
								    			  dest.append("-");
								    		  }
								    	  }
							    	  }
							      }
							      item.setText(2, dest.toString());
							      
							      
							}
						}
					}
				}
			} 
	    }

	    for (int i=0; i<titles.length; i++) {
	      table.getColumn (i).pack ();
	    }  
	    
	    return table;
	}
	private void fillDocumentsNames(){
		if(metadataDoc != null){
			Vector docs = metadataDoc.getMetadataDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){
					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
					String docName = doc.getName();
					String docLabel = doc.getLabel();
					if(docLabel != null && docName != null){
						docInfoUtil.put(docLabel, docName);
						docInfoUtil.put(docName, docLabel);
					}
				}
			}
		}
	}
	private void deleteNavigationFromModel(){
		IWorkbenchPage iworkbenchpage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		DocumentCompositionEditor editor= (DocumentCompositionEditor)iworkbenchpage.getActiveEditor();
		
		int selectedToDelete = table.getSelectionIndex();
		TableItem item = table.getItem(selectedToDelete);

		if(documentComp != null){

			Vector docs = documentComp.getDocumentsConfiguration().getDocuments();
			if(docs != null){

				for(int i=0; i<docs.size(); i++){

					//recupera ogni documento
					Document doc = (Document)docs.elementAt(i);
					Parameters params = doc.getParameters();
					Vector par = params.getParameter();
					if(par != null){
					for(int j =0; j<par.size(); j++){

						Parameter paramOut = (Parameter)par.elementAt(j);
						String navName = paramOut.getNavigationName();

						if(navName != null && navName.equalsIgnoreCase(item.getText())){
							//elimina la classe java del modello

							String destinations = item.getText(2);
							
							ParameterBO paramBo = new ParameterBO();
							RefreshDocLinkedBO bo = new RefreshDocLinkedBO();
							
							Vector refreshes = paramOut.getRefresh().getRefreshDocLinked();
							
							StringTokenizer st = new StringTokenizer(destinations,"-");
							while(st.hasMoreTokens()){
								String destinationDoc = st.nextToken().trim();
								
								
								String param = destinationDoc.substring(destinationDoc.indexOf("(")+1, destinationDoc.indexOf(")"));
								//cancella refresh
								
								destinationDoc = destinationDoc.substring(0, destinationDoc.indexOf("("));
								String destLabel =docInfoUtil.get(destinationDoc);
								
								for(int k=0; k<refreshes.size(); k++){
									RefreshDocLinked refresh = (RefreshDocLinked)refreshes.elementAt(k);
									String idParam =refresh.getIdParam();
									
									String idDoc =refresh.getLabelDoc();
									
									String idLabelParam =refresh.getLabelParam();
									
									if(idDoc.equals(destLabel) && idLabelParam.equals(param)){
										
										//controlla se usato da altri:
										boolean isUsedByOther = bo.inputParameterIsUsedByOther(documentComp, idParam);
										//cancella IN parameter corrispondente
										if(!isUsedByOther){
											// commented to keep input parameters even if not used by navigations
											//boolean result =paramBo.deleteParameterById(documentComp, idParam);
										}
										refreshes.remove(refresh);
										if(refreshes.size()== 0){
											//elimina anche parametro out
											par.remove(paramOut);
										}
										item.dispose();
										editor.setIsDirty(true);
										return;
									}
									
								}
								
								
							}

							
							
						}

					}

					}
				}
			}
		}
	}

	private void loadNavigations(FormToolkit toolkit, Composite parent){
		GridData gd = new GridData(SWT.LEFT);
		gd.widthHint = 50;
		gd.horizontalSpan =1;
		gd.horizontalAlignment= SWT.LEFT;
		final boolean[] result = new boolean[1];
		/**crea dialog x conferma**/
	
		newButton = toolkit.createButton(client, "New", SWT.PUSH); 
		deleteButton = createDeleteButton(parent, toolkit, client);
		updateButton = toolkit.createButton(client, "Modify", SWT.PUSH);
		
		
		newButton.pack();
		updateButton.pack();
		

		newButton.setLayoutData(gd);
		deleteButton.setLayoutData(gd);
		updateButton.setLayoutData(gd);
		
		
		// Add Delete Button Listener
		Listener deleteListener = new Listener() {
			public void handleEvent(Event event) {
		        switch (event.type) {
		        case SWT.Selection:
		        	TableItem[] items = table.getSelection();
		        	if(items.length == 0){
		        		MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning", "Please, select a navigation from list below.");
		        	}else{
				          /*Shell confirm = createConfirmDialog(client, result);
				          confirm.setText("Confirm delete?");
				          confirm.setSize(250,100);
						  confirm.open();*/
		        		boolean confirm =MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Confirm", "Do you wish to delete the selected navigation?");
		        		if(confirm){
		        			deleteNavigationFromModel();				  
							table.redraw();
		        		}
		        		
		        	}
		        	break;
		        }

			}
		};

		Listener addListener = new Listener() {
			public void handleEvent(Event event) {
		        switch (event.type) {
		        case SWT.Selection:
			    	///button to start the wizard
		    	    // Instantiates and initializes the wizard
		        	SpagoBINavigationWizard wizard = new SpagoBINavigationWizard();

		    	    wizard.init(PlatformUI.getWorkbench(),  new StructuredSelection(table));
		    	    // Instantiates the wizard container with the wizard and opens it
		    	    WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		    	    dialog.create();
		    	    dialog.open();
		        }

			}
		};
		
		Listener modifyListener = new Listener() {
			public void handleEvent(Event event) {
		        switch (event.type) {
		        case SWT.Selection:
			    		///button to start the wizard
		    	    // Instantiates and initializes the wizard
		        	TableItem[] items = table.getSelection();
		        	if(items.length == 0){
		        		MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning", "Please, select a navigation from list below.");
		        	}else{
		        			        	
			        	SpagoBIModifyNavigationWizard wizard = new SpagoBIModifyNavigationWizard();
	
			    	    wizard.init(PlatformUI.getWorkbench(),  new StructuredSelection(table));
			    	    // Instantiates the wizard container with the wizard and opens it
			    	    WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			    	    dialog.create();
			    	    dialog.open();
		        	}
		        }

			}
		};
		newButton.addListener(SWT.Selection, addListener);
		updateButton.addListener(SWT.Selection, modifyListener);
		deleteButton.addListener(SWT.Selection, deleteListener);
				
		/**tabella navigazioni**/
		createTable(parent, toolkit, client);

	}
	
	
	/**
	 *    Reload the navigation from the document composition
	 */
	public void reloadNavigations(){
		
		metadataDoc = Activator.getDefault().getMetadataDocumentComposition();
		documentComp= Activator.getDefault().getDocumentComposition();

		fillDocumentsNames();
		if(labelNoDocs != null){
			labelNoDocs.dispose();
		}

		if(table != null && !table.isDisposed()){
			table.dispose();
		}
		if((newButton == null && deleteButton == null && updateButton == null) ||(
				newButton.isDisposed() && deleteButton.isDisposed() && updateButton.isDisposed())){
			loadNavigations(toolkit, client.getParent().getParent());
		}else{
			/**tabella navigazioni**/
			createTable(client.getParent(), toolkit, client);
		}


		client.layout();
		client.redraw();
	}
	
	
	
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	public void cleanParameters(){
		table.removeAll();
	}


}
