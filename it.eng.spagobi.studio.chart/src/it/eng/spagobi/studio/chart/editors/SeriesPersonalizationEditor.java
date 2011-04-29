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
package it.eng.spagobi.studio.chart.editors;

import it.eng.spagobi.studio.chart.editors.model.chart.ChartModel;
import it.eng.spagobi.studio.chart.utils.SeriePersonalization;

import java.util.Iterator;
import java.util.Vector;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
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
 *	Thios class has method for the serie personalizations form creation, can change (if enabled) colors, labels, drawing style and scale
 */

public class SeriesPersonalizationEditor {

	Section sectionSeries=null;
	Composite sectionClientSeries=null;
	private static Logger logger = LoggerFactory.getLogger(SeriesPersonalizationEditor.class);

	final Label newSerLabel;
	final Text newSerLabelText;
	final Label newColorLabel;	
	final Label newOrderColorLabel;	
	Composite innerSection; 
	Composite orderInnerSection; 
	final Label colorLabel;
	final Label orderColorLabel;
	final Button colorButton;	
	final Button orderColorButton;	
	final Label drawLabel;
	final Combo comboDraw;
	final Label scaleLabel;
	final Combo comboScale;
	final Table parsTable;
	final Text newSerName; 
	// Field for personalization
	final Table orderTable;
	final Label orderLabelTitle;
	final Group orderGroup;

	public final static int NAME=0;
	public final static int COLOR=1;
	public final static int LABEL=2;
	public final static int DRAW=3;
	public final static int SCALE=4;	

	public SeriesPersonalizationEditor(final ChartModel model,FormToolkit toolkit, final ScrolledForm form) {

		sectionSeries = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE);
		sectionClientSeries=toolkit.createComposite(sectionSeries);

		//********* Main Section ******************
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		sectionSeries.setLayoutData(td);
		sectionSeries.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		sectionSeries.setText("Series Labels parameters");
		sectionSeries.setDescription("Define series settings");



		// ********** Main Layout ***************
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		sectionClientSeries.setLayout(gl);


		parsTable = new Table (sectionClientSeries, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		parsTable.setLinesVisible (true);
		parsTable.setHeaderVisible (true);
		GridData g=new GridData(GridData.FILL_BOTH);
		//g.verticalSpan=2;
		g.horizontalSpan=2	;
		parsTable.setLayoutData(g);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 150;
		data.widthHint=500;
		parsTable.setLayoutData(data);

		ChartEditorUtils.addBlanckSpace(sectionClientSeries);


		String[] titles = {"       Serie Name       ", "     Color     ", "         Label         ", "     Draw Style      ", " Scale "};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (parsTable, SWT.NONE);
			column.setText (titles [i]);
		}
		if(model.getSeriesPersonalizationHashMap()!=null){
			for (Iterator iterator = model.getSeriesPersonalizationHashMap().keySet().iterator(); iterator.hasNext();) {
				String parName = (String) iterator.next();
				SeriePersonalization serPers=model.getSeriesPersonalizationHashMap().get(parName);
				TableItem item = new TableItem (parsTable, SWT.NONE);
				item.setText(NAME, parName);
				if(model.isSeriesColorPersonalization() && serPers.getColor()!=null){
					item.setText(COLOR,ChartEditor.convertRGBToHexadecimal(serPers.getColor()));
				}
				if(model.isSeriesLabelPersonalization() && serPers.getLabel()!=null){
					item.setText(LABEL,serPers.getLabel());
				}
				if(model.isSeriesDrawPersonalization() && serPers.getDraw()!=null){
					item.setText(DRAW,serPers.getDraw());
				}
				if(model.isSeriesScalesPersonalization()){
					item.setText(SCALE,Integer.valueOf(serPers.getScale()).toString());
				}
			}
			for (int i=0; i<titles.length; i++) {
				parsTable.getColumn (i).pack ();
			}	

		} 
		parsTable.pack();


		//******************	Serie NAME *********************

		final Group group=new Group(sectionClientSeries, SWT.NULL);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout g2 = new GridLayout();
		g2.numColumns =4 ;
		group.setLayout(g2);

		//Image imageAdd = PlatformUI.getWorkbench( ).getSharedImages( ).getImage( ISharedImages.IMG_OBJ_ELEMENT);
		Button buttonAdd = new Button(group, SWT.PUSH);
		buttonAdd.setText("    Add Serie   ");
		//buttonAdd.setImage(imageAdd);
		buttonAdd.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		buttonAdd.setBackground(new Color(group.getDisplay(), new RGB( 0,255,255)));
		buttonAdd.setToolTipText("Add serie");
		buttonAdd.pack();

