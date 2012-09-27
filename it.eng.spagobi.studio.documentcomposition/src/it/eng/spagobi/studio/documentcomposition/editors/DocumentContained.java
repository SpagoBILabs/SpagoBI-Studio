/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition.editors;


import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataBO;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocument;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;
import it.eng.spagobi.studio.documentcomposition.util.DocCompUtilities;
import it.eng.spagobi.studio.documentcomposition.util.SpagoBIDocCompCostants;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentContained {

	Group group;	
	String documentLabel;
//	MetadataDocument metadataDocument;	

	Image image=null;
	Image whiteImage=null;
	Image scaledImage=null;
	Image natureImage=null;
	String imagePath=null;
	Designer designer;
	private static Logger logger = LoggerFactory.getLogger(DocumentContained.class);

	public static final String IMG_JASPER_REPORT="it/eng/spagobi/studio/documentcomposition/resources/images/IconReport2.png";
	public static final String IMG_BIRT_REPORT="it/eng/spagobi/studio/documentcomposition/resources/images/IconReport2.png";
	public static final String IMG_DASHBOARD="it/eng/spagobi/studio/documentcomposition/resources/images/IconDashboard2.png";
	public static final String IMG_CHART="it/eng/spagobi/studio/documentcomposition/resources/images/IconChart2.png";
	public static final String IMG_DOCUMENT_COMPOSITION="it/eng/spagobi/studio/documentcomposition/resources/images/IconGeneral2.png";
	public static final String IMG_OLAP="it/eng/spagobi/studio/documentcomposition/resources/images/IconOlap2.png";
	public static final String IMG_ETL="it/eng/spagobi/studio/documentcomposition/resources/images/IconEtl2.png";
	public static final String IMG_OFFICE_DOC="it/eng/spagobi/studio/documentcomposition/resources/images/IconOfficeDoc2.png";
	public static final String IMG_MAP="it/eng/spagobi/studio/documentcomposition/resources/images/IconMap2.png";
	public static final String IMG_DATAMART="it/eng/spagobi/studio/documentcomposition/resources/images/IconGeneral2.png";
	public static final String IMG_DOSSIER="it/eng/spagobi/studio/documentcomposition/resources/images/IconGeneral2.png";
	public static final String IMG_DATA_MINING="it/eng/spagobi/studio/documentcomposition/resources/images/IconGeneral2.png";
	public static final String IMG_GENERAL="it/eng/spagobi/studio/documentcomposition/resources/images/IconGeneral2.png";
	public static final String BACKGROUND="it/eng/spagobi/studio/documentcomposition/resources/images/white.PNG";

	public static final String TYPE_REPORT=SpagoBIDocCompCostants.REPORT_TYPE_CODE;
	public static final String TYPE_DOSSIER="DOSSIER";
	public static final String TYPE_OLAP="OLAP";
	public static final String TYPE_DATA_MINING = "DATA_MINING";
	public static final String TYPE_DASH=SpagoBIDocCompCostants.DASH_TYPE_CODE;
	public static final String TYPE_DATAMART="DATAMART";
	public static final String TYPE_MAP="MAP";
	public static final String TYPE_OFFICE_DOC="OFFICE_DOC";
	public static final String TYPE_ETL="ETL";
	public static final String TYPE_DOCUMENT_COMPOSITIOn=SpagoBIDocCompCostants.DOCUMENT_COMPOSITE_TYPE;


	public DocumentContained(Composite parent, int style, Designer _designer) throws Exception{
		group=new Group(parent, style);
		group.setBackground(new Color(parent.getDisplay(), DocContainer.COLOR_CONTAINERS));
		//group.setForeground(new Color(parent.getDisplay(), new RGB(255,0,0)));
		group.setSize(DocContainer.DEFAULT_WIDTH, DocContainer.DEFAULT_HEIGHT);
		designer=_designer;
	}


	/**
	 *  Get the metadata of the document inside the container and add the document
	 * @param composite
	 */
	public boolean recoverDocumentMetadata(Integer idContainer,IFile file){
		logger.debug("IN");
		try{
			int i=0;
			
			MetadataDocumentComposition metadataDocumentComposition = (new MetadataBO()).getMetadataDocumentComposition();
			
			String id=file.getPersistentProperty(SpagoBIStudioConstants.DOCUMENT_ID);
			if(documentLabel!=null){
				MessageDialog.openWarning(group.getShell(), 
						"Warning", "Container has already Document in!");
				logger.debug("OUT false");
				return false;
			}
			else if(id==null){
				MessageDialog.openWarning(group.getShell(), 
						"Warning", "Chosen file has no SpagoBI document metadata");
				logger.debug("OUT false");
				return false;
			}
			else{		// new document	
				i++;

				String localFileName=file.getName();
				IPath ia=file.getFullPath();
				//String localFileName=ia.toString();
				MetadataDocument metadataDocument=new MetadataDocument(file);				

				
				// check the document not alredy exist
				if(metadataDocument!=null){
					boolean contained=designer.isDocumentContained(idContainer, metadataDocument.getLabel());
					if(contained){
						MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning", "Document already present, cannot insert more times the same document!");
						metadataDocument=null;
						logger.debug("OUT false");
						return false;
					}
				}
				documentLabel = metadataDocument.getLabel();
				
				
				//metadataDocument.setIdMetadataDocument(idContainer+"_"+metadataDocument.getLabel());
				
				metadataDocument.setLocalFileName(localFileName);
				// add new MetadataDocumetn to list
				(new MetadataBO()).getMetadataDocumentComposition().addMetadataDocument(metadataDocument);
				logger.debug("OUT");
				return viewDocumentMetadata(metadataDocument);
			}
		}
		catch (Exception e) {	
			e.printStackTrace();
			logger.error("Exception while retrieving metadata",e);
			return false;
		}


	}


	/**
	 *  VIsualize the metadata of the document inside the container
	 * @param composite
	 */
	public boolean viewDocumentMetadata(MetadataDocument metadataDocument){
		logger.debug("IN");

		// get the image Path

		if(metadataDocument.getType()!=null){
			imagePath=null;
			if(metadataDocument.getType().equalsIgnoreCase(TYPE_REPORT)){
				imagePath=IMG_JASPER_REPORT;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_DASH)){
				// distinguish chart and dashboard
				if(metadataDocument.getEngineLabel() != null && metadataDocument.getEngineLabel().equals(SpagoBIStudioConstants.DASHBOARD_ENGINE_LABEL)){
					imagePath=IMG_DASHBOARD;
				}
				else{
					imagePath=IMG_CHART;	
				}
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_DATA_MINING)){
				imagePath=IMG_DATA_MINING;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_DATAMART)){
				imagePath=IMG_DATAMART;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_DOSSIER)){
				imagePath=IMG_DOSSIER;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_DOCUMENT_COMPOSITIOn)){
				imagePath=IMG_DOCUMENT_COMPOSITION;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_MAP)){
				imagePath=IMG_MAP;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_ETL)){
				imagePath=IMG_ETL;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_OFFICE_DOC)){
				imagePath=IMG_OFFICE_DOC;
			}
			else if(metadataDocument.getType().equalsIgnoreCase(TYPE_OLAP)){
				imagePath=IMG_OLAP;
			}
			else {
				imagePath=IMG_GENERAL;
			}

			drawImage();

			String titleGroup="Name: "+metadataDocument.getName() != null ? metadataDocument.getName() : metadataDocument.getLabel();
			group.setText(titleGroup);
			group.setToolTipText(metadataDocument.getLocalFileName());
			//			Label nameLabelName=new Label(group,SWT.NULL);
			//			nameLabelName.setText("Name: "+metadataDocument.getName() != null ? metadataDocument.getName() : "" );			
		}
		logger.debug("OUT");

		return true;

	}

	public void drawImage(){
		logger.debug("IN");

		InputStream is = null;
		InputStream isW = null;
		try{
			// white background image
			String whiteBgImage = BACKGROUND;
			isW=DocCompUtilities.getInputStreamFromResource(whiteBgImage);
			whiteImage = new Image(group.getDisplay(), isW);
		}
		catch (Exception e) {
			logger.error("COuld not find image "+imagePath, e);
			return;
			// TODO: handle exception
		}
		finally{
			try{
				if(is != null) is.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		try{	

			final String imagePathFinal=imagePath;
			is=DocCompUtilities.getInputStreamFromResource(imagePathFinal);
			if(is != null){
				logger.warn("immagine trovata");
			}
			else{
				logger.warn("immagine NON trovata");					
			}
			image = new Image(group.getDisplay(), is);
		}
		catch (Exception e) {
			logger.error("COuld not find image "+imagePath, e);
			return;
			// TODO: handle exception
		}
		finally{
			try{
				if(isW != null)
					isW.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}	


		if(image==null){
			logger.error("COuld not find image "+imagePath);
			return;
		}
		final int originalWidth = image.getBounds().width;
		final int originalHeight = image.getBounds().height; 				
		final int containerHeight=group.getBounds().height;
		final int containerWidth=group.getBounds().width;


		final double rapportoHeight=(double)containerHeight / (double)originalHeight;
		final double rapportoWidth=(double)containerWidth / (double)originalWidth;


		scaledImage = new Image(group.getDisplay(),
				whiteImage.getImageData().scaledTo((int)(originalWidth*rapportoWidth-20),(int)(originalHeight*rapportoHeight-20)));
		natureImage = new Image(group.getDisplay(), image.getImageData());


		group.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				//				image = null;
				//				try {
				//					InputStream is=DocCompUtilities.getInputStreamFromResource(imagePathFinal);
				//					image = new Image(group.getDisplay(), is);
				//
				//				} catch (FileNotFoundException e1) {
				//					e1.printStackTrace();
				//				}
				//				catch (Exception e2) {
				//
				//					e2.printStackTrace();
				//				}
				if(scaledImage != null){
					System.out.println("scaled "+scaledImage);
					e.gc.drawImage(scaledImage,20,20);
					System.out.println("nature "+natureImage);

					e.gc.drawImage(natureImage,(int)((containerWidth-originalWidth)/2),(int)((containerHeight-originalHeight)/2));
					image.dispose();

				}
				//}
				//				e.gc.drawImage(image, 20, 30);
				//				image.dispose();
			}
		});
		
		group.redraw();
		logger.debug("OUT");

	}




//	public MetadataDocument getMetadataDocument() {
//		return metadataDocument;
//	}
//
//
//	public void setMetadataDocument(MetadataDocument metadataDocument) {
//		this.metadataDocument = metadataDocument;
//	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public Image getScaledImage() {
		return scaledImage;
	}


	public void setScaledImage(Image scaledImage) {
		this.scaledImage = scaledImage;
	}


	public String getDocumentLabel() {
		return documentLabel;
	}


	public void setDocumentLabel(String documentLabel) {
		this.documentLabel = documentLabel;
	}

	
	
	public MetadataDocument getMetadataDocument() {
		MetadataDocument toReturn;
		final MetadataDocumentComposition metadataDocumentComposition = (new MetadataBO()).getMetadataDocumentComposition();
		toReturn = metadataDocumentComposition.findMetadataDocument(getDocumentLabel());	
		return toReturn;
	}


	public void setMetadataDocument(MetadataDocument metadataDocument) {
		MetadataDocument toReturn;
		final MetadataDocumentComposition metadataDocumentComposition = (new MetadataBO()).getMetadataDocumentComposition();
		metadataDocumentComposition.addMetadataDocument(metadataDocument);	
	}

}
