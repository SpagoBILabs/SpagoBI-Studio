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
package it.eng.spagobi.studio.core.bo;

import it.eng.spagobi.sdk.datasets.bo.SDKDataSet;
import it.eng.spagobi.sdk.datasources.bo.SDKDataSource;

public class DataSource {


	private java.lang.String attrSchema;

	private java.lang.String descr;

	private java.lang.Integer dialectId;

	private java.lang.String driver;

	private java.lang.Integer id;

	private java.lang.String jndi;

	private java.lang.String label;

	private java.lang.Integer multiSchema;

	private java.lang.String name;

	private java.lang.String pwd;

	private java.lang.String urlConnection;



	public DataSource(SDKDataSource sdk) {
		super();
		attrSchema=sdk.getAttrSchema();
		descr=sdk.getDescr();
		dialectId=sdk.getDialectId();
		driver=sdk.getDriver();
		id=sdk.getId();
		jndi=sdk.getJndi();
		label=sdk.getLabel();
		name=sdk.getName();
		multiSchema=sdk.getMultiSchema();
		pwd=sdk.getPwd();
		urlConnection=sdk.getUrlConnection();
	}

	
	public java.lang.String getAttrSchema() {
		return attrSchema;
	}

	public void setAttrSchema(java.lang.String attrSchema) {
		this.attrSchema = attrSchema;
	}

	public java.lang.String getDescr() {
		return descr;
	}

	public void setDescr(java.lang.String descr) {
		this.descr = descr;
	}

	public java.lang.Integer getDialectId() {
		return dialectId;
	}

	public void setDialectId(java.lang.Integer dialectId) {
		this.dialectId = dialectId;
	}

	public java.lang.String getDriver() {
		return driver;
	}

	public void setDriver(java.lang.String driver) {
		this.driver = driver;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getJndi() {
		return jndi;
	}

	public void setJndi(java.lang.String jndi) {
		this.jndi = jndi;
	}

	public java.lang.String getLabel() {
		return label;
	}

	public void setLabel(java.lang.String label) {
		this.label = label;
	}

	public java.lang.Integer getMultiSchema() {
		return multiSchema;
	}

	public void setMultiSchema(java.lang.Integer multiSchema) {
		this.multiSchema = multiSchema;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getPwd() {
		return pwd;
	}

	public void setPwd(java.lang.String pwd) {
		this.pwd = pwd;
	}

	public java.lang.String getUrlConnection() {
		return urlConnection;
	}

	public void setUrlConnection(java.lang.String urlConnection) {
		this.urlConnection = urlConnection;
	}



}
