package org.daf.index;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

import org.daf.common.OneStepAheadIterator;

/**
 * 
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 * @param <T>
 */
public class BeginEndIterator<T> extends OneStepAheadIterator<BeginEndEncapsulation<T>>
{
	public BeginEndIterator(Class<?> cls, SortedMap<Integer, SortedMap<Integer, Set<T>>> map, int begin, int end)
	{
		super();
		this.cls = cls;
		this.map = map;
		this.begin = begin;
		this.end = end;
		initBeginIterator();
		itHasNext = true;
		init();
	}

	@Override
	protected void findNext()
	{
		while ( (itemIterator==null) || (!itemIterator.hasNext()) )
		{
			while ( (null==endIterator) || (!endIterator.hasNext()) )
			{
				if (!beginIterator.hasNext())
				{
					itHasNext = false;
					return;
				}
				else
				{
					currentBegin = beginIterator.next();
					endIterator = map.get(currentBegin).headMap(end).keySet().iterator();
				}
			}
			currentEnd = endIterator.next();
			itemIterator = map.get(currentBegin).get(currentEnd).iterator();
		}
		theNext = new BeginEndEncapsulation<T>(currentBegin, currentEnd, cls, itemIterator.next());
	}
	
	private void initBeginIterator()
	{
		beginIterator = map.tailMap(begin).keySet().iterator();
	}
	
	private final Class<?> cls;
	private final SortedMap<Integer, SortedMap<Integer, Set<T>>> map;
	private final int begin;
	private final int end;

	private Iterator<Integer> beginIterator = null;
	private int currentBegin = -1;
	private Iterator<Integer> endIterator = null;
	private int currentEnd = -1;
	private Iterator<T> itemIterator = null;
}
