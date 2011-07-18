package it.eng.spagobi.studio.highchart.utils;

import it.eng.spagobi.studio.highchart.Activator;
import it.eng.spagobi.studio.highchart.configuration.HighChartConfigurations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class HighChartUtils {

	public static final String TYPE_AREA="area";
	public static final String TYPE_BAR="bar";
	public static final String TYPE_PIE="pie";
	public static final String TYPE_LINE="line";
	public static final String TYPE_SPLINE="spline";
	public static final String TYPE_AREASPLINE="areaspline";
	public static final String TYPE_COLUMN="column";
	public static final String TYPE_SCATTER="scatter";
	
	
	/** Get input stream from a resource
	 * 
	 * @param resourcePath
	 * @return
	 * @throws IOException
	 */

	public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
		Bundle b = Platform.getBundle(Activator.PLUGIN_ID);
		URL res = b.getResource(resourcePath);
		InputStream is = res.openStream();
		return is;
	}
	
	
	
	/** Get the chart image path for the selected type
	 * 
	 * @param imageType
	 * @return
	 * @throws Exception
	 */

	public static String getChartImagePath(String imageType) throws Exception {
		String toReturn = null;
		InputStream is = getInputStreamFromResource(HighChartConfigurations.INFO_FILE);
		Document document = new SAXReader().read(is);
		List charts = document.selectNodes("//HIGHCHARTS/HIGHCHART");
		if (charts == null || charts.size() == 0) throw new Exception("No charts configured");
		for (int i = 0; i < charts.size(); i++) {
			Node chart = (Node) charts.get(i);
			String type = chart.valueOf("@type");
			if (imageType.equalsIgnoreCase(type)) {
				String imagePath = chart.valueOf("@imagePath");
				toReturn = imagePath;
				break;
			}
		}
		return toReturn;
	}
	
}
