package org.dap.annotators.split_merge;

import java.util.Iterator;

import org.dap.common.DapAPI;
import org.dap.data_structures.Document;

/**
 * Generates new documents from a given document.
 * The typical usage of {@link Splitter} in in a split-and-merge flow, implemented by {@link SplitMergeAnnotator}.
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public abstract class Splitter implements AutoCloseable
{
	/**
	 * Generates new documents from the given document, and returns an iterator over them.
	 * @param document the given document
	 * @return iterator over the newly-generated documents.
	 */
	@DapAPI
	public abstract Iterator<Document> split(Document document);
}
