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
package org.deegree.tools.crs.georeferencing.communication.panel2D;

import java.awt.Rectangle;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import org.deegree.geometry.primitive.Ring;
import org.deegree.tools.crs.georeferencing.application.Scene2DValues;
import org.deegree.tools.crs.georeferencing.model.RowColumn;
import org.deegree.tools.crs.georeferencing.model.points.AbstractGRPoint;
import org.deegree.tools.crs.georeferencing.model.points.Point4Values;

/**
 * Abstract base class for the panels to show and draw and for mouse-communication.
 * 
 * @author <a href="mailto:thomas@lat-lon.de">Steffen Thomas</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public abstract class AbstractPanel2D extends JPanel {

    private static final long serialVersionUID = 132095136615318676L;

    protected boolean focus;

    protected Point4Values lastAbstractPoint;

    protected Rectangle zoomRect;

    public static int selectedPointSize;

    public static double zoomValue;

    public void addScene2DMouseListener( MouseListener m ) {

        this.addMouseListener( m );

    }

    public void addScene2DMouseMotionListener( MouseMotionListener m ) {

        this.addMouseMotionListener( m );
    }

    public void addScene2DMouseWheelListener( MouseWheelListener m ) {
        this.addMouseWheelListener( m );
    }

    public void addScene2DActionKeyListener( KeyListener l ) {
        this.addKeyListener( l );
    }

    public void addScene2DFocusListener( FocusListener l ) {
        this.addFocusListener( l );
    }

    public Rectangle getZoomRect() {
        return zoomRect;
    }

    public void setZoomRect( Rectangle zoomRect ) {
        this.zoomRect = zoomRect;
    }

    public void setFocus( boolean focus ) {
        this.focus = focus;
    }

    public boolean getFocus() {
        return focus;
    }

    public Point4Values getLastAbstractPoint() {
        return lastAbstractPoint;
    }

    public void setLastAbstractPoint( AbstractGRPoint lastAbstractPoint, AbstractGRPoint worldCoords, RowColumn rc ) {
        if ( lastAbstractPoint != null && worldCoords != null ) {
            this.lastAbstractPoint = new Point4Values( lastAbstractPoint, worldCoords, rc );
        } else {
            this.lastAbstractPoint = null;
        }
    }

    public abstract void updatePoints( Scene2DValues sceneValues );

    public abstract void setPolygonList( List<Ring> polygonRing, Scene2DValues sceneValues );

}
