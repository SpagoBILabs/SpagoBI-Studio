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
import it.eng.spagobi.studio.chart.utils.Interval;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialChartModel extends ChartModel {

	private static Logger logger = LoggerFactory.getLogger(DialChartModel.class);

	Vector<Interval> intervals;

	public final static String THERMOMETHER="thermomether";

	public void eraseSpecificParameters() {
		intervals=new Vector<Interval>();
		super.eraseSpecificParameters();
	}


	public DialChartModel(String type, String subType_, IFile thisFile, Document configDocument_) throws Exception {
		super(type, subType_, thisFile, configDocument_);
		intervals=new Vector<Interval>();
		// register intervals, 
		fillIntervalsInformation(type, thisDocument);
	}


	public void fillIntervalsInformation(String type, Document thisDocument){
		// Search in present template and fill the field from presentDocument, otherwise from templateDocument
		logger.debug("Recording and Filling intervals from file or from template");

		Node intervalsN=thisDocument.selectSingleNode("//"+type.toUpperCase()+"/INTERVALS");
		if(intervalsN!=null){
			ChartEditorUtils.print("Doc to insert", thisDocument);
			List<Node> allIntervals=thisDocument.selectNodes("//"+type.toUpperCase()+"/INTERVALS/INTERVAL");
			// run intervals found in actual document or in template and record them in model
			for (Iterator iterator = allIntervals.iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				Interval interval=new Interval();
				String label=node.valueOf("@label");
				String min=node.valueOf("@min");
				String max=node.valueOf("@max");
				String color=node.valueOf("@color");			
				if(label!=null){
					interval.setLabel(label);
				}
				if(min!=null){
					Double minValD;
					try{
						minValD=Double.valueOf(min);
					}
					catch (Exception e) {
						logger.error("Not double format for min parameter in interval; set default 0", e);				
						minValD=new Double(0);
					}
					interval.setMin(minValD);
				}
				if(max!=null){
					Double maxValD;
					try{
						maxValD=Double.valueOf(max);
					}
					catch (Exception e) {
						logger.error("Not double format for max parameter in interval; set default 0", e);				
						maxValD=new Double(0);
					}
					interval.setMax(maxValD);
				}
				if(color!=null){
					interval.setColor(ChartEditor.convertHexadecimalToRGB(color));
				}
				// Add interval to Vector

				this.intervals.add(interval);
			}
		}
	}




	@Override
	public void initializeEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		// TODO Auto-generated method stub
		super.initializeEditor(editor, components, toolkit, form);
		//fillIntervalsInformation(type, thisDocument, templateDocument);

		components.createIntervalsInformationsSection(this, editor, toolkit, form);
		components.getIntervalsInformationEditor().setVisible(true);

	}




	@Override
	public void refreshEditor(ChartEditor editor,
			ChartEditorComponents components, FormToolkit toolkit,
			ScrolledForm form) throws Exception {
		eraseSpecificParameters();
		super.refreshEditor(editor, components, toolkit, form);
		logger.debug("Erase fields of editor");
		components.getIntervalsInformationEditor().eraseComposite();
		//fillIntervalsInformation(type, thisDocument);
		components.getIntervalsInformationEditor().refillFieldsIntervalsInformation(this,editor, toolkit, form);							
		components.getIntervalsInformationEditor().setVisible(true);
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
		toReturn+="<DIALCHART type=\""+this.subType+"\" name=\""+this.title+"\">\n";

		toReturn+=super.toXML();

		// Print intervals
		toReturn+="<INTERVALS>\n";
		for (Iterator iterator = intervals.iterator(); iterator.hasNext();) {
			Interval interval = (Interval) iterator.next();
			toReturn+=interval.toXML()+"\n";
		}

		toReturn+="</INTERVALS>\n";

		toReturn+="</DIALCHART>\n";

		logger.debug("Final Template is\n:" + toReturn);
		return toReturn;
	}


	public Vector<Interval> getIntervals() {
		return intervals;
	}


	public void setIntervals(Vector<Interval> intervals) {
		this.intervals = intervals;
	}




}
