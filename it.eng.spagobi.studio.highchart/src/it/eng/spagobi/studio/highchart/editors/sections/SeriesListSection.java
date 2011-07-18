package it.eng.spagobi.studio.highchart.editors.sections;

import it.eng.spagobi.studio.highchart.model.bo.HighChart;
import it.eng.spagobi.studio.highchart.model.bo.Serie;
import it.eng.spagobi.studio.highchart.model.bo.SeriesList;
import it.eng.spagobi.studio.highchart.utils.ColorButton;
import it.eng.spagobi.studio.highchart.utils.ImageDescriptors;
import it.eng.spagobi.studio.highchart.utils.SWTUtils;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriesListSection extends AbstractSection {

	private static Logger logger = LoggerFactory.getLogger(DrillSection.class);

	Text documentText;

	Button insertButton;
	Button saveButton;
	Button deleteButton;

	Text nameText;
	Button chooseColor;
	ColorButton colorButton;
	Text aliasText;
	Combo typeCombo;
	Text sizetext;
	Text innerSizeText;

	Table seriesTable;

	static final int NAME = 0;
	static final int ALIAS = 1;
	static final int COLOR = 2;
	static final int TYPE = 3;
	static final int SIZE = 4;
	static final int INNER_SIZE = 5;


	public SeriesListSection(HighChart highChart) {
		super(highChart);
	}

	public void drawSection(final FormToolkit toolkit, final ScrolledForm form, int cols) {
		super.drawSection(toolkit, form, cols);
		logger.debug("IN");

		section.setText("Series List");
		section.setDescription("Series list options");

		SeriesList seriesList = highChart.getSeriesList();
		section.setClient(composite);

		// Parameters
		Composite paramComposite = toolkit.createComposite(composite);
		GridLayout gla = new GridLayout();
		gla.numColumns = 6;
		paramComposite.setLayout(gla);
		paramComposite.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		// three buttons
		insertButton = toolkit.createButton(paramComposite, "", SWT.PUSH);
		Image insertImage = ImageDescriptors.getAddIcon().createImage();
		insertButton.setImage(insertImage);
		insertButton.setEnabled(true);
		saveButton = toolkit.createButton(paramComposite, "", SWT.PUSH);		
		Image saveImage = ImageDescriptors.getSaveIcon().createImage();
		saveButton.setImage(saveImage);
		saveButton.setEnabled(false);
		deleteButton = toolkit.createButton(paramComposite, "", SWT.PUSH);
		Image deleteImage = ImageDescriptors.getEraseIcon().createImage();
		deleteButton.setImage(deleteImage);
		deleteButton.setEnabled(false);

		// the table		
		seriesTable = new Table (paramComposite, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		seriesTable.setLinesVisible (true);
		seriesTable.setHeaderVisible (true);
		GridData g=new GridData(GridData.FILL_BOTH);
		g.horizontalSpan=3;
		g.verticalSpan=8;
		g.grabExcessHorizontalSpace=true;
		g.grabExcessVerticalSpace=true;
		g.heightHint = 200;
		g.widthHint = 400;
		seriesTable.setLayoutData(g);

		String[] titles = {"          Name          ", "        Alias       ", "         Color          ","         Type          ", "  Size "," Inner Size " };
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (seriesTable, SWT.NONE);
			column.setText (titles [i]);
		}
		if(seriesList.getSeries()!=null){
			Vector<Serie> series = seriesList.getSeries();

			for (Iterator iterator = series.iterator(); iterator.hasNext();) {
				Serie serie = (Serie) iterator.next();
				TableItem item = new TableItem (seriesTable, SWT.NONE);
				if(serie.getName() != null)
					item.setText(NAME, serie.getName());
				if(serie.getAlias() != null)
					item.setText(ALIAS, serie.getAlias());
				if(serie.getSize() != null)
					item.setText(SIZE, serie.getSize());
				if(serie.getInnerSize() != null)
					item.setText(INNER_SIZE, serie.getInnerSize());
				if(serie.getColor() != null && !serie.getColor().equalsIgnoreCase("")){
					item.setText(COLOR, serie.getColor());
					item.setBackground(COLOR, SWTUtils.getColor(seriesTable.getDisplay(), serie.getColor()));	
					seriesTable.redraw();
				}
				if(serie.getType() != null)
					item.setText(TYPE, serie.getType());

			}

			for (int i=0; i<titles.length; i++) {
				seriesTable.getColumn (i).pack ();
			}	
		}

		nameText = SWTUtils.drawText(toolkit, paramComposite, "", "Name: ");
		nameText.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		nameText.setEnabled(false);
		nameText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

		chooseColor = SWTUtils.drawCheck(paramComposite, false, "Choose color");
		chooseColor.setEnabled(false);
		colorButton = SWTUtils.drawColorButton(toolkit, paramComposite, null, "Color");
		//colorButton.getColorLabel().setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		colorButton.getColorButton().setEnabled(false);
		colorButton.getColorButton().setVisible(false);
		colorButton.getColorLabel().setVisible(false);


		aliasText = SWTUtils.drawText(toolkit, paramComposite, "", "Alias: ");
		aliasText.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		aliasText.setEnabled(false);
		aliasText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

		sizetext = SWTUtils.drawText(toolkit, paramComposite, "", "Size (%): ");
		toolkit.createLabel(paramComposite, "");
		innerSizeText = SWTUtils.drawText(toolkit, paramComposite, "", "Inner size (%): ");
		toolkit.createLabel(paramComposite, "");

		typeCombo = SWTUtils.drawCombo(paramComposite, new String[]{"", "line", "spline", "area", "areaspline", "column", "bar", "pie", "scatter"}, null, "Type: ");
		typeCombo.setLayoutData(SWTUtils.getGridDataSpan(2, GridData.FILL_HORIZONTAL));
		typeCombo.setEnabled(false);

		addButtonListeners(seriesList);

		logger.debug("OUT");

	}


	public void addButtonListeners(final SeriesList seriesList){

		insertButton.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				editor.setIsDirty(true);
				// ADD button: 1) remove selection from table 2)disable cancel, enable save, 3) enable fields
				seriesTable.setSelection(-1);
				insertButton.setEnabled(false);
				chooseColor.setEnabled(true);
				chooseColor.setSelection(false);
				deleteButton.setEnabled(false);
				saveButton.setEnabled(true);
				nameText.setText("");
				nameText.setEnabled(true);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));

				colorButton.getColorLabel().setBackground(SWTUtils.getColor(colorButton.getColorButton().getDisplay(), SWTUtils.LIGHT_BLUE));
				colorButton.getColorButton().setEnabled(true);

				aliasText.setText("");
				aliasText.setEnabled(true);
				aliasText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));
				sizetext.setText("");
				sizetext.setEnabled(true);
				innerSizeText.setText("");
				innerSizeText.setEnabled(true);

				typeCombo.select(0);
				typeCombo.setEnabled(true);

			}

		}
		);

		chooseColor.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				colorButton.getColorButton().setVisible(chooseColor.getSelection());
				colorButton.getColorLabel().setVisible(chooseColor.getSelection());
			}
		});


		deleteButton.addListener(SWT.Selection, 
				new Listener() {

			public void handleEvent(Event event) {
				editor.setIsDirty(true);
				// delete button: 1) remove item2) remove selection from table 3)disable cancel, disable save, enable insert, 3) disable fields
				int index = seriesTable.getSelectionIndex();
				TableItem toremove = seriesTable.getItem(index);
				String name = toremove.getText(0);

				Vector<Serie> series = seriesList.getSeries();
				int indexTRoRemove = searchOnVectorSeries(seriesList, name);
				series.remove(indexTRoRemove);

				seriesTable.remove(indexTRoRemove);
				seriesTable.setSelection(-1);
				insertButton.setEnabled(true);
				deleteButton.setEnabled(false);
				saveButton.setEnabled(false);
				chooseColor.setEnabled(false);
				nameText.setText("");
				nameText.setEnabled(false);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));
				colorButton.getColorLabel().setBackground(SWTUtils.getColor(colorButton.getColorButton().getDisplay(), SWTUtils.WHITE));
				colorButton.getColorButton().setEnabled(false);
				chooseColor.setSelection(false);
				aliasText.setText("");
				aliasText.setEnabled(false);
				aliasText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));
				sizetext.setText("");
				sizetext.setEnabled(false);
				innerSizeText.setText("");
				innerSizeText.setEnabled(false);
				typeCombo.select(0);
				typeCombo.setEnabled(false);
				colorButton.getColorButton().setVisible(false);
				colorButton.getColorLabel().setVisible(false);

			}

		}
		);

		saveButton.addListener(SWT.Selection, 
				new Listener() {

			public void handleEvent(Event event) {
				editor.setIsDirty(true);

				// check name
				String name = nameText.getText();
				String color = null;
				if(chooseColor.getSelection()){
					color = SWTUtils.convertRGBToHexadecimal(colorButton.getColorLabel().getBackground().getRGB());
				}
				String alias = aliasText.getText();
				String type = typeCombo.getItem(typeCombo.getSelectionIndex());
				String size = sizetext.getText();
				String innerSize = innerSizeText.getText();



				// is tu update or insert
				int tableIndex = seriesTable.getSelectionIndex();
				Vector<Serie> series = seriesList.getSeries();
				if(tableIndex == -1){
					//insert: check not already existing
					int indexfound = searchOnVectorSeries(seriesList, name);
					if(indexfound != -1){
						MessageDialog.openWarning(composite.getShell(), "Warning", "Name "+name+" already present.");
						return;
					}

					//can insert
					Serie serie = new Serie();
					serie.setName(name);
					serie.setAlias(alias);
					if(!type.equalsIgnoreCase(""))
						serie.setType(type);
					if(!size.equalsIgnoreCase(""))
						serie.setSize(size);
					if(!innerSize.equalsIgnoreCase(""))
						serie.setInnerSize(innerSize);
					if(color != null) serie.setColor(color);
					seriesList.getSeries().add(serie);
					TableItem item = new TableItem (seriesTable, SWT.NONE);
					item.setText(NAME, name);					
					item.setText(ALIAS, alias);					
					if(color != null){
						item.setText(COLOR, color);
						item.setBackground(COLOR, SWTUtils.getColor(seriesTable.getDisplay(), color));
						seriesTable.redraw();
					}
					else{
						item.setText(COLOR, "");
						item.setBackground(COLOR, SWTUtils.getColor(seriesTable.getDisplay(), SWTUtils.WHITE));
						seriesTable.redraw();
					}
					item.setText(TYPE, type);	
					item.setText(SIZE, size);	
					item.setText(INNER_SIZE, innerSize);	

					seriesTable.redraw();
				}
				else{
					// update
					TableItem selecteditem = seriesTable.getItem(seriesTable.getSelectionIndex());
					String nameSelected = selecteditem.getText(NAME);
					int indexParam = searchOnVectorSeries(seriesList, nameSelected);
					Serie serie = seriesList.getSeries().get(indexParam);

					int indexNewParam = searchOnVectorSeries(seriesList, name);
					// check new Name does not exist
					if(indexNewParam != -1 && indexNewParam != indexParam){
						MessageDialog.openWarning(composite.getShell(), "Warning", "Name "+name+" already present.");
						return;
					}

					serie.setName(name);
					serie.setAlias(alias);
					serie.setColor(color);
					if(!type.equalsIgnoreCase(""))serie.setType(type);
					if(!size.equalsIgnoreCase(""))serie.setSize(size);
					if(!innerSize.equalsIgnoreCase(""))serie.setInnerSize(innerSize);

					selecteditem.setText(NAME,name);
					selecteditem.setText(ALIAS,alias);
					selecteditem.setText(SIZE,size);
					selecteditem.setText(INNER_SIZE,innerSize);
					selecteditem.setText(ALIAS,alias);
					selecteditem.setText(TYPE,type);					
					if(color != null) {
						selecteditem.setText(COLOR,color);
						selecteditem.setBackground(COLOR, SWTUtils.getColor(seriesTable.getDisplay(), color));
						seriesTable.redraw();
					}
					else {
						selecteditem.setText(COLOR,"");
						selecteditem.setBackground(COLOR, SWTUtils.getColor(seriesTable.getDisplay(), SWTUtils.WHITE));

					}

					seriesTable.redraw();
				}

				// enable insert, disable delete, disable selection	
				seriesTable.setSelection(-1);

				insertButton.setEnabled(true);
				deleteButton.setEnabled(false);
				saveButton.setEnabled(false);
				nameText.setEnabled(false);
				chooseColor.setEnabled(false);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));

				colorButton.getColorLabel().setBackground(SWTUtils.getColor(colorButton.getColorButton().getDisplay(), SWTUtils.WHITE));
				colorButton.getColorButton().setEnabled(false);
				colorButton.getColorButton().setVisible(false);
				colorButton.getColorLabel().setVisible(false);
				chooseColor.setSelection(false);
				aliasText.setText("");
				aliasText.setEnabled(false);
				aliasText.setBackground(new Color(composite.getDisplay(), new RGB(200,200,200)));
				sizetext.setText("");
				sizetext.setEnabled(false);
				innerSizeText.setText("");
				innerSizeText.setEnabled(false);
				typeCombo.select(0);
				typeCombo.setEnabled(false);
			}

		}
		);


		// Add listener that show details of parameter selected
		seriesTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TableItem tableItem = seriesTable.getItem(seriesTable.getSelectionIndex());
				String name = tableItem.getText(NAME);
				String alias = tableItem.getText(ALIAS);
				String color = tableItem.getText(COLOR);
				String type =tableItem.getText(TYPE);
				String size =tableItem.getText(SIZE);
				String innerSize =tableItem.getText(INNER_SIZE);

				nameText.setText(name);
				nameText.setEnabled(true);
				chooseColor.setEnabled(true);
				nameText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));

				aliasText.setText(alias);
				aliasText.setEnabled(true);
				aliasText.setBackground(new Color(composite.getDisplay(), new RGB(255,255,255)));

				sizetext.setText(size);
				sizetext.setEnabled(true);
				innerSizeText.setText(innerSize);
				innerSizeText.setEnabled(true);		

				typeCombo.setText(type);
				typeCombo.setEnabled(true);

				if(color != null && !color.equals("")){
					chooseColor.setSelection(true);
					colorButton.getColorLabel().setBackground(SWTUtils.getColor(nameText.getDisplay(), color));
					colorButton.getColorLabel().setEnabled(true);
					colorButton.getColorButton().setEnabled(true);

				}
				saveButton.setEnabled(true);
				deleteButton.setEnabled(true);
				insertButton.setEnabled(true);
				seriesTable.redraw();
			}
		});



		colorButton.getColorButton().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String colorSelected =  colorButton.handleSelctionEvent(colorButton.getColorLabel().getShell());
				//				highChart.getChart().setBackgroundColor(colorSelected);
				editor.setIsDirty(true);
			}
		});			

	}


	public int searchOnVectorSeries(SeriesList seriesList, String name){
		Vector<Serie> series = seriesList.getSeries();
		int indexTRoRemove = -1;
		for (Iterator iterator = series.iterator(); iterator.hasNext() && indexTRoRemove == -1;) {
			Serie param = (Serie) iterator.next();
			if(param.getName().equals(name)){
				indexTRoRemove = series.indexOf(param);
			}
		}
		return indexTRoRemove;
	}

}
