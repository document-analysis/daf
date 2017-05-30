package org.daf.index;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
public class Index<T>
{
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

	private final Map<Class<?>, Map<Integer, Map<Integer, T>>> map = new LinkedHashMap<>();
}
