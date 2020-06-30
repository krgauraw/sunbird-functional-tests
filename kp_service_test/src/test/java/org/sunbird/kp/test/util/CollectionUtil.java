package org.sunbird.kp.test.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CollectionUtil {

    private static final List<String> resourceMimeTypes = Arrays.asList("application/pdf", "application/vnd.ekstep.ecml-archive", "application/vnd.ekstep.html-archive", "application/vnd.ekstep.plugin-archive");
    private static final List<String> assetMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/jpg", "video/mp4", "video/webm", "video/webm", "video/mpeg", "audio/mp3");
    private static final Map<String, String> collectionChildRelation = new HashMap<String, String>() {
        {
            put("TextBook", "TextBookUnit");
        }
    };

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * This method will add resources or asset to a Payload collection provided as user's requirements.
     * The number of asset or resources to be created should be equal to the placeholders in the dynamic payload.
     * <p>
     * Resource placeholder should be resource_N, for eg: resource_1
     * Asset placeholder should be resource_N for eg: asset_2.
     *
     * @param workFlow       (Mandatory)
     * @param runner
     * @param payloadMap        (Mandatory -> Send new HashMap with (updateHierarchy -> Payload String), If you want to custom resource, pass createResource)
     * @param collectionType (For valid collection type see ContentUtil)
     * @param assetCount     (Number of assets that are needed to replace in the payload)
     * @param resourceCount  (Number of resources that are needed to replace in the payload
     * @param mimeType       (This field is optional and can be passed as null for random valid mimeType generation
     * @return a map of all the resources/assets added, all unit identifiers and textBook identifier
     * @see ContentUtil
     */
    public static Map<String, Object> prepareTestCollection(String workFlow, BaseCitrusTestRunner runner, Map<String, String> payloadMap, String collectionType,
                                                            Integer assetCount, Integer resourceCount, String mimeType) {
        String updateHierarchyPayload = null;
        Map<String, Object> collectionMap = new HashMap<>();
        if(!StringUtils.isEmpty(payloadMap.get("updateHierarchy"))) {
            updateHierarchyPayload = payloadMap.get("updateHierarchy");
            if (assetCount != 0) {
                List<String> assetIds = getLiveAsset(runner, assetCount, mimeType);
                for (String assetId : assetIds) {
                    collectionMap.put("asset_" + (assetIds.indexOf(assetId) + 1), assetId);
                    updateHierarchyPayload = updateHierarchyPayload.replace("asset_" + (assetIds.indexOf(assetId) + 1), assetId);
                }
            }
            if (resourceCount != 0) {
                List<String> resourceIds = getLiveResources(runner, resourceCount, mimeType, payloadMap.get("createResource"));
                for (String resourceId : resourceIds) {
                    collectionMap.put("resource_" + (resourceIds.indexOf(resourceId) + 1), resourceId);
                    updateHierarchyPayload = updateHierarchyPayload.replace("resource_" + (resourceIds.indexOf(resourceId) + 1), resourceId);
                }
            }
        }
        collectionMap.putAll(ContentUtil.createCollectionContent(runner, null, collectionType, null));

        //Map also has identifiers which is a map of all id's of unit's etc TODO: Return that too
        String contentId = (String) collectionMap.get("content_id");
        runner.variable("collectionIdVal", contentId);
        runner.variable("versionKey", collectionMap.get("versionKey"));
        Map collectionWorkMap = null;
        try {
            collectionWorkMap = mapper.readValue(WorkflowConstants.collectionWorkFlows, new TypeReference<Map<String, Object>>() {
            });
            List contentWorkList = (List<String>) collectionWorkMap.get(workFlow);
            Map<String, Supplier<Map<String, Object>>> actionMap = ContentUtil.getCollectionWorkFlowMap(runner, contentId, null, updateHierarchyPayload, null);
            if (!CollectionUtils.isEmpty(contentWorkList)) {
                contentWorkList.forEach(action -> {
                    Map response = actionMap.get(action).get();
                    if (response.get("content") != null)
                        response = (Map<String, Object>) response.get("content");
                    if( null != response.get("identifiers"))
                        collectionMap.put("identifiers", response.get("identifiers"));
                    if (StringUtils.isNotBlank((String) response.get("versionKey")))
                        runner.variable("versionKeyVal", response.get("versionKey"));
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        collectionMap = ContentUtil.readCollectionHierarchy(runner, contentId)
        return collectionMap;
    }

    /**
     *
     * @param runner
     * @param resourceCount
     * @param mimeType
     * @param createReqPayload  Payload for create resource request
     * @return
     */
    public static List<String> getLiveResources(BaseCitrusTestRunner runner, Integer resourceCount, String mimeType, String createReqPayload) {
        List<String> resourceIds = new ArrayList<>();
        Map<String, Object> headers = new HashMap<String, Object>() {{ put("X-Consumer-Id", UUID.randomUUID().toString()); }};
        IntStream.rangeClosed(1, resourceCount).forEach(val -> {
            String randMimeType = StringUtils.isBlank(mimeType) ? resourceMimeTypes.get((new Random()).nextInt(resourceMimeTypes.size())) : mimeType;
            resourceIds.add((String) ContentUtil.prepareResourceContent("contentInLive", runner, createReqPayload, randMimeType, headers).get("content_id"));
        });
        return resourceIds;
    }

    public static List<String> getLiveAsset(BaseCitrusTestRunner runner, Integer assetCount, String mimeType) {
        List<String> assetIds = new ArrayList<>();
        IntStream.rangeClosed(1, assetCount).forEach(val -> {
            String randMimeType = StringUtils.isBlank(mimeType) ? assetMimeTypes.get((new Random()).nextInt(resourceMimeTypes.size())) : mimeType;
            assetIds.add((String) ContentUtil.prepareAssetContent("assetLive", runner, randMimeType, null).get("content_id"));
        });
        return assetIds;
    }
}
