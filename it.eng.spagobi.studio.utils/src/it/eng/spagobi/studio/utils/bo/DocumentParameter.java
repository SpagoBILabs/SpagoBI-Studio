package it.eng.spagobi.studio.utils.bo;

import it.eng.spagobi.sdk.documents.bo.SDKConstraint;
import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;

public class DocumentParameter {

	private Constraint[] constraints;

	private  Integer id;

	private  String label;

	private  String type;

	private  String urlName;

	private  Object[] values;




	public DocumentParameter(SDKDocumentParameter sdk) {
		id = sdk.getId();
		label = sdk.getLabel();
		type = sdk.getType();
		urlName = sdk.getUrlName();
		values = sdk.getValues();

		SDKConstraint[] con = sdk.getConstraints();
		if(con != null){
			constraints = new Constraint[con.length];
			for (int i = 0; i < con.length; i++) {
				constraints[i] = new Constraint(con[i]);
			}			
		}


	}

	public DocumentParameter(DocumentParameter documentParameter) {
		super();

	}



	public Constraint[] getConstraints() {
		return constraints;
	}

	public void setConstraints(Constraint[] constraints) {
		this.constraints = constraints;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}





}
