package org.sunbird.kp.test.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility Class for After Content Publish Data Validation
 *
 * @author Kumar Gauraw
 */
public class PublishValidationUtil {

    private static final Map<String, Object> DEFAULT_VAL_PARAMS = new HashMap<String, Object>() {{
        put("status", "Live");
        put("pkgVersion", 1);
        put("variants", null);
        put("lastPublishedOn", null);
        put("downloadUrl", null);
        put("framework", null);
        put("compatibilityLevel",null);
        //put("publisher",null);
        put("prevState",null);
    }};

    //TODO: Provide Implementation
    public static Boolean validateEcarManifest(String ecarUrl, Map<String, Object> valParams) {
        return true;
    }


    public static void validateMetadata(Map<String, Object> contentMap, Map<String, Object> valParams) {
        if (MapUtils.isNotEmpty(contentMap)) {
            if (MapUtils.isNotEmpty(valParams)) {
                validate(contentMap, valParams);
            } else {
                validate(contentMap, DEFAULT_VAL_PARAMS);
            }
        } else {
            System.out.println("Metadata Validation Skipped for Published Content!");
        }

    }

    private static void validate(Map<String, Object> contentMap, Map<String, Object> valParams) {
        for (String key : valParams.keySet()) {
            System.out.println("key:"+key);
            Assert.assertTrue(contentMap.containsKey(key));
            System.out.println("Key Matched. Key = " + key + " | Value from Response = " + contentMap.get(key));

            if (null != valParams.get(key)) {
                System.out.println("Checking for Exact Value Match!");
                if (valParams.get(key) instanceof Integer) {
                    Assert.assertEquals(valParams.get(key), ((Number) contentMap.get(key)).intValue());
                } else if(valParams.get(key) instanceof Double){
                    Assert.assertEquals(valParams.get(key), ((Number) contentMap.get(key)).doubleValue());
                } else {
                    Assert.assertEquals(valParams.get(key), contentMap.get(key));
                }
            } else {
                System.out.println("Checking for Not Null!");
                Assert.assertNotNull(contentMap.get(key));
            }
        }
    }

    public static Boolean validateHierarchyJson(File ecarFile,  Map<String, Object> valParams ) {
        return true;
    }

}
