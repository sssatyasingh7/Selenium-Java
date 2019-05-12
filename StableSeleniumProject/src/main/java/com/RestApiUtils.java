package com;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public final class RestApiUtils {

	private RestApiUtils() {
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 */
	private static final JSONObject parseToJSONObject(String jsonString) {
		try {
			return (JSONObject) new JSONParser().parse(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param jsonFilePath
	 * @param keyValuePairs
	 * @return JSONObject
	 */
	private static final String jsonManipulation(String jsonFilePath, Map<String, String> keyValuePairs) {
		JSONObject json = parseToJSONObject(FileHelperUtils.readFileAsString(jsonFilePath));
		for (Entry<String, String> pair : keyValuePairs.entrySet()) {
			String fieldKey = pair.getKey();
			if (json.containsKey(fieldKey)) {
				json.replace(fieldKey, pair.getValue());
			} else {
				json.put(fieldKey, pair.getValue());
			}
		}
		return json.toString();
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @return Response
	 */
	public static final Response getRequest(String urlValue, String userName, String password) {
		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).when().get(urlValue);
		return response.getStatusCode() == 200 ? response : null;
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @param jsonFilePath
	 * @param map
	 * @return Response
	 */
	public static final Response postRequest(String urlValue, String userName, String password, String jsonFilePath,
			Map<String, String> map) {
		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
				.body(jsonManipulation(jsonFilePath, map)).when().post(urlValue);
		return response.getStatusCode() == 201 ? response : null;
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @param jsonString
	 * @return Response
	 */
	public static final Response postRequest(String urlValue, String userName, String password, String jsonString) {
		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
				.body(parseToJSONObject(jsonString)).when().post(urlValue);
		return response.getStatusCode() == 201 ? response : null;
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @return
	 */
	public static final Response postOperation(String urlValue, String userName, String password) {
		return given().contentType(ContentType.JSON).auth().basic(userName, password).request().when().post(urlValue);
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @param jsonFilePath
	 * @param map
	 * @return Response
	 */
	public static final Response putRequest(String urlValue, String userName, String password, String jsonFilePath,
			Map<String, String> map) {
		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
				.body(jsonManipulation(jsonFilePath, map)).when().put(urlValue);
		return response.getStatusCode() == 200 ? response : null;
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @param jsonString
	 * @return Response
	 */
	public static final Response putRequest(String urlValue, String userName, String password, String jsonString) {
		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
				.body(parseToJSONObject(jsonString)).when().put(urlValue);
		return response.getStatusCode() == 200 ? response : null;
	}

	/**
	 * 
	 * @param urlValue
	 * @param userName
	 * @param password
	 * @return true/false
	 */
	public static final boolean deleteRequest(String urlValue, String userName, String password) {
		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).when()
				.delete(urlValue);
		return (response != null && response.getStatusCode() == 204) ? true : false;
	}

	/**
	 * 
	 * @param response
	 * @param fieldPath
	 * @return String
	 */
	public static final String getFieldValueFromJSONResponse(Response response, String fieldPath) {
		try {
			return CommonUtils.returnStringValue(new JsonPath(response.asString()).getString(fieldPath).toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param response
	 * @param fieldPath
	 * @return List<String>
	 */
	public static final List<String> getListOfFieldValueFromJSONResponse(Response response, String fieldPath) {
		List<String> list = new ArrayList<>();
		if (response != null) {
			new JsonPath(response.asString()).getList(fieldPath, String.class).parallelStream().forEachOrdered(str -> {
				str = CommonUtils.returnStringValue(str);
				if (str != null) {
					list.add(str);
				}
			});
		}
		return list;
	}
}
