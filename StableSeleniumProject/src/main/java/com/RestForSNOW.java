package com;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Table;

import io.restassured.response.Response;

public class RestForSNOW {

	private static final String API_URL = "/api/now/table/";
	private static final String RESPONSE_DISPLAY_FIELD_QUERY = "?sysparm_display_value=true";
	// private static final RestApiUtils restUtils = new RestApiUtils();
	private static final TestEnvironment testEnv = TestEnvironment.getInstance();

	public final String getValueFromJson(Response response, String fieldKey) {
		return RestApiUtils.getFieldValueFromJSONResponse(response, String.format("result.%s.value", fieldKey));
	}

	public final String getValueFromJson(Response response, int recordIndex, String fieldKey) {
		return RestApiUtils.getFieldValueFromJSONResponse(response,
				String.format("result[%d].%s.value", recordIndex, fieldKey));
	}

	public final String getDisplayValueFromJson(Response response, String fieldKey) {
		return RestApiUtils.getFieldValueFromJSONResponse(response, String.format("result.%s.display_value", fieldKey));
	}

	public final String getDisplayValueFromJson(Response response, int recordIndex, String fieldKey) {
		return RestApiUtils.getFieldValueFromJSONResponse(response,
				String.format("result[%d].%s.display_value", recordIndex, fieldKey));
	}

	public final List<String> getListOfValueFromJson(Response response, String fieldKey) {
		return RestApiUtils.getListOfFieldValueFromJSONResponse(response, String.format("result.%s.value", fieldKey));
	}

	public final List<String> getListOfDisplayValueFromJson(Response response, String fieldKey) {
		return RestApiUtils.getListOfFieldValueFromJSONResponse(response,
				String.format("result.%s.display_value", fieldKey));
	}

	public String postJsonToSNOW(String tableName, String jsonFilePath, final Map<String, String> map) {
		return getValueFromJson(
				RestApiUtils.postRequest(testEnv.getDefaultURL() + API_URL + tableName + RESPONSE_DISPLAY_FIELD_QUERY,
						testEnv.getUserName(), testEnv.getPassword(), jsonFilePath, map),
				"sys_id");
	}

	public final String postJsonToSNOW(String tableName, final Map<String, String> map) {
		return getValueFromJson(
				RestApiUtils.postRequest(testEnv.getDefaultURL() + API_URL + tableName + RESPONSE_DISPLAY_FIELD_QUERY,
						testEnv.getUserName(), testEnv.getPassword(), map),
				"sys_id");
	}

	public final String postJsonToSNOW(String tableName, String jsonString) {
		return getValueFromJson(RestApiUtils.postRequest(testEnv.getDefaultURL(), testEnv.getUserName(),
				testEnv.getPassword(), jsonString), "sys_id");
	}

	public final String putJsonToSNOW(String tableName, String recordSysId, String jsonFilePath,
			final Map<String, String> map) {
		return recordSysId == null ? null
				: getValueFromJson(RestApiUtils.putRequest(testEnv.getDefaultURL(), testEnv.getUserName(),
						testEnv.getPassword(), jsonFilePath, map), "sys_id");
	}

	public final String putJsonToSNOW(String tableName, String recordSysId, String jsonString) {
		return recordSysId == null ? null
				: getValueFromJson(RestApiUtils.putRequest(testEnv.getDefaultURL(), testEnv.getUserName(),
						testEnv.getPassword(), jsonString), "sys-id");
	}

	public final Response getJsonFromSNOW(String tableName, String recordSysId) {
		return RestApiUtils.getRequest(
				testEnv.getDefaultURL() + API_URL + tableName + "/" + recordSysId + "?sysparm_display_value=all",
				testEnv.getUserName(), testEnv.getPassword());
	}

	public boolean deleteJsonFromSNOW(String tableName, String recordSysId) {
		return RestApiUtils.deleteRequest(testEnv.getDefaultURL() + API_URL + tableName + "/" + recordSysId,
				testEnv.getUserName(), testEnv.getPassword());
	}

	public static final Response glideRecordGetRequest(String apiURL) {
		return RestApiUtils.getRequest(testEnv.getDefaultURL() + API_URL + apiURL, testEnv.getUserName(),
				testEnv.getPassword());
	}

	public final void impersonateUser(String userName) {
		RestApiUtils.postOperation(String.format("%s/api/now/ui/impersonate/%s", testEnv.getDefaultURL(), userName),
				testEnv.getUserName(), testEnv.getPassword());
	}

	public void attachmentAttachmentToRecord(String parentTableName, String parentRecordSysId, String fileName,
			String message) {
		RestApiUtils.postTextFile(
				String.format("%s/api/now/attachment/file?table_name=%s&table_sys_id=%s", testEnv.getDefaultURL(),
						parentTableName, parentRecordSysId),
				testEnv.getUserName(), testEnv.getPassword(), fileName, message);
	}

	public final List<String> getRecentImpersonatedUserIds() {
		return getListOfValueFromJson(
				RestApiUtils.getRequest(testEnv.getDefaultURL() + "/api/now/ui/impersonate/recent",
						testEnv.getUserName(), testEnv.getPassword()),
				"user_name");
	}

	public final List<String> getRecentImpersonatedUsers() {
		return getListOfValueFromJson(
				RestApiUtils.getRequest(testEnv.getDefaultURL() + "/api/now/ui/impersonate/recent",
						testEnv.getUserName(), testEnv.getPassword()),
				"user_display_value");
	}

	public final List<String> getRecentImpersonatedUserSysId() {
		return getListOfValueFromJson(
				RestApiUtils.getRequest(testEnv.getDefaultURL() + "/api/now/ui/impersonate/recent",
						testEnv.getUserName(), testEnv.getPassword()),
				"user_sys_id");
	}
}
