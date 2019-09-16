package org.sunbird.kp.test.content.v3;

/**
 * This Class Holds All Scenarios for Content API
 *
 * @author Kumar Gauraw
 */
public class ContentV3Scenario {

    //Scenario's for Content Create API
    //Resource
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourcePdfContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourceEcmlContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourceHtmlContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourceH5pContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourceYoutubeContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourceVideoMp4ContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST =
            "testCreateResourceVideoMpegContentWithValidRequest";
    public static final String TEST_CREATE_RESOURCE_WITH_INVALID_OS_NAME =
            "testCreateResourceWithInvalidOsName";
    public static final String TEST_CREATE_RESOURCE_WITH_NO_CHANNEL_ID =
            "testCreateResourceWithNoChannelId";
    public static final String TEST_CREATE_RESOURCE_WITH_CONCEPTS=
            "testCreateResourceWithConcepts";
    public static final String TEST_CREATE_RESOURCE_WITH_QUESTIONS=
            "testCreateResourceWithQuestions";
    public static final String TEST_CREATE_RESOURCE_WITH_SCREENSHOTS=
            "testCreateResourceWithScreenshot";
    public static final String TEST_CREATE_RESOURCE_WITH_INVALID_CONCEPT_ID=
            "testCreateResourceWithInvalidConceptId";
    public static final String TEST_CREATE_RESOURCE_CONTENT_WITH_ECML_BODY=
            "testCreateResourceWithEcmlBody";
    public static final String TEST_CREATE_RESOURCE_CONTENT_WITH_INVALID_LICENSE_TYPE=
            "testCreateResourceWithInvalidLicenseType";
    public static final String TEST_CREATE_RESOURCE_CONTENT_WITH_VALID_FRAMEWORK_INVALID_SUBJECT=
            "testCreateResourceWithValidFrameworkInvalidSubject";
    public static final String TEST_CREATE_RESOURCE_CONTENT_WITH_VALID_FRAMEWORK_INVALID_CATEGORY=
            "testCreateResourceWithValidFrameworkInvalidCategor";
    public static final String TEST_CREATE_RESOURCE_CONTENT_WITH_INVALID_FRAMEWORK=
            "testCreateResourceWithInvalidFramework";
    public static final String TEST_CREATE_RESOURCE_CONTENT_WITH_INVALID_RESOURCE_TYPE=
            "testCreateResourceWithInvalidResourceType";
    //Plugin
    public static final String TEST_CREATE_PLUGIN_CONTENT_WITH_VALID_REQUEST =
            "testCreatePluginContentWithValidRequest";
    public static final String TEST_CREATE_PLUGIN_CONTENT_WITH_NO_CODE_GIVEN =
            "testCreatePluginContentWithNoCodeGiven";

    //Asset
    public static final String TEST_CREATE_ASSET_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST =
            "testCreateAssetVideoMp4ContentWithValidRequest";
    public static final String TEST_CREATE_ASSET_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST =
            "testCreateAssetVideoMpegContentWithValidRequest";
    public static final String TEST_CREATE_ASSET_VIDEO_WEBM_CONTENT_WITH_VALID_REQUEST =
            "testCreateAssetVideoWebmContentWithValidRequest";
    public static final String TEST_CREATE_ASSET_IMAGE_JPEG_CONTENT_WITH_VALID_REQUEST =
            "testCreateAssetImageJpegContentWithValidRequest";
    public static final String TEST_CREATE_ASSET_IMAGE_PNG_CONTENT_WITH_VALID_REQUEST =
            "testCreateAssetImagePngContentWithValidRequest";

    //Invalid Request
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_NAME =
            "testCreateResourcePdfContentWithInvalidRequestWithoutName";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_CODE =
            "testCreateResourcePdfContentWithInvalidRequestWithoutCode";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_MIMETYPE =
            "testCreateResourcePdfContentWithInvalidRequestWithoutMimeType";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_CONTENT_TYPE =
            "testCreateResourcePdfContentWithInvalidRequestWithoutContentType";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITH_SYSTEM_PROPS =
            "testCreateResourcePdfContentWithInvalidRequestWithSystemProps";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_MIMETYPE =
            "testCreateResourcePdfContentWithInvalidMimeType";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_CONTENT_TYPE =
            "testCreateResourcePdfContentWithInvalidContentType";
    public static final String TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_DUPLICATE_IDENTIFIER =
            "testCreateResourcePdfContentWithDuplicateIdentifier";

