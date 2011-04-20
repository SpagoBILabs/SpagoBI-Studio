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
package it.eng.spagobi.studio.core.editors;

import it.eng.spagobi.studio.core.bo.Server;
import it.eng.spagobi.studio.core.bo.xmlMapping.XmlServerGenerator;
import it.eng.spagobi.studio.core.services.server.ServerHandler;
import it.eng.spagobi.studio.core.util.SWTComponentUtilities;
import it.eng.spagobi.studio.core.util.SpagoBIStudioConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerEditor extends EditorPart {

	protected Server server = null;
	private static Logger logger = LoggerFactory.getLogger(ServerEditor.class);
	protected boolean isDirty = false;

	protected final static RGB RED = new RGB(255, 0, 0);
	protected final static RGB GREEN = new RGB(10, 255, 30);

	private Label labelName =null;
	private Label labelUrl =null;
	private Label labelUser =null;
	private Label labelPwd =null;
	private Label labelActive =null;
	private Label labelStatus =null;
	private Text textName = null;
	private Text textUser = null;
	private Text textPwd = null;
	private Text textUrl = null;
	private Button checkActive = null;
	private Button buttonTest = null;

	private boolean previouslyActive = false;


	public void doSave(IProgressMonitor monitor) {
		logger.debug("IN");
		ByteArrayInputStream bais = null;
		try { 
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			XmlServerGenerator xmlgen = new XmlServerGenerator();
			String xmlString = xmlgen.transformToXml(server);
			// if is active and before was inactive check that other servers are not active
			if(previouslyActive == false && server.isActive()){
				new ServerHandler(server).deactivateOtherServers(file);
			}

			byte[] bytes = xmlString.getBytes();
			bais = new ByteArrayInputStream(bytes);
			file.setContents(bais, IFile.FORCE, null);
		} catch (CoreException e) {
			logger.error("error in updating file");
			return;
		} finally { 
			if (bais != null)
				try {
					bais.close();
				} catch (IOException e) {
					logger.error("error in closing the byte array");
					return;
				}
		}
		setDirty(false);
		
		// refresh the Resource navigator
		SWTComponentUtilities.getNavigatorReference(SpagoBIStudioConstants.RESOURCE_NAVIGATOR_ID);
		
		logger.debug("OUT");
	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input) {
		logger.debug("IN");
		this.setPartName(input.getName());
		FileEditorInput fei = (FileEditorInput) input;	
		setInput(input);
		setSite(site);
		// check there is not another server editor open. (only one at a time for active server handling
		IEditorPart currentEditor = SWTComponentUtilities.getEditorReference(SpagoBIStudioConstants.SERVER_EDITOR_ID);
		if(currentEditor != null && currentEditor instanceof ServerEditor){
			logger.warn("Server editor is already opened!");
			//MessageDialog.openWarning(site.getShell(), "WARNING", "You have already a server editor opened");
			MessageDialog.openWarning(site.getShell(), "WARNING", "You have already a server editor opened");
			IWorkbenchPage iworkbenchpage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			iworkbenchpage.closeEditor(this, false);
			return;
		}

		IFile file = fei.getFile();
		XmlServerGenerator xmlGen = new XmlServerGenerator();
		try {
			server = xmlGen.readXml(file);
		} catch (Exception e) {
			logger.error("Error in reading the model in file "+file.getName());
			MessageDialog.openError(site.getShell(), "Error", "Error in reading the model in file "+file.getName());
			dispose();
		}

		logger.debug("OUT");
	}


	public boolean isSaveAsAllowed() {
		return false;
	}

	public void createPartControl(final Composite container) {
		logger.debug("IN");

		String url = "http://localhost:8080/SpagoBI";
		String user = "biadmin";
		String pwd = "biadmin";
		String name = "ServerName";
		boolean active = false;

		if(server != null){
			url = server.getUrl();
			user = server.getUser();
			pwd = server.getPassword();
			active = server.isActive();
			name = server.getName();
		}
		else logger.warn("server Object is null, use some defaults values");

		//new Composite(parent, SWT.NULL);
		FormToolkit toolkit = new FormToolkit(container.getDisplay());
		final ScrolledForm form = toolkit.createScrolledForm(container);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 10;
		layout.topMargin = 20;
		layout.leftMargin = 20;
		form.getBody().setLayout(layout);

		Section section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TITLE_BAR|Section.TWISTIE|Section.EXPANDED);

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		section.setLayoutData(td);

		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText(" Server information");
		section.setDescription("Set server connection data");
		Composite sectionClient = toolkit.createComposite(section);
		//		GridLayout gl = new GridLayout();
		//		gl.numColumns = 2;
		sectionClient.setLayout(new GridLayout(2, true));


		labelName = new Label(sectionClient, SWT.NULL);
		labelName.setText("                                           Template file name: ");
		labelName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		textName = new Text(sectionClient, SWT.BORDER);
		textName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				server.setName(textName.getText());
				setDirty(true);
			}
		});
		textName.setText(name);

		labelUrl = new Label(sectionClient, SWT.NULL);
		labelUrl.setText("                                           Server Name: ");
		labelUrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		textUrl = new Text(sectionClient, SWT.BORDER);
		textUrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textUrl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				server.setUrl(textUrl.getText());
				setDirty(true);
			}
		});
		textUrl.setText(url);

		labelUser = new Label(sectionClient, SWT.NULL);
		labelUser.setText("                                           User: ");
		labelUser.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		textUser = new Text(sectionClient, SWT.BORDER);
		textUser.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textUser.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				server.setUser(textUser.getText());
				setDirty(true);
			}
		});
		textUser.setText(user);

		labelPwd = new Label(sectionClient, SWT.NULL);
		labelPwd.setText("                                           Password: ");
		labelPwd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		textPwd = new Text(sectionClient, SWT.BORDER | SWT.PASSWORD);
		textPwd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textPwd.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				server.setPassword(textPwd.getText());
				setDirty(true);
			}
		});
		textPwd.setText(pwd);

		labelActive = new Label(sectionClient, SWT.NULL);
		labelActive.setText("                                           Active: ");
		labelActive.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		checkActive = new Button(sectionClient, SWT.CHECK);
		checkActive.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		checkActive.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				server.setActive(checkActive.getSelection());
				setDirty(true);		
			}
		});
		checkActive.setSelection(active);
		previouslyActive = server.isActive();


		Label labelEmpty = new Label(sectionClient, SWT.NULL);
		labelEmpty.setText("                                           ");
		labelEmpty.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonTest = new Button(sectionClient, SWT.PUSH);
		buttonTest.setText("Test");
		buttonTest.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonTest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				ServerHandler sh = new ServerHandler(server);
				boolean result = sh.testConnection();
				RGB setForeground = (result== true) ? GREEN : RED;
				labelStatus.setForeground(new Color(container.getDisplay(), setForeground));
				labelStatus.redraw();
				labelStatus.setText(sh.getMessage());
			}
		});


		//		labelEmpty = new Label(sectionClient, SWT.NULL);
		//		labelEmpty.setText("                                      ");
		//		labelEmpty.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridData statusGrid=new GridData(GridData.FILL_HORIZONTAL);
		statusGrid.horizontalSpan=2;
		labelStatus = new Label(sectionClient, SWT.NULL);
		labelStatus.setLayoutData(statusGrid);
		labelStatus.setText("                                                                                      ");
		section.setClient(sectionClient);
		logger.debug("OUT");
	}





	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean dirty) {
		this.isDirty = dirty;
		firePropertyChange(PROP_DIRTY);
	}

	public Server getServer() {
		return server;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
