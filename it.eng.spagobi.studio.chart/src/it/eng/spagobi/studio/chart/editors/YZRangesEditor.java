/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.editors.model.chart.XYChartModel;
import it.eng.spagobi.studio.chart.utils.ZRanges;

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
import org.eclipse.swt.widgets.Group;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gavardi
 *
 *	Thios class has method for the Y Z Ranges parameters form creation
 */

public class YZRangesEditor {

	private static Logger logger = LoggerFactory.getLogger(YZRangesEditor.class);
	
	Section sectionYZRanges=null;
	Composite sectionClientYZRanges=null;

	final Group yRangesGroup;
	final Label newYRangeLabel;
	final Text newYRangeText;
	final Button newYRangeButton;
	final Button cancelYRangeButton;	
	final Table yRangesTable;

	final Group zRangesGroup;
	final Label newZRangeLabel;
	final Text newZRangeText;
	final Button newZRangeButton;
	final Button cancelZRangeButton;	
	final Table zRangesTable;

	//final Label zRangeLabelLabel;
	//	final Text zRangeLabelText;

	final Label zRangeValueLowLabel;
	final Spinner zRangeValueLowText;
	final Label zRangeValueHighLabel;
	final Spinner zRangeValueHighText;
	Composite innerSection; 
	final Label zRangeColorLabel;
	final Button zRangeColorButton;	

	static final int LABEL=0;
	static final int VALUE_LOW=1;
	static final int VALUE_HIGH=2;
	static final int COLOR=3;

