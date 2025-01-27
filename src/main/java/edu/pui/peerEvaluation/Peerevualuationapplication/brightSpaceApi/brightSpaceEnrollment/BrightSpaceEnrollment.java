package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceEnrollment;

import java.util.List;

import lombok.Data;

@Data
public class BrightSpaceEnrollment {
    private OrgUnitInfo OrgUnit;
    private Access Access;
    private String PinDate;

    @Data
    public static class OrgUnitInfo {
        private String Identifier;
        private String Name;
        private String Code;
        private String Path;
    }

    @Data
    public static class Access {
        private boolean IsActive;
        private String StartDate;
        private String EndDate;
        private Boolean CanAccess;
        private String ClasslistRoleName;
        private List<String> LISRoles;
        private String LastAccessed;
    }
}

/*
 * Get enrollments of an user(instructor)
 * /d2l/api/lp/(version)/enrollments/myenrollments/ [GET]
 * Will return array of
 * {
 * "OrgUnit": { <composite:Enrollment.OrgUnitInfo> },
 * "Access": {
 * "IsActive": <boolean>,
 * "StartDate": <string:UTCDateTime>|null,
 * "EndDate": <string:UTCDateTime>|null,
 * "CanAccess": <boolean>,
 * "ClasslistRoleName": <string>|null,
 * "LISRoles": [ <string>, ... ],
 * "LastAccessed": <string:UTCDateTime>|null
 * },
 * "PinDate": <string:UTCDateTime>|null
 * }
 */