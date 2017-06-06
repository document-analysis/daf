package org.dap.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.dap.data_structures.Annotation;
import org.dap.data_structures.AnnotationReference;
import org.dap.data_structures.Document;
import org.dap.data_structures.DocumentCollection;
import org.dap.data_structures.ExampleAnnotationContents;
import org.dap.data_structures.ExampleFeature;
import org.junit.Test;

/**
 * 
 *
 * <p>
 * Date: 6 Jun 2017
 * @author Asher Stern
 *
 */
public class DocumentCopierTest
{
	@Test
	public void test()
	{
		Document document = new Document("name", "abc def ghi jkl");
		document.addAnnotation(0, 3, new ExampleAnnotationContents(1));
		AnnotationReference reference2 = document.addAnnotation(3, 4, new ExampleAnnotationContents(-1));
		document.addAnnotation(0, 15, new ExampleAnnotationContents(10));
		document.setFeature("f1", new ExampleFeature("f1"));
		document.setFeature("f2", new ExampleFeature("f2"));
		document.setFeature("f3", new ExampleFeature("f3"));
		document.removeFeature("f2");
		document.removeAnnotation(reference2);
		
		Document destination = DocumentCopier.copy(document, "destination");
		assertEquals("destination", destination.getName());
		validate(document, destination);
		
		destination = DocumentCopier.copy(document, "destination", new DocumentCollection());
		assertEquals("destination", destination.getName());
		validate(document, destination);
	}
	
	public void validate(Document origin, Document destination)
	{
		assertEquals(origin.getText(), destination.getText());
		assertEquals(origin.getFeatures(), destination.getFeatures());
		Iterator<Annotation<?>> iteratorOrigin = origin.iterator();
		Iterator<Annotation<?>> iteratorDestination = destination.iterator();
		while (iteratorOrigin.hasNext() && iteratorDestination.hasNext())
		{
			Annotation<?> annotationOrigin = iteratorOrigin.next();
			Annotation<?> annotationDestination = iteratorDestination.next();
			assertEquals(annotationOrigin.getBegin(), annotationDestination.getBegin());
			assertEquals(annotationOrigin.getEnd(), annotationDestination.getEnd());
			assertEquals(annotationOrigin.getCoveredText(), annotationDestination.getCoveredText());
			assertTrue(annotationOrigin.getAnnotationContents()==annotationDestination.getAnnotationContents());
		}
		assertFalse(iteratorOrigin.hasNext());
		assertFalse(iteratorDestination.hasNext());
	}

}
