package it.eng.spagobi.server.services.api.bo;

public interface IDocument {

	public abstract Integer getDataSetId();

	public abstract void setDataSetId(Integer dataSetId);

	public abstract Integer getDataSourceId();

	public abstract void setDataSourceId(Integer dataSourceId);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract Integer getEngineId();

	public abstract void setEngineId(Integer engineId);

	public abstract Integer getId();

	public abstract void setId(Integer id);

	public abstract String getLabel();

	public abstract void setLabel(String label);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getState();

	public abstract void setState(String state);

	public abstract String getType();

	public abstract void setType(String type);

}