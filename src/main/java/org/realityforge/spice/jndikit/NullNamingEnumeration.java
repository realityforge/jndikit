package org.realityforge.spice.jndikit;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.NoSuchElementException;

final class NullNamingEnumeration<T>
  implements NamingEnumeration<T>
{
    @Override
    public T next()
    {
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasMore()
    {
        return false;
    }

    @Override
    public void close()
    {
    }

    @Override
    public boolean hasMoreElements()
    {
        return false;
    }

    @Override
    public T nextElement()
    {
        throw new NoSuchElementException();
    }
}
