package org.daf.data_structures;

import java.io.Serializable;

import org.daf.common.DafException;

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
public final class Annotation<T extends AnnotationContents> implements Serializable
{
	private static final long serialVersionUID = 1750027436005437477L;
	
	Annotation(Document document, int begin, int end, long uniqueId, T annotationContents)
	{
		super();
		if (null==document) {throw new DafException("Null document");}
		if (null==annotationContents) {throw new DafException("Null annotationContents");}
		if (end<=begin) {throw new DafException("end <= begin. begin="+begin+". end="+end+".");}
		this.document = document;
		this.begin = begin;
		this.end = end;
		this.uniqueId = uniqueId;
		this.annotationContents = annotationContents;
	}
	
	
	public Document getDocument()
	{
		return document;
	}

	public int getBegin()
	{
		return begin;
	}

	public int getEnd()
	{
		return end;
	}

	public long getUniqueId()
	{
		return uniqueId;
	}

	public T getAnnotationContents()
	{
		return annotationContents;
	}
	
	
	private final Document document;
	private final int begin;
	private final int end;
	private final long uniqueId;
	private final T annotationContents;
}
