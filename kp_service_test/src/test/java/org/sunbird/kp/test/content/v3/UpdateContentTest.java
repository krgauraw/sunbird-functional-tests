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

public class UpdateContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/update";

    @Test(dataProvider = "updateResourceContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "needImage", "workflow"})
    @CitrusTest
    public void testUpdateResourceContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType,
            Map<String, Object> valParams, String mimeType, Boolean needImage, String workflow) {
        getAuthToken(this, userType);
        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);        String contentId = (String) map.get("content_id");
        String versionKey = (String) map.get("versionKey");
        this.variable("versionKeyVal", versionKey);
        this.variable("contentIdVal", contentId);
        contentId = needImage? contentId + IMAGE_SUFFIX : contentId;
        performPatchTest(
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


    @DataProvider(name = "updateResourceContent")
    public Object[][] updateResourceContent() {
        return new Object[][]{
                //Valid Requests (200) are here
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.html-archive", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.h5p-archive", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "video/x-youtube", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_NEW_FRAMEWORK, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_NEW_MIMETYPE, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_NEW_MEDIATYPE, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_VALID_CONTENT_TYPE, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_STATUS_REVIEW, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInReview"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_IN_LIVE, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInLive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_IN_LIVE_WITH_IMAGE, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInLiveImageDraft"
                },
//                new Object[]{
//                        ContentV3Scenario.TEST_UPDATE_CONTENT_IN_RETIRED, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentRetired"
//                },

                 //Invalid Request Format (400) requests are here

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_BLANK_VERSION_KEY, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_VERSION_KEY, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_CHANGED_STATUS, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_METADATA, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_INVALID_CONTENT_TYPE, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_WITH_SYSTEM_PROPERTY, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_STATUS_FLAGGED, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInFlagged"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_STATUS_FLAGREVIEW, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInFlagReview"
                },

                // Resource Not Found requests (404) are here

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_WITH_NOT_FOUND_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.NOT_FOUND, Constant.CREATOR, null, null, false, "contentInDraft"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_WITH_IMAGE_ID, APIUrl.UPDATE_CONTENT, HttpStatus.NOT_FOUND, Constant.CREATOR, null, "application/pdf", true, "contentInDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_CONTENT_AFTER_DISCARD, APIUrl.UPDATE_CONTENT, HttpStatus.NOT_FOUND, Constant.CREATOR, null, "application/pdf", false, "contentDiscarded"
                },

                // Resources with Server (500) errors are here
                new Object[]{
                        ContentV3Scenario.TEST_UPDATE_WITH_INVALID_FORMAT_RESERVED_DIALCODES, APIUrl.UPDATE_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
                },

        };
    }

}
