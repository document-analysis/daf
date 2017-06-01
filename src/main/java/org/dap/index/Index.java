package org.dap.index;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dap.common.DapException;
import org.dap.data_structures.UniquelyIdentifiedItem;

/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 * @param <K>
 * @param <T>
 */
public class Index<T extends UniquelyIdentifiedItem>
{
	public synchronized void add(Class<?> cls, int begin, int end, T item)
	{
		SortedMap<Integer, SortedMap<Integer, Set<T>>> clsMap = map.get(cls);
		if (null==clsMap)
		{
			clsMap = new TreeMap<>();
			map.put(cls, clsMap);
		}
		SortedMap<Integer, Set<T>> beginMap = clsMap.get(begin);
		if (null==beginMap)
		{
			beginMap = new TreeMap<>();
			clsMap.put(begin, beginMap);
		}
		Set<T> items = beginMap.get(end);
		if (null==items)
		{
			items = new LinkedHashSet<>();
			beginMap.put(end, items);
		}
		items.add(item);
	}
	
	public synchronized void remove(Class<?> cls, int begin, int end, T item)
	{
		SortedMap<Integer, SortedMap<Integer, Set<T>>> clsMap = map.get(cls);
		if (null==clsMap) {throw new DapException("Tried to remove an item that does not exist.");}
		SortedMap<Integer, Set<T>> beginMap = clsMap.get(begin);
		if (null==beginMap) {throw new DapException("Tried to remove an item that does not exist.");}
		Set<T> items = beginMap.get(end);
		if (null==items) {throw new DapException("Tried to remove an item that does not exist.");}
		for (T anItem : items)
		{
			if (item.getUniqueId()==anItem.getUniqueId())
			{
				items.remove(anItem);
			}
		}
		
		if (items.isEmpty())
		{
			beginMap.remove(end);
		}
		if (beginMap.isEmpty())
		{
			clsMap.remove(begin);
		}
		if (clsMap.isEmpty())
		{
			map.remove(cls);
		}
	}
	
	public Iterator<T> iterator(Class<?> cls, int begin, int end)
	{
		final Set<Class<?>> classes = findAllKnownDescendantsAndSelf(cls);
		final Map<Class<?>, Iterator<BeginEndEncapsulation<T>>> iteratorMap = new LinkedHashMap<>();
		for (Class<?> actualCls : classes)
		{
			iteratorMap.put(actualCls, 
					new BeginEndIterator<>(actualCls, map.get(actualCls), begin, end)
					);
		}
		final IndexIterator<T> indexIterator = new IndexIterator<>(iteratorMap);
		
		return new Iterator<T>()
		{
			@Override
			public boolean hasNext()
			{
				return indexIterator.hasNext();
			}

			@Override
			public T next()
			{
				return indexIterator.next().getItem();
			}
		};
	}
	
	private Set<Class<?>> findAllKnownDescendantsAndSelf(Class<?> cls)
	{
		Set<Class<?>> set = new LinkedHashSet<>();
		for (Class<?> key : map.keySet())
		{
			if (cls.isAssignableFrom(key))
			{
				set.add(key);
			}
		}
		return set;
	}
	
	private final Map<Class<?>, SortedMap<Integer, SortedMap<Integer, Set<T>>>> map = new LinkedHashMap<>();
}
