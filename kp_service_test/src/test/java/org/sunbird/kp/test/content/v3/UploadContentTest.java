package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
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

import java.util.Map;

/**
 * Functional test cases for Upload API
 *
 * @author pritha
 */
public class UploadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/upload";
    private static final String MATCHED_EXTENSION = ".pdf";
    private static final String FILE_URL = "https://ekstep-public-dev.s3-ap-south-1.amazonaws.com/content/do_112513878123618304117/artifact/test_1527573671403.pdf";

    @Test(dataProvider = "uploadResourceContentInLiveWithFile")
    @CitrusParameters({"testName", "userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentInLiveWithFile(String testName, String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        this.getTestCase().setName(testName);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        System.out.println("Resource Content Id:" + contentId);
        ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        ContentUtil.publishContent(this, null, "public", contentId, null);

        Map<String, Object> resourceMapBeforeUpload = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");
        String versionKeyBeforUpload = (String) resourceMapBeforeUpload.get("versionKey");
        String statusBeforeUpload = (String)resourceMapBeforeUpload.get("status");
        Assert.assertTrue(statusBeforeUpload.equals("Live"));

        Map<String, Object> result = ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));

        Map<String, Object> resourceMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");
        System.out.println("resourceMap == "+resourceMap);
        String status = (String)resourceMap.get("status");
        String versionKey = (String) resourceMap.get("versionKey");
        Assert.assertTrue(status.equals("Draft"));

        Assert.assertFalse(versionKeyBeforUpload.equals(versionKey));

    }



    @Test(dataProvider = "uploadResourceContentWithFileMimeTypes")
    @CitrusParameters({"testName", "userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentWithFileMimeTypes(String testName, String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        this.getTestCase().setName(testName);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @Test(dataProvider = "uploadAssetContentWithFileMimeTypes")
    @CitrusParameters({"testName", "userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadAssetContentWithFileMimeTypes(String testName, String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        this.getTestCase().setName(testName);
        String contentId = (String) ContentUtil.createAssetContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadAssetContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @Test(dataProvider = "uploadResourceContentWithFileUrl")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentWithFileUrl(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        System.out.println("created contentId = " + contentId);
        setContext(this, contentId, mimeType, extension, null, FILE_URL);
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                Constant.REQUEST_FORM_DATA,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "uploadResourceContentWithFile")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "extension", "fileName"})
    @CitrusTest
    public void testUploadResourceContentWithFile(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String extension, String fileName) {
        getAuthToken(this, userType);
        Map<String, Object> resourceMap = ContentUtil.createResourceContent(this, null, mimeType, null);
        String contentId = (String) resourceMap.get("content_id");
        this.variable("contentIdVal", contentId);

        setContext(this, contentId, mimeType, extension, fileName, "");
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                Constant.REQUEST_FORM_DATA,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @DataProvider(name = "uploadResourceContentWithFileMimeTypes")
    public Object[][] uploadResourceContentWithFileMimeTypes() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_PDF, Constant.CREATOR, "application/pdf", ".pdf"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_ECML, Constant.CREATOR, "application/vnd.ekstep.ecml-archive", ".zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_HTML, Constant.CREATOR, "application/vnd.ekstep.html-archive", ".zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_H5P, Constant.CREATOR, "application/vnd.ekstep.h5p-archive", ".zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_X_YOUTUBE, Constant.CREATOR, "video/x-youtube", ""},

        };
    }

    @DataProvider(name = "uploadAssetContentWithFileMimeTypes")
    public Object[][] uploadAssetContentWithFileMimeTypss() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_IMAGE_PNG, Constant.CREATOR, "image/png", ".png"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_IMAGE_JPG, Constant.CREATOR, "image/jpg", ".jpg"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_VIDEO_MP4, Constant.CREATOR, "video/mp4", ".mp4"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_VIDEO_WEBM, Constant.CREATOR, "video/webm", ".webm"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_AUDIO_MP3, Constant.CREATOR, "audio/mp3", ".mp3"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_VIDEO_MPEG, Constant.CREATOR, "video/mpeg", ".mpeg"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_ASSET_WITH_VIDEO_X_YOUTUBE, Constant.CREATOR, "video/x-youtube", ""},



        };
    }

    @DataProvider(name = "uploadResourceContentWithFileUrl")
    public Object[][] uploadResourceContentWithFileUrl() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_URL, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", ".pdf"},

        };
    }

    @DataProvider(name = "uploadResourceContentWithFile")
    public Object[][] uploadResourceContentWithFile() {
        return new Object[][]{
                //specific negetive test scenarios
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_MISMATCHED_MIME, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/pdf", ".jpg", null},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_INVALID_IDENTIFIER, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, null, null, ".pdf"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_EMPTY_ZIP, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "empty.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_ZIP_WITHOUT_INDEX, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "UploadWithoutIndex.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_ZIP_WITH_MISSING_ASSET_ID, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "sample_with_missing_assetid.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_INVALID_JSON, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "ecmlCorruptedJSON.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_GREATER_THAN_50MB_ZIP, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "contentAbove50MB.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_VALID_HTML_WITHOUT_HTML_INDEX, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR, null, "application/vnd.ekstep.html-archive", null, "html_without_index.zip"},

                // specific positive test cases
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_VALID_JSON, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "ecml_with_json.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_ECML_WITH_TWIN_ANIMATION_AUDIO_SPRITES_IMG_SPRITES, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "tweenAndaudioSprite.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_CONTAINING_JSON_ITEM, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "Item_json.zip"},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_ZIP_WITHOUT_ASSET, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/vnd.ekstep.ecml-archive", null, "Ecml_without_asset.zip"},

        };
    }


    @DataProvider(name = "uploadResourceContentInLiveWithFile")
    public Object[][] uploadResourceContentInLiveWithFile() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_UPLOAD_RESOURCE_LIVE_CONTENT_PDF, Constant.CREATOR, "application/pdf", ".pdf"},

        };
    }

    @Test(dataProvider = "uploadResourceContentInWorkflow")
    @CitrusParameters({"testName","userType", "workflow", "status"})
    @CitrusTest
    public void testUploadResourceContentInWorkflow(String testName, String userType, String workflow, String status) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.prepareResourceContent(workflow,this, null, "application/pdf", null).get("content_id");
        Map<String, Object> resourceMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");
        String oldArtifactUrl = "artifactUrl";
        if(!workflow.equalsIgnoreCase(WorkflowConstants.CONTENT_IN_RETIRED_STATE)){
            oldArtifactUrl = resourceMap.get("artifactUrl").toString();
        }

        setContext(this, contentId, "application/pdf", ".pdf", null, FILE_URL);
        performMultipartTest(this, TEMPLATE_DIR, testName, APIUrl.UPLOAD_CONTENT + contentId, null, Constant.REQUEST_FORM_DATA, HttpStatus.OK, null, null);
        Map<String, Object> newResourceMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");
        String newArtifactUrl = newResourceMap.get("artifactUrl").toString();

        Assert.assertNotEquals(oldArtifactUrl, newArtifactUrl);


    }
    @DataProvider(name = "uploadResourceContentInWorkflow")
    public Object[][] uploadResourceContentInWorkflow() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_UPLOAD_CONTENT_IN_LIVE,Constant.CREATOR,  WorkflowConstants.CONTENT_IN_LIVE_STATE},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_CONTENT_IN_REVIEW,Constant.CREATOR, WorkflowConstants.CONTENT_IN_REVIEW_STATE},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_CONTENT_IN_FLAG, Constant.CREATOR, WorkflowConstants.CONTENT_IN_FLAG_STATE},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_CONTENT_IN_FLAG_DRAFT,Constant.CREATOR, WorkflowConstants.CONTENT_IN_FLAG_DRAFT},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_CONTENT_IN_FLAG_REVIEW, Constant.CREATOR,  WorkflowConstants.CONTENT_IN_FLAG_REVIEW},
                new Object[]{ContentV3Scenario.TEST_UPLOAD_CONTENT_IN_RETIRED, Constant.CREATOR,  WorkflowConstants.CONTENT_IN_RETIRED_STATE},

        };
    }

    private void setContext(BaseCitrusTestRunner runner, String contentId, String mimeType, String ext, String fileName, String fileUrl) {
        String extension = ext != null ? ext : MATCHED_EXTENSION;
        String fileNameValue = fileName != null ? fileName : "sample" + extension;
        //String fileurl = fileUrl != null ? fileUrl : "sample_url" + extension;
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf":
                case "application/vnd.ekstep.ecml-archive":
                case "application/vnd.ekstep.html-archive":
                case "application/vnd.ekstep.h5p-archive":
                case "application/vnd.ekstep.plugin-archive":
                case "video/x-youtube":
                case "image/png":
                case "image/jpg":
                case "video/mp4":
                case "video/webm":
                case "video/mpeg":
                case "audio/mp3":
                    runner.testContext.setVariable("fileNameValue", fileNameValue);
                    runner.testContext.setVariable("fileUrlValue", fileUrl);
                    break;
            }
        } else { //handle for mimeType == null (invalid id)
            runner.testContext.setVariable("fileNameValue", "sample" + extension);
            runner.testContext.setVariable("fileUrlValue", "url_" + extension);
        }
    }

}
