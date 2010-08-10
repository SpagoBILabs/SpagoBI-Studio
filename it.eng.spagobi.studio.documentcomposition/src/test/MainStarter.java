package test;

import it.eng.spagobi.studio.documentcomposition.editors.Designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(1000, 1000);
		FillLayout fill=new FillLayout();
		shell.setLayout(fill);
		Composite main=new Composite(shell, SWT.BORDER);
		Designer designer=new Designer(main, null);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

		
		
	}

}
