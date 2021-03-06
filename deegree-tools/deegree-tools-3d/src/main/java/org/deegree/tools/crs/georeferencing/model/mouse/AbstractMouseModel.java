//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
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
package org.deegree.tools.crs.georeferencing.model.mouse;

import javax.vecmath.Point2d;

import org.deegree.tools.crs.georeferencing.model.points.AbstractGRPoint;

/**
 * TODO add class documentation here
 * 
 * @author <a href="mailto:thomas@lat-lon.de">Steffen Thomas</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public abstract class AbstractMouseModel {

    private Point2d pointMousePressed;

    private AbstractGRPoint mouseChanging;

    private AbstractGRPoint mouseMoved;

    private boolean mouseInside;

    // private Point2d cumulatedMouseChanging;

    public AbstractMouseModel() {
        reset();

    }

    public Point2d getPointMousePressed() {
        return pointMousePressed;
    }

    /**
     * 
     * @param pointMousePressed
     */
    public void setPointMousePressed( Point2d pointMousePressed ) {
        this.pointMousePressed = pointMousePressed;
    }

    public AbstractGRPoint getMouseChanging() {
        return mouseChanging;
    }

    public boolean isMouseInside() {
        return mouseInside;
    }

    public void setMouseInside( boolean mouseInside ) {
        this.mouseInside = mouseInside;
    }

    /**
     * 
     * @param mouseChanging
     */
    public void setMouseChanging( AbstractGRPoint mouseChanging ) {
        this.mouseChanging = mouseChanging;
    }

    /**
     * Resets the mouse values to initial values.
     */
    public void reset() {
        mouseChanging = null;

    }

    public AbstractGRPoint getMouseMoved() {
        return mouseMoved;
    }

    public void setMouseMoved( AbstractGRPoint mouseMoved ) {
        this.mouseMoved = mouseMoved;
    }

}
