package org.sunbird.kp.test.content.v3.publish;

import com.consol.citrus.annotations.CitrusTest;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.util.CompositeSearchUtil;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.ContentPayload;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Integration Test for Content Publish API With Multi Stage Scenario's
 * @author Kumar Gauraw
 */
public class PublishMultiStageScenarioTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/publish";

    /*
    * Scenario : Publish a Textbook Having Another Collection With Concept as children
    * Expectation: Textbook Should be Published and Get Hierarchy should work for the Textbook.
    *
    * Step 1: Create a Live Resource Content with Concept
    * Step 2: Create a Collection Content, add  Resource Content as children, Publish the Collection
    * Step 3: Create a Textbook, Add Collection Content (Have Concept) as children to textbook unit and publish Textbook.
    *
    * */
    @Test
    @CitrusTest
    public void testPublishTextbookContentWithResourceHavingConceptRelation() throws Exception {
        String resourceMimeType = "application/pdf";
        getAuthToken(this, null);

        String resourceId = (String) ContentUtil.createResourceContent(this, ContentPayload.CREATE_RESOURCE_CONTENT_WITH_CONCEPT, null, null).get("content_id");
        System.out.println("Resource Content Id:" + resourceId);
        Map<String, Object> uploadResult = ContentUtil.uploadResourceContent(this, resourceId, resourceMimeType, null);
        Assert.assertNotNull(uploadResult.get("content_url"));
        ContentUtil.publishContent(this, null, "public", resourceId, null);
        Map<String, Object> contentMap = ContentUtil.readContent(this, resourceId, null, null);
        System.out.println("Content Map (Resource): " + contentMap);

        String collectionId = (String) ContentUtil.createCollectionContent(this, null, "collection", null).get("content_id");
        System.out.println("Collection Id : " + collectionId);
        ContentUtil.updateContentHierarchy(this, collectionId, "collection", resourceId, null, null);
        ContentUtil.publishContent(this, null, "public", collectionId, null);

        String textbookId = (String) ContentUtil.createCollectionContent(this, null, "textbook", null).get("content_id");
        System.out.println("Textbook Id : " + textbookId);
        ContentUtil.updateContentHierarchy(this, textbookId, "textbook", collectionId, null, null);
        ContentUtil.publishContent(this, null, "public", textbookId, null);

        this.getTestCase().setName("testPublishTextbookContentWithResourceHavingConceptRelation");
        delay(this, 60000);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        Assert.assertNotNull(textbookMap);
        List<Map<String, Object>> list = (List<Map<String, Object>>) textbookMap.get("concepts");
        Map<String, Object> concept = list.get(0);
        Assert.assertEquals((String) textbookMap.get("status"), "Live");
        Assert.assertNotNull(textbookMap.get("variants"));
        Assert.assertEquals("LO53", (String) concept.get("identifier"));
        List<String> childNodes = (List<String>) textbookMap.get("childNodes");
        String payload = ContentPayload.SEARCH_CONTENT_WITH_IDENTIFIERS.replace("identifiersVal", objectMapper.writeValueAsString(childNodes));
        Map<String, Object> searchResult = CompositeSearchUtil.searchContent(this, payload, null, null);
        System.out.println("searchResult : " + searchResult);
        int count = (int) searchResult.get("count");
        Assert.assertEquals(childNodes.size(), count);
    }

    /*
     * Scenario : Publish a Textbook Having Resource Content (Which Got Updated Now and have new version)
     * Expectation: Textbook Should be Published and Get Hierarchy should work for the Textbook. Textbook Hierarchy should
     * have latest version of the resource.
     *
     * Step 1: Create a Live Resource Content.
     * Step 2: Create a Textbook, Add Resource Content as children to textbook unit and publish Textbook.
     * Step 3: Publish The Resource Content Again
     * Step 4: Publish The Textbook Again.
     * Step 5: Make Call to Get Hierarchy And Verify pkgVersion of the Resource Children.
     *
     * */
    @Test
    @CitrusTest
    public void testPublishTextbookWithResourceHavingNewVersion() {
        String resourceMimeType = "application/pdf";
        getAuthToken(this, null);

        String resourceId = (String) ContentUtil.createResourceContent(this, null, resourceMimeType, null).get("content_id");
        System.out.println("Resource Content Id:" + resourceId);
        Map<String, Object> uploadResult = ContentUtil.uploadResourceContent(this, resourceId, resourceMimeType, null);
        Assert.assertNotNull(uploadResult.get("content_url"));
        ContentUtil.publishContent(this, null, "public", resourceId, null);

        String textbookId = (String) ContentUtil.createCollectionContent(this, null, "textbook", null).get("content_id");
        System.out.println("Textbook Id : " + textbookId);
        ContentUtil.updateContentHierarchy(this, textbookId, "textbook", resourceId, null, null);
        ContentUtil.publishContent(this, null, "public", textbookId, null);

        delay(this, 40000);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        Assert.assertNotNull(textbookMap);
        Assert.assertEquals(textbookMap.get("status"), "Live");
        Assert.assertEquals(((Double) textbookMap.get("pkgVersion")).intValue(), 1);

        ContentUtil.publishContent(this, null, "public", resourceId, null);
        Map<String, Object> resourceMap = (Map<String, Object>) ContentUtil.readContent(this, resourceId, null, null).get("content");
        Assert.assertEquals( ((Double)resourceMap.get("pkgVersion")).intValue(), 2);

        ContentUtil.publishContent(this, null, "public", textbookId, null);

        delay(this, 40000);
        Map<String, Object> textbookUpdatedMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        Assert.assertEquals(((Double) textbookUpdatedMap.get("pkgVersion")).intValue(), 2);
        Assert.assertEquals((String) textbookUpdatedMap.get("status"), "Live");
        List<Map<String, Object>> children =
                (List<Map<String, Object>>) ((Map<String, Object>) ((List<Map<String, Object>>) textbookUpdatedMap.get("children")).get(0)).get("children");
        Map<String, Object> childrenMap = children.get(0);
        Assert.assertEquals(((Double) childrenMap.get("pkgVersion")).intValue(), 2);
    }

    @Test
    @CitrusTest
    public void testPublishTextbookWithRetiredResource() {
        String resourceMimeType = "application/pdf";
        getAuthToken(this, null);

        String resourceId = (String) ContentUtil.createResourceContent(this, null, resourceMimeType, null).get("content_id");
        System.out.println("Resource Content Id:" + resourceId);
        Map<String, Object> uploadResult = ContentUtil.uploadResourceContent(this, resourceId, resourceMimeType, null);
        Assert.assertNotNull(uploadResult.get("content_url"));
        ContentUtil.publishContent(this, null, "public", resourceId, null);

        String textbookId = (String) ContentUtil.createCollectionContent(this, null, "textbook", null).get("content_id");
        System.out.println("Textbook Id : " + textbookId);
        ContentUtil.updateContentHierarchy(this, textbookId, "textbook", resourceId, null, null);
        ContentUtil.publishContent(this, null, "public", textbookId, null);

        delay(this, 40000);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        Assert.assertNotNull(textbookMap);
        Assert.assertEquals(textbookMap.get("status"), "Live");
        Assert.assertEquals(((Double) textbookMap.get("pkgVersion")).intValue(), 1);
        List<String> childNodes = (List<String>) textbookMap.get("childNodes");
        Assert.assertTrue(childNodes.contains(resourceId));

        ContentUtil.retireContent(this,resourceId,null);
        Map<String, Object> resourceMap = (Map<String, Object>) ContentUtil.readContent(this, resourceId, "edit", null).get("content");
        Assert.assertEquals((String)resourceMap.get("status"),"Retired");

        ContentUtil.publishContent(this, null, "public", textbookId, null);

        delay(this, 40000);
        Map<String, Object> textbookUpdatedMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        Assert.assertEquals(((Double) textbookUpdatedMap.get("pkgVersion")).intValue(), 2);
        Assert.assertEquals((String) textbookUpdatedMap.get("status"), "Live");
        List<String> updatedChildNodes = (List<String>) textbookUpdatedMap.get("childNodes");
        Assert.assertFalse(updatedChildNodes.contains(resourceId));
    }

}
