/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

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

package it.eng.spagobi.studio.oda.design.impl;

import org.eclipse.datatools.connectivity.oda.IConnection;
import org.eclipse.datatools.connectivity.oda.IDriver;
import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.datatools.connectivity.oda.design.DataSetDesign;
import org.eclipse.datatools.connectivity.oda.design.DataSetParameters;
import org.eclipse.datatools.connectivity.oda.design.DesignFactory;
import org.eclipse.datatools.connectivity.oda.design.ParameterDefinition;
import org.eclipse.datatools.connectivity.oda.design.ResultSetColumns;
import org.eclipse.datatools.connectivity.oda.design.ResultSetDefinition;
import org.eclipse.datatools.connectivity.oda.design.ui.designsession.DesignSessionUtil;
import org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Auto-generated implementation of an ODA data set designer page
 * for an user to create or edit an ODA data set design instance.
 * This custom page provides a simple Query Text control for user input.  
 * It further extends the DTP design-time framework to update
 * an ODA data set design instance based on the query's derived meta-data.
 * <br>
 * A custom ODA designer is expected to change this exemplary implementation 
 * as appropriate. 
 */
public class SpagoBIDataSetWizardPage extends DataSetWizardPage
{

    private static String DEFAULT_MESSAGE = "Set the data set label";
    
    private transient Text m_queryTextField;

	/**
     * Constructor
	 * @param pageName
	 */
	public SpagoBIDataSetWizardPage( String pageName )
	{
        super( pageName );
        setTitle( pageName );
        setMessage( DEFAULT_MESSAGE );

        setPageComplete( false );
	}

