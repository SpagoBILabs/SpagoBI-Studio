/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.jasper.preferences;

import it.eng.spagobi.studio.jasper.Activator;
import it.eng.spagobi.studio.utils.util.SpagoBIStudioConstants;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
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

public class EditorsConfigurationPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	private FileFieldEditor iReportExecFile = null;
	
	public EditorsConfigurationPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		//setDescription("( In order for iReport to work be sure that the workspace path has no empty spaces ) ");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
//		iReportInstallDir = new StringFieldEditor(PreferenceConstants.IREPORT_INSTALLATION_DIR, "IReport Installation Dir:", getFieldEditorParent());
//		addField(iReportInstallDir);
	
		iReportExecFile = new FileFieldEditor(SpagoBIStudioConstants.IREPORT_EXEC_FILE, "IReport execution File:", getFieldEditorParent());
		addField(iReportExecFile);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	protected void contributeButtons(Composite parent) {
	}
	

	
}