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
	}
	
	@DapAPI
	public Document getDocument()
	{
		return document;
	}

	@DapAPI
	public int getBegin()
	{
		return begin;
	}

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

	@DapAPI
	public T getAnnotationContents()
	{
		return annotationContents;
	}
	
	@DapAPI
	public AnnotationReference getAnnotationReference()
	{
		return new AnnotationReference(document.getName(), uniqueId);
	}
	
	
	private final Document document;
	private final int begin;
	private final int end;
	private final long uniqueId;
	private final T annotationContents;
}
