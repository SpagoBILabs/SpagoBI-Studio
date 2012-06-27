/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.documentcomposition;

import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.metadata.MetadataDocumentComposition;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "it.eng.spagobi.studio.documentcomposition";

	// The shared instance
	private static Activator plugin;
	/*Modello del template del documento composto*/
	private DocumentComposition documentComposition;
	private MetadataDocumentComposition metadataDocumentComposition;
	


	public MetadataDocumentComposition getMetadataDocumentComposition() {
		return metadataDocumentComposition;
	}

	public void setMetadataDocumentComposition(MetadataDocumentComposition metadataDocumentComposition) {
		this.metadataDocumentComposition = metadataDocumentComposition;
	}

	public DocumentComposition getDocumentComposition() {
		return documentComposition;
	}

	public void setDocumentComposition(DocumentComposition documentComposition) {
		this.documentComposition = documentComposition;
	}

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
