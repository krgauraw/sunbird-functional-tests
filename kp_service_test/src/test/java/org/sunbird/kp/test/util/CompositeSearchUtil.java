package org.sunbird.kp.test.util;

import com.consol.citrus.context.TestContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.AppConfig;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.common.TestActionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility Class to make Composite Search Calls
 * @author Kumar Gauraw
 */
public class CompositeSearchUtil {

    private static final String API_KEY = AppConfig.config.getString("kp_api_key");
    private static final Boolean IS_USER_AUTH_REQUIRED = AppConfig.config.getBoolean("user_auth_enable");
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String SEARCH_PAYLOAD_DIR = "templates/payload/search";

    public static Map<String, Object> searchContent(BaseCitrusTestRunner runner, String payload, String testName, Map<String, Object> headers) {
        if (StringUtils.isNotBlank(payload)) {
            System.out.println("Dynamic Payload...");
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    Constant.KP_SEARCH_SERVICE_ENDPOINT,
                                    APIUrl.COMPOSITE_SEARCH,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));

        } else if (StringUtils.isNotBlank(testName)) {
            System.out.println("Static Payload Used With Dynamic Values...");
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    Constant.KP_SEARCH_SERVICE_ENDPOINT,
                                    SEARCH_PAYLOAD_DIR,
                                    testName,
                                    APIUrl.COMPOSITE_SEARCH,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                runner.testContext,
                                builder,
                                Constant.KP_SEARCH_SERVICE_ENDPOINT,
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(runner.testContext);
        return result;
    }


    /**
     * This Method provides all necessary header elements
     *
     * @param additionalHeaders
     * @return
     */
    private static Map<String, Object> getHeaders(Map<String, Object> additionalHeaders) {
        Map<String, Object> headers = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(additionalHeaders))
            headers.putAll(additionalHeaders);

        if (!headers.containsKey(Constant.X_CHANNEL_ID))
            headers.put(Constant.X_CHANNEL_ID, AppConfig.config.getString("kp_test_default_channel"));

        headers.put(Constant.AUTHORIZATION, Constant.BEARER + API_KEY);

        if (IS_USER_AUTH_REQUIRED)
            headers.put(Constant.X_AUTHENTICATED_USER_TOKEN, "${accessToken}");
        return headers;
    }

    /**
     * This Method Returns Result Map
     *
     * @param testContext
     * @return
     */
    private static Map<String, Object> getResult(TestContext testContext) {
        Map<String, Object> result = null;
        try {
            result = objectMapper.readValue(testContext.getVariable("result"), new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            System.out.println("Exception Occurred While parsing context variable : " + e);
        }
        return result;
    }
}
