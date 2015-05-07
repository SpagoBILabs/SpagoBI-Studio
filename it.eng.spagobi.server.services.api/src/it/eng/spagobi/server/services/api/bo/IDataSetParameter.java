package it.eng.spagobi.server.services.api.bo;

public interface IDataSetParameter {

	public abstract java.lang.String getName();

	public abstract void setName(java.lang.String name);

	public abstract java.lang.String getType();

	public abstract void setType(java.lang.String type);

	public abstract java.lang.String[] getValues();

	public abstract void setValues(java.lang.String[] values);

}