    //Collection
    public static final String TEST_COLLECTION_WITH_CHILDREN=
            "testCreateResourcePdfContentWithDuplicateIdentifier";
    public static final String TEST_COLLECTION_WITH_NO_CHILDREN=
            "testCreateResourcePdfContentWithDuplicateIdentifier";


    // Scenario's for Content Read API
    public static final String TEST_READ_RESOURCE_CONTENT_WITH_VALID_IDENTIFIER =
            "testReadResourceContentWithValidIdentifier";
    public static final String TEST_READ_RESOURCE_CONTENT_WITH_INVALID_IDENTIFIER =
            "testReadResourceContentWithInvalidIdentifier";


    // Scenario's for upload Content  API
    public static final String TEST_UPLOAD_CONTENT_IN_LIVE =
            "testuploadContentInLive";
    public static final String TEST_UPLOAD_CONTENT_IN_REVIEW =
            "testuploadContentInReview";
    public static final String TEST_UPLOAD_CONTENT_IN_RETIRED =
            "testuploadContentInRetired";
    public static final String TEST_UPLOAD_CONTENT_IN_FLAG =
            "testuploadContentInFlag";
    public static final String TEST_UPLOAD_CONTENT_IN_FLAG_DRAFT =
            "testuploadContentInFlagDraft";
    public static final String TEST_UPLOAD_CONTENT_IN_FLAG_REVIEW =
            "testuploadContentInFlagReview";

    public static final String TEST_UPLOAD_RESOURCE_WITH_PDF =
            "testUploadResourceWithPdf";
    public static final String TEST_UPLOAD_RESOURCE_WITH_ECML =
            "testUploadResourceWithEcml";
    public static final String TEST_UPLOAD_RESOURCE_WITH_HTML =
            "testUploadResourceWithHtml";
    public static final String TEST_UPLOAD_RESOURCE_WITH_H5P =
            "testUploadResourceWithh5p";
    public static final String TEST_UPLOAD_RESOURCE_WITH_X_YOUTUBE =
            "testUploadResourceWithxyoutube";
    public static final String TEST_UPLOAD_ASSET_WITH_IMAGE_PNG =
            "testUploadAssetWithImagePng";
    public static final String TEST_UPLOAD_ASSET_WITH_IMAGE_JPG =
            "testUploadAssetWithImageJpg";
    public static final String TEST_UPLOAD_ASSET_WITH_VIDEO_MP4 =
            "testUploadAssetWithVideoMp4";
    public static final String TEST_UPLOAD_ASSET_WITH_VIDEO_WEBM =
            "testUploadAssetWithVideoWebm";
    public static final String TEST_UPLOAD_ASSET_WITH_VIDEO_MPEG =
            "testUploadAssetWithVideoMpeg";
    public static final String TEST_UPLOAD_ASSET_WITH_VIDEO_X_YOUTUBE =
            "testUploadAssetWithVideoXYoutube";
    public static final String TEST_UPLOAD_ASSET_WITH_AUDIO_MP3 =
            "testUploadAssetWithAudioMp3";
    public static final String TEST_UPLOAD_RESOURCE_LIVE_CONTENT_PDF =
            "testUploadResourceForLiveContentWithPdf";

    public static final String TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_URL =
            "testUploadResourcePdfWithFileUrl";
    public static final String TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_MISMATCHED_MIME =
            "testUploadResourcePdfWithFileMismatchedMime";
    public static final String TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_INVALID_IDENTIFIER =
            "testUploadResourcePdfWithFileInvalidId";

    public static final String TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_VALID_JSON =
            "testUploadResourceWithValidEcmlValidJson";
    public static final String TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_CUSTOM_PLUGIN=
            "testUploadResourceWithValidEcmlCustom_Plugin";

    public static final String TEST_UPLOAD_RESOURCE_WITH_GREATER_THAN_50MB_ZIP =
            "testUploadResourceWithGreatedThan50MbZip";
    public static final String TEST_UPLOAD_RESOURCE_WITH_EMPTY_ZIP =
            "testUploadResourceWithEmptyZip";
    public static final String TEST_UPLOAD_RESOURCE_ZIP_WITHOUT_INDEX =
            "testUploadResourceZipWithoutIndex";
    public static final String TEST_UPLOAD_RESOURCE_ZIP_WITHOUT_ASSET =
            "testUploadResourceZipWithoutAsset";
    public static final String TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_INVALID_JSON =
            "testUploadResourceWithEcmlInvalidJson";
    public static final String TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_MISSING_ASSET =
            "testUploadResourceWithValidEcmlMissingAsset";
    public static final String TEST_UPLOAD_RESOURCE_ECML_WITH_TWIN_ANIMATION_AUDIO_SPRITES_IMG_SPRITES =
            "testUploadResourceEcmlWithTwinAnimationAudioSpritesImgSprites";
    public static final String TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_CONTAINING_JSON_ITEM =
            "testUploadResourceEcmlWithJsonItem";
    public static final String TEST_UPLOAD_RESOURCE_WITH_VALID_HTML_WITHOUT_HTML_INDEX =
            "testUploadResourceWithValidHtmlWithoutIndexHtml";
    public static final String TEST_UPLOAD_RESOURCE_ZIP_WITH_MISSING_ASSET_ID =
            "testUploadResourceZipWithMissingAsserId";

