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
package it.eng.spagobi.studio.highchart.model.bo;



public class YAxis {

	Boolean allowDecimals;
	String alternateGridColor;
	String categories;
	String dateTimeLabelFormats;
	Boolean endOnTick;
	String gridLineColor;
	String gridLineDashStyle;
	Integer gridLineWidth;
	Integer id;
	String labels;
	String lineColor;
	Integer lineWidth;
	String linkedTo;
	Integer max;
	Float maxPadding;
	Integer maxZoom;
	Integer min;
	String minorGridLineColor;
	String minorGridLineDashStyle;
	Integer minorGridLineWidth;
	String minorTickColor;
	String minorTickInterval; //combo
	Integer minorTickLength;
	String minorTickPosition; //combo
	Integer minorTickWidth;
	Float minPadding;
	Integer offset;
	Boolean opposite;
	String plotBands;
	String plotLines;
	Boolean reversed;
	Boolean showFirstLabel;
	Boolean showLastLabel;
	Integer startOfWeek;
	Boolean startOnTick;
	String tickColor;
	Integer tickInterval;
	Integer tickLength;
	String tickmarkPlacement;
	Integer tickPixelInterval;
	String tickPosition;
	Integer tickWidth;
	TitleAxis titleAxis;
	String type;
	String alias;