	/**
     * Constructor
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public SpagoBIDataSetWizardPage( String pageName, String title,
			ImageDescriptor titleImage )
	{
        super( pageName, title, titleImage );
        setMessage( DEFAULT_MESSAGE );
        setPageComplete( false );
	}

	/* (non-Javadoc)
	 * @see org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage#createPageCustomControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPageCustomControl( Composite parent )
	{
        setControl( createPageControl( parent ) );
        initializeControl();
	}
    
    /**
     * Creates custom control for user-defined query text.
     */
    private Control createPageControl( Composite parent )
    {
    	Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new GridLayout( 1, false ) );
        GridData gridData = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                | GridData.VERTICAL_ALIGN_FILL );

        composite.setLayoutData( gridData );

        Label fieldLabel = new Label( composite, SWT.NONE );
        fieldLabel.setText( "Dataset Label:" );
        
        m_queryTextField = new Text( composite, SWT.BORDER
                | SWT.V_SCROLL | SWT.H_SCROLL );
        GridData data = new GridData( GridData.FILL_HORIZONTAL );
        data.heightHint = 20;
        m_queryTextField.setLayoutData( data );
        m_queryTextField.addModifyListener( new ModifyListener( ) 
        {
            public void modifyText( ModifyEvent e )
            {
                validateData();
            }
        } );
        
       
        Table dataset = new Table(composite, SWT.BORDER);
        TableColumn tc1 = new TableColumn(dataset, SWT.LEFT);
        TableColumn tc2 = new TableColumn(dataset, SWT.LEFT);
        TableColumn tc3 = new TableColumn(dataset, SWT.LEFT);
        tc1.setText("First Name");
        tc2.setText("Last Name");
        tc3.setText("Address");
        tc1.setWidth(70);
        tc2.setWidth(70);
        tc3.setWidth(80);
        dataset.setHeaderVisible(true);

        
        GridData gdL = new GridData(GridData.FILL_BOTH);
        dataset.setLayoutData(gdL);	
        
        TableItem ti = new TableItem(dataset, 0);
		ti.setText(new String[]{"dtaset name1", "dtaset name2", "dtaset name3"});
		ti = new TableItem(dataset, 2);
		ti.setText(new String[]{"dtaset name4", "dtaset name5", "dtaset name6"});
		
	
        setPageComplete( true );
        return composite;
    }

	/**
	 * Initializes the page control with the last edited data set design.
	 */
	private void initializeControl( )
	{
        DataSetDesign dataSetDesign = getInitializationDesign();
        if( dataSetDesign == null )
            return; // nothing to initialize

        String queryText = dataSetDesign.getQueryText();
        if( queryText == null )
            return; // nothing to initialize

        // initialize control

        m_queryTextField.setText( queryText );
        validateData();
        setMessage( DEFAULT_MESSAGE );
	}

    /**
     * Obtains the user-defined query text of this data set from page control.
     * @return query text
     */
    private String getQueryText( )
    {
        return m_queryTextField.getText();
    }

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage#collectDataSetDesign(org.eclipse.datatools.connectivity.oda.design.DataSetDesign)
	 */
	protected DataSetDesign collectDataSetDesign( DataSetDesign design )
	{
        if( getControl() == null )     // page control was never created
            return design;             // no editing was done
        if( ! hasValidData() )
            return null;    // to trigger a design session error status
        savePage( design );
        return design;
	}

    /*
     * (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage#collectResponseState()
     */
	protected void collectResponseState( )
	{
		super.collectResponseState( );
		/*
		 * To optionally assign a custom response state, for inclusion in the ODA
		 * design session response, use 
         *      setResponseSessionStatus( SessionStatus status );
         *      setResponseDesignerState( DesignerState customState );
		 */
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSetWizardPage#canLeave()
	 */
	protected boolean canLeave( )
	{
        return isPageComplete();
	}

    /**
     * Validates the user-defined value in the page control exists
     * and not a blank text.
     * Set page message accordingly.
     */
	private void validateData( )
	{
        boolean isValid = ( m_queryTextField != null &&
            getQueryText() != null && getQueryText().trim().length() > 0 );

        if( isValid )
            setMessage( DEFAULT_MESSAGE );
        else
            setMessage( "Requires input value.", ERROR );

		setPageComplete( isValid );
	}

	/**
	 * Indicates whether the custom page has valid data to proceed 
     * with defining a data set.
	 */
	private boolean hasValidData( )
	{
        validateData( );
        
		return canLeave();
	}

	/**
     * Saves the user-defined value in this page, and updates the specified 
     * dataSetDesign with the latest design definition.
	 */
	private void savePage( DataSetDesign dataSetDesign )
	{
        // save user-defined query text
        String queryText = getQueryText();
        dataSetDesign.setQueryText( queryText );

        // obtain query's current runtime metadata, and maps it to the dataSetDesign
        IConnection customConn = null;
        try
        {
            // instantiate your custom ODA runtime driver class
            /* Note: You may need to manually update your ODA runtime extension's
             * plug-in manifest to export its package for visibility here.
             */
            IDriver customDriver = new it.eng.spagobi.studio.oda.impl.Driver();
            
            // obtain and open a live connection
            customConn = customDriver.getConnection( null );
            java.util.Properties connProps = 
                DesignSessionUtil.getEffectiveDataSourceProperties( 
                         getInitializationDesign().getDataSourceDesign() );
            customConn.open( connProps );

            // update the data set design with the 
            // query's current runtime metadata
            updateDesign( dataSetDesign, customConn, queryText );
        }
        catch( OdaException e )
        {
            // not able to get current metadata, reset previous derived metadata
            dataSetDesign.setResultSets( null );
            dataSetDesign.setParameters( null );
            
            e.printStackTrace();
        }
        finally
        {
            closeConnection( customConn );
        }
	}

    /**
     * Updates the given dataSetDesign with the queryText and its derived metadata
     * obtained from the ODA runtime connection.
     */
    private void updateDesign( DataSetDesign dataSetDesign,
                               IConnection conn, String queryText )
        throws OdaException
    {
        IQuery query = conn.newQuery( null );
        query.prepare( queryText );
        
        // TODO a runtime driver might require a query to first execute before
        // its metadata is available
//      query.setMaxRows( 1 );
//      query.executeQuery();
        
        try
        {
            IResultSetMetaData md = query.getMetaData();
            updateResultSetDesign( md, dataSetDesign );
        }
        catch( OdaException e )
        {
            // no result set definition available, reset previous derived metadata
            dataSetDesign.setResultSets( null );
            e.printStackTrace();
        }
        
        // proceed to get parameter design definition
        try
        {
            IParameterMetaData paramMd = query.getParameterMetaData();
            updateParameterDesign( paramMd, dataSetDesign );
        }
        catch( OdaException ex )
        {
            // no parameter definition available, reset previous derived metadata
            dataSetDesign.setParameters( null );
            ex.printStackTrace();
        }
        
        /*
         * See DesignSessionUtil for more convenience methods
         * to define a data set design instance.  
         */     
    }

    /**
     * Updates the specified data set design's result set definition based on the
     * specified runtime metadata.
     * @param md    runtime result set metadata instance
     * @param dataSetDesign     data set design instance to update
     * @throws OdaException
     */
	private void updateResultSetDesign( IResultSetMetaData md,
            DataSetDesign dataSetDesign ) 
        throws OdaException
	{
        ResultSetColumns columns = DesignSessionUtil.toResultSetColumnsDesign( md );

        ResultSetDefinition resultSetDefn = DesignFactory.eINSTANCE
                .createResultSetDefinition();
        // resultSetDefn.setName( value );  // result set name
        resultSetDefn.setResultSetColumns( columns );

        // no exception in conversion; go ahead and assign to specified dataSetDesign
        dataSetDesign.setPrimaryResultSet( resultSetDefn );
        dataSetDesign.getResultSets().setDerivedMetaData( true );
	}

    /**
     * Updates the specified data set design's parameter definition based on the
     * specified runtime metadata.
     * @param paramMd   runtime parameter metadata instance
     * @param dataSetDesign     data set design instance to update
     * @throws OdaException
     */
    private void updateParameterDesign( IParameterMetaData paramMd,
            DataSetDesign dataSetDesign ) 
        throws OdaException
    {
        DataSetParameters paramDesign = 
            DesignSessionUtil.toDataSetParametersDesign( paramMd, 
                    DesignSessionUtil.toParameterModeDesign( IParameterMetaData.parameterModeIn ) );
        
        // no exception in conversion; go ahead and assign to specified dataSetDesign
        dataSetDesign.setParameters( paramDesign );        
        if( paramDesign == null )
            return;     // no parameter definitions; done with update
        
        paramDesign.setDerivedMetaData( true );

        // TODO replace below with data source specific implementation;
        // hard-coded parameter's default value for demo purpose
        if( paramDesign.getParameterDefinitions().size() > 0 )
        {
            ParameterDefinition paramDef = 
                (ParameterDefinition) paramDesign.getParameterDefinitions().get( 0 );
            if( paramDef != null )
                paramDef.setDefaultScalarValue( "dummy default value" );
        }
    }

    /**
     * Attempts to close given ODA connection.
     */
    private void closeConnection( IConnection conn )
    {
        try
        {
            if( conn != null && conn.isOpen() )
                conn.close();
        }
        catch ( OdaException e )
        {
            // ignore
            e.printStackTrace();
        }
    }

}
