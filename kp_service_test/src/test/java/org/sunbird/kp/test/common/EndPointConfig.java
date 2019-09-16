package org.sunbird.kp.test.common;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * End Point Configuration Class
 * @author Kumar Gauraw
 */
@Configuration
public class EndPointConfig {

    @Bean
    public HttpClient kpRestClient() {
        return CitrusEndpoints.http()
                .client()
                .requestUrl(AppConfig.config.getString("kp_base_uri"))
                .build();
    }

    @Bean
    public HttpClient keycloakTestClient() {
        return CitrusEndpoints.http().client().requestUrl(AppConfig.config.getString("env_base_path")).build();
    }

    @Bean
    public HttpClient kpSearchServiceClient() {
        return CitrusEndpoints.http()
                .client()
                .requestUrl(AppConfig.config.getString("kp_search_base_uri"))
                .build();
    }

    @Bean
    public HttpClient kpDIALServiceClient() {
        return CitrusEndpoints.http()
                .client()
                .requestUrl(AppConfig.config.getString("kp_dial_base_uri"))
                .build();
    }

}
