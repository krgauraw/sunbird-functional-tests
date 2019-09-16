package org.sunbird.kp.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetadataValidationUtil {

    public static Boolean validateMetadataAfterCreate(Map<String, Object> resultMap) {
        Map<String, Object> contentMap = (Map<String,Object>) resultMap.get("content");
        return (contentMap.keySet().containsAll(METADATA_ADDED_AFTER_CREATION) &&
                (contentMap.keySet().stream().filter(key -> key == null).count()) == 0) ? true : false;
    }

    public static Boolean validateSpecificMetadata(Map<String, Object> metadataMap, Map<String, Object> resultMap) {
        return (resultMap.keySet().containsAll(metadataMap.keySet()) &&
                (resultMap.keySet().stream().filter(key -> null == key).count()) == 0) ? true : false;
    }

    public final static List<String> METADATA_ADDED_AFTER_CREATION = new ArrayList<String>() {{
        add("ownershipType");
        add("code");
        add("channel");
        add("language");
        add("mimeType");
        add("idealScreenSize");
        add("createdOn");
//        add("appId");
        add("contentDisposition");
        add("contentEncoding");
        add("lastUpdatedOn");
        add("contentType");
        add("dialcodeRequired");
        add("identifier");
        add("audience");
        add("lastStatusChangedOn");
        add("visibility");
        add("os");
//        add("consumerId");
        add("mediaType");
        add("osId");
        add("languageCode");
        add("version");
        add("versionKey");
        add("license");
        add("idealScreenDensity");
        add("framework");
        add("compatibilityLevel");
        add("name");
        add("status");
    }};


}
