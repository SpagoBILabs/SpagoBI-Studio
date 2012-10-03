/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.views.actionProvider;



import it.eng.spagobi.meta.editor.multi.wizards.SpagoBIModelEditorWizard;
import it.eng.spagobi.meta.editor.popup.actions.CreateJPAMappingProjectExplorerAction;
import it.eng.spagobi.meta.editor.popup.actions.CreateQueryProjectExplorerAction;
import it.eng.spagobi.studio.birt.wizards.SpagoBINewBirtReportWizard;
import it.eng.spagobi.studio.chart.wizards.SpagoBINewChartWizard;
import it.eng.spagobi.studio.console.wizard.SpagoBINewConsoleTemplateWizard;
import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.core.services.datamartTemplate.UploadDatamartTemplateService;
import it.eng.spagobi.studio.core.services.dataset.DeployDatasetService;
import it.eng.spagobi.studio.core.services.modelTemplate.RefreshModelService;
import it.eng.spagobi.studio.core.services.modelTemplate.RestoreModelService;
import it.eng.spagobi.studio.core.services.resources.ResourcesHandler;
import it.eng.spagobi.studio.core.services.template.DeployTemplateService;
import it.eng.spagobi.studio.core.services.template.RefreshTemplateService;
import it.eng.spagobi.studio.core.util.SWTComponentUtilities;
import it.eng.spagobi.studio.core.util.Utilities;
import it.eng.spagobi.studio.core.views.ResourceNavigator;
import it.eng.spagobi.studio.core.views.menu.SubmenuAction;
import it.eng.spagobi.studio.core.wizards.NewSpagoBIProjectWizard;
import it.eng.spagobi.studio.core.wizards.SpagoBIDeployDatasetWizard;
import it.eng.spagobi.studio.core.wizards.deployWizard.SpagoBIDeployWizard;
import it.eng.spagobi.studio.core.wizards.downloadModelWizard.SpagoBIDownloadModelWizard;
import it.eng.spagobi.studio.core.wizards.downloadWizard.SpagoBIDownloadWizard;
import it.eng.spagobi.studio.core.wizards.serverWizard.NewServerWizard;
import it.eng.spagobi.studio.dashboard.wizards.SpagoBINewDashboardWizard;
import it.eng.spagobi.studio.documentcomposition.wizards.SpagoBIDocumentCompositionWizard;
import it.eng.spagobi.studio.extchart.wizard.SpagoBINewExtChartWizard;
import it.eng.spagobi.studio.geo.wizards.SpagoBIGEOWizard;
import it.eng.spagobi.studio.highchart.wizard.SpagoBINewHighChartWizard;
import it.eng.spagobi.studio.jasper.wizards.SpagoBINewJasperReportWizard;
import it.eng.spagobi.studio.utils.util.ImageDescriptorGatherer;
import it.eng.spagobi.studio.utils.util.ResourceNavigatorHandler;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create the Edit actions (Cut/Copy/Paste) 
 * and register then globally in the workbench using EditActionProvider.
 * <p/>
 * Then, removes the Copy/Paste contributions in the pop-up menu.
 */
public class ResourceNavigatorActionProvider extends CommonActionProvider {

	public ActionContext currentContext = null;


	private static Logger logger = LoggerFactory.getLogger(ResourceNavigatorActionProvider.class);


	/**
	 *  Fill the context menu at folder selection, depending on the nature of the object selected
	 */
	
	Properties properties = null;



