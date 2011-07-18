package it.eng.spagobi.studio.highchart.model.bo;

public interface InterfaceType {

	public Boolean isAllowPointSelect();
	
	public void setAllowPointSelect(Boolean allowPointSelect);
	public Boolean isAnimation();
	public void setAnimation(Boolean animation);
	public String getColor();
	public void setColor(String color);
	public String getCursor();
	public void setCursor(String cursor);
	public String getDashStyle();
	public void setDashStyle(String dashStyle);
	public DataLabels getDataLabels();
	public void setDataLabels(DataLabels dataLabels);
	public Boolean isEnableMouseTracking();
	public void setEnableMouseTracking(Boolean enableMouseTracking);
	public Integer getLineWidth();
	public void setLineWidth(Integer lineWidth);
	public String getMarker();
	public void setMarker(String marker);
	public Integer getPointStart();
	public void setPointStart(Integer pointStart);
	public Integer getPointInterval();
	public void setPointInterval(Integer pointInterval);
	public Boolean isSelected();
	public void setSelected(Boolean selected);
	public Boolean isShadow();
	public void setShadow(Boolean shadow);
	public Boolean isShowCheckbox();
	public void setShowCheckbox(Boolean showCheckbox);
	public Boolean isShowInLegend();
	public void setShowInLegend(Boolean showInLegend);
	public String getStacking();
	public void setStacking(String stacking);
	public Boolean isStickyTracking();
	public void setStickyTracking(Boolean stickyTracking);
	public Boolean isVisible();
	public void setVisible(Boolean visible);
	public Integer getzIndex();
	public void setzIndex(Integer zIndex);

	
	
}
