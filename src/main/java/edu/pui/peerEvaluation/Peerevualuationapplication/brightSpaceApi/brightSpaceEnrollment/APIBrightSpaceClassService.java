package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceEnrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpacePagedResults.PagedResultSet;
import reactor.core.publisher.Mono;

//we will call myenrollments endpoint which will get Enrollment info of current user, requires access token 
//also we should only return the courses that the user is actually enrolled in, there is a boolean value we should be able to set when calling the endpoint
// /d2l/api/lp/(version)/enrollments/myenrollments/ [GET]@Service
public class APIBrightSpaceClassService {

    // 
    private final WebClient webClient;
    private static final String API_VERSION = "version?";
    private static final String ENROLLMENT_URL = "{start}/d2l/api/lp/(version)/enrollments/myenrollments/";


    @Autowired
    public APIBrightSpaceClassService(WebClient webClient) {
        this.webClient = webClient;
    }


    //the api will return multiple (paged) results because the user is likely enrolled in multiple courses so we will need to deal with that if webClient doesn't auto handle it
    public List<BrightSpaceEnrollment> callEnrollmentEndpoint(String accessToken) {
        return webClient.get()
                .uri(ENROLLMENT_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PagedResultSet<BrightSpaceEnrollment>>() {})
                .map(PagedResultSet::getItems)
                .block();
    }

}
