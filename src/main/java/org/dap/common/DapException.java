package org.dap.common;


/**
 * Indicates an error originated by either the Document Analysis Platform itself,
 * or a code that runs inside the platform (e.g., annotator implementation).  
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public class DapException extends RuntimeException
{
	private static final long serialVersionUID = -8385859689809061043L;

	@DapAPI
	public DapException()
	{
		super();
	}

	@DapAPI
	public DapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@DapAPI
	public DapException(String message, Throwable cause)
	{
		super(message, cause);
	}

	@DapAPI
	public DapException(String message)
	{
		super(message);
	}

	@DapAPI
	public DapException(Throwable cause)
	{
		super(cause);
	}
}
