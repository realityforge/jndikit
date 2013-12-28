/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.realityforge.spice.jndikit;

import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Abstract JNDI Context that can be inherited from to provide a particular type of Context.
 */
public abstract class AbstractContext
  implements Context
{
  private Hashtable<String, Object> m_environment;

  public AbstractContext()
  {
    this( new Hashtable<String, Object>() );
  }

  public AbstractContext( final Hashtable<String, Object> environment )
  {
    m_environment = environment;
  }

  protected abstract NameParser getNameParser()
    throws NamingException;

  /**
   * {@inheritDoc}
   */
  public Object addToEnvironment( final String key, final Object value )
    throws NamingException
  {
    if ( null == m_environment )
    {
      m_environment = new Hashtable<String, Object>( 5, 0.75f );
    }
    return m_environment.put( key, value );
  }

  /**
   * {@inheritDoc}
   */
  public void close()
  {
    m_environment = null;
  }

  protected boolean isSelf( final Name name )
  {
    return ( name.isEmpty() || name.get( 0 ).equals( "" ) );
  }

  /**
   * {@inheritDoc}
   */
  public void bind( final String name, final Object object )
    throws NamingException
  {
    bind( getNameParser().parse( name ), object );
  }

  /**
   * {@inheritDoc}
   */
  public void bind( final Name name, final Object object )
    throws NamingException
  {
    bind( name, object, false );
  }

  protected abstract void bind( Name name, Object object, boolean rebind )
    throws NamingException;

  /**
   * {@inheritDoc}
   */
  public String composeName( final String name, final String prefix )
    throws NamingException
  {
    final NameParser nameParser = getNameParser();
    final Name result =
      composeName( nameParser.parse( name ), nameParser.parse( prefix ) );
    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  public Name composeName( final Name name, final Name prefix )
    throws NamingException
  {
    final Name result = (Name) ( prefix.clone() );
    result.addAll( name );
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public Context createSubcontext( final String name )
    throws NamingException
  {
    return createSubcontext( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public void destroySubcontext( final String name )
    throws NamingException
  {
    destroySubcontext( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public Hashtable getEnvironment()
    throws NamingException
  {
    if ( null == m_environment )
    {
      return new Hashtable( 3, 0.75f );
    }
    else
    {
      return (Hashtable) m_environment.clone();
    }
  }

  /**
   * {@inheritDoc}
   */
  public NameParser getNameParser( final String name )
    throws NamingException
  {
    return getNameParser( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public NameParser getNameParser( final Name name )
    throws NamingException
  {
    if ( name.isEmpty() )
    {
      return getNameParser();
    }

    Object object = lookup( name );
    if ( !( object instanceof Context ) )
    {
      object = lookup( getPathName( name ) );
    }

    final Context context = (Context) object;
    final NameParser parser = context.getNameParser( "" );
    context.close();
    return parser;
  }

  /**
   * {@inheritDoc}
   */
  public NamingEnumeration<NameClassPair> list( final String name )
    throws NamingException
  {
    return list( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public NamingEnumeration<Binding> listBindings( final String name )
    throws NamingException
  {
    return listBindings( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public Object lookup( final String name )
    throws NamingException
  {
    return lookup( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public Object lookupLink( final String name )
    throws NamingException
  {
    return lookupLink( getNameParser().parse( name ) );
  }

  /**
   * {@inheritDoc}
   */
  public Object lookupLink( final Name name )
    throws NamingException
  {
    return lookup( name );
  }

  /**
   * {@inheritDoc}
   */
  public void rebind( final String name, final Object object )
    throws NamingException
  {
    rebind( getNameParser().parse( name ), object );
  }

  /**
   * {@inheritDoc}
   */
  public void rebind( final Name name, final Object object )
    throws NamingException
  {
    bind( name, object, true );
  }

  /**
   * {@inheritDoc}
   */
  public Object removeFromEnvironment( final String key )
    throws NamingException
  {
    if ( null == m_environment )
    {
      return null;
    }
    return m_environment.remove( key );
  }

  /**
   * {@inheritDoc}
   */
  public void rename( final String oldName, final String newName )
    throws NamingException
  {
    rename( getNameParser().parse( oldName ), getNameParser().parse( newName ) );
  }

  /**
   * {@inheritDoc}
   */
  public void rename( final Name oldName, final Name newName )
    throws NamingException
  {
    if ( isSelf( oldName ) || isSelf( newName ) )
    {
      final String message = "Failed to rebind self";
      throw new InvalidNameException( message );
    }
    else if ( oldName.equals( newName ) )
    {
      final String message = "Failed to rebind identical names";
      throw new InvalidNameException( message );
    }

    bind( newName, lookup( oldName ) );
    unbind( oldName );
  }

  /**
   * {@inheritDoc}
   */
  public void unbind( final String name )
    throws NamingException
  {
    unbind( getNameParser().parse( name ) );
  }

  /**
   * Utility method to retrieve raw environment value.
   * This means that null will be returned if the value is null.
   *
   * @return the environment map or null
   */
  protected final Hashtable<String, Object> getRawEnvironment()
  {
    return m_environment;
  }

  /**
   * Get name components minus leaf name component.
   *
   * @param name the name elements leading up to last element
   * @return the name
   * @throws javax.naming.NamingException if an error occurs
   */
  protected Name getPathName( final Name name )
    throws NamingException
  {
    return name.getPrefix( name.size() - 1 );
  }

  /**
   * Get leaf name component from specified Name object.
   *
   * @param name the name to retrieve leaf from
   * @return the leaf name component
   * @throws javax.naming.NamingException if an error occurs
   */
  protected Name getLeafName( final Name name )
    throws NamingException
  {
    return name.getSuffix( name.size() - 1 );
  }
}
