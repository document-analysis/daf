package org.daf.index;

/**
 * 
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 * @param <T>
 */
public class BeginEndEncapsulation<T> implements Comparable<BeginEndEncapsulation<T>>
{
	public BeginEndEncapsulation(int begin, int end, Class<?> cls, T item)
	{
		super();
		this.begin = begin;
		this.end = end;
		this.cls = cls;
		this.item = item;
	}
	
	
	public int getBegin()
	{
		return begin;
	}
	public int getEnd()
	{
		return end;
	}
	public Class<?> getCls()
	{
		return cls;
	}
	public T getItem()
	{
		return item;
	}


	@Override
	public int compareTo(BeginEndEncapsulation<T> o)
	{
		int beginCompare = Integer.compare(begin, o.begin);
		if (beginCompare!=0) {return beginCompare;}
		return Integer.compare(end, o.end);
	}

	
	private final int begin;
	private final int end;
	private final Class<?> cls;
	private final T item;
}
