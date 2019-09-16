package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.WorkflowConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;
/**
 * Flag API test scenarios
 *
 * @author pritha
 */
public class RejectFlag extends BaseCitrusTestRunner{

    private static final String TEMPLATE_DIR = "templates/content/v3/rejectflag";


    @Test(dataProvider = "rerejectFlagContent")
    @CitrusParameters({"testName", "userType", "mimeType", "httpStatusCode", "valParams", "workFlowStatus"})
    @CitrusTest
    public void testReRejectFlagContent(String testName, String userType, String mimeType, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus) {
        testRejectFlagContent(testName, userType, mimeType, HttpStatus.OK, valParams, workFlowStatus, null);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.REJECT_FLAG_CONTENT + "${contentIdVal}",
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @DataProvider(name = "rerejectFlagContent")
    public Object[][] rerejectFlagContent() {
        return new Object[][]{
                new Object[]{"testReRejectFlagWithContentRejected", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_FLAG_STATE},

        };
    }

    @Test(dataProvider = "rejectFlagContent")
    @CitrusParameters({"testName", "userType", "mimeType", "httpStatusCode", "valParams", "workFlowStatus", "responseJson"})
    @CitrusTest
    public void testRejectFlagContent(String testName, String userType, String mimeType, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus, String responseJson) {

        getAuthToken(this, userType);
        String contentId = "invalidId";
        String versionKey = "invalidVersionKey";
        if(workFlowStatus != null) {
            Map<String, Object> resourceMap = ContentUtil.prepareResourceContent(workFlowStatus, this, null, mimeType, null);
            contentId = (String) resourceMap.get("content_id");
            Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "null", null).get("content");
        }
        this.variable("contentIdVal", contentId);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.REJECT_FLAG_CONTENT + contentId,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                responseJson
        );
    }

    @DataProvider(name = "rejectFlagContent")
    public Object[][] rejectFlagContent() {
        return new Object[][]{

                new Object[]{"testRejectFlagWithValidResource", Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_FLAG_STATE, RESPONSE_JSON},
                new Object[]{"testRejectFlagWithInValidRequest", Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_FLAG_STATE, RESPONSE_JSON},

                new Object[]{"testRejectFlagWithInValidResourceId", Constant.CREATOR, "application/pdf", HttpStatus.NOT_FOUND, null, null, RESPONSE_JSON},
                new Object[]{"testRejectFlagWithNoRequest", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_FLAG_STATE, null},
                new Object[]{"testRejectFlagWithFlagDraftContent", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_FLAG_DRAFT, RESPONSE_JSON},
                new Object[]{"testRejectFlagWithFlagReviewContent", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_FLAG_REVIEW, RESPONSE_JSON},
                new Object[]{"testRejectFlagWithReviewContent", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_REVIEW_STATE, RESPONSE_JSON},
                new Object[]{"testRejectFlagWithRetierContent", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_RETIRED_STATE, RESPONSE_JSON},
                new Object[]{"testRejectFlagWithLiveContent", Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_LIVE_STATE, RESPONSE_JSON},

        };
    }

}