	public void fillContextMenu(IMenuManager menu) { 
		logger.debug("IN");
		super.fillContextMenu(menu);


		IStructuredSelection sel=(IStructuredSelection)currentContext.getSelection();

		Object objSel = null; 

		//setSpagoBIProjectWizard(menu);

		// actions to be done on one single selection
		if(sel.toList()!= null && sel.toList().size()<=1){
			objSel = sel.toList().get(0);

			String currentState = ResourceNavigatorHandler.getStateOfSelectedObject(objSel);

			currentState = currentState != null ? currentState : "";

			// if it is a folder of analysis hierarchy
			if(currentState.equalsIgnoreCase(ResourceNavigatorHandler.FOLDER_ANALYSIS_HIER)){
				logger.debug("Folder Analysis");
				// only if studio version
				if(Utilities.readBooleanProperty(properties, SpagoBIStudioConstants.CONFIG_PROPERTY_ANALYSIS_DOCUMENT)){
					setDownloadWizard(menu);
					setNewDocumentWizard(menu);	
				}
			}
			else if (currentState.equalsIgnoreCase(ResourceNavigatorHandler.FILE_ANALYSIS_HIER)){ // if it is a file of analysis hierarchy
				logger.debug("File ANalysis");		
				if(Utilities.readBooleanProperty(properties, SpagoBIStudioConstants.CONFIG_PROPERTY_ANALYSIS_DOCUMENT)){
					setDeployWizard(menu);			
					setRefreshWizard(menu);			
				}
			}
			else if (currentState.equalsIgnoreCase(ResourceNavigatorHandler.FOLDER_SERVER_HIER)){ // if it is a file of analysis hierarchy
				logger.debug("Folder Server");

				setServerWizard(menu);			
			}
			else if (currentState.equalsIgnoreCase(ResourceNavigatorHandler.FOLDER_MODEL_HIER)){ // if it is a file of analysis hierarchy
				logger.debug("Folder Model");

				setModelWizard(menu);		
				setDownloadModelWizard(menu);
			}
			else if (currentState.equalsIgnoreCase(ResourceNavigatorHandler.FILE_MODEL_HIER)){ // if it is a file of analysis hierarchy
				logger.debug("File under model hierarchy");
				setQueryWizard(menu);	
				setJpaNavigator(menu);
				setUploadDatamartTemplateWizard(menu);
				setRefreshModelWizard(menu);
			}
			else if (currentState.equalsIgnoreCase(ResourceNavigatorHandler.FILE_BCK_MODEL_HIER)){ // if it is a file of backup model
				logger.debug("File under backup model hierarchy");
				setRestoreModel(menu);	
			}
			else if (currentState.equalsIgnoreCase(ResourceNavigatorHandler.FILE_METAQUERY_HIER)){ // if it is a file of analysis hierarchy
				logger.debug("File under model hierarchy");
				setDeployDatasetWizard(menu);	
			}
		}


		List<IStructuredSelection> selList = sel.toList();

		if (!ResourceNavigatorHandler.isSelectedObjSystemFolder(selList)) // model has it's own delete		
		{ // if area files of analysis hierarchy
			logger.debug("Folder not system");
			setDeleteResourceWizard(menu);	
		}

		IContributionItem[] contributionItems = menu.getItems()	;
		for (int j = 0; j < contributionItems.length; j++) {
			IContributionItem conItem = contributionItems[j];
			//System.out.println(conItem.toString());
			//		menu.remove(conItem);
		}

	}

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);
		properties = new Utilities().getStudioMetaProperties();	
	}

	@Override
	public void setContext(ActionContext _context) {
		// TODO Auto-generated method stub
		super.setContext(_context);
		currentContext = _context;
	}


	/**
	 *  // draw document actions
	 * @param menu
	 */

	public void setNewDocumentWizard(IMenuManager menu){

		IAction[] actions = new IAction[2];
		ActionContributionItem birtACI = new ActionContributionItem(new Action()
		{	public void run() {
			SpagoBINewBirtReportWizard sbindw = new SpagoBINewBirtReportWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Birt Wizard");
		}
		});
		birtACI.getAction().setText("Report with Birt");
		birtACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_BIRT, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", birtACI);
		actions[0] = birtACI.getAction();
		
		//Jasper
		ActionContributionItem jasperACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Jasper");
			SpagoBINewJasperReportWizard sbindw = new SpagoBINewJasperReportWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Jasper Wizard");
		}
		});

		jasperACI.getAction().setText("Report with Jasper");
		jasperACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_JASPER, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", jasperACI);	
		actions[1] = jasperACI.getAction();
		
		// Report group
		SubmenuAction submenuReport = new SubmenuAction(actions, "Report", "Report", null, true); 
		menu.appendToGroup("group.new", submenuReport);
		

		actions = new IAction[4];
		
		//Chart
		ActionContributionItem chartACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Chart");
			SpagoBINewChartWizard sbindw = new SpagoBINewChartWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Chart Wizard");
		}
		});
		chartACI.getAction().setText("Chart with JfreeChart");
		chartACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_CHART, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", chartACI);	
		actions[0] = chartACI.getAction();
		
		//high Chart
		ActionContributionItem highChartACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New HighChart");
			SpagoBINewHighChartWizard sbindw = new SpagoBINewHighChartWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New HighChart Wizard");
		}
		});
		highChartACI.getAction().setText("Chart with HighChart");
		highChartACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_CHART, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", highChartACI);	
		actions[1] = highChartACI.getAction();
		
		//ext Chart
		ActionContributionItem extChartACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New ExtChart");
			SpagoBINewExtChartWizard sbindw = new SpagoBINewExtChartWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New ExtChart Wizard");
		}
		});
		extChartACI.getAction().setText("Chart with ExtChart");
		extChartACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_CHART, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", highChartACI);	
		actions[2] = extChartACI.getAction();
		
		SubmenuAction submenuChart = new SubmenuAction(actions, "Chart", "Chart", null, true); 
		menu.appendToGroup("group.new", submenuChart);

		actions = new IAction[1];
		//Dashboard
		ActionContributionItem dashACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Dashboard");
			SpagoBINewDashboardWizard sbindw = new SpagoBINewDashboardWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Dashboard Wizard");
		}
		});
		dashACI.getAction().setText("Dashboard basic component");
		dashACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DASHBOARD, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", dashACI);	
		actions[0] = dashACI.getAction();
		SubmenuAction submenuDash = new SubmenuAction(actions, "RT Dashboard", "RT Dashboard", null, true); 
		menu.appendToGroup("group.new", submenuDash);
		
		

		actions = new IAction[1];
		//Dc
		ActionContributionItem dcACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Document Composition");
			SpagoBIDocumentCompositionWizard sbindw = new SpagoBIDocumentCompositionWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Document Composition Wizard");
		}
		});
		dcACI.getAction().setText("Composed document");
		dcACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DOC_COMP, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", dcACI);	
		actions[0] = dcACI.getAction();
		SubmenuAction dcDash = new SubmenuAction(actions, "CockPit", "CockPit", null, true); 
		menu.appendToGroup("group.new", dcDash);
		
		actions = new IAction[1];

		//Console Template
		ActionContributionItem consoleTemplateACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Console Template");
			SpagoBINewConsoleTemplateWizard sbindw = new SpagoBINewConsoleTemplateWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Console Template Wizard");
		}
		});
		consoleTemplateACI.getAction().setText("Console Template");
		consoleTemplateACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_CHART, Activator.PLUGIN_ID));
		actions[0] = consoleTemplateACI.getAction();
		
		
		SubmenuAction consoleGroup = new SubmenuAction(actions, "Console", "Console", null, true); 
		menu.appendToGroup("group.new", consoleGroup);
		
		actions = new IAction[1];
		//Geo
		ActionContributionItem geoACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Geo");
			SpagoBIGEOWizard sbindw = new SpagoBIGEOWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Geo Wizard");
		}
		});
		geoACI.getAction().setText("GEO");
		geoACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_GEO, Activator.PLUGIN_ID));
		//menu.appendToGroup("group.new", geoACI);	
		actions[0] = geoACI.getAction();
		SubmenuAction geoDash = new SubmenuAction(actions, "Location Intelligence", "Location Intelligence", null, true); 
		menu.appendToGroup("group.new", geoDash);
	}


	public void setDeployWizard(IMenuManager menu){
		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Deploy");
			SpagoBIDeployWizard sbindw = new SpagoBIDeployWizard();	
			DeployTemplateService dts = new DeployTemplateService(currentContext.getSelection(), sbindw); 

			boolean isAutomatic = dts.doAutomaticDeploy();
			//			if(!isAutomatic){
			//				sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "Deploy document");
			//			}
		}
		});
		downACI.getAction().setText("Deploy");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DEPLOY, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}



	public void setRefreshWizard(IMenuManager menu){

		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("Refresh template");
			RefreshTemplateService rts = new RefreshTemplateService(currentContext.getSelection());
			rts.refreshTemplate();
		}
		});
		downACI.getAction().setText("Refresh Template");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_REFRESH, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}


	public void setServerWizard(IMenuManager menu){

		ActionContributionItem serverACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Server");

			NewServerWizard sbindw = new NewServerWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "New Server");
		}
		});
		serverACI.getAction().setText("New Server");
		serverACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_SERVER, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", serverACI);
	}

	public void setModelWizard(IMenuManager menu){

		ActionContributionItem modelACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Model");
			SpagoBIModelEditorWizard sbindw = new SpagoBIModelEditorWizard();	
			// call the whole cycle to not modify the meta wizard
			IStructuredSelection sel=(IStructuredSelection)currentContext.getSelection();
			IFolder folderSelected = null;
			Object objSel = sel.getFirstElement();
			// selection is limited to folder
			folderSelected = (IFolder)objSel;
			IPath pathSelected = folderSelected.getFullPath();
			sbindw.init(PlatformUI.getWorkbench(), sel);
			sbindw.setContainerFullPath(pathSelected);
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
			// Open the wizard dialog
			dialog.open();	
		}
		});
		modelACI.getAction().setText("New model");
		modelACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_MODEL, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", modelACI);
	}

	public void setDownloadModelWizard(IMenuManager menu){

		ActionContributionItem modelACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("Download Model");
			SpagoBIDownloadModelWizard sbindw = new SpagoBIDownloadModelWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "Download model");
		}
		});
		modelACI.getAction().setText("Download Models");
		modelACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DOWNLOAD, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", modelACI);
	}






	public void setDownloadWizard(IMenuManager menu){

		menu.add(new Separator());


		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			SpagoBIDownloadWizard sbindw = new SpagoBIDownloadWizard();	
			sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "Download document");
		}
		});
		downACI.getAction().setText("Download");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DOWNLOAD, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}


	public void setQueryWizard(IMenuManager menu){
		menu.add(new Separator());


		ActionContributionItem queryACI = new ActionContributionItem(new Action()
		{	public void run() {
			CreateQueryProjectExplorerAction action = new CreateQueryProjectExplorerAction();
			action.setActivePart(this,PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart() );
			action.setSelection((IStructuredSelection)currentContext.getSelection());
			action.run(this);
// refresh navigator
			SWTComponentUtilities.getNavigatorReference(ResourceNavigator.VIEW_ID);

		}
		});
		queryACI.getAction().setText("New Query");
		queryACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_QUERY, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", queryACI);
	}

	public void setJpaNavigator(IMenuManager menu){
		menu.add(new Separator());
		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			CreateJPAMappingProjectExplorerAction action = new CreateJPAMappingProjectExplorerAction();
			action.setActivePart(this,PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart() );
			action.run(this);
		}
		});
		downACI.getAction().setText("Jpa Mapping");
		//		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DOWNLOAD, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}

	public void setDeployDatasetWizard(IMenuManager menu){

		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Deploy Dataset");
			SpagoBIDeployDatasetWizard sbindw = new SpagoBIDeployDatasetWizard();	
			DeployDatasetService dts = new DeployDatasetService(currentContext.getSelection(), sbindw); 

			dts.tryAutomaticDeploy();
			//			if(!isAutomatic){
			//				sbindw.launchWizard((IStructuredSelection)currentContext.getSelection(), "Deploy dataset");
			//			}
		}
		});
		downACI.getAction().setText("Deploy Dataset");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DEPLOY, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}

	public void setUploadDatamartTemplateWizard(IMenuManager menu){

		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("New Upload datamart");
			UploadDatamartTemplateService dts = new UploadDatamartTemplateService(currentContext.getSelection()); 
			dts.datamartUpload();
		}
		});
		downACI.getAction().setText("Upload Datamart and Model on server");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DEPLOY, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}


	public void setRefreshModelWizard(IMenuManager menu){

		ActionContributionItem downACI = new ActionContributionItem(new Action(){	
			public void run() {
				logger.debug("Refresh Model template");
				RefreshModelService dts = new RefreshModelService(currentContext.getSelection()); 
				dts.refreshModelTemplate();
			}
		});
		downACI.getAction().setText("Refresh Model template from server");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_REFRESH, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}

	public void setDeleteResourceWizard(IMenuManager menu){

		ActionContributionItem delACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("Delete action");
			ResourcesHandler dts = new ResourcesHandler(); 
			dts.deleteResources(currentContext.getSelection());
		}
		});
		delACI.getAction().setText("Delete");
		delACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DELETE, Activator.PLUGIN_ID));
		menu.appendToGroup("group.edit", delACI);
	}



	public void setSpagoBIProjectWizard(IMenuManager menu){

		ActionContributionItem delACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("Spagobi Project");
			NewSpagoBIProjectWizard sbindw = new NewSpagoBIProjectWizard(); 
			sbindw.init(PlatformUI.getWorkbench(), (IStructuredSelection)currentContext.getSelection());
			// Create the wizard dialog
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),sbindw);
			// Open the wizard dialog
			dialog.open();


		}
		});
		delACI.getAction().setText("SpagoBI Project");
		delACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_SPAGOBI, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", delACI);
	}


	public void setRestoreModel(IMenuManager menu){

		ActionContributionItem downACI = new ActionContributionItem(new Action()
		{	public void run() {
			logger.debug("Restore Model template");
			RestoreModelService dts = new RestoreModelService(currentContext.getSelection()); 
			dts.RestoreModelTemplate();
		}
		});
		downACI.getAction().setText("Restore model file");
		downACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.FOLDER_ICON_ANALYSIS, Activator.PLUGIN_ID));
		menu.appendToGroup("group.new", downACI);
	}





}