    // Scenario's for review Content  API
    public static final String TEST_REVIEW_WITH_VALID_IDENTIFIER_WITH_NOT_UPLOAD_FILE =
            "testReviewWithValidIdentifieWithNonUploadFile";
    public static final String TEST_REVIEW_WITH_VALID_IDENTIFIER =
            "testReviewWithValidIdentifier";
    public static final String TEST_REVIEW_WITH_INVALID_IDENTIFIER =
            "testReviewWithInvalidIdentifier";
    public static final String TEST_REVIEW_WITH_REVIEWED_CONTENT =
            "testReviewWithReviewedContent";


    // Scenario's for Content Update API
    public static final String TEST_UPDATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST =
            "testUpdateResourcePdfContentWithValidRequest";
    public static final String TEST_UPDATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST =
            "testUpdateResourceEcmlContentWithValidRequest";
    public static final String TEST_UPDATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST =
            "testUpdateResourceHtmlContentWithValidRequest";
    public static final String TEST_UPDATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST =
            "testUpdateResourceH5pContentWithValidRequest";
    public static final String TEST_UPDATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST =
            "testUpdateResourceYoutubeContentWithValidRequest";
    public static final String TEST_UPDATE_CONTENT_WITH_BLANK_VERSION_KEY =
            "testUpdateContentBlankVersionKey";
    public static final String TEST_UPDATE_CONTENT_WITH_NOT_FOUND_REQUEST =
            "testUpdateContentNotFoundRequest";
    public static final String TEST_UPDATE_CONTENT_WITH_INVALID_VERSION_KEY =
            "testUpdateContentInvalidVersionKey";
    public static final String TEST_UPDATE_CONTENT_WITH_INVALID_METADATA =
            "testUpdateContentInvalidMetadata";
    //Get Content should have same framework if allow update is false
    public static final String TEST_UPDATE_CONTENT_WITH_NEW_FRAMEWORK =
            "testUpdateContentNewFramework";
    //Get Content should have same Status if allow update is false
    public static final String TEST_UPDATE_CONTENT_WITH_CHANGED_STATUS =
            "testUpdateContentChangeStatus";
    //Get Content should have same MimeType if allow update is false
    public static final String TEST_UPDATE_CONTENT_WITH_NEW_MIMETYPE =
            "testUpdateContentNewMimetype";
    public static final String TEST_UPDATE_CONTENT_WITH_NEW_MEDIATYPE =
            "testUpdateContentNewMediaType";
    public static final String TEST_UPDATE_CONTENT_WITH_INVALID_CONTENT_TYPE =
            "testUpdateContentInvalidContentType";
    public static final String TEST_UPDATE_CONTENT_WITH_VALID_CONTENT_TYPE =
            "testUpdateContentValidContentType";
    public static final String TEST_UPDATE_CONTENT_STATUS_REVIEW =
            "testUpdateContentInReviewState";
    public static final String TEST_UPDATE_CONTENT_IN_LIVE =
            "testUpdateContentInLive";
    public static final String TEST_UPDATE_CONTENT_IN_LIVE_WITH_IMAGE =
            "testUpdateContentInLiveWithImage";
    public static final String TEST_UPDATE_CONTENT_STATUS_FLAGGED =
            "testUpdateStatusFlagged";
    public static final String TEST_UPDATE_CONTENT_STATUS_FLAGREVIEW =
            "testUpdateStatusFlagReview";
    public static final String TEST_UPDATE_CONTENT_IN_RETIRED =
            "testUpdateContentInRetired";
    public static final String TEST_UPDATE_CONTENT_AFTER_DISCARD =
            "testUpdateContentAfterDiscard";
    public static final String TEST_UPDATE_WITH_IMAGE_ID =
            "testUpdateWithImageId";
    //Get Content Should expect lastSubmittedOn if allowUpdate is true

