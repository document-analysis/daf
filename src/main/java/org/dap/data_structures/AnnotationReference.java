package org.dap.data_structures;

import java.io.Serializable;

import org.dap.common.DapAPI;

/**
 * Instance of this class are objects that uniquely identify annotations. They are used to find annotations in the methods:
 * {@link Document#findAnnotation(AnnotationReference)} and {@link DocumentCollection#findAnnotation(AnnotationReference)}.
 * 
 * <p>
 * Note that non of the methods of this class are part of the public-API. Only the class itself.
 * 
 * <p>
 * Instances of this class are created and returned by {@link Document#addAnnotation(int, int, AnnotationContents)}, and
 * {@link Annotation#getAnnotationReference()}.
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
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