	public YZRangesEditor(final XYChartModel xyModel, FormToolkit toolkit, final ScrolledForm form) {

		sectionYZRanges= toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		sectionClientYZRanges=toolkit.createComposite(sectionYZRanges);

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		sectionYZRanges.setLayoutData(td);
		sectionYZRanges.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionYZRanges.setText("Y Z Ranges");
		sectionYZRanges.setDescription("Set all the ranges");

		GridLayout gridLayout=new GridLayout();
		gridLayout.numColumns=1;
		sectionClientYZRanges.setLayout(gridLayout);


		yRangesGroup = new Group(sectionClientYZRanges, SWT.NULL);
		yRangesGroup.setText("---------------------------------- ADD Y RANGES ----------------------------------");
		yRangesGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		GridLayout gridLayoutG=new GridLayout();
		gridLayoutG.numColumns=2;
		yRangesGroup.setLayout(gridLayoutG);

		newYRangeButton=new Button(yRangesGroup, SWT.PUSH);
		newYRangeButton.setText("Add");
		newYRangeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		newYRangeButton.setToolTipText("Add new Y Range");

		cancelYRangeButton=new Button(yRangesGroup, SWT.PUSH);
		cancelYRangeButton.setText("Cancel");
		cancelYRangeButton.setToolTipText("Cancel selected Y Range");
		cancelYRangeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		cancelYRangeButton.setEnabled(false);

		yRangesTable = new Table (yRangesGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		yRangesTable.setToolTipText("Y Ranges added");
		yRangesTable.setLinesVisible (true);
		yRangesTable.setHeaderVisible (true);
		GridData g=new GridData(GridData.FILL_BOTH);
		g.horizontalSpan=2;
		g.verticalSpan=2;
		g.grabExcessHorizontalSpace=true;
		g.grabExcessVerticalSpace=true;
		g.heightHint = 150;
		g.widthHint = 300;
		yRangesTable.setLayoutData(g);
		yRangesTable.setToolTipText("intervals added");

		TableColumn column = new TableColumn (yRangesTable, SWT.NONE);
		column.setText ("                             Label                        ");
		column.setWidth(300);

		if(xyModel.getYRanges()!=null){
			for (Iterator iterator = xyModel.getYRanges().iterator(); iterator.hasNext();) {
				String yRangeLabel = (String) iterator.next();
				TableItem item=new TableItem(yRangesTable, SWT.NULL);
				item.setText(LABEL, yRangeLabel);
			}			
		} //close if map is not null
		yRangesTable.redraw();



		newYRangeLabel = new Label(yRangesGroup, SWT.NULL); 
		newYRangeLabel.setText("Y Range Name: ");

		newYRangeText = new Text(yRangesGroup, SWT.BORDER);
		newYRangeText.setToolTipText("New Y Range name");
		newYRangeText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		//newYRangeText.pack();





		// Add Button Listener
		Listener addListener = new Listener() {
			public void handleEvent(Event event) {
				xyModel.getEditor().setIsDirty(true);				
				String newRange=newYRangeText.getText();
				if(newRange==null || newRange.equalsIgnoreCase("") ){
					logger.warn("Specify a name for Y Range");
					MessageDialog.openWarning(yRangesGroup.getShell(), "Warning", "Specify a name for Y Range");
				}
				else if(xyModel.getYRanges().contains(newRange)){
					logger.warn("Name already present for Y Range");
					MessageDialog.openWarning(yRangesGroup.getShell(), "Warning", "Name already present");					
				}
				else
				{
					TableItem item=new TableItem(yRangesTable,SWT.NULL);
					item.setText(LABEL,newRange);
					xyModel.getYRanges().add(newRange);
				}
			}
		};
		newYRangeButton.addListener(SWT.Selection, addListener);



		// Start the y range form

		Listener cancelListener = new Listener() {
			public void handleEvent(Event event) {
				xyModel.getEditor().setIsDirty(true);				
				int index=yRangesTable.getSelectionIndex();
				if(index!=-1){
					TableItem item=yRangesTable.getItem(index);
					String nameY=item.getText(LABEL);
					//remove from java list 
					if(xyModel.getYRanges().contains(nameY)){
						xyModel.getYRanges().remove(nameY);
					}
					cancelYRangeButton.setEnabled(false);
					yRangesTable.remove(index);
					yRangesTable.redraw();
				}			
			}
		};
		cancelYRangeButton.addListener(SWT.Selection, cancelListener);

		Label sl=new Label(yRangesGroup,SWT.NULL);
		sl.setText("");
		sl=new Label(yRangesGroup,SWT.NULL);
		sl.setText("");


		zRangesGroup = new Group(sectionClientYZRanges, SWT.NULL);
		zRangesGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		zRangesGroup.setText("------------------------------ ADD Z RANGES ----------------------------------------");
		GridLayout gridLayoutZ=new GridLayout();
		gridLayoutZ.numColumns=2;
		zRangesGroup.setLayout(gridLayoutZ);
		GridData gdZ=new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gdZ.horizontalSpan=2;
		zRangesGroup.setLayoutData(gdZ);


		newZRangeButton=new Button(zRangesGroup, SWT.PUSH);
		newZRangeButton.setText("Add");
		newZRangeButton.setToolTipText("Add new Z Range");
		newZRangeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		cancelZRangeButton=new Button(zRangesGroup, SWT.PUSH);
		cancelZRangeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		cancelZRangeButton.setToolTipText("Cancel selected Z Range");
		cancelZRangeButton.setText("Cancel");
		cancelZRangeButton.setEnabled(false);
		cancelZRangeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));