    public static final String TEST_UPDATE_WITH_SYSTEM_PROPERTY =
            "testUpdateContentWithSystemProperty";
    public static final String TEST_UPDATE_WITH_INVALID_FORMAT_RESERVED_DIALCODES =
            "testUpdateWithInvalidReservedDialcodes";
    public static final String TEST_UPDATE_WITH_STALE_DATA =
            "testUpdateContentWithStaleData";

    //Scenario's for Content Retire API
    public static final String TEST_RETIRE_WITH_IMAGE_ID =
            "testRetireContentWithImageId";
    public static final String TEST_RETIRE_CONTENT_STATUS_DRAFT =
            "testRetireStatusDraft";
    public static final String TEST_RETIRE_COLLECTION_STATUS_DRAFT =
            "testRetireCollectionStatusDraft";
    public static final String TEST_RETIRE_CONTENT_STATUS_REVIEW =
            "testRetireStatusReview";
    public static final String TEST_RETIRE_CONTENT_STATUS_FLAGGED =
            "testRetireStatusFlagged";
    public static final String TEST_RETIRE_CONTENT_STATUS_FLAGDRAFT =
            "testRetireStatusFlagDraft";
    public static final String TEST_RETIRE_CONTENT_STATUS_FLAGREVIEW =
            "testRetireStatusFlagReview";
    public static final String TEST_RETIRE_CONTENT_STATUS_LIVE =
            "testRetireStatusLive";
    public static final String TEST_RETIRE_CONTENT_STATUS_UNLISTED =
            "testRetireStatusUnlisted";
    public static final String TEST_RETIRE_CONTENT_STATUS_LIVE_WITH_IMAGE_REVIEW =
            "testRetireStatusLiveWithImageReview";
    public static final String TEST_RETIRE_CONTENT_STATUS_LIVE_WITH_IMAGE_DRAFT =
            "testRetireStatusLiveWithImageDraft";
    public static final String TEST_RETIRE_CONTENT_STATUS_RETIRED =
            "testRetireStatusRetired";
    //For Live node which has image
    public static final String TEST_RETIRE_CONTENT_AFTER_DISCARD =
            "testRetireAfterDiscard";


    // Scenario's for Content Discard API
    public static final String TEST_DISCARD_WITH_IMAGE_ID =
            "testDiscardContentWithImageId";
    public static final String TEST_DISCARD_CONTENT_STATUS_DRAFT =
            "testDiscardStatusDraft";
    public static final String TEST_DISCARD_COLLECTION_STATUS_DRAFT =
            "testPublishCollectionWithResourceContent";
    public static final String TEST_DISCARD_CONTENT_STATUS_REVIEW =
            "testDiscardStatusReview";
    public static final String TEST_DISCARD_CONTENT_STATUS_FLAGGED =
            "testDiscardStatusFlagged";
    public static final String TEST_DISCARD_CONTENT_STATUS_FLAGREVIEW =
            "testDiscardStatusFlagReview";
    public static final String TEST_DISCARD_CONTENT_STATUS_LIVE =
            "testDiscardStatusLive";
    public static final String TEST_DISCARD_CONTENT_STATUS_UNLISTED =
            "testDiscardStatusUnlisted";
    public static final String TEST_DISCARD_CONTENT_STATUS_LIVE_WITH_IMAGE_DRAFT =
            "testDiscardStatusLiveWithImageDraft";
    public static final String TEST_DISCARD_CONTENT_STATUS_LIVE_WITH_IMAGE_REVIEW =
            "testDiscardStatusLiveWithImageReview";
    public static final String TEST_DISCARD_CONTENT_STATUS_RETIRED =
            "testDiscardStatusRetired";
    public static final String TEST_DISCARD_CONTENT_AFTER_DISCARD =
            "testDiscardAfterDiscard";

