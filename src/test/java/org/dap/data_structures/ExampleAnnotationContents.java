package org.dap.data_structures;

/**
 * 
 *
 * <p>
 * Date: 2 Jun 2017
 * @author Asher Stern
 *
 */
public class ExampleAnnotationContents extends AnnotationContents
{
	private static final long serialVersionUID = 8765044930301735246L;


	public ExampleAnnotationContents(int number)
	{
		super();
		this.number = number;
	}
	
	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExampleAnnotationContents other = (ExampleAnnotationContents) obj;
		if (number != other.number)
			return false;
		return true;
	}


	private int number;
}
