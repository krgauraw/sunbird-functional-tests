package org.sunbird.kp.test.content.v3.publish;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.content.v3.ContentV3Scenario;
import org.sunbird.kp.test.util.CollectionUtil;
import org.sunbird.kp.test.util.CollectionUtilPayload;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.PublishValidationUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Publish API Tests for Collection Content
 *
 * @author Kumar Gauraw
 */
public class PublishCollectionContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/publish";

    @Test(dataProvider = "publishValidCollectionPdfResources")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishValidCollectionPdfResource(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInDraft", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/pdf");
        String textbookId = (String) collectionMap.get("content_id");
        ContentUtil.publishContent(this, null, "public", textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        //TODO: Validate Metadata
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String, Object>());
        //TODO: Enable It after having method implementation and supply required valParams
        PublishValidationUtil.validateEcarManifest((String) textbookMap.get("downloadUrl"), new HashMap<String, Object>());
        PublishValidationUtil.validateHierarchyJson(null, new HashMap<String, Object>());
    }

    @Test(dataProvider = "publishValidCollectionEcmlResources")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishValidCollectionEcmlContent(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInDraft", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.ecml-archive");
        String textbookId = (String) collectionMap.get("content_id");
        ContentUtil.publishContent(this, null, "public", textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        //TODO: Validate Metadata
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String, Object>());
        //TODO: Enable It after having method implementation and supply required valParams
        PublishValidationUtil.validateEcarManifest((String) textbookMap.get("downloadUrl"), new HashMap<String, Object>());
        PublishValidationUtil.validateHierarchyJson(null, new HashMap<String, Object>());
    }

    @Test(dataProvider = "publishValidCollectionHtmlResources")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishValidCollectionHtmlContent(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInDraft", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.html-archive");
        String textbookId = (String) collectionMap.get("content_id");
        ContentUtil.publishContent(this, null, "public", textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        //TODO: Validate Metadata
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String, Object>());
        //TODO: Enable It after having method implementation and supply required valParams
        PublishValidationUtil.validateEcarManifest((String) textbookMap.get("downloadUrl"), new HashMap<String, Object>());
        PublishValidationUtil.validateHierarchyJson(null, new HashMap<String, Object>());
    }

    @Test(dataProvider = "publishValidCollectionResources")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishValidCollectionContent(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInDraft", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.plugin-archive");
        String textbookId = (String) collectionMap.get("content_id");
        ContentUtil.publishContent(this, null, "public", textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        //TODO: Validate Metadata
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String, Object>());
        //TODO: Enable It after having method implementation and supply required valParams
        PublishValidationUtil.validateEcarManifest((String) textbookMap.get("downloadUrl"), new HashMap<String, Object>());
        PublishValidationUtil.validateHierarchyJson(null, new HashMap<String, Object>());
    }

    @Test(dataProvider = "publishValidCollectionPluginResources")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishValidCollectionPluginContent(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInDraft", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/vnd.ekstep.plugin-archive");
        String textbookId = (String) collectionMap.get("content_id");
        ContentUtil.publishContent(this, null, "public", textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        //TODO: Validate Metadata
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String, Object>());
        //TODO: Enable It after having method implementation and supply required valParams
        PublishValidationUtil.validateEcarManifest((String) textbookMap.get("downloadUrl"), new HashMap<String, Object>());
        PublishValidationUtil.validateHierarchyJson(null, new HashMap<String, Object>());
    }

    @Test(dataProvider = "publishWithDifferentVersionResource")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishWithDifferentVersionResource(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInLive", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/pdf");
        String textbookId = (String) collectionMap.get("content_id");
        String resourceId = (String) collectionMap.get("resource_1");
        ContentUtil.publishContent(this, null, "public" , resourceId, null);
        ContentUtil.publishContent(this, null, "public" , textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String,Object>() {
            {
                put("status", "Live");
                put("prevState", "Live");
                put("pkgVersion", 2);
                put("mimeTypesCount", "{\"application/pdf\":1,\"application/vnd.ekstep.content-collection\":1}");
                put("downloadUrl", null);
                put("variants", null);
//                put("leafNodes", "["+resourceId+"]");
                put("childNodes", null);
                put("size", null);
                put("totalCompressedSize", null);
                put("leafNodesCount", 1);

            }
        });
    }

    @Test(dataProvider = "publishWithRetiredResource")
    @CitrusParameters({"testName", "collectionType", "resourceCount", "payload"})
    @CitrusTest
    public void testPublishWithRetiredResource(String testName, String collectionType, Integer resourceCount, String payload) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        Map<String, Object> collectionMap = CollectionUtil.prepareTestCollection("collectionUnitsInLive", this,
                new HashMap<String,String>() {{put("updateHierarchy", payload);}}, collectionType, 0, resourceCount, "application/pdf");
        String textbookId = (String) collectionMap.get("content_id");
        String resourceId = (String) collectionMap.get("resource_1");
        ContentUtil.retireContent(this, resourceId, null);
        ContentUtil.publishContent(this, null, "public" , textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String,Object>() {
            {
                put("status", "Live");
                put("prevState", "Live");
                put("pkgVersion", 2);
                put("mimeTypesCount", "{\"application/vnd.ekstep.content-collection\":1}");
                put("downloadUrl", null);
                put("variants", null);
//                put("leafNodes", "["+resourceId+"]");
                put("childNodes", null);
                put("size", null);
                put("totalCompressedSize", null);
                put("leafNodesCount", 0);

            }
        });
    }

    @DataProvider(name = "publishValidCollectionPdfResources")
    public Object[][] publishValidCollectionPdfResources() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "collection", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "course", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "lessonplan", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                }
        };
    }

    @DataProvider(name = "publishValidCollectionEcmlResources")
    public Object[][] publishValidCollectionEcmlContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "collection", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "course", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "lessonplan", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                }
        };
    }

    @DataProvider(name = "publishValidCollectionHtmlResources")
    public Object[][] publishValidCollectionHtmlContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "collection", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "course", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "lessonplan", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                }
        };
    }

    @DataProvider(name = "publishValidCollectionPluginResources")
    public Object[][] publishValidCollectionPluginContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "collection", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "course", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "lessonplan", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                }
        };
    }

    @DataProvider(name = "publishValidCollectionResources")
    public Object[][] publishValidCollectionContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "collection", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "course", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "lessonplan", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                }
        };
    }

    @DataProvider(name = "publishWithDifferentVersionResource")
    public Object[][] publishWithDifferentVersionResource() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_WITH_DIFFERENT_VERSION, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },

        };
    }

    @DataProvider(name = "publishWithRetiredResource")
    public Object[][] publishWithRetiredResource() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_WITH_RESOURCE_RETIRED, "textbook", 1, CollectionUtilPayload.UPDATE_HIERARCHY_1_UNIT_1_RESOURCE
                },

        };
    }



}
