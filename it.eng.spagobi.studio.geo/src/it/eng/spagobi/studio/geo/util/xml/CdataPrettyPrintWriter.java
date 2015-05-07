/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.util.xml;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class CdataPrettyPrintWriter extends PrettyPrintWriter {
	public CdataPrettyPrintWriter(Writer writer, XmlFriendlyReplacer replacer) {
		super(writer, replacer);
	}

	protected void writeText(QuickWriter writer, String text) {
		// clean up text here
		super.writeText(writer, text);
	}
}
