package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.CollectionUtil;
import org.sunbird.kp.test.util.CollectionUtilPayload;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class RetireContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/retire";

    @Test(dataProvider = "retireContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "needImage", "workflow"})
    @CitrusTest
    public void testRetireContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType,
            Map<String, Object> valParams, String mimeType, Boolean needImage, String workflow) {
        getAuthToken(this, userType);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);
        String contentId = (String) map.get("content_id");
        this.variable("contentIdVal", contentId);
        contentId = needImage ? contentId + IMAGE_SUFFIX : contentId;
        performDeleteTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "retireCollection")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "payload", "needImage", "collectionType", "workflow", "assetCount", "resourceCount"})
    @CitrusTest
    public void testRetireCollectionContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, String payloadForHierarchy, Boolean needImage,
            String collectionType, String workflow, Integer assetCount, Integer resourceCount) {
        getAuthToken(this, userType);
        Map<String, Object> map = CollectionUtil.prepareTestCollection(workflow,this, new HashMap<String,String>() {{put("updateHierarchy",payloadForHierarchy);}}, collectionType, assetCount, resourceCount, null);
        System.out.println(map);
        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        contentId = needImage ? contentId + IMAGE_SUFFIX : contentId;
        performDeleteTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                null,
                RESPONSE_JSON
        );
    }


    @DataProvider(name = "retireContent")
    public Object[][] retireContent() {
        return new Object[][]{
                // Valid request (200) For Retire Content
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_DRAFT, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_DRAFT, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraftUpdated"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_LIVE_WITH_IMAGE_DRAFT, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInLiveImageDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_LIVE_WITH_IMAGE_REVIEW, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInLiveImageReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_LIVE, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInLive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_REVIEW, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_WITH_IMAGE_ID, APIUrl.RETIRE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", true, "contentInLiveImageDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_UNLISTED, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInUnlisted"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_FLAGDRAFT, APIUrl.RETIRE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInFlagDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_FLAGGED, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInFlagged"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_FLAGREVIEW, APIUrl.RETIRE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInFlagReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_CONTENT_STATUS_RETIRED, APIUrl.RETIRE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", true, "contentInUnlisted"
                }
        };
    }

    @DataProvider(name = "retireCollection")
    public Object[][] retireCollection() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_COLLECTION_STATUS_DRAFT, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE, false, "TextBook", "collectionUnitsInDraft", 0, 1
                },
                new Object[]{
                        ContentV3Scenario.TEST_RETIRE_COLLECTION_STATUS_DRAFT, APIUrl.RETIRE_CONTENT, HttpStatus.OK, Constant.CREATOR, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_ASSET, false, "TextBook", "collectionUnitsInDraft", 1, 0
                }
        };

    }

}
