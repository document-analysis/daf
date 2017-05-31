package org.daf.annotators.split_merge;

import org.daf.common.DafAPI;
import org.daf.data_structures.Document;

/**
 * Constructs a new {@link Merger} for each document.
 * The typical use-case of {@link MergerFactory} is in a split-and-merge scenario, implemented in {@link SplitMergeAnnotator}.
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public abstract class MergerFactory implements AutoCloseable
{
	/**
	 * Construct a new {@link Merger} for the given base-document
	 * (Base-document denotes the document provided to a {@link Merger} in its constructor).
	 * @param document a base-document to process.
	 * @return a new {@link Merger} for the given base-document.
	 */
	@DafAPI
	public abstract Merger createMerger(Document document);
}
