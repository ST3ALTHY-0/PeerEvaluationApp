package edu.pui.peerEvaluation.Peerevualuationapplication.config;

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* 
* config file for WebClient.
* We set the base URL of all the endpoints that we will use,
* so that when we make a webClient obj to call the brightSpace API
* we don't need to add the base path every time.
*/

@Configuration
public class WebClientConfig {

    private static final String BRIGHTSPACE_API_BASE_ADDRESS = "https://auth.brightspace.com/d2l/api/lp/";

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(BRIGHTSPACE_API_BASE_ADDRESS).build();
    }

}
