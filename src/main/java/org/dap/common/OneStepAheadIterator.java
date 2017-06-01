package org.dap.common;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 * @param <T>
 */
public abstract class OneStepAheadIterator<T> implements Iterator<T>
{
	
	@Override
	public synchronized final boolean hasNext()
	{
		return itHasNext;
	}
	
	@Override
	public synchronized final T next()
	{
		if (!itHasNext) {throw new NoSuchElementException();}
		T ret = this.theNext;
		findNext();
		return ret;
	}
	
	/**
	 * This method must be called from the constructor.
	 */
	protected final void init()
	{
		findNext();
	}



	/**
	 * Either sets a new value to {@link #theNext}, and sets {@link #itHasNext} to true,
	 * or sets null to {@link #theNext}, and set {@link #itHasNext} to false.
	 */
	protected abstract void findNext();
	
	protected boolean itHasNext = false;
	protected T theNext = null;
}
