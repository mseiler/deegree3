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
package org.deegree.feature.xpath;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.deegree.commons.tom.gml.GMLObject;
import org.deegree.commons.tom.gml.property.Property;
import org.deegree.feature.xpath.node.GMLObjectNode;
import org.deegree.feature.xpath.node.PropertyNode;

/**
 * {@link Iterator} over property nodes of a feature node.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </a>
 * @author last edited by: $Author:$
 * 
 * @version $Revision:$, $Date:$
 */
class PropertyNodeIterator implements Iterator<PropertyNode> {

    private GMLObjectNode<GMLObject, GMLObject> parent;

    private Iterator<Property> props;

    PropertyNodeIterator( GMLObjectNode<GMLObject, GMLObject> parent ) {
        this.parent = parent;
        if ( parent.getValue().getProperties() != null ) {
            this.props = parent.getValue().getProperties().iterator();
        }
    }

    @Override
    public boolean hasNext() {
        return props != null && props.hasNext();
    }

    @Override
    public PropertyNode next() {
        if ( !hasNext() ) {
            throw new NoSuchElementException();
        }
        Property prop = null;
        if ( props != null && props.hasNext() ) {
            prop = props.next();
        }
        return new PropertyNode( parent, prop );
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
