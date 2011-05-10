package it.eng.spagobi.studio.core.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ModelPropertyPageSettings implements IPropertyPageSettings {

	IFile fileSel = null;

	public String getDescription() {
		return "Model properties";
	}

	public ModelPropertyPageSettings(IFile filSel) {
		super();
		this.fileSel = filSel;
	}

	public Control createContents(Composite contents) {
		// TODO Auto-generated method stub
		return null;
	}

	public String fillValues() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}


}
