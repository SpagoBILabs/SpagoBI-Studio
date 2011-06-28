package it.eng.spagobi.studio.core.wizards.deployDatasetWizard;



import it.eng.spagobi.studio.utils.bo.DataSource;
import it.eng.spagobi.studio.utils.bo.Functionality;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wizard that lets you deploy a query into a dataset
 *cot
 */

public class SpagoBIDeployDatasetWizardPivotPage extends WizardPage {


	String projectName = null;
	private IStructuredSelection selection;


	/**
	 * SWT widgets
	 */

	private Group pivotGroup;
	private Label columnPivotLabel;
	private Label rowPivotLabel;
	private Label valuePivotLabel;
	private Label numberedColumnsPivotLabel;
	private Text columnPivotText;
	private Text rowPivotText;
	private Text valuePivotText;
	private Button numberedColumnsPivotCheck;
	private String query;


	JSONObject jsonObject;

	// Filter By type
	DataSource[] datasourceList;		
	Functionality functionality=null;

	private static Logger logger = LoggerFactory.getLogger(SpagoBIDeployDatasetWizardPivotPage.class);

	/**
	 * Constructor
	 * 
	 * @param pageName
	 */
	public SpagoBIDeployDatasetWizardPivotPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("Deploy Dataste");
		setDescription("Set Transformer Configuration");
		this.selection = selection;
	}


	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(final Composite parent) {
		logger.debug("IN");
		Composite all=new Composite(parent, SWT.NONE);
		Shell shell = all.getShell();
		shell.setSize(650,400);

		// get selection file
		Object objSel = selection.toList().get(0);
		File fileSelected=(File)objSel;
		projectName = fileSelected.getProject().getName();


		// Build the page
		FillLayout fl2=new FillLayout();
		fl2.type=SWT.HORIZONTAL;
		all.setLayout(fl2);

		final Composite left=new Composite(all,SWT.BORDER);

		// Left
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		left.setLayout(gl);

		// *************** Container **********************

		// pivot group
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;		
		pivotGroup = new Group(left, SWT.NULL);
		pivotGroup.setText("Pivot");
		pivotGroup.setLayoutData(gridData);
		pivotGroup.setLayout(new FillLayout());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		Composite pivotContainer = new Composite(pivotGroup, SWT.NULL);
		pivotContainer.setLayout(layout);
		columnPivotLabel = new Label(pivotContainer, SWT.NONE);
		columnPivotLabel.setText("Column: ");	
		columnPivotLabel.setEnabled(true);
		columnPivotText= new Text(pivotContainer, SWT.BORDER);
		columnPivotText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		columnPivotText.setEnabled(true);
		rowPivotLabel = new Label(pivotContainer, SWT.NONE);
		rowPivotLabel.setText("Row: ");	
		rowPivotLabel.setEnabled(true);
		rowPivotText= new Text(pivotContainer, SWT.BORDER);
		rowPivotText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rowPivotText.setEnabled(true);
		valuePivotLabel = new Label(pivotContainer, SWT.NONE);
		valuePivotLabel.setText("Value: ");	
		valuePivotLabel.setEnabled(true);		
		valuePivotText= new Text(pivotContainer, SWT.BORDER);
		valuePivotText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		valuePivotText.setEnabled(true);
		numberedColumnsPivotLabel = new Label(pivotContainer, SWT.NONE);
		numberedColumnsPivotLabel.setText("Number rows: ");	
		numberedColumnsPivotLabel.setEnabled(true);
		numberedColumnsPivotCheck = new Button(pivotContainer, SWT.CHECK);
		numberedColumnsPivotCheck.setEnabled(true);		
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				//check if page is complete
				boolean complete=isPageComplete();
				if(complete){
					setPageComplete(true);
				}
				else{
					setPageComplete(false);	        	
				}
			}
		};
		
		columnPivotText.addListener(SWT.KeyUp, listener);
		rowPivotText.addListener(SWT.KeyUp, listener);
		valuePivotText.addListener(SWT.KeyUp, listener);
		
		fillValues();

		setControl(left);
		setControl(all);

	}


	/**
	 *  fill the value
	 */
	public void fillValues(){
		logger.debug("IN");
		logger.debug("OUT");

	}





	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
			}
		}
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
		"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				//containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */


	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}


	public Text getColumnPivotText() {
		return columnPivotText;
	}


	public void setColumnPivotText(Text columnPivotText) {
		this.columnPivotText = columnPivotText;
	}


	public Text getRowPivotText() {
		return rowPivotText;
	}


	public void setRowPivotText(Text rowPivotText) {
		this.rowPivotText = rowPivotText;
	}


	public Text getValuePivotText() {
		return valuePivotText;
	}


	public void setValuePivotText(Text valuePivotText) {
		this.valuePivotText = valuePivotText;
	}



	public boolean isPageComplete() {
		boolean isComplete=true;
		String rowPivot = rowPivotText.getText();
		String columnPivot = columnPivotText.getText();
		String valuePivot = valuePivotText.getText();
		if(valuePivot == null || rowPivot==null || columnPivot==null ||rowPivot.equalsIgnoreCase("") || 
				rowPivot.equalsIgnoreCase("")  || columnPivot.equalsIgnoreCase("") || valuePivot.equalsIgnoreCase("")){
			return false;
		}	

		return isComplete;
	}


	public Button getNumberedColumnsPivotCheck() {
		return numberedColumnsPivotCheck;
	}


	public void setNumberedColumnsPivotCheck(Button numberedColumnsPivotCheck) {
		this.numberedColumnsPivotCheck = numberedColumnsPivotCheck;
	}

	


}