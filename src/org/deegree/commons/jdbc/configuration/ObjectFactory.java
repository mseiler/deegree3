//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.05.11 at 02:50:27 PM MESZ 
//


package org.deegree.commons.jdbc.configuration;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.deegree.commons.jdbc.configuration package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.deegree.commons.jdbc.configuration
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PooledConnection }
     * 
     */
    public PooledConnection createPooledConnection() {
        return new PooledConnection();
    }

    /**
     * Create an instance of {@link JDBCConnections }
     * 
     */
    public JDBCConnections createJDBCConnections() {
        return new JDBCConnections();
    }

}
