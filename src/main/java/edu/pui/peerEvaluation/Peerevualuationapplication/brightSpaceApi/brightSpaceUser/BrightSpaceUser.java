package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser;

import lombok.Data;

//this class models a brightSpace User when getting data from
//the '/d2l/api/lp/(version)/users/whoami [GET]' endpoint

@Data
public class BrightSpaceUser {

    //changed naming convention here to better match brightSpace's format

    private String Identifier; //{UserId} will need this for later use in other endpoints
    private String FirstName;
    private String LastName;
    private String UniqueName;
    private String ProfileIdentifier;
    private String Pronouns;
    
}
