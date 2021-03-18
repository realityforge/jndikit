/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.memory.test;

import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import org.realityforge.spice.jndikit.StandardNamespace;
import org.realityforge.spice.jndikit.test.AbstractContextTestCase;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

/**
 * Unit test for Memory context, using the {@link StandardNamespace}.
 */
public abstract class AbstractMemoryContextTestCase
  extends AbstractContextTestCase
{
  @Test
  public void testGetNameInNamespace()
    throws AssertionError
  {
    try
    {
      String name = m_context.getNameInNamespace();
      fail( "Expected getNameInNamespace to throw OperationNotSupportedException but returned " + name );
    }
    catch ( final OperationNotSupportedException expected )
    {
    }
    catch ( final NamingException ne )
    {
      throw new AssertionError( ne.getMessage() );
    }
  }
}
