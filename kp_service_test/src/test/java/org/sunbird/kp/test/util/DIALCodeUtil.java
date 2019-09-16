package org.sunbird.kp.test.util;

import com.consol.citrus.context.TestContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.List;
import java.util.Map;

/**
 * Utility Class for DIAL Code Related Operations.
 *
 * @author Kumar Gauraw
 */
public class DIALCodeUtil {

    private static final String API_KEY = AppConfig.config.getString("kp_dial_api_key");
    private static final String DEFAULT_CHANNEL = AppConfig.config.getString("kp_test_default_channel");
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String DIALCODE_PAYLOAD_DIR = "templates/payload/dialcode";

    public static Map<String, Object> generateDIALCode(BaseCitrusTestRunner runner, String payload, Integer count, Map<String, Object> headers) {
        String dialPayload = "";
        try {
            dialPayload = StringUtils.isNotBlank(payload) ? payload : (0 != count) ? UtilPayload.GENERATE_DIALCODE_REQ.replace("dialCountVal", objectMapper.writeValueAsString(count)) : null;
            System.out.println("reqPayload : " + dialPayload);
        } catch (Exception e) {
            System.out.println("Exception Occured While Preparing Generate DIAL Payload : " + e.getMessage());
            e.printStackTrace();
        }

        final String reqPayload = dialPayload;
        if (StringUtils.isNotBlank(reqPayload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    Constant.KP_DIAL_SERVICE_ENDPOINT,
                                    APIUrl.GENERATE_DIALCODE,
                                    MediaType.APPLICATION_JSON.toString(),
                                    reqPayload,
                                    getHeaders(headers)));

            runner.http(
                    builder ->
                            TestActionUtil.getExtractFromResponseTestAction(
                                    runner.testContext,
                                    builder,
                                    Constant.KP_DIAL_SERVICE_ENDPOINT,
                                    HttpStatus.OK,
                                    "$.result",
                                    "result"));
            Map<String, Object> result = getResult(runner.testContext);
            return result;

        }
        return null;
    }

    public static Map<String, Object> searchDIALCode(BaseCitrusTestRunner runner, String payload, List<String> dials, Map<String, Object> headers) {
        String searchPayload = "";
        try {
            searchPayload = StringUtils.isNotBlank(payload) ? payload : (CollectionUtils.isNotEmpty(dials)) ? UtilPayload.SEARCH_DIALCODE_WITH_IDS_REQ.replace("dialsVal", objectMapper.writeValueAsString(dials)) : null;
        } catch (Exception e) {
            System.out.println("Exception Occured While Preparing Search DIAL Payload : " + e.getMessage());
            e.printStackTrace();
        }
        final String reqPayload = searchPayload;
        if (StringUtils.isNotBlank(reqPayload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    Constant.KP_DIAL_SERVICE_ENDPOINT,
                                    APIUrl.SEARCH_DIALCODE,
                                    MediaType.APPLICATION_JSON.toString(),
                                    reqPayload,
                                    getHeaders(headers)));

            runner.http(
                    builder ->
                            TestActionUtil.getExtractFromResponseTestAction(
                                    runner.testContext,
                                    builder,
                                    Constant.KP_DIAL_SERVICE_ENDPOINT,
                                    HttpStatus.OK,
                                    "$.result",
                                    "result"));
            Map<String, Object> result = getResult(runner.testContext);
            return result;

        }
        return null;
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
            headers.put(Constant.X_CHANNEL_ID, DEFAULT_CHANNEL);

        headers.put(Constant.AUTHORIZATION, Constant.BEARER + API_KEY);

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
