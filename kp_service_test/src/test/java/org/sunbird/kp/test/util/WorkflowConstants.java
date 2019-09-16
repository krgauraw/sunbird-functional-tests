package org.sunbird.kp.test.util;

public class WorkflowConstants {

    public static final String CONTENT_IN_FLAG_STATE= "contentInFlagged";
    public static final String CONTENT_IN_FLAG_DRAFT= "contentInFlagDraft";
    public static final String CONTENT_IN_FLAG_REVIEW= "contentInFlagReview";
    public static final String CONTENT_IN_LIVE_STATE= "contentInLive";
    public static final String CONTENT_IN_LIVE_IMAGE_DRAFT_STATE= "contentInLiveImageDraft";
    public static final String CONTENT_IN_REVIEW_STATE= "contentInReview";
    public static final String CONTENT_IN_RETIRED_STATE= "contentRetired";


    // TODO: Add all the workflows for all test objects possible (Of Resource Type and Collection Type)
    public static final String contentWorkFlows = "{\n" +
            "\t\"contentInDraft\" : [],\n" +
            "\t\"contentUpload\" : [\"Upload\"],\n" +
            "\t\"contentDraftUpdated\" : [\"Update\"],\n" +
            "\t\"contentInReview\" : [\"Upload\", \"Review\"],\n" +
            "\t\"contentInLive\": [\"Upload\", \"Publish\"],\n" +
            "\t\"contentInLiveImageDraft\" : [\"Upload\", \"Publish\", \"Get\", \"Update\"],\n" +
            "\t\"contentInLiveImageReview\" : [\"Upload\", \"Publish\", \"Get\", \"Update\", \"Review\"],\n" +
            "\t\"contentInUnlisted\" : [\"Upload\", \"Unlisted\"],\n" +
            "\t\"contentInFlagged\" : [\"Upload\", \"Publish\", \"Get\", \"Flag\"],\n" +
            "\t\"contentInFlagDraft\" :  [\"Upload\", \"Publish\", \"Get\", \"Flag\", \"AcceptFlag\"],\n" +
            "\t\"contentInFlagReview\" :  [\"Upload\", \"Publish\", \"Get\", \"Flag\", \"AcceptFlag\", \"Review\"],\n" +
            "\t\"contentRetired\" : [\"Retire\"],\n" +
            "\t\"contentDiscarded\" : [\"Discard\"]\n" +
            "}";
    public static final String assetWorkFlows = "{\n" +
            "\"assetDraft\":[ ],\n" +
            "\"assetLive\":[\"Upload\"]\n" +
            "}";
    // Collection With Unit's or/and Resources and/or Asset's
    public static final String collectionWorkFlows = "{\n" +
            "\"collectionInDraft\":[],\n" +
            "\"collectionUnitsInDraft\":[\"Update\"],\n" +
            "\"collectionUnitsInLive\":[\"Update\", \"Publish\"],\n" +
            "\"collectionRead\":[\"Update\", \"Publish\"],\n" +
            "\"collectionUnitsResourcesInDraft\":[\"CreateResources\", \"Update\"]\n" +
            "}";

}
