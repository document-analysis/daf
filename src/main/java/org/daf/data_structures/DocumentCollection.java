package org.daf.data_structures;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.daf.common.DafException;

/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
public final class DocumentCollection implements Serializable
{
	private static final long serialVersionUID = -5837775355471567286L;

	public DocumentCollection()
	{
	}
	
	public void addDocument(String name, Document document)
	{
		if (null==name) {throw new DafException("Null name");}
		if (null==document) {throw new DafException("Null document");}
		if (mapNameToDocument.containsKey(name)) {throw new DafException("A document named \""+name+"\" already exists in this document collection.");}
		mapNameToDocument.put(name, document);
	}

	public boolean contains(String name)
	{
		return mapNameToDocument.containsKey(name);
	}
	
	public Document getDocument(String name)
	{
		if (!mapNameToDocument.containsKey(name)) {throw new DafException("This document collection does not contain a document named: \""+name+"\".");}
		return mapNameToDocument.get(name);
	}

	private final Map<String, Document> mapNameToDocument = new LinkedHashMap<>();
}
