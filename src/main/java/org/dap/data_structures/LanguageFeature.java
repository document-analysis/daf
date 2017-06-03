package org.dap.data_structures;

import org.dap.common.DapAPI;
import org.dap.common.DapException;

/**
 * The language-feature indicates the language of the document (e.g., English, French, etc.).
 * 
 * <p>
 * The usage of a language-feature is expected to be very common, so the platform provides this
 * built-in implementation.
 * <br>
 * Users are encouraged to use this implementation, and not develop their own custom implementation
 * for language-feature.
 * <br>
 * Also, it is strongly encouraged to use the feature name {@link #NAME} when setting this feature
 * in a {@link Document}, in {@link Document#setFeature(String, Feature)}.
 * 
 * <p>
 * Convenient methods to easily set and get the document-language are provided here by the public static
 * methods: {@link #setDocumentLanguage(Document, String)} and {@link #getDocumentLanguage(Document)}. 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public class LanguageFeature implements Feature
{
	private static final long serialVersionUID = 1631499711285682349L;
	
	public static final String NAME = "languague";

	/**
	 * Convenient method to get the document language.
	 * @param document a document
	 * @return the document language, if it has been set, or null.
	 */
	@DapAPI
	public static String getDocumentLanguage(Document document)
	{
		Feature feature = document.getFeatures().get(NAME);
		if ( (null==feature) || (!(feature instanceof LanguageFeature)) )
		{
			return null;
		}
		return ((LanguageFeature)feature).getLanguage();
	}
	
	/**
	 * Convenient method to set the document language.
	 * @param document a document
	 * @param language the language to set to this document.
	 */
	@DapAPI
	public static void setDocumentLanguage(Document document, String language)
	{
		document.setFeature(NAME, new LanguageFeature(language));
	}
	
	@DapAPI
	public LanguageFeature(String language)
	{
		super();
		if (null==language) {throw new DapException("Null language");}
		this.language = language;
	}
	
	@DapAPI
	public String getLanguage()
	{
		return language;
	}

	private final String language;
}
