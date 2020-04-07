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
	 * @return {@link String}
	 */
	public String getValue(String fieldKey);

	/**
	 * 
	 * @return {@link String}
	 */
	public String getDisplayValue(String fieldKey);

	/**
	 * 
	 * @return {@link List}
	 */
	public List<String> getListOfValue(String fieldKey);

	/**
	 * 
	 * @return {@link List}
	 */
	public List<String> getListOfDisplayValue(String fieldKey);

	/**
	 * 
	 * @param encodedQuery
	 * @param recordSysId
	 */
	public void update(String encodedQuery, String recordSysId);

	/**
	 * 
	 * @param fieldKey
	 */
	public void addNotNull(String fieldKey);
	
	/**
	 * 
	 * @param fieldKey
	 */
	public void addNull(String fieldKey);
	
	/**
	 * 
	 * @param count
	 */
	public void setLimit(int count);
	
	/**
	 * 
	 */
	public void next();
	
	/**
	 * 
	 * @return {@link Integer}
	 */
	public int getRecordCount();
	
	/**
	 * 
	 * @return {@link String}
	 */
	public String getSysId();
	
	/**
	 * 
	 * @param recordSysId
	 * @param encodedQuery
	 */
	public void updateListOfRecords(List<String> recordSysId, String encodedQuery);
	
	/**
	 * 
	 * @param recordSysId
	 */
	public void deleteRecord(String recordSysId);
	
	/**
	 * 
	 * @param recordSysId
	 */
	public void deleteListOfRecords(List<String> recordSysId);
}
