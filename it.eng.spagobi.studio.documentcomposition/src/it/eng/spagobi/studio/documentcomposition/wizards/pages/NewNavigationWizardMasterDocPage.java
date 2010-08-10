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
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataParameter;
import it.eng.spagobi.studio.documentcomposition.wizards.SpagoBINavigationWizard;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNavigationWizardMasterDocPage extends WizardPage {


	String name = "";
	String masterLabel = "";
	String paramOut ="";
	private ParameterBO bo = new ParameterBO();
	
	HashMap <String, String> docInfoUtil;

	Composite composite;
	Combo masterDocName;
	Combo masterDocOutputParam;
	Text masterDefaultValueOutputParam;
	
	boolean canFlip = true;
	
	private MetadataDocumentComposition metaDoc;

	public NewNavigationWizardMasterDocPage() {
		super("New Document - Master document");
		setTitle("Insert Master document");
	}

	public NewNavigationWizardMasterDocPage(String pageName) {
		super(pageName);
		setTitle("Insert Master document");
	}
	@Override
	public boolean canFlipToNextPage() {
		
		if ((masterDocName.getText() == null) && (masterDocOutputParam.getText() == null)) {
			return false;		
		}else if((canFlip && masterDocOutputParam.getText().length() != 0 || masterDocOutputParam.getSelectionIndex() != -1) && masterDocName.getSelectionIndex() != -1){
			return true;
		}else{
			return false;
		}

	}

	protected Shell createErrorDialog(Composite client, final boolean[] result){
		final Shell error = new Shell(client.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		error.setLayout(new GridLayout(3, false));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;

		error.setSize(300, 100);
		Point pt = client.getDisplay().getCursorLocation ();
		error.setLocation (pt.x, pt.y);

		String message = "Operation denied! Master output parameter already exists.";
		
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
	
	public void createControl(Composite parent) {
		
		composite = new Composite(parent, SWT.BORDER
				| SWT.NO_REDRAW_RESIZE);
		composite.setSize(600, 400);
		final GridLayout gl = new GridLayout();

		int ncol = 3;
		gl.numColumns = ncol;

		composite.setLayout(gl);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		
		new Label(composite, SWT.NONE).setText("Master document:");
		masterDocName = new Combo(composite, SWT.BORDER |SWT.READ_ONLY );
		fillMasterCombo();
		masterDocName.setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		gd.widthHint = 200;

		// fielset per parametri output
		new Label(composite, SWT.NONE).setText("Ouput parameter:");
		masterDocOutputParam = new Combo(composite, SWT.BORDER );
		masterDocOutputParam.setLayoutData(gd);
		
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		masterDocName.setLayoutData(gd);
		
		new Label(composite, SWT.NONE).setText("Default value:");
		masterDefaultValueOutputParam = new Text(composite, SWT.BORDER);
		masterDefaultValueOutputParam.setLayoutData(gd);

		masterDocName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				name = masterDocName.getText();
				SpagoBINavigationWizard wizard = (SpagoBINavigationWizard)getWizard();
				wizard.setSelectedMaster(masterDocName.getText());
				fillMasterParamCombo(name);
				masterLabel = docInfoUtil.get(name);
				
			}
		});
		
		masterDocOutputParam.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {	
				//verifico che il parametro di out non sia già presente
				String label = masterDocOutputParam.getText();
				String urlName= (String)masterDocOutputParam.getData(label);
				boolean exists =bo.outputParameterExists(Activator.getDefault().getDocumentComposition(), masterLabel, urlName);
				
				if(exists){
	        		//non è possibile cancellare destination
					final boolean[] result = new boolean[1];
			        Shell confirm = createErrorDialog(composite, result);
			        confirm.setText("Error");
			        confirm.setSize(300,100);
					confirm.open();
					canFlip= false;
					setPageComplete(false);	
					
					
				}else{
					canFlip= true;
					paramOut = urlName;
					setPageComplete(true);					
					
				}
				
			}
		});

		composite.pack();
		composite.redraw();
		setControl(composite);
		setPageComplete(false);
		
	}



	private void fillMasterCombo(){
		docInfoUtil = new HashMap<String, String>();
		metaDoc = Activator.getDefault().getMetadataDocumentComposition();
		if(metaDoc != null){
			Vector docs = metaDoc.getMetadataDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){

					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
					String masterName = doc.getName();
					String masterLabel = doc.getLabel();
					if(masterName != null && !masterName.equals("")){
						masterDocName.add(masterName);	
						docInfoUtil.put(masterName, masterLabel);
					}
				}
			}
		}
	}
	
	private void fillMasterParamCombo(String masterDoc){
		masterDocOutputParam.removeAll();
		if(metaDoc != null){
			Vector docs = metaDoc.getMetadataDocuments();
			if(docs != null){
				for(int i=0; i<docs.size(); i++){
					MetadataDocument doc = (MetadataDocument)docs.elementAt(i);
					String masterName = doc.getName();
					if(masterName != null && !masterName.equals("") &&(masterName.equals(masterDoc))){
						Vector params = doc.getMetadataParameters();
						if(params != null){
							for (int j =0; j<params.size(); j++){
								MetadataParameter param = (MetadataParameter)params.elementAt(j);
								String label = param.getLabel();
								masterDocOutputParam.add(label);
								masterDocOutputParam.setData(label, param.getUrlName());
							}
						}
					}
				}
			}
		}
		masterDocOutputParam.redraw();
	}
	public Text getMasterDefaultValueOutputParam() {
		return masterDefaultValueOutputParam;
	}

	public void setMasterDefaultValueOutputParam(Text masterDefaultValueOutputParam) {
		this.masterDefaultValueOutputParam = masterDefaultValueOutputParam;
	}

	public Combo getMasterDocName() {
		return masterDocName;
	}

	public void setMasterDocName(Combo masterDocName) {
		this.masterDocName = masterDocName;
	}
	public Combo getMasterDocOutputParam() {
		return masterDocOutputParam;
	}

	public String getName() {
		return name;
	}

	public String getParamOut() {
		return paramOut;
	}

	public HashMap<String, String> getDocInfoUtil() {
		return docInfoUtil;
	}

	public void setDocInfoUtil(HashMap<String, String> docInfoUtil) {
		this.docInfoUtil = docInfoUtil;
	}

	public String getMasterLabel() {
		return masterLabel;
	}

	public void setMasterLabel(String masterLabel) {
		this.masterLabel = masterLabel;
	}


}
