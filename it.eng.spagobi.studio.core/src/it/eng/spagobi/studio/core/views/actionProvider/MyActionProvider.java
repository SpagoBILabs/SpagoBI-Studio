package it.eng.spagobi.studio.core.views.actionProvider;



import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.internal.navigator.resources.actions.EditActionProvider;

/**
 * Create the Edit actions (Cut/Copy/Paste) 
 * and register then globally in the workbench using EditActionProvider.
 * <p/>
 * Then, removes the Copy/Paste contributions in the pop-up menu.
 */
public class MyActionProvider extends EditActionProvider {
	public void fillContextMenu(IMenuManager menu) { super.fillContextMenu(menu);
	// remove Copy/Paste contributions
	//IContributionItem copyItemRemoved = menu.remove("org.eclipse.ui.CopyAction");
	//IContributionItem pasteItemRemoved = menu.remove("org.eclipse.ui.PasteAction");
	IContributionItem[] contributionItems = menu.getItems();
	for (int j = 0; j < contributionItems.length; j++) {
		IContributionItem conItem = contributionItems[j];
		System.out.println(conItem.toString());
//		menu.remove(conItem);
	}

	IWorkbenchActivitySupport actSupp = PlatformUI.getWorkbench().getActivitySupport();

	Set set = actSupp.getActivityManager().getEnabledActivityIds();
	for (Iterator iterator = set.iterator(); iterator.hasNext();) {
		String type = (String) iterator.next();
		//System.out.println(type);
		//actSupp.getActivityManager().getActivity("").
	}
	
	
	
//	IWorkbenchPage page = 	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//	MenuManager mbManager = ((ApplicationWindow)page.getWorkbenchWindow()).getMenuBarManager();
	System.out.println("ECCO I MENU");
	for (int i =0; i<menu.getItems().length; i++){
		IContributionItem item=menu.getItems()[i];

		System.out.println(item.getId());

		if (item.getId().equals("refactorMenuId")
		||	item.getId().equals("navigate")	
		||	item.getId().equals("additions")
		||	item.getId().equals("org.eclipse.ui.run")
		||	item.getId().equals("group.new")
				||	item.getId().equals("group.goto")
		||	item.getId().equals("group.open")
		||	item.getId().equals("group.edit")
				||	item.getId().equals("org.eclipse.ui.DeleteResourceAction")
				||	item.getId().equals("group.properties")
				
				
		){
		//	item.setVisible(false);
		}
		System.out.println("------------------------");
	}
	}
}
