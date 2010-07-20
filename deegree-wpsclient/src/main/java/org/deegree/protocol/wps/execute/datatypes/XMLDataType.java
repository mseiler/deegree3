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
package org.deegree.protocol.wps.execute.datatypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code></code> class TODO add class documentation here.
 * 
 * @author <a href="mailto:ionita@lat-lon.de">Andrei Ionita</a>
 * 
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 * 
 */
public class XMLDataType implements DataType {

    private static Logger LOG = LoggerFactory.getLogger( XMLDataType.class );

    private File file;

    private ComplexAttributes complexAttribs;

    public XMLDataType( File file, ComplexAttributes complexAttribs ) {
        this.file = file;
        this.complexAttribs = complexAttribs;
    }

    /**
     * 
     * @return an {@link XMLStreamReader} instance
     * @throws FileNotFoundException
     */
    public XMLStreamReader getAsXMLStream() {
        XMLInputFactory inFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlReader = null;
        try {
            xmlReader = inFactory.createXMLStreamReader( new FileInputStream( file ), "UTF-8" );
        } catch ( XMLStreamException e ) {
            LOG.error( "Error while creating an XML stream to read. " + e.getMessage() );
        } catch ( FileNotFoundException e ) {
            LOG.error( "Error while creating an XML stream to read. " + e.getMessage() );
        }
        return xmlReader;
    }

    public ComplexAttributes getComplexAttributes() {
        return complexAttribs;
    }

}
