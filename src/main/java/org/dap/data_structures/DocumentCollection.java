package org.dap.data_structures;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dap.common.DapAPI;
import org.dap.common.DapException;

/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public final class DocumentCollection implements Serializable
{
	private static final long serialVersionUID = -5837775355471567286L;

	@DapAPI
	public DocumentCollection()
	{
	}
	
	@DapAPI
	public synchronized void addDocument(String name, Document document)
	{
		if (null==name) {throw new DapException("Null name");}
		if (null==document) {throw new DapException("Null document");}
		if (mapNameToDocument.containsKey(name)) {throw new DapException("A document named \""+name+"\" already exists in this document collection.");}
		mapNameToDocument.put(name, document);
	}

	@DapAPI
	public synchronized boolean contains(String name)
	{
		return mapNameToDocument.containsKey(name);
	}
	
	@DapAPI
	public synchronized Document getDocument(String name)
	{
		if (!mapNameToDocument.containsKey(name)) {throw new DapException("This document collection does not contain a document named: \""+name+"\".");}
		return mapNameToDocument.get(name);
	}
	
	@DapAPI
	public synchronized Map<String, Document> getMapNameToDocument()
	{
		return Collections.unmodifiableMap(mapNameToDocument);
	}
	
	@DapAPI
	public synchronized Annotation<?> findAnnotation(AnnotationReference annotationReference)
	{
		final String documentName = annotationReference.getDocumentName();
		if (!mapNameToDocument.containsKey(documentName)) {throw new DapException("Tried to find an annotation that does not exist: "+annotationReference);}
		return mapNameToDocument.get(documentName).findAnnotation(annotationReference, true);
	}
	
	@DapAPI
	public synchronized boolean isAnnotationExist(AnnotationReference annotationReference)
	{
		final String documentName = annotationReference.getDocumentName();
		if (!mapNameToDocument.containsKey(documentName)) {return false;}
		return mapNameToDocument.get(documentName).isAnnotationExist(annotationReference, true);
	}


	private final Map<String, Document> mapNameToDocument = new LinkedHashMap<>();
}
