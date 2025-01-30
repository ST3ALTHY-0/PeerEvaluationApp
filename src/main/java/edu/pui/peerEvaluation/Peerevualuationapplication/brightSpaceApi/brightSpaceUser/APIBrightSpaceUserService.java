package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIBrightSpaceUserService {

    // will get webClient that we configured in config/WebClientConfig.java
    private final WebClient webClient;
    private static final String WHOAMI_API_URL = "start/d2l/api/lp/(version)/users/whoami";
    private static final String USER_API_URL = "start/d2l/api/lp/(version)/profile/user/?"; 

    // private static final String API_VERSION = "version?";

    @Autowired
    public APIBrightSpaceUserService(WebClient webClient) {
        this.webClient = webClient;
    }

    //@param String identifier is optional, put null if getting whoami endpoint for basic data,
    //populate with userId to get extended user data 
    public BrightSpaceUser callWhoamiEndpoint(String accessToken) {
        try {

            return webClient.get() // start http GET request
                    .uri(WHOAMI_API_URL) // add the specific endpoint we want to get
                    .header("Authorization", "Bearer " + accessToken) // add accessToken to request so brightSpace knows
                                                                      // we are legit
                    .retrieve() // get a response
                    .bodyToMono(BrightSpaceUser.class) // specify that we just want the body of the response and throw
                                                       // it into a BrightSpaceUser class (which is just a 1to1 model of
                                                       // the data we're getting)
                    .block(); // this will throw an error if something goes wrong
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    //not sure that access token is needed here, i dont think so.
    public BrightSpaceUserExtended callUserIdEndpoint(String accessToken, String userId) {
        String url = USER_API_URL.replace("?", userId);
        try {
            return webClient.get()
                    .uri(url) 
                    .header("Authorization", "Bearer " + accessToken) //might remove access token
                    .retrieve()
                    .bodyToMono(BrightSpaceUserExtended.class)
                    .block();
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }


}
