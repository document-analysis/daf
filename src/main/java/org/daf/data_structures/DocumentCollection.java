package org.daf.data_structures;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.daf.common.DafAPI;
import org.daf.common.DafException;

/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public final class DocumentCollection implements Serializable
{
	private static final long serialVersionUID = -5837775355471567286L;

	@DafAPI
	public DocumentCollection()
	{
	}
	
	@DafAPI
	public synchronized void addDocument(String name, Document document)
	{
		if (null==name) {throw new DafException("Null name");}
		if (null==document) {throw new DafException("Null document");}
		if (mapNameToDocument.containsKey(name)) {throw new DafException("A document named \""+name+"\" already exists in this document collection.");}
		mapNameToDocument.put(name, document);
	}

	@DafAPI
	public synchronized boolean contains(String name)
	{
		return mapNameToDocument.containsKey(name);
	}
	
	@DafAPI
	public synchronized Document getDocument(String name)
	{
		if (!mapNameToDocument.containsKey(name)) {throw new DafException("This document collection does not contain a document named: \""+name+"\".");}
		return mapNameToDocument.get(name);
	}
	
	@DafAPI
	public synchronized Map<String, Document> getMapNameToDocument()
	{
		return Collections.unmodifiableMap(mapNameToDocument);
	}
	
	@DafAPI
	public synchronized Annotation<?> findAnnotation(AnnotationReference annotationReference)
	{
		final String documentName = annotationReference.getDocumentName();
		if (!mapNameToDocument.containsKey(documentName)) {throw new DafException("Tried to find an annotation that does not exist: "+annotationReference);}
		return mapNameToDocument.get(documentName).findAnnotation(annotationReference, true);
	}
	
	@DafAPI
	public synchronized boolean isAnnotationExist(AnnotationReference annotationReference)
	{
		final String documentName = annotationReference.getDocumentName();
		if (!mapNameToDocument.containsKey(documentName)) {return false;}
		return mapNameToDocument.get(documentName).isAnnotationExist(annotationReference, true);
	}


	private final Map<String, Document> mapNameToDocument = new LinkedHashMap<>();
}
