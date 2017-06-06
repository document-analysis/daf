package org.dap.utilities;

import java.util.Map;

import org.dap.common.DapAPI;
import org.dap.data_structures.Annotation;
import org.dap.data_structures.AnnotationContents;
import org.dap.data_structures.Document;
import org.dap.data_structures.DocumentCollection;
import org.dap.data_structures.Feature;

/**
 * Provides static methods to copy a document. Note that the copy is <i>shallow</i>, in the sense that the {@link AnnotationContents}
 * are not duplicated. The annotations in the new document hold the same {@link AnnotationContents} objects of the original document.
 * 
 *
 * <p>
 * Date: 6 Jun 2017
 * @author Asher Stern
 *
 */
@DapAPI
public class DocumentCopier
{
	/**
	 * Copy the given document.
	 * @param origin the source document to copy
	 * @param destinationName name of the newly created document
	 * @return the newly created document
	 */
	@DapAPI
	public static Document copy(Document origin, String destinationName)
	{
		return copy(origin, destinationName, null);
	}
	
	/**
	 * Copy the given document.
	 * @param origin the source document to copy
	 * @param destinationName name of the newly created document
	 * @param destinationDocumentCollection the {@link DocumentCollection} of the newly created document 
	 * @return the newly created document
	 */
	@DapAPI
	public static Document copy(Document origin, String destinationName, DocumentCollection destinationDocumentCollection)
	{
		Document destination = null;
		if (null==destinationDocumentCollection)
		{
			destination = new Document(destinationName, origin.getText());
		}
		else
		{
			destination = new Document(destinationName, destinationDocumentCollection, origin.getText());
		}
		
		for (Map.Entry<String, Feature> featureEntry : origin.getFeatures().entrySet())
		{
			destination.setFeature(featureEntry.getKey(), featureEntry.getValue());
		}
		for (Annotation<?> annotation : origin)
		{
			destination.addAnnotation(annotation.getBegin(), annotation.getEnd(), annotation.getAnnotationContents());
		}
		return destination;
	}
}
