/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.wizards.pages;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Refresh;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;
import it.eng.spagobi.studio.documentcomposition.wizards.SpagoBIModifyNavigationWizard;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.util.DestinationInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class ModifyNavigationWizardPage  extends WizardPage{


	Vector<Combo> destinationDocNameCombo;
	Vector<Combo> destinationInputParam ;
	Vector<Text> destinationInputParamDefaultValue ;

	HashMap <String, String> docInfoUtil = new HashMap<String, String>();
	HashMap <String, String> deletedParams = new HashMap<String, String>();
	HashMap <String, String> deletedRefresh = new HashMap<String, String>();

	String name = "";
	String paramIn = "";

	Vector<Button> deleteDestButtonVector = new Vector<Button>();

	int destinCounter = -1;

	private DestinationInfo destinationInfo;
	private Vector<DestinationInfo> destinationInfos;


	Text navigationNameText;

	Text masterDocName;
	Text masterParamName;
	Text masterDefaultValueOutputParam;


	private MetadataDocumentComposition metaDoc = Activator.getDefault().getMetadataDocumentComposition();

	public ModifyNavigationWizardPage() {		
		super("Modify navigation");
		setTitle("Modify Destination Document");		
		destinationInfos = new Vector<DestinationInfo>();
	}
	public ModifyNavigationWizardPage(String pageName) {		
		super(pageName);
		setTitle("Modify Destination document");
		destinationInfos = new Vector<DestinationInfo>();


	}
	@Override
	public boolean isPageComplete() {
		boolean ret= super.isPageComplete();

		if(destinCounter != -1){
			for(int i = 0; i<destinCounter; i++){
				int sel = destinationDocNameCombo.elementAt(destinCounter).getSelectionIndex();
				if(sel != -1){
					String destin = destinationDocNameCombo.elementAt(destinCounter).getItem(sel);

					if ((destinationInputParam.elementAt(destinCounter).getText() == null || destinationInputParam.elementAt(destinCounter).getText().length() == 0)
							&&(sel ==-1 || destin == null )) {
						return false;
					}
				}
			}	
		}else{
			ret = false;
		}
		return ret;
	}



	public void createControl(Composite parent) {

		destinationInfo = new DestinationInfo();
		destinationDocNameCombo = new Vector<Combo>();
		destinationInputParam = new Vector<Combo>();
		destinationInputParamDefaultValue = new Vector<Text>();

		fillDocInfoUtil();

		final ScrolledComposite sc =  new ScrolledComposite(parent, SWT.V_SCROLL );
		final Composite composite = new Composite(sc, SWT.NONE);
		sc.setContent(composite);

		final GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		composite.setLayout(gl);

		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
		composite.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth =260;
		gd.widthHint = 285;


		//primo composite
		Composite composite1 = new Composite(composite, SWT.NONE);
		composite1.setLayout(gl);

		final GridLayout glBlock = new GridLayout();
		glBlock.numColumns = 3;		

		Composite singleBlock = new Composite(composite1, SWT.BORDER | SWT.FILL);
		singleBlock.setLayout(glBlock);

		new Label(singleBlock, SWT.NONE).setText("Navigation name:  ");				
		navigationNameText = new Text(singleBlock, SWT.BORDER);
		navigationNameText.setLayoutData(gd);


		Composite singleBlockMaster = new Composite(composite1, SWT.BORDER | SWT.FILL);
		singleBlockMaster.setLayout(glBlock);

		new Label(singleBlockMaster, SWT.NONE).setText("Master document:");				
		masterDocName = new Text(singleBlockMaster, SWT.BORDER);		
		masterDocName.setLayoutData(gd);

		new Label(singleBlockMaster, SWT.NONE).setText("Output parameter:");				
		masterParamName = new Text(singleBlockMaster, SWT.BORDER);
		masterParamName.setLayoutData(gd);	

		new Label(singleBlockMaster, SWT.NONE).setText("Default value:");
		masterDefaultValueOutputParam = new Text(singleBlockMaster, SWT.BORDER);
		masterDefaultValueOutputParam.setLayoutData(gd);

		gd = new GridData(GridData.FILL);
		gd.horizontalSpan = 1;		


		getNavigationItem(composite);//riempie campi precedentemente inseriti

		// if destination is one delete not visible!
		// check how many destinations there are if only one disable delete button
		if(destinationDocNameCombo.size() == 1){
			for (Iterator iterator = deleteDestButtonVector.iterator(); iterator.hasNext();) {
				Button button = (Button) iterator.next();
				button.setVisible(false);
			}	
		}

		composite.pack();
		composite.redraw();

		setControl(composite);
	}

	private void fillDestinationCombo(String docDest, int comboToRedraw){
		if(destinationDocNameCombo.elementAt(comboToRedraw) != null){
			if(destinationDocNameCombo.elementAt(comboToRedraw).getItemCount() == 0){
				metaDoc = Activator.getDefault().getMetadataDocumentComposition();
				if(metaDoc != null){
					Vector docs = metaDoc.getMetadataDocuments();
					if(docs != null){
						for(int i=0; i<docs.size(); i++){
							MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
							String destName = doc.getName();
							String destLabel = doc.getLabel();
							if(doc.getMetadataParameters() != null && doc.getMetadataParameters().size()!=0){
								if(destName != null && !destName.equals("")){
									destinationDocNameCombo.elementAt(comboToRedraw).add(destName);

									if(docDest != null && docDest.equals(destLabel)){
										int pos = destinationDocNameCombo.elementAt(comboToRedraw).getItemCount();
										destinationDocNameCombo.elementAt(comboToRedraw).select(pos-1);

									}
								}
							}
						}
					}
				}
			}
			String master = masterDocName.getText();
			//per ridisegnare combo

			if(master != null && !master.equals("")){

				int posMaster =destinationDocNameCombo.elementAt(comboToRedraw).indexOf(master);
				if(posMaster != -1){
					destinationDocNameCombo.elementAt(comboToRedraw).remove(posMaster);

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
			destinationDocNameCombo.elementAt(comboToRedraw).redraw();
		}
	}
	private void refillDestinationCombo(String docDest, int comboToRedraw){
		try{
			destinationDocNameCombo.elementAt(comboToRedraw).getItemCount();

			if(destinationDocNameCombo.elementAt(comboToRedraw) != null && destinationDocNameCombo.elementAt(comboToRedraw).getItemCount() != -1){
				String[] items= destinationDocNameCombo.elementAt(comboToRedraw).getItems();
				Vector<String> presentItems = new Vector<String>();
				for(int j=0; j<items.length; j++){
					presentItems.add(items[j]);
				}
				metaDoc = Activator.getDefault().getMetadataDocumentComposition();
				if(metaDoc != null){
					Vector docs = metaDoc.getMetadataDocuments();
					if(docs != null){						
						for(int i=0; i<docs.size(); i++){
							MetadataDocument doc = (MetadataDocument)docs.elementAt(i);							
							String destLabel = doc.getLabel();
							if(doc.getMetadataParameters() != null && doc.getMetadataParameters().size()!=0){
								String destinationName = doc.getName();
								if(destinationName != null && !presentItems.contains(destinationName)){
									destinationDocNameCombo.elementAt(comboToRedraw).add(destinationName);

									if(docDest != null && docDest.equals(destinationName)){
										int pos = destinationDocNameCombo.elementAt(comboToRedraw).getItemCount();
										destinationDocNameCombo.elementAt(comboToRedraw).select(pos-1);
									}								
								}							
							}
						}
					}
				}
				String master = masterDocName.getText();
				//per ridisegnare combo

				if(master != null && !master.equals("")){

					int posMaster =destinationDocNameCombo.elementAt(comboToRedraw).indexOf(master);
					if(posMaster != -1){
						destinationDocNameCombo.elementAt(comboToRedraw).remove(posMaster);

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
				destinationDocNameCombo.elementAt(comboToRedraw).redraw();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void fillDestinationParamCombo(String destDoc, int destinComboToRedraw, String paramInSel){

		if(metaDoc != null){
			Vector docs = metaDoc.getMetadataDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){
					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
					String docName = doc.getName();
					String docLabel= doc.getLabel();

					if(docLabel != null && !docLabel.equals("") &&(docLabel.equals(destDoc))){
						Vector params = doc.getMetadataParameters();
						if(params != null){
							for (int j =0; j<params.size(); j++){
								MetadataParameter param = (MetadataParameter)params.elementAt(j);
								String label = param.getLabel();
								destinationInputParam.elementAt(destinComboToRedraw).add(label);
								destinationInputParam.elementAt(destinComboToRedraw).setData(label, param.getUrlName());

								String urlName= param.getUrlName();
								if(paramInSel != null && paramInSel.equals(urlName)){
									int pos = destinationInputParam.elementAt(destinComboToRedraw).getItemCount();
									destinationInputParam.elementAt(destinComboToRedraw).select(pos-1);
								}
							}
						}
					}
				}
			}
		}

	}

	private void fillMasterParamsData(String masterDoc){
		if(metaDoc != null){
			Vector docs = metaDoc.getMetadataDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){
					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
					String docName = doc.getName();
					String docLabel= doc.getLabel();
					if(docLabel != null && !docLabel.equals("") && (docLabel.equals(masterDoc))){
						Vector params = doc.getMetadataParameters();
						if(params != null){
							for (int j =0; j<params.size(); j++){
								MetadataParameter param = (MetadataParameter)params.elementAt(j);
								String label = param.getLabel();
								masterParamName.setData(label, param.getUrlName());
								masterParamName.setData(param.getUrlName(), label);
							}
						}

					}
				}
			}
		}
	}
	private HashMap<String, String> fillDocInfoUtil(){
		metaDoc = Activator.getDefault().getMetadataDocumentComposition();
		if(metaDoc != null){
			Vector docs = metaDoc.getMetadataDocuments();
			if(docs != null){						
				for(int i=0; i<docs.size(); i++){
					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);							
					String docLabel = doc.getLabel();
					String docName = doc.getName();
					docInfoUtil.put(docName, docLabel);
					//e il contrario!
					docInfoUtil.put(docLabel, docName);
				}
			}
		}
		return null;
	}

	private Parameter getNavigationItem(final Composite composite){
		Parameter param = null;

		IStructuredSelection selection =((SpagoBIModifyNavigationWizard)getWizard()).getSelection();
		Object objSel = selection.toList().get(0);
		Table listOfNavigations = (Table)objSel;
		TableItem[] itemsSel = listOfNavigations.getSelection();
		final GridLayout gl2 = new GridLayout();
		gl2.numColumns = 3;
		gl2.marginHeight =3;

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 200;
		gridData.minimumWidth =200;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		if(itemsSel != null && itemsSel.length != 0){

			final DocumentComposition docComp = Activator.getDefault().getDocumentComposition();
			if(docComp != null && docComp.getDocumentsConfiguration() != null){
				Vector docs = docComp.getDocumentsConfiguration().getDocuments();
				if(docs != null){
					for(int i=0; i<docs.size(); i++){
						Parameters params = ((Document)docs.elementAt(i)).getParameters();
						if(params != null){
							for (int j = 0; j<params.getParameter().size(); j++){
								param = params.getParameter().elementAt(j);
								String navigName = itemsSel[0].getText();

								if(param.getType() != null && param.getType().equalsIgnoreCase("OUT") 
										&& param.getNavigationName()!= null 
										&& param.getNavigationName().equals(navigName)){
									navigationNameText.setText(navigName);
									navigationNameText.setEditable(false);

									String masterDoc = ((Document)docs.elementAt(i)).getSbiObjLabel();
									String masterParam = param.getSbiParLabel();
									String masterParamDefault = param.getDefaultVal();


									masterDocName.setText(docInfoUtil.get(masterDoc));
									masterDocName.setEditable(false);

									fillMasterParamsData(masterDoc);

									//masterParamName.setText(masterParam);
									String labelMaster = (String)masterParamName.getData(masterParam);
									if(labelMaster == null){
										//edited by user
										labelMaster=masterParam;
									}
									masterParamName.setText(labelMaster);
									masterParamName.setEditable(false);

									masterDefaultValueOutputParam.setText(masterParamDefault);
									masterDefaultValueOutputParam.setEditable(false);

									//cicla su destinazioni
									Refresh refresh = param.getRefresh();
									Vector<RefreshDocLinked> destinations = refresh.getRefreshDocLinked();

									for(int k =0; k<destinations.size(); k++){

										//secondo composite
										final Composite composite2 = new Composite(composite, SWT.NONE);
										composite2.setLayout(gl2);

										destinCounter++;

										final RefreshDocLinked destin = destinations.get(k);


										String docDest = destin.getLabelDoc();
										final String docDestParam = destin.getLabelParam();


										//inserisce blocco x destinazione
										Composite destBlock = new Composite(composite2, SWT.BORDER | SWT.FILL);
										GridLayout glBlock = new GridLayout();
										glBlock.numColumns = 3;
										destBlock.setLayout(glBlock);

										new Label(destBlock, SWT.NONE).setText("Destination document:");	
										destinationDocNameCombo.addElement(new Combo(destBlock, SWT.BORDER |SWT.READ_ONLY ));

										destinationDocNameCombo.elementAt(destinCounter).setLayoutData(gridData);
										destinationDocNameCombo.elementAt(destinCounter).setVisible(true);

										fillDestinationCombo(docDest, destinCounter);

										//crea una nuovo output text
										new Label(destBlock, SWT.NONE).setText("Input parameter:");
										Combo newText =new Combo(destBlock, SWT.BORDER |SWT.READ_ONLY);


										destinationInputParam.addElement(newText);
										newText.setLayoutData(gridData);
										fillDestinationParamCombo(docDest, destinCounter, docDestParam);

										new Label(destBlock, SWT.NONE).setText("Default value:");
										destinationInputParamDefaultValue.addElement(new Text(destBlock, SWT.BORDER));
										destinationInputParamDefaultValue.elementAt(destinCounter).setLayoutData(gridData);
										setDefaultValue(docs,destinCounter,docDest,docDestParam);

										final int element = k;

										destinationDocNameCombo.elementAt(element).addModifyListener(new ModifyListener() {
											public void modifyText(ModifyEvent event) {
												Combo selectedCombo = (Combo) event.widget;
												//ricavo dal vettore di combo la sua posizione
												int destinComboToRedraw = destinationDocNameCombo.indexOf(selectedCombo);
												//controlla se destinazione precedentem selezionata
												boolean canSelect = canSelectDestination(selectedCombo.getText(), destinComboToRedraw);
												if(!canSelect){
													//messaggio di errore in dialog

													selectedCombo.deselect((selectedCombo).getSelectionIndex());
													final boolean[] result = new boolean[1];
													Shell confirm = createErrorDialog(composite2.getParent(), result, 1);
													confirm.setText("Error");
													confirm.setSize(300,100);
													confirm.open();
												}else{

													name = destinationDocNameCombo.elementAt(element).getText();
													destinationInfo = destinationInfos.get(element);
													destinationInfo.setDocDestName(name);
													destinationInputParam.elementAt(destinComboToRedraw).removeAll();
													String label = docInfoUtil.get(name);

													fillDestinationParamCombo(label, destinComboToRedraw, docDestParam);
													destinationInputParam.elementAt(destinComboToRedraw).redraw();
												}

											}
										});		
										destinationInputParam.elementAt(element).addModifyListener(new ModifyListener() {
											public void modifyText(ModifyEvent event) {
												//verifica se è già presente...
												ParameterBO bo = new ParameterBO();
												String docLab =docInfoUtil.get(destinationDocNameCombo.elementAt(element).getText());
												//				    							boolean exists = bo.inputParameterExists(docComp, docLab, destinationInputParam.elementAt(element).getText());
												//				    							if(exists){
												//			    					        		
												//				    								final boolean[] result = new boolean[1];
												//				    						        Shell confirm = createConfirmDialog(composite2.getParent(), result, element, destin);				    						        
												//				    								confirm.open();
												//
												//				    							}else
												//				    							{						    					

												destinationInfo = destinationInfos.get(element);
												//destinationInfo.setParamDestName(destinationInputParam.elementAt(element).getText());
												String label = destinationInputParam.elementAt(element).getText();
												String urlName= (String)destinationInputParam.elementAt(element).getData(label);
												destinationInfo.setParamDestName(urlName);
												destinationInfo.setParamDestId(destin.getIdParam());
												//				    							}

											}
										});	
										destinationInputParamDefaultValue.elementAt(element).addModifyListener(new ModifyListener() {
											public void modifyText(ModifyEvent event) {

												destinationInfo = destinationInfos.get(element);
												destinationInfo.setParamDefaultValue(destinationInputParamDefaultValue.elementAt(element));
												destinationInfo.setParamDestId(destin.getIdParam());

											}
										});	
										//pulsante delete
										GridData gd = new GridData(GridData.FILL);
										gd.horizontalSpan = 1;
										Button deleteDestButton = new Button(composite2, SWT.PUSH) ;
										deleteDestButtonVector.add(deleteDestButton);
										deleteDestButton.setText("Delete");
										deleteDestButton.setLayoutData(gd);
// set the counter id!!
										deleteDestButton.setData((Integer)destinCounter);
										
										Listener deleteListener = new Listener() {
											public void handleEvent(Event event) {
												switch (event.type) {
												case SWT.Selection:
													if(destinationDocNameCombo.size() == 1){
														//non è possibile cancellare destination
														final boolean[] result = new boolean[1];
														Shell confirm = createErrorDialog(composite2.getParent(), result, 3);
														confirm.setText("Error");
														confirm.setSize(300,100);
														confirm.open();

													}else{
														
//														I must get the selected combo!
														Button b = (Button)event.widget;
														Integer numero = (Integer)b.getData();
														int selectionIndex = destinationDocNameCombo.elementAt(numero).getSelectionIndex();
														
														name = destinationDocNameCombo.elementAt(numero).getItem(selectionIndex);
														String nomen = destinationDocNameCombo.elementAt(numero).getItem(selectionIndex);

														deletedParams.put(destin.getIdParam(), name);
														deleteDestination(numero, composite2);

														for(int i=0; i<=destinCounter;i++){
															refillDestinationCombo(null, i);
														}

														// after delete if remains only one make not visible all delete buttons!
														if(destinationDocNameCombo.size() == 1){
															for (Iterator iterator = deleteDestButtonVector.iterator(); iterator.hasNext();) {
																Button button = (Button) iterator.next();
																if(!button.isDisposed())
																	button.setVisible(false);
															}	
														}
														
														// move index of other button:
														for (Iterator iterator = deleteDestButtonVector.iterator(); iterator.hasNext();) {
															Button button = (Button) iterator.next();
															if (!button.isDisposed()){
																Integer data = (Integer)button.getData();
																if(data.intValue() > numero.intValue()){
																	button.setData(new Integer(data.intValue()-1));
																}
															}
														}
														

													}
													composite.pack();
													composite.redraw();
												}

											}
										};
										deleteDestButton.addListener(SWT.Selection, deleteListener);

										destinationInfo = new DestinationInfo();

										destinationInfo.setDocDestName(destinationDocNameCombo.elementAt(destinCounter).getText());				
										//destinationInfo.setParamDestName(destinationInputParam.elementAt(destinCounter).getText());
										String label = destinationInputParam.elementAt(destinCounter).getText();
										String urlName= (String)destinationInputParam.elementAt(destinCounter).getData(label);
										destinationInfo.setParamDestName(urlName);
										destinationInfo.setParamDefaultValue(destinationInputParamDefaultValue.elementAt(destinCounter));

										destinationInfos.add(destinationInfo);

										composite2.pack();
										composite2.redraw();
									}				    				
									//fine

								}	

							}

							setPageComplete(true);

						}	
					}
				}
			}
		}
		composite.pack();
		composite.layout();
		return param;
	}

	private void deleteDestination(int selectionIndex, Composite toDispose){
		destinCounter--;
		destinationInfos.remove(selectionIndex);
		destinationDocNameCombo.remove(selectionIndex);
		destinationInputParam.remove(selectionIndex);
		destinationInputParamDefaultValue.remove(selectionIndex);
		Composite parent =toDispose.getParent();
		toDispose.dispose();


	}

	private void setDefaultValue(Vector<Document> docs, int destinPos, String destDoc, String parLabel){
		if(docs != null){
			for(int i=0; i<docs.size(); i++){
				Document doc = (Document)docs.elementAt(i);
				if(doc.getSbiObjLabel().equals(destDoc)){
					Parameters params = (doc).getParameters();
					if(params != null){
						for (int j = 0; j<params.getParameter().size(); j++){
							Parameter param = params.getParameter().elementAt(j);
							if(param.getSbiParLabel().equals(parLabel )&& param.getType().equals("IN")){
								destinationInputParamDefaultValue.elementAt(destinPos).setText(param.getDefaultVal()!= null ? param.getDefaultVal() : "");
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
		}else if(messageType == 3){
			message = "Operation denied.";
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

	protected Shell createConfirmDialog(Composite client, final boolean[] result, int element, RefreshDocLinked destin){
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

		final int elem =element;
		final String refreshID =destin.getIdParam();

		Listener dialogListener = new Listener() {
			public void handleEvent(Event event) {
				result[0] = event.widget == ok;
				confirm.notifyListeners(event.type, event);
				confirm.close();
				if(result[0]){
					destinationInfo = destinationInfos.get(elem);
					//destinationInfo.setParamDestName(destinationInputParam.elementAt(elem).getText());
					String label = destinationInputParam.elementAt(elem).getText();
					String urlName= (String)destinationInputParam.elementAt(elem).getData(label);
					destinationInfo.setParamDestName(urlName);
					destinationInfo.setParamDestId(refreshID);
				}
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
	public Text getNavigationNameText() {
		return navigationNameText;
	}
	public void setNavigationNameText(Text navigationNameText) {
		this.navigationNameText = navigationNameText;
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
	public Text getMasterDocName() {
		return masterDocName;
	}
	public void setMasterDocName(Text masterDocName) {
		this.masterDocName = masterDocName;
	}
	public Text getMasterParamName() {
		return masterParamName;
	}
	public void setMasterParamName(Text masterParamName) {
		this.masterParamName = masterParamName;
	}
	public Vector<DestinationInfo> getDestinationInfos() {
		return destinationInfos;
	}
	public void setDestinationInfos(Vector<DestinationInfo> destinationInfos) {
		this.destinationInfos = destinationInfos;
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
	public Text getMasterDefaultValueOutputParam() {
		return masterDefaultValueOutputParam;
	}
	public void setMasterDefaultValueOutputParam(Text masterDefaultValueOutputParam) {
		this.masterDefaultValueOutputParam = masterDefaultValueOutputParam;
	}
	public HashMap<String, String> getDocInfoUtil() {
		return docInfoUtil;
	}
	public void setDocInfoUtil(HashMap<String, String> docInfoUtil) {
		this.docInfoUtil = docInfoUtil;
	}
	public HashMap<String, String> getDeletedParams() {
		return deletedParams;
	}
	public void setDeletedParams(HashMap<String, String> deletedParams) {
		this.deletedParams = deletedParams;
	}

	public HashMap<String, String> getDeletedRefresh() {
		return deletedRefresh;
	}
	public void setDeletedRefresh(HashMap<String, String> deletedRefresh) {
		this.deletedRefresh = deletedRefresh;
	}

}
