package org.sunbird.kp.test.util;

import com.consol.citrus.context.TestContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Utility Class for Content API Test
 *
 * @author Kumar Gauraw
 */
public class ContentUtil {

    private static final String API_KEY = AppConfig.config.getString("kp_api_key");
    private static final Boolean IS_USER_AUTH_REQUIRED = AppConfig.config.getBoolean("user_auth_enable");
    private static List<String> CS_API_LIST = AppConfig.config.getStringList("cs_api_list");
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

    private static final String CONTENT_PAYLOAD_DIR = "templates/payload/content";

    //static payload
    private static final String CREATE_RESOURCE_CONTENT_EXPECT_200 = "createResourceContentExpect200";
    private static final String UPLOAD_RESOURCE_CONTENT_EXPECT_200 = "uploadResourceContentExpect200";
    private static final String CREATE_ASSET_CONTENT_EXPECT_200 = "createAssetContentExpect200";
    private static final String UPLOAD_ASSET_CONTENT_EXPECT_200 = "uploadAssetContentExpect200";
    private static final String CREATE_COLLECTION_CONTENT_EXPECT_200 = "createCollectionContentExpect200";
    private static final String REVIEW_RESOURCE_CONTENT_EXPECT_200 = "reviewResourceContentExpect200";
    private static final String UPDATE_RESOURCE_CONTENT_EXPECT_200 = "updateResourceContentExpect200";
    private static final String PUBLISH_CONTENT_EXPECT_200 = "publishContentExpect200";
    private static final String UPDATE_HIERARCHY_EXPECT_200 = "updateHierarchyExpect200";
    private static final String RETIRE_CONTENT_EXPECT_200 = "retireContentExpect200";
    private static final String DISCARD_CONTENT_EXPECT_200 = "discardContentExpect200";
    private static final String FLAG_RESOURCE_CONTENT_EXPECT_200 = "flagResourceContentExpect200";
    private static final String ACCEPT_FLAG_RESOURCE_CONTENT_EXPECT_200 = "acceptFlagResourceContentExpect200";
    private static final String REJECT_FLAG_CONTENT_EXPECT_200 = "rejectFlagContentExpect200";

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * This method will allow you to create a test resource object in any state you want as per WorkflowConstants Class
     *
     * @param type
     * @param runner
     * @param createReqPayload
     * @param mimeType
     * @param headers
     * @return
     * @see WorkflowConstants
     */
    public static Map<String, Object> prepareResourceContent(String type, BaseCitrusTestRunner runner, String createReqPayload,
                                                             String mimeType, Map<String, Object> headers) {
        Map contentWorkMap = null;
        Map contentMap = ContentUtil.createResourceContent(runner, createReqPayload, mimeType, headers);
        Map<String, Object> result = new HashMap<>();
        String contentId = (String) contentMap.get("content_id");
        result.put("content_id", contentId);
        result.put("versionKey", contentMap.get("versionKey"));
        runner.variable("versionKeyVal", contentMap.get("versionKey"));
        try {
            contentWorkMap = mapper.readValue(WorkflowConstants.contentWorkFlows, new TypeReference<Map<String, Object>>() {
            });
            List contentWorkList = (List<String>) contentWorkMap.get(type);
            Map<String, Supplier<Map<String, Object>>> actionMap = getContentWorkflowMap(runner, contentId, mimeType, headers);
            if (!CollectionUtils.isEmpty(contentWorkList)) {
                contentWorkList.forEach(action -> {
                    Map response = actionMap.get(action).get();
                    if (response.get("content") != null)
                        response = (Map<String, Object>) response.get("content");

                    if (StringUtils.isNotBlank((String) response.get("versionKey"))) {
                        result.put("versionKey", response.get("versionKey"));
                        runner.variable("versionKeyVal", response.get("versionKey"));
                    }
                    if (StringUtils.isNotBlank((String) response.get("content_url")))
                        result.put("content_url", response.get("content_url"));
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method will allow you to create a test Asset object in any state you want as per WorkflowConstants Class
     *
     * @param type
     * @param runner
     * @param mimeType
     * @param headers
     * @return
     * @see WorkflowConstants
     */
    public static Map<String, Object> prepareAssetContent(String type, BaseCitrusTestRunner runner,
                                                          String mimeType, Map<String, Object> headers) {
        Map assetWorkMap = null;
        Map contentMap = ContentUtil.createAssetContent(runner, null, mimeType, headers);
        Map<String, Object> result = new HashMap<>();
        String contentId = (String) contentMap.get("content_id");
        result.put("content_id", contentId);
        result.put("versionKey", contentMap.get("versionKey"));
        runner.variable("versionKeyVal", contentMap.get("versionKey"));
        try {
            assetWorkMap = mapper.readValue(WorkflowConstants.assetWorkFlows, new TypeReference<Map<String, Object>>() {
            });
            List contentWorkList = (List<String>) assetWorkMap.get(type);
            Map<String, Supplier<Map<String, Object>>> actionMap = getAssetWorkFlowMap(runner, contentId, mimeType, headers);
            if (!CollectionUtils.isEmpty(contentWorkList)) {
                contentWorkList.forEach(action -> {
                    Map response = actionMap.get(action).get();
                    if (response.get("content") != null)
                        response = (Map<String, Object>) response.get("content");

                    if (StringUtils.isNotBlank((String) response.get("versionKey"))) {
                        result.put("versionKey", response.get("versionKey"));
                        runner.variable("versionKeyVal", response.get("versionKey"));
                    }
                    if (StringUtils.isNotBlank((String) response.get("content_url")))
                        result.put("content_url", response.get("content_url"));
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static Map<String, Object> createResourceContent(BaseCitrusTestRunner runner, String payload, String mimeType, Map<String, Object> headers) {
        String contentId = "KP_FT_12345";
        String versionKey = "12345";
        Map<String, Object> data = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payload)) {
            return createContent(runner, runner.testContext, payload, null, headers);
        } else if (StringUtils.isNotBlank(mimeType)) {
            runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
            switch (mimeType.toLowerCase()) {
                case "application/pdf": {
                    runner.variable("codeVal", "kp.ft.resource.pdf");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.ecml-archive": {
                    runner.variable("codeVal", "kp.ft.resource.ecml");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.html-archive": {
                    runner.variable("codeVal", "kp.ft.resource.html");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.h5p-archive": {
                    runner.variable("codeVal", "kp.ft.resource.h5p");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.plugin-archive": {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    runner.variable("idVal", timestamp);
                    runner.variable("codeVal", "KP_FT_" + timestamp);
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "video/x-youtube": {
                    runner.variable("codeVal", "kp.ft.resource.youtube");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "video/webm": {
                    runner.variable("codeVal", "kp.ft.resource.webm");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "video/mp4": {
                    runner.variable("codeVal", "kp.ft.resource.mp4");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
            }
            return createContent(runner, runner.testContext, null, CREATE_RESOURCE_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }

    public static Map<String, Object> createAssetContent(BaseCitrusTestRunner runner, String payload, String mimeType, Map<String, Object> headers) {
        String contentId = "KP_FT_12345";
        String versionKey = "12345";
        Map<String, Object> data = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payload)) {
            return createContent(runner, runner.testContext, payload, null, headers);
        } else if (StringUtils.isNotBlank(mimeType)) {
            runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
            runner.variable("mimeTypeVal", mimeType);
            String[] mimeTypeArray = mimeType.toLowerCase().split("/");
            switch (mimeTypeArray[0]) {
                case "image": {
                    runner.variable("codeVal", "kp.ft.asset." + mimeTypeArray[1]);
                    runner.variable("mediaTypeVal", mimeTypeArray[0]);
                    break;
                }
                case "video": {
                    runner.variable("codeVal", "kp.ft.asset." + mimeTypeArray[1]);
                    runner.variable("mediaTypeVal", mimeTypeArray[0]);
                    break;
                }
                case "audio": {
                    runner.variable("codeVal", "kp.ft.asset." + mimeTypeArray[1]);
                    runner.variable("mediaTypeVal", mimeTypeArray[0]);
                    break;
                }
            }
            return createContent(runner, runner.testContext, null, CREATE_ASSET_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }

    public static Map<String, Object> createCollectionContent(BaseCitrusTestRunner runner, String payload, String collectionType, Map<String, Object> headers) {
        String contentId = "KP_FT_12345";
        String versionKey = "12345";
        Map<String, Object> data = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payload)) {
            return createContent(runner, runner.testContext, payload, null, headers);
        } else if (StringUtils.isNotBlank(collectionType)) {
            runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
            switch (collectionType.toLowerCase()) {
                case "collection": {
                    runner.variable("codeVal", "kp.ft.collection." + collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "textbook": {
                    runner.variable("codeVal", "kp.ft.collection." + collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "course": {
                    runner.variable("codeVal", "kp.ft.collection." + collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "lessonplan": {
                    runner.variable("codeVal", "kp.ft.collection." + collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
            }
            return createContent(runner, runner.testContext, null, CREATE_COLLECTION_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }

    public static Map<String, Object> updateContentHierarchy(BaseCitrusTestRunner runner, String contentId, String collectionType, String resourceId, String payload, Map<String, Object> headers) {
        if (StringUtils.isNotBlank(payload)) {
            return updateContentHierarchy(runner, UPDATE_HIERARCHY_EXPECT_200, payload, headers);
        } else if (StringUtils.isNotBlank(collectionType)) {
            runner.variable("collectionIdVal", contentId);
            switch (collectionType.toLowerCase()) {
                case "collection": {
                    runner.variable("collectionTypeVal", "Collection");
                    runner.variable("contentTypeVal", "Collection");
                    runner.variable("resourceIdVal", resourceId);
                    break;
                }
                case "textbook": {
                    runner.variable("collectionTypeVal", "TextBook");
                    runner.variable("contentTypeVal", "TextBookUnit");
                    runner.variable("resourceIdVal", resourceId);
                    break;
                }
                case "course": {
                    runner.variable("collectionTypeVal", "Course");
                    runner.variable("contentTypeVal", "CourseUnit");
                    runner.variable("resourceIdVal", resourceId);
                    break;
                }
                case "lessonplan": {
                    runner.variable("collectionTypeVal", "LessonPlan");
                    runner.variable("contentTypeVal", "LessonPlanUnit");
                    runner.variable("resourceIdVal", StringUtils.capitalize(resourceId));
                    break;
                }
            }
            return updateContentHierarchy(runner, UPDATE_HIERARCHY_EXPECT_200,null, headers);
        }
        return null;
    }

    public static Map<String, Object> updateContentHierarchy(BaseCitrusTestRunner runner, String testName, String payload, Map<String, Object> headers) {
        TestContext testContext = runner.testContext;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPatchRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.UPDATE_CONTENT_HIERARCHY),
                                    APIUrl.UPDATE_CONTENT_HIERARCHY,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    payload,
                                    getHeaders(headers)));
        } else {
            runner.http(
                    builder ->
                            TestActionUtil.processPatchRequest(
                                    builder,
                                    getEndPoint(APIUrl.UPDATE_CONTENT_HIERARCHY),
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    APIUrl.UPDATE_CONTENT_HIERARCHY,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));

        }
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.UPDATE_CONTENT_HIERARCHY),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        return getResult(testContext);
    }

    public static Map<String, Object> readContent(BaseCitrusTestRunner runner, String contentId, String mode, String fields) {
        String testName = "readContentExpect200";
        String url = APIUrl.READ_CONTENT + contentId;
        if (StringUtils.isNotBlank(mode)) {
            url = url + "?mode=" + mode.trim();
        }
        if (StringUtils.isNotBlank(fields)) {
            if (url.contains("mode="))
                url = url + "&fields=" + fields.trim();
            else url = url + "?fields=" + fields.trim();
        }

        final String reqUrl = url;
        if (StringUtils.isNotBlank(contentId)) {
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processGetRequest(
                                    builder,
                                    getEndPoint(APIUrl.READ_CONTENT),
                                    testName,
                                    reqUrl,
                                    getHeaders(null)
                            ));
            runner.http(
                    builder ->
                            TestActionUtil.getExtractFromResponseTestAction(
                                    runner.testContext,
                                    builder,
                                    getEndPoint(APIUrl.READ_CONTENT),
                                    HttpStatus.OK,
                                    "$.result",
                                    "result"));
            Map<String, Object> result = getResult(runner.testContext);
            return result;
        }
        return null;
    }


    public static Map<String, Object> readCollectionHierarchy(BaseCitrusTestRunner runner, String contentId) {
        if (StringUtils.isNotBlank(contentId)) {
            runner.http(
                    builder ->
                            TestActionUtil.processGetRequest(
                                    builder,
                                    getEndPoint(APIUrl.READ_CONTENT_HIERARCHY),
                                    runner.getTestCase().getName(),
                                    APIUrl.READ_CONTENT_HIERARCHY + contentId,
                                    getHeaders(null)
                            ));
            runner.http(
                    builder ->
                            TestActionUtil.getExtractFromResponseTestAction(
                                    runner.testContext,
                                    builder,
                                    getEndPoint(APIUrl.READ_CONTENT_HIERARCHY),
                                    HttpStatus.OK,
                                    "$.result",
                                    "result"));
            Map<String, Object> result = getResult(runner.testContext);
            return result;
        }
        return null;
    }

    //TODO: Update File Name for All.
    public static Map<String, Object> uploadResourceContent(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String, Object> headers) {
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }
                case "application/vnd.ekstep.ecml-archive": {
                    runner.testContext.setVariable("fileNameValue", "ecml_with_json.zip");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }
                case "application/vnd.ekstep.html-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample_Html.zip");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }
                case "application/vnd.ekstep.h5p-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample_h5p_content.h5p");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }
                case "application/vnd.ekstep.plugin-archive": {
                    runner.testContext.setVariable("fileNameValue", "Custom_Plugin.zip");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }
                case "video/x-youtube": {
                    runner.testContext.setVariable("fileNameValue", "");
                    runner.testContext.setVariable("fileUrlValue", "https://www.youtube.com/watch?v=FfgT6zx4k3Q");
                    break;
                }
                case "video/mp4": {
                    runner.testContext.setVariable("fileNameValue", "sample.mp4");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }
                case "video/webm": {
                    runner.testContext.setVariable("fileNameValue", "sample.webm");
                    runner.testContext.setVariable("fileUrlValue", "");
                    break;
                }

            }
            return uploadContent(runner, runner.testContext, UPLOAD_RESOURCE_CONTENT_EXPECT_200, contentId, headers);
        }
        return null;
    }

    public static Map<String, Object> uploadAssetContent(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String, Object> headers) {
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "image/jpeg": {
                    runner.testContext.setVariable("fileNameValue", "sample.jpeg");
                    break;
                }
                case "image/png": {
                    runner.testContext.setVariable("fileNameValue", "sample.png");
                    break;
                }
                case "image/jpg": {
                    runner.testContext.setVariable("fileNameValue", "sample.jpg");
                    break;
                }
                case "video/mp4": {
                    runner.testContext.setVariable("fileNameValue", "sample.mp4");
                    break;
                }
                case "video/webm": {
                    runner.testContext.setVariable("fileNameValue", "sample.webm");
                    break;
                }
                case "video/mpeg": {
                    runner.testContext.setVariable("fileNameValue", "sample.mpeg");
                    break;
                }
                case "audio/mp3": {
                    runner.testContext.setVariable("fileNameValue", "sample.mp3");
                    break;
                }
                case "application/vnd.ekstep.h5p-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample_h5p_content.h5p");
                    break;
                }
                case "video/x-youtube": {
                    runner.testContext.setVariable("fileNameValue", "");
                    runner.testContext.setVariable("fileUrlValue", "https://www.youtube.com/watch?v=FfgT6zx4k3Q");
                    break;
                }
            }
            return uploadContent(runner, runner.testContext, UPLOAD_ASSET_CONTENT_EXPECT_200, contentId, headers);
        }
        return null;
    }


    private static Map<String, Object> createContent(BaseCitrusTestRunner runner, TestContext testContext, String payload, String testName, Map<String, Object> headers) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(payload)) {
            System.out.println("Dynamic Payload...");
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.CREATE_CONTENT),
                                    APIUrl.CREATE_CONTENT,
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
                                    getEndPoint(APIUrl.CREATE_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    APIUrl.CREATE_CONTENT,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.CREATE_CONTENT),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);

        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", (String) result.get("node_id"));
            data.put("versionKey", (String) result.get("versionKey"));
        }

        return data;
    }

    private static Map<String, Object> uploadContent(BaseCitrusTestRunner runner, TestContext testContext, String testName, String contentId, Map<String, Object> headers) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(testName)) {
            System.out.println("Static Payload Used With Dynamic Values...");
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processMultipartRequest(
                                    testContext,
                                    builder,
                                    getEndPoint(APIUrl.UPLOAD_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    APIUrl.UPLOAD_CONTENT + contentId,
                                    Constant.REQUEST_FORM_DATA,
                                    getHeaders(headers),
                                    runner.getClass().getClassLoader()
                            ));

        }
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.UPLOAD_CONTENT),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);

        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", (String) result.get("node_id"));
            data.put("versionKey", (String) result.get("versionKey"));
            data.put("content_url", (String) result.get("content_url"));
        }
        return data;
    }

    public static Map<String, Object> publishContent(BaseCitrusTestRunner runner, String payload, String publishType, String contentId, Map<String, Object> headers) {
        final String apiUrl = StringUtils.equalsIgnoreCase("unlisted", publishType.toLowerCase()) ? (APIUrl.UNLISTED_PUBLISH_CONTENT) : (APIUrl.PUBLIC_PUBLISH_CONTENT);
        final String url = apiUrl + contentId;
        final TestContext testContext = runner.testContext;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    getEndPoint(apiUrl),
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else {
            runner.getTestCase().setName(PUBLISH_CONTENT_EXPECT_200);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    getEndPoint(apiUrl),
                                    CONTENT_PAYLOAD_DIR,
                                    PUBLISH_CONTENT_EXPECT_200,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }
        runner.sleep(Constant.PUBLISH_WAIT_TIME);

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(apiUrl),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result))
            data.put("content_id", result.get("node_id"));
        return data;
    }

    /**
     * This method can review content with a static payload.
     * TODO: NEED TO ADD FOR DYNAMIC PAYLOAD
     *
     * @param runner
     * @param payload
     * @param testName
     * @param contentId
     * @param headers
     * @return
     */

    public static Map<String, Object> reviewContent(BaseCitrusTestRunner runner, String payload, String testName, String contentId, Map<String, Object> headers) {
        final TestContext testContext = runner.testContext;
        final String url = APIUrl.REVIEW_CONTENT + contentId;
        final String updatedTestName = StringUtils.isBlank(testName) ? REVIEW_RESOURCE_CONTENT_EXPECT_200 : testName;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.REVIEW_CONTENT),
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else if (StringUtils.isNotBlank(updatedTestName)) {
            runner.getTestCase().setName(updatedTestName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    getEndPoint(APIUrl.REVIEW_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    updatedTestName,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.REVIEW_CONTENT),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", result.get("node_id"));
            data.put("versionKey", (String) result.get("versionKey"));
        }

        return data;
    }

    /**
     * This method can update content with static payload
     * TODO: Need to add dynamic payload
     *
     * @param runner
     * @param payload
     * @param testName
     * @param contentId
     * @param headers
     * @return
     */
    public static Map<String, Object> updateContent(BaseCitrusTestRunner runner, String payload, String testName, String contentId, Map<String, Object> headers) {
        final String url = APIUrl.UPDATE_CONTENT + contentId;
        final TestContext testContext = runner.testContext;
        final String updatedTestName = StringUtils.isBlank(testName) ? UPDATE_RESOURCE_CONTENT_EXPECT_200 : testName;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPatchRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.UPDATE_CONTENT),
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else if (StringUtils.isNotBlank(updatedTestName)) {
            runner.getTestCase().setName(updatedTestName);
            runner.http(
                    builder ->
                            TestActionUtil.processPatchRequest(
                                    builder,
                                    getEndPoint(APIUrl.UPDATE_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    updatedTestName,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.UPDATE_CONTENT),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", result.get("node_id"));
            data.put("versionKey", result.get("versionKey"));
        }
        return data;
    }

    public static Map<String, Object> discardContent(BaseCitrusTestRunner runner, String contentId, Map<String, Object> headers) {
        final String url = APIUrl.DISCARD_CONTENT + contentId;
        runner.http(
                builder ->
                        TestActionUtil.processDeleteRequest(
                                builder,
                                getEndPoint(APIUrl.DISCARD_CONTENT),
                                CONTENT_PAYLOAD_DIR,
                                DISCARD_CONTENT_EXPECT_200,
                                url,
                                Constant.REQUEST_JSON,
                                Constant.CONTENT_TYPE_APPLICATION_JSON,
                                getHeaders(headers)));

        runner.variable("contentIdVal", contentId);
        runner.http(
                builder ->
                        TestActionUtil.getResponse(builder,
                                getEndPoint(APIUrl.DISCARD_CONTENT),
                                CONTENT_PAYLOAD_DIR,
                                DISCARD_CONTENT_EXPECT_200,
                                HttpStatus.OK,
                                Constant.RESPONSE_JSON,
                                null));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("content_id", contentId);
        return data;
    }

    public static Map<String, Object> flagContent(BaseCitrusTestRunner runner, String payload, String testName, String contentId, Map<String, Object> headers) {
        final TestContext testContext = runner.testContext;
        final String url = APIUrl.FLAG_CONTENT + contentId;
        final String updatedTestName = StringUtils.isBlank(testName) ? FLAG_RESOURCE_CONTENT_EXPECT_200 : testName;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.FLAG_CONTENT),
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else if (StringUtils.isNotBlank(updatedTestName)) {
            runner.getTestCase().setName(updatedTestName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    getEndPoint(APIUrl.FLAG_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    updatedTestName,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.FLAG_CONTENT),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", result.get("node_id"));
            data.put("versionKey", result.get("versionKey"));
        }
        return data;
    }


    public static Map<String, Object> acceptFlagContent(BaseCitrusTestRunner runner, String payload, String testName, String contentId, Map<String, Object> headers) {
        final String url = APIUrl.ACCEPT_FLAG_CONTENT + contentId;
        final String updatedTestName = StringUtils.isBlank(testName) ? ACCEPT_FLAG_RESOURCE_CONTENT_EXPECT_200 : testName;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.ACCEPT_FLAG_CONTENT),
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else if (StringUtils.isNotBlank(updatedTestName)) {
            runner.getTestCase().setName(updatedTestName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    getEndPoint(APIUrl.ACCEPT_FLAG_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    updatedTestName,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getResponse(
                                builder,
                                getEndPoint(APIUrl.ACCEPT_FLAG_CONTENT),
                                testName,
                                HttpStatus.OK));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("content_id", contentId);
        return data;
    }

    public static Map<String, Object> rejectFlagContent(BaseCitrusTestRunner runner, String payload, String contentId, Map<String, Object> headers) {
        final String url = APIUrl.REJECT_FLAG_CONTENT + contentId;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.REJECT_FLAG_CONTENT),
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else {
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    getEndPoint(APIUrl.REJECT_FLAG_CONTENT),
                                    CONTENT_PAYLOAD_DIR,
                                    REJECT_FLAG_CONTENT_EXPECT_200,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getResponse(
                                builder,
                                getEndPoint(APIUrl.REJECT_FLAG_CONTENT),
                                REJECT_FLAG_CONTENT_EXPECT_200,
                                HttpStatus.OK));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("content_id", contentId);
        return data;
    }


    public static Map<String, Object> retireContent(BaseCitrusTestRunner runner, String contentId, Map<String, Object> headers) {
        final String url = APIUrl.RETIRE_CONTENT + contentId;
        runner.getTestCase().setName(RETIRE_CONTENT_EXPECT_200);
        runner.http(
                builder ->
                        TestActionUtil.processDeleteRequest(
                                builder,
                                getEndPoint(APIUrl.RETIRE_CONTENT),
                                CONTENT_PAYLOAD_DIR,
                                RETIRE_CONTENT_EXPECT_200,
                                url,
                                Constant.REQUEST_JSON,
                                Constant.CONTENT_TYPE_APPLICATION_JSON,
                                getHeaders(headers)));

        runner.variable("contentIdVal", contentId);
        runner.http(
                builder ->
                        TestActionUtil.getResponse(builder,
                                getEndPoint(APIUrl.RETIRE_CONTENT),
                                CONTENT_PAYLOAD_DIR,
                                RETIRE_CONTENT_EXPECT_200,
                                HttpStatus.OK,
                                Constant.RESPONSE_JSON,
                                null));
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("content_id", contentId);
        return data;
    }

    public static Map<String, Object> systemUpdate(BaseCitrusTestRunner runner, TestContext testContext, String objectId, String payload, String testName, Map<String, Object> headers) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(payload) && StringUtils.isNotBlank(objectId)) {
            System.out.println("Dynamic Payload...");
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.getPatchRequestTestAction(
                                    builder,
                                    getEndPoint(APIUrl.SYSTEM_UPDATE),
                                    APIUrl.SYSTEM_UPDATE + objectId,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));

        } else {
            System.out.println("Received Invalid Request for System Update Api!");
            //TODO: Provide static payload implementation here.
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                getEndPoint(APIUrl.SYSTEM_UPDATE),
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = getResult(testContext);

        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", (String) result.get("node_id"));
            data.put("versionKey", (String) result.get("versionKey"));
        }

        return data;
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
        headers.put(Constant.X_CONSUMER_ID,  UUID.randomUUID().toString());

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

    public static Map<String, Supplier<Map<String, Object>>> getContentWorkflowMap(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String, Object> headers) {
        return new HashMap<String, Supplier<Map<String, Object>>>() {
            {
                put("Upload", () -> ContentUtil.uploadResourceContent(runner, contentId, mimeType, headers));
                put("Publish", () -> ContentUtil.publishContent(runner, null, "listed", contentId, headers));
                put("Review", () -> ContentUtil.reviewContent(runner, null, null, contentId, headers));
                put("Update", () -> ContentUtil.updateContent(runner, null, null, contentId, headers));
                put("Unlisted", () -> ContentUtil.publishContent(runner, null, "unlisted", contentId, headers));
                put("Retire", () -> ContentUtil.retireContent(runner, contentId, headers));
                put("Discard", () -> ContentUtil.discardContent(runner, contentId, headers));
                put("Flag", () -> ContentUtil.flagContent(runner, null, null, contentId, headers));
                put("AcceptFlag", () -> ContentUtil.acceptFlagContent(runner, null, null, contentId, headers));
                put("RejectFlag", () -> ContentUtil.rejectFlagContent(runner, null, contentId, headers));
                put("Get", () -> ContentUtil.readContent(runner, contentId, null, null));
            }
        };
    }

    public static Map<String, Supplier<Map<String, Object>>> getAssetWorkFlowMap(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String, Object> headers) {
        return new HashMap<String, Supplier<Map<String, Object>>>() {
            {
                put("Upload", () -> ContentUtil.uploadAssetContent(runner, contentId, mimeType, headers));
                put("Retire", () -> ContentUtil.retireContent(runner, contentId, headers));
                put("Discard", () -> ContentUtil.discardContent(runner, contentId, headers));
                put("Flag", () -> ContentUtil.flagContent(runner, null, null, contentId, headers));
                put("AcceptFlag", () -> ContentUtil.acceptFlagContent(runner, null, null, contentId, headers));
                put("RejectFlag", () -> ContentUtil.rejectFlagContent(runner, null, contentId, headers));
                put("Get", () -> ContentUtil.readContent(runner, contentId, null, null));
            }
        };
    }

    public static Map<String, Supplier<Map<String, Object>>> getCollectionWorkFlowMap(BaseCitrusTestRunner runner, String contentId, String testName, String payload, Map<String, Object> headers) {
        return new HashMap<String, Supplier<Map<String, Object>>>() {
            {
                put("Publish", () -> ContentUtil.publishContent(runner, null, "listed", contentId, headers));
                put("Update", () -> ContentUtil.updateContentHierarchy(runner, testName, payload, headers));
                put("Retire", () -> ContentUtil.retireContent(runner, contentId, headers));
                put("Discard", () -> ContentUtil.discardContent(runner, contentId, headers));
                put("Flag", () -> ContentUtil.flagContent(runner, null, null, contentId, headers));
                put("AcceptFlag", () -> ContentUtil.acceptFlagContent(runner, null, null, contentId, headers));
                put("RejectFlag", () -> ContentUtil.rejectFlagContent(runner, null, contentId, headers));
                put("Get", () -> ContentUtil.readCollectionHierarchy(runner, contentId));
                put("Create", () -> ContentUtil.readCollectionHierarchy(runner, contentId));
                put("Review", () -> ContentUtil.reviewContent(runner, payload, testName, contentId, headers));
            }
        };
    }

    private static String getEndPoint(String reqUrl) {
        return CS_API_LIST.contains(reqUrl) ? Constant.KP_CONTENT_SERVICE_ENDPOINT : Constant.KP_ENDPOINT;
    }
}

