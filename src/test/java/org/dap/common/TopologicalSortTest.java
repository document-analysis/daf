package org.dap.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 *
 * <p>
 * Date: 2 Jun 2017
 * @author Asher Stern
 *
 */
public class TopologicalSortTest
{
	@Test
	public void simpleTest()
	{
		Map<Integer, Set<Integer>> items = new LinkedHashMap<>();
		addToMapSet(items, 1, 10);
		addToMapSet(items, 1, 100);
		addToMapSet(items, 2, 20);
		addToMapSet(items, 3, 30);
		addToMapSet(items, 3, 300);
		
		List<Integer> list = new TopologicalSort<>(items).sort();
		System.out.println(list);
		
		assertComesBefore(list, 10, 1);
		assertComesBefore(list, 100, 1);
		assertComesBefore(list, 20, 2);
		assertComesBefore(list, 30, 3);
		assertComesBefore(list, 300, 3);
		
		
	}
	
	@Test
	public void testComplicated()
	{
		Map<Integer, Set<Integer>> items = new LinkedHashMap<>();
		addToMapSet(items, 1, 10);
		addToMapSet(items, 1, 100);
		addToMapSet(items, 1, 2);
		addToMapSet(items, 1, 3);
		addToMapSet(items, 2, 20);
		addToMapSet(items, 3, 30);
		addToMapSet(items, 3, 300);
		addToMapSet(items, 3, 2);
		
		List<Integer> list = new TopologicalSort<>(items).sort();
		System.out.println(list);
		
		assertComesBefore(list, 10, 1);
		assertComesBefore(list, 100, 1);
		assertComesBefore(list, 20, 2);
		assertComesBefore(list, 30, 3);
		assertComesBefore(list, 300, 3);
		assertComesBefore(list, 3, 1);
		assertComesBefore(list, 2, 1);
		assertComesBefore(list, 2, 3);
	}
	
	@Test
	public void testNoDependencies()
	{
		Map<Integer, Set<Integer>> items = new LinkedHashMap<>();
		items.put(1, Collections.emptySet());
		items.put(2, Collections.emptySet());
		items.put(3, Collections.emptySet());
		
		List<Integer> list = new TopologicalSort<>(items).sort();
		System.out.println(list);

		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertTrue(list.contains(3));
		
		assertEquals(3, list.size());
	}
	
	private static <T> void assertComesBefore(List<T> list, T before, T after)
	{
		boolean beforeEncountered = false;
		boolean afterEncountered = false;
		for (T t : list)
		{
			if (t.equals(before))
			{
				beforeEncountered = true;
			}
			if (t.equals(after))
			{
				assertTrue(beforeEncountered);
				afterEncountered = true;
			}
		}
		assertTrue(beforeEncountered);
		assertTrue(afterEncountered);
	}
	
	private static <T> void addToMapSet(Map<T, Set<T>> map, T key, T value)
	{
		Set<T> set = map.get(key);
		if (null==set)
		{
			set = new LinkedHashSet<>();
			map.put(key, set);
		}
		set.add(value);
	}
}
