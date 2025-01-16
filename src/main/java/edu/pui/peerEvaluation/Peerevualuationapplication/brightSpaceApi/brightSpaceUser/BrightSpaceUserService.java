package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class BrightSpaceUserService {

    // will get webClient that we configured in config/WebClientConfig.java
    private final WebClient webClient;
    //private static final String API_VERSION = "version?";

    @Autowired
    public BrightSpaceUserService(WebClient webClient) {
        this.webClient = webClient;
    }


    public BrightSpaceUser makeApiCallToGetUser(String accessToken, String apiUrl) {
        try {

            return webClient.get() // start http GET request
                    .uri(apiUrl) // add the specific endpoint we want to get
                    .header("Authorization", "Bearer " + accessToken) // add accessToken to request so brightSpace knows
                                                                      // we are legit
                    .retrieve() // get a response
                    .bodyToMono(BrightSpaceUser.class) // specify that we just want the body of the response and throw
                                                       // it into a BrightSpaceUser obj (which is just a 1to1 model of
                                                       // the data we're getting)
                    .block(); // this will throw an error if something goes wrong
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }
}
