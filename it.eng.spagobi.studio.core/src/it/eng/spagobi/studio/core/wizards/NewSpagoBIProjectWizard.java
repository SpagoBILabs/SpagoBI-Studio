package it.eng.spagobi.studio.core.wizards;

import it.eng.spagobi.studio.core.builder.SpagoBIStudioNature;
import it.eng.spagobi.studio.core.util.ImageDescriptorGatherer;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.omg.CORBA.PRIVATE_MEMBER;

public class NewSpagoBIProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	private WizardNewProjectCreationPage creationPage;
	private IConfigurationElement configElement = null;


	
	
	public NewSpagoBIProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	public boolean performFinish() {
		try {
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor monitor) {
					IProject projRef = createProject(monitor != null ? monitor
							: new NullProgressMonitor());
					buildProjectStructure(monitor != null ? monitor
							: new NullProgressMonitor(), projRef);
				}
			};
			getContainer().run(false, true, op);


			
			BasicNewProjectResourceWizard.updatePerspective(configElement);

		} catch (InvocationTargetException x) {
			reportError(x);
			return false;
		} catch (InterruptedException x) {
			reportError(x);
			return false;
		}
		return true; 
	}
	
	
	/** create and open the project
	 * 
	 * @param monitor
	 * @return a reference to the project
	 */

	protected IProject createProject(IProgressMonitor monitor) {
		monitor.beginTask("Creating Project",50);
		IProject project = null;
		try {

			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			monitor.subTask("Creating Project Directories ");
			project = root.getProject(creationPage.getProjectName());
			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
			if(!Platform.getLocation().equals(creationPage.getLocationPath()))
				description.setLocation(creationPage.getLocationPath());

			// set the nature 
			String[] natures = new String[1];
			natures[0] = SpagoBIStudioNature.NATURE_ID;
			description.setNatureIds(natures);
			// create project 
			project.create(description,monitor);
			monitor.worked(10);
			project.open(monitor);
			monitor.worked(10);
		} catch(CoreException x) {
			reportError(x);
		} finally {
			monitor.done();
		}
		return project;
	}


	/**
	 *  Function that creates folder structure
	 * @param monitor
	 * @param projectReference
	 */

	protected void buildProjectStructure(IProgressMonitor monitor, IProject projectReference ) {
		monitor.beginTask("Creating Project",50);
		try{
			monitor.subTask("Creating Project Directories ");

			// create SpagoBI project structure
			IFolder resourceFolder = projectReference.getFolder(SpagoBIStudioConstants.FOLDER_RESOURCE);
			resourceFolder.create(false, true, monitor);

			IFolder serverFolder = resourceFolder.getFolder(SpagoBIStudioConstants.FOLDER_SERVER);
			serverFolder.create(false, true, monitor);

			IFolder datasourceFolder = resourceFolder.getFolder(SpagoBIStudioConstants.FOLDER_DATA_SOURCE);
			datasourceFolder.create(false, true, monitor);

			IFolder metadataFolder = projectReference.getFolder(SpagoBIStudioConstants.FOLDER_METADATA_MODEL);
			metadataFolder.create(false, true, monitor);
			
			IFolder datasetFolder = metadataFolder.getFolder(SpagoBIStudioConstants.FOLDER_DATASET);
			datasetFolder.create(false, true, monitor);

			IFolder analysisFolder = projectReference.getFolder(SpagoBIStudioConstants.FOLDER_ANALYSIS);
			analysisFolder.create(false, true, monitor);

			IFolder privateFolder = projectReference.getFolder(SpagoBIStudioConstants.FOLDER_PRIVATE_DOCUMENTS);
			privateFolder.create(false, true, monitor);
			
		} catch(CoreException x) {
			reportError(x);
		} finally {
			monitor.done();
		}

	}





	public final void init(final IWorkbench workbench, final IStructuredSelection selectionParam) {
		setNeedsProgressMonitor(true);
	}

	private void reportError(Exception x) {
		ErrorDialog.openError(getShell(), "Error", "Error in Creating New Project", makeStatus(x));
	}


	public static IStatus makeStatus(Exception x){
		return new Status(IStatus.ERROR, "", IStatus.ERROR, x.getMessage(), null);
	}


	public void setInitializationData(IConfigurationElement confEl, String arg1, Object arg2) throws CoreException {
		configElement = confEl;
	}

	public final void addPages() {
		try{
			super.addPages();
			creationPage = new WizardNewProjectCreationPage("New SpagoBI Project Page");
			creationPage.setTitle("New SpagoBI Project");
			addPage(creationPage);
		} catch(Exception x) {
			reportError(x);
		}
	}

}
