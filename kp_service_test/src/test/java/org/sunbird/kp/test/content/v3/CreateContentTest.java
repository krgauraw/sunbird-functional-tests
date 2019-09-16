package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.MetadataValidationUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Integration Test Cases for Content Create API
 *
 * @author Kumar Gauraw
 *
 * Number of testcases for Create : 33 (Please increment this number)
 * Last Count Update: 03-09-2019
 */
public class CreateContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/create";
    private String identifier;

    @Test(dataProvider = "createResourceContentWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateResourceContentWithValidRequest(String testName) {
        identifier = "KP_FT" + generateRandomDigits(9);
        this.variable("contentIdVal", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
        Map<String, Object> readResult = ContentUtil.readContent(this, identifier, null, null);
        Assert.assertTrue(MetadataValidationUtil.validateMetadataAfterCreate(readResult));
    }

    @Test(dataProvider = "createAssetContentWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateAssetContentWithValidRequest(String testName) {
        identifier = "KP_FT" + generateRandomDigits(9);
        this.variable("contentIdVal", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
        Map<String, Object> readResult = ContentUtil.readContent(this, identifier, null, null);
        assert MetadataValidationUtil.validateMetadataAfterCreate(readResult);
    }

    @Test(dataProvider = "createResourceContentWithInvalidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void createResourceContentWithInvalidRequest(String testName) {
        identifier = "KP_FT" + generateRandomDigits(9);
        this.variable("contentIdVal", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.BAD_REQUEST,
                null,
                RESPONSE_JSON
        );
    }

    @Test
    @CitrusTest
    public void testCreateResourcePdfContentWithDuplicateIdentifier() {
        getAuthToken(this, Constant.CREATOR);
        String contentId = (String) ContentUtil.createResourceContent(this, null, "application/pdf", null).get("content_id");
        this.variable("identifierVal", contentId);
        performPostTest(
                this,
                TEMPLATE_DIR,
                ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_DUPLICATE_IDENTIFIER,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.BAD_REQUEST,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "createPluginContent")
    @CitrusParameters({"testName", "valid", "httpStatus"})
    @CitrusTest
    public void testCreatePluginContent(String testName, Boolean valid, HttpStatus httpStatus) {
        identifier = "KP_FT" + generateRandomDigits(9);
        this.variable("contentIdVal", identifier);
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatus,
                null,
                RESPONSE_JSON
        );
        if (valid) {
            Map<String, Object> readResult = ContentUtil.readContent(this, identifier, null, null);
            MetadataValidationUtil.validateMetadataAfterCreate(readResult);
        }
    }

    @DataProvider(name = "createResourceContentWithValidRequest")
    public Object[][] createResourceContentWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_PLUGIN_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_WITH_CONCEPTS
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_WITH_QUESTIONS
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_WITH_SCREENSHOTS
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_CONTENT_WITH_ECML_BODY
                }

        };
    }

    @DataProvider(name = "createPluginContent")
    public Object[][] createPluginContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_PLUGIN_CONTENT_WITH_VALID_REQUEST, true, HttpStatus.OK
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_PLUGIN_CONTENT_WITH_NO_CODE_GIVEN, false, HttpStatus.BAD_REQUEST
                }
        };
    }


    @DataProvider(name = "createAssetContentWithValidRequest")
    public Object[][] createAssetContentWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_WEBM_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_IMAGE_JPEG_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_IMAGE_PNG_CONTENT_WITH_VALID_REQUEST
                }
        };
    }

    @DataProvider(name = "createResourceContentWithInvalidRequest")
    public Object[][] createResourceContentWithInvalidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_NAME
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_CODE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_MIMETYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_CONTENT_TYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITH_SYSTEM_PROPS
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_MIMETYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_CONTENT_TYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_WITH_INVALID_OS_NAME
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_WITH_INVALID_CONCEPT_ID
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_CONTENT_WITH_INVALID_LICENSE_TYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_CONTENT_WITH_VALID_FRAMEWORK_INVALID_SUBJECT
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_CONTENT_WITH_INVALID_FRAMEWORK
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_CONTENT_WITH_INVALID_RESOURCE_TYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_CONTENT_WITH_VALID_FRAMEWORK_INVALID_CATEGORY
                }
        };
    }
}
