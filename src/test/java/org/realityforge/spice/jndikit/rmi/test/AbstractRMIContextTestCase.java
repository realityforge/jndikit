/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.rmi.test;

import javax.naming.Context;
import javax.naming.NamingException;
import org.realityforge.spice.jndikit.rmi.RMIInitialContextFactory;
import org.realityforge.spice.jndikit.test.AbstractContextTestCase;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

/**
 * Unit test for RMI context.
 */
public abstract class AbstractRMIContextTestCase
  extends AbstractContextTestCase
{

  private final RMITestSetup _helper;

  public AbstractRMIContextTestCase( RMIInitialContextFactory factory )
  {
    _helper = new RMITestSetup( factory );
  }

  @Test
  public void testGetNameInNamespace()
    throws AssertionError
  {
    try
    {
      Context sub1 = m_root.createSubcontext( "sub1" );
      Context sub2 = sub1.createSubcontext( "sub2" );
      Context sub3 = sub2.createSubcontext( "sub3" );
      Context sub4 = sub3.createSubcontext( "sub4" );
      assertEquals( "sub1/sub2/sub3/sub4", sub4.getNameInNamespace() );
    }
    catch ( final NamingException ne )
    {
      throw new AssertionError( ne.getMessage() );
    }
  }

  /**
   * Verifies that non-Serializable and non-Referenceable objects cannot be
   * bound.
   *
   * @throws AssertionError if the test fails
   */
  public void testInvalidBind()
    throws AssertionError
  {
    try
    {
      m_context.bind( "invalid", new Object() );
      fail( "Expected bind of non-Serializable, non-Referenceable object to throw NamingException" );
    }
    catch ( final NamingException expected )
    {
    }
  }

  protected void setUp()
    throws Exception
  {
    _helper.setUp();
    super.setUp();
  }

  protected void tearDown()
    throws Exception
  {
    _helper.tearDown();
    super.tearDown();
  }

  protected Context getRoot()
    throws Exception
  {
    return _helper.getRoot();
  }
}
