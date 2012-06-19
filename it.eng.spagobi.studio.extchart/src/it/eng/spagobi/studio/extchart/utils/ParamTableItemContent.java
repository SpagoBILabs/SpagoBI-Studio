package it.eng.spagobi.studio.extchart.utils;

import it.eng.spagobi.studio.extchart.model.bo.Param;
import it.eng.spagobi.studio.extchart.model.bo.Series;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;

public class ParamTableItemContent {


	Button deleteButton;
	Param param;



	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}

	/**
	 * @return the param
	 */
	public Param getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(Param param) {
		this.param = param;
	}



	
	
	
}
