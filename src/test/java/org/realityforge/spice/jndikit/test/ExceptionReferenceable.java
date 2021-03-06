/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.test;

import javax.naming.Reference;
import javax.naming.Referenceable;

/**
 * Implementation of {@link Referenceable}, for testing purposes. When {@link
 * TestObjectFactory} encounters a {@link Reference} to this, it throws an
 * exception.
 *
 * @see TestObjectFactory
 */
public class ExceptionReferenceable
  implements Referenceable
{
  /**
   * Retrieves the Reference of this object.
   *
   * @return the non-null Reference of this object.
   */
  public Reference getReference()
  {
    return new Reference( getClass().getName(),
                          TestObjectFactory.class.getName(),
                          null );
  }
}
