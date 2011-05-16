package it.eng.spagobi.studio.core.actions;

import it.eng.spagobi.studio.birt.services.WizardLauncher;
import it.eng.spagobi.studio.core.Activator;
import it.eng.spagobi.studio.utils.util.ImageDescriptorGatherer;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

public class DocumentsWizardMenuAction implements IWorkbenchWindowPulldownDelegate {

	private Menu actionsMenu;


	public void run(IAction action) {
		int i = 0;		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		int i = 0;		

	}

	public void dispose() {
		//		if (docWizMenu != null) {
		//			dsocWizMenu.dispose();
		//		}
	}

	public void init(IWorkbenchWindow window) {
		Action actionHome =
			new Action(
					"&Home",
					ImageDescriptor.createFromFile(null, "metadata.png")) {
			public void run() {
				System.out.println("HOME");
			}
		};
		//actionHome.setAccelerator(SWT.CTRL + 'H');

		ActionContributionItem item = new ActionContributionItem(actionHome);
		item.setMode(ActionContributionItem.MODE_FORCE_TEXT);

	}

	public Menu getMenu(Control parent) {
		actionsMenu = createActionsMenu(parent, actionsMenu);
		return actionsMenu;
	}



	private static Menu createActionsMenu(Control parent, Menu menu){
		if (menu == null) {
			menu = new Menu(parent);
		
			//BIRT
			ActionContributionItem birtACI = new ActionContributionItem(new Action()
			{	public void run() {
				it.eng.spagobi.studio.birt.services.WizardLauncher.wizardLaunch();
				}
			});
			birtACI.getAction().setText("Birt");
			birtACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_BIRT, Activator.PLUGIN_ID));
			birtACI.fill(menu, -1);

			// Chart
			ActionContributionItem chartACI = new ActionContributionItem(new Action()
			{	public void run() {
					it.eng.spagobi.studio.chart.services.WizardLauncher.wizardLaunch();
				}
			});
			chartACI.getAction().setText("Chart");
			chartACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_CHART, Activator.PLUGIN_ID));
			chartACI.fill(menu, -1);

			// Document Composition
			ActionContributionItem docCompACI = new ActionContributionItem(new Action()
			{	public void run() {
					it.eng.spagobi.studio.documentcomposition.services.WizardLauncher.wizardLaunch();
				}
			});
			docCompACI.getAction().setText("Document Composition");
			docCompACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DOC_COMP, Activator.PLUGIN_ID));
			docCompACI.fill(menu, -1);

			// Jasper 
			ActionContributionItem jasperACI = new ActionContributionItem(new Action()
			{	public void run() {
					it.eng.spagobi.studio.jasper.services.WizardLauncher.wizardLaunch();
				}
			});
			jasperACI.getAction().setText("Jasper report");
			jasperACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_JASPER, Activator.PLUGIN_ID));
			jasperACI.fill(menu, -1);

			// Geo
			ActionContributionItem geoACI = new ActionContributionItem(new Action()
			{	public void run() {
					it.eng.spagobi.studio.geo.services.WizardLauncher.wizardLaunch();
				}
			});
			geoACI.getAction().setText("Geo");
			geoACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_GEO, Activator.PLUGIN_ID));
			geoACI.fill(menu, -1);

			// Dashboard
			ActionContributionItem dashACI = new ActionContributionItem(new Action()
			{	public void run() {
					it.eng.spagobi.studio.dashboard.services.WizardLauncher.wizardLaunch();
				}
			});
			dashACI.getAction().setText("Dashboard");
			dashACI.getAction().setImageDescriptor(ImageDescriptorGatherer.getImageDesc(SpagoBIStudioConstants.ICON_WIZARD_DASHBOARD, Activator.PLUGIN_ID));
			dashACI.fill(menu, -1);

			
		}
		return menu;
	}


	private static Menu createViewsMenu(Control parent, Menu menu) {
		if (menu == null) {
			menu = new Menu(parent);




			IViewRegistry viewsRegistry = PlatformUI.getWorkbench().getViewRegistry();
			// Get all views
			IViewDescriptor[] viewDescriptors = viewsRegistry.getViews();

			// Sort alphabetically by label
			Arrays.sort(viewDescriptors, new Comparator<IViewDescriptor>() {
				public int compare(IViewDescriptor vd1, IViewDescriptor vd2) {
					return vd1.getLabel().compareTo(vd1.getLabel());
				}
			});

			// Configure the menu items for each View
			for (IViewDescriptor viewDescriptor : viewDescriptors) {
				MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
				menuItem.setText(viewDescriptor.getLabel());
				menuItem.setImage(viewDescriptor.getImageDescriptor()
						.createImage());
				menuItem.setData(viewDescriptor.getId());
				// Handle selection
				menuItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						IWorkbench workbench = PlatformUI.getWorkbench();
						try {
							IViewDescriptor viewWithId = workbench.getViewRegistry().find(
									(String) e.widget.getData());
							if (viewWithId != null) {
								IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
								IViewPart view = activePage.showView(viewWithId.getId(), null,
										IWorkbenchPage.VIEW_CREATE);
								activePage.activate(view);
							} else {
								// may be delete this menuItem ?
							}
						} catch (PartInitException pie) {
						}
					}
				});
			}
		} else {
			// Delete children
		}
		return menu;


	}
}