	public Boolean isAllowDecimals() {
		return allowDecimals;
	}
	public void setAllowDecimals(Boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}
	public String getAlternateGridColor() {
		return alternateGridColor;
	}
	public void setAlternateGridColor(String alternateGridColor) {
		this.alternateGridColor = alternateGridColor;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getDateTimeLabelFormats() {
		return dateTimeLabelFormats;
	}
	public void setDateTimeLabelFormats(String dateTimeLabelFormats) {
		this.dateTimeLabelFormats = dateTimeLabelFormats;
	}
	public Boolean isEndOnTick() {
		return endOnTick;
	}
	public void setEndOnTick(Boolean endOnTick) {
		this.endOnTick = endOnTick;
	}
	public String getGridLineColor() {
		return gridLineColor;
	}
	public void setGridLineColor(String gridLineColor) {
		this.gridLineColor = gridLineColor;
	}
	public String getGridLineDashStyle() {
		return gridLineDashStyle;
	}
	public void setGridLineDashStyle(String gridLineDashStyle) {
		this.gridLineDashStyle = gridLineDashStyle;
	}
	public Integer getGridLineWidth() {
		return gridLineWidth;
	}
	public void setGridLineWidth(Integer gridLineWidth) {
		this.gridLineWidth = gridLineWidth;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public String getLineColor() {
		return lineColor;
	}
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}
	public Integer getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(Integer lineWidth) {
		this.lineWidth = lineWidth;
	}
	public String getLinkedTo() {
		return linkedTo;
	}
	public void setLinkedTo(String linkedTo) {
		this.linkedTo = linkedTo;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Float getMaxPadding() {
		return maxPadding;
	}
	public void setMaxPadding(Float maxPadding) {
		this.maxPadding = maxPadding;
	}
	public Integer getMaxZoom() {
		return maxZoom;
	}
	public void setMaxZoom(Integer maxZoom) {
		this.maxZoom = maxZoom;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public String getMinorGridLineColor() {
		return minorGridLineColor;
	}
	public void setMinorGridLineColor(String minorGridLineColor) {
		this.minorGridLineColor = minorGridLineColor;
	}
	public String getMinorGridLineDashStyle() {
		return minorGridLineDashStyle;
	}
	public void setMinorGridLineDashStyle(String minorGridLineDashStyle) {
		this.minorGridLineDashStyle = minorGridLineDashStyle;
	}
	public Integer getMinorGridLineWidth() {
		return minorGridLineWidth;
	}
	public void setMinorGridLineWidth(Integer minorGridLineWidth) {
		this.minorGridLineWidth = minorGridLineWidth;
	}
	public String getMinorTickColor() {
		return minorTickColor;
	}
	public void setMinorTickColor(String minorTickColor) {
		this.minorTickColor = minorTickColor;
	}
	public String getMinorTickInterval() {
		return minorTickInterval;
	}
	public void setMinorTickInterval(String minorTickInterval) {
		this.minorTickInterval = minorTickInterval;
	}
	public Integer getMinorTickLength() {
		return minorTickLength;
	}
	public void setMinorTickLength(Integer minorTickLength) {
		this.minorTickLength = minorTickLength;
	}
	public String getMinorTickPosition() {
		return minorTickPosition;
	}
	public void setMinorTickPosition(String minorTickPosition) {
		this.minorTickPosition = minorTickPosition;
	}
	public Integer getMinorTickWidth() {
		return minorTickWidth;
	}
	public void setMinorTickWidth(Integer minorTickWidth) {
		this.minorTickWidth = minorTickWidth;
	}
	public Float getMinPadding() {
		return minPadding;
	}
	public void setMinPadding(Float minPadding) {
		this.minPadding = minPadding;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Boolean getOpposite() {
		return opposite;
	}
	public void setOpposite(Boolean opposite) {
		this.opposite = opposite;
	}
	public String getPlotBands() {
		return plotBands;
	}
	public void setPlotBands(String plotBands) {
		this.plotBands = plotBands;
	}
	public String getPlotLines() {
		return plotLines;
	}
	public void setPlotLines(String plotLines) {
		this.plotLines = plotLines;
	}
	public Boolean getReversed() {
		return reversed;
	}
	public void setReversed(Boolean reversed) {
		this.reversed = reversed;
	}
	public Boolean getShowFirstLabel() {
		return showFirstLabel;
	}
	public void setShowFirstLabel(Boolean showFirstLabel) {
		this.showFirstLabel = showFirstLabel;
	}
	public Boolean getShowLastLabel() {
		return showLastLabel;
	}
	public void setShowLastLabel(Boolean showLastLabel) {
		this.showLastLabel = showLastLabel;
	}
	public Integer getStartOfWeek() {
		return startOfWeek;
	}
	public void setStartOfWeek(Integer startOfWeek) {
		this.startOfWeek = startOfWeek;
	}
	public Boolean getStartOnTick() {
		return startOnTick;
	}
	public void setStartOnTick(Boolean startOnTick) {
		this.startOnTick = startOnTick;
	}
	public String getTickColor() {
		return tickColor;
	}
	public void setTickColor(String tickColor) {
		this.tickColor = tickColor;
	}
	public Integer getTickInterval() {
		return tickInterval;
	}
	public void setTickInterval(Integer tickInterval) {
		this.tickInterval = tickInterval;
	}
	public Integer getTickLength() {
		return tickLength;
	}
	public void setTickLength(Integer tickLength) {
		this.tickLength = tickLength;
	}
	public String getTickmarkPlacement() {
		return tickmarkPlacement;
	}
	public void setTickmarkPlacement(String tickmarkPlacement) {
		this.tickmarkPlacement = tickmarkPlacement;
	}
	public Integer getTickPixelInterval() {
		return tickPixelInterval;
	}
	public void setTickPixelInterval(Integer tickPixelInterval) {
		this.tickPixelInterval = tickPixelInterval;
	}
	public String getTickPosition() {
		return tickPosition;
	}
	public void setTickPosition(String tickPosition) {
		this.tickPosition = tickPosition;
	}
	public Integer getTickWidth() {
		return tickWidth;
	}
	public void setTickWidth(Integer tickWidth) {
		this.tickWidth = tickWidth;
	}
	
	public TitleAxis getTitleAxis() {
		if(titleAxis == null ) titleAxis = new TitleAxis();
		return titleAxis;
	}
	public void setTitleAxis(TitleAxis titleAxis) {
		this.titleAxis = titleAxis;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}


}
