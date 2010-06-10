//$HeadURL: svn+ssh://lbuesching@svn.wald.intevation.de/deegree/base/trunk/resources/eclipse/files_template.xml $
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2010 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.client.mdeditor.gui;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.deegree.client.mdeditor.configuration.ConfigurationException;
import org.deegree.client.mdeditor.configuration.form.FormConfigurationFactory;
import org.deegree.client.mdeditor.io.DataHandler;
import org.deegree.client.mdeditor.model.DataGroup;
import org.slf4j.Logger;

/**
 * TODO add class documentation here
 * 
 * @author <a href="mailto:buesching@lat-lon.de">Lyn Buesching</a>
 * @author last edited by: $Author: lyn $
 * 
 * @version $Revision: $, $Date: $
 */
@ManagedBean
@SessionScoped
public class DataGroupBean implements Serializable {

    private static final long serialVersionUID = -6705118936818062292L;

    private static final Logger LOG = getLogger( DataGroupBean.class );

    // grpId, data groups
    private Map<String, List<DataGroup>> dataGroups = new HashMap<String, List<DataGroup>>();

    // grpId, id of the data group
    private Map<String, String> selectedDataGroups = new HashMap<String, String>();

    private boolean fgiLoaded = false;

    public void setDataGroups( Map<String, List<DataGroup>> dataGroups ) {
        this.dataGroups = dataGroups;
    }

    public Map<String, List<DataGroup>> getDataGroups() {
        if ( !fgiLoaded ) {
            loadDataGroups();
            fgiLoaded = true;
        }
        return dataGroups;
    }

    public void setSelectedDataGroups( Map<String, String> selectedDataGroups ) {
        this.selectedDataGroups = selectedDataGroups;
    }

    public Map<String, String> getSelectedDataGroups() {
        return selectedDataGroups;
    }

    public void addSelectedDataGroup( String groupId, String id ) {
        selectedDataGroups.put( groupId, id );
    }

    public void reloadFormGroup( String grpId, boolean isReferencedGrp ) {
        LOG.debug( "Form Group with id " + grpId + " has changed. Force reload." );
        if ( isReferencedGrp ) {
            dataGroups.put( grpId, DataHandler.getInstance().getDataGroups( grpId ) );
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FormFieldBean formFieldBean = (FormFieldBean) fc.getApplication().getELResolver().getValue(
                                                                                                        fc.getELContext(),
                                                                                                        null,
                                                                                                        "formFieldBean" );
            dataGroups.put( grpId, formFieldBean.getDataGroups( grpId ) );
        }
    }

    // TODO
    private void loadDataGroups() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession( false );
        try {
            Map<String, Boolean> formGroupIds = FormConfigurationFactory.getOrCreateFormConfiguration( session.getId() ).getReferencedFormGroupIds();
            for ( String id : formGroupIds.keySet() ) {
                reloadFormGroup( id, formGroupIds.get( id ) );
            }
        } catch ( ConfigurationException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
