package org.dap.common;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
public class TopologicalSort<T>
{
	public TopologicalSort(Map<T, Set<T>> items)
	{
		super();
		this.items = items;
	}

	public List<T> sort()
	{
		insertedSoFar = new LinkedHashSet<>();
		sortedList = new LinkedList<>();
		for (T item : items.keySet())
		{
			insertItemToStack(item);
		}
		return sortedList;
	}
	
	private void insertItemToStack(T item)
	{
		if (!insertedSoFar.contains(item))
		{
			Set<T> mustComeBefore = items.get(item);
			if (mustComeBefore!=null)
			{
				for (T itemMustComeBefore : mustComeBefore)
				{
					insertItemToStack(itemMustComeBefore);
				}
			}
		}
		if (!insertedSoFar.contains(item))
		{
			sortedList.add(item);
			insertedSoFar.add(item);
		}
	}

	private final Map<T, Set<T>> items;
	private Set<T> insertedSoFar = null;
	private List<T> sortedList = null;
}
