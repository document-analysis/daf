package org.dap.data_structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.dap.common.DapException;
import org.junit.Test;

/**
 * 
 *
 * <p>
 * Date: 2 Jun 2017
 * @author Asher Stern
 *
 */
public class DocumentTest
{
	@Test
	public void testNameTextAndFeatures()
	{
		Document document = new Document("name1", "hello world");
		document.setFeature("feature1", new ExampleFeature("feature string 1"));
		
		assertEquals("name1", document.getName());
		assertEquals("hello world", document.getText());
		assertTrue(document.getFeatures().containsKey("feature1"));
		assertEquals(new ExampleFeature("feature string 1"), document.getFeatures().get("feature1"));
		
		document.removeFeature("feature1");
		assertFalse(document.getFeatures().containsKey("feature1"));
		
		assertNotNull(document.getDocumentCollection());
		assertEquals(document.getDocumentCollection(), document.getDocumentCollection());
	}
	
	@Test
	public void testAnnotation()
	{
		Document document = new Document("name1", "hello world");
		AnnotationReference annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		assertNotNull(annotationReference1);
		
		Annotation<?> annotation1 = document.findAnnotation(annotationReference1);
		assertEquals(new ExampleAnnotationContents(1), annotation1.getAnnotationContents());
		assertEquals(0, annotation1.getBegin());
		assertEquals(5, annotation1.getEnd());
		assertEquals(annotationReference1, annotation1.getAnnotationReference());
		assertEquals(document, annotation1.getDocument());
		assertEquals("hello", annotation1.getCoveredText());
	}
	
	@Test
	public void testIteration()
	{
		Document document = new Document("name1", "hello world");
		assertFalse(document.iterator().hasNext());
		AnnotationReference annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		boolean oneIterationFinish = false;
		for (Annotation<?> annotation1 : document)
		{
			assertFalse(oneIterationFinish);
			assertEquals(new ExampleAnnotationContents(1), annotation1.getAnnotationContents());
			assertEquals(0, annotation1.getBegin());
			assertEquals(5, annotation1.getEnd());
			assertEquals(annotationReference1, annotation1.getAnnotationReference());
			assertEquals(document, annotation1.getDocument());
			assertEquals("hello", annotation1.getCoveredText());
			oneIterationFinish = true;
		}
		
		AnnotationReference annotationReference2 = document.addAnnotation(0, 1, new ExampleAnnotationContents(2));
		assertNotEquals(annotationReference1, annotationReference2);
		assertEquals(2, countIterator(document.iterator()));
		
		// test the ordering
		Iterator<Annotation<?>> iterator = document.iterator();
		assertEquals(annotationReference2, iterator.next().getAnnotationReference());
		assertEquals(annotationReference1, iterator.next().getAnnotationReference());

		iterator = document.iterator();
		assertEquals(0, iterator.next().getBegin());
		assertEquals(0, iterator.next().getBegin());
		
		// test the ordering by end.
		iterator = document.iterator();
		assertEquals(1, iterator.next().getEnd());
		assertEquals(5, iterator.next().getEnd());
	}
	
	@Test
	public void testIterationByType()
	{
		Document document = new Document("name1", "hello world");
		AnnotationReference annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		AnnotationReference annotationReference2 = document.addAnnotation(0, 5, new ExampleAnnotationContents2("what","ever"));
		assertNotEquals(annotationReference1, annotationReference2);
		
		boolean oneIterationFinish = false;
		for (Annotation<?> annotation1 : document.iterable(ExampleAnnotationContents.class))
		{
			assertFalse(oneIterationFinish);
			assertEquals(new ExampleAnnotationContents(1), annotation1.getAnnotationContents());
			assertEquals(0, annotation1.getBegin());
			assertEquals(5, annotation1.getEnd());
			assertEquals(annotationReference1, annotation1.getAnnotationReference());
			assertEquals(document, annotation1.getDocument());
			assertEquals("hello", annotation1.getCoveredText());
			oneIterationFinish = true;
		}
		
		oneIterationFinish = false;
		for (Annotation<?> annotation2 : document.iterable(ExampleAnnotationContents2.class))
		{
			assertFalse(oneIterationFinish);
			assertEquals(new ExampleAnnotationContents2("what","ever"), annotation2.getAnnotationContents());
			assertEquals(0, annotation2.getBegin());
			assertEquals(5, annotation2.getEnd());
			assertEquals(annotationReference2, annotation2.getAnnotationReference());
			assertEquals(document, annotation2.getDocument());
			assertEquals("hello", annotation2.getCoveredText());
			oneIterationFinish = true;
		}
		
		assertEquals(2, countIterator(document.iterator()));
		assertEquals(2, countIterator(document.iterator(AnnotationContents.class)));
		
		AnnotationReference annotationReference3 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		assertEquals(2, countIterator(document.iterator(ExampleAnnotationContents.class)));
		for (Annotation<?> annotation : document.iterable(ExampleAnnotationContents.class))
		{
			assertTrue(
					annotationReference1.equals(annotation.getAnnotationReference())
					||
					annotationReference3.equals(annotation.getAnnotationReference())
					);
		}
		
		for (Annotation<?> annotation : document.iterable(ExampleAnnotationContents2.class))
		{
			assertEquals(annotationReference2, annotation.getAnnotationReference());
		}
	}
	
