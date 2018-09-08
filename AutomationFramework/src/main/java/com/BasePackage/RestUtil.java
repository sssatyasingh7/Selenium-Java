package com.BasePackage;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.StringCharacterIterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class RestUtil {

	String jsonInput;
	String escJSONOutput;
	JSONObject jsonResponseBody;

	/**
	 * Get Json from file
	 * 
	 * @param fileName
	 * @return
	 */
	public String getJSONfromFile(String fileName) {

		StringBuilder sb = new StringBuilder();
		String jsonInput = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((jsonInput = br.readLine()) != null) {
				sb.append(jsonInput);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

	public String escapeJSONchars(String input) {
		final StringBuilder escJSON = new StringBuilder();

		// StringCharacterIterator class iterates over the entire String
		StringCharacterIterator iterator = new StringCharacterIterator(input);
		char myChar = iterator.current();

		while (myChar != StringCharacterIterator.DONE) {
			if (myChar == '\"') {
				escJSON.append("\\\"");
			} else if (myChar == '\t') {
				escJSON.append("\\t");
			} else if (myChar == '\f') {
				escJSON.append("\\f");
			} else if (myChar == '\n') {
				escJSON.append("\\n");
			} else if (myChar == '\r') {
				escJSON.append("\\r");
			} else if (myChar == '\\') {
				escJSON.append("\\\\");
			} else if (myChar == '/') {
				escJSON.append("\\/");
			} else if (myChar == '\b') {
				escJSON.append("\\b");
			} else {

				// nothing matched - just as text as it is.
				escJSON.append(myChar);
			}
			myChar = iterator.next();
		}
		return escJSON.toString();
	}

	/**
	 * @param baseUri
	 *            Base URL of instance
	 * @param user
	 *            UserName
	 * @param password
	 *            Password
	 * @param apiURL
	 *            /api/now/table/{tableName} - For SN
	 * @param body
	 *            JSON
	 * 
	 * 
	 * 
	 *            post with basic authentication
	 */

	public Response post(String baseUri, String user, String password, String apiURL, String body) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		String APIBody = body;
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body
		builder.setBody(APIBody);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().post(APIUrl);

		// System.out.println(response.statusCode());
		// System.out.println(response.statusLine());
		JSONObject JSONResponseBody;
		try {
			JSONResponseBody = new JSONObject(response.body().asString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	/**
	 * @param baseUri
	 *            Base URL of instance
	 * @param user
	 *            UserName
	 * @param password
	 *            Password
	 * @param apiURL
	 *            /api/now/table/{tableName} - For SN
	 * @param fieldName
	 *            fieldTobeUpdated
	 * @param fieldData
	 *            valueTobeUpdated
	 * 
	 * 
	 * 
	 *            put with basic authentication
	 */

	public Response put(String baseUri, String user, String password, String apiURL, String fieldName,
			String fieldData) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		String APIBody = "{\"" + fieldName + "\":\"" + fieldData + "\"}";
		;
		return putApi(baseUri, user, password, APIUrl, APIBody);

	}

	private Response putApi(String baseUri, String user, String password, String APIUrl, String APIBody) {
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body
		builder.setBody(APIBody);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().put(APIUrl);

		JSONObject JSONResponseBody;
		try {
			JSONResponseBody = new JSONObject(response.body().asString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * @param baseUri
	 *            Base URL of instance
	 * @param user
	 *            UserName
	 * @param password
	 *            Password
	 * @param apiURL
	 *            /api/now/table/{tableName} - For SN
	 * @param body
	 *            JSON
	 * @param params
	 *            Hashmap
	 * @param Headers
	 *            Hashmap
	 * 
	 * 
	 *            post with basic authentication
	 */

	public String postWithRequestParamsasString(String baseUri, String user, String password, String apiURL,
			String body, Map params, Map headers) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		String APIBody = body;
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body - change to push
		builder.setBody(APIBody);
		builder.addParameters(params);
		builder.addHeaders(headers);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().post(APIUrl);
		JSONObject JSONResponseBody;
		try {
			JSONResponseBody = new JSONObject(response.body().asString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.asString();
	}

	public Response postWithRequestParams(String baseUri, String user, String password, String apiURL, String body,
			Map params, Map headers) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		String APIBody = body;
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body - change to push
		builder.setBody(APIBody);
		builder.addParameters(params);
		builder.addHeaders(headers);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().post(APIUrl);

		return response;
	}

	/**
	 * @param baseUri
	 *            Base URL of instance
	 * @param user
	 *            UserName
	 * @param password
	 *            Password
	 * @param apiURL
	 *            /api/now/table/{tableName} - For SN
	 * 
	 * 
	 *            Get with basic authentication
	 */

	public String get(String baseUri, String user, String password, String apiURL) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().get(APIUrl);

		return response.asString();
	}

	public Response getJsonResponse(String baseUri, String user, String password, String apiURL) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().get(APIUrl);

		return response;
	}

	public Response getResponseObject(String baseUri, String user, String password, String apiURL,
			Map<String, String> parametersMap) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		builder.addParams(parametersMap);
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().get(APIUrl);
		return response;
	}

	/**
	 * @param baseUri
	 *            Base URL of instance
	 * @param user
	 *            UserName
	 * @param password
	 *            Password
	 * @param apiURL
	 *            /api/now/table/{tableName} - For SN
	 * 
	 *            Returns the Reponse Headers
	 * 
	 *            Get with basic authentication
	 */

	public Headers getResponseHeaders(String baseUri, String user, String password, String apiURL) {
		// Initializing Rest API's URL
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().get(APIUrl);

		return response.getHeaders();
	}

	public JSONObject getObject(String baseUri, String user, String password, String apiURL) {
		String APIUrl = baseUri + apiURL;

		// Initializing payload or API body
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().get(APIUrl);

		try {
			jsonResponseBody = new JSONObject(response.body().asString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonResponseBody;

	}

	public JSONObject getWithRequestParams(String baseUri, String user, String password, String endpoint, Map paramsMap,
			Map headersMap) {
		// Initializing payload or API body
		RequestSpecBuilder builder = new RequestSpecBuilder();
		String APIUrl = baseUri + endpoint;
		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		builder.addHeaders(headersMap);
		builder.addParameters(paramsMap);
		RequestSpecification requestSpec = builder.build();

		Response response = given().baseUri(baseUri).authentication().preemptive().basic(user, password)
				.spec(requestSpec).when().get(APIUrl);

		try {
			jsonResponseBody = new JSONObject(response.body().asString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonResponseBody;

	}
}
