package org.dap.data_structures;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.dap.common.DapAPI;
import org.dap.common.DapException;
import org.dap.index.Index;

/**
 * Document is a data-structure that contains:
 * <ol>
 * <li>text</li>
 * <li>annotations</li>
 * <li>features</li>
 * </ol>
 * 
 * The most important functionality of {@link Document}s is to iterate over annotations in the document. For this, several
 * <code>iterator()</code> and <code>iterable()</code> methods are provided, which let the user to iterate over all the annotations,
 * or some of them.
 * 
 * <p>
 * <b>Documents must not change when iterating over annotations.</b>
 * Adding or removing annotations while iterating over the document's annotations is a logical error.
 * The result of such an operation is undefined.
 * It might result in some unexplained exception, or, even worse, wrong annotations returned from the iterator.
 * See the tutorial for more information.
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public final class Document implements Serializable, Iterable<Annotation<?>>
{
	private static final long serialVersionUID = -6556213855743551941L;
	
	/**
	 * Constructor with the document-name and the document-text.
	 * <br>
	 * Note that a default {@link DocumentCollection} will be created for this document.
	 * @param name document-name
	 * @param text document-text
	 */
	@DapAPI
	public Document(String name, String text)
	{
		this(name, new DocumentCollection(), text);
	}
	
	/**
	 * Constructor with the document-name, the {@link DocumentCollection} into which this document will be inserted,
	 * and the document-text.
	 * @param name document-name
	 * @param documentCollection the {@link DocumentCollection} into which this document will be inserted
	 * @param text document-text
	 */
	@DapAPI
	public Document(String name, DocumentCollection documentCollection, String text)
	{
		if (null==name) {throw new DapException("Null name");}
		if (null==documentCollection) {throw new DapException("Null documentCollection");}
		if (null==text) {throw new DapException("Null text");}
		
		synchronized(documentCollection)
		{
			this.name = name;
			this.documentCollection = documentCollection;
			this.documentCollection.addDocument(this.name, this);
		}
		
		this.text = text;
	}
	

	/**
	 * Get the document-name. The document-name uniquely identifies the document within its {@link DocumentCollection}.
	 */
	@DapAPI
	public String getName()
	{
		return name;
	}

	/**
	 * Get the {@link DocumentCollection} which this document belongs to.
	 */
	@DapAPI
	public DocumentCollection getDocumentCollection()
	{
		return documentCollection;
	}

	/**
	 * Get the text of this document.
	 */
	@DapAPI
	public String getText()
	{
		return text;
	}

	/**
	 * Set the given feature to the document.
	 * If a feature is already set for this name, it will be replaced.
	 * @param name the feature-name (within the scope of this document)
	 * @param feature the feature itself.
	 */
	@DapAPI
	public synchronized void setFeature(String name, Feature feature)
	{
		if (null==name) {throw new DapException("Null name");}
		if (null==feature) {throw new DapException("Null feature");}
		features.put(name, feature);
	}
	
	/**
	 * Remove the feature, mapped to the given name, from this document.
	 * @param name the feature-name
	 */
	@DapAPI
	public synchronized void removeFeature(String name)
	{
		if (null==name) {throw new DapException("Null name");}
		features.remove(name);
	}
	
	/**
	 * Get a map with all the features in this document.
	 */
	@DapAPI
	public synchronized Map<String, Feature> getFeatures()
	{
		return Collections.unmodifiableMap(features);
	}
	
	/**
	 * Add an annotation to the document. Annotate the text-span between the given begin and end indexes, and attach the given
	 * {@link AnnotationContents} to this newly-created annotation.
	 * @param begin the character-index specifying where the new annotation begins.
	 * @param end the character-index specifying where the new annotation ends, i.e., the first character the follows the annotation.
	 * @param annotationContents {@link AnnotationContents} to be attached to this newly-created annotation.
	 * @return An {@link AnnotationReference}, which uniquely identifies this annotation.
	 */
	@DapAPI
	public synchronized <T extends AnnotationContents> AnnotationReference addAnnotation(int begin, int end, T annotationContents)
	{
		if ((begin<0)||(end<0)||(end<begin)) {throw new DapException("Illegal begin / end value(s): begin="+begin+". end="+end+".");}
		if (end>text.length()) {throw new DapException("Illegal end value. end>text.length(). end="+end+". text.length()="+text.length()+".");}
		if (null==annotationContents) {throw new DapException("Null annotationContents");}
		
		final long uniqueId = nextUniqueId.getAndIncrement();
		Class<?> cls = annotationContents.getClass();
		Annotation<T> annotation = new Annotation<T>(this, begin, end, uniqueId, annotationContents);
		
		annotationsById.put(uniqueId, annotation);
		index.add(cls, begin, end, annotation);
		
		return new AnnotationReference(getName(), uniqueId);
	}
	
	@DapAPI
	public synchronized void removeAnnotation(AnnotationReference annotationReference)
	{
		if (null==annotationReference) {throw new DapException("Null annotation");}
		
		final Annotation<?> annotation = annotationsById.get(annotationReference.getAnnotationUniqueId());
		if (null==annotation) {throw new DapException("Tried to remove an annotation that does not exist.");}
		
		Class<?> cls = annotation.getAnnotationContents().getClass();
		index.remove(cls, annotation.getBegin(), annotation.getEnd(), annotation);
		annotationsById.remove(annotation.getUniqueId());
	}
	
	@DapAPI
	public synchronized void removeAnnotation(Annotation<?> annotation)
	{
		removeAnnotation(annotation.getAnnotationReference());
	}
	
	@DapAPI
	public synchronized boolean isAnnotationExist(AnnotationReference annotationReference)
	{
		return isAnnotationExist(annotationReference, false);
	}
	
	@DapAPI
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
	
	@DapAPI
	public synchronized Annotation<?> findAnnotation(AnnotationReference annotationReference)
	{
		return findAnnotation(annotationReference, false);
	}
	
	@DapAPI
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
				throw new DapException("Tried to find an annotation that does not exist: "+annotationReference);
			}
		}
		else
		{
			if (inThisDocumentOnly)
			{
				throw new DapException("Tried to find an annotation that does not exist: "+annotationReference);
			}
			else
			{
				return documentCollection.findAnnotation(annotationReference);
			}
		}
	}
	
	@DapAPI
	public Iterator<Annotation<? extends AnnotationContents>> iterator(Class<? extends AnnotationContents> superClass, int begin, int end)
	{
		if ((begin<0)||(end<0)||(end<begin)) {throw new DapException("Illegal begin / end value(s): begin="+begin+". end="+end+".");}
		if (null==superClass) {throw new DapException("Null superClass");}
		
		return index.iterator(superClass, begin, end);
	}
	
	@DapAPI
	public Iterator<Annotation<? extends AnnotationContents>> iterator(Class<? extends AnnotationContents> superClass, int begin)
	{
		return iterator(superClass, begin, text.length());
	}
	
	@DapAPI
	public Iterator<Annotation<? extends AnnotationContents>> iterator(Class<? extends AnnotationContents> superClass)
	{
		return iterator(superClass, 0, text.length());
	}
	
	@DapAPI
	@Override
	public Iterator<Annotation<? extends AnnotationContents>> iterator()
	{
		return iterator(AnnotationContents.class);
	}
	
	@DapAPI
	public Iterable<Annotation<? extends AnnotationContents>> iterable(Class<? extends AnnotationContents> superClass, int begin, int end)
	{
		return ()->iterator(superClass, begin, end);
	}
	
	@DapAPI
	public Iterable<Annotation<? extends AnnotationContents>> iterable(Class<? extends AnnotationContents> superClass, int begin)
	{
		return ()->iterator(superClass, begin);
	}
	
	@DapAPI
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