		zRangesTable = new Table (zRangesGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		zRangesTable.setLinesVisible (true);
		zRangesTable.setHeaderVisible (true);
		GridData gg=new GridData(GridData.FILL_BOTH);
		gg.horizontalSpan=2;
		gg.verticalSpan=2;
		gg.grabExcessHorizontalSpace=true;
		gg.grabExcessVerticalSpace=true;
		gg.heightHint = 150;
		gg.widthHint = 400;
		zRangesTable.setLayoutData(gg);
		zRangesTable.setToolTipText("intervals added");

		String[] titles1 = {"     Label    ", "  Value Low  ","  Value High  ","  Color  "};
		for (int i=0; i<titles1.length; i++) {
			TableColumn column1 = new TableColumn (zRangesTable, SWT.NONE);
			column1.setText (titles1 [i]);
			column1.setWidth(100);
		}

		if(xyModel.getZRanges()!=null){
			for (Iterator iterator = xyModel.getZRanges().keySet().iterator(); iterator.hasNext();) {
				String zLabel = (String) iterator.next();
				ZRanges zRan=xyModel.getZRanges().get(zLabel);
				TableItem item=new TableItem(zRangesTable, SWT.NONE);
				item.setText(LABEL, zRan.getLabel());
				item.setText(VALUE_LOW, zRan.getValueLow()!=null ? zRan.getValueLow().toString() : "");
				item.setText(VALUE_HIGH, zRan.getValueHigh()!=null ? zRan.getValueHigh().toString() : "");
				item.setText(COLOR, zRan.getColor()!=null ? ChartEditor.convertRGBToHexadecimal(zRan.getColor()) : "");
				if(zRan.getColor()!=null){
					Color col=new Color(item.getDisplay(),zRan.getColor());
					item.setBackground(COLOR, col);
				}

			}			
		} //close if map is not null
		zRangesTable.redraw();



		newZRangeLabel = new Label(zRangesGroup, SWT.NULL); 
		newZRangeLabel.setText("Z Range Name: ");
		newZRangeText = new Text(zRangesGroup, SWT.BORDER);
		newZRangeText.setToolTipText("New Z Range name");

		newZRangeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//		newZRangeText.pack();




		// Add Button Listener
		Listener addListenerZ = new Listener() {
			public void handleEvent(Event event) {
				xyModel.getEditor().setIsDirty(true);				
				String newRange=newZRangeText.getText();
				if(newRange==null || newRange.equalsIgnoreCase(""))
				{
					logger.warn("Specify a name for Z Range");
					MessageDialog.openWarning(yRangesGroup.getShell(), "Warning", "Specify a name for Z Range");										
				}
				else if (xyModel.getZRanges().keySet().contains(newRange)){
					logger.warn("Name already present for Z Range");
					MessageDialog.openWarning(yRangesGroup.getShell(), "Warning", "Name already present");					
				}
				else
				{
					TableItem item=new TableItem(zRangesTable,SWT.NULL);
					String labelS=newZRangeText.getText()!=null ? newZRangeText.getText() : "";
					String valueLowS=zRangeValueLowText.getText()!=null ? zRangeValueLowText.getText() : "";
					String valueHighS=zRangeValueHighText.getText()!=null ? zRangeValueHighText.getText() : "";
					String colorS=zRangeColorLabel.getText()!=null ? zRangeColorLabel.getText() : "";

					item.setText(LABEL, labelS);
					item.setText(VALUE_LOW, valueLowS);
					item.setText(VALUE_HIGH, valueHighS);

					ZRanges zR=new ZRanges();

					RGB rgb=null;
					if(zRangeColorLabel.getBackground().getRGB()!=null){
						rgb=zRangeColorLabel.getBackground().getRGB();
						Color color=new Color(item.getDisplay(),rgb);
						item.setBackground(COLOR,color);
						item.setText(COLOR,ChartEditor.convertRGBToHexadecimal(rgb));

					}
					zR.setLabel(labelS);

					try{
						double newMin = zRangeValueLowText.getSelection()/ Math.pow(10, zRangeValueLowText.getDigits());				
						Double dub=Double.valueOf(newMin);
						zR.setValueLow(dub);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					try{
						double newMax = zRangeValueHighText.getSelection()/ Math.pow(10, zRangeValueHighText.getDigits());				
						zR.setValueHigh(newMax);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					if(rgb!=null)	zR.setColor(rgb);


					xyModel.getZRanges().put(newRange,zR);
				}
			}
		};

		newZRangeButton.addListener(SWT.Selection, addListenerZ);



		// Start the z range form




		//		zRangeLabelLabel=new Label(zRangesGroup,SWT.NULL);
		//		zRangeLabelLabel.setText("Label: ");
		//		zRangeLabelLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		
		//		zRangeLabelText=new Text(zRangesGroup, SWT.BORDER);;
		//		zRangeLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//		zRangeLabelText.setEnabled(false);
		//
		//		zRangeLabelText.addModifyListener(new ModifyListener() {
		//			public void modifyText(ModifyEvent event) {
		//				String newLabel = zRangeLabelText.getText();
		//				int selection = zRangesList.getSelectionIndex();
		//				if(selection!=-1){
		//					String item=zRangesList.getItem(selection);
		//					ZRanges zRangeSelected=xyModel.getZRanges().get(item);
		//					if(zRangeSelected!=null && newLabel!=null && !newLabel.equals(""))
		//					{zRangeSelected.setLabel(newLabel);
		//					}
		//				}
		//			}
		//		});
		//
		//		zRangeLabelLabel.setEnabled(false);
		//		zRangeLabelText.setEnabled(false);

		innerSection = toolkit.createComposite(zRangesGroup);

		zRangeColorLabel=new Label(zRangesGroup,SWT.BORDER);
		zRangeColorLabel.setText("Color: ");
		zRangeColorButton= new Button(innerSection, SWT.PUSH);
		zRangeColorButton.setToolTipText("Color of the Z Range");

		final Color color = new org.eclipse.swt.graphics.Color(zRangesGroup.getDisplay(), new RGB(255,255,255));
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);
		zRangeColorLabel.setText("	   ");
		zRangeColorLabel.setBackground(color);
		zRangeColorButton.setText("Color...");
		zRangeColorButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));				
		final Shell parentShell = zRangesGroup.getShell();
		zRangeColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				xyModel.getEditor().setIsDirty(true);				
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(zRangeColorLabel.getBackground().getRGB());
				colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					zRangeColorLabel.setBackground(newColor);
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);

					int selection = zRangesTable.getSelectionIndex();
					if(selection!=-1){
						TableItem itemT=zRangesTable.getItem(selection);
						String itemName=itemT.getText(LABEL);
						ZRanges zRangeSelected=xyModel.getZRanges().get(itemName);
						if(zRangeSelected!=null && newColor!=null )
						{
							itemT.setBackground(COLOR, new Color(itemT.getDisplay(),ChartEditor.convertHexadecimalToRGB(newHexadecimal)));
							itemT.setText(COLOR,newHexadecimal);
							zRangeSelected.setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));
						}
					}


				}
				//centerShell.pack();
				centerShell.dispose();
			}

		});			




		//		Label sl=new Label(zRangesGroup, SWT.NULL);
		//		sl.setText("");
		zRangeValueLowLabel=new Label(zRangesGroup,SWT.NULL);
		zRangeValueLowLabel.setText("Value Low: ");
		zRangeValueLowLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		

		zRangeValueLowText= new Spinner (zRangesGroup, SWT.BORDER);
		zRangeValueLowText.setMaximum(1000000);
		zRangeValueLowText.setMinimum(-1000000);
		zRangeValueLowText.setToolTipText("Low value of Z Range");
		zRangeValueLowText.setDigits(1);
		zRangeValueLowText.setSelection(00);
		//styleSizeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		zRangeValueLowText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				xyModel.getEditor().setIsDirty(true);				
				double newMin = zRangeValueLowText.getSelection()/ Math.pow(10, zRangeValueLowText.getDigits());

				Double newMinD=null;
				try{
					newMinD=Double.valueOf(newMin);
				}
				catch (Exception e) {
					newMinD=new Double(0.0);
				}
				// get the zRange
				int index=zRangesTable.getSelectionIndex();
				if(index!=-1){
					TableItem item=zRangesTable.getItem(index);
					ZRanges zRan=xyModel.getZRanges().get(item.getText(LABEL));
					String selectedName=zRan.getLabel();
					ZRanges zR=xyModel.getZRanges().get(selectedName);
					if(zR!=null && newMinD!=null){
						zR.setValueLow(newMinD);
						item.setText(VALUE_LOW,newMinD.toString());
					}
				}
			}
		});


		zRangeValueHighLabel=new Label(zRangesGroup,SWT.NULL);
		zRangeValueHighLabel.setText("Value High: ");
		zRangeValueHighLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));		
		zRangeValueHighText= new Spinner (zRangesGroup, SWT.BORDER);
		zRangeValueHighText.setMaximum(1000000);
		zRangeValueHighText.setMinimum(-1000000);
		zRangeValueHighText.setToolTipText("High value of Z Range");
		zRangeValueHighText.setDigits(1);
		zRangeValueHighText.setSelection(00);
		//styleSizeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		zRangeValueHighText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				xyModel.getEditor().setIsDirty(true);				
				//double newMax = zRangeValueHighText.getSelection();
				double newMax = zRangeValueHighText.getSelection()/ Math.pow(10, zRangeValueHighText.getDigits());
				Double newMaxD=null;
				try{
					newMaxD=Double.valueOf(newMax);
				}
				catch (Exception e) {
					newMaxD=new Double(0.0);
				}
				// get the zRange
				int index=zRangesTable.getSelectionIndex();
				if(index!=-1){
					TableItem item=zRangesTable.getItem(index);
					ZRanges zRan=xyModel.getZRanges().get(item.getText(LABEL));
					if(zRan!=null && newMaxD!=null){
						String selectedName=zRan.getLabel();
						ZRanges zR=xyModel.getZRanges().get(selectedName);
						zR.setValueHigh(newMaxD);
						item.setText(VALUE_HIGH,newMaxD.toString());
					}
				}
			}
		});




		Listener cancelListenerZ = new Listener() {
			public void handleEvent(Event event) {
				xyModel.getEditor().setIsDirty(true);				
				int index=zRangesTable.getSelectionIndex();
				if(index!=-1){
					TableItem item=zRangesTable.getItem(index);
					String label= item.getText(LABEL);
					//remove from java list 
					if(xyModel.getZRanges().keySet().contains(label)){
						xyModel.getZRanges().remove(label);
					}
					cancelZRangeButton.setEnabled(false);
					zRangesTable.remove(index);
					zRangesTable.redraw();
					//zRangeLabelText.setText("");
					zRangeColorLabel.setBackground(null);
					zRangeValueLowText.setSelection(0);
					zRangeValueHighText.setSelection(0);

				}			
			}
		};
		cancelZRangeButton.addListener(SWT.Selection, cancelListenerZ);



		yRangesTable.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				// get par selected
				int selection = yRangesTable.getSelectionIndex();
				if(selection!=-1){
					cancelYRangeButton.setEnabled(true);

				}
			}
		});



		zRangesTable.addListener (SWT.Selection, new Listener () {
			public void handleEvent(Event e) {
				xyModel.getEditor().setIsDirty(true);				
				// get par selected
				int selection = zRangesTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=zRangesTable.getItem(selection);
					String itemName=item.getText(LABEL); 
					if(itemName!=null && xyModel.getZRanges().keySet().contains(itemName)){
						ZRanges zRangeSelected=xyModel.getZRanges().get(itemName);
						// put the default value

						String label=zRangeSelected.getLabel()!=null ? zRangeSelected.getLabel() : "";
						newZRangeText.setText(label);

						String min;
						if(zRangeSelected.getValueLow()!=null){
							min=zRangeSelected.getValueLow().toString();						
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


						zRangeValueLowText.setSelection(minI);

						String max;
						if(zRangeSelected.getValueHigh()!=null){
							max=zRangeSelected.getValueHigh().toString();						
						}
						else{
							max="";
						}
						indexPoint=max.indexOf('.');
						if(indexPoint!=-1){
							max=ChartEditorUtils.removeChar(max, '.');
						}
						Integer maxI=null;
						try{
							maxI=Integer.valueOf(max);	
						}
						catch (Exception e1) {
							maxI=Integer.valueOf(00);
						}

						zRangeValueHighText.setSelection(maxI);

						if(zRangeSelected.getColor()!=null){
							Color newColor = new Color(parentShell.getDisplay(), zRangeSelected.getColor());
							zRangeColorLabel.setBackground(newColor);
						}
						else
						{
							zRangeColorLabel.setBackground(null);
						}


						zRangeColorButton.setEnabled(true);
						zRangeColorLabel.setEnabled(true);
						//zRangeLabelText.setEnabled(true);
						zRangeValueHighLabel.setEnabled(true);
						zRangeValueHighText.setEnabled(true);
						zRangeValueLowLabel.setEnabled(true);
						zRangeValueLowText.setEnabled(true);
					}

					if(xyModel.getZRanges().keySet().size()!=-1){
						cancelZRangeButton.setEnabled(true);
					}
					else{
						cancelZRangeButton.setEnabled(false);

					}
				}
			} 
		});



		sectionYZRanges.setClient(sectionClientYZRanges);

	}








	public void setVisible(boolean visible){
		sectionYZRanges.setVisible(visible);

	}

	public boolean isVisible(){
		if(sectionYZRanges.isVisible())return true;
		else return false;
	}



	public void eraseComposite(){
		yRangesTable.removeAll();
		zRangesTable.removeAll();
		newZRangeText.setText("");
		zRangeValueHighText.setSelection(00);
		zRangeValueLowText.setSelection(00);
		zRangeColorLabel.setBackground(null);
		cancelZRangeButton.setEnabled(false);
		cancelYRangeButton.setEnabled(false);
	}

	public void refillFieldsSeriesPersonalization(final XYChartModel xyModel, final ChartEditor editor, FormToolkit toolkit, final ScrolledForm form){
		if(xyModel.getYRanges()!=null){
			for (int j = 0; j < xyModel.getYRanges().size(); j++) {
				String yR= (String) xyModel.getYRanges().get(j);
				TableItem item=new TableItem(yRangesTable,SWT.NULL);
				item.setText(yR);
			}
			yRangesTable.redraw();
		}

		if(xyModel.getZRanges()!=null){
			for (Iterator iterator = xyModel.getZRanges().keySet().iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				ZRanges zRan=xyModel.getZRanges().get(name);

				newZRangeText.setText(zRan.getLabel() != null ? zRan.getLabel(): "");

				TableItem item=new TableItem(zRangesTable,	SWT.NULL);
				if(zRan.getLabel()!=null)item.setText(LABEL,zRan.getLabel());	
				if(zRan.getValueLow()!=null)item.setText(VALUE_LOW,zRan.getValueLow().toString());	
				if(zRan.getValueHigh()!=null)item.setText(VALUE_HIGH,zRan.getValueHigh().toString());	
				RGB rgb=null;
				if(zRangeColorLabel.getBackground().getRGB()!=null){
					rgb=zRangeColorLabel.getBackground().getRGB();
					Color color=new Color(item.getDisplay(),rgb);
					item.setBackground(COLOR,color);
					item.setText(COLOR,ChartEditor.convertRGBToHexadecimal(rgb));

				}


				String min;
				if(zRan.getValueLow()!=null){
					min=zRan.getValueLow().toString();						
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

				zRangeValueLowText.setSelection(minI);



				String max;
				if(zRan.getValueHigh()!=null){
					max=zRan.getValueHigh().toString();						
				}
				else{
					max="";
				}
				int indexPoint1=max.indexOf('.');
				if(indexPoint1!=-1){
					max=ChartEditorUtils.removeChar(max, '.');
				}
				Integer maxI=null;
				try{
					maxI=Integer.valueOf(max);	
				}
				catch (Exception e2) {
					maxI=Integer.valueOf(00);
				}

				zRangeValueHighText.setSelection(maxI);


				zRangesTable.redraw();

			}		

		}


	}

}
