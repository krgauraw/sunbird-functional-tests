package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class DiscardContentTest extends BaseCitrusTestRunner {
    private static final String TEMPLATE_DIR = "templates/content/v3/discard";

    @Test(dataProvider = "discardContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "needImage", "workflow"})
    @CitrusTest
    public void testDiscardContent(
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

    @Test(dataProvider = "discardCollection")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "needImage", "collectionType"})
    @CitrusTest
    public void testDiscardCollection(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, Boolean needImage, String collectionType) {
        getAuthToken(this, userType);
        Map<String, Object> map = ContentUtil.createCollectionContent(this, null, collectionType, null);
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
                valParams,
                RESPONSE_JSON
        );
    }

    @DataProvider(name = "discardContent")
    public Object[][] discardContent() {
        return new Object[][]{
                //Valid request (200) For Discard Content
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_DRAFT, APIUrl.DISCARD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },

                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_DRAFT, APIUrl.DISCARD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraftUpdated"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_LIVE_WITH_IMAGE_DRAFT, APIUrl.DISCARD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInLiveImageDraft"
                },
                //Invalid Request Format (400) requests are here
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_LIVE_WITH_IMAGE_REVIEW, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInLiveImageReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_LIVE, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInLive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_REVIEW, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_WITH_IMAGE_ID, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", true, "contentInLiveImageDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_UNLISTED, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInUnlisted"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_FLAGGED, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInFlagged"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_FLAGREVIEW, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInFlagReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_CONTENT_STATUS_RETIRED, APIUrl.DISCARD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentRetired"
                },

        };
    }

    @DataProvider(name = "discardCollection")
    public Object[][] discardCollection() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_DISCARD_COLLECTION_STATUS_DRAFT, APIUrl.DISCARD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, false, "Collection"
                }
        };
    }

}
