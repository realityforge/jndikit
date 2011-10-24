/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.rmi.test;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ServiceUnavailableException;

import org.realityforge.spice.jndikit.Namespace;
import org.realityforge.spice.jndikit.NamingProvider;
import org.realityforge.spice.jndikit.RemoteContext;
import org.realityforge.spice.jndikit.StandardNamespace;
import org.realityforge.spice.jndikit.rmi.RMIInitialContextFactory;
import org.realityforge.spice.jndikit.test.TestStateFactory;

/**
 * Unit test for RMI context.
 */
public class RMIContextTestCase
    extends AbstractRMIContextTestCase
{
    public RMIContextTestCase()
    {
        super( new StandardNamespaceICF() );
    }

    static class StandardNamespaceICF extends RMIInitialContextFactory
    {

        public Context getInitialContext( Hashtable environment )
            throws NamingException
        {
            environment.put( Context.STATE_FACTORIES,
                             TestStateFactory.class.getName() );
            return super.getInitialContext( environment );
        }

        protected Namespace newNamespace( final Hashtable environment )
            throws NamingException
        {
            try
            {
                final NamingProvider provider =
                    ( NamingProvider ) environment.get(
                        RemoteContext.NAMING_PROVIDER );

                return new StandardNamespace( provider.getNameParser() );
            }
            catch( final Exception e )
            {
                if( e instanceof NamingException )
                {
                    throw ( NamingException ) e;
                }
                else
                {
                    final ServiceUnavailableException sue =
                        new ServiceUnavailableException( e.getMessage() );
                    sue.setRootCause( e );
                    throw sue;
                }
            }
        }
    }
}
