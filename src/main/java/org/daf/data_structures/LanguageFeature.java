package org.daf.data_structures;

import org.daf.common.DafAPI;
import org.daf.common.DafException;

/**
 * The language-feature indicates the language of the document (e.g., English, French, etc.).
 * 
 * <p>
 * The usage of a language-feature is expected to be very common, so the framework provides this
 * built-in implementation.
 * <br>
 * Users are encouraged to use this implementation, and not develop their own custom implementation
 * for language-feature.
 * <br>
 * Also, it is strongly encouraged to use the feature name {@link #NAME} when setting this feature
 * in a {@link Document}, in {@link Document#setFeature(String, Feature)}.
 * 
 * <p>
 * Example code:<br>
 * <code>document.setFeature(LanguageFeature.NAME, new LanguageFeature("en"));</code>
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public class LanguageFeature implements Feature
{
	private static final long serialVersionUID = 1631499711285682349L;
	
	public static final String NAME = "languague";

	@DafAPI
	public LanguageFeature(String language)
	{
		super();
		if (null==language) {throw new DafException("Null language");}
		this.language = language;
	}
	
	@DafAPI
	public String getLanguage()
	{
		return language;
	}

	private final String language;
}
