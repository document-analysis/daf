package org.dap.common;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * 
 *
 * <p>
 * Date: 2 Jun 2017
 * @author Asher Stern
 *
 */
public class OneStepAheadIteratorTest
{
	@Test
	public void test()
	{
		List<Integer> list = Arrays.asList(new Integer[]{1,2,3,4,5});
		
		Iterator<Integer> iterator = list.iterator();
		Iterator<Integer> testIterator = new TestIterator<>(list.iterator());
		
		while (iterator.hasNext())
		{
			assertTrue(testIterator.hasNext());
			assertEquals(iterator.next(), testIterator.next());
		}
		assertFalse(testIterator.hasNext());
	}

	private static class TestIterator<T> extends OneStepAheadIterator<T>
	{
		public TestIterator(Iterator<T> underlyingIterator)
		{
			super();
			this.underlyingIterator = underlyingIterator;
			init();
		}

		@Override
		protected void findNext()
		{
			if (underlyingIterator.hasNext())
			{
				this.itHasNext = true;
				this.theNext = underlyingIterator.next();
			}
			else
			{
				this.itHasNext = false;
			}
		}
		
		private final Iterator<T> underlyingIterator;
	}
}
