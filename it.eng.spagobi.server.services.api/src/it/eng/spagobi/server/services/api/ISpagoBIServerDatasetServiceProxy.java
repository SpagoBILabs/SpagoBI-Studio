package it.eng.spagobi.server.services.api;



import it.eng.spagobi.server.services.api.bo.IDataSet;
import it.eng.spagobi.server.services.api.bo.IDataSetParameter;
import it.eng.spagobi.server.services.api.bo.IDataStoreMetadata;
import it.eng.spagobi.server.services.api.bo.IDocument;
import it.eng.spagobi.server.services.api.bo.ITemplate;
import it.eng.spagobi.server.services.api.exception.MissingParValueException;
import it.eng.spagobi.server.services.api.exception.NoServerException;

import java.rmi.RemoteException;
import java.util.Vector;

public interface ISpagoBIServerDatasetServiceProxy {

	public abstract Integer saveNewDocument(IDocument newDocument,
			ITemplate template, Integer functionalityId) throws RemoteException;

	public abstract void uploadTemplate(Integer id, ITemplate template)
			throws RemoteException;

	public abstract IDataSet[] getDataSetList() throws RemoteException;

	public abstract IDataSet getDataSet(Integer id) throws RemoteException;

	public abstract Integer saveDataSet(IDataSet newDataset)
			throws RemoteException;

	public abstract String executeDataSet(String dataSetLabel,
			IDataSetParameter[] parameters) throws RemoteException;

	public abstract IDataStoreMetadata getDataStoreMetadata(Integer datasetId)
			throws NoServerException, MissingParValueException;

	public abstract Vector<IDataSet> getAllDatasets() throws NoServerException;
}