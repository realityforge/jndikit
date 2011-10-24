/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.test;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.StateFactory;


/**
 * Implementation of  {@link StateFactory} for testing purposes.
 */
public class TestStateFactory
  implements StateFactory
{
  public Object getStateToBind( Object obj, Name name, Context nameCtx,
                                Hashtable environment )
    throws NamingException
  {
    Object result = null;
    if ( obj instanceof TestData )
    {
      TestData data = (TestData) obj;
      TestDataReferenceable ref = new TestDataReferenceable(
        data.getValue() );
      result = ref.getReference();
    }
    return result;
  }
}
