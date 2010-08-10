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
package it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata;


import org.eclipse.swt.widgets.Composite;

import it.eng.spagobi.studio.documentcomposition.editors.Designer;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Style;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ModelBO;

public class MetadataStyle {

	private int x;
	private int y;
	private int width;
	private int height;




	public MetadataStyle(Style styleS) {
		String style=styleS.getStyle();
		int indexTop=style.indexOf("top:");

		int indexOfPXAfterTop=style.indexOf("px;", indexTop);
		String topValue=style.substring(indexTop+4, indexOfPXAfterTop);

		String leftValue="";
		int indexLeft=style.indexOf("left:");
		if(indexLeft==-1){
			indexLeft=style.indexOf("margin:");
			int indexOfPXAfterLeft=style.indexOf("px;", indexLeft);
			leftValue=style.substring(indexLeft+7, indexOfPXAfterLeft);

		}
		else{
			int indexOfPXAfterLeft=style.indexOf("px;", indexLeft);
			leftValue=style.substring(indexLeft+5, indexOfPXAfterLeft);
		}



		//		int indexWidth=style.indexOf("width:");
		//		int indexOfPXAfterWidth=style.indexOf("%;", indexWidth);
		//		String widthValue=style.substring(indexWidth+6, indexOfPXAfterWidth);
		//
		//
		//		int indexHeight=style.indexOf("height:");
		//		int indexOfPXAfterHeight=style.indexOf("%;", indexHeight);
		//		String heightValue=style.substring(indexHeight+7, indexOfPXAfterHeight);


		int indexWidth=style.indexOf("width:");
		int indexOfPXAfterWidth=style.indexOf("px;", indexWidth);
		String widthValue=style.substring(indexWidth+6, indexOfPXAfterWidth);


		int indexHeight=style.indexOf("height:");
		int indexOfPXAfterHeight=style.indexOf("px;", indexHeight);
		String heightValue=style.substring(indexHeight+7, indexOfPXAfterHeight);


		x=Integer.valueOf(leftValue).intValue();
		y=Integer.valueOf(topValue).intValue();
		width=Integer.valueOf(widthValue).intValue();

		//if template has been made with SpagoBISTudio  add 1
		// but not if was created with manual style!
		//		if(new MetadataBO().getMetadataDocumentComposition().isMadeWithStudio() && (styleS.getMode()==null || !styleS.getMode().equalsIgnoreCase("manual"))){
		//			widthPercentage+=1;
		//		}
		height=Integer.valueOf(heightValue).intValue();




		String videoHeight=(new ModelBO()).getModel().getDocumentsConfiguration().getVideoHeight();
		String videoWidth=(new ModelBO()).getModel().getDocumentsConfiguration().getVideoWidth();
		int videoHeightI=Integer.valueOf(videoHeight).intValue();
		int videoWidthI=Integer.valueOf(videoWidth).intValue();

		// calculate percentage of video size and put
		//		int heightValueConv= (videoHeightI/100)*heightPercentage;
		//		int widthValueConv= (videoWidthI/100)*widthPercentage;
		//		
		//		heightPercentage=heightValueConv;
		//		widthPercentage=widthValueConv;

		// x : 800 (DESIGNER_WIDTH) = mio : videoWidth 
		//		int scaledX=(x*videoWidthI) / Designer.DESIGNER_WIDTH;
		//		int scaledY=(y*videoHeightI) / Designer.DESIGNER_HEIGHT;
		//
		//		x=scaledX;
		//		y=scaledY;

	}

	//	public int getWidthFromPerc(Composite mainComposite){
	//		// calculate the percentage of the whole composite
	//		int mainSize=mainComposite.getBounds().width;
	//		mainSize=mainSize/100;
	//		int toReturn=(mainSize)*widthPercentage;
	//		return toReturn;
	//	}
	//	public int getHeightFromPerc(Composite mainComposite){
	//		// calculate the percentage of the whole composite
	//		int mainSize=mainComposite.getBounds().height;		
	//		mainSize=mainSize/100;		
	//		int toReturn=(mainSize)*heightPercentage;
	//		return toReturn;
	//	}


	// toConvert : videoSize = x : designerSize
	static public int fromVideoHeightToDesignerHeight(int toConvert, int videoSize, int designerSize ){
		int toReturn=(toConvert * designerSize) / videoSize;
		return toReturn;
	}

	static  public int fromVideoWidthToDesignerWidth(int toConvert, int videoSize, int designerSize ){
		int toReturn=(toConvert * designerSize) / videoSize;
		return toReturn;
	}

	// recordedHeight : designerSize = x : videoSize
	static public int fromDesignerHeightToVideoHeight(int recordeHeight, int videoSize, int designerSize ){
		int toReturn=(recordeHeight * videoSize) / designerSize;
		return toReturn;
	}

	// recordedWidth : designerSize = x : videoSize
	static public int fromDesignerWidthToVideoWidth(int recordeWidth, int videoSize, int designerSize ){
		int toReturn=(recordeWidth * videoSize) / designerSize;
		return toReturn;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int _width) {
		this.width = _width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int heightPercentage) {
		this.height = height;
	}



}
