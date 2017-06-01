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
 * Example code:<br>
 * <code>document.setFeature(LanguageFeature.NAME, new LanguageFeature("en"));</code>
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
