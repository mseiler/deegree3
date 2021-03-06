package org.deegree.console.util;

//$HeadURL: svn+ssh://mschneider@svn.wald.intevation.org/deegree/deegree3/trunk/deegree-client/deegree-jsf-console/src/main/java/org/deegree/client/generic/StatisticBean.java $
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

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.deegree.services.controller.FrontControllerStats;

/**
 * A request scoped bean containing detailed statistic information about the services
 * 
 * @author <a href="mailto:buesching@lat-lon.de">Lyn Buesching</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 29926 $, $Date: 2011-03-08 11:47:59 +0100 (Di, 08. Mär 2011) $
 */
@ManagedBean
@RequestScoped
public class StatisticBean implements Serializable {

    private static final long serialVersionUID = 338849983308369306L;

    private long dispatchedRequests = FrontControllerStats.getDispatchedRequests();

    private long activeRequests = FrontControllerStats.getActiveRequests();

    private long averageResponseTime = FrontControllerStats.getAverageResponseTime();

    private long maximumResponseTime = FrontControllerStats.getMaximumResponseTime();

    public long getDispatchedRequests() {
        return dispatchedRequests;
    }

    public long getActiveRequests() {
        return activeRequests;
    }

    public long getAverageResponseTime() {
        return averageResponseTime;
    }

    public long getMaximumResponseTime() {
        return maximumResponseTime;
    }
}
