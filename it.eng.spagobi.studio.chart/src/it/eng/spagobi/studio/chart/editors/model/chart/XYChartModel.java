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
package it.eng.spagobi.studio.chart.editors.model.chart;

import it.eng.spagobi.studio.chart.editors.ChartEditor;
import it.eng.spagobi.studio.chart.editors.ChartEditorComponents;
import it.eng.spagobi.studio.chart.editors.ChartEditorUtils;
import it.eng.spagobi.studio.chart.utils.ZRanges;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XYChartModel extends ChartModel {

	private static Logger logger = LoggerFactory.getLogger(XYChartModel.class);
	Vector<String> yRanges;

	TreeMap<String, ZRanges> zRanges;

	public void eraseSpecificParameters() {
		yRanges=new Vector<String>();
		zRanges=new TreeMap<String, ZRanges>();
		super.eraseSpecificParameters();
	}


	public XYChartModel(String type, String subType_, IFile thisFile, Document configDocument_) throws Exception {
		super(type, subType_, thisFile, configDocument_);
		yRanges=new Vector<String>();
		zRanges=new TreeMap<String, ZRanges>();
		// register intervals, 

		fillXYRanges(type, thisDocument);

	}









	public void fillXYRanges(String type, Document thisDocument){
		// Search in present template and fill the field from presentDocument, otherwise from templateDocument
		logger.debug("Recording and Filling ranges from file");

		Node yRangesNode=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/YRANGES");
		if(yRangesNode!=null){
			ChartEditorUtils.print("Doc to insert", thisDocument);
			List<Node> allYRanges=thisDocument.selectNodes("//"+type.toUpperCase()+"/YRANGES/RANGE");
			// run intervals found in actual document or in template and record them in model
			for (Iterator iterator = allYRanges.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				String label=node.valueOf("@label");
				if(label!=null && !label.equalsIgnoreCase("")){
					this.yRanges.add(label);
				}
			}
		}

		Node zRangesNode=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/ZRANGES");
		if(zRangesNode!=null){
			ChartEditorUtils.print("Doc to insert", thisDocument);
			List<Node> allZRanges=thisDocument.selectNodes("//"+type.toUpperCase()+"/ZRANGES/RANGE");
			// run intervals found in actual document or in template and record them in model
			for (Iterator iterator = allZRanges.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				ChartEditorUtils.print("", node);
				String label=node.valueOf("@label");
				String valueLow=node.valueOf("@value_low");
				String valueHigh=node.valueOf("@value_high");
				String color=node.valueOf("@colour");			
				if(label==null || label.equalsIgnoreCase("")){
					logger.warn("not accepted z range without label");
				}
				else{
					ZRanges zRange=new ZRanges();	
					zRange.setLabel(label);

					if(valueLow!=null){
						Double minValD;
						try{
							minValD=Double.valueOf(valueLow);
						}
						catch (Exception e) {
							logger.error("Not integer format for low value in range; set default 0", e);				
							minValD=new Double(0);
						}
						zRange.setValueLow(minValD);
					}
					if(valueHigh!=null){
						Double maxValD;
						try{
							maxValD=Double.valueOf(valueHigh);
						}
						catch (Exception e) {
							logger.error("Not Integer format for high parameter in interval; set default 0", e);				
							maxValD=new Double(0);
						}
						zRange.setValueHigh(maxValD);
					}
					if(color!=null){
						zRange.setColor(ChartEditor.convertHexadecimalToRGB(color));
					}

					// Add interval to Vector

					this.zRanges.put(zRange.getLabel(),zRange);

				} // label not null
			} // iterate over ranges
		} // if zRanges

	}







	@Override
	public void initializeEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		// TODO Auto-generated method stub
		super.initializeEditor(editor, components, toolkit, form);
		//fillIntervalsInformation(type, thisDocument, templateDocument);

		components.createYZRangesSection(this, toolkit, form);
		components.getYzRangesEditor().setVisible(true);

	}




	@Override
	public void refreshEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		eraseSpecificParameters();
		super.refreshEditor(editor, components, toolkit, form);
		logger.debug("Erase fields of editor");
		components.getYzRangesEditor().eraseComposite();
		fillXYRanges(type, thisDocument);
		components.getYzRangesEditor().refillFieldsSeriesPersonalization(this,editor, toolkit, form);							
		components.getYzRangesEditor().setVisible(true);
	}



	@Override
	public String toXML() {
		String toReturn="";
		logger.debug("Write XML for Model");
		toReturn = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
		if(subType==null) {
			logger.error("Sub Type not defined");
			return "";
		}

		logger.debug("General settings");

		//intestazione
		toReturn+="<XYCHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();

		// Print ranges
		toReturn+="<YRANGES>\n";
		if(yRanges.size()>0){
			for (Iterator iterator = yRanges.iterator(); iterator.hasNext();) {
				String yRange = (String) iterator.next();
				toReturn+="<RANGE label='"+yRange+"' />\n";
			}
		}
		toReturn+="</YRANGES>\n";

		toReturn+="<ZRANGES>\n";
		if(zRanges.keySet().size()>0){
			for (Iterator iterator = zRanges.keySet().iterator(); iterator.hasNext();) {
				String rangeLabel = (String) iterator.next();
				ZRanges range=zRanges.get(rangeLabel);
				toReturn+=range.toXML();

			}
		}
		toReturn+="</ZRANGES>\n";

		toReturn+="</XYCHART>\n";

		logger.debug("Final Template is\n:" + toReturn);
		return toReturn;
	}


	public Vector<String> getYRanges() {
		return yRanges;
	}


	public void setYRanges(Vector<String> ranges) {
		yRanges = ranges;
	}


	public TreeMap<String, ZRanges> getZRanges() {
		return zRanges;
	}


	public void setZRanges(TreeMap<String, ZRanges> ranges) {
		zRanges = ranges;
	}






}
