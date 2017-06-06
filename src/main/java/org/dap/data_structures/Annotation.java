package org.dap.data_structures;

import java.io.Serializable;

import org.dap.common.DapAPI;
import org.dap.common.DapException;

/**
 * Annotation is a span of a document, with {@link AnnotationContents} attached to it.
 * 
 * <p>
 * {@link Annotation} is immutable, and is created by {@link Document#addAnnotation(int, int, AnnotationContents)}.
 * <br>
 * Each {@link Annotation} is unique, and cannot be shared among {@link Document}s. Note, however, that the
 * {@link AnnotationContents} <b>can</b> be shared among multiple {@link Annotation}s and multiple {@link Document}s.
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public final class Annotation<T extends AnnotationContents> implements UniquelyIdentifiedItem, Serializable
{
	private static final long serialVersionUID = 1750027436005437477L;
	
	/**
	 * Constructor with document into which the annotation is added, being-index, end-index, unique-id and annotation-contents.
	 * This constructor should be called by {@link Document}, but by nobody else. Not that this constructor is not part
	 * of the public-API.
	 * 
	 * @param document the document into which this annotation is added.
	 * @param begin begin index
	 * @param end end index
	 * @param uniqueId unique id within the document
	 * @param annotationContents the annotation-contents of this annotation.
	 */
	Annotation(Document document, int begin, int end, long uniqueId, T annotationContents)
	{
		super();
		if (null==document) {throw new DapException("Null document");}
		if (null==annotationContents) {throw new DapException("Null annotationContents");}
		if (end<begin) {throw new DapException("end < begin. begin="+begin+". end="+end+".");}
		this.document = document;
		this.begin = begin;
		this.end = end;
		this.uniqueId = uniqueId;
		this.annotationContents = annotationContents;
		this.coveredText = document.getText().substring(begin, end);
	}
	
	/**
	 * Get the document in which this annotation resides.
	 */
	@DapAPI
	public Document getDocument()
	{
		return document;
	}

	/**
	 * Get the begin-index (the index of the character where this annotation begins).
	 */
	@DapAPI
	public int getBegin()
	{
		return begin;
	}

	/**
	 * Get the end-index (the index of the character after this annotation).
	 */
	@DapAPI
	public int getEnd()
	{
		return end;
	}

	@Override
	public long getUniqueId()
	{
		return uniqueId;
	}

	/**
	 * Get the annotation-contents.
	 */
	@DapAPI
	public T getAnnotationContents()
	{
		return annotationContents;
	}
	
	/**
	 * Get an {@link AnnotationReference} that uniquely identifies this annotation in the context of the {@link DocumentCollection}
	 * that contains the {@link Document} that contains this annotation.
	 * this {@link AnnotationReference} can be used as the argument of {@link Document#findAnnotation(AnnotationReference)} and
	 * {@link DocumentCollection#findAnnotation(AnnotationReference)}.
	 */
	@DapAPI
	public AnnotationReference getAnnotationReference()
	{
		return new AnnotationReference(document.getName(), uniqueId);
	}
	
	/**
	 * Get the text covered (annotated) by this annotation.
	 */
	@DapAPI
	public String getCoveredText()
	{
		return coveredText;
	}




	private final Document document;
	private final int begin;
	private final int end;
	private final long uniqueId;
	private final T annotationContents;
	private final String coveredText;
}
