//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2012 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -
 and
 - Occam Labs UG (haftungsbeschränkt) -

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

 Occam Labs UG (haftungsbeschränkt)
 Godesberger Allee 139, 53175 Bonn
 Germany

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.rendering.r2d;

import static com.vividsolutions.jts.algorithm.CGAlgorithms.isCCW;
import static org.deegree.geometry.primitive.GeometricPrimitive.PrimitiveType.Surface;
import static org.deegree.geometry.primitive.Ring.RingType.LinearRing;
import static org.deegree.geometry.primitive.Surface.SurfaceType.Polygon;
import static org.deegree.geometry.validation.GeometryFixer.invertOrientation;

import java.util.ArrayList;
import java.util.List;

import org.deegree.geometry.Geometry;
import org.deegree.geometry.multi.MultiGeometry;
import org.deegree.geometry.primitive.GeometricPrimitive;
import org.deegree.geometry.primitive.Polygon;
import org.deegree.geometry.primitive.Ring;
import org.deegree.geometry.primitive.Surface;
import org.deegree.geometry.standard.AbstractDefaultGeometry;
import org.deegree.geometry.standard.multi.DefaultMultiGeometry;
import org.deegree.geometry.standard.multi.DefaultMultiPolygon;
import org.deegree.geometry.standard.multi.DefaultMultiSurface;
import org.deegree.geometry.standard.primitive.DefaultPolygon;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LinearRing;

/**
 * Responsible for fixing geometry orientation (ring orientations of polygons).
 * 
 * @author <a href="mailto:schmitz@occamlabs.de">Andreas Schmitz</a>
 * @author last edited by: $Author: stranger $
 * 
 * @version $Revision: $, $Date: $
 */
class OrientationFixer {

    static Geometry fixOrientation( Geometry geom ) {
        switch ( geom.getGeometryType() ) {
        case PRIMITIVE_GEOMETRY:
            return fixOrientation( (GeometricPrimitive) geom );
        case MULTI_GEOMETRY:
            return fixOrientation( (MultiGeometry<?>) geom );
        default: {
            throw new UnsupportedOperationException();
        }
        }
    }

    private static Geometry fixOrientation( GeometricPrimitive geom ) {
        if ( geom.getPrimitiveType() == Surface ) {
            return fixOrientation( (Surface) geom );
        }
        return geom;
    }

    private static Geometry fixOrientation( Surface geom ) {
        if ( geom.getSurfaceType() == Polygon ) {
            return fixOrientation( (Polygon) geom );
        }
        throw new UnsupportedOperationException();
    }

    private static Geometry fixOrientation( Polygon geom ) {
        Ring exteriorRing = fixOrientation( geom.getExteriorRing(), false );
        List<Ring> interiorRings = fixInteriorOrientation( geom.getInteriorRings() );
        return new DefaultPolygon( null, geom.getCoordinateSystem(), null, exteriorRing, interiorRings );
    }

    private static List<Ring> fixInteriorOrientation( List<Ring> interiorRings ) {
        if ( interiorRings == null ) {
            return null;
        }
        List<Ring> fixedRings = new ArrayList<Ring>();
        for ( Ring interiorRing : interiorRings ) {
            fixedRings.add( fixOrientation( interiorRing, true ) );
        }
        return fixedRings;
    }

    private static Ring fixOrientation( Ring ring, boolean forceClockwise ) {
        if ( ring.getRingType() != LinearRing ) {
            throw new UnsupportedOperationException();
        }
        LinearRing jtsRing = (LinearRing) ( (AbstractDefaultGeometry) ring ).getJTSGeometry();
        Coordinate[] coords = jtsRing.getCoordinates();

        // TODO check if inversions can be applied in any case (i.e. whether JTS has a guaranteed orientation of
        // intersection result polygons)

        boolean needsInversion = isCCW( coords ) == forceClockwise;
        if ( needsInversion ) {
            return invertOrientation( ring );
        }
        return ring;
    }

    @SuppressWarnings("unchecked")
    private static Geometry fixOrientation( MultiGeometry<?> geom ) {

        List fixedMembers = new ArrayList<Object>( geom.size() );
        for ( Geometry member : geom ) {
            Geometry fixedMember = fixOrientation( member );
            fixedMembers.add( fixedMember );
        }

        switch ( geom.getMultiGeometryType() ) {
        case MULTI_GEOMETRY:
            return new DefaultMultiGeometry<Geometry>( null, geom.getCoordinateSystem(), null,
                                                       (List<Geometry>) fixedMembers );
        case MULTI_POLYGON:
            return new DefaultMultiPolygon( null, geom.getCoordinateSystem(), null, (List<Polygon>) fixedMembers );
        case MULTI_SURFACE:
            return new DefaultMultiSurface( null, geom.getCoordinateSystem(), null, (List<Surface>) fixedMembers );
        default:
            throw new UnsupportedOperationException();
        }
    }

}