	@Test
	public void testIterationByIndexes()
	{
		Document document = new Document("name1", "hello world");
		AnnotationReference annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		AnnotationReference annotationReference2 = document.addAnnotation(6, 11, new ExampleAnnotationContents2("what","ever"));
		assertNotEquals(annotationReference1, annotationReference2);
		
		assertEquals(2, countIterator(document.iterator(AnnotationContents.class, 0, 11)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 0, 5)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 0, 7)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 0, 10)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 6, 11)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 3, 11)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 1, 11)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 0, 1)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 0, 4)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 1, 6)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 1, 10)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 7, 11)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 10, 11)));
		
		for (Annotation<?> annotation1 : document.iterable(AnnotationContents.class, 0, 5))
		{
			assertEquals(annotationReference1, annotation1.getAnnotationReference());
		}
		for (Annotation<?> annotation1 : document.iterable(AnnotationContents.class, 0, 10))
		{
			assertEquals(annotationReference1, annotation1.getAnnotationReference());
		}
		for (Annotation<?> annotation2 : document.iterable(AnnotationContents.class, 3, 11))
		{
			assertEquals(annotationReference2, annotation2.getAnnotationReference());
		}
		for (Annotation<?> annotation2 : document.iterable(AnnotationContents.class, 5, 11))
		{
			assertEquals(annotationReference2, annotation2.getAnnotationReference());
		}
		for (Annotation<?> annotation2 : document.iterable(AnnotationContents.class, 6, 11))
		{
			assertEquals(annotationReference2, annotation2.getAnnotationReference());
		}
		
		assertEquals(0, countIterator(document.iterator(ExampleAnnotationContents2.class, 0, 10)));
		assertEquals(0, countIterator(document.iterator(ExampleAnnotationContents.class, 1, 11)));
	}
	
	@Test
	public void testWrongAnnotations()
	{
		Document document = new Document("name1", "hello world");
		try
		{
			document.addAnnotation(0, 12, new ExampleAnnotationContents(1));
			fail("document was annotated with a wrong annotation");
		}
		catch(DapException e)
		{
			// OK
		}
		try
		{
			document.addAnnotation(-3, 5, new ExampleAnnotationContents(1));
			fail("document was annotated with a wrong annotation");
		}
		catch(DapException e)
		{
			// OK
		}
		try
		{
			document.addAnnotation(10, 9, new ExampleAnnotationContents(1));
			fail("document was annotated with a wrong annotation");
		}
		catch(DapException e)
		{
			// OK
		}
		
		assertEquals(0, countIterator(document.iterator()));
		
		// This one is OK. It should pass.
		document.addAnnotation(10, 10, new ExampleAnnotationContents(1));
		
		assertEquals(1, countIterator(document.iterator()));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 10, 10)));
		assertEquals(1, countIterator(document.iterator(AnnotationContents.class, 9, 10)));
		assertEquals(0, countIterator(document.iterator(AnnotationContents.class, 0, 9)));
		
		assertEquals(1, countIterator(document.iterator(ExampleAnnotationContents.class, 0, 11)));
		assertEquals(0, countIterator(document.iterator(ExampleAnnotationContents2.class, 0, 11)));
	}
	
	@Test
	public void testRemoveAnnotation()
	{
		Document document = new Document("name1", "hello world");
		AnnotationReference annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		AnnotationReference annotationReference2 = document.addAnnotation(6, 11, new ExampleAnnotationContents2("what","ever"));
		assertNotEquals(annotationReference1, annotationReference2);
		
		assertEquals(2, countIterator(document.iterator()));
		for (Annotation<?> annotation : document.iterable(ExampleAnnotationContents.class))
		{
			document.removeAnnotation(annotation);
		}
		assertEquals(1, countIterator(document.iterator()));
		assertEquals(1, countIterator(document.iterator(ExampleAnnotationContents2.class)));
		assertEquals(0, countIterator(document.iterator(ExampleAnnotationContents.class)));
		
		document.removeAnnotation(annotationReference2);
		assertEquals(0, countIterator(document.iterator()));
		
		annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		assertEquals(1, countIterator(document.iterator()));
		assertEquals(0, countIterator(document.iterator(ExampleAnnotationContents2.class)));
		assertEquals(1, countIterator(document.iterator(ExampleAnnotationContents.class)));
		
		document.removeAnnotation(annotationReference1);
		assertEquals(0, countIterator(document.iterator()));
	}
	
	@Test
	public void testFindAnnotation()
	{
		Document document = new Document("name1", "hello world");
		AnnotationReference annotationReference1 = document.addAnnotation(0, 5, new ExampleAnnotationContents(1));
		AnnotationReference annotationReference2 = document.addAnnotation(6, 11, new ExampleAnnotationContents2("what","ever"));
		assertNotEquals(annotationReference1, annotationReference2);

		assertTrue(document.isAnnotationExist(annotationReference1));
		assertTrue(document.isAnnotationExist(annotationReference2));
		
		Annotation<?> annotation = document.findAnnotation(annotationReference1);
		assertEquals(0, annotation.getBegin());
		assertEquals(5, annotation.getEnd());
		assertEquals(new ExampleAnnotationContents(1), annotation.getAnnotationContents());
		
		annotation = document.findAnnotation(annotationReference2, true);
		assertEquals("world", annotation.getCoveredText());
		
		document.removeAnnotation(annotationReference1);
		assertFalse(document.isAnnotationExist(annotationReference1));
		assertTrue(document.isAnnotationExist(annotationReference2));
		document.removeAnnotation(annotationReference2);
		assertFalse(document.isAnnotationExist(annotationReference2));
		
		try
		{
			document.findAnnotation(annotationReference1);
			fail("Exception was not thrown when trying to find an annotation which does not exist.");
		}
		catch(DapException e)
		{
			// all OK
		}
	}
			
	
	private static int countIterator(Iterator<?> iterator)
	{
		int size = 0;
		while (iterator.hasNext())
		{
			iterator.next();
			++size;
		}
		return size;
	}
}
