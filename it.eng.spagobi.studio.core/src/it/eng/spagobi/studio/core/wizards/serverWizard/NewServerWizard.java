package it.eng.spagobi.studio.core.wizards.serverWizard;

import it.eng.spagobi.studio.core.actions.NewServerAction;
import it.eng.spagobi.studio.core.util.SWTComponentUtilities;
import it.eng.spagobi.studio.utils.bo.Server;
import it.eng.spagobi.studio.utils.bo.xmlMapping.XmlServerGenerator;
import it.eng.spagobi.studio.utils.services.server.ServerHandler;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;
import it.eng.spagobi.studio.utils.wizard.AbstractSpagoBIDocumentWizard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewServerWizard extends AbstractSpagoBIDocumentWizard {
	private NewServerWizardPage page;
	private IStructuredSelection selection;
	protected IWorkbench workbench;
	private static Logger logger = LoggerFactory.getLogger(NewServerAction.class);

	/**
	 *  vector that stores user messages to show at the end of download
	 */
	Vector<String> messages = new Vector<String>();

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewServerWizard() {
		super();
		setNeedsProgressMonitor(false);
	}


	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewServerWizardPage(selection);
		addPage(page);
	}



	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. Create the server template file
	 */
	public boolean performFinish() {
		logger.debug("IN");
		boolean toreturn = true;
		IPath pathNewFile = null;

		TreeItem[] selectedItems=null;
		ByteArrayInputStream bais = null;
		try{
			String name = page.getTextName().getText();
			String user = page.getTextUser().getText();
			String url = page.getTextUrl().getText();
			String pwd = page.getTextPwd().getText();
			boolean active = page.getCheckActive().getSelection();


			Server server = new Server( name, url, user, pwd, active);


			XmlServerGenerator xmlgen = new XmlServerGenerator();
			String xmlString = xmlgen.transformToXml(server);

			Object objSel = selection.toList().get(0);
			// selection is surely a folder
			Folder folderSel = (Folder)objSel;  
			String projectName = folderSel.getProject().getName();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = root.getProject(projectName);
			IPath pathFolder = folderSel.getProjectRelativePath(); 
			pathNewFile = pathFolder.append(name+"."+SpagoBIStudioConstants.SERVER_EXTENSION); 
			IFile newFile = project.getFile(pathNewFile);
			logger.debug("path of new file is: " + pathNewFile);

			// check if already existing
			if(newFile.exists()){
				logger.warn("File "+pathNewFile + " already exists ins erver folder, define a different name");
				MessageDialog.openWarning(page.getShell(), "Warning", "File "+pathNewFile + " already exists, write  a different name");
				toreturn = false;
			}
			else{
				byte[] bytes = xmlString.getBytes();
				bais = new ByteArrayInputStream(bytes);
				newFile.create(bais, true, null);
			}
		
			if(active){
				logger.debug("deactivate all other servers");
				new ServerHandler(server).deactivateOtherServers(newFile);
				SWTComponentUtilities.getNavigatorReference(SpagoBIStudioConstants.RESOURCE_NAVIGATOR_ID);
			}
		
		}
		catch (Exception e) {
			logger.error("Error in writing file with path "+pathNewFile);
			toreturn = false;
		}
		finally{
			try {
				if(bais!=null)
					bais.close();
			} catch (IOException e) {
				logger.warn("some errors in closing the byte array");
			}
		}
		logger.debug("OUT");
		return toreturn;
	}



	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench _workbench, IStructuredSelection _selection) {
		this.selection = _selection;
		this.workbench=_workbench;
	}



}


