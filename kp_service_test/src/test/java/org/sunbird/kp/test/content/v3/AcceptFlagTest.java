package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.common.Response;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class AcceptFlagTest extends BaseCitrusTestRunner {


    private static final String TEMPLATE_DIR = "templates/content/v3/acceptflag";


    private String doFlagAccept(String testName, String mimeType, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowType){
        String contentId = (String)ContentUtil.prepareResourceContent(workFlowType,this, null, mimeType, null).get("content_id");
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.ACCEPT_FLAG_CONTENT + contentId,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
        return contentId;

    }

    @Test(dataProvider = "acceptFlagContent")
    @CitrusParameters({"testName", "userType", "mimeType", "httpStatusCode", "valParams"})
    @CitrusTest
    public void testAcceptFlagContent(String testName, String userType, String mimeType, HttpStatus httpStatusCode, Map<String, Object> valParams) {
        getAuthToken(this, userType);

        String contentId = null;

        switch (testName){
            case ContentV3Scenario.TEST_ACCEPT_FLAG_VALID_ID_AND_VALID_STATUS : {
                contentId = doFlagAccept(testName, mimeType, httpStatusCode, valParams, "contentInFlagged");
                Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");
                Assert.assertTrue(StringUtils.equals(contentMap.get("status").toString(), "Retired"));
                Map<String, Object> contentMapImg = (Map<String, Object>) ContentUtil.readContent(this, contentId+".img", null, null).get("content");
                Assert.assertTrue(MapUtils.isNotEmpty(contentMapImg));
                Assert.assertTrue(StringUtils.equals(contentMapImg.get("status").toString(), "FlagDraft"));
                break;
            }

            case  ContentV3Scenario.TEST_ACCEPT_FLAG_VALID_ID_AND_INVALID_STATUS : {
                doFlagAccept(testName, mimeType, httpStatusCode, valParams, "contentInReview");
                break;

            }

            case ContentV3Scenario.TEST_REACCEPT_FLAG_VALID_ID_AND_VALID_STATUS : {
                contentId = ContentUtil.prepareResourceContent("contentInFlag", this,null, mimeType,null).get("content_id").toString();//doFlagAccept(testName, mimeType, httpStatusCode, valParams, "contentInFlagged");
                performPostTest(this, TEMPLATE_DIR, testName, APIUrl.ACCEPT_FLAG_CONTENT + contentId, null, REQUEST_JSON, MediaType.APPLICATION_JSON, httpStatusCode, valParams, RESPONSE_JSON);
                break;
            }
            case ContentV3Scenario.TEST_ACCEPT_FLAG_INVALID_ID : {
                performPostTest(this, TEMPLATE_DIR, testName, APIUrl.ACCEPT_FLAG_CONTENT + "invalidId", null, REQUEST_JSON, MediaType.APPLICATION_JSON, httpStatusCode, valParams, RESPONSE_JSON);
                break;
            }

        }

    }

    @DataProvider(name = "acceptFlagContent")
    public Object[][] acceptFlagContent() {
        return new Object[][]{
                //accept flag with valid scenarios
                new Object[]{ContentV3Scenario.TEST_ACCEPT_FLAG_VALID_ID_AND_VALID_STATUS, Constant.CREATOR, "application/pdf", HttpStatus.OK, null},
                // accept flag for invalid scenarios
                new Object[]{ContentV3Scenario.TEST_ACCEPT_FLAG_VALID_ID_AND_INVALID_STATUS, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null},
                new Object[]{ContentV3Scenario.TEST_REACCEPT_FLAG_VALID_ID_AND_VALID_STATUS, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null},
                new Object[]{ContentV3Scenario.TEST_ACCEPT_FLAG_INVALID_ID, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null},

        };
    }
}
