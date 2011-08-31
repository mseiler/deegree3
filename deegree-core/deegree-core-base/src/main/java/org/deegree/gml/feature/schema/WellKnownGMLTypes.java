//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
 Department of Geography, University of Bonn
 and
 lat/lon GmbH

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
package org.deegree.gml.feature.schema;

import static org.deegree.commons.xml.CommonNamespaces.GML3_2_NS;
import static org.deegree.commons.xml.CommonNamespaces.GMLNS;
import static org.deegree.feature.types.property.ValueRepresentation.BOTH;
import static org.deegree.protocol.wfs.WFSConstants.WFS_NS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.deegree.feature.types.FeatureCollectionType;
import org.deegree.feature.types.FeatureType;
import org.deegree.feature.types.GenericFeatureCollectionType;
import org.deegree.feature.types.property.ArrayPropertyType;
import org.deegree.feature.types.property.FeaturePropertyType;
import org.deegree.feature.types.property.PropertyType;

/**
 * Defines well-known {@link FeatureType}s from the GML / OGC core schemas to support GML processing without schema
 * assistance.
 * 
 * @author <a href="mailto:schmitz@lat-lon.de">Andreas Schmitz</a>
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public class WellKnownGMLTypes {

    private static final Map<QName, FeatureCollectionType> elNameToFt = new HashMap<QName, FeatureCollectionType>();

    /** gml:FeatureCollection (from GML 3.1.1 core schemas) */
    public static final FeatureCollectionType GML311_FEATURECOLLECTION;

    /** gml:FeatureCollection (from GML 3.2.1 core schemas) */
    public static final FeatureCollectionType GML321_FEATURECOLLECTION;

    /** wfs:FeatureCollection (from WFS 1.1.0 schemas) */
    public static final FeatureCollectionType WFS110_FEATURECOLLECTION;

    /** ll:FeatureCollection (used by deegree 2 WMS for GetFeatureInfo responses) */
    public static final FeatureCollectionType D2_WMS_FEATURECOLLECTION;

    static {
        // "gml:FeatureCollection" (GML 3.1)
        QName name = new QName( GMLNS, "FeatureCollection", "gml" );
        List<PropertyType> props = new ArrayList<PropertyType>();
        props.add( new FeaturePropertyType( new QName( GMLNS, "featureMember", "gml" ), 0, -1, null, null, null, BOTH ) );
        props.add( new ArrayPropertyType( new QName( GMLNS, "featureMembers", "gml" ), 0, -1, null, null ) );
        GML311_FEATURECOLLECTION = new GenericFeatureCollectionType( name, props, false );
        elNameToFt.put( name, GML311_FEATURECOLLECTION );
        
        // "gml:FeatureCollection" (GML 3.2)
        name = new QName( GML3_2_NS, "FeatureCollection", "gml" );
        props = new ArrayList<PropertyType>();
        props.add( new FeaturePropertyType( new QName( GML3_2_NS, "featureMember", "gml" ), 0, -1, null, null, null,
                                            BOTH ) );
        props.add( new ArrayPropertyType( new QName( GML3_2_NS, "featureMembers", "gml" ), 0, -1, null, null ) );
        GML321_FEATURECOLLECTION = new GenericFeatureCollectionType( name, props, false );
        elNameToFt.put( name, GML321_FEATURECOLLECTION );
        
        // "wfs:FeatureCollection" (WFS 1.1.0)
        name = new QName( WFS_NS, "FeatureCollection", "wfs" );
        props = new ArrayList<PropertyType>();
        props.add( new FeaturePropertyType( new QName( GMLNS, "featureMember", "gml" ), 0, -1, null, null, null, BOTH ) );
        props.add( new FeaturePropertyType( new QName( GML3_2_NS, "featureMember", "gml" ), 0, -1, null, null, null,
                                            BOTH ) );
        props.add( new ArrayPropertyType( new QName( GMLNS, "featureMembers", "gml" ), 0, -1, null, null ) );
        props.add( new ArrayPropertyType( new QName( GML3_2_NS, "featureMembers", "gml" ), 0, -1, null, null ) );
        WFS110_FEATURECOLLECTION = new GenericFeatureCollectionType( name, props, false );
        elNameToFt.put( name, WFS110_FEATURECOLLECTION );
        
        // "ll:FeatureCollection" (deegree 2 WMS GetFeatureInfo)
        name = new QName( "http://www.lat-lon.de", "FeatureCollection", "ll" );
        props = new ArrayList<PropertyType>();
        props.add( new FeaturePropertyType( new QName( GMLNS, "featureMember", "gml" ), 0, -1, null, null, null, BOTH ) );
        props.add( new ArrayPropertyType( new QName( GMLNS, "featureMembers", "gml" ), 0, -1, null, null ) );
        elNameToFt.put( name, new GenericFeatureCollectionType( name, props, false ) );
        D2_WMS_FEATURECOLLECTION = new GenericFeatureCollectionType( name, props, false );
        elNameToFt.put( name, D2_WMS_FEATURECOLLECTION );
    }

    /**
     * Looks up the well-known {@link FeatureCollectionType} for the given qualified name.
     * 
     * @param elName
     *            qualified element name, must not be <code>null</code>
     * @return well-known feature collection type, can be <code>null</code> (no such type)
     */
    public static FeatureCollectionType getType( QName elName ) {
        return elNameToFt.get( elName );
    }
}