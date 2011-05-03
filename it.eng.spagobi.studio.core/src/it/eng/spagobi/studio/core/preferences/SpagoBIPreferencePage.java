/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.core.preferences;

import it.eng.spagobi.studio.core.Activator;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class SpagoBIPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	private StringFieldEditor serverUrl = null;
	private StringFieldEditor userName = null;
	private StringFieldEditor userPassword = null;
	
	public SpagoBIPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("SpagoBI Server connection parameters");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		serverUrl = new StringFieldEditor(PreferenceConstants.SPAGOBI_SERVER_URL, "SpagoBI Server url:", getFieldEditorParent());
		addField(serverUrl);
		userName = new StringFieldEditor(PreferenceConstants.SPABOGI_USER_NAME, "User name:", getFieldEditorParent());
		addField(userName);
		userPassword = new StringFieldEditor(PreferenceConstants.SPABOGI_USER_PASSWORD, "Password:", getFieldEditorParent());
		userPassword.getTextControl(getFieldEditorParent()).setEchoChar('*');
		addField(userPassword);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	protected void contributeButtons(Composite parent) {
		Button testButton = new Button(parent, SWT.FLAT);
		testButton.setText("Test connection");
		((GridLayout) parent.getLayout()).numColumns++;
		GridData data = new GridData();
		testButton.setLayoutData(data);
		testButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
//				testConnection();
			};
		});
	}
	
//	private void testConnection() {
//        TestConnectionServiceProxy proxy = new TestConnectionServiceProxy(userName.getStringValue(), userPassword.getStringValue());
//        proxy.setEndpoint(serverUrl.getStringValue() + "/sdk/TestConnectionService");
//    	// set proxy configurations!
//        String value=serverUrl.getStringValue();
//		new ProxyDataRetriever().initProxyData(proxy,value);        
//        
//        try {
//    		// testing connection
//    		boolean result = proxy.connect();
//    		if (result) {
//    			MessageDialog.openInformation(this.getShell(), "", "Connection test successful!");
//    		} else {
//    			MessageDialog.openError(this.getShell(), "", "Could not connect to SpagoBI Server!");
//    		}
////    		setErrorMessage(null);
////    		setValid(true);
//    	} catch (AxisFault e) {
//    		if (e.getFaultString().startsWith("WSDoAllReceiver")) {
//        		MessageDialog.openError(this.getShell(), "", "Authentication failed!");
//    		} else {
//        		MessageDialog.openError(this.getShell(), "", "Could not connect to SpagoBI Server!");
//        		SpagoBILogger.errorLog("Could not connect to SpagoBI Server!", e);
//    		}
//    	} catch (Exception e) {
//    		MessageDialog.openError(this.getShell(), "", "Could not connect to SpagoBI Server!");
//    		SpagoBILogger.errorLog("Could not connect to SpagoBI Server!", e);
////    		setErrorMessage("Could not connect to SpagoBI Server!");
////    		setValid(false);
//    	}
//	}
	
}