package it.eng.spagobi.studio.core.wizards.serverWizard;

import it.eng.spagobi.studio.core.actions.NewServerAction;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * Thre Download Wizard let the user to navigate the funcitonalities tree and select a document to download
 *
 */

public class NewServerWizardPage extends WizardPage {
	//private Text containerText;

	//private Text fileText;

	private IStructuredSelection selection;

	// Labels Field
	private Label labelName =null;
	private Label labelUrl =null;
	private Label labelUser =null;
	private Label labelPwd =null;
	private Label labelActive =null;
	private Text textName = null;
	private Text textUser = null;
	private Text textPwd = null;
	private Text textUrl = null;
	private Button checkActive = null;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NewServerAction.class);



	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewServerWizardPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("New Server Wizard");
		setDescription("This wizard lets you define a new server");
		this.selection = selection;
	}

	/** Creates the wizard form
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		logger.debug("IN");
		Composite container = parent;
		//new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		container.setLayout(gl);

		labelName = new Label(container, SWT.NONE);
		labelName.setText("Tempalte file name: ");
		labelName.setLayoutData(new GridData(GridData.FILL_BOTH));

		textName = new Text(container, SWT.BORDER);
		textName.setLayoutData(new GridData(GridData.FILL_BOTH));
		textName.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				boolean complete=isPageComplete();
				if(complete) setPageComplete(true);
				else		setPageComplete(false);	        	
			}
		});

		labelName = new Label(container, SWT.NONE);
		labelName.setText("Server Name: ");
		labelName.setLayoutData(new GridData(GridData.FILL_BOTH));

		textUrl = new Text(container, SWT.BORDER);
		textUrl.setLayoutData(new GridData(GridData.FILL_BOTH));
		textUrl.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				boolean complete=isPageComplete();
				if(complete) setPageComplete(true);
				else		setPageComplete(false);	        	
			}
		});

		labelUser = new Label(container, SWT.NONE);
		labelUser.setText("User: ");
		labelUser.setLayoutData(new GridData(GridData.FILL_BOTH));

		textUser = new Text(container, SWT.BORDER);
		textUser.setLayoutData(new GridData(GridData.FILL_BOTH));
		textUser.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event event) {
				boolean complete=isPageComplete();
				if(complete) setPageComplete(true);
				else		setPageComplete(false);	        	
			}
		});

		labelPwd = new Label(container, SWT.NONE);
		labelPwd.setText("Password: ");
		labelPwd.setLayoutData(new GridData(GridData.FILL_BOTH));

		textPwd = new Text(container, SWT.BORDER | SWT.PASSWORD);
		textPwd.setLayoutData(new GridData(GridData.FILL_BOTH));

		labelActive = new Label(container, SWT.NONE);
		labelActive.setText("Active: ");
		labelActive.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		checkActive = new Button(container, SWT.CHECK);
		checkActive.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		initialize();
		setControl(container);
		logger.debug("OUT");

	}



	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
	}


	/**
	 * Ensures that both text fields are set.
	 */


	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}


	/**
	 *  New server wizard opage is complete if at list url and user are filled
	 */

	public boolean isPageComplete() {
		String name = textName.getText();
		String url = textUrl.getText();
		String user = textUser.getText();
		if(url.equalsIgnoreCase("")
				|| 
				user.equalsIgnoreCase("")
				||	
				name.equalsIgnoreCase("")
		)
			return false;
		else return true;
	}

	public Text getTextUser() {
		return textUser;
	}

	public void setTextUser(Text textUser) {
		this.textUser = textUser;
	}

	public Text getTextPwd() {
		return textPwd;
	}

	public void setTextPwd(Text textPwd) {
		this.textPwd = textPwd;
	}

	public Text getTextUrl() {
		return textUrl;
	}

	public void setTextUrl(Text textUrl) {
		this.textUrl = textUrl;
	}

	public Text getTextName() {
		return textName;
	}

	public void setTextName(Text textName) {
		this.textName = textName;
	}

	public Button getCheckActive() {
		return checkActive;
	}

	public void setCheckActive(Button checkActive) {
		this.checkActive = checkActive;
	}






}