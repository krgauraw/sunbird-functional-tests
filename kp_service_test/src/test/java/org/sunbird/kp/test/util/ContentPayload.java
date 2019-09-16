package org.sunbird.kp.test.util;

/**
 * This Class Holds All Dynamic Request Payload Required For Content Api's
 *
 * @author Kumar Gauraw
 */
public class ContentPayload {

    public static final String CREATE_RESOURCE_CONTENT_WITH_CONCEPT = "{\n" +
            "  \"request\": {\n" +
            "    \"content\": {\n" +
            "      \"identifier\": \"KP_FT_"+System.currentTimeMillis()+"\",\n" +
            "      \"name\": \"KP Integration Test Content\",\n" +
            "      \"code\": \"kp.ft.resource.pdf\",\n" +
            "      \"mimeType\": \"application/pdf\",\n" +
            "      \"contentType\": \"Resource\",\n" +
            "      \"concepts\": [\n" +
            "        {\n" +
            "          \"identifier\": \"LO53\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String SEARCH_CONTENT_WITH_IDENTIFIERS="{\n" +
            "    \"request\": {\n" +
            "        \"filters\": {\n" +
            "            \"objectType\": [\"Content\"],\n" +
            "            \"identifier\":identifiersVal\n" +
            "        },\n" +
            "        \"limit\": 0\n" +
            "    }\n" +
            "}";
    public static final String CREATE_RESOURCE_CONTENT_WITH_MULTIPLE_CONCEPTS = "{\n" +
            "  \"request\": {\n" +
            "    \"content\": {\n" +
            "      \"identifier\": \"KP_FT_"+System.currentTimeMillis()+"\",\n" +
            "      \"name\": \"KP Integration Test Content\",\n" +
            "      \"code\": \"kp.ft.resource.pdf\",\n" +
            "      \"mimeType\": \"application/pdf\",\n" +
            "      \"contentType\": \"Resource\",\n" +
            "      \"concepts\": [\n" +
            "        {\n" +
            "          \"identifier\": \"LO53\"\n" +
            "          \"identifier\": \"AI31\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

}
