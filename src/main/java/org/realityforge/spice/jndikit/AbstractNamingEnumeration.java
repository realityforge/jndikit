/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit;

import java.util.NoSuchElementException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Class for building NamingEnumerations.
 */
public abstract class AbstractNamingEnumeration<T>
  implements NamingEnumeration<T>
{
  private Context m_owner;
  private Namespace m_namespace;

  public AbstractNamingEnumeration( final Context owner, final Namespace namespace )
  {
    m_owner = owner;
    m_namespace = namespace;
  }

  public boolean hasMore()
    throws NamingException
  {
    return hasMoreElements();
  }

  public T nextElement()
  {
    try
    {
      return next();
    }
    catch ( final NamingException ne )
    {
      throw new NoSuchElementException( ne.toString() );
    }
  }

  protected Object resolve( final String name, final Object object )
    throws NamingException
  {
    // Call getObjectInstance for using any object factories
    try
    {
      final Name atom = m_owner.getNameParser( name ).parse( name );
      return m_namespace.
        getObjectInstance( object, atom, m_owner, m_owner.getEnvironment() );
    }
    catch ( final Exception e )
    {
      final NamingException ne = new NamingException( "getObjectInstance failed" );
      ne.setRootCause( e );
      throw ne;
    }
  }

  public void close()
  {
    m_namespace = null;
    m_owner = null;
  }
}
