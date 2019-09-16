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

import java.util.Map;
import javax.ws.rs.core.MediaType;

/**
 * Functional test cases for Review API
 * @author pritha
 */
public class ReviewContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/review";

    @Test(dataProvider = "reviewContent")
    @CitrusParameters({"testName", "httpStatusCode", "userType","valParams","mimeType", "workflow", "withValidId"})
    @CitrusTest
    public void testReviewContent(
            String testName, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String workflow, boolean withValidId) {
        getAuthToken(this, userType);

        String contentId = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null).get("content_id").toString();
        String identifier = withValidId ? contentId : "invalidId";
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.REVIEW_CONTENT + identifier,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @DataProvider(name = "reviewContent")
    public Object[][] reviewContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_VALID_IDENTIFIER_WITH_NOT_UPLOAD_FILE, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/pdf", "contentInDraft", true
                },
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_VALID_IDENTIFIER, HttpStatus.OK, Constant.REVIEWER,
                        null, "application/pdf", "contentUpload",true
                },
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_REVIEWED_CONTENT,  HttpStatus.OK, Constant.CREATOR,
                        null, "application/pdf", "contentInReview", true
                },
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_INVALID_IDENTIFIER,  HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/pdf", "contentInDraft", false
                }
        };
    }

}
