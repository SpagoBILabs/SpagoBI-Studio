package it.eng.spagobi.studio.extchart.editors.pages.editorComponent;

import it.eng.spagobi.studio.extchart.editors.ExtChartEditor;
import it.eng.spagobi.studio.extchart.editors.properties.PropertiesFactory;
import it.eng.spagobi.studio.extchart.editors.properties.series.SeriesProperties;
import it.eng.spagobi.studio.extchart.model.bo.Axes;
import it.eng.spagobi.studio.extchart.model.bo.ExtChart;
import it.eng.spagobi.studio.extchart.model.bo.Series;
import it.eng.spagobi.studio.extchart.utils.ExtChartConstants;
import it.eng.spagobi.studio.extchart.utils.ExtChartUtils;
import it.eng.spagobi.studio.extchart.utils.ImageDescriptors;
import it.eng.spagobi.studio.extchart.utils.SWTUtils;
import it.eng.spagobi.studio.extchart.utils.SerieTableItemContent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.LoggerFactory;

public class SingleSeriePanel {

	Vector<Series> series;
	Series newSerie;
	String serieType;
	Composite container;
	ExtChartEditor editor;



	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SingleSeriePanel.class);


	public SingleSeriePanel(Composite parent, int style, Vector<Series> series) {
		this.series = series;
		
		container = new Composite(parent,SWT.NONE);
		container.setLayout(SWTUtils.makeGridLayout(1));
		
		if (series.size() >0 ){
			newSerie = series.get(0);
		} else {
			newSerie= new Series();		
		}
		
	}


	public void drawSerieComposite(){
		logger.debug("IN");

		ExtChart extChart = editor.getExtChart();
		try {
			serieType = ExtChartUtils.getSerieTypeFromChartType(extChart.getType());
		} catch (Exception e) {
			logger.warn("could not find default serie type, check congfiguration");
			return;
		}
		newSerie.setType(serieType);
		if (extChart.getSeriesList().getSeries().isEmpty()){
			extChart.getSeriesList().getSeries().add(newSerie);
		}
		
		Group grpSerieProperties = new Group(container, SWT.NONE);

		grpSerieProperties.setText("Serie Properties");
		grpSerieProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpSerieProperties.setLayout(new GridLayout(2, false));
		
		Label lblThisTypeOf = new Label(grpSerieProperties, SWT.NONE);
		lblThisTypeOf.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblThisTypeOf.setText("This type of chart has only one serie.");
		
		Button btnCustomizeSerie = new Button(grpSerieProperties, SWT.NONE);
		btnCustomizeSerie.setText("Customize Serie");
		btnCustomizeSerie.addListener(SWT.Selection, 
				new Listener() {
			public void handleEvent(Event event) {
				// open edit window
				logger.debug("Open serie edit cell");
				ExtChart chart = editor.getExtChart();
				String type = chart.getType();
				logger.debug("Series properties for type "+type);
				SeriesProperties seriesProperties = PropertiesFactory.getSeriesProperties(type, editor, newSerie, container.getShell());
				seriesProperties.setTitle("Define serie properties: ");
				seriesProperties.drawProperties();
				seriesProperties.drawButtons();
				seriesProperties.showPopup();	
			}

		}
		);
		logger.debug("OUT");


	}



	public ExtChartEditor getEditor() {
		return editor;
	}


	public void setEditor(ExtChartEditor editor) {
		this.editor = editor;
	}


	/**
	 * @return the container
	 */
	public Composite getContainer() {
		return container;
	}


	/**
	 * @param container the container to set
	 */
	public void setContainer(Composite container) {
		this.container = container;
	}





}


