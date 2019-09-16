package org.sunbird.kp.test.content.v3.publish;

import org.sunbird.kp.test.util.PublishValidationUtil;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.content.v3.ContentV3Scenario;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Publish API Tests for Resource Content
 *
 * @author Kumar Gauraw
 */
public class PublishResourceContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/publish";

    private static final String ACTION_UPLOAD = "doUpload";
    private static final String ACTION_UPDATE_ECML = "doUpdateEcml";

    @Test(dataProvider = "publishValidResourceContent")
    @CitrusParameters({"testName", "mimeType"})
    @CitrusTest
    public void testPublishValidResourceContent(String testName, String mimeType) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        //String contentId = (String) ContentUtil.prepareResourceContent("contentInLive", this, null, mimeType, null).get("content_id");
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        String contentUrl = (String) ContentUtil.uploadResourceContent(this, contentId, mimeType, null).get("content_url");
        Assert.assertNotNull(contentUrl);
        ContentUtil.publishContent(this, null, "public", contentId, null);
        delay(this, 10000);
        Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");
        Assert.assertTrue(MapUtils.isNotEmpty(contentMap));
        PublishValidationUtil.validateMetadata(contentMap, null);
        //TODO: Enable It after having method implementation and supply required valParams
        //PublishValidationUtil.validateEcarManifest((String)contentMap.get("downloadUrl"), new HashMap<String, Object>());
    }

    //TODO: Enable h5p after having proper file for upload.
    @DataProvider(name = "publishValidResourceContent")
    public Object[][] publishValidResourceContent() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_PDF_CONTENT, "application/pdf"},
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_HTML_CONTENT, "application/vnd.ekstep.html-archive"},
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_ECML_CONTENT, "application/vnd.ekstep.ecml-archive"},
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_VIDEO_MP4, "video/mp4"},
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_VIDEO_WEBM, "video/webm"},
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_VIDEO_X_YOUTUBE, "video/x-youtube"},
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_H5P_CONTENT, "application/vnd.ekstep.h5p-archive"},

        };
    }


    @Test(dataProvider = "publishInValidResourceContentScenarios")
    @CitrusParameters({"testName", "httpStatusCode", "valParams"})
    @CitrusTest
    public void publishInValidResourceContent(String testName, HttpStatus httpStatusCode, Map<String, Object> valParams) {
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.PUBLIC_PUBLISH_CONTENT + "inValidId",
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );

    }

    @DataProvider(name = "publishInValidResourceContentScenarios")
    public Object[][] publishInValidResourceContentScenarios() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_PUBLISHING_INVALID_RESOURCE_CONTENT, HttpStatus.BAD_REQUEST, null},

        };
    }

    @Test(dataProvider = "republishValidResourceContentScenarios")
    @CitrusParameters({"testName", "mimeType", "toUpload", "toDelete"})
    @CitrusTest
    public void testRePublishValidResourceContent(String testName, String mimeType, boolean toUpload, boolean toDelete) {

        String contentId = doPublish(testName, mimeType, ACTION_UPLOAD, true);
        Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");
        String versionAfterPublish = (String) contentMap.get("versionKey");

        if (!toUpload && !toDelete) {
            System.out.println("content map on publish =" + contentMap);
            Assert.assertTrue(contentMap.get("status").toString().equals("Live"));
        } else {
            if (toUpload) {
                this.testContext.setVariable("fileNameValue", "pdf.pdf");
                performMultipartTest(this, TEMPLATE_DIR, testName, APIUrl.UPLOAD_CONTENT + contentId, null, Constant.REQUEST_FORM_DATA, HttpStatus.OK, null, null);
                Map<String, Object> uploadContentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");

                Assert.assertTrue(StringUtils.isNotBlank(uploadContentMap.get("artifactUrl").toString()));
                Assert.assertTrue(StringUtils.equals(uploadContentMap.get("status").toString(), "Live"));
                Assert.assertTrue(uploadContentMap.get("downloadUrl").toString().endsWith(".ecar"));
                Assert.assertTrue((double) uploadContentMap.get("size") > 0);
            }

            if (toDelete) {
                Map<String, Object> retireMap = ContentUtil.retireContent(this, contentId, null);
                System.out.println("Retire map == " + retireMap);
            }
            Map<String, Object> responseMap = ContentUtil.publishContent(this, null, "public", contentId, null);
            delay(this, 10000);
            System.out.println("republishContent == " + responseMap);
            Map<String, Object> rePublishContentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");


            System.out.println("content map on republish =" + rePublishContentMap);
            Assert.assertTrue(rePublishContentMap.get("status").toString().equals("Live"));
            String versionAfterRepublish = (String) rePublishContentMap.get("versionKey");
            Assert.assertFalse(versionAfterPublish == versionAfterRepublish);

        }

    }

    @DataProvider(name = "republishValidResourceContentScenarios")
    public Object[][] republishValidResourceContentScenarios() {
        return new Object[][]{
                new Object[]{ContentV3Scenario.TEST_PUBLISH_RESOURCE_PDF_CONTENT, "application/pdf", false, false},
                new Object[]{ContentV3Scenario.TEST_PUBLISHING_EXISTING_LIVE_RESOURCE_CONTENT, "application/pdf", true, false},
                new Object[]{ContentV3Scenario.TEST_PUBLISHING_DELETED_RESOURCE_CONTENT, "application/pdf", false, true},

        };
    }

    /**
     * This test performs scenarios of ecml body content
     */
    @Test(dataProvider = "publishEcmlResourceContentScenarios")
    @CitrusParameters({"testName", "mimeType", "action"})
    @CitrusTest
    public void testPublishEcmlResourceContent(String testName, String mimeType, String action) {
        String contentId = doPublish(testName, mimeType, ACTION_UPDATE_ECML, true);
        Map<String, Object> publishContentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");
        System.out.println("content map on publish =" + publishContentMap);
        //Assert.assertTrue(publishContentMap.get("status").toString().equals("Live"));

        switch (testName) {
            case ContentV3Scenario.TEST_PUBLISH_DRAFT_VERSION_OF_PUBLISHED_RESOURCE_ECML_CONTENT:
                Assert.assertTrue(publishContentMap.get("status").toString().equals("Live"));
                doUpdate(testName, contentId);
                ContentUtil.publishContent(this, null, "public", contentId, null);
                delay(this, 10000);
                Map<String, Object> updateEcmlContentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");
                System.out.println("content map on republish =" + updateEcmlContentMap.get("artifactUrl"));
                Assert.assertTrue(updateEcmlContentMap.get("status").toString().equals("Live"));
                Assert.assertTrue(updateEcmlContentMap.get("artifactUrl").toString().equals(publishContentMap.get("artifactUrl")));
                break;

            case ContentV3Scenario.TEST_PUBLISH_RESOURCE_ECML_CONTENT_WITH_CORRUPTED_ECML_BODY:
                Assert.assertTrue(publishContentMap.get("status").toString().equals("Failed"));
                Assert.assertTrue(StringUtils.isNotBlank(publishContentMap.get("publishError").toString()));
                break;

            case ContentV3Scenario.TEST_REPUBLISH_RESOURCE_ECML_CONTENT:
                ContentUtil.publishContent(this, null, "public", contentId, null);
                delay(this, 10000);
                Map<String, Object> rePublishContentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content");
                System.out.println("content map on republish =" + rePublishContentMap.get("artifactUrl"));
                Assert.assertTrue(rePublishContentMap.get("status").toString().equals("Live"));
                Assert.assertTrue(rePublishContentMap.get("artifactUrl").toString().equals(publishContentMap.get("artifactUrl")));
                break;


        }

    }

    //TODO: fix publish fails after update ecml body
    @DataProvider(name = "publishEcmlResourceContentScenarios")
    public Object[][] publishEcmlResourceContentScenarios() {
        return new Object[][]{
                //new Object[]{ ContentV3Scenario.TEST_REPUBLISH_RESOURCE_ECML_CONTENT, "application/vnd.ekstep.ecml-archive"},
                //new Object[]{ ContentV3Scenario.TEST_PUBLISH_DRAFT_VERSION_OF_PUBLISHED_RESOURCE_ECML_CONTENT, "application/vnd.ekstep.ecml-archive"},
                //new Object[]{ ContentV3Scenario.TEST_PUBLISH_RESOURCE_ECML_CONTENT_WITH_CORRUPTED_ECML_BODY, "application/vnd.ekstep.ecml-archive"},
        };
    }


    private String doPublish(String testName, String mimeType, String action, boolean isValid) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        System.out.println("content id == " + contentId);
        switch (action) {
            case ACTION_UPLOAD:
                ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
                break;
            case ACTION_UPDATE_ECML:
                doUpdate(testName, contentId);
                break;
        }

        if (!isValid) contentId = "invalid";
        Map<String, Object> publishContent = ContentUtil.publishContent(this, null, "public", contentId, null);
        delay(this, 10000);
        System.out.println("publishContent == " + publishContent);
        return contentId;

    }

    private void doUpdate(String testName, String contentId) {
        String versionKeyVal = ((Map<String, Object>) ContentUtil.readContent(this, contentId, null, null).get("content")).get("versionKey").toString();
        this.variable("versionKeyVal", versionKeyVal);
        performPatchTest(this, TEMPLATE_DIR, testName, APIUrl.UPDATE_CONTENT + contentId, null, REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, null);

    }


}