		final Button buttonRem = new Button(group, SWT.PUSH);
		buttonRem.setToolTipText("Remove");
		//		Image imageRem = PlatformUI.getWorkbench( ).getSharedImages( ).getImage( ISharedImages.IMG_TOOL_DELETE);
		buttonRem.setText("  Cancel Serie ");
		buttonRem.setBackground(new Color(group.getDisplay(), new RGB( 0,255,255)));
		buttonRem.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		buttonRem.setToolTipText("Remove serie");
		//		buttonRem.setImage(imageRem);
		buttonRem.pack();

		ChartEditorUtils.addBlanckSpace(group);
		ChartEditorUtils.addBlanckSpace(group);


		Label newNameLabel = new Label(group, SWT.NULL); 
		newNameLabel.setText("    Serie Name: ");
		newNameLabel.setToolTipText("New serie's name");
		//newNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		//newNameLabel.pack();		
		newSerName = new Text(group, SWT.BORDER);
		newSerName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		newSerName.setEnabled(true);


		newSerLabel=new Label(group, SWT.NULL); ;
		newSerLabel.setText("                         Label Serie: ");		
		//newSerLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		newSerLabel.pack();
		newSerLabelText=new Text(group, SWT.BORDER);;
		newSerLabelText.setToolTipText("Serie Label");
		//newSerLabelText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		newSerLabelText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		newSerLabelText.pack();
		newSerLabelText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);				
				String newLabel = newSerLabelText.getText();
				int selection = parsTable.getSelectionIndex();
				String parNameSelected=null;
				if(selection!=-1){
					TableItem tableItem=parsTable.getItem(selection);
					String serName=tableItem.getText(NAME);
					SeriePersonalization seriePers=model.getSeriesPersonalizationHashMap().get(serName);
					if(seriePers!=null){seriePers.setLabel(newLabel);
					tableItem.setText(LABEL, newLabel);
					}
				}
			}
		});
		//		newSerLabel.setEnabled(false);
		//		newSerLabelText.setEnabled(false);




		newColorLabel=new Label(group, SWT.NULL);

		innerSection = toolkit.createComposite(group);
		colorLabel = new Label(innerSection, SWT.BORDER);
		colorButton = new Button(innerSection, SWT.PUSH);
		colorButton.setToolTipText("Color of the serie");
		//newColorLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		newColorLabel.setText("    Color serie: ");
		//		newColorLabel.setEnabled(false);
		newColorLabel.pack();

		final Color color = new org.eclipse.swt.graphics.Color(group.getDisplay(), new RGB(255,255,255));
		GridLayout colorGd = new GridLayout();
		colorGd.numColumns = 2;
		colorGd.marginHeight = 0;
		colorGd.marginBottom = 0;
		innerSection.setLayout(colorGd);
		colorLabel.setText("          ");
		colorLabel.setBackground(color);
		colorButton.setText("Color...");
		final Shell parentShell = group.getShell();
		colorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				final Shell centerShell = new Shell(parentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(parentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
						(parentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(colorLabel.getBackground().getRGB());
				colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(parentShell.getDisplay(), rgb);
					colorLabel.setBackground(newColor);
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
					int selection = parsTable.getSelectionIndex();
					//get ParSelected
					TableItem tableItem=parsTable.getItem(selection);
					String parNameSelected=tableItem.getText(NAME);
					SeriePersonalization seriePers=model.getSeriesPersonalizationHashMap().get(parNameSelected);
					if(seriePers!=null){
						seriePers.setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));
						tableItem.setText(COLOR, newHexadecimal);
						tableItem.setBackground(COLOR, new Color(tableItem.getDisplay(),ChartEditor.convertHexadecimalToRGB(newHexadecimal)));
					}
					//centerShell.pack();
					centerShell.dispose();
				}
			}
		});			
		//		colorLabel.setEnabled(false);
		//		colorButton.setEnabled(false);




		drawLabel=new Label(group, SWT.NULL);
		drawLabel.setText("                         Draw style: ");		
		//drawLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		drawLabel.pack();

		comboDraw=new Combo(group,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		comboDraw.add("bar");
		comboDraw.add("line");
		comboDraw.add("line_no_shape");
		comboDraw.select(0);
		//comboDraw.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		comboDraw.pack();
		comboDraw.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);				
				String comboText = comboDraw.getText();
				int selection = parsTable.getSelectionIndex();
				if(selection!=-1){
					//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();				
					TableItem item=parsTable.getItem(selection);
					String parNameSelected=item.getText(NAME);
					SeriePersonalization seriePers=model.getSeriesPersonalizationHashMap().get(parNameSelected);
					if(parNameSelected!=null){
						seriePers.setDraw(comboText);
						item.setText(DRAW,comboText);
					}
				}
			}
		});
		//		drawLabel.setEnabled(false);
		//		comboDraw.setEnabled(false);
		comboDraw.pack();


		newColorLabel.setToolTipText("Set the drawing shape; can be bar, line or line without shape on the category point");
		scaleLabel=new Label(group, SWT.NULL);
		scaleLabel.setText("    Map to scale: ");		
		//scaleLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		comboScale=new Combo(group,  SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		comboScale.setToolTipText("Map the serie to the first or to the second scale");
		comboScale.add("1");
		comboScale.add("2");
		comboScale.select(0);
		comboScale.pack();
		//comboScale.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		comboScale.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				model.getEditor().setIsDirty(true);				
				String comboText = comboScale.getText();
				int selection = parsTable.getSelectionIndex();
				if(selection!=-1){
					TableItem item=parsTable.getItem(selection);
					String parNameSelected=item.getText(NAME);
					SeriePersonalization seriePers=model.getSeriesPersonalizationHashMap().get(parNameSelected);
					if(parNameSelected!=null){
						seriePers.setScale(Integer.valueOf(comboText).intValue());
						item.setText(SCALE,comboText);
					}
				}
			}
		});
		//		comboScale.setEnabled(false);
		//		scaleLabel.setEnabled(false);
		scaleLabel.pack();
		comboScale.pack();

		// Add Button Listener
		Listener addListener = new Listener() {
			public void handleEvent(Event event) {
				model.getEditor().setIsDirty(true);
				String nameToAdd=newSerName.getText();
				//parsMap=model.getSeriesPersonalizationHashMap();
				if(nameToAdd==null || nameToAdd.equalsIgnoreCase("")){
					logger.warn("Specify a name for serie");
					MessageDialog.openWarning(group.getShell(), "Warning", "Specify a name for serie");
				}
				else if(model.getSeriesPersonalizationHashMap().keySet().contains(nameToAdd)){
					logger.warn("Name already present for Serie");
					MessageDialog.openWarning(group.getShell(), "Warning", "Name already present");					
				}
				else {					
					SeriePersonalization serPers=new SeriePersonalization(nameToAdd);
					TableItem item = new TableItem (parsTable, SWT.NONE);
					item.setText (NAME, nameToAdd);


					if(model.isSeriesLabelPersonalization()){
						if(newSerLabelText.getText()!=null){
							serPers.setLabel(newSerLabelText.getText());
							item.setText (LABEL, newSerLabelText.getText());
						}
					}

					if(model.isSeriesDrawPersonalization()){
						String text=comboDraw.getText();
						serPers.setDraw(text);
						item.setText (DRAW, text);

					}

					if(model.isSeriesScalesPersonalization()){
						String text=comboScale.getText();
						serPers.setScale(Integer.valueOf(text));
						item.setText (SCALE, text);

					}
					if(model.isSeriesColorPersonalization()){
						RGB rgb=colorLabel.getBackground().getRGB();
						String text=ChartEditor.convertRGBToHexadecimal(rgb);
						serPers.setColor(rgb);
						item.setText (COLOR, text);
						item.setBackground(COLOR,new Color(item.getDisplay(),rgb));						

					}


					model.getSeriesPersonalizationHashMap().put(nameToAdd, serPers);
				}

			}
		};
		buttonAdd.addListener(SWT.Selection, addListener);
		buttonAdd.pack();


		// Add listener that show details of parameter selected
		parsTable.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				model.getEditor().setIsDirty(true);				
				// get par selected
				int selection = parsTable.getSelectionIndex();
				TableItem itemSelected=parsTable.getItem(selection);
				String nameSerie=itemSelected.getText(0);
				String colorSerie=itemSelected.getText(1);
				String labelSerie=itemSelected.getText(2);
				String drawSerie=itemSelected.getText(3);
				String scaleSerie=itemSelected.getText(4);

				newSerName.setText(nameSerie);
				SeriePersonalization seriePers=model.getSeriesPersonalizationHashMap().get(nameSerie);

				colorSerie=seriePers.getColor()!=null ? ChartEditor.convertRGBToHexadecimal(seriePers.getColor()) : "";
				if(model.isSeriesColorPersonalization()==true && seriePers.getColor()!=null){
					Color newColor = new Color(parentShell.getDisplay(), seriePers.getColor());
					colorLabel.setBackground(newColor);

				}
				else
				{
					colorLabel.setBackground(null);
				}

				if(model.isSeriesLabelPersonalization()==true){ 
					labelSerie=seriePers.getLabel()!=null ? seriePers.getLabel() : "";
					newSerLabelText.setText(labelSerie);
				}


				String draw=seriePers.getDraw();
				if(model.isSeriesDrawPersonalization()==true && draw!=null && !draw.equalsIgnoreCase("")){
					int index=comboDraw.indexOf(draw);
					comboDraw.select(index);
				}
				else{
					comboDraw.select(0);
				}

				if(model.isSeriesScalesPersonalization()==true){
					int scale=seriePers.getScale();
					int index=comboScale.indexOf(Integer.valueOf(scale).toString());
					comboScale.select(index);
				}

				parsTable.redraw();

				newSerLabel.setEnabled(true);
				newSerLabelText.setEnabled(true);
				colorLabel.setEnabled(true);
				colorButton.setEnabled(true);
				drawLabel.setEnabled(true);
				comboDraw.setEnabled(true);
				scaleLabel.setEnabled(true);
				comboScale.setEnabled(true);
				newColorLabel.setEnabled(true);
				buttonRem.setEnabled(true);
			}	
		});



		// Add Button Listener
		Listener cancelListener = new Listener() {
			public void handleEvent(Event event) {
				model.getEditor().setIsDirty(true);			
				int index=parsTable.getSelectionIndex();
				TableItem item=parsTable.getItem(index);
				String namePar=item.getText(NAME);
				//remove from java list 
				newSerLabelText.setText("");
				colorLabel.setBackground(null);

				if(model.getSeriesPersonalizationHashMap().containsKey(namePar)){
					model.getSeriesPersonalizationHashMap().remove(namePar);
				}
				parsTable.remove(index);
				buttonRem.setEnabled(false);
				//				parsList.pack();			
			}
		};
		buttonRem.addListener(SWT.Selection, cancelListener);
		buttonRem.setEnabled(false);
		group.pack();



		// SET SERIES_ORDER_COLORS

		Label l = new Label(sectionClientSeries, SWT.NULL);
		l.setText("");		
		GridData gd=new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=2	;
		l.setLayoutData(gd);
		orderLabelTitle = new Label(sectionClientSeries, SWT.NULL);
		orderLabelTitle.setText(" Section to set series order colors (overrides series settings) ");		
		orderLabelTitle.setLayoutData(gd);

		orderTable = new Table (sectionClientSeries, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		orderTable.setLinesVisible (true);
		orderTable.setHeaderVisible (true);
		orderTable.setLayoutData(gd);


		GridData dataOrder = new GridData(SWT.FILL, SWT.FILL, true, true);
		dataOrder.heightHint = 150;
		dataOrder.widthHint=500;
		orderTable.setLayoutData(dataOrder);

		ChartEditorUtils.addBlanckSpace(sectionClientSeries);


		String[] titlesOrder = {"       Color      "};
		for (int i=0; i<titlesOrder.length; i++) {
			TableColumn column = new TableColumn (orderTable, SWT.NONE);
			column.setText (titlesOrder [i]);
		}
		if(model.getSeriesOrderPersonalizationVector()!=null){
			for (Iterator iterator = model.getSeriesOrderPersonalizationVector().iterator(); iterator.hasNext();) {
				RGB rgb = (RGB) iterator.next();

				TableItem item = new TableItem (orderTable, SWT.NONE);
				item.setText(0, ChartEditor.convertRGBToHexadecimal(rgb	));
				item.setBackground(new Color(orderTable.getDisplay(),rgb));
			}
			for (int i=0; i<titlesOrder.length; i++) {
				orderTable.getColumn (i).pack ();
			}	
		} 
		orderTable.pack();



		//******************	Serie NAME *********************
		orderGroup=new Group(sectionClientSeries, SWT.NULL);
		orderGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout g3 = new GridLayout();
		g3.numColumns =2 ;
		orderGroup.setLayout(g3);

		//Image imageAdd = PlatformUI.getWorkbench( ).getSharedImages( ).getImage( ISharedImages.IMG_OBJ_ELEMENT);
		Button orderButtonAdd = new Button(orderGroup, SWT.PUSH);
		orderButtonAdd.setText("    Add Color   ");
		//buttonAdd.setImage(imageAdd);
		orderButtonAdd.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		orderButtonAdd.setBackground(new Color(orderGroup.getDisplay(), new RGB( 0,255,255)));
		orderButtonAdd.setToolTipText("Add Color");
		orderButtonAdd.pack();

		final Button orderButtonRem = new Button(orderGroup, SWT.PUSH);
		orderButtonRem.setToolTipText("Remove");
		//		Image imageRem = PlatformUI.getWorkbench( ).getSharedImages( ).getImage( ISharedImages.IMG_TOOL_DELETE);
		orderButtonRem.setText("  Cancel Serie ");
		orderButtonRem.setBackground(new Color(orderGroup.getDisplay(), new RGB( 0,255,255)));
		orderButtonRem.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		orderButtonRem.setToolTipText("Remove serie");
		//		buttonRem.setImage(imageRem);
		orderButtonRem.pack();

		//		ChartEditorUtils.addBlanckSpace(orderGroup);
		//		ChartEditorUtils.addBlanckSpace(orderGroup);


		newOrderColorLabel=new Label(orderGroup, SWT.NULL);

		orderInnerSection = toolkit.createComposite(orderGroup);
		orderColorLabel = new Label(orderInnerSection, SWT.BORDER);
		orderColorButton = new Button(orderInnerSection, SWT.PUSH);
		orderColorButton.setToolTipText("Color of the serie");
		//newColorLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		newOrderColorLabel.setText("    Color serie: ");
		//		newColorLabel.setEnabled(false);
		newOrderColorLabel.pack();

		final Color orderColor = new org.eclipse.swt.graphics.Color(orderGroup.getDisplay(), new RGB(255,255,255));
		GridLayout orderColorGd = new GridLayout();
		orderColorGd.numColumns = 2;
		orderColorGd.marginHeight = 0;
		orderColorGd.marginBottom = 0;
		orderInnerSection.setLayout(orderColorGd);
		orderColorLabel.setText("          ");
		orderColorLabel.setBackground(orderColor);
		orderColorButton.setText("Color...");
		final Shell orderParentShell = orderGroup.getShell();
		orderColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				final Shell centerShell = new Shell(orderParentShell, SWT.NO_TRIM);
				centerShell.setLocation(
						(orderParentShell.getSize().x - ChartEditor.COLORDIALOG_WIDTH) / 2,
						(orderParentShell.getSize().y - ChartEditor.COLORDIALOG_HEIGHT) / 2);
				ColorDialog colorDg = new ColorDialog(centerShell,
						SWT.APPLICATION_MODAL);
				colorDg.setRGB(colorLabel.getBackground().getRGB());
				colorDg.setText("Choose a color");
				RGB rgb = colorDg.open();
				//final Map<String, SeriePersonalization> parsMap=model.getSeriesPersonalizationHashMap();					
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					color.dispose();
					Color newColor = new Color(orderParentShell.getDisplay(), rgb);
					orderColorLabel.setBackground(newColor);
					String newHexadecimal = ChartEditor.convertRGBToHexadecimal(rgb);
					int selection = orderTable.getSelectionIndex();
					//get ParSelected
					TableItem tableItem=orderTable.getItem(selection);
					String parNameSelected=tableItem.getText(0);
					//					SeriePersonalization seriePers=model.getSeriesOrderPersonalizationVector()().get(parNameSelected);
					//					if(seriePers!=null){
					//						seriePers.setColor(ChartEditor.convertHexadecimalToRGB(newHexadecimal));
					//						tableItem.setText(COLOR, newHexadecimal);
					//						tableItem.setBackground(COLOR, new Color(tableItem.getDisplay(),ChartEditor.convertHexadecimalToRGB(newHexadecimal)));
					//					}
					centerShell.dispose();
				}
			}
		});			

		// Add Button Listener
		Listener addOrderListener = new Listener() {
			public void handleEvent(Event event) {
				model.getEditor().setIsDirty(true);
				RGB rgb=orderColorLabel.getBackground().getRGB();
				String color=ChartEditor.convertRGBToHexadecimal(rgb);

				//parsMap=model.getSeriesPersonalizationHashMap();

				TableItem item = new TableItem (orderTable, SWT.NONE);
				item.setBackground(new Color(item.getDisplay(),rgb));						
				item.setText (0, color);
				model.getSeriesOrderPersonalizationVector().add(rgb);
				orderTable.redraw();

			}
		};
		orderButtonAdd.addListener(SWT.Selection, addOrderListener);
		orderButtonAdd.pack();


		// Add listener that show details of parameter selected
		orderTable.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				model.getEditor().setIsDirty(true);				
				// get par selected
				int selection = orderTable.getSelectionIndex();
				TableItem itemSelected=orderTable.getItem(selection);
				String colorSerie=itemSelected.getText(0);

				orderTable.redraw();

				orderButtonRem.setEnabled(true);
			}	
		});



		// Add Button Listener
		Listener cancelOrderListener = new Listener() {
			public void handleEvent(Event event) {
				model.getEditor().setIsDirty(true);			
				int index=orderTable.getSelectionIndex();
				if( index != -1) {
					TableItem item=orderTable.getItem(index);
					String color = item.getText(0);
					RGB rgb = ChartEditor.convertHexadecimalToRGB(color);
					colorLabel.setBackground(null);

					Vector<RGB> vect =model.getSeriesOrderPersonalizationVector();
					if(model.getSeriesOrderPersonalizationVector().contains(rgb)){
						model.getSeriesOrderPersonalizationVector().remove(rgb);
					}

					orderTable.remove(index);
					orderButtonRem.setEnabled(false);
					//				parsList.pack();			
				}
			}
		};
		orderButtonRem.addListener(SWT.Selection, cancelOrderListener);
		orderButtonRem.setEnabled(false);
		orderGroup.pack();


		// disable if not 
		if(!model.isSeriesOrderColorPersonalization()){
			orderTable.setVisible(false);
			orderGroup.setVisible(false);
			orderLabelTitle.setVisible(false);
		}
		else {
			orderTable.setVisible(true);			
			orderGroup.setVisible(true);
			orderLabelTitle.setVisible(true);		}




		sectionClientSeries.pack();
		sectionSeries.setClient(sectionClientSeries);
		sectionSeries.setExpanded(true);
		sectionSeries.setExpanded(false);



	}








	public void setVisible(boolean visible){
		sectionSeries.setVisible(visible);

	}

	public boolean isVisible(){
		if(sectionSeries.isVisible())return true;
		else return false;
	}


	/** Among labels, color, draws personalizations allow only those recorded in config file
	 * 
	 * @param labels
	 * @param colors
	 * @param draws
	 */
	public void enablePersonalizations(boolean labels, boolean colors, boolean draws, boolean scales, boolean orderColors){
		newSerLabel.setVisible(labels);
		newSerLabelText.setVisible(labels);
		colorLabel.setVisible(colors);
		colorButton.setVisible(colors);
		drawLabel.setVisible(draws);
		comboDraw.setVisible(draws);
		scaleLabel.setVisible(scales);
		comboScale.setVisible(scales);
		newColorLabel.setVisible(colors);
		orderTable.setVisible(orderColors);
		orderLabelTitle.setVisible(orderColors);
		orderGroup.setVisible(orderColors);
	}

	public void eraseComposite(){
		parsTable.removeAll();
		newSerName.setText("");
		newSerLabelText.setText("");
		colorLabel.setBackground(null);
		comboDraw.select(0);
		comboScale.select(0);
		if(orderTable != null) {
			orderTable.removeAll();
			newOrderColorLabel.setBackground(new Color(newColorLabel.getDisplay(), new RGB(255,255,255)));
		}

	}

	public void refillFieldsSeriesPersonalization(final ChartModel model, final ChartEditor editor, FormToolkit toolkit, final ScrolledForm form){
		if(model.getSeriesPersonalizationHashMap()!=null){
			for (Iterator iterator = model.getSeriesPersonalizationHashMap().keySet().iterator(); iterator.hasNext();) {
				String serName = (String) iterator.next();
				SeriePersonalization serPers=model.getSeriesPersonalizationHashMap().get(serName);
				TableItem tI=new TableItem(parsTable, SWT.NONE);
				tI.setText(NAME, serPers.getName());
				tI.setText(COLOR,serPers.getColor()!=null ? ChartEditor.convertRGBToHexadecimal(serPers.getColor()) : "");
				tI.setText(LABEL,serPers.getLabel()!=null ? serPers.getLabel() : "");
				tI.setText(DRAW,serPers.getDraw()!=null ? serPers.getDraw() : "");
				tI.setText(SCALE,(Integer.valueOf(serPers.getScale())).toString());
			}
			parsTable.redraw();
		}
	}

}
