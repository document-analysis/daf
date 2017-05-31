package org.daf.common;


/**
 * Indicates an error originated by either the Document Analysis Framework itself,
 * or a code that runs inside the framework (e.g., annotator implementation).  
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DafAPI
public class DafException extends RuntimeException
{
	private static final long serialVersionUID = -8385859689809061043L;

	@DafAPI
	public DafException()
	{
		super();
	}

	@DafAPI
	public DafException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@DafAPI
	public DafException(String message, Throwable cause)
	{
		super(message, cause);
	}

	@DafAPI
	public DafException(String message)
	{
		super(message);
	}

	@DafAPI
	public DafException(Throwable cause)
	{
		super(cause);
	}
}
