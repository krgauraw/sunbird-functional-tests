package org.sunbird.kp.test.common;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Base Runner Class for Integration Test
 *
 * @author Kumar Gauraw
 */
public class BaseCitrusTestRunner extends TestNGCitrusTestRunner {

    @Autowired
    public TestContext testContext;
    public static final String IMAGE_SUFFIX = ".img";
    public static final String REQUEST_FORM_DATA = "request.params";
    public static final String REQUEST_JSON = "request.json";
    public static final String RESPONSE_JSON = "response.json";
    public static final String VALIDATE_JSON = "validate.json";
    public static final String RESPONSE_JSON_IMAGE = "response_image.json";

    private static final String API_KEY = AppConfig.config.getString("kp_api_key");
    private static final Boolean IS_USER_AUTH_REQUIRED = AppConfig.config.getBoolean("user_auth_enable");

    protected static ObjectMapper objectMapper = new ObjectMapper();

    private static List<String> CS_API_LIST = AppConfig.config.getStringList("cs_api_list");

    public BaseCitrusTestRunner() {
    }

    /**
     * This Method Perform Test for API with GET Method
     * @param runner
     * @param templateDir
     * @param testName
     * @param requestUrl
     * @param headers
     * @param responseCode
     * @param validationParams
     * @param responseJson
     */
    public void performGetTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            Map<String, Object> headers,
            HttpStatus responseCode,
            Map<String, Object> validationParams,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.processGetRequest(
                                builder,
                                getEndPoint(requestUrl),
                                testName,
                                requestUrl,
                                getHeaders(headers)
                        ));
        runner.http(
                builder ->
                        TestActionUtil.getResponse(
                                builder, getEndPoint(requestUrl), templateDir, testName, responseCode, responseJson, validationParams));
    }

    /**
     * This Method Perform Test for API with POST Method
     * @param runner
     * @param templateDir
     * @param testName
     * @param requestUrl
     * @param headers
     * @param requestJson
     * @param contentType
     * @param responseCode
     * @param validationParams
     * @param responseJson
     */
    public void performPostTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            Map<String, Object> headers,
            String requestJson,
            String contentType,
            HttpStatus responseCode,
            Map<String, Object> validationParams,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.processPostRequest(
                                builder,
                                getEndPoint(requestUrl),
                                templateDir,
                                testName,
                                requestUrl,
                                requestJson,
                                contentType,
                                getHeaders(headers)
                        ));
            runner.http(
                    builder ->
                            TestActionUtil.getResponse(
                                    builder, getEndPoint(requestUrl), templateDir, testName, responseCode, responseJson, validationParams));


    }

    /**
     * This Method Perform Test for API with PATCH Method
     * @param runner
     * @param templateDir
     * @param testName
     * @param requestUrl
     * @param headers
     * @param requestJson
     * @param contentType
     * @param responseCode
     * @param validationParams
     * @param responseJson
     */
    public void performPatchTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            Map<String, Object> headers,
            String requestJson,
            String contentType,
            HttpStatus responseCode,
            Map<String, Object> validationParams,
            String responseJson) {

        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.processPatchRequest(
                                builder,
                                getEndPoint(requestUrl),
                                templateDir,
                                testName,
                                requestUrl,
                                requestJson,
                                contentType,
                                getHeaders(headers)
                        ));
        runner.http(
                builder ->
                        TestActionUtil.getResponse(
                                builder, getEndPoint(requestUrl), templateDir, testName, responseCode, responseJson, validationParams));
    }

    /**
     * This Method Perform Test for API with DELETE Method
     * @param runner
     * @param templateDir
     * @param testName
     * @param requestUrl
     * @param headers
     * @param requestJson
     * @param contentType
     * @param responseCode
     * @param validationParams
     * @param responseJson
     */
    public void performDeleteTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            Map<String, Object> headers,
            String requestJson,
            String contentType,
            HttpStatus responseCode,
            Map<String, Object> validationParams,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.processDeleteRequest(
                                builder,
                                getEndPoint(requestUrl),
                                templateDir,
                                testName,
                                requestUrl,
                                requestJson,
                                contentType,
                                getHeaders(headers)
                        ));
        runner.http(
                builder ->
                        TestActionUtil.getResponse(
                                builder, getEndPoint(requestUrl), templateDir, testName, responseCode, responseJson, validationParams));
    }

    /**
     * This Method Perform Test for API with Multipart Request
     * @param runner
     * @param templateDir
     * @param testName
     * @param requestUrl
     * @param headers
     * @param requestFile
     * @param responseCode
     * @param validationParams
     * @param responseJson
     */
    public void performMultipartTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            Map<String, Object> headers, String requestFile,
            HttpStatus responseCode,
            Map<String, Object> validationParams,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.processMultipartRequest(
                                testContext,
                                builder,
                                getEndPoint(requestUrl),
                                templateDir,
                                testName,
                                requestUrl,
                                requestFile,
                                getHeaders(headers),
                                runner.getClass().getClassLoader()
                        ));
        runner.http(
                builder ->
                        TestActionUtil.getResponse(
                                builder, getEndPoint(requestUrl), templateDir, testName, responseCode, responseJson, validationParams));
    }

    /**
     * This Method Set User Auth Token to Test Runner Instance
     * @param runner
     * @param userType
     */
    protected void getAuthToken(TestNGCitrusTestRunner runner, String userType) {
        if (IS_USER_AUTH_REQUIRED) {
            if (StringUtils.equalsIgnoreCase(userType, "Reviewer"))
                getUserAuthToken(runner, AppConfig.config.getString("kp_reviewer_user"), AppConfig.config.getString("kp_reviewer_password"));
            else getUserAuthToken(runner, null, null);
        }
    }

    /**
     *
     * @param runner
     * @param userName
     * @param password
     */
    private void getUserAuthToken(TestNGCitrusTestRunner runner, String userName, String password) {
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            runner.http(
                    builder ->
                            TestActionUtil.getTokenRequest(
                                    builder, Constant.KEYCLOAK_ENDPOINT, userName, password));
            runner.http(builder -> TestActionUtil.getTokenResponse(builder, Constant.KEYCLOAK_ENDPOINT));
        } else {
            getUserAuthToken(runner, AppConfig.config.getString("kp_creator_user"), AppConfig.config.getString("kp_creator_password"));
        }
    }

    /**
     * This Method provides all necessary header elements
     * @param additionalHeaders
     * @return
     */
    private Map<String, Object> getHeaders(Map<String, Object> additionalHeaders) {
        Map<String, Object> headers = new HashMap<String, Object>();
        if (null != additionalHeaders && !additionalHeaders.isEmpty())
            headers.putAll(additionalHeaders);

        if (!headers.containsKey(Constant.X_CHANNEL_ID))
            headers.put(Constant.X_CHANNEL_ID, AppConfig.config.getString("kp_test_default_channel"));
        if (!headers.containsKey(Constant.X_APP_ID))
            headers.put(Constant.X_APP_ID, AppConfig.config.getString("kp_test_default_appId"));

        headers.put(Constant.AUTHORIZATION, Constant.BEARER + API_KEY);
        headers.put(Constant.X_CONSUMER_ID, UUID.randomUUID().toString());

        if (IS_USER_AUTH_REQUIRED)
            headers.put(Constant.X_AUTHENTICATED_USER_TOKEN, "${accessToken}");
        return headers;
    }

    protected void delay(TestNGCitrusTestRunner runner, long time) {
        try {
            runner.sleep(time);
        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }

    protected static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    public  String getEndPoint(String reqUrl) {
        return CS_API_LIST.stream().anyMatch(url -> StringUtils.contains(reqUrl, url))? Constant.KP_CONTENT_SERVICE_ENDPOINT : Constant.KP_ENDPOINT;
    }

}
