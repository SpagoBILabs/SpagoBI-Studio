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
package it.eng.spagobi.studio.documentcomposition.wizards.pages;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;
import it.eng.spagobi.studio.documentcomposition.wizards.SpagoBINavigationWizard;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.util.DestinationInfo;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNavigationWizardDestinDocPage extends WizardPage {


	Vector<Combo> destinationDocNameCombo;
	Vector<Combo> destinationInputParam ;
	Vector<Text> destinationInputParamDefaultValue ;
	
	private MetadataDocumentComposition metaDoc;
	

	String name = "";
	String paramIn = "";
	
	int destinCounter = 0;

	HashMap <String, String> docInfoUtil = new HashMap<String, String>();

	private DestinationInfo destinationInfo;
	private Vector<DestinationInfo> destinationInfos;
	
	
	public NewNavigationWizardDestinDocPage() {		
		super("New Document - Destination document");
		setTitle("Insert Destination Document");
		destinationInfos = new Vector<DestinationInfo>();
	}
	public NewNavigationWizardDestinDocPage(String pageName) {		
		super(pageName);
		setTitle("Insert Destination document");
		destinationInfos = new Vector<DestinationInfo>();

	}
	
	@Override
	public boolean isPageComplete() {
				
		boolean ret= super.isPageComplete();
		for(int i = 0; i<destinCounter; i++){
			int sel = destinationDocNameCombo.elementAt(destinCounter).getSelectionIndex();
			String destin = destinationDocNameCombo.elementAt(destinCounter).getItem(sel);
			
			if ((destinationInputParam.elementAt(destinCounter).getText() == null || destinationInputParam.elementAt(destinCounter).getText().length() == 0)
					&&(sel ==-1 || destin == null )) {
				return false;
			}
		}	
		
		return ret;
	}

	public void createControl(Composite parent) {
		destinationInfo = new DestinationInfo();
		
		destinationDocNameCombo = new Vector<Combo>();
		destinationInputParam = new Vector<Combo>();
		destinationInputParamDefaultValue = new Vector<Text>();

		final ScrolledComposite sc =  new ScrolledComposite(parent, SWT.V_SCROLL );
		final Composite composite = new Composite(sc, SWT.BORDER);
		sc.setContent(composite);

		
		composite.addListener(SWT.Show, new Listener() {
			public void handleEvent(Event event) {
				fillDestinationCombo();
			}
		});	

		
		final GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);
	
		
		new Label(composite, SWT.NONE).setText("Destination document:");				
		destinationDocNameCombo.addElement(new Combo(composite, SWT.BORDER |SWT.READ_ONLY ));
		
		/////////////riempie documenti dest
		fillDestinationCombo();
		

		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		destinationDocNameCombo.elementAt(destinCounter).setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth =200;
		gd.widthHint = 250;
		
		new Label(composite, SWT.NONE).setText("Input parameter:");
		destinationInputParam.addElement(new Combo(composite, SWT.BORDER | SWT.READ_ONLY));
		
		destinationInputParam.elementAt(destinCounter).setLayoutData(gd);
		
		new Label(composite, SWT.NONE).setText("Default value:");
		destinationInputParamDefaultValue.addElement(new Text(composite, SWT.BORDER));
		destinationInputParamDefaultValue.elementAt(destinCounter).setLayoutData(gd);
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan =2;

		final Button addButton = new Button(composite, SWT.PUSH) ;
		addButton.setText("Add destination");
		addButton.setVisible(false);
		addButton.setLayoutData(gd);


		
		destinationInputParam.elementAt(destinCounter).addListener( SWT.FocusIn, new Listener() {
			public void handleEvent(Event event) {
				int numberOfDestinations = destinationDocNameCombo.elementAt(destinCounter).getItemCount();
				//controlla se possibile aggiungere nuove destinazioni
				if(numberOfDestinations != 1){
					addButton.setVisible(true);				
					composite.redraw();
				}			
			}
		});	

		addButton.addListener( SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//setPageComplete(false);
				if(destinationDocNameCombo.elementAt(destinCounter).getItemCount() ==1){
					//messaggio di errore in dialog
					addButton.setVisible(false);
					final boolean[] result = new boolean[1];
			        Shell confirm = createErrorDialog(composite, result, 0);
			        confirm.setText("Error");
			        confirm.setSize(300,100);
					confirm.open();
					return;
				}

				destinationInfo = new DestinationInfo();
				int sel = destinationDocNameCombo.elementAt(destinCounter).getSelectionIndex();
				destinationInfo.setDocDestName(destinationDocNameCombo.elementAt(destinCounter).getItem(sel));
				
				int selIn = destinationInputParam.elementAt(destinCounter).getSelectionIndex();		
				//destinationInfo.setParamDestName(destinationInputParam.elementAt(destinCounter).getItem(selIn));
				String label = destinationInputParam.elementAt(destinCounter).getText();
				String urlName= (String)destinationInputParam.elementAt(destinCounter).getData(label);
				destinationInfo.setParamDestName(urlName);
				
				destinationInfo.setParamDefaultValue(destinationInputParamDefaultValue.elementAt(destinCounter));
				destinationInfos.add(destinationInfo);	

				
				destinCounter++;	
				
				
				GridData gridData = new GridData();
				gridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
				gridData.horizontalSpan = 1;
				gridData.widthHint = 250;
				
				new Label(composite, SWT.NONE).setText("Destination document:");	
				destinationDocNameCombo.addElement(new Combo(composite, SWT.BORDER |SWT.READ_ONLY ));
				
				/////////////riempie documenti dest
				fillDestinationCombo();
				//destinationInputParam.elementAt(destinCounter).removeAll();
				
				destinationDocNameCombo.elementAt(destinCounter).setLayoutData(gridData);
				destinationDocNameCombo.elementAt(destinCounter).setVisible(true);


				//crea una nuovo output text
				new Label(composite, SWT.NONE).setText("Input parameter:");
				Combo newText =new Combo(composite, SWT.BORDER |SWT.READ_ONLY );

				destinationInputParam.addElement(newText);
				
				newText.setLayoutData(gridData);

				
				new Label(composite, SWT.NONE).setText("Default value:");
				destinationInputParamDefaultValue.addElement(new Text(composite, SWT.BORDER));
				destinationInputParamDefaultValue.elementAt(destinCounter).setLayoutData(gridData);
				
				
				destinationDocNameCombo.elementAt(destinCounter).addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						Combo selectedCombo = (Combo) event.widget;
						
						//ricavo dal vettore di combo la sua posizione
						int destinComboToRedraw = destinationDocNameCombo.indexOf(selectedCombo);
						//controlla se destinazione precedentem selezionata
						boolean canSelect = canSelectDestination(selectedCombo.getText(), destinComboToRedraw);
						if(!canSelect){
							//messaggio di errore in dialog
							addButton.setVisible(false);
							selectedCombo.deselect((selectedCombo).getSelectionIndex());
							final boolean[] result = new boolean[1];
					        Shell confirm = createErrorDialog(composite, result, 1);
					        confirm.setText("Error");
					        confirm.setSize(300,100);
							confirm.open();
						}else{
							ParameterBO bo = new ParameterBO();
							String docLab =docInfoUtil.get(destinationDocNameCombo.elementAt(destinCounter).getText());
							boolean exists = bo.inputParameterExists(Activator.getDefault().getDocumentComposition(), docLab, selectedCombo.getText());
							if(exists){
				        		
								final boolean[] result = new boolean[1];
						        Shell confirm = createConfirmDialog(composite.getParent(), result, destinCounter);				    						        
								confirm.open();

							}else{						    					
								int sel = destinationDocNameCombo.elementAt(destinCounter).getSelectionIndex();
								if(sel != -1){
									setPageComplete(true);
									
									name = destinationDocNameCombo.elementAt(destinCounter).getItem(sel);
									
									destinationInputParam.elementAt(destinComboToRedraw).removeAll();
									
									fillDestinationParamCombo(name, destinComboToRedraw);
									destinationInputParam.elementAt(destinComboToRedraw).redraw();
								}
							}
						}
						


					}
				});	
				composite.pack(false);
				composite.getParent().redraw();

			}
		});

		destinationDocNameCombo.elementAt(0).addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				boolean canSelect = canSelectDestination(destinationDocNameCombo.elementAt(0).getText(), 0);
				if(!canSelect){
					//messaggio di errore in dialog
					final boolean[] result = new boolean[1];
			        Shell confirm = createErrorDialog(composite, result, 1);
			        confirm.setText("Error");
			        confirm.setSize(300,100);
					confirm.open();
					return;
				}
				// TODO Auto-generated method stub
				int sel = destinationDocNameCombo.elementAt(0).getSelectionIndex();
				if(sel != -1){
					name = destinationDocNameCombo.elementAt(0).getItem(sel);
					destinationInputParam.elementAt(0).removeAll();
					fillDestinationParamCombo(name, 0);
					destinationInputParam.elementAt(0).redraw();
				}
				
			}

			public void widgetSelected(SelectionEvent e) {
				//controlla se destinazione precedentem selezionata
				boolean canSelect = canSelectDestination(destinationDocNameCombo.elementAt(0).getText(), 0);
				if(!canSelect){
					//messaggio di errore in dialog
					((Combo)e.widget).deselect(((Combo)e.widget).getSelectionIndex());
					final boolean[] result = new boolean[1];
			        Shell confirm = createErrorDialog(composite, result, 1);
			        confirm.setText("Error");
			        confirm.setSize(300,100);
					confirm.open();
				}
				int sel = destinationDocNameCombo.elementAt(0).getSelectionIndex();
				if(sel != -1){
					name = destinationDocNameCombo.elementAt(0).getItem(sel);
					//controlla se è possibile modificare destinazioni
					fillDestinationParamCombo(name, 0);
					destinationInputParam.elementAt(0).redraw();
				}
			}
		});	
		
		destinationInputParam.elementAt(0).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				setPageComplete(true);
				//aggiunge pulsante x add delle pagine
				addButton.setVisible(true);
				
				ParameterBO bo = new ParameterBO();
				String docLab =docInfoUtil.get(destinationDocNameCombo.elementAt(0).getText());
				boolean exists = bo.inputParameterExists(Activator.getDefault().getDocumentComposition(), docLab, ((Combo)event.widget).getText());
				if(exists){
	        		
					final boolean[] result = new boolean[1];
			        Shell confirm = createConfirmDialog(composite.getParent(), result, 0);				    						        
					confirm.open();

				}else{						    					
					paramIn = destinationInputParam.elementAt(0).getText();	
				}
					
				
				composite.redraw();
			}
		});		
		
		composite.pack(false);
		composite.redraw();
		
		setControl(composite);
		setPageComplete(false);

	}

	
	private void fillDestinationCombo(){

		metaDoc = Activator.getDefault().getMetadataDocumentComposition();
		SpagoBINavigationWizard wizard = (SpagoBINavigationWizard)getWizard();

		if(destinationDocNameCombo.elementAt(destinCounter).getItemCount() == 0){
			if(metaDoc != null){
				Vector docs = metaDoc.getMetadataDocuments();
				if(docs != null){
					for(int i=0; i<docs.size(); i++){
						MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
						String destName = doc.getName();
						String destLabel = doc.getLabel();
						if(doc.getMetadataParameters() != null && doc.getMetadataParameters().size()!=0){
							String destinationName = doc.getName();							
							if(destinationName != null && !destinationName.equals("")){
								destinationDocNameCombo.elementAt(destinCounter).add(destinationName);
								docInfoUtil.put(destName, destLabel);
							}
						}
					}
				}
			}
		}
		String master = wizard.getSelectedMaster();
		//per ridisegnare combo
		
		if(master != null && !master.equals("")){

			int posMaster =destinationDocNameCombo.elementAt(destinCounter).indexOf(master);
			if(posMaster != -1){
				destinationDocNameCombo.elementAt(destinCounter).remove(posMaster);
				
			}
		}
		//rimuove anche destination precedentemente selezionata
		if(destinCounter != 0){
			for(int i=1; i<=destinCounter; i++){
				String destPrec = destinationDocNameCombo.elementAt(i-1).getText();
				int posDestPrec =destinationDocNameCombo.elementAt(destinCounter).indexOf(destPrec);
				if(posDestPrec != -1){
					destinationDocNameCombo.elementAt(destinCounter).remove(posDestPrec);
				}
			}
		}
		
		destinationDocNameCombo.elementAt(destinCounter).redraw();
	}
	private void fillDestinationParamCombo(String destDoc, int destinComboToRedraw){
		
		if(destinComboToRedraw == 0){
			destinationInputParam.elementAt(destinComboToRedraw).removeAll();
		}
		if(metaDoc != null){
			Vector docs = metaDoc.getMetadataDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){
					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
					String docName = doc.getName();
					if(docName != null && !docName.equals("") &&(docName.equals(destDoc))){
						Vector params = doc.getMetadataParameters();
						if(params != null){
							for (int j =0; j<params.size(); j++){
								MetadataParameter param = (MetadataParameter)params.elementAt(j);
								String label = param.getLabel();
								destinationInputParam.elementAt(destinComboToRedraw).add(label);
								destinationInputParam.elementAt(destinComboToRedraw).setData(label, param.getUrlName());
							}
						}
					}
				}
			}
		}
		
	}
	protected Shell createErrorDialog(Composite client, final boolean[] result, int messageType){
		final Shell error = new Shell(client.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		error.setLayout(new GridLayout(3, false));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;

		error.setSize(300, 100);
		Point pt = client.getDisplay().getCursorLocation ();
		error.setLocation (pt.x, pt.y);

		String message = "No more destination documents available.";
		if(messageType == 1){
			 message = "Destination already selected.";
		}else if(messageType == 2){
			message = "Select a destination.";
		}
		new Label(error, SWT.NONE).setText(message);
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 3;
	    final Button cancel = new Button(error, SWT.PUSH);
	    cancel.setLayoutData(gd);
	    cancel.setText("Cancel");

	    error.isReparentable();

	    Listener dialogListener = new Listener() {
	        public void handleEvent(Event event) {
	          result[0] = event.widget == cancel;
	          error.notifyListeners(event.type, event);
	          error.close();
	        }
	      };
	    cancel.addListener(SWT.Selection, dialogListener);
	    return error;
		
	}
	protected Shell createConfirmDialog(Composite client, final boolean[] result, int element){
		final Shell confirm = new Shell(client.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = 6;
		layout.marginHeight = 6;
		
		confirm.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		confirm.setSize(400, 120);
		
		String message = "Warning! Another navigation uses the same destination parameter. \n This operation will modify both. \nContinue? ";
		Label label = new Label(confirm, SWT.NONE);
		label.setText(message);
		label.setLayoutData(gd);
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 1;
		
		Point pt = client.getDisplay().getCursorLocation ();
		confirm.setLocation (pt.x, pt.y);

	    final Button ok = new Button(confirm, SWT.PUSH);
	    ok.setText("Confirm");
	    ok.setLayoutData(gd);
	    Button cancel = new Button(confirm, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(gd);
	    confirm.isReparentable();

	    Listener dialogListener = new Listener() {
	        public void handleEvent(Event event) {
	          result[0] = event.widget == ok;
	          confirm.notifyListeners(event.type, event);
	          confirm.close();

	        }
	      };
	      
	    ok.addListener(SWT.Selection, dialogListener);
	    cancel.addListener(SWT.Selection, dialogListener);
	    return confirm;
		
	}
	private boolean canSelectDestination(String currentDest, int currentCombo){
		boolean canSel = true;
		for(int i =0; i<destinationDocNameCombo.size(); i++){
			if(i != currentCombo){
				String prevDest = destinationDocNameCombo.elementAt(i).getText();
				if(currentDest.equals(prevDest)){
					canSel = false;
				}
			}
		}
		return canSel;
	}
	public Vector<DestinationInfo> getDestinationInfos() {
		return destinationInfos;
	}
	public void setDestinationInfos(Vector<DestinationInfo> destinationInfos) {
		this.destinationInfos = destinationInfos;
	}
	public DestinationInfo getDestinationInfo() {
		return destinationInfo;
	}
	public void setDestinationInfo(DestinationInfo destinationInfo) {
		this.destinationInfo = destinationInfo;
	}

	public Vector<Combo> getDestinationInputParam() {
		return destinationInputParam;
	}
	public Vector<Combo> getDestinationDocNameCombo() {
		return destinationDocNameCombo;
	}	
	public int getDestinCounter() {
		return destinCounter;
	}
	public void setDestinCounter(int destinCounter) {
		this.destinCounter = destinCounter;
	}
	public Vector<Text> getDestinationInputParamDefaultValue() {
		return destinationInputParamDefaultValue;
	}
	public void setDestinationInputParamDefaultValue(
			Vector<Text> destinationInputParamDefaultValue) {
		this.destinationInputParamDefaultValue = destinationInputParamDefaultValue;
	}
	public HashMap<String, String> getDocInfoUtil() {
		return docInfoUtil;
	}
	public void setDocInfoUtil(HashMap<String, String> docInfoUtil) {
		this.docInfoUtil = docInfoUtil;
	}
	
}


