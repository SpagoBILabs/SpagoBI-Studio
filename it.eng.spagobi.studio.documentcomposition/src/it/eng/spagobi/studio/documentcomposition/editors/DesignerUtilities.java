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

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class DesignerUtilities {

	
	public static int getScaledImageWidth(int originalWidth, int targetWidth){
		double rapportoWidth=(double)targetWidth / (double)originalWidth;
		int newWidth=(int)(originalWidth*rapportoWidth);
		return newWidth;
	}
	
	public static int getScaledImageHeight(int originalHeight, int targetHeight){
		double rapportoHeight=(double)targetHeight / (double)originalHeight;
		int newHeight=(int)(originalHeight*rapportoHeight);
		return newHeight;
	}



	public int calculateWidth(Composite currentComposite, int containerSize){
		// if componentSIze is almost the container size round it to that!		
		int width=currentComposite.getBounds().width;
		int x=currentComposite.getBounds().x;

		// total x
		int totalX=width+x;
		int toReturn;
		if((containerSize-totalX)<=DocContainer.ALIGNMENT_MARGIN){
			// add difference between actual width and remaining with limit
			toReturn=width+((containerSize-totalX));
		}
		else{		// else round it to less
			toReturn=width/DocContainer.ALIGNMENT_MARGIN;
			toReturn=toReturn*DocContainer.ALIGNMENT_MARGIN;			
		}
		return toReturn;
	}
	
	
	public int calculateHeight(Composite currentComposite, int containerSize){
		// if componentSIze is almost the container size round it to that!		
		int height=currentComposite.getBounds().height;
		int y=currentComposite.getBounds().y;

		// total y
		int totalY=height+y;
		int toReturn;
		if((containerSize-totalY)<=DocContainer.ALIGNMENT_MARGIN){
			// add difference between actual width and remaining with limit
			toReturn=height+((containerSize-totalY));
		}
		else{		// else round it to less
			toReturn=height/DocContainer.ALIGNMENT_MARGIN;
			toReturn=toReturn*DocContainer.ALIGNMENT_MARGIN;			
		}
		return toReturn;
	}

	public static void printPointer(String nomeEvento,Event event, Rectangle container, int x, int y){
//		System.out.println("----------"+nomeEvento+"-----------------");
		if(event!=null){
//			System.out.println("Evento: "+event.x+"/"+event.y);
		}
		if(container!=null){
//			System.out.println("Container: "+container.x+"/"+container.y);			
		}
//		System.out.println("Temporaneo: "+x+"/"+y);
//		System.out.println("---------------------------");
	}

}
