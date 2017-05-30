package org.daf.common;


/**
 * Indicates an error originated by the Document Analysis Framework 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
public class DafException extends RuntimeException
{
	private static final long serialVersionUID = -8385859689809061043L;

	public DafException()
	{
		super();
	}

	public DafException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DafException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DafException(String message)
	{
		super(message);
	}

	public DafException(Throwable cause)
	{
		super(cause);
	}
}
