package it.eng.spagobi.studio.core.views.menu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class SubmenuAction extends Action implements SelectionListener

{

	// / Who to inform when this action is fired (meaning display the submenu)

	private SelectionListener actionInstance;



	// the list of actions that are contained within this action

	private IAction[] actions;



	// should we hide the disabled ones (if not, they will appear as grayed out)

	private boolean hideDisabled;

	/***

  33:       * Create a submenu.

  34:       * 

  35:       * @param subactions

  36:       *            the actions that are contained within

  37:       * @param text

  38:       *            the container's textual label

  39:       * @param toolTip

  40:       *            the container's tooltip

  41:       * @param descriptor

  42:       *            the container's image descriptor

  43:       * @param hideDisabledActions

  44:       *            should we hide the disabled ones (if not, they will appear as

  45:       *            grayed out)

  46:       */

	public SubmenuAction(IAction[] subactions, String text, String toolTip, ImageDescriptor descriptor, boolean hideDisabledActions)

	{

		// indicate that this is a secondary fly-out menu.

		super("", IAction.AS_DROP_DOWN_MENU);



		this.actionInstance = this;

		this.actions = subactions;

		this.hideDisabled = hideDisabledActions;



		setText(text);
		setToolTipText(toolTip);
		setImageDescriptor(descriptor);



		// the secondayr menu logic

		setMenuCreator(new IMenuCreator()
		{
			public Menu getMenu(Control parent)
			{
				// this would be used outside of a menu. not useful for us.
				return null;
			}

			public Menu getMenu(Menu parent)
			{
				// create a submenu
				Menu menu = new Menu(parent);
				// fill it with our actions
				for (int i = 0; i < actions.length; i++)
				{
					// skip the disabled ones if necessary (or null actions)
					if (actions[i] == null || !actions[i].isEnabled() && hideDisabled)
						continue;
					

					// create the submenu item
					MenuItem item = new MenuItem(menu, SWT.NONE);

					// memorize the index
					item.setData(new Integer(i));
					// identify it
					item.setText(actions[i].getText());

					// create its image

					if (actions[i].getImageDescriptor() != null)
						item.setImage(actions[i].getImageDescriptor().createImage());


					// inform us when something is selected.
					item.addSelectionListener(actionInstance);
				}
				return menu;
			}

			public void dispose()
			{
			}

		});

	}

	/**

	 * Returns how many items are enabled in the flyout. Useful to hide the

	 * submenu when none are enabled.

	 * 
	 * @return the number of currently enabled menu items.
	 */

	public int getActiveOperationCount()

	{
		int operationCount = 0;

		for (int i = 0; i < actions.length; i++)
			operationCount += actions[i] != null && actions[i].isEnabled() ? 1 : 0;

		return operationCount;
	}

	/**
	 * Runs the default action
	 */
	public void run()
	{
		actions[0].run();
	}


	/**
	 * Runs the default action

	 */

	public void widgetDefaultSelected(SelectionEvent e)

	{

		actions[0].run();

	}



	/**

	 * Called when an item in the drop-down menu is selected. Runs the

	 * associated run() method

	 */

	public void widgetSelected(SelectionEvent e)

	{

		// get the index from the data and run that action.
		actions[((Integer) (((MenuItem) (e.getSource())).getData())).intValue()].run();

	}

}
