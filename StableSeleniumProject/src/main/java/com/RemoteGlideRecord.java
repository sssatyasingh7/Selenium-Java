package com;

import java.util.List;

public interface RemoteGlideRecord {

	/**
	 * 
	 * @param fieldKey
	 * @param fieldValue
	 */
	public void addQuery(String fieldKey, String fieldValue);

	/**
	 * 
	 * @param encodedQuery
	 */
	public void addEncodedQuery(String encodedQuery);

	/**
	 * 
	 * @param fieldKey
	 */
	public void orderBy(String fieldKey);

	/**
	 * 
	 * @param fieldKey
	 */
	public void orderByDesc(String fieldKey);

	/**
	 * 
	 */
	public void query();

	/**
	 * 
	 * @return
	 */
	public String getValue(String fieldKey);

	/**
	 * 
	 * @return
	 */
	public String getDisplayValue(String fieldKey);

	/**
	 * 
	 * @return
	 */
	public List<String> getListOfValue(String fieldKey);

	/**
	 * 
	 * @return
	 */
	public List<String> getListOfDisplayValue(String fieldKey);

	/**
	 * 
	 * @param encodedQuery
	 * @param recordSysId
	 */
	public void update(String encodedQuery, String recordSysId);

	public void addNotNull(String fieldKey);
	
	public void addNull(String fieldKey);
	
	public void setLimit(int count);
	
	public void next();
	
	public int getRecordCount();
	
	public String getSysId();
	
	public void updateListOfRecords(List<String> recordSysId, String encodedQuery);
	
	public void deleteRecord(String recordSysId);
	
	public void deleteListOfRecords(List<String> recordSysId);
}
