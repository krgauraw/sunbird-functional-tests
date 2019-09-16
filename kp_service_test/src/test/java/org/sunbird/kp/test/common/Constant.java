package org.sunbird.kp.test.common;

/**
 * Test Constants
 * @author Kumar Gauraw
 */
public class Constant {

    private Constant() {}

    //Header Constants
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_APPLICATION_JSON_LD = "application/json-ld";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String X_CHANNEL_ID = "X-Channel-Id";
    public static final String X_AUTHENTICATED_USER_TOKEN = "x-authenticated-user-token";

    //Test Client Constants
    public static final String KP_ENDPOINT = "kpRestClient";
    public static final String KP_SEARCH_SERVICE_ENDPOINT = "kpSearchServiceClient";
    public static final String KP_DIAL_SERVICE_ENDPOINT = "kpDIALServiceClient";
    public static final String KEYCLOAK_ENDPOINT = "keycloakTestClient";

    // Request Constants
    public static final String MULTIPART_FILE_NAME = "file";
    public static final String REQUEST_FORM_DATA = "request.params";
    public static final String REQUEST_JSON = "request.json";
    public static final String RESPONSE_JSON = "response.json";
    public static final String EQUAL_SIGN = "=";

    public static final String CONTENT_TYPE_RESOURCE="Resource";
    public static final String CONTENT_TYPE_PLUGIN="Plugin";
    public static final String CONTENT_TYPE_COLLECTION="Collection";


    public static final int ES_SYNC_WAIT_TIME = 10000;
    public static final int PUBLISH_WAIT_TIME = 20000;

    public static final String CREATOR = "Creator";
    public static final String REVIEWER = "Reviewer";


}
