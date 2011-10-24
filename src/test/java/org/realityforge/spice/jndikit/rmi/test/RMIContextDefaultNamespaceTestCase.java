/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.rmi.test;

import java.util.Hashtable;
import javax.naming.NamingException;
import org.realityforge.spice.jndikit.DefaultNamespace;
import org.realityforge.spice.jndikit.Namespace;
import org.realityforge.spice.jndikit.rmi.RMIInitialContextFactory;
import org.realityforge.spice.jndikit.test.TestObjectFactory;
import org.realityforge.spice.jndikit.test.TestStateFactory;

/**
 * Unit test for RMI context, using the {@link DefaultNamespace}.
 */
public class RMIContextDefaultNamespaceTestCase
  extends AbstractRMIContextTestCase
{

  public RMIContextDefaultNamespaceTestCase()
  {
    super( new DefaultNamespaceICF() );
  }

  static class DefaultNamespaceICF
    extends RMIInitialContextFactory
  {

    protected Namespace newNamespace( final Hashtable environment )
      throws NamingException
    {
      final DefaultNamespace namespace = (DefaultNamespace) super.newNamespace(
        environment );
      namespace.addObjectFactory( new TestObjectFactory() );
      namespace.addStateFactory( new TestStateFactory() );
      return namespace;
    }
  }
}
