/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

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
package it.eng.spagobi.studio.console.model.bo;

import java.io.IOException;

import it.eng.spagobi.studio.utils.exceptions.SavingEditorException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class JsonTemplateGenerator {
	
	public static String transformToJson(Object bean) throws SavingEditorException {
		String result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			//This option exclude object with null value from the serialization
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			//mapper.writeValueAsString(bean);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}	

	/** populate the ConsoleTemplateModel Object from template*/
	public static ConsoleTemplateModel readJson(IFile file) throws CoreException{
		ConsoleTemplateModel objFromJson = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			objFromJson = mapper.readValue(file.getContents(), ConsoleTemplateModel.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objFromJson;
	}
	
	//Only for testing
	public static void main(String[] args) {
		//Example fake model
		ConsoleTemplateModel example = new ConsoleTemplateModel();
		DatasetElement dataset_one = new DatasetElement();
		DatasetElement dataset_two = new DatasetElement();

		example.getDataset().add(dataset_one);
		example.getDataset().add(dataset_two);
		
		SummaryPanel summaryPanel = new SummaryPanel();
		example.setSummaryPanel(summaryPanel);
		
		LayoutManagerConfig layoutManagerConfig = new LayoutManagerConfig();
		summaryPanel.setLayoutConfig(layoutManagerConfig);
		
		layoutManagerConfig.getColumnWidths().add(".25");
		layoutManagerConfig.getColumnWidths().add(".25");
		layoutManagerConfig.getColumnWidths().add(".25");
		layoutManagerConfig.getColumnWidths().add(".25");

		
		
		Chart chart = new Chart();
		Chart chart_two = new Chart();
		
		WidgetConfigElement widget_one = new WidgetConfigElementLiveLine();
		chart.setWidgetConfig(widget_one);
		
		WidgetConfigElement widget_two = new WidgetConfigElementSemaphore();
		chart_two.setWidgetConfig(widget_two);

		summaryPanel.getCharts().add(chart);
		summaryPanel.getCharts().add(chart_two);

		

		try {
			System.out.println(transformToJson(example));
		} catch (SavingEditorException e) {
			e.printStackTrace();
		}
		

	}

}
