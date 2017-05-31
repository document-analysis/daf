package org.daf.data_structures;

import java.io.Serializable;

import org.daf.common.DafAPI;

/**
 * 
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public final class AnnotationReference implements Serializable
{
	private static final long serialVersionUID = 9219843096448580409L;
	
	public AnnotationReference(String documentName, long annotationUniqueId)
	{
		super();
		this.documentName = documentName;
		this.annotationUniqueId = annotationUniqueId;
	}
	
	
	public String getDocumentName()
	{
		return documentName;
	}
	public long getAnnotationUniqueId()
	{
		return annotationUniqueId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (annotationUniqueId ^ (annotationUniqueId >>> 32));
		result = prime * result + ((documentName == null) ? 0 : documentName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotationReference other = (AnnotationReference) obj;
		if (annotationUniqueId != other.annotationUniqueId)
			return false;
		if (documentName == null)
		{
			if (other.documentName != null)
				return false;
		}
		else if (!documentName.equals(other.documentName))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "AnnotationReference [documentName=" + documentName + ", annotationUniqueId=" + annotationUniqueId + "]";
	}


	private final String documentName;
	private final long annotationUniqueId;
}