    //Publish Content API
    public static final String TEST_PUBLISH_RESOURCE_PDF_CONTENT =
            "testPublishResourcePdfContent";
    public static final String TEST_PUBLISH_RESOURCE_ECML_CONTENT =
            "testPublishResourceEcmlContent";
    public static final String TEST_PUBLISH_RESOURCE_HTML_CONTENT =
            "testPublishResourceHtmlContent";
    public static final String TEST_PUBLISH_RESOURCE_H5P_CONTENT =
            "testPublishResourceH5pContent";
    public static final String TEST_PUBLISH_RESOURCE_VIDEO_MP4 =
            "testPublishResourceVideoMp4";
    public static final String TEST_PUBLISH_RESOURCE_VIDEO_WEBM =
            "testPublishResourceVideoWebm";
    public static final String TEST_PUBLISH_RESOURCE_VIDEO_X_YOUTUBE =
            "testPublishResourceVideoXYoutube";
    public static final String TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT =
            "testPublishCollectionWithResourceContent";
    public static final String TEST_PUBLISH_TEXTBOOK_WITH_RESOURCE_CONTENT =
            "testPublishTextbookWithResourceContent";
    public static final String TEST_PUBLISH_COURSE_WITH_RESOURCE_CONTENT =
            "testPublishCourseWithResourceContent";
    public static final String TEST_PUBLISH_LESSONPLAN_WITH_RESOURCE_CONTENT =
            "testPublishLessonPlanWithResourceContent";
    public static final String TEST_REPUBLISH_RESOURCE_ECML_CONTENT =
            "testRepublishResourceEcmlContent";
    public static final String TEST_PUBLISH_DRAFT_VERSION_OF_PUBLISHED_RESOURCE_ECML_CONTENT =
            "testPublishDraftVersionOfPublishedResourceEcmlContent";
    public static final String TEST_PUBLISH_RESOURCE_ECML_CONTENT_WITH_CORRUPTED_ECML_BODY =
            "testPublishResourceEcmlContentWithCorruptedEcmlBody";
    public static final String TEST_PUBLISHING_EXISTING_LIVE_RESOURCE_CONTENT =
            "testPublishingExistingLiveResourceContent";
    public static final String TEST_PUBLISHING_DELETED_RESOURCE_CONTENT =
            "testPublishingDeletedResourceContent";
    public static final String TEST_PUBLISHING_INVALID_RESOURCE_CONTENT =
            "testPublishingInvalidResourceContent";
    public static final String TEST_PUBLISH_WITH_DIFFERENT_VERSION =
            "testPublishWithDifferentVersionResource";
    public static final String TEST_PUBLISH_WITH_RESOURCE_RETIRED=
            "testPublishWithResourceRetired";
    public static final String TEST_PUBLISH_COLLECTION_WITH_CONCEPT=
            "testPublishWithResourceRetired";

    //Accept Flag Scenarios
    public static final String TEST_ACCEPT_FLAG_VALID_ID_AND_VALID_STATUS =
            "testAcceptFlagValidIdValidStatus";
    public static final String TEST_ACCEPT_FLAG_VALID_ID_AND_INVALID_STATUS =
            "testAcceptFlagValidIdInValidStatus";
    public static final String TEST_REACCEPT_FLAG_VALID_ID_AND_VALID_STATUS =
            "testReAcceptFlagValidIdInValidStatus";
    public static final String TEST_ACCEPT_FLAG_INVALID_ID =
            "testAcceptFlagInValidId";

    //Flag scenarios
    public static final String TEST_FLAG_VALID_REQUEST_VALID_ID =
            "testFlagValidRequestValidId";
    public static final String TEST_FLAG_VALID_REQUEST_INVALID_ID =
            "testFlagValidRequestInValidId";
    public static final String TEST_FLAG_VALID_REQUEST_WITHOUT_FLAGGEDBY =
            "testFlagValidRequestWithoutFlaggedBy";
    public static final String TEST_FLAG_VALID_REQUEST_WITHOUT_FLAG_ARRAY =
            "testFlagValidRequestWithoutFlagArray";
    public static final String TEST_FLAG_VALID_REQUEST_WITHOUT_VERSIONKEY =
            "testFlagValidRequestWithoutVersionkey";
    public static final String TEST_FLAG_VALID_REQUEST_WITH_INVALID_VERSIONKEY =
            "testFlagValidRequestWithInvalidVersionkey";
    public static final String TEST_FLAG_VALID_REQUEST_WITHOUT_FLAGGEDREASON =
            "testFlagValidRequestWithoutFlaggedReason";

    public static final String TEST_FLAG_WITH_FLAGGED_STATE_WITH_VALID_REQUEST =
            "testFlagValidRequestWithAlreadyFlagged";
    public static final String TEST_FLAG_WITH_FLAGDRAFT_STATE_WITH_VALID_REQUEST =
            "testFlagValidRequestWithResourceFlagDraft";
    public static final String TEST_FLAG_WITH_FLAGREVIEW_STATE_WITH_VALID_REQUEST =
            "testFlagValidRequestWithResourceFlagReview";
    public static final String TEST_FLAG_WITH_PUBLISHED_CONTENT_WITH_EXISTING_IMG_WITH_VALID_REQUEST =
            "testFlagWithPublishedContentWithExistingImage";
    public static final String TEST_FLAG_WITH_REVIEW_STATE_WITH_VALID_REQUEST =
            "testFlagValidRequestWithResourceInReview";
    public static final String TEST_FLAG_WITH_RETIRE_STATE_WITH_VALID_REQUEST =
            "testFlagValidRequestWithResourceInRetire";

}
