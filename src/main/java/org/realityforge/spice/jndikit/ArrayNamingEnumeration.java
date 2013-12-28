/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit;

import java.util.NoSuchElementException;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 * Class for building NamingEnumerations.
 */
final class ArrayNamingEnumeration<T>
  extends AbstractNamingEnumeration<T>
{
  protected T[] m_items;
  protected int m_index;

  public ArrayNamingEnumeration( final Context owner,
                                 final Namespace namespace,
                                 final T[] items )
  {
    super( owner, namespace );
    m_items = items;
    //m_index = 0;
  }

  public boolean hasMoreElements()
  {
    return m_index < m_items.length;
  }

  public T next()
    throws NamingException
  {
    if ( !hasMore() )
    {
      throw new NoSuchElementException();
    }

    final T object = m_items[ m_index++ ];

    if ( object instanceof Binding )
    {
      final Binding binding = (Binding) object;
      final Object resolvedObject = resolve( binding.getName(), binding.getObject() );
      binding.setObject( resolvedObject );
    }

    return object;
  }

  public void close()
  {
    super.close();
    m_items = null;
  }
}
