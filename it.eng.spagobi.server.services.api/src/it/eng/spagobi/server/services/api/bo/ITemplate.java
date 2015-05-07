package it.eng.spagobi.server.services.api.bo;

public interface ITemplate {

	public abstract javax.activation.DataHandler getContent();

	public abstract void setContent(javax.activation.DataHandler content);

	public abstract java.lang.String getFolderName();

	public abstract void setFolderName(String folderName);

	public abstract java.lang.String getFileName();

	public abstract void setFileName(String fileName);

}