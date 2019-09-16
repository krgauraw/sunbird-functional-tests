package org.sunbird.kp.test.common;

/**
 * All API End Point Url
 * @author Kumar Gauraw
 */
public class APIUrl {

    public static final String USER_AUTH = "/auth/realms/sunbird/protocol/openid-connect/token";
    //Content Api
    public static final String CREATE_CONTENT = "/content/v3/create";
    public static final String READ_CONTENT = "/content/v3/read/";
    public static final String UPLOAD_CONTENT = "/content/v3/upload/";
    public static final String UPDATE_CONTENT = "/content/v3/update/";
    public static final String REVIEW_CONTENT = "/content/v3/review/";
    public static final String PUBLIC_PUBLISH_CONTENT = "/content/v3/publish/";
    public static final String UNLISTED_PUBLISH_CONTENT = "/content/v3/unlisted/publish/";
    public static final String GET_PRESIGNED_URL = "/content/v3/upload/url/";
    public static final String BUNDLE_CONTENT = "/content/v3/bundle";
    public static final String READ_CONTENT_HIERARCHY = "/content/v3/hierarchy/";
    public static final String UPDATE_CONTENT_HIERARCHY = "/content/v3/hierarchy/update";
    public static final String COPY_CONTENT = "/content/v3/copy/";
    public static final String RETIRE_CONTENT = "/content/v3/retire/";
    public static final String FLAG_CONTENT = "/content/v3/flag/";
    public static final String ACCEPT_FLAG_CONTENT = "/content/v3/flag/accept/";
    public static final String REJECT_FLAG_CONTENT = "/content/v3/flag/reject/";
    public static final String DISCARD_CONTENT = "/content/v3/discard/";
    public static final String DIAL_LINK_CONTENT = "/content/v3/dialcode/link";
    public static final String DIAL_LINK_COLLECTION_CONTENT = "/collection/v3/dialcode/link/";
    public static final String RESERVE_DIAL_CONTENT = "/content/v3/dialcode/reserve/";
    public static final String RELEASE_DIAL_CONTENT = "/content/v3/dialcode/release/";

    //Search API
    public static final String COMPOSITE_SEARCH = "/v3/search";

    //DIAL Api's
    public static final String GENERATE_DIALCODE = "/dialcode/v3/generate";
    public static final String SEARCH_DIALCODE = "/dialcode/v3/search";

}
