package com.video.domain;

public class Tag {
	private final String word;

	public Tag(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}

	@Override
	public boolean equals(Object o) {
	    if(o == null)
	    {
	        return false;
	    }
	    if (o == this)
	    {
	        return true;
	    }
	    if (getClass() != o.getClass())
	    {
	        return false;
	    }
	     
	    //
	    Tag t = (Tag) o;
	    return (this.getWord().equals(t.getWord()));
	}
	
	@Override
	public int hashCode()
	{
	    final int PRIME = 31;
	    int result = 5;
	    result = PRIME * result + this.getWord().hashCode();
	    return result;
	}
}
