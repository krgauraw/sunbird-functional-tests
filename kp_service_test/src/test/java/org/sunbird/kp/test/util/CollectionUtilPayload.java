package org.sunbird.kp.test.util;

public class CollectionUtilPayload {
    public static final String UPDATE_HIERARCHY_1_UNIT_1_RESOURCE = "{\n" +
            "  \"request\": {\n" +
            "    \"data\": {\n" +
            "      \"nodesModified\": {\n" +
            "        \"${collectionIdVal}\":{\n" +
            "          \"isNew\": false,\n" +
            "          \"root\": true,\n" +
            "          \"metadata\": {}\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"isNew\": true,\n" +
            "          \"root\": false,\n" +
            "          \"metadata\": {\n" +
            "            \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "            \"contentType\": \"${contentTypeVal}\",\n" +
            "            \"code\": \"citrus:concat('unit_code_', citrus:randomNumber(10))\",\n" +
            "            \"name\": \"citrus:concat('Test_${contentTypeVal}_name_', citrus:randomNumber(10))\",\n" +
            "            \"description\": \"citrus:concat('Test_${contentTypeVal}_desc_', citrus:randomNumber(10))\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"hierarchy\": {\n" +
            "        \"${collectionIdVal}\": {\n" +
            "          \"children\": [\n" +
            "            \"2cb4d698-dc19-4f0c-9990-96f49daff753\"\n" +
            "          ],\n" +
            "          \"root\": true\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"children\": [\"resource_1\"],\n" +
            "          \"root\": false\n" +
            "        }\n" +
            "\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    public static final String UPDATE_HIERARCHY_1_UNIT_1_ASSET = "{\n" +
            "  \"request\": {\n" +
            "    \"data\": {\n" +
            "      \"nodesModified\": {\n" +
            "        \"${collectionIdVal}\":{\n" +
            "          \"isNew\": false,\n" +
            "          \"root\": true,\n" +
            "          \"metadata\": {}\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"isNew\": true,\n" +
            "          \"root\": false,\n" +
            "          \"metadata\": {\n" +
            "            \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "            \"contentType\": \"${contentTypeVal}\",\n" +
            "            \"code\": \"citrus:concat('unit_code_', citrus:randomNumber(10))\",\n" +
            "            \"name\": \"citrus:concat('Test_${contentTypeVal}_name_', citrus:randomNumber(10))\",\n" +
            "            \"description\": \"citrus:concat('Test_${contentTypeVal}_desc_', citrus:randomNumber(10))\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"hierarchy\": {\n" +
            "        \"${collectionIdVal}\": {\n" +
            "          \"children\": [\n" +
            "            \"2cb4d698-dc19-4f0c-9990-96f49daff753\"\n" +
            "          ],\n" +
            "          \"root\": true\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"children\": [\"asset_1\"],\n" +
            "          \"root\": false\n" +
            "        }\n" +
            "\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
