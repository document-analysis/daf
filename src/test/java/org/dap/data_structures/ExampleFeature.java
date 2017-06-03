package org.dap.data_structures;

/**
 * 
 *
 * <p>
 * Date: 2 Jun 2017
 * @author Asher Stern
 *
 */
public class ExampleFeature implements Feature
{
	private static final long serialVersionUID = -3582735171943962059L;


	public ExampleFeature(String str)
	{
		super();
		this.str = str;
	}
	
	public String getStr()
	{
		return str;
	}

	public void setStr(String str)
	{
		this.str = str;
	}
	
	

	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((str == null) ? 0 : str.hashCode());
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
		ExampleFeature other = (ExampleFeature) obj;
		if (str == null)
		{
			if (other.str != null)
				return false;
		}
		else if (!str.equals(other.str))
			return false;
		return true;
	}




	private String str;
}
