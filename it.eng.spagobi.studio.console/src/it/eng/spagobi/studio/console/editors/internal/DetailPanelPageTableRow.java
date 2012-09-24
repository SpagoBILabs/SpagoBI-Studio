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
package it.eng.spagobi.studio.console.editors.internal;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class DetailPanelPageTableRow {
	
	//this represent the single item in the row
	private TableItem tableItem;
	private CCombo comboHeaderType;
	private CCombo comboType;
	private Text textWidth;
	private Text textHeader;
	
	public DetailPanelPageTableRow(TableItem tableItem,Text textHeader,CCombo tableEditorCombo,CCombo comboType, Text textWidth ){
		this.tableItem = tableItem;
		this.comboHeaderType = tableEditorCombo;
		this.comboType = comboType;
		this.textWidth = textWidth;
		this.textHeader = textHeader;
	}
	
	/**
	 * @return the tableItem
	 */
	public TableItem getTableItem() {
		return tableItem;
	}
	/**
	 * @param tableItem the tableItem to set
	 */
	public void setTableItem(TableItem tableItem) {
		this.tableItem = tableItem;
	}
	/**
	 * @return the tableEditorCombo
	 */
	public CCombo getTableEditorCombo() {
		return comboHeaderType;
	}
	/**
	 * @param tableEditorCombo the tableEditorCombo to set
	 */
	public void setTableEditorCombo(CCombo tableEditorCombo) {
		this.comboHeaderType = tableEditorCombo;
	}
	
	/**
	 * @return the comboType
	 */
	public CCombo getComboType() {
		return comboType;
	}

	/**
	 * @param comboType the comboType to set
	 */
	public void setComboType(CCombo comboType) {
		this.comboType = comboType;
	}

	/**
	 * @return the textWidth
	 */
	public Text getTextWidth() {
		return textWidth;
	}

	/**
	 * @param textWidth the textWidth to set
	 */
	public void setTextWidth(Text textWidth) {
		this.textWidth = textWidth;
	}

	/**
	 * @return the textHeader
	 */
	public Text getTextHeader() {
		return textHeader;
	}

	/**
	 * @param textHeader the textHeader to set
	 */
	public void setTextHeader(Text textHeader) {
		this.textHeader = textHeader;
	}

	public void disposeRowElements() {
		tableItem.dispose();
		comboHeaderType.dispose();
		comboType.dispose();
		textWidth.dispose();
		textHeader.dispose();
	}
	
	

}
