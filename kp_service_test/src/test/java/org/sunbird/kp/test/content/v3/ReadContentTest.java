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

import java.util.HashMap;
import java.util.Map;

/**
 * Integration Test Cases for Content Read API
 * @author Kumar Gauraw
 */
public class ReadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/read";

    @Test(dataProvider = "readResourceContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType","valParams","mimeType"})
    @CitrusTest
    public void testReadResourceContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @DataProvider(name = "readResourceContent")
    public Object[][] readResourceContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_VALID_IDENTIFIER, APIUrl.READ_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        new HashMap(){{put("identifier",null);put("versionKey",null);put("mimeType","application/pdf");put("status","Draft");}}, "application/pdf"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_VALID_IDENTIFIER, APIUrl.READ_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        new HashMap(){{put("identifier",null);put("versionKey",null);put("mimeType","video/x-youtube");put("status","Draft");}}, "video/x-youtube"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_INVALID_IDENTIFIER, APIUrl.READ_CONTENT, HttpStatus.NOT_FOUND, Constant.CREATOR,
                        null, null
                }
        };
    }
}
