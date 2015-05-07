/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.engines.bo.SDKEngine;

public class Engine {

	private String className;

	private String description;

	private String documentType;

	private String driverClassName;

	private String driverName;

	private Integer encrypt;

	private String engineType;

	private Integer id;

	private String label;

	private String mainUrl;

	private String name;

	private String secondUrl;

	private String url;

	private Boolean useDataSet;

	private Boolean useDataSource;




	public Engine(SDKEngine sdk) {
		super();

		className = sdk.getClassName();
		description = sdk.getDescription();
		documentType = sdk.getDocumentType();
		driverClassName = sdk.getDriverClassName();
		driverName = sdk.getDriverName();
		encrypt = sdk.getEncrypt();
		engineType = sdk.getEngineType();
		id = sdk.getId();
		label = sdk.getLabel();
		mainUrl = sdk.getMainUrl();
		name = sdk.getName();
		secondUrl = sdk.getSecondUrl();
		url = sdk.getUrl();
		useDataSet = sdk.getUseDataSet();
		useDataSource = sdk.getUseDataSource();

	}







	public String getClassName() {
		return className;
	}










	public void setClassName(String className) {
		this.className = className;
	}










	public String getDescription() {
		return description;
	}










	public void setDescription(String description) {
		this.description = description;
	}










	public String getDocumentType() {
		return documentType;
	}










	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}










	public String getDriverClassName() {
		return driverClassName;
	}










	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}










	public String getDriverName() {
		return driverName;
	}










	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}










	public Integer getEncrypt() {
		return encrypt;
	}










	public void setEncrypt(Integer encrypt) {
		this.encrypt = encrypt;
	}










	public String getEngineType() {
		return engineType;
	}










	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}










	public Integer getId() {
		return id;
	}










	public void setId(Integer id) {
		this.id = id;
	}










	public String getLabel() {
		return label;
	}










	public void setLabel(String label) {
		this.label = label;
	}










	public String getMainUrl() {
		return mainUrl;
	}










	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}










	public String getName() {
		return name;
	}










	public void setName(String name) {
		this.name = name;
	}










	public String getSecondUrl() {
		return secondUrl;
	}










	public void setSecondUrl(String secondUrl) {
		this.secondUrl = secondUrl;
	}










	public String getUrl() {
		return url;
	}










	public void setUrl(String url) {
		this.url = url;
	}










	public Boolean getUseDataSet() {
		return useDataSet;
	}










	public void setUseDataSet(Boolean useDataSet) {
		this.useDataSet = useDataSet;
	}










	public Boolean getUseDataSource() {
		return useDataSource;
	}










	public void setUseDataSource(Boolean useDataSource) {
		this.useDataSource = useDataSource;
	}











}
