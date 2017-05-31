package org.daf.data_structures;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.daf.common.DafAPI;
import org.daf.common.DafException;
import org.daf.index.Index;

/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public final class Document implements Serializable, Iterable<Annotation<?>>
{
	private static final long serialVersionUID = -6556213855743551941L;
	
	@DafAPI
	public Document(String name, String text)
	{
		this(name, new DocumentCollection(), text);
	}
	
	@DafAPI
	public Document(String name, DocumentCollection documentCollection, String text)
	{
		if (null==name) {throw new DafException("Null name");}
		if (null==documentCollection) {throw new DafException("Null documentCollection");}
		if (null==text) {throw new DafException("Null text");}
		
		synchronized(documentCollection)
		{
			this.name = name;
			this.documentCollection = documentCollection;
			this.documentCollection.addDocument(this.name, this);
		}
		
		this.text = text;
	}
	

	@DafAPI
	public String getName()
	{
		return name;
	}

	@DafAPI
	public DocumentCollection getDocumentCollection()
	{
		return documentCollection;
	}
	
	@DafAPI
	public String getText()
	{
		return text;
	}

	@DafAPI
	public synchronized void setFeature(String name, Feature feature)
	{
		if (null==name) {throw new DafException("Null name");}
		if (null==feature) {throw new DafException("Null feature");}
		features.put(name, feature);
	}
	
	@DafAPI
	public synchronized void removeFeature(String name)
	{
		if (null==name) {throw new DafException("Null name");}
		features.remove(name);
	}
	
	@DafAPI
	public synchronized Map<String, Feature> getFeatures()
	{
		return Collections.unmodifiableMap(features);
	}
	
	@DafAPI
	public synchronized <T extends AnnotationContents> AnnotationReference addAnnotation(int begin, int end, T annotationContents)
	{
		if ((begin<0)||(end<0)||(end<begin)) {throw new DafException("Illegal begin / end value(s): begin="+begin+". end="+end+".");}
		if (end>text.length()) {throw new DafException("Illegal end value. end>text.length(). end="+end+". text.length()="+text.length()+".");}
		if (null==annotationContents) {throw new DafException("Null annotationContents");}
		
		final long uniqueId = nextUniqueId.getAndIncrement();
		Class<?> cls = annotationContents.getClass();
		Annotation<T> annotation = new Annotation<T>(this, begin, end, uniqueId, annotationContents);
		
		annotationsById.put(uniqueId, annotation);
		index.add(cls, begin, end, annotation);
		
		return new AnnotationReference(getName(), uniqueId);
	}
	
	@DafAPI
	public synchronized void removeAnnotation(AnnotationReference annotationReference)
	{
		if (null==annotationReference) {throw new DafException("Null annotation");}
		
		final Annotation<?> annotation = annotationsById.get(annotationReference.getAnnotationUniqueId());
		if (null==annotation) {throw new DafException("Tried to remove an annotation that does not exist.");}
		
		Class<?> cls = annotation.getAnnotationContents().getClass();
		index.remove(cls, annotation.getBegin(), annotation.getEnd(), annotation);
		annotationsById.remove(annotation.getUniqueId());
	}
	
	@DafAPI
	public synchronized void removeAnnotation(Annotation<?> annotation)
	{
		removeAnnotation(annotation.getAnnotationReference());
	}
	
	@DafAPI
	public synchronized boolean isAnnotationExist(AnnotationReference annotationReference)
	{
		return isAnnotationExist(annotationReference, false);
	}
	
	@DafAPI
	public synchronized boolean isAnnotationExist(AnnotationReference annotationReference, boolean inThisDocumentOnly)
	{
		if (name.equals(annotationReference.getDocumentName()))
		{
			return annotationsById.containsKey(annotationReference.getAnnotationUniqueId());
		}
		else
		{
			if (inThisDocumentOnly)
			{
				return false;
			}
			else
			{
				return documentCollection.isAnnotationExist(annotationReference);
			}
		}
	}
	
	@DafAPI
	public synchronized Annotation<?> findAnnotation(AnnotationReference annotationReference)
	{
		return findAnnotation(annotationReference, false);
	}
	
	@DafAPI
	public synchronized Annotation<?> findAnnotation(AnnotationReference annotationReference, boolean inThisDocumentOnly)
	{
		if (name.equals(annotationReference.getDocumentName()))
		{
			if (annotationsById.containsKey(annotationReference.getAnnotationUniqueId()))
			{
				return annotationsById.get(annotationReference.getAnnotationUniqueId());
			}
			else
			{
				throw new DafException("Tried to find an annotation that does not exist: "+annotationReference);
			}
		}
		else
		{
			if (inThisDocumentOnly)
			{
				throw new DafException("Tried to find an annotation that does not exist: "+annotationReference);
			}
			else
			{
				return documentCollection.findAnnotation(annotationReference);
			}
		}
	}
	
	@DafAPI
	public Iterator<Annotation<? extends AnnotationContents>> iterator(Class<? extends AnnotationContents> superClass, int begin, int end)
	{
		if ((begin<0)||(end<0)||(end<begin)) {throw new DafException("Illegal begin / end value(s): begin="+begin+". end="+end+".");}
		if (null==superClass) {throw new DafException("Null superClass");}
		
		return index.iterator(superClass, begin, end);
	}
	
	@DafAPI
	public Iterator<Annotation<? extends AnnotationContents>> iterator(Class<? extends AnnotationContents> superClass, int begin)
	{
		return iterator(superClass, begin, text.length());
	}
	
	@DafAPI
	public Iterator<Annotation<? extends AnnotationContents>> iterator(Class<? extends AnnotationContents> superClass)
	{
		return iterator(superClass, 0, text.length());
	}
	
	@DafAPI
	@Override
	public Iterator<Annotation<? extends AnnotationContents>> iterator()
	{
		return iterator(AnnotationContents.class);
	}
	
	@DafAPI
	public Iterable<Annotation<? extends AnnotationContents>> iterable(Class<? extends AnnotationContents> superClass, int begin, int end)
	{
		return ()->iterator(superClass, begin, end);
	}
	
	@DafAPI
	public Iterable<Annotation<? extends AnnotationContents>> iterable(Class<? extends AnnotationContents> superClass, int begin)
	{
		return ()->iterator(superClass, begin);
	}
	
	@DafAPI
	public Iterable<Annotation<? extends AnnotationContents>> iterable(Class<? extends AnnotationContents> superClass)
	{
		return ()->iterator(superClass);
	}
	
	
	private final String name;
	private final DocumentCollection documentCollection;
	private final String text;
	
	private final Map<String, Feature> features = new LinkedHashMap<>();
	private final Index<Annotation<?>> index = new Index<>();
	private final Map<Long, Annotation<?>> annotationsById = new LinkedHashMap<>();
	private final AtomicLong nextUniqueId = new AtomicLong(1L);
}
