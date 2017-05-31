package org.daf.annotators;

import org.daf.common.DafAPI;
import org.daf.data_structures.Document;

/**
 * Superclass for all annotators.
 * <br>
 * An annotator gets a document and performs some processing on that document.
 * Typically, that processing would be adding annotations to the document.
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public class Annotator implements AutoCloseable
{
	/**
	 * Perform some processing on the given document.
	 * @param document the document to process.
	 */
	@DafAPI
	public void annotate(Document document)
	{
		// Override this method.
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@DafAPI
	@Override
	public void close() 
	{
		// Override if necessary.
	}
}
