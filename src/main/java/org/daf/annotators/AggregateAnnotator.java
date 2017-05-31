package org.daf.annotators;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.daf.common.DafAPI;
import org.daf.common.TopologicalSort;
import org.daf.data_structures.Document;

/**
 * An annotator which encapsulates several underlying annotators.
 * The {@link #annotate(Document)} method delegates to the {@link Annotator#annotate(Document)} methods of each of them, sequentially.
 * 
 * <p>
 * Note: the underlying annotators must be {@link Annotator#close()}ed by the caller.
 * 
 *
 * <p>
 * Date: 31 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public class AggregateAnnotator extends Annotator
{
	/**
	 * Constructor with a sequential list of underlying annotators.
	 * @param annotators underlying annotators.
	 */
	@DafAPI
	public AggregateAnnotator(List<Annotator> annotators)
	{
		super();
		this.annotators = annotators;
	}
	
	/**
	 * Constructor which builds the list of the underlying annotators from the given map of annotators-with-dependencies.<br>
	 * The map's key-set should contain all the underlying annotators. Each annotator is mapped to a set of annotators on which
	 * it depends.
	 * <br>
	 * Given an annotator x, and a set of annotators on which it depends {y1, y2, y3 ...}, then upon calling the {@link #annotate(Document)} method,
	 * the annotate method of each of the {y1, y2, y3,...} annotators must run first, and only then x's annotate method can be called.
	 * <br>
	 * The map should represent a DAG (Directed Acyclic Graph). I.e., the annotators are the vertices, and the edges are the dependencies.
	 * 
	 * @param annotatorWithDependencies map whose keys are annotators, which are mapped to the annotators on which they depend.
	 */
	@DafAPI
	public AggregateAnnotator(Map<Annotator, Set<Annotator>> annotatorWithDependencies)
	{
		annotators = new TopologicalSort<>(annotatorWithDependencies).sort();
	}
	
	/**
	 * Process the given document, by delegating to the {@link Annotator#annotate(Document)} methods
	 * of each of the underlying annotators. 
	 */
	@DafAPI
	@Override
	public void annotate(Document document)
	{
		for (Annotator annotator : annotators)
		{
			annotator.annotate(document);
		}
	}


	private final List<Annotator> annotators;
}
