/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.

 **/
package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.domains.bo.SDKDomain;

public class Domain {

	private Integer valueId;

	private String domainCd;

	private String domainNm;

	private String valueCd;

	private String valueNm;

	private String valueDs;





	public Domain(SDKDomain sdk) {
		super();
		valueId = sdk.getValueId();
		domainCd = sdk.getDomainCd();
		domainNm = sdk.getDomainNm();
		valueCd = sdk.getValueCd();
		valueNm = sdk.getValueNm();
		valueDs = sdk.getValueDs();

	}





	public Integer getValueId() {
		return valueId;
	}





	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}





	public String getDomainCd() {
		return domainCd;
	}





	public void setDomainCd(String domainCd) {
		this.domainCd = domainCd;
	}





	public String getDomainNm() {
		return domainNm;
	}





	public void setDomainNm(String domainNm) {
		this.domainNm = domainNm;
	}





	public String getValueCd() {
		return valueCd;
	}





	public void setValueCd(String valueCd) {
		this.valueCd = valueCd;
	}





	public String getValueNm() {
		return valueNm;
	}





	public void setValueNm(String valueNm) {
		this.valueNm = valueNm;
	}





	public String getValueDs() {
		return valueDs;
	}





	public void setValueDs(String valueDs) {
		this.valueDs = valueDs;
	}









}
