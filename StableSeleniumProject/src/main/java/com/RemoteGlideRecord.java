package com;

public interface RemoteGlideRecord {
	
	public void addQuery(String fieldKey, String fieldValue);
	
	public void addEncodedQuery(String encodedQuery);
	
	public void orderBy(String fieldKey);
	
	public void orderByDesc(String fieldKey);
	
	public void query();
	
	public String getValue();
	
	public String getDisplayValue();

}
