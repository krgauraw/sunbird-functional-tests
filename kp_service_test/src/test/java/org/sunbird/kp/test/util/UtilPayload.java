package org.sunbird.kp.test.util;

/**
 * This Class Holds All Static Payload Used by Utility Classes
 *
 * @author Kumar Gauraw
 */
public class UtilPayload {

    public static final String GENERATE_DIALCODE_REQ="{\n" +
            "\t\"request\": {\n" +
            "      \"dialcodes\": {\n" +
            "        \"count\":dialCountVal\n" +
            "      }\n" +
            "  }\n" +
            "}";


    public static final String SEARCH_DIALCODE_WITH_IDS_REQ="{\n" +
            "    \"request\": {\n" +
            "        \"search\": {\n" +
            "            \"identifier\": dialsVal\n" +
            "        }\n" +
            "    }\n" +
            "}";


}
