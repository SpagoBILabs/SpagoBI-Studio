package it.eng.spagobi.studio.extchart.editors.pages;

import org.eclipse.swt.widgets.Composite;
import org.slf4j.LoggerFactory;

public abstract class AbstractPage extends Composite {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractPage.class);
	
	public AbstractPage(Composite parent, int style) {
		super(parent, style);
	}


	
	
}
