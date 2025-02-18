package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.brightSpaceUser;

import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.brightSpaceEnrollment.BrightSpaceEnrollment;
import lombok.Data;

//this class models a brightSpace User when getting data from
//the '/d2l/api/lp/(version)/users/whoami [GET]' endpoint

@Data
public class BrightSpaceUser {

    // call brightSpace whoami endpoint to get basic data
    private String Identifier; // {UserId} will need this for later use in other endpoints
    private String FirstName;
    private String LastName;
    private String UniqueName;
    private String ProfileIdentifier;
    private String Pronouns;

    // fields I added to more easily see what classes/role the user is
    private List<BrightSpaceEnrollment> brightSpaceClassList;
    private String role;
}
