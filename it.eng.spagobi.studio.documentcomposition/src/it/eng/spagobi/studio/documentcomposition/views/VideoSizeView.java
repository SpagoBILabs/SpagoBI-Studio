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

import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.ui.part.ViewPart;

public class VideoSizeView extends ViewPart{

	Composite client;
	Spinner heightSpin=null;
	Spinner widthSpin=null;

	@Override
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		// Lets make a layout for the first section of the screen
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		// Creating the Screen
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText("Video Size Properties"); //$NON-NLS-1$
		client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);

		int width=1000;
		int height=500;
		//final DocumentComposition documentComposition=(new ModelBO()).getModel();
		final ModelBO modelBO=new ModelBO();
		if(modelBO.getModel()!=null && modelBO.getModel().getDocumentsConfiguration()!=null){
			DocumentsConfiguration documentsConfiguration=modelBO.getModel().getDocumentsConfiguration();
			String heightS=documentsConfiguration.getVideoHeight();
			String widthS=documentsConfiguration.getVideoWidth();
			if(widthS!=null){
				width=Integer.valueOf(widthS);
			}
			if(heightS!=null){
				height=Integer.valueOf(heightS);
			}
		}

		Label text1=new org.eclipse.swt.widgets.Label(client, SWT.NULL);
		text1.setText("Video Width: ");

		widthSpin = new Spinner (client, SWT.BORDER);
		widthSpin.setMaximum(100000);
		widthSpin.setMinimum(0);
		widthSpin.setSelection(Integer.valueOf(width));
		//styleSizeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		widthSpin.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				int newSize = widthSpin.getSelection();
				Integer newSizeInt=null;
				try{
					newSizeInt=Integer.valueOf(newSize);
				}
				catch (Exception e) {
					newSizeInt=new Integer(10);
				}
				if(modelBO.getModel()!=null && modelBO.getModel().getDocumentsConfiguration()!=null){
					modelBO.getModel().getDocumentsConfiguration().setVideoWidth(newSizeInt.toString());
				}
				IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);
				if(editorPart!=null) ((DocumentCompositionEditor)editorPart).setIsDirty(true);				
			}
		});

		
		Label text2=new Label(client, SWT.NULL);
		text2.setText("Video Height: ");

		heightSpin = new Spinner (client, SWT.BORDER);
		heightSpin.setMaximum(100000);
		heightSpin.setMinimum(0);
		heightSpin.setSelection(Integer.valueOf(height));
		//styleSizeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		heightSpin.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				int newSize = heightSpin.getSelection();
				Integer newSizeInt=null;
				try{
					newSizeInt=Integer.valueOf(newSize);
				}
				catch (Exception e) {
					newSizeInt=new Integer(10);
				}
				if(modelBO.getModel()!=null && modelBO.getModel().getDocumentsConfiguration()!=null){
					modelBO.getModel().getDocumentsConfiguration().setVideoHeight(newSizeInt.toString());
				}
				IEditorPart editorPart=DocCompUtilities.getEditorReference(DocCompUtilities.DOCUMENT_COMPOSITION_EDITOR_ID);
				if(editorPart!=null) ((DocumentCompositionEditor)editorPart).setIsDirty(true);
				
			}
		});

		client.pack();
		section.setClient(client);



	}

	public void reloadSizes(){
		DocumentComposition docCompModel=new ModelBO().getModel();
		if(docCompModel!=null && docCompModel.getDocumentsConfiguration()!=null){
			String heightS=docCompModel.getDocumentsConfiguration().getVideoHeight();
			String widthS=docCompModel.getDocumentsConfiguration().getVideoWidth();
			int height=Integer.valueOf(heightS);
			int width=Integer.valueOf(widthS);
			heightSpin.setSelection(height);
			widthSpin.setSelection(width);
		}		
	}


	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		// TODO Auto-generated method stub
		super.init(site);
	}



}
