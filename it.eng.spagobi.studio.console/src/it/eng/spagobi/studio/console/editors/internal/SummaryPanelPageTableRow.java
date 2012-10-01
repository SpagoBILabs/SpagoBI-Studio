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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * @author Marco Cortella (marco.cortella@eng.it)
 *
 */
public class SummaryPanelPageTableRow {
	
	//this represent the single item in the row
	private TableItem tableItem;
	private Text textTitle;
	private CCombo comboDataset;
	private Text textWidth;
	private Text textHeight;
	private CCombo comboWidgetType;
	private Button defineWidgetButton;

	
	public SummaryPanelPageTableRow(TableItem tableItem,Text textTitle,CCombo comboDataset,Text textWidth,Text textHeight,CCombo comboWidgetType,Button defineWidgetButton  ){
		this.tableItem = tableItem;
		this.textTitle = textTitle;
		this.comboDataset = comboDataset;
		this.textWidth = textWidth;
		this.textHeight = textHeight;
		this.comboWidgetType = comboWidgetType;
		this.defineWidgetButton = defineWidgetButton;
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
	 * @return the textTitle
	 */
	public Text getTextTitle() {
		return textTitle;
	}



	/**
	 * @param textTitle the textTitle to set
	 */
	public void setTextTitle(Text textTitle) {
		this.textTitle = textTitle;
	}



	/**
	 * @return the comboDataset
	 */
	public CCombo getComboDataset() {
		return comboDataset;
	}



	/**
	 * @param comboDataset the comboDataset to set
	 */
	public void setComboDataset(CCombo comboDataset) {
		this.comboDataset = comboDataset;
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
	 * @return the textHeight
	 */
	public Text getTextHeight() {
		return textHeight;
	}



	/**
	 * @param textHeight the textHeight to set
	 */
	public void setTextHeight(Text textHeight) {
		this.textHeight = textHeight;
	}



	/**
	 * @return the comboWidgetType
	 */
	public CCombo getComboWidgetType() {
		return comboWidgetType;
	}



	/**
	 * @param comboWidgetType the comboWidgetType to set
	 */
	public void setComboWidgetType(CCombo comboWidgetType) {
		this.comboWidgetType = comboWidgetType;
	}



	/**
	 * @return the defineWidgetButton
	 */
	public Button getDefineWidgetButton() {
		return defineWidgetButton;
	}



	/**
	 * @param defineWidgetButton the defineWidgetButton to set
	 */
	public void setDefineWidgetButton(Button defineWidgetButton) {
		this.defineWidgetButton = defineWidgetButton;
	}



	public void disposeRowElements() {
		tableItem.dispose();
		textTitle.dispose();
		comboDataset.dispose();
		textWidth.dispose();
		textHeight.dispose();
		comboWidgetType.dispose();
		defineWidgetButton.dispose();
	}
	
	

}
