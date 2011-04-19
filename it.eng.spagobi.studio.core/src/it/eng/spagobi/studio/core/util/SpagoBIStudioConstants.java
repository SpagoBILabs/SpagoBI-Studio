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
package it.eng.spagobi.studio.core.util;

public class SpagoBIStudioConstants {


	// TYPES FOR SPAGOBI STUDIO
	public final static String DASHBOARD_ENGINE_EXTENSION="sbidash";
	public final static String CHART_ENGINE_EXTENSION="sbichart";
	public final static String JASPER_REPORT_ENGINE_EXTENSION="jrxml";
	public final static String BIRT_REPORT_ENGINE_EXTENSION="rptdesign";
	public final static String DOCUMENT_COMPOSITION_ENGINE_EXTENSION="sbidoccomp";
	public final static String GEO_ENGINE_EXTENSION="sbigeo";

	public final static String SERVER_EXTENSION="sbiserver";

	
	
	// Engine Labels
	public final static String DASHBOARD_ENGINE_LABEL="DashboardInternalEng";
	public final static String CHART_ENGINE_LABEL="ChartEngine";
	public final static String JASPER_REPORT_ENGINE_LABEL="JasperReportEngine";
	public final static String BIRT_REPORT_ENGINE_LABEL="BirtEngine";
	public final static String OLAP_ENGINE_LABEL="JPivotEngine";
	public final static String ETL_ENGINE_LABEL="TalendEngine";
	public final static String DOSSIER_ENGINE_LABEL="Dossier";
	public final static String DATA_MINING_ENGINE_LABEL="WekaEngine";
	public final static String QBE_ENGINE_LABEL="QbeEngine";
	public final static String OFFICE_DOCUMENT_ENGINE_LABEL="OfficeInternalEng";
	public final static String GEO_ENGINE_LABEL="GeoEngine";
	public final static String DOCUMENT_COMPOSITION_ENGINE_LABEL="DocumentCompositionInternalEng";
	
	public final static int BIOBJECT_LABEL_LIMIT=30;
	public final static int BIOBJECT_NAME_LIMIT=40;
	public final static int BIOBJECT_DESCRIPTION_LIMIT=160;
	
	// Name of prohect folders
	public static final String FOLDER_RESOURCE= "Resources";
	public static final String FOLDER_SERVER = "Server";
	public static final String FOLDER_DATA_SOURCE = "Data Source";
	public static final String FOLDER_METADATA_MODEL = "Metadata Model";
	public static final String FOLDER_DATASET = "DataSet";
	public static final String FOLDER_ANALYSIS = "Sbi Analysis";	
	public static final String FOLDER_PRIVATE_DOCUMENTS = "Private Documents";
	
	// name of project folder icons 
	public static final String FOLDER_ICON_RESOURCE= "resources.png";
	public static final String FOLDER_ICON_SERVER = "server.png";
	public static final String FOLDER_ICON_SERVER_ACTIVE = "server_active.png";
	public static final String FOLDER_ICON_SERVER_INACTIVE = "server_inactive.png";
	public static final String FOLDER_ICON_DATA_SOURCE = "datasource.png";
	public static final String FOLDER_ICON_METADATA_MODEL = "metadata.png";
	public static final String FOLDER_ICON_DATASET = "dataset.png";
	public static final String FOLDER_ICON_ANALYSIS = "analysis.png";	
	public static final String FOLDER_ICON_PRIVATE_DOCUMENTS = "private.png";
	public static final String FOLDER_ICON_SBI_PROJECT = "sbiproject.gif";
	
// 	Component ID
	public static final String SERVER_EDITOR_ID = "it.eng.spagobi.studio.core.editors.ServerEditor";
	
	
}
