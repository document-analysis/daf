package org.dap.index;

import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import org.dap.common.OneStepAheadIterator;

/**
 * 
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 * @param <T>
 */
public class IndexIterator<T> extends OneStepAheadIterator<BeginEndEncapsulation<T>>
{
	public IndexIterator(Map<Class<?>, Iterator<BeginEndEncapsulation<T>>> iteratorMap)
	{
		super();
		this.iteratorMap = iteratorMap;
		initQueue();
		itHasNext = true;
		init();
	}
	
	@Override
	protected void findNext()
	{
		theNext = queue.poll();
		if (null==theNext)
		{
			itHasNext = false;
		}
		else
		{
			Iterator<BeginEndEncapsulation<T>> iteratorOfNext = iteratorMap.get(theNext.getCls());
			if (iteratorOfNext.hasNext())
			{
				queue.offer(iteratorOfNext.next());
			}
		}
	}
	
	private void initQueue()
	{
		queue = new PriorityQueue<>();
		for (Map.Entry<Class<?>, Iterator<BeginEndEncapsulation<T>>> entry : iteratorMap.entrySet())
		{
			Iterator<BeginEndEncapsulation<T>> iterator = entry.getValue();
			if (iterator.hasNext())
			{
				queue.offer(iterator.next());
			}
		}
	}


	private final Map<Class<?>, Iterator<BeginEndEncapsulation<T>>> iteratorMap;
	
	private PriorityQueue<BeginEndEncapsulation<T>> queue = null;
}
