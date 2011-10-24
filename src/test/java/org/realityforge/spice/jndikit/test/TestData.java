/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit.test;

import java.io.Serializable;
import javax.naming.Context;

/**
 * Helper class for testing behaviour of {@link Context#bind} operations.
 */
public class TestData
  implements Serializable
{

  /**
   * Arbitrary test value.
   */
  private String _value;


  /**
   * Default ctor for serialization.
   */
  public TestData()
  {
  }

  /**
   * Construct a new <code>TestData</code>.
   *
   * @param value test data
   */
  public TestData( String value )
  {
    _value = value;
  }

  /**
   * Sets the value of this.
   *
   * @param value the value
   */
  public void setValue( String value )
  {
    _value = value;
  }

  /**
   * Returns the value of this.
   *
   * @return the value of this, assigned during construction
   */
  public String getValue()
  {
    return _value;
  }

}
