/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 */
package org.objectweb.proactive.core.util.wrapper;

import java.io.Serializable;

import org.objectweb.proactive.annotation.PublicAPI;


/**
 * <p>An reifiable object for wrapping the Java type <code>String</code>.</p>
 * <p>Use this class as result for ProActive asynchronous method calls.</p>
 *
 * @author Alexandre di Costanzo
 *
 * Created on Jul 28, 2005
 */
@PublicAPI
public class StringWrapper implements Serializable {

    /**
     * The not reifiable value.
     */
    protected String value;

    /**
     * The no arguments constructor for ProActive.
     */
    public StringWrapper() {
        // nothing to do
    }

    /**
     * Construct an reifiable object for a <code>String</code>.
     * @param value the class <code>String</code> value.
     */
    public StringWrapper(String value) {
        this.value = value;
    }

    /**
     * Return the value of the <code>String</code>.
     * @return the none reifiable value.
     */
    public String stringValue() {
        return this.value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof StringWrapper) && ((StringWrapper) obj).stringValue().equals(value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
