package org.daf.data_structures;

import java.io.Serializable;

import org.daf.common.DafException;

/**
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
public final class Document implements Serializable
{
	public Document(String name)
	{
		this(name, new DocumentCollection());
	}
	
	public Document(String name, DocumentCollection documentCollection)
	{
		if (null==name) {throw new DafException("Null name");}
		if (null==documentCollection) {throw new DafException("Null documentCollection");}
		
		synchronized(documentCollection)
		{
			this.name = name;
			this.documentCollection = documentCollection;
			this.documentCollection.addDocument(this.name, this);
		}
	}
	

	public DocumentCollection getDocumentCollection()
	{
		return documentCollection;
	}



	private final String name;
	private final DocumentCollection documentCollection;
}
