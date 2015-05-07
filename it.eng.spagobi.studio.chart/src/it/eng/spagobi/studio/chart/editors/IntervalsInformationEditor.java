/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.editors.model.chart.DialChartModel;
import it.eng.spagobi.studio.chart.utils.Interval;

import java.util.Iterator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;


/**
 * 
 * @author gavardi
 *
 *	Thios class has method for the Interval Informations form creation
 */

public class IntervalsInformationEditor {

	Section sectionIntervals=null;
	Composite sectionClientIntervals=null;



	final Button newIntervalButton;
	final Table intervalsTable; 
	final Label intervalLabelLabel;
	final Text intervalLabelText;
	final Label intervalMinLabel;
	final Spinner intervalMinText;
	final Label intervalMaxLabel;
	final Spinner intervalMaxText;
	Composite innerSection; 
	final Label intervalColorLabel;
	final Button intervalColorButton;	
	final Button buttonRem;	


	public static final int ORDER=0;
	public static final int LABEL=1;
	public static final int MIN=2;
	public static final int MAX=3;
	public static final int COLOR=4;


	public IntervalsInformationEditor(final DialChartModel dialModel, FormToolkit toolkit, final ScrolledForm form) {

		sectionIntervals= toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		sectionClientIntervals=toolkit.createComposite(sectionIntervals);

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		sectionIntervals.setLayoutData(td);
		sectionIntervals.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		boolean isThermomether=dialModel.getSubType().equalsIgnoreCase("thermomether") ? true : false;


		sectionIntervals.setText("Series Intervals");
		sectionIntervals.setDescription("Set all the intervals ");

		GridLayout gridLayout=new GridLayout();
		gridLayout.numColumns=2;
		sectionClientIntervals.setLayout(gridLayout);


		newIntervalButton=new Button(sectionClientIntervals, SWT.PUSH);
		newIntervalButton.setText("Add");
		newIntervalButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		newIntervalButton.setToolTipText("Add new Interval");

		buttonRem = new Button(sectionClientIntervals, SWT.PUSH);
		buttonRem.setToolTipText("Remove (can remove only the last inserted)");
		buttonRem.setText("Cancel");
		buttonRem.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		buttonRem.setEnabled(false);
		buttonRem.pack();



		intervalsTable = new Table (sectionClientIntervals, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		intervalsTable.setLinesVisible (true);
		intervalsTable.setHeaderVisible (true);
		GridData g=new GridData(GridData.FILL_BOTH);
		g.horizontalSpan=2;
		g.verticalSpan=2;
		g.grabExcessHorizontalSpace=true;
		g.grabExcessVerticalSpace=true;
		g.heightHint = 150;
		g.widthHint = 400;
		intervalsTable.setLayoutData(g);
		intervalsTable.setToolTipText("intervals added");

		String[] titles = {"Order", "     Name    ", "  Min  ","  Max  ","  Color  "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (intervalsTable, SWT.NONE);
			column.setText (titles [i]);
			column.setWidth(100);
		}


		int i=0;
		if(dialModel.getIntervals()!=null){
			for (Iterator iterator = dialModel.getIntervals().iterator(); iterator.hasNext();) {
				Interval interval = (Interval) iterator.next();
				TableItem item = new TableItem (intervalsTable, SWT.NONE);
				item.setText(ORDER, Integer.valueOf(i).toString());
				item.setText(LABEL, interval.getLabel()!=null ? interval.getLabel() : "");
				item.setText(MIN, interval.getMin()!=null ? interval.getMin().toString() : "");
				item.setText(MAX, interval.getMax()!=null ? interval.getMax().toString() : "");
				item.setText(COLOR, interval.getColor()!=null ? ChartEditor.convertRGBToHexadecimal(interval.getColor()) : "");
				if(interval.getColor()!=null){
					Color color=new Color(item.getDisplay(),interval.getColor());
					item.setBackground(COLOR, color);
				}
				i++;
			}			
		} //close if map is not null
		intervalsTable.redraw();

		// Add Button Listener
		Listener addListener = new Listener() {
			public void handleEvent(Event event) {
				dialModel.getEditor().setIsDirty(true);				
				// Add a new Interval: put it in kiew
				int size=dialModel.getIntervals().size();
				String label=intervalLabelText.getText()!=null ? intervalLabelText.getText() : "";
				String min=intervalMinText.getText()!=null ? intervalMinText.getText() : "";
				String max=intervalMaxText.getText()!=null ? intervalMaxText.getText() : "";

				// if it is a thermofmether special case for LABELS
				boolean isThermomether=dialModel.getSubType().equalsIgnoreCase(DialChartModel.THERMOMETHER) ? true : false;
				if(isThermomether){
					int sizeD=dialModel.getIntervals().size();
					if(sizeD==0)label="normal";
					else if(sizeD==1)label="warning";
					else if(sizeD==2)label="critical";
				}

				if(isThermomether && dialModel.getIntervals().size()==3){
					MessageDialog.openWarning(intervalsTable.getShell(), "Warning", "A thermomether cannot have more than three intervals");
				}
				else{
					Interval interval=new Interval();
					interval.setLabel(label);

					try{
						double newMin = intervalMinText.getSelection()/ Math.pow(10, intervalMinText.getDigits());									
						interval.setMin(Double.valueOf(newMin));
					}
					catch (Exception e) {
					}
					try{
						double newMax = intervalMaxText.getSelection()/ Math.pow(10, intervalMaxText.getDigits());									
						interval.setMax(Double.valueOf(newMax));
					}
					catch (Exception e) {
					}

					TableItem item=new TableItem(intervalsTable, SWT.NONE);
					RGB rgb=null;
					if(intervalColorLabel.getBackground().getRGB()!=null){
						rgb=intervalColorLabel.getBackground().getRGB();
						Color color=new Color(item.getDisplay(),rgb);
						item.setBackground(COLOR,color);
						item.setText(COLOR,ChartEditor.convertRGBToHexadecimal(rgb));

					}
					if(rgb!=null)	interval.setColor(rgb);
					item.setText(ORDER, (new Integer(size)).toString());
					item.setText(LABEL, label);
					item.setText(MIN, min);
					item.setText(MAX, max);
					dialModel.getIntervals().add(size,interval);
				}
			}
		};

		newIntervalButton.addListener(SWT.Selection, addListener);

		intervalLabelLabel=new Label(sectionClientIntervals,SWT.NULL);
		intervalLabelLabel.setText("label");
		intervalLabelText=new Text(sectionClientIntervals, SWT.BORDER);;
		intervalLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		intervalLabelText.setToolTipText("Label of the interval to add");

		intervalLabelText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				dialModel.getEditor().setIsDirty(true);				
				String newLabel = intervalLabelText.getText();
				int selection = intervalsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=intervalsTable.getItem(selection);
					Interval intervalSelected=dialModel.getIntervals().get(selection);
					if(newLabel!=null){
						if(intervalSelected!=null)
						{
							intervalSelected.setLabel(newLabel);
						}
						item.setText(LABEL,newLabel);
					}

				}
			}
		});
		if(isThermomether){
			intervalLabelText.setVisible(false);
			intervalLabelLabel.setVisible(false);
		}


		intervalMinLabel=new Label(sectionClientIntervals,SWT.NULL);
		intervalMinLabel.setText("min");
		intervalMinText=new Spinner(sectionClientIntervals, SWT.BORDER);
		intervalMinText.setToolTipText("Minimum value of the interval");
		intervalMinText.setMaximum(1000000);
		intervalMinText.setMinimum(-1000000);
		intervalMinText.setDigits(1);
		intervalMinText.setSelection(00);

		//intervalMinText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		intervalMinText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				dialModel.getEditor().setIsDirty(true);
				double newMin = intervalMinText.getSelection()/ Math.pow(10, intervalMinText.getDigits());
				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				int selection = intervalsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=intervalsTable.getItem(selection);
					Interval intervalSelected=dialModel.getIntervals().get(selection);
					if(intervalSelected!=null)
					{
						intervalSelected.setMin(newMinD);
						item.setText(MIN, newMinD.toString());
					}

				}
			}
		});



		intervalMaxLabel=new Label(sectionClientIntervals,SWT.NULL);
		intervalMaxLabel.setText("max");
		intervalMaxText=new Spinner(sectionClientIntervals, SWT.BORDER);;
		//intervalMaxText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		intervalMaxText.setToolTipText("Maximum value of the interval");
		intervalMaxText.setMaximum(1000000);
		intervalMaxText.setMinimum(-1000000);
		intervalMaxText.setDigits(1);
		intervalMaxText.setSelection(00);

		intervalMaxText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				dialModel.getEditor().setIsDirty(true);
				double newMax = intervalMaxText.getSelection()/ Math.pow(10, intervalMaxText.getDigits());
				Double newMaxD=null;
				try{
					newMaxD=Double.valueOf(newMax);
				}
				catch (Exception e) {
					newMaxD=new Double(0.0);
				}
				int selection = intervalsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=intervalsTable.getItem(selection);
					Interval intervalSelected=dialModel.getIntervals().get(selection);
					if(intervalSelected!=null)
					{
						intervalSelected.setMax(newMaxD);
						item.setText(MAX, newMaxD.toString());
					}

				}
			}
		});



		innerSection = toolkit.createComposite(sectionClientIntervals);

		intervalColorLabel=new Label(sectionClientIntervals,SWT.BORDER);
		intervalColorLabel.setText("color");
		intervalColorLabel.setToolTipText("Color of the interval");
		intervalColorButton= new Button(innerSection, SWT.PUSH);

		final Color color = new org.eclipse.swt.graphics.Color(sectionClientIntervals.getDisplay(), new RGB(255,255,255));
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);
		intervalColorLabel.setText("          ");
		intervalColorLabel.setBackground(color);
		intervalColorButton.setText("Color...");
		final Shell parentShell = sectionClientIntervals.getShell();
		intervalColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				dialModel.getEditor().setIsDirty(true);
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(intervalColorLabel.getBackground().getRGB());
				colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					intervalColorLabel.setBackground(newColor);
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
					int selection = intervalsTable.getSelectionIndex();
					//get ParSelected
					if(selection!=-1){
						TableItem item=intervalsTable.getItem(selection);
						item.setBackground(COLOR,new Color(item.getDisplay(),ChartEditor.convertHexadecimalToRGB(newHexadecimal)));
						item.setText(COLOR,newHexadecimal);

						Interval interval=dialModel.getIntervals().get(selection);
						interval.setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));						
						intervalsTable.redraw();
					}

				}
				//centerShell.pack();
				centerShell.dispose();
			}

		});			


		// Add listener that show details of parameter selected
		intervalsTable.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				// get par selected
				int selection = intervalsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=intervalsTable.getItem(selection);
					Interval intervalSelected=dialModel.getIntervals().get(selection);
					// put the default value

					String label=intervalSelected.getLabel()!=null ? intervalSelected.getLabel() : "";
					intervalLabelText.setText(label);

					String min;
					if(intervalSelected.getMin()!=null){
						min=intervalSelected.getMin().toString();						
					}
					else{
						min="";
					}
					int indexPoint=min.indexOf('.');
					if(indexPoint!=-1){
						min=ChartEditorUtils.removeChar(min, '.');
					}
					Integer minI=null;
					try{
						minI=Integer.valueOf(min);	
					}
					catch (Exception e2) {
						minI=Integer.valueOf(00);
					}

					intervalMinText.setSelection(minI);


					String max;
					if(intervalSelected.getMax()!=null){
						max=intervalSelected.getMax().toString();						
					}
					else{
						max="";
					}
					int indexP=max.indexOf('.');
					if(indexP!=-1){
						max=ChartEditorUtils.removeChar(max, '.');
					}
					Integer maxI=null;
					try{
						maxI=Integer.valueOf(max);	
					}
					catch (Exception e2) {
						maxI=Integer.valueOf(00);
					}

					intervalMaxText.setSelection(maxI);


					if(intervalSelected.getColor()!=null){
						Color newColor = new Color(parentShell.getDisplay(), intervalSelected.getColor());
						intervalColorLabel.setBackground(newColor);
					}
					else
					{
						intervalColorLabel.setBackground(null);
					}


				}
				if(selection==dialModel.getIntervals().size()-1){
					buttonRem.setEnabled(true);
				}
				else{
					buttonRem.setEnabled(false);

				}
			}
		});


		// Add Button Listener
		Listener cancelListener = new Listener() {
			public void handleEvent(Event event) {
				dialModel.getEditor().setIsDirty(true);
				int index=intervalsTable.getSelectionIndex();
				if(index!=-1 && index==dialModel.getIntervals().size()-1){
					TableItem item=intervalsTable.getItem(index);
					//can erase only the last
					dialModel.getIntervals().remove(index);
					intervalsTable.remove(index);
					intervalLabelText.setText("");
					intervalMinText.setSelection(00);
					intervalMaxText.setSelection(00);
					intervalColorLabel.setBackground(null);
					buttonRem.setEnabled(false);
				}
			}
		};
		buttonRem.addListener(SWT.Selection, cancelListener);

		sectionIntervals.setClient(sectionClientIntervals);

	}











	public void setVisible(boolean visible){
		sectionIntervals.setVisible(visible);

	}

	public boolean isVisible(){
		if(sectionIntervals.isVisible())return true;
		else return false;
	}



	public void eraseComposite(){
		intervalsTable.removeAll();
		intervalsTable.redraw();
		intervalColorLabel.setBackground(null);
		intervalLabelText.setText("");
		intervalMaxText.setSelection(00);
		intervalMinText.setSelection(00);
		buttonRem.setEnabled(false);
	}

	public void refillFieldsIntervalsInformation(final DialChartModel dialmodel, final ChartEditor editor, FormToolkit toolkit, final ScrolledForm form){
		final boolean isThermomether=dialmodel.getSubType().equalsIgnoreCase("thermomether") ? true : false;

		if(dialmodel.getIntervals()!=null){
			for (int j = 0; j < dialmodel.getIntervals().size(); j++) {
				Interval interval= (Interval) dialmodel.getIntervals().get(j);
				TableItem item=intervalsTable.getItem(j);
				item.setText(ORDER, Integer.valueOf(j).toString());
				item.setText(LABEL, interval.getLabel()!=null ? interval.getLabel() : "");
				item.setText(MIN, interval.getMin()!=null ? interval.getMin().toString() : "");
				item.setText(MAX, interval.getMax()!=null ? interval.getMax().toString() : "");
				item.setText(COLOR, interval.getColor()!=null ? ChartEditor.convertRGBToHexadecimal(interval.getColor()) : "");
				if(interval.getColor()!=null){
					Color color=new Color(item.getDisplay(),interval.getColor());
					item.setBackground(COLOR, color);
				}
			}
			if(isThermomether){
				intervalLabelText.setVisible(false);
				intervalLabelLabel.setVisible(false);
			}
			else{
				intervalLabelText.setVisible(true);
				intervalLabelLabel.setVisible(true);
			}
			intervalsTable.redraw();
		}
	}

}
