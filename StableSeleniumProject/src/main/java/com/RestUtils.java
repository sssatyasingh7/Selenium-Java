package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public enum RestUtils {
 	INSTANCE;

 	/**
 	 * Get Request
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response getRequest(String url, String userName, String password) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).when().get(url);
 		return response.statusCode() == 200 ? response : null;
 	}

 	/**
 	 * JSON Manipulation
 	 * 
 	 * @param jsonFilePath
 	 * @param keyValuePairs
 	 * @return {@link String}
 	 * @author skuma726
 	 */
 	private final String jsonManipulation(String jsonFilePath, Map<String, String> keyValuePairs) {
 		return CommonUtils.mapToJsonString(CommonUtils
 				.mergeMap(CommonUtils.jsonStringToMap(FileHelperUtils.readFileAsString(jsonFilePath)), keyValuePairs));
 	}

 	/**
 	 * Post Request
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param jsonFileName
 	 * @param returnField
 	 * @param fieldNameValuePairs
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response postRequest(String url, String userName, String password, String jsonFilePath,
 			Map<String, String> fieldNameValuePairs) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
 				.body(jsonManipulation(jsonFilePath, fieldNameValuePairs)).when().post(url);
 		Assert.assertEquals(response.getStatusCode(), 201);
 		return response;
 	}

 	/**
 	 * Post Request Operation
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param fieldNameValuePairs
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response postRequest(String url, String userName, String password,
 			final Map<String, String> fieldNameValuePairs) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
 				.body(CommonUtils.mapToJsonString(fieldNameValuePairs)).when().post(url);
 		Assert.assertEquals(response.getStatusCode(), 201);
 		return response;
 	}

 	/**
 	 * Post Request Operation
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param jsonString
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response postRequest(String url, String userName, String password, String jsonString) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
 				.body(jsonString).when().post(url);
 		Assert.assertEquals(response.getStatusCode(), 201);
 		return response;
 	}

 	/**
 	 * Put Request
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param jsonFilePath
 	 * @param returnField
 	 * @param fieldNameValuePairs
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response putRequest(String url, String userName, String password, String jsonFilePath,
 			Map<String, String> fieldNameValuePairs) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
 				.body(jsonManipulation(jsonFilePath, fieldNameValuePairs)).when().put(url);
 		return response.getStatusCode() == 200 ? response : null;
 	}

 	/**

	 	 * Put Request Operation
	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param fieldNameValuePairs
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response putRequest(String url, String userName, String password,
 			Map<String, String> fieldNameValuePairs) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
 				.body(CommonUtils.mapToJsonString(fieldNameValuePairs)).when().put(url);
 		return response.getStatusCode() == 200 ? response : null;
 	}
	/**
 	 * Put Operation
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param jsonString
 	 * @return {@link Response}
 	 * @author skuma726
 	 */
 	public final Response putRequest(String url, String userName, String password, String jsonString) {
 		Response response = given().contentType(ContentType.JSON).auth().basic(userName, password).request()
 				.body(jsonString).when().put(url);
 		return response.getStatusCode() == 200 ? response : null;
 	}
	/**
 	 * Delete Request
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @return {@link Boolean}
 	 * @author skuma726
 	 */
 	public final boolean deleteRequest(String url, String userName, String password) {
 		int statusCode = given().contentType(ContentType.JSON).auth().basic(userName, password).when().delete(url)
 				.getStatusCode();
 		return (statusCode == 204);
 	}
	/**
 	 * Get Field Value from JSON Response
 	 * 
 	 * @param response
 	 * @param fieldPath
 	 * @return {@link String}
 	 * @author skuma726
 	 */
 	public final String getFieldValueFromJSONResponse(Response response, String fieldPath) {
 		try {
 			return CommonUtils.returnValue(new JsonPath(response.asString()).getString(fieldPath));
 		} catch (Exception e) {
 			return null;
 		}
 	}
	/**
 	 * Get List Of Value for a field from JSON Response
 	 * 
 	 * @param response
 	 * @param fieldPath
 	 * @return List<String>
 	 */
 	public final List<String> getListOfValueFromJSONResponse(Response response, String fieldPath) {
 		return (response != null)
 				? new JsonPath(response.asString()).getList(fieldPath, String.class).stream()
 						.map(CommonUtils::returnValue).filter(Objects::nonNull).collect(Collectors.toList())
 				: new ArrayList<>();
 	}

 	/**
 	 * Post Operation
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @author skuma726
 	 */
 	public final void postOperation(String url, String userName, String password) {
 		given().contentType(ContentType.JSON).auth().basic(userName, password).request().when().post(url);
 	}

 	/**
 	 * Post Text File
 	 * 
 	 * @param url
 	 * @param userName
 	 * @param password
 	 * @param fileName
 	 * @param message
	 * @author skuma726
 	 */
 	public final void postTextFile(String url, String userName, String password, String fileName, String message) {
 		given().contentType(ContentType.TEXT).auth().basic(userName, password).body(message).when()
 				.post(String.format("%s&file_name=%s", url, fileName));
 	}
 }
