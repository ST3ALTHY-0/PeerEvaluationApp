package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceEnrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

////d2l/api/lp/(version)/enrollments/users/(userId)/orgUnits/
@Service
public class BrightSpaceClassService {

    // will get webClient that we configured in config/WebClientConfig.java
    private final WebClient webClient;
    // private static final String API_VERSION = "version?";

    @Autowired
    public BrightSpaceClassService(WebClient webClient) {
        this.webClient = webClient;
    }

    /*
     * @param userId is the userId of the user in brightSpace it is also called
     * 'Identifier' in the brightSpaceUser class
     */
    // TODO: need to add userId to either the header or the apiURL
    public BrightSpaceEnrollment makeApiCallToGetUser(String accessToken, String apiUrl, String userId) {
        try {

            return webClient.get() // start http GET request
                    .uri(apiUrl) // add the specific endpoint we want to get
                    .header("Authorization", "Bearer " + accessToken) // add accessToken to request so brightSpace knows
                                                                      // we are legit
                    .retrieve() // get a response
                    .bodyToMono(BrightSpaceEnrollment.class) // specify that we just want the body of the response and
                                                             // throw
                    // it into a BrightSpaceUser class (which is just a 1to1 model of
                    // the data we're getting)
                    .block(); // this will throw an error if something goes wrong
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }
}
