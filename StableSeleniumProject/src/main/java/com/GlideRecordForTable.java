package com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;

class GlideRecordForTable implements RemoteGlideRecord {

	private String API_URL;
	private int INIT_COUNT;;
	private Response RESPONSE;
	private String ADD_QUERY;
	private String ENCODED_QUERY;
	private String TABLE_NAME;

	private static final RestForSNOW rest = new RestForSNOW();

	GlideRecordForTable(String tableName) {
		this.TABLE_NAME = tableName;
		this.API_URL = String.format("%s?sysparm_display_value=all", tableName);
		this.ADD_QUERY = null;
		this.ENCODED_QUERY = null;
		this.INIT_COUNT = -1;
		this.RESPONSE = null;
	}

	@Override
	public final void addQuery(String fieldKey, String fieldValue) {
		String queryString = String.format("%s=%s", fieldKey, fieldValue);
		if (this.ADD_QUERY == null) {
			this.ADD_QUERY = "&" + queryString;
		} else {
			this.ADD_QUERY = this.ADD_QUERY + "&" + queryString;
		}

	}

	@Override
	public final void addEncodedQuery(String encodedQuery) {
		if (this.ENCODED_QUERY == null) {
			this.ENCODED_QUERY = "&sysparm_query=" + encodedQuery;
		} else {
			this.ENCODED_QUERY = this.ENCODED_QUERY + "^" + encodedQuery;
		}
	}

	@Override
	public final void orderBy(String fieldKey) {
		addEncodedQuery(String.format("ORDERBY%s", fieldKey));
	}

	@Override
	public final void orderByDesc(String fieldKey) {
		addEncodedQuery(String.format("ORDERBYDESC%s", fieldKey));
	}

	@Override
	public final void query() {
		if (this.ENCODED_QUERY != null) {
			this.API_URL = this.API_URL + this.ENCODED_QUERY;
		}
		if (this.ADD_QUERY != null) {
			this.API_URL = this.API_URL + this.ADD_QUERY;
		}
		this.RESPONSE = RestForSNOW.glideRecordGetRequest(this.API_URL);
	}

	@Override
	public final String getValue(String fieldKey) {
		return rest.getValueFromJson(this.RESPONSE, this.INIT_COUNT, fieldKey);
	}

	@Override
	public final String getDisplayValue(String fieldKey) {
		return rest.getDisplayValueFromJson(this.RESPONSE, this.INIT_COUNT, fieldKey);
	}

	@Override
	public final List<String> getListOfValue(String fieldKey) {
		return rest.getListOfValueFromJson(this.RESPONSE, fieldKey);
	}

	@Override
	public final List<String> getListOfDisplayValue(String fieldKey) {
		return rest.getListOfDisplayValueFromJson(this.RESPONSE, fieldKey);
	}

	@Override
	public final void update(String encodedQuery, String recordSysId) {
		rest.putJsonToSNOW(this.TABLE_NAME, recordSysId, encodedQueryToJsonStringConversion(encodedQuery));
	}

	private static final String encodedQueryToJsonStringConversion(String encodedQuery) {
		Map<String, String> map = new HashMap<>();
		map.put("\\^", ",");
		map.put("=", ":");
		map.put("IN", ":");
		if (encodedQuery.contains("\\^")) {
			if (encodedQuery.startsWith("\\^")) {
				encodedQuery = encodedQuery.substring(1);
			}
			if (encodedQuery.endsWith("\\^")) {
				encodedQuery = encodedQuery.substring(0, encodedQuery.length() - 2);
			}
		}
		return CommonUtils.stringToJsonString(encodedQuery, map);
	}

	@Override
	public final void addNotNull(String fieldKey) {
		addEncodedQuery(String.format("%s!=null", fieldKey));
	}

	@Override
	public final void addNull(String fieldKey) {
		addEncodedQuery(String.format("%s=null", fieldKey));
	}

	@Override
	public final void setLimit(int count) {
		this.API_URL = this.API_URL + String.format("&sysparm_limit=%d", count);
	}

	@Override
	public final void next() {
		this.INIT_COUNT++;
	}

	@Override
	public final int getRecordCount() {
		return Integer.parseInt(this.RESPONSE.getHeader("X-Total-Count"));
	}

	@Override
	public final String getSysId() {
		return getValue("sys_id");
	}

	@Override
	public final void updateListOfRecords(List<String> recordSysId, String encodedQuery) {
		String jsonString = encodedQueryToJsonStringConversion(encodedQuery);
		recordSysId.forEach(sysId -> rest.putJsonToSNOW(this.TABLE_NAME, sysId, jsonString));
	}

	@Override
	public final void deleteRecord(String recordSysId) {
		rest.deleteJsonFromSNOW(this.TABLE_NAME, recordSysId);
	}

	@Override
	public final void deleteListOfRecords(List<String> recordSysId) {
		recordSysId.forEach(this::deleteRecord);
	}

}
